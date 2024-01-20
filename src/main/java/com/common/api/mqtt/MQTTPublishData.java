package com.common.api.mqtt;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.api.constant.APICustomConstant;

@Service
public class MQTTPublishData extends APICustomConstant implements MQTTUtils {

	@Autowired
	MQTTConnection mqttConnection;

	public void publishData(MqttClient mqttClient, final String topic, String data) {

		String logString = MQTT_PUBLISH_DATA + " topic : " + topic + " data: " + data;

        try {

            byte[] encodedPayload = new byte[0];
            encodedPayload = data.getBytes("UTF-8");

            MqttMessage message = new MqttMessage(encodedPayload);
            message.setRetained(false);
            mqttClient.publish(topic, message);

        } catch (MqttException errMess) {
			logString = logString + " exceptionType: " + MQTT_EXCEPTION + " errMess: " + errMess.getMessage();
        } catch (UnsupportedEncodingException errMess) {
			logString = logString + " exceptionType: " + UNSUPPORTED_ENCODING_EXCEPTION + " errMess: " + errMess.getMessage();
        }
		//APILog.writeInfoLog(logString);
    }

}
