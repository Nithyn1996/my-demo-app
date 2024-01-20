package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.DivisionPreferenceService;
import com.common.api.model.DivisionPreference;
import com.common.api.model.field.DivisionPreferenceField;
import com.common.api.resultset.DivisionPreferenceResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSDivisionPreferenceService extends APIFixedConstant implements DivisionPreferenceService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<DivisionPreference> viewListByCriteria(String companyId, String groupId, String divisionId, String id, String name, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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

			selQuery = "select * from " + TB_DIVISION_PREF + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new DivisionPreferenceResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public DivisionPreference createDivisionPreference(DivisionPreference divisionPreference) {

		String objectId = Util.getTableOrCollectionObjectId();
		divisionPreference.setId(objectId);

		DivisionPreferenceField divisionPreferenceFieldTemp = divisionPreference.getDivisionPreferenceField();
		String divisionPreferenceField = resultSetConversion.divisionPreferenceFieldToString(divisionPreferenceFieldTemp);

		String insertQuery = "insert into " + TB_DIVISION_PREF + " (id, company_id, group_id, division_id, "
											+ " name, division_preference_field, status, "
											+ " category, type, created_at, modified_at, "
											+ " created_by, modified_by, start_time, end_time) "
									+ "VALUES (:id, :companyId, :groupId, :divisionId, :name, "
											+ " :divisionPreferenceField, :status, :category, "
											+ " :type, :createdAt, :modifiedAt, :createdBy, :modifiedBy, "
											+ " :startTime, :endTime)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", divisionPreference.getId())
		        .addValue("companyId", divisionPreference.getCompanyId())
		        .addValue("groupId", divisionPreference.getGroupId())
		        .addValue("divisionId", divisionPreference.getDivisionId())
		        .addValue("name", divisionPreference.getName())
		        .addValue("divisionPreferenceField", divisionPreferenceField)
		        .addValue("status", divisionPreference.getStatus())
		        .addValue("category", divisionPreference.getCategory())
		        .addValue("type", divisionPreference.getType())
		        .addValue("active", divisionPreference.getActive())
		        .addValue("createdAt", divisionPreference.getCreatedAt())
		        .addValue("modifiedAt", divisionPreference.getModifiedAt())
		        .addValue("createdBy", divisionPreference.getCreatedBy())
		        .addValue("modifiedBy", divisionPreference.getModifiedBy())
		        .addValue("startTime", divisionPreference.getStartTime())
		        .addValue("endTime", divisionPreference.getEndTime());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if(status > 0) {
			return divisionPreference;
		}
		return new DivisionPreference();
	}

	@Override
	public DivisionPreference updateDivisionPreference(DivisionPreference divisionPreference) {

		DivisionPreferenceField divisionPreferenceFieldTemp = divisionPreference.getDivisionPreferenceField();
		String divisionPreferenceField = resultSetConversion.divisionPreferenceFieldToString(divisionPreferenceFieldTemp);

		String updateQuery = "update " + TB_DIVISION_PREF
									+ " set "
										+ " company_id = :companyId, "
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " name = :name, "
										+ " start_time = :startTime, "
										+ " end_time = :endTime, "
										+ " division_preference_field = :divisionPreferenceField, "
										+ " status = :status, "
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy "
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", divisionPreference.getId())
				.addValue("companyId", divisionPreference.getCompanyId())
				.addValue("groupId", divisionPreference.getGroupId())
				.addValue("divisionId", divisionPreference.getDivisionId())
				.addValue("name", divisionPreference.getName())
				.addValue("startTime", divisionPreference.getStartTime())
				.addValue("endTime", divisionPreference.getEndTime())
				.addValue("divisionPreferenceField", divisionPreferenceField)
				.addValue("status", divisionPreference.getStatus())
				.addValue("modifiedAt", divisionPreference.getModifiedAt())
				.addValue("modifiedBy", divisionPreference.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(updateQuery, parameters);

		if (status > 0) {
			return divisionPreference;
		}
		return new DivisionPreference();
	}

	@Override
	public int removeDivisionPreferenceById(String companyId, String divisionId, String id) {

		String deleteQuery = "update " + TB_DIVISION_PREF
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