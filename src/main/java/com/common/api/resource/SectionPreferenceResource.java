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
import com.common.api.constant.APIResourceName;
import com.common.api.constant.APIRolePrivilegeType;
import com.common.api.datasink.service.SectionPreferenceService;
import com.common.api.model.SectionPreference;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileCardinalityAccess;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileCardinalityService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.service.util.ServiceFCMNotification;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.PayloadValidator;
import com.common.api.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Section Preference", tags = {"Section Preference"})
@RestController
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class SectionPreferenceResource extends APIFixedConstant {

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
    SectionPreferenceService sectionPreferenceService;
    @Autowired
    ServiceFCMNotification serviceFCMNotification;

	@Value("${e003.profile.not.exist}")
	String e003ProfileNotExist = "";
	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";

	@ApiOperation(value = "View Section Preference Details",
				  nickname = "SectionPreferenceDetailView",
				  notes = "View Section Preference Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = SectionPreference.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/sectionPreference", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewSectionPreferenceDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = false) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = true) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = true) String sectionId,
			@RequestParam(name = "id", defaultValue = "", required = false) String id,
			@RequestParam(name = "name", defaultValue = "", required = false) String name,
			@RequestParam(name = "code", defaultValue = "", required = false) String code,
			@RequestParam(name = "category", defaultValue = "", required = false) String category,
			@RequestParam(name = "subCategory", defaultValue = "", required = false) String subCategory,
			@RequestParam(name = "status", defaultValue = "", required = false) String status,
			@RequestParam(name = "type", defaultValue = "SECTION_PREFERENCE", required = true) String type,
 			@RequestParam(name = "sortBy", defaultValue = GC_SORT_BY_CREATED_BY, required = true) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = GC_SORT_ASC, required = true) String sortOrder,
			@RequestParam(name = "offset", defaultValue = "0", required = true) int offset,
			@RequestParam(name = "limit", defaultValue = "100", required = true) int limit) {

		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_SECTION_PREFERENCE_VIEW";
    	String apiInstLogString = "LOG_INST_SECTION_PREFERENCE_VIEW";

		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;
    	type	  = (type.length() <= 0) ? APIResourceName.SECTION_PREFERENCE.toString() : type;
    	String resourceType 	= APIResourceName.SECTION_PREFERENCE.toString();

    	List<String> statusList = Util.convertIntoArrayString(status);
    	List<String> typeList   = Util.convertIntoArrayString(type);

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
		if (!FieldValidator.isValidId(userId))
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
		if (!FieldValidator.isValidIdOptional(propertyId))
			errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_PROPERTY_ID));
		if (!FieldValidator.isValidIdOptional(sectionId))
			errorList.add(new APIPreConditionErrorField("sectionId", EM_INVALID_SECTION_ID));
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

				List<SectionPreference> succPayload = sectionPreferenceService.viewListByCriteria(companyId, groupId, divisionId, moduleId, userId, propertyId, sectionId, id, name, code, statusList, typeList, sortBy, sortOrder, offset, limit);

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
	@ApiOperation(value = "Create Section Preference Details",
				  nickname = "SectionPreferenceDetailCreate",
				  notes = "Create Section Preference Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = SectionPreference.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/sectionPreference", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> createSectionPreferenceDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody SectionPreference sectionPreferenceReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_SECTION_PREFERENCE_CREATE";
    	String apiInstLogString = "LOG_INST_SECTION_PREFERENCE_CREATE";

    	String companyId 	= sectionPreferenceReq.getCompanyId();
    	String type			= sectionPreferenceReq.getType();
    	type	  			= (type.length() <= 0) ? APIResourceName.SECTION_PREFERENCE.toString() : type;
    	String resourceType 	= APIResourceName.SECTION_PREFERENCE.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<SectionPreference>> validationErrors = validator.validate(sectionPreferenceReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidSectionPreferencePayload(GC_METHOD_POST, sectionPreferenceReq));
 		for (ConstraintViolation<SectionPreference> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._CREATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();
			String sessionUserId  = roleStatus.getUserId();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.sectionPreferenceProfileDependencyStatus(GC_METHOD_POST, sectionPreferenceReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					ProfileCardinalityAccess profileCardinality = profileCardinalityService.sectionPreferenceProfileCardinalityStatus(GC_METHOD_POST, sectionPreferenceReq);
					String profileCardinalityCode = profileCardinality.getCode();
					String profileCardinalityMess = profileCardinality.getMessage();

					if (profileCardinalityCode.equals(GC_STATUS_SUCCESS)) {

						sectionPreferenceReq.setCreatedAt(dbOperationStartTime);
						sectionPreferenceReq.setModifiedAt(dbOperationStartTime);
						sectionPreferenceReq.setCreatedBy(sessionUserId);
						sectionPreferenceReq.setModifiedBy(sessionUserId);

			  			SectionPreference succPayload = sectionPreferenceService.createSectionPreference(sectionPreferenceReq);

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

	@ResponseStatus(code = HttpStatus.ACCEPTED)
	@ApiOperation(value = "Update Section Preference Details",
				  nickname = "SectionPreferenceDetailCreate",
				  notes = "Update Section Preference Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = SectionPreference.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/sectionPreference", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<?> updateSectionPreferenceDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestBody SectionPreference sectionPreferenceReq) {

		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_SECTION_PREFERENCE_UPDATE";
    	String apiInstLogString = "LOG_INST_SECTION_PREFERENCE_UPDATE";

    	String id			= sectionPreferenceReq.getId();
    	String companyId 	= sectionPreferenceReq.getCompanyId();
    	String type			= sectionPreferenceReq.getType();
     	type	  			= (type.length() <= 0) ? APIResourceName.SECTION_PREFERENCE.toString() : type;
    	String resourceType 	= APIResourceName.SECTION_PREFERENCE.toString();

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<SectionPreference>> validationErrors = validator.validate(sectionPreferenceReq);

    	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);
		errorList.addAll(payloadValidator.isValidSectionPreferencePayload(GC_METHOD_PUT, sectionPreferenceReq));
 		for (ConstraintViolation<SectionPreference> error : validationErrors)
			errorList.add(new APIPreConditionErrorField(error.getPropertyPath().toString(), error.getMessage()));

  		if (errorList.size() == 0) {

  			String rolePrivilegeType = type + APIRolePrivilegeType._UPDATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();
			String sessionUserId  = roleStatus.getUserId();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.sectionPreferenceProfileDependencyStatus(GC_METHOD_PUT, sectionPreferenceReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					List<String> emptyList = new ArrayList<>();
					List<SectionPreference> sectionPreferenceList = sectionPreferenceService.viewListByCriteria(companyId, "", "", "", "", "", "", id, "", "", emptyList, emptyList, "", "", 0, 0);

					if (sectionPreferenceList.size() > 0) {

						SectionPreference sectionPreferenceDetail = sectionPreferenceList.get(0);

						sectionPreferenceDetail.setCompanyId(sectionPreferenceReq.getCompanyId());
						sectionPreferenceDetail.setName(sectionPreferenceReq.getName());
						sectionPreferenceDetail.setCode(sectionPreferenceReq.getCode());
						sectionPreferenceDetail.setRemarks(sectionPreferenceReq.getRemarks());
						sectionPreferenceDetail.setComments(sectionPreferenceReq.getComments());
						sectionPreferenceDetail.setSectionPrefVehicleInspection(sectionPreferenceReq.getSectionPrefVehicleInspection());
						sectionPreferenceDetail.setCategory(sectionPreferenceReq.getCategory());
						sectionPreferenceDetail.setModifiedBy(sessionUserId);
			  			sectionPreferenceDetail.setModifiedAt(dbOperationStartTime);

			  			SectionPreference succPayload = sectionPreferenceService.updateSectionPreference(sectionPreferenceDetail);

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

}