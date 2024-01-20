package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.DashboardMap;

public interface DashboardMapService {

	public List<DashboardMap> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String procedureName, String dashboardType, String sortBy, String sortOrder, int offset, int limit);

}
