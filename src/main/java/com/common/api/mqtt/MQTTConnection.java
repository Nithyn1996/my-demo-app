package com.common.api.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.provider.HWDeviceDataService;
import com.common.api.provider.HWDeviceService;
import com.common.api.util.APILog;

@Service
public class MQTTConnection implements MQTTUtils {

	IMqttToken token;
	MqttClient mqttClient;
	MqttConnectOptions options;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public void clientConnect(String serverURI, String appDataSourceProvider, HWDeviceService hardwareProfileService, HWDeviceDataService hardwareProfileDataService) {

		String clientId = MQTT_CLIENT_ID + "_" + appDataSourceProvider;
		String logString = MQTT_CONNECTION + " serverURI : " + serverURI + " clientId: " + clientId;

		try {

			mqttClient = new MqttClient(serverURI, clientId);
			mqttClient.setCallback(new MQTTSubscribe(mqttClient, hardwareProfileService, hardwareProfileDataService));
				new MQTTSubscribeData(mqttClient, hardwareProfileService, hardwareProfileDataService);
			options = new MqttConnectOptions();
			options.setConnectionTimeout(MQTT_CONNECT_TIMEOUT);
			options.setAutomaticReconnect(true);
			token = mqttClient.connectWithResult(options);

			// Topics - Subscribe List
			mqttClient.subscribe(TS_DEVICE);
			mqttClient.subscribe(TS_DEVICE_LIVE_DATA);
			mqttClient.subscribe(TS_DEVICE_ERROR_DATA);

			// MQTT Connection Status Logs
			logString = logString + " status: Connected";

			// Subscribe Topics List Logs
			logString = logString + " Topic: Subscribed are below, ";
			logString = logString + " \r\n " + TS_DEVICE;
			logString = logString + " \r\n " + TS_DEVICE_LIVE_DATA;
			logString = logString + " \r\n " + TS_DEVICE_ERROR_DATA;

		} catch (MqttException errMess) {
			logString = logString + " status: Failed exceptionType: " + MQTT_EXCEPTION + " errMess: " + errMess.getMessage();
		} catch (Exception errMess) {
			logString = logString + " status: Failed exceptionType: " + NORMAL_EXCEPTION + " errMess: " + errMess.getMessage();
		}
		APILog.writeInfoLog(logString);
	}

}
