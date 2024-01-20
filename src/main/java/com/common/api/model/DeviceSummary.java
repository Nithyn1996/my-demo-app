package com.common.api.model;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "deviceSummary")
@TypeAlias(value = "deviceSummary")
@JsonIgnoreProperties(
	ignoreUnknown = true
	, value = { "active", "createdBy", "modifiedBy", "createdAt", "modifiedAt" }
	, allowGetters = false, allowSetters = false)
public class DeviceSummary extends APIFixedConstant{ 

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

	@ApiModelProperty(value = "accidentCount", required = false)
    @JsonProperty(value = "accidentCount")
	private float accidentCount = 0;

	@ApiModelProperty(value = "animalCrossingCount", required = false)
	@JsonProperty(value = "animalCrossingCount")
	private float animalCrossingCount = 0;

	@ApiModelProperty(value = "cautionCount", required = false)
	@JsonProperty(value = "cautionCount")
	private float cautionCount = 0;

	@ApiModelProperty(value = "congestionCount", required = false)
	@JsonProperty(value = "congestionCount")
	private float congestionCount = 0;

	@ApiModelProperty(value = "curveCount", required = false)
	@JsonProperty(value = "curveCount")
	private float curveCount = 0;

	@ApiModelProperty(value = "hillCount", required = false)
	@JsonProperty(value = "hillCount")
	private float hillCount = 0;

	@ApiModelProperty(value = "hillDownwardsCount", required = false)
	@JsonProperty(value = "hillDownwardsCount")
	private float hillDownwardsCount = 0;

	@ApiModelProperty(value = "hillUpwardsCount", required = false)
	@JsonProperty(value = "hillUpwardsCount")
	private float hillUpwardsCount = 0;

	@ApiModelProperty(value = "icyConditionsCount", required = false)
	@JsonProperty(value = "icyConditionsCount")
	private float icyConditionsCount = 0;

	@ApiModelProperty(value = "intersectionCount", required = false)
	@JsonProperty(value = "intersectionCount")
	private float intersectionCount = 0;

	@ApiModelProperty(value = "laneMergeCount", required = false)
	@JsonProperty(value = "laneMergeCount")
	private float laneMergeCount = 0;

	@ApiModelProperty(value = "lowGearAreaCount", required = false)
	@JsonProperty(value = "lowGearAreaCount")
	private float lowGearAreaCount = 0;

	@ApiModelProperty(value = "narrowRoadCount", required = false)
	@JsonProperty(value = "narrowRoadCount")
	private float narrowRoadCount = 0;

	@ApiModelProperty(value = "noOvertakingCount", required = false)
	@JsonProperty(value = "noOvertakingCount")
	private float noOvertakingCount = 0;

	@ApiModelProperty(value = "noOvertakingTrucksCount", required = false)
	@JsonProperty(value = "noOvertakingTrucksCount")
	private float noOvertakingTrucksCount = 0;

	@ApiModelProperty(value = "pedestrianCrossingCount", required = false)
	@JsonProperty(value = "pedestrianCrossingCount")
	private float pedestrianCrossingCount = 0;

	@ApiModelProperty(value = "priorityCount", required = false)
	@JsonProperty(value = "priorityCount")
	private float priorityCount = 0;

	@ApiModelProperty(value = "priorityToOncomingTrafficCount", required = false)
	@JsonProperty(value = "priorityToOncomingTrafficCount")
	private float priorityToOncomingTrafficCount = 0;

	@ApiModelProperty(value = "railwayCrossingCount", required = false)
	@JsonProperty(value = "railwayCrossingCount")
	private float railwayCrossingCount = 0;

	@ApiModelProperty(value = "riskOfGroundingCount", required = false)
	@JsonProperty(value = "riskOfGroundingCount")
	private float riskOfGroundingCount = 0;

	@ApiModelProperty(value = "schoolZoneCount", required = false)
	@JsonProperty(value = "schoolZoneCount")
	private float schoolZoneCount = 0;

	@ApiModelProperty(value = "slipperyRoadsCount", required = false)
	@JsonProperty(value = "slipperyRoadsCount")
	private float slipperyRoadsCount = 0;

	@ApiModelProperty(value = "stopSignCount", required = false)
	@JsonProperty(value = "stopSignCount")
	private float stopSignCount = 0;

	@ApiModelProperty(value = "trafficLightCount", required = false)
	@JsonProperty(value = "trafficLightCount")
	private float trafficLightCount = 0;

	@ApiModelProperty(value = "tramwayCrossingCount", required = false)
	@JsonProperty(value = "tramwayCrossingCount")
	private float tramwayCrossingCount = 0;

	@ApiModelProperty(value = "windCount", required = false)
	@JsonProperty(value = "windCount")
	private float windCount = 0;

	@ApiModelProperty(value = "windingRoadCount", required = false)
	@JsonProperty(value = "windingRoadCount")
	private float windingRoadCount = 0;

	@ApiModelProperty(value = "yieldCount", required = false)
	@JsonProperty(value = "yieldCount")
	private float yieldCount = 0;

	@ApiModelProperty(value = "roundAboutCount", required = false)
	@JsonProperty(value = "roundAboutCount")
	private float roundAboutCount = 0;

	@ApiModelProperty(value = "overSpeedCount", required = false)
	@JsonProperty(value = "overSpeedCount")
	private float overSpeedCount = 0;

	@ApiModelProperty(value = "overSpeedDuration", required = false)
	@JsonProperty(value = "overSpeedDuration")
	private float overSpeedDuration = 0;

	@ApiModelProperty(value = "overSpeedDistance", required = false)
	@JsonProperty(value = "overSpeedDistance")
	private float overSpeedDistance = 0;

	@ApiModelProperty(value = "mobileUseIncallCount", required = false)
	@JsonProperty(value = "mobileUseIncallCount")
	private float mobileUseInAcceptedCount = 0;
	
	@ApiModelProperty(value = "mobileUseInAcceptedDuration", required = false)
	@JsonProperty(value = "mobileUseInAcceptedDuration")
	private float mobileUseInAcceptedDuration = 0;
	
	@ApiModelProperty(value = "mobileUseInAcceptedDistance", required = false)
	@JsonProperty(value = "mobileUseInAcceptedDistance")
	private float mobileUseInAcceptedDistance = 0;
	
	@ApiModelProperty(value = "mobileUseOutAcceptedCount", required = false)
	@JsonProperty(value = "mobileUseOutAcceptedCount")
	private float mobileUseOutAcceptedCount = 0;
	
	@ApiModelProperty(value = "mobileUseOutAcceptedDuration", required = false)
	@JsonProperty(value = "mobileUseOutAcceptedDuration")
	private float mobileUseOutAcceptedDuration = 0;
	
	@ApiModelProperty(value = "mobileUseOutAcceptedDistance", required = false)
	@JsonProperty(value = "mobileUseOutAcceptedDistance")
	private float mobileUseOutAcceptedDistance = 0;

	@ApiModelProperty(value = "mobileScreenScreenOnCount", required = false)
	@JsonProperty(value = "mobileScreenScreenOnCount")
	private float mobileScreenScreenOnCount = 0;

	@ApiModelProperty(value = "mobileScreenScreenOnDuration", required = false)
	@JsonProperty(value = "mobileScreenScreenOnDuration")
	private float mobileScreenScreenOnDuration = 0;

	@ApiModelProperty(value = "mobileScreenScreenOnDistance", required = false)
	@JsonProperty(value = "mobileScreenScreenOnDistance")
	private float mobileScreenScreenOnDistance = 0;

	@ApiModelProperty(value = "severeBrakingLowCount", required = false)
	@JsonProperty(value = "severeBrakingLowCount")
	private float severeBrakingLowCount = 0;

	@ApiModelProperty(value = "severeBrakingMediumCount", required = false)
	@JsonProperty(value = "severeBrakingMediumCount")
	private float severeBrakingMediumCount = 0;

	@ApiModelProperty(value = "severeBrakingHighCount", required = false)
	@JsonProperty(value = "severeBrakingHighCount")
	private float severeBrakingHighCount = 0;

	@ApiModelProperty(value = "severeAccelerationLowCount", required = false)
	@JsonProperty(value = "severeAccelerationLowCount")
	private float severeAccelerationLowCount = 0;

	@ApiModelProperty(value = "severeAccelerationMediumCount", required = false)
	@JsonProperty(value = "severeAccelerationMediumCount")
	private float severeAccelerationMediumCount = 0;

	@ApiModelProperty(value = "severeAccelerationHighCount", required = false)
	@JsonProperty(value = "severeAccelerationHighCount")
	private float severeAccelerationHighCount = 0;

	@ApiModelProperty(value = "severeCorneringLowCount", required = false)
	@JsonProperty(value = "severeCorneringLowCount")
	private float severeCorneringLowCount = 0;

	@ApiModelProperty(value = "severeCorneringMediumCount", required = false)
	@JsonProperty(value = "severeCorneringMediumCount")
	private float severeCorneringMediumCount = 0;

	@ApiModelProperty(value = "severeCorneringHighCount", required = false)
	@JsonProperty(value = "severeCorneringHighCount")
	private float severeCorneringHighCount = 0;

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

	@ApiModelProperty(value = "active", hidden = true)
    @JsonProperty(value = "active")
    private String active = "";

	@ApiModelProperty(value = "createdAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "createdAt")
    private Date createdAt = null;

	@ApiModelProperty(value = "modifiedAt", hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GC_DATE_TIME_FORMAT)
    @JsonProperty(value = "modifiedAt")
    private Date modifiedAt = null;

	@ApiModelProperty(value = "createdBy", hidden = true)
    @JsonProperty(value = "createdBy")
    private String createdBy = "";

	@ApiModelProperty(value = "modifiedBy", hidden = true)
    @JsonProperty(value = "modifiedBy")
    private String modifiedBy = "";

	public DeviceSummary() {
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

	public float getAccidentCount() {
		return accidentCount;
	}

	public void setAccidentCount(float accidentCount) {
		this.accidentCount = accidentCount;
	}

	public float getAnimalCrossingCount() {
		return animalCrossingCount;
	}

	public void setAnimalCrossingCount(float animalCrossingCount) {
		this.animalCrossingCount = animalCrossingCount;
	}

	public float getCautionCount() {
		return cautionCount;
	}

	public void setCautionCount(float cautionCount) {
		this.cautionCount = cautionCount;
	}

	public float getCongestionCount() {
		return congestionCount;
	}

	public void setCongestionCount(float congestionCount) {
		this.congestionCount = congestionCount;
	}

	public float getCurveCount() {
		return curveCount;
	}

	public void setCurveCount(float curveCount) {
		this.curveCount = curveCount;
	}

	public float getHillCount() {
		return hillCount;
	}

	public void setHillCount(float hillCount) {
		this.hillCount = hillCount;
	}

	public float getHillDownwardsCount() {
		return hillDownwardsCount;
	}

	public void setHillDownwardsCount(float hillDownwardsCount) {
		this.hillDownwardsCount = hillDownwardsCount;
	}

	public float getHillUpwardsCount() {
		return hillUpwardsCount;
	}

	public void setHillUpwardsCount(float hillUpwardsCount) {
		this.hillUpwardsCount = hillUpwardsCount;
	}

	public float getIcyConditionsCount() {
		return icyConditionsCount;
	}

	public void setIcyConditionsCount(float icyConditionsCount) {
		this.icyConditionsCount = icyConditionsCount;
	}

	public float getIntersectionCount() {
		return intersectionCount;
	}

	public void setIntersectionCount(float intersectionCount) {
		this.intersectionCount = intersectionCount;
	}

	public float getLaneMergeCount() {
		return laneMergeCount;
	}

	public void setLaneMergeCount(float laneMergeCount) {
		this.laneMergeCount = laneMergeCount;
	}

	public float getLowGearAreaCount() {
		return lowGearAreaCount;
	}

	public void setLowGearAreaCount(float lowGearAreaCount) {
		this.lowGearAreaCount = lowGearAreaCount;
	}

	public float getNarrowRoadCount() {
		return narrowRoadCount;
	}

	public void setNarrowRoadCount(float narrowRoadCount) {
		this.narrowRoadCount = narrowRoadCount;
	}

	public float getNoOvertakingCount() {
		return noOvertakingCount;
	}

	public void setNoOvertakingCount(float noOvertakingCount) {
		this.noOvertakingCount = noOvertakingCount;
	}

	public float getNoOvertakingTrucksCount() {
		return noOvertakingTrucksCount;
	}

	public void setNoOvertakingTrucksCount(float noOvertakingTrucksCount) {
		this.noOvertakingTrucksCount = noOvertakingTrucksCount;
	}

	public float getPedestrianCrossingCount() {
		return pedestrianCrossingCount;
	}

	public void setPedestrianCrossingCount(float pedestrianCrossingCount) {
		this.pedestrianCrossingCount = pedestrianCrossingCount;
	}

	public float getPriorityCount() {
		return priorityCount;
	}

	public void setPriorityCount(float priorityCount) {
		this.priorityCount = priorityCount;
	}

	public float getPriorityToOncomingTrafficCount() {
		return priorityToOncomingTrafficCount;
	}

	public void setPriorityToOncomingTrafficCount(float priorityToOncomingTrafficCount) {
		this.priorityToOncomingTrafficCount = priorityToOncomingTrafficCount;
	}

	public float getRailwayCrossingCount() {
		return railwayCrossingCount;
	}

	public void setRailwayCrossingCount(float railwayCrossingCount) {
		this.railwayCrossingCount = railwayCrossingCount;
	}

	public float getRiskOfGroundingCount() {
		return riskOfGroundingCount;
	}

	public void setRiskOfGroundingCount(float riskOfGroundingCount) {
		this.riskOfGroundingCount = riskOfGroundingCount;
	}

	public float getSchoolZoneCount() {
		return schoolZoneCount;
	}

	public void setSchoolZoneCount(float schoolZoneCount) {
		this.schoolZoneCount = schoolZoneCount;
	}

	public float getSlipperyRoadsCount() {
		return slipperyRoadsCount;
	}

	public void setSlipperyRoadsCount(float slipperyRoadsCount) {
		this.slipperyRoadsCount = slipperyRoadsCount;
	}

	public float getStopSignCount() {
		return stopSignCount;
	}

	public void setStopSignCount(float stopSignCount) {
		this.stopSignCount = stopSignCount;
	}

	public float getTrafficLightCount() {
		return trafficLightCount;
	}

	public void setTrafficLightCount(float trafficLightCount) {
		this.trafficLightCount = trafficLightCount;
	}

	public float getTramwayCrossingCount() {
		return tramwayCrossingCount;
	}

	public void setTramwayCrossingCount(float tramwayCrossingCount) {
		this.tramwayCrossingCount = tramwayCrossingCount;
	}

	public float getWindCount() {
		return windCount;
	}

	public void setWindCount(float windCount) {
		this.windCount = windCount;
	}

	public float getWindingRoadCount() {
		return windingRoadCount;
	}

	public void setWindingRoadCount(float windingRoadCount) {
		this.windingRoadCount = windingRoadCount;
	}

	public float getYieldCount() {
		return yieldCount;
	}

	public void setYieldCount(float yieldCount) {
		this.yieldCount = yieldCount;
	}

	public float getRoundAboutCount() {
		return roundAboutCount;
	}

	public void setRoundAboutCount(float roundAboutCount) {
		this.roundAboutCount = roundAboutCount;
	}

	public float getOverSpeedCount() {
		return overSpeedCount;
	}

	public void setOverSpeedCount(float overSpeedCount) {
		this.overSpeedCount = overSpeedCount;
	}

	public float getOverSpeedDuration() {
		return overSpeedDuration;
	}

	public void setOverSpeedDuration(float overSpeedDuration) {
		this.overSpeedDuration = overSpeedDuration;
	}

	public float getOverSpeedDistance() {
		return overSpeedDistance;
	}

	public void setOverSpeedDistance(float overSpeedDistance) {
		this.overSpeedDistance = overSpeedDistance;
	}

	public float getMobileUseInAcceptedCount() {
		return mobileUseInAcceptedCount;
	}

	public void setMobileUseInAcceptedCount(float mobileUseInAcceptedCount) {
		this.mobileUseInAcceptedCount = mobileUseInAcceptedCount;
	}

	public float getMobileUseInAcceptedDuration() {
		return mobileUseInAcceptedDuration;
	}

	public void setMobileUseInAcceptedDuration(float mobileUseInAcceptedDuration) {
		this.mobileUseInAcceptedDuration = mobileUseInAcceptedDuration;
	}

	public float getMobileUseInAcceptedDistance() {
		return mobileUseInAcceptedDistance;
	}

	public void setMobileUseInAcceptedDistance(float mobileUseInAcceptedDistance) {
		this.mobileUseInAcceptedDistance = mobileUseInAcceptedDistance;
	}

	public float getMobileUseOutAcceptedCount() {
		return mobileUseOutAcceptedCount;
	}

	public void setMobileUseOutAcceptedCount(float mobileUseOutAcceptedCount) {
		this.mobileUseOutAcceptedCount = mobileUseOutAcceptedCount;
	}

	public float getMobileUseOutAcceptedDuration() {
		return mobileUseOutAcceptedDuration;
	}

	public void setMobileUseOutAcceptedDuration(float mobileUseOutAcceptedDuration) {
		this.mobileUseOutAcceptedDuration = mobileUseOutAcceptedDuration;
	}

	public float getMobileUseOutAcceptedDistance() {
		return mobileUseOutAcceptedDistance;
	}

	public void setMobileUseOutAcceptedDistance(float mobileUseOutAcceptedDistance) {
		this.mobileUseOutAcceptedDistance = mobileUseOutAcceptedDistance;
	}

	public float getMobileScreenScreenOnCount() {
		return mobileScreenScreenOnCount;
	}

	public void setMobileScreenScreenOnCount(float mobileScreenScreenOnCount) {
		this.mobileScreenScreenOnCount = mobileScreenScreenOnCount;
	}

	public float getMobileScreenScreenOnDuration() {
		return mobileScreenScreenOnDuration;
	}

	public void setMobileScreenScreenOnDuration(float mobileScreenScreenOnDuration) {
		this.mobileScreenScreenOnDuration = mobileScreenScreenOnDuration;
	}

	public float getMobileScreenScreenOnDistance() {
		return mobileScreenScreenOnDistance;
	}

	public void setMobileScreenScreenOnDistance(float mobileScreenScreenOnDistance) {
		this.mobileScreenScreenOnDistance = mobileScreenScreenOnDistance;
	}

	public float getSevereBrakingLowCount() {
		return severeBrakingLowCount;
	}

	public void setSevereBrakingLowCount(float severeBrakingLowCount) {
		this.severeBrakingLowCount = severeBrakingLowCount;
	}

	public float getSevereBrakingMediumCount() {
		return severeBrakingMediumCount;
	}

	public void setSevereBrakingMediumCount(float severeBrakingMediumCount) {
		this.severeBrakingMediumCount = severeBrakingMediumCount;
	}

	public float getSevereBrakingHighCount() {
		return severeBrakingHighCount;
	}

	public void setSevereBrakingHighCount(float severeBrakingHighCount) {
		this.severeBrakingHighCount = severeBrakingHighCount;
	}

	public float getSevereAccelerationLowCount() {
		return severeAccelerationLowCount;
	}

	public void setSevereAccelerationLowCount(float severeAccelerationLowCount) {
		this.severeAccelerationLowCount = severeAccelerationLowCount;
	}

	public float getSevereAccelerationMediumCount() {
		return severeAccelerationMediumCount;
	}

	public void setSevereAccelerationMediumCount(float severeAccelerationMediumCount) {
		this.severeAccelerationMediumCount = severeAccelerationMediumCount;
	}

	public float getSevereAccelerationHighCount() {
		return severeAccelerationHighCount;
	}

	public void setSevereAccelerationHighCount(float severeAccelerationHighCount) {
		this.severeAccelerationHighCount = severeAccelerationHighCount;
	}

	public float getSevereCorneringLowCount() {
		return severeCorneringLowCount;
	}

	public void setSevereCorneringLowCount(float severeCorneringLowCount) {
		this.severeCorneringLowCount = severeCorneringLowCount;
	}

	public float getSevereCorneringMediumCount() {
		return severeCorneringMediumCount;
	}

	public void setSevereCorneringMediumCount(float severeCorneringMediumCount) {
		this.severeCorneringMediumCount = severeCorneringMediumCount;
	}

	public float getSevereCorneringHighCount() {
		return severeCorneringHighCount;
	}

	public void setSevereCorneringHighCount(float severeCorneringHighCount) {
		this.severeCorneringHighCount = severeCorneringHighCount;
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

	@Override
	public String toString() {
		return "DeviceSummary [id=" + id + ", companyId=" + companyId + ", groupId=" + groupId + ", divisionId="
				+ divisionId + ", moduleId=" + moduleId + ", userId=" + userId + ", propertyId=" + propertyId
				+ ", sectionId=" + sectionId + ", portionId=" + portionId + ", deviceId=" + deviceId
				+ ", accidentCount=" + accidentCount + ", animalCrossingCount=" + animalCrossingCount
				+ ", cautionCount=" + cautionCount + ", congestionCount=" + congestionCount + ", curveCount="
				+ curveCount + ", hillCount=" + hillCount + ", hillDownwardsCount=" + hillDownwardsCount
				+ ", hillUpwardsCount=" + hillUpwardsCount + ", icyConditionsCount=" + icyConditionsCount
				+ ", intersectionCount=" + intersectionCount + ", laneMergeCount=" + laneMergeCount
				+ ", lowGearAreaCount=" + lowGearAreaCount + ", narrowRoadCount=" + narrowRoadCount
				+ ", noOvertakingCount=" + noOvertakingCount + ", noOvertakingTrucksCount=" + noOvertakingTrucksCount
				+ ", pedestrianCrossingCount=" + pedestrianCrossingCount + ", priorityCount=" + priorityCount
				+ ", priorityToOncomingTrafficCount=" + priorityToOncomingTrafficCount + ", railwayCrossingCount="
				+ railwayCrossingCount + ", riskOfGroundingCount=" + riskOfGroundingCount + ", schoolZoneCount="
				+ schoolZoneCount + ", slipperyRoadsCount=" + slipperyRoadsCount + ", stopSignCount=" + stopSignCount
				+ ", trafficLightCount=" + trafficLightCount + ", tramwayCrossingCount=" + tramwayCrossingCount
				+ ", windCount=" + windCount + ", windingRoadCount=" + windingRoadCount + ", yieldCount=" + yieldCount
				+ ", roundAboutCount=" + roundAboutCount + ", overSpeedCount=" + overSpeedCount + ", overSpeedDuration="
				+ overSpeedDuration + ", overSpeedDistance=" + overSpeedDistance + ", mobileUseInAcceptedCount="
				+ mobileUseInAcceptedCount + ", mobileUseInAcceptedDuration=" + mobileUseInAcceptedDuration
				+ ", mobileUseInAcceptedDistance=" + mobileUseInAcceptedDistance + ", mobileUseOutAcceptedCount="
				+ mobileUseOutAcceptedCount + ", mobileUseOutAcceptedDuration=" + mobileUseOutAcceptedDuration
				+ ", mobileUseOutAcceptedDistance=" + mobileUseOutAcceptedDistance + ", mobileScreenScreenOnCount="
				+ mobileScreenScreenOnCount + ", mobileScreenScreenOnDuration=" + mobileScreenScreenOnDuration
				+ ", mobileScreenScreenOnDistance=" + mobileScreenScreenOnDistance + ", severeBrakingLowCount="
				+ severeBrakingLowCount + ", severeBrakingMediumCount=" + severeBrakingMediumCount
				+ ", severeBrakingHighCount=" + severeBrakingHighCount + ", severeAccelerationLowCount="
				+ severeAccelerationLowCount + ", severeAccelerationMediumCount=" + severeAccelerationMediumCount
				+ ", severeAccelerationHighCount=" + severeAccelerationHighCount + ", severeCorneringLowCount="
				+ severeCorneringLowCount + ", severeCorneringMediumCount=" + severeCorneringMediumCount
				+ ", severeCorneringHighCount=" + severeCorneringHighCount + ", type=" + type + ", status=" + status
				+ ", active=" + active + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", createdBy="
				+ createdBy + ", modifiedBy=" + modifiedBy + "]";
	}
	
	
}