package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.CompanyPreference;

public interface CompanyPreferenceService {

	public List<CompanyPreference> viewListByCriteria(String companyId, String id, String name, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

}
