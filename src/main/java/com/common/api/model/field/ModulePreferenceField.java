package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModulePreferenceField extends APIFixedConstant {

 	@ApiModelProperty(value = "description", required = true)
    @JsonProperty(value = "description")
    private String description = "";

	public ModulePreferenceField() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ModulePreferenceField [description=" + description + "]";
	}

}
