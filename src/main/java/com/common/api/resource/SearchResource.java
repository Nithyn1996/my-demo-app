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
import com.common.api.datasink.service.SearchMapService;
import com.common.api.datasink.service.SearchService;
import com.common.api.model.SearchMap;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.util.APIAuthorization;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.ResultSetConversion;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Search", tags = {"Search"})
@RestController
public class SearchResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	FieldValidator fieldValidator;
	@Autowired
	ResultSetConversion resultSetConversion;

	@Autowired
	APIAuthorization apiAuthorization;
    @Autowired
    SearchService searchService;
    @Autowired
    SearchMapService searchMapService;

	@Value("${e002.session.not.matched}")
	private String e002SessionNotMatched = "";
	@Value("${e012.procedure.not.defined}")
	String e012ProcedureNotDefined = "";

	@ApiOperation(value = "View Search Details",
				  nickname = "SearchDetailView",
				  notes = "View Search Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = Object.class, responseContainer = "List"),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> viewSearchDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = false) String companyId,
			@RequestParam(name = "groupId", defaultValue = "", required = false) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = false) String userId,
 			@RequestParam(name = "deviceId", defaultValue = "", required = false) String propertyId,
 			@RequestParam(name = "sectionId", defaultValue = "", required = false) String sectionId,
 			@RequestParam(name = "portionId", defaultValue = "", required = false) String portionId,
 			@RequestParam(name = "deviceId", defaultValue = "", required = false) String deviceId,
			@RequestParam(name = "username", defaultValue = "", required = false) String username,
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
			@RequestParam(name = "searchType", defaultValue = "", required = true) String searchType,
			@RequestParam(name = "searchFields", defaultValue = "", required = false) String searchFieldsReq) {

		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_SEARCH_VIEW";
    	String apiInstLogString = "LOG_INST_SEARCH_VIEW";

		sortOrder = (sortOrder.equals("")) ? GC_SORT_ASC : sortOrder;
    	sortBy    = (sortBy.equals("")) ? GC_SORT_BY_CREATED_BY : sortBy;
    	fromTimeZone = (fromTimeZone.equals("")) ? GC_TIME_ZONE_UTC : fromTimeZone;
    	toTimeZone   = (toTimeZone.equals("")) ? GC_TIME_ZONE_ISD : toTimeZone;

    	List<APIPreConditionErrorField> errorList = fieldValidator.isValidSortOrderAndSortBy(sortOrder, sortBy);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (!FieldValidator.isValidIdOptional(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (!FieldValidator.isValidIdOptional(userId))
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
		if (!FieldValidator.isValidIdOptional(deviceId))
			errorList.add(new APIPreConditionErrorField("id", EM_INVALID_ID));
		if (!FieldValidator.isValidInternalTextOptional(status))
			errorList.add(new APIPreConditionErrorField("status", EM_INVALID_STATUS));
		if (!FieldValidator.isValidInternalTextOptional(type))
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));

		try {
			if (!FieldValidator.isValid(startDateTime, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, false))
				errorList.add(new APIPreConditionErrorField("startDateTime", EM_INVALID_START_DATE_TIME));
			if (!FieldValidator.isValid(endDateTime, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, false))
				errorList.add(new APIPreConditionErrorField("endDateTime", EM_INVALID_END_DATE_TIME));

			if (startDateTime.length() == FL_DATE_TIME_SIZE && endDateTime.length() == FL_DATE_TIME_SIZE) {
				if (!FieldValidator.isValidFromAndToDateTime(startDateTime, endDateTime)) {
					errorList.add(new APIPreConditionErrorField("startDateTime", EM_INVALID_START_DATE_TIME));
					errorList.add(new APIPreConditionErrorField("endDateTime", EM_INVALID_END_DATE_TIME));
				}
			}

		} catch (Exception errMess) {
			errorList.add(new APIPreConditionErrorField("startDateTime", EM_INVALID_START_DATE_TIME));
			errorList.add(new APIPreConditionErrorField("endDateTime", EM_INVALID_END_DATE_TIME));
		}

		JsonNode searchFields = null;
		if (searchFieldsReq.length() > 0) {
			JsonNode searchFieldsTemp = resultSetConversion.convertStringToJsonNode(searchFieldsReq);
			if (searchFieldsTemp.size() > 0) {
				searchFields = searchFieldsTemp;
			} else {
				errorList.add(new APIPreConditionErrorField("searchFields", EM_INVALID_SEARCH_FIELDS));
			}
		} else {
			searchFields = resultSetConversion.convertStringToJsonNode(searchFieldsReq);
		}

  		if (errorList.size() == 0) {

  			if (apiAuthorization.verifyStaticSessionId(sessionId)) {

				List<SearchMap> searchMapResults = searchMapService.viewListByCriteria(companyId, "", "", "", "", "", searchType, "", "", 0, 0);

				if (searchMapResults.size() > 0) {

					SearchMap searchMapTemp = searchMapResults.get(0);
					String procedureName = searchMapTemp.getProcedureName();

					List<Map<String, Object>> succPayload = searchService.viewListByCriteria(procedureName, fromTimeZone, toTimeZone, companyId, groupId, divisionId, moduleId, userId, propertyId, sectionId, portionId, deviceId,
							username, "", subCategory, category, status, type, sortBy, sortOrder, offset, limit, searchFields, startDateTime, endDateTime);

					APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return ResponseEntity.status(HttpStatus.OK.value()).body(succPayload);

				}
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_012_PROCEDURE_NOT_DEFINED, e012ProcedureNotDefined));
			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, e002SessionNotMatched));
		}
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
    }


}