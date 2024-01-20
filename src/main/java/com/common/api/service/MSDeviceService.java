package com.common.api.service;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.DeviceService;
import com.common.api.model.Device;
import com.common.api.model.field.DeviceField;
import com.common.api.model.field.DeviceSafetyField;
import com.common.api.resultset.DeviceResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util; 

@Service
public class MSDeviceService extends APIFixedConstant implements DeviceService {

	@Autowired      
	ResultSetConversion resultSetConversion;  
	@Autowired  
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;  
	
	@Override
	public List<Device> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String portionId, String id, String uniqueCode, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {  

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
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) "; 
			parameters.addValue("id", id);   
		}   
		if (uniqueCode != null && uniqueCode.length() > 0) {    
			selQuery = selQuery + " AND LOWER(unique_code) = LOWER(:uniqueCode) ";  
			parameters.addValue("uniqueCode", uniqueCode);  
		}
		if (category != null && category.length() > 0) {
			selQuery = selQuery + " AND LOWER(category) = LOWER(:category) "; 
			parameters.addValue("category", category);      
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
			
			selQuery = "select * from " + TB_DEVICE + " where " + selQuery; 
			return namedParameterJdbcTemplate.query(selQuery, parameters, new DeviceResultset()); 
		}
		return new ArrayList<Device>();      
	}
	
	@Override 
	public List<Device> viewListByDeviceId(String companyId, String groupId, String divisionId, String moduleId, String userId, String id, List<String> validationStatusList, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {  
		
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
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) "; 
			parameters.addValue("id", id);   
		}   
		if (validationStatusList != null && validationStatusList.size() > 0) { 
			String validationStatusListTemp = resultSetConversion.stringListToCommaAndQuoteString(validationStatusList); 
			selQuery = selQuery + " AND score_validation_status in (" + validationStatusListTemp + ")";    
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
			
			selQuery = "select * from " + TB_DEVICE + " where " + selQuery; 
			return namedParameterJdbcTemplate.query(selQuery, parameters, new DeviceResultset()); 
		}
		return new ArrayList<Device>();      
	}
	  
	@Override 
	public List<Device> viewDeviceExistListByCreatedAt(String companyId, String userId, Date createdAt, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {  
		
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
		if (userId != null && userId.length() > 0) { 
			selQuery = selQuery + " AND LOWER(user_id) = LOWER(:userId) "; 
			parameters.addValue("userId", userId);  
		}	 
		if (typeList != null && typeList.size() > 0) {   
			String typeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList); 
			selQuery = selQuery + " AND type in (" + typeListTemp + ")";    
		}  	 
		if (createdAt != null) {  
 			selQuery = selQuery + " AND start_date_time <= :createdAt AND :createdAt <= end_date_time ";   
			parameters.addValue("createdAt", createdAt);    
		}
		 
		if (selQuery.length() > 0) { 

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;  
			selQuery = selQuery + " OFFSET :offset ROWS";  
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";  
			     
			parameters.addValue("offset", offset);       
			parameters.addValue("limit", limit);  
			
			selQuery = "select * from " + TB_DEVICE + " where " + selQuery; 
			return namedParameterJdbcTemplate.query(selQuery, parameters, new DeviceResultset()); 
		}
		return new ArrayList<Device>();      
	}

	@Override
	public List<Device> viewListByCriteria(String companyId, String userId, String propertyId, String sectionId, 
			String portionId, String id, String deviceRawFileName, String sortBy, String sortOrder, int offset,
			int limit) { 
		
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
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) "; 
			parameters.addValue("id", id);   
		}   
		if (deviceRawFileName != null && deviceRawFileName.length() > 0) {    
			selQuery = selQuery + " AND LOWER(device_raw_file_name) = LOWER(:deviceRawFileName) ";  
			parameters.addValue("deviceRawFileName", deviceRawFileName);   
		} 
		
		if (selQuery.length() > 0) { 

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;  
			selQuery = selQuery + " OFFSET :offset ROWS";  
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";  
			     
			parameters.addValue("offset", offset);       
			parameters.addValue("limit", limit);  
			
			selQuery = "select * from " + TB_DEVICE + " where " + selQuery; 
			return namedParameterJdbcTemplate.query(selQuery, parameters, new DeviceResultset()); 
		}
		return new ArrayList<Device>();    
	}
	
	@Override
	public Device createDevice(Device device) {
		 
		String objectId = Util.getTableOrCollectionObjectId();   
		device.setId(objectId);        
		
		DeviceField deviceFieldTemp = device.getDeviceField();     
		String deviceField = resultSetConversion.deviceFieldToString(deviceFieldTemp);    
		
		DeviceField lastDeviceFieldTemp = device.getDeviceField();     
		String lastDeviceField = resultSetConversion.deviceFieldToString(lastDeviceFieldTemp);     
		  
		String insertQuery = "insert into " + TB_DEVICE + " (id, company_id, group_id, division_id, module_id, user_id, "
											+ " property_id, section_id, portion_id, "
				 							+ " name, unique_code, device_mode, location_name, latitude, longitude, zip_code, "
											+ " device_field, last_location_name, last_latitude, last_longitude, last_zip_code, last_device_field, "
											+ " driving_score, driving_skill, self_confidence, anticipation, kilo_meter, travel_time, "
											+ " urban_percent, rural_percent, highway_percent, urban_kilometer, rural_kilometer, highway_kilometer, day_percentage, night_percentage, "
											+ " start_data_count, distance_data_count, alert_data_count, stress_strain_data_count, manual_data_count, end_data_count, "
											+ " mobile_screen_on_duration, mobile_use_call_duration, over_speed_duration, mobile_screen_on_kilo_meter, mobile_use_call_kilo_meter, over_speed_kilo_meter, "
											+ " start_date_time, end_date_time, access_level, origin, insert_mode, internal_system_status, score_validation_status, "
											+ " timezone_code, sub_category, category, sub_type, type, status, "
											+ " device_raw_file_name, "
 											+ " created_at, modified_at, created_by, modified_by, inserted_at) "
								+ " values (:id, :companyId, :groupId, :divisionId, :moduleId, :userId, "
											+ "	:propertyId, :sectionId, :portionId, "
											+ " :name, :uniqueCode, :deviceMode, :locationName, :latitude, :longitude, :zipCode, " 
											+ " :deviceField, :lastLocationName, :lastLatitude, :lastLongitude, :lastZipCode, :lastDeviceField, "
											+ "	:drivingScore, :drivingSkill, :selfConfidence, :anticipation, :kiloMeter, :travelTime,"
											+ " :urbanPercent, :ruralPercent, :highwayPercent, :urbanKilometer, :ruralKilometer, :highwayKilometer, :dayPercentage, :nightPercentage, "
											+ " :startDataCount, :distanceDataCount, :alertDataCount, :stressStrainDataCount, :manualDataCount, :endDataCount, "
											+ " :mScreenOnDuration, :mUseCallDuration, :overSpeedDuration, :mScreenOnKiloMeter, :mUseCallKiloMeter, :overSpeedKiloMeter, "
											+ " :startDateTime, :endDateTime, :accessLevel, :origin, :insertMode, :internalSystemStatus, :scoreValidationStatus, " 
											+ " :timezoneCode, :subCategory, :category, :subType, :type, :status, "
											+ " :deviceRawFileName, "
 											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy, :insertedAt)";      
		 
		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", device.getId())  
			.addValue("companyId", device.getCompanyId())
			.addValue("groupId", device.getGroupId())  
			.addValue("divisionId", device.getDivisionId()) 
			.addValue("moduleId", device.getModuleId())
			.addValue("userId", device.getUserId()) 
			.addValue("propertyId", device.getPropertyId())  
			.addValue("sectionId", device.getSectionId())   
			.addValue("portionId", device.getPortionId())  
			.addValue("name", device.getName())    
			.addValue("uniqueCode", device.getUniqueCode()) 
			.addValue("deviceMode", device.getDeviceMode()) 
			.addValue("locationName", device.getLocationName())
			.addValue("latitude", device.getLatitude())  
			.addValue("longitude", device.getLongitude())  
			.addValue("zipCode", device.getZipCode())  			  
			.addValue("deviceField", deviceField)     
			.addValue("lastLocationName", device.getLastLocationName())    
			.addValue("lastLatitude", device.getLastLatitude())    
			.addValue("lastLongitude", device.getLastLongitude())    
			.addValue("lastZipCode", device.getLastZipCode())    
			.addValue("lastDeviceField", lastDeviceField)  
			.addValue("drivingScore", device.getDrivingScore())  
			.addValue("drivingSkill", device.getDrivingSkill())  
			.addValue("selfConfidence", device.getSelfConfidence())  
			.addValue("anticipation", device.getAnticipation())  
			.addValue("kiloMeter", device.getKiloMeter())  
			.addValue("travelTime", device.getTravelTime())  
			.addValue("urbanPercent", device.getUrbanPercent())  
			.addValue("ruralPercent", device.getRuralPercent())  
			.addValue("highwayPercent", device.getHighwayPercent())  
			.addValue("urbanKilometer", device.getUrbanKilometer())  
			.addValue("ruralKilometer", device.getRuralKilometer())  
			.addValue("highwayKilometer", device.getHighwayKilometer())  
			.addValue("dayPercentage", device.getDayPercentage())  
			.addValue("nightPercentage", device.getNightPercentage()) 
			.addValue("startDataCount", device.getStartDataCount()) 
			.addValue("distanceDataCount", device.getDistanceDataCount()) 
			.addValue("alertDataCount", device.getAlertDataCount())  
			.addValue("stressStrainDataCount", device.getStressStrainDataCount())
			.addValue("manualDataCount", device.getManualDataCount())
			.addValue("endDataCount", device.getEndDataCount())   
			.addValue("mScreenOnDuration", device.getMobileScreenOnDuration())
			.addValue("mUseCallDuration", device.getMobileUseCallDuration())
			.addValue("overSpeedDuration", device.getOverSpeedDuration())
			.addValue("mScreenOnKiloMeter", device.getMobileScreenOnKiloMeter())
			.addValue("mUseCallKiloMeter", device.getMobileUseCallKiloMeter())
			.addValue("overSpeedKiloMeter", device.getOverSpeedKiloMeter())     
			.addValue("startDateTime", device.getStartDateTime()) 
			.addValue("endDateTime", device.getEndDateTime())   
			.addValue("accessLevel", device.getAccessLevel())                    
			.addValue("origin", device.getOrigin())             
			.addValue("timezoneCode", device.getTimezoneCode())  
			.addValue("internalSystemStatus", device.getInternalSystemStatus())   			
			.addValue("scoreValidationStatus", device.getScoreValidationStatus())			
			.addValue("deviceRawFileName", device.getDeviceRawFileName()) 
			.addValue("insertMode", device.getInsertMode())               
			.addValue("subCategory", device.getSubCategory())         
			.addValue("category", device.getCategory())
			.addValue("subType", device.getSubType())   
			.addValue("type", device.getType())            
			.addValue("status", device.getStatus())       
			.addValue("createdAt", device.getCreatedAt())
			.addValue("modifiedAt", device.getModifiedAt())   
			.addValue("createdBy", device.getCreatedBy())   
			.addValue("modifiedBy", device.getModifiedBy())
			.addValue("insertedAt", device.getInsertedAt());     
		 
		int status = namedParameterJdbcTemplate.update(insertQuery, parameters); 
			
		if (status > 0) {  
			return device;    
		}  
		return new Device();   
	}
	
	@Override
	public Device updateDevice(Device device) { 
		  
		DeviceField deviceFieldTemp = device.getDeviceField();   
		String deviceField = resultSetConversion.deviceFieldToString(deviceFieldTemp); 

		DeviceField lastDeviceFieldTemp = device.getLastDeviceField();     
		String lastDeviceField = resultSetConversion.deviceFieldToString(lastDeviceFieldTemp);    
		
		String insertQuery = "update " + TB_DEVICE 
									+ " set "  
										+ " company_id = :companyId, "
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " module_id = :moduleId, "
										+ " user_id = :userId, " 
										+ " property_id = :propertyId, "  
										+ " section_id = :sectionId, "  
										+ " portion_id = :portionId, " 										 
										+ " name = :name, " 
										+ " unique_code = :uniqueCode, " 
										+ " device_mode = :deviceMode, " 
										+ " location_name = :locationName, " 
										+ " latitude = :latitude, "
										+ " longitude = :longitude, "
										+ " zip_code = :zipCode, "										 
										+ " device_field = :deviceField, "  
										+ " last_location_name = :lastLocationName, "  
										+ " last_latitude = :lastLatitude, "  
										+ " last_longitude = :lastLongitude, "  
										+ " last_zip_code = :lastZipCode, "   
										+ " last_device_field = :lastDeviceField, " 										 
										+ " driving_score = :drivingScore, " 
										+ " driving_skill = :drivingSkill, " 
										+ " self_confidence = :selfConfidence, "
										+ " anticipation = :anticipation, " 
										+ " kilo_meter = :kiloMeter, " 
										+ " travel_time = :travelTime, " 										 
										+ " urban_percent = :urbanPercent, " 
										+ " rural_percent = :ruralPercent, " 
										+ " highway_percent = :highwayPercent, " 
										+ " urban_kilometer = :urbanKilometer, " 
										+ " rural_kilometer = :ruralKilometer, " 
										+ " highway_kilometer = :highwayKilometer, " 										 
										+ " day_percentage = :dayPercentage, "							 
										+ " night_percentage = :nightPercentage, "    
										+ " start_data_count = :startDataCount, " 
										+ " distance_data_count = :distanceDataCount, " 
										+ " alert_data_count = :alertDataCount, "
										+ " stress_strain_data_count = :stressStrainDataCount, "
										+ " manual_data_count = :manualDataCount, "
										+ " end_data_count = :endDataCount, "
										+ " mobile_screen_on_duration = :mScreenOnDuration, " 
										+ " mobile_use_call_duration = :mUseCallDuration, " 
										+ " over_speed_duration = :overSpeedDuration, " 
										+ " mobile_screen_on_kilo_meter = :mScreenOnKiloMeter, " 
										+ " mobile_use_call_kilo_meter = :mUseCallKiloMeter, " 
										+ " over_speed_kilo_meter = :overSpeedKiloMeter, "  
										+ " start_date_time = :startDateTime, " 
										+ " end_date_time = :endDateTime, "
										+ " internal_system_status = :internalSystemStatus, "
										+ " score_validation_status = :scoreValidationStatus, "				
										+ " device_data_insert_status = :deviceDataInsertStatus, "	
										+ " access_level = :accessLevel, " 				
										+ " origin = :origin, "						
										+ " timezone_code = :timezoneCode, "
										+ " sub_category = :subCategory, " 
										+ " category = :category, " 
										+ " sub_type = :subType," 
										+ " type = :type," 
										+ " status = :status, "
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy" 
								+ " WHERE id = :id" ;     

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", device.getId())
				.addValue("companyId", device.getCompanyId())
				.addValue("groupId", device.getGroupId())
				.addValue("divisionId", device.getDivisionId())
				.addValue("moduleId", device.getModuleId())
				.addValue("userId", device.getUserId())
				.addValue("propertyId", device.getPropertyId())  
				.addValue("sectionId", device.getSectionId())  
				.addValue("portionId", device.getPortionId())    
				.addValue("name", device.getName())
				.addValue("uniqueCode", device.getUniqueCode()) 
				.addValue("deviceMode", device.getDeviceMode()) 
				.addValue("locationName", device.getLocationName())
				.addValue("latitude", device.getLatitude())  
				.addValue("longitude", device.getLongitude())  
				.addValue("zipCode", device.getZipCode())  			  
				.addValue("deviceField", deviceField)     
				.addValue("lastLocationName", device.getLastLocationName())    
				.addValue("lastLatitude", device.getLastLatitude())    
				.addValue("lastLongitude", device.getLastLongitude())    
				.addValue("lastZipCode", device.getLastZipCode())    
				.addValue("lastDeviceField", lastDeviceField)   
				.addValue("drivingScore", device.getDrivingScore())  
				.addValue("drivingSkill", device.getDrivingSkill())  
				.addValue("selfConfidence", device.getSelfConfidence())  
				.addValue("anticipation", device.getAnticipation())  
				.addValue("kiloMeter", device.getKiloMeter())  
				.addValue("travelTime", device.getTravelTime())  
				.addValue("urbanPercent", device.getUrbanPercent())  
				.addValue("ruralPercent", device.getRuralPercent())  
				.addValue("highwayPercent", device.getHighwayPercent())  
				.addValue("urbanKilometer", device.getUrbanKilometer())  
				.addValue("ruralKilometer", device.getRuralKilometer())  
				.addValue("highwayKilometer", device.getHighwayKilometer())  
				.addValue("dayPercentage", device.getDayPercentage()) 
				.addValue("nightPercentage", device.getNightPercentage()) 
				.addValue("startDataCount", device.getStartDataCount()) 
				.addValue("distanceDataCount", device.getDistanceDataCount()) 
				.addValue("alertDataCount", device.getAlertDataCount()) 
				.addValue("stressStrainDataCount", device.getStressStrainDataCount())
				.addValue("manualDataCount", device.getManualDataCount())
				.addValue("endDataCount", device.getEndDataCount())  
				.addValue("mScreenOnDuration", device.getMobileScreenOnDuration())
				.addValue("mUseCallDuration", device.getMobileUseCallDuration())
				.addValue("overSpeedDuration", device.getOverSpeedDuration())
				.addValue("mScreenOnKiloMeter", device.getMobileScreenOnKiloMeter())
				.addValue("mUseCallKiloMeter", device.getMobileUseCallKiloMeter())
				.addValue("overSpeedKiloMeter", device.getOverSpeedKiloMeter()) 
				.addValue("startDateTime", device.getStartDateTime()) 
				.addValue("endDateTime", device.getEndDateTime())     
				.addValue("internalSystemStatus", device.getInternalSystemStatus())   			
				.addValue("scoreValidationStatus", device.getScoreValidationStatus())   
				.addValue("accessLevel", device.getAccessLevel())                 
				.addValue("deviceDataInsertStatus", device.getDeviceDataInsertStatus())                         
				.addValue("origin", device.getOrigin())                       
				.addValue("timezoneCode", device.getTimezoneCode())                         
				.addValue("subCategory", device.getSubCategory())                       
				.addValue("category", device.getCategory())
				.addValue("subType", device.getSubType()) 
				.addValue("type", device.getType())
				.addValue("status", device.getStatus())
				.addValue("modifiedAt", device.getModifiedAt())  
				.addValue("modifiedBy", device.getModifiedBy());  
		  
		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);  
			 
		if (status > 0) {  
			return device;    
		} 
		return new Device();   
	}
	
	@Override
	public Device updateDeviceByCSV(Device device) {  
		  
		DeviceField deviceFieldTemp = device.getDeviceField();   
		String deviceField = resultSetConversion.deviceFieldToString(deviceFieldTemp); 

		DeviceField lastDeviceFieldTemp = device.getLastDeviceField();     
		String lastDeviceField = resultSetConversion.deviceFieldToString(lastDeviceFieldTemp);    
		
		String insertQuery = "update " + TB_DEVICE 
									+ " set "   									 
										+ " name = :name, " 
										+ " unique_code = :uniqueCode, " 
										+ " device_mode = :deviceMode, " 
										+ " location_name = :locationName, " 
										+ " latitude = :latitude, "
										+ " longitude = :longitude, "
										+ " zip_code = :zipCode, "										 
										+ " device_field = :deviceField, "  
										+ " last_location_name = :lastLocationName, "  
										+ " last_latitude = :lastLatitude, "  
										+ " last_longitude = :lastLongitude, "  
										+ " last_zip_code = :lastZipCode, "   
										+ " last_device_field = :lastDeviceField, " 										 
										+ " driving_score = :drivingScore, " 
										+ " driving_skill = :drivingSkill, " 
										+ " self_confidence = :selfConfidence, "
										+ " anticipation = :anticipation, " 
										+ " kilo_meter = :kiloMeter, " 
										+ " travel_time = :travelTime, " 										 
										+ " urban_percent = :urbanPercent, " 
										+ " rural_percent = :ruralPercent, " 
										+ " highway_percent = :highwayPercent, " 
										+ " urban_kilometer = :urbanKilometer, " 
										+ " rural_kilometer = :ruralKilometer, " 
										+ " highway_kilometer = :highwayKilometer, " 										 
										+ " day_percentage = :dayPercentage, "							 
										+ " night_percentage = :nightPercentage, "    
										+ " start_data_count = :startDataCount, "
										+ " distance_data_count = :distanceDataCount, " 
										+ " alert_data_count = :alertDataCount, "
										+ " stress_strain_data_count = :stressStrainDataCount, "
										+ " manual_data_count = :manualDataCount, "
										+ " end_data_count = :endDataCount, "  
										+ " mobile_screen_on_duration = :mScreenOnDuration, " 
										+ " mobile_use_call_duration = :mUseCallDuration, " 
										+ " over_speed_duration = :overSpeedDuration, " 
										+ " mobile_screen_on_kilo_meter = :mScreenOnKiloMeter, " 
										+ " mobile_use_call_kilo_meter = :mUseCallKiloMeter, " 
										+ " over_speed_kilo_meter = :overSpeedKiloMeter, "  
										+ " risk_negative_count = :riskNegativeCount, "
										+ " risk_zero_count = :riskZeroCount, "
										+ " risk_slot_1_count = :riskSlot1Count, "
										+ " risk_slot_2_count = :riskSlot2Count, " 
										+ " risk_slot_3_count = :riskSlot3Count, " 
										+ " risk_slot_4_count = :riskSlot4Count, "
										+ " risk_slot_5_count = :riskSlot5Count, "
										+ " risk_slot_6_count = :riskSlot6Count, "
										+ " risk_slot_7_count = :riskSlot7Count, "
										+ " risk_slot_8_count = :riskSlot8Count, "
										+ " risk_slot_9_count = :riskSlot9Count, "
										+ " risk_slot_10_count = :riskSlot10Count, "
										+ " start_date_time = :startDateTime, " 
										+ " end_date_time = :endDateTime, " 
										+ " internal_system_status = :internalSystemStatus, " 
										+ " score_validation_status = :scoreValidationStatus, "				
										+ " device_data_insert_status = :deviceDataInsertStatus, "			
										+ " device_raw_file_name = :deviceRawFileName, "	
										+ " access_level = :accessLevel, " 				
										+ " origin = :origin, "					 	
										+ " timezone_code = :timezoneCode, "
										+ " sub_category = :subCategory, " 
										+ " category = :category, " 
										+ " sub_type = :subType," 
										+ " type = :type," 
										+ " status = :status, "
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy" 
								+ " WHERE id = :id" ;      

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", device.getId())  
				.addValue("name", device.getName())
				.addValue("uniqueCode", device.getUniqueCode()) 
				.addValue("deviceMode", device.getDeviceMode()) 
				.addValue("locationName", device.getLocationName())
				.addValue("latitude", device.getLatitude())  
				.addValue("longitude", device.getLongitude())  
				.addValue("zipCode", device.getZipCode())  			  
				.addValue("deviceField", deviceField)     
				.addValue("lastLocationName", device.getLastLocationName())    
				.addValue("lastLatitude", device.getLastLatitude())    
				.addValue("lastLongitude", device.getLastLongitude())    
				.addValue("lastZipCode", device.getLastZipCode())    
				.addValue("lastDeviceField", lastDeviceField)   
				.addValue("drivingScore", device.getDrivingScore())  
				.addValue("drivingSkill", device.getDrivingSkill())  
				.addValue("selfConfidence", device.getSelfConfidence())  
				.addValue("anticipation", device.getAnticipation())  
				.addValue("kiloMeter", device.getKiloMeter())  
				.addValue("travelTime", device.getTravelTime())  
				.addValue("urbanPercent", device.getUrbanPercent())  
				.addValue("ruralPercent", device.getRuralPercent())  
				.addValue("highwayPercent", device.getHighwayPercent())  
				.addValue("urbanKilometer", device.getUrbanKilometer())  
				.addValue("ruralKilometer", device.getRuralKilometer())  
				.addValue("highwayKilometer", device.getHighwayKilometer())  
				.addValue("dayPercentage", device.getDayPercentage()) 
				.addValue("nightPercentage", device.getNightPercentage()) 
				.addValue("startDataCount", device.getStartDataCount()) 
				.addValue("distanceDataCount", device.getDistanceDataCount()) 
				.addValue("alertDataCount", device.getAlertDataCount()) 
				.addValue("stressStrainDataCount", device.getStressStrainDataCount())
				.addValue("manualDataCount", device.getManualDataCount()) 
				.addValue("endDataCount", device.getEndDataCount())    
				.addValue("mScreenOnDuration", device.getMobileScreenOnDuration())
				.addValue("mUseCallDuration", device.getMobileUseCallDuration())
				.addValue("overSpeedDuration", device.getOverSpeedDuration())
				.addValue("mScreenOnKiloMeter", device.getMobileScreenOnKiloMeter())
				.addValue("mUseCallKiloMeter", device.getMobileUseCallKiloMeter())
				.addValue("overSpeedKiloMeter", device.getOverSpeedKiloMeter())
				.addValue("riskNegativeCount", device.getRiskNegativeCount())  
				.addValue("riskZeroCount", device.getRiskZeroCount())  
				.addValue("riskSlot1Count", device.getRiskSlot1Count())   
				.addValue("riskSlot2Count", device.getRiskSlot2Count())  
				.addValue("riskSlot3Count", device.getRiskSlot3Count())  
				.addValue("riskSlot4Count", device.getRiskSlot4Count())  
				.addValue("riskSlot5Count", device.getRiskSlot5Count())  
				.addValue("riskSlot6Count", device.getRiskSlot6Count())  
				.addValue("riskSlot7Count", device.getRiskSlot7Count())  
				.addValue("riskSlot8Count", device.getRiskSlot8Count())  
				.addValue("riskSlot9Count", device.getRiskSlot9Count())  
				.addValue("riskSlot10Count", device.getRiskSlot10Count()) 
				.addValue("startDateTime", device.getStartDateTime()) 
				.addValue("endDateTime", device.getEndDateTime())     
				.addValue("internalSystemStatus", device.getInternalSystemStatus())   			
				.addValue("scoreValidationStatus", device.getScoreValidationStatus())   
				.addValue("accessLevel", device.getAccessLevel())                 
				.addValue("deviceDataInsertStatus", device.getDeviceDataInsertStatus())  
				.addValue("deviceRawFileName", device.getDeviceRawFileName())                        
				.addValue("origin", device.getOrigin())                       
				.addValue("timezoneCode", device.getTimezoneCode())                         
				.addValue("subCategory", device.getSubCategory())                       
				.addValue("category", device.getCategory())
				.addValue("subType", device.getSubType()) 
				.addValue("type", device.getType())
				.addValue("status", device.getStatus())
				.addValue("modifiedAt", device.getModifiedAt())  
				.addValue("modifiedBy", device.getModifiedBy());  
		  
		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);  
			 
		if (status > 0) {  
			return device;    
		} 
		return new Device();   
	}
	
	@Override 
	public int updateDeviceStatusById(String companyId, String userId, String deviceId, String status, String internalSystemStatus, String scoreValidationStatus, String deviceDataInsertStatus) {
			
		String deleteQuery = "update " + TB_DEVICE 
							+ " SET status = :status, " 
							+ " 	internal_system_status = :internalSystemStatus, "	 				
							+ " 	score_validation_status = :scoreValidationStatus, "					
							+ " 	device_data_insert_status = :deviceDataInsertStatus "		
							+ " where id = :deviceId AND " 
							+ " company_id = :companyId AND "  
							+ " user_id = :userId AND "
							+ " id != '' ";           
		MapSqlParameterSource parameters = new MapSqlParameterSource()           
				.addValue("companyId", companyId) 
				.addValue("userId", userId)
				.addValue("deviceId", deviceId)
				.addValue("status", status)    
				.addValue("internalSystemStatus", internalSystemStatus)                     
				.addValue("scoreValidationStatus", scoreValidationStatus)                         
				.addValue("deviceDataInsertStatus", deviceDataInsertStatus);
		
		return namedParameterJdbcTemplate.update(deleteQuery, parameters); 
		
	}

	@Override 
	public int updateDeviceInternalSystemStatusById(String companyId, String userId, String deviceId, String internalSystemStatus) {
			
		String deleteQuery = "update " + TB_DEVICE 
							+ " SET internal_system_status = :internalSystemStatus" 
							+ " where id = :deviceId AND " 
							+ " company_id = :companyId AND "
							+ " user_id = :userId AND "
							+ " id != '' ";           
		MapSqlParameterSource parameters = new MapSqlParameterSource()           
				.addValue("companyId", companyId) 
				.addValue("userId", userId)
				.addValue("deviceId", deviceId)
				.addValue("internalSystemStatus", internalSystemStatus);   
		return namedParameterJdbcTemplate.update(deleteQuery, parameters); 
		
	}
	
	@Override 
	public int updateDeviceValidationScoreStatusById(String companyId, String userId, String deviceId, String validationScoreStatus) {
			
		String deleteQuery = "update " + TB_DEVICE 
							+ " SET validation_score_status = :validationScoreStatus" 
							+ " where id = :deviceId AND " 
							+ " company_id = :companyId AND "
							+ " user_id = :userId AND "
							+ " id != '' ";           
		MapSqlParameterSource parameters = new MapSqlParameterSource()           
				.addValue("companyId", companyId) 
				.addValue("userId", userId)
				.addValue("deviceId", deviceId)
				.addValue("validationScoreStatus", validationScoreStatus);   
		return namedParameterJdbcTemplate.update(deleteQuery, parameters); 
		
	}
	
	@Override 
	public int updateDeviceDataCountByDeviceId(String userId, String deviceId, int startData, int distanceData, int alertData, int stressStrainData, int manualData, int endData, 
												float mScreenOnDuration, float mUseCallDuration, float overSpeedDuration, float mScreenOnKiloMeter, float mUseCallKiloMeter, float overSpeedKiloMeter,
												int riskNegativeCount, int riskZeroCount, int riskSlot1Count, int riskSlot2Count, int riskSlot3Count, int riskSlot4Count, int riskSlot5Count,
												int riskSlot6Count, int riskSlot7Count, int riskSlot8Count, int riskSlot9Count, int riskSlot10Count) {
			
		String updateQuery = "update " + TB_DEVICE 
							+ " SET start_data_count = start_data_count + " + startData + " , "
								+ " distance_data_count = distance_data_count + " + distanceData + " , " 
								+ " alert_data_count = alert_data_count + " + alertData + " , "  
								+ " stress_strain_data_count = stress_strain_data_count + " + stressStrainData + " , " 
								+ " manual_data_count = manual_data_count + " + manualData + " , " 
								+ " end_data_count = end_data_count + " + endData + " , "  
								+ " mobile_screen_on_duration = :mScreenOnDuration, " 
								+ " mobile_use_call_duration = :mUseCallDuration, " 
								+ " over_speed_duration = :overSpeedDuration, " 
								+ " mobile_screen_on_kilo_meter = :mScreenOnKiloMeter, " 
								+ " mobile_use_call_kilo_meter = :mUseCallKiloMeter, " 
								+ " over_speed_kilo_meter = :overSpeedKiloMeter, " 
								+ " risk_negative_count = :riskNegativeCount , " 
								+ " risk_zero_count = :riskZeroCount , " 
								+ " risk_slot_1_count = :riskSlot1Count , "  
								+ " risk_slot_2_count = :riskSlot2Count , "  
								+ " risk_slot_3_count = :riskSlot3Count , "  
								+ " risk_slot_4_count = :riskSlot4Count , " 
								+ " risk_slot_5_count = :riskSlot5Count , " 
								+ " risk_slot_6_count = :riskSlot6Count , " 
								+ " risk_slot_7_count = :riskSlot7Count , "  
								+ " risk_slot_8_count = :riskSlot8Count , " 
								+ " risk_slot_9_count = :riskSlot9Count , " 
								+ " risk_slot_10_count = :riskSlot10Count "  
							+ " where id = :deviceId AND "   
	 							+ " user_id = :userId AND "
								+ " id != '' ";   
		
		MapSqlParameterSource parameters = new MapSqlParameterSource()           
 			.addValue("userId", userId)
			.addValue("deviceId", deviceId)
			.addValue("riskNegativeCount", riskNegativeCount)
			.addValue("riskZeroCount", riskZeroCount)
			.addValue("riskSlot1Count", riskSlot1Count)
			.addValue("riskSlot2Count", riskSlot2Count)
			.addValue("riskSlot3Count", riskSlot3Count)
			.addValue("riskSlot4Count", riskSlot4Count)
			.addValue("riskSlot5Count", riskSlot5Count)
			.addValue("riskSlot6Count", riskSlot6Count) 
			.addValue("riskSlot7Count", riskSlot7Count) 
			.addValue("riskSlot8Count", riskSlot8Count)
			.addValue("riskSlot9Count", riskSlot9Count)
			.addValue("riskSlot10Count", riskSlot10Count)
			.addValue("mScreenOnDuration", mScreenOnDuration)
			.addValue("mUseCallDuration", mUseCallDuration)
			.addValue("overSpeedDuration", overSpeedDuration)
			.addValue("mScreenOnKiloMeter", mScreenOnKiloMeter)
			.addValue("mUseCallKiloMeter", mUseCallKiloMeter)
			.addValue("overSpeedKiloMeter", overSpeedKiloMeter);  
		
 		return namedParameterJdbcTemplate.update( updateQuery, parameters); 
		
	}

	@Override
	public int updateDeviceMultipartFilePathById(String companyId, String userId, String deviceId, String fileName) {

		String updateQuery = " update " + TB_DEVICE 
							+ " SET device_user_picture_path = :deviceUserPicturePath "
							+ " where id = :id AND "
							+ " company_id = :companyId AND  " 
							+ " user_id = :userId";             
		MapSqlParameterSource parameters = new MapSqlParameterSource()          
				.addValue("companyId", companyId)
				.addValue("userId", userId)
				.addValue("id", deviceId)
				.addValue("deviceUserPicturePath", fileName);   
		return namedParameterJdbcTemplate.update(updateQuery, parameters); 
	}

	@Override
	public int updateDeviceMultipartRawFilePathById(String companyId, String userId, String deviceId, String fileName, String fileStatus) {

		String updateQuery = " update " + TB_DEVICE 
							+ " SET device_raw_file_path = :deviceRawFilePath,"
								+ "	device_raw_file_status = :deviceRawFileStatus  "
							+ " where id = :id AND "
							+ " company_id = :companyId AND  " 
							+ " user_id = :userId";             
		MapSqlParameterSource parameters = new MapSqlParameterSource()           
				.addValue("companyId", companyId)
				.addValue("userId", userId)
				.addValue("id", deviceId)
				.addValue("deviceRawFilePath", fileName)
				.addValue("deviceRawFileStatus", fileStatus);    
		return namedParameterJdbcTemplate.update(updateQuery, parameters); 
	}

	@Override
	public int updateDeviceSafetyFieldById(Device device) {

		String companyId = device.getCompanyId();
		String userId    = device.getUserId();
		String deviceId  = device.getId(); 
		String analyticServerStatus  = device.getAnalyticServerStatus();
		
		DeviceSafetyField deviceSafetyFieldTemp = device.getDeviceSafetyField();   
		String deviceSafetyField = resultSetConversion.deviceSafetyFieldToString(deviceSafetyFieldTemp); 
		
		String updateQuery = " update " + TB_DEVICE 
								+ " SET device_safety_field = :deviceSafetyField, " 
										+ " analytic_server_status = :analyticServerStatus" 
							+ " where id = :id AND "
							+ " company_id = :companyId AND  "  
							+ " user_id = :userId";                
		MapSqlParameterSource parameters = new MapSqlParameterSource()           
				.addValue("companyId", companyId)
				.addValue("userId", userId)
				.addValue("id", deviceId) 
				.addValue("deviceSafetyField", deviceSafetyField)
				.addValue("analyticServerStatus", analyticServerStatus);     
		return namedParameterJdbcTemplate.update(updateQuery, parameters); 
	}

	@Override
	public int removeDeviceById(String companyId, String userId, String id) {
		
		String deleteQuery = "update " + TB_DEVICE 
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