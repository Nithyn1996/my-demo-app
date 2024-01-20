package com.common.api.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import com.common.api.constant.APICustomConstant;
import com.common.api.provider.HWDeviceDataService;
import com.common.api.provider.HWDeviceService;
import com.common.api.util.APILog;

@Service
public class MQTTSubscribeData extends APICustomConstant implements MQTTUtils {

	public static MqttClient mqttClient;
	HWDeviceService hardwareProfileService;
	HWDeviceDataService hardwareProfileDataService;

	public MQTTSubscribeData() {
	}

	public MQTTSubscribeData(MqttClient mqttClient, HWDeviceService hardwareProfileService, HWDeviceDataService hardwareProfileDataService) {

		String logString = MQTT_SUBSCRIBE_DATA_ONCREATE;

		MQTTSubscribeData.mqttClient = mqttClient;
		this.hardwareProfileService = hardwareProfileService;
		this.hardwareProfileDataService = hardwareProfileDataService;

		if (mqttClient != null) {
			logString = logString + " status: Connected";
			APILog.writeInfoLog(logString);
		}
	}

	public void readProtoDataFromController(String topic, MqttMessage message) {

		String logString = MQTT_SUBSCRIBE_DATA + " topic : " + topic;

		try {

			String data = new String(message.getPayload());
			logString = logString + " data: " + data;

 			if (topic.equals(TS_DEVICE)) {
 				hardwareProfileService.device(data);
 			} else if (topic.equals(TS_DEVICE_LIVE_DATA)) {
				hardwareProfileDataService.deviceDataLive(data);
 			} else if (topic.equals(TS_DEVICE_ERROR_DATA)) {
 				hardwareProfileDataService.deviceDataError(data);
 			}

		} catch (Exception errMess) {
			logString = logString + " exceptionType: " + NORMAL_EXCEPTION + " errMess: " + errMess.getMessage();
		}
		//APILog.writeInfoLog(logString);
	}

}
