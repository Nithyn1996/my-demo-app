package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Language;

public interface LanguageService {

	public List<Language> viewListByCriteria(String companyId, String id, String name, String code, List<String> status, List<String> type, String sortBy, String sortOrder, int offset, int limit);

}
