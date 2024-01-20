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
import org.springframework.context.annotation.PropertySource;
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
import com.common.api.datasink.service.DeviceSummaryService;
import com.common.api.model.DeviceSummary;
import com.common.api.response.APIGeneralSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileCardinalityAccess;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Device Summary", tags = {"Device Summary"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class DeviceSummaryResource extends APIFixedConstant{

	@Autowired
	FieldValidator fieldValidator;
	@Autowired
	ProfileSessionRoleService profileSessionRoleService;
	@Autowired
	DeviceSummaryService deviceSummaryService;
	@Autowired
	PayloadValidator payloadValidator;
	@Autowired
	ProfileDependencyService profileDependencyService;
	@Autowired
	ProfileCardinalityService profileCardinalityService;
	
	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";
	@Value("${s001.request.successful}")
   	String s001RequestSuccessful = "";
    @Value("${e003.profile.not.exist}")
	String e003ProfileNotExist = "";

	@ApiOperation(value = "View Device Summary Details",
			  nickname = "DeviceSummaryDetailView",
			  notes = "View Device Summary Details")
	@ApiResponses(value = {
		@ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = DeviceSummary.class, responseContainer = "List"),
		@ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
		@ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
		@ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
		@ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
		})
	@RequestMapping(value = "/deviceSummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewDeviceDataDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = true) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = false) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = false) String sectionId,
			@RequestParam(name = "portionId", defaultValue = "", required = false) String portionId,
			@RequestParam(name = "deviceId", defaultValue = "", required = false) String deviceId,
 			@RequestParam(name = "id", defaultValue = "", required = false) String id,
			@RequestParam(name = "status", defaultValue = "", required = false) String status,
			@RequestParam(name = "type", defaultValue = "DEVICE_SUMMARY", required = true) String type,
  			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit){

		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_DEVICE_SUMMARY_VIEW";
    	String apiInstLogString = "LOG_INST_DEVICE_SUMMARY_VIEW";

		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;
    	type	  = (type.length() <= 0) ? APIResourceName.DEVICE_SUMMARY.toString() : type;
    	String resourceType = APIResourceName.DEVICE_SUMMARY.toString();

    	List<String> statusList 		= Util.convertIntoArrayString(status);
    	List<String> typeList   		= Util.convertIntoArrayString(type);

    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (!FieldValidator.isValidIdOptional(groupId))
			errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID));
		if (!FieldValidator.isValidId(userId))
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
		if (!FieldValidator.isValidIdOptional(propertyId))
			errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_ID));
		if (!FieldValidator.isValidInternalTextOptional(status))
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		if (!FieldValidator.isValidInternalTextOptional(status))
			errorList.add(new APIPreConditionErrorField("status", EM_INVALID_STATUS));
		if (!FieldValidator.isValidInternalTextOptional(type))
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));

		apiLogString = apiLogString + " referredBy: " + referredBy + " sessionId: " + sessionId + " userId: " + userId;

		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, moduleId);
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				List<DeviceSummary> succPayload = deviceSummaryService.viewListByCriteria(companyId, groupId, divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId, id, statusList, typeList, sortBy, sortOrder, offset, limit);

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
	@ApiOperation(value = "Create Device Summary Details", 
				  nickname = "DeviceSummaryDetailCreate",  
				  notes = "Create Device Summary Details")   
	@ApiResponses(value = { 
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = DeviceSummary.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/deviceSummary", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createDeviceSummaryDetail(HttpServletRequest httpRequest,    
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,  
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestBody DeviceSummary deviceSummaryReq) { 
		
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation(); 
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();   
		
		String apiLogString     = "LOG_API_DEVICE_SUMMARY_CREATE";          
    	String apiInstLogString = "LOG_INST_DEVICE_SUMMARY_CREATE";  
    	
    	String companyId 	= deviceSummaryReq.getCompanyId(); 
    	String divisionId 	= deviceSummaryReq.getDivisionId();
    	String type			= deviceSummaryReq.getType();     
    	type	  			= (type.length() <= 0) ? APIResourceName.DEVICE_SUMMARY.toString() : type;
    	String resourceType = APIResourceName.DEVICE_SUMMARY.toString(); 
    	
    	ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<DeviceSummary>> validationErrors = validator.validate(deviceSummaryReq);
		
		List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidDeviceSummaryPayload(GC_METHOD_POST, deviceSummaryReq));
		for (ConstraintViolation<DeviceSummary> error : validationErrors)  
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage())); 
		
		if (errorList.size() == 0) {
			
			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();  
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, "");
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage();  
			String sessionUserId  = roleStatus.getUserId(); 
			
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {
				
				ProfileDependencyAccess profileDependency = profileDependencyService.deviceSummaryProfileDependencyStatus(GC_METHOD_POST, deviceSummaryReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();
				
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {
					
					ProfileCardinalityAccess profileCardinality = profileCardinalityService.deviceSummaryProfileCardinalityStatus(GC_METHOD_POST, deviceSummaryReq);
					String profileCardinalityCode = profileCardinality.getCode();
					String profileCardinalityMess = profileCardinality.getMessage();
						
						if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) { 
							
							deviceSummaryReq.setCreatedAt(dbOperationStartTime);
							deviceSummaryReq.setModifiedAt(dbOperationStartTime);
							deviceSummaryReq.setCreatedBy(sessionUserId);
							deviceSummaryReq.setModifiedBy(sessionUserId);
							
							DeviceSummary succPayload = deviceSummaryService.createDeviceSummary(deviceSummaryReq);
							
							if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {
								
								APILog.writeInfoLog(apiLogString + " " + succPayload.toString());   
								APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
								return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload); 
							}	
							return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency)); 
					}
					return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY,profileCardinalityMess));
				}
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY,profileDependencyMess));
			} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) { 
				return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED,roleStatucMess));
			} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED,roleStatucMess));
			}
		}
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	} 

	@ResponseStatus(code = HttpStatus.ACCEPTED)
	@ApiOperation(value = "Update Device Summary Details",
				  nickname = "UpdateDeviceSummaryDetailCreate",
				  notes = "Update Device Summary Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = DeviceSummary.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/deviceSummary", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updaeDeviceSummaryDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody DeviceSummary deviceSummaryReq){

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_DEVICE_DATA_UPDATE";
    	String apiInstLogString = "LOG_INST_DEVICE_DATA_UPDATE";

    	String id           = deviceSummaryReq.getId();
    	String companyId    = deviceSummaryReq.getCompanyId();
    	String type         = deviceSummaryReq.getType();
    	type	  			= (type.length() <= 0) ? APIResourceName.DEVICE_SUMMARY.toString() : type;
    	String resourceType = APIResourceName.DEVICE_SUMMARY.toString();

    	ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
    	Set<ConstraintViolation<DeviceSummary>> validationErrors = validator.validate(deviceSummaryReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidDeviceSummaryPayload(GC_METHOD_PUT, deviceSummaryReq));
		for (ConstraintViolation<DeviceSummary> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));


		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();
			String sessionUserId  = roleStatus.getUserId();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.deviceSummaryProfileDependencyStatus(GC_METHOD_PUT, deviceSummaryReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {
					
					List<String> emptyList = new ArrayList<>();
					List<DeviceSummary> deviceSummaryList = deviceSummaryService.viewListByCriteria(companyId, "", "", "", "", "", "", "", "", id, emptyList, emptyList, "", "", 0, 0);

					if (deviceSummaryList.size() > 0) {

						DeviceSummary deviceSummaryDetail = deviceSummaryList.get(0);

						deviceSummaryDetail.setAccidentCount(deviceSummaryReq.getAccidentCount());
					    deviceSummaryDetail.setAnimalCrossingCount(deviceSummaryReq.getAnimalCrossingCount());
					    deviceSummaryDetail.setCautionCount(deviceSummaryReq.getCautionCount());
					    deviceSummaryDetail.setCongestionCount(deviceSummaryReq.getCongestionCount());
					    deviceSummaryDetail.setCurveCount(deviceSummaryReq.getCurveCount());
					    deviceSummaryDetail.setHillCount(deviceSummaryReq.getHillCount());
					    deviceSummaryDetail.setHillDownwardsCount(deviceSummaryReq.getHillDownwardsCount());
					    deviceSummaryDetail.setHillUpwardsCount(deviceSummaryReq.getHillUpwardsCount());
					    deviceSummaryDetail.setIcyConditionsCount(deviceSummaryReq.getIcyConditionsCount());
					    deviceSummaryDetail.setIntersectionCount(deviceSummaryReq.getIntersectionCount());
					    deviceSummaryDetail.setLaneMergeCount(deviceSummaryReq.getLaneMergeCount());
					    deviceSummaryDetail.setLowGearAreaCount(deviceSummaryReq.getLowGearAreaCount());
					    deviceSummaryDetail.setNarrowRoadCount(deviceSummaryReq.getNarrowRoadCount());
					    deviceSummaryDetail.setNoOvertakingCount(deviceSummaryReq.getNoOvertakingCount());
					    deviceSummaryDetail.setNoOvertakingTrucksCount(deviceSummaryReq.getNoOvertakingTrucksCount());
					    deviceSummaryDetail.setPedestrianCrossingCount(deviceSummaryReq.getPedestrianCrossingCount());
					    deviceSummaryDetail.setPriorityCount(deviceSummaryReq.getPriorityCount());
					    deviceSummaryDetail.setPriorityToOncomingTrafficCount(deviceSummaryReq.getPriorityToOncomingTrafficCount());
					    deviceSummaryDetail.setRailwayCrossingCount(deviceSummaryReq.getRailwayCrossingCount());
					    deviceSummaryDetail.setRiskOfGroundingCount(deviceSummaryReq.getRiskOfGroundingCount());
					    deviceSummaryDetail.setSchoolZoneCount(deviceSummaryReq.getSchoolZoneCount());
					    deviceSummaryDetail.setSlipperyRoadsCount(deviceSummaryReq.getSlipperyRoadsCount());
					    deviceSummaryDetail.setStopSignCount(deviceSummaryReq.getStopSignCount());
					    deviceSummaryDetail.setTrafficLightCount(deviceSummaryReq.getTrafficLightCount());
					    deviceSummaryDetail.setTramwayCrossingCount(deviceSummaryReq.getTramwayCrossingCount());
					    deviceSummaryDetail.setWindCount(deviceSummaryReq.getWindCount());
					    deviceSummaryDetail.setWindingRoadCount(deviceSummaryReq.getWindingRoadCount());
					    deviceSummaryDetail.setYieldCount(deviceSummaryReq.getYieldCount());
					    deviceSummaryDetail.setRoundAboutCount(deviceSummaryReq.getRoundAboutCount());
					    deviceSummaryDetail.setOverSpeedCount(deviceSummaryReq.getOverSpeedCount());
					    deviceSummaryDetail.setOverSpeedDuration(deviceSummaryReq.getOverSpeedDuration());
					    deviceSummaryDetail.setOverSpeedDistance(deviceSummaryReq.getOverSpeedDistance());
					    deviceSummaryDetail.setMobileUseInAcceptedCount(deviceSummaryReq.getMobileUseInAcceptedCount());
					    deviceSummaryDetail.setMobileUseInAcceptedDuration(deviceSummaryReq.getMobileUseInAcceptedDuration());
					    deviceSummaryDetail.setMobileUseInAcceptedDistance(deviceSummaryReq.getMobileUseInAcceptedDistance());
					    deviceSummaryDetail.setMobileUseOutAcceptedCount(deviceSummaryReq.getMobileUseOutAcceptedCount());
					    deviceSummaryDetail.setMobileUseOutAcceptedDuration(deviceSummaryReq.getMobileUseOutAcceptedDuration());
					    deviceSummaryDetail.setMobileUseOutAcceptedDistance(deviceSummaryReq.getMobileUseOutAcceptedDistance());
					    deviceSummaryDetail.setMobileScreenScreenOnDuration(deviceSummaryReq.getMobileScreenScreenOnDuration());
					    deviceSummaryDetail.setMobileScreenScreenOnDistance(deviceSummaryReq.getMobileScreenScreenOnDistance());
					    deviceSummaryDetail.setSevereBrakingLowCount(deviceSummaryReq.getSevereBrakingLowCount());
					    deviceSummaryDetail.setSevereBrakingMediumCount(deviceSummaryReq.getSevereBrakingMediumCount());
					    deviceSummaryDetail.setSevereBrakingHighCount(deviceSummaryReq.getSevereBrakingHighCount());
					    deviceSummaryDetail.setSevereAccelerationLowCount(deviceSummaryReq.getSevereAccelerationLowCount());
					    deviceSummaryDetail.setSevereAccelerationMediumCount(deviceSummaryReq.getSevereAccelerationMediumCount());
					    deviceSummaryDetail.setSevereAccelerationHighCount(deviceSummaryReq.getSevereAccelerationHighCount());
					    deviceSummaryDetail.setSevereCorneringLowCount(deviceSummaryReq.getSevereCorneringLowCount());
					    deviceSummaryDetail.setSevereCorneringMediumCount(deviceSummaryReq.getSevereCorneringMediumCount());
					    deviceSummaryDetail.setSevereCorneringHighCount(deviceSummaryReq.getSevereCorneringHighCount());
					    deviceSummaryDetail.setModifiedAt(dbOperationStartTime);
					    deviceSummaryDetail.setModifiedBy(sessionUserId);
					     
					    DeviceSummary succPayload = deviceSummaryService.updateDeviceSummary(deviceSummaryDetail);

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

	@ApiOperation(value = "Remove DeviceSummaryDetails",
			  nickname = "DeviceSummaryRemove",
			  notes = "Remove DeviceSummary Details")
	@ApiResponses(value = {
	  @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = APIGeneralSuccess.class),
	  @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
	  @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	  @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
	  @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
	})
	@RequestMapping(value = "/deviceSummary", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeDeviceSummaryDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = false) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = false) String sectionId,
			@RequestParam(name = "portionId", defaultValue = "", required = false) String portionId,
			@RequestParam(name = "deviceId", defaultValue = "", required = true) String deviceId,
 			@RequestParam(name = "id", defaultValue = "", required = true) String id){

		 Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		    String apiLogString = "LOG_API_DEVICE_SUMMARY_REMOVE";
		    String apiInstLogString = "LOG_INST_DEVICE_SUMMARY_REMOVE";
		    String resourceType = APIResourceName.DEVICE_SUMMARY.toString();

		    List<APIPreConditionErrorField> errorList = new ArrayList<>();
		    errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		    if (!FieldValidator.isValidId(companyId))
				errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
			if (!FieldValidator.isValidId(userId))
				errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
			if (!FieldValidator.isValidIdOptional(propertyId))
				errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_PROPERTY_ID));
			if (!FieldValidator.isValidIdOptional(sectionId))
				errorList.add(new APIPreConditionErrorField("sectionId", EM_INVALID_SECTION_ID));
			if (!FieldValidator.isValidIdOptional(portionId))
				errorList.add(new APIPreConditionErrorField("portionId", EM_INVALID_PORTION_ID));
			if (!FieldValidator.isValidIdOptional(deviceId))
				errorList.add(new APIPreConditionErrorField("deviceId", EM_INVALID_DEVICE_ID));
			if (!FieldValidator.isValidId(id))
				errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));

		    if (errorList.size() == 0) {

		        List<String> emptyList = new ArrayList<>();
		        List<DeviceSummary> deviceSummaryList = deviceSummaryService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId, id, emptyList, emptyList, "", "", 0, 0);

		        if (deviceSummaryList.size() > 0) {

		        	String type = deviceSummaryList.get(0).getType();

		        	String rolePrivilegeType = type + APIRolePrivilegeType._DELETE.toString();
		            ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, divisionId, "");
		            String roleStatucCode = roleStatus.getCode();
		            String roleStatucMess = roleStatus.getMessage();

		            if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

		            	int result = deviceSummaryService.removeDeviceSummaryById(companyId, userId, id);

		            	if (result > 0) {

		            		APIGeneralSuccess succPayload = new APIGeneralSuccess(SC_001_REQUEST_SUCCESSFUL, s001RequestSuccessful);

		                    APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
		                    APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
		                    return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(succPayload);
		                }
		                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
		            } else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) {
		                return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));
		            } else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
		                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, roleStatucMess));
		            }
		        }
		        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_003_PROFILE_NOT_EXIST, e003ProfileNotExist));
		    }
		    APILog.writeTraceLog(apiLogString + errorList);
		    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
		}
}