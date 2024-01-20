package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.RoleModule;

public interface RoleModuleService {

	public List<RoleModule> viewListByCriteria(String companyId, String divisionId, String moduleId, String id,  List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

}
