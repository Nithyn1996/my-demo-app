package com.common.api.model.util;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel; 

@ApiModel(value = "deviceSummaryUpdateField")
@TypeAlias(value = "deviceSummaryUpdateField")  
@JsonIgnoreProperties ( 
	ignoreUnknown = true
	, value = { "active", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class DeviceSummaryUpdateField extends APIFixedConstant { 
  
    @JsonProperty(value = "category")    
    private String category = ""; 
  
    @JsonProperty(value = "subCategory")   
    private String subCategory = "";  
 
    @JsonProperty(value = "subCategoryLevel")   
    private String subCategoryLevel = ""; 
 
    @JsonProperty(value = "count")   
    private int count = -1; 
 
    @JsonProperty(value = "distance")    
    private float distance = -1;
    
    @JsonProperty(value = "duration")   
    private float duration = -1; 
    
	public DeviceSummaryUpdateField() { 
	}
	
	public DeviceSummaryUpdateField(String category, String subCategory, String subCategoryLevel, int count, float distance, float duration) { 
		this.category = category;
		this.subCategory = subCategory;
		this.subCategoryLevel = subCategoryLevel;
		this.count = count;
		this.distance = distance;
		this.duration = duration;
	}
 
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getSubCategoryLevel() {
		return subCategoryLevel;
	}

	public void setSubCategoryLevel(String subCategoryLevel) {
		this.subCategoryLevel = subCategoryLevel;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) { 
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "DeviceSummaryUpdateField [category=" + category + ", subCategory=" + subCategory + ", subCategoryLevel="
				+ subCategoryLevel + ", count=" + count + ", distance=" + distance + ", duration=" + duration + "]";
	}
 
}