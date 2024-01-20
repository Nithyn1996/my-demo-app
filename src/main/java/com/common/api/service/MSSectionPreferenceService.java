package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.SectionPreferenceService;
import com.common.api.model.SectionPreference;
import com.common.api.model.field.SectionPrefVehicleInspection;
import com.common.api.resultset.SectionPreferenceResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSSectionPreferenceService extends APIFixedConstant implements SectionPreferenceService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<SectionPreference> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String id, String name, String code, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (propertyId != null && propertyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(property_id) = LOWER(:propertyId) ";
			parameters.addValue("propertyId", propertyId);
		}
		if (sectionId != null && sectionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(section_id) = LOWER(:sectionId) ";
			parameters.addValue("sectionId", sectionId);
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

			selQuery = "select * from " + TB_SECTION_PREF + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new SectionPreferenceResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public SectionPreference createSectionPreference(SectionPreference sectionPreference) {

		String objectId = Util.getTableOrCollectionObjectId();
		sectionPreference.setId(objectId);

		SectionPrefVehicleInspection sectionPrefVehicleInspectionTemp = sectionPreference.getSectionPrefVehicleInspection();
		String sectionPrefVehicleInspection = resultSetConversion.sectionPrefAppBootSettingToString(sectionPrefVehicleInspectionTemp);

		String insertQuery = "insert into " + TB_SECTION_PREF + " (id, company_id, group_id, division_id, module_id, user_id, property_id, section_id, "
											+ " name, code, section_pref_vehicle_inspection, "
											+ " remarks, comments, status, category, type, "
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :groupId, :divisionId, :moduleId, :userId, :propertyId, :sectionId,"
											+ "	:name, :code, :sectionPrefVehicleInspection, "
											+ " :remarks, :comments, :status, :category, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", sectionPreference.getId())
			.addValue("companyId", sectionPreference.getCompanyId())
			.addValue("groupId", sectionPreference.getGroupId())
			.addValue("divisionId", sectionPreference.getDivisionId())
			.addValue("moduleId", sectionPreference.getModuleId())
			.addValue("userId", sectionPreference.getUserId())
			.addValue("propertyId", sectionPreference.getPropertyId())
			.addValue("sectionId", sectionPreference.getSectionId())
			.addValue("name", sectionPreference.getName())
			.addValue("code", sectionPreference.getCode())
			.addValue("sectionPrefVehicleInspection", sectionPrefVehicleInspection)
			.addValue("category", sectionPreference.getCategory())
			.addValue("remarks", sectionPreference.getRemarks())
			.addValue("comments", sectionPreference.getComments())
			.addValue("status", sectionPreference.getStatus())
			.addValue("type", sectionPreference.getType())
			.addValue("createdAt", sectionPreference.getCreatedAt())
			.addValue("modifiedAt", sectionPreference.getModifiedAt())
			.addValue("createdBy", sectionPreference.getCreatedBy())
			.addValue("modifiedBy", sectionPreference.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return sectionPreference;
		}
		return new SectionPreference();
	}

	@Override
	public SectionPreference updateSectionPreference(SectionPreference sectionPreference) {

		SectionPrefVehicleInspection sectionPrefVehicleInspectionTemp = sectionPreference.getSectionPrefVehicleInspection();
		String sectionPrefVehicleInspection = resultSetConversion.sectionPrefAppBootSettingToString(sectionPrefVehicleInspectionTemp);

		String updateQuery = "update " + TB_SECTION_PREF
									+ " set "
										+ " company_id = :companyId, "
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " module_id = :moduleId, "
										+ " user_id = :userId, "
										+ " property_id = :propertyId, "
										+ " section_id = :sectionId, "
										+ " name = :name, "
										+ " code = :code, "
										+ " remarks = :remarks, "
										+ " comments = :comments, "
 										+ " section_pref_vehicle_inspection = :sectionPrefVehicleInspection, "
										+ " category = :category, "
										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", sectionPreference.getId())
				.addValue("companyId", sectionPreference.getCompanyId())
				.addValue("groupId", sectionPreference.getGroupId())
				.addValue("divisionId", sectionPreference.getDivisionId())
				.addValue("moduleId", sectionPreference.getModuleId())
				.addValue("userId", sectionPreference.getUserId())
				.addValue("propertyId", sectionPreference.getPropertyId())
				.addValue("sectionId", sectionPreference.getSectionId())
				.addValue("name", sectionPreference.getName())
				.addValue("code", sectionPreference.getCode())
 				.addValue("sectionPrefVehicleInspection", sectionPrefVehicleInspection)
 				.addValue("remarks", sectionPreference.getRemarks())
 				.addValue("comments", sectionPreference.getComments())
				.addValue("category", sectionPreference.getCategory())
				.addValue("status", sectionPreference.getStatus())
				.addValue("type", sectionPreference.getType())
				.addValue("modifiedAt", sectionPreference.getModifiedAt())
				.addValue("modifiedBy", sectionPreference.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(updateQuery, parameters);

		if (status > 0) {
			return sectionPreference;
		}
		return new SectionPreference();
	}

}