package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserSecretService;
import com.common.api.model.UserSecret;
import com.common.api.resultset.UserSecretResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserSecretService extends APIFixedConstant implements UserSecretService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserSecret> viewListByCriteria(String companyId, String userId, String id, String secretKey, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (secretKey != null && secretKey.length() > 0) {
			selQuery = selQuery + " AND LOWER(secret_key) = LOWER(:secretKey) ";
			parameters.addValue("secretKey", secretKey);
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

			selQuery = "select * from " + TB_USER_SECRET + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserSecretResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserSecret createUserSecret(UserSecret userSecret) {

		String objectId = Util.getTableOrCollectionObjectId();
		userSecret.setId(objectId);

		String insertQuery = "insert into " + TB_USER_SECRET + " (id, company_id, division_id, user_id, secret_key, "
											+ " status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :divisionId, :userId, :secretKey, "
											+ " :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userSecret.getId())
			.addValue("companyId", userSecret.getCompanyId())
			.addValue("divisionId", userSecret.getDivisionId())
			.addValue("userId", userSecret.getUserId())
			.addValue("secretKey", userSecret.getSecretKey())
			.addValue("status", userSecret.getStatus())
			.addValue("type", userSecret.getType())
			.addValue("createdAt", userSecret.getCreatedAt())
			.addValue("modifiedAt", userSecret.getModifiedAt())
			.addValue("createdBy", userSecret.getCreatedBy())
			.addValue("modifiedBy", userSecret.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userSecret;
		}
		return new UserSecret();
	}

}