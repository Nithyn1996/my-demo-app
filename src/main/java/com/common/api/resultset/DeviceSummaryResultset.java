package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.DeviceSummary;
import com.common.api.util.ResultSetConversion;

public class DeviceSummaryResultset extends ResultSetConversion implements RowMapper<DeviceSummary>{

	@Override
	public DeviceSummary mapRow(ResultSet rs, int rowNum) throws SQLException {

		DeviceSummary deviceSummary = new DeviceSummary();
		try {
			deviceSummary.setId(resultSetToString(rs, "id"));
			deviceSummary.setCompanyId(resultSetToString(rs, "company_id"));
			deviceSummary.setGroupId(resultSetToString(rs, "group_id"));
    		deviceSummary.setDivisionId(resultSetToString(rs, "division_id"));
    		deviceSummary.setModuleId(resultSetToString(rs, "module_id"));
    		deviceSummary.setUserId(resultSetToString(rs, "user_id"));
    		deviceSummary.setPropertyId(resultSetToString(rs, "property_id"));
    		deviceSummary.setSectionId(resultSetToString(rs, "section_id"));
    		deviceSummary.setPortionId(resultSetToString(rs, "portion_id"));
    		deviceSummary.setDeviceId(resultSetToString(rs, "device_id"));

    		deviceSummary.setAccidentCount(resultSetToFloat(rs, "accident_count"));
    		deviceSummary.setAnimalCrossingCount(resultSetToFloat(rs, "animal_crossing_count"));
    		deviceSummary.setCautionCount(resultSetToFloat(rs, "caution_count"));
    		deviceSummary.setCongestionCount(resultSetToFloat(rs, "congestion_count"));
    		deviceSummary.setCurveCount(resultSetToFloat(rs, "curve_count"));
    		deviceSummary.setHillCount(resultSetToFloat(rs, "hill_count"));
    		deviceSummary.setHillDownwardsCount(resultSetToFloat(rs, "hill_downwards_count"));
    		deviceSummary.setHillUpwardsCount(resultSetToFloat(rs, "hill_upwards_count"));
    		deviceSummary.setIcyConditionsCount(resultSetToFloat(rs, "icy_conditions_count"));
    		deviceSummary.setIntersectionCount(resultSetToFloat(rs, "intersection_count"));
    		deviceSummary.setLaneMergeCount(resultSetToFloat(rs, "lane_merge_count"));
    		deviceSummary.setLowGearAreaCount(resultSetToFloat(rs, "low_gear_area_count"));
    		deviceSummary.setNarrowRoadCount(resultSetToFloat(rs, "narrow_road_count"));
    		deviceSummary.setNoOvertakingCount(resultSetToFloat(rs, "no_overtaking_count"));
    		deviceSummary.setNoOvertakingTrucksCount(resultSetToFloat(rs, "no_overtaking_trucks_count"));
    		deviceSummary.setPedestrianCrossingCount(resultSetToFloat(rs, "pedestrian_crossing_count"));
    		deviceSummary.setPriorityCount(resultSetToFloat(rs, "priority_count"));
    		deviceSummary.setPriorityToOncomingTrafficCount(resultSetToFloat(rs, "priority_to_oncoming_traffic_count"));
    		deviceSummary.setRailwayCrossingCount(resultSetToFloat(rs, "railway_crossing_count"));
    		deviceSummary.setRiskOfGroundingCount(resultSetToFloat(rs, "risk_of_grounding_count"));
    		deviceSummary.setSchoolZoneCount(resultSetToFloat(rs, "school_zone_count"));
    		deviceSummary.setSlipperyRoadsCount(resultSetToFloat(rs, "slippery_roads_count"));
    		deviceSummary.setStopSignCount(resultSetToFloat(rs, "stop_sign_count"));
    		deviceSummary.setTrafficLightCount(resultSetToFloat(rs, "traffic_light_count"));
    		deviceSummary.setTramwayCrossingCount(resultSetToFloat(rs, "tramway_crossing_count"));
    		deviceSummary.setWindCount(resultSetToFloat(rs, "wind_count"));
    		deviceSummary.setWindingRoadCount(resultSetToFloat(rs, "winding_road_count"));
    		deviceSummary.setYieldCount(resultSetToFloat(rs, "yield_count"));
    		deviceSummary.setRoundAboutCount(resultSetToFloat(rs, "round_about_count"));
    		deviceSummary.setOverSpeedCount(resultSetToFloat(rs, "over_speed_count"));
    		deviceSummary.setOverSpeedDuration(resultSetToFloat(rs, "over_speed_duration"));
    		deviceSummary.setOverSpeedDistance(resultSetToFloat(rs, "over_speed_distance"));
    		deviceSummary.setMobileUseInAcceptedCount(resultSetToFloat(rs, "mobile_use_in_accepted_count"));
    		deviceSummary.setMobileUseInAcceptedDuration(resultSetToFloat(rs, "mobile_use_in_accepted_duration"));
    		deviceSummary.setMobileUseInAcceptedDistance(resultSetToFloat(rs, "mobile_use_in_accepted_distance"));
    		deviceSummary.setMobileUseOutAcceptedCount(resultSetToFloat(rs, "mobile_use_out_accepted_count"));
    		deviceSummary.setMobileUseOutAcceptedDuration(resultSetToFloat(rs, "mobile_use_out_accepted_duration"));
    		deviceSummary.setMobileUseOutAcceptedDistance(resultSetToFloat(rs, "mobile_use_out_accepted_distance"));
    		deviceSummary.setMobileScreenScreenOnCount(resultSetToFloat(rs, "mobile_screen_screen_on_count"));
    		deviceSummary.setMobileScreenScreenOnDuration(resultSetToFloat(rs, "mobile_screen_screen_on_duration"));
    		deviceSummary.setMobileScreenScreenOnDistance(resultSetToFloat(rs, "mobile_screen_screen_on_distance"));
    		deviceSummary.setSevereBrakingLowCount(resultSetToFloat(rs, "severe_braking_low_count"));
    		deviceSummary.setSevereBrakingMediumCount(resultSetToFloat(rs, "severe_braking_medium_count"));
    		deviceSummary.setSevereBrakingHighCount(resultSetToFloat(rs, "severe_braking_high_count"));
    		deviceSummary.setSevereAccelerationLowCount(resultSetToFloat(rs, "severe_acceleration_low_count"));
    		deviceSummary.setSevereAccelerationMediumCount(resultSetToFloat(rs, "severe_acceleration_medium_count"));
    		deviceSummary.setSevereAccelerationHighCount(resultSetToFloat(rs, "severe_acceleration_high_count"));
    		deviceSummary.setSevereCorneringLowCount(resultSetToFloat(rs, "severe_cornering_low_count"));
    		deviceSummary.setSevereCorneringMediumCount(resultSetToFloat(rs, "severe_cornering_medium_count"));
    		deviceSummary.setSevereCorneringHighCount(resultSetToFloat(rs, "severe_cornering_high_count"));

    		deviceSummary.setType(resultSetToString(rs, "type"));
    		deviceSummary.setStatus(resultSetToString(rs, "status"));
    		deviceSummary.setActive(resultSetToString(rs, "active"));
    		deviceSummary.setCreatedBy(resultSetToString(rs, "created_by"));
    		deviceSummary.setModifiedBy(resultSetToString(rs, "modified_by"));
    		deviceSummary.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		deviceSummary.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

		}catch (Exception errMess) {
		}

		return deviceSummary;
	}

}
