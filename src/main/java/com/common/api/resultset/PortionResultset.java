package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Portion;
import com.common.api.util.ResultSetConversion;

public class PortionResultset extends ResultSetConversion implements RowMapper<Portion> {

    @Override
	public Portion mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Portion portion = new Portion();
    	try {
    		portion.setId(resultSetToString(rs, "id"));
    		portion.setCompanyId(resultSetToString(rs, "company_id"));
    		portion.setGroupId(resultSetToString(rs, "group_id"));
    		portion.setDivisionId(resultSetToString(rs, "division_id"));
    		portion.setModuleId(resultSetToString(rs, "module_id"));
    		portion.setUserId(resultSetToString(rs, "user_id"));
    		portion.setPropertyId(resultSetToString(rs, "property_id"));
    		portion.setSectionId(resultSetToString(rs, "section_id"));
    		portion.setName(resultSetToString(rs, "name"));
    		portion.setType(resultSetToString(rs, "type"));
    		portion.setCategory(resultSetToString(rs, "category"));
    		portion.setStatus(resultSetToString(rs, "status"));
    		portion.setActive(resultSetToString(rs, "active"));
    		portion.setCreatedBy(resultSetToString(rs, "created_by"));
    		portion.setModifiedBy(resultSetToString(rs, "modified_by"));
    		portion.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		portion.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			portion.setPortionField(stringToPortionField(resultSetToString(rs, "portion_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return portion;
    }

}