package com.common.api.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.ModulePrefAppBootSetting;
import com.common.api.model.field.ModulePreferenceField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "modulePreference")
@TypeAlias(value = "modulePreference")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { //"category", "systemCode", "modulePreferenceField",
				"active", "createdBy", "modifiedBy", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class ModulePreference extends APIFixedConstant {

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

	@Size(min = FL_CODE_MIN_OPTIONAL, max = FL_CODE_MAX, message = EM_INVALID_STABLE_VERSION)
 	@ApiModelProperty(value = "minimumStableVersion", required = true)
    @JsonProperty(value = "minimumStableVersion")
    private String minimumStableVersion = "";

	@Size(min = FL_CODE_MIN_OPTIONAL, max = FL_CODE_MAX, message = EM_INVALID_STABLE_VERSION)
 	@ApiModelProperty(value = "currentStableVersion", required = true)
    @JsonProperty(value = "currentStableVersion")
    private String currentStableVersion = "";

	@Size(min = FL_SYSTEM_CODE_MIN, max = FL_SYSTEM_CODE_MAX, message = EM_INVALID_SYSTEM_CODE)
	@Pattern(regexp = RE_SYSTEM_CODE, message = EM_INVALID_SYSTEM_CODE + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "systemCode", required = true)
    @JsonProperty(value = "systemCode")
    private String systemCode = "";

	@Valid
	@ApiModelProperty(value = "modulePreferenceField", required = false)
    @JsonProperty(value = "modulePreferenceField")
    private ModulePreferenceField modulePreferenceField = new ModulePreferenceField();

	@Valid
	@ApiModelProperty(value = "modulePrefAppBootSetting", required = false)
    @JsonProperty(value = "modulePrefAppBootSetting")
    private ModulePrefAppBootSetting modulePrefAppBootSetting = new ModulePrefAppBootSetting();

	@Size(min = FL_CATEGORY_MIN_OPTIONAL, max = FL_CATEGORY_MAX, message = EM_INVALID_CATEGORY)
	@ApiModelProperty(value = "category", required = true)
    @JsonProperty(value = "category")
    private String category = "";

	@Size(min = FL_STATUS_MIN, max = FL_STATUS_MAX, message = EM_INVALID_NOTIFY_STATUS)
	@Pattern(regexp = RE_STATUS, message = EM_INVALID_NOTIFY_STATUS + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "notificationStatus", required = true)
    @JsonProperty(value = "notificationStatus")
    private String notificationStatus = "";

	@ApiModelProperty(value = "notificationStatusSuccessAt", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "notificationStatusSuccessAt")
    private Date notificationStatusSuccessAt = null;

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

	public ModulePreference() {
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

	public String getMinimumStableVersion() {
		return minimumStableVersion;
	}

	public void setMinimumStableVersion(String minimumStableVersion) {
		this.minimumStableVersion = minimumStableVersion;
	}

	public String getCurrentStableVersion() {
		return currentStableVersion;
	}

	public void setCurrentStableVersion(String currentStableVersion) {
		this.currentStableVersion = currentStableVersion;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public ModulePreferenceField getModulePreferenceField() {
		return modulePreferenceField;
	}

	public void setModulePreferenceField(ModulePreferenceField modulePreferenceField) {
		this.modulePreferenceField = modulePreferenceField;
	}

	public ModulePrefAppBootSetting getModulePrefAppBootSetting() {
		return modulePrefAppBootSetting;
	}

	public void setModulePrefAppBootSetting(ModulePrefAppBootSetting modulePrefAppBootSetting) {
		this.modulePrefAppBootSetting = modulePrefAppBootSetting;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public Date getNotificationStatusSuccessAt() {
		return notificationStatusSuccessAt;
	}

	public void setNotificationStatusSuccessAt(Date notificationStatusSuccessAt) {
		this.notificationStatusSuccessAt = notificationStatusSuccessAt;
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
		return "ModulePreference [Id=" + Id + ", companyId=" + companyId + ", groupId=" + groupId + ", divisionId="
				+ divisionId + ", moduleId=" + moduleId + ", name=" + name + ", code=" + code
				+ ", minimumStableVersion=" + minimumStableVersion + ", currentStableVersion=" + currentStableVersion
				+ ", systemCode=" + systemCode + ", modulePreferenceField=" + modulePreferenceField
				+ ", modulePrefAppBootSetting=" + modulePrefAppBootSetting + ", category=" + category
				+ ", notificationStatus=" + notificationStatus + ", notificationStatusSuccessAt="
				+ notificationStatusSuccessAt + ", status=" + status + ", type=" + type + ", active=" + active
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdAt=" + createdAt
				+ ", modifiedAt=" + modifiedAt + "]";
	}

}
