package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserAuthentication;
import com.common.api.util.ResultSetConversion;

public class UserAuthenticationResultset extends ResultSetConversion implements RowMapper<UserAuthentication> {

    @Override
	public UserAuthentication mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserAuthentication usrAuthen = new UserAuthentication();
    	try {
    		usrAuthen.setId(resultSetToString(rs, "id"));
    		usrAuthen.setCompanyId(resultSetToString(rs, "company_id"));
    		usrAuthen.setDivisionId(resultSetToString(rs, "division_id"));
    		usrAuthen.setModuleId(resultSetToString(rs, "module_id"));
    		usrAuthen.setUserId(resultSetToString(rs, "user_id"));
    		usrAuthen.setName(resultSetToString(rs, "name"));
    		usrAuthen.setDeviceUniqueId(resultSetToString(rs, "device_unique_id"));
    		usrAuthen.setType(resultSetToString(rs, "type"));
    		usrAuthen.setCategory(resultSetToString(rs, "category"));
    		usrAuthen.setStatus(resultSetToString(rs, "status"));
    		usrAuthen.setActive(resultSetToString(rs, "active"));
    		usrAuthen.setCreatedBy(resultSetToString(rs, "created_by"));
    		usrAuthen.setModifiedBy(resultSetToString(rs, "modified_by"));
    		usrAuthen.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		usrAuthen.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			usrAuthen.setUserAuthenticationField(stringToUserAuthenticationField(resultSetToString(rs, "user_authentication_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return usrAuthen;
    }

}