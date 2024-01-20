package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPreferenceField extends APIFixedConstant {

 	@ApiModelProperty(value = "continentId", required = false)
    @JsonProperty(value = "continentId")
    private String continentId = "";

 	@ApiModelProperty(value = "countryId", required = false)
    @JsonProperty(value = "countryId")
    private String countryId = "";

 	@ApiModelProperty(value = "appNotification", required = false)
    @JsonProperty(value = "appNotification")
    private String appNotification = "";

 	@ApiModelProperty(value = "backgroundStartup", required = false)
    @JsonProperty(value = "backgroundStartup")
    private String backgroundStartup = "";

 	@ApiModelProperty(value = "speedLimitBeep", required = false)
    @JsonProperty(value = "speedLimitBeep")
    private String speedLimitBeep = "";

 	@ApiModelProperty(value = "beep", required = false)
    @JsonProperty(value = "beep")
    private String beep = "";

 	@ApiModelProperty(value = "voiceAlert", required = false)
    @JsonProperty(value = "voiceAlert")
    private String voiceAlert = "";

 	@ApiModelProperty(value = "coPliotMode", required = false)
    @JsonProperty(value = "coPliotMode")
    private String coPliotMode = "";

	public UserPreferenceField() {
	}

	public String getContinentId() {
		return continentId;
	}

	public void setContinentId(String continentId) {
		this.continentId = continentId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getAppNotification() {
		return appNotification;
	}

	public void setAppNotification(String appNotification) {
		this.appNotification = appNotification;
	}

	public String getBackgroundStartup() {
		return backgroundStartup;
	}

	public void setBackgroundStartup(String backgroundStartup) {
		this.backgroundStartup = backgroundStartup;
	}

	public String getSpeedLimitBeep() {
		return speedLimitBeep;
	}

	public void setSpeedLimitBeep(String speedLimitBeep) {
		this.speedLimitBeep = speedLimitBeep;
	}

	public String getBeep() {
		return beep;
	}

	public void setBeep(String beep) {
		this.beep = beep;
	}

	public String getVoiceAlert() {
		return voiceAlert;
	}

	public void setVoiceAlert(String voiceAlert) {
		this.voiceAlert = voiceAlert;
	}

	public String getCoPliotMode() {
		return coPliotMode;
	}

	public void setCoPliotMode(String coPliotMode) {
		this.coPliotMode = coPliotMode;
	}

	@Override
	public String toString() {
		return "UserPreferenceField [continentId=" + continentId + ", countryId=" + countryId + ", appNotification="
				+ appNotification + ", backgroundStartup=" + backgroundStartup + ", speedLimitBeep=" + speedLimitBeep
				+ ", beep=" + beep + ", voiceAlert=" + voiceAlert + ", coPliotMode=" + coPliotMode + "]";
	}

}
