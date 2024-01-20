package com.common.api.model;

import java.util.Date;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.UserVerificationField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "userVerification")
@TypeAlias(value = "userVerification")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "userId", "active", "createdBy", "modifiedBy", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class UserVerification extends APIFixedConstant {

	@Size(min = FL_AUTO_GEN_ID_MIN, max = FL_AUTO_GEN_ID_MAX, message = EM_INVALID_ID)
	@ApiModelProperty(value = "id", hidden = false)
    @JsonProperty(value = "id")
    private String Id = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COMP_ID)
	@ApiModelProperty(value = "companyId", required = true)
    @JsonProperty(value = "companyId")
    private String companyId = "";

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_USER_ID)
	@ApiModelProperty(value = "userId", hidden = true)
    @JsonProperty(value = "userId")
    private String userId = "";

	@Size(min = FL_USERNAME_MIN, max = FL_USERNAME_MAX, message = EM_INVALID_USERNAME)
	@Pattern(regexp = RE_USERNAME_CUSTOM, message = EM_INVALID_USERNAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "username", required = true)
    @JsonProperty(value = "username")
    private String username = "";

	@Size(min = FL_VERIFICATION_CODE_MIN_OPTIONAL, max = FL_VERIFICATION_CODE_MAX, message = EM_INVALID_CODE)
	@ApiModelProperty(value = "verificationCode", required = false)
    @JsonProperty(value = "verificationCode")
    private String verificationCode = "";

	@Valid
	@ApiModelProperty(value = "userVerificationField", required = false)
    @JsonProperty(value = "userVerificationField")
    private UserVerificationField userVerificationField = new UserVerificationField();

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

	public UserVerification() {
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	@JsonIgnore
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public UserVerificationField getUserVerificationField() {
		return userVerificationField;
	}

	public void setUserVerificationField(UserVerificationField userVerificationField) {
		this.userVerificationField = userVerificationField;
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
		return "UserVerification [Id=" + Id + ", companyId=" + companyId + ", userId=" + userId + ", username="
				+ username + ", verificationCode=" + verificationCode + ", userVerificationField="
				+ userVerificationField + ", status=" + status + ", type=" + type + ", active=" + active
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdAt=" + createdAt
				+ ", modifiedAt=" + modifiedAt + ", payloadVersion=" + payloadVersion + "]";
	}

}
