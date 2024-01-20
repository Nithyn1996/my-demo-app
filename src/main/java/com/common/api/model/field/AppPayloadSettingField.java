package com.common.api.model.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppPayloadSettingField {

	@ApiModelProperty(value = "encryptedPayloadVersionAndroid", required = false)
	@JsonProperty(value = "encryptedPayloadVersionAndroid")
	private float encryptedPayloadVersionAndroid = 0;

	@ApiModelProperty(value = "encryptedPayloadVersionIos", required = false)
    @JsonProperty(value = "encryptedPayloadVersionIos")
    private float encryptedPayloadVersionIos = 0;

	public AppPayloadSettingField() {
	}

	public AppPayloadSettingField(float encryptedPayloadVersionAndroid, float encryptedPayloadVersionIos) {
		this.encryptedPayloadVersionAndroid = encryptedPayloadVersionAndroid;
		this.encryptedPayloadVersionIos = encryptedPayloadVersionIos;
	}

	public float getEncryptedPayloadVersionAndroid() {
		return encryptedPayloadVersionAndroid;
	}

	public void setEncryptedPayloadVersionAndroid(float encryptedPayloadVersionAndroid) {
		this.encryptedPayloadVersionAndroid = encryptedPayloadVersionAndroid;
	}

	public float getEncryptedPayloadVersionIos() {
		return encryptedPayloadVersionIos;
	}

	public void setEncryptedPayloadVersionIos(float encryptedPayloadVersionIos) {
		this.encryptedPayloadVersionIos = encryptedPayloadVersionIos;
	}

	@Override
	public String toString() {
		return "AppPayloadSettingField [encryptedPayloadVersionAndroid=" + encryptedPayloadVersionAndroid
				+ ", encryptedPayloadVersionIos=" + encryptedPayloadVersionIos + "]";
	}

}
