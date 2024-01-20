package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.ReportMap;

public interface ReportMapService {

	public List<ReportMap> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String procedureName, String reportType, String sortBy, String sortOrder, int offset, int limit);

}
