package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.UserPreference;

public interface UserPreferenceService {

	public List<UserPreference> viewListByCriteria(String companyId, String divisionId, String moduleId, String userId, String id, String name, String code, List<String> subCategoryList, List<String> categoryList, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public UserPreference createUserPreference(UserPreference userPreference);

	public UserPreference updateUserPreference(UserPreference userPreference);

	public int removeUserPreferenceById(String companyId, String divisionId, String id);

}
