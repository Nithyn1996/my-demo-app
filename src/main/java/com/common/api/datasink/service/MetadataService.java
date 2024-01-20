package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Metadata;

public interface MetadataService {

	public List<Metadata> getAvailableMetadata(String companyId);

	public List<Metadata> viewListByCriteria(String companyId, String id, String moduleName, String fieldName, String fieldValue, String sortBy, String sortOrder, int offset, int limit);

	public List<Metadata> viewCardinalityListByCriteria(String companyId, String fieldValue);

}
