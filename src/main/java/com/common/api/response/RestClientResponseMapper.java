package com.common.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestClientResponseMapper {

	@JsonProperty("code")
	private int code = 400;

	@JsonProperty("message")
	private String message = "";

	@JsonProperty("success")
	private String success = "";

	@JsonProperty("failed")
	private String failed = "";

	@JsonProperty("messages")
	private Object messages = "";

	@JsonProperty("results")
	private Object results = "";

	@JsonProperty("errors")
	private Object errors = "";

	@JsonProperty("helmetAloneCount")
	private int helmetAloneCount = 0;

	@JsonProperty("userWithHelmetCount")
	private int userWithHelmetCount = 0;

	@JsonProperty("userWithoutHelmetCount")
	private int userWithoutHelmetCount = 0;

	@JsonProperty("userWithSeatbeltCount")
	private int userWithSeatbeltCount = 0;

	@JsonProperty("userWithoutSeatbeltCount")
	private int userWithoutSeatbeltCount = 0;

	public RestClientResponseMapper() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}

	public Object getMessages() {
		return messages;
	}

	public void setMessages(Object messages) {
		this.messages = messages;
	}

	public Object getResults() {
		return results;
	}

	public void setResults(Object results) {
		this.results = results;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}

	public int getHelmetAloneCount() {
		return helmetAloneCount;
	}

	public void setHelmetAloneCount(int helmetAloneCount) {
		this.helmetAloneCount = helmetAloneCount;
	}

	public int getUserWithHelmetCount() {
		return userWithHelmetCount;
	}

	public void setUserWithHelmetCount(int userWithHelmetCount) {
		this.userWithHelmetCount = userWithHelmetCount;
	}

	public int getUserWithoutHelmetCount() {
		return userWithoutHelmetCount;
	}

	public void setUserWithoutHelmetCount(int userWithoutHelmetCount) {
		this.userWithoutHelmetCount = userWithoutHelmetCount;
	}

	public int getUserWithSeatbeltCount() {
		return userWithSeatbeltCount;
	}

	public void setUserWithSeatbeltCount(int userWithSeatbeltCount) {
		this.userWithSeatbeltCount = userWithSeatbeltCount;
	}

	public int getUserWithoutSeatbeltCount() {
		return userWithoutSeatbeltCount;
	}

	public void setUserWithoutSeatbeltCount(int userWithoutSeatbeltCount) {
		this.userWithoutSeatbeltCount = userWithoutSeatbeltCount;
	}

	@Override
	public String toString() {
		return "RestClientResponseMapper [code=" + code + ", message=" + message + ", success=" + success + ", failed="
				+ failed + ", messages=" + messages + ", results=" + results + ", errors=" + errors
				+ ", helmetAloneCount=" + helmetAloneCount + ", userWithHelmetCount=" + userWithHelmetCount
				+ ", userWithoutHelmetCount=" + userWithoutHelmetCount + ", userWithSeatbeltCount="
				+ userWithSeatbeltCount + ", userWithoutSeatbeltCount=" + userWithoutSeatbeltCount + "]";
	}

}
