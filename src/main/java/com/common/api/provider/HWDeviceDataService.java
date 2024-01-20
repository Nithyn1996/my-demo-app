package com.common.api.provider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.DeviceDataService;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.PortionService;
import com.common.api.model.Device;
import com.common.api.model.DeviceData;
import com.common.api.model.Portion;
import com.common.api.model.field.DeviceDataErrorField;
import com.common.api.model.field.DeviceDataLiveField;
import com.common.api.model.type.CategoryType;
import com.common.api.request.HWDeviceDataError;
import com.common.api.request.HWDeviceDataLive;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.HWDataConversion;
import com.common.api.util.PayloadValidatorHardware;

@Service
public class HWDeviceDataService extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	PayloadValidatorHardware payloadValidatorHW;

	@Autowired
	PortionService portionService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	DeviceDataService deviceDataService;

	public DeviceData deviceDataLive(String deviceData) {

		DeviceData result = new DeviceData();
		String logString =  "LOG_PROVIDER_HW_DEVICE_DATA_LIVE";
		logString = logString + " deviceData: " + deviceData;

		try {

			HWDeviceDataLive hwDeviceData = HWDataConversion.stringToHWDeviceDataLive(deviceData);

			if (hwDeviceData != null && hwDeviceData.getpId().length() > 0) {

				List<APIPreConditionErrorField> errorList = payloadValidatorHW.isValidHWDeviceDataLive(GC_ACTION_CREATE, hwDeviceData);

				if (errorList.size() <= 0) {

					List<String> emptyList = new ArrayList<>();
					String portionId  	   = hwDeviceData.getpId();
					String uniqueCode 	   = hwDeviceData.getuCode();
					String type			   = hwDeviceData.getType();
					DeviceDataLiveField data = hwDeviceData.getData();

					List<Portion> portionList = portionService.viewListByCriteria("", "", "", "", "", "", "", portionId, "", emptyList, emptyList, "", "", 0, 0);

					if (portionList.size() > 0) {

						Portion portionDetail = portionList.get(0);
						String companyId  = portionDetail.getCompanyId();
						String divisionId = portionDetail.getDivisionId();
						String moduleId   = portionDetail.getModuleId();
						String userId     = portionDetail.getUserId();
						String propertyId = portionDetail.getPropertyId();
						String sectionId  = portionDetail.getSectionId();

						List<Device> deviceList = deviceService.viewListByCriteria("", "", "", "", "", "", "", portionId, "", uniqueCode, "", emptyList, emptyList, "", "", 0, 0);

						if (deviceList.size() > 0) {

							Device deviceDetail = deviceList.get(0);
							String deviceId = deviceDetail.getId();

							Timestamp dbOperationTime = APIDateTime.getGlobalDateTimeDBOperation();
							DeviceData deviceDataObj = new DeviceData();
							deviceDataObj.setCompanyId(companyId);
							deviceDataObj.setDivisionId(divisionId);
							deviceDataObj.setModuleId(moduleId);
							deviceDataObj.setUserId(userId);
							deviceDataObj.setPropertyId(propertyId);
							deviceDataObj.setSectionId(sectionId);
							deviceDataObj.setPortionId(portionId);
							deviceDataObj.setDeviceId(deviceId);

							deviceDataObj.setDeviceDataErrorField(null);
							deviceDataObj.setDeviceDataLiveField(data);
							deviceDataObj.setCategory(CategoryType.DeviceDataCategory.LIVE_DATA.toString());
							deviceDataObj.setType(type);
							deviceDataObj.setStatus(GC_STATUS_REGISTERED);

							deviceDataObj.setCreatedBy(userId);
							deviceDataObj.setModifiedBy(userId);
							deviceDataObj.setCreatedAt(dbOperationTime);
							deviceDataObj.setModifiedAt(dbOperationTime);
							deviceDataObj.setInsertedAt(dbOperationTime);

							deviceDataObj = deviceDataService.createDeviceData(deviceDataObj);

							if (deviceDataObj != null && deviceDataObj.getId() != null) {
								String deviceDataId = deviceDataObj.getId();
								logString = logString + " deviceCreate: Success deviceDataId: " + deviceDataId;
								result    = deviceDataObj;
							} else {
								logString = logString + " deviceCreate: Failed";
							}

						} else {
							logString = logString + " deviceList.size(): " + deviceList.size();
						}

					} else {
						logString = logString + " portionList.size(): " + portionList.size();
					}

				} else {
					logString = logString + " validationError: " + errorList.toString();
				}
			}

		} catch (Exception errMess) {
			logString = logString + " Exception errMess: " + errMess.getMessage();
		}
		APILog.writeInfoLog(logString);
		return result;
	}

	public DeviceData deviceDataError(String deviceData) {

		DeviceData result = new DeviceData();
		String logString =  "LOG_PROVIDER_HW_DEVICE_DATA_ERROR";
		logString = logString + " deviceData: " + deviceData;

		try {

			HWDeviceDataError hwDeviceDataInfo = HWDataConversion.stringToHWDeviceDataError(deviceData);

			if (hwDeviceDataInfo != null && hwDeviceDataInfo.getpId().length() > 0) {

				List<APIPreConditionErrorField> errorList = payloadValidatorHW.isValidHWDeviceDataError(GC_ACTION_CREATE, hwDeviceDataInfo);

				if (errorList.size() <= 0) {

					List<String> emptyList = new ArrayList<>();
					String portionId   	   = hwDeviceDataInfo.getpId();
					String uniqueCode 	   = hwDeviceDataInfo.getuCode();
					String type			   = hwDeviceDataInfo.getType();
					DeviceDataErrorField data = hwDeviceDataInfo.getData();

					List<Portion> portionList = portionService.viewListByCriteria("", "", "", "", "", "", "", portionId, "", emptyList, emptyList, "", "", 0, 0);

					if (portionList.size() > 0) {

						Portion portionDetail = portionList.get(0);
						String companyId  = portionDetail.getCompanyId();
						String divisionId = portionDetail.getDivisionId();
						String moduleId   = portionDetail.getModuleId();
						String userId     = portionDetail.getUserId();
						String propertyId = portionDetail.getPropertyId();
						String sectionId  = portionDetail.getSectionId();

						List<Device> deviceList = deviceService.viewListByCriteria("", "", "", "", "", "", "", portionId, "", uniqueCode, "", emptyList, emptyList, "", "", 0, 0);

						if (deviceList.size() > 0) {

							Device deviceDetail = deviceList.get(0);
							String deviceId = deviceDetail.getId();

							Timestamp dbOperationTime = APIDateTime.getGlobalDateTimeDBOperation();
							DeviceData deviceDataObj = new DeviceData();
							deviceDataObj.setCompanyId(companyId);
							deviceDataObj.setDivisionId(divisionId);
							deviceDataObj.setModuleId(moduleId);
							deviceDataObj.setUserId(userId);
							deviceDataObj.setPropertyId(propertyId);
							deviceDataObj.setSectionId(sectionId);
							deviceDataObj.setPortionId(portionId);
							deviceDataObj.setDeviceId(deviceId);

							deviceDataObj.setDeviceDataErrorField(data);
							deviceDataObj.setDeviceDataLiveField(null);
							deviceDataObj.setCategory(CategoryType.DeviceDataCategory.ERROR_DATA.toString());
							deviceDataObj.setType(type);
							deviceDataObj.setStatus(GC_STATUS_REGISTERED);

							deviceDataObj.setCreatedBy(userId);
							deviceDataObj.setModifiedBy(userId);
							deviceDataObj.setCreatedAt(dbOperationTime);
							deviceDataObj.setModifiedAt(dbOperationTime);
							deviceDataObj.setInsertedAt(dbOperationTime);

							deviceDataObj = deviceDataService.createDeviceData(deviceDataObj);

							if (deviceDataObj != null && deviceDataObj.getId() != null) {
								String deviceDataId = deviceDataObj.getId();
								logString = logString + " deviceCreate: Success deviceDataId: " + deviceDataId;
								result    = deviceDataObj;
							} else {
								logString = logString + " deviceCreate: Failed";
							}

						} else {
							logString = logString + " deviceList.size(): " + deviceList.size();
						}

					} else {
						logString = logString + " portionList.size(): " + portionList.size();
					}

				} else {
					logString = logString + " validationError: " + errorList.toString();
				}
			}

		} catch (Exception errMess) {
			logString = logString + " Exception errMess: " + errMess.getMessage();
		}
		APILog.writeInfoLog(logString);
		return result;
	}

}
