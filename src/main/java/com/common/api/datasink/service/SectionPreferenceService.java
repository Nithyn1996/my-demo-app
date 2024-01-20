package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.SectionPreference;

public interface SectionPreferenceService {

	public List<SectionPreference> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String id, String name, String code, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public SectionPreference createSectionPreference(SectionPreference sectionPreference);

	public SectionPreference updateSectionPreference(SectionPreference sectionPreference);

}
