package com.common.api.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.common.api.datasink.service.UserLastActivityService;
import com.common.api.datasink.service.UserService;
import com.common.api.datasink.service.UserSessionService;
import com.common.api.datasink.service.UserVerificationService;
import com.common.api.model.User;
import com.common.api.model.UserVerification;
import com.common.api.model.status.ModelStatus;
import com.common.api.model.type.ModelType;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileCardinalityAccess;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.service.util.ServiceSMSGateway;
import com.common.api.util.APIAuthorization;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.EncryptDecrypt;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Verification", tags = {"User Verification"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class UserVerificationResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	APIAuthorization apiAuthorization;
	@Autowired
	FieldValidator fieldValidator;
	@Autowired
	PayloadValidator payloadValidator;
	@Autowired
	ServiceSMSGateway serviceSMSGateway;

    @Autowired
    UserService userService;
    @Autowired
    UserSessionService userSessionService;
    @Autowired
    UserVerificationService userVerificationService;
	@Autowired
	ProfileSessionRoleService profileSessionRoleService;
	@Autowired
	ProfileDependencyService profileDependencyService;
	@Autowired
	ProfileCardinalityService profileCardinalityService;
	@Autowired
	UserLastActivityService userLastActivityService;

	@Value("${e003.profile.not.exist}")
	String e003ProfileNotExist = "";
	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";
	@Value("${e008.process.already.completed}")
	String e008ProcessAlreadyCompleted = "";
	@Value("${e013.sms.gateway.process.failed}")
	String e013SmsGatewayProcessFailed = "";
	@Value("${e016.username.type.not.allowed}")
	String e016UsernameTypeNotAllowed = "";

	@ApiOperation(value = "View User Verification Details",
				  nickname = "UserVerificationDetailView",
				  notes = "View User Verification Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = UserVerification.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/userVerification", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewUserVerificationDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "userId", defaultValue = "", required = false) String userId,
			@RequestParam(name = "id", defaultValue = "", required = false) String id,
			@RequestParam(name = "username", defaultValue = "", required = true) String username,
			@RequestParam(name = "verificationCode", defaultValue = "", required = true) String verificationCode,
			@RequestParam(name = "status", defaultValue = "", required = false) String status,
			@RequestParam(name = "type", defaultValue = "USER_VERIFICATION", required = true) String type,
 			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) {

		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_USER_VERIFICATION_VIEW";
    	String apiInstLogString = "LOG_INST_USER_VERIFICATION_VIEW";

		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;
    	type	  = (type.length() <= 0) ? APIResourceName.USER_VERIFICATION.toString() : type;
    	String resourceType 	= APIResourceName.USER_VERIFICATION.toString();

    	List<String> statusList = Util.convertIntoArrayString(status);
    	List<String> typeList   = Util.convertIntoArrayString(type);

    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (!FieldValidator.isValidIdOptional(userId))
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
		if (!FieldValidator.isValidIdOptional(id))
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		if (!FieldValidator.isValidUsername(username, true))
			errorList.add(new APIPreConditionErrorField("username", EM_INVALID_USERNAME));
		if (!FieldValidator.isValidInternalTextOptional(status))
			errorList.add(new APIPreConditionErrorField("status", EM_INVALID_STATUS));
		if (!FieldValidator.isValidInternalTextOptional(type))
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				List<UserVerification> succPayload = userVerificationService.viewListByCriteria(companyId, userId, id, username, verificationCode, statusList, typeList, sortBy, sortOrder, offset, limit);

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
	@ApiOperation(value = "Create User Verification Details",
				  nickname = "UserVerificationDetailCreate",
				  notes = "Create User Verification Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = UserVerification.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/userVerification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createUserVerificationDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody UserVerification userVerificationReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_VERIFICATION_CREATE";
    	String apiInstLogString = "LOG_INST_USER_VERIFICATION_CREATE";

    	String companyId	= userVerificationReq.getCompanyId();
    	String type			= userVerificationReq.getType();
    	String username 	= userVerificationReq.getUsername();
    	type	  			= (type.length() <= 0) ? APIResourceName.USER_VERIFICATION.toString() : type;
    	String resourceType 	= APIResourceName.USER_VERIFICATION.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<UserVerification>> validationErrors = validator.validate(userVerificationReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidUserVerificationPayload(GC_METHOD_POST, userVerificationReq));
    	for (ConstraintViolation<UserVerification> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.userVerificationProfileDependencyStatus(GC_METHOD_POST, userVerificationReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					List<String> emptyList = new ArrayList<>();
					List<User> userList = userService.viewListByCriteria(companyId, "", "", "", "", username, "", "", emptyList, emptyList, "", "", 0, 0);

					if (userList.size() > 0) {

						boolean isAllowedNextLevel = true;
						User userDetail = userList.get(0);

						String userId    = userDetail.getId();
						String status    = userDetail.getStatus();
						String usernameType = userDetail.getUsernameType();

						if (usernameType.equalsIgnoreCase(ModelType.UsernameType.MOBILE_NUMBER.toString())) {

							ProfileCardinalityAccess profileCardinality = profileCardinalityService.userVerificationProfileCardinalityStatus(GC_METHOD_POST, userVerificationReq);
							String profileCardinalityCode = profileCardinality.getCode();
							String profileCardinalityMess = profileCardinality.getMessage();

							if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {

								if (type.equals(ModelType.UserVerificationType.USER_ACTIVATION.toString())
									&& status.equals(ModelStatus.UserStatus.ACTIVE.toString())) {
									isAllowedNextLevel = false;
								}

								if (isAllowedNextLevel) {

									String verifCode = Util.generateRandomValueAsNumber(4);

									userVerificationReq.setCompanyId(companyId);
									userVerificationReq.setUserId(userId);
									userVerificationReq.setVerificationCode(verifCode);
									userVerificationReq.setCreatedAt(dbOperationStartTime);
						  			userVerificationReq.setModifiedAt(dbOperationStartTime);

						  			userVerificationService.removeUserVerificationByCriteria(userId, type);
						  			UserVerification succPayload = userVerificationService.createUserVerification(userVerificationReq);

						  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

						  				String smsResult = serviceSMSGateway.sendVerificationCodeToUserBySMS(userDetail, verifCode);

						  				if (smsResult.equalsIgnoreCase(GC_STATUS_SUCCESS)) {

						  					succPayload.setVerificationCode("");
											APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
											APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
											return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
						  				}
										return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_013_SMS_GATEWAY_PROCESS_FAILED, e013SmsGatewayProcessFailed));
	 					  			}
									return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
								}
								return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_008_PROCESS_ALREADY_CMPTD, e008ProcessAlreadyCompleted));
							}
							return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileCardinalityMess));
						}
						return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_016_USERNAME_TYPE_MOBILE_NO, e016UsernameTypeNotAllowed));
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

	@ResponseStatus(code = HttpStatus.ACCEPTED)
	@ApiOperation(value = "Update User Verification Details",
				  nickname = "UserVerificationDetailUpdate",
				  notes = "Update User Verification Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = UserVerification.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/userVerification", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> updateUserVerificationDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody UserVerification userVerificationReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_VERIFICATION_UPDATE";
    	String apiInstLogString = "LOG_INST_USER_VERIFICATION_UPDATE";

    	String companyId	= userVerificationReq.getCompanyId();
    	String id			= userVerificationReq.getId();
    	String type			= userVerificationReq.getType();
    	String username 	= userVerificationReq.getUsername();
    	String verifyCode 	= userVerificationReq.getVerificationCode();
    	String password 	= userVerificationReq.getUserVerificationField().getPassword();
    	String mobilePin 	= userVerificationReq.getUserVerificationField().getMobilePin();
    	String payloadVersion = userVerificationReq.getPayloadVersion();

    	type	  		= (type.length() <= 0) ? APIResourceName.USER_VERIFICATION.toString() : type;
    	String resourceType 	= APIResourceName.USER_VERIFICATION.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<UserVerification>> validationErrors = validator.validate(userVerificationReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);

    	/** Converting MD5 format if raw password before validation - Starts */
    	if (type.equals(ModelType.UserVerificationType.USER_FORGOT_PASSWORD.toString()) ||
    		type.equals(ModelType.UserVerificationType.FORCE_CHANGE_PASSWORD.toString())) {
			if (payloadVersion.equalsIgnoreCase("0")) {
				EncryptDecrypt encryptDecryptTemp = new EncryptDecrypt();
				password = encryptDecryptTemp.getMD5EncryptedValue(password);
				if (!FieldValidator.isValidMD5(password))
					errorList.add(new APIPreConditionErrorField("password", EM_INVALID_PASSWORD));
			} else if (payloadVersion.equalsIgnoreCase("1")) {
				if (!FieldValidator.isValidMD5(password))
					errorList.add(new APIPreConditionErrorField("password", EM_INVALID_PASSWORD));
			}
    	}
    	/** Converting MD5 format if raw password before validation - Ends */

    	errorList.addAll(payloadValidator.isValidUserVerificationPayload(GC_METHOD_PUT, userVerificationReq));
    	for (ConstraintViolation<UserVerification> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				List<String> statusList = Arrays.asList(GC_STATUS_REGISTERED);
				List<String> typeList 	= Arrays.asList(type);
				List<UserVerification> userVerificationList = userVerificationService.viewListByCriteria(companyId, "", id, username, verifyCode, statusList, typeList, "", "", 0, 0);

				if (userVerificationList.size() > 0) {

		  			List<String> emptyList = new ArrayList<>();
					List<User> userList = userService.viewListByCriteria(companyId, "", "", "", "", username, "", "", emptyList, emptyList, "", "", 0, 0);

					if (userList.size() > 0) {

						boolean isAllowedNextLevel = true;
						User userDetail = userList.get(0);
						String userId    = userDetail.getId();
						String status    = userDetail.getStatus();
						String usernameType = userDetail.getUsernameType();

						if (usernameType.equalsIgnoreCase(ModelType.UsernameType.MOBILE_NUMBER.toString())) {

							if (type.equals(ModelType.UserVerificationType.USER_ACTIVATION.toString())
								&& status.equals(ModelStatus.UserStatus.ACTIVE.toString())) {
								isAllowedNextLevel = false;
							}

							if (isAllowedNextLevel) {

								UserVerification userVerificationDetail = userVerificationList.get(0);

								userVerificationDetail.setStatus(userVerificationReq.getStatus());
								userVerificationDetail.setModifiedAt(dbOperationStartTime);
					  			UserVerification succPayload = userVerificationService.updateUserVerification(userVerificationDetail);

					  			if (type.equals(ModelType.UserVerificationType.USER_ACTIVATION.toString())) {
									userDetail.setUsernameVerified(ModelStatus.UsernameVerifiedStatus.YES.toString());
					  				userDetail.setStatus(ModelStatus.UserStatus.ACTIVE.toString());
					  				userService.updateUser(userDetail);
					  			} else if (type.equals(ModelType.UserVerificationType.USER_FORGOT_PASSWORD.toString())) {
					  				userDetail.setPassword(password);
					  				userService.updateUserPassword(userDetail);
					  				userSessionService.removeUserSessionByUserId(userId);
					  				userService.updateUserNextActivity(userId, "");
					  				userLastActivityService.updateUserLastActivityByPasswordChanged(userId, dbOperationStartTime, dbOperationStartTime);
					  			} else if (type.equals(ModelType.UserVerificationType.FORCE_CHANGE_PASSWORD.toString())) {
					  				userDetail.setPassword(password);
					  				userService.updateUserPassword(userDetail);
					  				userSessionService.removeUserSessionByUserId(userId);
					  				userService.updateUserNextActivity(userId, "");
					  				userLastActivityService.updateUserLastActivityByPasswordChanged(userId, dbOperationStartTime, dbOperationStartTime);
					  			} else if (type.equals(ModelType.UserVerificationType.USER_FORGOT_MOBILE_PIN.toString())) {
					  				userDetail.setMobilePin(mobilePin);
					  				userService.updateUserMobilePin(userDetail);
					  				userSessionService.removeUserSessionByUserId(userId);
					  			}

					  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

									APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
									APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
									return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(succPayload);
					  			}
					  			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
							}
							return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_008_PROCESS_ALREADY_CMPTD, e008ProcessAlreadyCompleted));
						}
						return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_016_USERNAME_TYPE_MOBILE_NO, e016UsernameTypeNotAllowed));
					}
 				}
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_003_PROFILE_NOT_EXIST, e003ProfileNotExist));
			} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) {
				return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));
			} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, roleStatucMess));
			}
  		}
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
    }

}