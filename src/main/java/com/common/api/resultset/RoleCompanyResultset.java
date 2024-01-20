package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.RoleCompany;
import com.common.api.util.ResultSetConversion;

public class RoleCompanyResultset extends ResultSetConversion implements RowMapper<RoleCompany> {

    @Override
	public RoleCompany mapRow(ResultSet rs, int rowNum) throws SQLException {

    	RoleCompany roleCompany = new RoleCompany();

    	try {

    		roleCompany.setId(resultSetToString(rs, "id"));
    		roleCompany.setCompanyId(resultSetToString(rs, "company_id"));
    		roleCompany.setRoleCompanyName(resultSetToString(rs, "role_company_name"));
    		roleCompany.setStatus(resultSetToString(rs, "status"));
    		roleCompany.setType(resultSetToString(rs, "type"));
    		roleCompany.setActive(resultSetToString(rs, "active"));
    		roleCompany.setCreatedBy(resultSetToString(rs, "created_by"));
    		roleCompany.setModifiedBy(resultSetToString(rs, "modified_by"));
    		roleCompany.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		roleCompany.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
        		roleCompany.setRoleCompanyPrivileges(objectStringToStringList(resultSetToString(rs, "role_company_privileges")));
	    	} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return roleCompany;
    }


}