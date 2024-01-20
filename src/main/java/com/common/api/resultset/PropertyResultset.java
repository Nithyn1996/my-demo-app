package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Property;
import com.common.api.util.ResultSetConversion;

public class PropertyResultset extends ResultSetConversion implements RowMapper<Property> {

    @Override
	public Property mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Property property = new Property();
    	try {
    		property.setId(resultSetToString(rs, "id"));
    		property.setCompanyId(resultSetToString(rs, "company_id"));
    		property.setGroupId(resultSetToString(rs, "group_id"));
    		property.setDivisionId(resultSetToString(rs, "division_id"));
    		property.setModuleId(resultSetToString(rs, "module_id"));
    		property.setUserId(resultSetToString(rs, "user_id"));
    		property.setName(resultSetToString(rs, "name"));
    		property.setCategory(resultSetToString(rs, "category"));
    		property.setType(resultSetToString(rs, "type"));
    		property.setCategory(resultSetToString(rs, "category"));
    		property.setStatus(resultSetToString(rs, "status"));
    		property.setActive(resultSetToString(rs, "active"));
    		property.setCreatedBy(resultSetToString(rs, "created_by"));
    		property.setModifiedBy(resultSetToString(rs, "modified_by"));
    		property.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		property.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			property.setPropertyField(stringToPropertyField(resultSetToString(rs, "property_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return property;
    }

}