package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDataField1 extends APIFixedConstant {

 	@ApiModelProperty(value = "trafficScore", required = false)
    @JsonProperty(value = "trafficScore")
    private String trafficScore = "";

 	@ApiModelProperty(value = "driverState", required = false)
    @JsonProperty(value = "driverState")
    private String driverState = "";

 	@ApiModelProperty(value = "transportMode", required = false)
    @JsonProperty(value = "transportMode")
    private String transportMode = "";

 	@ApiModelProperty(value = "drivingStyle", required = false)
    @JsonProperty(value = "drivingStyle")
    private String drivingStyle = "";

 	@ApiModelProperty(value = "riskStyle", required = false)
    @JsonProperty(value = "riskStyle")
    private String riskStyle = "";

 	@ApiModelProperty(value = "batteryLevel", required = false)
    @JsonProperty(value = "batteryLevel")
    private String batteryLevel = "";
 	
 	@ApiModelProperty(value = "healthLevel", required = false)
 	@JsonProperty(value = "healthLevel")
 	private String healthLevel = "";

	public DeviceDataField1() {
	}

	public String getTrafficScore() {
		return trafficScore;
	}

	public void setTrafficScore(String trafficScore) {
		this.trafficScore = trafficScore;
	}

	public String getDriverState() {
		return driverState;
	}

	public void setDriverState(String driverState) {
		this.driverState = driverState;
	}

	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	public String getDrivingStyle() {
		return drivingStyle;
	}

	public void setDrivingStyle(String drivingStyle) {
		this.drivingStyle = drivingStyle;
	}

	public String getRiskStyle() {
		return riskStyle;
	}

	public void setRiskStyle(String riskStyle) {
		this.riskStyle = riskStyle;
	}

	public String getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(String batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	

	public String getHealthLevel() {
		return healthLevel;
	}

	public void setHealthLevel(String healthLevel) {
		this.healthLevel = healthLevel;
	}

	@Override
	public String toString() {
		return "DeviceDataField1 [trafficScore=" + trafficScore + ", driverState=" + driverState + ", transportMode="
				+ transportMode + ", drivingStyle=" + drivingStyle + ", riskStyle=" + riskStyle + ", batteryLevel="
				+ batteryLevel + ", healthLevel=" + healthLevel + "]";
	}

}
