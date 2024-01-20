package com.common.api.model.type;

public class NotificationModelType {

	public NotificationModelType() {
	}

	public enum pushType {
		USER_RIDE_DIRECT_RISK_ALERT_TO_ADMIN, USER_APP_BOOT_SETTING,
		ANDROID_LIBRARY_ZIP, IOS_LIBRARY_ZIP,
		ANDROID_APP_VERSION, IOS_APP_VERSION,
		ANDROID_NO_ACTIVITY, IOS_NO_ACTIVITY;
	}

	public enum ManualPushType {
		VEHICLE_ACCIDENT_ALERT_TO_CO_WORKER, VEHICLE_TRAFFIC_HALT_TO_CO_WORKER
	}

	public enum ManualPushEvent {
		NEW_ALERT, CANCEL_LAST_ALERT
	}

}
