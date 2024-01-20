package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Division;
import com.common.api.util.ResultSetConversion;

public class DivisionResultset extends ResultSetConversion implements RowMapper<Division> {

    @Override
	public Division mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Division division = new Division();
    	try {
    		division.setId(resultSetToString(rs, "id"));
    		division.setCompanyId(resultSetToString(rs, "company_id"));
    		division.setGroupId(resultSetToString(rs, "group_id"));
    		division.setCode(resultSetToString(rs, "code"));
    		division.setType(resultSetToString(rs, "type"));
    		division.setName(resultSetToString(rs, "name"));
    		division.setCategory(resultSetToString(rs, "category"));
    		division.setDeviceAutoStartMode(resultSetToString(rs, "device_auto_start_mode"));
    		division.setDeviceDetailTransferMode(resultSetToString(rs, "device_detail_transfer_mode"));
    		division.setStatus(resultSetToString(rs, "status"));
    		division.setActive(resultSetToString(rs, "active"));
    		division.setCreatedBy(resultSetToString(rs, "created_by"));
    		division.setModifiedBy(resultSetToString(rs, "modified_by"));
    		division.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		division.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			division.setDivisionField(stringToDivisionField(resultSetToString(rs, "division_field")));
      		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return division;
    }


}