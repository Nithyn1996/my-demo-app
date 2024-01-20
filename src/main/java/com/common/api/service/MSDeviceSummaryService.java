package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.DeviceSummaryService;
import com.common.api.model.DeviceSummary;
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.KeyValuesFloat;
import com.common.api.resultset.DeviceSummaryResultset;
import com.common.api.util.PayloadValidator;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSDeviceSummaryService extends APIFixedConstant implements DeviceSummaryService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired 
	PayloadValidator payloadValidator;

	@Override
	public List<DeviceSummary> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId,
			String userId, String propertyId, String sectionId, String portionId, String deviceId, String id,
			List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String selQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(company_id) = LOWER(:companyId) ";
			parameters.addValue("companyId", companyId);
		}
		if (groupId != null && groupId.length() > 0) {
			selQuery = selQuery + " AND LOWER(group_id) = LOWER(:groupId) ";
			parameters.addValue("groupId", groupId);
		}
		if (divisionId != null && divisionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);
		}
		if (moduleId != null && moduleId.length() > 0) {
			selQuery = selQuery + " AND LOWER(module_id) = LOWER(:moduleId) ";
			parameters.addValue("moduleId", moduleId);
		}
		if (userId != null && userId.length() > 0) {
			selQuery = selQuery + " AND LOWER(user_id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (propertyId != null && propertyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(property_id) = LOWER(:propertyId) ";
			parameters.addValue("propertyId", propertyId);
		}
		if (sectionId != null && sectionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(section_id) = LOWER(:sectionId) ";
			parameters.addValue("sectionId", sectionId);
		}
		if (portionId != null && portionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(portion_id) = LOWER(:portionId) ";
			parameters.addValue("portionId", portionId);
		}
		if (deviceId != null && deviceId.length() > 0) {
			selQuery = selQuery + " AND LOWER(device_id) = LOWER(:deviceId) ";
			parameters.addValue("deviceId", deviceId);
		}
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (statusList != null && statusList.size() > 0) {
			String statusListTemp = resultSetConversion.stringListToCommaAndQuoteString(statusList);
			selQuery = selQuery + " AND status in (" + statusListTemp + ")";
		}
		if (typeList != null && typeList.size() > 0) {
			String typeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList);
			selQuery = selQuery + " AND type in (" + typeListTemp + ")";
		}

		if (selQuery.length() > 0) {

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;
			selQuery = selQuery + " OFFSET :offset ROWS";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			selQuery = "select * from " + TB_DEVICE_SUMMARY + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new DeviceSummaryResultset());
		}
		return new ArrayList<>();
	}
	
	@Override
	public DeviceSummary createDeviceSummary(DeviceSummary deviceSummary) {
	
		String objectId = Util.getTableOrCollectionObjectId();
		deviceSummary.setId(objectId);
		
		String insertQuery = "insert into " + TB_DEVICE_SUMMARY 
				+ " (id, company_id, group_id, division_id, " 
				+ " module_id, user_id, property_id, section_id, " 
				+ " portion_id, device_id, accident_count, " 
				+ " animal_crossing_count, caution_count, congestion_count, " 
				+ " curve_count, hill_count, hill_downwards_count, " 
				+ " hill_upwards_count, icy_conditions_count, intersection_count," 
				+ " lane_merge_count, low_gear_area_count, narrow_road_count, " 
				+ " no_overtaking_count, no_overtaking_trucks_count, pedestrian_crossing_count, " 
				+ " priority_count, priority_to_oncoming_traffic_count, railway_crossing_count, " 
				+ " risk_of_grounding_count, school_zone_count, slippery_roads_count, " 
	            + " stop_sign_count, traffic_light_count, tramway_crossing_count, " 
	            + " wind_count, winding_road_count, yield_count, round_about_count, " 
	            + " over_speed_count, over_speed_duration, over_speed_distance, " 
	            + " mobile_use_in_accepted_count, mobile_use_out_accepted_count, mobile_use_in_accepted_duration, " 
	            + " mobile_use_out_accepted_duration, mobile_use_in_accepted_distance, mobile_use_out_accepted_distance, " 
	            + " mobile_screen_screen_on_count, mobile_screen_screen_on_duration, mobile_screen_screen_on_distance, " 
	            + " severe_braking_low_count, severe_braking_medium_count, severe_braking_high_count, " 
	            + " severe_acceleration_low_count, severe_acceleration_medium_count, severe_acceleration_high_count, " 
	            + " severe_cornering_low_count, severe_cornering_medium_count, severe_cornering_high_count, " 
	            + " type, status, created_at, modified_at, created_by, modified_by) " 
	+ " values "
	            + " (:id, :companyId, :groupId, :divisionId, " 
	            + " :moduleId, :userId, :propertyId, :sectionId, " +
	            " :portionId, :deviceId, :accidentCount, " +
	            " :animalCrossingCount, :cautionCount, :congestionCount, " +
	            " :curveCount, :hillCount, :hillDownwardsCount, " +
	            " :hillUpwardsCount, :icyConditionsCount, :intersectionCount," +
	            " :laneMergeCount, :lowGearAreaCount, :narrowRoadCount, " +
	            " :noOvertakingCount, :noOvertakingTrucksCount, :pedestrianCrossingCount, " +
	            " :priorityCount, :priorityToOncomingTrafficCount, :railwayCrossingCount, " +
	            " :riskOfGroundingCount, :schoolZoneCount, :slipperyRoadsCount, " +
	            " :stopSignCount, :trafficLightCount, :tramwayCrossingCount, " +
	            " :windCount, :windingRoadCount, :yieldCount, :roundAboutCount, " +
	            " :overSpeedCount, :overSpeedDuration, :overSpeedDistance, " +
	            " :mobileUseInAcceptedCount, :mobileUseOutAcceptedCount, :mobileUseInAcceptedDuration, " +
	            " :mobileUseOutAcceptedDuration, :mobileUseInAcceptedDistance, :mobileUseOutAcceptedDistance, " +
	            " :mobileScreenScreenOnCount, :mobileScreenScreenOnDuration, :mobileScreenScreenOnDistance, " +
	            " :severeBrakingLowCount, :severeBrakingMediumCount, :severeBrakingHighCount, " +
	            " :severeAccelerationLowCount, :severeAccelerationMediumCount, :severeAccelerationHighCount, " +
	            " :severeCorneringLowCount, :severeCorneringMediumCount, :severeCorneringHighCount, " +
	            " :type, :status, :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		
		MapSqlParameterSource parameters = new MapSqlParameterSource()
		        .addValue("id", deviceSummary.getId())
		        .addValue("companyId", deviceSummary.getCompanyId())
		        .addValue("groupId", deviceSummary.getGroupId())
		        .addValue("divisionId", deviceSummary.getDivisionId())
		        .addValue("moduleId", deviceSummary.getModuleId())
		        .addValue("userId", deviceSummary.getUserId())
		        .addValue("propertyId", deviceSummary.getPropertyId())
		        .addValue("sectionId", deviceSummary.getSectionId())
		        .addValue("portionId", deviceSummary.getPortionId())
		        .addValue("deviceId", deviceSummary.getDeviceId())
		        .addValue("accidentCount", deviceSummary.getAccidentCount())
				.addValue("animalCrossingCount", deviceSummary.getAnimalCrossingCount())
				.addValue("cautionCount", deviceSummary.getCautionCount())
				.addValue("congestionCount", deviceSummary.getCongestionCount())
				.addValue("curveCount", deviceSummary.getCurveCount())
				.addValue("hillCount", deviceSummary.getHillCount())
				.addValue("hillDownwardsCount", deviceSummary.getHillDownwardsCount())
				.addValue("hillUpwardsCount", deviceSummary.getHillUpwardsCount())
				.addValue("icyConditionsCount", deviceSummary.getIcyConditionsCount())
				.addValue("intersectionCount", deviceSummary.getIntersectionCount())
				.addValue("laneMergeCount", deviceSummary.getLaneMergeCount())
				.addValue("lowGearAreaCount", deviceSummary.getLowGearAreaCount())
				.addValue("narrowRoadCount", deviceSummary.getNarrowRoadCount())
				.addValue("noOvertakingCount", deviceSummary.getNoOvertakingCount())
				.addValue("noOvertakingTrucksCount", deviceSummary.getNoOvertakingTrucksCount())
				.addValue("pedestrianCrossingCount", deviceSummary.getPedestrianCrossingCount())
				.addValue("priorityCount", deviceSummary.getPriorityCount())
				.addValue("priorityToOncomingTrafficCount", deviceSummary.getPriorityToOncomingTrafficCount())
				.addValue("railwayCrossingCount", deviceSummary.getRailwayCrossingCount())
				.addValue("riskOfGroundingCount", deviceSummary.getRiskOfGroundingCount())
				.addValue("schoolZoneCount", deviceSummary.getSchoolZoneCount())
				.addValue("slipperyRoadsCount", deviceSummary.getSlipperyRoadsCount())
				.addValue("stopSignCount", deviceSummary.getStopSignCount())
				.addValue("trafficLightCount", deviceSummary.getTrafficLightCount())
				.addValue("tramwayCrossingCount", deviceSummary.getTramwayCrossingCount())
				.addValue("windCount", deviceSummary.getWindCount())
				.addValue("windingRoadCount", deviceSummary.getWindingRoadCount())
				.addValue("yieldCount", deviceSummary.getYieldCount())
				.addValue("roundAboutCount", deviceSummary.getRoundAboutCount())
				.addValue("overSpeedCount", deviceSummary.getOverSpeedCount())
				.addValue("overSpeedDuration", deviceSummary.getOverSpeedDuration())
				.addValue("overSpeedDistance", deviceSummary.getOverSpeedDistance())
				.addValue("mobileUseInAcceptedCount", deviceSummary.getMobileUseInAcceptedCount())
				.addValue("mobileUseOutAcceptedCount", deviceSummary.getMobileUseOutAcceptedCount())
				.addValue("mobileUseInAcceptedDuration", deviceSummary.getMobileUseInAcceptedDuration())
				.addValue("mobileUseOutAcceptedDuration", deviceSummary.getMobileUseOutAcceptedDuration())
				.addValue("mobileUseInAcceptedDistance", deviceSummary.getMobileUseInAcceptedDistance())
				.addValue("mobileUseOutAcceptedDistance", deviceSummary.getMobileUseOutAcceptedDistance())
				.addValue("mobileScreenScreenOnCount", deviceSummary.getMobileScreenScreenOnCount())
				.addValue("mobileScreenScreenOnDuration", deviceSummary.getMobileScreenScreenOnDuration())
				.addValue("mobileScreenScreenOnDistance", deviceSummary.getMobileScreenScreenOnDistance())
				.addValue("severeBrakingLowCount", deviceSummary.getSevereBrakingLowCount())
				.addValue("severeBrakingMediumCount", deviceSummary.getSevereBrakingMediumCount())
				.addValue("severeBrakingHighCount", deviceSummary.getSevereBrakingHighCount())
				.addValue("severeAccelerationLowCount", deviceSummary.getSevereAccelerationLowCount())
				.addValue("severeAccelerationMediumCount", deviceSummary.getSevereAccelerationMediumCount())
				.addValue("severeAccelerationHighCount", deviceSummary.getSevereAccelerationHighCount())
				.addValue("severeCorneringLowCount", deviceSummary.getSevereCorneringLowCount())
				.addValue("severeCorneringMediumCount", deviceSummary.getSevereCorneringMediumCount())
				.addValue("severeCorneringHighCount", deviceSummary.getSevereCorneringHighCount())
		        .addValue("type", deviceSummary.getType())
		        .addValue("status", deviceSummary.getStatus())
		        .addValue("createdAt", deviceSummary.getCreatedAt())
		        .addValue("modifiedAt", deviceSummary.getModifiedAt())
		        .addValue("createdBy", deviceSummary.getCreatedBy())
		        .addValue("modifiedBy", deviceSummary.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);
		
		if(status > 0) {
			return deviceSummary;
		}		
		return new DeviceSummary();
		
	}

	@Override
	public DeviceSummary updateDeviceSummary(DeviceSummary deviceSummary){

		String updateQuery = "update " + TB_DEVICE_SUMMARY
		        				+ " set "
							        + " accident_count = :accidentCount, "
							        + " animal_crossing_count = :animalCrossingCount, "
							        + " caution_count = :cautionCount, "
							        + " congestion_count = :congestionCount, "
							        + " curve_count = :curveCount, "
							        + " hill_count = :hillCount, "
							        + " hill_downwards_count = :hillDownwardsCount, "
							        + " hill_upwards_count = :hillUpwardsCount, "
							        + " icy_conditions_count = :icyConditionsCount, "
							        + " intersection_count = :intersectionCount, "
							        + " lane_merge_count = :laneMergeCount, "
							        + " low_gear_area_count = :lowGearAreaCount, "
							        + " narrow_road_count = :narrowRoadCount, "
							        + " no_overtaking_count = :noOvertakingCount, "
							        + " no_overtaking_trucks_count = :noOvertakingTrucksCount, "
							        + " pedestrian_crossing_count = :pedestrianCrossingCount, "
							        + " priority_count = :priorityCount, "
							        + " priority_to_oncoming_traffic_count = :priorityToOncomingTrafficCount, "
							        + " railway_crossing_count = :railwayCrossingCount, "
							        + " risk_of_grounding_count = :riskOfGroundingCount, "
							        + " school_zone_count = :schoolZoneCount, "
							        + " slippery_roads_count = :slipperyRoadsCount, "
							        + " stop_sign_count = :stopSignCount, "
							        + " traffic_light_count = :trafficLightCount, "
							        + " tramway_crossing_count = :tramwayCrossingCount, "
							        + " wind_count = :windCount, "
							        + " winding_road_count = :windingRoadCount, "
							        + " yield_count = :yieldCount, "
							        + " round_about_count = :roundAboutCount, "
							        + " over_speed_count = :overSpeedCount, "
							        + " over_speed_duration = :overSpeedDuration, "
							        + " over_speed_distance = :overSpeedDistance, "
							        + " mobile_use_in_accepted_count = :mobileUseInAcceptedCount, "
							        + " mobile_use_out_accepted_count = :mobileUseOutAcceptedCount, "
							        + " mobile_use_in_accepted_duration = :mobileUseInAcceptedDuration, "
							        + " mobile_use_out_accepted_duration = :mobileUseOutAcceptedDuration, "
							        + " mobile_use_in_accepted_distance = :mobileUseInAcceptedDistance, "								      
							        + " mobile_use_out_accepted_distance = :mobileUseOutAcceptedDistance, "
							        + " mobile_screen_screen_on_count = :mobileScreenScreenOnCount, "
							        + " mobile_screen_screen_on_duration = :mobileScreenScreenOnDuration, "
							        + " mobile_screen_screen_on_distance = :mobileScreenScreenOnDistance, "
							        + " severe_braking_low_count = :severeBrakingLowCount, "
							        + " severe_braking_medium_count = :severeBrakingMediumCount, "
							        + " severe_braking_high_count = :severeBrakingHighCount, "
							        + " severe_acceleration_low_count = :severeAccelerationLowCount, "
							        + " severe_acceleration_medium_count = :severeAccelerationMediumCount, "
							        + " severe_acceleration_high_count = :severeAccelerationHighCount, "
							        + " severe_cornering_low_count = :severeCorneringLowCount, "
							        + " severe_cornering_medium_count = :severeCorneringMediumCount, "
							        + " severe_cornering_high_count = :severeCorneringHighCount, "
							        + " type = :type, "
							        + " status = :status, "
							        + " modified_at = :modifiedAt, "
							        + " modified_by = :modifiedBy "
							  + " where "
									+ " id = :id AND "
									+ " company_id = :companyId AND "
									+ " group_id = :groupId AND "
									+ " division_id = :divisionId AND "
									+ " module_id = :moduleId AND "
									+ " user_id = :userId AND "
									+ " property_id = :propertyId AND "
									+ " section_id = :sectionId AND "
									+ " portion_id = :portionId AND "
									+ " device_id = :deviceId ";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", deviceSummary.getId())
		        .addValue("companyId", deviceSummary.getCompanyId())
		        .addValue("groupId", deviceSummary.getGroupId())
		        .addValue("divisionId", deviceSummary.getDivisionId())
		        .addValue("moduleId", deviceSummary.getModuleId())
		        .addValue("userId", deviceSummary.getUserId())
		        .addValue("propertyId", deviceSummary.getPropertyId())
		        .addValue("sectionId", deviceSummary.getSectionId())
		        .addValue("portionId", deviceSummary.getPortionId())
		        .addValue("deviceId", deviceSummary.getDeviceId())
		        .addValue("accidentCount", deviceSummary.getAccidentCount())
				.addValue("animalCrossingCount", deviceSummary.getAnimalCrossingCount())
				.addValue("cautionCount", deviceSummary.getCautionCount())
				.addValue("congestionCount", deviceSummary.getCongestionCount())
				.addValue("curveCount", deviceSummary.getCurveCount())
				.addValue("hillCount", deviceSummary.getHillCount())
				.addValue("hillDownwardsCount", deviceSummary.getHillDownwardsCount())
				.addValue("hillUpwardsCount", deviceSummary.getHillUpwardsCount())
				.addValue("icyConditionsCount", deviceSummary.getIcyConditionsCount())
				.addValue("intersectionCount", deviceSummary.getIntersectionCount())
				.addValue("laneMergeCount", deviceSummary.getLaneMergeCount())
				.addValue("lowGearAreaCount", deviceSummary.getLowGearAreaCount())
				.addValue("narrowRoadCount", deviceSummary.getNarrowRoadCount())
				.addValue("noOvertakingCount", deviceSummary.getNoOvertakingCount())
				.addValue("noOvertakingTrucksCount", deviceSummary.getNoOvertakingTrucksCount())
				.addValue("pedestrianCrossingCount", deviceSummary.getPedestrianCrossingCount())
				.addValue("priorityCount", deviceSummary.getPriorityCount())
				.addValue("priorityToOncomingTrafficCount", deviceSummary.getPriorityToOncomingTrafficCount())
				.addValue("railwayCrossingCount", deviceSummary.getRailwayCrossingCount())
				.addValue("riskOfGroundingCount", deviceSummary.getRiskOfGroundingCount())
				.addValue("schoolZoneCount", deviceSummary.getSchoolZoneCount())
				.addValue("slipperyRoadsCount", deviceSummary.getSlipperyRoadsCount())
				.addValue("stopSignCount", deviceSummary.getStopSignCount())
				.addValue("trafficLightCount", deviceSummary.getTrafficLightCount())
				.addValue("tramwayCrossingCount", deviceSummary.getTramwayCrossingCount())
				.addValue("windCount", deviceSummary.getWindCount())
				.addValue("windingRoadCount", deviceSummary.getWindingRoadCount())
				.addValue("yieldCount", deviceSummary.getYieldCount())
				.addValue("roundAboutCount", deviceSummary.getRoundAboutCount())
				.addValue("overSpeedCount", deviceSummary.getOverSpeedCount())
				.addValue("overSpeedDuration", deviceSummary.getOverSpeedDuration())
				.addValue("overSpeedDistance", deviceSummary.getOverSpeedDistance())
				.addValue("mobileUseInAcceptedCount", deviceSummary.getMobileUseInAcceptedCount())
				.addValue("mobileUseOutAcceptedCount", deviceSummary.getMobileUseOutAcceptedCount())
				.addValue("mobileUseInAcceptedDuration", deviceSummary.getMobileUseInAcceptedDuration())
				.addValue("mobileUseOutAcceptedDuration", deviceSummary.getMobileUseOutAcceptedDuration())
				.addValue("mobileUseInAcceptedDistance", deviceSummary.getMobileUseInAcceptedDistance())
				.addValue("mobileUseOutAcceptedDistance", deviceSummary.getMobileUseOutAcceptedDistance())
				.addValue("mobileScreenScreenOnCount", deviceSummary.getMobileScreenScreenOnCount())
				.addValue("mobileScreenScreenOnDuration", deviceSummary.getMobileScreenScreenOnDuration())
				.addValue("mobileScreenScreenOnDistance", deviceSummary.getMobileScreenScreenOnDistance())
				.addValue("severeBrakingLowCount", deviceSummary.getSevereBrakingLowCount())
				.addValue("severeBrakingMediumCount", deviceSummary.getSevereBrakingMediumCount())
				.addValue("severeBrakingHighCount", deviceSummary.getSevereBrakingHighCount())
				.addValue("severeAccelerationLowCount", deviceSummary.getSevereAccelerationLowCount())
				.addValue("severeAccelerationMediumCount", deviceSummary.getSevereAccelerationMediumCount())
				.addValue("severeAccelerationHighCount", deviceSummary.getSevereAccelerationHighCount())
				.addValue("severeCorneringLowCount", deviceSummary.getSevereCorneringLowCount())
				.addValue("severeCorneringMediumCount", deviceSummary.getSevereCorneringMediumCount())
				.addValue("severeCorneringHighCount", deviceSummary.getSevereCorneringHighCount())
		        .addValue("type", deviceSummary.getType())
		        .addValue("status", deviceSummary.getStatus())
		        .addValue("modifiedAt", deviceSummary.getModifiedAt())
		        .addValue("modifiedBy", deviceSummary.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(updateQuery, parameters);

		if(status > 0) {
			return deviceSummary;
		}
		return new DeviceSummary();
	} 
	
	@Override
	public int updateDeviceSummaryCountByDeviceId(String userId, String deviceId, List<String> countFieldList, List<KeyValuesFloat> keyValues, DeviceDataRunningField ddRunningField) {

        String sqlQueryUpdatePrefix = "UPDATE " + TB_DEVICE_SUMMARY + " SET ";
        String sqlQueryUpdateMiddle = payloadValidator.convertAsDeviceSummaryUpdateQueryMiddle(countFieldList, keyValues, ddRunningField);
        String sqlQueryUpdateSuffix = " WHERE user_id = :userId AND device_id = :deviceId;";
 
        if (sqlQueryUpdateMiddle.length() > 0) { 
        	 
	        String updateQuery = sqlQueryUpdatePrefix + " " + sqlQueryUpdateMiddle + " " + sqlQueryUpdateSuffix;
	        MapSqlParameterSource parameters = new MapSqlParameterSource()
	                .addValue("userId", userId)
	                .addValue("deviceId", deviceId);	
	        return namedParameterJdbcTemplate.update(updateQuery, parameters);
        }  
        return 0;            
	}

	@Override
	public int removeDeviceSummaryById(String companyId,  String userId, String id) {
		String deleteQuery = "update " + TB_DEVICE_SUMMARY
				+ " SET active = :active "
				+ " where id = :id AND "
					+ " company_id = :companyId AND "
					+ " user_id = :userId";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("companyId", companyId)
				.addValue("userId", userId)
				.addValue("id", id)
				.addValue("active", DV_INACTIVE);

		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}
