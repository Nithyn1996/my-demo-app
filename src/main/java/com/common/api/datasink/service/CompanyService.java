package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Company;

public interface CompanyService {

	public List<Company> viewListByCriteria(String id, String name, String code, String category, List<String> status, List<String> type, String sortBy, String sortOrder, int offset, int limit);

}
