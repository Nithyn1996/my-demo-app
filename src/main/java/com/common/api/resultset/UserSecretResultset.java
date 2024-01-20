package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserSecret;
import com.common.api.util.ResultSetConversion;

public class UserSecretResultset extends ResultSetConversion implements RowMapper<UserSecret> {

    @Override
	public UserSecret mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserSecret userSecret = new UserSecret();

    	try {

    		userSecret.setId(resultSetToString(rs, "id"));
    		userSecret.setCompanyId(resultSetToString(rs, "company_id"));
    		userSecret.setDivisionId(resultSetToString(rs, "division_id"));
    		userSecret.setUserId(resultSetToString(rs, "user_id"));

    		userSecret.setSecretKey(resultSetToString(rs, "secret_key"));

    		userSecret.setStatus(resultSetToString(rs, "status"));
    		userSecret.setActive(resultSetToString(rs, "active"));
    		userSecret.setType(resultSetToString(rs, "type"));

    		userSecret.setCreatedBy(resultSetToString(rs, "created_by"));
    		userSecret.setModifiedBy(resultSetToString(rs, "modified_by"));
    		userSecret.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		userSecret.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return userSecret;

    }

}