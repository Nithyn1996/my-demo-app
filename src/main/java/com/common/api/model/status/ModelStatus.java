package com.common.api.model.status;

public class ModelStatus {

	public ModelStatus() {
	}

	public enum internalSystemStatus {
		REGISTERED, IN_PROGRESS, COMPLETED;
	}
	public enum  scoreValidationStatus {
		PENDING, SUCCESS, FAILED;
	}
	public enum deviceDataInsertStatus {
		ALLOW, NOT_ALLOW;
	}
	public enum EmailVerifiedStatus {
		YES, NO;
	}
	public enum UsernameVerifiedStatus {
		YES, NO;
	}
	public enum UserStatus {
		INITIATED, REGISTERED, ACTIVE;
	}
	public enum DeviceStatus {
		REGISTERED, IN_PROGRESS, COMPLETED;
	}

}
