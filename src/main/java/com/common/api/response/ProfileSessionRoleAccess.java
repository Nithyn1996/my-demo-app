package com.common.api.response;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.model.UserSession;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProfileSessionRoleAccess")
@TypeAlias(value = "ProfileSessionRoleAccess")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileSessionRoleAccess {

	@ApiModelProperty(value = "code", required = true)
	@JsonProperty(value = "code")
	private String code = "";

	@ApiModelProperty(value = "message", required = true)
	@JsonProperty(value = "message")
	private String message = "";

	@ApiModelProperty(value = "userId", required = true)
	@JsonProperty(value = "userId")
	private String userId = "";

	@ApiModelProperty(value = "userSession", required = true)
	@JsonProperty(value = "userSession")
	private UserSession userSession = new UserSession();

	public ProfileSessionRoleAccess() {
	}

	public ProfileSessionRoleAccess(String code, String message, String userId) {
		this.code = code;
		this.message = message;
		this.userId = userId;
	}


	public ProfileSessionRoleAccess(String code, String message, String userId, UserSession userSession) {
		this.code = code;
		this.message = message;
		this.userId = userId;
		this.userSession = userSession;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	@Override
	public String toString() {
		return "ProfileSessionRoleAccess [code=" + code + ", message=" + message + ", userId=" + userId
				+ ", userSession=" + userSession + "]";
	}

}
