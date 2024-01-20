package com.common.api.resource;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.common.api.constant.APIResourceName;
import com.common.api.constant.APIRolePrivilegeType;
import com.common.api.datasink.service.UserSecretService;
import com.common.api.model.UserSecret;
import com.common.api.response.APIEncryptedSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.EncryptDecrypt;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.ResultSetConversion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Secret", tags = {"User Secret"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"}) 
public class UserSecretResource extends APIFixedConstant {

	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {              
		return new PropertySourcesPlaceholderConfigurer();     
	} 

	@Autowired
	FieldValidator fieldValidator;      
	@Autowired 
	PayloadValidator payloadValidator; 
	@Autowired
	ResultSetConversion resultSetConversion;
	
	@Autowired     
	ProfileSessionRoleService profileSessionRoleService;    
	@Autowired 
	ProfileDependencyService profileDependencyService;  
	@Autowired 
	ProfileCardinalityService profileCardinalityService; 
	
    @Autowired 
    UserSecretService userSecretService; 

	@Value("${e003.profile.not.exist}")       
	String e003ProfileNotExist = ""; 
	@Value("${e006.failed.dependency}")       
	String e006FailedDependency = "";  
	
	@ApiOperation(value = "View User Secret Details", 
				  nickname = "UserSecretDetailView",  
				  notes = "View User Secret Details")
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = UserSecret.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
      })
	@RequestMapping(value = "/userSecret", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewUserSecretDetail(HttpServletRequest httpRequest,   
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,  
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId, 
			@RequestParam(name = "divisionId", defaultValue = "", required = true) String divisionId, 
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId, 
			@RequestParam(name = "type", defaultValue = "USER_SECRET", required = true) String type) { 
		
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();  
		String apiLogString     = "LOG_API_USER_SECRET_VIEW";          
    	String apiInstLogString = "LOG_INST_USER_SECRET_VIEW";       
    	  
    	String resourceType = APIResourceName.USER_SECRET.toString();
    	type	  = (type.length() >= 0) ? APIResourceName.USER.toString() : type;                       
  
    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		if (FieldValidator.isValidId(companyId) == false)        
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID)); 
		if (FieldValidator.isValidIdOptional(divisionId) == false)        
			errorList.add(new APIPreConditionErrorField("divisionId", EM_INVALID_DIVI_ID));  
		if (FieldValidator.isValidId(userId) == false)    
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));   
		if (FieldValidator.isValidInternalTextOptional(type) == false)        
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE)); 
		          
  		if (errorList.size() == 0) {   
  			  
  			String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, "");
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage();
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {     
			   
				List<String> emptyList = new ArrayList<String>(); 
				List<UserSecret> succPayload = userSecretService.viewListByCriteria(companyId, userId, "", "", emptyList, emptyList, "", "", 0, 0);   
				 
	  	  		if (succPayload.size() >= 0) {        

					String originalText = resultSetConversion.arrayListObjectToString(succPayload);  
			    	String encodedText = new EncryptDecrypt().base64Encode(originalText);  
			    	 
					APILog.writeInfoLog(apiLogString + " " + succPayload.toString());    
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return ResponseEntity.status(HttpStatus.OK.value()).body(new APIEncryptedSuccess(encodedText));      
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
	
}