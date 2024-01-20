package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceField extends APIFixedConstant {

 	@ApiModelProperty(value = "city", required = false)
    @JsonProperty(value = "city")
    private String city = "";

 	@ApiModelProperty(value = "state", required = false)
    @JsonProperty(value = "state")
    private String state = "";

 	@ApiModelProperty(value = "country", required = false)
    @JsonProperty(value = "country")
    private String country = "";

 	@ApiModelProperty(value = "zipCode", required = false)
    @JsonProperty(value = "zipCode")
    private String zipCode = "";

	public DeviceField() {
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "DeviceField [city=" + city + ", state=" + state + ", country=" + country + ", zipCode=" + zipCode + "]";
	}

}
