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
import com.common.api.datasink.service.ManualSchedulerService;
import com.common.api.datasink.service.ModulePreferenceService;
import com.common.api.datasink.service.UserSessionService;
import com.common.api.model.UserLastActivity;
import com.common.api.model.UserVerification;
import com.common.api.model.type.NotificationModelType;
import com.common.api.model.util.ManualScheduler;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.service.util.ServiceFCMNotification;
import com.common.api.util.APIAuthorization;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manual Scheduler", tags = {"Manual Scheduler"})
@RestController
@PropertySource({ "classpath:application.properties" })
public class ManualSchedulerResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	FieldValidator fieldValidator;
	@Autowired
	APIAuthorization apiAuthorization;
    @Autowired
    ModulePreferenceService modulePreferenceService;
    @Autowired
    ServiceFCMNotification serviceFCMNotification;
    @Autowired
    ManualSchedulerService manualSchedulerService;

    @Autowired
    UserSessionService userSessionService;

	/** Properties Constants */
	@Value("${e002.session.not.matched}")
	private String e002SessionNotMatched = "";

	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "View Manual Scheduler Details",
				  nickname = "ViewSchedulerDetailCreate",
				  notes = "View Manual Scheduler Details")
	@ApiResponses(value = {
	        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = UserLastActivity.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/manualScheduler", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> viewManualSchedulerDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = false) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = false) String userId,
 			@RequestParam(name = "status", defaultValue = GC_STATUS_PENDING, required = true) String status,
 			@RequestParam(name = "type", defaultValue = "", required = true) String type,
 			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_MANUAL_SCHEDULER_VIEW";
    	String apiInstLogString = "LOG_INST_MANUAL_SCHEDULER_VIEW";

    	offset	  = (offset < 0) ? 0 : offset;
    	limit	  = (limit <= 0) ? 100 : limit;
    	status	  = (status.equals("")) ? GC_STATUS_PENDING : status;
		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;

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
		if (!FieldValidator.isValidIdOptional(userId))
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_USER_ID));
		if (!FieldValidator.isValid(type, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, true))
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));

		if (errorList.size() == 0) {

			if (apiAuthorization.verifyStaticSessionId(sessionId)) {

				List<?> succPayload = new ArrayList<>();

				if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_APP_VERSION.toString())) {
 					succPayload = modulePreferenceService.viewListByCriteria(companyId, status, type, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_APP_VERSION.toString())) {
 					succPayload = modulePreferenceService.viewListByCriteria(companyId, status, type, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_LIBRARY_ZIP.toString())) {
 					succPayload = modulePreferenceService.viewListByCriteria(companyId, status, type, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_LIBRARY_ZIP.toString())) {
 					succPayload = modulePreferenceService.viewListByCriteria(companyId, status, type, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_NO_ACTIVITY.toString())) {
					modulePreferenceService.updateNotificationStatusPendingByNotificationStatusTime(type, GC_STATUS_PENDING, dbOperationStartTime);
 					succPayload = modulePreferenceService.viewListByCriteria(companyId, status, type, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_NO_ACTIVITY.toString())) {
					modulePreferenceService.updateNotificationStatusPendingByNotificationStatusTime(type, GC_STATUS_PENDING, dbOperationStartTime);
 					succPayload = modulePreferenceService.viewListByCriteria(companyId, status, type, sortBy, sortOrder, offset, limit);
				}

				if (succPayload.size() >= 0) {

					APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload);

				}
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, "Request Process Failed"));
			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, e002SessionNotMatched));
		}
 		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Create Manual Scheduler Details",
				  nickname = "ManualSchedulerDetailCreate",
				  notes = "Create Manual Scheduler Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = UserVerification.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/manualScheduler", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createManualSchedulerDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody ManualScheduler manualSchedulerReq) {

		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_MANUAL_SCHEDULER_CREATE";
    	String apiInstLogString = "LOG_INST_MANUAL_SCHEDULER_CREATE";

    	String companyId	= manualSchedulerReq.getCompanyId();
    	String groupId		= manualSchedulerReq.getGroupId();
    	String divisionId	= manualSchedulerReq.getDivisionId();
    	String moduleId		= manualSchedulerReq.getModuleId();
    	String userId		= manualSchedulerReq.getUserId();
    	String type			= manualSchedulerReq.getType();
    	int offset			= manualSchedulerReq.getOffset();
    	int limit			= manualSchedulerReq.getLimit();

    	String sortOrder 	= GC_SORT_ASC;
    	String sortBy    	= GC_SORT_BY_CREATED_BY;
    	String notifiStatus	= GC_STATUS_PENDING;

    	ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<ManualScheduler>> validationErrors = validator.validate(manualSchedulerReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
     	for (ConstraintViolation<ManualScheduler> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

		if (errorList.size() == 0) {

			if (apiAuthorization.verifyStaticSessionId(sessionId)) {

				List<?> succPayload = new ArrayList<>();

				if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_APP_VERSION.toString())) {
					String userSessionType = GC_REFERRED_BY_IOS;
 					succPayload = serviceFCMNotification.sendAppOrMapSettingAlertToUser(companyId, groupId, divisionId, moduleId, userId, notifiStatus, type, userSessionType, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_APP_VERSION.toString())) {
					String userSessionType = GC_REFERRED_BY_ANDROID;
 					succPayload = serviceFCMNotification.sendAppOrMapSettingAlertToUser(companyId, groupId, divisionId, moduleId, userId, notifiStatus, type, userSessionType, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_LIBRARY_ZIP.toString())) {
					String userSessionType = GC_REFERRED_BY_IOS;
 					succPayload = serviceFCMNotification.sendAppOrMapSettingAlertToUser(companyId, groupId, divisionId, moduleId, userId, notifiStatus, type, userSessionType, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_LIBRARY_ZIP.toString())) {
					String userSessionType = GC_REFERRED_BY_ANDROID;
 					succPayload = serviceFCMNotification.sendAppOrMapSettingAlertToUser(companyId, groupId, divisionId, moduleId, userId, notifiStatus, type, userSessionType, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_NO_ACTIVITY.toString())) {
					String userSessionType = GC_REFERRED_BY_IOS;
 					succPayload = serviceFCMNotification.sendNoActivityAlertToUser(companyId, groupId, divisionId, moduleId, userId, notifiStatus, type, userSessionType, sortBy, sortOrder, offset, limit);
				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_NO_ACTIVITY.toString())) {
					String userSessionType = GC_REFERRED_BY_ANDROID;
 					succPayload = serviceFCMNotification.sendNoActivityAlertToUser(companyId, groupId, divisionId, moduleId, userId, notifiStatus, type, userSessionType, sortBy, sortOrder, offset, limit);
				}

				if (succPayload.size() >= 0) {
					APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
				}
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, "Request Process Failed"));
			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, e002SessionNotMatched));
		}
 		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	}

}