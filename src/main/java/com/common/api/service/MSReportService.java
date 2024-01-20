package com.common.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.ReportService;
import com.common.api.resultset.StoredProcedureResultset;
import com.common.api.util.ResultSetConversion;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class MSReportService extends APIFixedConstant implements ReportService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Map<String, Object>> viewListByCriteria(String procedureName, String fromTimeZone, String toTimeZone, String companyId, String groupId, String divisionId, String moduleId,
			 String userId, String propertyId, String sectionId, String portionId, String deviceId, String subscriptionId, String orderId,
			 String code, String subCategory, String category, String status, String type, String sortBy, String sortOrder, int offset, int limit, JsonNode reportFields, String startDateTime, String endDateTime) {

		List<Map<String, Object>> results = new ArrayList<>();

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		try {

			Map<String, Object> requestParameters = new HashMap<>();
			requestParameters.put("fromTimeZone", 	fromTimeZone);
			requestParameters.put("toTimeZone",   	toTimeZone);
			requestParameters.put("companyId", 		companyId);
			requestParameters.put("groupId", 		groupId);
			requestParameters.put("divisionId", 	divisionId);
			requestParameters.put("moduleId", 		moduleId);
			requestParameters.put("userId", 		userId);
			requestParameters.put("propertyId", 	propertyId);
			requestParameters.put("sectionId", 		sectionId);
			requestParameters.put("portionId", 		portionId);
			requestParameters.put("deviceId", 		deviceId);
			requestParameters.put("subscriptionId", subscriptionId);
			requestParameters.put("orderId", 		orderId);
			requestParameters.put("code",			code);
			requestParameters.put("category", 		category);
			requestParameters.put("subCategory",	subCategory);
			requestParameters.put("status", 		status);
			requestParameters.put("type", 			type);
			requestParameters.put("sortBy", 		sortBy);
			requestParameters.put("sortOrder", 		sortOrder);
			requestParameters.put("offset", 		offset);
			requestParameters.put("limit", 			limit);
			requestParameters.put("reportFields", 	reportFields);
			requestParameters.put("startDateTime", 	startDateTime);
			requestParameters.put("endDateTime", 	endDateTime);

			String requestParametersStr = resultSetConversion.convertJsonNodeToString(requestParameters);
 			String selQuery = " EXEC " + procedureName + " @inputParams = '" + requestParametersStr + "'; ";

			MapSqlParameterSource parameters = new MapSqlParameterSource();
			results =  namedParameterJdbcTemplate.query(selQuery, parameters, new StoredProcedureResultset());

		} catch (Exception errMess) {
		}
		return results;
	}

}