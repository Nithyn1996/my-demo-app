package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDataAddressField extends APIFixedConstant {

 	@ApiModelProperty(value = "location", required = true)
    @JsonProperty(value = "location")
    private String location = "";

 	@ApiModelProperty(value = "city", required = true)
    @JsonProperty(value = "city")
    private String city = "";

 	@ApiModelProperty(value = "state", required = true)
    @JsonProperty(value = "state")
    private String state = "";

 	@ApiModelProperty(value = "country", required = true)
    @JsonProperty(value = "country")
    private String country = "";

	public DeviceDataAddressField() {
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	@Override
	public String toString() {
		return "DeviceDataAddressField [location=" + location + ", city=" + city + ", state=" + state + ", country="
				+ country + "]";
	}

}
