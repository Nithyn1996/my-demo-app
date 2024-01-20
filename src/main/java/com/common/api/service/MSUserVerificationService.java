package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserVerificationService;
import com.common.api.model.UserVerification;
import com.common.api.model.field.UserVerificationField;
import com.common.api.resultset.UserVerificationResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserVerificationService extends APIFixedConstant implements UserVerificationService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserVerification> viewListByCriteria(String companyId, String userId, String id, String username, String verificationCode, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (verificationCode != null && verificationCode.length() > 0) {
			selQuery = selQuery + " AND LOWER(verification_code) = LOWER(:verificationCode) ";
			parameters.addValue("verificationCode", verificationCode);
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

			selQuery = "select * from " + TB_USER_VERIFICATION + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserVerificationResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserVerification createUserVerification(UserVerification userVerification) {

		String objectId = Util.getTableOrCollectionObjectId();
		userVerification.setId(objectId);

		UserVerificationField userVerificationFieldTemp = userVerification.getUserVerificationField();
		String userVerificationField = resultSetConversion.userVerificationFieldToString(userVerificationFieldTemp);

		String insertQuery = "insert into " + TB_USER_VERIFICATION + " (id, company_id, user_id, username, verification_code, "
											+ " user_verification_field, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :userId, :username, :verificationCode, "
											+ " :userVerificationField, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userVerification.getId())
			.addValue("companyId", userVerification.getCompanyId())
			.addValue("userId", userVerification.getUserId())
			.addValue("username", userVerification.getUsername())
			.addValue("verificationCode", userVerification.getVerificationCode())
			.addValue("userVerificationField", userVerificationField)
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
		return new UserVerification();
	}

	@Override
	public UserVerification updateUserVerification(UserVerification userVerification) {

		UserVerificationField userVerificationFieldTemp = userVerification.getUserVerificationField();
		String userVerificationField = resultSetConversion.userVerificationFieldToString(userVerificationFieldTemp);

		String insertQuery = "update " + TB_USER_VERIFICATION
									+ " set "
										+ " company_id = :companyId, "
										+ " user_id = :userId, "
										+ " username = :username, "
										+ " verification_code = :verificationCode, "
										+ " user_verification_field = :userVerificationField, "
										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userVerification.getId())
			.addValue("companyId", userVerification.getCompanyId())
			.addValue("userId", userVerification.getUserId())
			.addValue("username", userVerification.getUsername())
			.addValue("verificationCode", userVerification.getVerificationCode())
			.addValue("userVerificationField", userVerificationField)
			.addValue("status", userVerification.getStatus())
			.addValue("type", userVerification.getType())
			.addValue("modifiedAt", userVerification.getModifiedAt())
			.addValue("modifiedBy", userVerification.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userVerification;
		}
		return new UserVerification();
	}

	@Override
	public int removeUserVerificationByCriteria(String userId, String type) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String deleteQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (userId != null && userId.length() > 0) {
			deleteQuery = deleteQuery + " AND LOWER(user_id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (type != null && type.length() > 0) {
			deleteQuery = deleteQuery + " AND LOWER(type) = LOWER(:type) ";
			parameters.addValue("type", type);
		}

		deleteQuery = "delete from " + TB_USER_VERIFICATION + " where " + deleteQuery;
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}