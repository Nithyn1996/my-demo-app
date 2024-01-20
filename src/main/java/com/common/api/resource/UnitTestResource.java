package com.common.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.api.constant.APIFixedConstant;
import com.common.api.provider.PushProvider;
import com.common.api.response.APIGeneralSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.util.APIAuthorization;
import com.common.api.util.FieldValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Unit Test", tags = {"Unit Test"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties" })
public class UnitTestResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	APIAuthorization apiAuthorization;
	@Autowired
	PushProvider pushProvider;

	@Value("${e002.session.not.matched}")
	private String e002SessionNotMatched = "";

	@ApiOperation(value = "View Push FCM Android Status",
				  nickname = "PushFCMAndroidView",
				  notes = "View Push FCM Android Status")
	@ApiResponses(value = {
			@ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = APIGeneralSuccess.class),
	        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
	        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
            @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
    	})
	@RequestMapping(value = "/unitTest/push/fcm/android", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendPushFCMAndroid(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "adminUserId", defaultValue = "", required = true) String[] adminUserId,
			@RequestParam(name = "deviceToken", defaultValue = "", required = true) String deviceToken,
			@RequestParam(name = "adminFirstName", defaultValue = "Motiv AI", required = true) String adminFirstName,
			@RequestParam(name = "divisionName", defaultValue = "MotivAI", required = true) String divisionName,
			@RequestParam(name = "pushTitle", defaultValue = "Rudu - Push FCM Android", required = true) String pushTitle,
			@RequestParam(name = "pushMessage", defaultValue = "Rudu - Push FCM Android - New Risk Alert", required = true) String pushMessage,
			@RequestParam(name = "userFirstName", defaultValue = "User 1", required = true) String userFirstName,
			@RequestParam(name = "deviceName", defaultValue = "Ride 501", required = true) String deviceName,
			@RequestParam(name = "deviceSubCategory", defaultValue = "OVER_SPPED", required = true) String deviceSubCategory,
			@RequestParam(name = "riskValue", defaultValue = "98.76", required = true) float riskValue) {

		List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		if (deviceToken.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceToken", "Invalid Device Token"));
		if (adminFirstName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("adminFirstName", "Invalid Admin First Name"));
		if (userFirstName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("userFirstName", "Invalid User First Name"));
		if (divisionName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceName", "Invalid Division Name"));
		if (deviceName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceName", "Invalid Device Name"));
		if (deviceSubCategory.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceSubCategory", "Invalid Device Sub Category"));
		if (riskValue <= 0)
			errorList.add(new APIPreConditionErrorField("riskValue", "Invalid Risk Value"));
		if (adminUserId.length <= 0)
			errorList.add(new APIPreConditionErrorField("adminUserId", "Invalid Admin User ID"));

		if (errorList.size() <= 0) {

			JSONObject pushPayload = new JSONObject();
			pushPayload.put("pushType", "USER_RIDE_DIRECT_RISK_ALERT_TO_ADMIN");
			pushPayload.put("adminUserIdList", adminUserId);
			pushPayload.put("adminFirstName", adminFirstName);
			pushPayload.put("userFirstName", userFirstName);
			pushPayload.put("divisionName", divisionName);
			pushPayload.put("deviceName", deviceName);
			pushPayload.put("deviceSubCategory", deviceSubCategory);
			pushPayload.put("riskValue", riskValue);

			if (apiAuthorization.verifyStaticSessionId(sessionId)) {

				List<String> deviceTokenList = Arrays.asList(deviceToken);
				String result = pushProvider.sendPushNotificationAndroid(deviceTokenList, pushTitle, pushMessage, pushPayload);

				if (result.equalsIgnoreCase(GC_STATUS_SUCCESS)) {

			    	APIGeneralSuccess succPayload = new APIGeneralSuccess();
			    	succPayload.setCode("SUCCESS");
			    	succPayload.setMessage("Push FCM Android Send Successfully");
			    	return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload);
				}
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_014_FCM_PROCESS_FAILED, result));
 			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, e002SessionNotMatched));
		}
    	return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	}

	@ApiOperation(value = "View Push FCM Web Status",
				  nickname = "PushFCMWebView",
				  notes = "View Push FCM Web Status")
	@ApiResponses(value = {
			@ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = APIGeneralSuccess.class),
	      @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
	      @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	      @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
	      @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
		})
	@RequestMapping(value = "/unitTest/push/fcm/web", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendPushFCMWeb(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "deviceToken", defaultValue = "", required = true) String deviceToken,
			@RequestParam(name = "adminFirstName", defaultValue = "MotivAI", required = true) String adminFirstName,
			@RequestParam(name = "pushTitle", defaultValue = "Rudu - Push FCM Web", required = true) String pushTitle,
			@RequestParam(name = "pushMessage", defaultValue = "Rudu - Push FCM Web - New Risk Alert", required = true) String pushMessage,
			@RequestParam(name = "userFirstName", defaultValue = "User 1", required = true) String userFirstName,
			@RequestParam(name = "deviceName", defaultValue = "Ride 501", required = true) String deviceName,
			@RequestParam(name = "deviceSubCategory", defaultValue = "OVER_SPPED", required = true) String deviceSubCategory,
			@RequestParam(name = "riskValue", defaultValue = "98.76", required = true) float riskValue) {

		List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		if (deviceToken.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceToken", "Invalid Device Token"));
		if (adminFirstName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("adminFirstName", "Invalid Admin First Name"));
		if (userFirstName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("userFirstName", "Invalid User First Name"));
		if (deviceName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceName", "Invalid Device Name"));
		if (deviceSubCategory.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceSubCategory", "Invalid Device Sub Category"));
		if (riskValue <= 0)
			errorList.add(new APIPreConditionErrorField("riskValue", "Invalid Risk Value"));

		if (errorList.size() <= 0) {

			JSONObject pushPayload = new JSONObject();
			pushPayload.put("pushType", "USER_RIDE_DIRECT_RISK_ALERT_TO_ADMIN");
			pushPayload.put("adminFirstName", adminFirstName);
			pushPayload.put("userFirstName", userFirstName);
			pushPayload.put("deviceName", deviceName);
			pushPayload.put("deviceSubCategory", deviceSubCategory);
			pushPayload.put("riskValue", riskValue);

			if (apiAuthorization.verifyStaticSessionId(sessionId)) {

				List<String> deviceTokenList = Arrays.asList(deviceToken);
				String result = pushProvider.sendPushNotificationWeb(deviceTokenList, pushTitle, pushMessage, pushPayload);

				if (result.equalsIgnoreCase(GC_STATUS_SUCCESS)) {

			    	APIGeneralSuccess succPayload = new APIGeneralSuccess();
			    	succPayload.setCode("SUCCESS");
			    	succPayload.setMessage("Push FCM Web Send Successfully");
			    	return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload);
				}
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_014_FCM_PROCESS_FAILED, result));
			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, e002SessionNotMatched));
		}
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	}

	@ApiOperation(value = "View Push FCM IOS Status",
				  nickname = "PushFCMIOSView",
				  notes = "View Push FCM IOS Status")
	@ApiResponses(value = {
			@ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = APIGeneralSuccess.class),
	      @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
	      @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	      @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
	      @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
		})
	@RequestMapping(value = "/unitTest/push/fcm/ios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendPushFCMIOS(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "adminUserId", defaultValue = "", required = true) String[] adminUserId,
			@RequestParam(name = "deviceToken", defaultValue = "", required = true) String deviceToken,
			@RequestParam(name = "adminFirstName", defaultValue = "Motiv AI", required = true) String adminFirstName,
			@RequestParam(name = "divisionName", defaultValue = "MotivAI", required = true) String divisionName,
			@RequestParam(name = "pushTitle", defaultValue = "Rudu - Push FCM Android", required = true) String pushTitle,
			@RequestParam(name = "pushMessage", defaultValue = "Rudu - Push FCM Android - New Risk Alert", required = true) String pushMessage,
			@RequestParam(name = "userFirstName", defaultValue = "User 1", required = true) String userFirstName,
			@RequestParam(name = "deviceName", defaultValue = "Ride 501", required = true) String deviceName,
			@RequestParam(name = "deviceSubCategory", defaultValue = "OVER_SPPED", required = true) String deviceSubCategory,
			@RequestParam(name = "riskValue", defaultValue = "98.76", required = true) float riskValue) {

		List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		if (deviceToken.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceToken", "Invalid Device Token"));
		if (adminFirstName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("adminFirstName", "Invalid Admin First Name"));
		if (userFirstName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("userFirstName", "Invalid User First Name"));
		if (divisionName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceName", "Invalid Division Name"));
		if (deviceName.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceName", "Invalid Device Name"));
		if (deviceSubCategory.length() <= 0)
			errorList.add(new APIPreConditionErrorField("deviceSubCategory", "Invalid Device Sub Category"));
		if (riskValue <= 0)
			errorList.add(new APIPreConditionErrorField("riskValue", "Invalid Risk Value"));
		if (adminUserId.length <= 0)
			errorList.add(new APIPreConditionErrorField("adminUserId", "Invalid Admin User ID"));

		if (errorList.size() <= 0) {

			JSONObject pushPayload = new JSONObject();
			pushPayload.put("pushType", "USER_RIDE_DIRECT_RISK_ALERT_TO_ADMIN");
			pushPayload.put("adminUserIdList", adminUserId);
			pushPayload.put("adminFirstName", adminFirstName);
			pushPayload.put("userFirstName", userFirstName);
			pushPayload.put("divisionName", divisionName);
			pushPayload.put("deviceName", deviceName);
			pushPayload.put("deviceSubCategory", deviceSubCategory);
			pushPayload.put("riskValue", riskValue);

			if (apiAuthorization.verifyStaticSessionId(sessionId)) {

				List<String> deviceTokenList = Arrays.asList(deviceToken);
				String result = pushProvider.sendPushNotificationAndroid(deviceTokenList, pushTitle, pushMessage, pushPayload);

				if (result.equalsIgnoreCase(GC_STATUS_SUCCESS)) {

			    	APIGeneralSuccess succPayload = new APIGeneralSuccess();
			    	succPayload.setCode("SUCCESS");
			    	succPayload.setMessage("Push FCM Android Send Successfully");
			    	return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload);
				}
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_014_FCM_PROCESS_FAILED, result));
			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, e002SessionNotMatched));
		}
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	}

}