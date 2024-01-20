package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceSafetyField extends APIFixedConstant {

	@JsonProperty("helmetAloneCount")
	private int helmetAloneCount = 0;

	@JsonProperty("userWithHelmetCount")
	private int userWithHelmetCount = 0;

	@JsonProperty("userWithoutHelmetCount")
	private int userWithoutHelmetCount = 0;

	@JsonProperty("userWithSeatbeltCount")
	private int userWithSeatbeltCount = 0;

	@JsonProperty("userWithoutSeatbeltCount")
	private int userWithoutSeatbeltCount = 0;

	@JsonProperty("analyticsServerStatusCode")
	private int analyticsServerStatusCode = 0;

	public DeviceSafetyField() {
	}

	public int getHelmetAloneCount() {
		return helmetAloneCount;
	}

	public void setHelmetAloneCount(int helmetAloneCount) {
		this.helmetAloneCount = helmetAloneCount;
	}

	public int getUserWithHelmetCount() {
		return userWithHelmetCount;
	}

	public void setUserWithHelmetCount(int userWithHelmetCount) {
		this.userWithHelmetCount = userWithHelmetCount;
	}

	public int getUserWithoutHelmetCount() {
		return userWithoutHelmetCount;
	}

	public void setUserWithoutHelmetCount(int userWithoutHelmetCount) {
		this.userWithoutHelmetCount = userWithoutHelmetCount;
	}

	public int getUserWithSeatbeltCount() {
		return userWithSeatbeltCount;
	}

	public void setUserWithSeatbeltCount(int userWithSeatbeltCount) {
		this.userWithSeatbeltCount = userWithSeatbeltCount;
	}

	public int getUserWithoutSeatbeltCount() {
		return userWithoutSeatbeltCount;
	}

	public void setUserWithoutSeatbeltCount(int userWithoutSeatbeltCount) {
		this.userWithoutSeatbeltCount = userWithoutSeatbeltCount;
	}

	public int getAnalyticsServerStatusCode() {
		return analyticsServerStatusCode;
	}

	public void setAnalyticsServerStatusCode(int analyticsServerStatusCode) {
		this.analyticsServerStatusCode = analyticsServerStatusCode;
	}

	@Override
	public String toString() {
		return "DeviceSafetyField [helmetAloneCount=" + helmetAloneCount + ", userWithHelmetCount="
				+ userWithHelmetCount + ", userWithoutHelmetCount=" + userWithoutHelmetCount
				+ ", userWithSeatbeltCount=" + userWithSeatbeltCount + ", userWithoutSeatbeltCount="
				+ userWithoutSeatbeltCount + ", analyticsServerStatusCode=" + analyticsServerStatusCode + "]";
	}

}
