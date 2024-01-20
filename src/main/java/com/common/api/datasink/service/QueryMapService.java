package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.QueryMap;

public interface QueryMapService {

	public List<QueryMap> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String procedureName, String queryType, String sortBy, String sortOrder, int offset, int limit);

}
