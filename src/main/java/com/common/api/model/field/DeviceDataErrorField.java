package com.common.api.model.field;

import java.util.ArrayList;
import java.util.List;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDataErrorField extends APIFixedConstant {

 	@ApiModelProperty(value = "codes", required = false)
    @JsonProperty(value = "codes")
    private List<String> codes = new ArrayList<>();

	public DeviceDataErrorField() {
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	@Override
	public String toString() {
		return "DeviceDataErrorField [codes=" + codes + "]";
	}


}
