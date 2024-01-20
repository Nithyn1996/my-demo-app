package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserFeedback;
import com.common.api.util.ResultSetConversion;

public class UserFeedbackResultset extends ResultSetConversion implements RowMapper<UserFeedback> {

    @Override
	public UserFeedback mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserFeedback userFeedback = new UserFeedback();
    	try {
    		userFeedback.setId(resultSetToString(rs, "id"));
    		userFeedback.setCompanyId(resultSetToString(rs, "company_id"));
    		userFeedback.setDivisionId(resultSetToString(rs, "division_id"));
    		userFeedback.setModuleId(resultSetToString(rs, "module_id"));
    		userFeedback.setUserId(resultSetToString(rs, "user_id"));
    		userFeedback.setType(resultSetToString(rs, "type"));
    		userFeedback.setDescription(resultSetToString(rs, "description"));
    		userFeedback.setStatus(resultSetToString(rs, "status"));
    		userFeedback.setActive(resultSetToString(rs, "active"));
    		userFeedback.setCreatedBy(resultSetToString(rs, "created_by"));
    		userFeedback.setModifiedBy(resultSetToString(rs, "modified_by"));
    		userFeedback.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		userFeedback.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			userFeedback.setUserFeedbackField(stringToUserFeedbackField(resultSetToString(rs, "user_feedback_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return userFeedback;
    }

}