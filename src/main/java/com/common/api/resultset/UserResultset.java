package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.User;
import com.common.api.util.ResultSetConversion;

public class UserResultset extends ResultSetConversion implements RowMapper<User> {

    @Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

    	User user = new User();
    	try {
    		user.setId(resultSetToString(rs, "id"));
    		user.setCompanyId(resultSetToString(rs, "company_id"));
    		user.setGroupId(resultSetToString(rs, "group_id"));
    		user.setDivisionId(resultSetToString(rs, "division_id"));
    		user.setModuleId(resultSetToString(rs, "module_id"));
    		user.setCountryId(resultSetToString(rs, "country_id"));
    		user.setTimeZoneId(resultSetToString(rs, "time_zone_id"));
    		user.setLanguageId(resultSetToString(rs, "language_id"));
    		user.setEmail(resultSetToString(rs, "email"));
    		user.setGender(resultSetToString(rs, "gender"));
    		user.setFirstName(resultSetToString(rs, "first_name"));
    		user.setLastName(resultSetToString(rs, "last_name"));
    		user.setUsername(resultSetToString(rs, "username"));
    		user.setPassword(resultSetToString(rs, "password"));
    		user.setMobilePin(resultSetToString(rs, "mobile_pin"));
    		user.setEmailVerified(resultSetToString(rs, "email_verified"));
    		user.setProfilePicturePath(resultSetToString(rs, "profile_picture_path"));
    		user.setUsernameVerified(resultSetToString(rs, "username_verified"));
    		user.setDeviceAutoStartSubMode(resultSetToString(rs, "device_auto_start_sub_mode"));
    		user.setNextActivity(resultSetToString(rs, "next_activity"));
    		user.setCategory(resultSetToString(rs, "category"));
    		user.setStatus(resultSetToString(rs, "status"));
    		user.setType(resultSetToString(rs, "type"));
    		user.setActive(resultSetToString(rs, "active"));
    		user.setCreatedBy(resultSetToString(rs, "created_by"));
    		user.setModifiedBy(resultSetToString(rs, "modified_by"));
    		user.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		user.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		user.setUsernameType(resultSetToString(rs, "username_type"));

    		try {
    			user.setUserField(stringToUserField(resultSetToString(rs, "user_field")));
      		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return user;
    }


}