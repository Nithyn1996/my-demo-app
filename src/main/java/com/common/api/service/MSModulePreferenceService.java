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
import com.common.api.datasink.service.ModulePreferenceService;
import com.common.api.model.ModulePreference;
import com.common.api.model.field.ModulePrefAppBootSetting;
import com.common.api.model.field.ModulePreferenceField;
import com.common.api.resultset.ModulePreferenceResultset;
import com.common.api.util.ResultSetConversion;

@Service
public class MSModulePreferenceService extends APIFixedConstant implements ModulePreferenceService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<ModulePreference> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String name, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (name != null && name.length() > 0) {
			selQuery = selQuery + " AND LOWER(name) = LOWER(:name) ";
			parameters.addValue("name", name);
		}
		if (category != null && category.length() > 0) {
			selQuery = selQuery + " AND LOWER(category) = LOWER(:category) ";
			parameters.addValue("category", category);
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

			selQuery = "select * from " + TB_MODULE_PREF + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new ModulePreferenceResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public List<ModulePreference> viewListByCriteria(String companyId, String notificationStatus, String type, String sortBy, String sortOrder, int offset, int limit) {

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
		if (notificationStatus != null && notificationStatus.length() > 0) {
			selQuery = selQuery + " AND LOWER(notification_status) = LOWER(:notificationStatus) ";
			parameters.addValue("notificationStatus", notificationStatus);
		}
		if (type != null && type.length() > 0) {
 			selQuery = selQuery + " AND type = LOWER(:type) ";
			parameters.addValue("type", type);
		}

		if (selQuery.length() > 0) {

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;
			selQuery = selQuery + " OFFSET :offset ROWS";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			selQuery = "select * from " + TB_MODULE_PREF + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new ModulePreferenceResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public ModulePreference updateModulePreference(ModulePreference modulePreference) {

		ModulePreferenceField modulePreferenceFieldTemp = modulePreference.getModulePreferenceField();
		String modulePreferenceField = resultSetConversion.modulePreferenceFieldToString(modulePreferenceFieldTemp);

		ModulePrefAppBootSetting modulePrefAppBootSettingTemp = modulePreference.getModulePrefAppBootSetting();
		String modulePrefAppBootSetting = resultSetConversion.modulePrefAppBootSettingToString(modulePrefAppBootSettingTemp);

		String insertQuery = "update " + TB_MODULE_PREF
									+ " set "
										+ " company_id = :companyId, "
										+ " name = :name, "
										+ " code = :code, "
										+ " minimum_stable_version = :minimumStableVersion, "
										+ " current_stable_version = :currentStableVersion, "
										+ " system_code = :systemCode, "
										+ " module_preference_field = :modulePreferenceField, "
										+ " module_pref_app_boot_setting = :modulePrefAppBootSetting, "
 										+ " notification_status = :notificationStatus, "
										+ " notification_status_success_at = :notificationStatusSuccessAt, "
										+ " category = :category, "
										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", modulePreference.getId())
				.addValue("companyId", modulePreference.getCompanyId())
				.addValue("groupId", modulePreference.getGroupId())
				.addValue("divisionId", modulePreference.getDivisionId())
				.addValue("moduleId", modulePreference.getModuleId())
				.addValue("name", modulePreference.getName())
				.addValue("code", modulePreference.getCode())
				.addValue("minimumStableVersion", modulePreference.getMinimumStableVersion())
				.addValue("currentStableVersion", modulePreference.getCurrentStableVersion())
				.addValue("systemCode", modulePreference.getSystemCode())
				.addValue("modulePreferenceField", modulePreferenceField)
				.addValue("modulePrefAppBootSetting", modulePrefAppBootSetting)
				.addValue("notificationStatus", modulePreference.getNotificationStatus())
				.addValue("notificationStatusSuccessAt", modulePreference.getNotificationStatusSuccessAt())
				.addValue("category", modulePreference.getCategory())
				.addValue("status", modulePreference.getStatus())
				.addValue("type", modulePreference.getType())
				.addValue("modifiedAt", modulePreference.getModifiedAt())
				.addValue("modifiedBy", modulePreference.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return modulePreference;
		}
		return new ModulePreference();
	}

	@Override
	public int updateNotificationStatusPendingByNotificationStatusTime(String type, String notificationStatus, Timestamp modifiedAt) {

		String updateQuery = "update " + TB_MODULE_PREF
									+ " set modified_at = :modifiedAt,"
											+ "	notification_status = :notificationStatus "
								+ " WHERE type = :type and len(type) > 0 and notification_status != :notificationStatus and "
									+ "	(FORMAT(convert(datetime, notification_status_success_at), 'yyyy-MM-dd') != FORMAT(convert(datetime, GETDATE()), 'yyyy-MM-dd')) " ;

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("modifiedAt", modifiedAt);
		parameters.addValue("type", type);
		parameters.addValue("notificationStatus", notificationStatus);
		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

}