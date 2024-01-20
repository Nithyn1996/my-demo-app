package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.HardwareDevice;

public interface HardwareDeviceService {

	public List<HardwareDevice> viewListByCriteria(String companyId, String id, String deviceOrderId, String deviceUniqueId, String deviceVersionNumber, String deviceModelName, String deviceType, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public HardwareDevice createHardwareDevice(HardwareDevice hardwareDevice);

	public HardwareDevice updateHardwareDevice(HardwareDevice hardwareDevice);

	public int removeHardwareDeviceByCriteria(String companyId, String id, String type);

}
