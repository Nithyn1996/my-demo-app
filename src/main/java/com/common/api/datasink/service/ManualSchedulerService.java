package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.User;

public interface ManualSchedulerService {

	public List<User> viewUserListByUserSession(String companyId, String groupId, String divisionId, String moduleId, String userId, String type, String userSessionType, String sortBy, String sortOrder, int offset, int limit);

	public List<User> viewUserListByDevice(String companyId, String groupId, String divisionId, String moduleId, String userId, String type, String userSessionType, String sortBy, String sortOrder, int offset, int limit);

}