package com.common.api.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.DivisionField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "division")
@TypeAlias(value = "division")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "active", "createdBy", "modifiedBy", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class Division extends APIFixedConstant {

	@Size(min = FL_AUTO_GEN_ID_MIN, max = FL_AUTO_GEN_ID_MAX, message = EM_INVALID_ID)
	@ApiModelProperty(value = "id", required = false)
    @JsonProperty(value = "id")
    private String Id = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COMP_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_COMP_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "companyId", required = true)
    @JsonProperty(value = "companyId")
    private String companyId = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_GROUP_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_GROUP_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "groupId", required = true)
    @JsonProperty(value = "groupId")
    private String groupId = "";

	@Size(min = FL_NAME_MIN, max = FL_NAME_MAX, message = EM_INVALID_NAME)
	@Pattern(regexp = RE_NAME, message = EM_INVALID_NAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "name", required = true)
    @JsonProperty(value = "name")
    private String name = "";

	@Size(min = FL_CODE_MIN, max = FL_CODE_MAX, message = EM_INVALID_CODE)
	@Pattern(regexp = RE_CODE, message = EM_INVALID_CODE + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "code", required = true)
    @JsonProperty(value = "code")
    private String code = "";

	@Valid
	@ApiModelProperty(value = "divisionField", required = false)
    @JsonProperty(value = "divisionField")
    private DivisionField divisionField = new DivisionField();

	@ApiModelProperty(value = "deviceAutoStartMode", required = true)
    @JsonProperty(value = "deviceAutoStartMode")
    private String deviceAutoStartMode = "";

	@ApiModelProperty(value = "deviceDetailTransferMode", required = true)
    @JsonProperty(value = "deviceDetailTransferMode")
    private String deviceDetailTransferMode = "";

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

	public Division() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DivisionField getDivisionField() {
		return divisionField;
	}

	public void setDivisionField(DivisionField divisionField) {
		this.divisionField = divisionField;
	}

	public String getDeviceAutoStartMode() {
		return deviceAutoStartMode;
	}

	public void setDeviceAutoStartMode(String deviceAutoStartMode) {
		this.deviceAutoStartMode = deviceAutoStartMode;
	}

	public String getDeviceDetailTransferMode() {
		return deviceDetailTransferMode;
	}

	public void setDeviceDetailTransferMode(String deviceDetailTransferMode) {
		this.deviceDetailTransferMode = deviceDetailTransferMode;
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

	@Override
	public String toString() {
		return "Division [Id=" + Id + ", companyId=" + companyId + ", groupId=" + groupId + ", name=" + name + ", code="
				+ code + ", divisionField=" + divisionField + ", deviceAutoStartMode=" + deviceAutoStartMode
				+ ", deviceDetailTransferMode=" + deviceDetailTransferMode + ", category=" + category + ", status="
				+ status + ", type=" + type + ", active=" + active + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + "]";
	}

}
