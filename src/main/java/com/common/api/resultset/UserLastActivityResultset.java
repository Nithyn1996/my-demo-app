package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserLastActivity;
import com.common.api.util.ResultSetConversion;

public class UserLastActivityResultset extends ResultSetConversion implements RowMapper<UserLastActivity> {

    @Override
	public UserLastActivity mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserLastActivity userLastActivity = new UserLastActivity();

    	try {

    		userLastActivity.setId(resultSetToString(rs, "id"));
    		userLastActivity.setCompanyId(resultSetToString(rs, "company_id"));
    		userLastActivity.setGroupId(resultSetToString(rs, "group_id"));
    		userLastActivity.setDivisionId(resultSetToString(rs, "division_id"));
    		userLastActivity.setModuleId(resultSetToString(rs, "module_id"));
    		userLastActivity.setUserId(resultSetToString(rs, "user_id"));

    		userLastActivity.setPasswordChangedAt(resultSetToTimestamp(rs, "password_changed_at"));

    		userLastActivity.setSessionWebAt(resultSetToTimestamp(rs, "session_web_at"));
    		userLastActivity.setSessionIosAt(resultSetToTimestamp(rs, "session_ios_at"));
    		userLastActivity.setSessionAndroidAt(resultSetToTimestamp(rs, "session_android_at"));

    		userLastActivity.setActivityWebAt(resultSetToTimestamp(rs, "activity_web_at"));
    		userLastActivity.setActivityIosAt(resultSetToTimestamp(rs, "activity_ios_at"));
    		userLastActivity.setActivityAndroidAt(resultSetToTimestamp(rs, "activity_android_at"));

    		userLastActivity.setNoActivityPushWebAt(resultSetToTimestamp(rs, "no_activity_push_web_at"));
    		userLastActivity.setNoActivityPushIosAt(resultSetToTimestamp(rs, "no_activity_push_ios_at"));
    		userLastActivity.setNoActivityPushAndroidAt(resultSetToTimestamp(rs, "no_activity_push_android_at"));

    		userLastActivity.setAppUpdatePushIosAt(resultSetToTimestamp(rs, "app_update_push_ios_at"));
    		userLastActivity.setAppUpdatePushAndroidAt(resultSetToTimestamp(rs, "app_update_push_android_at"));

    		userLastActivity.setMapUpdatePushIosAt(resultSetToTimestamp(rs, "map_update_push_ios_at"));
    		userLastActivity.setMapUpdatePushAndroidAt(resultSetToTimestamp(rs, "map_update_push_android_at"));

    		userLastActivity.setType(resultSetToString(rs, "type"));
    		userLastActivity.setActive(resultSetToString(rs, "active"));
    		userLastActivity.setCreatedBy(resultSetToString(rs, "created_by"));
    		userLastActivity.setModifiedBy(resultSetToString(rs, "modified_by"));
    		userLastActivity.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		userLastActivity.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return userLastActivity;
    }

}