package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserField extends APIFixedConstant {

 	@ApiModelProperty(value = "age", required = false)
    @JsonProperty(value = "age")
    private String age = "";

 	@ApiModelProperty(value = "emergencyContactNumber", required = false)
    @JsonProperty(value = "emergencyContactNumber")
    private String emergencyContactNumber = "";

 	@ApiModelProperty(value = "drivingLicenseIssueDate", required = false)
    @JsonProperty(value = "drivingLicenseIssueDate")
    private String drivingLicenseIssueDate = "";

	public UserField() {
	}

	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}

	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDrivingLicenseIssueDate() {
		return drivingLicenseIssueDate;
	}

	public void setDrivingLicenseIssueDate(String drivingLicenseIssueDate) {
		this.drivingLicenseIssueDate = drivingLicenseIssueDate;
	}

	@Override
	public String toString() {
		return "UserField [emergencyContactNumber=" + emergencyContactNumber + ", age="
				+ age + ", drivingLicenseIssueDate=" + drivingLicenseIssueDate + "]";
	}

}
