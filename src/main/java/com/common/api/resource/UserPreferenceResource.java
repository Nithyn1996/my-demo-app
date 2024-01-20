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
import com.common.api.datasink.service.UserPreferenceService;
import com.common.api.model.UserPreference;
import com.common.api.model.type.NotificationModelType;
import com.common.api.response.APIGeneralSuccess;
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
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Preference", tags = {"User Preference"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class UserPreferenceResource extends APIFixedConstant {

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
    UserPreferenceService userPreferenceService;
    @Autowired
    ServiceFCMNotification serviceFCMNotification;

    @Value("${s001.request.successful}")
   	String s001RequestSuccessful = "";
    @Value("${e003.profile.not.exist}")
	String e003ProfileNotExist = "";
	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";

	@ApiOperation(value = "View User Preference Details",
				  nickname = "UserPreferenceDetailView",
				  notes = "View User Preference Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = UserPreference.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/userPreference", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewUserPreferenceDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "id", defaultValue = "", required = false) String id,
			@RequestParam(name = "name", defaultValue = "", required = false) String name,
			@RequestParam(name = "code", defaultValue = "", required = false) String code,
			@RequestParam(name = "category", defaultValue = "", required = false) String category,
			@RequestParam(name = "subCategory", defaultValue = "", required = false) String subCategory,
			@RequestParam(name = "status", defaultValue = "", required = false) String status,
			@RequestParam(name = "type", defaultValue = "USER_PREFERENCE", required = true) String type,
 			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) {

		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_USER_PREFERENCE_VIEW";
    	String apiInstLogString = "LOG_INST_USER_PREFERENCE_VIEW";

		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;
    	type	  = (type.length() <= 0) ? APIResourceName.USER_PREFERENCE.toString() : type;
    	String resourceType 	= APIResourceName.USER_PREFERENCE.toString();

    	List<String> statusList = Util.convertIntoArrayString(status);
    	List<String> typeList   = Util.convertIntoArrayString(type);
    	List<String> categoryList   	= Util.convertIntoArrayString(category);
    	List<String> subCategoryList 	= Util.convertIntoArrayString(subCategory);

    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (!FieldValidator.isValidId(userId))
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
		if (!FieldValidator.isValidIdOptional(id))
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		if (!FieldValidator.isValidInternalTextOptional(status))
			errorList.add(new APIPreConditionErrorField("status", EM_INVALID_STATUS));
		if (!FieldValidator.isValidInternalTextOptional(type))
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, moduleId);
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				List<UserPreference> succPayload = userPreferenceService.viewListByCriteria(companyId, divisionId, moduleId, userId, id, name, code, categoryList, subCategoryList, statusList, typeList, sortBy, sortOrder, offset, limit);

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
	@ApiOperation(value = "Create User Preference Details",
				  nickname = "UserPreferenceDetailCreate",
				  notes = "Create User Preference Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = UserPreference.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/userPreference", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createUserPreferenceDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody UserPreference userPreferenceReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_PREFERENCE_CREATE";
    	String apiInstLogString = "LOG_INST_USER_PREFERENCE_CREATE";

    	String companyId 	= userPreferenceReq.getCompanyId();
    	String divisionId 	= userPreferenceReq.getDivisionId();
    	String userId	 	= userPreferenceReq.getUserId();
    	String type			= userPreferenceReq.getType();
    	type	  			= (type.length() <= 0) ? APIResourceName.USER_PREFERENCE.toString() : type;
    	String resourceType 	= APIResourceName.USER_PREFERENCE.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<UserPreference>> validationErrors = validator.validate(userPreferenceReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidUserPreferencePayload(GC_METHOD_POST, userPreferenceReq));
 		for (ConstraintViolation<UserPreference> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();
			String sessionUserId  = roleStatus.getUserId();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.userPreferenceProfileDependencyStatus(GC_METHOD_POST, userPreferenceReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					ProfileCardinalityAccess profileCardinality = profileCardinalityService.userPreferenceProfileCardinalityStatus(GC_METHOD_POST, userPreferenceReq);
					String profileCardinalityCode = profileCardinality.getCode();
					String profileCardinalityMess = profileCardinality.getMessage();

					if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {

						userPreferenceReq.setCreatedAt(dbOperationStartTime);
			  			userPreferenceReq.setModifiedAt(dbOperationStartTime);
			  			userPreferenceReq.setCreatedBy(sessionUserId);
			  			userPreferenceReq.setModifiedBy(sessionUserId);

			  			UserPreference succPayload = userPreferenceService.createUserPreference(userPreferenceReq);

			  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

			  				if (type.equalsIgnoreCase(NotificationModelType.pushType.USER_APP_BOOT_SETTING.toString())) {
			  					serviceFCMNotification.sendUserAppBootSettingAlertToUser(companyId, divisionId, userId);
			  				}

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
	@ApiOperation(value = "Update User Preference Details",
				  nickname = "UpdatePreferenceDetailCreate",
				  notes = "Update User Preference Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = UserPreference.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/userPreference", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> updateUserPreferenceDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody UserPreference userPreferenceReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_PREFERENCE_UPDATE";
    	String apiInstLogString = "LOG_INST_USER_PREFERENCE_UPDATE";

    	String id			= userPreferenceReq.getId();
    	String companyId 	= userPreferenceReq.getCompanyId();
    	String divisionId 	= userPreferenceReq.getDivisionId();
    	String userId	 	= userPreferenceReq.getUserId();
    	String type			= userPreferenceReq.getType();
     	type	  			= (type.length() <= 0) ? APIResourceName.USER_PREFERENCE.toString() : type;
    	String resourceType 	= APIResourceName.USER_PREFERENCE.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<UserPreference>> validationErrors = validator.validate(userPreferenceReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidUserPreferencePayload(GC_METHOD_PUT, userPreferenceReq));
 		for (ConstraintViolation<UserPreference> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();
			String sessionUserId  = roleStatus.getUserId();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.userPreferenceProfileDependencyStatus(GC_METHOD_PUT, userPreferenceReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					List<String> emptyList = new ArrayList<>();
					List<UserPreference> userPreferenceList = userPreferenceService.viewListByCriteria(companyId, "", "", "", id, "", "", emptyList, emptyList, emptyList, emptyList, "", "", 0, 0);

					if (userPreferenceList.size() > 0) {

						UserPreference userPreferenceDetail = userPreferenceList.get(0);

						userPreferenceDetail.setCompanyId(userPreferenceReq.getCompanyId());
						userPreferenceDetail.setName(userPreferenceReq.getName());
						userPreferenceDetail.setCode(userPreferenceReq.getCode());
 						userPreferenceDetail.setUserPreferenceField(userPreferenceReq.getUserPreferenceField());
 						userPreferenceDetail.setUserPrefAppBootSetting(userPreferenceReq.getUserPrefAppBootSetting());
						userPreferenceDetail.setCategory(userPreferenceReq.getCategory());
						userPreferenceDetail.setSubCategory(userPreferenceReq.getSubCategory());
						userPreferenceDetail.setStartTime(userPreferenceReq.getStartTime());
						userPreferenceDetail.setEndTime(userPreferenceReq.getEndTime());
			  			userPreferenceDetail.setModifiedBy(sessionUserId);
			  			userPreferenceDetail.setModifiedAt(dbOperationStartTime);

			  			UserPreference succPayload = userPreferenceService.updateUserPreference(userPreferenceDetail);

			  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

			  				if (type.equalsIgnoreCase(NotificationModelType.pushType.USER_APP_BOOT_SETTING.toString())) {
			  					serviceFCMNotification.sendUserAppBootSettingAlertToUser(companyId, divisionId, userId);
			  				}

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

	@ApiOperation(value = "Remove User Preference Details",
			  nickname = "UserPreferenceRemove",
			  notes = "Remove User Preference Details")
	@ApiResponses(value = {
	  @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = APIGeneralSuccess.class),
	  @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
	  @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	  @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
	  @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
	})
	@RequestMapping(value = "/userPreference", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeUserPreferencDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "divisionId", defaultValue = "", required = true) String divisionId,
			@RequestParam(name = "id", defaultValue = "", required = true) String id){

		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_DIVISION_PREFERENCE_REMOVE";
		String apiInstLogString = "LOG_INST_DIVISION_PREFERENCE_REMOVE";
		String resourceType 	= APIResourceName.DIVISION_PREFERENCE.toString();

		List<APIPreConditionErrorField> errorList = new ArrayList<>();
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if(!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if(!FieldValidator.isValidId(divisionId))
			errorList.add(new APIPreConditionErrorField("divisionId", EM_INVALID_DIVI_ID));
		if(!FieldValidator.isValidId(id))
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));

		if (errorList.size() == 0) {
			List<String> emptyList = new ArrayList<>();
			List<UserPreference> userPrefernceList = userPreferenceService.viewListByCriteria(companyId, divisionId, "", "", id, "", "", emptyList, emptyList, emptyList, emptyList,"" , "", 0, 0);

			if (userPrefernceList.size() > 0) {

				String type = userPrefernceList.get(0).getType();

				String rolePrivilegeType = type + APIRolePrivilegeType._DELETE.toString();
				ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, "");
				String roleStatucCode = roleStatus.getCode();
				String roleStatucMess = roleStatus.getMessage();

				if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

					int results = userPreferenceService.removeUserPreferenceById(companyId, divisionId, id);

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