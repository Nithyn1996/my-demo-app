package com.common.api.model.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSessionField {

	@ApiModelProperty(value = "mobAppLoginVersion", required = false)
	@JsonProperty(value = "mobAppLoginVersion")
	private String mobAppLoginVersion = "";

	@ApiModelProperty(value = "deviceOrderId", required = false)
    @JsonProperty(value = "deviceOrderId")
    private String deviceOrderId = "";

	@ApiModelProperty(value = "deviceUniqueId", required = false)
    @JsonProperty(value = "deviceUniqueId")
    private String deviceUniqueId = "";

	@ApiModelProperty(value = "deviceVersionNumber", required = false)
    @JsonProperty(value = "deviceVersionNumber")
    private String deviceVersionNumber = "";

	@ApiModelProperty(value = "deviceModelName", required = false)
    @JsonProperty(value = "deviceModelName")
    private String deviceModelName = "";

	@ApiModelProperty(value = "deviceType", required = false)
    @JsonProperty(value = "deviceType")
    private String deviceType = "";

	@ApiModelProperty(value = "deviceStatus", required = false)
    @JsonProperty(value = "deviceStatus")
    private String deviceStatus = "";

	@ApiModelProperty(value = "deviceToken", required = false)
    @JsonProperty(value = "deviceToken")
    private String deviceToken = "";

	public UserSessionField() {
	}

	public String getMobAppLoginVersion() {
		return mobAppLoginVersion;
	}

	public void setMobAppLoginVersion(String mobAppLoginVersion) {
		this.mobAppLoginVersion = mobAppLoginVersion;
	}

	public String getDeviceOrderId() {
		return deviceOrderId;
	}

	public void setDeviceOrderId(String deviceOrderId) {
		this.deviceOrderId = deviceOrderId;
	}

	public String getDeviceUniqueId() {
		return deviceUniqueId;
	}

	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}

	public String getDeviceVersionNumber() {
		return deviceVersionNumber;
	}

	public void setDeviceVersionNumber(String deviceVersionNumber) {
		this.deviceVersionNumber = deviceVersionNumber;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Override
	public String toString() {
		return "UserSessionField [mobAppLoginVersion=" + mobAppLoginVersion + ", deviceOrderId=" + deviceOrderId
				+ ", deviceUniqueId=" + deviceUniqueId + ", deviceVersionNumber=" + deviceVersionNumber
				+ ", deviceModelName=" + deviceModelName + ", deviceType=" + deviceType + ", deviceStatus="
				+ deviceStatus + ", deviceToken=" + deviceToken + "]";
	}

}
