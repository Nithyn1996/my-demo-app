package com.common.api.datasink.service;

import java.util.Date;
import java.util.List;

import com.common.api.model.Device; 
 
public interface DeviceService {      
	  
	public List<Device> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String portionId, String id, String uniqueCode, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);
 
	public List<Device> viewListByDeviceId(String companyId, String groupId, String divisionId, String moduleId, String userId, String id, List<String> validationStatusList, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<Device> viewDeviceExistListByCreatedAt(String companyId, String userId, Date createdAt, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);
	
	public List<Device> viewListByCriteria(String companyId, String userId, String propertyId, String sectionId, String portionId, String id, String deviceRawFileName, String sortBy, String sortOrder, int offset, int limit);
 
	public Device createDevice(Device device); 

	public Device updateDevice(Device device);

	public Device updateDeviceByCSV(Device device); 
	
	public int updateDeviceInternalSystemStatusById(String companyId, String userId, String deviceId, String internalSystemStatus);
	
	public int updateDeviceValidationScoreStatusById(String companyId, String userId, String deviceId, String validationScoreStatus);
	
	public int updateDeviceStatusById(String companyId, String userId, String deviceId, String status, String internalSystemStatus, String scoreValidationStatus, String deviceDataInsertStatus);   

	public int updateDeviceDataCountByDeviceId(String userId, String deviceId, int startData, int distanceData, int alertData, int stressStrainData, int manualData, int endData, 
												float mScreenOnDuration, float mUseCallDuration, float overSpeedDuration, float mScreenOnKiloMeter, float mUseCallKiloMeter, float overSpeedKiloMeter,  
											    int riskNegativeCount, int riskZeroCount, int riskSlot1Count, int riskSlot2Count, int riskSlot3Count, int riskSlot4Count, int riskSlot5Count,
												int riskSlot6Count, int riskSlot7Count, int riskSlot8Count, int riskSlot9Count, int riskSlot10Count);   

	public int updateDeviceMultipartFilePathById(String companyId, String userId, String deviceId, String fileName);

	public int updateDeviceMultipartRawFilePathById(String companyId, String userId, String deviceId, String fileName, String fileStatus);
 
	public int updateDeviceSafetyFieldById(Device device); 
	
	public int removeDeviceById(String companyId, String userId, String id);

}	
