package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.RoleUser;

public interface RoleUserService {

	public List<RoleUser> viewListByCriteria(String companyId, String rloleGroupId, String userId, String id,  List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public RoleUser createRoleUser(RoleUser roleUser);

}
