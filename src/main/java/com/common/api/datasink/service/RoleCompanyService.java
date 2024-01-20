package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.RoleCompany;

public interface RoleCompanyService {

	public List<RoleCompany> viewListByCriteria(String companyId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

}
