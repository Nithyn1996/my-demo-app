package com.common.api.service;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.DeviceDataService;
import com.common.api.model.DeviceData;
import com.common.api.model.field.DeviceDataAddressField;
import com.common.api.model.field.DeviceDataErrorField;
import com.common.api.model.field.DeviceDataField;
import com.common.api.model.field.DeviceDataField1;
import com.common.api.model.field.DeviceDataLiveField;
import com.common.api.model.field.DeviceDataRiskField;
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.DeviceDataTrackingField;
import com.common.api.resultset.DeviceDataResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util; 

@Service
public class MSDeviceDataService extends APIFixedConstant implements DeviceDataService {

	@Autowired      
	ResultSetConversion resultSetConversion;   
	@Autowired 
	NamedParameterJdbcTemplate namedParameterJdbcTemplate; 
	
	public List<DeviceData> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String portionId, String deviceId, String id, List<String> categoryList, List<String> subCategoryList, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {  

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
		if (subCategoryList != null && subCategoryList.size() > 0) {  
			String subCategoryListTemp = resultSetConversion.stringListToCommaAndQuoteString(subCategoryList);  
			selQuery = selQuery + " AND sub_category in (" + subCategoryListTemp + ")";    
		}
		if (categoryList != null && categoryList.size() > 0) { 
			String categoryListTemp = resultSetConversion.stringListToCommaAndQuoteString(categoryList);  
			selQuery = selQuery + " AND category in (" + categoryListTemp + ")";    
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
			  
			selQuery = "select * from " + TB_DEVICE_DATA + " where " + selQuery; 
			return namedParameterJdbcTemplate.query(selQuery, parameters, new DeviceDataResultset()); 
		}
		return new ArrayList<DeviceData>();       
	}

	@Override
	public DeviceData createDeviceData(DeviceData deviceData) { 
		 
		String objectId = Util.getTableOrCollectionObjectId();   
		deviceData.setId(objectId);          

		DeviceDataAddressField deviceDataAddressFieldTemp = deviceData.getDeviceDataAddressField();    
		String deviceDataAddressField = resultSetConversion.deviceDataAddressFieldToString(deviceDataAddressFieldTemp);  

		DeviceDataField deviceDataFieldTemp = deviceData.getDeviceDataField();    
		String deviceDataField = resultSetConversion.deviceDataFieldToString(deviceDataFieldTemp);  

		DeviceDataField1 deviceDataField1Temp = deviceData.getDeviceDataField1();     
		String deviceDataField1 = resultSetConversion.deviceDataField1ToString(deviceDataField1Temp);  
		
		DeviceDataTrackingField deviceDataTrackingFieldTemp = deviceData.getDeviceDataTrackingField();    
		String deviceDataTrackingField = resultSetConversion.deviceDataTrackingFieldToString(deviceDataTrackingFieldTemp);  

		DeviceDataRiskField deviceDataRiskFieldTemp = deviceData.getDeviceDataRiskField();    
		String deviceDataRiskField = resultSetConversion.deviceDataRiskFieldToString(deviceDataRiskFieldTemp);  

		DeviceDataRunningField deviceDataRunningFieldTemp = deviceData.getDeviceDataRunningField();    
		String deviceDataRunningField = resultSetConversion.deviceDataRunningFieldToString(deviceDataRunningFieldTemp);  

		DeviceDataLiveField deviceDataLiveFieldTemp = deviceData.getDeviceDataLiveField();    
		String deviceDataLiveField = resultSetConversion.deviceDataLiveFieldToString(deviceDataLiveFieldTemp);  
 
		DeviceDataErrorField deviceDataErrorFieldTemp = deviceData.getDeviceDataErrorField();  
		String deviceDataErrorField = resultSetConversion.deviceDataErrorFieldToString(deviceDataErrorFieldTemp); 
		
		String insertQuery = "insert into " + TB_DEVICE_DATA + " (id, company_id, group_id, division_id, module_id, user_id, property_id, section_id, portion_id, device_id, "
											+ " device_data_address_field, device_data_field, device_data_tracking_field, device_data_risk_field, device_data_running_field, "
											+ " device_data_field_1, device_data_live_field, device_data_error_field, red_alert_duration, red_alert_distance, point_of_interest, "
											+ " risk, kilo_meter, speed, previous_speed, speed_limit, activity_duration, activity_kilo_meter, latitude, longitude, zip_code, device_mode, "
											+ " device_code, sub_category, category, status, sub_type, type, access_level, insert_mode, sub_category_level, " 
											+ " created_at, modified_at, created_by, modified_by, inserted_at) " 
								+ " values (:id, :companyId, :groupId, :divisionId, :moduleId, :userId, :propertyId, :sectionId, :portionId, :deviceId, "
											+ " :deviceDataAddressField, :deviceDataField, :deviceDataTrackingField, :deviceDataRiskField, :deviceDataRunningField, "
											+ " :deviceDataField1, :deviceDataLiveField, :deviceDataErrorField, :redAlertDuration, :redAlertDistance, :pointOfInterest, "
											+ " :risk, :kiloMeter, :speed, :previousSpeed, :speedLimit, :activityDuration, :activityKiloMeter, :latitude, :longitude, :zipCode, :deviceMode, "
											+ " :deviceCode, :subCategory, :category, :status, :subType, :type, :accessLevel, :insertMode, :subCategoryLevel, "    
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy, :insertedAt)";    
 
		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", deviceData.getId())
			.addValue("companyId", deviceData.getCompanyId())
			.addValue("groupId", deviceData.getGroupId())
			.addValue("divisionId", deviceData.getDivisionId())
			.addValue("moduleId", deviceData.getModuleId())
			.addValue("userId", deviceData.getUserId())
			.addValue("propertyId", deviceData.getPropertyId())  
			.addValue("sectionId", deviceData.getSectionId())   
			.addValue("portionId", deviceData.getPortionId())   
			.addValue("deviceId", deviceData.getDeviceId())   
			.addValue("accessLevel", deviceData.getAccessLevel())    
			.addValue("risk", deviceData.getRisk())  
			.addValue("kiloMeter", deviceData.getKiloMeter())  
			.addValue("speed", deviceData.getSpeed()) 
			.addValue("previousSpeed", deviceData.getPreviousSpeed())  
			.addValue("speedLimit", deviceData.getSpeedLimit())  
			.addValue("activityDuration", deviceData.getActivityDuration())   
			.addValue("activityKiloMeter", deviceData.getActivityKiloMeter())    
			.addValue("latitude", deviceData.getLatitude())  
			.addValue("longitude", deviceData.getLongitude())  
			.addValue("redAlertDuration", deviceData.getRedAlertDuration())   
			.addValue("redAlertDistance", deviceData.getRedAlertDistance())    
			.addValue("pointOfInterest", deviceData.getPointOfInterest())   
			.addValue("zipCode", deviceData.getZipCode())  
			.addValue("deviceMode", deviceData.getDeviceMode())
			.addValue("deviceCode", deviceData.getDeviceCode())
 			.addValue("deviceDataAddressField", deviceDataAddressField)    
			.addValue("deviceDataField", deviceDataField)    
			.addValue("deviceDataTrackingField", deviceDataTrackingField)   
			.addValue("deviceDataRiskField", deviceDataRiskField)   
			.addValue("deviceDataRunningField", deviceDataRunningField)  
			.addValue("deviceDataField1", deviceDataField1)   
			.addValue("deviceDataLiveField", deviceDataLiveField)        
			.addValue("deviceDataErrorField", deviceDataErrorField)             
			.addValue("insertMode", deviceData.getInsertMode())                
			.addValue("subCategoryLevel", deviceData.getSubCategoryLevel())             
			.addValue("subCategory", deviceData.getSubCategory())                  
			.addValue("category", deviceData.getCategory())            
			.addValue("status", deviceData.getStatus())           
			.addValue("subType", deviceData.getSubType())      
			.addValue("type", deviceData.getType())   
			.addValue("createdAt", deviceData.getCreatedAt())    
			.addValue("modifiedAt", deviceData.getModifiedAt())   
			.addValue("createdBy", deviceData.getCreatedBy())   
			.addValue("modifiedBy", deviceData.getModifiedBy())
			.addValue("insertedAt", deviceData.getInsertedAt());      
			
		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);   
			
		if (status > 0) {  
			return deviceData;   
		}
		return new DeviceData();   
	}
	
	@Override 
	public DeviceData updateDeviceData(DeviceData deviceData) { 

		DeviceDataAddressField deviceDataAddressFieldTemp = deviceData.getDeviceDataAddressField();    
		String deviceDataAddressField = resultSetConversion.deviceDataAddressFieldToString(deviceDataAddressFieldTemp);  

		DeviceDataField deviceDataFieldTemp = deviceData.getDeviceDataField();   
		String deviceDataField = resultSetConversion.deviceDataFieldToString(deviceDataFieldTemp); 

		DeviceDataField1 deviceDataField1Temp = deviceData.getDeviceDataField1();       
		String deviceDataField1 = resultSetConversion.deviceDataField1ToString(deviceDataField1Temp);  
		
		DeviceDataTrackingField deviceDataTrackingFieldTemp = deviceData.getDeviceDataTrackingField();    
		String deviceDataTrackingField = resultSetConversion.deviceDataTrackingFieldToString(deviceDataTrackingFieldTemp);  

		DeviceDataRiskField deviceDataRiskFieldTemp = deviceData.getDeviceDataRiskField();    
		String deviceDataRiskField = resultSetConversion.deviceDataRiskFieldToString(deviceDataRiskFieldTemp);  

		DeviceDataRunningField deviceDataRunningFieldTemp = deviceData.getDeviceDataRunningField();    
		String deviceDataRunningField = resultSetConversion.deviceDataRunningFieldToString(deviceDataRunningFieldTemp);  

		DeviceDataLiveField deviceDataLiveFieldTemp = deviceData.getDeviceDataLiveField();   
		String deviceDataLiveField = resultSetConversion.deviceDataLiveFieldToString(deviceDataLiveFieldTemp); 
 
		DeviceDataErrorField deviceDataErrorFieldTemp = deviceData.getDeviceDataErrorField();  
		String deviceDataErrorField = resultSetConversion.deviceDataErrorFieldToString(deviceDataErrorFieldTemp); 
		
		String insertQuery = "update " + TB_DEVICE_DATA
									+ " set " 
										+ " company_id = :companyId, " 
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " module_id = :moduleId, " 
										+ " user_id = :userId, " 
										+ " property_id = :propertyId, "   
										+ " section_id = :sectionId, "  
										+ " portion_id = :portionId, "  
										+ " device_id = :deviceId, "  
										+ " access_level = :accessLevel, " 
										+ " risk = :risk, "     
										+ " kiloMeter = :kiloMeter, "   
										+ " speed = :speed, "     
										+ " previous_speed = :previousSpeed, "   
										+ " speed_limit = :speedLimit, "     
										+ " activity_duration = :activityDuration, "     
										+ " activity_kilo_meter = :activityKiloMeter, "
										+ " latitude = :latitude, "      
										+ " longitude = :longitude, "      
										+ " red_alert_duration = :redAlertDuration, "     
										+ " red_alert_distance = :redAlertDistance, "      
										+ " point_of_interest = :pointOfInterest, "     
										+ " zip_code = :zipCode, "     
										+ " device_mode = :deviceMode, "    
										+ " device_data_address_field = :deviceDataAddressField, " 
										+ " device_data_field = :deviceDataField, "
										+ " device_data_tracking_field = :deviceDataTrackingField, "
										+ " device_data_risk_field = :deviceDataRiskField, " 
										+ " device_data_running_field = :deviceDataRunningField, " 
										+ " device_data_field_1 = :deviceDataField1, "  
										+ " device_data_live_field = :deviceDataLiveField, "
										+ " device_data_error_field = :deviceDataErrorField, " 
										+ " sub_category = :subCategory, "
										+ " category = :category, " 
										+ " sub_type = :subType, "
										+ " status = :status, " 
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;     

		MapSqlParameterSource parameters = new MapSqlParameterSource() 
				.addValue("id", deviceData.getId())
				.addValue("companyId", deviceData.getCompanyId())	
				.addValue("groupId", deviceData.getGroupId())
				.addValue("divisionId", deviceData.getDivisionId())
				.addValue("moduleId", deviceData.getModuleId())
				.addValue("userId", deviceData.getUserId()) 
				.addValue("propertyId", deviceData.getPropertyId())   
				.addValue("sectionId", deviceData.getSectionId())  
				.addValue("portionId", deviceData.getPortionId())  
				.addValue("deviceId", deviceData.getDeviceId())  
				.addValue("accessLevel", deviceData.getAccessLevel()) 				 
				.addValue("risk", deviceData.getRisk())  
				.addValue("kiloMeter", deviceData.getKiloMeter()) 
				.addValue("speed", deviceData.getSpeed()) 
				.addValue("previousSpeed", deviceData.getPreviousSpeed())  
				.addValue("speedLimit", deviceData.getSpeedLimit())  
				.addValue("activityDuration", deviceData.getActivityDuration())  
				.addValue("activityKiloMeter", deviceData.getActivityKiloMeter())   
				.addValue("latitude", deviceData.getLatitude())  
				.addValue("longitude", deviceData.getLongitude())  
				.addValue("redAlertDuration", deviceData.getRedAlertDuration())  
				.addValue("redAlertDistance", deviceData.getRedAlertDistance())     
				.addValue("pointOfInterest", deviceData.getPointOfInterest())   
				.addValue("zipCode", deviceData.getZipCode())  
				.addValue("deviceMode", deviceData.getDeviceMode())  
				.addValue("deviceDataAddressField", deviceDataAddressField)     
				.addValue("deviceDataField", deviceDataField)     
				.addValue("deviceDataTrackingField", deviceDataTrackingField)  
				.addValue("deviceDataRiskField", deviceDataRiskField)  
				.addValue("deviceDataRunningField", deviceDataRunningField)  
				.addValue("deviceDataField1", deviceDataField1)     
				.addValue("deviceDataLiveField", deviceDataLiveField)       
				.addValue("deviceDataErrorField", deviceDataErrorField)                
				.addValue("subCategory", deviceData.getSubCategory())              
				.addValue("category", deviceData.getCategory())         
				.addValue("status", deviceData.getStatus())           
				.addValue("subType", deviceData.getSubType())  
				.addValue("type", deviceData.getType())    
				.addValue("modifiedAt", deviceData.getModifiedAt())   
				.addValue("modifiedBy", deviceData.getModifiedBy());    
		  
		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);   
			 
		if (status > 0) {  
			return deviceData;      
		} 
		return new DeviceData();   
	}
 
}