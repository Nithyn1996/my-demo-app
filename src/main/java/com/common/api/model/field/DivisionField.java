package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DivisionField extends APIFixedConstant {

 	@ApiModelProperty(value = "description", required = true)
    @JsonProperty(value = "description")
    private String description = "";

 	@ApiModelProperty(value = "addressLine1", required = true)
    @JsonProperty(value = "addressLine1")
    private String addressLine1 = "";

 	@ApiModelProperty(value = "addressLine2", required = true)
    @JsonProperty(value = "addressLine2")
    private String addressLine2 = "";

 	@ApiModelProperty(value = "city", required = true)
    @JsonProperty(value = "city")
    private String city = "";

 	@ApiModelProperty(value = "state", required = true)
    @JsonProperty(value = "state")
    private String state = "";

 	@ApiModelProperty(value = "country", required = true)
    @JsonProperty(value = "country")
    private String country = "";

 	@ApiModelProperty(value = "postalCode", required = true)
    @JsonProperty(value = "postalCode")
    private String postalCode = "";

 	@ApiModelProperty(value = "contactMail", required = true)
    @JsonProperty(value = "contactMail")
    private String contactEmail = "";

 	@ApiModelProperty(value = "contactPhone", required = true)
    @JsonProperty(value = "contactPhone")
    private String contactPhone = "";

 	@ApiModelProperty(value = "contactMobile", required = true)
    @JsonProperty(value = "contactMobile")
    private String contactMobile = "";

	public DivisionField() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	@Override
	public String toString() {
		return "DivisionField [description=" + description + ", addressLine1=" + addressLine1 + ", addressLine2="
				+ addressLine2 + ", city=" + city + ", state=" + state + ", country=" + country + ", postalCode="
				+ postalCode + ", contactEmail=" + contactEmail + ", contactPhone=" + contactPhone + ", contactMobile="
				+ contactMobile + "]";
	}

}
