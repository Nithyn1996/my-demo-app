package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserPreferenceService;
import com.common.api.model.UserPreference;
import com.common.api.model.field.UserPrefAppBootSetting;
import com.common.api.model.field.UserPreferenceField;
import com.common.api.resultset.UserPreferenceResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserPreferenceService extends APIFixedConstant implements UserPreferenceService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserPreference> viewListByCriteria(String companyId, String divisionId, String moduleId, String userId, String id, String name, String code, List<String> subCategoryList, List<String> categoryList, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (name != null && name.length() > 0) {
			selQuery = selQuery + " AND LOWER(name) = LOWER(:name) ";
			parameters.addValue("name", name);
		}
		if (code != null && code.length() > 0) {
			selQuery = selQuery + " AND LOWER(code) = LOWER(:code) ";
			parameters.addValue("code", code);
		}
		if (subCategoryList != null && subCategoryList.size() > 0) {
			String subCategoryListTemp = resultSetConversion.stringListToCommaAndQuoteString(subCategoryList);
			selQuery = selQuery + " AND sub_category in (" + subCategoryListTemp + ")";
		}
		if (categoryList != null && categoryList.size() > 0) {
			String categoryListTemp = resultSetConversion.stringListToCommaAndQuoteString(categoryList);
			selQuery = selQuery + " AND category in (" + categoryListTemp + ")";
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

			selQuery = "select * from " + TB_USER_PREF + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserPreferenceResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserPreference createUserPreference(UserPreference userPreference) {

		String objectId = Util.getTableOrCollectionObjectId();
		userPreference.setId(objectId);

		UserPreferenceField userPreferenceFieldTemp = userPreference.getUserPreferenceField();
		String userPreferenceField = resultSetConversion.userPreferenceFieldToString(userPreferenceFieldTemp);

		UserPrefAppBootSetting userPrefAppBootSettingTemp = userPreference.getUserPrefAppBootSetting();
		String userPrefAppBootSetting = resultSetConversion.userPrefAppBootSettingToString(userPrefAppBootSettingTemp);

		String insertQuery = "insert into " + TB_USER_PREF + " (id, company_id, division_id, module_id, user_id, name, code, "
											+ " user_preference_field, user_pref_app_boot_setting, start_time, end_time, "
											+ " status, category, sub_category, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :divisionId, :moduleId,  :userId, :name, :code, "
											+ " :userPreferenceField, :userPrefAppBootSetting, :startTime, :endTime, "
											+ " :status, :category, :subCategory, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userPreference.getId())
			.addValue("companyId", userPreference.getCompanyId())
			.addValue("divisionId", userPreference.getDivisionId())
			.addValue("moduleId", userPreference.getModuleId())
			.addValue("userId", userPreference.getUserId())
			.addValue("name", userPreference.getName())
			.addValue("code", userPreference.getCode())
			.addValue("startTime", userPreference.getStartTime())
			.addValue("endTime", userPreference.getEndTime())
			.addValue("userPreferenceField", userPreferenceField)
			.addValue("userPrefAppBootSetting", userPrefAppBootSetting)
			.addValue("subCategory", userPreference.getSubCategory())
			.addValue("category", userPreference.getCategory())
			.addValue("status", userPreference.getStatus())
			.addValue("type", userPreference.getType())
			.addValue("createdAt", userPreference.getCreatedAt())
			.addValue("modifiedAt", userPreference.getModifiedAt())
			.addValue("createdBy", userPreference.getCreatedBy())
			.addValue("modifiedBy", userPreference.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userPreference;
		}
		return new UserPreference();
	}

	@Override
	public UserPreference updateUserPreference(UserPreference userPreference) {

		UserPreferenceField userPreferenceFieldTemp = userPreference.getUserPreferenceField();
		String userPreferenceField = resultSetConversion.userPreferenceFieldToString(userPreferenceFieldTemp);

		UserPrefAppBootSetting userPrefAppBootSettingTemp = userPreference.getUserPrefAppBootSetting();
		String userPrefAppBootSetting = resultSetConversion.userPrefAppBootSettingToString(userPrefAppBootSettingTemp);

		String insertQuery = "update " + TB_USER_PREF
									+ " set "
										+ " company_id = :companyId, "
										+ " division_id = :divisionId, "
										+ " module_id = :moduleId, "
										+ " user_id = :userId, "
										+ " name = :name, "
										+ " code = :code, "
										+ " start_time = :startTime, "
										+ " end_time = :endTime, "
 										+ " user_preference_field = :userPreferenceField, "
 										+ " user_pref_app_boot_setting = :userPrefAppBootSetting, "
										+ " sub_category = :subCategory, "
										+ " category = :category, "
										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", userPreference.getId())
				.addValue("companyId", userPreference.getCompanyId())
				.addValue("divisionId", userPreference.getDivisionId())
				.addValue("moduleId", userPreference.getModuleId())
				.addValue("userId", userPreference.getUserId())
				.addValue("name", userPreference.getName())
				.addValue("code", userPreference.getCode())
				.addValue("startTime", userPreference.getStartTime())
				.addValue("endTime", userPreference.getEndTime())
 				.addValue("userPreferenceField", userPreferenceField)
 				.addValue("userPrefAppBootSetting", userPrefAppBootSetting)
				.addValue("subCategory", userPreference.getSubCategory())
				.addValue("category", userPreference.getCategory())
				.addValue("status", userPreference.getStatus())
				.addValue("type", userPreference.getType())
				.addValue("modifiedAt", userPreference.getModifiedAt())
				.addValue("modifiedBy", userPreference.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userPreference;
		}
		return new UserPreference();
	}


	@Override
	public int removeUserPreferenceById(String companyId, String divisionId, String id) {

		String deleteQuery = "update "+ TB_USER_PREF
									  + " SET active = :active "
									  + " where id = :id AND "
									  + " company_id = :companyId AND "
									  + " division_id = :divisionId";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id)
				.addValue("companyId", companyId)
				.addValue("divisionId", divisionId)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}