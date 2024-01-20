package com.common.api.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.common.api.datasink.service.QueryService;
import com.common.api.model.DeviceData;
import com.common.api.model.type.NotificationModelType;
import com.common.api.response.APIGeneralSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.service.util.RequestService;
import com.common.api.service.util.ServiceAnalyticsGateway;
import com.common.api.service.util.ServiceFCMNotification;
import com.common.api.util.APIAuthorization;
import com.common.api.util.APIDateTime;
import com.common.api.util.FieldValidator;
import com.common.api.util.FileValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.ResultSetConversion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Health", tags = {"Health"}) 
@RestController 
@PropertySource({ "classpath:application.properties" })
public class HealthResource extends APIFixedConstant {       
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {  
		return new PropertySourcesPlaceholderConfigurer();
	} 
 
	@Autowired 
	private RequestService requestService; 
    @Autowired
    ServiceFCMNotification serviceFCMNotification;  	 
	@Autowired
	APIAuthorization apiAuthorization;  
	@Autowired
	APIDateTime apiDateTime; 
	@Autowired
	ServiceAnalyticsGateway serviceAnalyticsGateway;  
	@Autowired
	FileValidator fileValidator;  
	@Autowired
	ResultSetConversion resultSetConversion;  
	@Autowired 
	PayloadValidator payloadValidator;
    @Autowired 
    QueryService queryService;   
	  
	/** Properties Constants */    
	@Value("${app.application.name}")           
	String appApplicationName = "";     
	@Value("${app.module.name.common}")             
	String appModuleName = "";      

	@Value("${e002.session.not.matched}")               
	private String e002SessionNotMatched = "";
   	@Value("${app.sp.device.summary.count.update}")       
   	String spDeviceSummaryCountUpdate = "";  
 
	@ApiOperation(value = "View Health Status", 
				  nickname = "HealthView", 
				  notes = "View Server Health Status")
	@ApiResponses(value = { 
			@ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = APIGeneralSuccess.class), 
            @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
    	})
	@RequestMapping(value = "/health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewHealth() {         
		
    	String healthMessage = appApplicationName + " - " + appModuleName;    
    	 
    	APIGeneralSuccess succPayload = new APIGeneralSuccess();	       
    	succPayload.setCode("SUCCESS");
    	succPayload.setMessage(healthMessage);
	
    	String companyId  = "comp00000000000000000001";
    	String userId     = "654a359d9dd2c000a42a3870";
    	String deviceId   = "659392ceb0757f4040f3b478";
    	
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();    
		try {
			if (spDeviceSummaryCountUpdate.length() > 0) 
				results = queryService.updateByCriteria(spDeviceSummaryCountUpdate, companyId, "", "", "", userId, "", "", "", deviceId, "", ""); 
		} catch (Exception errMess) { }
    	 
    	return ResponseEntity.status(HttpStatus.OK.value()).body(results);    
	}
	
	@ApiOperation(value = "View Remote Address", 
				  nickname = "RemoteAddress", 
				  notes = "View  Remote Address")    
	@ApiResponses(value = { 
			@ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = APIGeneralSuccess.class), 
	    @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
		})
	@RequestMapping(value = "/remoteAddress", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewRemoteAddress(HttpServletRequest httpRequest) {  
		 
		String remoteAddress = httpRequest.getRemoteAddr();    
		String clientIP 	 = requestService.getClientIp(httpRequest); 
		String responseMessage = "remoteAddress => " + remoteAddress + " clientIP => " + clientIP;    
		 
		APIGeneralSuccess succPayload = new APIGeneralSuccess();       
		succPayload.setCode("SUCCESS");      
		succPayload.setMessage(responseMessage);     
		
		return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload);      
	}
	
	@ApiOperation(value = "View FCM Health", 
			  nickname = "HealthFCMView", 
			  notes = "View FCM Status")  
	@ApiResponses(value = {  
			@ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = APIGeneralSuccess.class), 
	      @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
		}) 
	@RequestMapping(value = "/health/fcm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewHealthFCM(	 
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,  
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "pushType", defaultValue = "", required = true) String pushType) {    
		 
		List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId); 
		if (FieldValidator.isValidId(companyId) == false)        
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID)); 
		if (FieldValidator.isValidId(divisionId) == false)        
			errorList.add(new APIPreConditionErrorField("divisionId", EM_INVALID_DIVI_ID));   
		if (FieldValidator.isValidId(userId) == false)        
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));    
		if (FieldValidator.isValid(pushType, VT_TYPE, 10, 300, true) == false)         
			errorList.add(new APIPreConditionErrorField("pushType", EM_INVALID_PUSH_TYPE));     
 			
		if (errorList.size() == 0) { 
			
			if (apiAuthorization.verifyStaticSessionId(sessionId) == true) { 

				String result = GC_STATUS_ERROR;
				
				if (pushType.equalsIgnoreCase(NotificationModelType.pushType.USER_RIDE_DIRECT_RISK_ALERT_TO_ADMIN.toString())) { 
					DeviceData deviceDataReq = new DeviceData();
					deviceDataReq.setCompanyId(companyId);
					deviceDataReq.setDivisionId(divisionId);
					deviceDataReq.setUserId(userId);    
					result = serviceFCMNotification.sendUserRideRiskAlertLiveToAdmin(deviceDataReq);   
				} else if (pushType.equalsIgnoreCase(NotificationModelType.pushType.USER_APP_BOOT_SETTING.toString())) { 
					result = serviceFCMNotification.sendUserAppBootSettingAlertToUser(companyId, divisionId, userId);   
				} 
				
				if (result.equalsIgnoreCase(GC_STATUS_SUCCESS)) { 
					
					APIGeneralSuccess succPayload = new APIGeneralSuccess(); 
					succPayload.setCode("SUCCESS");  
					return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload); 
					 
				} 
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, "Request Process Failed"));
			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, e002SessionNotMatched)); 

		}
 		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));  
	} 
	  
}