package com.common.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceFieldName;
import com.common.api.constant.APIResourceName;
import com.common.api.datasink.service.MetadataService;
import com.common.api.datasink.service.PortionService;
import com.common.api.model.Metadata;
import com.common.api.model.Portion;
import com.common.api.request.HWDevice;
import com.common.api.request.HWDeviceDataError;
import com.common.api.request.HWDeviceDataLive;
import com.common.api.response.APIPreConditionErrorField;

@Service
public class PayloadValidatorHardware extends APIFixedConstant {

	@Autowired
	FieldValidator fieldValidator;
	@Autowired
	MetadataService metadataService;
	@Autowired
	PortionService portionService;

	public List<APIPreConditionErrorField> isValidHWDevice(String actionType, HWDevice hwDevice) {

		List<APIPreConditionErrorField> errorList = new ArrayList<>();

		String action	= hwDevice.getAction();
		String mVersion = hwDevice.getmV();
		mVersion        = (mVersion.length() <= 0) ? "0.1" : mVersion;

		if (action.equalsIgnoreCase(GC_ACTION_CREATE)) {
			if (mVersion.equalsIgnoreCase("0.1")) {
				errorList = isValidHWDeviceV0_1(actionType, hwDevice);
			}
		} else {
			errorList.add(new APIPreConditionErrorField("action", EM_INVALID_ACTION));
		}
		return errorList;
	}

	public List<APIPreConditionErrorField> isValidHWDeviceV0_1(String actionType, HWDevice hwDevice) {

		List<APIPreConditionErrorField> errorList = new ArrayList<>();

		String companyId = hwDevice.getcId();
		String portionId = hwDevice.getpId();
		String uCode   	 = hwDevice.getuCode();
		String type   	 = hwDevice.getType();

		List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);
 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.DEVICE.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));

		if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (!FieldValidator.isValidId(portionId))
			errorList.add(new APIPreConditionErrorField("portionId", EM_INVALID_PORTION_ID));
		if (!FieldValidator.isValid(uCode, VT_UNIQUE_CODE, FL_UNIQUE_CODE_MIN, FL_UNIQUE_CODE_MAX, true))
			errorList.add(new APIPreConditionErrorField("uCode", EM_INVALID_UNIQUE_CODE));

  		return errorList;
	}

	public List<APIPreConditionErrorField> isValidHWDeviceDataLive(String actionType, HWDeviceDataLive hwDeviceDataLive) {

		List<APIPreConditionErrorField> errorList = new ArrayList<>();

		String action	= hwDeviceDataLive.getAction();
		String mVersion = hwDeviceDataLive.getmV();
		mVersion  		= (mVersion.length() <= 0) ? "0.1" : mVersion;

		if (action.equalsIgnoreCase(GC_ACTION_CREATE)) {
			if (mVersion.equalsIgnoreCase("0.1")) {
				errorList = isValidHWDeviceDataLiveV0_1(actionType, hwDeviceDataLive);
			}
		} else {
			errorList.add(new APIPreConditionErrorField("action", EM_INVALID_ACTION));
		}
		return errorList;
	}

	public List<APIPreConditionErrorField> isValidHWDeviceDataLiveV0_1(String actionType, HWDeviceDataLive hwDeviceDataLive) {

		List<String> emptyList = new ArrayList<>();
		List<APIPreConditionErrorField> errorList = new ArrayList<>();

		String portionId = hwDeviceDataLive.getpId();
		String uCode  	 = hwDeviceDataLive.getuCode();
		String type   	 = hwDeviceDataLive.getType();

		if (!FieldValidator.isValid(uCode, VT_UNIQUE_CODE, FL_UNIQUE_CODE_MIN, FL_UNIQUE_CODE_MAX, true))
			errorList.add(new APIPreConditionErrorField("uCode", EM_INVALID_UNIQUE_CODE));

		if (!FieldValidator.isValidId(portionId)) {
			errorList.add(new APIPreConditionErrorField("portionId", EM_INVALID_PORTION_ID));
		} else {
			List<Portion> portionList = portionService.viewListByCriteria("", "", "", "", "", "", "", portionId, "", emptyList, emptyList, "", "", 0, 0);
			if (portionList.size() > 0) {
				String companyId = portionList.get(0).getCompanyId();
				List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);
		 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.DEVICE_DATA.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
			} else {
				errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
			}
		}
  		return errorList;
	}

	public List<APIPreConditionErrorField> isValidHWDeviceDataError(String actionType, HWDeviceDataError hwDeviceDataError) {

		List<APIPreConditionErrorField> errorList = new ArrayList<>();

		String action	= hwDeviceDataError.getAction();
		String mVersion = hwDeviceDataError.getmV();
		mVersion 		= (mVersion.length() <= 0) ? "0.1" : mVersion;

		if (action.equalsIgnoreCase(GC_ACTION_CREATE)) {
			if (mVersion.equalsIgnoreCase("0.1")) {
				errorList = isValidHWDeviceDataErrorV0_1(actionType, hwDeviceDataError);
			}
		} else {
			errorList.add(new APIPreConditionErrorField("action", EM_INVALID_ACTION));
		}
		return errorList;
	}

	public List<APIPreConditionErrorField> isValidHWDeviceDataErrorV0_1(String actionType, HWDeviceDataError hwDeviceDataError) {

		List<String> emptyList = new ArrayList<>();
		List<APIPreConditionErrorField> errorList = new ArrayList<>();

		String portionId = hwDeviceDataError.getpId();
		String uCode  	 = hwDeviceDataError.getuCode();
		String type   	 = hwDeviceDataError.getType();

		if (!FieldValidator.isValid(uCode, VT_UNIQUE_CODE, FL_UNIQUE_CODE_MIN, FL_UNIQUE_CODE_MAX, true))
			errorList.add(new APIPreConditionErrorField("uCode", EM_INVALID_UNIQUE_CODE));

		if (!FieldValidator.isValidId(portionId)) {
			errorList.add(new APIPreConditionErrorField("portionId", EM_INVALID_PORTION_ID));
		} else {
			List<Portion> portionList = portionService.viewListByCriteria("", "", "", "", "", "", "", portionId, "", emptyList, emptyList, "", "", 0, 0);
			if (portionList.size() > 0) {
				String companyId = portionList.get(0).getCompanyId();
				List<Metadata> metadataList = metadataService.getAvailableMetadata(companyId);
		 		errorList.addAll(fieldValidator.checkMetadata(metadataList, APIResourceName.DEVICE_DATA.toString(), APIResourceFieldName.TYPE.toString(), Arrays.asList(type), 1));
			} else {
				errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
			}
		}
  		return errorList;
	}

}
