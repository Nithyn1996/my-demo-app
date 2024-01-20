package com.common.api.request;

import org.springframework.data.annotation.Transient;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.DeviceField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HWDevice extends APIFixedConstant {

 	@ApiModelProperty(value = "action", required = true)
    @JsonProperty(value = "action")
	@Transient
    private String action = "";

 	@ApiModelProperty(value = "mV", required = true)
    @JsonProperty(value = "mV")
	@Transient
    private String mV = "";

 	@ApiModelProperty(value = "cId", required = true)
    @JsonProperty(value = "cId")
	@Transient
    private String cId = "";

 	@ApiModelProperty(value = "pId", required = true)
    @JsonProperty(value = "pId")
	@Transient
    private String pId = "";

 	@ApiModelProperty(value = "uCode", required = true)
    @JsonProperty(value = "uCode")
	@Transient
    private String uCode = "";

 	@ApiModelProperty(value = "data", required = false)
    @JsonProperty(value = "data")
    private DeviceField data = new DeviceField();

 	@ApiModelProperty(value = "type", required = true)
    @JsonProperty(value = "type")
	@Transient
    private String type = "";

	public HWDevice() {
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

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
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

	public DeviceField getData() {
		return data;
	}

	public void setData(DeviceField data) {
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
		return "HWDevice [action=" + action + ", mV=" + mV + ", cId=" + cId + ", pId=" + pId + ", uCode=" + uCode
				+ ", data=" + data + ", type=" + type + "]";
	}

}
