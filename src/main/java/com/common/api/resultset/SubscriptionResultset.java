package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Subscription;
import com.common.api.util.ResultSetConversion;


public class SubscriptionResultset extends ResultSetConversion implements RowMapper<Subscription> {

	@Override
	public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {

		Subscription subscription = new Subscription();
		try {
			subscription.setId(resultSetToString(rs, "id"));
			subscription.setCompanyId(resultSetToString(rs, "company_id"));
			subscription.setGroupId(resultSetToString(rs, "group_id"));
			subscription.setDivisionId(resultSetToString(rs, "division_id"));
			subscription.setName(resultSetToString(rs, "name"));
			subscription.setStartTime(resultSetToTimestamp(rs, "start_time"));
			subscription.setEndTime(resultSetToTimestamp(rs, "end_time"));
			subscription.setOrderCount(resultSetToInteger(rs, "order_count"));
			subscription.setIosLicenseCount(resultSetToInteger(rs, "ios_license_count"));
			subscription.setAndroidLicenseCount(resultSetToInteger(rs, "android_license_count"));
			subscription.setAvailableLicenseCount(resultSetToInteger(rs, "available_license_count"));
			subscription.setStatus(resultSetToString(rs, "status"));
			subscription.setType(resultSetToString(rs, "type"));
			subscription.setActive(resultSetToString(rs, "active"));
			subscription.setCreatedBy(resultSetToString(rs, "created_by"));
			subscription.setModifiedBy(resultSetToString(rs, "modified_by"));
			subscription.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
			subscription.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));
		}catch (Exception errMess) {
    	}
		return subscription;
	}
}

