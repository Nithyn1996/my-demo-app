package com.common.api.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.common.api.constant.APIFixedConstant;
import com.common.api.model.Device;
import com.common.api.model.DeviceData;
import com.common.api.model.field.DeviceDataAddressField;
import com.common.api.model.field.DeviceDataField;
import com.common.api.model.field.DeviceDataField1;
import com.common.api.model.field.DeviceDataRiskField;
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.DeviceField;
import com.common.api.model.field.KeyValuesFloat;
import com.common.api.model.type.CategoryType;
import com.common.api.resource.DeviceDataResource;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.CSVFileUtil;
import com.common.api.util.FieldValidator;
import com.common.api.util.ResultSetConversion;

@Service
public class ServiceCSVFile  extends APIFixedConstant {  

	@Autowired
	DeviceDataResource deviceDataResource;
	@Autowired
	ResultSetConversion rsConversion;
	@Autowired
	CSVFileUtil csvFileUtil;
	  
	enum dDataFields {   
	  					portionId, timezoneCode, accessLevel, category,   
	  					subCategory, subType, city, country, location, state, createdAt, accelerX, accelerY, accelerZ,  
	  					alert, alertId, alertName, alertValue, anticipation, deviceMode, deviceCode, drivingScore, drivingVehicleNo,
	  					drivingSkill, ehorizonLength, engineState, gpsCount, gpsTimeDiff, gyroscopeX, gyroscopeY, gyroscopeZ, 
	  					highwayKilometer, highwayPercent, kiloMeter, latitude, longitude, magnetometerX, magnetometerY, magnetometerZ,   
	  					previousSpeed, previousTravelTime, rideTime, risk, ruralKilometer, ruralPercent, selfConfidence, senRotate,   
	  					speed, travelTime, urbanKilometer, urbanPercent, zipCode, driverState, drivingStyle,
	  					riskStyle, trafficScore, transportMode, subCategoryLevel, riskNegativeCount, riskZeroCount, riskSlot1Count, 
	  					riskSlot2Count, riskSlot3Count, riskSlot4Count, riskSlot5Count, riskSlot6Count, riskSlot7Count,
	  					riskSlot8Count, riskSlot9Count, riskSlot10Count,riskSlot11Count, speedLimit, activityDuration, activityKiloMeter,
	  					deviceCategory, deviceSubCategory, redAlertDuration, redAlertDistance, pointOfInterest, batteryLevel,healthLevel,
	  					mobileScreenOnDuration, mobileScreenOnKiloMeter, overSpeedKiloMeter, 
	  					mobileUseCallDuration, mobileUseCallKiloMeter, 
	  					overSpeedDistance, overSpeedDuration,
	  					mobileScreenScreenOnDistance, mobileScreenScreenOnDuration, 
	  					mobileUseInAcceptedDistance, mobileUseInAcceptedDuration, 
	  					mobileUseOutAcceptedDistance, mobileUseOutAcceptedDuration,  
	  			   }	
	  
	public List<Device> convertCSVToDevice(MultipartFile multipartFile) { 
		  
		InputStream is = null; 
		String logString = "API_LOG_DEVICE_CSV "; 		
		
		try {
			is = multipartFile.getInputStream();
		} catch (IOException e) {
		}
		  
		int successRowCount = 0, failedRowCount = 0; 
	  	List<Device> ruduRideList = new ArrayList<Device>(); 
	  	
	  	if (is != null) {
	  	   
			  try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				   CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) 
			  {
	
			      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			      
			      for (CSVRecord csvRecord : csvRecords) { 
			   
			    	  try { 

			    		  String name               = "Ride"; 
			    		  String locationName       = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.location.name());    
			    		  String deviceCity         = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.city.name()); 
			    		  String deviceState        = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.state.name()); 
			    		  String deviceCountry      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.country.name()); 
			    		  String deviceZipCode      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.zipCode.name()); 
			    		  String deviceSubType      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.subType.name()); 
			    		  String deviceLatitude     = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.latitude.name()); 
			    		  String deviceLongitude    = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.longitude.name()); 
			    		  String timezoneCode       = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.timezoneCode.name());    
			    		  String createdAt 	     	= csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.createdAt.name());  
			    		  String deviceMode         = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.deviceMode.name()); 
			    		  String deviceAccessLevel  = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.accessLevel.name());
			    		  String deviceCategory     = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.deviceCategory.name());
			    		  String deviceSubCategory  = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.deviceSubCategory.name());
			    		  
			    		  try {
			    			  createdAt = createdAt.replace("===", " "); 
			    		  } catch (Exception errMess) { }
			    		   
			    		  /**
			    		  System.out.println("******************************************************************");
 			    		  
			    		  System.out.println("timezoneCode : " + timezoneCode);  
			    		  System.out.println("name : " + name + " => " + FieldValidator.isValid(name, VT_TYPE,FL_TYPE_MIN, FL_TYPE_MAX, true));
						  System.out.println("locationName : " + locationName + " => " + FieldValidator.isValid(locationName, VT_NAME, FL_NAME_MIN, FL_NAME_MAX, false));
						  System.out.println("deviceCity : " + deviceCity + " => " + FieldValidator.isValid(deviceCity, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false));
						  System.out.println("deviceState : " + deviceState + " => " + FieldValidator.isValid(deviceState, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false));
						  System.out.println("deviceCountry : " + deviceCountry + " => " + FieldValidator.isValid(deviceCountry, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false));
						  System.out.println("deviceZipCode : " + deviceZipCode + " => " + FieldValidator.isValid(deviceZipCode, VT_NUMBER, FL_NUMBER_MIN, FL_NUMBER_MAX, false));
						  System.out.println("deviceSubType : " + deviceSubType+ " => " + FieldValidator.isValid(deviceSubType,VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false));
						  System.out.println("deviceLatitude : " + deviceLatitude+ " => " + FieldValidator.isValid(deviceLatitude, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false));
						  System.out.println("deviceLongitude : " + deviceLongitude+ " => " + FieldValidator.isValid(deviceLongitude, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false));
						  System.out.println("deviceAccessLevel : " + deviceAccessLevel+ " => " + FieldValidator.isValid(deviceAccessLevel, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false));
			    		  */
			    		  
						  int errorCount = 0; 	 
			    		  deviceMode = deviceDataResource.extraUpdateDeviceMode(deviceMode);
			    		  	
						  if(FieldValidator.isValid(createdAt, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, true) == false ||
							 //FieldValidator.isValid(name, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, true) == false                      ||
							 FieldValidator.isValidFreeFormText(locationName, FL_NAME_MIN, FL_NAME_MAX, false) == false  	          ||
							 FieldValidator.isValid(deviceCity, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false) == false     ||
							 FieldValidator.isValid(deviceState, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false) == false     ||
							 FieldValidator.isValid(deviceCountry, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX,false) == false      ||
							 FieldValidator.isValid(deviceZipCode, VT_NUMBER, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false       ||
							 FieldValidator.isValid(deviceSubType, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false          ||
							 FieldValidator.isValid(deviceLatitude, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false              ||
							 FieldValidator.isValid(deviceLongitude, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false             ||
							 FieldValidator.isValid(deviceAccessLevel, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false     ||
			    			 FieldValidator.isValid(deviceMode, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false            
							) {
			    			  errorCount++; 
						  }
						  
			    		 if (errorCount <= 0) { 
			    			 
			    			 Timestamp createdAtFinal = APIDateTime.convertStringDateTimeToTimestamp(createdAt);
								
			    			 Device device = new Device();  
				    			 device.setAccessLevel(deviceAccessLevel); 
				    			 device.setName(name);
				    			 device.setDeviceMode(deviceMode); 
				    			 device.setSubType(deviceSubType);
				    			 device.setTimezoneCode(timezoneCode);  
				    			 device.setLocationName(locationName); 
				    			 device.setZipCode(deviceZipCode); 
				    			 device.setLatitude(rsConversion.resultSetToFloatValue(deviceLatitude, true)); 
				    			 device.setLongitude(rsConversion.resultSetToFloatValue(deviceLongitude, true));
				    			 device.setStartDateTime(createdAtFinal); 
				    			 device.setEndDateTime(createdAtFinal); 
				    			 device.setCategory(deviceCategory);
				    			 device.setSubCategory(deviceSubCategory);  
				    			 device.setCreatedAt(createdAtFinal); 

			    			 DeviceField deviceField = new DeviceField(); 
				    			 deviceField.setCity(deviceCity);
				    			 deviceField.setState(deviceState); 
				    			 deviceField.setCountry(deviceCountry);
				    			 deviceField.setZipCode(deviceZipCode);
			    			 device.setDeviceField(deviceField);
			    			 
				    		 ruduRideList.add(device);
				    		 successRowCount++;	    		 
			    		 } else {
			    			 failedRowCount++;	 
			    		 } 
			    	  } catch (Exception errMess) {  
			    		  APILog.writeInfoLog(logString + " Exception=> errMess: " + errMess.getMessage());  
			    	  }	
			    	  return ruduRideList; 
			      }
			      
			      APILog.writeInfoLog(logString + " successRowCount: " + successRowCount + " failedRowCount: " + failedRowCount); 		            
			      return ruduRideList;			      
			  } catch (IOException errMess) {  
	    		  APILog.writeInfoLog(logString + " IOException=> errMess: " + errMess.getMessage());  
			  }
	  	}
	  	return ruduRideList; 
	}
	
	public List<DeviceData> convertCSVToDeviceData(MultipartFile multipartFile) {
		  
		String logString = "API_LOG_DEVICE_DATA_CSV ";
 		  
		InputStream is = null;  
		try { 
			is = multipartFile.getInputStream();
		} catch (IOException errMess) {
			APILog.writeInfoLog(logString + " IOException InputStream => errMess: " + errMess.getMessage());
		}
		  
		int successRowCount = 0, failedRowCount = 0; 
	  	List<DeviceData> ruduRideList = new ArrayList<DeviceData>(); 
	  	  
	  	if (is != null) { 

	  		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	  				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
							   															.withIgnoreHeaderCase() 
							   															.withTrim())) 
	  		{
	  			
	  			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			      
	  			for (CSVRecord csvRecord : csvRecords) {
			    	    
			    	try { 
			    		
			    		// Mandatory Fields	
			    		String status 		    = "REGISTERED";   
			    		String type 		    = "SPEEDO_METER_DEVICE_DATA"; 
			    		String categ 		    = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.category.name());  
			    		String subCategory      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.subCategory.name());  
			    		String createdAt	    = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.createdAt.name());  
			    		String anticipation     = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.anticipation.name()); 
			    		String drivingScore     = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.drivingScore.name());  
			    		String drivingSkill     = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.drivingSkill.name());
			    		String selfConfidence   = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.selfConfidence.name());  
			    		String drivingVehicleNo = csvFileUtil.formatFieldValueAsFloat(csvRecord, dDataFields.drivingVehicleNo.name());

			    		// Optional Fields with validation
			    		String country            = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.country.name());  
			    		String state              = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.state.name());  
			    		String city               = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.city.name());  
			    		String location           = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.location.name());  
			    		String latitude           = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.latitude.name());  
			    		String longitude          = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.longitude.name()); 
			    		String zipCode            = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.zipCode.name()); 
			    		String accLevel           = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.accessLevel.name()); 
			    		String subType            = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.subType.name());  
			    		String deviceMode         = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.deviceMode.name()); 
			    		String deviceCode		  = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.deviceCode.name());
			    		String kiloMeter          = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.kiloMeter.name());    
 			    		String urbanKilometer     = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.urbanKilometer.name());     
			    		String highwayKilometer   = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.highwayKilometer.name());       
			    		String ruralKilometer     = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.ruralKilometer.name());  
			    		String urbanPercent       = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.urbanPercent.name());         
			    		String highwayPercent     = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.highwayPercent.name());     
			    		String ruralPercent       = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.ruralPercent.name());       
			    		String travelTime         = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.travelTime.name());           
			    		String speed              = csvFileUtil.formatFieldValueAsFloat(csvRecord,  dDataFields.speed.name());     
			    		String subCategoryLevel   = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.subCategoryLevel.name());                     
			    		 
			    		float redAlertDuration   = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.redAlertDuration.name());
			    		float redAlertDistance   = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.redAlertDistance.name());
			    		float pointOfInterest    = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.pointOfInterest.name());
			    		
		    		  	// Optional Fields without validation
			    		String accelerX           = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.accelerX.name());    
			    		String accelerY           = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.accelerY.name());        
			    		String accelerZ           = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.accelerZ.name());      
			    		String alert              = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.alert.name());  
			    		String alertId            = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.alertId.name());         
			    		String alertName          = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.alertName.name());  
 			    		String alertValue         = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.alertValue.name());  
			    		String ehorizonLength     = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.ehorizonLength.name());  
			    		String engineState        = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.engineState.name());  
			    		String gpsCount           = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.gpsCount.name());  
			    		String gpsTimeDiff        = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.gpsTimeDiff.name());           
			    		String gyroscopeX         = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.gyroscopeX.name());         
			    		String gyroscopeY         = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.gyroscopeY.name());           
			    		String gyroscopeZ         = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.gyroscopeZ.name());         
			    		String magnetometerX      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.magnetometerX.name());         
			    		String magnetometerY      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.magnetometerY.name());         
			    		String magnetometerZ      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.magnetometerZ.name());         
			    		String previousSpeed      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.previousSpeed.name());        
			    		String rideTime           = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.rideTime.name());  
			    		String risk               = csvFileUtil.formatFieldValueAsFloat(csvRecord, dDataFields.risk.name());  
			    		String senRotate          = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.senRotate.name());               
			    		String driverState        = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.driverState.name());           
			    		String drivingStyle       = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.drivingStyle.name());  
			    		String riskStyle          = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.riskStyle.name());               
			    		String trafficScore       = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.trafficScore.name());          
			    		String transportMode      = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.transportMode.name());   
			    		String previousTravelTime = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.previousTravelTime.name());   
			    		String speedLimit		  = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.speedLimit.name());   
			    		String activityDuration	  = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.activityDuration.name());   
			    		String activityKiloMeter  = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.activityKiloMeter.name());   
			    		String batteryLevel   	  = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.batteryLevel.name());
			    		String healthLevel		  = csvFileUtil.formatFieldValueAsString(csvRecord, dDataFields.healthLevel.name());
			    		System.out.println("healthLEvel="+healthLevel);
			    		int riskNegativeCount = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskNegativeCount.name());   
			    		int riskZeroCount 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskZeroCount.name());   
			    		int riskSlot1Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot1Count.name());   
			    		int riskSlot2Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot2Count.name());   
			    		int riskSlot3Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot3Count.name());    
 			    		int riskSlot4Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot4Count.name());    
 			    		int riskSlot5Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot5Count.name());   
 			    		int riskSlot6Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot6Count.name());   
 			    		int riskSlot7Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot7Count.name());   
 			    		int riskSlot8Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot8Count.name());   
 			    		int riskSlot9Count 	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot9Count.name());   
 			    		int riskSlot10Count   = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot10Count.name());
 			    		int riskSlot11Count	  = csvFileUtil.formatFieldValueAsInteger(csvRecord, dDataFields.riskSlot11Count.name());
 			    		  
 			    		/** Running Fields */ 
			    		float overSpeedKiloMeter      = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.overSpeedKiloMeter.name()); // To be removed
			    		float mobileScreenOnKiloMeter = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileScreenOnKiloMeter.name()); // To be removed
			    		float mobileScreenOnDuration  = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileScreenOnDuration.name()); // To be removed 
			    		float mobileUseCallKiloMeter  = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileUseCallKiloMeter.name()); // To be remove
			    		float mobileUseCallDuration   = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileUseCallDuration.name()); // To be remove
			    		
			    		float overSpeedDistance       = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.overSpeedDistance.name());
			    		float overSpeedDuration       = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.overSpeedDuration.name());    
			    		    
			    		float mobileUseInAcceptedDistance  = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileUseInAcceptedDistance.name()); 
			    		float mobileUseInAcceptedDuration  = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileUseInAcceptedDuration.name());
			    		
			    		float mobileUseOutAcceptedDistance = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileUseOutAcceptedDistance.name()); 
			    		float mobileUseOutAcceptedDuration = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileUseOutAcceptedDuration.name()); 
			    		
			    		float mobileScreenScreenOnDistance = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileScreenScreenOnDistance.name());  
			    		float mobileScreenScreenOnDuration = csvFileUtil.formatFieldValueAsFloatValue(csvRecord, dDataFields.mobileScreenScreenOnDuration.name());  
			    	
 			    		try {  
			    			createdAt = createdAt.replace("===", " ");
			    		} catch (Exception errMess) { }
 			    		
 			    		overSpeedDistance = (overSpeedDistance > 0) ? overSpeedDistance : overSpeedKiloMeter;
 			    		mobileUseInAcceptedDistance  = (mobileUseInAcceptedDistance > 0)  ? mobileUseInAcceptedDistance : mobileUseCallKiloMeter;
 			    		mobileUseInAcceptedDuration  = (mobileUseInAcceptedDuration > 0)  ? mobileUseInAcceptedDuration : mobileUseCallDuration;
 			    		mobileScreenScreenOnDistance = (mobileScreenScreenOnDistance > 0) ? mobileScreenScreenOnDistance : mobileScreenOnKiloMeter;
 			    		mobileScreenScreenOnDuration = (mobileScreenScreenOnDuration > 0) ? mobileScreenScreenOnDuration : mobileScreenOnDuration;
 			    		
 			    		List<KeyValuesFloat> keyValuesFloat = Arrays.asList( 
 			    				new KeyValuesFloat(rsConversion.concatenateTwoString(CategoryType.DeviceDataSubCategory.OVER_SPEED.toString(), ""), Arrays.asList(overSpeedDistance, overSpeedDuration)),
 			    				new KeyValuesFloat(rsConversion.concatenateTwoString(CategoryType.DeviceDataSubCategory.MOBILE_USE.toString(), CategoryType.DeviceDataSubCategoryLevel.IN_ACCEPTED.toString()), Arrays.asList(mobileUseInAcceptedDistance, mobileUseInAcceptedDuration)),
 			    				new KeyValuesFloat(rsConversion.concatenateTwoString(CategoryType.DeviceDataSubCategory.MOBILE_USE.toString(), CategoryType.DeviceDataSubCategoryLevel.OUT_ACCEPTED.toString()), Arrays.asList(mobileUseOutAcceptedDistance, mobileUseOutAcceptedDuration)),
 			    				new KeyValuesFloat(rsConversion.concatenateTwoString(CategoryType.DeviceDataSubCategory.MOBILE_SCREEN.toString(), CategoryType.DeviceDataSubCategoryLevel.SCREEN_ON.toString()), Arrays.asList(mobileScreenScreenOnDistance, mobileScreenScreenOnDuration))   
 			    			); 
		  				
			    		/**
		    		  	System.out.println("******************************************************************");
		    		  
		    		  	System.out.println("categ : " + FieldValidator.isValid(categ, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, true) +  " "                              + categ);
		    		  	System.out.println("status : " + FieldValidator.isValid(status, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, true) +  " "                            + status);
		    		  	System.out.println("subCategory : " + FieldValidator.isValid(subCategory, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                 + subCategory);
		    		  	
		    		  	System.out.println("type : " + FieldValidator.isValid(type, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, true) + " "                                 + type);
		    		  	System.out.println("createdAt : " + FieldValidator.isValid(createdAt, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, true) +  " "     + createdAt);
		    		  	System.out.println("anticipation : " + FieldValidator.isValid(anticipation, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, true) +  " "               + anticipation);
		    		  	System.out.println("drivingScore : " + FieldValidator.isValid(drivingScore, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, true) +  " "               + drivingScore);
		    		  	System.out.println("drivingSkill : " + FieldValidator.isValid(drivingSkill, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, true) +  " "               + drivingSkill);
		    		  	System.out.println("selfConfidence : " +  FieldValidator.isValid(selfConfidence, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, true) +  " "          + selfConfidence);
		    		  	System.out.println("country : " +FieldValidator.isValid(country, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                   + country);
		    		  	System.out.println("state : " + FieldValidator.isValid(state, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                      + state);
		    		  	System.out.println("city : " + FieldValidator.isValid(city, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                        + city);
		    		  	System.out.println("location : " +  FieldValidator.isValidFreeFormText(location, FL_FREE_FORM_MIN, FL_FREE_FORM_MAX, false) +  " "         + location);
		    		  	System.out.println("latitude : " +FieldValidator.isValid(latitude, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                       + latitude);
		    		  	System.out.println("longitude : " + FieldValidator.isValid(longitude, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                    + longitude);
		    		  	System.out.println("subCategoryLevel : " +  FieldValidator.isValid(subCategoryLevel, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) +  " "  + subCategoryLevel);

		    		  	System.out.println("zipCode : " + FieldValidator.isValid(zipCode, VT_NUMBER, FL_NUMBER_MIN, FL_NUMBER_MAX, false) +  " "                   + zipCode);
		    		  	System.out.println("accLevel : " + FieldValidator.isValid(accLevel, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) +  " "                   + accLevel);
		    		  	System.out.println("subType : " +  FieldValidator.isValid(subType, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) +  " "                    + subType);
		    		  	System.out.println("deviceMode : " + FieldValidator.isValid(deviceMode, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) +  " "               + deviceMode);
		    		  	System.out.println("urbanKilometer : " + FieldValidator.isValid(urbanKilometer, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "          + urbanKilometer);
		    		  	System.out.println("highwayKilometer : " +  FieldValidator.isValid(highwayKilometer, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "     + highwayKilometer);
		    		  	System.out.println("kiloMeter : " + FieldValidator.isValid(kiloMeter, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                    + kiloMeter);
		    		  	System.out.println("ruralKilometer : " + FieldValidator.isValid(ruralKilometer, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "          + ruralKilometer);
		    		  
		    		  	System.out.println("urbanPercent : " + FieldValidator.isValid(urbanPercent, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "              + urbanPercent);
		    		  	System.out.println("highwayPercent : " + FieldValidator.isValid(highwayPercent, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "          + highwayPercent);
		    		  	System.out.println("ruralPercent : " + FieldValidator.isValid(ruralPercent, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "              + ruralPercent);
		    		  	System.out.println("travelTime : " + FieldValidator.isValid(travelTime, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                  + travelTime);
		    		  	System.out.println("speed : " + FieldValidator.isValid(speed, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) +  " "                            + speed); 
		    		    */ 
		    		  	int errorCount = 0;    
		    		  	deviceMode = deviceDataResource.extraUpdateDeviceMode(deviceMode); 

		    		  	if (FieldValidator.isValid(categ, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, true) == false                      ||
		    		  		FieldValidator.isValid(status, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, true) == false                     ||
		    		  		FieldValidator.isValid(subCategory, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, false) == false               ||
		    		  		FieldValidator.isValid(type, VT_TYPE, FL_TYPE_MIN, FL_TYPE_MAX, true) == false                       || 
		    		  		FieldValidator.isValid(createdAt, VT_DATE_TIME, FL_DATE_TIME_SIZE, FL_DATE_TIME_SIZE, true) == false ||
		    		  		FieldValidator.isValid(anticipation, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, true) == false              ||
		    		  		FieldValidator.isValid(drivingScore, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, true) == false              ||
		    		  		FieldValidator.isValid(drivingSkill, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, true) == false              ||
		    		  		FieldValidator.isValid(selfConfidence, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, true) == false            || 
		    		  		FieldValidator.isValid(country, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false) == false            ||
		    		  		FieldValidator.isValid(state, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false) == false              ||
		    		  		FieldValidator.isValid(city, VT_DESCRIPTION, FL_TYPE_MIN, FL_TYPE_MAX, false) == false               ||
		    		  		FieldValidator.isValidFreeFormText(location, FL_FREE_FORM_MIN, FL_FREE_FORM_MAX, false) == false     ||
		    		  		FieldValidator.isValid(latitude, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false                 ||
		    		  		FieldValidator.isValid(longitude, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false                ||
		    		  		FieldValidator.isValid(zipCode, VT_NUMBER, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false             ||
		    		  		FieldValidator.isValid(accLevel, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false              || 
		    		  		FieldValidator.isValid(subType, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false               ||
		    		  		FieldValidator.isValid(deviceMode, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false            ||
		    		  		FieldValidator.isValid(deviceCode, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false			 ||
 		    		  		FieldValidator.isValid(urbanKilometer, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false           ||
		    		  		FieldValidator.isValid(highwayKilometer, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false         ||
		    		  		FieldValidator.isValid(kiloMeter, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false                ||
		    		  		FieldValidator.isValid(ruralKilometer, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false           ||
 		    		  		FieldValidator.isValid(urbanPercent, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false             ||
		    		  		FieldValidator.isValid(highwayPercent, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false           ||
		    		  		FieldValidator.isValid(ruralPercent, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false             ||
		    		  		FieldValidator.isValid(travelTime, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false               || 
		    		  		FieldValidator.isValid(speed, VT_FLOAT, FL_TYPE_MIN, FL_TYPE_MAX, false) == false 			         || 
		    		  		FieldValidator.isValid(subCategoryLevel, VT_TYPE, FL_NUMBER_MIN, FL_NUMBER_MAX, false) == false             
		    		  		) { 
		    		  		errorCount++;
		    		  	}

		    		  	if (errorCount <= 0) {  

		    		  		Timestamp createdAtFinal = APIDateTime.convertStringDateTimeToTimestamp(createdAt);
		    		  		 
		    		  		DeviceData deviceData = new DeviceData(); 
					    		 deviceData.setKiloMeter(rsConversion.resultSetToFloatValue(kiloMeter, false));
					    		 deviceData.setRisk(rsConversion.resultSetToFloatValue(risk, false));  
					    		 deviceData.setSpeed(rsConversion.resultSetToFloatValue(speed, false)); 
					    		 deviceData.setSpeedLimit(rsConversion.resultSetToFloatValue(speedLimit, false)); 
					    		 deviceData.setPreviousSpeed(rsConversion.resultSetToFloatValue(previousSpeed, false)); 
					    		 deviceData.setActivityDuration(rsConversion.resultSetToFloatValue(activityDuration, false));  
					    		 deviceData.setActivityKiloMeter(rsConversion.resultSetToFloatValue(activityKiloMeter, false)); 
					    		 deviceData.setLatitude(rsConversion.resultSetToFloatValue(latitude, true));  
					    		 deviceData.setLongitude(rsConversion.resultSetToFloatValue(longitude, true));   
					    		 deviceData.setDeviceMode(deviceMode);
					    		 deviceData.setDeviceCode(deviceCode);
					    		 deviceData.setZipCode(zipCode); 
					    		 deviceData.setAccessLevel(accLevel);   
					    		 
					    		 deviceData.setRedAlertDuration(redAlertDuration);
					    		 deviceData.setRedAlertDistance(redAlertDistance);
					    		 deviceData.setPointOfInterest(pointOfInterest); 
					    		 
					    		 deviceData.setCreatedAt(createdAtFinal); 
					    		 deviceData.setStatus(status); 
					    		 deviceData.setSubCategoryLevel(subCategoryLevel);
					    		 deviceData.setSubCategory(subCategory);
					    		 deviceData.setCategory(categ);
					    		 deviceData.setSubType(subType); 
					    		 deviceData.setType(type);
			    		 
					    	DeviceDataAddressField deviceDataAddressField = new DeviceDataAddressField();
					    		 deviceDataAddressField.setCity(city);
					    		 deviceDataAddressField.setCountry(country);
					    		 deviceDataAddressField.setLocation(location);
					    		 deviceDataAddressField.setState(state);
					    	deviceData.setDeviceDataAddressField(deviceDataAddressField);
			    		 
					    	DeviceDataField1 deviceDataField1 = new DeviceDataField1();
					    		 deviceDataField1.setDriverState(driverState);
					    		 deviceDataField1.setDrivingStyle(drivingStyle);
					    		 deviceDataField1.setRiskStyle(riskStyle); 
					    		 deviceDataField1.setTrafficScore(trafficScore); 
					    		 deviceDataField1.setTransportMode(transportMode);
					    		 deviceDataField1.setBatteryLevel(batteryLevel); 
					    		 deviceDataField1.setHealthLevel(healthLevel);
					    		 deviceData.setDeviceDataField1(deviceDataField1);
			    		 
				    		DeviceDataField deviceDataField = new DeviceDataField();
					    		 deviceDataField.setAccelerX(accelerX);
					    		 deviceDataField.setAccelerY(accelerY);
					    		 deviceDataField.setAccelerZ(accelerZ);
					    		 deviceDataField.setAlert(alertValue);
					    		 deviceDataField.setAlertId(alertId);
 					    		 deviceDataField.setAlertName(alertName);
 					    		 deviceDataField.setAlertValue(alertValue); 
					    		 deviceDataField.setAnticipation(anticipation);
					    		 deviceDataField.setDeviceMode(deviceMode);
					    		 deviceDataField.setDrivingScore(drivingScore);
					    		 deviceDataField.setDrivingSkill(drivingSkill);
					    		 deviceDataField.setEhorizonLength(ehorizonLength);
					    		 deviceDataField.setEngineState(engineState);
					    		 deviceDataField.setGpsCount(gpsCount);
					    		 deviceDataField.setGpsTimeDiff(gpsTimeDiff);
					    		 deviceDataField.setGyroscopeX(gyroscopeX);
					    		 deviceDataField.setGyroscopeY(gyroscopeY);
					    		 deviceDataField.setGyroscopeZ(gyroscopeZ);
					    		 deviceDataField.setHighwayKilometer(highwayKilometer);
					    		 deviceDataField.setHighwayPercent(highwayPercent);
					    		 deviceDataField.setLatitude(latitude);
					    		 deviceDataField.setLongitude(longitude);
					    		 deviceDataField.setMagnetometerX(magnetometerX);
					    		 deviceDataField.setMagnetometerY(magnetometerY);
					    		 deviceDataField.setMagnetometerZ(magnetometerZ); 
					    		 deviceDataField.setPreviousTravelTime(previousTravelTime);
					    		 deviceDataField.setRideTime(rideTime);
					    		 deviceDataField.setRisk(risk);
					    		 deviceDataField.setRuralKilometer(ruralKilometer);
					    		 deviceDataField.setRuralPercent(ruralPercent);
					    		 deviceDataField.setSelfConfidence(selfConfidence);
					    		 deviceDataField.setSenRotate(senRotate);
					    		 deviceDataField.setSpeed(speed);
					    		 deviceDataField.setUrbanKilometer(urbanKilometer);
					    		 deviceDataField.setUrbanPercent(urbanPercent);
					    		 deviceDataField.setZipCode(zipCode);
					    		 deviceDataField.setAlert(alert); 				 	    	
					    		 deviceDataField.setTravelTime(travelTime);		    	
					    		 deviceDataField.setAlertTime(travelTime);	 
					    		 deviceDataField.setKiloMeter(kiloMeter);
					    		 deviceDataField.setTotalKiloMeter(kiloMeter);
					    		 deviceDataField.setAlertKiloMeter(kiloMeter);
					    		 deviceDataField.setPreviousSpeed(previousSpeed);  
					    		 deviceDataField.setActivityDuration(activityDuration);
					    		 deviceDataField.setDrivingVehicleNo(drivingVehicleNo);
					    		 deviceDataField.setSpeedLimit(speedLimit);  
					    		 deviceData.setDeviceDataField(deviceDataField);	 
					    		 
					    	
					    	DeviceDataRiskField deviceDataRiskField = new DeviceDataRiskField();
					    		deviceDataRiskField.setSlotNegativeCount(riskNegativeCount);
					    		deviceDataRiskField.setSlotZeroCount(riskZeroCount);
					    		deviceDataRiskField.setSlot1Count(riskSlot1Count);
					    		deviceDataRiskField.setSlot2Count(riskSlot2Count);
					    		deviceDataRiskField.setSlot3Count(riskSlot3Count); 
					    		deviceDataRiskField.setSlot4Count(riskSlot4Count);
					    		deviceDataRiskField.setSlot5Count(riskSlot5Count);
					    		deviceDataRiskField.setSlot6Count(riskSlot6Count);
					    		deviceDataRiskField.setSlot7Count(riskSlot7Count);
					    		deviceDataRiskField.setSlot8Count(riskSlot8Count);
					    		deviceDataRiskField.setSlot9Count(riskSlot9Count);
					    		deviceDataRiskField.setSlot10Count(riskSlot10Count);
					    		deviceDataRiskField.setSlot11Count(riskSlot11Count);
					    	deviceData.setDeviceDataRiskField(deviceDataRiskField); 
					    		
					    	DeviceDataRunningField deviceDataRunningField = new DeviceDataRunningField();
					    		deviceDataRunningField.setMobileScreenOnDuration(mobileScreenOnDuration);
					    		deviceDataRunningField.setMobileUseCallDuration(mobileUseCallDuration);
					    		deviceDataRunningField.setOverSpeedDuration(overSpeedDuration);
					    		deviceDataRunningField.setMobileScreenOnKiloMeter(mobileScreenOnKiloMeter);
					    		deviceDataRunningField.setMobileUseCallKiloMeter(mobileUseCallKiloMeter); 
					    		deviceDataRunningField.setOverSpeedKiloMeter(overSpeedKiloMeter); 
					    		deviceDataRunningField.setKeyValues(keyValuesFloat);
					    	deviceData.setDeviceDataRunningField(deviceDataRunningField);
					    	
					    	ruduRideList.add(deviceData);  
					    	successRowCount++;	    
					    	
		    		  	} else {  
		    		  		failedRowCount++;	
		    		  	} 

			    	} catch (Exception errMess) { 
		    		  APILog.writeInfoLog(logString + " Exception=> errMess: " + errMess.getMessage());  
			    	}	    	  
	  			}
			      
	  			APILog.writeInfoLog(logString + " successRowCount: " + successRowCount + " failedRowCount: " + failedRowCount);
	  			System.out.println(ruduRideList);
	  			return ruduRideList;			      
			} catch (IOException errMess) {    
				  APILog.writeInfoLog(logString + " IOException=> errMess: " + errMess.getMessage());
		    }
	  	}
	  	return ruduRideList;
	} 
	  
}