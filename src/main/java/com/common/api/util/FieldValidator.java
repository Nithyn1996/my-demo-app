package com.common.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceFieldName;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.MetadataService;
import com.common.api.model.Metadata;
import com.common.api.response.APIPreConditionErrorField;

@Service
@PropertySource({ "classpath:application.properties"})
public class FieldValidator extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	MetadataService metadataService;

	public static boolean isValidReferredBy(String referredBy) {
		List<String> allowedList = new ArrayList<>();
		allowedList.add(GC_REFERRED_BY_WEB);
		allowedList.add(GC_REFERRED_BY_ANDROID);
		allowedList.add(GC_REFERRED_BY_IOS);
 		return (allowedList.contains(referredBy)) ? true : false;
	}

	public static List<APIPreConditionErrorField> isValidHeaderFieldValue(String referredBy) {
		List<APIPreConditionErrorField> errorList = new ArrayList<>();
		if (referredBy != null && !FieldValidator.isValidReferredBy(referredBy))
			errorList.add(new APIPreConditionErrorField("referredBy", EM_INVALID_REFERRED_BY));
		return errorList;
	}

	public static List<APIPreConditionErrorField> isValidHeaderFieldValue(String referredBy, String sessionId) {
		List<APIPreConditionErrorField> errorList = new ArrayList<>();
		if (referredBy != null && !FieldValidator.isValidReferredBy(referredBy))
			errorList.add(new APIPreConditionErrorField("referredBy", EM_INVALID_REFERRED_BY));
		if (sessionId != null && !FieldValidator.isValidId(sessionId))
			errorList.add(new APIPreConditionErrorField("sessionId", EM_INVALID_SESSION_ID));
		return errorList;
	}

	public static boolean isValidId(String value) {
		if (value == null) {
			return false;
		} else {
	 		return isValid(value, VT_AUTO_GEN_ID, 24, 24, true);
		}
	}

	public static boolean isValidMD5(String value) {
		if (value == null){
			return false;
		} else if (value.equals("d41d8cd98f00b204e9800998ecf8427e")) {
			return false;
		} else {
	 		return isValid(value, VT_MD5, 32, 32, true);
		}
	}

	public static boolean isValidIdListOptional(String value) {
		if (value == null) {
			return false;
		} else {
			int validCount = 0;
			List<String> valueList = Util.convertIntoArrayString(value);
			for (int vc = 0; vc < valueList.size(); vc++) {
				if (!isValid(value, VT_AUTO_GEN_ID, 24, 24, false))
					return false;
				else
					validCount++;
			}
			return (valueList.size() == validCount) ? true : false;
		}
	}

	public static boolean isValidIdOptional(String value) {
		if (value == null) {
			return false;
		} else {
			return isValid(value, VT_AUTO_GEN_ID, 24, 24, false);
		}
	}

	public static boolean isValidYesOrNo(String value) {
		List<String> allowedValues = Arrays.asList("YES", "NO");
		return (allowedValues.contains(value)) ? true : false;
	}

	public List<APIPreConditionErrorField> isValidSortOrderAndSortBy(String sortOrder, String sortBy) {

		List<APIPreConditionErrorField> errorList = new ArrayList<>();
 		List <String> allowedSortBy = SortByConstant.getEnumValueListJsonFieldd();
 		List <String> allowedSortOrder = Arrays.asList(GC_SORT_ASC, GC_SORT_DESC);

 		if (!allowedSortOrder.contains(sortOrder))
			errorList.add(new APIPreConditionErrorField("sortOrder", EM_INVALID_SORT_ORDER));
 		if (!allowedSortBy.contains(sortBy))
			errorList.add(new APIPreConditionErrorField("sortBy", EM_INVALID_SORT_BY));
 		return errorList;
	}

	public static boolean isValidUsername(String inputVal, boolean mandatory) {

		int minLength = 10;
		int maxLength = 10;
		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (!mandatory && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minLength > 0 && maxLength > 0 && inputValLength >= minLength && inputValLength <= maxLength) {
 			return Pattern.compile(RE_USERNAME).matcher(inputValTemp).matches();
		}
		return false;
	}

	public static boolean isValidUsernameCustom(String inputVal, boolean mandatory) {

		int minLength = 10;
		int maxLength = 20;
		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (!mandatory && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minLength > 0 && maxLength > 0 && inputValLength >= minLength && inputValLength <= maxLength) {
 			return Pattern.compile(RE_USERNAME_CUSTOM).matcher(inputValTemp).matches();
		}
		return false;
	}

	/**
	public static boolean isValidPassword(String inputVal, boolean mandatory) {

		int minLength = 5;
		int maxLength = 50;
		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (mandatory == false && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minLength > 0 && maxLength > 0 && inputValLength >= minLength && inputValLength <= maxLength) {
 			return Pattern.compile(RE_PASSWORD).matcher(inputValTemp).matches();
		}
		return false;
	} */

	public static boolean isValidMobilePin(String inputVal, boolean mandatory) {

		int minLength = 4;
		int maxLength = 4;
		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (!mandatory && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minLength > 0 && maxLength > 0 && inputValLength >= minLength && inputValLength <= maxLength) {
 			return Pattern.compile(RE_MOBILE_PIN).matcher(inputValTemp).matches();
		}
		return false;
	}

	public static boolean isValidFreeFormText(String inputVal, int minLength, int maxLength, boolean mandatory) {

		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (!mandatory && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minLength > 0 && maxLength > 0 && inputValLength >= minLength && inputValLength <= maxLength) {
			return true;
		}
		return false;
	}

	public static boolean isValidEmail(String inputVal, boolean mandatory) {

		int minLength = 5;
		int maxLength = 50;
		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (!mandatory && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minLength > 0 && maxLength > 0 && inputValLength >= minLength && inputValLength <= maxLength) {
 			return Pattern.compile(RE_EMAIL).matcher(inputValTemp).matches();
		}
		return false;
	}

	public static boolean isValidInternalText(String inputVal) {
		return isValidInternalText(inputVal, 2, 1000, true);
	}
	public static boolean isValidInternalTextOptional(String inputVal) {
		return isValidInternalText(inputVal, 2, 1000, false);
	}

	public static boolean isValidInternalText(String inputVal, int minLength, int maxLength, boolean mandatory) {

		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (!mandatory && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minLength > 0 && maxLength > 0 && inputValLength >= minLength && inputValLength <= maxLength) {
 			return Pattern.compile(RE_MULTI_TYPE).matcher(inputValTemp).matches();
		}
		return false;
	}

	public static boolean isValid(String inputVal, String type, int minLength, int maxLength, boolean mandatory) {

		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (!mandatory && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minLength > 0 && maxLength > 0 && inputValLength >= minLength && inputValLength <= maxLength) {

			String regExpFormula = "";
			switch (type) {
				case VT_AUTO_GEN_ID: regExpFormula = RE_ID; break;
				case VT_MD5: regExpFormula = RE_MD5; break;
				case VT_NAME: regExpFormula = RE_NAME; break;
				case VT_TYPE: regExpFormula = RE_TYPE; break;
				case VT_NUMBER: regExpFormula = RE_NUMBER; break;
				case VT_DATE: regExpFormula = RE_DATE; break;
				case VT_TIME: regExpFormula = RE_TIME; break;
				case VT_DATE_TIME: regExpFormula = RE_DATE_TIME; break;
				case VT_DESCRIPTION: regExpFormula = RE_DESCRIPTION; break;
				case VT_UNIQUE_CODE: regExpFormula = RE_UNIQUE_CODE; break;
				case VT_FLOAT: regExpFormula = RE_FLOAT; break;
			}

			if (regExpFormula.length() > 0)
				return Pattern.compile(regExpFormula).matcher(inputValTemp).matches();
		}
		return false;
	}

	public static boolean isValidNumber(String inputVal, int minValue, int maxValue, boolean mandatory) {

		String inputValTemp = inputVal.trim();
		int inputValLength = inputValTemp.length();

		if (!mandatory && inputValLength == 0) {
			return true;
		} else if (inputValLength > 0 && minValue >= 0 && maxValue >= 0 && maxValue >= minValue) {
			if (Pattern.compile(RE_NUMBER).matcher(inputValTemp).matches()) {
				try {
					int inputValNumber = Integer.parseInt(inputVal);
					if (inputValNumber >= minValue && inputValNumber <= maxValue) {
						return true;
					}
				} catch (Exception errMess) { }
			}
		}
		return false;
	}

	public List<APIPreConditionErrorField> checkMetadata(List<Metadata> metadataList, String moduleName, String fieldName, List<String> fieldValueList, int mandatoryRecords) {

		String errorMessage  = "", fieldNameTemp = fieldName;
		List<APIPreConditionErrorField> results = new ArrayList<>();
		List<Metadata> matchedMetadataList = new ArrayList<>();
		APIResourceFieldName fieldNameObject = APIResourceFieldName.getEnumValue(fieldName);

		switch (fieldNameObject) {

			case TYPE:
				errorMessage  = EM_INVALID_TYPE; fieldNameTemp = "type"; break;
			case SUB_TYPE:
				errorMessage = EM_INVALID_SUB_TYPE;	fieldNameTemp = "subType"; break;
			case CATEGORY:
				errorMessage  = EM_INVALID_CATEGORY; fieldNameTemp = "category"; break;
			case SUB_CATEGORY:
				errorMessage = EM_INVALID_SUB_CATEGORY;	fieldNameTemp = "subCategory"; break;
			case SUB_CATEGORY_LEVEL:
				errorMessage = EM_INVALID_SUB_CATEGORY_LEVEL;	fieldNameTemp = "subCategoryLevel"; break;
			case STATUS:
				errorMessage  = EM_INVALID_STATUS; fieldNameTemp = "status"; break;
			default:
				break;

		}

		if (errorMessage.length() > 0) {
			if (fieldName.toString().length() > 0) {
				matchedMetadataList = metadataList.stream()
											.filter(str -> (str.getModuleName().equals(moduleName) &&
															str.getFieldName().equals(fieldName)))
											.collect(Collectors.toList());
			} else {
				matchedMetadataList = metadataList.stream()
											.filter(str -> (str.getModuleName().equals(moduleName)))
											.collect(Collectors.toList());
			}
		}

 		int noOfMatchedCount = 0;
 	 	if (matchedMetadataList.size() > 0) {
 	 		for (Metadata metadataTemp : matchedMetadataList) {
 	 			String fieldValue = metadataTemp.getFieldValue();
				boolean isFieldValueMatched = fieldValueList.stream().anyMatch(str -> str.equals(fieldValue));
 	 			if (isFieldValueMatched)
					noOfMatchedCount++;
 	 		}
 		}

		if (noOfMatchedCount != fieldValueList.size() && noOfMatchedCount <= mandatoryRecords)
			results.add(new APIPreConditionErrorField(fieldNameTemp, errorMessage));
		return results;
	}

	public static boolean isValidFromAndToDateTime(String fromDateTimeValue, String toDateTimeValue) {

		DateFormat format = new SimpleDateFormat(GC_DATE_TIME_FORMAT);

		try {
			Date fromTime 	= format.parse(fromDateTimeValue);
			Date toTime 	= format.parse(toDateTimeValue);
			if (fromTime.before(toTime) || fromTime.equals(toTime))
				return true;
		} catch (ParseException errMess) {
		}
		return false;
	}


}
