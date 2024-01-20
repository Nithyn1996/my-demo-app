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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.constant.APIRolePrivilegeType;
import com.common.api.datasink.service.PropertyService;
import com.common.api.model.Property;
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

@Api(value = "Property", tags = {"Property"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class PropertyResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	FieldValidator fieldValidator;
	@Autowired
	PayloadValidator payloadValidator;
	@Autowired
	ProfileSessionRoleService profileSessionRoleService;
	@Autowired
	ProfileDependencyService profileDependencyService;
	@Autowired
	ProfileCardinalityService profileCardinalityService;

    @Autowired
    PropertyService propertyService;

	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";

	@ApiOperation(value = "View Property Details",
				  nickname = "PropertyDetailView",
				  notes = "View Property Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = Property.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/property", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewPropertyDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = false) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
 			@RequestParam(name = "id", defaultValue = "", required = false) String id,
			@RequestParam(name = "category", defaultValue = "", required = false) String category,
			@RequestParam(name = "status", defaultValue = "", required = false) String status,
			@RequestParam(name = "type", defaultValue = "PROPERTY", required = true) String type,
 			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) {

		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_PROPERTY_VIEW";
    	String apiInstLogString = "LOG_INST_PROPERTY_VIEW";

		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;
    	type	  = (type.length() <= 0) ? APIResourceName.PROPERTY.toString() : type;
    	String resourceType 	= APIResourceName.PROPERTY.toString();

    	List<String> statusList = Util.convertIntoArrayString(status);
    	List<String> typeList   = Util.convertIntoArrayString(type);

    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (!FieldValidator.isValidIdOptional(groupId))
			errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID));
		if (!FieldValidator.isValidId(userId))
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
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

				List<Property> succPayload = propertyService.viewListByCriteria(companyId, groupId, divisionId, moduleId, userId, id, category, statusList, typeList, sortBy, sortOrder, offset, limit);

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
	@ApiOperation(value = "Create Property Details",
				  nickname = "PropertyDetailCreate",
				  notes = "Create Property Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = Property.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/property", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createPropertyDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody Property propertyReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_PROPERTY_CREATE";
    	String apiInstLogString = "LOG_INST_PROPERTY_CREATE";

    	String companyId 	= propertyReq.getCompanyId();
    	String type			= propertyReq.getType();
    	type	  			= (type.length() <= 0) ? APIResourceName.PROPERTY.toString() : type;
    	String resourceType 	= APIResourceName.PROPERTY.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Property>> validationErrors = validator.validate(propertyReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidPropertyPayload(GC_METHOD_POST, propertyReq));
    	for (ConstraintViolation<Property> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();
			String sessionUserId  = roleStatus.getUserId();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.propertyProfileDependencyStatus(GC_METHOD_POST, propertyReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					ProfileCardinalityAccess profileCardinality = profileCardinalityService.propertyProfileCardinalityStatus(GC_METHOD_POST, propertyReq);
					String profileCardinalityCode = profileCardinality.getCode();
					String profileCardinalityMess = profileCardinality.getMessage();

					if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {

						propertyReq.setCreatedAt(dbOperationStartTime);
						propertyReq.setModifiedAt(dbOperationStartTime);
			  			propertyReq.setCreatedBy(sessionUserId);
			  			propertyReq.setModifiedBy(sessionUserId);

			  			Property succPayload = propertyService.createProperty(propertyReq);

			  			if (succPayload != null && succPayload.getId() != null && succPayload.getId().length() > 0) {

							APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
							APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
							return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
			  			}
						return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
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

}