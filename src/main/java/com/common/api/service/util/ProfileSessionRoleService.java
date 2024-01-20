package com.common.api.service.util;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.amazonaws.services.iot.model.ModelStatus;
import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.constant.APIRolePrivilegeType;
import com.common.api.datasink.service.DivisionService;
import com.common.api.datasink.service.GroupService;
import com.common.api.datasink.service.MetadataService;
import com.common.api.datasink.service.ModuleService;
import com.common.api.datasink.service.RoleCompanyService;
import com.common.api.datasink.service.RoleDivisionService;
import com.common.api.datasink.service.RoleGroupService;
import com.common.api.datasink.service.RoleModuleService;
import com.common.api.datasink.service.RoleUserService;
import com.common.api.datasink.service.UserLastActivityService;
import com.common.api.datasink.service.UserService;
import com.common.api.datasink.service.UserSessionService;
import com.common.api.model.Division;
import com.common.api.model.Group;
import com.common.api.model.Module;
import com.common.api.model.Metadata;
import com.common.api.model.RoleCompany;
import com.common.api.model.RoleDivision;
import com.common.api.model.RoleGroup;
import com.common.api.model.RoleModule;
import com.common.api.model.RoleUser;
import com.common.api.model.User;
import com.common.api.model.UserSession;
import com.common.api.model.type.CategoryType;
import com.common.api.model.type.SessionType;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.util.APIDateTime;   

@Service
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"}) 
public class ProfileSessionRoleService extends APIFixedConstant {

	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {              
		return new PropertySourcesPlaceholderConfigurer();      
	} 
	
	@Autowired  
	MetadataService metadataService;       
    @Autowired 
    GroupService groupService;        
    @Autowired 
    DivisionService divisionService;        
    @Autowired 
    ModuleService moduleService;        
    @Autowired 
    UserService userService;   
    @Autowired 
    UserSessionService userSessionService;
    @Autowired 
    UserLastActivityService userLastActivityService; 
    
    @Autowired 
    RoleUserService roleUserService;    
    @Autowired 
    RoleGroupService roleGroupService;     
    @Autowired 
    RoleCompanyService roleCompanyService;     
    @Autowired 
    RoleDivisionService roleDivisionService;      
    @Autowired 
    RoleModuleService roleModuleService;       
     
	@Value("${app.static.session.id}")                      
	private String appStaticSessionId = "";     
	@Value("${e002.session.not.matched}")       
	public String e002SessionNotMatched = "";  
	 
	public String getSessionType(String companyId, String resourceType, String rolePrivilegeType) {  

		String result = "";   
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, resourceType);
		 
		if (metadataList.size() > 0) { 
			if (rolePrivilegeType.contains(APIRolePrivilegeType._READ_ALL.toString())) 
				result = metadataList.get(0).getSessionTypeReadAll(); 
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._CREATE_ALL.toString())) 
				result = metadataList.get(0).getSessionTypeCreateAll();
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._UPDATE_ALL.toString())) 
				result = metadataList.get(0).getSessionTypeUpdateAll();
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._DELETE_ALL.toString()))  
				result = metadataList.get(0).getSessionTypeDeleteAll();  
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._READ.toString()))
				result = metadataList.get(0).getSessionTypeRead(); 
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._CREATE.toString())) 
				result = metadataList.get(0).getSessionTypeCreate();
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._UPDATE.toString())) 
				result = metadataList.get(0).getSessionTypeUpdate();
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._DELETE.toString()))  
				result = metadataList.get(0).getSessionTypeDelete();
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._DASHBOARD_ALL.toString())) 
				result = metadataList.get(0).getSessionTypeReadAll(); 
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._QUERY_ALL.toString())) 
				result = metadataList.get(0).getSessionTypeReadAll();  
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._REPORT_ALL.toString())) 
				result = metadataList.get(0).getSessionTypeReadAll(); 
			else if (rolePrivilegeType.contains(APIRolePrivilegeType._SEARCH_ALL.toString())) 
				result = metadataList.get(0).getSessionTypeReadAll();  
		} 
		return result; 
	} 
	
	public ProfileSessionRoleAccess verifyUserRolePermission(String resourceType, String type, String rolePrivilegeType, String sessionId, String companyId, String divisionId, String moduleId) {
		return verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", divisionId, moduleId, "");
	}
	 
	public ProfileSessionRoleAccess verifyUserRolePermission(String resourceType, String type, String rolePrivilegeType, String sessionId, String companyId, String groupId, String divisionId, String moduleId, String userId) { 
 
		List<String> companyLevelResources = new ArrayList<String>(); 
			companyLevelResources.add(APIResourceName.COMPANY.toString()); 
			companyLevelResources.add(APIResourceName.COMPANY_PREFERENCE.toString()); 
			companyLevelResources.add(APIResourceName.CONTINENT.toString());  
			companyLevelResources.add(APIResourceName.COUNTRY.toString()); 
			companyLevelResources.add(APIResourceName.META_DATA.toString());    
			
		List<String> divisionLevelResources = new ArrayList<String>();
			divisionLevelResources.add(APIResourceName.GROUP.toString()); 
			divisionLevelResources.add(APIResourceName.GROUP_PREFERENCE.toString());   
			divisionLevelResources.add(APIResourceName.DIVISION.toString()); 
			divisionLevelResources.add(APIResourceName.DIVISION_PREFERENCE.toString());     
			divisionLevelResources.add(APIResourceName.SUBSCRIPTION.toString());  
			divisionLevelResources.add(APIResourceName.ORDER.toString());   
		  
		String sessionTye = getSessionType(companyId, type, rolePrivilegeType); 

		System.out.println("sessionTye: " + sessionTye); 
		
		if (sessionTye.length() <= 0) {
 
			String errMess = "Session is not assigned to this resource";
			return new ProfileSessionRoleAccess(GC_STATUS_SESSION_FAILED, errMess, "");
			
		} else if (sessionTye.equals(SessionType.STATIC.toString())) { 
 
			if (appStaticSessionId.equals(sessionId)) 
				return new ProfileSessionRoleAccess(GC_STATUS_SUCCESS, "", "");
			
			String errMess = "Session token is invalid. Please check the session token";
			return new ProfileSessionRoleAccess(GC_STATUS_SESSION_FAILED, errMess, "");  
			 
		} else if (sessionTye.equals(SessionType.DYNAMIC.toString())) {  
 
			if (!appStaticSessionId.equals(sessionId)) { 
 
				List<String> emptyList = new ArrayList<String>(); 
				List<UserSession> userSessionList = userSessionService.viewListWithSecretKeyByCriteria("", "", sessionId, "", emptyList, emptyList, "", "", 0, 0); 

				System.out.println("sessionId: " + sessionId); 
				System.out.println("userSessionList.size(): " + userSessionList.size()); 
				
				if (userSessionList.size() > 0) {
 
					/** Updating the session activity time - Starts */ 
					Timestamp dbDataAndTime = APIDateTime.getGlobalDateTimeDBOperation();
 
					UserSession userSession = userSessionList.get(0);
					String sessionTypeDB 	= userSession.getType();
					String userIdDB  		= userSession.getUserId(); 
					String moduleIdDB  		= userSession.getModuleId(); 
					String divisionIdDB		= userSession.getDivisionId(); 
					String groupIdDB		= userSession.getGroupId(); 
					String companyIdDB 		= userSession.getCompanyId();
 					userSession.setModifiedAt(dbDataAndTime);   
					userSessionService.modifyUserSession(userSession);    
					/** Updating the session activity time - Ends */
 
					if (userIdDB.length() > 0) { 
 
						String userCategoryDB = "";  
						List<User> userList = userService.viewListByCriteria(companyId, "", "", "", userIdDB, "", "", "", emptyList, emptyList, "", "", 0, 0); 
						if (userList.size() > 0) {
							User userProfile = userList.get(0);
							userCategoryDB = userProfile.getCategory(); 
						}
						
						/** Role Access Validation for Super Admin */ 
						if (userCategoryDB.equalsIgnoreCase(CategoryType.User.SUPER_ADMIN.toString())) { 
							this.checkAndUpdateUserLastActivity(dbDataAndTime, sessionTypeDB, userIdDB);
							return new ProfileSessionRoleAccess(GC_STATUS_SUCCESS, "", userIdDB, userSession);
						/** Role Access Validation for Admin */
						} else if (userCategoryDB.equalsIgnoreCase(CategoryType.User.ADMIN.toString())) {
 
							String groupIdTemp = "";	 
							if (companyId.length() > 0 && userId.length() > 0) {
								List<String> statusList = Arrays.asList(ModelStatus.ACTIVE.toString());  
								List<User> userListTemp = userService.viewListByCriteria(companyId, groupId, divisionId, moduleId, userId, "", "", "", statusList, emptyList, "", "", 0, 0); 
								groupIdTemp = (userListTemp.size() > 0) ? userListTemp.get(0).getGroupId() : "";
							} else if (companyId.length() > 0 && moduleId.length() > 0) {
								List<Module> moduleListTemp = moduleService.viewListByCriteria(companyId, groupId, divisionId, moduleId, "", "", "", emptyList, emptyList, "", "", 0, 0);
								groupIdTemp = (moduleListTemp.size() > 0) ? moduleListTemp.get(0).getGroupId() : "";
							} else if (companyId.length() > 0 && divisionId.length() > 0) {
								List<Division> divisionListTemp = divisionService.viewListByCriteria(companyId, groupId, divisionId, "", "", emptyList, emptyList, emptyList, "", "", 0, 0);
								groupIdTemp = (divisionListTemp.size() > 0) ? divisionListTemp.get(0).getGroupId() : "";
							} else if (companyId.length() > 0 && groupId.length() > 0) {
								List<Group> groupListTemp = groupService.viewListByCriteria(companyId, groupId, "", "", "", emptyList, emptyList, "", "", 0, 0);
								groupIdTemp = (groupListTemp.size() > 0) ? groupListTemp.get(0).getId() : ""; 
							}
 
							if (groupIdTemp.equals(groupIdDB) && groupIdTemp.length() > 0) {    
 
								List<RoleUser> roleUserList = new ArrayList<RoleUser>();  
								if ((companyId.length() <= 0  || companyIdDB.equalsIgnoreCase(companyId)) && 
									(groupId.length() <= 0    || groupIdDB.equalsIgnoreCase(groupId)) &&
									(divisionId.length() <= 0 || divisionIdDB.equalsIgnoreCase(divisionId)) &&
									(moduleId.length() <= 0   || moduleIdDB.equalsIgnoreCase(moduleId))) {  
									roleUserList = roleUserService.viewListByCriteria(companyId, "", userIdDB, "", emptyList, emptyList, "", "", 0, 0);
								}	      
		 
								if (roleUserList.size() > 0) {  
 
									String roleGroupId = roleUserList.get(0).getRoleGroupId();  
									List<RoleGroup> roleGroupList = roleGroupService.viewListByCriteria(companyId, "", "", "", roleGroupId, emptyList, emptyList, "", "", 0, 0);
									
									if (roleGroupList.size() > 0) { 
	 
										String roleCompanyId  = roleGroupList.get(0).getRoleCompanyId();
										String roleDivisionId = roleGroupList.get(0).getRoleDivisionId();
										String roleModuleId   = roleGroupList.get(0).getRoleModuleId(); 
										
										List<String> rolePrivilegesList = new ArrayList<String>(); 
 
										if (companyLevelResources.contains(resourceType)) {
		 									List<RoleCompany> roleCompanyList = roleCompanyService.viewListByCriteria(companyId, roleCompanyId, emptyList, emptyList, "", "", 0, 0);
		 									if (roleCompanyList.size() > 0) 
												rolePrivilegesList = roleCompanyList.get(0).getRoleCompanyPrivileges(); 
										} else if (divisionLevelResources.contains(resourceType)) {
											List<RoleDivision> roleDivisionList = roleDivisionService.viewListByCriteria(companyId, "", roleDivisionId, emptyList, emptyList, "", "", 0, 0);
		 									if (roleDivisionList.size() > 0) 
												rolePrivilegesList = roleDivisionList.get(0).getRoleDivisionPrivileges(); 
										} else { 
											List<RoleModule> roleModuleList = roleModuleService.viewListByCriteria(companyId, "", "", roleModuleId, emptyList, emptyList, "", "", 0, 0); 
		 									if (roleModuleList.size() > 0) 
												rolePrivilegesList = roleModuleList.get(0).getRoleModulePrivileges(); 
										}  
				 
		 								if (rolePrivilegesList.contains(rolePrivilegeType)) { 
		 									this.checkAndUpdateUserLastActivity(dbDataAndTime, sessionTypeDB, userIdDB);
		 									return new ProfileSessionRoleAccess(GC_STATUS_SUCCESS, "", userIdDB, userSession);
		 								}  
		 							}
		 						} 	
							}
 
						/** Role Access Validation for User */  
						} else if (userCategoryDB.equalsIgnoreCase(CategoryType.User.USER.toString())) {
 
							List<RoleUser> roleUserList = new ArrayList<RoleUser>();  
							if ((companyId.length() <= 0  || companyIdDB.equalsIgnoreCase(companyId)) && 
								(groupId.length() <= 0    || groupIdDB.equalsIgnoreCase(groupId)) &&
								(divisionId.length() <= 0 || divisionIdDB.equalsIgnoreCase(divisionId)) &&
								(moduleId.length() <= 0   || moduleIdDB.equalsIgnoreCase(moduleId)) &&
								(userId.length() <= 0     || userIdDB.equalsIgnoreCase(userId))) {  
								roleUserList = roleUserService.viewListByCriteria(companyId, "", userIdDB, "", emptyList, emptyList, "", "", 0, 0);
							}    

							System.out.println("roleUserList.size(): " + roleUserList.size()); 
							System.out.println("rolePrivilegeType: " + rolePrivilegeType); 
							
							if (roleUserList.size() > 0) {   
								
								String roleGroupId = roleUserList.get(0).getRoleGroupId();  
								List<RoleGroup> roleGroupList = roleGroupService.viewListByCriteria(companyId, "", "", "", roleGroupId, emptyList, emptyList, "", "", 0, 0);

								System.out.println("roleGroupList.size(): " + roleGroupList.size()); 
								
								if (roleGroupList.size() > 0) { 
 
									String roleCompanyId  = roleGroupList.get(0).getRoleCompanyId();
									String roleDivisionId = roleGroupList.get(0).getRoleDivisionId();
									String roleModuleId   = roleGroupList.get(0).getRoleModuleId(); 
									
									List<String> rolePrivilegesList = new ArrayList<String>(); 
									 
									if (companyLevelResources.contains(resourceType)) {
	 									List<RoleCompany> roleCompanyList = roleCompanyService.viewListByCriteria(companyId, roleCompanyId, emptyList, emptyList, "", "", 0, 0);
	 									if (roleCompanyList.size() > 0) 
											rolePrivilegesList = roleCompanyList.get(0).getRoleCompanyPrivileges(); 

										System.out.println("resourceType: " + resourceType + " roleCompanyList.size(): " + roleCompanyList.size()); 
					
									} else if (divisionLevelResources.contains(resourceType)) {

										List<RoleDivision> roleDivisionList = roleDivisionService.viewListByCriteria(companyId, "", roleDivisionId, emptyList, emptyList, "", "", 0, 0);
	 									if (roleDivisionList.size() > 0) 
											rolePrivilegesList = roleDivisionList.get(0).getRoleDivisionPrivileges(); 

										System.out.println("resourceType: " + resourceType + " roleDivisionList.size(): " + roleDivisionList.size()); 
										
									} else {
 
										List<RoleModule> roleModuleList = roleModuleService.viewListByCriteria(companyId, "", "", roleModuleId, emptyList, emptyList, "", "", 0, 0); 
	 									if (roleModuleList.size() > 0) 
											rolePrivilegesList = roleModuleList.get(0).getRoleModulePrivileges(); 
	 									
	 									System.out.println("roleModuleList: " + roleModuleList);

										System.out.println("resourceType: " + resourceType + " roleModuleId: " + roleModuleId + " roleModuleList.size(): " + roleModuleList.size()); 
					
									}  

									System.out.println("rolePrivilegesList.size(): " + rolePrivilegesList.toString()); 
									
	 								if (rolePrivilegesList.contains(rolePrivilegeType)) {
	 									this.checkAndUpdateUserLastActivity(dbDataAndTime, sessionTypeDB, userIdDB);
	 									return new ProfileSessionRoleAccess(GC_STATUS_SUCCESS, "", userIdDB, userSession);
	 								}  
	 							}
	 						} 	
						}  
					}    
				}  
  
				String errMess = "You don't have permission. Please contact Admin.";
				return new ProfileSessionRoleAccess(GC_STATUS_ACCESS_FAILED, errMess, ""); 	 
			} else {
				String errMess = "Session token is invalid. Please login again for new session token"; 
				return new ProfileSessionRoleAccess(GC_STATUS_SESSION_FAILED, errMess, "");
			}   
			
		} else {		 
			return new ProfileSessionRoleAccess(GC_STATUS_SESSION_FAILED, e002SessionNotMatched, ""); 
		}
	} 
	
    public void checkAndUpdateUserLastActivity(Timestamp dbDataAndTime, String userSessionType, String userId) {
    	
    	try { 
		
    		Timestamp activityWebAt = null, activityIosAt = null, activityAndroidAt = null;
			if (userSessionType.equalsIgnoreCase(GC_REFERRED_BY_WEB)) 
				activityWebAt = dbDataAndTime;
		 	else if (userSessionType.equalsIgnoreCase(GC_REFERRED_BY_IOS)) 
		 		activityIosAt = dbDataAndTime;
		 	else if (userSessionType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID))
		 		activityAndroidAt = dbDataAndTime; 
			
			userLastActivityService.updateUserLastActivityByActivity(userId, dbDataAndTime, activityWebAt, activityIosAt, activityAndroidAt);   

	    } catch (Exception errMess) {  
    	}
    }
  
}