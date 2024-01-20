package com.common.api.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.MutipartFile;
import com.common.api.datasink.service.CompanyService;
import com.common.api.datasink.service.DeviceDataService;
import com.common.api.datasink.service.DeviceService;
import com.common.api.datasink.service.UserService;
import com.common.api.provider.AzureStorageProvider;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileDistinctService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.service.util.ServiceCSVFile;
import com.common.api.service.util.ServiceFCMNotification;
import com.common.api.util.APIAuthorization;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.FileValidator;
import com.common.api.util.ResultSetConversion;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "General Multipart File", tags = {"General Multipart File"})
@RestController
@PropertySource({ "classpath:application.properties" })
public class MultipartFileResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/** Basic Module */
	@Autowired
	APIAuthorization apiAuthorization;
	@Autowired
	AzureStorageProvider azureStorageProvider;
	@Autowired
	FileValidator fileValidator;
    @Autowired
    ProfileDistinctService profileDistinctService;

	/** Service Module */
    @Autowired
    DeviceService deviceService;
    @Autowired
    DeviceDataService deviceDataService;
    @Autowired
    DeviceDataResource deviceDataResource;
	@Autowired
	UserService userService;
	@Autowired
	CompanyService organizationService;
	@Autowired
	ProfileSessionRoleService profileSessionRoleService;
	@Autowired
	ProfileDependencyService profileDependencyService;
	@Autowired
	ServiceCSVFile serviceCSVFile;
    @Autowired
    ResultSetConversion rsConversion;
    @Autowired
    ServiceFCMNotification serviceFCMNotification;

	@Value("${app.file.storage.provider}")
	private String appFileStorageProvider = "";

	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";
	@Value("${app.device.name.with.suffix.number}")
	String deviceNameWithSuffixNumber = "";


	public static String appFileStorageProviderAWS   = "AWS";
	public static String appFileStorageProviderAZURE = "AZURE";

	@ApiResponses(value = {
	        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = byte[].class, responseContainer = "List"),
	        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
	        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
	        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
	      })
	@RequestMapping(value = "/multipartFile", method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewMultipartFileDetail(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
    		@RequestParam(name = "type", defaultValue = "", required = true) String type) {

    	Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_MULTIPART_FILE_VIEW";
    	String apiInstLogString = "LOG_INST_MULTIPART_FILE_VIEW";

		byte[] results = {};
		HttpHeaders headers   = new HttpHeaders();
    	List<APIPreConditionErrorField> errorList = new ArrayList<>();
    	//List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);

    	String filePathToDownload = "";
    	String fileNameToDownload = "";
    	String fileNameToView     = "";
    	String fileMimeType		  = "";

		MutipartFile mutipartFileDetail  = MutipartFile.getEnumValue(type);
		if (mutipartFileDetail != null && mutipartFileDetail.getFileName().length() > 0) {
			filePathToDownload = mutipartFileDetail.getFilePathCloud();
			fileNameToDownload = mutipartFileDetail.getFileNameCloud();
			fileNameToView     = mutipartFileDetail.getFileName();
			fileMimeType       = mutipartFileDetail.getFileMimeType();
		} else {
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));
		}

    	if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));

		if (errorList.size() == 0) {

			/**String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS) || 1 > 0) { */

				if (fileNameToDownload.length() > 0) {
					if (appFileStorageProvider.equalsIgnoreCase(appFileStorageProviderAZURE)) {
						results = azureStorageProvider.downloadMultipartFile(filePathToDownload, "", fileNameToDownload);
					}
				}

				headers.setContentType(MediaType.parseMediaType(fileMimeType));
		 	    headers.add("content-disposition", "inline;filename=" + FilenameUtils.getName(fileNameToView));
		 	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
				APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
				return new ResponseEntity<>(results, headers, HttpStatus.OK);

			/**} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) {
				return ResponseEntity.status(SC_STATUS_SESSION).body(new APIRunTimeError(EC_002_SESSION_EXPIRED, roleStatucMess));
			} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIRunTimeError(EC_007_ACCESS_BLOCKED, roleStatucMess));
			}   **/

		}
		APILog.writeTraceLog(apiLogString + errorList);
		return new ResponseEntity<>(results, headers, HttpStatus.PRECONDITION_FAILED);
		//return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED.value()).body(new APIPreConditionError(EC_001_PRECONDITION_ERROR, errorList));
	}

}