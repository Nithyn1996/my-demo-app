package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.RoleDivision;
import com.common.api.util.ResultSetConversion;

public class RoleDivisionResultset extends ResultSetConversion implements RowMapper<RoleDivision> {

    @Override
	public RoleDivision mapRow(ResultSet rs, int rowNum) throws SQLException {

    	RoleDivision roleDivision = new RoleDivision();

    	try {

    		roleDivision.setId(resultSetToString(rs, "id"));
    		roleDivision.setCompanyId(resultSetToString(rs, "company_id"));
    		roleDivision.setDivisionId(resultSetToString(rs, "division_id"));
    		roleDivision.setRoleDivisionName(resultSetToString(rs, "role_division_name"));
    		roleDivision.setStatus(resultSetToString(rs, "status"));
    		roleDivision.setType(resultSetToString(rs, "type"));
    		roleDivision.setActive(resultSetToString(rs, "active"));
    		roleDivision.setCreatedBy(resultSetToString(rs, "created_by"));
    		roleDivision.setModifiedBy(resultSetToString(rs, "modified_by"));
    		roleDivision.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		roleDivision.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
        		roleDivision.setRoleDivisionPrivileges(objectStringToStringList(resultSetToString(rs, "role_division_privileges")));
	    	} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return roleDivision;
    }


}