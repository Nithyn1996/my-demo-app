package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDataTrackingField extends APIFixedConstant {

 	@ApiModelProperty(value = "internet", required = false)
    @JsonProperty(value = "internet")
    private String internet = "";

 	@ApiModelProperty(value = "gps", required = false)
    @JsonProperty(value = "gps")
    private String gps = "";

 	@ApiModelProperty(value = "location", required = false)
    @JsonProperty(value = "location")
    private String location = "";

 	@ApiModelProperty(value = "doa", required = false)
    @JsonProperty(value = "doa")
    private String doa = "";

 	@ApiModelProperty(value = "deviceStartMode", required = false)
    @JsonProperty(value = "deviceStartMode")
    private String deviceStartMode = "";

	public DeviceDataTrackingField() {
	}

	public String getInternet() {
		return internet;
	}

	public void setInternet(String internet) {
		this.internet = internet;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDoa() {
		return doa;
	}

	public void setDoa(String doa) {
		this.doa = doa;
	}

	public String getDeviceStartMode() {
		return deviceStartMode;
	}

	public void setDeviceStartMode(String deviceStartMode) {
		this.deviceStartMode = deviceStartMode;
	}

	@Override
	public String toString() {
		return "DeviceDataTrackingField [internet=" + internet + ", gps=" + gps + ", location=" + location + ", doa="
				+ doa + ", deviceStartMode=" + deviceStartMode + "]";
	}

}
