package com.common.api.datasink.service;

import java.sql.Timestamp;
import java.util.List;

import com.common.api.model.UserSession;

public interface UserSessionService {

	public List<UserSession> viewListByCriteria(String companyId, String userId, String id, String username, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<UserSession> viewListWithSecretKeyByCriteria(String companyId, String userId, String id, String username, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<UserSession> viewUserListByCriteriaForPushNotification(String companyId, String divisionId, String userId, String userCategory, List<String> deviceStatusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<UserSession> viewListByCriteriaForPushNotification(String companyId, String divisionId, String userId, String userCategory, List<String> deviceStatusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<UserSession> viewListByCriteriaForSchedulerPushNotification(String companyId, String divisionId, String userId, String type, String sortBy, String sortOrder, int offset, int limit);

	public UserSession createUserSession(UserSession userSession);

	public int modifyUserSession(UserSession userSession);

	public int removeUserSessionById(String id);

	public int removeUserSessionByUserId(String userId);

	public int removeUserSessionByTypeAndDeviceUniqueId(String type, String deviceUniqueId);

	public int removeUserSessionByTypeAndDeviceToken(String type, String deviceToken);

	public int removeActiveUserSessionByModifiedAt(Timestamp modifiedAt);

	public int removeInactiveUserSessionByModifiedAt(Timestamp modifiedAt);

}
