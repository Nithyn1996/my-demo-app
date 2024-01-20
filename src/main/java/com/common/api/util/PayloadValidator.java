package com.common.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceFieldName;
import com.common.api.constant.APIResourceName;
import com.common.api.constant.GroupByConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.MetadataService;
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
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.KeyValuesFloat;
import com.common.api.model.type.CategoryType;
import com.common.api.model.type.ModelType;
import com.common.api.model.type.NotificationModelType;
import com.common.api.model.type.PredefinedType;
import com.common.api.request.KeyValue;
import com.common.api.request.PushNotificationRequest;
import com.common.api.request.UserCollection;
import com.common.api.request.UserCollectionData;
import com.common.api.response.APIPreConditionErrorField;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper; 
  
@Service
public class PayloadValidator extends APIFixedConstant {               

	@Autowired
	FieldValidator fieldValidator;
	@Autowired      
	ResultSetConversion resultSetConversion;   
	@Autowired  
	MetadataService metadataService;  	 
	@Autowired
	APIAuthorization apiAuthorization;  

	public List<String> isValidGroupBy(String groupBy) {    
 		List<String> errorList = new ArrayList<>();  
 		List <String> allowedGroupBy = GroupByConstant.getEnumValueListJsonField();     
 		if (!allowedGroupBy.contains(groupBy))
			errorList.add(EM_INVALID_GROUP_BY);   
 		return errorList;
	}   
	   
	public List<String> isValidSortOrderAndSortBy(String sortOrder, String sortBy) { 
 		
		List<String> errorList = new ArrayList<>();  
 		List <String> allowedSortBy = SortByConstant.getEnumValueListJsonFieldd();  
 		List <String> allowedSortOrder = Arrays.asList(GC_SORT_ASC, GC_SORT_DESC);
 		
 		if (!allowedSortOrder.contains(sortOrder))  
			errorList.add(EM_INVALID_SORT_ORDER);  
 		if (!allowedSortBy.contains(sortBy)) 
			errorList.add(EM_INVALID_SORT_BY); 
 		return errorList;
	}
	
	public String isValidJsonNodeToStringDynamicField(JsonNode jsonNode) {
	    try {
	    	if (jsonNode != null && jsonNode.size() > 0) {
	    		ObjectMapper mapper = new ObjectMapper();
	    		Object json = mapper.readValue(jsonNode.toString(), Object.class);
	    		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json); 
	    	}
	    } catch (Exception errMess) { 
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
	    }
	    return "";
	}
	
	public List<APIPreConditionErrorField> isValidDynamicField(JsonNode inputValueObj, boolean mandatory) { 

		String inputValue = ""; 
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
		
		try { 
			inputValue = isValidJsonNodeToStringDynamicField(inputValueObj);  
		} catch (Exception errMess) { }  
		
		String inputValTemp = inputValue.trim();     
		int inputValLength = inputValTemp.length();     
		 
		if (mandatory == true && inputValLength == 0) {        
			errorList.add(new APIPreConditionErrorField("dynamicField", EM_INVALID_JSON_NODE) );  
		} 
		if (inputValLength > 0) { 
	    	JsonNode parentReference = resultSetConversion.convertStringToJsonNode(inputValue); 
	    	if (parentReference.size() <= 0)
	    		errorList.add(new APIPreConditionErrorField("dynamicField", EM_INVALID_JSON_NODE)); 
		}      
 		return errorList;  
	}
	
	public List<APIPreConditionErrorField> isValidDynamicField(String inputValue, boolean mandatory) { 

		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();   
		String inputValTemp = inputValue.trim();   
		int inputValLength = inputValTemp.length();   
		 
		if (mandatory == true && inputValLength == 0) {        
			errorList.add(new APIPreConditionErrorField("dynamicField", EM_INVALID_JSON_NODE) );  
		} 
		if (inputValLength > 0) { 
	    	JsonNode parentReference = resultSetConversion.convertStringToJsonNode(inputValue); 
	    	if (parentReference.size() <= 0)
	    		errorList.add(new APIPreConditionErrorField("dynamicField", EM_INVALID_JSON_NODE)); 
		}      
 		return errorList;  
	} 

	public Collection<APIPreConditionErrorField> isValidDivisionPreferencePayload(String requestMethod, DivisionPreference divisionPreferenceReq){
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
		
		String id        = divisionPreferenceReq.getId();
		String companyId = divisionPreferenceReq.getCompanyId();
		String type   	 = divisionPreferenceReq.getType();    
		String status 	 = divisionPreferenceReq.getStatus();   
		String category  = divisionPreferenceReq.getCategory();
 		
		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.DIVISION_PREFERENCE.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
  		
 		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) { 

 		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {   
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		}	
 		return errorList;  
	}
 
	public List<APIPreConditionErrorField> isValidUserFeedbackPayload(String requestMethod, UserFeedback userFeedbackReq) { 

		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();

		String id 	     = userFeedbackReq.getId();    
		String type      = userFeedbackReq.getType();
		String status    = userFeedbackReq.getStatus(); 
		String companyId = userFeedbackReq.getCompanyId();

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.USER_FEEDBACK.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
		
		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) { 
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {  
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		}		
		return errorList;
	}
	
	public Collection<APIPreConditionErrorField> isValidSectionPreferencePayload(String requestMethod, SectionPreference sectionPreferenceReq) {
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
 
		String id 	  		= sectionPreferenceReq.getId();    
		String type   		= sectionPreferenceReq.getType();  
		String status 		= sectionPreferenceReq.getStatus();    
		String companyId    = sectionPreferenceReq.getCompanyId();
		String category  	= sectionPreferenceReq.getCategory();     

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);		  
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.SECTION_PREFERENCE.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
 		
		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {  
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {   
			
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}		
		return errorList;
	}

	public Collection<APIPreConditionErrorField> isValidUserPreferencePayload(String requestMethod, UserPreference userPreferenceReq) {
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
 
		String id 	  		= userPreferenceReq.getId();    
		String type   		= userPreferenceReq.getType();  
		String status 		= userPreferenceReq.getStatus();    
		String companyId    = userPreferenceReq.getCompanyId();
		String category  	= userPreferenceReq.getCategory();  
		String subCategory 	= userPreferenceReq.getSubCategory();    

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);
		String continentId 			= userPreferenceReq.getUserPreferenceField().getContinentId();
		String countryId   			= userPreferenceReq.getUserPreferenceField().getCountryId();
		
		String appNotification		= userPreferenceReq.getUserPreferenceField().getAppNotification();
		String backgroundStartup   	= userPreferenceReq.getUserPreferenceField().getBackgroundStartup();
		String beep				   	= userPreferenceReq.getUserPreferenceField().getBeep();
		String speedLimitBeep   	= userPreferenceReq.getUserPreferenceField().getSpeedLimitBeep();
		String voiceAlert   		= userPreferenceReq.getUserPreferenceField().getVoiceAlert(); 
		String coPliotMode   		= userPreferenceReq.getUserPreferenceField().getCoPliotMode();
		  
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.USER_PREFERENCE.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
  		if (subCategory.length() > 0)  
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.SUB_CATEGORY.toString(), Arrays.asList(subCategory), 1));
  		
 		if (type.equalsIgnoreCase(ModelType.UserPreferenceType.APP_SETTINGS.toString())) {
	 			
 			if (FieldValidator.isValidYesOrNo(appNotification) == false) 
				errorList.add(new APIPreConditionErrorField("userPreferenceField.appNotification", "Invalid app notification"));
	 		if (FieldValidator.isValidYesOrNo(backgroundStartup) == false) 
				errorList.add(new APIPreConditionErrorField("userPreferenceField.backgroundStartup", "Invalid background starting"));
	 		if (FieldValidator.isValidYesOrNo(beep) == false) 
				errorList.add(new APIPreConditionErrorField("userPreferenceField.beep", "Invalid beep"));
	 		if (FieldValidator.isValidYesOrNo(speedLimitBeep) == false) 
				errorList.add(new APIPreConditionErrorField("userPreferenceField.speedLimitBeep", "Invalid speed limit beep"));
	 		if (FieldValidator.isValidYesOrNo(voiceAlert) == false) 
				errorList.add(new APIPreConditionErrorField("userPreferenceField.voiceAlert", "Invalid voice alert"));
	 		if (FieldValidator.isValidNumber(coPliotMode, 0, 100, true) == false)   
				errorList.add(new APIPreConditionErrorField("userPreferenceField.coPliotMode", "Invalid co-pliot mode"));
	 		
 		} else if (type.equalsIgnoreCase(ModelType.UserPreferenceType.MAP_SETTINGS.toString())) {
	 		
 			if (FieldValidator.isValidId(continentId) == false) 
				errorList.add(new APIPreConditionErrorField("userPreferenceField.continentId", EM_INVALID_CONTINENT_ID));
	 		if (FieldValidator.isValidId(countryId) == false) 
				errorList.add(new APIPreConditionErrorField("userPreferenceField.countryId", EM_INVALID_COUNTRY_ID));
 		
 		}
 		
		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {  
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {   
			
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}		
		return errorList;
	}

	public Collection<APIPreConditionErrorField> isValidUserVerificationPayload(String requestMethod, UserVerification userVerificationReq) {  
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
 
		String id 	  		= userVerificationReq.getId();      
		String type   		= userVerificationReq.getType();   
		String status 		= userVerificationReq.getStatus();     
		String companyId    = userVerificationReq.getCompanyId();
		String password		= userVerificationReq.getUserVerificationField().getPassword();
    	String mobilePin 	= userVerificationReq.getUserVerificationField().getMobilePin();
 
		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);    
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.USER_VERIFICATION.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		 
		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {  
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {  
			
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));  
			if (type.equals(ModelType.UserVerificationType.USER_FORGOT_PASSWORD.toString())) { 		 		  
				if (FieldValidator.isValidMD5(password) == false) {      
					errorList.add(new APIPreConditionErrorField("userVerificationField.password", EM_INVALID_PASSWORD));
				} 
			} else if (type.equals(ModelType.UserVerificationType.USER_FORGOT_MOBILE_PIN.toString())) { 		 		 
				if (FieldValidator.isValidMobilePin(mobilePin, true) == false) {  
					errorList.add(new APIPreConditionErrorField("userVerificationField.mobilePin", EM_INVALID_MOBILE_PIN));
				}
			}
		}		
		return errorList;
	}

	public Collection<APIPreConditionErrorField> isValidUserPayload(String requestMethod, User userReq) {
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
 
		String id 	  		= userReq.getId();  
		String companyId	= userReq.getCompanyId();   
		String type   		= userReq.getType();    
		String status 		= userReq.getStatus();   

		String lastName			= userReq.getLastName();
		String email			= userReq.getEmail();
		String username			= userReq.getUsername(); 
		String password			= userReq.getPassword();    
		String emailVerified 	= userReq.getEmailVerified();      
		String usernameVerified	= userReq.getUsernameVerified();  
		String gender			= userReq.getGender(); 
		String payloadVersion   = userReq.getPayloadVersion();    
 
		String age					= userReq.getUserField().getAge();
		String eContactNumber 		= userReq.getUserField().getEmergencyContactNumber();
		String dLicenseIssueDate	= userReq.getUserField().getDrivingLicenseIssueDate();

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.USER.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));

 		if (gender.length() > 0) {
 			if (!gender.equalsIgnoreCase(APIFixedConstant.GC_GENDER_MALE) && 
 				!gender.equalsIgnoreCase(APIFixedConstant.GC_GENDER_FEMALE)) {
 				errorList.add(new APIPreConditionErrorField("gender", "Invalid Gender"));
 			} 
 		}   		
 		if (FieldValidator.isValid(lastName, VT_NAME, FL_NAME_MIN, FL_NAME_MAX, false) == false)  
			errorList.add(new APIPreConditionErrorField("lastName", "Invalid Last Name"));
 		if (FieldValidator.isValidEmail(email, false) == false)   
			errorList.add(new APIPreConditionErrorField("email", "Invalid Email"));
 		if (FieldValidator.isValidUsernameCustom(username, true) == false) 
			errorList.add(new APIPreConditionErrorField("username", "Invalid Username"));
 		if (FieldValidator.isValidYesOrNo(emailVerified) == false)  
			errorList.add(new APIPreConditionErrorField("userField.emailVerified", "Invalid Email Verified"));
 		if (FieldValidator.isValidYesOrNo(usernameVerified) == false) 
			errorList.add(new APIPreConditionErrorField("userField.usernameVerified", "Invalid Username Verified"));
  
 		if (FieldValidator.isValidNumber(age, 1, 120, false) == false)  
			errorList.add(new APIPreConditionErrorField("UserField.age", "Invalid Age"));
 		if (FieldValidator.isValid(eContactNumber, VT_NUMBER, 10, 15, false) == false)  
			errorList.add(new APIPreConditionErrorField("userField.emergencyContactNumber", "Invalid Emergency Contact Number"));
 		if (FieldValidator.isValid(dLicenseIssueDate, VT_DATE, 10, 10, false) == false)   
			errorList.add(new APIPreConditionErrorField("userField.drivingLicenseIssueDate", "Invalid Driving License Issue Date"));
		
		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {      
			 
	 		if (payloadVersion.equalsIgnoreCase("0")) { 
				EncryptDecrypt encryptDecryptTemp = new EncryptDecrypt();   
				password = encryptDecryptTemp.getMD5EncryptedValue(password);   
		    	userReq.setPassword(password);  
				if (FieldValidator.isValidMD5(password) == false)        
					errorList.add(new APIPreConditionErrorField("password", EM_INVALID_PASSWORD)); 
			} else if (payloadVersion.equalsIgnoreCase("1")) {
				if (FieldValidator.isValidMD5(password) == false)     
					errorList.add(new APIPreConditionErrorField("password", EM_INVALID_PASSWORD));      
			}
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {  
			
			if (FieldValidator.isValidId(id) == false)  
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}		
		return errorList;
	}
	
	public Collection<APIPreConditionErrorField> isValidUserSessionPayload(String requestMethod, UserSession userSessionReq) {
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
		
		String category       = userSessionReq.getCategory();
		String password  	  = userSessionReq.getPassword();  	
		String mobilePin 	  = userSessionReq.getMobilePin();  
		String payloadVersion = userSessionReq.getPayloadVersion(); 
		
		if (password.length() <= 0) {
			errorList.add(new APIPreConditionErrorField("password", EM_INVALID_PASSWORD));   
		}  
		 
		if (category.equalsIgnoreCase(CategoryType.UserSession.USER_PASSWORD.toString())) {  
			
			if (payloadVersion.equalsIgnoreCase("0")) { 
				EncryptDecrypt encryptDecryptTemp = new EncryptDecrypt();  
				password = encryptDecryptTemp.getMD5EncryptedValue(password);   
		    	userSessionReq.setPassword(password);  
				if (FieldValidator.isValidMD5(password) == false)         
					errorList.add(new APIPreConditionErrorField("password", EM_INVALID_PASSWORD)); 
			} else if (payloadVersion.equalsIgnoreCase("1")) {
				if (FieldValidator.isValidMD5(password) == false)      
					errorList.add(new APIPreConditionErrorField("password", EM_INVALID_PASSWORD));      
			}
			
		} else if (category.equalsIgnoreCase(CategoryType.UserSession.USER_MOBILE_PIN.toString())) { 
			if (FieldValidator.isValidMobilePin(mobilePin, true) == false) 
				errorList.add(new APIPreConditionErrorField("mobilePin", "Invalid Mobile Pin"));    
		} else { 
			errorList.add(new APIPreConditionErrorField("category", "Invalid Category"));  
		}
		return errorList;
	}
	 
	public Collection<APIPreConditionErrorField> isValidPropertyPayload(String requestMethod, Property propertyReq) {

		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
		
		String id 	  		= propertyReq.getId();  
		String companyId	= propertyReq.getCompanyId();  
		String type   		= propertyReq.getType();    
		String status 		= propertyReq.getStatus();   
		String category  	= propertyReq.getCategory();     

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.PROPERTY.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
  		
 		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {  
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {   
			
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}	
 		return errorList;   
	}

	public Collection<APIPreConditionErrorField> isValidSectionPayload(String requestMethod, Section sectionReq) { 
	
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();

		String id 	  		= sectionReq.getId();  
		String companyId	= sectionReq.getCompanyId();  
		String type   		= sectionReq.getType();    
		String status 		= sectionReq.getStatus();   
		String category  	= sectionReq.getCategory();     

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.SECTION.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
 		
 		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {  
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {   
			
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}	
		return errorList;    
	}   

	public Collection<APIPreConditionErrorField> isValidPortionPayload(String requestMethod, Portion portionReq) { 
	
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();

		String id 	  		= portionReq.getId();  
		String companyId	= portionReq.getCompanyId();  
		String type   		= portionReq.getType();    
		String status 		= portionReq.getStatus();   
		String category  	= portionReq.getCategory();    

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.PORTION.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
  		
 		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {  
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {   
			
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}	 		
 		return errorList;    
	}

	public Collection<APIPreConditionErrorField> isValidDevicePayload(String requestMethod, Device deviceReq) { 
	
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();

		String id 	  		= deviceReq.getId();  
		String companyId	= deviceReq.getCompanyId();  
		String subType 		= deviceReq.getSubType(); 
		String type   		= deviceReq.getType();    
		String status 		= deviceReq.getStatus();   
		String category  	= deviceReq.getCategory(); 
		String subCategory 	= deviceReq.getSubCategory();  
		Date createdAt   	= deviceReq.getCreatedAt();      
 
		try {  
			String createdAtStr = APIDateTime.convertDateTimeToString(createdAt); 
			if (FieldValidator.isValid(createdAtStr, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, false) == false)
				errorList.add(new APIPreConditionErrorField("createdAt", EM_INVALID_CREATED_DATE_TIME));  
		} catch (Exception errMess) {
			errorList.add(new APIPreConditionErrorField("createdAt", EM_INVALID_CREATED_DATE_TIME));  
		} 
		
		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.DEVICE.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
 		if (subCategory.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.SUB_CATEGORY.toString(), Arrays.asList(subCategory), 1));
 		if (subType.length() > 0)    
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.SUB_TYPE.toString(), Arrays.asList(subType), 1));
  		
 		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {  
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {   
			 
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}	
 		return errorList;   
	}
	
	public Collection<APIPreConditionErrorField> isValidDeviceDataPayload(String requestMethod, DeviceData deviceDataReq) { 
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();

		String id 	  		= deviceDataReq.getId();  
		String companyId	= deviceDataReq.getCompanyId(); 
		String subType 		= deviceDataReq.getSubType();    
		String type   		= deviceDataReq.getType();    
		String status 		= deviceDataReq.getStatus();   
		String category  	= deviceDataReq.getCategory();  
		String subCategory 	= deviceDataReq.getSubCategory();
		String subCategoryLevel = deviceDataReq.getSubCategoryLevel();	 
		Date createdAt   	= deviceDataReq.getCreatedAt();      
		 
		try {  
			String createdAtStr = APIDateTime.convertDateTimeToString(createdAt);
			if (FieldValidator.isValid(createdAtStr, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, false) == false)
				errorList.add(new APIPreConditionErrorField("createdAt", EM_INVALID_CREATED_DATE_TIME));  
		} catch (Exception errMess) {
			errorList.add(new APIPreConditionErrorField("createdAt", EM_INVALID_CREATED_DATE_TIME));  
		} 
		
		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.DEVICE_DATA.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
  		if (subCategory.length() > 0)  
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.SUB_CATEGORY.toString(), Arrays.asList(subCategory), 1));
  		if (subType.length() > 0)  
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.SUB_TYPE.toString(), Arrays.asList(subType), 1));
  		if (subCategoryLevel.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.SUB_CATEGORY_LEVEL.toString(), Arrays.asList(subCategoryLevel), 1));
  		 
  		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {        
			 
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) { 
			
			if (FieldValidator.isValidId(id) == false)
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}	
		return errorList;  
 
	}
 
	public Collection<APIPreConditionErrorField> isValidAdminUserPayload(String requestMethod, UserCollection userCollectionReq) {
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();

		String companyId	= userCollectionReq.getCompanyId();   
		String status		= userCollectionReq.getStatus();     
		String type			= userCollectionReq.getType();    
		List<UserCollectionData> userCollections = userCollectionReq.getUserCollections(); 

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.USER.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));

		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {    

			List<String> deviceTypeList  = Arrays.asList(GC_REFERRED_BY_ANDROID, GC_REFERRED_BY_IOS); 
			List<String> allowedUsernameType = Arrays.asList(ModelType.UsernameType.MOBILE_NUMBER.toString(), ModelType.UsernameType.CUSTOM.toString()); 
			
	 		for (int uc = 0; uc < userCollections.size(); uc++) { 
	 			
	 			UserCollectionData userColData = userCollections.get(uc);    
		 		String username = userColData.getUsername();  
		 		String password = userColData.getPassword();    
		 		String usernameType = userColData.getUsernameType(); 
		 		String deviceType   = userColData.getDeviceType(); 
		 		
		 		if (allowedUsernameType.contains(usernameType)) { 
		 			if (usernameType.equalsIgnoreCase(ModelType.UsernameType.MOBILE_NUMBER.toString()) && FieldValidator.isValidUsername(username, true) == false) 
		 				errorList.add(new APIPreConditionErrorField("usernameType", EM_INVALID_USERNAME + " 0")); 
		 			else if (usernameType.equalsIgnoreCase(ModelType.UsernameType.CUSTOM.toString()) && FieldValidator.isValidUsernameCustom(username, true) == false) 
		 				errorList.add(new APIPreConditionErrorField("usernameType", EM_INVALID_USERNAME + " 1")); 
		 		} else {
	 				errorList.add(new APIPreConditionErrorField("usernameType", EM_INVALID_USERNAME_TYPE + " 3")); 
		 		}
		 		
				if (!deviceTypeList.contains(deviceType)) 
					errorList.add(new APIPreConditionErrorField("deviceType", EM_INVALID_D_TYPE)); 
	 		  
		 		if (FieldValidator.isValidMD5(password) == false)   
					errorList.add(new APIPreConditionErrorField("password", EM_INVALID_PASSWORD));
	 		}  
		}		
		return errorList; 
	}

	public Collection<APIPreConditionErrorField> isValidUserAuthenticationPayload(String requestMethod, UserAuthentication userAuthenticationReq) { 
	
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>(); 

		String id 	  		= userAuthenticationReq.getId();  
		String companyId	= userAuthenticationReq.getCompanyId();  
		String type   		= userAuthenticationReq.getType();      
		String status 		= userAuthenticationReq.getStatus();    
		String category  	= userAuthenticationReq.getCategory();    

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.USER_AUTHENTICATION.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		if (category.length() > 0) 
 	 		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.CATEGORY.toString(), Arrays.asList(category), 1));
  		
 		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {  
			
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {   
			
			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
			
		}	 		
 		return errorList;
	}

	public Collection<APIPreConditionErrorField> isValidManualPushNotificationPayload(String requestMethod, PushNotificationRequest pushNotifReq) {

		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();   
		
		String event = pushNotifReq.getEvent(); 
		String type  = pushNotifReq.getType(); 
		List<KeyValue> keyValues = pushNotifReq.getKeyValues();   
		event = (event.length() <= 0) ? NotificationModelType.ManualPushEvent.NEW_ALERT.toString() : event;
		
		List<String> allowedTypes = Arrays.asList(NotificationModelType.ManualPushType.VEHICLE_ACCIDENT_ALERT_TO_CO_WORKER.toString(),
												  NotificationModelType.ManualPushType.VEHICLE_TRAFFIC_HALT_TO_CO_WORKER.toString());
		
		List<String> allowedEvent = Arrays.asList(NotificationModelType.ManualPushEvent.NEW_ALERT.toString(),
												  NotificationModelType.ManualPushEvent.CANCEL_LAST_ALERT.toString());
		
		if (allowedTypes.contains(type)) { 
			
			if (type.equalsIgnoreCase(NotificationModelType.ManualPushType.VEHICLE_ACCIDENT_ALERT_TO_CO_WORKER.toString())) {
				
				if (allowedEvent.contains(event)) {
				
					List<String> allowedKeys = Arrays.asList("username", "firstName", "location", "address", "latitude", "longitude"); 
					
					for (int kvc = 0; kvc < keyValues.size(); kvc++) { 
						
						KeyValue keyValueTemp = keyValues.get(kvc);
						String key   = keyValueTemp.getKey();
						String value = keyValueTemp.getValue();
						
						if (!allowedKeys.contains(key))  
							errorList.add(new APIPreConditionErrorField("keyValues.key." + key, EM_INVALID_KEY));	 
						if (!allowedKeys.contains(key)) 
							errorList.add(new APIPreConditionErrorField("keyValues.value." + value, EM_INVALID_VALUE)); 
					} 
					
				} else {
					errorList.add(new APIPreConditionErrorField("event", EM_INVALID_EVENT));			
				} 
				
			} else if (type.equalsIgnoreCase(NotificationModelType.ManualPushType.VEHICLE_ACCIDENT_ALERT_TO_CO_WORKER.toString())) {
				
				if (allowedEvent.contains(event)) {
					
					List<String> allowedKeys = Arrays.asList("username", "firstName", "location", "address", "latitude", "longitude"); 
					
					for (int kvc = 0; kvc < keyValues.size(); kvc++) { 
						
						KeyValue keyValueTemp = keyValues.get(kvc);
						String key   = keyValueTemp.getKey(); 
						String value = keyValueTemp.getValue(); 
						
						if (!allowedKeys.contains(key) || key.length() <= 0)  
							errorList.add(new APIPreConditionErrorField("keyValues.key." + key, EM_INVALID_KEY));	 
						if (!allowedKeys.contains(key) || value.length() <= 0) 
							errorList.add(new APIPreConditionErrorField("keyValues.value." + value, EM_INVALID_VALUE)); 
					} 
					 
				} else {
					errorList.add(new APIPreConditionErrorField("event", EM_INVALID_EVENT));			
				} 
			}
		
		} else {
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));			
		}
 		return errorList;  
	}

	public Collection<APIPreConditionErrorField> isValidSubscriptionPayload(String requestMethod, Subscription subscriptionReq) {
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
		 
		String id        = subscriptionReq.getId();
		String companyId = subscriptionReq.getCompanyId();
		String type   	 = subscriptionReq.getType();    
		String status 	 = subscriptionReq.getStatus();
		
		int iosLicenseCount			 = subscriptionReq.getIosLicenseCount();
		int androidLicenseCount		 = subscriptionReq.getAndroidLicenseCount();
		int availableLicenseCount	 = subscriptionReq.getAvailableLicenseCount();
		
		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);		
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.SUBSCRIPTION.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
		
		if (iosLicenseCount < 0) 
			errorList.add(new APIPreConditionErrorField("iosLicenseCount", EM_INVALID_IOS_COUNT));
		if (androidLicenseCount < 0)
			errorList.add(new APIPreConditionErrorField("androidLicenseCount", EM_INVALID_ANDROID_COUNT));
		
		availableLicenseCount = iosLicenseCount + androidLicenseCount;
		subscriptionReq.setAvailableLicenseCount(availableLicenseCount);
		
		if(requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {
			
		} else if(requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {
			if (FieldValidator.isValidId(id) == false)
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		}
		
		return errorList;
	}
	
	public Collection<APIPreConditionErrorField> isValidOrderPayload(String requestMethod, Order order){
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
		
		String id = order.getId();
		String companyId = order.getCompanyId();
		String type = order.getType();
		String status = order.getStatus();
		
		int iosLicenseCount 	= order.getIosLicenseCount();
		int androidLicenseCount = order.getAndroidLicenseCount();
		int totalLicenseCount 	= order.getTotalLicenseCount();
		
		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId); 
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.ORDER.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
 		
 		if(iosLicenseCount < 0)
			errorList.add(new APIPreConditionErrorField("iosLicenseCount", EM_INVALID_IOS_COUNT));
		if(androidLicenseCount < 0) 
			errorList.add(new APIPreConditionErrorField("androidLicenseCount", EM_INVALID_ANDROID_COUNT));
		
		totalLicenseCount = iosLicenseCount + androidLicenseCount;
 		order.setTotalLicenseCount(totalLicenseCount);
 		
 		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) { 
 			
 		}else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {
 			
 			if (FieldValidator.isValidId(id) == false) 
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
 			
 		}
		return errorList;
		
	}
	
	public Collection<APIPreConditionErrorField> isValidDeviceSummaryPayload(String requestMethod, DeviceSummary deviceSummaryReq) {

		List<APIPreConditionErrorField> errorList = new ArrayList<>();

		String id 	  		= deviceSummaryReq.getId(); 
		String companyId	= deviceSummaryReq.getCompanyId();
		String type   		= deviceSummaryReq.getType();
		String status 		= deviceSummaryReq.getStatus();
		Date createdAt   	= deviceSummaryReq.getCreatedAt();

		try {
			String createdAtStr = APIDateTime.convertDateTimeToString(createdAt);
			if (FieldValidator.isValid(createdAtStr, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, false) == false)
				errorList.add(new APIPreConditionErrorField("createdAt", EM_INVALID_CREATED_DATE_TIME));
		} catch (Exception errMess) {
			errorList.add(new APIPreConditionErrorField("createdAt", EM_INVALID_CREATED_DATE_TIME));
		}

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);
		errorList.addAll(fieldValidator.checkMetadata(metadataList, type, APIResourceFieldName.STATUS.toString(), Arrays.asList(status), 1));
		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.DEVICE_SUMMARY.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));

		if (requestMethod.equalsIgnoreCase(GC_METHOD_POST)) {
		} else if (requestMethod.equalsIgnoreCase(GC_METHOD_PUT)) {
			if (!FieldValidator.isValidId(id))
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		}
		return errorList;
	}
	 
    public String convertAsDeviceSummaryUpdateQueryMiddle(List<String> countFieldList, List<KeyValuesFloat> keyValues, DeviceDataRunningField ddRunningField) {
		 
		String result = "";  
		String countField     = "_count";
		String distanceField  = "_distance";  
		String durationField  = "_duration";  
		List<String> countFields    = apiAuthorization.predefinedFieldList(PredefinedType.COUNT_FIELDS.toString());
		List<String> distanceFields = apiAuthorization.predefinedFieldList(PredefinedType.DISTANCE_FIELDS.toString()); 
		List<String> durationFields = apiAuthorization.predefinedFieldList(PredefinedType.DURATION_FIELDS.toString());
 
		float overSpeedKiloMeter      = ddRunningField.getOverSpeedKiloMeter(); // To be removed
		float overSpeedDuration       = ddRunningField.getOverSpeedDuration(); // To be removed 
		float mobileUseCallKiloMeter  = ddRunningField.getMobileUseCallKiloMeter(); // To be remove
		float mobileUseCallDuration   = ddRunningField.getMobileUseCallDuration(); // To be remove
		float mobileScreenOnKiloMeter = ddRunningField.getMobileScreenOnKiloMeter(); // To be removed
		float mobileScreenOnDuration  = ddRunningField.getMobileScreenOnDuration(); // To be removed 
	
		Map<String, Long> fieldList = countFieldList.stream().collect(Collectors.groupingBy(string -> string, Collectors.counting()));
		for (Map.Entry<String, Long> entry : fieldList.entrySet()) {
		
			String key = entry.getKey().toLowerCase();
			long value = entry.getValue();    
			
			String countKey = key + countField;
			if (countFields.contains(countKey) && value > 0) 
				result = result + countKey + " = " + countKey + " + " + value + ", ";   
		} 
		
		for (int kvc = 0; kvc < keyValues.size(); kvc++) {

			KeyValuesFloat kvDetail = keyValues.get(kvc); 
			String key = kvDetail.getKey().toLowerCase();
			List<Float> values = kvDetail.getValues();	
			
			String distanceKey = key + distanceField;
			String durationKey = key + durationField;
			float distanceVal  = (values.size() > 0) ? values.get(0) : 0; 
			float durationVal  = (values.size() > 1) ? values.get(1) : 0;
			
			if (distanceKey.equals("over_speed_distance") && distanceVal <= 0) 
				distanceVal = overSpeedKiloMeter;
			if (distanceKey.equals("mobile_use_in_accepted_distance") && distanceVal <= 0) 
				distanceVal = mobileUseCallKiloMeter;
			if (distanceKey.equals("mobile_screen_screen_on_distance") && distanceVal <= 0) 
				distanceVal = mobileScreenOnKiloMeter;
			
			if (durationKey.equals("over_speed_duration") && durationVal <= 0) 
				durationVal = overSpeedDuration;
			if (durationKey.equals("mobile_use_in_accepted_duration") && durationVal <= 0)   
				durationVal = mobileUseCallDuration;
			if (durationKey.equals("mobile_screen_screen_on_duration") && durationVal <= 0) 
				durationVal = mobileScreenOnDuration; 
			
			if (distanceFields.contains(distanceKey) && distanceVal > 0)
				result = result + distanceKey + " = " + distanceVal + ", "; 
			if (durationFields.contains(durationKey) && durationVal > 0)
				result = result + durationKey + " = " + durationVal + ", ";
		} 
		return result.trim().replaceAll(",$", "");
	}
 
} 
