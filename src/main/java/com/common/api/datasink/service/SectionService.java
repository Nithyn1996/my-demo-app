package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Section;

public interface SectionService {

	public List<Section> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String id, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public Section createSection(Section section);

	public Section updateSection(Section section);

}
