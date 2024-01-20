package com.common.api.constant;

public class DBFieldNameConstant extends APISerivceProviderConstant {

	/** Default Values */
	public static final int DV_OFFSET = 0;
	public static final int DV_LIMIT  = 1000;
	public static final int DV_OFFSET_DB = 0;
	public static final int DV_LIMIT_DB  = 10000;
	public static final String DV_ACTIVE   = "ACTIVE";
	public static final String DV_INACTIVE = "INACTIVE";

	/** Table / Collection Constant */
	public static final String TB_CONTINENT		= "tb_continent";
	public static final String TB_COUNTRY  		= "tb_country";
	public static final String TB_COMPANY  		= "tb_company";
	public static final String TB_COMPANY_PREF  = "tb_company_preference";
	public static final String TB_GROUP 		= "tb_group";
	public static final String TB_GROUP_PREF 	= "tb_group_preference";
	public static final String TB_DIVISION 		= "tb_division";
	public static final String TB_DIVISION_PREF = "tb_division_preference";
	public static final String TB_HW_DEVICE     = "tb_hardware_device";
	public static final String TB_LANGUAGE		= "tb_language";
	public static final String TB_METADATA 		= "tb_metadata";
	public static final String TB_MODULE 		= "tb_module";
	public static final String TB_MODULE_PREF	= "tb_module_preference";
	public static final String TB_ROLE_COMPANY	= "tb_role_company";
	public static final String TB_ROLE_DIVISION	= "tb_role_division";
	public static final String TB_ROLE_MODULE	= "tb_role_module";
	public static final String TB_ROLE_GROUP	= "tb_role_group";
	public static final String TB_ROLE_USER		= "tb_role_user";
	public static final String TB_USER	 		= "tb_user";
	public static final String TB_USER_DEVICE   = "tb_user_device";
	public static final String TB_USER_FEEDBACK = "tb_user_feedback";
	public static final String TB_USER_PREF		= "tb_user_preference";
	public static final String TB_USER_SECRET	= "tb_user_secret_key";
	public static final String TB_USER_SESSION	= "tb_user_session";
	public static final String TB_USER_DISTINCT	= "tb_user_distinct";
	public static final String TB_PROPERTY 		= "tb_property";
	public static final String TB_SECTION 		= "tb_section";
	public static final String TB_SECTION_PREF	= "tb_section_preference";
	public static final String TB_PORTION 		= "tb_portion";
	public static final String TB_DEVICE 		= "tb_device";
	public static final String TB_DEVICE_DATA	= "tb_device_data";
	public static final String TB_DEVICE_SUMMARY	= "tb_device_summary";
	public static final String TB_MEMBER 		= "tb_member";
	public static final String TB_MEMBER_DEVICE	= "tb_member_device";
	public static final String TB_SEARCH_MAP	= "tb_search_map";
	public static final String TB_QUERY_MAP		= "tb_query_map";
	public static final String TB_REPORT_MAP	= "tb_report_map";
	public static final String TB_DASHBOARD_MAP	= "tb_dashboard_map";
	public static final String TB_PRELOADED_DATA = "tb_preloaded_data";
	public static final String TB_USER_VERIFICATION		= "tb_user_verification";
	public static final String TB_USR_AUTHENTICATION 	= "tb_user_authentication";
	public static final String TB_USER_DEVICE_SESSION	= "tb_user_device_session";
	public static final String TB_USER_LAST_ACTIVITY 	= "tb_user_last_activity";
	public static final String TB_ORDER 				= "tb_order";
	public static final String TB_SUBSCRIPTION 			= "tb_subscription";
	

	public static final int FL_DATE_SIZE = 10;
	public static final int FL_TIME_SIZE = 8;
	public static final int FL_DATE_TIME_SIZE = 19;

	public static final int FL_AUTO_GEN_ID_MIN = 0;
	public static final int FL_AUTO_GEN_ID_MAX = 24;

	public static final int FL_REFERENCE_ID_MIN = 24;
	public static final int FL_REFERENCE_ID_MAX = 24;
	public static final int FL_REFERENCE_ID_MIN_OPTIONAL = 0;

	public static final int FL_BLOB_MIN = 1;
	public static final int FL_BLOB_MAX = 100000;
	public static final int FL_BLOB_MIN_OPTIONAL = 0;

	public static final int FL_CODE_MIN = 1;
	public static final int FL_CODE_MAX = 100;
	public static final int FL_CODE_MIN_OPTIONAL = 0;

	public static final int FL_SYSTEM_CODE_MIN = 1;
	public static final int FL_SYSTEM_CODE_MAX = 20;
	public static final int FL_SYSTEM_CODE_MIN_OPTIONAL = 0;

	public static final int FL_EMAIL_MIN = 1;
	public static final int FL_EMAIL_MAX = 50;
	public static final int FL_EMAIL_MIN_OPTIONAL = 0;

	public static final int FL_DESC_MIN = 1;
	public static final int FL_DESC_MAX = 2000;
	public static final int FL_DESE_MIN_OPTIONAL = 0;

	public static final int FL_LIST_MIN = 1;
	public static final int FL_LIST_MAX = 200;
	public static final int FL_LIST_MIN_OPTIONAL = 0;

	public static final int FL_KEY_MIN = 1;
	public static final int FL_KEY_MAX = 20;
	public static final int FL_KEY_MIN_OPTIONAL = 0;

	public static final int FL_NAME_MIN = 1;
	public static final int FL_NAME_MAX = 100;
	public static final int FL_NAME_MIN_OPTIONAL = 0;

	public static final int FL_YES_NO_MIN = 1;
	public static final int FL_YES_NO_MAX = 50;
	public static final int FL_YES_NO_MIN_OPTIONAL = 0;

	public static final int FL_STATUS_MIN = 1;
	public static final int FL_STATUS_MAX = 50;
	public static final int FL_STATUS_MIN_OPTIONAL = 0;

	public static final int FL_TYPE_MIN = 1;
	public static final int FL_TYPE_MAX = 50;
	public static final int FL_TYPE_MIN_OPTIONAL = 0;

	public static final int FL_CATEGORY_MIN = 1;
	public static final int FL_CATEGORY_MAX = 50;
	public static final int FL_CATEGORY_MIN_OPTIONAL = 0;

	public static final int FL_ORIGIN_MIN = 1;
	public static final int FL_ORIGIN_MAX = 50;
	public static final int FL_ORIGIN_MIN_OPTIONAL = 0;

	public static final int FL_SUB_CATEGORY_MIN = 1;
	public static final int FL_SUB_CATEGORY_MAX = 50;
	public static final int FL_SUB_CATEGORY_MIN_OPTIONAL = 0;

	public static final int FL_SUB_DOCUMENT_LIST_MIN = 0;
	public static final int FL_SUB_DOCUMENT_LIST_MAX = 50;
	public static final int FL_SUB_DOCUMENT_LIST_MIN_OPTIONAL = 0;

	public static final int FL_USERNAME_MIN = 1;
	public static final int FL_USERNAME_MAX = 50;
	public static final int FL_USERNAME_MIN_OPTIONAL = 0;

	public static final int FL_DEVICE_FIELD_MIN = 1;
	public static final int FL_DEVICE_FIELD_MAX = 500;
	public static final int FL_DEVICE_FIELD_MIN_OPTIONAL = 0;

	public static final int FL_PASSWORD_MIN = 32;
	public static final int FL_PASSWORD_MAX = 32;

	public static final int FL_MOBILE_PIN_MIN = 5;
	public static final int FL_MOBILE_PIN_MAX = 20;
	public static final int FL_MOBILE_PIN_MIN_OPTIONAL = 0;

	public static final int FL_VERIFICATION_CODE_MIN = 4;
	public static final int FL_VERIFICATION_CODE_MAX = 4;
	public static final int FL_VERIFICATION_CODE_MIN_OPTIONAL = 0;

	public static final int FL_UNIQUE_CODE_MIN = 1;
	public static final int FL_UNIQUE_CODE_MAX = 100;
	public static final int FL_UNIQUE_CODE_MIN_OPTIONAL = 0;

	public static final int FL_UNIQUE_ID_MIN = 1;
	public static final int FL_UNIQUE_ID_MAX = 100;
	public static final int FL_UNIQUE_ID_MIN_OPTIONAL = 0;

	public static final int FL_IMAGE_PATH_MIN = 1;
	public static final int FL_IMAGE_PATH_MAX = 100;
	public static final int FL_IMAGE_PATH_MIN_OPTIONAL = 0;

	public static final int FL_FIELD_NAME_MIN = 1;
	public static final int FL_FIELD_NAME_MAX = 100;
	public static final int FL_FIELD_NAME_MIN_OPTIONAL = 0;

	public static final int FL_FIELD_VALUE_MIN = 1;
	public static final int FL_FIELD_VALUE_MAX = 100;
	public static final int FL_FIELD_VALUE_MIN_OPTIONAL = 0;

	public static final int FL_FREE_FORM_MIN = 1;
	public static final int FL_FREE_FORM_MAX = 50;
	public static final int FL_FREE_FORM_MIN_OPTIONAL = 0;

	public static final int FL_NUMBER_MIN = 1;
	public static final int FL_NUMBER_MAX = 50;
	public static final int FL_NUMBER_MIN_OPTIONAL = 0;

}
