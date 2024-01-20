package com.common.api.provider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.PortionService;
import com.common.api.model.Device;
import com.common.api.model.Portion;
import com.common.api.model.field.DeviceField;
import com.common.api.request.HWDevice;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.HWDataConversion;
import com.common.api.util.PayloadValidatorHardware;

@Service
public class HWDeviceService extends APIFixedConstant {

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

	public Device device(String deviceData) {

		Device result = new Device();
		String logString =  "LOG_PROVIDER_HW_DEVICE";
		logString = logString + " deviceData: " + deviceData;

		try {

			HWDevice hwDeviceInfo = HWDataConversion.stringToHWDevice(deviceData);

			if (hwDeviceInfo != null && hwDeviceInfo.getpId().length() > 0) {

				List<APIPreConditionErrorField> errorList = payloadValidatorHW.isValidHWDevice(GC_ACTION_CREATE, hwDeviceInfo);

				if (errorList.size() <= 0) {

					List<String> emptyList = new ArrayList<>();
					String portionId   	   = hwDeviceInfo.getpId();
					String uniqueCode 	   = hwDeviceInfo.getuCode();
					DeviceField data   	   = hwDeviceInfo.getData();
					String type			   = hwDeviceInfo.getType();

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

						if (deviceList.size() <= 0) {

							Timestamp dbOperationTime = APIDateTime.getGlobalDateTimeDBOperation();
							Device deviceObj = new Device();
							deviceObj.setCompanyId(companyId);
							deviceObj.setDivisionId(divisionId);
							deviceObj.setModuleId(moduleId);
							deviceObj.setUserId(userId);
							deviceObj.setPropertyId(propertyId);
							deviceObj.setSectionId(sectionId);
							deviceObj.setPortionId(portionId);

							deviceObj.setUniqueCode(uniqueCode);
							deviceObj.setName(uniqueCode);
							deviceObj.setDeviceField(data);

							deviceObj.setType(type);
							deviceObj.setStatus(GC_STATUS_REGISTERED);

							deviceObj.setCreatedBy(userId);
							deviceObj.setModifiedBy(userId);
							deviceObj.setCreatedAt(dbOperationTime);
							deviceObj.setModifiedAt(dbOperationTime);
							deviceObj.setInsertedAt(dbOperationTime);

							deviceObj = deviceService.createDevice(deviceObj);

							if (deviceObj != null && deviceObj.getId() != null) {
								String deviceId = deviceObj.getId();
								logString = logString + " deviceCreate: Success deviceId: " + deviceId;
								result    = deviceObj;
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

			} else {
				logString = logString + " jsonParserError ";
			}

		} catch (Exception errMess) {
			logString = logString + " Exception errMess: " + errMess.getMessage();
		}
		APILog.writeInfoLog(logString);
		return result;
	}

}
