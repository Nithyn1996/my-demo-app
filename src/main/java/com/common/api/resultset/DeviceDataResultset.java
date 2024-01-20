package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.DeviceData;
import com.common.api.util.ResultSetConversion;  

public class DeviceDataResultset extends ResultSetConversion implements RowMapper<DeviceData> {
	 
    public DeviceData mapRow(ResultSet rs, int rowNum) throws SQLException {   
    	
    	DeviceData deviceData = new DeviceData();      
    	try { 
    		deviceData.setId(resultSetToString(rs, "id"));  
    		deviceData.setCompanyId(resultSetToString(rs, "company_id")); 
    		deviceData.setGroupId(resultSetToString(rs, "group_id"));
    		deviceData.setDivisionId(resultSetToString(rs, "division_id"));  
    		deviceData.setModuleId(resultSetToString(rs, "module_id")); 
    		deviceData.setUserId(resultSetToString(rs, "user_id"));  
    		deviceData.setPropertyId(resultSetToString(rs, "property_id"));  
    		deviceData.setSectionId(resultSetToString(rs, "section_id"));  
    		deviceData.setPortionId(resultSetToString(rs, "portion_id")); 
    		deviceData.setDeviceId(resultSetToString(rs, "device_id"));  
    		
    		deviceData.setAccessLevel(resultSetToString(rs, "access_level"));
    		deviceData.setRisk(resultSetToFloat(rs, "risk"));
    		deviceData.setKiloMeter(resultSetToFloat(rs, "kilo_meter"));
    		deviceData.setSpeed(resultSetToFloat(rs, "speed"));  
    		deviceData.setPreviousSpeed(resultSetToFloat(rs, "previous_speed"));
    		deviceData.setSpeedLimit(resultSetToFloat(rs, "speed_limit"));
    		deviceData.setActivityDuration(resultSetToFloat(rs, "activity_duration"));
    		deviceData.setActivityKiloMeter(resultSetToFloat(rs, "activity_kilo_meter")); 
    		deviceData.setLatitude(resultSetToFloat(rs, "latitude")); 
    		deviceData.setLongitude(resultSetToFloat(rs, "longitude")); 
    		deviceData.setZipCode(resultSetToString(rs, "zip_code"));
    		deviceData.setDeviceMode(resultSetToString(rs, "device_mode"));
    		deviceData.setDeviceCode(resultSetToString(rs, "device_code"));
    		deviceData.setRedAlertDuration(resultSetToFloat(rs, "red_alert_duration")); 
    		deviceData.setRedAlertDistance(resultSetToFloat(rs, "red_alert_distance")); 
    		deviceData.setPointOfInterest(resultSetToFloat(rs, "point_of_interest"));  
    		
    		deviceData.setSubType(resultSetToString(rs, "sub_type"));     
    		deviceData.setType(resultSetToString(rs, "type"));    
    		deviceData.setSubCategoryLevel(resultSetToString(rs, "sub_category_level"));   
    		deviceData.setSubCategory(resultSetToString(rs, "sub_category"));      
    		deviceData.setCategory(resultSetToString(rs, "category"));  
    		deviceData.setStatus(resultSetToString(rs, "status"));  
    		deviceData.setActive(resultSetToString(rs, "active"));     
    		deviceData.setCreatedBy(resultSetToString(rs, "created_by"));  
    		deviceData.setModifiedBy(resultSetToString(rs, "modified_by"));   
    		deviceData.setCreatedAt(resultSetToTimestamp(rs, "created_at"));     
    		deviceData.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));    
    		deviceData.setInsertedAt(resultSetToTimestamp(rs, "inserted_at"));
 
    		// Other Fields  
    		deviceData.setInsertMode(resultSetToString(rs, "insert_mode"));   
    		
    		try {        
    			deviceData.setDeviceDataAddressField(stringToDeviceDataAddressField(resultSetToString(rs, "device_data_address_field")));  
    		} catch (Exception errMess) { }  
    		
    		try {        
    			deviceData.setDeviceDataField(stringToDeviceDataField(resultSetToString(rs, "device_data_field")));  
    		} catch (Exception errMess) { }  
    		
    		try {          
    			deviceData.setDeviceDataField1(stringToDeviceDataField1(resultSetToString(rs, "device_data_field_1")));  
    		} catch (Exception errMess) { }  
    		 
    		try {        
    			deviceData.setDeviceDataLiveField(stringToDeviceDataLiveField(resultSetToString(rs, "device_data_live_field")));  
    		} catch (Exception errMess) { }  
    		 
    		try {        
    			deviceData.setDeviceDataErrorField(stringToDeviceDataErrorField(resultSetToString(rs, "device_data_error_field")));  
    		} catch (Exception errMess) { }    
    		
    		try {        
    			deviceData.setDeviceDataTrackingField(stringToDeviceDataTrackingField(resultSetToString(rs, "device_data_tracking_field")));  
    		} catch (Exception errMess) { }    
    		
    		try {        
    			deviceData.setDeviceDataRiskField(stringToDeviceDataRiskField(resultSetToString(rs, "device_data_risk_field")));  
    		} catch (Exception errMess) { }   
    		
    		try {         
    			deviceData.setDeviceDataRunningField(stringToDeviceDataRunningField(resultSetToString(rs, "device_data_running_field")));  
    		} catch (Exception errMess) { } 
    		
    	} catch (Exception errMess) { 
    	}
        return deviceData;
    }
    
}