package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.DivisionPreference;
import com.common.api.util.ResultSetConversion;

public class DivisionPreferenceResultset extends ResultSetConversion implements RowMapper<DivisionPreference> {

    @Override
	public DivisionPreference mapRow(ResultSet rs, int rowNum) throws SQLException {

    	DivisionPreference divisionPreference = new DivisionPreference();
    	try {
    		divisionPreference.setId(resultSetToString(rs, "id"));
    		divisionPreference.setCompanyId(resultSetToString(rs, "company_id"));
    		divisionPreference.setGroupId(resultSetToString(rs, "group_id"));
    		divisionPreference.setDivisionId(resultSetToString(rs, "division_id"));
    		divisionPreference.setType(resultSetToString(rs, "type"));
    		divisionPreference.setName(resultSetToString(rs, "name"));
    		divisionPreference.setStartTime(resultSetToTime(rs, "start_time"));
    		divisionPreference.setEndTime(resultSetToTime(rs, "end_time"));
    		divisionPreference.setCategory(resultSetToString(rs, "category"));
    		divisionPreference.setStatus(resultSetToString(rs, "status"));
    		divisionPreference.setActive(resultSetToString(rs, "active"));
    		divisionPreference.setCreatedBy(resultSetToString(rs, "created_by"));
    		divisionPreference.setModifiedBy(resultSetToString(rs, "modified_by"));
    		divisionPreference.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		divisionPreference.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			divisionPreference.setDivisionPreferenceField(stringToDivisionPreferenceField(resultSetToString(rs, "division_preference_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return divisionPreference;
    }

}