package com.common.api.resource;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.common.api.datasink.service.DivisionService;
import com.common.api.datasink.service.GroupService;
import com.common.api.datasink.service.LanguageService;
import com.common.api.datasink.service.ModulePreferenceService;
import com.common.api.datasink.service.QueryService;
import com.common.api.datasink.service.UserDeviceService;
import com.common.api.datasink.service.UserLastActivityService;
import com.common.api.datasink.service.UserPreferenceService;
import com.common.api.datasink.service.UserService;
import com.common.api.datasink.service.UserSessionService;
import com.common.api.model.Division;
import com.common.api.model.Group;
import com.common.api.model.Language;
import com.common.api.model.ModulePreference;
import com.common.api.model.User;
import com.common.api.model.UserDevice;
import com.common.api.model.UserLastActivity;
import com.common.api.model.UserPreference;
import com.common.api.model.UserSession;
import com.common.api.model.field.AppPayloadSettingField;
import com.common.api.model.field.UserSessionField;
import com.common.api.model.status.ModelStatus;
import com.common.api.model.type.CategoryType;
import com.common.api.model.type.ModelType;
import com.common.api.model.type.ModelType.ModulePreferenceType;
import com.common.api.model.type.ModelType.UserPreferenceType;
import com.common.api.response.APIGeneralSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileCardinalityAccess;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.response.UserSessionResult;
import com.common.api.service.util.ProfileAutoSystematicService;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileHardwareDeviceService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.service.util.RequestService;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Session", tags = {"User Session"}) 
@RestController           
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"}) 
public class UserSessionResource extends APIFixedConstant {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {              
		return new PropertySourcesPlaceholderConfigurer();     
	}
	
	@Autowired
	ProfileHardwareDeviceService profileHardwareDeviceService; 
	@Autowired     
	ProfileSessionRoleService profileSessionRoleService;  
	@Autowired 
	ProfileDependencyService profileDependencyService; 
	@Autowired
	ProfileCardinalityService profileCardinalityService;   
	@Autowired
	ProfileAutoSystematicService profileAutoSystematicService;
	
	@Autowired
	PayloadValidator payloadValidator; 
	@Autowired 
	RequestService requestService; 
	@Autowired
	ModulePreferenceService modulePreferenceService; 
	@Autowired
	UserPreferenceService userPreferenceService;
    @Autowired 
    QueryService queryService;   
	
    @Autowired  
    UserService userService;   
    @Autowired 
    DivisionService divisionService; 
    @Autowired 
    GroupService groupService;    
    @Autowired  
    LanguageService languageService;    
    @Autowired 
    UserDeviceService userDeviceService;   
    @Autowired 
    UserSessionService userSessionService;    
    @Autowired 
    UserLastActivityService userLastActivityService; 

   	@Value("${app.user.session.remove.enable}")        
   	boolean appUserSessionRemoveEnable = false;   
   	@Value("${app.user.session.active.remove.day}")        
   	int appUserSessionActiveRemoveDay = 365;     
   	@Value("${app.user.session.inactive.remove.day}")       
   	int appUserSessionInactiveRemoveDay = 30;   
   	@Value("${app.user.next.password.change.duration.day}")        
   	int appUserNextPasswordChangeDurationDay = 90;    
   	@Value("${app.ios.stable.version.control.enable}")       
   	boolean appIosStableVersionControlEnable = false;  
   	@Value("${app.android.stable.version.control.enable}")       
   	boolean appAndroidStableVersionControlEnable = false;   
   	@Value("${app.mob.unsupported.version}")       
   	String appMobUnsupportedVersion = ""; 

   	@Value("${app.encrypted.payload.version.android}")        
   	float encryptedPayloadVersionAndroid = 0; ;   
   	@Value("${app.encrypted.payload.version.ios}")        
   	float encryptedPayloadVersionIos = 0; ;   

   	@Value("${app.sp.device.summary.count.update}")       
   	String spDeviceSummaryCountUpdate = "";  
   	
   	@Value("${s001.request.successful}")       
   	String s001RequestSuccessful = "";  
   	 
	@Value("${e003.profile.not.exist}")       
	String e003ProfileNotExist = ""; 
	@Value("${e004.usr.pwd.not.match}")      
	String e004UsrPwdNotmatch = "";   
	@Value("${e006.failed.dependency}")       
	String e006FailedDependency = "";   
	@Value("${e009.verification.process.not.completed}")       
	String e009VerificationProcessNotCompleted = "";   
	@Value("${e010.hw.device.not.matched}")       
	String e010hardwareDeviceNotMatched = "";   
	@Value("${e011.hw.device.not.matched}")       
	String e011hardwareDeviceNotMatched = "";  
	@Value("${e005.username.not.exist}")       
	String e005UsernameNotExist = "";  
	
	
    @ResponseStatus(code = HttpStatus.CREATED) 
	@ApiOperation(value = "Create User Session Details", 
				  nickname = "UserSessionDetailCreate", 
				  notes = "Create User Session Details")  
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = UserSessionResult.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/userSession", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createUserSessionDetail(HttpServletRequest httpRequest,    
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,  
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestBody UserSession userSessionReq) { 
  
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();   
 
		String apiLogString     = "LOG_API_USER_SESSION_CREATE";          
    	String apiInstLogString = "LOG_INST_USER_SESSION_CREATE";       

    	String category			= userSessionReq.getCategory();  
    	String companyIdReq		= userSessionReq.getCompanyId(); 
    	String usernameReq 		= userSessionReq.getUsername();  
    	String remoteAddress 	= httpRequest.getRemoteAddr(); 
		String userAgent 		= httpRequest.getHeader("USER-AGENT");   
    	String type			 	= userSessionReq.getType();      
    	type	  				= (type.length() <= 0) ? APIResourceName.USER_SESSION.toString() : type;  
    	String resourceType 	= APIResourceName.USER_VERIFICATION.toString();                   

    	try {
    		remoteAddress	 	= requestService.getClientIp(httpRequest); 
    	} catch (Exception errMess) { }
    	
    	float mobAppLoginVersion  = 0; 
    	String deviceUniqueId = "", deviceType = "", deviceStatus = "", mobAppLoginVersionReq = "", deviceToken = ""; 
    	UserSessionField userSessionField = userSessionReq.getUserSessionField();
    	if (userSessionField != null) {  
			deviceType	        	= userSessionField.getDeviceType();  
    		deviceUniqueId  		= userSessionField.getDeviceUniqueId();  
    		deviceToken    		    = userSessionField.getDeviceToken(); 
			deviceStatus        	= userSessionField.getDeviceStatus(); 
			mobAppLoginVersionReq	= userSessionField.getMobAppLoginVersion(); 
    	}
    	
    	String errMessageMobAppControll = "";
    	try {
    		try {
    			mobAppLoginVersion = Float.parseFloat(mobAppLoginVersionReq); 
    		} catch (Exception errMess) { } 
    		Map<String, String> replaceList = new HashMap<String, String>(); 
			replaceList.put(":OLD_APP_VERSION", mobAppLoginVersionReq);  
			errMessageMobAppControll = Util.stringReplaceByKeyList(appMobUnsupportedVersion, replaceList);
    	} catch (Exception errMess) {   
    	}
    	
    	userSessionReq.setType(type);
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<UserSession>> validationErrors = validator.validate(userSessionReq);

		List<String> emptyList = new ArrayList<String>();  
    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId); 
    	errorList.addAll(payloadValidator.isValidUserSessionPayload(GC_METHOD_POST, userSessionReq));   
    	
    	for (ConstraintViolation<UserSession> error : validationErrors) 
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));
    	
    	/** Check and Update Force Password Change */
    	String nextActivity = ModelType.UserNextActivity.FORCE_PASSWORD_CHANGE.toString(); 
    	userService.updateUserForPasswordChangeCondition(dbOperationStartTime, appUserNextPasswordChangeDurationDay, nextActivity);
    	/** Check and Update User Session */
    	this.checkAndRemoveUserSession(deviceType, deviceUniqueId, deviceToken); 
 
  		if (errorList.size() == 0) {     
  			
  			if (deviceType.equalsIgnoreCase(referredBy) && deviceType.length() > 0) {
  				
  				boolean isAllowAppVersion = this.checkUserAppVersionEligibility(companyIdReq, deviceType, mobAppLoginVersion); 
  				 
	  			if (isAllowAppVersion == true) {  
	  				 
		  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();
		  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyIdReq, "", "");
					String roleStatucCode = roleStatus.getCode();			 
					String roleStatucMess = roleStatus.getMessage();
					 
					if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {    
						
						ProfileDependencyAccess profileDependency = profileDependencyService.userSessionProfileDependencyStatus(GC_METHOD_POST, userSessionReq); 
						String profileDependencyCode = profileDependency.getCode(); 
						String profileDependencyMess = profileDependency.getMessage();
								
						if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {	
						 
							List<User> userExist = userService.viewCaseInsensitiveListByCriteria("", "", "", "", "", usernameReq, "", "", emptyList, emptyList, "", "", 0, 0);
							
							if (userExist.size() > 0) {

								String passwordReq = userSessionReq.getPassword();  
								
								User userDetail   = userExist.get(0); 
								String userId	  = userDetail.getId(); 
								String status	  = userDetail.getStatus(); 
								String companyId  = userDetail.getCompanyId(); 
								String groupId    = userDetail.getGroupId();   
								String divisionId = userDetail.getDivisionId(); 
								String moduleId   = userDetail.getModuleId();	  
								String languageId = userDetail.getLanguageId();
								String categoryDB = userDetail.getCategory();    
								String passwordDB = userDetail.getPassword();  
 
								if (passwordReq.equals(passwordDB) && category.equalsIgnoreCase(CategoryType.UserSession.USER_PASSWORD.toString()) && 
									status.equals(ModelStatus.UserStatus.ACTIVE.toString())) {
 
									ProfileCardinalityAccess profileCardinality = profileCardinalityService.userSessionProfileCardinalityStatus(GC_METHOD_POST, userSessionReq); 
									String profileCardinalityCode = profileCardinality.getCode();
									String profileCardinalityMess = profileCardinality.getMessage();
									 
									if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {      
										
										int isAllowAppDevice = this.checkUserDeviceEligibility(companyId, userId, deviceType, deviceUniqueId, deviceStatus);   
										
										if (isAllowAppDevice == 1 || isAllowAppDevice == 2 || isAllowAppDevice == 3) {       
												 
								  			userSessionReq.setCompanyId(companyId); 
								  			userSessionReq.setGroupId(groupId); 
								  			userSessionReq.setDivisionId(divisionId);
								  			userSessionReq.setModuleId(moduleId); 
								  			userSessionReq.setUserId(userId); 
								  			
								  			userSessionReq.setRemoteAddress(remoteAddress); 
								  			userSessionReq.setUserAgent(userAgent);    
								  			userSessionReq.setStatus(DV_ACTIVE);  
								  			userSessionReq.setCreatedAt(dbOperationStartTime); 
								  			userSessionReq.setModifiedAt(dbOperationStartTime);  
								  			userSessionReq.setCreatedBy(userId);
								  			userSessionReq.setModifiedBy(userId);    
								  			
								  			userSessionReq.setDeviceUniqueId(deviceUniqueId); 
								  			userSessionReq.setDeviceToken(deviceToken);  
								  			userSessionReq.setDeviceStatus(deviceStatus);  
								  			userSessionReq.setAppVersion(mobAppLoginVersion);    
								  			userSessionReq.setType(deviceType);  
								  			
								  			UserSession userSession = userSessionService.createUserSession(userSessionReq); 
								  			
								  			if (userSession != null && userSession.getId() != null && userSession.getId().length() > 0) {
								  				
								  				this.checkAndUpdateUserLastActivity(userDetail, categoryDB, deviceType, userId);  
								  				UserSessionResult succPayload = new UserSessionResult(userDetail, userSession);  
								  				succPayload.setAppPayloadSettingField(new AppPayloadSettingField(encryptedPayloadVersionAndroid, encryptedPayloadVersionIos)); 
								  				
								  				this.checkAndAddAdditionalProfile(succPayload, companyId, groupId, divisionId, moduleId, userId, languageId, deviceType);
								  				try {  
									  				profileHardwareDeviceService.createHardwareDevice(companyId, divisionId, moduleId, userId, userSessionField, deviceStatus); 
								  				} catch (Exception errMess) { }
								  				
								  				try {
								  					if (spDeviceSummaryCountUpdate.length() > 0) 
								  						queryService.updateByCriteria(spDeviceSummaryCountUpdate, companyId, "", "", "", userId, "", "", "", "", "", GC_ACTION_UPDATE); 
								  				} catch (Exception errMess) { }
								  				
												APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
												APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
												return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);  
								  			}   
											return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));  
										
										} else if (isAllowAppDevice == 3) { 
											return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_010_HW_DEVICE_MISMATCHED, e010hardwareDeviceNotMatched));  
										} else {
											return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_011_HW_DEVICE_MISMATCHED, e011hardwareDeviceNotMatched));  
										}
									}
									return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileCardinalityMess));
								
								} else if (passwordReq.equals(passwordDB) && category.equalsIgnoreCase(CategoryType.UserSession.USER_PASSWORD.toString()) && 
										   status.equals(ModelStatus.UserStatus.REGISTERED.toString())) {  
									return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_009_VERIFY_PROCESS_NOT_CMPTD, e009VerificationProcessNotCompleted));
								} else { 
									return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_004_USR_PWD_NOT_MATCH, e004UsrPwdNotmatch));
								}  
							}  
							return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_003_PROFILE_NOT_EXIST, e005UsernameNotExist));   
						}
						return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileDependencyMess));
		 
					} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) {
						return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));  
					} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
						return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, roleStatucMess));
					} 
	  			}
  			} 
			return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, errMessageMobAppControll));
  		}
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));  
    }
    
    /**
    @ResponseStatus(code = HttpStatus.CREATED) 
	@ApiOperation(value = "Create User Session Authentication Details", 
				  nickname = "UserSessionAuthenticationDetailCreate", 
				  notes = "Create User Authentication Session Details")  
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = UserSessionResult.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/userSessionAuthentication", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createUserSessionAuthenticationDetail(HttpServletRequest httpRequest,    
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,  
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestBody UserSession userSessionReq) { 
  
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();   
		
		String apiLogString     = "LOG_API_USER_SESSION_AUTHENTICATION_CREATE";          
    	String apiInstLogString = "LOG_INST_USER_SESSION_AUTHENTICATION_CREATE";       

    	String category			= userSessionReq.getCategory();  
    	String companyIdReq		= userSessionReq.getCompanyId();  
    	String usernameReq 		= userSessionReq.getUsername();  
    	String passwordReq 		= userSessionReq.getPassword();  
    	String mobilePinReq 	= userSessionReq.getMobilePin();
    	String remoteAddress 	= httpRequest.getRemoteAddr(); 
		String userAgent 		= httpRequest.getHeader("USER-AGENT");   
    	String type			 	= userSessionReq.getType();      
    	type	  				= (type.length() <= 0) ? APIResourceName.USER_SESSION.toString() : type;  
    	String resourceType 	= APIResourceName.USER_VERIFICATION.toString();                   

    	try {
    		remoteAddress	 	= requestService.getClientIp(httpRequest); 
    	} catch (Exception errMess) { }
    	
    	float mobAppLoginVersion  = 0; 
    	String deviceUniqueId = "", deviceType = "", deviceStatus = "", mobAppLoginVersionReq = "", deviceToken = ""; 
    	UserSessionField userSessionField = userSessionReq.getUserSessionField();
    	if (userSessionField != null) {  
			deviceType	        	= userSessionField.getDeviceType();  
    		deviceUniqueId  		= userSessionField.getDeviceUniqueId();  
    		deviceToken    		    = userSessionField.getDeviceToken(); 
			deviceStatus        	= userSessionField.getDeviceStatus(); 
			mobAppLoginVersionReq	= userSessionField.getMobAppLoginVersion(); 
    	}
    	
    	String errMessageMobAppControll = "";
    	try {
    		try {
    			mobAppLoginVersion = Float.parseFloat(mobAppLoginVersionReq); 
    		} catch (Exception errMess) { } 
    		Map<String, String> replaceList = new HashMap<String, String>(); 
			replaceList.put(":OLD_APP_VERSION", mobAppLoginVersionReq);  
			errMessageMobAppControll = Util.stringReplaceByKeyList(appMobUnsupportedVersion, replaceList);
    	} catch (Exception errMess) {   
    	}
    	
    	userSessionReq.setType(type);
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<UserSession>> validationErrors = validator.validate(userSessionReq);

		List<String> emptyList = new ArrayList<String>();  
    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId); 
    	errorList.addAll(payloadValidator.isValidUserSessionPayload(GC_METHOD_POST, userSessionReq));   
    	
    	for (ConstraintViolation<UserSession> error : validationErrors) 
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));
    	
    	// Check and Update Force Password Change 
    	String nextActivity = ModelType.UserNextActivity.FORCE_PASSWORD_CHANGE.toString(); 
    	userService.updateUserForPasswordChangeCondition(dbOperationStartTime, appUserNextPasswordChangeDurationDay, nextActivity);
    	// Check and Update User Session 
    	this.checkAndRemoveUserSession(deviceType, deviceUniqueId, deviceToken); 
    	
  		if (errorList.size() == 0) {     
  			
  			if (deviceType.equalsIgnoreCase(referredBy) && deviceType.length() > 0) {
  				
  				boolean isAllowAppVersion = this.checkUserAppVersionEligibility(companyIdReq, deviceType, mobAppLoginVersion); 
  				 
	  			if (isAllowAppVersion == true) {  
	  				 
		  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();  
		  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyIdReq, "", "");
					String roleStatucCode = roleStatus.getCode();			 
					String roleStatucMess = roleStatus.getMessage();
					 
					if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {    
						
						ProfileDependencyAccess profileDependency = profileDependencyService.userSessionProfileDependencyStatus(GC_METHOD_POST, userSessionReq); 
						String profileDependencyCode = profileDependency.getCode(); 
						String profileDependencyMess = profileDependency.getMessage();
								
						if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {  
						 
							List<User> userExist = userService.viewListByCriteria("", "", "", "", "", usernameReq, "", "", emptyList, emptyList, "", "", 0, 0);
							
							if (userExist.size() > 0) {
								
								User userDetail   = userExist.get(0); 
								String userId	  = userDetail.getId(); 
								String status	  = userDetail.getStatus(); 
								String companyId  = userDetail.getCompanyId(); 
								String groupId    = userDetail.getGroupId(); 
								String password   = userDetail.getPassword();
								String mobilePin  = userDetail.getMobilePin();  
								String divisionId = userDetail.getDivisionId(); 
								String moduleId   = userDetail.getModuleId();	  
								String languageId = userDetail.getLanguageId();
								String categoryDB  = userDetail.getCategory();  	   

								// For Temporarily Purpose Starts 
								if (categoryDB.equalsIgnoreCase(ModelType.UserType.USER.toString())) { 
									List<String> passwordList = Arrays.asList("SAdmin@123", "SAdmin@2022", "Admin@123", "User@123");
									if (passwordList.contains(password)) {
										userDetail.setPassword(passwordReq);  
										userService.updateUserPassword(userDetail);    
									}									
									password = passwordReq; 	
								}
								// For Temporarily Purpose Ends
		 
								if (((passwordReq.equals(password) && category.equalsIgnoreCase(CategoryType.UserSession.USER_PASSWORD.toString())) || 
									 (mobilePinReq.equals(mobilePin) && category.equalsIgnoreCase(CategoryType.UserSession.USER_MOBILE_PIN.toString()))) && 
									 status.equals(ModelStatus.UserStatus.ACTIVE.toString())) {
									
									ProfileCardinalityAccess profileCardinality = profileCardinalityService.userSessionProfileCardinalityStatus(GC_METHOD_POST, userSessionReq); 
									String profileCardinalityCode = profileCardinality.getCode();
									String profileCardinalityMess = profileCardinality.getMessage();
									
									if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {     
										
										int isAllowAppDevice = this.checkUserDeviceEligibility(companyId, userId, deviceType, deviceUniqueId, deviceStatus);   
										
										if (isAllowAppDevice == 1 || isAllowAppDevice == 2 || isAllowAppDevice == 3) {       
											
								  			userSessionReq.setCompanyId(companyId); 
								  			userSessionReq.setGroupId(groupId); 
								  			userSessionReq.setDivisionId(divisionId);
								  			userSessionReq.setModuleId(moduleId); 
								  			userSessionReq.setUserId(userId); 
								  			
								  			userSessionReq.setRemoteAddress(remoteAddress); 
								  			userSessionReq.setUserAgent(userAgent);    
								  			userSessionReq.setStatus(DV_ACTIVE);  
								  			userSessionReq.setCreatedAt(dbOperationStartTime); 
								  			userSessionReq.setModifiedAt(dbOperationStartTime);  
								  			userSessionReq.setCreatedBy(userId);
								  			userSessionReq.setModifiedBy(userId);    
								  			
								  			userSessionReq.setDeviceUniqueId(deviceUniqueId); 
								  			userSessionReq.setDeviceToken(deviceToken);  
								  			userSessionReq.setDeviceStatus(deviceStatus);  
								  			userSessionReq.setAppVersion(mobAppLoginVersion);   
								  			userSessionReq.setType(deviceType);  
								  			
								  			UserSession userSession = userSessionService.createUserSession(userSessionReq); 
								  			
								  			if (userSession != null && userSession.getId() != null && userSession.getId().length() > 0) {
								  				
								  				this.checkAndUpdateUserLastActivity(userDetail, categoryDB, deviceType, userId);  
								  				UserSessionResult succPayload = new UserSessionResult(userDetail, userSession);  
								  				this.checkAndAddAdditionalProfile(succPayload, companyId, groupId, divisionId, moduleId, userId, languageId, deviceType);
								  				try {  
									  				profileHardwareDeviceService.createHardwareDevice(companyId, divisionId, moduleId, userId, userSessionField, deviceStatus); 
								  				} catch (Exception errMess) { }
								  				
												APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
												APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
												return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);  
								  			}   
											return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));  
										
										} else if (isAllowAppDevice == 3) { 
											return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_010_HW_DEVICE_MISMATCHED, e010hardwareDeviceNotMatched));  
										} else {
											return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_011_HW_DEVICE_MISMATCHED, e011hardwareDeviceNotMatched));  
										}
									}
									return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileCardinalityMess));
								
								} else if (((passwordReq.equals(password) && category.equalsIgnoreCase(CategoryType.UserSession.USER_PASSWORD.toString())) || 
											(mobilePinReq.equals(mobilePin) && category.equalsIgnoreCase(CategoryType.UserSession.USER_MOBILE_PIN.toString()))) && 
											status.equals(ModelStatus.UserStatus.REGISTERED.toString())) {  
									return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_009_VERIFY_PROCESS_NOT_CMPTD, e009VerificationProcessNotCompleted));
								} else {
									return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_004_USR_PWD_NOT_MATCH, e004UsrPwdNotmatch));
								} 
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
  			} 
			return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, errMessageMobAppControll));
  		}
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));  
    } **/
    
    @ResponseStatus(code = HttpStatus.ACCEPTED) 
   	@ApiOperation(value = "Remove User Session Details", 
   				  nickname = "UserSessionDetailRemove", 
   				  notes = "Remove User Session Details")   
   	@ApiResponses(value = { 
               @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = APIGeneralSuccess.class),
               @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
               @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
               @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
               @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
             })
   	@RequestMapping(value = "/userSession", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeUserSessionDetail(HttpServletRequest httpRequest,    
   			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "companyId", defaultValue = "", required = true) String companyId, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId) { 
    
   		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();  
   		String apiLogString     = "LOG_API_USER_SESSION_REMOVE";          
       	String apiInstLogString = "LOG_INST_USER_SESSION_REMVOE";       
       	  
   		List<String> emptyList = new ArrayList<String>(); 
       	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);  

       	String type	= APIResourceName.USER_SESSION.toString();     
    	String resourceType 	= APIResourceName.USER_VERIFICATION.toString();                   
    	
       	if (errorList.size() == 0) { 

  			String rolePrivilegeType = type + APIRolePrivilegeType._DELETE.toString();   
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage(); 
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {     
					 
				List<UserSession> userSessionList = userSessionService.viewListByCriteria("", "", sessionId, "", emptyList, emptyList, "", "", 0, 0);   
				
		  		if (userSessionList.size() > 0) {        
		  			 
		  			int userSessionStatus = userSessionService.removeUserSessionById(sessionId);  
		  			
		  			if (userSessionStatus > 0) { 
		  				
		  				APIGeneralSuccess succPayload = new APIGeneralSuccess(SC_001_REQUEST_SUCCESSFUL, s001RequestSuccessful); 
		  				  
						APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
						APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
						return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(succPayload);  
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
    
    public void checkAndUpdateUserLastActivity(User userDetail, String userCategory, String deviceType, String userId) {
    	
    	try {
    		
	    	Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeLogOperation(); 
	    	Timestamp sessionWebAt = null, sessionIosAt = null, sessionAndroidAt = null;
	    	if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_WEB))
	    		sessionWebAt = dbOperationStartTime;
	    	else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_IOS)) 
	    		sessionIosAt = dbOperationStartTime;
	    	else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID))
	    		sessionAndroidAt = dbOperationStartTime;
	    	 
	    	//if (!userCategory.equalsIgnoreCase(ModelType.UserType.USER.toString())) {
		    	List<String> typeList = Arrays.asList(ModelType.UserLastActivityType.USER_LAST_ACTIVITY.toString());
		    	List<UserLastActivity> ulaList = userLastActivityService.viewListByCriteria("", "", "", "", userId, "", typeList, "", "", 0, 0);
		    	if (ulaList.size() <= 0) {
		    		profileAutoSystematicService.createUserLastActivityByUser(userDetail); 
		    	}
	    	//} 
    		userLastActivityService.updateUserLastActivityBySession(userId, dbOperationStartTime, sessionWebAt, sessionIosAt, sessionAndroidAt); 

	    } catch (Exception errMess) {  
    	}
    }
    
    public void checkAndRemoveUserSession(String deviceType, String deviceUniqueId, String deviceToken) {  
		
    	String apiLogString    = "LOG_API_USER_SESSION_REMOVE_RECORD: ";
    	String apiLogStringError = ""; 
    	int activeUSessionRemovedCount = 0, inactiveUSessionRemovedCount = 0;
    	int androidInactiveUSCount = 0, iosInactiveUSCount = 0, webInactiveUSCount = 0; 
    	
    	Timestamp dbOperRemveTimeTempActive   = APIDateTime.convertToAnotherDateTime(GC_ACTION_MINUS, 0, 0, appUserSessionActiveRemoveDay, 0, 0, 0);  
    	Timestamp dbOperRemveTimeTempInactive = APIDateTime.convertToAnotherDateTime(GC_ACTION_MINUS, 0, 0, appUserSessionInactiveRemoveDay, 0, 0, 0);  

    	if (appUserSessionRemoveEnable == true) {       
    		
    		try { 
		    	activeUSessionRemovedCount   = userSessionService.removeActiveUserSessionByModifiedAt(dbOperRemveTimeTempActive);   
	 	    	inactiveUSessionRemovedCount = userSessionService.removeInactiveUserSessionByModifiedAt(dbOperRemveTimeTempInactive);   
		    	if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {   
		    		if (deviceUniqueId.length() > 0)  
		    			androidInactiveUSCount = userSessionService.removeUserSessionByTypeAndDeviceUniqueId(deviceType, deviceUniqueId); 
	 			} else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_IOS)) { 
					if (deviceUniqueId.length() > 0) 
						iosInactiveUSCount = userSessionService.removeUserSessionByTypeAndDeviceUniqueId(deviceType, deviceUniqueId);  
	 			} else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_WEB)) {  
					if (deviceToken.length() > 0) 
						webInactiveUSCount = userSessionService.removeUserSessionByTypeAndDeviceToken(deviceType, deviceToken); 
	 			}  
    		} catch (Exception errMess) { 
    			apiLogStringError = errMess.getMessage(); 
    		}    
    	} 
    	
    	String logString = " dbOperRemveTimeTempInactive: " + dbOperRemveTimeTempInactive + " dbOperRemveTimeTempActive: " + dbOperRemveTimeTempActive; 
    	logString = logString + " activeUSessionRemovedCount: " + activeUSessionRemovedCount + " inactiveUSessionRemovedCount: " + inactiveUSessionRemovedCount; 
    	logString = logString + " androidInactiveUSCount: " + androidInactiveUSCount + " iosInactiveUSCount: " + iosInactiveUSCount + " webInactiveUSCount: " + webInactiveUSCount; 

    	APILog.writeInfoLog(apiLogString + logString + " apiLogStringError: " + apiLogStringError);    

    }
    
    public boolean checkUserAppVersionEligibility(String companyIdReq, String deviceType, float mobAppLoginVersion) {
    	
    	boolean isAllowAppVersion = false;
    	List<String> emptyList = new ArrayList<String>(); 
    	
    	if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_WEB)) {
			isAllowAppVersion = true; 
		} else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_IOS)) {   

			if (appIosStableVersionControlEnable == true) {
				
				List<String> typeList = new ArrayList<String>();
				typeList.add(ModelType.ModulePreferenceType.IOS_APP_VERSION.toString());  
				List<ModulePreference> modulePreferenceList = modulePreferenceService.viewListByCriteria(companyIdReq, "", "", "", "", "", "", emptyList, typeList, "", "", 0, 0); 

				if (modulePreferenceList.size() > 0) {	
  					
					ModulePreference modulePreference = modulePreferenceList.get(0);
					String currentStableVersion = modulePreference.getCurrentStableVersion();
					String minimumStableVersion = modulePreference.getMinimumStableVersion(); 
					float currnetVersion = Float.parseFloat(currentStableVersion); 
					float minimumVersion = Float.valueOf(minimumStableVersion);  
					
					if (mobAppLoginVersion >= minimumVersion && mobAppLoginVersion <= currnetVersion) {
						isAllowAppVersion = true;  
					} 
				}
			} else { 
				isAllowAppVersion = true;  
			} 
			
		} else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {
			
			if (appAndroidStableVersionControlEnable == true) {
				
				List<String> typeList = new ArrayList<String>();
				typeList.add(ModelType.ModulePreferenceType.ANDROID_APP_VERSION.toString());  
				List<ModulePreference> modulePreferenceList = modulePreferenceService.viewListByCriteria(companyIdReq, "", "", "", "", "", "", emptyList, typeList, "", "", 0, 0); 

				if (modulePreferenceList.size() > 0) {	
  					
					ModulePreference modulePreference = modulePreferenceList.get(0);
					String currentStableVersion = modulePreference.getCurrentStableVersion();
					String minimumStableVersion = modulePreference.getMinimumStableVersion(); 
					float currnetVersion = Float.parseFloat(currentStableVersion); 
					float minimumVersion = Float.valueOf(minimumStableVersion);  
					
					if (mobAppLoginVersion >= minimumVersion &&  mobAppLoginVersion <= currnetVersion) {
						isAllowAppVersion = true;
					} 
				}
			} else { 
				isAllowAppVersion = true;  
			} 
		}
    	return isAllowAppVersion; 
    }
    
    public int checkUserDeviceEligibility(String companyId, String userId, String deviceType, String deviceUniqueId, String deviceStatus) {
    	
    	int isAllowAppDevice = 0;
    	
    	List<String> statusList = Arrays.asList(ModelStatus.DeviceStatus.REGISTERED.toString()); 
		List<String> typeList   = Arrays.asList(APIResourceName.USER_DEVICE.toString()); 
			
		if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID) || deviceType.equalsIgnoreCase(GC_REFERRED_BY_IOS)) {  
			List<UserDevice> userDeviceList = userDeviceService.viewListByCriteria(companyId, userId, "", "", deviceUniqueId, "", "", deviceType, statusList, typeList, "", "", 0, 0);
			if ((userDeviceList.size() > 0 || deviceStatus.equalsIgnoreCase(GC_STAtUS_DEFAULT) || deviceStatus.equalsIgnoreCase(GC_STATUS_AUDIT)) && deviceStatus.length() > 0) {   
				isAllowAppDevice = 2;      
			} else { 
				isAllowAppDevice = 3;    
			}   
		} else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_WEB)) {
			isAllowAppDevice = 1;
		}
		return isAllowAppDevice; 
    }
    
    public UserSessionResult checkAndAddAdditionalProfile(UserSessionResult succPayload, String companyId, String groupId, String divisionId, String moduleId, String userId, String languageId, String deviceType) {
 
    	List<String> emptyList = new ArrayList<String>(); 
    	
		/** Group Object */
		try {  
			if (groupId.length() > 0) {
				List<Group> groupList = groupService.viewListByCriteria(companyId, groupId, "", "", "", emptyList, emptyList, "", "", 0, 0);
				if (groupList.size() > 0) {
					Group groupDetail = groupList.get(0);
					succPayload.setGroup(groupDetail);   
				}
			} 
		} catch (Exception errMess) { }
		
		/** Division Object */
		try {  
			List<Division> divisionList = divisionService.viewListByCriteria(companyId, "", divisionId, "", "", emptyList, emptyList, emptyList, "", "", 0, 0);
			if (divisionList.size() > 0) {
				Division devisionDetail = divisionList.get(0);
				succPayload.setDivision(devisionDetail);  
			}
		} catch (Exception errMess) { }
		
		/** Language Object */
		try {   
			if (languageId.length() > 0) { 
				List<Language> languageList = languageService.viewListByCriteria(companyId, languageId, "", "", emptyList, emptyList, "", "", 0, 0); 
				if (languageList.size() > 0) {  
					Language languageDetail = languageList.get(0); 
					succPayload.setLanguage(languageDetail);   
				} 
			} 
		} catch (Exception errMess) { }
		
		/** Module Preference */  
		try {   
			
			List<String> mpTypeListCommon = new ArrayList<String>(); 
			if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {
				mpTypeListCommon.add(ModulePreferenceType.ANDROID_APP_VERSION.toString());
				mpTypeListCommon.add(ModulePreferenceType.ANDROID_LIBRARY_ZIP.toString());
			} else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_IOS)) {
				mpTypeListCommon.add(ModulePreferenceType.IOS_APP_VERSION.toString());
				mpTypeListCommon.add(ModulePreferenceType.IOS_LIBRARY_ZIP.toString()); 
			} 
			
			List<String> mpTypeList = Arrays.asList(ModulePreferenceType.MODULE_APP_BOOT_SETTING.toString());  
			List<ModulePreference> modulePrefList 		= modulePreferenceService.viewListByCriteria(companyId, "", divisionId, moduleId, "", "", "", emptyList, mpTypeList, "", "", 0, 0); 
			if (mpTypeListCommon.size() > 0) {
				List<ModulePreference> modulePrefListCommon = modulePreferenceService.viewListByCriteria(companyId, "", "", "", "", "", "", emptyList, mpTypeListCommon, "", "", 0, 0); 
				modulePrefList.addAll(modulePrefListCommon);  
			} 
			succPayload.setModulePreferences(modulePrefList);
			
		} catch (Exception errMess) { }
		
		/** User Preference */
		try {
			List<String> upTypeList = new ArrayList<String>(); 
				upTypeList.add(UserPreferenceType.USER_APP_BOOT_SETTING.toString());
				upTypeList.add(UserPreferenceType.APP_SETTINGS.toString()); 
			List<UserPreference> userPrefList = userPreferenceService.viewListByCriteria(companyId, divisionId, moduleId, userId, "", "", "", emptyList, emptyList, emptyList, upTypeList, "", "", 0, 0);
			succPayload.setUserPreferences(userPrefList); 
		} catch (Exception errMess) { }
			
		return succPayload; 
			
    }
    
}