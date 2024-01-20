package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Section;
import com.common.api.util.ResultSetConversion;

public class SectionResultset extends ResultSetConversion implements RowMapper<Section> {

    @Override
	public Section mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Section section = new Section();
    	try {
    		section.setId(resultSetToString(rs, "id"));
    		section.setCompanyId(resultSetToString(rs, "company_id"));
    		section.setGroupId(resultSetToString(rs, "group_id"));
    		section.setDivisionId(resultSetToString(rs, "division_id"));
    		section.setModuleId(resultSetToString(rs, "module_id"));
    		section.setUserId(resultSetToString(rs, "user_id"));
    		section.setPropertyId(resultSetToString(rs, "property_id"));
    		section.setName(resultSetToString(rs, "name"));
    		section.setType(resultSetToString(rs, "type"));
    		section.setCategory(resultSetToString(rs, "category"));
    		section.setStatus(resultSetToString(rs, "status"));
    		section.setActive(resultSetToString(rs, "active"));
    		section.setCreatedBy(resultSetToString(rs, "created_by"));
    		section.setModifiedBy(resultSetToString(rs, "modified_by"));
    		section.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		section.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			section.setSectionField(stringToSectionField(resultSetToString(rs, "section_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return section;
    }

}