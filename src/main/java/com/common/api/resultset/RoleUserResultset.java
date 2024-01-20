package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.RoleUser;
import com.common.api.util.ResultSetConversion;

public class RoleUserResultset extends ResultSetConversion implements RowMapper<RoleUser> {

    @Override
	public RoleUser mapRow(ResultSet rs, int rowNum) throws SQLException {

    	RoleUser roleUser = new RoleUser();

    	try {

    		roleUser.setId(resultSetToString(rs, "id"));
    		roleUser.setCompanyId(resultSetToString(rs, "company_id"));
    		roleUser.setRoleGroupId(resultSetToString(rs, "role_group_id"));
    		roleUser.setUserId(resultSetToString(rs, "user_id"));
    		roleUser.setPriority(resultSetToString(rs, "priority"));
    		roleUser.setStatus(resultSetToString(rs, "status"));
    		roleUser.setType(resultSetToString(rs, "type"));
    		roleUser.setActive(resultSetToString(rs, "active"));
    		roleUser.setCreatedBy(resultSetToString(rs, "created_by"));
    		roleUser.setModifiedBy(resultSetToString(rs, "modified_by"));
    		roleUser.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		roleUser.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return roleUser;
    }


}