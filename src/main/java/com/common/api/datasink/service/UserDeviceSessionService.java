package com.common.api.datasink.service;

import java.sql.Timestamp;

import com.common.api.model.UserDeviceSession;

public interface UserDeviceSessionService {

	public UserDeviceSession createUserDeviceSession(UserDeviceSession userDeviceSession);

	public int removeUserDeviceSessionByModifiedAt(Timestamp modifiedAt);

}
