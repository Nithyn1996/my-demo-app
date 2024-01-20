package com.common.api.model.field;

import java.util.ArrayList;
import java.util.List;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyValuesFloat extends APIFixedConstant { 

 	@ApiModelProperty(value = "key", required = true)
    @JsonProperty(value = "key")   
    private String key = "";

 	@ApiModelProperty(value = "values", required = true)
    @JsonProperty(value = "values")   
    private List<Float> values = new ArrayList<Float>();
 	
	public KeyValuesFloat() { 
	}
	 
	public KeyValuesFloat(String key, List<Float> values) {
		this.key = key;
		this.values = values; 
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Float> getValues() {
		return values;
	}

	public void setValues(List<Float> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "KeyValuesFloat [key=" + key + ", values=" + values + "]";
	}
 
}
