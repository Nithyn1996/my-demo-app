package com.common.api.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MutipartFile {

	RIDE_RAW_CSV("Ride Raw CSV", "csv", "device_raw.csv", "text/csv", "device-raw", "device_raw.csv"),

	RIDE_CSV("Ride CSV", "csv", "ride.csv", "application/csv", "ride", "ride.csv"),
	USER_PROFILE_PICTURE("User Profile Picture", "png", "user.png", "image/png", "user-profile-picture", "user.png"),

	RUDU_APP_ANDROID_LIBRARY_ZIP("Android Library File", "zip", "rudu_android_library.zip", "application/zip", "android-library-file", "road_android_library.zip"),
 	RUDU_APP_IOS_LIBRARY_ZIP("IOS Library File", "zip", "rudu_ios_library.zip", "application/zip", "ios-library-file", "road_ios_library.zip"),

 	RUDU_APP_MULTI_LANGUAGE_VOICE_ALERT_ZIP("Multi Language Voice Alert", "zip", "voice_alert.zip", "application/zip", "multi-language", "voice_alert.zip"),

	DEVICE_USER_PICTURE("Device User Picture", "png", "device_user.png", "image/png", "device-user-picture", "device_user.png");

	private String fileDescription	= "";
	private String fileExtension   	= "";
	private String fileName    		= "";
	private String fileMimeType		= "";
	private String filePathCloud	= "";
	private String fileNameCloud	= "";

	private MutipartFile(String fileDescription, String fileExtension, String fileName,
						 String fileMimeType, String filePathCloud, String fileNameCloud) {
		this.fileDescription = fileDescription;
		this.fileExtension   = fileExtension;
		this.fileName 		 = fileName;
		this.fileMimeType    = fileMimeType;
		this.filePathCloud 	 = filePathCloud;
		this.fileNameCloud 	 = fileNameCloud;
	}

	public String getFileDescription() {
		return fileDescription;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFileMimeType() {
		return fileMimeType;
	}
	public String getFilePathCloud() {
		return filePathCloud;
	}
	public String getFileNameCloud() {
		return fileNameCloud;
	}

	public static List<String> getEnumValueList() {
		try {
			return Stream.of(MutipartFile.values()).map(ev -> ev.name()).collect(Collectors.toList());
		} catch(Exception errMess) {
			return new ArrayList<>();
		}
    }
	public static MutipartFile getEnumValue(String value) {
		try {
			return MutipartFile.valueOf(value);
		} catch(Exception errMess) {
			return null;
		}
    }

}
