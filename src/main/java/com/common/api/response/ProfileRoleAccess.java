package com.common.api.response;

import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProfileRoleAccess")
@TypeAlias(value = "ProfileRoleAccess")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileRoleAccess {

	@ApiModelProperty(value = "code", required = true)
	@JsonProperty(value = "code")
	private String code = "";

	@ApiModelProperty(value = "message", required = true)
	@JsonProperty(value = "message")
	private String message = "";

	public ProfileRoleAccess() {
	}

	public ProfileRoleAccess(String code, String message) {
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
		return "SessionAndRoleAccess [code=" + code + ", message=" + message + "]";
	}

}
