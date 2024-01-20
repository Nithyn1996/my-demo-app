package com.common.api.service.util;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.DeviceDataService;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.DeviceSummaryService;
import com.common.api.datasink.service.DivisionPreferenceService;
import com.common.api.datasink.service.MetadataService;
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
import com.common.api.datasink.service.UserSessionService;
import com.common.api.datasink.service.UserVerificationService;
import com.common.api.model.Device;
import com.common.api.model.DeviceData;
import com.common.api.model.DeviceSummary;
import com.common.api.model.DivisionPreference;
import com.common.api.model.Metadata;
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
import com.common.api.model.type.ModelType;
import com.common.api.request.UserCollection;
import com.common.api.request.UserCollectionData;
import com.common.api.response.ProfileCardinalityAccess;  

@Service
public class ProfileCardinalityService extends APIFixedConstant {
  
	@Autowired 
	MetadataService metadataService;  
	@Autowired 
	PropertyService propertyService;    
	@Autowired 
	SectionService sectionService;     
	@Autowired 
	SectionPreferenceService sectionPreferenceService;       
	@Autowired 
	PortionService portionService;    
	@Autowired 
	DeviceService deviceService;    
	@Autowired 
	DeviceDataService deviceDataService;    
	@Autowired 
	UserService userService;  
	@Autowired 
	UserFeedbackService userFeedbackService;  
	@Autowired 
	UserPreferenceService userPreferenceService;  
	@Autowired 
	UserSessionService userSessionService; 
	@Autowired 
	UserVerificationService userVerificationService;    
	@Autowired 
	UserAuthenticationService userAuthenticationService; 
	@Autowired
	DivisionPreferenceService divisionPreferenceService;
	@Autowired
	DeviceSummaryService deviceSummaryService;

	@Autowired
	OrderService orderService;
	@Autowired
	SubscriptionService subscriptionService;
	
	public ProfileCardinalityAccess userProfileCardinalityStatus(String requestMethod, User userDetail) { 

		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, "");   
		
		String type 	 = userDetail.getType(); 
		String companyId = userDetail.getCompanyId(); 
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();   
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {  
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();   
		}
		
		/** Available value */
		List<User> userList = userService.viewListByCriteria(companyId, "", "", "", "", "", "", "", emptyList, typeList, "", "", 0, 0);  
		cardinalityValueReq = userList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq)
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
		
 		return result;
	}
 
	public ProfileCardinalityAccess userFeedbackProfileCardinalityStatus(String requestMethod, UserFeedback userFeedbackDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 
		
		String type 	 = userFeedbackDetail.getType(); 
		String companyId = userFeedbackDetail.getCompanyId();
		String userId	 = userFeedbackDetail.getUserId();
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {  
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();  
		}
		
		/** Available value */
		List<UserFeedback> userFeedbackList = userFeedbackService.viewListByCriteria(companyId, "", "", userId, "", emptyList, typeList, "", "", 0, 0); 
		cardinalityValueReq = userFeedbackList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq)
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess userPreferenceProfileCardinalityStatus(String requestMethod, UserPreference userPreferenceDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId = userPreferenceDetail.getCompanyId();
		String userId	 = userPreferenceDetail.getUserId();
		String type 	 = userPreferenceDetail.getType();
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {  
			cardinalityValueDB = metadataList.get(0).getCardinality();
			displayValueMetadata = metadataList.get(0).getDisplayValue(); 
		}
		
		/** Available value */
		List<UserPreference> userPreferenceList = userPreferenceService.viewListByCriteria(companyId, "", "", userId, "", "", "", emptyList, emptyList,	emptyList, typeList, "", "", 0, 0);
		cardinalityValueReq = userPreferenceList.size();  
		
		if (cardinalityValueDB == cardinalityValueReq)
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess userSessionProfileCardinalityStatus(String requestMethod, UserSession userSessionDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId = userSessionDetail.getCompanyId();
		String type 	 = userSessionDetail.getType(); 
		String username	 = userSessionDetail.getUsername();
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();   
		}
		
		/** Available value */ 
		List<UserSession> userSessionList = userSessionService.viewListByCriteria("", "", "", username, emptyList, typeList, "", "", 0, 0);  
		cardinalityValueReq = userSessionList.size(); 
 
		if (cardinalityValueDB == cardinalityValueReq)
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess userVerificationProfileCardinalityStatus(String requestMethod, UserVerification userVerificationDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId = userVerificationDetail.getCompanyId();
		String type 	 = userVerificationDetail.getType(); 
		String username	 = userVerificationDetail.getUsername();
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();    
		}
		
		/** Available value */ 
		List<UserVerification> userVerificationList = userVerificationService.viewListByCriteria("", "", "", username, "", emptyList, typeList, "", "", 0, 0);   
		cardinalityValueReq = userVerificationList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq)
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess propertyProfileCardinalityStatus(String requestMethod, Property propertyDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId = propertyDetail.getCompanyId(); 
		String userId	 = propertyDetail.getUserId();
		String type 	 = propertyDetail.getType();  
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();    
		}
		
		/** Available value */ 
		List<Property> propertyList = propertyService.viewListByCriteria(companyId, "", "", "", userId, "", "", emptyList, typeList, "", "", 0, 0);    
		cardinalityValueReq = propertyList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq) 
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess sectionProfileCardinalityStatus(String requestMethod, Section sectionDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId 	= sectionDetail.getCompanyId(); 
		String userId	 	= sectionDetail.getUserId();  
		String type 	 	= sectionDetail.getType();   
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();     
		}
		 
		/** Available value */ 
		List<Section> sectionList = sectionService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", emptyList, typeList, "", "", 0, 0);    
		cardinalityValueReq = sectionList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq) 
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess sectionPreferenceProfileCardinalityStatus(String requestMethod, SectionPreference sectionPreferenceDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId = sectionPreferenceDetail.getCompanyId();
		String userId	 = sectionPreferenceDetail.getUserId();
		String type 	 = sectionPreferenceDetail.getType();
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {  
			cardinalityValueDB = metadataList.get(0).getCardinality();
			displayValueMetadata = metadataList.get(0).getDisplayValue(); 
		}
		
		/** Available value */
		List<SectionPreference> sectionPreferenceList = sectionPreferenceService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", "", "", emptyList, typeList, "", "", 0, 0);
		cardinalityValueReq = sectionPreferenceList.size();  
		
		if (cardinalityValueDB == cardinalityValueReq) 
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}

	public ProfileCardinalityAccess portionProfileCardinalityStatus(String requestMethod, Portion portionDetail) {

		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId 	= portionDetail.getCompanyId(); 
		String userId	 	= portionDetail.getUserId(); 
		String type 	 	= portionDetail.getType();   
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();     
		}
		 
		/** Available value */ 
		List<Portion> portionList = portionService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", "", emptyList, typeList, "", "", 0, 0);    
		cardinalityValueReq = portionList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq)  
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess deviceProfileCardinalityStatus(String requestMethod, Device deviceDetail) {

		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId 	= deviceDetail.getCompanyId(); 
		String userId	 	= deviceDetail.getUserId(); 
		String type 	 	= deviceDetail.getType();   
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();     
		}
		  
		/** Available value */ 
		List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", "", "", "", emptyList, typeList, "", "", 0, 0);    
		cardinalityValueReq = deviceList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq)  
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess deviceDataProfileCardinalityStatus(String requestMethod, DeviceData deviceDataDetail) {

		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId 	= deviceDataDetail.getCompanyId(); 
		String userId	 	= deviceDataDetail.getUserId(); 
		String type 	 	= deviceDataDetail.getType();   
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();      
		}
		   
		/** Available value */ 
		List<DeviceData> deviceDataList = deviceDataService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", "", "", emptyList, emptyList, emptyList, typeList, "", "", 0, 0);    
		cardinalityValueReq = deviceDataList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq)  
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
 
	public ProfileCardinalityAccess userAuthenticationProfileCardinalityStatus(String requestMethod, UserAuthentication userAuthenticationReq) {

		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, ""); 

		String companyId 	= userAuthenticationReq.getCompanyId(); 
		String userId	 	= userAuthenticationReq.getUserId(); 
		String type 	 	= userAuthenticationReq.getType();   
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();     
		} 
		 
		/** Available value */ 
		List<UserAuthentication> usrAuthenList = userAuthenticationService.viewListByCriteria(companyId, "", "", userId, "", "", "", emptyList, typeList, "", "", 0, 0);    
		cardinalityValueReq = usrAuthenList.size();  
		
		if (cardinalityValueDB == cardinalityValueReq) 
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
	  
	public ProfileCardinalityAccess adminUserProfileCardinalityStatus(String requestMethod, UserCollection userCollectionDetail) { 

		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, "");   
		
		String type 	  = userCollectionDetail.getType(); 
		String companyId  = userCollectionDetail.getCompanyId(); 
		String groupId 	  = userCollectionDetail.getGroupId(); 
		String divisionId = userCollectionDetail.getDivisionId(); 
		
		List<UserCollectionData> userCollList = userCollectionDetail.getUserCollections();
		int licenseCountReq = userCollList.size(); 
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();   
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {  
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();   
		}
		
		/** Available value */
		List<User> userList = userService.viewListByCriteria(companyId, "", "", "", "", "", "", "", emptyList, typeList, "", "", 0, 0);  
		cardinalityValueReq = userList.size();  
		
		if (cardinalityValueDB == cardinalityValueReq) 
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);

		boolean isLicenseAvailable = false;
		List<String> typeListSubs = Arrays.asList(ModelType.SubscriptionType.MOBILE_DEVICE_LICENSE_SUBSCRIPTION.toString()); 
		List<Subscription> subscriptionList = subscriptionService.viewListByCriteria(companyId, groupId, divisionId, "", typeListSubs);  
 
		if (subscriptionList.size() > 0) {   
		
			Subscription subDetail = subscriptionList.get(0);
			int availableCount = subDetail.getAvailableLicenseCount();
			int androidCount   = subDetail.getAndroidLicenseCount();
			int iosCount       = subDetail.getIosLicenseCount(); 
			
			if (licenseCountReq == 1 && availableCount > 0 && (androidCount > 0 || iosCount > 0)) { 
				
				for (int dlc = 0; dlc < userCollList.size(); dlc++) {
				
					UserCollectionData userCollDetail = userCollList.get(dlc); 
					String deviceType = userCollDetail.getDeviceType();  
					
					if ((licenseCountReq <= androidCount && deviceType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) ||
						(licenseCountReq <= iosCount && deviceType.equalsIgnoreCase(GC_REFERRED_BY_IOS))) { 
						isLicenseAvailable = true; 
					}
				}
			}   
		}
		
		if (isLicenseAvailable == false)		 	
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You don't have user license(s) in your company. Please contact Rudu Admin");
		return result;
	}

	public ProfileCardinalityAccess divisionPreferenceProfileCardinalityStatus(String requestMethod, DivisionPreference divisionPreferenceDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, "");
		
		String companyId = divisionPreferenceDetail.getCompanyId();
		String type      = divisionPreferenceDetail.getType();
		
		String displayValueMetadata = "";
		List<String> typeList  = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type); 
		if(metadataList.size() > 0) {
			cardinalityValueDB   = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();
		}
		
		/** Available value */ 
		List<DivisionPreference> divisionPreferencesList = divisionPreferenceService.viewListByCriteria(companyId, "", "", "", "", "", emptyList, typeList, "", "", 0, 0);
		cardinalityValueReq = divisionPreferencesList.size();
		
		if (cardinalityValueDB == cardinalityValueReq) 
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}
	
	public ProfileCardinalityAccess subscriptionProfileCardinalityStatus(String requestMethod, Subscription subscriptionDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, "");
		
		String companyId  = subscriptionDetail.getCompanyId();
		String divisionId = subscriptionDetail.getDivisionId();
		String type       = subscriptionDetail.getType();
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if(metadataList.size() > 0) {
			cardinalityValueDB = metadataList.get(0).getCardinality(); 
			displayValueMetadata = metadataList.get(0).getDisplayValue();
		}
		
		/** Available value */ 
		List<Subscription> subscriptionList = subscriptionService.viewListByCriteria(companyId, "", divisionId, "", "", emptyList, typeList, "", "", 0, 0);
		cardinalityValueReq = subscriptionList.size();
		
		if (cardinalityValueDB == cardinalityValueReq) 
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
	}   
	
	public ProfileCardinalityAccess orderProfileCardinalityStatus(String requestMethod, Order orderDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, "");
		
		String companyId      = orderDetail.getCompanyId();
		String divisionId 	  = orderDetail.getDivisionId();
		String subscriptionId = orderDetail.getSubscriptionId();
		String type           = orderDetail.getType();
		
		String displayValueMetadata = "";
		List<String> typeList 		= Arrays.asList(type);     
		List<String> emptyList 		= new ArrayList<String>();
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if(metadataList.size() > 0) {
			cardinalityValueDB = metadataList.get(0).getCardinality();
			displayValueMetadata = metadataList.get(0).getDisplayValue();
		} 
		
		/** Available value */
		List<Order> orderList = orderService.viewListByCriteria(companyId, "", divisionId, subscriptionId, "", "", emptyList, typeList, "", "", 0, 0);
		cardinalityValueReq = orderList.size(); 
		
		if (cardinalityValueDB == cardinalityValueReq)
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximun limit in " + displayValueMetadata);
 		return result;
		
	}
	
	public ProfileCardinalityAccess deviceSummaryProfileCardinalityStatus(String requestMethod, DeviceSummary deviceSummaryDetail) {
		
		int cardinalityValueDB = 0, cardinalityValueReq = 0;
		ProfileCardinalityAccess result = new ProfileCardinalityAccess(GC_STATUS_SUCCESS, "");
		
		String companyId 	= deviceSummaryDetail.getCompanyId();
		String deviceId     = deviceSummaryDetail.getDeviceId();
		String type 	 	= deviceSummaryDetail.getType();   
		
		String displayValueMetadata = "";
		List<String> typeList = Arrays.asList(type);      
		List<String> emptyList = new ArrayList<String>();  
		
		/** Metadata value */
		List<Metadata> metadataList = metadataService.viewCardinalityListByCriteria(companyId, type);
		if (metadataList.size() > 0) {    
			cardinalityValueDB = metadataList.get(0).getCardinality();
			displayValueMetadata = metadataList.get(0).getDisplayValue(); 
		}

		/** Available value */
		List<DeviceSummary> deviceSummaryList = deviceSummaryService.viewListByCriteria(companyId, "", "", "", "", "", "", "", deviceId, "", emptyList, typeList, "", "", 0, 0);    
		cardinalityValueReq = deviceSummaryList.size();

		if (cardinalityValueDB == cardinalityValueReq)  
			return new ProfileCardinalityAccess(GC_STATUS_ERROR, "You have reached maximum limit in " + displayValueMetadata);
		return result;
		
	}
    
}