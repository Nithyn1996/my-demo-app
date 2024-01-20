package com.common.api.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.constant.APIRolePrivilegeType;
import com.common.api.constant.MutipartFile;
import com.common.api.datasink.service.DeviceDataService;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.DeviceSummaryService;
import com.common.api.datasink.service.QueryService;
import com.common.api.model.Device;
import com.common.api.model.DeviceData;
import com.common.api.model.User;
import com.common.api.model.field.DeviceDataRunningField;
import com.common.api.model.field.KeyValuesFloat;
import com.common.api.model.status.ModelStatus;
import com.common.api.model.type.CategoryType;
import com.common.api.model.type.InsertModeType;
import com.common.api.model.type.ModelType;
import com.common.api.provider.AzureStorageProvider;
import com.common.api.response.APIGeneralSuccess;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileAutoSystematicService;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileDistinctService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.service.util.ServiceAnalyticsGateway;
import com.common.api.service.util.ServiceCSVFile;
import com.common.api.service.util.ServiceFCMNotification;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.FileValidator;
import com.common.api.util.ResultSetConversion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Device Multipart File", tags = {"Device Multipart File"})
@RestController  
@PropertySource({ "classpath:application.properties" })
public class DeviceMultipartFileResource extends APIFixedConstant {        
 
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {  
		return new PropertySourcesPlaceholderConfigurer();
	}   
	
	/** Basic Module */   
	@Autowired
	AzureStorageProvider azureStorageProvider;   
	@Autowired
	FileValidator fileValidator; 
    @Autowired
    ProfileDistinctService profileDistinctService;  
	@Autowired 
	ProfileAutoSystematicService profileAutoSystamaticService;  
	@Autowired
	ResultSetConversion rsConversion;

	/** Service Module */
    @Autowired 
    QueryService queryService;   
    @Autowired
    DeviceService deviceService;   
    @Autowired 
    DeviceDataService deviceDataService;
    @Autowired
    DeviceDataResource deviceDataResource;  
    @Autowired
    DeviceSummaryService deviceSummaryService; 
	@Autowired     
	ProfileSessionRoleService profileSessionRoleService; 
	@Autowired 
	ProfileDependencyService profileDependencyService; 
	@Autowired
	ServiceCSVFile serviceCSVFile; 
    @Autowired
    ServiceFCMNotification serviceFCMNotification;
    @Autowired
    ServiceAnalyticsGateway serviceAnalyticsGateway;

   	@Value("${app.sp.device.summary.count.update}")       
   	String spDeviceSummaryCountUpdate = "";  
   	
	@Value("${app.file.storage.provider}")    
	private String appFileStorageProvider = "";  
	@Value("${e006.failed.dependency}")           
	private String e006FailedDependency = "";  
	@Value("${e015.record.already.available}")            
	private String e015RecordAlreadyAvailable = "";
	@Value("${app.device.name.with.suffix.number}")       
	private String deviceNameWithSuffixNumber = "";   

	public static String appFileStorageProviderAZURE = "AZURE";
	
	@ResponseStatus(code = HttpStatus.ACCEPTED) 
	@ApiOperation(value = "Upload Device Multipart File", 
				  nickname = "DeviceMultipartFileUpload",  
				  notes = "Upload Device Multipart File")   
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = User.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class), 
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class), 
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)   
      })
	@RequestMapping(value = "/device/image/user", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> uploadDeviceImage(HttpServletRequest httpRequest,    
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,  
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestParam(value  = "companyId", defaultValue = "", required = true) String companyId, 
			@RequestParam(name   = "userId", defaultValue = "", required = true) String userId, 
			@RequestParam(value  = "deviceId", defaultValue = "", required = true) String deviceId,    
    		@RequestParam(value  = "type", defaultValue = "", required = true) String type,   
			@RequestParam(value  = "file", required = true) MultipartFile fileReq) {                
    	 
		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();   
		
		String apiLogString     = "LOG_API_DEVICE_IMAGE_USER_ADD";          
    	String apiInstLogString = "LOG_INST_DEVICE_IMAGE_USER_ADD";     
    	String resourceType 	= APIResourceName.MULTIPART_FILE.toString();    
    	     
    	List<APIPreConditionErrorField> errorList = fileValidator.isValidFile(fileReq);  
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));  
		if (FieldValidator.isValidId(companyId) == false)          
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));    
		if (FieldValidator.isValidId(userId) == false)          
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));   
		if (FieldValidator.isValidId(deviceId) == false)          
			errorList.add(new APIPreConditionErrorField("deviceId", EM_INVALID_DEVICE_ID));  
		
		String filePathToDownload	= ""; 
    	String fileName			 	= "";
    	boolean updatedStatus  = false;    
		boolean uploadedResult = false; 
		APIGeneralSuccess succPayload = new APIGeneralSuccess();   
		
		MutipartFile mutipartFileDetail  = MutipartFile.getEnumValue(type);  
		if (mutipartFileDetail != null && mutipartFileDetail.getFileName().length() > 0) {
			filePathToDownload = mutipartFileDetail.getFilePathCloud();  
			fileName = mutipartFileDetail.getFileNameCloud();    
		} else {
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));  
		}
		  
		if (errorList.size() == 0) {           
			 
			String roleType = APIResourceName.DEVICE.toString();    
			String rolePrivilegeType = ModelType.DeviceType.SPEEDO_METER_DEVICE.toString() + APIRolePrivilegeType._CREATE.toString();

  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, roleType, rolePrivilegeType, sessionId, companyId, "", ""); 
			String roleStatucCode = roleStatus.getCode();			 
			String roleStatucMess = roleStatus.getMessage(); 
			 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {   

				ProfileDependencyAccess profileDependency = profileDependencyService.fileProfileDependencyStatus(GC_METHOD_POST, companyId, userId); 
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();
						
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {   
					
					if (type.equals(MutipartFile.DEVICE_USER_PICTURE.toString())) {   
				    	fileName = deviceId + "." + fileValidator.getFileExtension(fileReq.getOriginalFilename());  	   
						uploadedResult = azureStorageProvider.uploadMultipartFile(filePathToDownload, userId, fileName, fileReq);
					
						if (uploadedResult == true) {    
							int deviceUpdatedStatus = deviceService.updateDeviceMultipartFilePathById(companyId, userId, deviceId, fileName);
							if (deviceUpdatedStatus > 0) {
								succPayload.setCode(SC_001_REQUEST_SUCCESSFUL);
								succPayload.setMessage(fileName); 
								updatedStatus = true;   
							}
						} 
					}
					
					if (updatedStatus == true) {  
						
						try { 
							String fileExtension = fileValidator.getFileExtension(fileName);
							serviceAnalyticsGateway.getDeviceSafetyDetails("", userId, deviceId, fileExtension, ""); 
 						} catch (Exception errMess) { 
						}
						
						APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
 						return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
					}   			 
					return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
				} 
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileDependencyMess)); 
  			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));    
		}    
		APILog.writeTraceLog(apiLogString + errorList);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));    
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)  
	@ApiOperation(value = "Create Device Details", nickname = "DeviceDetailCreate", notes = "Create Device Details")
	@ApiResponses(value = {
			@ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = DeviceData.class, responseContainer = "List"),
			@ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
			@ApiResponse(code = SC_STATUS_CONFLICT, message = SM_STATUS_CONFLICT, response = APIRunTimeError.class),
			@ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),  
			@ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
			@ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) })
	@RequestMapping(value = "/file/device/android", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadDeviceCSVFile(@RequestParam("file") MultipartFile multipartFile,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(value = "companyId", defaultValue = "", required = true) String companyId,			
			@RequestParam(name = "groupId", defaultValue = "", required = true) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = true) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = true) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = true) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = true) String sectionId,
			@RequestParam(name = "portionId", defaultValue = "", required = true) String portionId,
			@RequestParam(name = "deviceRawFileName", defaultValue = "", required = true) String deviceRawFileName, 
			@RequestParam(name = "type", defaultValue = "", required = true) String type) {
 
		String apiLogString = "LOG_API_CSV_DEVICE_ADD";
		String apiInstLogString = "LOG_INST_CSV_DEVICE_ADD";
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();

		String resourceType = APIResourceName.MULTIPART_FILE.toString();
		List<APIPreConditionErrorField> errorList = fileValidator.isValidFile(multipartFile);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
 
		if (FieldValidator.isValidId(companyId) == false)
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID)); 
		if (FieldValidator.isValidId(groupId) == false)
			errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID)); 
		if (FieldValidator.isValidId(divisionId) == false)
			errorList.add(new APIPreConditionErrorField("divisionId", EM_INVALID_DIVI_ID)); 
		if (FieldValidator.isValidId(moduleId) == false)
			errorList.add(new APIPreConditionErrorField("moduleId", EM_INVALID_MODU_ID));
		if (FieldValidator.isValidId(userId) == false)
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
		if (FieldValidator.isValidId(propertyId) == false)
			errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_PROPERTY_ID));
		if (FieldValidator.isValidId(sectionId) == false)
			errorList.add(new APIPreConditionErrorField("sectionId", EM_INVALID_SECTION_ID));
		if (FieldValidator.isValidId(portionId) == false)
			errorList.add(new APIPreConditionErrorField("portionId", EM_INVALID_PORTION_ID)); 
		
		MutipartFile mutipartFileDetail = MutipartFile.getEnumValue(type);
		if (mutipartFileDetail != null && mutipartFileDetail.getFileName().length() <= 0)
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));
		System.out.println("errorList= "+errorList);
		if (errorList.size() == 0) {

			String roleType = APIResourceName.DEVICE.toString(); 
			String rolePrivilegeType = ModelType.DeviceType.SPEEDO_METER_DEVICE.toString() + APIRolePrivilegeType._CREATE.toString();
			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, roleType, rolePrivilegeType, sessionId, companyId, divisionId, moduleId);
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();
			String sessionUserId  = roleStatus.getUserId();  
			System.out.println("roleStatus= "+roleStatus);
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				Device deviceReq = new Device();
				deviceReq.setCompanyId(companyId);
				deviceReq.setDivisionId(divisionId);
				deviceReq.setModuleId(moduleId);
				deviceReq.setUserId(userId);
				deviceReq.setPropertyId(propertyId);
				deviceReq.setSectionId(sectionId);
				deviceReq.setPortionId(portionId);
				System.out.println("deviceReq "+deviceReq);
				DeviceData deviceDataReq = new DeviceData();
				deviceDataReq.setCompanyId(companyId);
				deviceDataReq.setDivisionId(divisionId);
				deviceDataReq.setModuleId(moduleId);
				deviceDataReq.setUserId(userId);
				deviceDataReq.setPropertyId(propertyId);
				deviceDataReq.setSectionId(sectionId);
				deviceDataReq.setPortionId(portionId); 
				System.out.println("deviceDataReq "+deviceDataReq);
				ProfileDependencyAccess profileDependency = profileDependencyService.deviceProfileDependencyStatus(GC_METHOD_POST, deviceReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();
				System.out.println("profileDependency "+profileDependency);
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) { 
					
					groupId = deviceReq.getGroupId();
					List<DeviceData> succPayload = new ArrayList<DeviceData>(); 
					List<Device> requestPayloadDevice 	= serviceCSVFile.convertCSVToDevice(multipartFile);  
					List<DeviceData> requestPayloadData = serviceCSVFile.convertCSVToDeviceData(multipartFile); 
					System.out.println("requestPayloadDevice "+requestPayloadDevice); 
					System.out.println("requestPayloadData "+requestPayloadData); 
					if (requestPayloadDevice.size() > 0 && requestPayloadData.size() > 0) {

						String deviceId = "";
						Device deviceDetail = null;  
						DeviceData deviceDataDetail = null; 
						Date startDateTime  = null, endDateTime = null;   
						  
						if (requestPayloadDevice.size() > 0) {
							
							Device device = requestPayloadDevice.get(0);  
							Date createdAtDevice = device.getCreatedAt(); 
							 
							List<String> typeList  = Arrays.asList(ModelType.DeviceType.SPEEDO_METER_DEVICE.toString());   
							List<Device> deviceList = deviceService.viewDeviceExistListByCreatedAt(companyId, userId, createdAtDevice, typeList, "", "", 0, 0); 
							System.out.println("typeList "+typeList);
							System.out.println("deviceList "+deviceList);
							if (deviceList.size() <= 0) {
										
								device.setDeviceRawFileName(deviceRawFileName);     
								device.setCompanyId(companyId);
								device.setGroupId(groupId); 
								device.setDivisionId(divisionId);
								device.setModuleId(moduleId);
								device.setUserId(userId);
								device.setPropertyId(propertyId);
								device.setSectionId(sectionId);
								device.setPortionId(portionId);   
								
								device.setInternalSystemStatus(ModelStatus.internalSystemStatus.IN_PROGRESS.toString());
								device.setDeviceDataInsertStatus(ModelStatus.deviceDataInsertStatus.ALLOW.toString());  
								device.setScoreValidationStatus(ModelStatus.scoreValidationStatus.PENDING.toString()); 

								device.setInsertMode(InsertModeType.RIDE_CSV.toString()); 
								device.setStatus(ModelStatus.DeviceStatus.IN_PROGRESS.toString());
								device.setType(ModelType.DeviceType.SPEEDO_METER_DEVICE.toString());
								
								device.setOrigin(referredBy);  
								device.setCreatedBy(sessionUserId);
								device.setModifiedBy(sessionUserId);  
								device.setModifiedAt(dbOperationStartTime);  
								device.setInsertedAt(dbOperationStartTime);   
								
								System.out.println("device "+device);
								
								if (deviceNameWithSuffixNumber.equalsIgnoreCase(GC_ACTION_YES)) {   
						    		long nextDeviceId = profileDistinctService.getNextDeviceId(device); 
									try {  
							    		device.setName(device.getName() + " " + nextDeviceId);   
									} catch (Exception errMess) { }
								}
								
								deviceDetail = deviceService.createDevice(device);
								System.out.println("deviceDetail "+ deviceDetail);
					  			if (deviceDetail != null && deviceDetail.getId() != null && deviceDetail.getId().length() > 0) 
					  				deviceId = deviceDetail.getId();

								if (deviceId.length() > 0) {

									deviceReq.setId(deviceId);
									System.out.println(deviceReq);
					  				profileAutoSystamaticService.createDeviceSummaryByDevice(deviceReq); 
					  				
									List<String> countFieldList = new ArrayList<String>();				  					 
									List<KeyValuesFloat> keyValuesFloat = new ArrayList<KeyValuesFloat>();
								
									int highRiskcount = 0,  startDataCount = 0, distanceDataCount = 0, manualDataCount = 0;
									int alertDataCount = 0, stressStrainDataCount = 0, endDataCount = 0;
 		 							
									float mScreenOnDuration  = 0, mUseCallDuration  = 0, overSpeedDuration  = 0;
									float mScreenOnKiloMeter = 0, mUseCallKiloMeter = 0, overSpeedKiloMeter = 0;
									System.out.println(keyValuesFloat);
									/** Bulk Insert Process - Starts */
									int dDataCount = requestPayloadData.size(); 
									for (int dDataIndex = 0; dDataIndex < dDataCount; dDataIndex++) {
	
										DeviceData deviceData = requestPayloadData.get(dDataIndex); 
										String categoryTemp = deviceData.getCategory();
										float  riskTemp	    = deviceData.getRisk(); 
										Date createdAt      = deviceData.getCreatedAt(); 
										String subCategory  = deviceData.getSubCategory(); 
										String subCateLevel = deviceData.getSubCategoryLevel();
										System.out.println("dDataCount "+dDataCount);
										DeviceDataRunningField ddRunningField = deviceData.getDeviceDataRunningField();  
										
										keyValuesFloat     = ddRunningField.getKeyValues(); 
										mScreenOnDuration  = ddRunningField.getMobileScreenOnDuration();  
										mUseCallDuration   = ddRunningField.getMobileUseCallDuration(); 
										overSpeedDuration  = ddRunningField.getOverSpeedDuration();
										mScreenOnKiloMeter = ddRunningField.getMobileScreenOnKiloMeter(); 
										mUseCallKiloMeter  = ddRunningField.getMobileUseCallKiloMeter();
										overSpeedKiloMeter = ddRunningField.getOverSpeedKiloMeter();
										System.out.println("ddRunningField "+ddRunningField);		
										if (dDataIndex == 0) 
											startDateTime = createdAt;  
											endDateTime = createdAt;  
			
											deviceData.setCompanyId(companyId);
											deviceData.setGroupId(groupId); 
											deviceData.setUserId(userId);
											deviceData.setDeviceId(deviceId);
											deviceData.setDivisionId(divisionId);
											deviceData.setModuleId(moduleId);
											deviceData.setSectionId(sectionId); 
											deviceData.setPortionId(portionId);
											deviceData.setPropertyId(propertyId);  
			
											deviceData.setInsertMode(InsertModeType.RIDE_CSV.toString());
											deviceData.setModifiedAt(dbOperationStartTime);
											deviceData.setCreatedBy(userId);
											deviceData.setModifiedBy(userId);  
											deviceData.setInsertedAt(dbOperationStartTime);   
											System.out.println("deviceData "+deviceData);
										countFieldList.add(rsConversion.concatenateTwoString(subCategory, subCateLevel));  
					  						 
		 								if (categoryTemp.equalsIgnoreCase(CategoryType.DeviceDataCategory.START_DATA.toString())) {
											startDataCount = startDataCount + 1;
										} else if (categoryTemp.equalsIgnoreCase(CategoryType.DeviceDataCategory.DISTANCE_DATA.toString())) {
											distanceDataCount = distanceDataCount + 1;
										} else if (categoryTemp.equalsIgnoreCase(CategoryType.DeviceDataCategory.ALERT_DATA.toString())) {
											alertDataCount = alertDataCount + 1;
											if (riskTemp > 90) 
												highRiskcount++; 
										} else if (categoryTemp.equalsIgnoreCase(CategoryType.DeviceDataCategory.STRESS_STRAIN_DATA.toString())) {
											stressStrainDataCount = stressStrainDataCount + 1; 
										} else if (categoryTemp.equalsIgnoreCase(CategoryType.DeviceDataCategory.MANUAL_DATA.toString())) {
											manualDataCount = manualDataCount + 1;								
										} else if (categoryTemp.equalsIgnoreCase(CategoryType.DeviceDataCategory.END_DATA.toString())) {
											endDataCount = endDataCount + 1;								
										} 
		 
										deviceDataDetail = deviceDataService.createDeviceData(deviceData);
										System.out.println("deviceDataDetail "+deviceDataDetail);
										if (deviceDataDetail != null && deviceDataDetail.getId() != null && deviceDataDetail.getId().length() > 0)  
		 									succPayload.add(deviceDataDetail);    
										
									}
									/**  Bulk Insert Process - Ends */
									
									/** Last Record means updating all the values into device profile - Starts */ 
									try {
										if (deviceDataDetail != null && deviceDataDetail.getId() != null && deviceDataDetail.getId().length() > 0) { 
											
											deviceDetail.setStartDataCount(startDataCount);
											deviceDetail.setDistanceDataCount(distanceDataCount);  
											deviceDetail.setAlertDataCount(alertDataCount); 
											deviceDetail.setStressStrainDataCount(stressStrainDataCount); 
											deviceDetail.setManualDataCount(manualDataCount);  
											deviceDetail.setEndDataCount(endDataCount);
											deviceDetail.setMobileScreenOnDuration(mScreenOnDuration); 
											deviceDetail.setMobileUseCallDuration(mUseCallDuration); 
											deviceDetail.setOverSpeedDuration(overSpeedDuration); 
											deviceDetail.setMobileScreenOnKiloMeter(mScreenOnKiloMeter); 
											deviceDetail.setMobileUseCallKiloMeter(mUseCallKiloMeter); 
											deviceDetail.setOverSpeedKiloMeter(overSpeedKiloMeter); 
											deviceDetail.setStartDateTime(startDateTime);  
											deviceDetail.setEndDateTime(endDateTime);
											
											Device deviceDetailUpdated = deviceDataResource.updateDeviceProfileByDeviceProfileCSVFile(deviceDetail, deviceDataDetail); 
											System.out.println("deviceDetailUpdated "+deviceDetailUpdated);				
											if (deviceDetailUpdated != null && deviceDetailUpdated.getId() != null) {
												deviceDetailUpdated.setDeviceRawFileName(deviceRawFileName); 
												deviceService.updateDeviceByCSV(deviceDetailUpdated);  
											} 
											try {
							  				 	deviceSummaryService.updateDeviceSummaryCountByDeviceId(userId, deviceId, countFieldList, keyValuesFloat, new DeviceDataRunningField()); 
							  				} catch (Exception errMess) { }   
							  				
											if (highRiskcount > 0)  
							  					serviceFCMNotification.sendUserRideRiskAlertLiveToAdmin(deviceDataReq);  
										} 
		 							} catch (Exception errMess) {
									} 
									/** Last Record means updating all the values into device profile - Ends */ 
									System.out.println("succPayload "+succPayload);
									if (succPayload != null && succPayload.size() > 0) {
										
										try {
						  					if (spDeviceSummaryCountUpdate.length() > 0) 
						  						queryService.updateByCriteria(spDeviceSummaryCountUpdate, companyId, "", "", "", userId, "", "", "", deviceId, "", GC_ACTION_UPDATE); 
						  				} catch (Exception errMess) { }
						  				
										APILog.writeInfoLog(apiLogString + " " + succPayload.toString());
										APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
										return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload);
	
									}
								}
								return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
							}
//							return ResponseEntity.status(HttpStatus.CREATED.value()).body(succPayload); // For Temporarily 
							return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(new APIRunTimeError(EC_015_RECORD_ALREADY_AVAILABLE, e015RecordAlreadyAvailable));
 						}
					}
					APILog.writeInfoLog(apiLogString + " deviceSize: " + requestPayloadDevice.size() + " deviceDataSize: " + requestPayloadData.size());
					return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, "Invalid File Data"));
				}
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileDependencyMess));
			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));
		}
		APILog.writeTraceLog(apiLogString);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)  
	@ApiOperation(value = "Create Device Raw Details", nickname = "DeviceRawDetailCreate", notes = "Create Device Raw Details")
	@ApiResponses(value = {
			@ApiResponse(code = SC_STATUS_CREATED, message = SM_STATUS_CREATED, response = DeviceData.class, responseContainer = "List"),
			@ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
			@ApiResponse(code = SC_STATUS_CONFLICT, message = SM_STATUS_CONFLICT, response = APIRunTimeError.class),
			@ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),  
			@ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
			@ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) })
	@RequestMapping(value = "/file/device/rawData", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadDeviceRawCSVFile(@RequestParam("file") MultipartFile multipartFile,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestParam(value = "companyId", defaultValue = "", required = true) String companyId,			
			@RequestParam(name = "groupId", defaultValue = "", required = true) String groupId,
			@RequestParam(name = "divisionId", defaultValue = "", required = true) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = true) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
			@RequestParam(name = "propertyId", defaultValue = "", required = true) String propertyId,
			@RequestParam(name = "sectionId", defaultValue = "", required = true) String sectionId,
			@RequestParam(name = "portionId", defaultValue = "", required = true) String portionId,
			@RequestParam(name = "deviceId", defaultValue = "", required = false) String deviceId,  
			@RequestParam(name = "deviceRawFileName", defaultValue = "", required = true) String deviceRawFileName,  
			@RequestParam(name = "type", defaultValue = "", required = true) String type) {	
 
		String apiLogString = "LOG_API_CSV_DEVICE_RAW_ADD";
		String apiInstLogString = "LOG_INST_CSV_DEVICE_RAW_ADD";
		Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();

		String resourceType = APIResourceName.MULTIPART_FILE.toString(); 
		List<APIPreConditionErrorField> errorList = fileValidator.isValidFile(multipartFile);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		
		if (deviceRawFileName.length() > 0) {
			List<Device> deviceList = deviceService.viewListByCriteria(companyId, userId, propertyId, sectionId, portionId, deviceId, deviceRawFileName, "", "", 0, 0); 
			if (deviceList.size() > 0) {  
				Device deviceDetail = deviceList.get(0);  
				deviceId = deviceDetail.getId();
			} else {
				deviceId = ""; 
			}
		}
		
		if (FieldValidator.isValidId(companyId) == false)
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID)); 
		if (FieldValidator.isValidId(groupId) == false)
			errorList.add(new APIPreConditionErrorField("groupId", EM_INVALID_GROUP_ID)); 
		if (FieldValidator.isValidId(divisionId) == false)
			errorList.add(new APIPreConditionErrorField("divisionId", EM_INVALID_DIVI_ID)); 
		if (FieldValidator.isValidId(moduleId) == false)
			errorList.add(new APIPreConditionErrorField("moduleId", EM_INVALID_MODU_ID));
		if (FieldValidator.isValidId(userId) == false)
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));
		if (FieldValidator.isValidId(propertyId) == false)
			errorList.add(new APIPreConditionErrorField("propertyId", EM_INVALID_PROPERTY_ID)); 
		if (FieldValidator.isValidId(sectionId) == false)
			errorList.add(new APIPreConditionErrorField("sectionId", EM_INVALID_SECTION_ID));
		if (FieldValidator.isValidId(portionId) == false)
			errorList.add(new APIPreConditionErrorField("portionId", EM_INVALID_PORTION_ID)); 
		if (FieldValidator.isValidId(deviceId) == false) 
			errorList.add(new APIPreConditionErrorField("deviceId", EM_INVALID_DEVICE_ID)); 
		if (!type.equals(MutipartFile.RIDE_RAW_CSV.toString()))  
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));
		
		String filePathCloud = "";  
		MutipartFile mutipartFileDetail = MutipartFile.getEnumValue(type);
		if (mutipartFileDetail != null && mutipartFileDetail.getFileName().length() <= 0)  
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE)); 
		if (mutipartFileDetail != null && mutipartFileDetail.getFileName().length() > 0) 
			filePathCloud = mutipartFileDetail.getFilePathCloud();    
		
		if (errorList.size() == 0) {

			String roleType = APIResourceName.DEVICE.toString(); 
			String rolePrivilegeType = ModelType.DeviceType.SPEEDO_METER_DEVICE.toString() + APIRolePrivilegeType._CREATE.toString();
			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, roleType, rolePrivilegeType, sessionId, companyId, divisionId, moduleId);
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage(); 

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				Device deviceReq = new Device();
				deviceReq.setId(deviceId);
				deviceReq.setCompanyId(companyId);
				deviceReq.setDivisionId(divisionId);
				deviceReq.setModuleId(moduleId);
				deviceReq.setUserId(userId);
				deviceReq.setPropertyId(propertyId);
				deviceReq.setSectionId(sectionId);
				deviceReq.setPortionId(portionId);
				 
				ProfileDependencyAccess profileDependency = profileDependencyService.deviceProfileDependencyStatus(GC_METHOD_PUT, deviceReq);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage(); 
				
				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {
					
					String	fileName = deviceId + "." + fileValidator.getFileExtension(multipartFile.getOriginalFilename());  	   
					boolean updatedStatus = azureStorageProvider.uploadMultipartFile(filePathCloud, userId, fileName, multipartFile);
					
					if (updatedStatus == true) { 
						
						String fileStatus = "AVAILABLE"; 
						deviceService.updateDeviceMultipartRawFilePathById(companyId, userId, deviceId, fileName, fileStatus); 
						
						APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
						return ResponseEntity.status(HttpStatus.CREATED.value()).body(deviceReq);  
					}    
					return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY.value()).body(new APIRunTimeError(EC_006_FAILED_DEPENDENCY, e006FailedDependency));
			 
				}
				return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new APIRunTimeError(EC_000_RUNTIME_DEPENDENCY, profileDependencyMess));
			}
			return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));
		}
		APILog.writeTraceLog(apiLogString);
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	}
	
	@ApiResponses(value = {
	        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = byte[].class, responseContainer = "List"),
	        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class), 
	        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class), 
	        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class) 
	      })        
	@RequestMapping(value = "/file/device/rawData", method = RequestMethod.GET)  
    public ResponseEntity<byte[]> downloadDeviceRawCSVFile(HttpServletRequest httpRequest,  
    		@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy, 
    		@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId, 
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId, 
			@RequestParam(name = "deviceId", defaultValue = "", required = true) String deviceId, 
    		@RequestParam(name = "type", defaultValue = "", required = true) String type) {                        
		    
    	Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation(); 
    	String resourceType 	= APIResourceName.MULTIPART_FILE.toString();            
    	   
    	String apiLogString 	= "LOG_API_CSV_DEVICE_RAW_VIEW";
		String apiInstLogString = "LOG_INST_CSV_DEVICE_RAW_VIEW";
		
		byte[] results = {};          
		List<String> emptyList = new ArrayList<String>(); 
		HttpHeaders headers   = new HttpHeaders();           
     	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId); 

     	String filePathToDownload	= ""; 
    	String fileNameToDownload 	= "";
    	String fileMimeType		  	= "";

		MutipartFile mutipartFileDetail  = MutipartFile.getEnumValue(type);  
		if (mutipartFileDetail != null && mutipartFileDetail.getFileName().length() > 0) {
			filePathToDownload = mutipartFileDetail.getFilePathCloud();    
			fileMimeType       = mutipartFileDetail.getFileMimeType();  
			fileNameToDownload = deviceId + ".csv";  
		} else { 
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));  
		}
		 
    	if (FieldValidator.isValidId(companyId) == false)        
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
    	if (FieldValidator.isValidId(userId) == false)         
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID)); 

		if (errorList.size() == 0) {         
			 
			String roleType = ModelType.DeviceType.SPEEDO_METER_DEVICE.toString(); 
			String rolePrivilegeType = roleType + APIRolePrivilegeType._READ_ALL.toString();  
			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, roleType, rolePrivilegeType, sessionId, companyId, divisionId, moduleId);
			String roleStatucCode = roleStatus.getCode();		
 
			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {    
				 
				List<Device> deviceList = deviceService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, "", "", "", deviceId, "", "", emptyList, emptyList, "", "", DV_OFFSET, DV_LIMIT);  
 
				if (deviceList.size() > 0) {  
					
					Device deviceDetail = deviceList.get(0); 
					fileNameToDownload = deviceDetail.getDeviceRawFilePath(); 
					 
					if (fileNameToDownload.length() > 0) { 
						results = azureStorageProvider.downloadMultipartFile(filePathToDownload, userId, fileNameToDownload);
					} else {
						fileNameToDownload = deviceId + ".csv"; 
					}

					headers.setContentType(MediaType.parseMediaType(fileMimeType));          
			 	    headers.add("content-disposition", "inline;filename=" + FilenameUtils.getName(fileNameToDownload));   
			 	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0"); 
			  
					APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
					return new ResponseEntity<byte[]>(results, headers, HttpStatus.OK);  
				}
				
				headers.setContentType(MediaType.parseMediaType(fileMimeType));          
		 	    headers.add("content-disposition", "inline;filename=" + FilenameUtils.getName(fileNameToDownload));   
		 	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");  
 				return new ResponseEntity<byte[]>(results, headers, HttpStatus.FORBIDDEN);   
				  
 			} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) { 
				headers.setContentType(MediaType.parseMediaType(fileMimeType));          
		 	    headers.add("content-disposition", "inline;filename=" + FilenameUtils.getName(fileNameToDownload));   
		 	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");  
				return new ResponseEntity<byte[]>(results, headers, HttpStatus.BAD_REQUEST);    
 			} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
				headers.setContentType(MediaType.parseMediaType(fileMimeType));          
		 	    headers.add("content-disposition", "inline;filename=" + FilenameUtils.getName(fileNameToDownload));   
		 	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");  
 				return new ResponseEntity<byte[]>(results, headers, HttpStatus.FORBIDDEN);    
 			}       
 
		}          
		APILog.writeInfoLog(apiLogString + errorList);      
		return new ResponseEntity<byte[]>(results, headers, HttpStatus.PRECONDITION_FAILED); 
 	}
	
}