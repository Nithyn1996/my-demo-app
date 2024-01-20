package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.SectionPreference;
import com.common.api.util.ResultSetConversion;

public class SectionPreferenceResultset extends ResultSetConversion implements RowMapper<SectionPreference> {

    @Override
	public SectionPreference mapRow(ResultSet rs, int rowNum) throws SQLException {

    	SectionPreference sectionPreference = new SectionPreference();
    	try {
    		sectionPreference.setId(resultSetToString(rs, "id"));
    		sectionPreference.setCompanyId(resultSetToString(rs, "company_id"));
    		sectionPreference.setGroupId(resultSetToString(rs, "group_id"));
    		sectionPreference.setDivisionId(resultSetToString(rs, "division_id"));
    		sectionPreference.setModuleId(resultSetToString(rs, "module_id"));
    		sectionPreference.setUserId(resultSetToString(rs, "user_id"));
    		sectionPreference.setPropertyId(resultSetToString(rs, "property_id"));
    		sectionPreference.setSectionId(resultSetToString(rs, "section_id"));
    		sectionPreference.setType(resultSetToString(rs, "type"));
    		sectionPreference.setName(resultSetToString(rs, "name"));
    		sectionPreference.setCode(resultSetToString(rs, "code"));
    		sectionPreference.setRemarks(resultSetToString(rs, "remarks"));
    		sectionPreference.setComments(resultSetToString(rs, "comments"));
    		sectionPreference.setCategory(resultSetToString(rs, "category"));
    		sectionPreference.setStatus(resultSetToString(rs, "status"));
    		sectionPreference.setActive(resultSetToString(rs, "active"));
    		sectionPreference.setCreatedBy(resultSetToString(rs, "created_by"));
    		sectionPreference.setModifiedBy(resultSetToString(rs, "modified_by"));
    		sectionPreference.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		sectionPreference.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			sectionPreference.setSectionPrefVehicleInspection(stringToSectionPrefVehicleInspection(resultSetToString(rs, "section_pref_vehicle_inspection")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return sectionPreference;
    }

}