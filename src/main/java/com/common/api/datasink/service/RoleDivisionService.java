package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.RoleDivision;

public interface RoleDivisionService {

	public List<RoleDivision> viewListByCriteria(String companyId, String divisionId, String id,  List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

}
