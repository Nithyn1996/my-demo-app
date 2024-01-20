package com.common.api.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.response.RestClientResponseMapper;
import com.common.api.util.APILog;

@Service
@PropertySource({ "classpath:application.properties"})
public class PushProvider extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	HTTPSProvider httpsProvider;
	@Autowired
	RetrofitClientProvider retrofitClientProvider;

	/** Properties Constants */
	@Value("${app.push.android.api.url}")
	private String appPushAndroidAPIURL = "";
	@Value("${app.push.android.api.key}")
	private String appPushAndroidAPIKey = "";
	@Value("${app.push.android.api.read.timeout.sec}")
	private int appPushAndroidAPIReadTimeoutSec = 1;
	@Value("${app.push.android.api.connection.timeout.sec}")
	private int appPushAndroidAPIConnectionTimeoutSec = 1;

	@Value("${app.push.web.api.url}")
	private String appPushWebAPIURL = "";
	@Value("${app.push.web.api.key}")
	private String appPushWebAPIKey = "";
	@Value("${app.push.web.api.read.timeout.sec}")
	private int appPushWebAPIReadTimeoutSec = 1;
	@Value("${app.push.web.api.connection.timeout.sec}")
	private int appPushWebAPIConnectionTimeoutSec = 1;

	@Value("${app.push.ios.api.url}")
	private String appPushIosAPIURL = "";
	@Value("${app.push.ios.api.key}")
	private String appPushIosAPIKey = "";
	@Value("${app.push.ios.api.read.timeout.sec}")
	private int appPushIosAPIReadTimeoutSec = 1;
	@Value("${app.push.ios.api.connection.timeout.sec}")
	private int appPushIosAPIConnectionTimeoutSec = 1;

	@Value("${app.push.ios.api.url}")
	private String appPushIOSAPIURL = "";
	@Value("${app.push.ios.api.key}")
	private String appPushIOSAPIKey = "";
	@Value("${app.push.ios.api.read.timeout.sec}")
	private int appPushIOSAPIReadTimeoutSec = 1;
	@Value("${app.push.ios.api.connection.timeout.sec}")
	private int appPushIOSAPIConnectionTimeoutSec = 1;

	public PushProvider() { }

	public String sendPushNotificationAndroid(List<String> deviceTokenIdList, String pushTitle, String pushMessage, JSONObject pushPayload) {

		String fcmResult = GC_STATUS_FAILED;
		String fcmLogString = "LOG_PROVIDER_PUSH_FCM_ANDROID_CREATE";

		String logString = fcmLogString + " deviceTokenIdList: " + deviceTokenIdList;
		logString = logString + " pushMessage: " + pushMessage + " pushPayload: "+ pushPayload.toString();

		try {

        	Map<String, String> headers = new HashMap<>();
    		headers.put("Authorization", "key=" + appPushAndroidAPIKey);
    		headers.put("Content-Type", "application/json");

            JSONObject infoJson = new JSONObject();
	           	infoJson.put("title", pushTitle);
            	infoJson.put("body", pushMessage);
	           	infoJson.put("data", pushPayload);
        	JSONObject requestPayload = new JSONObject();
        		requestPayload.put("registration_ids", deviceTokenIdList);
            	requestPayload.put("notification", infoJson);

         	RestClientResponseMapper restClientStatus = httpsProvider.HTTPSMethodPost(appPushAndroidAPIURL, headers, requestPayload.toString(), appPushAndroidAPIConnectionTimeoutSec, appPushAndroidAPIReadTimeoutSec);
            if (restClientStatus.getCode() == 200) {
            	int successCount = 0;
            	try {
            		successCount = Integer.parseInt(restClientStatus.getSuccess());
            	} catch (Exception errMess) {
            	}
            	if (successCount > 0)
            		fcmResult = GC_STATUS_SUCCESS;
            }
        	logString = logString + " requestPayload: " + requestPayload.toString() + " Response: " + restClientStatus.toString();

        } catch (Exception errMess) {
        	APILog.writeInfoLog(logString + "Exception: " + errMess.getMessage());
        	return fcmResult;
        }
		APILog.writeInfoLog(logString + " fcmResult: "+ fcmResult);
		return fcmResult;
	}

	public String sendPushNotificationWeb(List<String> deviceTokenIdList, String pushTitle, String pushMessage, JSONObject pushPayload) {

		String fcmResult = GC_STATUS_FAILED;
		String fcmLogString = "LOG_PROVIDER_PUSH_FCM_WEB_CREATE";

		String logString = fcmLogString + " deviceTokenIdList: " + deviceTokenIdList;
		logString = logString + " pushMessage: " + pushMessage + " pushPayload: "+ pushPayload.toString();

		try {

        	Map<String, String> headers = new HashMap<>();
    		headers.put("Authorization", "key=" + appPushWebAPIKey);
    		headers.put("Content-Type", "application/json");

            JSONObject infoJson = new JSONObject();
	           	infoJson.put("title", pushTitle);
            	infoJson.put("body", pushMessage);
        	JSONObject requestPayload = new JSONObject();
        		requestPayload.put("registration_ids", deviceTokenIdList);
            	requestPayload.put("data", pushPayload);
            	requestPayload.put("notification", infoJson);

         	RestClientResponseMapper restClientStatus = httpsProvider.HTTPSMethodPost(appPushWebAPIURL, headers, requestPayload.toString(), appPushWebAPIConnectionTimeoutSec, appPushWebAPIReadTimeoutSec);
         	if (restClientStatus.getCode() == 200) {
         		int successCount = 0;
             	try {
             		successCount = Integer.parseInt(restClientStatus.getSuccess());
             	} catch (Exception errMess) {
             	}
             	if (successCount > 0)
             		fcmResult = GC_STATUS_SUCCESS;
            }

        	logString = logString + " requestPayload: " + requestPayload.toString() + " Response: " + restClientStatus.toString();

        } catch (Exception errMess) {
        	APILog.writeTraceLog(logString + "Exception: " + errMess.getMessage());
        	return fcmResult;
        }
		APILog.writeInfoLog(logString + " fcmResult: "+ fcmResult);
		return fcmResult;
	}

	public String sendPushNotificationIOS(List<String> deviceTokenIdList, String pushTitle, String pushMessage, JSONObject pushPayload) {

		String fcmResult = GC_STATUS_FAILED;
		String fcmLogString = "LOG_PROVIDER_PUSH_FCM_IOS_CREATE";

		String logString = fcmLogString + " deviceTokenIdList: " + deviceTokenIdList;
		logString = logString + " pushMessage: " + pushMessage + " pushPayload: "+ pushPayload.toString();

		try {

        	Map<String, String> headers = new HashMap<>();
    		headers.put("Authorization", "key=" + appPushIOSAPIKey);
    		headers.put("Content-Type", "application/json");

            JSONObject infoJson = new JSONObject();
	           	infoJson.put("title", pushTitle);
            	infoJson.put("body", pushMessage);
	           	infoJson.put("data", pushPayload);
        	JSONObject requestPayload = new JSONObject();
        		requestPayload.put("registration_ids", deviceTokenIdList);
            	requestPayload.put("notification", infoJson);

         	RestClientResponseMapper restClientStatus = httpsProvider.HTTPSMethodPost(appPushIOSAPIURL, headers, requestPayload.toString(), appPushIOSAPIConnectionTimeoutSec, appPushIOSAPIReadTimeoutSec);
            if (restClientStatus.getCode() == 200) {
            	int successCount = 0;
            	try {
            		successCount = Integer.parseInt(restClientStatus.getSuccess());
            	} catch (Exception errMess) {
            	}
            	if (successCount > 0)
            		fcmResult = GC_STATUS_SUCCESS;
            }
        	logString = logString + " requestPayload: " + requestPayload.toString() + " Response: " + restClientStatus.toString();

        } catch (Exception errMess) {
        	APILog.writeInfoLog(logString + "Exception: " + errMess.getMessage());
        	return fcmResult;
        }
		APILog.writeInfoLog(logString + " fcmResult: "+ fcmResult);
		return fcmResult;
	}

}
