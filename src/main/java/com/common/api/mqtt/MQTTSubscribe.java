package com.common.api.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.common.api.provider.HWDeviceDataService;
import com.common.api.provider.HWDeviceService;
import com.common.api.util.APILog;

public class MQTTSubscribe implements MqttCallbackExtended, MQTTUtils {

	public static MqttClient mqttClient;
	HWDeviceService hardwareProfileService;
	HWDeviceDataService hardwareProfileDataService;

	public MQTTSubscribe(MqttClient mqttClient, HWDeviceService hardwareProfileService, HWDeviceDataService hardwareProfileDataService) {
		MQTTSubscribe.mqttClient = mqttClient;
		this.hardwareProfileService = hardwareProfileService;
		this.hardwareProfileDataService = hardwareProfileDataService;
	}

	@Override
	public void connectionLost(Throwable throwable) {
		APILog.writeInfoLog(MQTT_CONNECTION_LOST);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {

		String logString = MQTT_SUBSCRIBE + " topic: " + topic;
		String data = new String(message.getPayload());
		logString = logString + " data: " + data;

		MQTTSubscribeData subscribeData = new MQTTSubscribeData(mqttClient, hardwareProfileService, hardwareProfileDataService);
		subscribeData.readProtoDataFromController(topic, message);
		//APILog.writeInfoLog(logString);

	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {

		String logString = MQTT_RECONNECTION + " serverURI : " + serverURI;

		if (reconnect) {

			try {

				// Topics - Subscribe List
				mqttClient.subscribe(TS_DEVICE);
				mqttClient.subscribe(TS_DEVICE_LIVE_DATA);
				mqttClient.subscribe(TS_DEVICE_ERROR_DATA);

				// MQTT Connection Status Logs
				logString = logString + " status: Connected";
				logString = logString + " Topic: Subscribed are below, ";

				// Subscribe Topics List Logs
				logString = logString + " \r\n " + TS_DEVICE;
				logString = logString + " \r\n " + TS_DEVICE_LIVE_DATA;
				logString = logString + " \r\n " + TS_DEVICE_ERROR_DATA;

			} catch (MqttException errMess) {
				logString = logString + " status: Failed exceptionType: " + MQTT_EXCEPTION + " errMess: " + errMess.getMessage();
			} catch (Exception errMess) {
				logString = logString + " status: Failed exceptionType: " + NORMAL_EXCEPTION + " errMess: " + errMess.getMessage();
			}
			//APILog.writeInfoLog(logString);
		}
	}

}
