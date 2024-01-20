package com.common.api.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.DeviceDataAddressField;
import com.common.api.model.field.DeviceDataErrorField;
import com.common.api.model.field.DeviceDataField;
import com.common.api.model.field.DeviceDataField1;
import com.common.api.model.field.DeviceDataLiveField;
import com.common.api.model.field.DeviceDataRiskField;
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.DeviceDataTrackingField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty; 

@ApiModel(value = "deviceData")
@TypeAlias(value = "deviceData") 
@JsonIgnoreProperties( 
	ignoreUnknown = true 
	, value = { "active", "createdBy", "modifiedBy", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class DeviceData extends APIFixedConstant {

	@Size(min = FL_AUTO_GEN_ID_MIN, max = FL_AUTO_GEN_ID_MAX, message = EM_INVALID_ID)   
	@ApiModelProperty(value = "id", required = false) 
    @JsonProperty(value = "id")     
    private String id = "";
	
	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_COMP_ID)  
	@Pattern(regexp = RE_ID, message = EM_INVALID_COMP_ID + EM_INVALID_PATTERN) 
	@ApiModelProperty(value = "companyId", required = true)
    @JsonProperty(value = "companyId")    
    private String companyId = "";  

	@Size(min = FL_REFERENCE_ID_MIN_OPTIONAL, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_GROUP_ID)  
	@ApiModelProperty(value = "groupId", required = true)
    @JsonProperty(value = "groupId")     
    private String groupId = "";  
	
	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_DIVI_ID)  
	@Pattern(regexp = RE_ID, message = EM_INVALID_DIVI_ID + EM_INVALID_PATTERN)
	@ApiModelProperty(value = "divisionId", required = true) 
    @JsonProperty(value = "divisionId")   
    private String divisionId = ""; 

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_MODU_ID)  
	@Pattern(regexp = RE_ID, message = EM_INVALID_MODU_ID + EM_INVALID_PATTERN) 
	@ApiModelProperty(value = "moduleId", required = true)
    @JsonProperty(value = "moduleId")   
    private String moduleId = ""; 
	
	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_USER_ID)  
	@Pattern(regexp = RE_ID, message = EM_INVALID_USER_ID + EM_INVALID_PATTERN) 
	@ApiModelProperty(value = "userId", required = true)
    @JsonProperty(value = "userId")   
    private String userId = ""; 
	
	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_PROPERTY_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_PROPERTY_ID + EM_INVALID_PATTERN)   
 	@ApiModelProperty(value = "propertyId", required = true) 
    @JsonProperty(value = "propertyId")   
    private String propertyId = ""; 

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_SECTION_ID)
	@Pattern(regexp = RE_ID, message = EM_INVALID_SECTION_ID + EM_INVALID_PATTERN)   
 	@ApiModelProperty(value = "sectionId", required = true)  
    @JsonProperty(value = "sectionId")   
    private String sectionId = "";  

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_PORTION_ID) 
	@Pattern(regexp = RE_ID, message = EM_INVALID_PORTION_ID + EM_INVALID_PATTERN)  
 	@ApiModelProperty(value = "portionId", required = true)   
    @JsonProperty(value = "portionId")    
    private String portionId = "";   

	@Size(min = FL_REFERENCE_ID_MIN, max = FL_REFERENCE_ID_MAX, message = EM_INVALID_DEVICE_ID)  
	@Pattern(regexp = RE_ID, message = EM_INVALID_DEVICE_ID + EM_INVALID_PATTERN) 
 	@ApiModelProperty(value = "deviceId", required = true)  
    @JsonProperty(value = "deviceId")   
    private String deviceId = "";   
	
	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_ACCESS_LEVEL) 
	@ApiModelProperty(value = "accessLevel", required = true)
    @JsonProperty(value = "accessLevel")   
    private String accessLevel = "";  

	@Valid
	@ApiModelProperty(value = "deviceDataAddressField", required = false) 
    @JsonProperty(value = "deviceDataAddressField")         
    private DeviceDataAddressField deviceDataAddressField = new DeviceDataAddressField();     
	 
	@Valid
	@ApiModelProperty(value = "deviceDataField", required = false) 
    @JsonProperty(value = "deviceDataField")       
    private DeviceDataField deviceDataField = new DeviceDataField();  
	 
	@Valid
	@ApiModelProperty(value = "deviceDataField1", required = false) 
    @JsonProperty(value = "deviceDataField1")       
    private DeviceDataField1 deviceDataField1 = new DeviceDataField1();   
	
	@Valid
	@ApiModelProperty(value = "deviceDataLiveField", required = false)
    @JsonProperty(value = "deviceDataLiveField")     
    private DeviceDataLiveField deviceDataLiveField = new DeviceDataLiveField();

	@Valid
	@ApiModelProperty(value = "deviceDataErrorField", required = false)
    @JsonProperty(value = "deviceDataErrorField")     
    private DeviceDataErrorField deviceDataErrorField = new DeviceDataErrorField();

	@Valid
	@ApiModelProperty(value = "deviceDataTrackingField", required = false) 
    @JsonProperty(value = "deviceDataTrackingField")         
    private DeviceDataTrackingField deviceDataTrackingField = new DeviceDataTrackingField();      
	
	@Valid
	@ApiModelProperty(value = "deviceDataRiskField", required = false) 
    @JsonProperty(value = "deviceDataRiskField")         
    private DeviceDataRiskField deviceDataRiskField = new DeviceDataRiskField();        

	@Valid
	@ApiModelProperty(value = "deviceDataRunningField", required = false) 
    @JsonProperty(value = "deviceDataRunningField")         
    private DeviceDataRunningField deviceDataRunningField = new DeviceDataRunningField();         
	
 	@ApiModelProperty(value = "risk", required = false)
    @JsonProperty(value = "risk")   
    private float risk = 0;   

 	@ApiModelProperty(value = "kiloMeter", required = false)
    @JsonProperty(value = "kiloMeter")   
    private float kiloMeter = 0;  

 	@ApiModelProperty(value = "speed", required = false)
    @JsonProperty(value = "speed")   
    private float speed = 0;  

 	@ApiModelProperty(value = "previousSpeed", required = false)
    @JsonProperty(value = "previousSpeed")   
    private float previousSpeed = 0;  

 	@ApiModelProperty(value = "speedLimit", required = false)
    @JsonProperty(value = "speedLimit")   
    private float speedLimit = 0;

 	@ApiModelProperty(value = "activityDuration", required = false)
    @JsonProperty(value = "activityDuration")   
    private float activityDuration = 0;

 	@ApiModelProperty(value = "activityKiloMeter", required = false)
    @JsonProperty(value = "activityKiloMeter")   
    private float activityKiloMeter = 0; 
 
 	@ApiModelProperty(value = "deviceMode", required = false)
    @JsonProperty(value = "deviceMode")   
    private String deviceMode = "";  
 	
 	@ApiModelProperty(value = "deviceCode", required = false )
 	@JsonProperty(value = "deviceCode")
 	private String deviceCode = "";

 	@ApiModelProperty(value = "zipCode", required = false)
    @JsonProperty(value = "zipCode")   
    private String zipCode = ""; 

	@ApiModelProperty(value = "latitude", required = false) 
    @JsonProperty(value = "latitude")       
    private float latitude = 0;   
 	
 	@ApiModelProperty(value = "longitude", required = false)
    @JsonProperty(value = "longitude")     
    private float longitude = 0;   
 	 
 	@ApiModelProperty(value = "redAlertDuration", required = false)
    @JsonProperty(value = "redAlertDuration")     
    private float redAlertDuration = 0;   
 	 
 	@ApiModelProperty(value = "redAlertDistance", required = false)
    @JsonProperty(value = "redAlertDistance")      
    private float redAlertDistance = 0;    

 	@ApiModelProperty(value = "pointOfInterest", required = false)
    @JsonProperty(value = "pointOfInterest")      
    private float pointOfInterest = 0;    

	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_INSERT_MODE)
	@ApiModelProperty(value = "insertMode", required = true) 
    @JsonProperty(value = "insertMode")     
    private String insertMode = "";  

	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_SUB_CATEGORY_LEVEL)
	@ApiModelProperty(value = "subCategoryLevel", required = true)  
    @JsonProperty(value = "subCategoryLevel")      
    private String subCategoryLevel = "";   
	
	@Size(min = FL_SUB_CATEGORY_MIN_OPTIONAL, max = FL_SUB_CATEGORY_MAX, message = EM_INVALID_SUB_CATEGORY) 
	@ApiModelProperty(value = "subCategory", required = true) 
    @JsonProperty(value = "subCategory")   
    private String subCategory = "";     
	
	@Size(min = FL_CATEGORY_MIN_OPTIONAL, max = FL_CATEGORY_MAX, message = EM_INVALID_CATEGORY) 
	@ApiModelProperty(value = "category", required = true)  
    @JsonProperty(value = "category")   
    private String category = "";    

	@Size(min = FL_STATUS_MIN, max = FL_STATUS_MAX, message = EM_INVALID_STATUS)
	@Pattern(regexp = RE_STATUS, message = EM_INVALID_STATUS + EM_INVALID_PATTERN)  
	@ApiModelProperty(value = "status", required = true)
    @JsonProperty(value = "status")   
    private String status = ""; 
	 
	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_SUB_TYPE) 
	@ApiModelProperty(value = "subType", required = true)
    @JsonProperty(value = "subType")   
    private String subType = "";   
 
	@Size(min = FL_TYPE_MIN, max = FL_TYPE_MAX, message = EM_INVALID_TYPE)
	@Pattern(regexp = RE_TYPE, message = EM_INVALID_TYPE + EM_INVALID_PATTERN) 
	@ApiModelProperty(value = "type", required = true)
    @JsonProperty(value = "type")   
    private String type = ""; 

	@ApiModelProperty(value = "active", hidden = true)
    @JsonProperty(value = "active")   
    private String active = "";

	@ApiModelProperty(value = "createdBy", hidden = true)
    @JsonProperty(value = "createdBy")   
    private String createdBy = "";

	@ApiModelProperty(value = "modifiedBy", hidden = true)
    @JsonProperty(value = "modifiedBy")   
    private String modifiedBy = "";

	@ApiModelProperty(value = "createdAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT) 
    @JsonProperty(value = "createdAt")   
    private Date createdAt = null;

	@ApiModelProperty(value = "modifiedAt", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT) 
    @JsonProperty(value = "modifiedAt")   
    private Date modifiedAt = null;

	@ApiModelProperty(value = "insertedAt", hidden = true) 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT) 
    @JsonProperty(value = "insertedAt")   
    private Date insertedAt = null;  
	
	public DeviceData() { 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getPortionId() {
		return portionId;
	}

	public void setPortionId(String portionId) {
		this.portionId = portionId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public DeviceDataAddressField getDeviceDataAddressField() {
		return deviceDataAddressField;
	}

	public void setDeviceDataAddressField(DeviceDataAddressField deviceDataAddressField) {
		this.deviceDataAddressField = deviceDataAddressField;
	}

	public DeviceDataField getDeviceDataField() {
		return deviceDataField;
	}

	public void setDeviceDataField(DeviceDataField deviceDataField) {
		this.deviceDataField = deviceDataField;
	}

	public DeviceDataField1 getDeviceDataField1() {
		return deviceDataField1;
	}

	public void setDeviceDataField1(DeviceDataField1 deviceDataField1) {
		this.deviceDataField1 = deviceDataField1;
	}

	public DeviceDataLiveField getDeviceDataLiveField() {
		return deviceDataLiveField;
	}

	public void setDeviceDataLiveField(DeviceDataLiveField deviceDataLiveField) {
		this.deviceDataLiveField = deviceDataLiveField;
	}

	public DeviceDataErrorField getDeviceDataErrorField() {
		return deviceDataErrorField;
	}

	public void setDeviceDataErrorField(DeviceDataErrorField deviceDataErrorField) {
		this.deviceDataErrorField = deviceDataErrorField;
	}

	public DeviceDataTrackingField getDeviceDataTrackingField() {
		return deviceDataTrackingField;
	}

	public void setDeviceDataTrackingField(DeviceDataTrackingField deviceDataTrackingField) {
		this.deviceDataTrackingField = deviceDataTrackingField;
	}

	public DeviceDataRiskField getDeviceDataRiskField() {
		return deviceDataRiskField;
	}

	public void setDeviceDataRiskField(DeviceDataRiskField deviceDataRiskField) {
		this.deviceDataRiskField = deviceDataRiskField;
	}

	public DeviceDataRunningField getDeviceDataRunningField() {
		return deviceDataRunningField;
	} 

	public void setDeviceDataRunningField(DeviceDataRunningField deviceDataRunningField) {
		this.deviceDataRunningField = deviceDataRunningField;
	}

	public float getRisk() {
		return risk;
	}

	public void setRisk(float risk) {
		this.risk = risk;
	}

	public float getKiloMeter() {
		return kiloMeter;
	}

	public void setKiloMeter(float kiloMeter) {
		this.kiloMeter = kiloMeter;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getPreviousSpeed() {
		return previousSpeed;
	}

	public void setPreviousSpeed(float previousSpeed) {
		this.previousSpeed = previousSpeed;
	}

	public float getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(float speedLimit) {
		this.speedLimit = speedLimit;
	}	

	public float getActivityDuration() {
		return activityDuration;
	}

	public void setActivityDuration(float activityDuration) {
		this.activityDuration = activityDuration;
	}
	  
	public float getActivityKiloMeter() {
		return activityKiloMeter;
	}

	public void setActivityKiloMeter(float activityKiloMeter) {
		this.activityKiloMeter = activityKiloMeter;
	}

	public String getDeviceMode() {
		return deviceMode;
	}

	public void setDeviceMode(String deviceMode) {
		this.deviceMode = deviceMode;
	}	

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getRedAlertDuration() {
		return redAlertDuration;
	}

	public void setRedAlertDuration(float redAlertDuration) {
		this.redAlertDuration = redAlertDuration;
	}

	public float getRedAlertDistance() {
		return redAlertDistance;
	}

	public void setRedAlertDistance(float redAlertDistance) {
		this.redAlertDistance = redAlertDistance;
	}
	
	public float getPointOfInterest() {
		return pointOfInterest;
	} 

	public void setPointOfInterest(float pointOfInterest) {
		this.pointOfInterest = pointOfInterest;
	}

	public String getInsertMode() {
		return insertMode;
	}

	public void setInsertMode(String insertMode) {
		this.insertMode = insertMode;
	}

	public String getSubCategoryLevel() {
		return subCategoryLevel;
	}

	public void setSubCategoryLevel(String subCategoryLevel) {
		this.subCategoryLevel = subCategoryLevel;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return modifiedAt; 
	} 

	public void setModifiedAt(Date modifiedAt) {  
		this.modifiedAt = modifiedAt;
	}

	public Date getInsertedAt() {
		return insertedAt;
	}

	public void setInsertedAt(Date insertedAt) {
		this.insertedAt = insertedAt;
	}

	@Override
	public String toString() {
		return "DeviceData [id=" + id + ", companyId=" + companyId + ", groupId=" + groupId + ", divisionId="
				+ divisionId + ", moduleId=" + moduleId + ", userId=" + userId + ", propertyId=" + propertyId
				+ ", sectionId=" + sectionId + ", portionId=" + portionId + ", deviceId=" + deviceId + ", accessLevel="
				+ accessLevel + ", deviceDataAddressField=" + deviceDataAddressField + ", deviceDataField="
				+ deviceDataField + ", deviceDataField1=" + deviceDataField1 + ", deviceDataLiveField="
				+ deviceDataLiveField + ", deviceDataErrorField=" + deviceDataErrorField + ", deviceDataTrackingField="
				+ deviceDataTrackingField + ", deviceDataRiskField=" + deviceDataRiskField + ", deviceDataRunningField="
				+ deviceDataRunningField + ", risk=" + risk + ", kiloMeter=" + kiloMeter + ", speed=" + speed
				+ ", previousSpeed=" + previousSpeed + ", speedLimit=" + speedLimit + ", activityDuration="
				+ activityDuration + ", activityKiloMeter=" + activityKiloMeter + ", deviceMode=" + deviceMode
				+ ", deviceCode=" + deviceCode + ", zipCode=" + zipCode + ", latitude=" + latitude + ", longitude="
				+ longitude + ", redAlertDuration=" + redAlertDuration + ", redAlertDistance=" + redAlertDistance
				+ ", pointOfInterest=" + pointOfInterest + ", insertMode=" + insertMode + ", subCategoryLevel="
				+ subCategoryLevel + ", subCategory=" + subCategory + ", category=" + category + ", status=" + status
				+ ", subType=" + subType + ", type=" + type + ", active=" + active + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt
				+ ", insertedAt=" + insertedAt + "]";
	}
	
}
