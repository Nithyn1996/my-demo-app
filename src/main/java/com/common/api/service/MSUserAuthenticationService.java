package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserAuthenticationService;
import com.common.api.model.UserAuthentication;
import com.common.api.model.field.UserAuthenticationField;
import com.common.api.resultset.UserAuthenticationResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserAuthenticationService extends APIFixedConstant implements UserAuthenticationService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserAuthentication> viewListByCriteria(String companyId, String divisionId, String moduleId, String userId, String id, String deviceUniqueId, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (deviceUniqueId != null && deviceUniqueId.length() > 0) {
			selQuery = selQuery + " AND LOWER(device_unique_id) = LOWER(:deviceUniqueId) ";
			parameters.addValue("deviceUniqueId", deviceUniqueId);
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

			selQuery = "select * from " + TB_USR_AUTHENTICATION + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserAuthenticationResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserAuthentication createUserAuthentication(UserAuthentication userAuthentication) {

		String objectId = Util.getTableOrCollectionObjectId();
		userAuthentication.setId(objectId);

		UserAuthenticationField usrAuthenFieldTemp = userAuthentication.getUserAuthenticationField();
		String usrAuthenField = resultSetConversion.userAuthenticationFieldToString(usrAuthenFieldTemp);

		String insertQuery = "insert into " + TB_USR_AUTHENTICATION + " (id, company_id, division_id, module_id, user_id, name, "
											+ " user_authentication_field, device_unique_id, category, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :divisionId, :moduleId, :userId, :name, "
											+ " :usrAuthenField, :deviceUniqueId, :category, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userAuthentication.getId())
			.addValue("companyId", userAuthentication.getCompanyId())
			.addValue("divisionId", userAuthentication.getDivisionId())
			.addValue("moduleId", userAuthentication.getModuleId())
			.addValue("userId", userAuthentication.getUserId())
			.addValue("name", userAuthentication.getName())
			.addValue("usrAuthenField", usrAuthenField)
			.addValue("deviceUniqueId", userAuthentication.getDeviceUniqueId())
			.addValue("category", userAuthentication.getCategory())
			.addValue("status", userAuthentication.getStatus())
			.addValue("type", userAuthentication.getType())
			.addValue("createdAt", userAuthentication.getCreatedAt())
			.addValue("modifiedAt", userAuthentication.getModifiedAt())
			.addValue("createdBy", userAuthentication.getCreatedBy())
			.addValue("modifiedBy", userAuthentication.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userAuthentication;
		}
		return new UserAuthentication();
	}

	@Override
	public UserAuthentication updateUserAuthentication(UserAuthentication userAuthentication) {

		UserAuthenticationField usrAuthenFieldTemp = userAuthentication.getUserAuthenticationField();
		String usrAuthenField = resultSetConversion.userAuthenticationFieldToString(usrAuthenFieldTemp);

		String insertQuery = "update " + TB_USR_AUTHENTICATION
									+ " set "
										+ " company_id = :companyId, "
										+ " division_id = :divisionId, "
										+ " module_id = :moduleId, "
										+ " user_id = :userId, "
										+ " name = :name, "
										+ " user_authentication_field = :usrAuthenField, "
										+ " category = :category, "
										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", userAuthentication.getId())
				.addValue("companyId", userAuthentication.getCompanyId())
				.addValue("divisionId", userAuthentication.getDivisionId())
				.addValue("moduleId", userAuthentication.getModuleId())
				.addValue("userId", userAuthentication.getUserId())
				.addValue("name", userAuthentication.getName())
				.addValue("usrAuthenField", usrAuthenField)
				.addValue("deviceUniqueId", userAuthentication.getDeviceUniqueId())
				.addValue("category", userAuthentication.getCategory())
				.addValue("status", userAuthentication.getStatus())
				.addValue("type", userAuthentication.getType())
				.addValue("modifiedAt", userAuthentication.getModifiedAt())
				.addValue("modifiedBy", userAuthentication.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userAuthentication;
		}
		return new UserAuthentication();
	}

	@Override
	public int removeDeviceById(String companyId, String userId, String id) {

		String deleteQuery = "update " + TB_USR_AUTHENTICATION
							+ " SET active = :active "
							+ " where id = :id AND "
							+ " company_id = :companyId AND "
							+ " user_id = :userId";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("companyId", companyId)
				.addValue("userId", userId)
				.addValue("id", id)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);

	}

}