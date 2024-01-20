package com.common.api.util;

import org.springframework.stereotype.Service;

@Service
public class DataTypeConversion {

	public float resultSetToFloat(String value) {
 		try {
 			return Float.valueOf(value);
		} catch (Exception errMess) {
		}
		return 0;
	}

	public float convertStringToFloat(float originalValue, String newValue) {
		float result = this.resultSetToFloat(newValue);
		return (result > 0) ? result : originalValue;
	}

	public String convertStringToString(String originalValue, String newValue) {
 		return (newValue.length() > 0) ? newValue : originalValue;
	}

}
