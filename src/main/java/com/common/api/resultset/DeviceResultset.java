package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Device;
import com.common.api.util.ResultSetConversion;  

public class DeviceResultset extends ResultSetConversion implements RowMapper<Device> {
	 
    public Device mapRow(ResultSet rs, int rowNum) throws SQLException { 
    	
    	Device device = new Device();    
    	try { 
    		
    		// Main Internal Fields		
    		device.setId(resultSetToString(rs, "id"));  
    		device.setCompanyId(resultSetToString(rs, "company_id"));
    		device.setGroupId(resultSetToString(rs, "group_id"));
    		device.setDivisionId(resultSetToString(rs, "division_id"));  
    		device.setModuleId(resultSetToString(rs, "module_id")); 
    		device.setUserId(resultSetToString(rs, "user_id"));  
    		device.setPropertyId(resultSetToString(rs, "property_id"));  
    		device.setSectionId(resultSetToString(rs, "section_id"));  
    		device.setPortionId(resultSetToString(rs, "portion_id"));
    		
    		// Device Level Fields
    		device.setName(resultSetToString(rs, "name"));       
    		device.setUniqueCode(resultSetToString(rs, "unique_code"));     
    		device.setDeviceMode(resultSetToString(rs, "device_mode"));       
    		device.setLocationName(resultSetToString(rs, "location_name"));    
    		device.setLatitude(resultSetToFloat(rs, "latitude"));
    		device.setLongitude(resultSetToFloat(rs, "longitude")); 
    		device.setZipCode(resultSetToString(rs, "zip_code"));
    		 
    		device.setAnalyticServerStatus(resultSetToString(rs, "analytic_server_status"));
    		try {        
    			device.setDeviceField(stringToDeviceField(resultSetToString(rs, "device_field")));   
    		} catch (Exception errMess) { }   
    		
    		try {        
    			device.setDeviceSafetyField(stringToDeviceSafetyField(resultSetToString(rs, "device_safety_field")));   
    		} catch (Exception errMess) { }   
    		
    		device.setDeviceUserPicturePath(resultSetToString(rs, "device_user_picture_path")); 	
    		device.setDeviceRawFilePath(resultSetToString(rs, "device_raw_file_path")); 	
    		device.setDeviceRawFileStatus(resultSetToString(rs, "device_raw_file_status"));	 	
    		device.setDeviceRawFileName(resultSetToString(rs, "device_raw_file_name"));	

    		// Device Data Fields - Driving Behaviour      
    		device.setLastLocationName(resultSetToString(rs, "last_location_name"));   
    		device.setLastLatitude(resultSetToFloat(rs, "last_latitude"));   
    		device.setLastLongitude(resultSetToFloat(rs, "last_longitude"));    
    		device.setLastZipCode(resultSetToString(rs, "last_zip_code"));     
    		try {        
    			device.setLastDeviceField(stringToDeviceField(resultSetToString(rs, "last_device_field")));  
    		} catch (Exception errMess) { }  
    		device.setDrivingScore(resultSetToFloat(rs, "driving_score"));  
    		device.setDrivingSkill(resultSetToFloat(rs, "driving_skill"));  
    		device.setSelfConfidence(resultSetToFloat(rs, "self_confidence"));  
    		device.setAnticipation(resultSetToFloat(rs, "anticipation"));  
    		device.setKiloMeter(resultSetToFloat(rs, "kilo_meter"));  
    		device.setTravelTime(resultSetToFloat(rs, "travel_time"));  
    		device.setUrbanPercent(resultSetToFloat(rs, "urban_percent"));
    		device.setRuralPercent(resultSetToFloat(rs, "rural_percent"));
    		device.setHighwayPercent(resultSetToFloat(rs, "highway_percent"));    		
    		device.setUrbanKilometer(resultSetToFloat(rs, "urban_kilometer"));  
    		device.setRuralKilometer(resultSetToFloat(rs, "rural_kilometer"));  
    		device.setHighwayKilometer(resultSetToFloat(rs, "highway_kilometer")); 

    		device.setDayPercentage(resultSetToFloat(rs, "day_percentage"));  
    		device.setStartDataCount(resultSetToInteger(rs, "start_data_count")); 
    		device.setDistanceDataCount(resultSetToInteger(rs, "distance_data_count"));    
    		device.setAlertDataCount(resultSetToInteger(rs, "alert_data_count"));   
    		device.setStressStrainDataCount(resultSetToInteger(rs, "stress_strain_data_count")); 
    		device.setManualDataCount(resultSetToInteger(rs, "manual_data_count")); 
    		device.setEndDataCount(resultSetToInteger(rs, "end_data_count"));  
    		
    		device.setMobileScreenOnDuration(resultSetToFloat(rs, "mobile_screen_on_duration")); 
    		device.setMobileUseCallDuration(resultSetToFloat(rs, "mobile_use_call_duration")); 
    		device.setOverSpeedDuration(resultSetToFloat(rs, "over_speed_duration")); 
    		device.setMobileScreenOnKiloMeter(resultSetToFloat(rs, "mobile_screen_on_kilo_meter")); 
    		device.setMobileUseCallKiloMeter(resultSetToFloat(rs, "mobile_use_call_kilo_meter")); 
    		device.setOverSpeedKiloMeter(resultSetToFloat(rs, "over_speed_kilo_meter"));  
    		
    		// Other Fields 
    		device.setInsertMode(resultSetToString(rs, "insert_mode"));
    		device.setInternalSystemStatus(resultSetToString(rs, "internal_system_status"));   
    		device.setScoreValidationStatus(resultSetToString(rs, "score_validation_status"));     
    		device.setDeviceDataInsertStatus(resultSetToString(rs, "device_data_insert_status"));    
    		
    		// Main Supported Fields   
    		device.setStartDateTime(resultSetToTimestamp(rs, "start_date_time"));     
    		device.setEndDateTime(resultSetToTimestamp(rs, "end_date_time"));  
    		device.setAccessLevel(resultSetToString(rs, "access_level")); 
    		device.setOrigin(resultSetToString(rs, "origin"));   
    		device.setTimezoneCode(resultSetToString(rs, "timezone_code")); 
    		device.setSubCategory(resultSetToString(rs, "sub_category")); 
    		device.setCategory(resultSetToString(rs, "category"));    
    		device.setSubType(resultSetToString(rs, "sub_type"));       
    		device.setType(resultSetToString(rs, "type"));   
    		device.setStatus(resultSetToString(rs, "status"));  
    		
    		// Default Fields 
    		device.setActive(resultSetToString(rs, "active"));     
    		device.setCreatedBy(resultSetToString(rs, "created_by"));  
    		device.setModifiedBy(resultSetToString(rs, "modified_by"));   
    		device.setCreatedAt(resultSetToTimestamp(rs, "created_at"));     
    		device.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));  
    		device.setInsertedAt(resultSetToTimestamp(rs, "inserted_at")); 
    		
    	} catch (Exception errMess) { 
    	}
        return device;
    }
    
}