package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.ModulePreference;
import com.common.api.util.ResultSetConversion;

public class ModulePreferenceResultset extends ResultSetConversion implements RowMapper<ModulePreference> {

    @Override
	public ModulePreference mapRow(ResultSet rs, int rowNum) throws SQLException {

    	ModulePreference modulePreference = new ModulePreference();
    	try {
    		modulePreference.setId(resultSetToString(rs, "id"));
    		modulePreference.setCompanyId(resultSetToString(rs, "company_id"));
    		modulePreference.setGroupId(resultSetToString(rs, "group_id"));
    		modulePreference.setDivisionId(resultSetToString(rs, "division_id"));
    		modulePreference.setModuleId(resultSetToString(rs, "module_id"));
    		modulePreference.setCode(resultSetToString(rs, "code"));
    		modulePreference.setMinimumStableVersion(resultSetToString(rs, "minimum_stable_version"));
    		modulePreference.setCurrentStableVersion(resultSetToString(rs, "current_stable_version"));
    		modulePreference.setSystemCode(resultSetToString(rs, "system_code"));
    		modulePreference.setNotificationStatus(resultSetToString(rs, "notification_status"));
    		modulePreference.setNotificationStatusSuccessAt(resultSetToTimestamp(rs, "notification_status_success_at"));
    		modulePreference.setStatus(resultSetToString(rs, "status"));
    		modulePreference.setType(resultSetToString(rs, "type"));
    		modulePreference.setName(resultSetToString(rs, "name"));
    		modulePreference.setCategory(resultSetToString(rs, "category"));
    		modulePreference.setStatus(resultSetToString(rs, "status"));
    		modulePreference.setActive(resultSetToString(rs, "active"));
    		modulePreference.setCreatedBy(resultSetToString(rs, "created_by"));
    		modulePreference.setModifiedBy(resultSetToString(rs, "modified_by"));
    		modulePreference.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		modulePreference.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			modulePreference.setModulePreferenceField(stringToModulePreferenceField(resultSetToString(rs, "module_preference_field")));
    		} catch (Exception errMess) { }
    		try {
    			modulePreference.setModulePrefAppBootSetting(stringToModulePrefAppBootSetting(resultSetToString(rs, "module_pref_app_boot_setting")));
    		} catch (Exception errMess) { }


    	} catch (Exception errMess) {
    	}
        return modulePreference;
    }

}