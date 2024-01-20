package com.common.api.model.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDeviceSessionField {

	@ApiModelProperty(value = "userSessionId", required = false)
	@JsonProperty(value = "userSessionId")
	private String userSessionId = "";

	@ApiModelProperty(value = "latitude", required = false)
	@JsonProperty(value = "latitude")
	private String latitude = "";

	@ApiModelProperty(value = "longitude", required = false)
    @JsonProperty(value = "longitude")
    private String longitude = "";

	@ApiModelProperty(value = "gps", required = false)
    @JsonProperty(value = "gps")
    private String gps = "";

	@ApiModelProperty(value = "batteryPercentage", required = false)
    @JsonProperty(value = "batteryPercentage")
    private String batteryPercentage = "";

	@ApiModelProperty(value = "subType", required = false)
    @JsonProperty(value = "subType")
    private String subType = "";

	public UserDeviceSessionField() {
	}

	public String getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getBatteryPercentage() {
		return batteryPercentage;
	}

	public void setBatteryPercentage(String batteryPercentage) {
		this.batteryPercentage = batteryPercentage;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	@Override
	public String toString() {
		return "UserDeviceSessionField [userSessionId=" + userSessionId + ", latitude=" + latitude + ", longitude="
				+ longitude + ", gps=" + gps + ", batteryPercentage=" + batteryPercentage + ", subType=" + subType
				+ "]";
	}

}
