package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.DeviceSummary;
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.KeyValuesFloat;

public interface DeviceSummaryService {

	public List<DeviceSummary> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String portionId, String deviceId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public DeviceSummary createDeviceSummary(DeviceSummary deviceSummary);
	
	public DeviceSummary updateDeviceSummary(DeviceSummary deviceSummary);
	
	public int updateDeviceSummaryCountByDeviceId(String userId, String deviceId, List<String> countFieldList, List<KeyValuesFloat> keyValues, DeviceDataRunningField ddRunningField); 
	
	public int removeDeviceSummaryById(String companyId, String userId, String id);
	
}
