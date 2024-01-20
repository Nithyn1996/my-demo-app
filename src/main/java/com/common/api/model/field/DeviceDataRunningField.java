package com.common.api.model.field;

import java.util.ArrayList;
import java.util.List;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDataRunningField extends APIFixedConstant { 

 	@ApiModelProperty(value = "mobileScreenOnDuration", required = false) 
    @JsonProperty(value = "mobileScreenOnDuration")       
    private float mobileScreenOnDuration = 0;      
 	
 	@ApiModelProperty(value = "mobileUseCallDuration", required = false) 
    @JsonProperty(value = "mobileUseCallDuration")       
    private float mobileUseCallDuration = 0;     
 	
 	@ApiModelProperty(value = "overSpeedDuration", required = false) 
    @JsonProperty(value = "overSpeedDuration")       
    private float overSpeedDuration = 0;     
 	
 	@ApiModelProperty(value = "mobileScreenOnKiloMeter", required = false) 
    @JsonProperty(value = "mobileScreenOnKiloMeter")       
    private float mobileScreenOnKiloMeter = 0;  
 	
 	@ApiModelProperty(value = "mobileUseCallKiloMeter", required = false) 
    @JsonProperty(value = "mobileUseCallKiloMeter")       
    private float mobileUseCallKiloMeter = 0;  
 	
 	@ApiModelProperty(value = "overSpeedKiloMeter", required = false) 
    @JsonProperty(value = "overSpeedKiloMeter")       
    private float overSpeedKiloMeter = 0; 
 	 
	@ApiModelProperty(value = "keyValues", required = false) 
    @JsonProperty(value = "keyValues")    
	private List<KeyValuesFloat> keyValues = new ArrayList<KeyValuesFloat>();
 	
	public DeviceDataRunningField() {
	}

	public float getMobileScreenOnDuration() {
		return mobileScreenOnDuration;
	}

	public void setMobileScreenOnDuration(float mobileScreenOnDuration) {
		this.mobileScreenOnDuration = mobileScreenOnDuration;
	}

	public float getMobileUseCallDuration() {
		return mobileUseCallDuration;
	}

	public void setMobileUseCallDuration(float mobileUseCallDuration) {
		this.mobileUseCallDuration = mobileUseCallDuration;
	}

	public float getOverSpeedDuration() {
		return overSpeedDuration;
	}

	public void setOverSpeedDuration(float overSpeedDuration) {
		this.overSpeedDuration = overSpeedDuration;
	}

	public float getMobileScreenOnKiloMeter() {
		return mobileScreenOnKiloMeter;
	}

	public void setMobileScreenOnKiloMeter(float mobileScreenOnKiloMeter) {
		this.mobileScreenOnKiloMeter = mobileScreenOnKiloMeter;
	}

	public float getMobileUseCallKiloMeter() {
		return mobileUseCallKiloMeter;
	}

	public void setMobileUseCallKiloMeter(float mobileUseCallKiloMeter) {
		this.mobileUseCallKiloMeter = mobileUseCallKiloMeter;
	}

	public float getOverSpeedKiloMeter() {
		return overSpeedKiloMeter;
	}

	public void setOverSpeedKiloMeter(float overSpeedKiloMeter) {
		this.overSpeedKiloMeter = overSpeedKiloMeter;
	}

	public List<KeyValuesFloat> getKeyValues() {
		return keyValues;
	} 
 
	public void setKeyValues(List<KeyValuesFloat> keyValues) {
		this.keyValues = keyValues;
	}

	@Override
	public String toString() {
		return "DeviceDataRunningField [mobileScreenOnDuration=" + mobileScreenOnDuration + ", mobileUseCallDuration="
				+ mobileUseCallDuration + ", overSpeedDuration=" + overSpeedDuration + ", mobileScreenOnKiloMeter="
				+ mobileScreenOnKiloMeter + ", mobileUseCallKiloMeter=" + mobileUseCallKiloMeter
				+ ", overSpeedKiloMeter=" + overSpeedKiloMeter + ", keyValues=" + keyValues + "]";
	}
	  
}
