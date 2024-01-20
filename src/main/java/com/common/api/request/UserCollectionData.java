package com.common.api.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCollectionData extends APIFixedConstant {

	@Size(min = FL_USERNAME_MIN, max = FL_USERNAME_MAX, message = EM_INVALID_USERNAME)
	@Pattern(regexp = RE_USERNAME, message = EM_INVALID_USERNAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "username", required = true)
    @JsonProperty(value = "username")
 	private String username = "";

	@ApiModelProperty(value = "password", required = false)
	@Size(min = FL_PASSWORD_MIN, max = FL_PASSWORD_MAX, message = EM_INVALID_PASSWORD)
    @JsonProperty(value  = "password", access = Access.WRITE_ONLY)
    private String password = "";

	@Size(min = FL_CATEGORY_MIN_OPTIONAL, max = FL_CATEGORY_MAX, message = EM_INVALID_CATEGORY)
	@ApiModelProperty(value = "category", required = true)
    @JsonProperty(value = "category")
    private String category = "";

	@Size(min = FL_TYPE_MIN, max = FL_TYPE_MAX, message = EM_INVALID_USERNAME_TYPE)
	@Pattern(regexp = RE_TYPE, message = EM_INVALID_USERNAME_TYPE + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "usernameType", required = true)
    @JsonProperty(value = "usernameType")
    private String usernameType = "";

	@Size(min = FL_TYPE_MIN, max = FL_TYPE_MAX, message = EM_INVALID_D_TYPE)
	@Pattern(regexp = RE_TYPE, message = EM_INVALID_D_TYPE + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "deviceType", required = true)
    @JsonProperty(value = "deviceType")
    private String deviceType = "";

	public UserCollectionData() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUsernameType() {
		return usernameType;
	}

	public void setUsernameType(String usernameType) {
		this.usernameType = usernameType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		return "UserCollectionData [username=" + username + ", password=" + password + ", category=" + category
				+ ", usernameType=" + usernameType + ", deviceType=" + deviceType + "]";
	}

}
