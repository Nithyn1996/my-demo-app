package com.common.api.model.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel; 

@ApiModel(value = "predefindedField")
@TypeAlias(value = "predefindedField")  
@JsonIgnoreProperties ( 
	ignoreUnknown = true
	, value = { }
	, allowGetters = false, allowSetters = false)
public class PredefinedField extends APIFixedConstant { 
  
    @JsonProperty(value = "type")    
    private String type = ""; 

    @JsonProperty(value = "values")    
    private List<String> values = new ArrayList<String>(); 
    
	public PredefinedField() { 
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "PredefinedField [type=" + type + ", values=" + values + "]";
	} 
	
}
