package com.common.api.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.PortionField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "portion")
@TypeAlias(value = "portion")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "active", "createdBy", "modifiedBy", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class Portion extends APIFixedConstant {

	@Size(min = FL_AUTO_GEN_ID_MIN, max = FL_AUTO_GEN_ID_MAX, message = EM_INVALID_ID)
	@ApiModelProperty(value = "id", required = false)
    @JsonProperty(value = "id")
    private String id = "";

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

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_PROPERTY_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_PROPERTY_ID + EM_INVALID_PATTERN)
 	@ApiModelProperty(value = "propertyId", required = true)
    @JsonProperty(value = "propertyId")
    private String propertyId = "";

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_SECTION_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_SECTION_ID + EM_INVALID_PATTERN)
 	@ApiModelProperty(value = "sectionId", required = true)
    @JsonProperty(value = "sectionId")
    private String sectionId = "";

	@Size(min = FL_NAME_MIN, max = FL_NAME_MAX, message = EM_INVALID_NAME)
	@Pattern(regexp = RE_NAME, message = EM_INVALID_NAME + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "name", required = true)
    @JsonProperty(value = "name")
    private String name = "";

	@Valid
	@ApiModelProperty(value = "portionField", required = false)
    @JsonProperty(value = "portionField")
    private PortionField portionField = new PortionField();

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

	public Portion() {
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

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PortionField getPortionField() {
		return portionField;
	}

	public void setPortionField(PortionField portionField) {
		this.portionField = portionField;
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
		return "Portion [id=" + id + ", companyId=" + companyId + ", groupId=" + groupId + ", divisionId=" + divisionId
				+ ", moduleId=" + moduleId + ", userId=" + userId + ", propertyId=" + propertyId + ", sectionId="
				+ sectionId + ", name=" + name + ", portionField=" + portionField + ", category=" + category
				+ ", status=" + status + ", type=" + type + ", active=" + active + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + "]";
	}

}
