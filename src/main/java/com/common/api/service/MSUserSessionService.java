package com.common.api.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserSessionService;
import com.common.api.model.UserSession;
import com.common.api.model.field.UserSessionField;
import com.common.api.resultset.UserSessionResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserSessionService extends APIFixedConstant implements UserSessionService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserSession> viewListByCriteria(String companyId, String userId, String id, String username, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String selQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(company_id) = LOWER(:companyId) ";
			parameters.addValue("companyId", companyId);
		}
		if (userId != null && userId.length() > 0) {
			selQuery = selQuery + " AND LOWER(user_id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (username != null && username.length() > 0) {
			selQuery = selQuery + " AND LOWER(username) = LOWER(:username) ";
			parameters.addValue("username", username);
		}
		if (statusList != null && statusList.size() > 0) {
			String statusListTemp = resultSetConversion.stringListToCommaAndQuoteString(statusList);
			selQuery = selQuery + " AND status in (" + statusListTemp + ")";
		}
		if (typeList != null && typeList.size() > 0) {
			String typeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList);
			selQuery = selQuery + " AND type in (" + typeListTemp + ")";
		}

		if (selQuery.length() > 0) {

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;
			selQuery = selQuery + " OFFSET :offset ROWS";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			selQuery = "select * from " + TB_USER_SESSION + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserSessionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public List<UserSession> viewListWithSecretKeyByCriteria(String companyId, String userId, String id, String username, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String selQuery = " LOWER(usess.active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(usess.company_id) = LOWER(:companyId) ";
			parameters.addValue("companyId", companyId);
		}
		if (userId != null && userId.length() > 0) {
			selQuery = selQuery + " AND LOWER(usess.user_id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(usess.id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (username != null && username.length() > 0) {
			selQuery = selQuery + " AND LOWER(usess.username) = LOWER(:username) ";
			parameters.addValue("username", username);
		}
		if (statusList != null && statusList.size() > 0) {
			String statusListTemp = resultSetConversion.stringListToCommaAndQuoteString(statusList);
			selQuery = selQuery + " AND usess.status in (" + statusListTemp + ")";
		}
		if (typeList != null && typeList.size() > 0) {
			String typeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList);
			selQuery = selQuery + " AND usess.type in (" + typeListTemp + ")";
		}

		if (selQuery.length() > 0) {

			selQuery = selQuery + " ORDER BY usess." + sortBy + " " + sortOrder;
			selQuery = selQuery + " OFFSET :offset ROWS";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			selQuery = "select usess.*, ISNULL(us.secret_key, '') as userSecretKey "
					+ "	 from " + TB_USER_SESSION + " as usess "
							+ " LEFT JOIN " + TB_USER_SECRET + " as us "
									+ " ON us.user_id = usess.user_id and us.active = 'ACTIVE'"
					+ " where " + selQuery;

			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserSessionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public List<UserSession> viewUserListByCriteriaForPushNotification(String companyId, String divisionId, String userId, String userCategory, List<String> deviceStatusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String selQuery = " LOWER(usess.active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(usess.company_id) = LOWER(:companyId) ";
			parameters.addValue("companyId", companyId);
		}
		if (divisionId != null && divisionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(usr.division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);
		}
		if (userId != null && userId.length() > 0) {
			selQuery = selQuery + " AND LOWER(usr.id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (userCategory != null && userCategory.length() > 0) {
			selQuery = selQuery + " AND LOWER(usr.category) = LOWER(:userCategory) ";
			parameters.addValue("userCategory", userCategory);
		}
		if (deviceStatusList != null && deviceStatusList.size() > 0) {
			String deviceStatusListTemp = resultSetConversion.stringListToCommaAndQuoteString(deviceStatusList);
			selQuery = selQuery + " AND usess.device_status in (" + deviceStatusListTemp + ") ";
			selQuery = selQuery + " AND len(usess.device_token) > 50 ";
			selQuery = selQuery + " AND len(usess.device_unique_id) > 5 ";
		}
		if (typeList != null && typeList.size() > 0) {
			String deviceTypeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList);
			selQuery = selQuery + " AND usess.type in (" + deviceTypeListTemp + ") ";
			selQuery = selQuery + " AND len(usess.device_token) > 50 ";
			selQuery = selQuery + " AND len(usess.device_unique_id) > 5 ";
		}

		if (selQuery.length() > 0) {

			selQuery = "select usess.user_id, usess.username, usr.first_name as user_first_name "
					 + " from " + TB_USER_SESSION + " as usess "
					 		+ " inner join " + TB_USER + " as usr "
					 			+ " on usr.id = usess.user_id "
					 + " where " + selQuery;

			selQuery = selQuery + " GROUP BY usess.user_id, usess.username, usr.first_name ";
			selQuery = selQuery + " ORDER BY usess.user_id, usess.username, usr.first_name ";
			selQuery = selQuery + " OFFSET :offset ROWS ";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY ";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserSessionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public List<UserSession> viewListByCriteriaForPushNotification(String companyId, String divisionId, String userId, String userCategory, List<String> deviceStatusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String selQuery = " LOWER(usess.active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(usess.company_id) = LOWER(:companyId) ";
			parameters.addValue("companyId", companyId);
		}
		if (divisionId != null && divisionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(usr.division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);
		}
		if (userId != null && userId.length() > 0) {
			selQuery = selQuery + " AND LOWER(usr.id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (userCategory != null && userCategory.length() > 0) {
			selQuery = selQuery + " AND LOWER(usr.category) = LOWER(:userCategory) ";
			parameters.addValue("userCategory", userCategory);
		}
		if (deviceStatusList != null && deviceStatusList.size() > 0) {
			String deviceStatusListTemp = resultSetConversion.stringListToCommaAndQuoteString(deviceStatusList);
			selQuery = selQuery + " AND usess.device_status in (" + deviceStatusListTemp + ") ";
			selQuery = selQuery + " AND len(usess.device_token) > 50 ";
			selQuery = selQuery + " AND len(usess.device_unique_id) > 5 ";
		}
		if (typeList != null && typeList.size() > 0) {
			String deviceTypeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList);
			selQuery = selQuery + " AND usess.type in (" + deviceTypeListTemp + ") ";
			selQuery = selQuery + " AND len(usess.device_token) > 50 ";
			selQuery = selQuery + " AND len(usess.device_unique_id) > 5 ";
		}

		if (selQuery.length() > 0) {

			selQuery = "select usess.*, usr.first_name as user_first_name "
					 + " from " + TB_USER_SESSION + " as usess "
					 		+ " inner join " + TB_USER + " as usr "
					 			+ " on usr.id = usess.user_id "
					 + " where " + selQuery;

			selQuery = selQuery + " ORDER BY usess." + sortBy + " " + sortOrder;
			selQuery = selQuery + " OFFSET :offset ROWS";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserSessionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public List<UserSession> viewListByCriteriaForSchedulerPushNotification(String companyId, String divisionId, String userId, String type, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String selQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(company_id) = LOWER(:companyId) ";
			parameters.addValue("companyId", companyId);
		}
		if (divisionId != null && divisionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);
		}
		if (userId != null && userId.length() > 0) {
			selQuery = selQuery + " AND LOWER(user_id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (type != null && type.length() > 0) {
 			selQuery = selQuery + " AND type = LOWER(:type) ";
 			selQuery = selQuery + " AND len(device_token) > 50 ";
 			parameters.addValue("type", type);
		}

		if (selQuery.length() > 0) {

			selQuery = "select * from " + TB_USER_SESSION + " where " + selQuery;

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;
			selQuery = selQuery + " OFFSET :offset ROWS";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserSessionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserSession createUserSession(UserSession userSession) {

		String objectId = Util.getTableOrCollectionObjectId();
		userSession.setId(objectId);

		UserSessionField userSessionFieldTemp = userSession.getUserSessionField();
		String userSessionField = resultSetConversion.userSessionFieldToString(userSessionFieldTemp);

		String insertQuery = "insert into " + TB_USER_SESSION + " (id, company_id, group_id, division_id, module_id, user_id, username, "
											+ "	device_unique_id, device_token, device_status, app_version, "
											+ " user_session_field, remote_address, user_agent, category, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :groupId, :divisionId, :moduleId, :userId, :username, "
											+ "	:deviceUniqueId, :deviceToken, :deviceStatus, :appVersion, "
											+ " :userSessionField, :remoteAddress, :userAgent, :category, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userSession.getId())
			.addValue("companyId", userSession.getCompanyId())
			.addValue("groupId", userSession.getGroupId())
			.addValue("divisionId", userSession.getDivisionId())
			.addValue("moduleId", userSession.getModuleId())
			.addValue("userId", userSession.getUserId())
			.addValue("username",  userSession.getUsername())
			.addValue("deviceUniqueId",  userSession.getDeviceUniqueId())
			.addValue("deviceToken",  userSession.getDeviceToken())
			.addValue("deviceStatus",  userSession.getDeviceStatus())
			.addValue("appVersion",  userSession.getAppVersion())
			.addValue("userSessionField", userSessionField)
			.addValue("remoteAddress",  userSession.getRemoteAddress())
			.addValue("userAgent",  userSession.getUserAgent())
			.addValue("category",  userSession.getCategory())
			.addValue("status",  userSession.getStatus())
			.addValue("type",  userSession.getType())
			.addValue("createdAt",  userSession.getCreatedAt())
			.addValue("modifiedAt",  userSession.getModifiedAt())
			.addValue("createdBy",  userSession.getCreatedBy())
			.addValue("modifiedBy",  userSession.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userSession;
		}
		return new UserSession();
	}

	@Override
	public int modifyUserSession(UserSession userSession) {
		String updateQuery = "update " + TB_USER_SESSION  + " set modified_at = :modifiedAt "
				+ " WHERE LOWER(id) = LOWER(:id) ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userSession.getId())
			.addValue("modifiedAt", userSession.getModifiedAt());
		return namedParameterJdbcTemplate.update(updateQuery, parameters);
	}

	@Override
	public int removeUserSessionById(String id) {
		String deleteQuery = "update " + TB_USER_SESSION
				+ " SET active = :active "
				+ " where LOWER(id) = LOWER(:id) ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

	@Override
	public int removeUserSessionByUserId(String userId) {
		String deleteQuery = "update " + TB_USER_SESSION
				+ " SET active = :active "
				+ " where id != '' AND "
					+ "	LOWER(user_id) = LOWER(:userId) ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

	@Override
	public int removeUserSessionByTypeAndDeviceUniqueId(String type, String deviceUniqueId) {
		String deleteQuery = "update " + TB_USER_SESSION
					+ " SET active = :active "
					+ " where id != '' AND type = :type AND device_unique_id = :deviceUniqueId ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("type", type)
				.addValue("deviceUniqueId", deviceUniqueId)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

	@Override
	public int removeUserSessionByTypeAndDeviceToken(String type, String deviceToken) {
		String deleteQuery = "update " + TB_USER_SESSION
					+ " SET active = :active "
					+ " where id != '' AND type = :type AND device_token = :deviceToken ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("type", type)
				.addValue("deviceToken", deviceToken)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

	@Override
	public int removeActiveUserSessionByModifiedAt(Timestamp modifiedAt) {
		String deleteQuery = "delete from " + TB_USER_SESSION
				+ " where id != '' AND active = :active AND "
					+ "	modified_at <= :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("modifiedAt", modifiedAt)
				.addValue("active", DV_ACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

	@Override
	public int removeInactiveUserSessionByModifiedAt(Timestamp modifiedAt) {
		String deleteQuery = "delete from " + TB_USER_SESSION
				+ " where id != '' AND active = :active AND "
					+ "	modified_at <= :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("modifiedAt", modifiedAt)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}