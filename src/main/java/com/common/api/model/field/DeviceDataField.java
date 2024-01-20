package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDataField extends APIFixedConstant {

 	@ApiModelProperty(value = "deviceMode", required = false)
    @JsonProperty(value = "deviceMode")
    private String deviceMode = "";

 	@ApiModelProperty(value = "latitude", required = false)
    @JsonProperty(value = "latitude")
    private String latitude = "";

 	@ApiModelProperty(value = "longitude", required = false)
    @JsonProperty(value = "longitude")
    private String longitude = "";

 	@ApiModelProperty(value = "zipCode", required = false)
    @JsonProperty(value = "zipCode")
    private String zipCode = "";

 	@ApiModelProperty(value = "risk", required = false)
    @JsonProperty(value = "risk")
    private String risk = "";

 	@ApiModelProperty(value = "speed", required = false)
    @JsonProperty(value = "speed")
    private String speed = "";

 	@ApiModelProperty(value = "previousSpeed", required = false)
    @JsonProperty(value = "previousSpeed")
    private String previousSpeed = "";

 	@ApiModelProperty(value = "speedLimit", required = false)
    @JsonProperty(value = "speedLimit")
    private String speedLimit = "";

 	@ApiModelProperty(value = "activityDuration", required = false)
    @JsonProperty(value = "activityDuration")
    private String activityDuration = "";

 	@ApiModelProperty(value = "gpsCount", required = false)
    @JsonProperty(value = "gpsCount")
    private String gpsCount = "";

 	@ApiModelProperty(value = "accelerX", required = false)
    @JsonProperty(value = "accelerX")
    private String accelerX = "";

 	@ApiModelProperty(value = "accelerY", required = false)
    @JsonProperty(value = "accelerY")
    private String accelerY = "";

 	@ApiModelProperty(value = "accelerZ", required = false)
    @JsonProperty(value = "accelerZ")
    private String accelerZ = "";

 	@ApiModelProperty(value = "senRotate", required = false)
    @JsonProperty(value = "senRotate")
    private String senRotate = "";

 	@ApiModelProperty(value = "kiloMeter", required = false)
    @JsonProperty(value = "kiloMeter")
    private String kiloMeter = "";

 	// ALERT_DATA category parameters

 	@ApiModelProperty(value = "alert", required = false)
    @JsonProperty(value = "alert")
    private String alert = "";

 	@ApiModelProperty(value = "alertId", required = false)
    @JsonProperty(value = "alertId")
    private String alertId = "";

 	@ApiModelProperty(value = "alertValue", required = false)
    @JsonProperty(value = "alertValue")
    private String alertValue = "";

 	@ApiModelProperty(value = "alertTime", required = false)
    @JsonProperty(value = "alertTime")
    private String alertTime = "";

 	@ApiModelProperty(value = "alertKiloMeter", required = false)
    @JsonProperty(value = "alertKiloMeter")
    private String alertKiloMeter = "";

 	// END_DATA category parameters

 	@ApiModelProperty(value = "anticipation", required = false)
    @JsonProperty(value = "anticipation")
    private String anticipation = "";

 	@ApiModelProperty(value = "drivingScore", required = false)
    @JsonProperty(value = "drivingScore")
    private String drivingScore = "";

 	@ApiModelProperty(value = "drivingSkill", required = false)
    @JsonProperty(value = "drivingSkill")
    private String drivingSkill = "";

 	@ApiModelProperty(value = "selfConfidence", required = false)
    @JsonProperty(value = "selfConfidence")
    private String selfConfidence = "";

 	@ApiModelProperty(value = "totalKiloMeter", required = false)
    @JsonProperty(value = "totalKiloMeter")
    private String totalKiloMeter = "";

 	@ApiModelProperty(value = "travelTime", required = false)
    @JsonProperty(value = "travelTime")
    private String travelTime = "";

 	@ApiModelProperty(value = "previousTravelTime", required = false)
    @JsonProperty(value = "previousTravelTime")
    private String previousTravelTime = "";

 	@ApiModelProperty(value = "rideTime", required = false)
    @JsonProperty(value = "rideTime")
    private String rideTime = "";

 	// Percentage

 	@ApiModelProperty(value = "urbanPercent", required = false)
    @JsonProperty(value = "urbanPercent")
    private String urbanPercent = "";

	@ApiModelProperty(value = "ruralPercent", required = false)
    @JsonProperty(value = "ruralPercent")
    private String ruralPercent = "";

	@ApiModelProperty(value = "highwayPercent", required = false)
    @JsonProperty(value = "highwayPercent")
    private String highwayPercent = "";

	@ApiModelProperty(value = "urbanKilometer", required = false)
    @JsonProperty(value = "urbanKilometer")
    private String urbanKilometer = "";

	@ApiModelProperty(value = "ruralKilometer", required = false)
    @JsonProperty(value = "ruralKilometer")
    private String ruralKilometer = "";

	@ApiModelProperty(value = "highwayKilometer", required = false)
    @JsonProperty(value = "highwayKilometer")
    private String highwayKilometer = "";

	@ApiModelProperty(value = "gyroscopeX", required = false)
    @JsonProperty(value = "gyroscopeX")
    private String gyroscopeX = "";

	@ApiModelProperty(value = "gyroscopeY", required = false)
    @JsonProperty(value = "gyroscopeY")
    private String gyroscopeY = "";

	@ApiModelProperty(value = "gyroscopeZ", required = false)
    @JsonProperty(value = "gyroscopeZ")
    private String gyroscopeZ = "";

	@ApiModelProperty(value = "magnetometerX", required = false)
    @JsonProperty(value = "magnetometerX")
    private String magnetometerX = "";

	@ApiModelProperty(value = "magnetometerY", required = false)
    @JsonProperty(value = "magnetometerY")
    private String magnetometerY = "";

	@ApiModelProperty(value = "magnetometerZ", required = false)
    @JsonProperty(value = "magnetometerZ")
    private String magnetometerZ = "";

	@ApiModelProperty(value = "alertName", required = false)
    @JsonProperty(value = "alertName")
    private String alertName = "";

	@ApiModelProperty(value = "ehorizonLength", required = false)
    @JsonProperty(value = "ehorizonLength")
    private String ehorizonLength = "";

	@ApiModelProperty(value = "engineState", required = false)
    @JsonProperty(value = "engineState")
    private String engineState = "";

	@ApiModelProperty(value = "gpsTimeDiff", required = false)
    @JsonProperty(value = "gpsTimeDiff")
    private String gpsTimeDiff = "";
	
	@ApiModelProperty(value = "drivingVehicleNo", required=false)
	@JsonProperty(value = "drivingVehicleNo")
	private String drivingVehicleNo;

	public DeviceDataField() {
	}

	public String getDeviceMode() {
		return deviceMode;
	}

	public void setDeviceMode(String deviceMode) {
		this.deviceMode = deviceMode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getPreviousSpeed() {
		return previousSpeed;
	}

	public void setPreviousSpeed(String previousSpeed) {
		this.previousSpeed = previousSpeed;
	}

	public String getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(String speedLimit) {
		this.speedLimit = speedLimit;
	}

	public String getActivityDuration() {
		return activityDuration;
	}

	public void setActivityDuration(String activityDuration) {
		this.activityDuration = activityDuration;
	}

	public String getGpsCount() {
		return gpsCount;
	}

	public void setGpsCount(String gpsCount) {
		this.gpsCount = gpsCount;
	}

	public String getAccelerX() {
		return accelerX;
	}

	public void setAccelerX(String accelerX) {
		this.accelerX = accelerX;
	}

	public String getAccelerY() {
		return accelerY;
	}

	public void setAccelerY(String accelerY) {
		this.accelerY = accelerY;
	}

	public String getAccelerZ() {
		return accelerZ;
	}

	public void setAccelerZ(String accelerZ) {
		this.accelerZ = accelerZ;
	}

	public String getSenRotate() {
		return senRotate;
	}

	public void setSenRotate(String senRotate) {
		this.senRotate = senRotate;
	}

	public String getKiloMeter() {
		return kiloMeter;
	}

	public void setKiloMeter(String kiloMeter) {
		this.kiloMeter = kiloMeter;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getAlertId() {
		return alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public String getAlertValue() {
		return alertValue;
	}

	public void setAlertValue(String alertValue) {
		this.alertValue = alertValue;
	}

	public String getAlertTime() {
		return alertTime;
	}

	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}

	public String getAlertKiloMeter() {
		return alertKiloMeter;
	}

	public void setAlertKiloMeter(String alertKiloMeter) {
		this.alertKiloMeter = alertKiloMeter;
	}

	public String getAnticipation() {
		return anticipation;
	}

	public void setAnticipation(String anticipation) {
		this.anticipation = anticipation;
	}

	public String getDrivingScore() {
		return drivingScore;
	}

	public void setDrivingScore(String drivingScore) {
		this.drivingScore = drivingScore;
	}

	public String getDrivingSkill() {
		return drivingSkill;
	}

	public void setDrivingSkill(String drivingSkill) {
		this.drivingSkill = drivingSkill;
	}

	public String getSelfConfidence() {
		return selfConfidence;
	}

	public void setSelfConfidence(String selfConfidence) {
		this.selfConfidence = selfConfidence;
	}

	public String getTotalKiloMeter() {
		return totalKiloMeter;
	}

	public void setTotalKiloMeter(String totalKiloMeter) {
		this.totalKiloMeter = totalKiloMeter;
	}

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getPreviousTravelTime() {
		return previousTravelTime;
	}

	public void setPreviousTravelTime(String previousTravelTime) {
		this.previousTravelTime = previousTravelTime;
	}

	public String getRideTime() {
		return rideTime;
	}

	public void setRideTime(String rideTime) {
		this.rideTime = rideTime;
	}

	public String getUrbanPercent() {
		return urbanPercent;
	}

	public void setUrbanPercent(String urbanPercent) {
		this.urbanPercent = urbanPercent;
	}

	public String getRuralPercent() {
		return ruralPercent;
	}

	public void setRuralPercent(String ruralPercent) {
		this.ruralPercent = ruralPercent;
	}

	public String getHighwayPercent() {
		return highwayPercent;
	}

	public void setHighwayPercent(String highwayPercent) {
		this.highwayPercent = highwayPercent;
	}

	public String getUrbanKilometer() {
		return urbanKilometer;
	}

	public void setUrbanKilometer(String urbanKilometer) {
		this.urbanKilometer = urbanKilometer;
	}

	public String getRuralKilometer() {
		return ruralKilometer;
	}

	public void setRuralKilometer(String ruralKilometer) {
		this.ruralKilometer = ruralKilometer;
	}

	public String getHighwayKilometer() {
		return highwayKilometer;
	}

	public void setHighwayKilometer(String highwayKilometer) {
		this.highwayKilometer = highwayKilometer;
	}

	public String getGyroscopeX() {
		return gyroscopeX;
	}

	public void setGyroscopeX(String gyroscopeX) {
		this.gyroscopeX = gyroscopeX;
	}

	public String getGyroscopeY() {
		return gyroscopeY;
	}

	public void setGyroscopeY(String gyroscopeY) {
		this.gyroscopeY = gyroscopeY;
	}

	public String getGyroscopeZ() {
		return gyroscopeZ;
	}

	public void setGyroscopeZ(String gyroscopeZ) {
		this.gyroscopeZ = gyroscopeZ;
	}

	public String getMagnetometerX() {
		return magnetometerX;
	}

	public void setMagnetometerX(String magnetometerX) {
		this.magnetometerX = magnetometerX;
	}

	public String getMagnetometerY() {
		return magnetometerY;
	}

	public void setMagnetometerY(String magnetometerY) {
		this.magnetometerY = magnetometerY;
	}

	public String getMagnetometerZ() {
		return magnetometerZ;
	}

	public void setMagnetometerZ(String magnetometerZ) {
		this.magnetometerZ = magnetometerZ;
	}

	public String getAlertName() {
		return alertName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	public String getEhorizonLength() {
		return ehorizonLength;
	}

	public void setEhorizonLength(String ehorizonLength) {
		this.ehorizonLength = ehorizonLength;
	}

	public String getEngineState() {
		return engineState;
	}

	public void setEngineState(String engineState) {
		this.engineState = engineState;
	}

	public String getGpsTimeDiff() {
		return gpsTimeDiff;
	}

	public void setGpsTimeDiff(String gpsTimeDiff) {
		this.gpsTimeDiff = gpsTimeDiff;
	}	

	public String getDrivingVehicleNo() {
		return drivingVehicleNo;
	}

	public void setDrivingVehicleNo(String drivingVehicleNo) {
		this.drivingVehicleNo = drivingVehicleNo;
	}

	@Override
	public String toString() {
		return "DeviceDataField [deviceMode=" + deviceMode + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", zipCode=" + zipCode + ", risk=" + risk + ", speed=" + speed + ", previousSpeed=" + previousSpeed
				+ ", speedLimit=" + speedLimit + ", activityDuration=" + activityDuration + ", gpsCount=" + gpsCount
				+ ", accelerX=" + accelerX + ", accelerY=" + accelerY + ", accelerZ=" + accelerZ + ", senRotate="
				+ senRotate + ", kiloMeter=" + kiloMeter + ", alert=" + alert + ", alertId=" + alertId + ", alertValue="
				+ alertValue + ", alertTime=" + alertTime + ", alertKiloMeter=" + alertKiloMeter + ", anticipation="
				+ anticipation + ", drivingScore=" + drivingScore + ", drivingSkill=" + drivingSkill
				+ ", selfConfidence=" + selfConfidence + ", totalKiloMeter=" + totalKiloMeter + ", travelTime="
				+ travelTime + ", previousTravelTime=" + previousTravelTime + ", rideTime=" + rideTime
				+ ", urbanPercent=" + urbanPercent + ", ruralPercent=" + ruralPercent + ", highwayPercent="
				+ highwayPercent + ", urbanKilometer=" + urbanKilometer + ", ruralKilometer=" + ruralKilometer
				+ ", highwayKilometer=" + highwayKilometer + ", gyroscopeX=" + gyroscopeX + ", gyroscopeY=" + gyroscopeY
				+ ", gyroscopeZ=" + gyroscopeZ + ", magnetometerX=" + magnetometerX + ", magnetometerY=" + magnetometerY
				+ ", magnetometerZ=" + magnetometerZ + ", alertName=" + alertName + ", ehorizonLength=" + ehorizonLength
				+ ", engineState=" + engineState + ", gpsTimeDiff=" + gpsTimeDiff + ", drivingVehicleNo="
				+ drivingVehicleNo + "]";
	}

}
