package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.RoleGroup;
import com.common.api.util.ResultSetConversion;

public class RoleGroupResultset extends ResultSetConversion implements RowMapper<RoleGroup> {

    @Override
	public RoleGroup mapRow(ResultSet rs, int rowNum) throws SQLException {

    	RoleGroup roleGroup = new RoleGroup();

    	try {

    		roleGroup.setId(resultSetToString(rs, "id"));
    		roleGroup.setCompanyId(resultSetToString(rs, "company_id"));
    		roleGroup.setRoleCompanyId(resultSetToString(rs, "role_company_id"));
    		roleGroup.setRoleDivisionId(resultSetToString(rs, "role_division_id"));
    		roleGroup.setRoleModuleId(resultSetToString(rs, "role_module_id"));

    		roleGroup.setRoleGroupName(resultSetToString(rs, "role_group_name"));
    		roleGroup.setStatus(resultSetToString(rs, "status"));
    		roleGroup.setType(resultSetToString(rs, "type"));
    		roleGroup.setActive(resultSetToString(rs, "active"));
    		roleGroup.setCreatedBy(resultSetToString(rs, "created_by"));
    		roleGroup.setModifiedBy(resultSetToString(rs, "modified_by"));
    		roleGroup.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		roleGroup.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return roleGroup;
    }


}