package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModulePrefAppBootSetting extends APIFixedConstant {

 	@ApiModelProperty(value = "deviceAutoStartMode", required = true)
    @JsonProperty(value = "deviceAutoStartMode")
    private String deviceAutoStartMode = "";

 	@ApiModelProperty(value = "deviceDetailTranferMode", required = true)
    @JsonProperty(value = "deviceDetailTranferMode")
    private String deviceDetailTranferMode = "";

 	@ApiModelProperty(value = "deviceAutoStopMinute", required = true)
    @JsonProperty(value = "deviceAutoStopMinute")
    private String deviceAutoStopMinute = "";

	public ModulePrefAppBootSetting() {
	}

	public String getDeviceAutoStartMode() {
		return deviceAutoStartMode;
	}

	public void setDeviceAutoStartMode(String deviceAutoStartMode) {
		this.deviceAutoStartMode = deviceAutoStartMode;
	}

	public String getDeviceDetailTranferMode() {
		return deviceDetailTranferMode;
	}

	public void setDeviceDetailTranferMode(String deviceDetailTranferMode) {
		this.deviceDetailTranferMode = deviceDetailTranferMode;
	}

	@Override
	public String toString() {
		return "ModulePrefAppBootSetting [deviceAutoStartMode=" + deviceAutoStartMode + ", deviceDetailTranferMode="
				+ deviceDetailTranferMode + "]";
	}

}
