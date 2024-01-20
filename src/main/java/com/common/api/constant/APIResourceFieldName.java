package com.common.api.constant;

public enum APIResourceFieldName {

	TYPE, STATUS, CATEGORY, SUB_CATEGORY, SUB_TYPE, SUB_CATEGORY_LEVEL;

	public static APIResourceFieldName getEnumValue(String value) {
		try {
			return APIResourceFieldName.valueOf(value);
		} catch(Exception errMess) {
			return null;
		}
    }

}
