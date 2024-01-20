package com.common.api.datasink;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.CompanyPreferenceService;
import com.common.api.datasink.service.CompanyService;
import com.common.api.datasink.service.ContinentService;
import com.common.api.datasink.service.CountryService;
import com.common.api.datasink.service.DashboardMapService;
import com.common.api.datasink.service.DashboardService;
import com.common.api.datasink.service.DeviceDataService;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.DeviceSummaryService;
import com.common.api.datasink.service.DivisionPreferenceService;
import com.common.api.datasink.service.DivisionService;
import com.common.api.datasink.service.GroupPreferenceService;
import com.common.api.datasink.service.GroupService;
import com.common.api.datasink.service.HardwareDeviceService;
import com.common.api.datasink.service.LanguageService;
import com.common.api.datasink.service.ManualSchedulerService;
import com.common.api.datasink.service.MetadataService;
import com.common.api.datasink.service.ModulePreferenceService;
import com.common.api.datasink.service.ModuleService;
import com.common.api.datasink.service.OrderService;
import com.common.api.datasink.service.PortionService;
import com.common.api.datasink.service.PropertyService;
import com.common.api.datasink.service.QueryMapService;
import com.common.api.datasink.service.QueryService;
import com.common.api.datasink.service.ReportMapService;
import com.common.api.datasink.service.ReportService;
import com.common.api.datasink.service.RoleCompanyService;
import com.common.api.datasink.service.RoleDivisionService;
import com.common.api.datasink.service.RoleGroupService;
import com.common.api.datasink.service.RoleModuleService;
import com.common.api.datasink.service.RoleUserService;
import com.common.api.datasink.service.SearchMapService;
import com.common.api.datasink.service.SearchService;
import com.common.api.datasink.service.SectionPreferenceService;
import com.common.api.datasink.service.SectionService;
import com.common.api.datasink.service.SubscriptionService;
import com.common.api.datasink.service.UserAuthenticationService;
import com.common.api.datasink.service.UserDeviceService;
import com.common.api.datasink.service.UserDeviceSessionService;
import com.common.api.datasink.service.UserDistinctService;
import com.common.api.datasink.service.UserFeedbackService;
import com.common.api.datasink.service.UserLastActivityService;
import com.common.api.datasink.service.UserPreferenceService;
import com.common.api.datasink.service.UserSecretService;
import com.common.api.datasink.service.UserService;
import com.common.api.datasink.service.UserSessionService;
import com.common.api.datasink.service.UserVerificationService;
import com.common.api.service.MSCompanyPreferenceService;
import com.common.api.service.MSCompanyService;
import com.common.api.service.MSContinentService;
import com.common.api.service.MSCountryService;
import com.common.api.service.MSDashboardMapService;
import com.common.api.service.MSDashboardService;
import com.common.api.service.MSDeviceDataService;
import com.common.api.service.MSDeviceService;
import com.common.api.service.MSDeviceSummaryService;
import com.common.api.service.MSDivisionPreferenceService;
import com.common.api.service.MSDivisionService;
import com.common.api.service.MSGroupPreferenceService;
import com.common.api.service.MSGroupService;
import com.common.api.service.MSHardwareDeviceService;
import com.common.api.service.MSLanguageService;
import com.common.api.service.MSManualSchedulerService;
import com.common.api.service.MSMetadataService;
import com.common.api.service.MSModulePreferenceService;
import com.common.api.service.MSModuleService;
import com.common.api.service.MSOrderService;
import com.common.api.service.MSPortionService;
import com.common.api.service.MSPropertyService;
import com.common.api.service.MSQueryMapService;
import com.common.api.service.MSQueryService;
import com.common.api.service.MSReportMapService;
import com.common.api.service.MSReportService;
import com.common.api.service.MSRoleCompanyService;
import com.common.api.service.MSRoleDivisionService;
import com.common.api.service.MSRoleGroupService;
import com.common.api.service.MSRoleModuleService;
import com.common.api.service.MSRoleUserService;
import com.common.api.service.MSSearchMapService;
import com.common.api.service.MSSearchService;
import com.common.api.service.MSSectionPreferenceService;
import com.common.api.service.MSSectionService;
import com.common.api.service.MSSubscriptionService;
import com.common.api.service.MSUserAuthenticationService;
import com.common.api.service.MSUserDeviceService;
import com.common.api.service.MSUserDeviceSessionService;
import com.common.api.service.MSUserDistinctService;
import com.common.api.service.MSUserFeedbackService;
import com.common.api.service.MSUserLastActivityService;
import com.common.api.service.MSUserPreferenceService;
import com.common.api.service.MSUserSecretService;
import com.common.api.service.MSUserService;
import com.common.api.service.MSUserSessionService;
import com.common.api.service.MSUserVerificationService;
import com.common.api.util.CustomException; 
 
@Configuration 
@PropertySource({ "classpath:application.properties" })
public class DataSinkFactory extends APIFixedConstant {    

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {  
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${app.db.engine.code}")  
	private String appDBEngineCode = "";    
        
	@Bean   
	@Primary
	public ContinentService getContinentPreferenceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSContinentService();   
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public CountryService getCountryPreferenceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSCountryService();  
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
    
	@Bean   
	@Primary
	public CompanyService getCompanyServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSCompanyService();   
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}

	@Bean   
	@Primary
	public CompanyPreferenceService getCompanyPreferenceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSCompanyPreferenceService();   
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);          
	}
	
	@Bean   
	@Primary  
	public DashboardMapService getDashboardMapServiceFactory() throws CustomException {       
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSDashboardMapService();           
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);          
	}
	
	@Bean    
	@Primary 
	public DashboardService getDashboardServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSDashboardService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary 
	public DeviceService getDeviceServiceFactory() throws CustomException {       
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSDeviceService();           
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);          
	}
	
	@Bean   
	@Primary 
	public DeviceDataService getDeviceDataServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSDeviceDataService();          
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Primary
	public DeviceSummaryService getDeviceSummaryServiceFactory() throws CustomException {
		if(appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))
			return new MSDeviceSummaryService();
		else
			throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);
	}

	@Bean   
	@Primary 
	public DivisionService getDivisionServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSDivisionService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
		
	@Bean   
	@Primary 
	public DivisionPreferenceService getDivisionPreferenceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSDivisionPreferenceService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean
	@Primary 
	public GroupService getGroupServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))
			return new MSGroupService();       
	    else 
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean    
	@Primary  
	public GroupPreferenceService getGroupPreferenceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSGroupPreferenceService();      
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary 
	public HardwareDeviceService getHardwareDeviceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSHardwareDeviceService();         
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean 
	@Primary
	public LanguageService getLanguageServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSLanguageService();    
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	 
	@Bean  
	@Primary
	public MetadataService getMetadataServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSMetadataService();   
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public ModuleService getModuleServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSModuleService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public ModulePreferenceService getModulePreferenceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSModulePreferenceService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}

	@Bean   
	@Primary 
	public OrderService getOrderServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSOrderService();        
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
 
	@Bean   
	@Primary 
	public PortionService getPortionServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSPortionService();       
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary 
	public PropertyService getPropertyServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSPropertyService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}

	@Bean   
	@Primary   
	public QueryMapService getQueryMapServiceFactory() throws CustomException {       
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSQueryMapService();            
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);          
	}

	@Bean   
	@Primary   
	public SearchMapService getSearchMapServiceFactory() throws CustomException {       
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSSearchMapService();               
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);          
	}
	
	@Bean   
	@Primary 
	public QueryService getQueryServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSQueryService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary  
	public ReportMapService getReportMapServiceFactory() throws CustomException {       
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSReportMapService();            
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);          
	}
	
	@Bean   
	@Primary   
	public ReportService getReportServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSReportService();       
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public RoleCompanyService getRoleCompanyServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSRoleCompanyService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public RoleDivisionService getRoleDivisionServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSRoleDivisionService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}

	@Bean   
	@Primary
	public RoleModuleService getRoleModuleServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSRoleModuleService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public RoleGroupService getRoleGroupServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSRoleGroupService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public RoleUserService getRoleUserServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSRoleUserService();     
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary 
	public SearchService getSearchServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSSearchService();        
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean
	@Primary
	public SectionService getSectionServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSSectionService();       
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary 
	public SectionPreferenceService getSectionPreferenceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSSectionPreferenceService();          
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}

	@Bean   
	@Primary 
	public SubscriptionService getSubscriptionServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSSubscriptionService();        
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
 
	@Bean   
	@Primary
	public UserAuthenticationService getUserAuthenticationServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))     
			return new MSUserAuthenticationService();         
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	 
	@Bean   
	@Primary
	public UserService getUserServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean
	@Primary
	public UserSecretService getUserSecretServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserSecretService();       
	    else     
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary 
	public UserDeviceService getUserDeviceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserDeviceService();       
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public UserDistinctService getUserDistinctServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserDistinctService();       
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary 
	public UserFeedbackService getUserFeedbackServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))    
			return new MSUserFeedbackService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary  
	public UserLastActivityService getUserLastActivityServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserLastActivityService();     
	    else      
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary 
	public UserPreferenceService getUserPreferenceServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserPreferenceService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public UserDeviceSessionService getUserDeviceSessionServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserDeviceSessionService();      
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
	@Bean   
	@Primary
	public UserSessionService getUserSessionServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserSessionService();     
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	 
	@Bean   
	@Primary
	public UserVerificationService getUserVerificationServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))   
			return new MSUserVerificationService();      
	    else    
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	} 
	
	@Bean
	@Primary 
	public ManualSchedulerService getManualSchedulerServiceFactory() throws CustomException {   
		if (appDBEngineCode.equalsIgnoreCase(GC_DB_ENGINE_MS_SQL))
			return new MSManualSchedulerService();       
	    else  
	    	throw new CustomException(EM_INVALID_DB_ENGINE_TYPE);         
	}
	
}
