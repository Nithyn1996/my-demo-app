package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Country;

public interface CountryService {

	public List<Country> viewListByCriteria(String companyId, String continentId, String id, String name, String isoCode2, String isoCode3, String dialingCode, List<String> status, List<String> type, String sortBy, String sortOrder, int offset, int limit);

}
