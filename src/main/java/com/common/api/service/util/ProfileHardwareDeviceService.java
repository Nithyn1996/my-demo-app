package com.common.api.service.util;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.datasink.service.HardwareDeviceService;
import com.common.api.datasink.service.UserDeviceService;
import com.common.api.model.HardwareDevice;
import com.common.api.model.UserDevice;
import com.common.api.model.field.UserSessionField;
import com.common.api.model.status.ModelStatus;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.ResultSetConversion;

@Service
public class ProfileHardwareDeviceService extends APIFixedConstant {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    UserDeviceService userDeviceService;
    @Autowired
    HardwareDeviceService hardwareDeviceService;

	public UserSessionField createHardwareDevice(String companyId, String divisionId, String moduleId, String userId, UserSessionField userSessionField, String deviceStatus) {

		String apiLogString = "LOG_API_USER_DEVICE_SERVICE_CREATE: ";
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();

		try {

			String hwDeviceId   = "";
			String userDeviceId = "";
			String status = ModelStatus.DeviceStatus.REGISTERED.toString();
			String hardwareDeviceResourceType  = APIResourceName.HARDWARE_DEVICE.toString();
			String userDeviceResourceType      = APIResourceName.USER_DEVICE.toString();

			String deviceOrderId  	   = userSessionField.getDeviceOrderId();
			String deviceUniqueId  	   = userSessionField.getDeviceUniqueId();
			String deviceVersionNumber = userSessionField.getDeviceVersionNumber();
			String deviceModelName 	   = userSessionField.getDeviceModelName();
			String deviceType	       = userSessionField.getDeviceType();

			if (deviceUniqueId.length() > 0 && deviceVersionNumber.length() > 0 && deviceModelName.length() > 0 && deviceType.length() > 0) {

				List<String> statusList = Arrays.asList(status);
				List<String> hwDeviceTypeList = Arrays.asList(hardwareDeviceResourceType);
				List<String> userDeviceTypeList = Arrays.asList(userDeviceResourceType);

				List<HardwareDevice> hwDeviceList = hardwareDeviceService.viewListByCriteria(companyId, "", deviceOrderId, deviceUniqueId, deviceVersionNumber, deviceModelName, deviceType, statusList, hwDeviceTypeList, "", "", 0, 0);

				if (hwDeviceList.size() > 0) {
					hwDeviceId = hwDeviceList.get(0).getId();
				} else {

					HardwareDevice hwDevice = new HardwareDevice();
					hwDevice.setCompanyId(companyId);
					hwDevice.setStatus(status);
					hwDevice.setDeviceOrderId(deviceOrderId);
					hwDevice.setDeviceUniqueId(deviceUniqueId);
					hwDevice.setDeviceVersionNumber(deviceVersionNumber);
					hwDevice.setDeviceModelName(deviceModelName);
					hwDevice.setDeviceType(deviceType);
					hwDevice.setType(hardwareDeviceResourceType);
					hwDevice.setCreatedAt(dbOperationStartTime);
					hwDevice.setModifiedAt(dbOperationStartTime);
					hwDevice.setCreatedBy("");
					hwDevice.setModifiedBy("");
					HardwareDevice hwDeviceRes = hardwareDeviceService.createHardwareDevice(hwDevice);

					if (hwDeviceRes != null) {
						hwDeviceId = hwDeviceRes.getId();
					}
				}

				if (!deviceStatus.equalsIgnoreCase(GC_STATUS_AUDIT)) {

					List<UserDevice> userDeviceList = userDeviceService.viewListByCriteria(companyId, userId, "", deviceOrderId, deviceUniqueId, deviceVersionNumber, deviceModelName, deviceType, statusList, userDeviceTypeList, "", "", 0, 0);

					if (userDeviceList.size() > 0) {
						userDeviceId = userDeviceList.get(0).getId();
					} else {

						UserDevice userDevice = new UserDevice();
						userDevice.setCompanyId(companyId);
						userDevice.setDivisionId(divisionId);
						userDevice.setModuleId(moduleId);
						userDevice.setUserId(userId);
						userDevice.setStatus(status);
						userDevice.setDeviceOrderId(deviceOrderId);
						userDevice.setDeviceUniqueId(deviceUniqueId);
						userDevice.setDeviceVersionNumber(deviceVersionNumber);
						userDevice.setDeviceModelName(deviceModelName);
						userDevice.setDeviceType(deviceType);
						userDevice.setType(userDeviceResourceType);
						userDevice.setCreatedAt(dbOperationStartTime);
						userDevice.setModifiedAt(dbOperationStartTime);
						userDevice.setCreatedBy("");
						userDevice.setModifiedBy("");

						int removedCount = userDeviceService.removeUserDeviceByCriteria(companyId, userId, deviceType);
						APILog.writeInfoLog(apiLogString + " inactivatedDeviceCount: " + removedCount);

						UserDevice userDeviceRes = userDeviceService.createUserDevice(userDevice);
						if (userDeviceRes != null) {
							userDeviceId = userDeviceRes.getId();
						}
					}
				}

				if (hwDeviceId.length() > 0 || userDeviceId.length() > 0) {
					return userSessionField;
				}
			}

		} catch (Exception errMess) {
		}
		return new UserSessionField();
	}

}