package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.UserDevice;
import com.common.api.util.ResultSetConversion;

public class UserDeviceResultset extends ResultSetConversion implements RowMapper<UserDevice> {

    @Override
	public UserDevice mapRow(ResultSet rs, int rowNum) throws SQLException {

    	UserDevice userDevice = new UserDevice();

    	try {

    		userDevice.setId(resultSetToString(rs, "id"));
    		userDevice.setCompanyId(resultSetToString(rs, "company_id"));
    		userDevice.setDivisionId(resultSetToString(rs, "division_id"));
    		userDevice.setModuleId(resultSetToString(rs, "module_id"));
    		userDevice.setUserId(resultSetToString(rs, "user_id"));

    		userDevice.setDeviceOrderId(resultSetToString(rs, "device_order_id"));
    		userDevice.setDeviceUniqueId(resultSetToString(rs, "device_unique_id"));
    		userDevice.setDeviceVersionNumber(resultSetToString(rs, "device_version_number"));
    		userDevice.setDeviceModelName(resultSetToString(rs, "device_model_name"));
    		userDevice.setDeviceType(resultSetToString(rs, "device_type"));

    		userDevice.setStatus(resultSetToString(rs, "status"));
    		userDevice.setActive(resultSetToString(rs, "active"));
    		userDevice.setType(resultSetToString(rs, "type"));

    		userDevice.setCreatedBy(resultSetToString(rs, "created_by"));
    		userDevice.setModifiedBy(resultSetToString(rs, "modified_by"));
    		userDevice.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		userDevice.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return userDevice;

    }

}