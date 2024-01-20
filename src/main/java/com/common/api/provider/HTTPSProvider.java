package com.common.api.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Service;

import com.common.api.response.RestClientResponseMapper;
import com.common.api.util.APILog;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HTTPSProvider {

	public HTTPSProvider() { }

	public RestClientResponseMapper HTTPSMethodGet(String apiPath, Map<String, String> headers, String requestPayload, int connectionTimeout, int readTimeout) {

		String serverLogString = "LOG_HTTPS_GET_REQ: ";
		int httpsStatus = httpsSSLCertificateEnable();
		RestClientResponseMapper results = new RestClientResponseMapper();

		if (httpsStatus == 1) {

			try {

				URL httpGetURL = new URL(apiPath + requestPayload);
				HttpURLConnection conn = (HttpURLConnection) httpGetURL.openConnection();
				conn.setRequestMethod("GET");

				for (Map.Entry<String, String> item : headers.entrySet()) {
				    String key = item.getKey();
				    String value = item.getValue();
					if (key.length() > 0 && value.length() > 0)
						conn.setRequestProperty(key, value);
				}

				/** Note: 1 second equal to 1000 */
				conn.setConnectTimeout(connectionTimeout * 1000);
				/** Note: 1 second equal to 1000 */
				conn.setReadTimeout(readTimeout * 1000);

				String responseData = "";
				int responseCode = conn.getResponseCode();

				if (responseCode == 200)
					responseData = convertInputStreamToString(conn.getInputStream());
				else
					responseData = convertInputStreamToString(conn.getErrorStream());

				try {
					ObjectMapper mapper = new ObjectMapper();
					results = mapper.readValue(responseData, RestClientResponseMapper.class);
				} catch (Exception errMess) {
				}

				results.setCode(responseCode);

			} catch (java.net.SocketTimeoutException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " socketTimeoutException: " + exError.getMessage());
				return results;
			} catch (MalformedURLException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " malformedURLException: " + exError.getMessage());
				return results;
			} catch (IOException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " ioException: " + exError.getMessage());
				return results;
			}
		}
		APILog.writeInfoLog(serverLogString + "requestPayload: "+ requestPayload + " responsePayload: " + results);
		return results;
	}

	public RestClientResponseMapper HTTPSMethodPost(String apiPath, Map<String, String> headers, String requestPayload, int connectionTimeout, int readTimeout) {

		String serverLogString = "LOG_HTTPS_POST_REQ: ";
		int httpsStatus = httpsSSLCertificateEnable();
		RestClientResponseMapper results = new RestClientResponseMapper();

		if (httpsStatus == 1) {

			try {

				URL httpPostURL = new URL(apiPath);
				HttpURLConnection conn = (HttpURLConnection) httpPostURL.openConnection();
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");

				for (Map.Entry<String, String> item : headers.entrySet()) {
				    String key = item.getKey();
				    String value = item.getValue();
					if (key.length() > 0 && value.length() > 0)
						conn.setRequestProperty(key, value);
				}

				/** Note: 1 second equal to 1000 */
				conn.setConnectTimeout(connectionTimeout * 1000);
				/** Note: 1 second equal to 1000 */
				conn.setReadTimeout(readTimeout * 1000);

				OutputStream os = conn.getOutputStream();
				os.write(requestPayload.getBytes());
				os.flush();

				String responseData = "";
				int responseCode = conn.getResponseCode();
				if (responseCode == 200)
					responseData = convertInputStreamToString(conn.getInputStream());
				else
					responseData = convertInputStreamToString(conn.getErrorStream());

				try {
					ObjectMapper mapper = new ObjectMapper();
					results = mapper.readValue(responseData, RestClientResponseMapper.class);
				} catch (Exception errMess) {
				}

				results.setCode(responseCode);

			} catch (java.net.SocketTimeoutException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " socketTimeoutException: " + exError.getMessage());
				return results;
			} catch (MalformedURLException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " malformedURLException: " + exError.getMessage());
				return results;
			} catch (IOException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " ioException: " + exError.getMessage());
				return results;
			}
		}
		APILog.writeInfoLog(serverLogString + "requestPayload: "+ requestPayload + " responsePayload: " + results);
		return results;
	}

	/**
	 * Use : This method is used to call for all HTTPS REST PUT method
	 */
	public RestClientResponseMapper HTTPSMethodPut(String apiPath, Map<String, String> headers, String requestPayload, int connectionTimeout, int readTimeout) {

		String serverLogString = "LOG_HTTPS_PUT_REQ: ";
		int httpsStatus = httpsSSLCertificateEnable();
		RestClientResponseMapper results = new RestClientResponseMapper();

		if (httpsStatus == 1) {

			try {

				URL httpPutURL = new URL(apiPath);
				HttpURLConnection conn = (HttpURLConnection) httpPutURL.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("PUT");

				for (Map.Entry<String, String> item : headers.entrySet()) {
				    String key = item.getKey();
				    String value = item.getValue();
					if (key.length() > 0 && value.length() > 0)
						conn.setRequestProperty(key, value);
				}

				/** Note: 1 second equal to 1000 */
				conn.setConnectTimeout(connectionTimeout * 1000);
				/** Note: 1 second equal to 1000 */
				conn.setReadTimeout(readTimeout * 1000);

				OutputStream os = conn.getOutputStream();
				os.write(requestPayload.getBytes());
				os.flush();

				String responseData = "";
				int responseCode = conn.getResponseCode();
				if (responseCode == 200)
					responseData = convertInputStreamToString(conn.getInputStream());
				else
					responseData = convertInputStreamToString(conn.getErrorStream());

				try {
					ObjectMapper mapper = new ObjectMapper();
					results = mapper.readValue(responseData, RestClientResponseMapper.class);
				} catch (Exception errMess) {
				}

				results.setCode(responseCode);

			} catch (java.net.SocketTimeoutException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " socketTimeoutException: " + exError.getMessage());
				return results;
			} catch (MalformedURLException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " malformedURLException: " + exError.getMessage());
				return results;
			} catch (IOException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ requestPayload + " ioException: " + exError.getMessage());
				return results;
			}
		}
		APILog.writeInfoLog(serverLogString + "requestPayload: "+ requestPayload + " responsePayload: " + results);
		return results;
	}

	/**
	 * Use : This method is used to call for all HTTPS REST DELETE method
	 */
	public RestClientResponseMapper HTTPSMethodDelete(String apiPath, Map<String, String> headers, String serverMethod, int connectionTimeout, int readTimeout) {

		String serverLogString = "LOG_HTTPS_DELETE_REQ: ";
		int httpsStatus = httpsSSLCertificateEnable();
		RestClientResponseMapper results = new RestClientResponseMapper();

		if (httpsStatus == 1) {

			try {

				URL httpDeleteURL = new URL(apiPath + serverMethod);
				HttpURLConnection conn = (HttpURLConnection) httpDeleteURL.openConnection();
				conn.setRequestMethod("DELETE");

				for (Map.Entry<String, String> item : headers.entrySet()) {
				    String key = item.getKey();
				    String value = item.getValue();
					if (key.length() > 0 && value.length() > 0)
						conn.setRequestProperty(key, value);
				}

				/** Note: 1 second equal to 1000 */
				conn.setConnectTimeout(connectionTimeout * 1000);
				/** Note: 1 second equal to 1000 */
				conn.setReadTimeout(readTimeout * 1000);

				String responseData = "";
				int responseCode = conn.getResponseCode();
				if (responseCode == 200)
					responseData = convertInputStreamToString(conn.getInputStream());
				else
					responseData = convertInputStreamToString(conn.getErrorStream());

				try {
					ObjectMapper mapper = new ObjectMapper();
					results = mapper.readValue(responseData, RestClientResponseMapper.class);
				} catch (Exception errMess) {
				}

				results.setCode(responseCode);

			} catch (java.net.SocketTimeoutException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ serverMethod + " socketTimeoutException: " + exError.getMessage());
				return results;
			} catch (MalformedURLException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ serverMethod + " malformedURLException: " + exError.getMessage());
				return results;
			} catch (IOException exError) {
				APILog.writeErrorLog(serverLogString + "requestPayload: "+ serverMethod + " ioException: " + exError.getMessage());
				return results;
			}
		}
		APILog.writeInfoLog(serverLogString + "requestPayload: "+ serverMethod + " responsePayload: " + results);
		return results;
	}

	/**
	 * Use : This method is used to read the input stream data
	 */
	private String convertInputStreamToString(InputStream inputStreamData) throws IOException {

		String line = "";
		String resultString = "";
		String serverLogString = "LOG_HTTPS_BUFFER_READER: ";

		try {
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStreamData));
			while ((line = bufferedReader.readLine()) != null) {
				resultString += line;
			}
			inputStreamData.close();
		} catch (IOException exError) {
			APILog.writeInfoLog(serverLogString + " inputStreamData: " + inputStreamData + " IOException: " + exError.getMessage());
		}
		return resultString;
	}

	/**
	 * Use : This method is used to enable the access for HTTPS certificate in the current system
	 */
	private int httpsSSLCertificateEnable() {

		int httpsRlts = 0;
		String serverLogString = "LOG_HTTPS_SSL_CERTIFICATE: ";

		try {
			/** Start of Fix */
		    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		        @Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
		        @Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) { }
		        @Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) { }

		    }};
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		    /** Create all-trusting host name verifier */
		    HostnameVerifier allHostsValid = new HostnameVerifier() {
		        @Override
				public boolean verify(String hostname, SSLSession session) { return true; }
		    };
		    /** Install the all-trusting host verifier */
		    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		    /** End of the fix */
		    httpsRlts = 1;

		} catch (NoSuchAlgorithmException exError) {
			APILog.writeInfoLog(serverLogString + " NoSuchAlgorithmException: " + exError.getMessage());
		} catch (KeyManagementException exError) {
			APILog.writeInfoLog(serverLogString + " KeyManagementException: " + exError.getMessage());
		}
		return httpsRlts;
	}

}
