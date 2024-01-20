package com.common.api.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushNotificationRequest extends APIFixedConstant {

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

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_USER_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_USER_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "userId", hidden = true)
    @JsonProperty(value = "userId")
    private String userId = "";

 	@ApiModelProperty(value = "keyValues", required = true)
    @JsonProperty(value = "keyValues")
    private List<KeyValue> keyValues = new ArrayList<>();

 	@ApiModelProperty(value = "action", required = true)
    @JsonProperty(value = "action")
    private String action = "";

 	@ApiModelProperty(value = "event", required = true)
    @JsonProperty(value = "event")
    private String event = "";

 	@ApiModelProperty(value = "type", required = true)
    @JsonProperty(value = "type")
    private String type = "";

	public PushNotificationRequest() {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<KeyValue> getKeyValues() {
		return keyValues;
	}

	public void setKeyValues(List<KeyValue> keyValues) {
		this.keyValues = keyValues;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PushNotificationRequest [companyId=" + companyId + ", divisionId=" + divisionId + ", userId=" + userId
				+ ", keyValues=" + keyValues + ", action=" + action + ", event=" + event + ", type=" + type + "]";
	}

}
