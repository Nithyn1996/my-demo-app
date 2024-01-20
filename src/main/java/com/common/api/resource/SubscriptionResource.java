package com.common.api.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.common.api.datasink.service.SubscriptionService;
import com.common.api.model.Subscription;
import com.common.api.response.APIGeneralSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileAvailabilityAccess;
import com.common.api.response.ProfileCardinalityAccess;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileAvailabilityService;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
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

@Api(value = "Subscription", tags = {"Subscription"}) 
@RestController        
public class SubscriptionResource  extends APIFixedConstant {
	
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
	ProfileAvailabilityService profileAvailabilityService; 
    
    @Autowired
    SubscriptionService subscriptionService;
    
    @Value("${e006.failed.dependency}")       
	String e006FailedDependency = "";
    
    @Value("${e003.profile.not.exist}")
	String e003ProfileNotExist = "";
    
	@Value("${s001.request.successful}")       
   	String s001RequestSuccessful = "";
    
	@ApiOperation(value = "View Subscription Details",
			  nickname = "SubscriptionDetailView",
			  notes = "View Subscription Details")
	@ApiResponses(value = {
		@ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = Subscription.class, responseContainer = "List"),
		@ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class), 
		@ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
		@ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class), 
		@ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
	})
	@RequestMapping(value = "/subscription", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewSubscriptionDetail (HttpServletRequest httpRequest,   
		@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
		@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
		@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
		@RequestParam(name = "groupId", defaultValue = "", required = true) String groupId,
		@RequestParam(name = "divisionId", defaultValue = "", required = true) String divisionId, 
		@RequestParam(name = "id", defaultValue = "", required = false) String id,  
		@RequestParam(name = "name", defaultValue = "", required = false) String name,  
		@RequestParam(name = "status", defaultValue = "", required = false) String status,       
		@RequestParam(name = "type", defaultValue = "SUBSCRIPTION", required = true) String type,   
		@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,  
		@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
		@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,      
		@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) { 
	
	Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();  
	String apiLogString     = "LOG_API_SUBSCRIPTION_VIEW";         
	String apiInstLogString = "LOG_INST_SUBSCRIPTION_VIEW";       
	 
	sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;     
	type	  = (type.length() <= 0) ? APIResourceName.SUBSCRIPTION.toString() : type; 
	String resourceType 	= APIResourceName.SUBSCRIPTION.toString();     

	List<String> statusList = Util.convertIntoArrayString(status); 
	List<String> typeList   = Util.convertIntoArrayString(type); 

	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
	errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
	if (FieldValidator.isValidId(companyId) == false)        
		errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID)); 
	if (FieldValidator.isValidIdOptional(groupId) == false)         
		errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID)); 
	if (FieldValidator.isValidId(divisionId) == false)         
		errorList.add(new APIPreConditionErrorField("divisionId", EM_INVALID_DIVI_ID));
	if (FieldValidator.isValidIdOptional(id) == false)        
		errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));  
	if (FieldValidator.isValidInternalTextOptional(status) == false)         
		errorList.add(new APIPreConditionErrorField("status", EM_INVALID_STATUS));  
	if (FieldValidator.isValidInternalTextOptional(type) == false)        
		errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE)); 
	
	if (errorList.size() == 0) {

		String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();  
		ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, "");
		String roleStatucCode = roleStatus.getCode();		
		String roleStatucMess = roleStatus.getMessage();

		if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {        

			List<Subscription> succPayload = subscriptionService.viewListByCriteria(companyId, groupId, divisionId, id, name, statusList, typeList, sortBy, sortOrder, offset, limit);  
			 
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
	@ApiOperation(value = "Create Subscription Details", 
				  nickname = "SubscriptionDetailCreate",  
				  notes = "Create Subscription Details")  
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = Subscription.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/subscription", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createSubscriptionDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy",defaultValue = "", required = true)String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true)String sessionId,
			@RequestBody Subscription subscriptionReq){
		
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();
		Timestamp subscriptionEndTime  = APIDateTime.convertToAnotherDateTime(GC_ACTION_PLUS, 1, 0, 0, 0, 0, 0);
		
		String apiLogString     = "LOG_API_SUBSCRIPTION_CREATE";          
    	String apiInstLogString = "LOG_INST_SUBSCRIPTION_CREATE"; 
    	
    	String companyId  = subscriptionReq.getCompanyId();
    	String divisionId = subscriptionReq.getDivisionId();
    	String type       = subscriptionReq.getType();
    	type = (type.length() <= 0) ? APIResourceName.SUBSCRIPTION.toString() : type; 
    	String resourceType = APIResourceName.SUBSCRIPTION.toString();
    	 
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator(); 
		Set<ConstraintViolation<Subscription>> validationErrors = validator.validate(subscriptionReq);
		
    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
    	errorList.addAll(payloadValidator.isValidSubscriptionPayload(GC_METHOD_POST, subscriptionReq));

		for (ConstraintViolation<Subscription> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

		if (errorList.size() == 0) {

			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, "");
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage();  
			String sessionUserId  = roleStatus.getUserId(); 
			
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {
				
				ProfileDependencyAccess profileDependency = profileDependencyService.subscriptionProfileDependencyStatus(GC_METHOD_POST, subscriptionReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();
				
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {
					
					ProfileCardinalityAccess profileCardinality = profileCardinalityService.subscriptionProfileCardinalityStatus(GC_METHOD_POST, subscriptionReq);
					String profileCardinalityCode = profileCardinality.getCode();
					String profileCardinalityMess = profileCardinality.getMessage();
					
					if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {
						
						ProfileAvailabilityAccess profileAvailability = profileAvailabilityService.subscriptionProfileAvailabilityStatus(GC_METHOD_POST, subscriptionReq);
						String profileAvailabilityCode = profileAvailability.getCode();
						String profileAvailabilityMess = profileAvailability.getMessage();
						
						if (profileAvailabilityCode.equals(GC_STATUS_SUCCESS)) {
							
							subscriptionReq.setCreatedAt(dbOperationStartTime);
							subscriptionReq.setModifiedAt(dbOperationStartTime);
							subscriptionReq.setCreatedBy(sessionUserId);
							subscriptionReq.setModifiedBy(sessionUserId);
							subscriptionReq.setStartTime(dbOperationStartTime);
							subscriptionReq.setEndTime(subscriptionEndTime);
							
							Subscription succPayload = subscriptionService.createSubscription(subscriptionReq);
							
							if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {
								
								APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
								APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
								return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload); 
							}
							return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency)); 
						}
						return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(new APIRunTimeError(EC_016_ALREADY_EXIST_CONFLICT, profileAvailabilityMess));
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
	@ApiOperation(value = "Update Subscription Details",
				  nickname = "SubscriptionDetailUpdate",
				  notes = "Update Subscription Details")
	@ApiResponses(value = {
		@ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = Subscription.class),
		@ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
		@ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
		@ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
		@ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
	})
	@RequestMapping(value = "/subscription", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSubscriptionDetail(HttpServletRequest httpRequest,
			@RequestHeader(value="referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value="sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody Subscription subscriptionReq){
		
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		
		String apiLogString		= "LOG_API_SUBSCRIPTION_UPDATE";
		String apiInstLogString = "LOG_INST_SUBSCRIPTION_UPDATE";
		
		String id 		 	= subscriptionReq.getId();
		String companyId 	= subscriptionReq.getCompanyId();
		String type			= subscriptionReq.getType();
		type				= (type.length() <= 0) ? APIResourceName.SUBSCRIPTION.toString() : type;
		String resourceType	= APIResourceName.SUBSCRIPTION.toString();
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Subscription>> validationErrors = validator.validate(subscriptionReq);
		
		List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy,sessionId);
		errorList.addAll(payloadValidator.isValidSubscriptionPayload(GC_METHOD_PUT, subscriptionReq));
		for(ConstraintViolation<Subscription> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));
		
		if (errorList.size() == 0) {
			
  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage();  
			String sessionUserId  = roleStatus.getUserId(); 
			
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {  
				ProfileDependencyAccess profileDependency = profileDependencyService.subscriptionProfileDependencyStatus(GC_METHOD_PUT, subscriptionReq); 
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();
				
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {				

					ProfileAvailabilityAccess profileAvailability = profileAvailabilityService.subscriptionProfileAvailabilityStatus(GC_METHOD_PUT, subscriptionReq);
					String profileAvailabilityCode = profileAvailability.getCode();
					String profileAvailabilityMess = profileAvailability.getMessage();
					
					if (profileAvailabilityCode.equals(GC_STATUS_SUCCESS)) {
					
						List<String> emptyList = new ArrayList<String>(); 					
						List<Subscription> subscriptionList = subscriptionService.viewListByCriteria(companyId, "", "", id, "", emptyList, emptyList, "", "", 0, 0);
					
						if (subscriptionList.size() > 0) {
						
							Subscription subscriptionDetail = subscriptionList.get(0);						
				        
							subscriptionDetail.setCompanyId(subscriptionReq.getCompanyId());
							subscriptionDetail.setName(subscriptionReq.getName());
							subscriptionDetail.setOrderCount(subscriptionReq.getOrderCount());
							subscriptionDetail.setIosLicenseCount(subscriptionReq.getIosLicenseCount());
							subscriptionDetail.setAndroidLicenseCount(subscriptionReq.getAndroidLicenseCount());						
							subscriptionDetail.setAvailableLicenseCount(subscriptionReq.getAvailableLicenseCount());
							subscriptionDetail.setModifiedAt(dbOperationStartTime);
							subscriptionDetail.setModifiedBy(sessionUserId);
							
							Subscription succPayload = subscriptionService.updateSubscription(subscriptionDetail);
							
							if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {
								APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
								APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
								return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(succPayload); 
							}
							return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));  
						}
						return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_003_PROFILE_NOT_EXIST, e003ProfileNotExist));
					}
					return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(new APIRunTimeError(EC_016_ALREADY_EXIST_CONFLICT,profileAvailabilityMess));
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
	
	@ApiOperation(value = "Remove Subscription Details",
			  nickname = "SubscriptionRemove",
			  notes = "Remove Subscription Details")
	@ApiResponses(value = {  
	  @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = APIGeneralSuccess.class),
	  @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class), 
	  @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	  @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class), 
	  @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
	})
	@RequestMapping(value = "/subscription", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeSubscriptionDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "divisionId", defaultValue = "", required = true) String divisionId,
			@RequestParam(name = "id", defaultValue = "", required = true) String id){
		
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_SUBSCRIPTION_REMOVE"; 
		String apiInstLogString = "LOG_INST_SUBSCRIPTION_REMOVE"; 
		String resourceType 	= APIResourceName.SUBSCRIPTION.toString();
		
		List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (FieldValidator.isValidId(companyId) == false)
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (FieldValidator.isValidId(divisionId) == false)
			errorList.add(new APIPreConditionErrorField("divisionId", EM_INVALID_DIVI_ID));
		if (FieldValidator.isValidId(id) == false)
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		
		if (errorList.size() == 0) {
			
			List<String> emptyList = new ArrayList<String>();
			List<Subscription> subscriptionList = subscriptionService.viewListByCriteria(companyId, "", divisionId, id, "", emptyList, emptyList, "", "", 0, 0);

			if (subscriptionList.size() > 0) {
				
				String type = subscriptionList.get(0).getType();
				
				String rolePrivilegeType = type + APIRolePrivilegeType._DELETE.toString();
				ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, "");
				String roleStatucCode = roleStatus.getCode();			 
				String roleStatucMess = roleStatus.getMessage(); 

				if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {
					
					int results = subscriptionService.removeSubscriptionById(companyId, divisionId, id);
						
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
