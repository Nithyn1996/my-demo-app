package com.common.api.provider;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.response.ClientAPIErrorPayload;
import com.common.api.response.RestClientResponseMapper;
import com.common.api.util.APILog;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class RetrofitClientProvider extends APIFixedConstant {

	public RetrofitClientProvider() { }

	public RestClientResponseMapper retrofitMethodGet(String baseUrl, String url, String username, String password, Map<String, String> headers, Map<String, String> params) {

		String serverLogString = "LOG_PROVIDER_RETROFIT_GET: ";
		RestClientResponseMapper results = new RestClientResponseMapper();

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
			    									.addInterceptor(new BasicAuthInterceptor(username, password))
											        .connectTimeout(60, TimeUnit.SECONDS)
											        .readTimeout(30, TimeUnit.SECONDS)
											        .writeTimeout(15, TimeUnit.SECONDS)
											        .build();
		Retrofit retrofit = new Retrofit.Builder()
										.client(okHttpClient)
									    .baseUrl(baseUrl)
									    .addConverterFactory(GsonConverterFactory.create())
									    .build();

		RetrofitClientService responseObject = retrofit.create(RetrofitClientService.class);
        Call<RestClientResponseMapper> responseList = responseObject.retrofitMethodGet(url, headers, params);

        try {

        	Response<RestClientResponseMapper> responseObj = responseList.execute();
        	int responseCode = responseObj.code();

        	if (responseCode == 200) {
        		results = responseObj.body();
         		results.setCode(responseCode);
         		results.setHelmetAloneCount(responseObj.body().getHelmetAloneCount());
         		results.setUserWithHelmetCount(responseObj.body().getUserWithHelmetCount());
         		results.setUserWithoutHelmetCount(responseObj.body().getUserWithoutHelmetCount());
         		results.setUserWithSeatbeltCount(responseObj.body().getUserWithSeatbeltCount());
         		results.setUserWithoutSeatbeltCount(responseObj.body().getUserWithoutSeatbeltCount());
         		results.setMessage(GC_HTTP_SUCCESS_MESSAGE);
        	} else {
        		Converter<ResponseBody, ClientAPIErrorPayload> errorConverter = retrofit.responseBodyConverter(ClientAPIErrorPayload.class, new Annotation[0]);
 	        	ClientAPIErrorPayload responseErrors = errorConverter.convert(responseObj.errorBody());
  	     		results.setCode(responseCode);
 	     		results.setErrors(responseErrors.getErrors());
 	     		results.setMessages(responseErrors.getMessages());
  	        	results.setMessage(GC_HTTP_FAILED_MESSAGE);
        	}

     		APILog.writeInfoLog(serverLogString + " baseUrl: " + baseUrl + " url: " + url + " headers: " + headers + " params: " + params + " results: " + results.toString());
		} catch (IOException errMess) {
	 		APILog.writeErrorLog(serverLogString + " baseUrl: " + baseUrl + " url: " + url + " headers: " + headers + " params: " + params + " errMess: " + errMess.getMessage());
		}
		return results;
	}

	public RestClientResponseMapper retrofitMethodPost(String baseUrl, String url, String username, String password, Map<String, String> headers, Map<String, String> body) {

		String serverLogString = "LOG_PROVIDER_RETROFIT_POST: ";
		RestClientResponseMapper results = new RestClientResponseMapper();

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
													.addInterceptor(new BasicAuthInterceptor(username, password))
											        .connectTimeout(60, TimeUnit.SECONDS)
											        .readTimeout(30, TimeUnit.SECONDS)
											        .writeTimeout(15, TimeUnit.SECONDS)
											        .build();
		Retrofit retrofit = new Retrofit.Builder()
										.client(okHttpClient)
									    .baseUrl(baseUrl)
									    .addConverterFactory(GsonConverterFactory.create())
									    .build();

		RetrofitClientService responseObject = retrofit.create(RetrofitClientService.class);
        Call<RestClientResponseMapper> responseList = responseObject.retrofitMethodPost(url, headers, body);

        try {

        	Response<RestClientResponseMapper> responseObj = responseList.execute();
        	int responseCode = responseObj.code();

        	if (responseCode == 201) {
        		results = responseObj.body();
         		results.setCode(responseCode);
         		results.setMessage(GC_HTTP_SUCCESS_MESSAGE);
        	} else {
        		Converter<ResponseBody, ClientAPIErrorPayload> errorConverter = retrofit.responseBodyConverter(ClientAPIErrorPayload.class, new Annotation[0]);
 	        	ClientAPIErrorPayload responseErrors = errorConverter.convert(responseObj.errorBody());
  	     		results.setCode(responseCode);
 	     		results.setErrors(responseErrors.getErrors());
  	        	results.setMessage(GC_HTTP_FAILED_MESSAGE);
        	}
     		APILog.writeInfoLog(serverLogString + " baseUrl: " + baseUrl + " url: " + url + " headers: " + headers + " body: " + body + " results: " + results.toString());
		} catch (IOException errMess) {
	 		APILog.writeErrorLog(serverLogString + " baseUrl: " + baseUrl + " url: " + url + " headers: " + headers + " body: " + body + " errMess: " + errMess.getMessage());
		}
		return results;
	}

	public RestClientResponseMapper retrofitMethodPut(String baseUrl, String url, String username, String password, Map<String, String> headers, Map<String, String> body) {

		String serverLogString = "LOG_PROVIDER_RETROFIT_PUT: ";
		RestClientResponseMapper results = new RestClientResponseMapper();

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
													.addInterceptor(new BasicAuthInterceptor(username, password))
											        .connectTimeout(60, TimeUnit.SECONDS)
											        .readTimeout(30, TimeUnit.SECONDS)
											        .writeTimeout(15, TimeUnit.SECONDS)
											        .build();
		Retrofit retrofit = new Retrofit.Builder()
										.client(okHttpClient)
									    .baseUrl(baseUrl)
									    .addConverterFactory(GsonConverterFactory.create())
									    .build();

		RetrofitClientService responseObject = retrofit.create(RetrofitClientService.class);
        Call<RestClientResponseMapper> responseList = responseObject.retrofitMethodPut(url, headers, body);

        try {

        	Response<RestClientResponseMapper> responseObj = responseList.execute();
        	int responseCode = responseObj.code();

        	if (responseCode == 200) {
        		results = responseObj.body();
         		results.setCode(responseCode);
         		results.setMessage(GC_HTTP_SUCCESS_MESSAGE);
        	} else {
        		Converter<ResponseBody, ClientAPIErrorPayload> errorConverter = retrofit.responseBodyConverter(ClientAPIErrorPayload.class, new Annotation[0]);
 	        	ClientAPIErrorPayload responseErrors = errorConverter.convert(responseObj.errorBody());
  	     		results.setCode(responseCode);
 	     		results.setErrors(responseErrors.getErrors());
  	        	results.setMessage(GC_HTTP_FAILED_MESSAGE);
        	}
     		APILog.writeInfoLog(serverLogString + " baseUrl: " + baseUrl + " url: " + url + " headers: " + headers + " body: " + body + " results: " + results.toString());
		} catch (IOException errMess) {
	 		APILog.writeErrorLog(serverLogString + " baseUrl: " + baseUrl + " url: " + url + " headers: " + headers + " body: " + body + " errMess: " + errMess.getMessage());
		}
		return results;
	}

	public RestClientResponseMapper retrofitMethodDelete(String baseUrl, String url, String username, String password, Map<String, String> headers, Map<String, String> params) {

		String serverLogString = "LOG_PROVIDER_RETROFIT_DELETE: ";
		RestClientResponseMapper results = new RestClientResponseMapper();

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
													.addInterceptor(new BasicAuthInterceptor(username, password))
											        .connectTimeout(60, TimeUnit.SECONDS)
											        .readTimeout(30, TimeUnit.SECONDS)
											        .writeTimeout(15, TimeUnit.SECONDS)
											        .build();
		Retrofit retrofit = new Retrofit.Builder()
										.client(okHttpClient)
									    .baseUrl(baseUrl)
									    .addConverterFactory(GsonConverterFactory.create())
									    .build();

		RetrofitClientService responseObject = retrofit.create(RetrofitClientService.class);
        Call<RestClientResponseMapper> responseList = responseObject.retrofitMethodDelete(url, headers, params);

        try {

        	Response<RestClientResponseMapper> responseObj = responseList.execute();
        	int responseCode = responseObj.code();

        	if (responseCode == 202) {
        		results = responseObj.body();
         		results.setCode(responseCode);
         		results.setMessage(GC_HTTP_SUCCESS_MESSAGE);
        	} else {
        		Converter<ResponseBody, ClientAPIErrorPayload> errorConverter = retrofit.responseBodyConverter(ClientAPIErrorPayload.class, new Annotation[0]);
 	        	ClientAPIErrorPayload responseErrors = errorConverter.convert(responseObj.errorBody());
  	     		results.setCode(responseCode);
 	     		results.setErrors(responseErrors.getErrors());
  	        	results.setMessage(GC_HTTP_FAILED_MESSAGE);
        	}
     		APILog.writeInfoLog(serverLogString + " baseUrl: " + baseUrl + " url: " + url + " headers: " + headers + " params: " + params + " results: " + results.toString());
		} catch (IOException errMess) {
	 		APILog.writeErrorLog(serverLogString + " baseUrl: " + baseUrl + " url: " + url + " headers: " + headers + " params: " + params + " errMess: " + errMess.getMessage());
		}
		return results;
	}

}
