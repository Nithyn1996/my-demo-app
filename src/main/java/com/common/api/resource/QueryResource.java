package com.common.api.resource;
 
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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
import com.common.api.datasink.service.QueryMapService;
import com.common.api.datasink.service.QueryService;
import com.common.api.model.QueryMap;
import com.common.api.model.UserSession;
import com.common.api.response.APIEncryptedSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.util.APIAuthorization;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.EncryptDecrypt;
import com.common.api.util.FieldValidator;
import com.common.api.util.ResultSetConversion;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Query", tags = {"Query"}) 
@RestController          
public class QueryResource extends APIFixedConstant {  

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {              
		return new PropertySourcesPlaceholderConfigurer();     
	} 
 
	@Autowired  
	FieldValidator fieldValidator;
	@Autowired     
	ProfileSessionRoleService profileSessionRoleService;  
	@Autowired
	ResultSetConversion resultSetConversion;

	@Autowired
	APIAuthorization apiAuthorization;  
    @Autowired 
    QueryService queryService;   
    @Autowired 
    QueryMapService queryMapService;
    
   	@Value("${app.encrypted.payload.version.android}")        
   	float encryptedPayloadVersionAndroid = 0; ;   
   	@Value("${app.encrypted.payload.version.ios}")        
   	float encryptedPayloadVersionIos = 0; ;   

	@Value("${e002.session.not.matched}")               
	private String e002SessionNotMatched = "";
	@Value("${e012.procedure.not.defined}")       
	String e012ProcedureNotDefined = "";   
   
	@ApiOperation(value = "View Query Details", 
				  nickname = "QueryDetailView",  
				  notes = "View Query Details") 
	@ApiResponses(value = {  
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = Object.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
      })
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewQueryDetail(HttpServletRequest httpRequest,   
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,  
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = false) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId, 
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = false) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = false) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = false) String sectionId,
			@RequestParam(name = "portionId", defaultValue = "", required = false) String portionId,
 			@RequestParam(name = "deviceId", defaultValue = "", required = false) String deviceId,  
 			@RequestParam(name = "code", defaultValue = "", required = false) String code,
 			@RequestParam(name = "category", defaultValue = "", required = false) String category, 
 			@RequestParam(name = "subCategory", defaultValue = "", required = false) String subCategory,   
			@RequestParam(name = "status", defaultValue = "", required = false) String status,          
			@RequestParam(name = "type", defaultValue = "", required = true) String type,
 			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,   
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,      
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit, 
			@RequestParam(name = "fromTimeZone", defaultValue = GC_TIME_ZONE_UTC, required = true) String fromTimeZone,   
			@RequestParam(name = "toTimeZone", defaultValue = GC_TIME_ZONE_ISD, required = true) String toTimeZone,  
			@RequestParam(name = "startDateTime", defaultValue = "", required = false) String startDateTime,      
			@RequestParam(name = "endDateTime", defaultValue = "", required = false) String endDateTime,                     
			@RequestParam(name = "queryType", defaultValue = "", required = true) String queryType,      
			@RequestParam(name = "queryFields", defaultValue = "", required = false) String queryFieldsReq,                  
			@RequestParam(name = "viewType", defaultValue = "DATA_ENCRYPT", required = true) String viewType) {     
			
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();      
		String apiLogString     = "LOG_API_QUERY_VIEW";      
    	String apiInstLogString = "LOG_INST_QUERY_VIEW";  
    	
		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;      
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;    
    	fromTimeZone = (fromTimeZone.equals("")) ? GC_TIME_ZONE_UTC : fromTimeZone;  
    	toTimeZone   = (toTimeZone.equals("")) ? GC_TIME_ZONE_ISD : toTimeZone;    
    	viewType     = (viewType.equals("")) ? "DATA_ENCRYPT" : viewType;    
    	String resourceType 	= APIResourceName.QUERY.toString();                   
    	 
    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (FieldValidator.isValidId(companyId) == false)        
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID)); 
		if (FieldValidator.isValidIdOptional(groupId) == false)         
			errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID)); 
		if (FieldValidator.isValidIdOptional(userId) == false)    
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID)); 
		if (FieldValidator.isValidIdOptional(propertyId) == false)        
			errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_PROPERTY_ID));  
		if (FieldValidator.isValidIdOptional(deviceId) == false)           
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));  
		if (FieldValidator.isValidInternalTextOptional(status) == false)          
			errorList.add(new APIPreConditionErrorField("status", EM_INVALID_STATUS));  
		if (FieldValidator.isValidInternalTextOptional(type) == false)        
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE)); 
		    
		try { 
			if (FieldValidator.isValid(startDateTime, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, false) == false)
				errorList.add(new APIPreConditionErrorField("startDateTime", EM_INVALID_START_DATE_TIME));
			if (FieldValidator.isValid(endDateTime, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, false) == false)
				errorList.add(new APIPreConditionErrorField("endDateTime", EM_INVALID_END_DATE_TIME));
 
			if (startDateTime.length() == FL_DATE_TIME_SIZE && endDateTime.length() == FL_DATE_TIME_SIZE) { 
				if (FieldValidator.isValidFromAndToDateTime(startDateTime, endDateTime) == false) {
					errorList.add(new APIPreConditionErrorField("startDateTime", EM_INVALID_START_DATE_TIME));  
					errorList.add(new APIPreConditionErrorField("endDateTime", EM_INVALID_END_DATE_TIME));  
				}
			}
			
		} catch (Exception errMess) {
			errorList.add(new APIPreConditionErrorField("startDateTime", EM_INVALID_START_DATE_TIME));  
			errorList.add(new APIPreConditionErrorField("endDateTime", EM_INVALID_END_DATE_TIME)); 
		}
		
		JsonNode queryFields = null; 
		if (queryFieldsReq.length() > 0) { 
			JsonNode queryFieldsTemp = resultSetConversion.convertStringToJsonNode(queryFieldsReq);
			if (queryFieldsTemp.size() > 0) { 
				queryFields = queryFieldsTemp; 
			} else { 
				errorList.add(new APIPreConditionErrorField("queryFields", EM_INVALID_QUERY_FIELDS));
			} 
		} else { 
			queryFields = resultSetConversion.convertStringToJsonNode(queryFieldsReq);
		}
		
  		if (errorList.size() == 0) {     
  			  
  			String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, groupId, divisionId, moduleId, userId);
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage(); 
		 
			float appVersion     = 0; 
			String sessionType   = ""; 
			String userSecretKey = "";
			try { 
				UserSession userSession = roleStatus.getUserSession();
				appVersion    = userSession.getAppVersion();
				sessionType   = userSession.getType(); 
				userSecretKey = userSession.getUserSecreyKey(); 
			} catch (Exception errMess) { } 
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {           
				
				List<QueryMap> queryMapResults = queryMapService.viewListByCriteria(companyId, "", "", "", "", "", queryType, "", "", 0, 0);
				
				if (queryMapResults.size() > 0) {
					 
					QueryMap queryMapTemp = queryMapResults.get(0);  
					String procedureName = queryMapTemp.getProcedureName();     
					
					List<Map<String, Object>> succPayload = queryService.viewListByCriteria(procedureName, fromTimeZone, toTimeZone, companyId, groupId, divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId, 
							code, subCategory, category, status, type, sortBy, sortOrder, offset, limit, queryFields, startDateTime, endDateTime);    
	 			
					if (sessionType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID) && appVersion >= encryptedPayloadVersionAndroid ||
						sessionType.equalsIgnoreCase(GC_REFERRED_BY_IOS) && appVersion >= encryptedPayloadVersionIos) {
							
						String originalText = resultSetConversion.arrayMapObjectToString(succPayload);  
						String encryptedText = new EncryptDecrypt().encryptDataForMobile(userSecretKey, originalText);
						 
						APILog.writeInfoLog(apiLogString + " " + succPayload.toString());    
						APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
						return ResponseEntity.status(HttpStatus.OK.value()).body(new APIEncryptedSuccess(encryptedText)); 
	 			    	
 			    	} else if (sessionType.equalsIgnoreCase(GC_REFERRED_BY_WEB) && viewType.equalsIgnoreCase("DATA_ENCRYPT")) {
 			    		 
						String originalText = resultSetConversion.arrayMapObjectToString(succPayload);   
						String encryptedText = new EncryptDecrypt().encryptDataForWeb(userSecretKey, originalText);
						 
						APILog.writeInfoLog(apiLogString + " " + succPayload.toString());    
						APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
						return ResponseEntity.status(HttpStatus.OK.value()).body(new APIEncryptedSuccess(encryptedText)); 
	 			    	 
 			    	} 
 			    	
					APILog.writeInfoLog(apiLogString + " " + succPayload.toString());    
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload); 
					
				}  
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_012_PROCEDURE_NOT_DEFINED, e012ProcedureNotDefined));  
				 
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