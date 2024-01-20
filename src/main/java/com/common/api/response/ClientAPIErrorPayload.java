package com.common.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "clientAPIErrorPayload")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAPIErrorPayload {

	@ApiModelProperty(value = "errors", required = false)
	@JsonProperty("errors")
	private Object errors = null;

	@ApiModelProperty(value = "messages", required = false)
	@JsonProperty("messages")
	private Object messages = null;

	public ClientAPIErrorPayload() {
	}

	public ClientAPIErrorPayload(Object errors) {
		this.errors = errors;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}

	public Object getMessages() {
		return messages;
	}

	public void setMessages(Object messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "ClientAPIErrorPayload [errors=" + errors + ", messages=" + messages + "]";
	}

}
