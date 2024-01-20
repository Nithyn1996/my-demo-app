package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPrefAppBootSetting extends APIFixedConstant {

 	@ApiModelProperty(value = "deviceUserRole", required = true)
    @JsonProperty(value = "deviceUserRole")
    private String deviceUserRole = ""; // ["", "RIDE_DRIVER", "RIDE_PASSENGER"] Default: "" Consider Value: RIDE_DRIVER

 	@ApiModelProperty(value = "deviceAutoStartMode", required = true)
    @JsonProperty(value = "deviceAutoStartMode")
    private String deviceAutoStartMode = ""; // ["", "NORMAL", "SENSOR"] Default: "" Consider Value: NORMAL

 	@ApiModelProperty(value = "deviceAutoStartSubMode", required = true)
    @JsonProperty(value = "deviceAutoStartSubMode")
    private String deviceAutoStartSubMode = ""; // ["", "AUTO", "MANUAL"] Default: "" Consider Value: AUTO

 	@ApiModelProperty(value = "deviceDetailTranferMode", required = true)
    @JsonProperty(value = "deviceDetailTranferMode")
    private String deviceDetailTranferMode = ""; // ["", "API_CALL", "RIDE_CSV"] Default: "" Consider Value: API_CALL

 	@ApiModelProperty(value = "deviceAutoStopMinute", required = true)
    @JsonProperty(value = "deviceAutoStopMinute")
    private String deviceAutoStopMinute = ""; // ["5"] Default: 5  Consider Value: 5

 	@ApiModelProperty(value = "deviceRawDetailTranferFile", required = true)
    @JsonProperty(value = "deviceRawDetailTranferFile")
    private String deviceRawDetailTranferFile = ""; // ["", "ALL_RIDE", "VALID_RIDE"] Default: "VALID_RIDE" Consider Value: VALID_RIDE

 	@ApiModelProperty(value = "deviceDetailCSVTranferFrom", required = true)
    @JsonProperty(value = "deviceDetailCSVTranferFrom")
    private String deviceDetailCSVTranferFrom = ""; // ["", "ALL", "ANDROID", "IOS"] Default: "". This is only for CSV process

	public UserPrefAppBootSetting() {
	}

	public String getDeviceUserRole() {
		return deviceUserRole;
	}

	public void setDeviceUserRole(String deviceUserRole) {
		this.deviceUserRole = deviceUserRole;
	}

	public String getDeviceAutoStartMode() {
		return deviceAutoStartMode;
	}

	public void setDeviceAutoStartMode(String deviceAutoStartMode) {
		this.deviceAutoStartMode = deviceAutoStartMode;
	}

	public String getDeviceAutoStartSubMode() {
		return deviceAutoStartSubMode;
	}

	public void setDeviceAutoStartSubMode(String deviceAutoStartSubMode) {
		this.deviceAutoStartSubMode = deviceAutoStartSubMode;
	}

	public String getDeviceDetailTranferMode() {
		return deviceDetailTranferMode;
	}

	public void setDeviceDetailTranferMode(String deviceDetailTranferMode) {
		this.deviceDetailTranferMode = deviceDetailTranferMode;
	}

	public String getDeviceAutoStopMinute() {
		return deviceAutoStopMinute;
	}

	public void setDeviceAutoStopMinute(String deviceAutoStopMinute) {
		this.deviceAutoStopMinute = deviceAutoStopMinute;
	}

	public String getDeviceRawDetailTranferFile() {
		return deviceRawDetailTranferFile;
	}

	public void setDeviceRawDetailTranferFile(String deviceRawDetailTranferFile) {
		this.deviceRawDetailTranferFile = deviceRawDetailTranferFile;
	}

	public String getDeviceDetailCSVTranferFrom() {
		return deviceDetailCSVTranferFrom;
	}

	public void setDeviceDetailCSVTranferFrom(String deviceDetailCSVTranferFrom) {
		this.deviceDetailCSVTranferFrom = deviceDetailCSVTranferFrom;
	}

	@Override
	public String toString() {
		return "UserPrefAppBootSetting [deviceUserRole=" + deviceUserRole + ", deviceAutoStartMode="
				+ deviceAutoStartMode + ", deviceAutoStartSubMode=" + deviceAutoStartSubMode
				+ ", deviceDetailTranferMode=" + deviceDetailTranferMode + ", deviceAutoStopMinute="
				+ deviceAutoStopMinute + ", deviceRawDetailTranferFile=" + deviceRawDetailTranferFile
				+ ", deviceDetailCSVTranferFrom=" + deviceDetailCSVTranferFrom + "]";
	}

}
