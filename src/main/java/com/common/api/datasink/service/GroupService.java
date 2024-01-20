package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Group;

public interface GroupService {

	public List<Group> viewListByCriteria(String companyId, String id, String name, String code, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

}
