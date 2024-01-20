package com.common.api.model;

import java.util.Date;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "metadata")
@TypeAlias(value = "metadata")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "active", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class Metadata extends APIFixedConstant {

	@ApiModelProperty(value = "id", required = false)
    @JsonProperty(value = "id")
    private String Id = "";

	@ApiModelProperty(value = "moduleName", required = true)
    @JsonProperty(value = "moduleName")
    private String moduleName = "";

	@ApiModelProperty(value = "fieldName", required = true)
    @JsonProperty(value = "fieldName")
    private String fieldName = "";

	@ApiModelProperty(value = "fieldValue", required = true)
    @JsonProperty(value = "fieldValue")
    private String fieldValue = "";

	@ApiModelProperty(value = "displayValue", required = true)
    @JsonProperty(value = "displayValue")
    private String displayValue = "";

	@ApiModelProperty(value = "cardinality", required = true)
    @JsonProperty(value = "cardinality")
    private int cardinality = 0;

	@ApiModelProperty(value = "sessionTypeCreate", required = true)
    @JsonProperty(value = "sessionTypeCreate")
    private String sessionTypeCreate = "";

	@ApiModelProperty(value = "sessionTypeRead", required = true)
    @JsonProperty(value = "sessionTypeRead")
    private String sessionTypeRead = "";

	@ApiModelProperty(value = "sessionTypeUpdate", required = true)
    @JsonProperty(value = "sessionTypeUpdate")
    private String sessionTypeUpdate = "";

	@ApiModelProperty(value = "sessionTypeDelete", required = true)
    @JsonProperty(value = "sessionTypeDelete")
    private String sessionTypeDelete = "";


	@ApiModelProperty(value = "sessionTypeCreateAll", required = true)
    @JsonProperty(value = "sessionTypeCreateAll")
    private String sessionTypeCreateAll = "";

	@ApiModelProperty(value = "sessionTypeReadAll", required = true)
    @JsonProperty(value = "sessionTypeReadAll")
    private String sessionTypeReadAll = "";

	@ApiModelProperty(value = "sessionTypeUpdateAll", required = true)
    @JsonProperty(value = "sessionTypeUpdateAll")
    private String sessionTypeUpdateAll = "";

	@ApiModelProperty(value = "sessionTypeDeleteAll", required = true)
    @JsonProperty(value = "sessionTypeDeleteAll")
    private String sessionTypeDeleteAll = "";

	@ApiModelProperty(value = "status", required = true)
    @JsonProperty(value = "status")
    private String status = "";

	@ApiModelProperty(value = "active", hidden = true)
    @JsonProperty(value = "active")
    private String active = "";

	@ApiModelProperty(value = "createdAt", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "createdAt")
    private Date createdAt = null;

	@ApiModelProperty(value = "modifiedAt", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "modifiedAt")
    private Date modifiedAt = null;

	public Metadata() {
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public int getCardinality() {
		return cardinality;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}

	public String getSessionTypeCreate() {
		return sessionTypeCreate;
	}

	public void setSessionTypeCreate(String sessionTypeCreate) {
		this.sessionTypeCreate = sessionTypeCreate;
	}

	public String getSessionTypeRead() {
		return sessionTypeRead;
	}

	public void setSessionTypeRead(String sessionTypeRead) {
		this.sessionTypeRead = sessionTypeRead;
	}

	public String getSessionTypeUpdate() {
		return sessionTypeUpdate;
	}

	public void setSessionTypeUpdate(String sessionTypeUpdate) {
		this.sessionTypeUpdate = sessionTypeUpdate;
	}

	public String getSessionTypeDelete() {
		return sessionTypeDelete;
	}

	public void setSessionTypeDelete(String sessionTypeDelete) {
		this.sessionTypeDelete = sessionTypeDelete;
	}

	public String getSessionTypeCreateAll() {
		return sessionTypeCreateAll;
	}

	public void setSessionTypeCreateAll(String sessionTypeCreateAll) {
		this.sessionTypeCreateAll = sessionTypeCreateAll;
	}

	public String getSessionTypeReadAll() {
		return sessionTypeReadAll;
	}

	public void setSessionTypeReadAll(String sessionTypeReadAll) {
		this.sessionTypeReadAll = sessionTypeReadAll;
	}

	public String getSessionTypeUpdateAll() {
		return sessionTypeUpdateAll;
	}

	public void setSessionTypeUpdateAll(String sessionTypeUpdateAll) {
		this.sessionTypeUpdateAll = sessionTypeUpdateAll;
	}

	public String getSessionTypeDeleteAll() {
		return sessionTypeDeleteAll;
	}

	public void setSessionTypeDeleteAll(String sessionTypeDeleteAll) {
		this.sessionTypeDeleteAll = sessionTypeDeleteAll;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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
		return "Metadata [Id=" + Id + ", moduleName=" + moduleName + ", fieldName=" + fieldName + ", fieldValue="
				+ fieldValue + ", displayValue=" + displayValue + ", cardinality=" + cardinality
				+ ", sessionTypeCreate=" + sessionTypeCreate + ", sessionTypeRead=" + sessionTypeRead
				+ ", sessionTypeUpdate=" + sessionTypeUpdate + ", sessionTypeDelete=" + sessionTypeDelete
				+ ", sessionTypeCreateAll=" + sessionTypeCreateAll + ", sessionTypeReadAll=" + sessionTypeReadAll
				+ ", sessionTypeUpdateAll=" + sessionTypeUpdateAll + ", sessionTypeDeleteAll=" + sessionTypeDeleteAll
				+ ", status=" + status + ", active=" + active + ", createdAt=" + createdAt + ", modifiedAt="
				+ modifiedAt + "]";
	}

}
