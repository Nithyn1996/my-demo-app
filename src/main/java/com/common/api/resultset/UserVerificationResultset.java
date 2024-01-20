package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserVerification;
import com.common.api.util.ResultSetConversion;

public class UserVerificationResultset extends ResultSetConversion implements RowMapper<UserVerification> {

    @Override
	public UserVerification mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserVerification userPreference = new UserVerification();
    	try {
    		userPreference.setId(resultSetToString(rs, "id"));
    		userPreference.setCompanyId(resultSetToString(rs, "company_id"));
    		userPreference.setUserId(resultSetToString(rs, "user_id"));
    		userPreference.setType(resultSetToString(rs, "type"));
    		userPreference.setUsername(resultSetToString(rs, "username"));
    		userPreference.setVerificationCode(resultSetToString(rs, "verification_code"));
    		userPreference.setStatus(resultSetToString(rs, "status"));
    		userPreference.setActive(resultSetToString(rs, "active"));
    		userPreference.setCreatedBy(resultSetToString(rs, "created_by"));
    		userPreference.setModifiedBy(resultSetToString(rs, "modified_by"));
    		userPreference.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		userPreference.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			userPreference.setUserVerificationField(stringToUserVerificationField(resultSetToString(rs, "user_verification_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return userPreference;
    }

}