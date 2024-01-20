package com.common.api.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.request.HWDevice;
import com.common.api.request.HWDeviceDataError;
import com.common.api.request.HWDeviceDataLive;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HWDataConversion extends APIFixedConstant {

	/** Service Module */
	@Autowired
	ResultSetConversion resultSetConversion;

	public static HWDevice stringToHWDevice(String inputValue) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(inputValue, HWDevice.class);
		} catch (IOException errMess) {
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
			return new HWDevice();
		}
	}

	public static HWDeviceDataLive stringToHWDeviceDataLive(String inputValue) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(inputValue, HWDeviceDataLive.class);
		} catch (IOException errMess) {
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
			return new HWDeviceDataLive();
		}
	}

	public static HWDeviceDataError stringToHWDeviceDataError(String inputValue) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(inputValue, HWDeviceDataError.class);
		} catch (IOException errMess) {
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
			return new HWDeviceDataError();
		}
	}

}
