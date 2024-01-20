package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Division;

public interface DivisionService {

	public List<Division> viewListByCriteria(String companyId, String groupId, String id, String name, String code, List<String> categoryList, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

}
