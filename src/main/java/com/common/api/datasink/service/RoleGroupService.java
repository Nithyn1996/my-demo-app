package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.RoleGroup;

public interface RoleGroupService {

	public List<RoleGroup> viewListByCriteria(String companyId, String roleCompanyId, String roleDivisionId, String roleModuleId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<RoleGroup> viewListByCriteria(String companyId, String divisionId, String moduleId, String sortBy, String sortOrder, int offset, int limit);

}
