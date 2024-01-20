package com.common.api.datasink.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public interface DashboardService {

	public List<Map<String, Object>> viewListByCriteria(String procedureName, String fromTimeZone, String toTimeZone, String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String portionId, String deviceId,
			String code, String subCategory, String category, String status, String type, String sortBy, String sortOrder, int offset, int limit, JsonNode dashboardFields, String startDateTime, String endDateTime);

}