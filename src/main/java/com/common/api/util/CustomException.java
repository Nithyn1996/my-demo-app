package com.common.api.util;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public CustomException(String errorMessage) {
		this.message = errorMessage;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
	    return message;
	}

}
