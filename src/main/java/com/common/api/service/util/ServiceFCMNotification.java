package com.common.api.service.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.DivisionService;
import com.common.api.datasink.service.ManualSchedulerService;
import com.common.api.datasink.service.ModulePreferenceService;
import com.common.api.datasink.service.UserLastActivityService;
import com.common.api.datasink.service.UserService;
import com.common.api.datasink.service.UserSessionService;
import com.common.api.model.DeviceData;
import com.common.api.model.ModulePreference;
import com.common.api.model.User;
import com.common.api.model.UserSession;
import com.common.api.model.field.UserSessionField;
import com.common.api.model.type.CategoryType;
import com.common.api.model.type.NotificationModelType;
import com.common.api.provider.PushProvider;
import com.common.api.request.KeyValue;
import com.common.api.request.PushNotificationRequest;
import com.common.api.util.APIDateTime;

@Service
@PropertySource({ "classpath:application.properties" })
public class ServiceFCMNotification extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	PushProvider pushProvider;
    @Autowired
    ManualSchedulerService manualSchedulerService;
    @Autowired
    UserLastActivityService userLastActivityService;
    @Autowired
    ModulePreferenceService modulePreferenceService;

	@Autowired
	DivisionService divisionService;
	@Autowired
	UserService userService;
	@Autowired
	UserSessionService userSessionService;
	@Autowired
	DeviceService deviceService;

	public String sendUserRideRiskAlertLiveToAdmin(DeviceData deviceData) {

		String result = GC_STATUS_ERROR;

		try {

			String userFirstName     = "";
			String pushTitle         = "";
			String pushMessage 		 = "";
			List<String> adminUserIdList = new ArrayList<>();

			String companyId  	= deviceData.getCompanyId();
			String divisionId  	= deviceData.getDivisionId();
			String userId  		= deviceData.getUserId();

			List<String> emptyList = new ArrayList<>();
			String userCategory    = CategoryType.User.ADMIN.toString();
			List<String> deviceStatusList = Arrays.asList(GC_STATUS_REGISTERED, GC_STAtUS_DEFAULT);
			List<String> typeList         = Arrays.asList(GC_REFERRED_BY_WEB);
			List<UserSession> userSessionList = userSessionService.viewListByCriteriaForPushNotification(companyId, divisionId, "", userCategory, deviceStatusList, typeList, "", "", DV_OFFSET, DV_LIMIT);

			List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, "", userId, "", "", "", emptyList, emptyList, "", "", DV_OFFSET, DV_LIMIT);
			if (userList.size() > 0) {
				User userProfile = userList.get(0);
				userFirstName = userProfile.getFirstName();
			}

			if (userSessionList.size() > 0 && userFirstName.length() > 0) {

				for (int usc = 0; usc < userSessionList.size(); usc++) {
					UserSession userSession = userSessionList.get(usc);
					String userIdTemp = userSession.getUserId();
					if (!adminUserIdList.contains(userIdTemp)) {
						adminUserIdList.add(userIdTemp);
					}
				}

				for (int auc = 0; auc < adminUserIdList.size(); auc++) {

					String adminUserIdTemp = adminUserIdList.get(auc);
					String adminFirstNameTemp = "";
					//List<String> deviceTokenListAndroid = new ArrayList<String>();
					List<String> deviceTokenListWeb     = new ArrayList<>();

					for (int usc = 0; usc < userSessionList.size(); usc++) {

						UserSession userSession = userSessionList.get(usc);
						String userIdTemp = userSession.getUserId();

						if (adminUserIdTemp.equalsIgnoreCase(userIdTemp)) {

							if (adminFirstNameTemp.length() <= 0)
								adminFirstNameTemp = userSession.getUserFirstName();

							UserSessionField usrSessField = userSession.getUserSessionField();
							String deviceType  = usrSessField.getDeviceType();
							String deviceToken = usrSessField.getDeviceToken();

							if (deviceToken.length() > 0) {
								if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {
									//deviceTokenListAndroid.add(deviceToken);
								} else if (deviceType.equalsIgnoreCase(GC_REFERRED_BY_WEB)) {
									deviceTokenListWeb.add(deviceToken);
								}
							}
						}
					}

					JSONObject pushPayload = new JSONObject();
					pushPayload.put("pushType", NotificationModelType.pushType.USER_RIDE_DIRECT_RISK_ALERT_TO_ADMIN.toString());
					pushPayload.put("adminUserIdList", adminUserIdList);
	 				pushPayload.put("userFirstName", userFirstName);

					pushTitle   = "Notification from Rudu";
					pushMessage = "Hi " + adminFirstNameTemp + ", New risk alert from " + userFirstName;

					/**if (deviceTokenListAndroid.size() > 0) {
						String resultAndroid = pushProvider.sendPushNotificationAndroid(deviceTokenListAndroid, pushTitle, pushMessage, pushPayload);
						if (resultAndroid.equalsIgnoreCase(GC_STATUS_SUCCESS)) {
							result = GC_STATUS_SUCCESS;
						}
					}**/
					if (deviceTokenListWeb.size() > 0) {
						String resultWeb     = pushProvider.sendPushNotificationWeb(deviceTokenListWeb, pushTitle, pushMessage, pushPayload);
						if (resultWeb.equalsIgnoreCase(GC_STATUS_SUCCESS)) {
							result = GC_STATUS_SUCCESS;
						}
					}
				}
			}

		} catch (Exception errMess) {
		}
		return result;
	}

	public String sendUserAppBootSettingAlertToUser(String companyId, String divisionId, String userId) {

		String result = GC_STATUS_ERROR;

		try {

			String firstName    = "";
			String userCategory = CategoryType.User.USER.toString();
			List<String> deviceStatusList = Arrays.asList(GC_STATUS_REGISTERED, GC_STAtUS_DEFAULT, GC_STATUS_AUDIT);
 			List<String> typeList   	   = Arrays.asList(GC_REFERRED_BY_ANDROID, GC_REFERRED_BY_IOS);
			List<UserSession> userSessionList = userSessionService.viewListByCriteriaForPushNotification(companyId, divisionId, userId, userCategory, deviceStatusList, typeList, "", "", DV_OFFSET, DV_LIMIT);

			if (userSessionList.size() > 0) {

				List<String> deviceTokenListWeb     = new ArrayList<>();
				List<String> deviceTokenListIOS		= new ArrayList<>();
				List<String> deviceTokenListAndroid = new ArrayList<>();

				for (int usc = 0; usc < userSessionList.size(); usc++) {

					UserSession userSession = userSessionList.get(usc);
					String type  	   = userSession.getType();
					String deviceToken = userSession.getDeviceToken();
					firstName = userSession.getUserFirstName();

					if (deviceToken.length() > 0) {
						if (type.equalsIgnoreCase(GC_REFERRED_BY_WEB)) {
							deviceTokenListWeb.add(deviceToken);
						} else if (type.equalsIgnoreCase(GC_REFERRED_BY_IOS)) {
							deviceTokenListIOS.add(deviceToken);
						} else if (type.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {
							deviceTokenListAndroid.add(deviceToken);
						}
					}
				}

				JSONObject pushPayload = new JSONObject();
				pushPayload.put("pushType", NotificationModelType.pushType.USER_APP_BOOT_SETTING.toString());
				pushPayload.put("userId", userId);

				String pushTitle   = "Notification from Rudu";
				String pushMessage = "Hi " + firstName + ", User app boot setting updated";

				if (deviceTokenListAndroid.size() > 0) {
					String resultAndroid = pushProvider.sendPushNotificationAndroid(deviceTokenListAndroid, pushTitle, pushMessage, pushPayload);
					if (resultAndroid.equalsIgnoreCase(GC_STATUS_SUCCESS)) {
						result = GC_STATUS_SUCCESS;
					}
				}
				if (deviceTokenListWeb.size() > 0) {
					String resultWeb     = pushProvider.sendPushNotificationWeb(deviceTokenListWeb, pushTitle, pushMessage, pushPayload);
					if (resultWeb.equalsIgnoreCase(GC_STATUS_SUCCESS)) {
						result = GC_STATUS_SUCCESS;
					}
				}
				if (deviceTokenListIOS.size() > 0) {
					String resultIOS     = pushProvider.sendPushNotificationIOS(deviceTokenListIOS, pushTitle, pushMessage, pushPayload);
					if (resultIOS.equalsIgnoreCase(GC_STATUS_SUCCESS)) {
						result = GC_STATUS_SUCCESS;
					}
				}
			}
		} catch (Exception errMess) {
		}
		return result;
	}

	public List<User> sendAppOrMapSettingAlertToUser(String companyId, String groupId, String divisionId, String moduleId, String userId, String notificationStatus, String type, String userSessionType, String sortBy, String sortOrder, int offset, int limit) {

		List<User> userList = new ArrayList<>();
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		List<ModulePreference> modulePreferenceList = modulePreferenceService.viewListByCriteria(companyId, notificationStatus, type, sortBy, sortOrder, offset, limit);

		if (modulePreferenceList.size() > 0) {

			ModulePreference modulePreferenceDetail = modulePreferenceList.get(0);
			String name = modulePreferenceDetail.getName();

			userList = manualSchedulerService.viewUserListByUserSession(companyId, groupId, divisionId, moduleId, userId, type, userSessionType, sortBy, sortOrder, offset, limit);

			for (int uc = 0; uc < userList.size(); uc++) {

				User userDetail = userList.get(uc);
				String userIdTemp = userDetail.getId();
				String firstName  = userDetail.getFirstName();

				List<UserSession> userSessionList = userSessionService.viewListByCriteriaForSchedulerPushNotification(companyId, divisionId, userIdTemp, userSessionType, sortBy, sortOrder, DV_OFFSET, DV_LIMIT);

				if (userSessionList.size() > 0) {

					List<String> deviceTokenListIOS		= new ArrayList<>();
					List<String> deviceTokenListAndroid = new ArrayList<>();

					for (int usc = 0; usc < userSessionList.size(); usc++) {

						UserSession userSession    = userSessionList.get(usc);
						String userSessionTypeTemp = userSession.getType();
						String deviceToken = userSession.getDeviceToken();

						if (deviceToken.length() > 0) {
							if (userSessionTypeTemp.equalsIgnoreCase(GC_REFERRED_BY_IOS)) {
								deviceTokenListIOS.add(deviceToken);
							} else if (userSessionTypeTemp.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {
								deviceTokenListAndroid.add(deviceToken);
							}
						}
					}

					JSONObject pushPayload = new JSONObject();
					pushPayload.put("pushType", type);
					pushPayload.put("userId", userId);

					String pushResult  = GC_STATUS_FAILED;
					String pushTitle   = "Notification from Rudu";
					String pushMessage = "Hi " + firstName + ", " + name + " updated";

					if (deviceTokenListAndroid.size() > 0)
						pushResult = pushProvider.sendPushNotificationAndroid(deviceTokenListAndroid, pushTitle, pushMessage, pushPayload);

					if (deviceTokenListIOS.size() > 0)
						pushResult = pushProvider.sendPushNotificationIOS(deviceTokenListIOS, pushTitle, pushMessage, pushPayload);

					if (pushResult.equalsIgnoreCase(GC_STATUS_SUCCESS) || pushResult.equalsIgnoreCase(GC_STATUS_FAILED)) {

						if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_APP_VERSION.toString())) {
							userLastActivityService.updateUserLastActivityByAppVersion(userIdTemp, dbOperationStartTime, null, dbOperationStartTime);
		 				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_APP_VERSION.toString())) {
		 					userLastActivityService.updateUserLastActivityByAppVersion(userIdTemp, dbOperationStartTime, dbOperationStartTime, null);
		 				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_LIBRARY_ZIP.toString())) {
		 					userLastActivityService.updateUserLastActivityByMapVersion(userIdTemp, dbOperationStartTime, null, dbOperationStartTime);
		 				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_LIBRARY_ZIP.toString())) {
		 					userLastActivityService.updateUserLastActivityByMapVersion(userIdTemp, dbOperationStartTime, dbOperationStartTime, null);
		 				}
 					}
				}
			}

			List<User> userListPending = manualSchedulerService.viewUserListByUserSession(companyId, "", "", "", "", type, userSessionType, sortBy, sortOrder, DV_OFFSET, DV_LIMIT);

			if (userListPending.size() <= 0) {
				modulePreferenceDetail.setNotificationStatus(GC_STATUS_SUCCESS);
				modulePreferenceDetail.setNotificationStatusSuccessAt(dbOperationStartTime);
				modulePreferenceService.updateModulePreference(modulePreferenceDetail);
			}
		}
		return userList;
	}

	public List<User> sendNoActivityAlertToUser(String companyId, String groupId, String divisionId, String moduleId, String userId, String notificationStatus, String type, String userSessionType, String sortBy, String sortOrder, int offset, int limit) {

		//int noOfDays = 7;
		List<User> userList = new ArrayList<>();
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
		List<ModulePreference> modulePreferenceList = modulePreferenceService.viewListByCriteria(companyId, notificationStatus, type, sortBy, sortOrder, offset, limit);

		if (modulePreferenceList.size() > 0) {

			ModulePreference modulePreferenceDetail = modulePreferenceList.get(0);
			//String name = modulePreferenceDetail.getName();

			userList = manualSchedulerService.viewUserListByDevice(companyId, groupId, divisionId, moduleId, userId, type, userSessionType, sortBy, sortOrder, offset, limit);

			for (int uc = 0; uc < userList.size(); uc++) {

				User userDetail = userList.get(uc);
				String userIdTemp = userDetail.getId();
				String firstName  = userDetail.getFirstName();

				List<UserSession> userSessionList = userSessionService.viewListByCriteriaForSchedulerPushNotification(companyId, divisionId, userIdTemp, userSessionType, sortBy, sortOrder, DV_OFFSET, DV_LIMIT);

				if (userSessionList.size() > 0) {

					List<String> deviceTokenListIOS		= new ArrayList<>();
					List<String> deviceTokenListAndroid = new ArrayList<>();

					for (int usc = 0; usc < userSessionList.size(); usc++) {

						UserSession userSession    = userSessionList.get(usc);
						String userSessionTypeTemp = userSession.getType();
						String deviceToken = userSession.getDeviceToken();

						if (deviceToken.length() > 0) {
							if (userSessionTypeTemp.equalsIgnoreCase(GC_REFERRED_BY_IOS)) {
								deviceTokenListIOS.add(deviceToken);
							} else if (userSessionTypeTemp.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {
								deviceTokenListAndroid.add(deviceToken);
							}
						}
					}

					JSONObject pushPayload = new JSONObject();
					pushPayload.put("pushType", type);
					pushPayload.put("userId", userId);

					String pushResult  = GC_STATUS_FAILED;
					String pushTitle   = "Notification from Rudu";
					//String pushMessage = "Hi " + firstName + ", " + name + " for past " + noOfDays + " days";
					String pushMessage = "Dear " + firstName + ", Kindly turn ON the GPS and start taking rides as no/very less rides are recorded in RUDU";

					if (deviceTokenListAndroid.size() > 0)
						pushResult = pushProvider.sendPushNotificationAndroid(deviceTokenListAndroid, pushTitle, pushMessage, pushPayload);

					if (deviceTokenListIOS.size() > 0)
						pushResult = pushProvider.sendPushNotificationIOS(deviceTokenListIOS, pushTitle, pushMessage, pushPayload);

					if (pushResult.equalsIgnoreCase(GC_STATUS_SUCCESS) || pushResult.equalsIgnoreCase(GC_STATUS_FAILED)) {

						if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_NO_ACTIVITY.toString())) {
							userLastActivityService.updateUserLastActivityByNoActivity(userIdTemp, dbOperationStartTime, null, null, dbOperationStartTime);
		 				} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_NO_ACTIVITY.toString())) {
		 					userLastActivityService.updateUserLastActivityByNoActivity(userIdTemp, dbOperationStartTime, null, dbOperationStartTime, null);
		 				}
 					}
				}
			}

			List<User> userListPending = manualSchedulerService.viewUserListByDevice(companyId, "", "", "", "", type, userSessionType, sortBy, sortOrder, DV_OFFSET, DV_LIMIT);

			if (userListPending.size() <= 0) {
				modulePreferenceDetail.setNotificationStatus(GC_STATUS_SUCCESS);
				modulePreferenceDetail.setNotificationStatusSuccessAt(dbOperationStartTime);
				modulePreferenceService.updateModulePreference(modulePreferenceDetail);
			}
		}
		return userList;
	}

    public PushNotificationRequest sendVehicleAccidentAlertToCoWorker(PushNotificationRequest pushNotifReq) {

    	String resultCode = GC_STATUS_FAILED;
    	PushNotificationRequest result = new PushNotificationRequest();
    	String companyId  = pushNotifReq.getCompanyId();
    	String divisionId = pushNotifReq.getDivisionId();

    	String eventTime  = APIDateTime.getZoneIdBasedDateTime(DV_TIME_ZONE_ID_ASIA_KOLKATA);
    	String pushEvent  = pushNotifReq.getEvent();
    	String pushType   = pushNotifReq.getType();
    	List<KeyValue> keyValues = pushNotifReq.getKeyValues();
    	pushEvent = (pushEvent.length() <= 0) ? NotificationModelType.ManualPushEvent.NEW_ALERT.toString() : pushEvent;

    	List<String> emptyList = new ArrayList<>();
    	List<String> typeList  = Arrays.asList(GC_REFERRED_BY_IOS, GC_REFERRED_BY_ANDROID);

    	String userIdTemp = "";
    	String userIdReq  = pushNotifReq.getUserId();
		List<UserSession> userList = userSessionService.viewUserListByCriteriaForPushNotification(companyId, divisionId, userIdTemp, "", emptyList, typeList, "", "", DV_OFFSET_DB, DV_LIMIT_DB);

		if (userList.size() > 0) {

			String username = "", firstName   = "", location = "", address = "", latitude = "", longitude = "";

			for (KeyValue keyValueTemp : keyValues) {

				String key   = keyValueTemp.getKey();
				String value = keyValueTemp.getValue();

				username  = (key.equals("username")) ? value  : username;
				firstName = (key.equals("firstName")) ? value : firstName;
				location  = (key.equals("location"))  ? value : location;
				address   = (key.equals("address"))   ? value : address;
				latitude  = (key.equals("latitude"))  ? value : latitude;
				longitude = (key.equals("longitude")) ? value : longitude;

			}

			for (int uc = 0; uc < userList.size(); uc++) {

				UserSession userDetail = userList.get(uc);
				String userId   = userDetail.getUserId();

				List<String> deviceTokenListIOS		= new ArrayList<>();
				List<String> deviceTokenListAndroid = new ArrayList<>();

				if (!userIdReq.equalsIgnoreCase(userId)) {

					List<UserSession> userSessionList = userSessionService.viewListByCriteriaForPushNotification(companyId, divisionId, userId, "", emptyList, typeList, "", "", DV_OFFSET_DB, DV_LIMIT_DB);

					for (int usc = 0; usc < userSessionList.size(); usc++) {

						UserSession userSession    = userSessionList.get(usc);
						String userSessionTypeTemp = userSession.getType();
						String deviceToken = userSession.getDeviceToken();

						if (deviceToken.length() > 0) {
							if (userSessionTypeTemp.equalsIgnoreCase(GC_REFERRED_BY_IOS)) {
								deviceTokenListIOS.add(deviceToken);
							} else if (userSessionTypeTemp.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {
								deviceTokenListAndroid.add(deviceToken);
							}
						}
					}
				}

				JSONArray keyValuesJson = new JSONArray();
				keyValuesJson.put(new JSONObject().put("k", "firstName").put("v", firstName));
				keyValuesJson.put(new JSONObject().put("k", "location").put("v", location));
				keyValuesJson.put(new JSONObject().put("k", "address").put("v", address));

				JSONObject pushPayload = new JSONObject();
				pushPayload.put("pushType",  pushType);
				pushPayload.put("pushEvent", pushEvent);
				pushPayload.put("username",  username);
				pushPayload.put("latitude",  latitude);
				pushPayload.put("longitude", longitude);
				pushPayload.put("keyValues", keyValuesJson);

				String pushMessage = "";
				String pushTitle   = "Notification from Rudu at " + eventTime;

				if (pushEvent.equals(NotificationModelType.ManualPushEvent.NEW_ALERT.toString())) {
					pushMessage = "An accident has just taken place to your co-worker " + firstName + "(" + username+ ") at the " + location + ", " + address + ". Please attend immediately for help.";
				} else if (pushEvent.equals(NotificationModelType.ManualPushEvent.CANCEL_LAST_ALERT.toString())) {
					pushMessage = "Please ignore the previous emergency alert as it was triggered by mistake";
				}

				if  (pushMessage.length() > 0) {

					if (deviceTokenListAndroid.size() > 0)
						resultCode = pushProvider.sendPushNotificationAndroid(deviceTokenListAndroid, pushTitle, pushMessage, pushPayload);

					if (deviceTokenListIOS.size() > 0)
						resultCode = pushProvider.sendPushNotificationIOS(deviceTokenListIOS, pushTitle, pushMessage, pushPayload);

					if (resultCode.equalsIgnoreCase(GC_STATUS_SUCCESS))
						result = pushNotifReq;
				}
			}

		}
    	return result;

    }

    public PushNotificationRequest sendVehicleTrafficHaltToCoWorker(PushNotificationRequest pushNotifReq) {

    	String resultCode = GC_STATUS_FAILED;
    	PushNotificationRequest result = new PushNotificationRequest();
    	String companyId  = pushNotifReq.getCompanyId();
    	String divisionId = pushNotifReq.getDivisionId();

    	String eventTime  = APIDateTime.getZoneIdBasedDateTime(DV_TIME_ZONE_ID_ASIA_KOLKATA);
    	String pushEvent  = pushNotifReq.getEvent();
    	String pushType   = pushNotifReq.getType();
    	List<KeyValue> keyValues = pushNotifReq.getKeyValues();
    	pushEvent = (pushEvent.length() <= 0) ? NotificationModelType.ManualPushEvent.NEW_ALERT.toString() : pushEvent;

    	List<String> emptyList = new ArrayList<>();
    	List<String> typeList  = Arrays.asList(GC_REFERRED_BY_IOS, GC_REFERRED_BY_ANDROID);

    	String userIdTemp = "";
    	String userIdReq  = pushNotifReq.getUserId();
		List<UserSession> userList = userSessionService.viewUserListByCriteriaForPushNotification(companyId, divisionId, userIdTemp, "", emptyList, typeList, "", "", DV_OFFSET_DB, DV_LIMIT_DB);

		if (userList.size() > 0) {

			String location = "", address = "", latitude = "", longitude = "";

			for (KeyValue keyValueTemp : keyValues) {

				String key   = keyValueTemp.getKey();
				String value = keyValueTemp.getValue();

				location  = (key.equals("location"))  ? value : location;
				address   = (key.equals("address"))   ? value : address;
				latitude  = (key.equals("latitude"))  ? value : latitude;
				longitude = (key.equals("longitude")) ? value : longitude;

			}

			for (int uc = 0; uc < userList.size(); uc++) {

				UserSession userDetail = userList.get(uc);
				String userId    = userDetail.getUserId();
				String username  = userDetail.getUsername();
				String firstName = userDetail.getUserFirstName();

				List<String> deviceTokenListIOS		= new ArrayList<>();
				List<String> deviceTokenListAndroid = new ArrayList<>();

				if (!userIdReq.equalsIgnoreCase(userId)) {

					List<UserSession> userSessionList = userSessionService.viewListByCriteriaForPushNotification(companyId, divisionId, userId, "", emptyList, typeList, "", "", DV_OFFSET_DB, DV_LIMIT_DB);

					for (int usc = 0; usc < userSessionList.size(); usc++) {

						UserSession userSession    = userSessionList.get(usc);
						String userSessionTypeTemp = userSession.getType();
						String deviceToken = userSession.getDeviceToken();

						if (deviceToken.length() > 0) {
							if (userSessionTypeTemp.equalsIgnoreCase(GC_REFERRED_BY_IOS)) {
								deviceTokenListIOS.add(deviceToken);
							} else if (userSessionTypeTemp.equalsIgnoreCase(GC_REFERRED_BY_ANDROID)) {
								deviceTokenListAndroid.add(deviceToken);
							}
						}
					}
				}

				JSONArray keyValuesJson = new JSONArray();
				keyValuesJson.put(new JSONObject().put("k", "firstName").put("v", firstName));
				keyValuesJson.put(new JSONObject().put("k", "location").put("v", location));
				keyValuesJson.put(new JSONObject().put("k", "address").put("v", address));

				JSONObject pushPayload = new JSONObject();
				pushPayload.put("pushType",  pushType);
				pushPayload.put("pushEvent", pushEvent);
				pushPayload.put("username",  username);
				pushPayload.put("latitude",  latitude);
				pushPayload.put("longitude", longitude);
				pushPayload.put("keyValues", keyValuesJson);

				String pushMessage = "";
				String pushTitle   = "Notification from Rudu at " + eventTime;

				if (pushEvent.equals(NotificationModelType.ManualPushEvent.NEW_ALERT.toString())) {
					pushMessage = "Dear " + firstName + "(" + username+ "), there seems to be heavy traffic in " + location + ", " + address + " as alerted by you co-worker, so kindly take an alternative route";
				} else if (pushEvent.equals(NotificationModelType.ManualPushEvent.CANCEL_LAST_ALERT.toString())) {
					pushMessage = "Please ignore the previous traffic halt alert as it was triggered by mistake";
				}

				if  (pushMessage.length() > 0) {

					if (deviceTokenListAndroid.size() > 0)
						resultCode = pushProvider.sendPushNotificationAndroid(deviceTokenListAndroid, pushTitle, pushMessage, pushPayload);

					if (deviceTokenListIOS.size() > 0)
						resultCode = pushProvider.sendPushNotificationIOS(deviceTokenListIOS, pushTitle, pushMessage, pushPayload);

					if (resultCode.equalsIgnoreCase(GC_STATUS_SUCCESS))
						result = pushNotifReq;

				}
			}

		}
    	return result;

    }

}