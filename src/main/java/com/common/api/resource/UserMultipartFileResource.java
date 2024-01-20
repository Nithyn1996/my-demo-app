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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.APIResourceName;
import com.common.api.constant.APIRolePrivilegeType;
import com.common.api.constant.MutipartFile;
import com.common.api.datasink.service.CompanyService;
import com.common.api.datasink.service.UserService;
import com.common.api.model.User;
import com.common.api.provider.AzureStorageProvider;
import com.common.api.response.APIPreConditionError;
import com.common.api.response.APIPreConditionErrorField;
import com.common.api.response.APIRunTimeError;
import com.common.api.response.ProfileDependencyAccess;
import com.common.api.response.ProfileSessionRoleAccess;
import com.common.api.service.util.ProfileDependencyService;
import com.common.api.service.util.ProfileSessionRoleService;
import com.common.api.util.APIDateTime;
import com.common.api.util.APILog;
import com.common.api.util.FieldValidator;
import com.common.api.util.FileValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "User Multipart File", tags = {"User Multipart File"})
@RestController
@PropertySource({ "classpath:application.properties" })
public class UserMultipartFileResource extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/** Basic Module */
	@Autowired
	AzureStorageProvider azureStorageProvider;
	@Autowired
	FileValidator fileValidator;

	/** Service Module */
	@Autowired
	UserService userService;
	@Autowired
	CompanyService organizationService;
	@Autowired
	ProfileSessionRoleService profileSessionRoleService;
	@Autowired
	ProfileDependencyService profileDependencyService;

	@Value("${app.file.storage.provider}")
	private String appFileStorageProvider = "";

	@Value("${e006.failed.dependency}")
	String e006FailedDependency = "";

	public static String appFileStorageProviderAWS   = "AWS";
	public static String appFileStorageProviderAZURE = "AZURE";

	@ApiResponses(value = {
	        @ApiResponse(code = SC_STATUS_OK, message = SM_STATUS_OK, response = byte[].class, responseContainer = "List"),
	        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
	        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
	        @ApiResponse(code = SC_STATUS_SESSION, message = SM_STATUS_SESSION, response = APIRunTimeError.class),
	        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
	      })
	@RequestMapping(value = "/file", method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewFileDetail(HttpServletRequest httpRequest,
    		@RequestParam(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestParam(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(name = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(name = "divisionId", defaultValue = "", required = false) String divisionId,
			@RequestParam(name = "moduleId", defaultValue = "", required = false) String moduleId,
			@RequestParam(name = "userId", defaultValue = "", required = true) String userId,
    		@RequestParam(name = "type", defaultValue = "", required = true) String type) {

    	Timestamp instrumentStartTime = APIDateTime.getGlobalDateTimeLogOperation();
		String apiLogString     = "LOG_API_MULTIPART_FILE_VIEW";
    	String apiInstLogString = "LOG_INST_MULTIPART_FILE_VIEW";
    	String resourceType 	= APIResourceName.MULTIPART_FILE.toString();

		byte[] results = {};
		List<String> emptyList = new ArrayList<>();
		HttpHeaders headers   = new HttpHeaders();
     	List<APIPreConditionErrorField> errorList = FieldValidator.isValidHeaderFieldValue(referredBy, sessionId);

     	String filePathToDownload	= "";
    	String fileNameToDownload 	= "";
    	String fileNameToView     	= "";
    	String fileMimeType		  	= "";

		MutipartFile mutipartFileDetail  = MutipartFile.getEnumValue(type);
		if (mutipartFileDetail != null && mutipartFileDetail.getFileName().length() > 0) {
			filePathToDownload = mutipartFileDetail.getFilePathCloud();
			fileNameToDownload = mutipartFileDetail.getFileNameCloud();
			fileNameToView     = mutipartFileDetail.getFileName();
			fileMimeType       = mutipartFileDetail.getFileMimeType();
		} else {
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));
		}

		headers.setContentType(MediaType.parseMediaType(fileMimeType));
 	    headers.add("content-disposition", "inline;filename=" + FilenameUtils.getName(fileNameToView));
 	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

    	if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
    	if (!FieldValidator.isValidId(userId))
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_COMP_ID));

		if (errorList.size() == 0) {

			type = APIResourceName.USER.toString(); // Temporarily Purpose
			String rolePrivilegeType = type + APIRolePrivilegeType._READ_ALL.toString();
			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, type, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				List<User> userList = userService.viewListByCriteria(companyId, "", divisionId, moduleId, userId, "", "", "", emptyList, emptyList, "", "", 0, 0);

				if (userList.size() > 0) {
					User userDetail = userList.get(0);
					fileNameToDownload = userDetail.getProfilePicturePath();
				}

				if (appFileStorageProvider.equalsIgnoreCase(appFileStorageProviderAWS)) {
					if (fileNameToDownload.length() > 0) {
						//results = awss3Provider.downloadMultipartFile(fileNameToDownload);
					} else if (fileNameToDownload.length() <= 0 || results.length <= 0) {
						//fileNameToDownload = "user/profile/user.png";
						//results = awss3Provider.downloadMultipartFile(fileNameToDownload);
					}
				} else if (appFileStorageProvider.equalsIgnoreCase(appFileStorageProviderAZURE)) {
					if (fileNameToDownload.length() > 0) {
						results = azureStorageProvider.downloadMultipartFile(filePathToDownload, userId, fileNameToDownload);
					}
					if (fileNameToDownload.length() <= 0 || results.length <= 0) {
						fileNameToDownload = "user.png";
						results = azureStorageProvider.downloadMultipartFile(filePathToDownload, "", fileNameToDownload);
					}
				}

 				APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
				return new ResponseEntity<>(results, headers, HttpStatus.OK);
 			} else if (roleStatucCode.equals(GC_STATUS_SESSION_FAILED)) {
				return new ResponseEntity<>(results, headers, HttpStatus.valueOf(SC_STATUS_SESSION));
 			} else if (roleStatucCode.equals(GC_STATUS_ACCESS_FAILED)) {
 				return new ResponseEntity<>(results, headers, HttpStatus.FORBIDDEN);
 			}

		}
		APILog.writeTraceLog(apiLogString + errorList);
		return new ResponseEntity<>(results, headers, HttpStatus.PRECONDITION_FAILED);
 	}

    @ResponseStatus(code = HttpStatus.ACCEPTED)
	@ApiOperation(value = "Update File Details",
				  nickname = "UserFileUpdate",
				  notes = "Update File Details")
	@ApiResponses(value = {
        @ApiResponse(code = SC_STATUS_ACCEPTED, message = SM_STATUS_ACCEPTED, response = User.class),
        @ApiResponse(code = SC_STATUS_FORBIDDEN, message = SM_STATUS_FORBIDDEN, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_PRE_CONDITION, message = SM_STATUS_PRE_CONDITION, response = APIPreConditionError.class),
        @ApiResponse(code = SC_STATUS_FAILED_DEPENDENCY, message = SM_STATUS_FAILED_DEPENDENCY, response = APIRunTimeError.class),
        @ApiResponse(code = SC_STATUS_INTERNAL, message = SM_STATUS_INTERNAL, response = APIRunTimeError.class)
      })
	@RequestMapping(value = "/file", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateFile(HttpServletRequest httpRequest,
			@RequestHeader(value = "referredBy", defaultValue = "", required = true) String referredBy,
			@RequestHeader(value = "sessionId", defaultValue = "", required = true) String sessionId,
			@RequestParam(value = "companyId", defaultValue = "", required = true) String companyId,
			@RequestParam(value = "userId", defaultValue = "", required = true) String userId,
    		@RequestParam(value = "type", defaultValue = "", required = true) String type,
			@RequestParam(value = "file", required = true) MultipartFile fileReq) {

		Timestamp instrumentStartTime  = APIDateTime.getGlobalDateTimeLogOperation();

		String apiLogString     = "LOG_API_USER_UPDATE";
    	String apiInstLogString = "LOG_INST_USER_UPDATE";
    	String resourceType 	= APIResourceName.MULTIPART_FILE.toString();

    	List<APIPreConditionErrorField> errorList = fileValidator.isValidFile(fileReq);
		errorList.addAll(FieldValidator.isValidHeaderFieldValue(referredBy, sessionId));
		if (!FieldValidator.isValidId(companyId))
			errorList.add(new APIPreConditionErrorField("companyId", EM_INVALID_COMP_ID));
		if (!FieldValidator.isValidId(userId))
			errorList.add(new APIPreConditionErrorField("userId", EM_INVALID_USER_ID));

		String filePathToDownload	= "";
    	String fileName			 	= "";
    	boolean updatedStatus  = false;
		boolean uploadedResult = false;
		User userDetail = new User();

		MutipartFile mutipartFileDetail  = MutipartFile.getEnumValue(type);
		if (mutipartFileDetail != null && mutipartFileDetail.getFileName().length() > 0) {
			filePathToDownload = mutipartFileDetail.getFilePathCloud();
			fileName = mutipartFileDetail.getFileNameCloud();
		} else {
			errorList.add(new APIPreConditionErrorField("type", EM_INVALID_TYPE));
		}

		if (errorList.size() == 0) {

			String roleType = APIResourceName.USER.toString();
			String rolePrivilegeType = APIResourceName.USER.toString() + APIRolePrivilegeType._UPDATE.toString();
  			ProfileSessionRoleAccess roleStatus = profileSessionRoleService.verifyUserRolePermission(resourceType, roleType, rolePrivilegeType, sessionId, companyId, "", "");
			String roleStatucCode = roleStatus.getCode();
			String roleStatucMess = roleStatus.getMessage();

			if (roleStatucCode.equals(GC_STATUS_SUCCESS)) {

				ProfileDependencyAccess profileDependency = profileDependencyService.fileProfileDependencyStatus(GC_METHOD_POST, companyId, userId);
				String profileDependencyCode = profileDependency.getCode();
				String profileDependencyMess = profileDependency.getMessage();

				if (profileDependencyCode.equals(GC_STATUS_SUCCESS)) {

					if (type.equals(MutipartFile.USER_PROFILE_PICTURE.toString())) {
				    	fileName = userId + "." + fileValidator.getFileExtension(fileReq.getOriginalFilename());
						if (appFileStorageProvider.equalsIgnoreCase(appFileStorageProviderAWS)) {
							//uploadedResult = awss3Provider.uploadMultipartFile(fileName, fileReq);
						} else if (appFileStorageProvider.equalsIgnoreCase(appFileStorageProviderAZURE)) {
							uploadedResult = azureStorageProvider.uploadMultipartFile(filePathToDownload, userId, fileName, fileReq);
						}

						if (uploadedResult) {
							if (type.equals(MutipartFile.USER_PROFILE_PICTURE.toString())) {
								userDetail.setId(userId);
								userDetail.setCompanyId(companyId);
								userDetail.setProfilePicturePath(fileName);
								userService.updateUserProfilePicture(userDetail);
								updatedStatus = true;
							}
						}
					}

					if (updatedStatus) {
						APILog.writeInstrumentationTimeLog(apiInstLogString, instrumentStartTime, APIDateTime.getGlobalDateTimeLogOperation());
						return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(userDetail);
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

}