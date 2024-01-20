package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.GroupPreference;

public interface GroupPreferenceService {

	public List<GroupPreference> viewListByCriteria(String companyId, String groupId, String id, String name, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

}
