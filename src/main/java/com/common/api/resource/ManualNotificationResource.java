package com.common.api.resource;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.constant.APIRolePrivilegeType;
import com.common.api.model.type.NotificationModelType;
import com.common.api.request.PushNotificationRequest;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.response.UserSessionResult;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.service.util.ServiceFCMNotification;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manual Notification", tags = {"Manual Notification"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class ManualNotificationResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	ProfileSessionRoleService profileSessionRoleService;

	@Autowired
	PayloadValidator payloadValidator;
	@Autowired
	ServiceFCMNotification serviceFCMNotification;

	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";

    @ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Create Push Notification Details",
				  nickname = "Push NotificationDetailCreate",
				  notes = "Create Push Notification Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = UserSessionResult.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/manual/pushNotification", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createManualPushNotification(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody PushNotificationRequest pushNotifReq) {

		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_MANUAL_NOTIFICATION_CREATE";
    	String apiInstLogString = "LOG_INST_MANUAL_NOTIFICATION_CREATE";

    	String companyId	= pushNotifReq.getCompanyId();
    	String type		 	= pushNotifReq.getType();
    	String resourceType = APIResourceName.PUSH_NOTIFICATION.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<PushNotificationRequest>> validationErrors = validator.validate(pushNotifReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
    	errorList.addAll(payloadValidator.isValidManualPushNotificationPayload(GC_METHOD_POST, pushNotifReq));

    	for (ConstraintViolation<PushNotificationRequest> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = APIResourceName.USER.toString() + APIRolePrivilegeType._UPDATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, APIResourceName.USER.toString(), rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				PushNotificationRequest succPayload = new PushNotificationRequest();

				if (type.equalsIgnoreCase(NotificationModelType.ManualPushType.VEHICLE_ACCIDENT_ALERT_TO_CO_WORKER.toString())) {
					succPayload = serviceFCMNotification.sendVehicleAccidentAlertToCoWorker(pushNotifReq);
				} else if (type.equalsIgnoreCase(NotificationModelType.ManualPushType.VEHICLE_TRAFFIC_HALT_TO_CO_WORKER.toString())) {
					succPayload = serviceFCMNotification.sendVehicleTrafficHaltToCoWorker(pushNotifReq);
				}

	  			if (succPayload != null && succPayload.getType() != null && succPayload.getType().length() > 0) {

					APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
	  			}
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));

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