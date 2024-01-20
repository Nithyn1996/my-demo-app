package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.UserDevice;

public interface UserDeviceService {

	public List<UserDevice> viewListByCriteria(String companyId, String userId, String id, String deviceOrderId, String deviceUniqueId, String deviceVersionNumber, String deviceModelName, String deviceType, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public UserDevice createUserDevice(UserDevice userDevice);

	public UserDevice updateUserDevice(UserDevice userDevice);

	public int removeUserDeviceByCriteria(String companyId, String userId, String deviceType);

}
