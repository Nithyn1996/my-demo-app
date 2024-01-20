package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.ManualSchedulerService;
import com.common.api.model.User;
import com.common.api.model.type.NotificationModelType;
import com.common.api.resultset.UserResultset;
import com.common.api.util.ResultSetConversion;

@Service
public class MSManualSchedulerService extends APIFixedConstant implements ManualSchedulerService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<User> viewUserListByUserSession(String companyId, String groupId, String divisionId, String moduleId, String userId, String type, String userSessionType, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String selQueryTemp = "";
		if (groupId.length() > 0)
			selQueryTemp = selQueryTemp + " AND usr.group_id = '" + groupId + "'";
		if (divisionId.length() > 0)
			selQueryTemp = selQueryTemp + " AND usr.division_id = '" + divisionId + "'";
		if (moduleId.length() > 0)
			selQueryTemp = selQueryTemp + " AND usr.module_id = '" + moduleId + "'";
		if (userId.length() > 0)
			selQueryTemp = selQueryTemp + " AND usr.id = '" + userId + "'";

		if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_APP_VERSION.toString())) {
			selQueryTemp = selQueryTemp + " AND (FORMAT(convert(datetime, uLastAct.app_update_push_android_at), 'yyyy-MM-dd') != FORMAT(convert(datetime, GETDATE()), 'yyyy-MM-dd')) ";
 		} else if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_LIBRARY_ZIP.toString())) {
 			selQueryTemp = selQueryTemp + " AND (FORMAT(convert(datetime, uLastAct.map_update_push_android_at), 'yyyy-MM-dd') != FORMAT(convert(datetime, GETDATE()), 'yyyy-MM-dd')) ";
		} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_APP_VERSION.toString())) {
			selQueryTemp = selQueryTemp + " AND (FORMAT(convert(datetime, uLastAct.app_update_push_ios_at), 'yyyy-MM-dd') != FORMAT(convert(datetime, GETDATE()), 'yyyy-MM-dd')) ";
 		} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_LIBRARY_ZIP.toString())) {
 			selQueryTemp = selQueryTemp + " AND (FORMAT(convert(datetime, uLastAct.map_update_push_ios_at), 'yyyy-MM-dd') != FORMAT(convert(datetime, GETDATE()), 'yyyy-MM-dd')) ";
 		}

		String selQuery = "select usr.id, max(usr.username) as username, max(usr.first_name) as first_name, "
									+ "	max(usr.company_id) as company_id, max(usr.group_id) as group_id, "
									+ " max(usr.division_id) as division_id, max(usr.module_id) as module_id, "
									+ "	max(usr.category) as category, max(usr.type) as type, max(usr.status) as status, "
									+ " usr.created_at "
								+ "	from " + TB_USER + " AS usr "
									+ " inner join " + TB_USER_SESSION + " AS uSession "
											+ "	on usr.id = uSession.user_id "
									+ " inner join " + TB_USER_LAST_ACTIVITY + " AS uLastAct "
											+ "	on usr.id = uLastAct.user_id AND uLastAct.type = 'USER_LAST_ACTIVITY' "
								+ "	where usr.active = 'ACTIVE' AND uSession.active = 'ACTIVE' AND uLastAct.active = 'ACTIVE' "
										+ "	AND usr.company_id = '" + companyId + "' AND uSession.type = '" + userSessionType + "' "
										+ " AND len(uSession.device_token) > 30 " + selQueryTemp
								+ " GROUP BY usr.id, usr.created_at"
								+ "	ORDER BY usr." + sortBy + " " + sortOrder
								+ " OFFSET " + offset + " ROWS"
								+ " FETCH NEXT " + limit + " ROWS ONLY";

		if (selQuery.length() > 0) {
 			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public List<User> viewUserListByDevice(String companyId, String groupId, String divisionId, String moduleId, String userId, String type, String userSessionType, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String selQueryTemp = "";
		if (groupId.length() > 0)
			selQueryTemp = selQueryTemp + " AND usr.group_id = '" + groupId + "'";
		if (divisionId.length() > 0)
			selQueryTemp = selQueryTemp + " AND usr.division_id = '" + divisionId + "'";
		if (moduleId.length() > 0)
			selQueryTemp = selQueryTemp + " AND usr.module_id = '" + moduleId + "'";
		if (userId.length() > 0)
			selQueryTemp = selQueryTemp + " AND usr.id = '" + userId + "'";

		if (type.equalsIgnoreCase(NotificationModelType.pushType.ANDROID_NO_ACTIVITY.toString())) {
			selQueryTemp = selQueryTemp + " AND (FORMAT(convert(datetime, uLastAct.no_activity_push_android_at), 'yyyy-MM-dd') != FORMAT(convert(datetime, GETDATE()), 'yyyy-MM-dd')) ";
 		} else if (type.equalsIgnoreCase(NotificationModelType.pushType.IOS_NO_ACTIVITY.toString())) {
			selQueryTemp = selQueryTemp + " AND (FORMAT(convert(datetime, uLastAct.no_activity_push_ios_at), 'yyyy-MM-dd') != FORMAT(convert(datetime, GETDATE()), 'yyyy-MM-dd')) ";
 		}

		String selQuery = "select usrFinal.* "
							+ "from (select usr.id, max(usr.username) as username, max(usr.first_name) as first_name, "
											+ "	max(usr.company_id) as company_id, max(usr.group_id) as group_id, "
											+ " max(usr.division_id) as division_id, max(usr.module_id) as module_id, "
											+ "	max(usr.category) as category, max(usr.type) as type, max(usr.status) as status, "
											+ "	max(device.name) as deviceName, max(device.created_at) as deviceCreatedAt, "
											+ " usr.created_at "
										+ "	from " + TB_USER + " AS usr "
											+ " inner join " + TB_USER_SESSION + " AS uSession "
													+ "	on usr.id = uSession.user_id "
											+ " inner join " + TB_USER_LAST_ACTIVITY + " AS uLastAct "
													+ "	on usr.id = uLastAct.user_id AND uLastAct.type = 'USER_LAST_ACTIVITY'"
											+ "	inner join " + TB_DEVICE + " AS device "
													+ "	on usr.id = device.user_id AND device.type = 'SPEEDO_METER_DEVICE' "
										+ "	where usr.active = 'ACTIVE' AND uSession.active = 'ACTIVE' AND uLastAct.active = 'ACTIVE' AND device.active = 'ACTIVE' "
												+ "	AND usr.company_id = '" + companyId + "' AND uSession.type = '" + userSessionType + "' "
												+ " AND len(uSession.device_token) > 30 " + selQueryTemp
										+ " GROUP BY usr.id, usr.created_at "
									+ " ) as usrFinal "
								+ " WHERE usrFinal.deviceCreatedAt < DATEADD(day, -7, GETDATE()) "
								+ "	ORDER BY usrFinal." + sortBy + " " + sortOrder
								+ " OFFSET " + offset + " ROWS "
								+ " FETCH NEXT " + limit + " ROWS ONLY";

		if (selQuery.length() > 0) {
 			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserResultset());
		}
		return new ArrayList<>();
	}

}