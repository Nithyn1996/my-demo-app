package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.SearchMap;

public interface SearchMapService {

	public List<SearchMap> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String procedureName, String searchType, String sortBy, String sortOrder, int offset, int limit);

}
