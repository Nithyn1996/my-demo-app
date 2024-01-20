package com.common.api.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.UserService;
import com.common.api.datasink.service.UserSessionService;
import com.common.api.model.Portion;
import com.common.api.model.Property;
import com.common.api.model.Section;
import com.common.api.model.User;
import com.common.api.model.UserPreference;
import com.common.api.model.UserSession;
import com.common.api.model.type.ModelType.UserPreferenceType;
import com.common.api.model.util.PredefinedField;

@Service
@PropertySource({"classpath:application.properties"})           
public class APIAuthorization {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	     
	@Autowired
	APIDateTime apiDateTime;  

    @Autowired 
    UserService userService;     
    @Autowired 
    UserSessionService userSessionService;    
    @Autowired 
    ResultSetConversion resultSetConversion;      
     
	@Value("${app.static.session.id}")                      
	private String appStaticSessionId = "";   
  
	@Value("classpath:user_property.json") 
	Resource userPropertyJson = null;   
	@Value("classpath:user_section.json") 
	Resource userSectionJson = null; 
	@Value("classpath:user_portion.json")    
	Resource userPortionJson = null;   
	@Value("classpath:user_pref_app_boot_setting.json")   
	Resource userPrefAppBootSettingJson = null; 
	@Value("classpath:predefined_field.json")   
	Resource predefinedFieldJson = null;    
    
	public boolean verifyStaticSessionId(String sessionId) {    
		return (appStaticSessionId.equals(sessionId) == true) ? true : false;
	}
	
	public List<UserSession> verifySessionId(String sessionId) {    
		
		List<String> emptyList = new ArrayList<String>(); 
		List<UserSession> userSessionList = userSessionService.viewListByCriteria("", "", sessionId, "", emptyList, emptyList, "", "", 0, 0); 
		
		if (userSessionList.size() > 0) {
			Timestamp dbDataAndTime = APIDateTime.getGlobalDateTimeDBOperation();
			UserSession userSession = userSessionList.get(0);
			userSession.setModifiedAt(dbDataAndTime); 
			userSessionService.modifyUserSession(userSession); 
		}
		return userSessionList;
	}
	 
	public Property getPropertyStatus(User userDetail) {   
		
    	String queryData = "[]";
    	List<Property> propertyConfigList = new ArrayList<Property>();
    	
		try {
			queryData = FileCopyUtils.copyToString(new InputStreamReader(userPropertyJson.getInputStream())); 
			propertyConfigList = resultSetConversion.stringToPropertyList(queryData);
		} catch (IOException errMess) {
		}
		 
    	String companyId = userDetail.getCompanyId();  
    	   
		if (companyId.length() > 0) { 
			
			for (int urgi = 0; urgi < propertyConfigList.size(); urgi++) {
				
				Property propertyConfigTemp = propertyConfigList.get(urgi); 
				String companyIdConfig = propertyConfigTemp.getCompanyId();
				String statusConfig    = propertyConfigTemp.getStatus();    
				 
				if (statusConfig.equalsIgnoreCase(APIFixedConstant.GC_ACTION_YES.toString()) && companyId.equals(companyIdConfig)) {  
					return propertyConfigTemp;  
				}  
			}
		} 
		return new Property();
	}
	
	public Section getSectionStatus(User userDetail) {   
		
    	String queryData = "[]";
    	List<Section> sectionConfigList = new ArrayList<Section>(); 
    	
		try {
			queryData = FileCopyUtils.copyToString(new InputStreamReader(userSectionJson.getInputStream())); 
			sectionConfigList = resultSetConversion.stringToSectionList(queryData);
		} catch (IOException errMess) { 
		} 
		
    	String companyId = userDetail.getCompanyId(); 
    	   
		if (companyId.length() > 0) { 
			
			for (int urgi = 0; urgi < sectionConfigList.size(); urgi++) {
				
				Section sectionConfigTemp = sectionConfigList.get(urgi); 
				String companyIdConfig = sectionConfigTemp.getCompanyId();
				String statusConfig    = sectionConfigTemp.getStatus();    
				 
				if (statusConfig.equalsIgnoreCase(APIFixedConstant.GC_ACTION_YES.toString()) && companyId.equals(companyIdConfig)) {  
					return sectionConfigTemp;  
				}  
			}
		} 
		return new Section();
	}

	public Portion getPortionStatus(User userDetail) {
		
		String queryData = "[]";
    	List<Portion> portionConfigList = new ArrayList<Portion>(); 
    	 
		try {
			queryData = FileCopyUtils.copyToString(new InputStreamReader(userPortionJson.getInputStream())); 
			portionConfigList = resultSetConversion.stringToPortionList(queryData);
		} catch (IOException errMess) { 
		}
		
    	String companyId = userDetail.getCompanyId();  
    	    
		if (companyId.length() > 0) { 
			
			for (int urgi = 0; urgi < portionConfigList.size(); urgi++) {
				
				Portion portionConfigTemp = portionConfigList.get(urgi); 
				String companyIdConfig = portionConfigTemp.getCompanyId(); 
				String statusConfig    = portionConfigTemp.getStatus();    
				 
				if (statusConfig.equalsIgnoreCase(APIFixedConstant.GC_ACTION_YES.toString()) && companyId.equals(companyIdConfig)) {  
					return portionConfigTemp;    
				}  
			}
		} 
		return new Portion();
	}
	
	public UserPreference getUserPrefAppBootSettingStatus(User userDetail) {
		
		String queryData = "[]";  
    	List<UserPreference> uPreferenceConfigList = new ArrayList<UserPreference>(); 
    	
		try {
			queryData = FileCopyUtils.copyToString(new InputStreamReader(userPrefAppBootSettingJson.getInputStream())); 
			uPreferenceConfigList = resultSetConversion.stringToUserPreferenceList(queryData);
		} catch (IOException errMess) { 
		}
		
    	String companyId = userDetail.getCompanyId();
    	    
		if (companyId.length() > 0) { 
			
			for (int urgi = 0; urgi < uPreferenceConfigList.size(); urgi++) {
				
				UserPreference uPreferenceConfigTemp = uPreferenceConfigList.get(urgi); 
				String companyIdConfig = uPreferenceConfigTemp.getCompanyId(); 
				String typeConfig      = uPreferenceConfigTemp.getType();     
				String statusConfig    = uPreferenceConfigTemp.getStatus();      
				
				if (statusConfig.equalsIgnoreCase(APIFixedConstant.GC_ACTION_YES.toString()) && 
					typeConfig.equalsIgnoreCase(UserPreferenceType.USER_APP_BOOT_SETTING.toString()) && 
					companyId.equals(companyIdConfig)) {  
					return uPreferenceConfigTemp;    
				}  
			}
		} 
		return new UserPreference();
	}
	
	public List<String> predefinedFieldList(String type) { 
		try {
	    	String queryData = FileCopyUtils.copyToString(new InputStreamReader(predefinedFieldJson.getInputStream()));
	    	List<PredefinedField> predefinedFieldList = resultSetConversion.stringToPredefinedFieldList(queryData);
            for (int pdfi = 0; pdfi < predefinedFieldList.size(); pdfi++) {
            	PredefinedField predefinedField = predefinedFieldList.get(pdfi);  
            	if (type.equals(predefinedField.getType()))
            		return predefinedField.getValues(); 
            } 
        } catch (IOException e) { 
        }
		return new ArrayList<>();
    }
 
	
}
