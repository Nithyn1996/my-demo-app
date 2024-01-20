package com.common.api.model;

import java.util.Date;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.UserField;
import com.common.api.model.field.UserSessionField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "user")
@TypeAlias(value = "user")
@JsonIgnoreProperties (
	ignoreUnknown = true
	, value = { "active", "createdBy", "modifiedBy", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class User extends APIFixedConstant {

	@Size(min = FL_AUTO_GEN_ID_MIN, max = FL_AUTO_GEN_ID_MAX, message = EM_INVALID_ID)
	@ApiModelProperty(value = "id", required = false)
    @JsonProperty(value = "id")
    private String Id = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COMP_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_COMP_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "companyId", required = true)
    @JsonProperty(value = "companyId")
    private String companyId = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_GROUP_ID)
 	@ApiModelProperty(value = "groupId", required = true)
    @JsonProperty(value = "groupId")
    private String groupId = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_DIVI_ID)
	@ApiModelProperty(value = "divisionId", required = true)
    @JsonProperty(value = "divisionId")
    private String divisionId = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_MODU_ID)
	@ApiModelProperty(value = "moduleId", required = true)
    @JsonProperty(value = "moduleId")
    private String moduleId = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COUNTRY_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_COUNTRY_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "countryId", required = true)
    @JsonProperty(value = "countryId")
    private String countryId = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_TIME_ZONEY_ID)
	@ApiModelProperty(value = "timeZoneId", required = false)
    @JsonProperty(value = "timeZoneId")
    private String timeZoneId = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_LANGUAGE_ID)
 	@ApiModelProperty(value = "languageId", required = true)
    @JsonProperty(value = "languageId")
    private String languageId = "";

	@Size(min = FL_NAME_MIN, max = FL_NAME_MAX, message = EM_INVALID_NAME)
	@Pattern(regexp = RE_NAME, message = EM_INVALID_NAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "firstName", required = true)
    @JsonProperty(value = "firstName")
    private String firstName = "";

	@Size(min = FL_NAME_MIN_OPTIONAL, max = FL_NAME_MAX, message = EM_INVALID_NAME)
	@ApiModelProperty(value = "lastName", required = false)
    @JsonProperty(value = "lastName")
    private String lastName = "";

	@Size(min = FL_EMAIL_MIN_OPTIONAL, max = FL_EMAIL_MAX, message = EM_INVALID_EMAIL)
	@ApiModelProperty(value = "email", required = false)
    @JsonProperty(value = "email")
    private String email = "";

	@Size(min = FL_EMAIL_MIN_OPTIONAL, max = FL_EMAIL_MAX, message = EM_INVALID_GENDER)
	@ApiModelProperty(value = "gender", required = false)
    @JsonProperty(value = "gender")
    private String gender = "";

	@Size(min = FL_USERNAME_MIN, max = FL_USERNAME_MAX, message = EM_INVALID_USERNAME)
	@Pattern(regexp = RE_USERNAME_CUSTOM, message = EM_INVALID_USERNAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "username", required = true)
    @JsonProperty(value = "username")
    private String username = "";

	@ApiModelProperty(value = "password", required = false)
    @JsonProperty(value  = "password", access = Access.WRITE_ONLY)
    private String password = "";

	@ApiModelProperty(value = "mobilePin", required = true)
    @JsonProperty(value  = "mobilePin", access = Access.WRITE_ONLY)
    private String mobilePin = "";

	@Size(min = FL_YES_NO_MIN, max = FL_YES_NO_MAX, message = EM_INVALID_EMAIL_VERIFIED)
	@Pattern(regexp = RE_YES_NO, message = EM_INVALID_EMAIL_VERIFIED + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "emailVerified", required = true)
    @JsonProperty(value = "emailVerified")
    private String emailVerified = "";

	@Size(min = FL_YES_NO_MIN, max = FL_YES_NO_MAX, message = EM_INVALID_USERNAME_VERIFIED)
	@Pattern(regexp = RE_YES_NO, message = EM_INVALID_USERNAME_VERIFIED + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "usernameVerified", required = true)
    @JsonProperty(value = "usernameVerified")
    private String usernameVerified = "";

	@Valid
	@ApiModelProperty(value = "userField", required = false)
    @JsonProperty(value = "userField")
    private UserField userField = new UserField();

	@Valid
	@Transient
	@ApiModelProperty(value = "userSessionField", required = false)
    @JsonProperty(value = "userSessionField")
    private UserSessionField userSessionField = new UserSessionField();

	@Size(min = FL_IMAGE_PATH_MIN_OPTIONAL, max = FL_IMAGE_PATH_MAX, message = EM_INVALID_IMAGE_PATH)
	@ApiModelProperty(value = "profilePicturePath", required = true)
    @JsonProperty(value = "profilePicturePath")
    private String profilePicturePath = "";

	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_DEVICE_AUTO_START_SUB_MODE)
	@ApiModelProperty(value = "deviceAutoStartSubMode", required = true)
    @JsonProperty(value = "deviceAutoStartSubMode")
    private String deviceAutoStartSubMode = "";

	@Size(min = FL_CATEGORY_MIN_OPTIONAL, max = FL_CATEGORY_MAX, message = EM_INVALID_CATEGORY)
	@ApiModelProperty(value = "nextActivity", required = true)
    @JsonProperty(value = "nextActivity")
    private String nextActivity = "";

	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_USERNAME_TYPE)
	@ApiModelProperty(value = "usernameType", required = true)
    @JsonProperty(value = "usernameType")
    private String usernameType = "";

	@Size(min = FL_CATEGORY_MIN_OPTIONAL, max = FL_CATEGORY_MAX, message = EM_INVALID_CATEGORY)
	@ApiModelProperty(value = "category", required = true)
    @JsonProperty(value = "category")
    private String category = "";

	@Size(min = FL_STATUS_MIN, max = FL_STATUS_MAX, message = EM_INVALID_STATUS)
	@Pattern(regexp = RE_STATUS, message = EM_INVALID_STATUS + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "status", required = true)
    @JsonProperty(value = "status")
    private String status = "";

	@Size(min = FL_TYPE_MIN, max = FL_TYPE_MAX, message = EM_INVALID_TYPE)
	@Pattern(regexp = RE_TYPE, message = EM_INVALID_TYPE + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "type", required = true)
    @JsonProperty(value = "type")
    private String type = "";

	@ApiModelProperty(value = "active", hidden = true)
    @JsonProperty(value = "active")
    private String active = "";

	@ApiModelProperty(value = "createdBy", hidden = true)
    @JsonProperty(value = "createdBy")
    private String createdBy = "";

	@ApiModelProperty(value = "modifiedBy", hidden = true)
    @JsonProperty(value = "modifiedBy")
    private String modifiedBy = "";

	@ApiModelProperty(value = "createdAt", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "createdAt")
    private Date createdAt = null;

	@ApiModelProperty(value = "modifiedAt", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "modifiedAt")
    private Date modifiedAt = null;

	@Transient
    @JsonProperty(value = "payloadVersion")
    private String payloadVersion = "0";

	public User() {
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getMobilePin() {
		return mobilePin;
	}

	public void setMobilePin(String mobilePin) {
		this.mobilePin = mobilePin;
	}

	public String getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(String emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getUsernameVerified() {
		return usernameVerified;
	}

	public void setUsernameVerified(String usernameVerified) {
		this.usernameVerified = usernameVerified;
	}

	public UserField getUserField() {
		return userField;
	}

	public void setUserField(UserField userField) {
		this.userField = userField;
	}

	public UserSessionField getUserSessionField() {
		return userSessionField;
	}

	public void setUserSessionField(UserSessionField userSessionField) {
		this.userSessionField = userSessionField;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getProfilePicturePath() {
		return profilePicturePath;
	}

	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}

	public String getDeviceAutoStartSubMode() {
		return deviceAutoStartSubMode;
	}

	public void setDeviceAutoStartSubMode(String deviceAutoStartSubMode) {
		this.deviceAutoStartSubMode = deviceAutoStartSubMode;
	}

	public String getNextActivity() {
		return nextActivity;
	}

	public void setNextActivity(String nextActivity) {
		this.nextActivity = nextActivity;
	}

	public String getUsernameType() {
		return usernameType;
	}

	public void setUsernameType(String usernameType) {
		this.usernameType = usernameType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getPayloadVersion() {
		return payloadVersion;
	}

	public void setPayloadVersion(String payloadVersion) {
		this.payloadVersion = payloadVersion;
	}

	@Override
	public String toString() {
		return "User [Id=" + Id + ", companyId=" + companyId + ", groupId=" + groupId + ", divisionId=" + divisionId
				+ ", moduleId=" + moduleId + ", countryId=" + countryId + ", timeZoneId=" + timeZoneId + ", languageId="
				+ languageId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", gender="
				+ gender + ", username=" + username + ", password=" + password + ", mobilePin=" + mobilePin
				+ ", emailVerified=" + emailVerified + ", usernameVerified=" + usernameVerified + ", userField="
				+ userField + ", userSessionField=" + userSessionField + ", profilePicturePath=" + profilePicturePath
				+ ", deviceAutoStartSubMode=" + deviceAutoStartSubMode + ", nextActivity=" + nextActivity
				+ ", usernameType=" + usernameType + ", category=" + category + ", status=" + status + ", type=" + type
				+ ", active=" + active + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdAt="
				+ createdAt + ", modifiedAt=" + modifiedAt + ", payloadVersion=" + payloadVersion + "]";
	}

}
