package com.common.api.util;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.api.constant.APIFixedConstant;

public class APILog extends APIFixedConstant {

	private static final Logger logger = LoggerFactory.getLogger(GC_LOG_PACKAGE_NAME);

	public APILog() {
	}

	public static boolean writeTraceLog(String logText) {

		String logTextTemp = logText.trim();
		if (logTextTemp.length() > 0) {
			logger.trace(logTextTemp);
			return true;
		}
		return false;
	}

	public static boolean writeDebugLog(String logText) {

		String logTextTemp = logText.trim();
		if (logTextTemp.length() > 0) {
			logger.debug(logTextTemp);
			return true;
		}
		return false;
	}

	public static boolean writeInfoLog(String logText) {

		String logTextTemp = logText.trim();
		if (logTextTemp.length() > 0) {
			logger.info(logTextTemp);
			return true;
		}
		return false;
	}

	public static boolean writeWarnLog(String logText) {

		String logTextTemp = logText.trim();
		if (logTextTemp.length() > 0) {
			logger.warn(logTextTemp);
			return true;
		}
		return false;
	}

	public static boolean writeErrorLog(String logText) {

		String logTextTemp = logText.trim();
		if (logTextTemp.length() > 0) {
			logger.error(logTextTemp);
			return true;
		}
		return false;
	}

	public static boolean writeInstrumentationTimeLog(String logText, Timestamp startTime, Timestamp endTime) {

		String logTextTemp = logText.trim();
		if (logTextTemp.length() > 0) {
			long instrumentationTimeInMs = (endTime.getTime() - startTime.getTime());
			logger.info(logTextTemp + " Duration: " + instrumentationTimeInMs + "ms");
			return true;
		}
		return false;
	}

}
