package com.common.api.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.CountryService;
import com.common.api.model.Country;
import com.common.api.model.User;
import com.common.api.provider.RetrofitClientProvider;
import com.common.api.response.RestClientResponseMapper;
import com.common.api.util.Util;

@Service
@PropertySource({ "classpath:application.properties" })
public class ServiceSMSGateway extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	/** Properties Constants */
	@Value("${app.smsgatewayhub.root.url}")
	private String appSmsGatewayhubRootUrl = "";
	@Value("${app.smsgatewayhub.api.key}")
	private String appSmsGatewayhubApiKey = "";
	@Value("${app.smsgatewayhub.otp.sender.id}")
	private String appSmsGatewayhubOTPSenderId = "";
	@Value("${app.smsgatewayhub.otp.channel}")
	private String appSmsGatewayhubOTPChannel = "2";
	@Value("${app.smsgatewayhub.otp.dcs}")
	private String appSmsGatewayhubOTPDcs = "0";
	@Value("${app.smsgatewayhub.otp.flashsms}")
	private String appSmsGatewayhubOTPFlashsms = "0";
	@Value("${app.smsgatewayhub.otp.content}")
	private String appSmsGatewayhubOTPContent = "";

	@Autowired
	RetrofitClientProvider retrofitClientProvider;
    @Autowired
    CountryService countryService;

	public String sendVerificationCodeToUserBySMS (User user, String verificationCode) {

		String result = GC_STATUS_ERROR;
		String method = SP_GET_METHOD_SEND_SMS;

		try {

			List<String> emptyList = new ArrayList<>();

			String companyId  	= user.getCompanyId();
			String username  	= user.getUsername();
			String firstName 	= user.getFirstName();
			String countryId 	= user.getCountryId();

			List<Country> countryList = countryService.viewListByCriteria(companyId, "", countryId, "", "", "", "", emptyList, emptyList, "", "", 0, 0);

			if (countryList.size() > 0) {

				Country countryDetail = countryList.get(0);
				String countryCode 	  = countryDetail.getDialingCode();
				countryCode           = countryCode.replace("+", "");
				countryCode           = countryCode.replace("-", "");
				String mobileNumber   = countryCode + username;

				Map<String, String> replaceList = new HashMap<>();
				replaceList.put(":FIRST_NAME", firstName);
				replaceList.put(":VERIFICATION_CODE", verificationCode);
				String text = Util.stringReplaceByKeyList(appSmsGatewayhubOTPContent, replaceList);

				Map<String, String> headers = new HashMap<>();
				Map<String, String> params  = new HashMap<>();
				params.put("APIKey", 	appSmsGatewayhubApiKey);
				params.put("senderid", 	appSmsGatewayhubOTPSenderId);
				params.put("channel", 	appSmsGatewayhubOTPChannel);
				params.put("DCS", 		appSmsGatewayhubOTPDcs);
				params.put("flashsms", 	appSmsGatewayhubOTPFlashsms);
				params.put("number", 	mobileNumber);
				params.put("text", 		text);

				RestClientResponseMapper resultObject = retrofitClientProvider.retrofitMethodGet(appSmsGatewayhubRootUrl, method, "", "", headers, params);
				int code = resultObject.getCode();

				if (code == 200) {
					result = GC_STATUS_SUCCESS;
				}
			}

		} catch (Exception errMess) {
		}
		return result;
	}

}