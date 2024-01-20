package com.common.api.service.util;
 
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.DeviceSummaryService;
import com.common.api.datasink.service.PortionService;
import com.common.api.datasink.service.PropertyService;
import com.common.api.datasink.service.RoleGroupService;
import com.common.api.datasink.service.RoleUserService;
import com.common.api.datasink.service.SectionService;
import com.common.api.datasink.service.UserLastActivityService;
import com.common.api.datasink.service.UserPreferenceService;
import com.common.api.datasink.service.UserSecretService;
import com.common.api.model.Device;
import com.common.api.model.DeviceSummary;
import com.common.api.model.Portion;
import com.common.api.model.Property;
import com.common.api.model.RoleGroup;
import com.common.api.model.RoleModule;
import com.common.api.model.RoleUser;
import com.common.api.model.Section;
import com.common.api.model.User;
import com.common.api.model.UserLastActivity;
import com.common.api.model.UserPreference;
import com.common.api.model.UserSecret;
import com.common.api.model.type.ModelType;
import com.common.api.util.APIAuthorization;
import com.common.api.util.APIDateTime;
import com.common.api.util.EncryptDecrypt;
import com.common.api.util.ResultSetConversion; 

@Service
public class ProfileAutoSystematicService extends APIFixedConstant {

	@Autowired      
	ResultSetConversion resultSetConversion;  
	@Autowired  
	NamedParameterJdbcTemplate namedParameterJdbcTemplate; 

	@Autowired 
	APIAuthorization apiAuthorization; 
    @Autowired 
    RoleUserService roleUserService;   
    @Autowired 
    PropertyService propertyService;     
    @Autowired 
    SectionService sectionService;        
    @Autowired  
    PortionService portionService;         
    @Autowired  
    DeviceService deviceService;   
    @Autowired
    DeviceSummaryService deviceSummaryService;      
    @Autowired
    UserPreferenceService userPreferenceService;    
    @Autowired
    RoleGroupService roleGroupService;  
    @Autowired
    UserLastActivityService userLastActivityService; 
    @Autowired
    UserSecretService userSecretService; 
    
	public RoleUser createRoleUserByUser(User user) {   
		   
    	String companyId    = user.getCompanyId();  
    	String divisionId   = user.getDivisionId();  
    	String moduleId     = user.getModuleId();     
		String userId 		= user.getId();
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
				
		try { 
			
			List<RoleGroup> roleGroupList = roleGroupService.viewListByCriteria(companyId, divisionId, moduleId, "", "", 0, 0); 
			  
			if (roleGroupList.size() > 0) { 
				
				RoleGroup roleGroupDetail = roleGroupList.get(0); 
				String roleGroupId = roleGroupDetail.getId();  
			
				RoleUser roleUser = new RoleUser();
				roleUser.setCompanyId(companyId);
				roleUser.setRoleGroupId(roleGroupId);
				roleUser.setUserId(userId);
				roleUser.setPriority("PRIMARY");
				roleUser.setStatus(GC_STATUS_REGISTERED);
				roleUser.setType(APIResourceName.ROLE_USER.toString());
				roleUser.setCreatedAt(dbOperationStartTime);
				roleUser.setModifiedAt(dbOperationStartTime); 
				roleUser.setCreatedBy("");
				roleUser.setModifiedBy(""); 
				
				return roleUserService.createRoleUser(roleUser); 
			}
			
		} catch (Exception errMess) { 
		} 
		return new RoleUser();  
	}
 
	public Property createPropertyByUser(User user, RoleModule roleModule) { 
		
    	String companyId  = user.getCompanyId();  
    	String groupId	  = user.getGroupId();  
    	String divisionId = user.getDivisionId();   
    	String moduleId   = user.getModuleId();  
		String userId 	  = user.getId();
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
				
		try {
			
			Property propertyStatus = apiAuthorization.getPropertyStatus(user); 
			 
			if (propertyStatus != null && propertyStatus.getStatus().equalsIgnoreCase(APIFixedConstant.GC_ACTION_YES.toString())) {  

				Property property = new Property();
				property.setCompanyId(companyId); 
				property.setGroupId(groupId); 
				property.setDivisionId(divisionId);
				property.setModuleId(moduleId); 
				property.setUserId(userId);  
				property.setName(propertyStatus.getName()); 
				property.setStatus(GC_STATUS_REGISTERED);
				property.setType(propertyStatus.getType()); 
				property.setCreatedAt(dbOperationStartTime);
				property.setModifiedAt(dbOperationStartTime); 
				property.setCreatedBy("");
				property.setModifiedBy(""); 
 
				return propertyService.createProperty(property);  
			}
			
		} catch (Exception errMess) {   
		}   
		return new Property();   	
	}
 
	public Section createSectionByUser(User user, RoleModule roleModule, Property property) { 
		 
    	String companyId  = user.getCompanyId();	  	
    	String groupId	  = user.getGroupId();  
    	String divisionId = user.getDivisionId();
    	String moduleId   = user.getModuleId();    
		String userId 	  = user.getId();
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
				
		try {  
			
			Section sectionStatus = apiAuthorization.getSectionStatus(user); 

			String propertyId = "";  
			if (property != null) { 
				propertyId = property.getId(); 
			}
 
			if (sectionStatus != null && sectionStatus.getStatus().equalsIgnoreCase(APIFixedConstant.GC_ACTION_YES.toString())) {  
			
				Section section = new Section();
				section.setCompanyId(companyId); 
				section.setGroupId(groupId); 
				section.setDivisionId(divisionId);
				section.setModuleId(moduleId); 
				section.setUserId(userId); 
				section.setPropertyId(propertyId); 
				section.setName(sectionStatus.getName());
				section.setStatus(GC_STATUS_REGISTERED);
				section.setType(sectionStatus.getType());
				section.setCreatedAt(dbOperationStartTime);
				section.setModifiedAt(dbOperationStartTime); 
				section.setCreatedBy("");
				section.setModifiedBy(""); 
				 
				return sectionService.createSection(section);  
			}
			
		} catch (Exception errMess) {  
		} 
		return new Section();  
	}
 
	public Portion createPortionByUser(User user, RoleModule roleModule, Property property, Section section) {
		
    	String companyId  = user.getCompanyId();  
    	String groupId	  = user.getGroupId();  
    	String divisionId = user.getDivisionId();   
    	String moduleId   = user.getModuleId();     
		String userId 	  = user.getId();
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
				 
		try {  
			
			Portion portionStatus = apiAuthorization.getPortionStatus(user);  	

			String propertyId = "", sectionId = "";  
			if (property != null) { 
				propertyId = property.getId(); 
			}
			if (section != null) { 
				sectionId = section.getId();    
			}
			
			if (portionStatus != null && portionStatus.getStatus().equalsIgnoreCase(APIFixedConstant.GC_ACTION_YES.toString())) {  
				
				Portion portion = new Portion(); 
				portion.setCompanyId(companyId);
				portion.setGroupId(groupId); 
				portion.setDivisionId(divisionId);
				portion.setModuleId(moduleId); 
				portion.setUserId(userId);
				portion.setPropertyId(propertyId); 
				portion.setSectionId(sectionId);   
				portion.setName(portionStatus.getName());
				portion.setStatus(GC_STATUS_REGISTERED);
				portion.setType(portionStatus.getType());
				portion.setCreatedAt(dbOperationStartTime);
				portion.setModifiedAt(dbOperationStartTime); 
				portion.setCreatedBy("");
				portion.setModifiedBy("");  
				
				return portionService.createPortion(portion);  
			}
			
		} catch (Exception errMess) {
		}
		return new Portion();
	}	  
	
	public UserPreference createUserPrefAppBootSettingByUser(User user) { 
		
    	String companyId  = user.getCompanyId();  
    	String divisionId = user.getDivisionId();  
    	String moduleId   = user.getModuleId();   
		String userId 	  = user.getId(); 
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();  
				
		try {   
			
			UserPreference userPreferenceStatus = apiAuthorization.getUserPrefAppBootSettingStatus(user);  	
 
			if (userPreferenceStatus != null && userPreferenceStatus.getStatus().equalsIgnoreCase(APIFixedConstant.GC_ACTION_YES.toString())) {  
				
				UserPreference userPreference = new UserPreference(); 
				userPreference.setCompanyId(companyId);
				userPreference.setDivisionId(divisionId);
				userPreference.setModuleId(moduleId);  
				userPreference.setUserId(userId);
				userPreference.setUserPrefAppBootSetting(userPreferenceStatus.getUserPrefAppBootSetting()); 
				userPreference.setName(userPreferenceStatus.getName());
				userPreference.setStatus(GC_STATUS_REGISTERED); 
				userPreference.setType(userPreferenceStatus.getType()); 
				userPreference.setCreatedAt(dbOperationStartTime);
				userPreference.setModifiedAt(dbOperationStartTime); 
				userPreference.setCreatedBy("");
				userPreference.setModifiedBy("");  
				
				return userPreferenceService.createUserPreference(userPreference);    
			}
			
		} catch (Exception errMess) {
		}
		return new UserPreference();
	}

	public UserLastActivity createUserLastActivityByUser(User user) {
		
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();  
		
		String defaultDateTimeString = "2023-01-01 00:00:00"; 
		Timestamp defaultTimeLastActivity = APIDateTime.convertStringDateTimeToTimestamp(defaultDateTimeString);

		UserLastActivity userLastActivity = new UserLastActivity();
		userLastActivity.setCompanyId(user.getCompanyId()); 
		userLastActivity.setGroupId(user.getGroupId());
		userLastActivity.setDivisionId(user.getDivisionId()); 
		userLastActivity.setModuleId(user.getModuleId()); 
		userLastActivity.setUserId(user.getId()); 
 
		userLastActivity.setPasswordChangedAt(user.getCreatedAt()); 
		
		userLastActivity.setSessionWebAt(defaultTimeLastActivity); 
		userLastActivity.setSessionIosAt(defaultTimeLastActivity);
		userLastActivity.setSessionAndroidAt(defaultTimeLastActivity);

		userLastActivity.setActivityWebAt(defaultTimeLastActivity);  
		userLastActivity.setActivityIosAt(defaultTimeLastActivity);
		userLastActivity.setActivityAndroidAt(defaultTimeLastActivity);
		
		userLastActivity.setNoActivityPushAndroidAt(defaultTimeLastActivity);
		userLastActivity.setNoActivityPushIosAt(defaultTimeLastActivity);
		userLastActivity.setNoActivityPushWebAt(defaultTimeLastActivity);
		
		userLastActivity.setAppUpdatePushIosAt(defaultTimeLastActivity);
		userLastActivity.setAppUpdatePushAndroidAt(defaultTimeLastActivity);

		userLastActivity.setMapUpdatePushIosAt(defaultTimeLastActivity);
		userLastActivity.setMapUpdatePushAndroidAt(defaultTimeLastActivity);
		
		userLastActivity.setType(ModelType.UserLastActivityType.USER_LAST_ACTIVITY.toString()); 
		userLastActivity.setCreatedAt(dbOperationStartTime); 
		userLastActivity.setModifiedAt(dbOperationStartTime);
		userLastActivity.setCreatedBy("");
		userLastActivity.setModifiedBy("");  
		
		return userLastActivityService.createUserLastActivity(userLastActivity); 
		
	}
	
	public UserSecret createUserSecretByUser(User user) {
		
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		 
		UserSecret userSecret = new UserSecret();
		userSecret.setCompanyId(user.getCompanyId()); 
		userSecret.setDivisionId(user.getDivisionId());  
		userSecret.setUserId(user.getId());  
		
		userSecret.setSecretKey(new EncryptDecrypt().getUserSecretKey());   

		userSecret.setStatus(GC_STATUS_REGISTERED);
		userSecret.setType(ModelType.UserSecretType.USER_SECRET.toString());
		userSecret.setCreatedAt(dbOperationStartTime);
		userSecret.setModifiedAt(dbOperationStartTime);
		userSecret.setCreatedBy("");
		userSecret.setModifiedBy("");  
		
		return userSecretService.createUserSecret(userSecret);   
		
	}
	
	public DeviceSummary createDeviceSummaryByDevice(Device deviceReq) {
		
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		  
		DeviceSummary deviceSummary = new DeviceSummary();
		deviceSummary.setCompanyId(deviceReq.getCompanyId());
		deviceSummary.setGroupId(deviceReq.getGroupId());
		deviceSummary.setDivisionId(deviceReq.getDivisionId()); 
		deviceSummary.setModuleId(deviceReq.getModuleId());
		deviceSummary.setUserId(deviceReq.getUserId());
		deviceSummary.setPropertyId(deviceReq.getPropertyId());
		deviceSummary.setSectionId(deviceReq.getSectionId());
		deviceSummary.setPortionId(deviceReq.getPortionId());
		deviceSummary.setDeviceId(deviceReq.getId());
		deviceSummary.setType(ModelType.DeviceSummary.SPEEDO_METER_DEVICE_SUMMARY.toString());
		deviceSummary.setStatus(GC_STATUS_REGISTERED);  
		deviceSummary.setCreatedAt(dbOperationStartTime);
		deviceSummary.setModifiedAt(dbOperationStartTime);
		deviceSummary.setCreatedBy(deviceReq.getUserId());
		deviceSummary.setModifiedBy(deviceReq.getUserId()); 
		return deviceSummaryService.createDeviceSummary(deviceSummary); 
		
	}
  
}