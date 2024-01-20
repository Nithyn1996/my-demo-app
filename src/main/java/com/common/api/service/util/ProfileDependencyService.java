package com.common.api.service.util;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.CompanyService;
import com.common.api.datasink.service.CountryService;
import com.common.api.datasink.service.DeviceDataService;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.DeviceSummaryService;
import com.common.api.datasink.service.DivisionPreferenceService;
import com.common.api.datasink.service.DivisionService;
import com.common.api.datasink.service.ModuleService;
import com.common.api.datasink.service.OrderService;
import com.common.api.datasink.service.PortionService;
import com.common.api.datasink.service.PropertyService;
import com.common.api.datasink.service.SectionPreferenceService;
import com.common.api.datasink.service.SectionService;
import com.common.api.datasink.service.SubscriptionService;
import com.common.api.datasink.service.UserAuthenticationService;
import com.common.api.datasink.service.UserFeedbackService;
import com.common.api.datasink.service.UserPreferenceService;
import com.common.api.datasink.service.UserService;
import com.common.api.datasink.service.UserVerificationService;
import com.common.api.model.Company;
import com.common.api.model.Country;
import com.common.api.model.Device;
import com.common.api.model.DeviceData;
import com.common.api.model.DeviceSummary;
import com.common.api.model.Division;
import com.common.api.model.DivisionPreference;
import com.common.api.model.Module;
import com.common.api.model.Order;
import com.common.api.model.Portion;
import com.common.api.model.Property;
import com.common.api.model.Section;
import com.common.api.model.SectionPreference;
import com.common.api.model.Subscription;
import com.common.api.model.User;
import com.common.api.model.UserAuthentication;
import com.common.api.model.UserFeedback;
import com.common.api.model.UserPreference;
import com.common.api.model.UserSession;
import com.common.api.model.UserVerification;
import com.common.api.request.UserCollection;
import com.common.api.response.ProfileDependencyAccess;   

@Service
public class ProfileDependencyService extends APIFixedConstant { 
  
	@Autowired 
	CountryService countryService; 
	@Autowired 
	CompanyService companyService; 
	@Autowired 
	DivisionService divisionService;  
	@Autowired 
	ModuleService moduleService; 
	@Autowired 
	PropertyService propertyService;   
	@Autowired 
	SectionService sectionService;    
	@Autowired 
	SectionPreferenceService sectionPreferenceService;   
	@Autowired
	DivisionPreferenceService divisionPreferenceService;
	@Autowired 
	PortionService portionService;  
	@Autowired 
	UserService userService;   
	@Autowired 
	DeviceService deviceService;   
	@Autowired 
	DeviceDataService deviceDataService; 
	@Autowired
	DeviceSummaryService deviceSummaryService;
	
	@Autowired 
	UserPreferenceService userPreferenceService;  
	@Autowired 
	UserFeedbackService userFeedbackService;    
	@Autowired 
	UserVerificationService userVerificationService;   
	@Autowired 
	UserAuthenticationService userAuthenticationService; 

	@Autowired
	OrderService orderService;
	@Autowired
	SubscriptionService subscriptionService;
	
	public ProfileDependencyAccess userProfileDependencyStatus(String requestMethod, User userDetail) { 
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  
	 	
		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= userDetail.getCompanyId();
		String divisionId 	= userDetail.getDivisionId();
		String moduleId 	= userDetail.getModuleId();
		String countryId 	= userDetail.getCountryId();  
		String userId    	= userDetail.getId();
		
		List<Country> countryList = countryService.viewListByCriteria(companyId, "", countryId, "", "", "", "", emptyList, emptyList, "", "", 0, 0); 
		if (countryList.size() <= 0) 
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_COUNTRY_NOT_EXISTS);   
		
		List<Module> moduleList = moduleService.viewListByCriteria(companyId, "", divisionId, moduleId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
		if (moduleList.size() <= 0)     
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_MODULE_NOT_MATCHED);  
		  
		if (requestMethod.equals(GC_METHOD_PUT)) {   
			
			List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
			if (userList.size() <= 0)  
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_COMPANY_NOT_MATCHED);  
		 
		}
 		return result; 
 		
	}
 
	public ProfileDependencyAccess userPreferenceProfileDependencyStatus(String requestMethod, UserPreference userPreferenceDetail) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>();  
		String companyId 	= userPreferenceDetail.getCompanyId();
		String divisionId 	= userPreferenceDetail.getDivisionId();
		String moduleId    	= userPreferenceDetail.getModuleId();  
		String userId    	= userPreferenceDetail.getUserId(); 
		String userPrefId  	= userPreferenceDetail.getId();   
		 
		List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
		if (userList.size() <= 0)  
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_COMPANY_NOT_MATCHED); 
		
		if (requestMethod.equals(GC_METHOD_PUT)) {
			
			List<UserPreference> userPreferenceList = userPreferenceService.viewListByCriteria(companyId, divisionId, moduleId, userId, userPrefId, "", "", emptyList, emptyList, emptyList, emptyList, "", "", 0, 0);  
			if (userPreferenceList.size() <= 0)   
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_PREFER_NOT_MATCHED); 
		 
		}
		return result;
		
	}
 
	public ProfileDependencyAccess userFeedbackProfileDependencyStatus(String requestMethod, UserFeedback userFeedbackDetail) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= userFeedbackDetail.getCompanyId();
		String divisionId 	= userFeedbackDetail.getDivisionId();
		String moduleId    	= userFeedbackDetail.getModuleId();  
		String userId    	= userFeedbackDetail.getUserId(); 
		String userFBackId 	= userFeedbackDetail.getId();     
	
		List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
		if (userList.size() <= 0)
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_COMPANY_NOT_MATCHED); 
		
		if (requestMethod.equals(GC_METHOD_PUT)) {  
			 
			List<UserFeedback> userFeedbackList = userFeedbackService.viewListByCriteria(companyId, divisionId, moduleId, userId, userFBackId, emptyList, emptyList, "", "", 0, 0);   
			if (userFeedbackList.size() <= 0)   
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_FEEDBACK_NOT_MATCHED);  
		 
		}
		return result;
		
	}
 
	public ProfileDependencyAccess userSessionProfileDependencyStatus(String requestMethod, UserSession userSessionDetail) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>();  
		String companyId 	= userSessionDetail.getCompanyId();
		String username    	= userSessionDetail.getUsername();    
		 
		List<User> userList = userService.viewListByCriteria(companyId, "", "", "", "", username, "", "", emptyList, emptyList, "", "", 0, 0);  
		if (userList.size() <= 0)  
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USERNAME_NOT_AVAILABLE); 
		  
		return result; 
		
	} 
 
	public ProfileDependencyAccess userVerificationProfileDependencyStatus(String requestMethod, UserVerification userVerificationDetail) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  
		
		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= userVerificationDetail.getCompanyId(); 
		String username    	= userVerificationDetail.getUsername();  
		String userVerifyId = userVerificationDetail.getId();   
		
		List<User> userList = userService.viewListByCriteria(companyId, "", "", "", "", username, "", "", emptyList, emptyList, "", "", 0, 0);  
		if (userList.size() <= 0)  
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USERNAME_NOT_AVAILABLE); 
	  
		if (requestMethod.equals(GC_METHOD_PUT)) {  
			 
			List<UserVerification> userVerificationList = userVerificationService.viewListByCriteria(companyId, "", userVerifyId, username, "", emptyList, emptyList, "", "", 0, 0);    
			if (userVerificationList.size() <= 0)   
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_VERIFI_NOT_MATCHED);  
		 
		}
		return result;
		
	}
 
	public ProfileDependencyAccess propertyProfileDependencyStatus(String requestMethod, Property propertyDetail) {

		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= propertyDetail.getCompanyId();
		String divisionId 	= propertyDetail.getDivisionId();
		String moduleId    	= propertyDetail.getModuleId();   
		String userId    	= propertyDetail.getUserId();   
		String propertyId  	= propertyDetail.getId();  
		  
		List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
		if (userList.size() <= 0)  
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_COMPANY_NOT_MATCHED); 
		
		/** For Temporarily Starts */ 
		if (userList.size() > 0) {			
			User userTemp = userList.get(0); 
			String groupIdTemp = userTemp.getGroupId(); 
			propertyDetail.setGroupId(groupIdTemp); 
		}		
		/** For Temporarily Ends */ 
		
		if (requestMethod.equals(GC_METHOD_PUT)) {
			
			List<Property> propertyList = propertyService.viewListByCriteria(companyId, "", "", "", userId, propertyId, "", emptyList, emptyList, "", "", 0, 0);   
			if (propertyList.size() <= 0)   
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_PROPERTY_NOT_MATCHED);  
		 
		}
		return result; 
	}
 
	public ProfileDependencyAccess sectionProfileDependencyStatus(String requestMethod, Section sectionDetail) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= sectionDetail.getCompanyId();
		String divisionId 	= sectionDetail.getDivisionId(); 
		String moduleId    	= sectionDetail.getModuleId();  
		String userId    	= sectionDetail.getUserId();   
		String propertyId  	= sectionDetail.getPropertyId(); 
		String sectionId  	= sectionDetail.getId();  
		 
		List<Property> propertyList = propertyService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, "", emptyList, emptyList, "", "", 0, 0);   
		if (propertyList.size() <= 0)       
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_PROPERTY_NOT_MATCHED);  
		
		/** For Temporarily Starts */ 
		if (propertyList.size() > 0) {			
			Property propertyTemp = propertyList.get(0);
			String groupIdTemp = propertyTemp.getGroupId();
			sectionDetail.setGroupId(groupIdTemp); 
		}		
		/** For Temporarily Ends */ 
		
		if (requestMethod.equals(GC_METHOD_PUT)) { 
			List<Section> sectionList = sectionService.viewListByCriteria(companyId, "", "", "", userId, propertyId, sectionId, "", emptyList, emptyList, "", "", 0, 0);   
			if (sectionList.size() <= 0)   
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_SECTION_NOT_MATCHED);  
		}
		return result; 
	}
	
	public ProfileDependencyAccess sectionPreferenceProfileDependencyStatus(String requestMethod, SectionPreference sectionPreferenceDetail) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= sectionPreferenceDetail.getCompanyId(); 
		String divisionId 	= sectionPreferenceDetail.getDivisionId(); 
		String moduleId    	= sectionPreferenceDetail.getModuleId();  
		String userId    	= sectionPreferenceDetail.getUserId();   
		String propertyId  	= sectionPreferenceDetail.getPropertyId(); 
		String sectionId  	= sectionPreferenceDetail.getSectionId();  
	  
		List<Section> sectionList = sectionService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, "", emptyList, emptyList, "", "", 0, 0);   
		if (sectionList.size() <= 0)   
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_SECTION_NOT_MATCHED);  
		
		/** For Temporarily Starts */ 
		if (sectionList.size() > 0) {			
			Section sectionTemp = sectionList.get(0); 
			String groupIdTemp = sectionTemp.getGroupId(); 
			sectionPreferenceDetail.setGroupId(groupIdTemp); 
		}		
		/** For Temporarily Ends */ 
		
		if (requestMethod.equals(GC_METHOD_PUT)) { 
			List<SectionPreference> sectionPreferenceList = sectionPreferenceService.viewListByCriteria(companyId, "", "", "", userId, propertyId, sectionId, "", "", "", emptyList, emptyList, "", "", 0, 0);   
			if (sectionPreferenceList.size() <= 0)    
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_SECTION_PREFER__NOT_MATCHED);  
		}
		return result;
	}
 
	public ProfileDependencyAccess portionProfileDependencyStatus(String requestMethod, Portion portionDetail) {

		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= portionDetail.getCompanyId();  
		String divisionId 	= portionDetail.getDivisionId(); 
		String moduleId    	= portionDetail.getModuleId();    
		String userId    	= portionDetail.getUserId();  
		String propertyId  	= portionDetail.getPropertyId();
		String sectionId  	= portionDetail.getSectionId();    
		String portionId  	= portionDetail.getId();   
		 
		List<Section> sectionList = sectionService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, "", emptyList, emptyList, "", "", 0, 0);   
		if (sectionList.size() <= 0)   
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_SECTION_NOT_MATCHED);  
		
		/** For Temporarily Starts */ 
		if (sectionList.size() > 0) {			
			Section sectionTemp = sectionList.get(0); 
			String groupIdTemp = sectionTemp.getGroupId(); 
			portionDetail.setGroupId(groupIdTemp); 
		}		
		/** For Temporarily Ends */ 
		
		if (requestMethod.equals(GC_METHOD_PUT)) { 
			List<Portion> portionList = portionService.viewListByCriteria(companyId, "", "", "", userId, propertyId, sectionId, portionId, "", emptyList, emptyList, "", "", 0, 0);   
			if (portionList.size() <= 0)   
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_PROTION_NOT_MATCHED);   
		}
		return result;
	}
 
	public ProfileDependencyAccess deviceProfileDependencyStatus(String requestMethod, Device deviceDetail) {   
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, ""); 

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= deviceDetail.getCompanyId();  
		String divisionId 	= deviceDetail.getDivisionId(); 
		String moduleId    	= deviceDetail.getModuleId();    
		String userId    	= deviceDetail.getUserId();  
		String propertyId  	= deviceDetail.getPropertyId();
		String sectionId  	= deviceDetail.getSectionId(); 
		String portionId  	= deviceDetail.getPortionId();      
		String deviceId  	= deviceDetail.getId();
		 
		List<Portion> portionList = portionService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, portionId, "", emptyList, emptyList, "", "", 0, 0);   
		if (portionList.size() <= 0)     
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_PROTION_NOT_MATCHED);
		
		/** For Temporarily Starts */
		if (portionList.size() > 0) { 		 	
			Portion portionTemp = portionList.get(0); 
			String groupIdTemp = portionTemp.getGroupId();  
			deviceDetail.setGroupId(groupIdTemp);  
		}		
		/** For Temporarily Ends */
		
		if (requestMethod.equals(GC_METHOD_PUT)) {  
			List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", "", "", userId, propertyId, sectionId, portionId, deviceId, "", "", emptyList, emptyList, "", "", 0, 0);   
			if (deviceList.size() <= 0)    
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_DEVICE_NOT_MATCHED);    
		}
		return result; 
	}
 
	public ProfileDependencyAccess deviceDataProfileDependencyStatus(String requestMethod, DeviceData deviceDataDetail) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= deviceDataDetail.getCompanyId();  
		String divisionId 	= deviceDataDetail.getDivisionId(); 
		String moduleId    	= deviceDataDetail.getModuleId();    
		String userId    	= deviceDataDetail.getUserId();  
		String propertyId  	= deviceDataDetail.getPropertyId();
		String sectionId  	= deviceDataDetail.getSectionId(); 
		String portionId  	= deviceDataDetail.getPortionId();      
		String deviceId  	= deviceDataDetail.getDeviceId();    
		String deviceDataId = deviceDataDetail.getId();
		 
		List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId, "", "", emptyList, emptyList, "", "", 0, 0);    
		if (deviceList.size() <= 0)     
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_DEVICE_NOT_MATCHED);  
		 	
		/** For Temporarily Starts */  
		if (deviceList.size() > 0) {		
			Device deviceTemp = deviceList.get(0);
			String groupIdTemp = deviceTemp.getGroupId();
			deviceDataDetail.setGroupId(groupIdTemp);
		}		
		/** For Temporarily Ends */ 
		
		if (requestMethod.equals(GC_METHOD_PUT)) {   
			List<DeviceData> deviceDataList = deviceDataService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId, deviceDataId, emptyList, emptyList, emptyList, emptyList, "", "", 0, 0);   
			if (deviceDataList.size() <= 0)      
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_DEVICE_DATA_NOT_MATCHED);    
		} 
		return result;
	}
  
	public ProfileDependencyAccess fileProfileDependencyStatus(String requestMethod, String companyId, String userId) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  
	 	
		List<String> emptyList = new ArrayList<String>();  
		
		List<Company> companyList = companyService.viewListByCriteria(companyId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
		if (companyList.size() <= 0)     
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_COMPANY_NOT_EXISTS);   
 		 
		if (requestMethod.equals(GC_METHOD_PUT)) {  
			
			List<User> userList = userService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
			if (userList.size() <= 0)  
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_COMPANY_NOT_MATCHED);    
		 
		}
 		return result;
	}
	
	public ProfileDependencyAccess adminUserProfileDependencyStatus(String requestMethod, UserCollection userCollectionDetail) { 
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  
	 	
		List<String> emptyList 	= new ArrayList<String>(); 
		String companyId 		= userCollectionDetail.getCompanyId();  
		String userId    		= userCollectionDetail.getUserId();
		
		List<Company> companyList = companyService.viewListByCriteria(companyId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
		if (companyList.size() <= 0)     
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_COMPANY_NOT_EXISTS);
		   
		if (requestMethod.equals(GC_METHOD_PUT)) {    
			
			List<User> userList = userService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
			if (userList.size() <= 0)  
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_COMPANY_NOT_MATCHED);  
		 
		}
 		return result; 
 		
	}
	
	public ProfileDependencyAccess userAuthenticationProfileDependencyStatus(String requestMethod, UserAuthentication userAuthenticationReq) {

		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	= userAuthenticationReq.getCompanyId(); 
		String divisionId 	= userAuthenticationReq.getDivisionId(); 
		String moduleId    	= userAuthenticationReq.getModuleId();    
		String userId    	= userAuthenticationReq.getUserId();     
		String usrAuthenId 	= userAuthenticationReq.getId();   
		 
		List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, "", "", "", emptyList, emptyList, "", "", 0, 0);  
		if (userList.size() <= 0)
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_USER_COMPANY_NOT_MATCHED); 
		 
		if (requestMethod.equals(GC_METHOD_PUT)) { 
			List<UserAuthentication> usrAuthenList = userAuthenticationService.viewListByCriteria(companyId, "", "", userId, usrAuthenId, "", "", emptyList, emptyList, "", "", 0, 0);   
			if (usrAuthenList.size() <= 0)     
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_PROTION_NOT_MATCHED);   
		}
		return result;
	}
	
	public ProfileDependencyAccess divisionPreferenceProfileDependencyStatus(String requestMethod, DivisionPreference divisionPreferenceDetail) {
		
	    ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  
	    
	    List<String> emptyList = new ArrayList<String>(); 
	    String companyId  = divisionPreferenceDetail.getCompanyId();
	    String divisionId = divisionPreferenceDetail.getDivisionId(); 
	    String divPrefId  = divisionPreferenceDetail.getId();
	    
	    List<Division> divisionList = divisionService.viewListByCriteria(companyId, "", divisionId, "", "", emptyList, emptyList, emptyList, "", "", DV_OFFSET, DV_LIMIT); 
        
	    if (divisionList.size() <= 0)
	        return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_DIVISION_PREFER_NOT_MATCHED);
	    
	    /** For Temporarily Starts */ 
	    if  (divisionList.size() > 0) {
	    	Division divisionTemp = divisionList.get(0);
	        String groupIdTemp = divisionTemp.getGroupId(); 
	        divisionPreferenceDetail.setGroupId(groupIdTemp);  
	    }
	    /** For Temporarily Ends */
	    
	    if (requestMethod.equals(GC_METHOD_PUT)) {
	        List<DivisionPreference> divisionPreferenceList = divisionPreferenceService.viewListByCriteria(companyId, "", divisionId, divPrefId, "", "", emptyList, emptyList, "", "", 0, 0);
	        if(divisionPreferenceList.size() <= 0)
	            return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_DIVISION_PREFER_NOT_MATCHED);
	    }
	    return result;
	}
	
	public ProfileDependencyAccess subscriptionProfileDependencyStatus(String requestMethod, Subscription subscriptionDetail) {
		
	    ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");  
	    
	    List<String> emptyList = new ArrayList<String>(); 
	    
	    String companyId  		= subscriptionDetail.getCompanyId();
	    String divisionId 		= subscriptionDetail.getDivisionId();
	    String subId 			= subscriptionDetail.getId();

	    List<Division> divisionList = divisionService.viewListByCriteria(companyId, "", divisionId, "", "", emptyList, emptyList, emptyList, "", "", DV_OFFSET, DV_LIMIT); 
        
	    if (divisionList.size() <= 0)
	        return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_DIVISION_PREFER_NOT_MATCHED);

	    /** For Temporarily Starts */ 
	    if  (divisionList.size() > 0) { 
	    	Division divisionTemp = divisionList.get(0);
	        String groupIdTemp = divisionTemp.getGroupId(); 
	        subscriptionDetail.setGroupId(groupIdTemp);  
	    }
	    /** For Temporarily Ends */
	    
	    if (requestMethod.equals(GC_METHOD_PUT)) {
	        List<Subscription> subscriptionList = subscriptionService.viewListByCriteria(companyId, "", divisionId, subId, "", emptyList, emptyList, "", "", 0, 0);
	        if (subscriptionList.size() <= 0)
	            return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_SUBSCRIPTION_NOT_MATCHED);
	    }
	    return result;
	}
		
	public ProfileDependencyAccess orderProfileDependencyStatus(String requestMethod, Order orderDetail) {
		
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");
		
		List<String> emptyList = new ArrayList<String>(); 
		
	    String companyId  	= orderDetail.getCompanyId();
	    String divisionId 	= orderDetail.getDivisionId();
	    String orderId 		= orderDetail.getId();
	    String subscriptionId = orderDetail.getSubscriptionId();	
	
	    List<Subscription> subscriptionList = subscriptionService.viewListByCriteria(companyId, "", divisionId, subscriptionId, "", emptyList, emptyList, "", "", 0, 0);
	    if (subscriptionList.size() <= 0)
	        return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_SUBSCRIPTION_NOT_MATCHED);
	    
	    /** For Temporarily Starts */ 
	    if (subscriptionList.size() > 0) {
	    	Subscription subscriptionTemp = subscriptionList.get(0);
	    	String groupIdTemp = subscriptionTemp.getGroupId();
	    	orderDetail.setGroupId(groupIdTemp);  
	    }
	    /** For Temporarily Ends */
	    
	    if (requestMethod.equals(GC_METHOD_PUT)) {
		    List<Order> orderList = orderService.viewListByCriteria(companyId, "", divisionId, subscriptionId, orderId, "", emptyList, emptyList, "", "", 0, 0);
		    if(orderList.size() <= 0) {
	            return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_ORDER_NOT_MATCHED);
		    }  
	    }
	    /** For Temporarily Ends */
	    
	    return result;
	}
	
	public ProfileDependencyAccess deviceSummaryProfileDependencyStatus(String requestMethod, DeviceSummary deviceSummaryDetail) {
		ProfileDependencyAccess result = new ProfileDependencyAccess(GC_STATUS_SUCCESS, "");

		List<String> emptyList = new ArrayList<>();
		String companyId 	= deviceSummaryDetail.getCompanyId();
		String divisionId 	= deviceSummaryDetail.getDivisionId();
		String moduleId    	= deviceSummaryDetail.getModuleId();
		String userId    	= deviceSummaryDetail.getUserId();
		String propertyId  	= deviceSummaryDetail.getPropertyId();
		String sectionId  	= deviceSummaryDetail.getSectionId();
		String portionId  	= deviceSummaryDetail.getPortionId();
		String deviceId  	= deviceSummaryDetail.getDeviceId();
		String deviceSummaryId = deviceSummaryDetail.getId();

		List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId, "", "", emptyList, emptyList, "", "", 0, 0);
		if (deviceList.size() <= 0)
			return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_DEVICE_NOT_MATCHED);

		/** For Temporarily Starts */
		if (deviceList.size() > 0) {
			Device deviceTemp = deviceList.get(0);
			String groupIdTemp = deviceTemp.getGroupId();
			deviceSummaryDetail.setGroupId(groupIdTemp);
		}
		/** For Temporarily Ends */

		if (requestMethod.equals(GC_METHOD_PUT)) {
			List<DeviceSummary> deviceSummaryList = deviceSummaryService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId, deviceSummaryId, emptyList, emptyList, "", "", 0, 0);
			if (deviceSummaryList.size() <= 0)
				return new ProfileDependencyAccess(GC_STATUS_ERROR, RTVE_DEVICE_SUMMARY_NOT_MATCHED);
		}
		return result;
	}
		
	 
}