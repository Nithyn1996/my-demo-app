package com.common.api.util;
 
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.common.api.constant.APIFixedConstant;
import com.common.api.response.APIPreConditionErrorField;
 
@Service 
@PropertySource({ "classpath:application.properties"}) 
public class FileValidator extends APIFixedConstant {      
	 
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {  
		return new PropertySourcesPlaceholderConfigurer();
	}
	 
	@Value("${app.file.max.byte.size}")                      
	private int appFileMaxByteSize = 0; 
	@Value("${app.file.allow.type}")                      
	private String appAllowFileType = ""; 
	 
    public  boolean isValidFileExt(String fileExt) {  
    	try {
	    	List<String> allowedFileExt = Pattern.compile(GC_STRING_SEPERATOR_COMMA).splitAsStream(appAllowFileType).collect(Collectors.toList()); 
	    	if (allowedFileExt.contains(fileExt))  
				return true; 
    		return false;   
    	} catch (Exception errMess) {  
    		return false;
    	} 
	}
     
    public boolean isValidFileSize(long fileSize) {  
    	try {
			if (fileSize <= appFileMaxByteSize && fileSize > 0)  
				return true;   
    		return false;
    	} catch (Exception errMess) { 
    		return false;
    	} 
	}
    
    public String getFileExtension(String originalFileName) { 
    	try {
    		return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    	} catch (Exception errMess) { 
			return "";    
		}  
    } 
     
    public List<APIPreConditionErrorField> isValidFile(MultipartFile file) {
    	List<APIPreConditionErrorField> errorList = new ArrayList<APIPreConditionErrorField>();  
    	try { 
			boolean isValidFileExt  = isValidFileExt(getFileExtension(file.getOriginalFilename()));
			boolean isValidFileSize = isValidFileSize(file.getSize());
			if (isValidFileExt == false) 
				errorList.add(new APIPreConditionErrorField("fileType", EM_INVALID_FILE_TYPE));   
			if (isValidFileSize == false)  
				errorList.add(new APIPreConditionErrorField("fileSize", EM_INVALID_FILE_SIZE));  
    	} catch (Exception errMess) {  
			errorList.add(new APIPreConditionErrorField("fileSize", EM_INVALID_FILE_SIZE)); 
			errorList.add(new APIPreConditionErrorField("fileType", EM_INVALID_FILE_TYPE));  
    	} 
		return errorList; 
	} 
	  
}
