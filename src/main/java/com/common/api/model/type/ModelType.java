package com.common.api.model.type;

public class ModelType {
 
	public ModelType() { 
	} 
	
	public enum DeviceSummary {
		SPEEDO_METER_DEVICE_SUMMARY;   
	}
	public enum DeviceMode {
		WORK, PERSONAL, AUTO, MANUAL; 
	}
	public enum ModulePreferenceType {   
		ANDROID_LIBRARY_ZIP, ANDROID_APP_VERSION, 
		IOS_LIBRARY_ZIP, IOS_APP_VERSION, 
		MODULE_APP_BOOT_SETTING;  
	} 
	public enum UserVerificationType {   
		USER_ACTIVATION, USER_FORGOT_PASSWORD, USER_FORGOT_MOBILE_PIN, FORCE_CHANGE_PASSWORD;  
	} 
	public enum UserPreferenceType {   
		APP_SETTINGS, MAP_SETTINGS, USER_APP_BOOT_SETTING,; 
	}
	public enum UserSecretType {   
		USER_SECRET;  
	}
	public enum UsernameType {   
		MOBILE_NUMBER, CUSTOM;  
	}
	public enum UserType {   
		SUPER_ADMIN, ADMIN, USER;  
	}
	public enum UserLastActivityType {   
		USER_LAST_ACTIVITY;  
	}
	public enum DeviceType { 
		SPEEDO_METER_DEVICE; 
	}
	public enum DeviceDataType {
		SPEEDO_METER_DEVICE_DATA; 
	}
	public enum UserNextActivity {
		FORCE_PASSWORD_CHANGE;
	}
	public enum SubscriptionType { 
		MOBILE_DEVICE_LICENSE_SUBSCRIPTION; 
	}
	 
}
