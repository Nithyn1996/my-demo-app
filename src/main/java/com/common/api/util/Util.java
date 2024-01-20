package com.common.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;

public class Util {

	public Util() {
	}

	public static String getTableOrCollectionObjectId() {
		ObjectId id = new ObjectId();
		return id.toString();
	}

	public static Object setErrorDetail(String errCode, String errMessage) {
 		Map<Object, Object> errorObject = new HashMap<>();
		errorObject.put("code", errCode);
		errorObject.put("message", errMessage);
		return errorObject;
	}

    public static List<String> arrayUniqueValues(List<String> inputArray) {
    	return inputArray.stream().filter(arg0 -> arg0 != null && arg0.length() > 0).distinct().collect(Collectors.toList());
	}

    public static String generateRandomValueAsNumber(int noOfChars) {
    	noOfChars = (noOfChars > 0) ? noOfChars : 4;
    	return RandomStringUtils.randomNumeric(noOfChars).toUpperCase();
	}

    public static String generateRandomValueAsAlphanumeric(int noOfChars) {
    	noOfChars = (noOfChars > 0) ? noOfChars : 4;
    	return RandomStringUtils.randomAlphanumeric(noOfChars).toUpperCase();
	}

    public static String generateRandomValueAsAlphabetic(int noOfChars) {
    	noOfChars = (noOfChars > 0) ? noOfChars : 4;
    	return RandomStringUtils.randomAlphabetic(noOfChars).toUpperCase();
	}

    public static String stringReplaceByKeyList(String inputValue, Map<String, String> replaceList) {
     	return replaceList.entrySet().stream().reduce(inputValue, (s, e) -> s.replace(e.getKey(), e.getValue()), (s1, s2) -> null);
    }

    public static List<String> convertIntoArrayString(String value) {
    	if (value.length() > 0)
    		return Pattern.compile(",").splitAsStream(value).collect(Collectors.toList());
 	    return new ArrayList<>();
    }

    public List<String> convertStringIntoArrayString(String value) {
    	if (value.length() > 0)
    		return Pattern.compile(",").splitAsStream(value).collect(Collectors.toList());
 	    return new ArrayList<>();
    }

    public static List<String> convertIntoArrayString(String value, String separator) {
    	separator = (separator != null && separator.length() > 0) ? separator : ",";
    	if (value.length() > 0)
    		return Pattern.compile(separator).splitAsStream(value).collect(Collectors.toList());
 	    return new ArrayList<>();
    }

    public static String convertIntoLowerCase(String value) {
    	if (value.length() > 0)
    		return value.replace("_", " ").toLowerCase();
 	    return "";
    }

}
