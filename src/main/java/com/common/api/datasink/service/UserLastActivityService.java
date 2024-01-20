package com.common.api.datasink.service;

import java.sql.Timestamp;
import java.util.List;

import com.common.api.model.UserLastActivity;

public interface UserLastActivityService {

	public List<UserLastActivity> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String id, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public UserLastActivity createUserLastActivity(UserLastActivity userLastActivity);

	public UserLastActivity updateUserLastActivity(UserLastActivity userLastActivity);

	public int updateUserLastActivityByPasswordChanged(String userId, Timestamp modifiedAt, Timestamp passwordChangedAt);

	public int updateUserLastActivityBySession(String userId, Timestamp modifiedAt, Timestamp sessionWebAt, Timestamp sessionIosAt, Timestamp sessionAndroidAt);

	public int updateUserLastActivityByActivity(String userId, Timestamp modifiedAt, Timestamp activityWebAt, Timestamp activityIosAt, Timestamp activityAndroidAt);

	public int updateUserLastActivityByNoActivity(String userId, Timestamp modifiedAt, Timestamp noActivityPushWebAt, Timestamp noActivityPushIosAt, Timestamp noActivityPushAndroidAt);

	public int updateUserLastActivityByAppVersion(String userId, Timestamp modifiedAt, Timestamp appUpdatePushIosAt, Timestamp appUpdatePushAndroidAt);

	public int updateUserLastActivityByMapVersion(String userId, Timestamp modifiedAt, Timestamp mapUpdatePushIosAt, Timestamp mapUpdatePushAndroidAt);

}
