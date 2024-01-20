package com.common.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {

	private static final String ALGORITHM_MD5  = "MD5";
	private static final String ALGORITHM_AES  = "AES";
	// Mobile
	private static final int GCM_NONCE_LENGTH  = 12;
	private static final String TRANSFORMATION = "AES/GCM/NoPadding";
	// Web
	private static final String TRANSFORMATION_WEB = "AES/CBC/PKCS5Padding";

	public String getUserSecretKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public String base64Encode(String inputText) {
		return Base64.getEncoder().encodeToString(inputText.getBytes());
	}

	public String base64Decode(String inputText) {
		byte[] decodedBytes = Base64.getDecoder().decode(inputText);
		return new String(decodedBytes);
	}

	public String getMD5EncryptedValue(String inputText) {
	   String result = "";
	   final byte[] defaultBytes = inputText.getBytes();
	   try {
		   final MessageDigest md5MsgDigest = MessageDigest.getInstance(ALGORITHM_MD5);
		   md5MsgDigest.reset();
		   md5MsgDigest.update(defaultBytes);
		   final byte messageDigest[] = md5MsgDigest.digest();
		   final StringBuffer hexString = new StringBuffer();
		   for (final byte element : messageDigest) {
		       final String hex = Integer.toHexString(0xFF & element);
		       if (hex.length() == 1) {
		           hexString.append('0');
		       }
		       hexString.append(hex);
		   }
		   result = hexString + "";
	   } catch (final NoSuchAlgorithmException errMess) {
	   }
	   return result;
	}

	public String encryptDataForMobile(String userSecretKey, String inputText)  {
		String resultFinal = "";
		try {
		    SecretKey secretKey = new SecretKeySpec(userSecretKey.getBytes(), ALGORITHM_AES);
		    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		    //Generate a random nonce (IV) for GCM
		    byte[] nonce = new byte[GCM_NONCE_LENGTH];
		    //You should use a secure random number generator in a real-world scenario
		    new java.security.SecureRandom().nextBytes(nonce);
		    GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, nonce);
		    cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
		    byte[] encryptedData = cipher.doFinal(inputText.getBytes());
		    //Concatenate nonce and encrypted data for transmission
		    byte[] result = new byte[nonce.length + encryptedData.length];
		    System.arraycopy(nonce, 0, result, 0, nonce.length);
		    System.arraycopy(encryptedData, 0, result, nonce.length, encryptedData.length);
		    resultFinal = Base64.getEncoder().encodeToString(result);
		} catch (Exception errMess) {
		}
	    return resultFinal;
	}

	public String decryptDataForMobile(String userSecretKey, String inputText) {
		String resultFinal = "";
		try {
		     SecretKey secretKey = new SecretKeySpec(userSecretKey.getBytes(), ALGORITHM_AES);
		     Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		     byte[] encryptedBytes = Base64.getDecoder().decode(inputText);
		     //Split nonce and encrypted data
		     byte[] nonce = new byte[GCM_NONCE_LENGTH];
		     byte[] encryptedDataBytes = new byte[encryptedBytes.length - GCM_NONCE_LENGTH];
		     System.arraycopy(encryptedBytes, 0, nonce, 0, GCM_NONCE_LENGTH);
		     System.arraycopy(encryptedBytes, GCM_NONCE_LENGTH, encryptedDataBytes, 0, encryptedDataBytes.length);
		     GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, nonce);
		     cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
		     byte[] decryptedData = cipher.doFinal(encryptedDataBytes);
		     resultFinal = new String(decryptedData);
		} catch (Exception errMess) {
		}
	    return resultFinal;
	}

	public String encryptDataForWeb(String secretKey, String inputText) {
		String resultFinal = "";
		try {
			String iv = new String(secretKey).substring(0, 16);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION_WEB);
			byte[] dataBytes = inputText.getBytes();
			int plaintextLength = dataBytes.length;
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM_AES);
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			resultFinal = new String(Base64.getEncoder().encode(encrypted));
		} catch (Exception errMess) {
		}
	    return resultFinal;
	}

	public String decryptDataForWeb(String secretKey, String inputText) {
		String resultFinal = "";
		try {
			String iv = new String(secretKey).substring(0, 16);
			byte[] encrypted = Base64.getDecoder().decode(inputText);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION_WEB);
			SecretKeySpec keyspec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM_AES);
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted);
			resultFinal = new String(original);
		} catch (Exception errMess) {
		}
	    return resultFinal;
	 }

}
