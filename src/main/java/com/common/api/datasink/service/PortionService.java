package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Portion;

public interface PortionService {

	public List<Portion> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String id, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public Portion createPortion(Portion portion);

	public Portion updatePortion(Portion portion);

}
