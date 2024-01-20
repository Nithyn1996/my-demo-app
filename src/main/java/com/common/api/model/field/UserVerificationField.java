package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVerificationField extends APIFixedConstant {

	@ApiModelProperty(value = "password", required = false)
    @JsonProperty(value  = "password", access = Access.WRITE_ONLY)
    private String password = "";

	@ApiModelProperty(value = "mobilePin", required = false)
    @JsonProperty(value  = "mobilePin", access = Access.WRITE_ONLY)
    private String mobilePin = "";

	public UserVerificationField() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePin() {
		return mobilePin;
	}

	public void setMobilePin(String mobilePin) {
		this.mobilePin = mobilePin;
	}

	@Override
	public String toString() {
		return "UserVerificationField [password=" + password + ", mobilePin=" + mobilePin + "]";
	}

}
