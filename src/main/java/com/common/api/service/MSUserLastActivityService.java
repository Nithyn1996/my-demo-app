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
import com.common.api.datasink.service.UserLastActivityService;
import com.common.api.model.UserLastActivity;
import com.common.api.model.type.ModelType;
import com.common.api.resultset.UserLastActivityResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserLastActivityService extends APIFixedConstant implements UserLastActivityService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserLastActivity> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String id, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (groupId != null && groupId.length() > 0) {
			selQuery = selQuery + " AND LOWER(group_id) = LOWER(:groupId) ";
			parameters.addValue("groupId", groupId);
		}
		if (divisionId != null && divisionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);
		}
		if (moduleId != null && moduleId.length() > 0) {
			selQuery = selQuery + " AND LOWER(module_id) = LOWER(:moduleId) ";
			parameters.addValue("moduleId", moduleId);
		}
		if (userId != null && userId.length() > 0) {
			selQuery = selQuery + " AND LOWER(user_id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
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

			selQuery = "select * from " + TB_USER_LAST_ACTIVITY + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserLastActivityResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserLastActivity createUserLastActivity(UserLastActivity userLastActivity) {

		String objectId = Util.getTableOrCollectionObjectId();
		userLastActivity.setId(objectId);

		String insertQuery = "insert into " + TB_USER_LAST_ACTIVITY + " (id, company_id, group_id, division_id, module_id, user_id, "
											+ " password_changed_at, session_web_at, session_ios_at, session_android_at, "
											+ " activity_web_at, activity_ios_at, activity_android_at, "
											+ "	no_activity_push_web_at, no_activity_push_ios_at, no_activity_push_android_at, "
											+ "	app_update_push_ios_at, app_update_push_android_at, "
											+ "	map_update_push_ios_at, map_update_push_android_at, "
											+ " type, created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :groupId, :divisionId, :moduleId,  :userId, "
											+ " :passwordChangedAt, :sessionWebAt, :sessionIosAt, :sessionAndroidAt, "
											+ " :activityWebAt, :activityIosAt, :activityAndroidAt, "
											+ "	:noActivityPushWebAt, :noActivityPushIosAt, :noActivityPushAndroidAt, "
											+ "	:appUpdatePushIosAt, :appUpdatePushAndroidAt, "
											+ "	:mapUpdatePushIosAt, :mapUpdatePushAndroidAt, "
											+ " :type, :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userLastActivity.getId())
			.addValue("companyId", userLastActivity.getCompanyId())
			.addValue("groupId", userLastActivity.getGroupId())
			.addValue("divisionId", userLastActivity.getDivisionId())
			.addValue("moduleId", userLastActivity.getModuleId())
			.addValue("userId", userLastActivity.getUserId())
			.addValue("passwordChangedAt", userLastActivity.getPasswordChangedAt())
			.addValue("sessionWebAt", userLastActivity.getSessionWebAt())
			.addValue("sessionIosAt", userLastActivity.getSessionIosAt())
			.addValue("sessionAndroidAt", userLastActivity.getSessionAndroidAt())
			.addValue("activityWebAt", userLastActivity.getActivityWebAt())
			.addValue("activityIosAt", userLastActivity.getActivityIosAt())
			.addValue("activityAndroidAt", userLastActivity.getActivityAndroidAt())
			.addValue("noActivityPushWebAt", userLastActivity.getNoActivityPushWebAt())
			.addValue("noActivityPushIosAt", userLastActivity.getNoActivityPushIosAt())
			.addValue("noActivityPushAndroidAt", userLastActivity.getNoActivityPushAndroidAt())
			.addValue("appUpdatePushIosAt", userLastActivity.getAppUpdatePushIosAt())
			.addValue("appUpdatePushAndroidAt", userLastActivity.getAppUpdatePushAndroidAt())
			.addValue("mapUpdatePushIosAt", userLastActivity.getMapUpdatePushIosAt())
			.addValue("mapUpdatePushAndroidAt", userLastActivity.getMapUpdatePushAndroidAt())
			.addValue("type", userLastActivity.getType())
			.addValue("createdAt", userLastActivity.getCreatedAt())
			.addValue("modifiedAt", userLastActivity.getModifiedAt())
			.addValue("createdBy", userLastActivity.getCreatedBy())
			.addValue("modifiedBy", userLastActivity.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userLastActivity;
		}
		return new UserLastActivity();
	}

	@Override
	public UserLastActivity updateUserLastActivity(UserLastActivity userLastActivity) {

		String updateQuery = "update " + TB_USER_LAST_ACTIVITY
									+ " set "
										+ " company_id = :companyId, "
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " module_id = :moduleId, "
										+ " user_id = :userId, "
										+ " password_changed_at = :passwordChangedAt, "
										+ " session_web_at = :sessionWebAt, "
										+ " session_ios_at = :sessionIosAt, "
										+ " session_android_at = :sessionAndroidAt, "
										+ " activity_web_at = :activityWebAt, "
										+ " activity_ios_at = :activityIosAt, "
										+ " activity_android_at = :activityAndroidAt, "
										+ " no_activity_push_web_at = :noActivityPushWebAt, "
										+ " no_activity_push_ios_at = :noActivityPushIosAt, "
										+ " no_activity_push_android_at = :noActivityPushAndroidAt, "
										+ " app_update_push_ios_at = :appUpdatePushIosAt, "
										+ " app_update_push_android_at = :appUpdatePushAndroidAt, "
										+ " map_update_push_ios_at = :mapUpdatePushIosAt, "
										+ " map_update_push_android_at = :mapUpdatePushAndroidAt, "
										+ " type = :type, "
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", userLastActivity.getId())
				.addValue("companyId", userLastActivity.getCompanyId())
				.addValue("groupId", userLastActivity.getGroupId())
				.addValue("divisionId", userLastActivity.getDivisionId())
				.addValue("moduleId", userLastActivity.getModuleId())
				.addValue("userId", userLastActivity.getUserId())
				.addValue("passwordChangedAt", userLastActivity.getPasswordChangedAt())
				.addValue("sessionWebAt", userLastActivity.getSessionWebAt())
				.addValue("sessionIosAt", userLastActivity.getSessionIosAt())
				.addValue("sessionAndroidAt", userLastActivity.getSessionAndroidAt())
				.addValue("activityWebAt", userLastActivity.getActivityWebAt())
				.addValue("activityIosAt", userLastActivity.getActivityIosAt())
				.addValue("activityAndroidAt", userLastActivity.getActivityAndroidAt())
				.addValue("noActivityPushWebAt", userLastActivity.getNoActivityPushWebAt())
				.addValue("noActivityPushIosAt", userLastActivity.getNoActivityPushIosAt())
				.addValue("noActivityPushAndroidAt", userLastActivity.getNoActivityPushAndroidAt())
				.addValue("appUpdatePushIosAt", userLastActivity.getAppUpdatePushIosAt())
				.addValue("appUpdatePushAndroidAt", userLastActivity.getAppUpdatePushAndroidAt())
				.addValue("mapUpdatePushIosAt", userLastActivity.getMapUpdatePushIosAt())
				.addValue("mapUpdatePushAndroidAt", userLastActivity.getMapUpdatePushAndroidAt())
				.addValue("type", userLastActivity.getType())
				.addValue("modifiedAt", userLastActivity.getModifiedAt())
				.addValue("modifiedBy", userLastActivity.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(updateQuery, parameters);

		if (status > 0) {
			return userLastActivity;
		}
		return new UserLastActivity();
	}

	@Override
	public int updateUserLastActivityBySession(String userId, Timestamp modifiedAt, Timestamp sessionWebAt, Timestamp sessionIosAt, Timestamp sessionAndroidAt) {

		String updateQueryTemp = " modified_at = :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("modifiedAt", modifiedAt);

		if (sessionWebAt != null) {
			updateQueryTemp = updateQueryTemp + ", session_web_at = :sessionWebAt ";
			//updateQueryTemp = updateQueryTemp + ", activity_web_at = :sessionWebAt ";
			parameters.addValue("sessionWebAt", sessionWebAt);
		}
		if (sessionIosAt != null) {
			updateQueryTemp = updateQueryTemp + ", session_ios_at = :sessionIosAt ";
			//updateQueryTemp = updateQueryTemp + ", activity_ios_at = :sessionIosAt ";
			parameters.addValue("sessionIosAt", sessionIosAt);
		}
		if (sessionAndroidAt != null) {
			updateQueryTemp = updateQueryTemp + ", session_android_at = :sessionAndroidAt ";
			//updateQueryTemp = updateQueryTemp + ", activity_android_at = :sessionAndroidAt ";
			parameters.addValue("sessionAndroidAt", sessionAndroidAt);
		}

		String updateQuery = "update " + TB_USER_LAST_ACTIVITY
									+ " set " + updateQueryTemp
								+ " WHERE user_id = :userId and type = :type" ;

		parameters.addValue("userId", userId)
				  .addValue("type", ModelType.UserLastActivityType.USER_LAST_ACTIVITY.toString());
		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int updateUserLastActivityByPasswordChanged(String userId, Timestamp modifiedAt, Timestamp passwordChangedAt) {

		String updateQueryTemp = " modified_at = :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("modifiedAt", modifiedAt);

		if (passwordChangedAt != null) {
			updateQueryTemp = updateQueryTemp + ", password_changed_at = :passwordChangedAt ";
			parameters.addValue("passwordChangedAt", passwordChangedAt);
		}

		String updateQuery = "update " + TB_USER_LAST_ACTIVITY
									+ " set " + updateQueryTemp
								+ " WHERE user_id = :userId and type = :type" ;

		parameters.addValue("userId", userId)
				  .addValue("type", ModelType.UserLastActivityType.USER_LAST_ACTIVITY.toString());
		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int updateUserLastActivityByActivity(String userId, Timestamp modifiedAt, Timestamp activityWebAt, Timestamp activityIosAt, Timestamp activityAndroidAt) {

		String updateQueryTemp = " modified_at = :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("modifiedAt", modifiedAt);

		if (activityWebAt != null) {
			updateQueryTemp = updateQueryTemp + ", activity_web_at = :activityWebAt ";
			parameters.addValue("activityWebAt", activityWebAt);
		}
		if (activityIosAt != null) {
			updateQueryTemp = updateQueryTemp + ", activity_ios_at = :activityIosAt ";
			parameters.addValue("activityIosAt", activityIosAt);
		}
		if (activityAndroidAt != null) {
			updateQueryTemp = updateQueryTemp + ", activity_android_at = :activityAndroidAt ";
			parameters.addValue("activityAndroidAt", activityAndroidAt);
		}

		String updateQuery = "update " + TB_USER_LAST_ACTIVITY
									+ " set " + updateQueryTemp
								+ " WHERE user_id = :userId and type = :type" ;

		parameters.addValue("userId", userId)
				  .addValue("type", ModelType.UserLastActivityType.USER_LAST_ACTIVITY.toString());
		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int updateUserLastActivityByNoActivity(String userId, Timestamp modifiedAt, Timestamp noActivityPushWebAt, Timestamp noActivityPushIosAt, Timestamp noActivityPushAndroidAt) {

		String updateQueryTemp = " modified_at = :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("modifiedAt", modifiedAt);

		if (noActivityPushWebAt != null) {
			updateQueryTemp = updateQueryTemp + ", no_activity_push_web_at = :noActivityPushWebAt ";
			parameters.addValue("noActivityPushWebAt", noActivityPushWebAt);
		}
		if (noActivityPushIosAt != null) {
			updateQueryTemp = updateQueryTemp + ", no_activity_push_ios_at = :noActivityPushIosAt ";
			parameters.addValue("noActivityPushIosAt", noActivityPushIosAt);
		}
		if (noActivityPushAndroidAt != null) {
			updateQueryTemp = updateQueryTemp + ", no_activity_push_android_at = :noActivityPushAndroidAt ";
			parameters.addValue("noActivityPushAndroidAt", noActivityPushAndroidAt);
		}

		String updateQuery = "update " + TB_USER_LAST_ACTIVITY
									+ " set " + updateQueryTemp
								+ " WHERE user_id = :userId and type = :type" ;

		parameters.addValue("userId", userId)
				  .addValue("type", ModelType.UserLastActivityType.USER_LAST_ACTIVITY.toString());
		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int updateUserLastActivityByAppVersion(String userId, Timestamp modifiedAt, Timestamp appUpdatePushIosAt, Timestamp appUpdatePushAndroidAt) {

		String updateQueryTemp = " modified_at = :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("modifiedAt", modifiedAt);

		if (appUpdatePushAndroidAt != null) {
			updateQueryTemp = updateQueryTemp + ", app_update_push_android_at = :appUpdatePushAndroidAt ";
			parameters.addValue("appUpdatePushAndroidAt", appUpdatePushAndroidAt);
		}
		if (appUpdatePushIosAt != null) {
			updateQueryTemp = updateQueryTemp + ", app_update_push_ios_at = :appUpdatePushIosAt ";
			parameters.addValue("appUpdatePushIosAt", appUpdatePushIosAt);
		}

		String updateQuery = "update " + TB_USER_LAST_ACTIVITY
									+ " set " + updateQueryTemp
								+ " WHERE user_id = :userId and type = :type" ;

		parameters.addValue("userId", userId)
				  .addValue("type", ModelType.UserLastActivityType.USER_LAST_ACTIVITY.toString());
		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int updateUserLastActivityByMapVersion(String userId, Timestamp modifiedAt, Timestamp mapUpdatePushIosAt, Timestamp mapUpdatePushAndroidAt) {

		String updateQueryTemp = " modified_at = :modifiedAt ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("modifiedAt", modifiedAt);

		if (mapUpdatePushAndroidAt != null) {
			updateQueryTemp = updateQueryTemp + ", map_update_push_android_at = :mapUpdatePushAndroidAt ";
			parameters.addValue("mapUpdatePushAndroidAt", mapUpdatePushAndroidAt);
		}
		if (mapUpdatePushIosAt != null) {
			updateQueryTemp = updateQueryTemp + ", map_update_push_ios_at = :mapUpdatePushIosAt ";
			parameters.addValue("mapUpdatePushIosAt", mapUpdatePushIosAt);
		}

		String updateQuery = "update " + TB_USER_LAST_ACTIVITY
									+ " set " + updateQueryTemp
								+ " WHERE user_id = :userId and type = :type" ;

		parameters.addValue("userId", userId)
				  .addValue("type", ModelType.UserLastActivityType.USER_LAST_ACTIVITY.toString());
		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

}