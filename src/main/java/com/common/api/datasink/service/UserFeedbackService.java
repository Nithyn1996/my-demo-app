package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.UserFeedback;

public interface UserFeedbackService {

	public List<UserFeedback> viewListByCriteria(String companyId, String divisionId, String moduleId, String userId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public UserFeedback createUserFeedback(UserFeedback userFeedback);

}
