package com.common.api.response;

import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProfileCardinalityAccess")
@TypeAlias(value = "ProfileCardinalityAccess")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileCardinalityAccess {

	@ApiModelProperty(value = "code", required = true)
	@JsonProperty(value = "code")
	private String code = "";

	@ApiModelProperty(value = "message", required = true)
	@JsonProperty(value = "message")
	private String message = "";

	public ProfileCardinalityAccess() {
	}

	public ProfileCardinalityAccess(String code, String message) {
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
		return "ProfileCardinalityAccess [code=" + code + ", message=" + message + "]";
	}

}
