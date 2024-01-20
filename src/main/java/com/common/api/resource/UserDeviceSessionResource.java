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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.datasink.service.UserDeviceSessionService;
import com.common.api.datasink.service.UserService;
import com.common.api.model.User;
import com.common.api.model.UserDeviceSession;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.UserSessionResult;
import com.common.api.service.util.RequestService;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Device Session", tags = {"User Device Session"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class UserDeviceSessionResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	RequestService requestService;

    @Autowired
    UserService userService;
    @Autowired
    UserDeviceSessionService userDeviceSessionService;

	@Value("${app.static.session.id}")
	private String appStaticSessionId = "";

	@Value("${e003.profile.not.exist}")
	String e003ProfileNotExist = "";
	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";

    @ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Create User Device Session Details",
				  nickname = "UserDeviceSessionDetailCreate",
				  notes = "Create User Device Session Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = UserSessionResult.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/userDeviceSession", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createUserDeviceSessionDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody UserDeviceSession userDeviceSessionReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_DEVICE_SESSION_CREATE";
    	String apiInstLogString = "LOG_INST_USER_DEVICE_SESSION_CREATE";

    	String userId			= userDeviceSessionReq.getUserId();
    	String remoteAddress 	= httpRequest.getRemoteAddr();
    	String type			 	= userDeviceSessionReq.getType();
		String userAgent 		= httpRequest.getHeader("USER-AGENT");
    	type	  				= (type.length() <= 0) ? APIResourceName.USER_DEVICE_SESSION.toString() : type;

    	try {
    		remoteAddress	 	= requestService.getClientIp(httpRequest);
    	} catch (Exception errMess) { }

    	userDeviceSessionReq.setType(type);
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<UserDeviceSession>> validationErrors = validator.validate(userDeviceSessionReq);

		List<String> emptyList = new ArrayList<>();
    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
    	//Timestamp dbOperRemveStartTimeTemp = APIDateTime.convertToAnotherDateTime(GC_ACTION_MINUS, 0, 0, 0, userSessionRemoveDuration, 0, 0);
    	//userDeviceSessionService.removeUserDeviceSessionByModifiedAt(dbOperRemveStartTimeTemp);

    	for (ConstraintViolation<UserDeviceSession> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

			List<User> userExist = userService.viewListByCriteria("", "", "", "", userId, "", "", "", emptyList, emptyList, "", "", 0, 0);

			if (userExist.size() > 0) {

			  	userDeviceSessionReq.setRemoteAddress(remoteAddress);
			  	userDeviceSessionReq.setUserAgent(userAgent);
			  	userDeviceSessionReq.setStatus(GC_STATUS_REGISTERED);
			  	userDeviceSessionReq.setCreatedAt(dbOperationStartTime);
			  	userDeviceSessionReq.setModifiedAt(dbOperationStartTime);
			  	userDeviceSessionReq.setCreatedBy(userId);
			  	userDeviceSessionReq.setModifiedBy(userId);

			  	UserDeviceSession succPayload = userDeviceSessionService.createUserDeviceSession(userDeviceSessionReq);

			  	if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

					APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
	  			}
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
 			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_003_PROFILE_NOT_EXIST, e003ProfileNotExist));
 		}
 		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
    }

}