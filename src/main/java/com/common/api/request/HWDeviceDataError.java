package com.common.api.request;

import org.springframework.data.annotation.Transient;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.DeviceDataErrorField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HWDeviceDataError extends APIFixedConstant {

 	@ApiModelProperty(value = "action", required = true)
    @JsonProperty(value = "action")
	@Transient
    private String action = "";

 	@ApiModelProperty(value = "mV", required = true)
    @JsonProperty(value = "mV")
	@Transient
    private String mV = "";

 	@ApiModelProperty(value = "pId", required = true)
    @JsonProperty(value = "pId")
	@Transient
    private String pId = "";

 	@ApiModelProperty(value = "uCode", required = true)
    @JsonProperty(value = "uCode")
	@Transient
    private String uCode = "";

 	@ApiModelProperty(value = "data", required = true)
    @JsonProperty(value = "data")
    private DeviceDataErrorField data = new DeviceDataErrorField();

 	@ApiModelProperty(value = "type", required = true)
    @JsonProperty(value = "type")
	@Transient
    private String type = "";

	public HWDeviceDataError() {
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getmV() {
		return mV;
	}

	public void setmV(String mV) {
		this.mV = mV;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getuCode() {
		return uCode;
	}

	public void setuCode(String uCode) {
		this.uCode = uCode;
	}

	public DeviceDataErrorField getData() {
		return data;
	}

	public void setData(DeviceDataErrorField data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "HWDeviceDataError [action=" + action + ", mV=" + mV + ", pId=" + pId + ", uCode=" + uCode
				+ ", data=" + data + ", type=" + type + "]";
	}

}
