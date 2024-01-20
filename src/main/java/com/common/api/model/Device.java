package com.common.api.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.field.DeviceField;
import com.common.api.model.field.DeviceSafetyField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty; 

@ApiModel(value = "device")
@TypeAlias(value = "device") 
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "active", "createdBy", "modifiedBy",  "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class Device extends APIFixedConstant {    

	// Main Internal Fields
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
	
	// Device Level Fields 
	@Size(min = FL_NAME_MIN, max = FL_NAME_MAX, message = EM_INVALID_NAME)
	@Pattern(regexp = RE_NAME, message = EM_INVALID_NAME + EM_INVALID_PATTERN)  
	@ApiModelProperty(value = "name", required = true)
    @JsonProperty(value = "name")   
    private String name = "";  
 
	@Size(min = FL_UNIQUE_CODE_MIN_OPTIONAL, max = FL_UNIQUE_CODE_MAX, message = EM_INVALID_UNIQUE_CODE)
 	@ApiModelProperty(value = "uniqueCode", required = true)
    @JsonProperty(value = "uniqueCode") 
    private String uniqueCode = ""; 

 	@ApiModelProperty(value = "deviceMode", required = false)
    @JsonProperty(value = "deviceMode")   
    private String deviceMode = "";  
 	
 	@ApiModelProperty(value = "deviceCode", required = false)
    @JsonProperty(value = "deviceCode")   
    private String deviceCode = "";  
 	
 	
	@ApiModelProperty(value = "locationName", required = true)
    @JsonProperty(value = "locationName")      
    private String locationName = "";   
	
	@ApiModelProperty(value = "latitude", required = false) 
    @JsonProperty(value = "latitude")       
    private float latitude = 0;
 	
 	@ApiModelProperty(value = "longitude", required = false)
    @JsonProperty(value = "longitude")     
    private float longitude = 0;   

 	@ApiModelProperty(value = "zipCode", required = false)
    @JsonProperty(value = "zipCode")   
    private String zipCode = ""; 

 	@ApiModelProperty(value = "analyticServerStatus", required = false)
    @JsonProperty(value = "analyticServerStatus")   
    private String analyticServerStatus = ""; 

	@Valid 
	@ApiModelProperty(value = "deviceSafetyField", required = false)
    @JsonProperty(value = "deviceSafetyField")      
    private DeviceSafetyField deviceSafetyField = new DeviceSafetyField();
	
	@Valid
	@ApiModelProperty(value = "deviceField", required = false)
    @JsonProperty(value = "deviceField")     
    private DeviceField deviceField = new DeviceField(); 
	
	// Device Data Fields - Driving Behaviour
	@ApiModelProperty(value = "lastLocationName", required = true)
    @JsonProperty(value = "lastLocationName")      
    private String lastLocationName = "";   
	
	@ApiModelProperty(value = "lastLatitude", required = false) 
    @JsonProperty(value = "lastLatitude")       
    private float lastLatitude = 0;   
 	
 	@ApiModelProperty(value = "lastLongitude", required = false)
    @JsonProperty(value = "lastLongitude")      
    private float lastLongitude = 0;    

 	@ApiModelProperty(value = "lastZipCode", required = false)
    @JsonProperty(value = "lastZipCode")   
    private String lastZipCode = ""; 

	@Valid
	@ApiModelProperty(value = "lastDeviceField", required = false)
    @JsonProperty(value = "lastDeviceField")      
    private DeviceField lastDeviceField = new DeviceField();
	 
 	@ApiModelProperty(value = "drivingScore", required = false) 
    @JsonProperty(value = "drivingScore")       
    private float drivingScore = 0;  

 	@ApiModelProperty(value = "drivingSkill", required = false)
    @JsonProperty(value = "drivingSkill")     
    private float drivingSkill = 0;  
 
 	@ApiModelProperty(value = "selfConfidence", required = false)
    @JsonProperty(value = "selfConfidence")     
    private float selfConfidence = 0; 

 	@ApiModelProperty(value = "anticipation", required = false)
    @JsonProperty(value = "anticipation")      
    private float anticipation = 0;  

 	@ApiModelProperty(value = "kiloMeter", required = false)
    @JsonProperty(value = "kiloMeter")   
    private float kiloMeter = 0;  

 	@ApiModelProperty(value = "travelTime", required = false)
    @JsonProperty(value = "travelTime")   
    private float travelTime = 0;     

 	@ApiModelProperty(value = "urbanPercent", required = false)
    @JsonProperty(value = "urbanPercent")   
    private float urbanPercent = 0; 
	 
	@ApiModelProperty(value = "ruralPercent", required = false)
    @JsonProperty(value = "ruralPercent")   
    private float ruralPercent = 0; 
	 
	@ApiModelProperty(value = "highwayPercent", required = false)
    @JsonProperty(value = "highwayPercent")    
    private float highwayPercent = 0; 
	
	@ApiModelProperty(value = "urbanKilometer", required = false)
    @JsonProperty(value = "urbanKilometer")    
    private float urbanKilometer = 0;  
	 
	@ApiModelProperty(value = "ruralKilometer", required = false)
    @JsonProperty(value = "ruralKilometer")   
    private float ruralKilometer = 0;  
	 
	@ApiModelProperty(value = "highwayKilometer", required = false)
    @JsonProperty(value = "highwayKilometer")    
    private float highwayKilometer = 0;  
	  
	@ApiModelProperty(value = "dayPercentage", required = false) 
    @JsonProperty(value = "dayPercentage")       
    private float dayPercentage = 0;	

	@ApiModelProperty(value = "nightPercentage", required = false) 
    @JsonProperty(value = "nightPercentage")       
    private float nightPercentage = 0;	  
	
	@ApiModelProperty(value = "startDataCount", required = false) 
    @JsonProperty(value = "startDataCount")       
    private int startDataCount = 0;   	
	
	@ApiModelProperty(value = "distanceDataCount", required = false) 
    @JsonProperty(value = "distanceDataCount")       
    private int distanceDataCount = 0;   
	
	@ApiModelProperty(value = "alertDataCount", required = false) 
    @JsonProperty(value = "alertDataCount")       
    private int alertDataCount = 0;   

	@ApiModelProperty(value = "stressStrainDataCount", required = false) 
    @JsonProperty(value = "stressStrainDataCount")        
    private int stressStrainDataCount = 0;      

	@ApiModelProperty(value = "manualDataCount", required = false) 
    @JsonProperty(value = "manualDataCount")        
    private int manualDataCount = 0; 
	
	@ApiModelProperty(value = "endDataCount", required = false) 
    @JsonProperty(value = "endDataCount")        
    private int endDataCount = 0;    

 	@ApiModelProperty(value = "mobileScreenOnDuration", required = false)
    @JsonProperty(value = "mobileScreenOnDuration")   
    private float mobileScreenOnDuration = 0;
 	
 	@ApiModelProperty(value = "mobileUseCallDuration", required = false)
    @JsonProperty(value = "mobileUseCallDuration")   
    private float mobileUseCallDuration = 0;
 	
 	@ApiModelProperty(value = "overSpeedDuration", required = false)
    @JsonProperty(value = "overSpeedDuration")   
    private float overSpeedDuration = 0;
 	
 	@ApiModelProperty(value = "mobileScreenOnKiloMeter", required = false)
    @JsonProperty(value = "mobileScreenOnKiloMeter")   
    private float mobileScreenOnKiloMeter = 0;
 	
 	@ApiModelProperty(value = "mobileUseCallKiloMeter", required = false)
    @JsonProperty(value = "mobileUseCallKiloMeter")   
    private float mobileUseCallKiloMeter = 0;
 	
 	@ApiModelProperty(value = "overSpeedKiloMeter", required = false)
    @JsonProperty(value = "overSpeedKiloMeter")   
    private float overSpeedKiloMeter = 0;

	@ApiModelProperty(value = "riskNegativeCount", required = false) 
    @JsonProperty(value = "riskNegativeCount")        
    private int riskNegativeCount = 0;    

	@ApiModelProperty(value = "riskZeroCount", required = false) 
    @JsonProperty(value = "riskZeroCount")        
    private int riskZeroCount = 0; 

	@ApiModelProperty(value = "riskSlot1Count", required = false) 
    @JsonProperty(value = "riskSlot1Count")        
    private int riskSlot1Count = 0; 

	@ApiModelProperty(value = "riskSlot2Count", required = false) 
    @JsonProperty(value = "riskSlot2Count")        
    private int riskSlot2Count = 0; 

	@ApiModelProperty(value = "riskSlot3Count", required = false) 
    @JsonProperty(value = "riskSlot3Count")        
    private int riskSlot3Count = 0; 

	@ApiModelProperty(value = "riskSlot4Count", required = false) 
    @JsonProperty(value = "riskSlot4Count")        
    private int riskSlot4Count = 0; 

	@ApiModelProperty(value = "riskSlot5Count", required = false) 
    @JsonProperty(value = "riskSlot5Count")        
    private int riskSlot5Count = 0; 

	@ApiModelProperty(value = "riskSlot6Count", required = false) 
    @JsonProperty(value = "riskSlot6Count")        
    private int riskSlot6Count = 0; 

	@ApiModelProperty(value = "riskSlot7Count", required = false) 
    @JsonProperty(value = "riskSlot7Count")        
    private int riskSlot7Count = 0; 

	@ApiModelProperty(value = "riskSlot8Count", required = false) 
    @JsonProperty(value = "riskSlot8Count")        
    private int riskSlot8Count = 0; 

	@ApiModelProperty(value = "riskSlot9Count", required = false) 
    @JsonProperty(value = "riskSlot9Count")        
    private int riskSlot9Count = 0; 

	@ApiModelProperty(value = "riskSlot10Count", required = false) 
    @JsonProperty(value = "riskSlot10Count")        
    private int riskSlot10Count = 0;

 	@ApiModelProperty(value = "deviceUserPicturePath", required = false) 
    @JsonProperty(value = "deviceUserPicturePath")     
    private String deviceUserPicturePath = ""; 
 	
 	@ApiModelProperty(value = "deviceRawFilePath", required = false) 
    @JsonProperty(value = "deviceRawFilePath")      
    private String deviceRawFilePath = ""; 
 	
 	@ApiModelProperty(value = "deviceRawFileStatus", required = false) 
    @JsonProperty(value = "deviceRawFileStatus")      
    private String deviceRawFileStatus = ""; 
 	
 	@ApiModelProperty(value = "deviceRawFileName", required = false) 
    @JsonProperty(value = "deviceRawFileName")      
    private String deviceRawFileName = ""; 
	
	// Main Supported Fields 
	@ApiModelProperty(value = "startDateTime", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT) 
    @JsonProperty(value = "startDateTime")   
    private Date startDateTime = null;   
	
	@ApiModelProperty(value = "endDateTime", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT) 
    @JsonProperty(value = "endDateTime")    
    private Date endDateTime = null;    

	@Size(min = FL_STATUS_MIN_OPTIONAL, max = FL_STATUS_MAX, message = EM_INVALID_INTERNAL_SYSTEM_STATUS)
	@ApiModelProperty(value = "internalSystemStatus", required = true) 
    @JsonProperty(value = "internalSystemStatus")     
    private String internalSystemStatus = ""; 
	
	@Size(min = FL_STATUS_MIN_OPTIONAL, max = FL_STATUS_MAX, message = EM_INVALID_STATUS)
	@ApiModelProperty(value = "scoreValidationStatus", required = true) 
    @JsonProperty(value = "scoreValidationStatus")     
    private String scoreValidationStatus = ""; 

	@Size(min = FL_STATUS_MIN_OPTIONAL, max = FL_STATUS_MAX, message = EM_INVALID_DEVICE_DATA_INSERT_STATUS)
	@ApiModelProperty(value = "deviceDataInsertStatus", required = false) 
    @JsonProperty(value = "deviceDataInsertStatus")      
    private String deviceDataInsertStatus = ""; 
	
	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_INSERT_MODE)
	@ApiModelProperty(value = "insertMode", required = true) 
    @JsonProperty(value = "insertMode")     
    private String insertMode = ""; 
	
	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_ACCESS_LEVEL) 
	@ApiModelProperty(value = "accessLevel", required = true)
    @JsonProperty(value = "accessLevel")   
    private String accessLevel = "";   

	@Size(min = FL_ORIGIN_MIN_OPTIONAL, max = FL_ORIGIN_MAX, message = EM_INVALID_ORIGIN) 
	@ApiModelProperty(value = "origin", required = true) 
    @JsonProperty(value = "origin")    
    private String origin = "";    

	@Size(min = FL_NAME_MIN_OPTIONAL, max = FL_NAME_MAX, message = EM_INVALID_TIMEZONE_CODE) 
	@ApiModelProperty(value = "timezoneCode", required = true)  
    @JsonProperty(value = "timezoneCode")     
    private String timezoneCode = "";   

	@Size(min = FL_SUB_CATEGORY_MIN_OPTIONAL, max = FL_SUB_CATEGORY_MAX, message = EM_INVALID_SUB_CATEGORY) 
	@ApiModelProperty(value = "subCategory", required = true) 
    @JsonProperty(value = "subCategory")   
    private String subCategory = "";  
	
	@Size(min = FL_CATEGORY_MIN_OPTIONAL, max = FL_CATEGORY_MAX, message = EM_INVALID_CATEGORY) 
	@ApiModelProperty(value = "category", required = true) 
    @JsonProperty(value = "category")   
    private String category = "";    

	@Size(min = FL_TYPE_MIN_OPTIONAL, max = FL_TYPE_MAX, message = EM_INVALID_SUB_TYPE) 
	@ApiModelProperty(value = "subType", required = true)
    @JsonProperty(value = "subType")   
    private String subType = "";   
	
	@Size(min = FL_TYPE_MIN, max = FL_TYPE_MAX, message = EM_INVALID_TYPE)
	@Pattern(regexp = RE_TYPE, message = EM_INVALID_TYPE + EM_INVALID_PATTERN) 
	@ApiModelProperty(value = "type", required = true)
    @JsonProperty(value = "type")   
    private String type = ""; 

	@Size(min = FL_STATUS_MIN, max = FL_STATUS_MAX, message = EM_INVALID_STATUS)
	@Pattern(regexp = RE_STATUS, message = EM_INVALID_STATUS + EM_INVALID_PATTERN)  
	@ApiModelProperty(value = "status", required = true)
    @JsonProperty(value = "status")   
    private String status = ""; 
	
	// Default fields 
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

	public Device() {  
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getDeviceMode() {
		return deviceMode;
	}

	public void setDeviceMode(String deviceMode) {
		this.deviceMode = deviceMode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public DeviceField getDeviceField() {
		return deviceField;
	}

	public void setDeviceField(DeviceField deviceField) {
		this.deviceField = deviceField;
	}

	public DeviceSafetyField getDeviceSafetyField() {
		return deviceSafetyField;
	}

	public void setDeviceSafetyField(DeviceSafetyField deviceSafetyField) {
		this.deviceSafetyField = deviceSafetyField;
	}

	public String getAnalyticServerStatus() { 
		return analyticServerStatus;
	} 

	public void setAnalyticServerStatus(String analyticServerStatus) {
		this.analyticServerStatus = analyticServerStatus;
	}

	public String getLastLocationName() {
		return lastLocationName;
	}

	public void setLastLocationName(String lastLocationName) {
		this.lastLocationName = lastLocationName;
	}

	public float getLastLatitude() {
		return lastLatitude;
	}

	public void setLastLatitude(float lastLatitude) {
		this.lastLatitude = lastLatitude;
	}

	public float getLastLongitude() {
		return lastLongitude;
	}

	public void setLastLongitude(float lastLongitude) {
		this.lastLongitude = lastLongitude;
	}

	public String getLastZipCode() {
		return lastZipCode;
	}

	public void setLastZipCode(String lastZipCode) {
		this.lastZipCode = lastZipCode;
	}

	public DeviceField getLastDeviceField() {
		return lastDeviceField;
	}

	public void setLastDeviceField(DeviceField lastDeviceField) {
		this.lastDeviceField = lastDeviceField;
	}

	public float getDrivingScore() {
		return drivingScore;
	}

	public void setDrivingScore(float drivingScore) {
		this.drivingScore = drivingScore;
	}

	public float getDrivingSkill() {
		return drivingSkill;
	}

	public void setDrivingSkill(float drivingSkill) {
		this.drivingSkill = drivingSkill;
	}

	public float getSelfConfidence() {
		return selfConfidence;
	}

	public void setSelfConfidence(float selfConfidence) {
		this.selfConfidence = selfConfidence;
	}

	public float getAnticipation() {
		return anticipation;
	}

	public void setAnticipation(float anticipation) {
		this.anticipation = anticipation;
	}

	public float getKiloMeter() {
		return kiloMeter;
	}

	public void setKiloMeter(float kiloMeter) {
		this.kiloMeter = kiloMeter;
	}

	public float getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(float travelTime) {
		this.travelTime = travelTime;
	}

	public float getUrbanPercent() {
		return urbanPercent;
	}

	public void setUrbanPercent(float urbanPercent) {
		this.urbanPercent = urbanPercent;
	}

	public float getRuralPercent() {
		return ruralPercent;
	}

	public void setRuralPercent(float ruralPercent) {
		this.ruralPercent = ruralPercent;
	}

	public float getHighwayPercent() {
		return highwayPercent;
	}

	public void setHighwayPercent(float highwayPercent) {
		this.highwayPercent = highwayPercent;
	}

	public float getUrbanKilometer() {
		return urbanKilometer;
	}

	public void setUrbanKilometer(float urbanKilometer) {
		this.urbanKilometer = urbanKilometer;
	}

	public float getRuralKilometer() {
		return ruralKilometer;
	}

	public void setRuralKilometer(float ruralKilometer) {
		this.ruralKilometer = ruralKilometer;
	}

	public float getHighwayKilometer() {
		return highwayKilometer;
	}

	public void setHighwayKilometer(float highwayKilometer) {
		this.highwayKilometer = highwayKilometer;
	}

	public float getDayPercentage() {
		return dayPercentage;
	}

	public void setDayPercentage(float dayPercentage) {
		this.dayPercentage = dayPercentage;
	}

	public float getNightPercentage() {
		return nightPercentage;
	}

	public void setNightPercentage(float nightPercentage) {
		this.nightPercentage = nightPercentage;
	}

	public int getStartDataCount() {
		return startDataCount;
	}

	public void setStartDataCount(int startDataCount) {
		this.startDataCount = startDataCount;
	}

	public int getDistanceDataCount() {
		return distanceDataCount;
	}

	public void setDistanceDataCount(int distanceDataCount) {
		this.distanceDataCount = distanceDataCount;
	}

	public int getAlertDataCount() {
		return alertDataCount;
	}

	public void setAlertDataCount(int alertDataCount) {
		this.alertDataCount = alertDataCount;
	}

	public int getStressStrainDataCount() {
		return stressStrainDataCount;
	}

	public void setStressStrainDataCount(int stressStrainDataCount) {
		this.stressStrainDataCount = stressStrainDataCount;
	}
	
	public int getManualDataCount() {
		return manualDataCount;
	}

	public void setManualDataCount(int manualDataCount) {
		this.manualDataCount = manualDataCount;
	}

	public int getEndDataCount() {
		return endDataCount;
	}

	public void setEndDataCount(int endDataCount) {
		this.endDataCount = endDataCount;
	}
	
	public float getMobileScreenOnDuration() { 
		return mobileScreenOnDuration;
	}

	public void setMobileScreenOnDuration(float mobileScreenOnDuration) {
		this.mobileScreenOnDuration = mobileScreenOnDuration;
	}

	public float getMobileUseCallDuration() {
		return mobileUseCallDuration;
	}

	public void setMobileUseCallDuration(float mobileUseCallDuration) {
		this.mobileUseCallDuration = mobileUseCallDuration;
	}

	public float getOverSpeedDuration() {
		return overSpeedDuration;
	}

	public void setOverSpeedDuration(float overSpeedDuration) {
		this.overSpeedDuration = overSpeedDuration;
	}

	public float getMobileScreenOnKiloMeter() {
		return mobileScreenOnKiloMeter;
	}

	public void setMobileScreenOnKiloMeter(float mobileScreenOnKiloMeter) {
		this.mobileScreenOnKiloMeter = mobileScreenOnKiloMeter;
	}

	public float getMobileUseCallKiloMeter() {
		return mobileUseCallKiloMeter;
	}

	public void setMobileUseCallKiloMeter(float mobileUseCallKiloMeter) {
		this.mobileUseCallKiloMeter = mobileUseCallKiloMeter;
	}

	public float getOverSpeedKiloMeter() {
		return overSpeedKiloMeter;
	}

	public void setOverSpeedKiloMeter(float overSpeedKiloMeter) {
		this.overSpeedKiloMeter = overSpeedKiloMeter;
	}

	public int getRiskNegativeCount() {
		return riskNegativeCount;
	}

	public void setRiskNegativeCount(int riskNegativeCount) {
		this.riskNegativeCount = riskNegativeCount;
	}

	public int getRiskZeroCount() {
		return riskZeroCount;
	}

	public void setRiskZeroCount(int riskZeroCount) {
		this.riskZeroCount = riskZeroCount;
	}

	public int getRiskSlot1Count() {
		return riskSlot1Count;
	}

	public void setRiskSlot1Count(int riskSlot1Count) {
		this.riskSlot1Count = riskSlot1Count;
	}

	public int getRiskSlot2Count() {
		return riskSlot2Count;
	}

	public void setRiskSlot2Count(int riskSlot2Count) {
		this.riskSlot2Count = riskSlot2Count;
	}

	public int getRiskSlot3Count() {
		return riskSlot3Count;
	}

	public void setRiskSlot3Count(int riskSlot3Count) {
		this.riskSlot3Count = riskSlot3Count;
	}

	public int getRiskSlot4Count() {
		return riskSlot4Count;
	}

	public void setRiskSlot4Count(int riskSlot4Count) {
		this.riskSlot4Count = riskSlot4Count;
	}

	public int getRiskSlot5Count() {
		return riskSlot5Count;
	}

	public void setRiskSlot5Count(int riskSlot5Count) {
		this.riskSlot5Count = riskSlot5Count;
	}

	public int getRiskSlot6Count() {
		return riskSlot6Count;
	}

	public void setRiskSlot6Count(int riskSlot6Count) {
		this.riskSlot6Count = riskSlot6Count;
	}

	public int getRiskSlot7Count() {
		return riskSlot7Count;
	}

	public void setRiskSlot7Count(int riskSlot7Count) {
		this.riskSlot7Count = riskSlot7Count;
	}

	public int getRiskSlot8Count() {
		return riskSlot8Count;
	}

	public void setRiskSlot8Count(int riskSlot8Count) {
		this.riskSlot8Count = riskSlot8Count;
	}

	public int getRiskSlot9Count() {
		return riskSlot9Count;
	}

	public void setRiskSlot9Count(int riskSlot9Count) {
		this.riskSlot9Count = riskSlot9Count;
	}

	public int getRiskSlot10Count() {
		return riskSlot10Count;
	}

	public void setRiskSlot10Count(int riskSlot10Count) {
		this.riskSlot10Count = riskSlot10Count;
	}

	public String getDeviceUserPicturePath() { 
		return deviceUserPicturePath;
	}

	public void setDeviceUserPicturePath(String deviceUserPicturePath) {
		this.deviceUserPicturePath = deviceUserPicturePath;
	}

	public String getDeviceRawFilePath() {
		return deviceRawFilePath;
	}

	public void setDeviceRawFilePath(String deviceRawFilePath) {
		this.deviceRawFilePath = deviceRawFilePath;
	}

	public String getDeviceRawFileStatus() {
		return deviceRawFileStatus;
	}

	public void setDeviceRawFileStatus(String deviceRawFileStatus) {
		this.deviceRawFileStatus = deviceRawFileStatus;
	}
	
	public String getDeviceRawFileName() { 
		return deviceRawFileName;
	}

	public void setDeviceRawFileName(String deviceRawFileName) {
		this.deviceRawFileName = deviceRawFileName;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getInternalSystemStatus() {
		return internalSystemStatus;
	}

	public void setInternalSystemStatus(String internalSystemStatus) {
		this.internalSystemStatus = internalSystemStatus;
	}

	public String getScoreValidationStatus() {
		return scoreValidationStatus;
	}

	public void setScoreValidationStatus(String scoreValidationStatus) {
		this.scoreValidationStatus = scoreValidationStatus;
	}

	public String getDeviceDataInsertStatus() {
		return deviceDataInsertStatus;
	}

	public void setDeviceDataInsertStatus(String deviceDataInsertStatus) {
		this.deviceDataInsertStatus = deviceDataInsertStatus;
	}

	public String getInsertMode() {
		return insertMode;
	}

	public void setInsertMode(String insertMode) {
		this.insertMode = insertMode;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getTimezoneCode() {
		return timezoneCode;
	}

	public void setTimezoneCode(String timezoneCode) {
		this.timezoneCode = timezoneCode;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "Device [id=" + id + ", companyId=" + companyId + ", groupId=" + groupId + ", divisionId=" + divisionId
				+ ", moduleId=" + moduleId + ", userId=" + userId + ", propertyId=" + propertyId + ", sectionId="
				+ sectionId + ", portionId=" + portionId + ", name=" + name + ", uniqueCode=" + uniqueCode
				+ ", deviceMode=" + deviceMode + ", locationName=" + locationName + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", zipCode=" + zipCode + ", analyticServerStatus=" + analyticServerStatus
				+ ", deviceSafetyField=" + deviceSafetyField + ", deviceField=" + deviceField + ", lastLocationName="
				+ lastLocationName + ", lastLatitude=" + lastLatitude + ", lastLongitude=" + lastLongitude
				+ ", lastZipCode=" + lastZipCode + ", lastDeviceField=" + lastDeviceField + ", drivingScore="
				+ drivingScore + ", drivingSkill=" + drivingSkill + ", selfConfidence=" + selfConfidence
				+ ", anticipation=" + anticipation + ", kiloMeter=" + kiloMeter + ", travelTime=" + travelTime
				+ ", urbanPercent=" + urbanPercent + ", ruralPercent=" + ruralPercent + ", highwayPercent="
				+ highwayPercent + ", urbanKilometer=" + urbanKilometer + ", ruralKilometer=" + ruralKilometer
				+ ", highwayKilometer=" + highwayKilometer + ", dayPercentage=" + dayPercentage + ", nightPercentage="
				+ nightPercentage + ", startDataCount=" + startDataCount + ", distanceDataCount=" + distanceDataCount
				+ ", alertDataCount=" + alertDataCount + ", stressStrainDataCount=" + stressStrainDataCount
				+ ", manualDataCount=" + manualDataCount + ", endDataCount=" + endDataCount
				+ ", mobileScreenOnDuration=" + mobileScreenOnDuration + ", mobileUseCallDuration="
				+ mobileUseCallDuration + ", overSpeedDuration=" + overSpeedDuration + ", mobileScreenOnKiloMeter="
				+ mobileScreenOnKiloMeter + ", mobileUseCallKiloMeter=" + mobileUseCallKiloMeter
				+ ", overSpeedKiloMeter=" + overSpeedKiloMeter + ", riskNegativeCount=" + riskNegativeCount
				+ ", riskZeroCount=" + riskZeroCount + ", riskSlot1Count=" + riskSlot1Count + ", riskSlot2Count="
				+ riskSlot2Count + ", riskSlot3Count=" + riskSlot3Count + ", riskSlot4Count=" + riskSlot4Count
				+ ", riskSlot5Count=" + riskSlot5Count + ", riskSlot6Count=" + riskSlot6Count + ", riskSlot7Count="
				+ riskSlot7Count + ", riskSlot8Count=" + riskSlot8Count + ", riskSlot9Count=" + riskSlot9Count
				+ ", riskSlot10Count=" + riskSlot10Count + ", deviceUserPicturePath=" + deviceUserPicturePath
				+ ", deviceRawFilePath=" + deviceRawFilePath + ", deviceRawFileStatus=" + deviceRawFileStatus
				+ ", deviceRawFileName=" + deviceRawFileName + ", startDateTime=" + startDateTime + ", endDateTime="
				+ endDateTime + ", internalSystemStatus=" + internalSystemStatus + ", scoreValidationStatus="
				+ scoreValidationStatus + ", deviceDataInsertStatus=" + deviceDataInsertStatus + ", insertMode="
				+ insertMode + ", accessLevel=" + accessLevel + ", origin=" + origin + ", timezoneCode=" + timezoneCode
				+ ", subCategory=" + subCategory + ", category=" + category + ", subType=" + subType + ", type=" + type
				+ ", status=" + status + ", active=" + active + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", insertedAt=" + insertedAt
				+ "]";
	}
 
}
