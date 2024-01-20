package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserPreference;
import com.common.api.util.ResultSetConversion;

public class UserPreferenceResultset extends ResultSetConversion implements RowMapper<UserPreference> {

    @Override
	public UserPreference mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserPreference userPreference = new UserPreference();
    	try {
    		userPreference.setId(resultSetToString(rs, "id"));
    		userPreference.setCompanyId(resultSetToString(rs, "company_id"));
    		userPreference.setDivisionId(resultSetToString(rs, "division_id"));
    		userPreference.setModuleId(resultSetToString(rs, "module_id"));
    		userPreference.setUserId(resultSetToString(rs, "user_id"));
    		userPreference.setType(resultSetToString(rs, "type"));
    		userPreference.setName(resultSetToString(rs, "name"));
    		userPreference.setCode(resultSetToString(rs, "code"));
    		userPreference.setStartTime(resultSetToTime(rs, "start_time"));
    		userPreference.setEndTime(resultSetToTime(rs, "end_time"));
    		userPreference.setSubCategory(resultSetToString(rs, "sub_category"));
    		userPreference.setCategory(resultSetToString(rs, "category"));
    		userPreference.setStatus(resultSetToString(rs, "status"));
    		userPreference.setActive(resultSetToString(rs, "active"));
    		userPreference.setCreatedBy(resultSetToString(rs, "created_by"));
    		userPreference.setModifiedBy(resultSetToString(rs, "modified_by"));
    		userPreference.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		userPreference.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			userPreference.setUserPreferenceField(stringToUserPreferenceField(resultSetToString(rs, "user_preference_field")));
    		} catch (Exception errMess) { }

    		try {
    			userPreference.setUserPrefAppBootSetting(stringToUserPrefAppBootSetting(resultSetToString(rs, "user_pref_app_boot_setting")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return userPreference;
    }

}