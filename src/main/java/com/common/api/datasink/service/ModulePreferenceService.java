package com.common.api.datasink.service;

import java.sql.Timestamp;
import java.util.List;

import com.common.api.model.ModulePreference;

public interface ModulePreferenceService {

	public List<ModulePreference> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String name, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<ModulePreference> viewListByCriteria(String companyId, String notificationStatus, String type, String sortBy, String sortOrder, int offset, int limit);

	public ModulePreference updateModulePreference(ModulePreference modulePreference);

	public int updateNotificationStatusPendingByNotificationStatusTime(String type, String notificationStatus, Timestamp modifiedAt);

}
