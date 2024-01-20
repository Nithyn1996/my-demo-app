package com.common.api.model;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "userDevice")
@TypeAlias(value = "userDevice")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "userId", "active", "createdBy", "modifiedBy", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class UserDevice extends APIFixedConstant {

	@Size(min = FL_AUTO_GEN_ID_MIN, max = FL_AUTO_GEN_ID_MAX, message = EM_INVALID_ID)
	@ApiModelProperty(value = "id", hidden = false)
    @JsonProperty(value = "id")
    private String Id = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COMP_ID)
	@ApiModelProperty(value = "companyId", required = true)
    @JsonProperty(value = "companyId")
    private String companyId = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_DIVI_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_DIVI_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "divisionId", required = true)
    @JsonProperty(value = "divisionId")
    private String divisionId = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_MODU_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_MODU_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "moduleId", required = true)
    @JsonProperty(value = "moduleId")
    private String moduleId = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_USER_ID)
	@ApiModelProperty(value = "userId", hidden = true)
    @JsonProperty(value = "userId")
    private String userId = "";

	@Size(min = FL_DEVICE_FIELD_MIN_OPTIONAL, max = FL_DEVICE_FIELD_MAX, message = EM_INVALID_D_ORDER_ID)
	@Pattern(regexp = RE_DEVICE_FIELD, message = EM_INVALID_D_ORDER_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "deviceOrderId", required = true)
    @JsonProperty(value = "deviceOrderId")
    private String deviceOrderId = "";

	@Size(min = FL_DEVICE_FIELD_MIN_OPTIONAL, max = FL_DEVICE_FIELD_MAX, message = EM_INVALID_D_UNIQUE_ID)
	@Pattern(regexp = RE_DEVICE_FIELD, message = EM_INVALID_D_UNIQUE_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "deviceUniqueId", required = true)
    @JsonProperty(value = "deviceUniqueId")
    private String deviceUniqueId = "";

	@Size(min = FL_DEVICE_FIELD_MIN_OPTIONAL, max = FL_DEVICE_FIELD_MAX, message = EM_INVALID_D_VERSION_NO)
	@Pattern(regexp = RE_DEVICE_FIELD, message = EM_INVALID_D_VERSION_NO + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "deviceVersionNumber", required = true)
    @JsonProperty(value = "deviceVersionNumber")
    private String deviceVersionNumber = "";

	@Size(min = FL_DEVICE_FIELD_MIN_OPTIONAL, max = FL_DEVICE_FIELD_MAX, message = EM_INVALID_D_MODEL_NAME)
	@Pattern(regexp = RE_DEVICE_FIELD, message = EM_INVALID_D_MODEL_NAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "deviceModelName", required = true)
    @JsonProperty(value = "deviceModelName")
    private String deviceModelName = "";

	@Size(min = FL_TYPE_MIN, max = FL_TYPE_MAX, message = EM_INVALID_D_TYPE)
	@Pattern(regexp = RE_TYPE, message = EM_INVALID_D_TYPE + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "deviceType", required = true)
    @JsonProperty(value = "deviceType")
    private String deviceType = "";

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

	public UserDevice() {
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

	public String getDeviceVersionNumber() {
		return deviceVersionNumber;
	}

	public void setDeviceVersionNumber(String deviceVersionNumber) {
		this.deviceVersionNumber = deviceVersionNumber;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
		return "UserDevice [Id=" + Id + ", companyId=" + companyId + ", divisionId=" + divisionId + ", moduleId="
				+ moduleId + ", userId=" + userId + ", deviceOrderId=" + deviceOrderId + ", deviceUniqueId="
				+ deviceUniqueId + ", deviceVersionNumber=" + deviceVersionNumber + ", deviceModelName="
				+ deviceModelName + ", deviceType=" + deviceType + ", status=" + status + ", type=" + type + ", active="
				+ active + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdAt=" + createdAt
				+ ", modifiedAt=" + modifiedAt + "]";
	}

}
