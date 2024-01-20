package com.common.api.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.UserDeviceSessionService;
import com.common.api.model.UserDeviceSession;
import com.common.api.model.field.UserDeviceSessionField;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserDeviceSessionService extends APIFixedConstant implements UserDeviceSessionService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public UserDeviceSession createUserDeviceSession(UserDeviceSession userDeviceSession) {

		String objectId = Util.getTableOrCollectionObjectId();
		userDeviceSession.setId(objectId);

		UserDeviceSessionField userDeviceSessionFieldTemp = userDeviceSession.getUserDeviceSessionField();
		String userDeviceSessionField = resultSetConversion.userDeviceSessionFieldToString(userDeviceSessionFieldTemp);

		String insertQuery = "insert into " + TB_USER_DEVICE_SESSION + " (id, company_id, user_id, device_order_id, device_unique_id, device_type, "
											+ " user_device_session_field, remote_address, user_agent, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :userId, :deviceOrderId, :deviceUniqueId, :deviceType, "
											+ " :userDeviceSessionField, :remoteAddress, :userAgent, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userDeviceSession.getId())
			.addValue("companyId", userDeviceSession.getCompanyId())
			.addValue("userId", userDeviceSession.getUserId())
			.addValue("deviceOrderId", userDeviceSession.getDeviceOrderId())
			.addValue("deviceUniqueId", userDeviceSession.getDeviceUniqueId())
			.addValue("deviceType", userDeviceSession.getDeviceType())
			.addValue("userDeviceSessionField", userDeviceSessionField)
			.addValue("remoteAddress",  userDeviceSession.getRemoteAddress())
			.addValue("userAgent",  userDeviceSession.getUserAgent())
			.addValue("status",  userDeviceSession.getStatus())
			.addValue("type",  userDeviceSession.getType())
			.addValue("createdAt",  userDeviceSession.getCreatedAt())
			.addValue("modifiedAt",  userDeviceSession.getModifiedAt())
			.addValue("createdBy",  userDeviceSession.getCreatedBy())
			.addValue("modifiedBy",  userDeviceSession.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userDeviceSession;
		}
		return new UserDeviceSession();
	}

	@Override
	public int removeUserDeviceSessionByModifiedAt(Timestamp modifiedAt) {
		String deleteQuery = "delete from " + TB_USER_DEVICE_SESSION + " where modified_at <= :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("modifiedAt", modifiedAt);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}