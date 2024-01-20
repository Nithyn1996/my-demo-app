package com.common.api.response;

import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "APIRunTimeError")
@TypeAlias(value = "APIRunTimeError")
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIRunTimeError {

	@ApiModelProperty(value = "code", required = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "code")
	private String code = "";

	@ApiModelProperty(value = "message", required = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "message")
	private String message = "";

	public APIRunTimeError() {
	}

	public APIRunTimeError(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "APIRunTimeError [code=" + code + ", message=" + message + "]";
	}

}
