package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.GroupPreference;
import com.common.api.util.ResultSetConversion;

public class GroupPreferenceResultset extends ResultSetConversion implements RowMapper<GroupPreference> {

    @Override
	public GroupPreference mapRow(ResultSet rs, int rowNum) throws SQLException {

    	GroupPreference groupPreference = new GroupPreference();
    	try {
    		groupPreference.setId(resultSetToString(rs, "id"));
    		groupPreference.setCompanyId(resultSetToString(rs, "company_id"));
    		groupPreference.setGroupId(resultSetToString(rs, "group_id"));
     		groupPreference.setType(resultSetToString(rs, "type"));
    		groupPreference.setName(resultSetToString(rs, "name"));
    		groupPreference.setCategory(resultSetToString(rs, "category"));
    		groupPreference.setStatus(resultSetToString(rs, "status"));
    		groupPreference.setActive(resultSetToString(rs, "active"));
    		groupPreference.setCreatedBy(resultSetToString(rs, "created_by"));
    		groupPreference.setModifiedBy(resultSetToString(rs, "modified_by"));
    		groupPreference.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		groupPreference.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			groupPreference.setGroupPreferenceField(stringToGroupPreferenceField(resultSetToString(rs, "group_preference_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return groupPreference;
    }

}