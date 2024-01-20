package com.common.api.model.type;

public class CategoryType {
 
	public CategoryType() { 
	}   
	public enum DeviceDataCategory {   
		LIVE_DATA, ERROR_DATA,
		START_DATA, END_DATA, DISTANCE_DATA, ALERT_DATA, STRESS_STRAIN_DATA, MANUAL_DATA;
	} 
	public enum UserSession {    
		USER_PASSWORD, USER_MOBILE_PIN;  
	}
	public enum MemberDevice {   
		STANDARD_SHARING, ONE_TIME_SHARING;   
	}  
	public enum User {    
		USER, ADMIN, SUPER_ADMIN;   
	} 
	public enum DeviceDataSubCategory {    
		OVER_SPEED, MOBILE_USE, MOBILE_SCREEN; 
	} 
	public enum DeviceDataSubCategoryLevel {   
		IN_ACCEPTED, OUT_ACCEPTED, SCREEN_ON;
	} 
	
}
