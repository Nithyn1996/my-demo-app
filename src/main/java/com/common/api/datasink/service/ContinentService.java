package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Continent;

public interface ContinentService {

	public List<Continent> viewListByCriteria(String companyId, String id, String name, String isoCode2, List<String> status, List<String> type, String sortBy, String sortOrder, int offset, int limit);

}
