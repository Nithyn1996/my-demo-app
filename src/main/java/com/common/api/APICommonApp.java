package com.common.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.common.api.mqtt.MQTTConnection;
import com.common.api.provider.HWDeviceDataService;
import com.common.api.provider.HWDeviceService;

@SpringBootApplication
@PropertySource({ "classpath:application.properties"})
public class APICommonApp {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/** Case Constants*/
	@Value("${app.mqtt.active.mode}")
	private boolean appMqttActiveMode = false;
	@Value("${app.mqtt.broker.uri}")
	private String serverURI = "";
	@Value("${app.mqtt.client.suffix.id}")
	private String appMqttClientSuffixId = "";

	/** Service Module */
	@Autowired
	HWDeviceService hardwareProfileService;
	@Autowired
	HWDeviceDataService hardwareProfileDataService;

	public static void main(String[] args) {
		SpringApplication.run(APICommonApp.class, args);
	}

    @PostConstruct
    public void init() {
    	if (appMqttActiveMode && serverURI.length() > 0) {
	        MQTTConnection mqttConnection = new MQTTConnection();
	        mqttConnection.clientConnect(serverURI, appMqttClientSuffixId, hardwareProfileService, hardwareProfileDataService);
    	}
    }

}
