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

@ApiModel(value = "userLastActivity")
@TypeAlias(value = "userLastActivity")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "active", "createdBy", "modifiedBy", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class UserLastActivity extends APIFixedConstant {

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

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_USER_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_USER_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "userId", required = true)
    @JsonProperty(value = "userId")
    private String userId = "";

	@ApiModelProperty(value = "passwordChangedAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "passwordChangedAt")
    private Date passwordChangedAt = null;

	@ApiModelProperty(value = "sessionWebAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "sessionWebAt")
    private Date sessionWebAt = null;

	@ApiModelProperty(value = "sessionIosAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "sessionIosAt")
    private Date sessionIosAt = null;

	@ApiModelProperty(value = "sessionAndroidAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "sessionAndroidAt")
    private Date sessionAndroidAt = null;

	@ApiModelProperty(value = "activityWebAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "activityWebAt")
    private Date activityWebAt = null;

	@ApiModelProperty(value = "activityIosAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "activityIosAt")
    private Date activityIosAt = null;

	@ApiModelProperty(value = "activityAndroidAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "activityAndroidAt")
    private Date activityAndroidAt = null;

	@ApiModelProperty(value = "noActivityPushWebAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "noActivityPushWebAt")
    private Date noActivityPushWebAt = null;

	@ApiModelProperty(value = "noActivityPushIosAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "noActivityPushIosAt")
    private Date noActivityPushIosAt = null;

	@ApiModelProperty(value = "noActivityPushAndroidAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "noActivityPushAndroidAt")
    private Date noActivityPushAndroidAt = null;

	@ApiModelProperty(value = "appUpdatePushIosAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "appUpdatePushIosAt")
    private Date appUpdatePushIosAt = null;

	@ApiModelProperty(value = "appUpdatePushAndroidAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "appUpdatePushAndroidAt")
    private Date appUpdatePushAndroidAt = null;

	@ApiModelProperty(value = "mapUpdatePushIosAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "mapUpdatePushIosAt")
    private Date mapUpdatePushIosAt = null;

	@ApiModelProperty(value = "mapUpdatePushAndroidAt", hidden = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "mapUpdatePushAndroidAt")
    private Date mapUpdatePushAndroidAt = null;

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

	public UserLastActivity() {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getPasswordChangedAt() {
		return passwordChangedAt;
	}

	public void setPasswordChangedAt(Date passwordChangedAt) {
		this.passwordChangedAt = passwordChangedAt;
	}

	public Date getSessionWebAt() {
		return sessionWebAt;
	}

	public void setSessionWebAt(Date sessionWebAt) {
		this.sessionWebAt = sessionWebAt;
	}

	public Date getSessionIosAt() {
		return sessionIosAt;
	}

	public void setSessionIosAt(Date sessionIosAt) {
		this.sessionIosAt = sessionIosAt;
	}

	public Date getSessionAndroidAt() {
		return sessionAndroidAt;
	}

	public void setSessionAndroidAt(Date sessionAndroidAt) {
		this.sessionAndroidAt = sessionAndroidAt;
	}

	public Date getActivityWebAt() {
		return activityWebAt;
	}

	public void setActivityWebAt(Date activityWebAt) {
		this.activityWebAt = activityWebAt;
	}

	public Date getActivityIosAt() {
		return activityIosAt;
	}

	public void setActivityIosAt(Date activityIosAt) {
		this.activityIosAt = activityIosAt;
	}

	public Date getActivityAndroidAt() {
		return activityAndroidAt;
	}

	public void setActivityAndroidAt(Date activityAndroidAt) {
		this.activityAndroidAt = activityAndroidAt;
	}

	public Date getNoActivityPushWebAt() {
		return noActivityPushWebAt;
	}

	public void setNoActivityPushWebAt(Date noActivityPushWebAt) {
		this.noActivityPushWebAt = noActivityPushWebAt;
	}

	public Date getNoActivityPushIosAt() {
		return noActivityPushIosAt;
	}

	public void setNoActivityPushIosAt(Date noActivityPushIosAt) {
		this.noActivityPushIosAt = noActivityPushIosAt;
	}

	public Date getNoActivityPushAndroidAt() {
		return noActivityPushAndroidAt;
	}

	public void setNoActivityPushAndroidAt(Date noActivityPushAndroidAt) {
		this.noActivityPushAndroidAt = noActivityPushAndroidAt;
	}

	public Date getAppUpdatePushIosAt() {
		return appUpdatePushIosAt;
	}

	public void setAppUpdatePushIosAt(Date appUpdatePushIosAt) {
		this.appUpdatePushIosAt = appUpdatePushIosAt;
	}

	public Date getAppUpdatePushAndroidAt() {
		return appUpdatePushAndroidAt;
	}

	public void setAppUpdatePushAndroidAt(Date appUpdatePushAndroidAt) {
		this.appUpdatePushAndroidAt = appUpdatePushAndroidAt;
	}

	public Date getMapUpdatePushIosAt() {
		return mapUpdatePushIosAt;
	}

	public void setMapUpdatePushIosAt(Date mapUpdatePushIosAt) {
		this.mapUpdatePushIosAt = mapUpdatePushIosAt;
	}

	public Date getMapUpdatePushAndroidAt() {
		return mapUpdatePushAndroidAt;
	}

	public void setMapUpdatePushAndroidAt(Date mapUpdatePushAndroidAt) {
		this.mapUpdatePushAndroidAt = mapUpdatePushAndroidAt;
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
		return "UserLastActivity [Id=" + Id + ", companyId=" + companyId + ", groupId=" + groupId + ", divisionId="
				+ divisionId + ", moduleId=" + moduleId + ", userId=" + userId + ", passwordChangedAt="
				+ passwordChangedAt + ", sessionWebAt=" + sessionWebAt + ", sessionIosAt=" + sessionIosAt
				+ ", sessionAndroidAt=" + sessionAndroidAt + ", activityWebAt=" + activityWebAt + ", activityIosAt="
				+ activityIosAt + ", activityAndroidAt=" + activityAndroidAt + ", noActivityPushWebAt="
				+ noActivityPushWebAt + ", noActivityPushIosAt=" + noActivityPushIosAt + ", noActivityPushAndroidAt="
				+ noActivityPushAndroidAt + ", appUpdatePushIosAt=" + appUpdatePushIosAt + ", appUpdatePushAndroidAt="
				+ appUpdatePushAndroidAt + ", mapUpdatePushIosAt=" + mapUpdatePushIosAt + ", mapUpdatePushAndroidAt="
				+ mapUpdatePushAndroidAt + ", type=" + type + ", active=" + active + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + "]";
	}

}
