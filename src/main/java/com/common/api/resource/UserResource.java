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
import com.common.api.datasink.service.RoleModuleService;
import com.common.api.datasink.service.RoleUserService;
import com.common.api.datasink.service.SubscriptionService;
import com.common.api.datasink.service.UserService;
import com.common.api.model.Property;
import com.common.api.model.RoleModule;
import com.common.api.model.RoleUser;
import com.common.api.model.Section;
import com.common.api.model.User;
import com.common.api.model.field.UserSessionField;
import com.common.api.model.status.ModelStatus;
import com.common.api.model.type.CategoryType;
import com.common.api.model.type.ModelPartialUpdateType;
import com.common.api.model.type.ModelType;
import com.common.api.request.PartialUpdateRequest;
import com.common.api.request.UserCollection;
import com.common.api.request.UserCollectionData;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileCardinalityAccess;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileAutoSystematicService;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileHardwareDeviceService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.util.APIAuthorization;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.PayloadValidatorPartial;
import com.common.api.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User", tags = {"User"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class UserResource extends APIFixedConstant {

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
	PayloadValidatorPartial payloadValidatorPartial;
	@Autowired
	ProfileHardwareDeviceService profileHardwareDeviceService;
	@Autowired
	ProfileSessionRoleService profileSessionRoleService;
	@Autowired
	ProfileDependencyService profileDependencyService;
	@Autowired
	ProfileCardinalityService profileCardinalityService;
	@Autowired
	ProfileAutoSystematicService profileAutoSystamaticService;

    @Autowired
    UserService userService;
    @Autowired
    RoleUserService roleUserService;
    @Autowired
    RoleModuleService roleModuleService;
    @Autowired
    SubscriptionService subscriptionService;

	@Value("${e003.profile.not.exist}")
	String e003ProfileNotExist = "";
	@Value("${e005.username.already.exist}")
	String e005UsernameAlreadyExist = "";
	@Value("${e005.email.already.exist}")
	String e005EmailAlreadyExist = "";
	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";
	@Value("${e007.access.blocked}")
	String e007AccessBlocked = "";

	@Value("${app.prelist.username.validation}")
	String appPrelistUsernameValidation = "";
	@Value("${app.prelist.email.validation}")
	String appPrelistEmailValidation = "";
	@Value("${app.prelist.username.validation.mess}")
	String appPrelistUsernameValidationMess = "";
	@Value("${app.prelist.email.validation.mess}")
	String appPrelistEmailValidationMess = "";

	@ApiOperation(value = "View User Details",
				  nickname = "UserDetailView",
				  notes = "View User Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = User.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewUserDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = true) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "id", defaultValue = "", required = false) String id,
			@RequestParam(name = "username", defaultValue = "", required = false) String username,
			@RequestParam(name = "email", defaultValue = "", required = false) String email,
			@RequestParam(name = "status", defaultValue = "", required = false) String status,
			@RequestParam(name = "type", defaultValue = "USER", required = true) String type,
			@RequestParam(name = "category", defaultValue = "", required = false) String category,
 			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) {

		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_USER_VIEW";
    	String apiInstLogString = "LOG_INST_USER_VIEW";

		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;
    	type	  = (type.length() <= 0) ? APIResourceName.USER.toString() : type;
    	String resourceType 	= APIResourceName.USER.toString();

    	List<String> statusList = Util.convertIntoArrayString(status);
    	List<String> typeList   = Util.convertIntoArrayString(type);

    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (!FieldValidator.isValidIdOptional(groupId))
			errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID));
		if (!FieldValidator.isValidIdOptional(divisionId))
			errorList.add(new APIPreConditionErrorField("divisionId", EM_INVALID_DIVI_ID));
		if (!FieldValidator.isValidIdOptional(moduleId))
			errorList.add(new APIPreConditionErrorField("moduleId", EM_INVALID_MODU_ID));
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

				List<User> succPayload = userService.viewListByCriteria(companyId, groupId, divisionId, moduleId, id, username, email, category, statusList, typeList, sortBy, sortOrder, offset, limit);

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

	/**
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Create User Details",
				  nickname = "UserDetailCreate",
				  notes = "Create User  Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = User.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createUserDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody User userReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_CREATE";
    	String apiInstLogString = "LOG_INST_USER_CREATE";
    	String resourceType 	= APIResourceName.USER.toString();

    	String companyId    = userReq.getCompanyId();
    	String divisionId   = userReq.getDivisionId();
    	String moduleId     = userReq.getModuleId();
    	String type			= userReq.getType();
    	String email		= userReq.getEmail();
    	String username		= userReq.getUsername();
    	type	  			= (type.length() <= 0) ? APIResourceName.USER.toString() : type;

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<User>> validationErrors = validator.validate(userReq);

		try {
			userReq.setEmail(userReq.getEmail().toLowerCase());
		} catch (Exception errMess) { }

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidUserPayload(GC_METHOD_POST, userReq));
    	for (ConstraintViolation<User> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.userProfileDependencyStatus(GC_METHOD_POST, userReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					List<String> emptyList = new ArrayList<String>();

 					List<User> usernameList = userService.viewListByCriteria(companyId, "", "", "", "", username, "", "", emptyList, emptyList, "", "", 0, 0);
 					List<User> emailList    = userService.viewListByCriteria(companyId, "", "", "", "", "", email, "", emptyList, emptyList, "", "", 0, 0);

					if (usernameList.size() <= 0 && emailList.size() <= 0) {

						ProfileCardinalityAccess profileCardinality = profileCardinalityService.userProfileCardinalityStatus(GC_METHOD_POST, userReq);
						String profileCardinalityCode = profileCardinality.getCode();
						String profileCardinalityMess = profileCardinality.getMessage();

						if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {

							userReq.setEmailVerified(ModelStatus.EmailVerifiedStatus.NO.toString());
							userReq.setUsernameVerified(ModelStatus.UsernameVerifiedStatus.NO.toString());
							userReq.setStatus(ModelStatus.UserStatus.REGISTERED.toString());
							userReq.setCreatedAt(dbOperationStartTime);
				  			userReq.setModifiedAt(dbOperationStartTime);
				  			userReq.setCreatedBy("");
				  			userReq.setModifiedBy("");

				  			User succPayload = userService.createUser(userReq);

				  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

				  				try {
					  				String userId = succPayload.getId();
					  				UserSessionField userSessionField = userReq.getUserSessionField();
					  				String userDeviceStatus = userSessionField.getDeviceStatus();
					  				profileHardwareDeviceService.createHardwareDevice(companyId, divisionId, moduleId, userId, userSessionField, userDeviceStatus);
 				  				} catch (Exception errMess) {
				  				}

				  				try {

				  					// Create Role User Profile
				  					RoleUser roleUser = profileAutoSystamaticService.createRoleUserByUser(succPayload);

				  					// Create Other Profiles
				  					if (roleUser != null && roleUser.getId() != null) {

				  						List<RoleModule> roleModuleList = roleModuleService.viewListByCriteria(companyId, "", "", "", emptyList, emptyList, "", "", 0, 0);

				  						if (roleModuleList.size() > 0 && roleModuleList.size() < 2) {
				  							RoleModule roleModule = roleModuleList.get(0);
					  						Property property = profileAutoSystamaticService.createPropertyByUser(succPayload, roleModule);
				  							Section section   = profileAutoSystamaticService.createSectionByUser(succPayload, roleModule, property);
				  							profileAutoSystamaticService.createPortionByUser(succPayload, roleModule, property, section);
 				  						}
				  					}
				  				} catch (Exception errMess) {
				  				}

								APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
								APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
								return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
				  			}
							return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
						}
						return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileCardinalityMess));
					} else if (usernameList.size() > 0) {
						return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_005_USR_NAME_ALRDY_EXIST, e005UsernameAlreadyExist));
					} else {
						return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_005_EMAIL_ALRDY_EXIST, e005EmailAlreadyExist));
					}
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
	*/

	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Create Users Registration Details",
				  nickname = "UsersRegistrationDetailCreate",
				  notes = "Create Users Registration Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = UserCollection.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/users/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> usersRegistrationDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody UserCollection userCollectionReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USERS_REGISTRATION_CREATE";
    	String apiInstLogString = "LOG_INST_USERS_REGISTRATION_CREATE";

		String companyId	= userCollectionReq.getCompanyId();
    	String groupId 		= userCollectionReq.getGroupId();
    	String divisionId 	= userCollectionReq.getDivisionId();
    	String moduleId 	= userCollectionReq.getModuleId();
    	String userId 		= userCollectionReq.getUserId();
    	String type			= userCollectionReq.getType();
    	List<UserCollectionData> userCollectionList = userCollectionReq.getUserCollections();
    	type				= (type.length() <= 0) ? APIResourceName.USER.toString() : type;
    	String resourceType 	= APIResourceName.USER.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<UserCollection>> validationErrors = validator.validate(userCollectionReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidAdminUserPayload(GC_METHOD_POST, userCollectionReq));
    	for (ConstraintViolation<UserCollection> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE_ALL.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, groupId, divisionId, moduleId, userId);
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.adminUserProfileDependencyStatus(GC_METHOD_POST, userCollectionReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					ProfileCardinalityAccess profileCardinality = profileCardinalityService.adminUserProfileCardinalityStatus(GC_METHOD_POST, userCollectionReq);
					String profileCardinalityCode = profileCardinality.getCode();
					String profileCardinalityMess = profileCardinality.getMessage();

					if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {

						List<User> succPayloadList = new ArrayList<>();
						String timeZoneId = "", countryId  = "", typeDB   = "", languageId = "";
						int noOfUsersCount = 0, noOfUsersActive = 0, noOfUsersRegistered = 0, noOfUsersNotRegistered = 0, noOfUsersInitiated = 0;

						List<String> emptyList = new ArrayList<>();
						List<User> userExistList = userService.viewListByCriteria(companyId, "", "", "", userId, "", "", "", emptyList, emptyList, "", "", 0, 0);

						if (userExistList.size() > 0) {
							User userDetail = userExistList.get(0);
							groupId    = userDetail.getGroupId();
							divisionId = userDetail.getDivisionId();
							moduleId   = userDetail.getModuleId();
							languageId = userDetail.getLanguageId();
							timeZoneId = userDetail.getTimeZoneId();
							countryId  = userDetail.getCountryId();
							typeDB   = userDetail.getType();
						}

						if (type.equalsIgnoreCase(ModelType.UserType.USER.toString()) && type.equalsIgnoreCase(typeDB)) {

							for (int uCount = 0; uCount < userCollectionList.size(); uCount++) {

								UserCollectionData userColData = userCollectionList.get(uCount);
								String username = userColData.getUsername();
								String password = userColData.getPassword();
								String category = userColData.getCategory();
								String usernameType = userColData.getUsernameType();
								String deviceType = userColData.getDeviceType();

								List<User> userList = userService.viewListByCriteria(companyId, "", "", "", "", username, "", "", emptyList, emptyList, "", "", 0, 0);

								if (userList.size() > 0) {

									User userDetail = userList.get(0);
									String userStatus = userDetail.getStatus();

									if (userStatus.equalsIgnoreCase(ModelStatus.UserStatus.REGISTERED.toString())) {
	 									noOfUsersRegistered++;
									} else if (userStatus.equalsIgnoreCase(ModelStatus.UserStatus.ACTIVE.toString())) {
	 									noOfUsersActive++;
									} else if (userStatus.equalsIgnoreCase(ModelStatus.UserStatus.INITIATED.toString())) {
	 									noOfUsersInitiated++;
									}

								} else {

									noOfUsersNotRegistered++;

									User userReq = new User();
									userReq.setCompanyId(companyId);
									userReq.setGroupId(groupId);
									userReq.setDivisionId(divisionId);
									userReq.setModuleId(moduleId);
									userReq.setCountryId(countryId);
									userReq.setLanguageId(languageId);
									userReq.setFirstName(username);
									userReq.setLastName(category.toLowerCase());
									userReq.setPassword(password);
									userReq.setTimeZoneId(timeZoneId);
									userReq.setUsername(username);
									userReq.setCategory(category);
									userReq.setEmailVerified(ModelStatus.EmailVerifiedStatus.NO.toString());
									userReq.setUsernameVerified(ModelStatus.UsernameVerifiedStatus.NO.toString());

									if (usernameType.equalsIgnoreCase(ModelType.UsernameType.CUSTOM.toString()))
										userReq.setStatus(ModelStatus.UserStatus.ACTIVE.toString());
									else
										userReq.setStatus(ModelStatus.UserStatus.INITIATED.toString());

									userReq.setUsernameType(usernameType);
									userReq.setType(type);
									userReq.setCreatedAt(dbOperationStartTime);
									userReq.setModifiedAt(dbOperationStartTime);
									userReq.setCreatedBy(userId);
									userReq.setModifiedBy(userId);

						  			User succPayload = userService.createUser(userReq);
						  			succPayloadList.add(succPayload);

						  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

						  				noOfUsersCount++;

						  				String typeSubs = ModelType.SubscriptionType.MOBILE_DEVICE_LICENSE_SUBSCRIPTION.toString();

						  				if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID))
		                            		subscriptionService.subscriptionCountDecrementAndroid(companyId, divisionId, typeSubs, 1);
						  				if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_IOS))
		                            		subscriptionService.subscriptionCountDecrementIos(companyId, divisionId, typeSubs, 1);

						  				try {

						  					/** Create Role User Profile */
						  					RoleUser roleUser = profileAutoSystamaticService.createRoleUserByUser(succPayload);

						  					/** Create Other Profiles */
						  					if (roleUser != null && roleUser.getId() != null) {

						  						List<RoleModule> roleModuleList = roleModuleService.viewListByCriteria(companyId, divisionId, moduleId, "", emptyList, emptyList, "", "", 0, 0);

						  						if (roleModuleList.size() > 0 && roleModuleList.size() < 2) {
						  							RoleModule roleModule = roleModuleList.get(0);
							  						Property property = profileAutoSystamaticService.createPropertyByUser(succPayload, roleModule);
						  							Section section   = profileAutoSystamaticService.createSectionByUser(succPayload, roleModule, property);
						  							profileAutoSystamaticService.createPortionByUser(succPayload, roleModule, property, section);
						  							profileAutoSystamaticService.createUserPrefAppBootSettingByUser(succPayload);
						  							profileAutoSystamaticService.createUserLastActivityByUser(succPayload);
						  							profileAutoSystamaticService.createUserSecretByUser(succPayload);
		 				  						}
						  					}
						  				} catch (Exception errMess) {
						  				}
						  			}
								}
							}

							if (noOfUsersCount > 0) {
								APILog.writeInfoLog(apiLogString + " " + succPayloadList.toString());
								APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
								return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayloadList);
							} else if (noOfUsersNotRegistered > 0) {
								return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, "Username is available"));
							} else if (noOfUsersRegistered > 0) {
								return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, "Username is already taken"));
							} else if (noOfUsersActive > 0) {
								return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, "Username is already taken"));
							} else if (noOfUsersInitiated > 0) {
								return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, "Username is already taken"));
							} else {
								return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
	 						}
						}
						return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, e007AccessBlocked));
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

	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Create User Activation Details",
				  nickname = "UserActivationDetailCreate",
				  notes = "Create User Activation Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = User.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/user/activation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> userActivationDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody User userReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_ACTIVATION_CREATE";
    	String apiInstLogString = "LOG_INST_USER_ACTIVATION_CREATE";

    	String companyId    = userReq.getCompanyId();
    	String languageId   = userReq.getLanguageId();
    	String category		= userReq.getCategory();
    	String type			= userReq.getType();
    	String username		= userReq.getUsername();
    	String password		= userReq.getPassword();
    	String gender 		= userReq.getGender();
    	type	  			= (type.length() <= 0) ? APIResourceName.USER.toString() : type;
    	String resourceType = APIResourceName.USER.toString();

		if (gender.length() <= 0) {
			gender = "MALE";
			userReq.setGender(gender);
		}
		if (languageId.length() <= 0) {
			languageId = "lang00000000000000000001";
			userReq.setLanguageId(languageId);
		}

		try {
			userReq.setEmail(userReq.getEmail().toLowerCase());
		} catch (Exception errMess) { }

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<User>> validationErrors = validator.validate(userReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidUserPayload(GC_METHOD_POST, userReq));
    	for (ConstraintViolation<User> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

    	password		= userReq.getPassword();

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.userProfileDependencyStatus(GC_METHOD_POST, userReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					List<String> emptyList = new ArrayList<>();
					List<User> userExistList = userService.viewListByCriteria(companyId, "", "", "", "", username, "", "", emptyList, emptyList, "", "", 0, 0);

					if (userExistList.size() > 0) {

						User userDetail   = userExistList.get(0);
				    	String divisionId   = userDetail.getDivisionId();
				    	String moduleId     = userDetail.getModuleId();
						String categoryDB   = userDetail.getCategory();
						String typeDB   	= userDetail.getType();
						String statusDB		= userDetail.getStatus();

						if (category.equalsIgnoreCase(CategoryType.User.USER.toString()) &&
							category.equalsIgnoreCase(categoryDB) &&
 							type.equalsIgnoreCase(ModelType.UserType.USER.toString()) &&
							type.equalsIgnoreCase(typeDB) &&
							statusDB.equalsIgnoreCase(ModelStatus.UserStatus.INITIATED.toString())) {

							if (password.length() > 0) {
								userDetail.setPassword(password);
							}

							userDetail.setGender(gender);
							userDetail.setLanguageId(languageId);
							userDetail.setFirstName(userReq.getFirstName());
							userDetail.setLastName(userReq.getLastName());
							userDetail.setEmail(userReq.getEmail());
							userDetail.setMobilePin(userReq.getMobilePin());
							userDetail.setUserField(userReq.getUserField());
							userDetail.setUserSessionField(userReq.getUserSessionField());

							userDetail.setEmailVerified(ModelStatus.EmailVerifiedStatus.NO.toString());
							userDetail.setUsernameVerified(ModelStatus.UsernameVerifiedStatus.NO.toString());
							userDetail.setStatus(ModelStatus.UserStatus.REGISTERED.toString());

							userDetail.setModifiedAt(dbOperationStartTime);

				  			User succPayload = userService.updateUser(userDetail);

				  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

				  				try {
					  				String userId = succPayload.getId();
					  				UserSessionField userSessionField = userReq.getUserSessionField();
					  				String userDeviceStatus = userSessionField.getDeviceStatus();
					  				profileHardwareDeviceService.createHardwareDevice(companyId, divisionId, moduleId, userId, userSessionField, userDeviceStatus);
 				  				} catch (Exception errMess) {
				  				}

								APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
								APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
								return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
				  			}
							return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
						} else if (statusDB.equalsIgnoreCase(ModelStatus.UserStatus.ACTIVE.toString())) {
							return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, "Username is already activated"));
						} else if (statusDB.equalsIgnoreCase(ModelStatus.UserStatus.REGISTERED.toString())) {
							return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, "Username is already registered"));
						}
						return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, e007AccessBlocked));
					}
					return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_003_PROFILE_NOT_EXIST, appPrelistUsernameValidationMess));
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
	@ApiOperation(value = "Update User Details",
				  nickname = "UserDetailUpdate",
				  notes = "Update User  Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = User.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> updateUserDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody User userReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_UPDATE";
    	String apiInstLogString = "LOG_INST_USER_UPDATE";

    	String id			= userReq.getId();
    	String companyId	= userReq.getCompanyId();
    	String divisionId   = userReq.getDivisionId();
    	String moduleId     = userReq.getModuleId();
    	String gender 		= userReq.getGender();
    	String type			= userReq.getType();
    	type	  			= (type.length() <= 0) ? APIResourceName.USER.toString() : type;
    	String resourceType 	= APIResourceName.USER.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<User>> validationErrors = validator.validate(userReq);

		try {
			userReq.setEmail(userReq.getEmail().toLowerCase());
		} catch (Exception errMess) { }

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidUserPayload(GC_METHOD_PUT, userReq));
    	for (ConstraintViolation<User> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.userProfileDependencyStatus(GC_METHOD_PUT, userReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					List<String> emptyList = new ArrayList<>();
					List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, moduleId, id, "", "", "", emptyList, emptyList, "", "", 0, 0);

					if (userList.size() > 0) {

						User userDetail = userList.get(0);

						userDetail.setCountryId(userReq.getCountryId());
						userDetail.setFirstName(userReq.getFirstName());
						userDetail.setLastName(userReq.getLastName());
						userDetail.setEmail(userReq.getEmail());
						if (gender.length() > 0) {
							userDetail.setGender(gender);
						}
						userDetail.setUserField(userReq.getUserField());
						userDetail.setModifiedAt(dbOperationStartTime);
			  			userDetail.setModifiedBy(id);

			  			User succPayload = userService.updateUser(userDetail);

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

	@ResponseStatus(code = HttpStatus.ACCEPTED)
	@ApiOperation(value = "Update User Partial Details",
				  nickname = "UserPartialDetailUpdate",
				  notes = "Update User Partial Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = PartialUpdateRequest.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/user", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> updateUserPartialDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody PartialUpdateRequest partialUpdateReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_PARTIAL_UPDATE";
    	String apiInstLogString = "LOG_INST_USER_PARTIAL_UPDATE";

    	String id			= partialUpdateReq.getId();
    	String companyId	= partialUpdateReq.getCompanyId();
    	String divisionId   = partialUpdateReq.getDivisionId();
    	String moduleId     = partialUpdateReq.getModuleId();
		String fieldName  	= partialUpdateReq.getFieldName();
		String fieldValue 	= partialUpdateReq.getFieldValue();
    	String type	  		= APIResourceName.USER.toString();
    	String resourceType = APIResourceName.USER.toString();
    	String partialType  = partialUpdateReq.getType();

    	String partialUpdateType = (partialType.length() <= 0) ? ModelPartialUpdateType.UserPartialUpdateType.LANGUAGE_UPDATE.toString() : partialType;
    	partialUpdateReq.setType(partialUpdateType);

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<PartialUpdateRequest>> validationErrors = validator.validate(partialUpdateReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidatorPartial.isValidUserPartialPayload(partialUpdateReq));
    	for (ConstraintViolation<PartialUpdateRequest> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				List<String> emptyList = new ArrayList<>();
				List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, moduleId, id, "", "", "", emptyList, emptyList, "", "", 0, 0);

				if (userList.size() > 0) {

					boolean isValidToUpdate = false;
					User userDetail = userList.get(0);

					if (fieldName.equals("languageId") && partialUpdateType.equalsIgnoreCase(ModelPartialUpdateType.UserPartialUpdateType.LANGUAGE_UPDATE.toString())) {
 						isValidToUpdate = true;
						userDetail.setLanguageId(fieldValue);
						userDetail.setModifiedAt(dbOperationStartTime);
					}

					if (isValidToUpdate) {

			  			User succPayload = userService.updateUser(userDetail);

			  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

							APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
							APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
							return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(partialUpdateReq);
			  			}

					}
					return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
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