package com.common.api.resource;
 
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.common.api.datasink.service.QueryService;
import com.common.api.datasink.service.UserDistinctService;
import com.common.api.model.Device;
import com.common.api.model.field.DeviceField;
import com.common.api.model.status.ModelStatus;
import com.common.api.model.type.InsertModeType;
import com.common.api.response.APIGeneralSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileCardinalityAccess;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileAutoSystematicService;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileDistinctService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Device", tags = {"Device"}) 
@RestController            
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"}) 
public class DeviceResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {         
		return new PropertySourcesPlaceholderConfigurer();     
	}
 
	@Autowired 
	FieldValidator fieldValidator;
	@Autowired 
	PayloadValidator payloadValidator;  
	@Autowired     
	ProfileSessionRoleService profileSessionRoleService;    
	@Autowired 
	ProfileDependencyService profileDependencyService; 
	@Autowired 
	ProfileCardinalityService profileCardinalityService; 

    @Autowired 
    QueryService queryService;   
	@Autowired
	DeviceDataResource deviceDataResource;
    @Autowired 
    DeviceService deviceService; 
    @Autowired 
    DeviceDataService deviceDataService;  
    @Autowired 
    ProfileDistinctService profileDistinctService;  
    @Autowired 
    UserDistinctService userDistinctService; 
	@Autowired 
	ProfileAutoSystematicService profileAutoSystamaticService;  

   	@Value("${app.sp.device.summary.count.update}")       
   	String spDeviceSummaryCountUpdate = "";  
   	
   	@Value("${s001.request.successful}")       
   	String s001RequestSuccessful = "";  
   	 
	@Value("${e006.failed.dependency}")       
	String e006FailedDependency = "";   
	@Value("${e003.profile.not.exist}")       
	String e003ProfileNotExist = ""; 
	 
	@Value("${app.device.name.with.suffix.number}")       
	String deviceNameWithSuffixNumber = ""; 
	
	@ApiOperation(value = "View Devoice Details", 
				  nickname = "DeviceDetailView", 
				  notes = "View Device Details") 
	@ApiResponses(value = {  
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = Device.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
      })
	@RequestMapping(value = "/device", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewDeviceDetail(HttpServletRequest httpRequest,   
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = false) String groupId, 
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = false) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = false) String sectionId,
			@RequestParam(name = "portionId", defaultValue = "", required = false) String portionId,
 			@RequestParam(name = "id", defaultValue = "", required = false) String id,     
			@RequestParam(name = "uniqueCode", defaultValue = "", required = false) String uniqueCode, 
			@RequestParam(name = "category", defaultValue = "", required = false) String category,
			@RequestParam(name = "status", defaultValue = "", required = false) String status,         
			@RequestParam(name = "type", defaultValue = "DEVICE", required = true) String type,  
 			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,   
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,      
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) {    
		
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();  
		String apiLogString     = "LOG_API_DEVICE_VIEW";        
    	String apiInstLogString = "LOG_INST_DEVICE_VIEW";     
    	   
		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder; 
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;     
    	type	  = (type.length() <= 0) ? APIResourceName.DEVICE.toString() : type;
    	String resourceType = APIResourceName.DEVICE.toString();                    
 
    	List<String> statusList = Util.convertIntoArrayString(status);  
    	List<String> typeList   = Util.convertIntoArrayString(type);  

    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (FieldValidator.isValidId(companyId) == false)        
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (FieldValidator.isValidIdOptional(groupId) == false)         
			errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID));  
		if (FieldValidator.isValidId(userId) == false)    
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID)); 
		if (FieldValidator.isValidIdOptional(propertyId) == false)        
			errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_PROPERTY_ID));  
		if (FieldValidator.isValidIdOptional(id) == false)          
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));  
		if (FieldValidator.isValidInternalTextOptional(status) == false)         
			errorList.add(new APIPreConditionErrorField("status", EM_INVALID_STATUS));  
		if (FieldValidator.isValidInternalTextOptional(type) == false)        
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE)); 
		          
  		if (errorList.size() == 0) { 
  			  
  			String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, moduleId);
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage(); 
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {      
			   
				List<Device> succPayload = deviceService.viewListByCriteria(companyId, groupId, divisionId, moduleId, userId, propertyId, sectionId, portionId, id, uniqueCode, category, statusList, typeList, sortBy, sortOrder, offset, limit);  
				 
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
	@ApiOperation(value = "Create Device Details", 
				  nickname = "DeviceDetailCreate",  
				  notes = "Create Device Details")   
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = Device.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/device", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createDeviceDetail(HttpServletRequest httpRequest,    
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,  
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestBody Device deviceReq) {   
		
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();   
		
		String apiLogString     = "LOG_API_DEVICE_CREATE";          
    	String apiInstLogString = "LOG_INST_DEVICE_CREATE";        
    	
    	String companyId 	= deviceReq.getCompanyId(); 
    	String userId 	    = deviceReq.getUserId(); 
    	Date createdAtReq	= deviceReq.getCreatedAt();   
    	String type			= deviceReq.getType();      
    	String zipCode		= deviceReq.getZipCode();    
    	type	  			= (type.length() <= 0) ? APIResourceName.DEVICE.toString() : type;  
    	String resourceType = APIResourceName.DEVICE.toString(); 
    	DeviceField deviceField	= deviceReq.getDeviceField();                     

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Device>> validationErrors = validator.validate(deviceReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidDevicePayload(GC_METHOD_POST, deviceReq));   
    	for (ConstraintViolation<Device> error : validationErrors)  
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage())); 
    	
    	/** This will be removed in future - Starts */ 
    	try { 
	    	if (zipCode.length() <= 0) {
	    		zipCode = deviceField.getZipCode();   
	    		deviceReq.setZipCode(zipCode); 
	    	} 
    	} catch (Exception errMess) { }
    	/** This will be removed in future - Ends */
    	
    	/** Existing records status updates - Starts */
    	/**
    	List<String> emptyList 	= Arrays.asList();
    	List<String> statusList = Arrays.asList(ModelStatus.DeviceStatus.REGISTERED.toString(), 
    											ModelStatus.DeviceStatus.IN_PROGRESS.toString()); 
    	List<String> typeList 	= Arrays.asList(ModelType.DeviceType.SPEEDO_METER_DEVICE.toString()); 
    	List<Device> deviceList = deviceService.viewListByDeviceId(companyId, divisionId, moduleId, userId, "", emptyList, statusList, typeList, "", "", 0, 0);	
    	
    	for (int dlc = 0; dlc < deviceList.size(); dlc++) {
    		
    		Device deviceInfo 	= deviceList.get(dlc);
    		
    		String deviceId 			  = deviceInfo.getId();
    		float drivingScore 			  = deviceInfo.getDrivingScore(); 
    		String internalSystemStatus	  = deviceInfo.getInternalSystemStatus();     	
			String scoreValidationStatus  = ModelStatus.scoreValidationStatus.FAILED.toString();  
			if (drivingScore > 0) {
				scoreValidationStatus  = ModelStatus.scoreValidationStatus.SUCCESS.toString(); 
			}
    		String status 				  = ModelStatus.DeviceStatus.COMPLETED.toString(); 
			String deviceDataInsertStatus = ModelStatus.deviceDataInsertStatus.NOT_ALLOW.toString();
			deviceService.updateDeviceStatusById(companyId, userId, deviceId, status, internalSystemStatus, scoreValidationStatus, deviceDataInsertStatus);
    	} **/
    	/** Existing records status updates - Ends */

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
				
				ProfileDependencyAccess profileDependency = profileDependencyService.deviceProfileDependencyStatus(GC_METHOD_POST, deviceReq); 
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage(); 
						
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {
				 
					ProfileCardinalityAccess profileCardinality = profileCardinalityService.deviceProfileCardinalityStatus(GC_METHOD_POST, deviceReq); 
					String profileCardinalityCode = profileCardinality.getCode();
					String profileCardinalityMess = profileCardinality.getMessage();
					
					if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) { 

				    	if (deviceNameWithSuffixNumber.equalsIgnoreCase(GC_ACTION_YES)) {  
				    		long nextDeviceId = profileDistinctService.getNextDeviceId(deviceReq); 
							try {
					    		//String deviceName = deviceReq.getName() + ' ' + nextDeviceId;    
								String deviceName = "Ride " + nextDeviceId;    
								deviceReq.setName(deviceName);   
							} catch (Exception errMess) { }
						}
				    	
				    	deviceReq.setInsertMode(InsertModeType.API_CALL.toString());
				    	deviceReq.setInternalSystemStatus(ModelStatus.internalSystemStatus.REGISTERED.toString());
				    	deviceReq.setDeviceDataInsertStatus(ModelStatus.deviceDataInsertStatus.ALLOW.toString());  
				    	deviceReq.setScoreValidationStatus(ModelStatus.scoreValidationStatus.PENDING.toString()); 
				    	deviceReq.setOrigin(referredBy);   
				    	deviceReq.setDeviceMode(deviceDataResource.extraUpdateDeviceMode(deviceReq.getDeviceMode())); 
				    	deviceReq.setStartDateTime(createdAt);
				    	deviceReq.setEndDateTime(createdAt);
						deviceReq.setCreatedAt(createdAt);	
						deviceReq.setModifiedAt(dbOperationStartTime);	  
						deviceReq.setCreatedBy(sessionUserId);		
						deviceReq.setModifiedBy(sessionUserId);
						deviceReq.setInsertedAt(dbOperationStartTime);	
			  			
						Device succPayload = deviceService.createDevice(deviceReq);
			  			
			  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {
			  			
			  				String deviceId = succPayload.getId();
			  				profileAutoSystamaticService.createDeviceSummaryByDevice(deviceReq); 
			  				try {
			  					if (spDeviceSummaryCountUpdate.length() > 0) 
			  						queryService.updateByCriteria(spDeviceSummaryCountUpdate, companyId, "", "", "", userId, "", "", "", "", deviceId, GC_ACTION_UPDATE); 
			  				} catch (Exception errMess) { }
			  				
							APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
							APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
							return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);  
			  			}   
						return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));  
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
	@ApiOperation(value = "Update Device Details", 
				  nickname = "UpdateDeviceDetailCreate",  
				  notes = "Update Device Details")     
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = Device.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/device", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> updateDeviceDetail(HttpServletRequest httpRequest,    
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,  
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestBody Device deviceReq) {    
 
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();   
		
		String apiLogString     = "LOG_API_DEVICE_UPDATE";    
    	String apiInstLogString = "LOG_INST_DEVICE_UPDATE";     
  
    	String companyId 	= deviceReq.getCompanyId();          
    	String id			= deviceReq.getId();
    	String type			= deviceReq.getType();      
    	type	  			= (type.length() <= 0) ? APIResourceName.DEVICE.toString() : type; 
    	String resourceType = APIResourceName.DEVICE.toString();                    

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Device>> validationErrors = validator.validate(deviceReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidDevicePayload(GC_METHOD_PUT, deviceReq));   
    	for (ConstraintViolation<Device> error : validationErrors) 
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage())); 
    	
  		if (errorList.size() == 0) {      

  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage();  
			String sessionUserId  = roleStatus.getUserId(); 
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.deviceProfileDependencyStatus(GC_METHOD_PUT, deviceReq); 
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();  
						  
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {  
				 
					List<String> emptyList = new ArrayList<String>();   
					List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", "", "", "", "", "", "", id, "", "", emptyList, emptyList, "", "", 0, 0);
				    
					if (deviceList.size() > 0) {     
	
						Device deviceDetail = deviceList.get(0);  
						
						deviceDetail.setCompanyId(deviceReq.getCompanyId());
						deviceDetail.setName(deviceReq.getName());
						deviceDetail.setUniqueCode(deviceReq.getUniqueCode()); 
						deviceDetail.setDeviceField(deviceReq.getDeviceField()); 
						deviceDetail.setModifiedBy(sessionUserId);
						deviceDetail.setModifiedAt(dbOperationStartTime);    
			  			
						Device succPayload = deviceService.updateDevice(deviceDetail); 
			  			
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
	
	@ApiOperation(value = "Remove Device Details", 
			  nickname = "DeviceDetailRemove", 
			  notes = "Remove Device Details") 
	@ApiResponses(value = {  
	  @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = APIGeneralSuccess.class),
	  @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class), 
	  @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	  @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class), 
	  @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
	}) 
	@RequestMapping(value = "/device", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeDeviceDetail(HttpServletRequest httpRequest,   
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId, 
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId, 
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = false) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = false) String sectionId,
			@RequestParam(name = "portionId", defaultValue = "", required = false) String portionId,
 			@RequestParam(name = "id", defaultValue = "", required = true) String id) {   
		
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();  
		String apiLogString     = "LOG_API_DEVICE_REMOVE";        
		String apiInstLogString = "LOG_INST_DEVICE_REMOVE";     
    	String resourceType 	= APIResourceName.DEVICE.toString();                    
		    
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();  
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (FieldValidator.isValidId(companyId) == false)        
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID)); 
		if (FieldValidator.isValidId(userId) == false)    
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID)); 
		if (FieldValidator.isValidIdOptional(propertyId) == false)         
			errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_PROPERTY_ID));   
		if (FieldValidator.isValidIdOptional(sectionId) == false)         
			errorList.add(new APIPreConditionErrorField("sectionId", EM_INVALID_SECTION_ID));   
		if (FieldValidator.isValidIdOptional(portionId) == false)         
			errorList.add(new APIPreConditionErrorField("portionId", EM_INVALID_PORTION_ID));   
		if (FieldValidator.isValidId(id) == false)         
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		          
		if (errorList.size() == 0) { 
			  
			List<String> emptyList 	= new ArrayList<String>();  
			List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, portionId, id, "", "", emptyList, emptyList, "", "", 0, 0);  
			 
	  		if (deviceList.size() > 0) {  
	  			
	  			String type = deviceList.get(0).getType();  

				String rolePrivilegeType = type + APIRolePrivilegeType._DELETE.toString();  
				ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, moduleId);
				String roleStatucCode = roleStatus.getCode();			 
				String roleStatucMess = roleStatus.getMessage(); 
				 
				if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {    
					
					int results = deviceService.removeDeviceById(companyId, userId, id);
					
					if (results > 0) {
						
		  				APIGeneralSuccess succPayload = new APIGeneralSuccess(SC_001_REQUEST_SUCCESSFUL, s001RequestSuccessful); 
		  				  
						APILog.writeInfoLog(apiLogString + " " + succPayload.toString());    
						APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
						return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(succPayload);      
						 
					} 
		  			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));

				} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) {   
					return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));  
				} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, roleStatucMess));
				}   		
			}  
	  		return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_003_PROFILE_NOT_EXIST, e003ProfileNotExist));    
		}  
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));  
	} 

}
