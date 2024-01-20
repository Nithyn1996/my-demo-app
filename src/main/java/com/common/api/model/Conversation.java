package com.common.api.model;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "conversation")
@TypeAlias(value = "conversation")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { }
	, allowGetters = false, allowSetters = false)
public class Conversation extends APIFixedConstant {

	@ApiModelProperty(value = "inputValue", required = true)
    @JsonProperty(value = "inputValue")
    private String inputValue = "";

	@ApiModelProperty(value = "type", required = true)
    @JsonProperty(value = "type")
    private String type = "";

	public Conversation() {
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Conversation [inputValue=" + inputValue + ", type=" + type + "]";
	}

}
