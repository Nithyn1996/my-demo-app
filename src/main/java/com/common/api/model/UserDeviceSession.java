package com.common.api.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.UserDeviceSessionField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "userDeviceSession")
@TypeAlias(value = "userDeviceSession")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, allowGetters = false, allowSetters = false)
public class UserDeviceSession extends APIFixedConstant {

	@JsonProperty(value = "id")
	@ApiModelProperty(value = "id", required = false)
    private String id = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COMP_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_COMP_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "companyId", required = true)
    @JsonProperty(value = "companyId")
    private String companyId = "";

	@ApiModelProperty(value = "userId", hidden = true)
    @JsonProperty(value = "userId")
    private String userId = "";

	@ApiModelProperty(value = "deviceOrderId", required = true)
    @JsonProperty(value = "deviceOrderId")
    private String deviceOrderId = "";

	@ApiModelProperty(value = "deviceUniqueId", required = true)
    @JsonProperty(value = "deviceUniqueId")
    private String deviceUniqueId = "";

	@ApiModelProperty(value = "deviceType", required = true)
    @JsonProperty(value = "deviceType")
    private String deviceType = "";

	@Valid
	@ApiModelProperty(value = "userDeviceSessionField")
    @JsonProperty(value = "userDeviceSessionField")
    private UserDeviceSessionField userDeviceSessionField = new UserDeviceSessionField();

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

	public UserDeviceSession() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceOrderId() {
		return deviceOrderId;
	}

	public void setDeviceOrderId(String deviceOrderId) {
		this.deviceOrderId = deviceOrderId;
	}

	public String getDeviceUniqueId() {
		return deviceUniqueId;
	}

	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public UserDeviceSessionField getUserDeviceSessionField() {
		return userDeviceSessionField;
	}

	public void setUserDeviceSessionField(UserDeviceSessionField userDeviceSessionField) {
		this.userDeviceSessionField = userDeviceSessionField;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

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

	@Override
	public String toString() {
		return "UserDeviceSession [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", deviceOrderId="
				+ deviceOrderId + ", deviceUniqueId=" + deviceUniqueId + ", deviceType=" + deviceType
				+ ", userDeviceSessionField=" + userDeviceSessionField + ", remoteAddress=" + remoteAddress
				+ ", userAgent=" + userAgent + ", status=" + status + ", type=" + type + ", active=" + active
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdAt=" + createdAt
				+ ", modifiedAt=" + modifiedAt + "]";
	}

}
