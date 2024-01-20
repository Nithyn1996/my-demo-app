package com.common.api.model;

import java.sql.Time;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.UserPrefAppBootSetting;
import com.common.api.model.field.UserPreferenceField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "userPreference")
@TypeAlias(value = "userPreference")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "active", "createdBy", "modifiedBy", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class UserPreference extends APIFixedConstant {

	@Size(min = FL_AUTO_GEN_ID_MIN, max = FL_AUTO_GEN_ID_MAX, message = EM_INVALID_ID)
	@ApiModelProperty(value = "id", required = false)
    @JsonProperty(value = "id")
    private String Id = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COMP_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_COMP_ID + EM_INVALID_PATTERN)
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

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_USER_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_USER_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "userId", required = true)
    @JsonProperty(value = "userId")
    private String userId = "";

	@Size(min = FL_NAME_MIN, max = FL_NAME_MAX, message = EM_INVALID_NAME)
	@Pattern(regexp = RE_NAME, message = EM_INVALID_NAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "name", required = true)
    @JsonProperty(value = "name")
    private String name = "";

	@Size(min = FL_CODE_MIN_OPTIONAL, max = FL_CODE_MAX, message = EM_INVALID_CODE)
 	@ApiModelProperty(value = "code", required = true)
    @JsonProperty(value = "code")
    private String code = "";

	@Valid
	@ApiModelProperty(value = "userPreferenceField", required = false)
    @JsonProperty(value = "userPreferenceField")
    private UserPreferenceField userPreferenceField = new UserPreferenceField();

	@Valid
	@ApiModelProperty(value = "userPrefAppBootSetting", required = false)
    @JsonProperty(value = "userPrefAppBootSetting")
    private UserPrefAppBootSetting userPrefAppBootSetting = new UserPrefAppBootSetting();

	@ApiModelProperty(value = "startTime", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_TIME_FORMAT)
    @JsonProperty(value = "startTime")
    private Time startTime = new Time(0);

	@ApiModelProperty(value = "endTime", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_TIME_FORMAT)
    @JsonProperty(value = "endTime")
    private Time endTime = new Time(0);

	@Size(min = FL_SUB_CATEGORY_MIN_OPTIONAL, max = FL_SUB_CATEGORY_MAX, message = EM_INVALID_SUB_CATEGORY)
	@ApiModelProperty(value = "subCategory", required = true)
    @JsonProperty(value = "subCategory")
    private String subCategory = "";

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

	@ApiModelProperty(value = "createdAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "createdAt")
    private Date createdAt = null;

	@ApiModelProperty(value = "modifiedAt", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "modifiedAt")
    private Date modifiedAt = null;

	public UserPreference() {
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

	public UserPreferenceField getUserPreferenceField() {
		return userPreferenceField;
	}

	public void setUserPreferenceField(UserPreferenceField userPreferenceField) {
		this.userPreferenceField = userPreferenceField;
	}

	public UserPrefAppBootSetting getUserPrefAppBootSetting() {
		return userPrefAppBootSetting;
	}

	public void setUserPrefAppBootSetting(UserPrefAppBootSetting userPrefAppBootSetting) {
		this.userPrefAppBootSetting = userPrefAppBootSetting;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
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
		return "UserPreference [Id=" + Id + ", companyId=" + companyId + ", divisionId=" + divisionId + ", moduleId="
				+ moduleId + ", userId=" + userId + ", name=" + name + ", code=" + code + ", userPreferenceField="
				+ userPreferenceField + ", userPrefAppBootSetting=" + userPrefAppBootSetting + ", startTime="
				+ startTime + ", endTime=" + endTime + ", subCategory=" + subCategory + ", category=" + category
				+ ", status=" + status + ", type=" + type + ", active=" + active + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + "]";
	}

}
