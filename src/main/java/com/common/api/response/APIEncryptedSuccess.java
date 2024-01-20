package com.common.api.response;

import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "APIGeneralSuccess")
@TypeAlias(value = "APIGeneralSuccess")
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIEncryptedSuccess {

	@ApiModelProperty(value = "message", required = true)
	@JsonProperty(value = "message")
	private String message = "";

	public APIEncryptedSuccess() {
	}

	public APIEncryptedSuccess(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "APIGeneralSuccess [message=" + message + "]";
	}

}
