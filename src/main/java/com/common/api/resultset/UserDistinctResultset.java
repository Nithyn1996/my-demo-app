package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserDistinct;
import com.common.api.util.ResultSetConversion;

public class UserDistinctResultset extends ResultSetConversion implements RowMapper<UserDistinct> {

    @Override
	public UserDistinct mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserDistinct userDistinct = new UserDistinct();

    	try {

    		userDistinct.setId(resultSetToString(rs, "id"));
    		userDistinct.setCompanyId(resultSetToString(rs, "company_id"));
    		userDistinct.setUserId(resultSetToString(rs, "user_id"));
    		userDistinct.setNextDeviceId(resultSetToLong(rs, "next_device_id"));
     		userDistinct.setType(resultSetToString(rs, "type"));
    		userDistinct.setStatus(resultSetToString(rs, "status"));
    		userDistinct.setActive(resultSetToString(rs, "active"));
    		userDistinct.setCreatedBy(resultSetToString(rs, "created_by"));
    		userDistinct.setModifiedBy(resultSetToString(rs, "modified_by"));
    		userDistinct.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		userDistinct.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return userDistinct;
    }

}