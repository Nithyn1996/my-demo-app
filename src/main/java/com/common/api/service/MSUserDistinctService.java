package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserDistinctService;
import com.common.api.model.UserDistinct;
import com.common.api.resultset.UserDistinctResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserDistinctService extends APIFixedConstant implements UserDistinctService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserDistinct> viewListByCriteria(String companyId, String userId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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

			selQuery = "select * from " + TB_USER_DISTINCT + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserDistinctResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserDistinct createUserDistinct(UserDistinct userVerification) {

		String objectId = Util.getTableOrCollectionObjectId();
		userVerification.setId(objectId);

		String insertQuery = "insert into " + TB_USER_DISTINCT + " (id, company_id, user_id, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :userId, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userVerification.getId())
			.addValue("companyId", userVerification.getCompanyId())
			.addValue("userId", userVerification.getUserId())
			.addValue("nextDeviceId", userVerification.getNextDeviceId())
			.addValue("status", userVerification.getStatus())
			.addValue("type", userVerification.getType())
			.addValue("createdAt", userVerification.getCreatedAt())
			.addValue("modifiedAt", userVerification.getModifiedAt())
			.addValue("createdBy", userVerification.getCreatedBy())
			.addValue("modifiedBy", userVerification.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userVerification;
		}
		return new UserDistinct();
	}

	@Override
	public UserDistinct updateUserDistinct(UserDistinct userVerification) {

		String insertQuery = "update " + TB_USER_DISTINCT
									+ " set "
										+ " company_id = :companyId, "
										+ " user_id = :userId, "
										+ " next_device_id = :nextDeviceId, "
										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userVerification.getId())
			.addValue("companyId", userVerification.getCompanyId())
			.addValue("userId", userVerification.getUserId())
			.addValue("nextDeviceId", userVerification.getNextDeviceId())
			.addValue("status", userVerification.getStatus())
			.addValue("type", userVerification.getType())
			.addValue("modifiedAt", userVerification.getModifiedAt())
			.addValue("modifiedBy", userVerification.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userVerification;
		}
		return new UserDistinct();
	}

	@Override
	public int updateNextDeviceId(String companyId, String userId, String type) {

		String insertQuery = "update " + TB_USER_DISTINCT
									+ " set next_device_id = (next_device_id + 1) "
								+ " WHERE company_id = :companyId and "
									+ " user_id = :userId and "
									+ " type = :type" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
 			.addValue("companyId", companyId)
			.addValue("userId", userId)
			.addValue("type", type);

		return namedParameterJdbcTemplate.update(insertQuery, parameters);
	}

	@Override
	public int removeUserDistinctByCriteria(String companyId, String userId, String type) {

		String deleteQuery = "update " + TB_USER_DISTINCT
								+ " SET active = :active "
								+ " where id = :id AND "
								+ " company_id = :companyId ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("companyId", companyId)
				.addValue("userId", userId)
				.addValue("type", type)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}