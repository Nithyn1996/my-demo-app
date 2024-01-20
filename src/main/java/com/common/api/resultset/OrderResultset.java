package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Order;
import com.common.api.util.ResultSetConversion;

public class OrderResultset extends ResultSetConversion implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

		Order order = new Order();
		try {
			order.setId(resultSetToString(rs, "id"));
			order.setCompanyId(resultSetToString(rs, "company_id"));
			order.setGroupId(resultSetToString(rs, "group_id"));
			order.setDivisionId(resultSetToString(rs, "division_id"));
			order.setSubscriptionId(resultSetToString(rs, "subscription_id"));
			order.setName(resultSetToString(rs, "name"));
			order.setIosLicenseCount(resultSetToInteger(rs, "ios_license_count"));
			order.setAndroidLicenseCount(resultSetToInteger(rs, "android_license_count"));
			order.setTotalLicenseCount(resultSetToInteger(rs, "total_license_count"));
			order.setStatus(resultSetToString(rs, "status"));
			order.setType(resultSetToString(rs, "type"));
			order.setActive(resultSetToString(rs, "active"));
			order.setCreatedBy(resultSetToString(rs, "created_by"));
			order.setModifiedBy(resultSetToString(rs, "modified_by"));
			order.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
			order.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));
		}catch (Exception errMess) {
    	}
		return order;
	}

}
