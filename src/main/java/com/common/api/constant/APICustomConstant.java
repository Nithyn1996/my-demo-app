package com.common.api.constant;

public class APICustomConstant extends DBFieldNameConstant {

	/** Success Code Constant */
	public static final String SC_001_REQUEST_SUCCESSFUL   	= "S001";

	/** Error Code Constant */
	public static final String EC_000_RUNTIME_DEPENDENCY	= "E000";
	public static final String EC_001_PRECONDITION_ERROR  	= "E001";
	public static final String EC_002_SESSION_EXPIRED      	= "E002";
	public static final String EC_003_PROFILE_NOT_EXIST    	= "E003";
	public static final String EC_004_USR_PWD_NOT_MATCH    	= "E004";
	public static final String EC_005_USR_NAME_ALRDY_EXIST	= "E005";
	public static final String EC_005_EMAIL_ALRDY_EXIST		= "E005";
	public static final String EC_006_FAILED_DEPENDENCY    	= "E006";
	public static final String EC_007_ACCESS_BLOCKED	  	= "E007";
	public static final String EC_008_PROCESS_ALREADY_CMPTD	= "E008";
	public static final String EC_009_VERIFY_PROCESS_NOT_CMPTD	 = "E009";
	public static final String EC_010_HW_DEVICE_MISMATCHED	     = "E010";
	public static final String EC_011_HW_DEVICE_MISMATCHED	     = "E011";
	public static final String EC_012_PROCEDURE_NOT_DEFINED	     = "E012";
	public static final String EC_013_SMS_GATEWAY_PROCESS_FAILED = "E013";
	public static final String EC_014_FCM_PROCESS_FAILED		 = "E014";
	public static final String EC_015_RECORD_ALREADY_AVAILABLE	 = "E015";
	public static final String EC_016_USERNAME_TYPE_MOBILE_NO    = "E016";
	public static final String EC_016_ALREADY_EXIST_CONFLICT	 = "E016";

	/** Error Message */
	public static final String EM_INVALID_DB_ENGINE_TYPE 	= "Invalid DB Engine Type";
	public static final String EM_INVALID_SESSION_ID  		= "Invalid Session Id";
	public static final String EM_INVALID_REFERRED_BY 		= "Invalid Referred By";
	public static final String EM_PROFILE_DEPENDENCY_ERROR	= "Profile has some dependency error(s)"; 
 
	public static final String EM_INVALID_ID 	     	= "Invalid Id";
	public static final String EM_INVALID_LANGUAGE_ID   = "Invalid Language Id";
	public static final String EM_INVALID_TIME_ZONEY_ID = "Invalid Time Zone Id";
	public static final String EM_INVALID_CONTINENT_ID 	= "Invalid Continent Id";
	public static final String EM_INVALID_COUNTRY_ID 	= "Invalid Country Id";
	public static final String EM_INVALID_COMP_ID    	= "Invalid Company Id";
	public static final String EM_INVALID_GROUP_ID    	= "Invalid Group Id";
	public static final String EM_INVALID_DIVI_ID    	= "Invalid Division Id";
	public static final String EM_INVALID_MODU_ID    	= "Invalid Module Id";
	public static final String EM_INVALID_USER_ID    	= "Invalid User Id";
	public static final String EM_INVALID_SUBS_ID    	= "Invalid Subscription Id";
	public static final String EM_INVALID_SECRET_KEY    	= "Invalid User Secret Key";
	public static final String EM_INVALID_USER_DEVICE_ID  	= "Invalid User Device Id";
	public static final String EM_INVALID_ROLE_COMPANY_ID	= "Invalid Role Company Id";
	public static final String EM_INVALID_ROLE_DIVISION_ID	= "Invalid Role Division Id";
	public static final String EM_INVALID_ROLE_MODULE_ID	= "Invalid Role Module Id";
	public static final String EM_INVALID_ROLE_GROUP_ID  	= "Invalid User Group Id";
	public static final String EM_INVALID_PROPERTY_ID    	= "Invalid Property Id";
	public static final String EM_INVALID_SECTION_ID    	= "Invalid Section Id";
	public static final String EM_INVALID_PORTION_ID    	= "Invalid Portion Id";
	public static final String EM_INVALID_DEVICE_ID    		= "Invalid Device Id";
	public static final String EM_INVALID_ACTION      	    = "Invalid Action";
	public static final String EM_INVALID_MEMBER_ID    		= "Invalid Member Id";
	public static final String EM_INVALID_MEMBER_USER_ID    = "Invalid Member User Id";
	public static final String EM_INVALID_START_DATE_TIME 	= "Invalid Start Date And Time";
	public static final String EM_INVALID_END_DATE_TIME   	= "Invalid End Date And Time";
	public static final String EM_INVALID_CREATED_DATE_TIME = "Invalid Created Date And Time";

	public static final String EM_INVALID_USERNAME 	 = "Invalid Username";
	public static final String EM_INVALID_PASSWORD 	 = "Invalid Password";
	public static final String EM_INVALID_MOBILE_PIN = "Invalid Mobile Pin";
	public static final String EM_INVALID_MEMBER_USERNAME = "Invalid Member User ";

	public static final String EM_INVALID_D_ORDER_ID 	 = "Invalid Device Order ID";
	public static final String EM_INVALID_D_UNIQUE_ID 	 = "Invalid Device Unique ID";
	public static final String EM_INVALID_D_VERSION_NO 	 = "Invalid Device Version Number";
	public static final String EM_INVALID_D_MODEL_NAME 	 = "Invalid Device Mdoel Name";
	public static final String EM_INVALID_D_TYPE	 	 = "Invalid Device Type";
	public static final String EM_INVALID_PUSH_TYPE	 	 = "Invalid Push Type";

	public static final String EM_INVALID_PATTERN	 	= " Pattern";
	public static final String EM_INVALID_KEY		 	= "Invalid Key";
	public static final String EM_INVALID_VALUE		 	= "Invalid Value";
	public static final String EM_INVALID_PRIORITY    	= "Invalid Priority";
	public static final String EM_INVALID_SORT_BY    	= "Invalid Sort By";
	public static final String EM_INVALID_SORT_ORDER 	= "Invalid Sort Order";
	public static final String EM_INVALID_USERNAME_TYPE	= "Invalid Username Type";
	public static final String EM_INVALID_TYPE		 	= "Invalid Type";
	public static final String EM_INVALID_EVENT		 	= "Invalid Event";
	public static final String EM_INVALID_SUB_TYPE		= "Invalid Sub Type";
	public static final String EM_INVALID_SIZE		 	= "Invalid Size";
	public static final String EM_INVALID_STATUS	 	= "Invalid Status";
	public static final String EM_INVALID_NOTIFY_STATUS	= "Invalid Notification Status";
	public static final String EM_INVALID_CODE		 	= "Invalid Code";
	public static final String EM_INVALID_NAME		 	= "Invalid Name";
	public static final String EM_INVALID_REMAKRS	 	= "Invalid Remarks";
	public static final String EM_INVALID_COMMENTS	 	= "Invalid Comments";
	public static final String EM_INVALID_FIELD_NAME	= "Invalid Field Name";
	public static final String EM_INVALID_FIELD_VALUE	= "Invalid Field Value";
	public static final String EM_INVALID_IMAGE_PATH    = "Invalid Image Path";
	public static final String EM_INVALID_EMAIL		 	= "Invalid Email";
	public static final String EM_INVALID_GENDER	 	= "Invalid Gender";
	public static final String EM_INVALID_DESC		 	= "Invalid Description";
	public static final String EM_INVALID_SUB_DOC_LIST_SIZE = "Invalid Sub Document List Size";
	public static final String EM_INVALID_JSON_NODE	 	= "Invalid JSON Object";
	public static final String EM_INVALID_GROUP_BY	 	= "Invalid Group By";
	public static final String EM_INVALID_PRIVILEGES	= "Invalid Role Privileges";
	public static final String EM_INVALID_EMAIL_VERIFIED	= "Invalid Email Verified";
	public static final String EM_INVALID_USERNAME_VERIFIED	= "Invalid Username Verified";
	public static final String EM_INVALID_UNIQUE_ID		   	= "Invalid Unique Id";
	public static final String EM_INVALID_UNIQUE_CODE   	= "Invalid Unique Code";
	public static final String EM_INVALID_SUB_CATEGORY	 	= "Invalid Sub Category";
	public static final String EM_INVALID_CATEGORY	 		= "Invalid Category";
	public static final String EM_INVALID_SYSTEM_CODE 	    = "Invalid System Code";
	public static final String EM_INVALID_MEMBER_USERNAME_TYPE 	= "Invalid Member Username Type";
	public static final String EM_INVALID_FILE_TYPE 			= "Invalid File Type";
	public static final String EM_INVALID_FILE_SIZE 			= "Invalid File Size";
	public static final String EM_INVALID_LOCATION_NAME		 	= "Invalid Location Name";
	public static final String EM_INVALID_TIMEZONE_CODE		 	= "Invalid Timezone Code";
	public static final String EM_INVALID_ORIGIN		 		= "Invalid Origin";
	public static final String EM_INVALID_ACCESS_LEVEL   		= "Invalid Access Level";
	public static final String EM_INVALID_STABLE_VERSION		= "Invalid Stable Version";
	public static final String EM_INVALID_NEXT_ACTIVITY			= "Invalid Next Activity";
	public static final String EM_INVALID_DEVICE_DATA_INSERT_STATUS	= "Invalid Device Data Insert Status";

	public static final String EM_INVALID_QUERY_FIELDS   	= "Invalid Query Fields";
	public static final String EM_INVALID_DASHBOARD_FIELDS  = "Invalid Dashboard Fields";
	public static final String EM_INVALID_SEARCH_FIELDS   	= "Invalid Search Fields";
	public static final String EM_INVALID_REPORT_FIELDS   	= "Invalid Report Fields";
	public static final String EM_INVALID_PROCEDURE_NAME 	= "Invalid Procedure Name";
	public static final String EM_INVALID_QUERY_TYPE	 	= "Invalid Query Type";
	public static final String EM_INVALID_REPORT_TYPE	 	= "Invalid Report Type";
	public static final String EM_INVALID_DASHBOARD_TYPE 	= "Invalid Dashboard Type";
	public static final String EM_INVALID_SEARCH_TYPE	 	= "Invalid Search Type";
	public static final String EM_INVALID_ANDROID_COUNT     = "Invalid Android License Count";
	public static final String EM_INVALID_IOS_COUNT 	    = "Invalid Ios License Count";

	public static final String EM_INVALID_SUB_CATEGORY_LEVEL 		 = "Invalid Sub Category Level";
	public static final String EM_INVALID_INSERT_MODE   			 = "Invalid Insert Mode";
	public static final String EM_INVALID_INTERNAL_SYSTEM_STATUS 	 = "Invalid Internal System Status";
	public static final String EM_INVALID_DEVICE_AUTO_START_SUB_MODE = "Invalid Device Auto Start Sub Mode";
	public static final String EM_NAME_ALRDY_EXIST  				 = "Name Already Exist";

}
