package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserFeedbackService;
import com.common.api.model.UserFeedback;
import com.common.api.model.field.UserFeedbackField;
import com.common.api.resultset.UserFeedbackResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserFeedbackService extends APIFixedConstant implements UserFeedbackService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserFeedback> viewListByCriteria(String companyId, String divisionId, String moduleId, String userId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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

			selQuery = "select * from " + TB_USER_FEEDBACK + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserFeedbackResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserFeedback createUserFeedback(UserFeedback userFeedback) {

		String objectId = Util.getTableOrCollectionObjectId();
		userFeedback.setId(objectId);

		UserFeedbackField userFeedbackFieldTemp = userFeedback.getUserFeedbackField();
		String userFeedbackField = resultSetConversion.userFeedbackFieldToString(userFeedbackFieldTemp);

		String insertQuery = "insert into " + TB_USER_FEEDBACK + " (id, company_id, division_id, module_id, user_id, description, "
											+ " user_feedback_field, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :divisionId, :moduleId, :userId, :description, "
											+ " :userFeedbackField, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userFeedback.getId())
			.addValue("companyId", userFeedback.getCompanyId())
			.addValue("divisionId", userFeedback.getDivisionId())
			.addValue("moduleId", userFeedback.getModuleId())
			.addValue("userId", userFeedback.getUserId())
			.addValue("description", userFeedback.getDescription())
			.addValue("userFeedbackField", userFeedbackField)
			.addValue("status", userFeedback.getStatus())
			.addValue("type", userFeedback.getType())
			.addValue("createdAt", userFeedback.getCreatedAt())
			.addValue("modifiedAt", userFeedback.getModifiedAt())
			.addValue("createdBy", userFeedback.getCreatedBy())
			.addValue("modifiedBy", userFeedback.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userFeedback;
		}
		return new UserFeedback();
	}

}