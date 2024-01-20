package com.common.api.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "APIPreConditionError")
@TypeAlias(value = "APIPreConditionError")
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIPreConditionError {

	@ApiModelProperty(value = "code", required = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "code")
	private String code = "";

	@ApiModelProperty(value = "messages", required = true)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty(value = "messages")
	private List<APIPreConditionErrorField> messages = new ArrayList<>();

	public APIPreConditionError() {
	}

	public APIPreConditionError(String code, List<APIPreConditionErrorField> messages) {
		this.code = code;
		this.messages = messages;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<APIPreConditionErrorField> getMessages() {
		return messages;
	}

	public void setMessages(List<APIPreConditionErrorField> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "APIPreConditionError [code=" + code + ", messages=" + messages + "]";
	}

}
