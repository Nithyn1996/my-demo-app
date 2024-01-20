package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.DeviceData; 
 
public interface DeviceDataService {       
	  
	public List<DeviceData> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String portionId, String deviceId, String id, List<String> categoryList, List<String> subCategoryList, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public DeviceData createDeviceData(DeviceData deviceData); 
 
	public DeviceData updateDeviceData(DeviceData deviceData);   
	 
}    
