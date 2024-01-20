package com.common.api.resource;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.constant.APIRolePrivilegeType;
import com.common.api.datasink.service.DeviceDataService;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.DeviceSummaryService;
import com.common.api.datasink.service.UserDistinctService;
import com.common.api.model.Device;
import com.common.api.model.DeviceData;
import com.common.api.model.field.DeviceDataAddressField;
import com.common.api.model.field.DeviceDataField;
import com.common.api.model.field.DeviceDataRiskField;
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.DeviceField;
import com.common.api.model.field.KeyValuesFloat;
import com.common.api.model.status.ModelStatus;
import com.common.api.model.type.CategoryType;
import com.common.api.model.type.InsertModeType;
import com.common.api.model.type.ModelType;
import com.common.api.model.type.ModelType.DeviceType;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileCardinalityAccess;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.service.util.ServiceFCMNotification;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.DataTypeConversion;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Device Data", tags = {"Device Data"}) 
@RestController            
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"}) 
public class DeviceDataResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {              
		return new PropertySourcesPlaceholderConfigurer();     
	} 
 
	@Autowired 
	FieldValidator fieldValidator;      
	@Autowired 
	PayloadValidator payloadValidator; 
	@Autowired
	ResultSetConversion resultSetConversion;  
	@Autowired     
	ProfileSessionRoleService profileSessionRoleService;    
	@Autowired 
	ProfileDependencyService profileDependencyService; 
	@Autowired 
	ProfileCardinalityService profileCardinalityService; 

    @Autowired 
    DeviceService deviceService; 
    @Autowired 
    DeviceDataService deviceDataService; 
    @Autowired 
    DeviceSummaryService deviceSummaryService; 
    @Autowired 
    UserDistinctService userDistinctService; 
    @Autowired
    ResultSetConversion rsConversion; 
    @Autowired
    DataTypeConversion dataTypeConversion; 
    @Autowired
    ServiceFCMNotification serviceFCMNotification;
    
	@Value("${e006.failed.dependency}")       
	String e006FailedDependency = "";  
	@Value("${e003.profile.not.exist}")        
	String e003ProfileNotExist = ""; 
	@Value("${e008.process.already.completed}")        
	String e008ProcessAlreadyCompleted = "";  
	
	@ApiOperation(value = "View Device Data Details", 
				  nickname = "DeviceDataDetailView", 
				  notes = "View Device Data Details") 
	@ApiResponses(value = {  
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = DeviceData.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
      })
	@RequestMapping(value = "/deviceData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewDeviceDataDetail(HttpServletRequest httpRequest,   
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = true) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = false) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = false) String sectionId,
			@RequestParam(name = "portionId", defaultValue = "", required = false) String portionId,
			@RequestParam(name = "deviceId", defaultValue = "", required = false) String deviceId,
 			@RequestParam(name = "id", defaultValue = "", required = false) String id,      
			@RequestParam(name = "status", defaultValue = "", required = false) String status,      
			@RequestParam(name = "category", defaultValue = "", required = false) String category,  
			@RequestParam(name = "subCategory", defaultValue = "", required = false) String subCategory, 
			@RequestParam(name = "type", defaultValue = "DEVICE_DATA", required = true) String type,    
  			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,  
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,      
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) { 
		
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();  
		String apiLogString     = "LOG_API_DEVICE_DATA_VIEW";        
    	String apiInstLogString = "LOG_INST_DEVICE_DATA_VIEW";     
    	   
		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder; 
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;     
    	type	  = (type.length() <= 0) ? APIResourceName.DEVICE_DATA.toString() : type;   
    	String resourceType = APIResourceName.DEVICE_DATA.toString();                 

    	List<String> statusList 		= Util.convertIntoArrayString(status); 
    	List<String> typeList   		= Util.convertIntoArrayString(type);
    	List<String> categoryList   	= Util.convertIntoArrayString(category);
    	List<String> subCategoryList 	= Util.convertIntoArrayString(subCategory); 

    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (FieldValidator.isValidId(companyId) == false)        
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID)); 
		if (FieldValidator.isValidIdOptional(groupId) == false)         
			errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID));  
		if (FieldValidator.isValidId(userId) == false)    
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID)); 
		if (FieldValidator.isValidIdOptional(propertyId) == false)        
			errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_ID));  
		if (FieldValidator.isValidInternalTextOptional(status) == false)          
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));  
		if (FieldValidator.isValidInternalTextOptional(status) == false)         
			errorList.add(new APIPreConditionErrorField("status", EM_INVALID_STATUS));  
		if (FieldValidator.isValidInternalTextOptional(type) == false)        
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE)); 
		
		apiLogString = apiLogString + " referredBy: " + referredBy + " sessionId: " + sessionId + " userId: " + userId;   
		          
  		if (errorList.size() == 0) {
  			  
  			String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, moduleId);
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage(); 
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {       
			   
				List<DeviceData> succPayload = deviceDataService.viewListByCriteria(companyId, groupId, divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId, id, categoryList, subCategoryList, statusList, typeList, sortBy, sortOrder, offset, limit);  
				 
	  	  		if (succPayload.size() >= 0) {                   
					APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload);      
				}   

			} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) {
				return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));  
			} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, roleStatucMess));
			}   		
		}  
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));  
    } 
	
	@ResponseStatus(code = HttpStatus.CREATED) 
	@ApiOperation(value = "Create Device Data Details", 
				  nickname = "DeviceDataDetailCreate",  
				  notes = "Create Device Data Details")  
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = DeviceData.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/deviceData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createDeviceDataDetail(HttpServletRequest httpRequest,    
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,  
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestBody DeviceData deviceDataReq) {   
 
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();   
		
		String apiLogString     = "LOG_API_DEVICE_DATA_CREATE";          
    	String apiInstLogString = "LOG_INST_DEVICE_DATA_CREATE";          
    	
    	String companyId 	= deviceDataReq.getCompanyId();  
    	String userId 		= deviceDataReq.getUserId(); 
    	String deviceId 	= deviceDataReq.getDeviceId();   
    	String type			= deviceDataReq.getType();  
    	String category		= deviceDataReq.getCategory();  
    	String subCategory	= deviceDataReq.getSubCategory(); 
    	String subCategoryLevel	= deviceDataReq.getSubCategoryLevel();   
    	Date createdAtReq	= deviceDataReq.getCreatedAt();   
    	
    	type	  			= (type.length() <= 0) ? APIResourceName.DEVICE_DATA.toString() : type;
    	String resourceType = APIResourceName.DEVICE_DATA.toString();            
    	 
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<DeviceData>> validationErrors = validator.validate(deviceDataReq);

    	/** Temporary Conditions 1 - Starts */
    	if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.ALERT_DATA.toString())) {
    		if (subCategory.equalsIgnoreCase("FORCE_ACCELERATION") || subCategory.equalsIgnoreCase("SEVERE_ACCELERATION")) {
    			deviceDataReq.setCategory(CategoryType.DeviceDataCategory.STRESS_STRAIN_DATA.toString());
    			deviceDataReq.setSubCategory("SEVERE_ACCELERATION"); 
    			if (subCategoryLevel.length() <= 0) 
    				deviceDataReq.setSubCategoryLevel("LOW");
    		} else if (subCategory.equalsIgnoreCase("SUDDEN_BRAKING") || subCategory.equalsIgnoreCase("SEVERE_BRAKING")) {
    			deviceDataReq.setCategory(CategoryType.DeviceDataCategory.STRESS_STRAIN_DATA.toString());
    			deviceDataReq.setSubCategory("SEVERE_BRAKING");  
    			if (subCategoryLevel.length() <= 0) 
    				deviceDataReq.setSubCategoryLevel("LOW");
    		} else if (subCategory.equalsIgnoreCase("SEVERE_CORNERING")) { 
    			deviceDataReq.setCategory(CategoryType.DeviceDataCategory.STRESS_STRAIN_DATA.toString());  
    			if (subCategoryLevel.length() <= 0) 
    				deviceDataReq.setSubCategoryLevel("LOW");   	 		
    		}
    	}
     	category	= deviceDataReq.getCategory();  
     	if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.STRESS_STRAIN_DATA.toString()) && subCategoryLevel.length() <= 0)  
			deviceDataReq.setSubCategoryLevel("LOW");    
    	/** Temporary Conditions 1 - Ends */
     	
     	/** Temporary Conditions 2 - Starts */
     	List<String> subCategoryList = Arrays.asList("OVER_SPEED", "MOBILE_USE", "MOBILE_SCREEN"); 
		if (subCategoryList.contains(subCategory)) {    
			deviceDataReq.setCategory(CategoryType.DeviceDataCategory.MANUAL_DATA.toString()); 
		} 
		/** Temporary Conditions 1 - Ends */
    	
    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidDeviceDataPayload(GC_METHOD_POST, deviceDataReq));   
    	for (ConstraintViolation<DeviceData> error : validationErrors)  
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage())); 
    	
    	Timestamp createdAt = dbOperationStartTime;
    	try {
	    	if (createdAtReq != null) 
	    		createdAt = APIDateTime.convertDateTimeToTimestamp(createdAtReq);  
    	} catch (Exception errMess) { }

  		if (errorList.size() == 0) {      

  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage(); 
			String sessionUserId  = roleStatus.getUserId();  
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {     

				ProfileDependencyAccess profileDependency = profileDependencyService.deviceDataProfileDependencyStatus(GC_METHOD_POST, deviceDataReq); 
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage(); 
						
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {   
				 
					ProfileCardinalityAccess profileCardinality = profileCardinalityService.deviceDataProfileCardinalityStatus(GC_METHOD_POST, deviceDataReq); 
					String profileCardinalityCode = profileCardinality.getCode();
					String profileCardinalityMess = profileCardinality.getMessage();
					
					if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {
						
						List<String> categoryList = Arrays.asList(CategoryType.DeviceDataCategory.END_DATA.toString());        
						List<String> emptyList = new ArrayList<String>();  
						List<DeviceData> deviceDataList = deviceDataService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", deviceId, "", categoryList, emptyList, emptyList, emptyList, "", "", 0, 0);    
						
						if (deviceDataList.size() <= 0) {   
								  
							deviceDataReq.setInsertMode(InsertModeType.API_CALL.toString()); 
							deviceDataReq.setCreatedAt(createdAt);
							deviceDataReq.setModifiedAt(dbOperationStartTime);
							deviceDataReq.setCreatedBy(sessionUserId);  
							deviceDataReq.setModifiedBy(sessionUserId);
							deviceDataReq.setInsertedAt(dbOperationStartTime);  
							
							deviceDataReq = this.extraUpdateDeviceDataField(deviceDataReq);
							DeviceData succPayload = deviceDataService.createDeviceData(deviceDataReq); 
							
				  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

				  				try {
				  					DeviceDataRunningField ddRunningField = deviceDataReq.getDeviceDataRunningField();
				  					List<KeyValuesFloat> keyValuesFloat = deviceDataReq.getDeviceDataRunningField().getKeyValues(); 
					  				List<String> countFieldList = Arrays.asList(resultSetConversion.concatenateTwoString(subCategory, subCategoryLevel));
 					  				deviceSummaryService.updateDeviceSummaryCountByDeviceId(userId, deviceId, countFieldList, keyValuesFloat, ddRunningField); 
				  				} catch (Exception errMess) { } 
				  				
				  				this.extraUpdateDeviceDataCountInDevice(deviceDataReq);   
			  					this.extraUpdateDeviceDataDetailInDevice(deviceDataReq, dbOperationStartTime);
			  					
								APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
								APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
								return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);  
				  			}   
							return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
						}   
						return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_008_PROCESS_ALREADY_CMPTD, e008ProcessAlreadyCompleted));
					}
					return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileCardinalityMess));
				}
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileDependencyMess));

			} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) { 
				return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess)); 
			} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, roleStatucMess));
			} 
		}  
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));  
    }
	
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	@ApiOperation(value = "Update Device Data Details",
				  nickname = "UpdateDeviceDataDetailCreate",    
				  notes = "Update Device Data Details")
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = DeviceData.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/deviceData", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> updateDeviceDataDetail(HttpServletRequest httpRequest,    
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,  
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestBody DeviceData deviceDataReq) {    
 
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();     
		
		String apiLogString     = "LOG_API_DEVICE_DATA_UPDATE";    
    	String apiInstLogString = "LOG_INST_DEVICE_DATA_UPDATE";     
    
    	String id			= deviceDataReq.getId();  
    	String companyId 	= deviceDataReq.getCompanyId();      
    	String type			= deviceDataReq.getType();      
    	type	  			= (type.length() <= 0) ? APIResourceName.DEVICE_DATA.toString() : type;  
    	String resourceType = APIResourceName.DEVICE_DATA.toString();                 

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<DeviceData>> validationErrors = validator.validate(deviceDataReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidDeviceDataPayload(GC_METHOD_PUT, deviceDataReq));   
    	for (ConstraintViolation<DeviceData> error : validationErrors) 
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage())); 
    	
  		if (errorList.size() == 0) {      

  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage();   
			String sessionUserId  = roleStatus.getUserId();  
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) { 

				ProfileDependencyAccess profileDependency = profileDependencyService.deviceDataProfileDependencyStatus(GC_METHOD_PUT, deviceDataReq); 
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();   
						  
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {  
				 
					List<String> emptyList = new ArrayList<String>();    
					List<DeviceData> deviceDataList = deviceDataService.viewListByCriteria(companyId, "", "", "", "", "", "", "", "", id, emptyList, emptyList, emptyList, emptyList, "", "", 0, 0);
				    
					if (deviceDataList.size() > 0) {     
	
						DeviceData deviceDataDetail = deviceDataList.get(0);  
						 
						deviceDataDetail.setDeviceDataField(deviceDataReq.getDeviceDataField()); 
						deviceDataDetail.setDeviceDataLiveField(deviceDataReq.getDeviceDataLiveField()); 
						deviceDataDetail.setDeviceDataErrorField(deviceDataReq.getDeviceDataErrorField());   
						deviceDataDetail.setModifiedBy(sessionUserId); 
						deviceDataDetail.setModifiedAt(dbOperationStartTime);      
			  			
						DeviceData succPayload = deviceDataService.updateDeviceData(deviceDataDetail); 
			  			
			  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {
			  				 
							APILog.writeInfoLog(apiLogString + " " + succPayload.toString());    
							APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
							return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(succPayload);   
			  			}   
						return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));  
					}
					return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_003_PROFILE_NOT_EXIST, e003ProfileNotExist));
				}
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileDependencyMess));

			} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) {
				return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));  
			} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, roleStatucMess));
			} 
		}  
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));  
    }

	public DeviceData extraUpdateDeviceDataField(DeviceData deviceDataReq) {  
		
		try {
			
			DeviceDataField dDataFieldReq = deviceDataReq.getDeviceDataField();   
			deviceDataReq.setRisk(rsConversion.resultSetToFloatValue(dDataFieldReq.getRisk(), false)); 
			deviceDataReq.setKiloMeter(rsConversion.resultSetToFloatValue(dDataFieldReq.getKiloMeter(), false));
			deviceDataReq.setSpeed(rsConversion.resultSetToFloatValue(dDataFieldReq.getSpeed(), false)); 
			
			deviceDataReq.setPreviousSpeed(rsConversion.resultSetToFloatValue(dDataFieldReq.getPreviousSpeed(), false)); 
			deviceDataReq.setSpeedLimit(rsConversion.resultSetToFloatValue(dDataFieldReq.getSpeedLimit(), false)); 
			deviceDataReq.setActivityDuration(rsConversion.resultSetToFloatValue(dDataFieldReq.getActivityDuration(), false)); 
			deviceDataReq.setLatitude(rsConversion.resultSetToFloatValue(dDataFieldReq.getLatitude(), false)); 
			deviceDataReq.setLongitude(rsConversion.resultSetToFloatValue(dDataFieldReq.getLongitude(), false)); 

			deviceDataReq.setZipCode(dDataFieldReq.getZipCode());  
			String deviceMode = (dDataFieldReq.getDeviceMode().length() > 0) ? dDataFieldReq.getDeviceMode() : deviceDataReq.getDeviceMode();
 			deviceDataReq.setDeviceMode(this.extraUpdateDeviceMode(deviceMode));   
			
		} catch (Exception errMess) { }
		return deviceDataReq; 	
	}
	
	public String extraUpdateDeviceMode(String deviceModeReq) {
		
		String prefix = ModelType.DeviceMode.WORK.toString();   
		String suffix = ModelType.DeviceMode.AUTO.toString();

		try {   
			
			boolean personalMatch = (deviceModeReq.contains(ModelType.DeviceMode.PERSONAL.toString())) ? true : false; 
			boolean manualMatch   = (deviceModeReq.contains(ModelType.DeviceMode.MANUAL.toString())) ? true : false; 
					
			if (personalMatch == true)  
				prefix = ModelType.DeviceMode.PERSONAL.toString(); 
			if (manualMatch == true)  
				suffix = ModelType.DeviceMode.MANUAL.toString();		
			
		} catch (Exception errMess) { } 		 
		
		return prefix + "_" + suffix;   	
		
	}
	
	public int extraUpdateDeviceDataCountInDevice(DeviceData deviceDataReq) { 
		
		String logString = "DEVICE_DATA_CREATE_DEVICE_UPDATE: ";
		
		int result	= 0;   
		String userId 		= deviceDataReq.getUserId(); 
		String deviceId 	= deviceDataReq.getDeviceId();   
		String category 	= deviceDataReq.getCategory();   
		String subCategory 	= deviceDataReq.getSubCategory(); 
		
		DeviceDataRiskField riskField = deviceDataReq.getDeviceDataRiskField();
		int riskNegativeCount = riskField.getSlotNegativeCount();
		int riskZeroCount 	  = riskField.getSlotZeroCount();
		int riskSlot1Count 	  = riskField.getSlot1Count();  
		int riskSlot2Count 	  = riskField.getSlot2Count();
		int riskSlot3Count 	  = riskField.getSlot3Count();
		int riskSlot4Count 	  = riskField.getSlot4Count();
		int riskSlot5Count 	  = riskField.getSlot5Count();
		int riskSlot6Count 	  = riskField.getSlot6Count();
		int riskSlot7Count 	  = riskField.getSlot7Count();
		int riskSlot8Count 	  = riskField.getSlot8Count();
		int riskSlot9Count 	  = riskField.getSlot9Count();
		int riskSlot10Count   = riskField.getSlot10Count();

		DeviceDataRunningField runningField = deviceDataReq.getDeviceDataRunningField();   
		float mScreenOnDuration  = runningField.getMobileScreenOnDuration(); 
		float mUseCallDuration   = runningField.getMobileUseCallDuration(); 
		float overSpeedDuration  = runningField.getOverSpeedDuration();  
		float mScreenOnKiloMeter = runningField.getMobileScreenOnKiloMeter(); 
		float mUseCallKiloMeter  = runningField.getMobileUseCallKiloMeter(); 
		float overSpeedKiloMeter = runningField.getOverSpeedKiloMeter(); 
		
		try {     
			
			int startData = 0, distanceData = 0, alertData = 0, stressStrainData = 0, manualData = 0, endData = 0;
			if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.START_DATA.toString())) 
				startData = startData + 1; 
			else if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.DISTANCE_DATA.toString()))  
					distanceData = distanceData + 1; 
			else if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.ALERT_DATA.toString()) && subCategory.length() > 0)  
					alertData = alertData + 1; 
			else if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.STRESS_STRAIN_DATA.toString())) 
				stressStrainData = stressStrainData + 1; 
			else if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.MANUAL_DATA.toString())) 
				manualData = manualData + 1; 
			else if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.END_DATA.toString()))  
				endData = endData + 1; 
 			
			result = deviceService.updateDeviceDataCountByDeviceId(userId, deviceId, startData, distanceData, alertData, stressStrainData, manualData, endData, 
																	mScreenOnDuration, mUseCallDuration, overSpeedDuration, mScreenOnKiloMeter, mUseCallKiloMeter, overSpeedKiloMeter, 
																	riskNegativeCount, riskZeroCount, riskSlot1Count, riskSlot2Count, riskSlot3Count, riskSlot4Count, 
																	riskSlot5Count, riskSlot6Count, riskSlot7Count, riskSlot8Count, riskSlot9Count, riskSlot10Count); 
		
		} catch (Exception errMess) {  
			APILog.writeInfoLog(logString + " IOException=> errMess: " + errMess.getMessage());  
		}
		return result;
	}
	
	public void extraUpdateDeviceDataDetailInDevice(DeviceData deviceDataDetail, Timestamp dbOperationStartTime) {

		List<String> emptyList = new ArrayList<String>(); 
		String companyId 	   = deviceDataDetail.getCompanyId();  
		String userId 	  	   = deviceDataDetail.getUserId(); 
		String deviceId        = deviceDataDetail.getDeviceId();   
		String category 	   = deviceDataDetail.getCategory();
 
		float drivingScore = 0;
		float riskValueInt = deviceDataDetail.getRisk();
		Device deviceDetail = this.updateDeviceProfileByDeviceDataProfileAPICall(deviceDataDetail, dbOperationStartTime); 

		try { 
			 
			if (deviceDetail != null && deviceDetail.getId() != null) {
 				drivingScore = deviceDetail.getDrivingScore();
				deviceService.updateDevice(deviceDetail);     
			}
			 
			if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.ALERT_DATA.toString())) {
				if (riskValueInt > 90) {
					serviceFCMNotification.sendUserRideRiskAlertLiveToAdmin(deviceDataDetail);   
				}  
			}   
			
		} catch (Exception errMess) {   
			
			if (category.equalsIgnoreCase(CategoryType.DeviceDataCategory.END_DATA.toString())) {
				
				List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", deviceId, "", "", emptyList, emptyList, "", "", 0, 0);
				if (deviceList.size() > 0) {  
					Device deviceDetailTemp = deviceList.get(0);
					drivingScore = deviceDetailTemp.getDrivingScore();										
				} 
			
				String status 				  = ModelStatus.DeviceStatus.COMPLETED.toString();
				String internalSystemStatus   = ModelStatus.internalSystemStatus.COMPLETED.toString();
				String scoreValidationStatus  = ModelStatus.scoreValidationStatus.FAILED.toString();  
				if (drivingScore > 0) { 
					scoreValidationStatus  = ModelStatus.scoreValidationStatus.SUCCESS.toString(); 
				}	
				String deviceDataInsertStatus = ModelStatus.deviceDataInsertStatus.NOT_ALLOW.toString();  
				deviceService.updateDeviceStatusById(companyId, userId, deviceId, status, internalSystemStatus, scoreValidationStatus, deviceDataInsertStatus);  
			}	
		}
	}
	
	public Device updateDeviceProfileByDeviceDataProfileAPICall(DeviceData deviceDataDetail, Timestamp dbOperationStartTime) {

		Device result = new Device(); 
		String companyId 	  	= deviceDataDetail.getCompanyId(); 
		String userId 	  		= deviceDataDetail.getUserId(); 
		String deviceId       	= deviceDataDetail.getDeviceId(); 
		String dDataCategory 	= deviceDataDetail.getCategory();  
  
		List<String> emptyList  = new ArrayList<String>(); 
		List<String> typeList   = Arrays.asList(DeviceType.SPEEDO_METER_DEVICE.toString());   
		List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", "", "", "", "", "", "", deviceId, "", "", emptyList, typeList, "", "", 0, 0);

		if (deviceList.size() > 0) {   

			String status 		= ModelStatus.internalSystemStatus.IN_PROGRESS.toString(); 
			String insertStatus = ModelStatus.deviceDataInsertStatus.ALLOW.toString(); 

			DeviceDataField deviceDataField 		 = deviceDataDetail.getDeviceDataField();
			DeviceDataAddressField dDataAddressField = deviceDataDetail.getDeviceDataAddressField();

			try {
				
				Device deviceDetail = deviceList.get(0);        
				DeviceField lastDeviceField = deviceDetail.getLastDeviceField();
				String scoreValStatus 		= deviceDetail.getScoreValidationStatus(); 

				/** This will be removed in future - Starts */  	
				String kiloMeter = deviceDataField.getKiloMeter();
		    	try {  
			    	if (kiloMeter.length() <= 0) 
			    		kiloMeter = deviceDataField.getTotalKiloMeter();  
			    	if (kiloMeter.length() <= 0)
				    	kiloMeter = deviceDataField.getAlertKiloMeter();  	 					    	
		    	} catch (Exception errMess) { }
		    	/** This will be removed in future - Ends */  
 
				/** Main Parameters */
			 	String zipCode      = deviceDataField.getZipCode(); 	
				float drivingScoreTemp 	= dataTypeConversion.convertStringToFloat(0, deviceDataField.getDrivingScore()); 

				float drivingScore 	= dataTypeConversion.convertStringToFloat(deviceDetail.getDrivingScore(), deviceDataField.getDrivingScore()); 
				if (dDataCategory.equalsIgnoreCase(CategoryType.DeviceDataCategory.END_DATA.toString())) {
					status 		 = ModelStatus.DeviceStatus.COMPLETED.toString();  
					insertStatus = ModelStatus.deviceDataInsertStatus.NOT_ALLOW.toString(); 
				}

				/** Increment Ride Sequence Number by 1 - Starts */ 
				if (drivingScore > 0 && !scoreValStatus.equalsIgnoreCase(ModelStatus.scoreValidationStatus.SUCCESS.toString())) {
					scoreValStatus = ModelStatus.scoreValidationStatus.SUCCESS.toString();
	 				String type = ModelType.DeviceType.SPEEDO_METER_DEVICE.toString();
					userDistinctService.updateNextDeviceId(companyId, userId, type); // For enabling sequence number for each ride
	 			}	 
				/** Increment Ride Sequence Number by 1 - Ends */

				lastDeviceField.setCity(dataTypeConversion.convertStringToString(lastDeviceField.getCity(), dDataAddressField.getCity()));  			
				lastDeviceField.setState(dataTypeConversion.convertStringToString(lastDeviceField.getState(), dDataAddressField.getState())); 		 	
				lastDeviceField.setCountry(dataTypeConversion.convertStringToString(lastDeviceField.getCountry(), dDataAddressField.getCountry()));    
				lastDeviceField.setZipCode(zipCode);  
	 
				deviceDetail.setDrivingScore(drivingScore);	  
				if (drivingScoreTemp > 0) { 
					deviceDetail.setDrivingSkill(dataTypeConversion.convertStringToFloat(0, deviceDataField.getDrivingSkill()));
					deviceDetail.setAnticipation(dataTypeConversion.convertStringToFloat(0, deviceDataField.getAnticipation())); 
					deviceDetail.setSelfConfidence(dataTypeConversion.convertStringToFloat(0, deviceDataField.getSelfConfidence())); 
				}
				
				deviceDetail.setKiloMeter(dataTypeConversion.convertStringToFloat(deviceDetail.getKiloMeter(), kiloMeter /**deviceDataField.getKiloMeter()*/));
				deviceDetail.setTravelTime(dataTypeConversion.convertStringToFloat(deviceDetail.getTravelTime(), deviceDataField.getTravelTime())); 

				deviceDetail.setUrbanPercent(dataTypeConversion.convertStringToFloat(deviceDetail.getUrbanPercent(), deviceDataField.getUrbanPercent())); 
				deviceDetail.setRuralPercent(dataTypeConversion.convertStringToFloat(deviceDetail.getRuralPercent(), deviceDataField.getRuralPercent()));
				deviceDetail.setHighwayPercent(dataTypeConversion.convertStringToFloat(deviceDetail.getHighwayPercent(), deviceDataField.getHighwayPercent()));
				deviceDetail.setUrbanKilometer(dataTypeConversion.convertStringToFloat(deviceDetail.getUrbanKilometer(), deviceDataField.getUrbanKilometer()));
				deviceDetail.setRuralKilometer(dataTypeConversion.convertStringToFloat(deviceDetail.getRuralKilometer(), deviceDataField.getRuralKilometer()));
				deviceDetail.setHighwayKilometer(dataTypeConversion.convertStringToFloat(deviceDetail.getHighwayKilometer(), deviceDataField.getHighwayKilometer()));

				deviceDetail.setLastLatitude(dataTypeConversion.convertStringToFloat(deviceDetail.getLastLatitude(), deviceDataField.getLatitude()));
				deviceDetail.setLastLongitude(dataTypeConversion.convertStringToFloat(deviceDetail.getLastLongitude(), deviceDataField.getLongitude())); 
				deviceDetail.setLastZipCode(dataTypeConversion.convertStringToString(deviceDetail.getLastZipCode(), zipCode));
				deviceDetail.setLastLocationName(dataTypeConversion.convertStringToString(deviceDetail.getLastLocationName(), dDataAddressField.getLocation())); 
				
				deviceDetail.setDeviceMode(dataTypeConversion.convertStringToString(deviceDetail.getDeviceMode(), deviceDataField.getDeviceMode()));  

				deviceDetail.setStatus(status);     
				deviceDetail.setInternalSystemStatus(status);   
				deviceDetail.setScoreValidationStatus(scoreValStatus); 	
				deviceDetail.setDeviceDataInsertStatus(insertStatus);  
				deviceDetail.setEndDateTime(dbOperationStartTime);  
					  
				return deviceDetail;  
			} catch (Exception errMess) { }
		} 
		return result; 
	}
	 
	public Device updateDeviceProfileByDeviceProfileCSVFile(Device deviceDetail, DeviceData deviceDataDetail) {
  
		try {
			
			// Device Level
			String companyId 	  = deviceDetail.getCompanyId();  
			String userId	 	  = deviceDetail.getUserId();		  
			String scoreValStatus = ModelStatus.scoreValidationStatus.FAILED.toString();    
			DeviceField lastDeviceField = deviceDetail.getLastDeviceField(); 
			
			// Device Data Level
			String dDataCategory = deviceDataDetail.getCategory(); 
			DeviceDataField deviceDataField = deviceDataDetail.getDeviceDataField();
			DeviceDataRiskField dDataRiskField = deviceDataDetail.getDeviceDataRiskField();
			DeviceDataAddressField dDataAddressField = deviceDataDetail.getDeviceDataAddressField(); 

			String status 		= ModelStatus.internalSystemStatus.IN_PROGRESS.toString(); 
			String zipCode 		= dataTypeConversion.convertStringToString(lastDeviceField.getZipCode(), deviceDataField.getZipCode());   
			float drivingScore 	= dataTypeConversion.convertStringToFloat(deviceDetail.getDrivingScore(), deviceDataField.getDrivingScore()); 
 			
			if (dDataCategory.equalsIgnoreCase(CategoryType.DeviceDataCategory.END_DATA.toString()))
				status = ModelStatus.DeviceStatus.COMPLETED.toString();  

			/** Increment Ride Sequence Number by 1 - Starts */
			if (drivingScore > 0) {
				if (!scoreValStatus.equalsIgnoreCase(ModelStatus.scoreValidationStatus.SUCCESS.toString())) { 
					scoreValStatus = ModelStatus.scoreValidationStatus.SUCCESS.toString();
					String type = ModelType.DeviceType.SPEEDO_METER_DEVICE.toString();
					userDistinctService.updateNextDeviceId(companyId, userId, type); // For enabling sequence number for each ride
				}
			}
			/** Increment Ride Sequence Number by 1 - Ends */
  			  
			lastDeviceField.setCity(dataTypeConversion.convertStringToString(lastDeviceField.getCity(), dDataAddressField.getCity()));  			
			lastDeviceField.setState(dataTypeConversion.convertStringToString(lastDeviceField.getState(), dDataAddressField.getState())); 		 	
			lastDeviceField.setCountry(dataTypeConversion.convertStringToString(lastDeviceField.getCountry(), dDataAddressField.getCountry()));    
			lastDeviceField.setZipCode(zipCode);  
			
			deviceDetail.setDrivingScore(drivingScore);	 
			float drivingScoreTemp 	= dataTypeConversion.convertStringToFloat(0, deviceDataField.getDrivingScore()); 
			if (drivingScoreTemp > 0) { 
				deviceDetail.setDrivingSkill(dataTypeConversion.convertStringToFloat(0, deviceDataField.getDrivingSkill()));
				deviceDetail.setAnticipation(dataTypeConversion.convertStringToFloat(0, deviceDataField.getAnticipation())); 
				deviceDetail.setSelfConfidence(dataTypeConversion.convertStringToFloat(0, deviceDataField.getSelfConfidence())); 
			}
			
		 	deviceDetail.setKiloMeter(dataTypeConversion.convertStringToFloat(deviceDetail.getKiloMeter(), deviceDataField.getKiloMeter()));
			deviceDetail.setTravelTime(dataTypeConversion.convertStringToFloat(deviceDetail.getTravelTime(), deviceDataField.getTravelTime())); 
			
			deviceDetail.setUrbanPercent(dataTypeConversion.convertStringToFloat(deviceDetail.getUrbanPercent(), deviceDataField.getUrbanPercent())); 
			deviceDetail.setRuralPercent(dataTypeConversion.convertStringToFloat(deviceDetail.getRuralPercent(), deviceDataField.getRuralPercent()));
			deviceDetail.setHighwayPercent(dataTypeConversion.convertStringToFloat(deviceDetail.getHighwayPercent(), deviceDataField.getHighwayPercent()));
 			deviceDetail.setUrbanKilometer(dataTypeConversion.convertStringToFloat(deviceDetail.getUrbanKilometer(), deviceDataField.getUrbanKilometer()));
			deviceDetail.setRuralKilometer(dataTypeConversion.convertStringToFloat(deviceDetail.getRuralKilometer(), deviceDataField.getRuralKilometer()));
			deviceDetail.setHighwayKilometer(dataTypeConversion.convertStringToFloat(deviceDetail.getHighwayKilometer(), deviceDataField.getHighwayKilometer()));
			
			deviceDetail.setLastLatitude(dataTypeConversion.convertStringToFloat(deviceDetail.getLastLatitude(), deviceDataField.getLatitude()));
			deviceDetail.setLastLongitude(dataTypeConversion.convertStringToFloat(deviceDetail.getLastLongitude(), deviceDataField.getLongitude())); 
			deviceDetail.setLastZipCode(dataTypeConversion.convertStringToString(deviceDetail.getLastZipCode(), zipCode));
			deviceDetail.setLastLocationName(dataTypeConversion.convertStringToString(deviceDetail.getLastLocationName(), dDataAddressField.getLocation()));
			
			deviceDetail.setRiskNegativeCount(dDataRiskField.getSlotNegativeCount()); 
			deviceDetail.setRiskZeroCount(dDataRiskField.getSlotZeroCount());
			deviceDetail.setRiskSlot1Count(dDataRiskField.getSlot1Count());    
			deviceDetail.setRiskSlot2Count(dDataRiskField.getSlot2Count());   
			deviceDetail.setRiskSlot3Count(dDataRiskField.getSlot3Count());    
			deviceDetail.setRiskSlot4Count(dDataRiskField.getSlot4Count());   
			deviceDetail.setRiskSlot5Count(dDataRiskField.getSlot5Count());   
			deviceDetail.setRiskSlot6Count(dDataRiskField.getSlot6Count());   
			deviceDetail.setRiskSlot7Count(dDataRiskField.getSlot7Count());   
			deviceDetail.setRiskSlot8Count(dDataRiskField.getSlot8Count());   
			deviceDetail.setRiskSlot9Count(dDataRiskField.getSlot9Count());   
			deviceDetail.setRiskSlot10Count(dDataRiskField.getSlot10Count());  
			
			deviceDetail.setStatus(status);     
			deviceDetail.setInternalSystemStatus(status);   
			deviceDetail.setScoreValidationStatus(scoreValStatus); 	
			deviceDetail.setDeviceDataInsertStatus(ModelStatus.deviceDataInsertStatus.NOT_ALLOW.toString());  
			  
		} catch (Exception errMess) {  
			
		} 
		return deviceDetail; 
	}
	
}