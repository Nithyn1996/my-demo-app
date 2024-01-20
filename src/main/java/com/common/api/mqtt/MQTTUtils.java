package com.common.api.mqtt;

import org.springframework.stereotype.Service;

@Service
public interface MQTTUtils {

	// Constants
	public static final int MQTT_CONNECT_TIMEOUT    = 10 * 1000;
    public static final String MQTT_CONNECTION      = "MQTT_CONNECTION: ";
    public static final String MQTT_CLIENT_ID       = "MQTT_CLIENT_ID";
    public static final String MQTT_RECONNECTION    = "MQTT_RECONNECTION: ";
	public static final String MQTT_CONNECTION_LOST = "MQTT_CONNECTION_LOST:";
	public static final String MQTT_SUBSCRIBE		= "MQTT_SUBSCRIBE";
	public static final String MQTT_SUBSCRIBE_DATA	= "MQTT_SUBSCRIBE_DATA";
	public static final String MQTT_PUBLISH_DATA	= "MQTT_PUBLISH_DATA";
	public static final String MQTT_SUBSCRIBE_DATA_ONCREATE	= "MQTT_SUBSCRIBE_DATA_ONCREATE";

	public static final String NORMAL_EXCEPTION	    = "NORMAL_EXCEPTION";
	public static final String MQTT_EXCEPTION		= "MQTT_EXCEPTION";
	public static final String UNSUPPORTED_ENCODING_EXCEPTION = "UNSUPPORTED_ENCODING_EXCEPTION";

	// MQTT Subscribe Topics
	public static final String TS_DEVICE		    = "/device";
	public static final String TS_DEVICE_LIVE_DATA  = "/deviceDataLive";
	public static final String TS_DEVICE_ERROR_DATA = "/deviceDataError";

}
