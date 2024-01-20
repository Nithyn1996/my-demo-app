package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserSession;
import com.common.api.util.ResultSetConversion;

public class UserSessionResultset extends ResultSetConversion implements RowMapper<UserSession> {

    @Override
	public UserSession mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserSession userSession = new UserSession();
    	try {
    		userSession.setId(resultSetToString(rs, "id"));
    		userSession.setCompanyId(resultSetToString(rs, "company_id"));
    		userSession.setGroupId(resultSetToString(rs, "group_id"));
    		userSession.setDivisionId(resultSetToString(rs, "division_id"));
    		userSession.setModuleId(resultSetToString(rs, "module_id"));
    		userSession.setUserId(resultSetToString(rs, "user_id"));
    		userSession.setUsername(resultSetToString(rs, "username"));
    		userSession.setDeviceUniqueId(resultSetToString(rs, "device_unique_id"));
    		userSession.setDeviceToken(resultSetToString(rs, "device_token"));
    		userSession.setAppVersion(resultSetToFloat(rs, "app_version"));
    		userSession.setCategory(resultSetToString(rs, "category"));
    		userSession.setStatus(resultSetToString(rs, "status"));
    		userSession.setUserFirstName(resultSetToString(rs, "user_first_name"));
    		userSession.setType(resultSetToString(rs, "type"));
    		userSession.setActive(resultSetToString(rs, "active"));
    		userSession.setCreatedBy(resultSetToString(rs, "created_by"));
    		userSession.setModifiedBy(resultSetToString(rs, "modified_by"));
    		userSession.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		userSession.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		userSession.setUserSecreyKey(resultSetToString(rs, "userSecretKey"));

    		try {
    			userSession.setUserSessionField(stringToUserSessionField(resultSetToString(rs, "user_session_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return userSession;
    }

}