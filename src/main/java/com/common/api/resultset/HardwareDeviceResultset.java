package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.HardwareDevice;
import com.common.api.util.ResultSetConversion;

public class HardwareDeviceResultset extends ResultSetConversion implements RowMapper<HardwareDevice> {

    @Override
	public HardwareDevice mapRow(ResultSet rs, int rowNum) throws SQLException {

    	HardwareDevice hardwareDevice = new HardwareDevice();

    	try {

    		hardwareDevice.setId(resultSetToString(rs, "id"));
    		hardwareDevice.setCompanyId(resultSetToString(rs, "company_id"));

    		hardwareDevice.setDeviceOrderId(resultSetToString(rs, "device_order_id"));
    		hardwareDevice.setDeviceUniqueId(resultSetToString(rs, "device_unique_id"));
    		hardwareDevice.setDeviceVersionNumber(resultSetToString(rs, "device_version_number"));
    		hardwareDevice.setDeviceModelName(resultSetToString(rs, "device_model_name"));
    		hardwareDevice.setDeviceType(resultSetToString(rs, "device_type"));

    		hardwareDevice.setStatus(resultSetToString(rs, "status"));
    		hardwareDevice.setActive(resultSetToString(rs, "active"));
    		hardwareDevice.setType(resultSetToString(rs, "type"));

    		hardwareDevice.setCreatedBy(resultSetToString(rs, "created_by"));
    		hardwareDevice.setModifiedBy(resultSetToString(rs, "modified_by"));
    		hardwareDevice.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		hardwareDevice.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return hardwareDevice;

    }

}