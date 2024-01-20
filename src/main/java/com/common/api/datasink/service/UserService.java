package com.common.api.datasink.service;

import java.sql.Timestamp;
import java.util.List;

import com.common.api.model.User;

public interface UserService {

	public List<User> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String username, String email, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<User> viewCaseInsensitiveListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String username, String email, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public User createUser(User user);

	public User updateUser(User user);

	public User updateUserPassword(User user);

	public User updateUserProfilePicture(User user);

	public User updateUserMobilePin(User user);

	public int updateUserNextActivity(String userId, String nextActivity);

	public int updateUserForPasswordChangeCondition(Timestamp modifiedAt, int noOfDays, String nextActivity);

}
