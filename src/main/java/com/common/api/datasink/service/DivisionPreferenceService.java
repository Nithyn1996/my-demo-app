package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.DivisionPreference;

public interface DivisionPreferenceService {

	public List<DivisionPreference> viewListByCriteria(String companyId, String groupId, String divisionId, String id, String name, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public DivisionPreference createDivisionPreference(DivisionPreference divisionPreference);

	public DivisionPreference updateDivisionPreference(DivisionPreference divisionPreference);

	public int removeDivisionPreferenceById(String companyId, String divisionId, String id);

}
