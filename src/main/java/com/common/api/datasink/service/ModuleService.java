package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Module;

public interface ModuleService {

	public List<Module> viewListByCriteria(String companyId, String groupId, String divisionId, String id, String name, String code, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

}
