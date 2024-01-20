package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Module;
import com.common.api.util.ResultSetConversion;

public class ModuleResultset extends ResultSetConversion implements RowMapper<Module> {

    @Override
	public Module mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Module module = new Module();
    	try {
    		module.setId(resultSetToString(rs, "id"));
    		module.setCompanyId(resultSetToString(rs, "company_id"));
    		module.setGroupId(resultSetToString(rs, "group_id"));
    		module.setDivisionId(resultSetToString(rs, "division_id"));
    		module.setCode(resultSetToString(rs, "code"));
    		module.setType(resultSetToString(rs, "type"));
    		module.setName(resultSetToString(rs, "name"));
    		module.setCategory(resultSetToString(rs, "category"));
    		module.setStatus(resultSetToString(rs, "status"));
    		module.setActive(resultSetToString(rs, "active"));
    		module.setCreatedBy(resultSetToString(rs, "created_by"));
    		module.setModifiedBy(resultSetToString(rs, "modified_by"));
    		module.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		module.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			module.setModuleField(stringToModuleField(resultSetToString(rs, "module_field")));
      		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return module;
    }


}