package com.common.api.util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.common.api.model.Device;
import com.common.api.model.Portion;
import com.common.api.model.Property;
import com.common.api.model.RoleUser;
import com.common.api.model.Section;
import com.common.api.model.UserPreference;
import com.common.api.model.field.CompanyField;
import com.common.api.model.field.CompanyPreferenceField;
import com.common.api.model.field.DeviceDataAddressField;
import com.common.api.model.field.DeviceDataErrorField;
import com.common.api.model.field.DeviceDataField;
import com.common.api.model.field.DeviceDataField1;
import com.common.api.model.field.DeviceDataLiveField;
import com.common.api.model.field.DeviceDataRiskField;
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.DeviceDataTrackingField;
import com.common.api.model.field.DeviceField;
import com.common.api.model.field.DeviceSafetyField;
import com.common.api.model.field.DivisionField;
import com.common.api.model.field.DivisionPreferenceField;
import com.common.api.model.field.GroupField;
import com.common.api.model.field.GroupPreferenceField;
import com.common.api.model.field.MemberDeviceField;
import com.common.api.model.field.MemberField;
import com.common.api.model.field.ModuleField;
import com.common.api.model.field.ModulePrefAppBootSetting;
import com.common.api.model.field.ModulePreferenceField;
import com.common.api.model.field.PortionField;
import com.common.api.model.field.PropertyField;
import com.common.api.model.field.SectionField;
import com.common.api.model.field.SectionPrefVehicleInspection;
import com.common.api.model.field.UserAuthenticationField;
import com.common.api.model.field.UserDeviceSessionField;
import com.common.api.model.field.UserFeedbackField;
import com.common.api.model.field.UserField;
import com.common.api.model.field.UserPrefAppBootSetting;
import com.common.api.model.field.UserPreferenceField;
import com.common.api.model.field.UserSessionField;
import com.common.api.model.field.UserVerificationField;
import com.common.api.model.util.PredefinedField;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper; 
 
@Service 
public class ResultSetConversion {   
	
	public int getResultsetColumnValueType(String inputValue) {
		
		// 1 for String, 2 for JSON Object and 3 for JSON Array of objects
		try {
            new JSONObject(inputValue); 
            return 2;
        } catch (JSONException ex) {
            try {
                new JSONArray(inputValue);
                return 3;
            } catch (JSONException errMess) {
                return 1;
            }
        } 
        
    }
	
	public String arrayListObjectToString(List<?> inputText) {  
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(inputText);       
		} catch (IOException errMess) {   
		} 
		return result;   
	} 
	  
	public String arrayMapObjectToString(List<Map<String, Object>> inputText) {  
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(inputText);       
		} catch (IOException errMess) {   
		} 
		return result;   
	} 
		  
	public String userSessionFieldToString(UserSessionField userSessionField) {  
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(userSessionField);       
		} catch (IOException errMess) {   
		} 
		return result;   
	} 
	  
	public String userDeviceSessionFieldToString(UserDeviceSessionField userDeviceSessionField) {  
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(userDeviceSessionField);       
		} catch (IOException errMess) {   
		} 
		return result;   
	} 
	
	public Time resultSetToTime(ResultSet rs, String key) {  
 		try {       
			Time result = rs.getTime(key); 
			return (result != null) ? result : null;    
		} catch (Exception errMess) {   
		} 
		return null;    
	} 	

	public String resultSetToString(ResultSet rs, String key) {  
 		try {     
			String result = rs.getString(key); 
			return (result != null) ? result : "";    
		} catch (Exception errMess) {   
		} 
		return "";   
	} 	
	
	public float resultSetToFloat(ResultSet rs, String key) {  
 		try {    
 			return rs.getFloat(key);  
		} catch (Exception errMess) {   
		}  
		return 0;    
	} 
	
	public Integer resultSetToInteger(ResultSet rs, String key) {  
 		try {    
			Integer result = rs.getInt(key); 
			return (result != null) ? result : 0;     
		} catch (Exception errMess) {   
		} 
		return 0;    
	} 
	
	public Long resultSetToLong(ResultSet rs, String key) {  
 		try {    
			Long result = rs.getLong(key);  
			return (result != null) ? result : 0L;     
		} catch (Exception errMess) {   
		} 
		return 0L;    
	} 
	
	public Timestamp resultSetToTimestamp(ResultSet rs, String key) {  
 		try {    
			return rs.getTimestamp(key);    
		} catch (Exception errMess) {   
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
		} 
		return null;     
	}
	 
	public JsonNode convertMapToJsonNode(List<Map<String,Object>> inputValue) { 
	    try {
	    	ObjectMapper mapper = new ObjectMapper(); 
	    	return mapper.readTree(mapper.writeValueAsString(inputValue));
	    } catch (Exception errMess) {
	    	APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
	    	ObjectMapper mapper = new ObjectMapper();
	    	return mapper.createObjectNode();
	    } 
	}
	
	public JsonNode convertStringToJsonNode(String inputValue) {
	    try { 
	    	if (inputValue != null && inputValue.length() > 0) {
	    		ObjectMapper mapper = new ObjectMapper();
	    		return mapper.readTree(inputValue);
	    	} else {
		    	ObjectMapper mapper = new ObjectMapper();
		    	return mapper.createObjectNode();   
	    	}
	    } catch (Exception errMess) { 
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
	    	ObjectMapper mapper = new ObjectMapper();
	    	return mapper.createObjectNode();   
	    } 
	}  
	
	public List<String> objectStringToStringList(String inputValueList) {  
		List<String> results = new ArrayList<String>();
		try {      
			inputValueList = inputValueList.replace("{", "["); 
			inputValueList = inputValueList.replace("}", "]");  
			ObjectMapper mapper = new ObjectMapper();    
			results.addAll(Arrays.asList(mapper.readValue(inputValueList, String[].class)));
		} catch (IOException errMess) {   
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
		} 
		return results;   
	}
	
	public String stringListToCommaAndQuoteString(List<String> inputValue) {
		if (inputValue.size() > 0) 
			return inputValue.stream().collect(Collectors.joining("','", "'", "'")); 
		return "";  
 	}  
	
	public CompanyField stringToCompanyField(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, CompanyField.class);  
		} catch (IOException errMess) {  
		} 
		return new CompanyField();    
	} 
	
	public CompanyPreferenceField stringToCompanyPreferenceField(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, CompanyPreferenceField.class);  
		} catch (IOException errMess) {  
		} 
		return new CompanyPreferenceField();    
	} 
	
	public DivisionField stringToDivisionField(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DivisionField.class);  
		} catch (IOException errMess) {  
		} 
		return new DivisionField();    
	} 
	
	public DivisionPreferenceField stringToDivisionPreferenceField(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DivisionPreferenceField.class);  
		} catch (IOException errMess) {  
		} 
		return new DivisionPreferenceField();     
	}  
	
	public GroupField stringToGroupField(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, GroupField.class);  
		} catch (IOException errMess) {  
		} 
		return new GroupField();    
	} 
	
	public GroupPreferenceField stringToGroupPreferenceField(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, GroupPreferenceField.class);  
		} catch (IOException errMess) {  
		} 
		return new GroupPreferenceField();     
	} 
	
	public String modulePreferenceFieldToString(ModulePreferenceField modulePreferenceField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(modulePreferenceField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}
	
	public ModulePrefAppBootSetting stringToModulePrefAppBootSetting(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();   
			return mapper.readValue(inputValue, ModulePrefAppBootSetting.class);   
		} catch (IOException errMess) {   
		}
		return new ModulePrefAppBootSetting();    
	} 
	
	public ModuleField stringToModuleField(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, ModuleField.class);  
		} catch (IOException errMess) {  
		} 
		return new ModuleField();    
	} 
	
	public String modulePrefAppBootSettingToString(ModulePrefAppBootSetting modulePrefAppBootSetting) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper(); 
			result = mapper.writeValueAsString(modulePrefAppBootSetting);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}

	public ModulePreferenceField stringToModulePreferenceField(String inputValue) {   
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, ModulePreferenceField.class);  
		} catch (IOException errMess) {  
		} 
		return new ModulePreferenceField();    
	} 
	
	public UserField stringToUserField(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();   
			return mapper.readValue(inputValue, UserField.class);  
		} catch (IOException errMess) {  
		} 
		return new UserField();    
	} 

	public String userFieldToString(UserField userField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(userField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}

	public String sectionPrefAppBootSettingToString(SectionPrefVehicleInspection sectionPrefVehicleInspection) {
		String result = "";  
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(sectionPrefVehicleInspection);        
		} catch (IOException errMess) {   
		} 
		return result;   
	}
	
	public SectionPrefVehicleInspection stringToSectionPrefVehicleInspection(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();   
			return mapper.readValue(inputValue, SectionPrefVehicleInspection.class);   
		} catch (IOException errMess) {    
		}
		return new SectionPrefVehicleInspection();     
	} 
	 
	public UserPreferenceField stringToUserPreferenceField(String inputValue) {   
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, UserPreferenceField.class);  
		} catch (IOException errMess) {  
		} 
		return new UserPreferenceField();    
	}
	
	public UserPrefAppBootSetting stringToUserPrefAppBootSetting(String inputValue) {   
		try {     
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, UserPrefAppBootSetting.class);   
		} catch (IOException errMess) {    
		}
		return new UserPrefAppBootSetting();     
	} 

	public String userPreferenceFieldToString(UserPreferenceField userPreferenceField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(userPreferenceField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}
	
	public String userPrefAppBootSettingToString(UserPrefAppBootSetting userPrefAppBootSetting) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(userPrefAppBootSetting);        
		} catch (IOException errMess) {   
		} 
		return result;   
	}

	public UserSessionField stringToUserSessionField(String inputValue) {   
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, UserSessionField.class);  
		} catch (IOException errMess) {  
		} 
		return new UserSessionField();   
	}
	 
	public UserFeedbackField stringToUserFeedbackField(String inputValue) {   
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, UserFeedbackField.class);  
		} catch (IOException errMess) {  
		} 
		return new UserFeedbackField();    
	} 

	public String userFeedbackFieldToString(UserFeedbackField userFeedbackField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(userFeedbackField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}

	public UserVerificationField stringToUserVerificationField(String inputValue) {   
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, UserVerificationField.class);  
		} catch (IOException errMess) {  
		} 
		return new UserVerificationField();    
	}
	 
	public String userVerificationFieldToString(UserVerificationField userVerificationField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(userVerificationField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}
	 
	public PropertyField stringToPropertyField(String inputValue) {    
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, PropertyField.class);  
		} catch (IOException errMess) {  
		} 
		return new PropertyField();    
	}
	 
	public String propertyFieldToString(PropertyField propertyField) { 
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(propertyField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}
	
	public SectionField stringToSectionField(String inputValue) {    
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, SectionField.class);  
		} catch (IOException errMess) {  
		} 
		return new SectionField();    
	}
	 
	public String sectionFieldToString(SectionField sectionField) { 
		String result = "";  
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(sectionField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}
	
	public PortionField stringToPortionField(String inputValue) {    
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, PortionField.class);  
		} catch (IOException errMess) {  
		} 
		return new PortionField();    
	}
	 
	public String portionFieldToString(PortionField portionField) { 
		String result = "";  
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(portionField);      
		} catch (IOException errMess) {    
		} 
		return result;   
	}

	public DeviceSafetyField stringToDeviceSafetyField(String inputValue) {    
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceSafetyField.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceSafetyField();    
	}
	 
	public String deviceSafetyFieldToString(DeviceSafetyField deviceSafetyField) { 
		String result = "";  
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(deviceSafetyField);      
		} catch (IOException errMess) {    
		} 
		return result;   
	}
	
	public DeviceField stringToDeviceField(String inputValue) {    
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceField.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceField();    
	}
	 
	public String deviceFieldToString(DeviceField deviceField) { 
		String result = "";  
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(deviceField);      
		} catch (IOException errMess) {    
		} 
		return result;   
	}
	 
	public MemberField stringToMemberField(String inputValue) {    
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, MemberField.class);  
		} catch (IOException errMess) {  
		} 
		return new MemberField();     
	}
	 
	public String memberFieldToString(MemberField memberField) { 
		String result = "";  
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(memberField);      
		} catch (IOException errMess) {    
		} 
		return result;   
	}
	
	public MemberDeviceField stringToMemberDeviceField(String inputValue) {    
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, MemberDeviceField.class);  
		} catch (IOException errMess) {  
		} 
		return new MemberDeviceField();      
	}
	 
	public String memberDeviceFieldToString(MemberDeviceField memberDeviceField) { 
		String result = "";  
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(memberDeviceField);      
		} catch (IOException errMess) {    
		} 
		return result;   
	}
	
	public DeviceDataAddressField stringToDeviceDataAddressField(String inputValue) {   
		try {       
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceDataAddressField.class);   
		} catch (IOException errMess) {  
		} 
		return new DeviceDataAddressField();     
	}
	 
	public DeviceDataField stringToDeviceDataField(String inputValue) {   
		try {       
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceDataField.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceDataField();    
	}

	public String deviceDataRunningFieldToString(DeviceDataRunningField DeviceDataRunningField) {
		String result = ""; 
		try {    
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(DeviceDataRunningField);      
		} catch (IOException errMess) {   
		} 
		return result;    
	} 

	public DeviceDataRunningField stringToDeviceDataRunningField(String inputValue) {   
		try {        
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceDataRunningField.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceDataRunningField();        
	}

	public String deviceDataRiskFieldToString(DeviceDataRiskField DeviceDataRiskField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(DeviceDataRiskField);      
		} catch (IOException errMess) {   
		} 
		return result;    
	} 
	
	public DeviceDataRiskField stringToDeviceDataRiskField(String inputValue) {   
		try {        
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceDataRiskField.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceDataRiskField();        
	}
	
	public String deviceDataTrackingFieldToString(DeviceDataTrackingField DeviceDataTrackingField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(DeviceDataTrackingField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	} 
	
	public DeviceDataTrackingField stringToDeviceDataTrackingField(String inputValue) {   
		try {        
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceDataTrackingField.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceDataTrackingField();     
	}
	
	public DeviceDataField1 stringToDeviceDataField1(String inputValue) {   
		try {       
			ObjectMapper mapper = new ObjectMapper();   
			return mapper.readValue(inputValue, DeviceDataField1.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceDataField1();    
	}
	
	public String deviceDataAddressFieldToString(DeviceDataAddressField DeviceDataAddressField) {
		String result = ""; 
		try {    
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(DeviceDataAddressField);       
		} catch (IOException errMess) {   
		} 
		return result;   
	} 
	
	public String deviceDataFieldToString(DeviceDataField DeviceDataField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(DeviceDataField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	} 
	
	public String deviceDataField1ToString(DeviceDataField1 DeviceDataField1) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(DeviceDataField1);      
		} catch (IOException errMess) {   
		} 
		return result;    
	}
	
	public DeviceDataLiveField stringToDeviceDataLiveField(String inputValue) {   
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceDataLiveField.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceDataLiveField();    
	}

	public String deviceDataLiveFieldToString(DeviceDataLiveField DeviceDataLiveField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(DeviceDataLiveField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	} 
	 
	public String userAuthenticationFieldToString(UserAuthenticationField userAuthenticationField) { 
		String result = "";  
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(userAuthenticationField);      
		} catch (IOException errMess) {    
		} 
		return result;   
	}
	
	public UserAuthenticationField stringToUserAuthenticationField(String inputValue) {    
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, UserAuthenticationField.class);  
		} catch (IOException errMess) {  
		} 
		return new UserAuthenticationField();    
	}
	 	
	public DeviceDataErrorField stringToDeviceDataErrorField(String inputValue) {   
		try {      
			ObjectMapper mapper = new ObjectMapper();  
			return mapper.readValue(inputValue, DeviceDataErrorField.class);  
		} catch (IOException errMess) {  
		} 
		return new DeviceDataErrorField();    
	}
	
	public String deviceDataErrorFieldToString(DeviceDataErrorField deviceDataErrorField) {
		String result = ""; 
		try {   
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(deviceDataErrorField);      
		} catch (IOException errMess) {   
		} 
		return result;   
	}
	
	public List<RoleUser> stringToRoleUserList(String inputValue) {    
		List<RoleUser> results = new ArrayList<RoleUser>();
		try {      
			ObjectMapper mapper = new ObjectMapper();   
			results = Arrays.asList(mapper.readValue(inputValue, RoleUser[].class));  
		} catch (IOException errMess) {   
		} 
		return results;    
	}
	
	public List<Property> stringToPropertyList(String inputValue) {    
		List<Property> results = new ArrayList<Property>();
		try {      
			ObjectMapper mapper = new ObjectMapper();   
			results = (List<Property>)Arrays.asList(mapper.readValue(inputValue, Property[].class));  
		} catch (IOException errMess) { 
		} 
		return results;    
	}
	
	public List<Section> stringToSectionList(String inputValue) {    
		List<Section> results = new ArrayList<Section>();
		try {      
			ObjectMapper mapper = new ObjectMapper();   
			results = (List<Section>)Arrays.asList(mapper.readValue(inputValue, Section[].class));  
		} catch (IOException errMess) {  
		} 
		return results;    
	}
	
	public List<Portion> stringToPortionList(String inputValue) {     
		List<Portion> results = new ArrayList<Portion>();
		try {      
			ObjectMapper mapper = new ObjectMapper();   
			results = (List<Portion>)Arrays.asList(mapper.readValue(inputValue, Portion[].class));  
		} catch (IOException errMess) {  
		} 
		return results;    
	} 
	
	public List<PredefinedField> stringToPredefinedFieldList(String inputValue) {      
		List<PredefinedField> results = new ArrayList<PredefinedField>(); 
		try {      
			ObjectMapper mapper = new ObjectMapper();    
			results = (List<PredefinedField>)Arrays.asList(mapper.readValue(inputValue, PredefinedField[].class));  
		} catch (IOException errMess) {  
		} 
		return results;      
	} 
	
	public List<UserPreference> stringToUserPreferenceList(String inputValue) {     
		List<UserPreference> results = new ArrayList<UserPreference>(); 
		try {      
			ObjectMapper mapper = new ObjectMapper();    
			results = (List<UserPreference>)Arrays.asList(mapper.readValue(inputValue, UserPreference[].class));  
		} catch (IOException errMess) {  
		} 
		return results;      
	} 
		
	public List<Device> stringToDeviceList(String inputValue) {     
		List<Device> results = new ArrayList<Device>();
		try {      
			ObjectMapper mapper = new ObjectMapper();     
			results = (List<Device>)Arrays.asList(mapper.readValue(inputValue, Device[].class));  
		} catch (IOException errMess) {  
		} 
		return results;    
	}
 
	public String convertJsonNodeToString(Map<String, Object> inputValue) {
    try {  
	    	ObjectMapper mapper = new ObjectMapper();              
	    	return mapper.writeValueAsString(inputValue); 
	    } catch (Exception errMess) {
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
	    }
	    return "{}"; 
	}
	
	public String convertJsonNodeToString(JsonNode jsonNode) {
	    try {
	    	if (jsonNode != null && jsonNode.size() > 0) {
	    		ObjectMapper mapper = new ObjectMapper();
	    		Object json = mapper.readValue(jsonNode.toString(), Object.class);
	    		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json); 
	    	}
	    } catch (Exception errMess) {
			APILog.writeTraceLog("Data Conversion IOException: " + errMess.getMessage());
	    }
	    return "{}";
	}
	
	public float resultSetToFloatValue(String value, boolean allowNegativeValue) {    
		float result = 0;
 		try {     
 			result = Float.valueOf(value); 
 			if (result <= 0 && allowNegativeValue == false) 
 				result = 0;   
		} catch (Exception errMess) {   
		}  
		return result;    
	}
	
	public String divisionPreferenceFieldToString(DivisionPreferenceField divisionPreferenceField) {
		String result = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(divisionPreferenceField); 
		} catch (IOException errMess) {
			
		}
		return result;
	}
	
	public String concatenateTwoString(String field1, String field2) {
		String result = ""; 
		if (field1.length() > 0)
			result = field1; 
		if (result.length() > 0 && field2.length() > 0)
			result = result + "_" + field2;
		else if (result.length() <= 0 && field2.length() > 0)
			result = field2;
		return result;
	}

}
