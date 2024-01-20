package com.common.api.model;

import java.util.Date;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.UserSessionField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "userSession")
@TypeAlias(value = "userSession")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "userSecreyKey", "userId", "groupId", "divisionId", "moduleId",
				"deviceToken", "deviceUniqueId", "appVersion",
				"remoteAddress", "userAgent", "status",
				"type", "active", "createdBy", "modifiedBy", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class UserSession extends APIFixedConstant {

	@JsonProperty(value = "id")
	@ApiModelProperty(value = "id", required = false)
    private String id = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COMP_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_COMP_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "companyId", required = true)
    @JsonProperty(value = "companyId")
    private String companyId = "";

	@ApiModelProperty(value = "groupId", hidden = true)
    @JsonProperty(value = "groupId")
    private String groupId = "";

	@ApiModelProperty(value = "divisionId", hidden = true)
    @JsonProperty(value = "divisionId")
    private String divisionId = "";

	@ApiModelProperty(value = "moduleId", hidden = true)
    @JsonProperty(value = "moduleId")
    private String moduleId = "";

	@ApiModelProperty(value = "userId", hidden = true)
    @JsonProperty(value = "userId")
    private String userId = "";

	@Size(min = FL_USERNAME_MIN, max = FL_USERNAME_MAX, message = EM_INVALID_USERNAME)
	@Pattern(regexp = RE_USERNAME_CUSTOM, message = EM_INVALID_USERNAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "username", required = true)
    @JsonProperty(value = "username")
    private String username = "";

	@ApiModelProperty(value = "password", required = true)
    @JsonProperty(value  = "password", access = Access.WRITE_ONLY)
    private String password = "";

	@Size(min = FL_MOBILE_PIN_MIN_OPTIONAL, max = FL_MOBILE_PIN_MAX, message = EM_INVALID_MOBILE_PIN)
	@ApiModelProperty(value = "mobilePin", required = true)
    @JsonProperty(value  = "mobilePin", access = Access.WRITE_ONLY)
    private String mobilePin = "";

	@ApiModelProperty(value = "appVersion", required = false)
	@JsonProperty(value = "appVersion")
	private float appVersion = 0;

	@ApiModelProperty(value = "deviceUniqueId", required = false)
    @JsonProperty(value = "deviceUniqueId")
    private String deviceUniqueId = "";

	@ApiModelProperty(value = "deviceToken", required = false)
    @JsonProperty(value = "deviceToken")
    private String deviceToken = "";

	@ApiModelProperty(value = "deviceStatus", required = false)
    @JsonProperty(value = "deviceStatus")
    private String deviceStatus = "";

	@Valid
	@ApiModelProperty(value = "userSessionField")
    @JsonProperty(value = "userSessionField")
    private UserSessionField userSessionField = new UserSessionField();

	@Size(min = FL_CATEGORY_MIN, max = FL_CATEGORY_MAX, message = EM_INVALID_CATEGORY)
	@ApiModelProperty(value = "category", required = true)
    @JsonProperty(value = "category")
    private String category = "";

	@ApiModelProperty(value = "remoteAddress", hidden = true)
    @JsonProperty(value = "remoteAddress")
    private String remoteAddress = "";

	@ApiModelProperty(value = "userAgent", hidden = true)
    @JsonProperty(value = "userAgent")
    private String userAgent = "";

	@ApiModelProperty(value = "status", hidden = true)
    @JsonProperty(value = "status")
    private String status = "";

	@ApiModelProperty(value = "type", hidden = true)
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
    @JsonProperty(value = "userFirstName")
    private String userFirstName = "";

	@Transient
    @JsonProperty(value = "payloadVersion")
    private String payloadVersion = "0";

	@Transient
    @JsonProperty(value = "userSecreyKey")
    private String userSecreyKey = "";

	public UserSession() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonIgnore
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@JsonIgnore
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@JsonIgnore
	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	@JsonIgnore
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@JsonIgnore
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getMobilePin() {
		return mobilePin;
	}

	public void setMobilePin(String mobilePin) {
		this.mobilePin = mobilePin;
	}

	@JsonIgnore
	public float getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(float appVersion) {
		this.appVersion = appVersion;
	}

	@JsonIgnore
	public String getDeviceUniqueId() {
		return deviceUniqueId;
	}

	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}

	@JsonIgnore
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@JsonIgnore
	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public UserSessionField getUserSessionField() {
		return userSessionField;
	}

	public void setUserSessionField(UserSessionField userSessionField) {
		this.userSessionField = userSessionField;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@JsonIgnore
	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	@JsonIgnore
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
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

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getPayloadVersion() {
		return payloadVersion;
	}

	public void setPayloadVersion(String payloadVersion) {
		this.payloadVersion = payloadVersion;
	}

	public String getUserSecreyKey() {
		return userSecreyKey;
	}

	public void setUserSecreyKey(String userSecreyKey) {
		this.userSecreyKey = userSecreyKey;
	}

	@Override
	public String toString() {
		return "UserSession [id=" + id + ", companyId=" + companyId + ", groupId=" + groupId + ", divisionId="
				+ divisionId + ", moduleId=" + moduleId + ", userId=" + userId + ", username=" + username
				+ ", password=" + password + ", mobilePin=" + mobilePin + ", appVersion=" + appVersion
				+ ", deviceUniqueId=" + deviceUniqueId + ", deviceToken=" + deviceToken + ", deviceStatus="
				+ deviceStatus + ", userSessionField=" + userSessionField + ", category=" + category
				+ ", remoteAddress=" + remoteAddress + ", userAgent=" + userAgent + ", status=" + status + ", type="
				+ type + ", active=" + active + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", userFirstName=" + userFirstName
				+ ", payloadVersion=" + payloadVersion + ", userSecreyKey=" + userSecreyKey + "]";
	}

}
