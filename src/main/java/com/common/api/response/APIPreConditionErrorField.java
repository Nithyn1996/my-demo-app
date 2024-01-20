package com.common.api.response;

import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "APIPreConditionErrorField")
@TypeAlias(value = "APIPreConditionErrorField")
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIPreConditionErrorField {

	@ApiModelProperty(value = "fieldName", required = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "fieldName")
	private String fieldName = "";

	@ApiModelProperty(value = "message", required = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "message")
	private String message = "";

	public APIPreConditionErrorField() {
	}

	public APIPreConditionErrorField(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "APIPreConditionErrorField [fieldName=" + fieldName + ", message=" + message + "]";
	}

}
