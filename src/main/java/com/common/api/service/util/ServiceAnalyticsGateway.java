package com.common.api.service.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.DeviceService;
import com.common.api.model.Device;
import com.common.api.model.field.DeviceSafetyField;
import com.common.api.provider.RetrofitClientProvider;
import com.common.api.response.RestClientResponseMapper;
import com.common.api.util.APILog;

@Service
@PropertySource({ "classpath:application.properties" })
public class ServiceAnalyticsGateway extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/** Properties Constants */
	@Value("${app.analytics.api.url}")
	private String appAnalyticsApiUrl = "";
	@Value("${app.analytics.api.session.id}")
	private String appAnalyticsApiSessionId = "";
	@Value("${app.analytics.api.referred.by}")
	private String appAnalyticsApiReferredBy = "";
	@Value("${app.analytics.api.environment}")
	private String appAnalyticsApiEnvironment = "";

	@Autowired
	RetrofitClientProvider retrofitClientProvider;
	@Autowired
	DeviceService deviceService;

	public String getDeviceSafetyDetails (String companyId, String userId, String deviceId, String fileExtension, String vehicleType) {

		String apiLogString = "ANALYTICS_SERVER_SAFETY_CHECK ";
		int analyticsUpdatedCount = 0;
		String result = GC_STATUS_ERROR;
		String method = SP_GET_METHOD_DEVICE_SAFETY;

		try {

			Map<String, String> headers = new HashMap<>();
			headers.put("referredBy", appAnalyticsApiReferredBy);
			headers.put("sessionId",  appAnalyticsApiSessionId);

			Map<String, String> params  = new HashMap<>();
			params.put("environment", 	appAnalyticsApiEnvironment);
			params.put("userId", 	  	userId);
			params.put("deviceId",    	deviceId);
			params.put("fileExtension", fileExtension);
			params.put("vehicleType",   vehicleType);

			RestClientResponseMapper resultObject = retrofitClientProvider.retrofitMethodGet(appAnalyticsApiUrl, method, "", "", headers, params);
			int code = resultObject.getCode();
			if (code == 200) {
				result = GC_STATUS_SUCCESS;
			}

			List<Device> deviceList = deviceService.viewListByCriteria(companyId, userId, "", "", "", deviceId, "", "", "", 0, 0);
			if (deviceList.size() > 0) {

				Device deviceDetail = deviceList.get(0);
				DeviceSafetyField devicesafetyField = deviceDetail.getDeviceSafetyField();
				int analyticsServerCode = devicesafetyField.getAnalyticsServerStatusCode();

				if (analyticsServerCode <= 0) {

					devicesafetyField.setHelmetAloneCount(resultObject.getHelmetAloneCount());
					devicesafetyField.setUserWithHelmetCount(resultObject.getUserWithHelmetCount());
					devicesafetyField.setUserWithoutHelmetCount(resultObject.getUserWithoutHelmetCount());

					devicesafetyField.setUserWithSeatbeltCount(resultObject.getUserWithSeatbeltCount());
					devicesafetyField.setUserWithoutSeatbeltCount(resultObject.getUserWithoutSeatbeltCount());

					devicesafetyField.setAnalyticsServerStatusCode(code);
					deviceDetail.setAnalyticServerStatus(result);

					analyticsUpdatedCount = deviceService.updateDeviceSafetyFieldById(deviceDetail);
				}
			}

		} catch (Exception errMess) {
		}
		APILog.writeInfoLog(apiLogString + " userId: " + userId + " deviceId: " + deviceId + " analyticsUpdatedCount: " + analyticsUpdatedCount);
		return result;
	}

}