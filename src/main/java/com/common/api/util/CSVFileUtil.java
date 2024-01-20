package com.common.api.util;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
public class CSVFileUtil {

	public CSVFileUtil() {
	}

	public String formatFieldValueAsString(CSVRecord csvRecord, String fieldName) {

		String result = "";
 		try {
 			if (csvRecord.isMapped(fieldName) && csvRecord.get(fieldName) != null)
 				result = csvRecord.get(fieldName).trim();
 			return result.replace("null", "");
		} catch (Exception errMess) {
		}
		return result;

	}

	public String formatFieldValueAsFloat(CSVRecord csvRecord, String fieldName) {

		String result = "0.00";
 		try {
 			if (csvRecord.isMapped(fieldName) && csvRecord.get(fieldName) != null)
 				result = csvRecord.get(fieldName).trim();
 			result = result.replace("null", "");
 			if (result.length() <= 0)
 				return  "0.00";
		} catch (Exception errMess) {
		}
		return result;

	}

	public float formatFieldValueAsFloatValue(CSVRecord csvRecord, String fieldName) {

		float result = 0;
 		try {
 			result = Float.parseFloat(this.formatFieldValueAsFloat(csvRecord, fieldName));
		} catch (Exception errMess) {
		}
		return result;

	}

	public int formatFieldValueAsInteger(CSVRecord csvRecord, String fieldName) {

		int result = 0;
 		try {
 			String valueTemp = "";
 			if (csvRecord.isMapped(fieldName) && csvRecord.get(fieldName) != null)
 				valueTemp = csvRecord.get(fieldName).trim();
 			valueTemp = valueTemp.replace("null", "");
 			if (valueTemp.length() > 0)
 				result = Integer.parseInt(valueTemp);
		} catch (Exception errMess) {
		}
		return result;

	}

}
