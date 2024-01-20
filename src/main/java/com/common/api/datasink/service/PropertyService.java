package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Property;

public interface PropertyService {

	public List<Property> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String id, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public Property createProperty(Property property);

	public Property updateProperty(Property property);

}
