package com.common.api.constant;

public class APIFixedConstant extends APICustomConstant {

	/** Database Engine */
	public static final String GC_DB_ENGINE_MS_SQL = "MS_SQL";

	/** Database Engine */
	public static final String GC_REFERRED_BY_ANDROID = "ANDROID";
	public static final String GC_REFERRED_BY_WEB     = "WEB";
	public static final String GC_REFERRED_BY_IOS     = "IOS";

	/** Log Package */
	public static final String GC_LOG_PACKAGE_NAME = "com.common.api";
	/** Resource Package */
	public static final String GC_RSOURCE_PACKAGE_NAME = "com.common.api.resource";

	/** Time zone */
	public static final String GC_GLOBAL_TIME_ZONE_ID = "UTC";
	/** Status */
	public static final String GC_STATUS_SUCCESS  = "SUCCESS";
	public static final String GC_STATUS_FAILED   = "FAILED";
	public static final String GC_STATUS_ERROR    = "ERROR";
	public static final String GC_STATUS_SESSION_FAILED = "SESSION_FAILED";
	public static final String GC_STATUS_ACCESS_FAILED  = "ACCESS_FAILED";
	public static final String GC_STRING_SEPERATOR_COMMA = ",";

	/** Date and Time */
	public static final String GC_DATE_FORMAT = "yyyy-MM-dd";
	public static final String GC_TIME_FORMAT = "HH:mm:ss";
	public static final String GC_DATE_TIME_FORMAT   = GC_DATE_FORMAT + " " + GC_TIME_FORMAT;

	/** Date Time */
	public static final String DV_TIME_ZONE_ID = "UTC";
	public static final String DV_TIME_ZONE_ID_ASIA_KOLKATA = "Asia/Kolkata";

	/** Action */
	public static final String GC_ACTION_PLUS    = "PLUS";
	public static final String GC_ACTION_MINUS   = "MINUS";

	/** Method */
	public static final String GC_METHOD_GET    = "GET";
	public static final String GC_METHOD_POST   = "POST";
	public static final String GC_METHOD_PUT    = "PUT";
	public static final String GC_METHOD_DELETE = "DELETE";

	/** Action */
	public static final String GC_ACTION_CREATE = "CREATE";
	public static final String GC_ACTION_VIEW   = "VIEW";
	public static final String GC_ACTION_UPDATE = "UPDATE";
	public static final String GC_ACTION_DELETE = "DELETE";
	public static final String GC_ACTION_YES 	= "YES";

	/** Sorting */
	public static final String GC_SORT_ASC  = "ASC";
	public static final String GC_SORT_DESC	= "DESC";

	public static final String GC_SORT_BY_CREATED_BY = "createdAt";
	public static final String GC_STATUS_REGISTERED	 = "REGISTERED";
	public static final String GC_STATUS_PENDING	 = "PENDING";
	public static final String GC_STAtUS_DEFAULT	 = "DEFAULT";
	public static final String GC_STATUS_AUDIT   	 = "AUDIT";

	public static final String GC_TIME_ZONE_UTC = "UTC";
	public static final String GC_TIME_ZONE_ISD = "IST";

	/** Gender */
	public static final String GC_GENDER_MALE     = "MALE";
	public static final String GC_GENDER_FEMALE   = "FEMALE";

	public static final String GC_HTTP_SUCCESS_MESSAGE = "Request Process Successfully";
	public static final String GC_HTTP_FAILED_MESSAGE  = "Request Process Failed";

	/** Provider */
	public static final String DS_SEPARATOR_SLASH = "/";

	/** HTTP Status Code Constants */
	public static final String SM_STATUS_OK		       		= "Request Success";
	public static final String SM_STATUS_CREATED	    	= "Request Success";
	public static final String SM_STATUS_ACCEPTED       	= "Request Success";
	public static final String SM_STATUS_FORBIDDEN    		= "Access Bocked";
	public static final String SM_STATUS_CONFLICT     		= "Conflict Error(s)";
	public static final String SM_STATUS_PRE_CONDITION  	= "Validation Error(s)";
	public static final String SM_STATUS_FAILED_DEPENDENCY 	= "Failed Dependency";
	public static final String SM_STATUS_SESSION      		= "Session Timeout";
	public static final String SM_STATUS_INTERNAL     		= "Internal Server Error(s)";
	public static final String SM_STATUS_EXTERNAL_ERR 		= "External API Error(s)";

	public static final int SC_STATUS_OK		   		= 200;
	public static final int SC_STATUS_CREATED      		= 201;
	public static final int SC_STATUS_ACCEPTED     		= 202;
	public static final int SC_STATUS_FORBIDDEN			= 403;
	public static final int SC_STATUS_CONFLICT     		= 409;
	public static final int SC_STATUS_PRE_CONDITION		= 412;
	public static final int SC_STATUS_FAILED_DEPENDENCY = 424;
	public static final int SC_STATUS_SESSION      		= 440;
	public static final int SC_STATUS_INTERNAL     		= 500;
	public static final int SC_STATUS_EXTERNAL_ERR 		= 502;

	/** Regular Expression Constants */
	public static final String RE_USERNAME		= "^[0-9]+$";
	public static final String RE_USERNAME_CUSTOM = "^[a-zA-Z0-9]+$";
	public static final String RE_PASSWORD 	   	= "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";
	public static final String RE_MOBILE_PIN   	= "^[a-zA-Z0-9@]+$";
	public static final String RE_EMAIL   	   	= "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@+[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$";

	public static final String RE_DEVICE_FIELD	= "[a-zA-z0-9/,-/s ]+$";

	public static final String RE_ID           	= "^[a-zA-Z0-9]+$";
	public static final String RE_MD5          	= "^[a-z0-9]+$";
	public static final String RE_YES_NO 	   	= "^[A-Z0-9]+$";
	public static final String RE_NUMBER	   	= "^[0-9]+$";
	public static final String RE_NAME         	= "[a-zA-Z 0-9]+$";
	public static final String RE_TYPE		   	= "^[A-Z0-9_]+$";
	public static final String RE_STATUS	   	= "^[A-Z0-9_]+$";
	public static final String RE_MULTI_TYPE   	= "^[A-Z0-9_/,]+$";
	public static final String RE_CODE         	= "^[a-zA-Z0-9+]+$";
	public static final String RE_SYSTEM_CODE  	= "^[a-zA-Z0-9+]+$";
 	public static final String RE_DATE         	= "^[0-9]{4}-[0-9]{2}-[0-9]{2}";
	public static final String RE_DESCRIPTION  	= "[a-zA-z0-9/,-/s ]+$";
  	public static final String RE_DATE_TIME    	= "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
	public static final String RE_TIME         	= "(?:[01][0-9]|2[0123]):(?:[012345][0-9]):(?:[012345][0-9])";
	public static final String RE_UNIQUE_ID  	= "[a-zA-Z:0-9]+$";
	public static final String RE_UNIQUE_CODE  	= "[a-zA-Z:0-9]+$";
	public static final String RE_FLOAT 	= "^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$";

	/** Validation Type Constants */
	public static final String VT_MD5	    = "md5";
	public static final String VT_NUMBER 	= "number";
	public static final String VT_NAME  	= "name";
	public static final String VT_DATE  	= "date";
	public static final String VT_TIME  	= "time";
	public static final String VT_TYPE 		= "type";
	public static final String VT_DATE_TIME   = "dateTime";
	public static final String VT_PASSWORD	  = "password";
	public static final String VT_AUTO_GEN_ID = "autoGenerateId";
	public static final String VT_DESCRIPTION = "description";
	public static final String VT_UNIQUE_CODE = "uniqueCode";
	public static final String VT_FLOAT = "float";

	/** Run Time Validation Error(s) */
	public static final String RTVE_COMPANY_NOT_EXISTS 		  = "Company does not exist";
	public static final String RTVE_COUNTRY_NOT_EXISTS 		  = "Country does not exist";
	public static final String RTVE_USERNAME_NOT_AVAILABLE    = "Username does not available";
	public static final String RTVE_USER_VERIFI_NOT_MATCHED   = "User verification does not matched";
	public static final String RTVE_USER_FEEDBACK_NOT_MATCHED = "User feedback does not matched";
	public static final String RTVE_USER_COMPANY_NOT_MATCHED  = "User company does not matched";
	public static final String RTVE_USER_DIVISION_NOT_MATCHED = "User division does not matched";
	public static final String RTVE_USER_MODULE_NOT_MATCHED   = "User module does not matched";
	public static final String RTVE_USER_PREFER_NOT_MATCHED   = "User preference does not matched";
	public static final String RTVE_PROPERTY_NOT_MATCHED   	  = "Property does not matched";
	public static final String RTVE_SECTION_NOT_MATCHED   	  = "Section does not matched";
	public static final String RTVE_PROTION_NOT_MATCHED   	  = "Portion does not matched";
	public static final String RTVE_DEVICE_NOT_MATCHED   	  = "Device does not matched";
	public static final String RTVE_DEVICE_DATA_NOT_MATCHED   = "Device data does not matched";
	public static final String RTVE_DEVICE_SUMMARY_NOT_MATCHED= "Device summary does not matched";
	public static final String RTVE_MEMBER_NOT_MATCHED   	  = "Member does not matched";
	public static final String RTVE_MEMBER_DEV_NOT_MATCHED    = "Member device does not matched";
	public static final String RTVE_SECTION_PREFER__NOT_MATCHED  = "Section preference does not matched";
	public static final String RTVE_DIVISION_PREFER_NOT_MATCHED  = "Division Preference does not matched";
	public static final String RTVE_SUBSCRIPTION_NOT_MATCHED     = "Subscription does not matched";
	public static final String RTVE_ORDER_NOT_MATCHED   	     = "Order does not matched"; 
	

}
