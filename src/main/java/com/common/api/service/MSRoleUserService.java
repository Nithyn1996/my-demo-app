package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.RoleUserService;
import com.common.api.model.RoleUser;
import com.common.api.resultset.RoleUserResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSRoleUserService extends APIFixedConstant implements RoleUserService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<RoleUser> viewListByCriteria(String companyId, String rloleGroupId, String userId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (rloleGroupId != null && rloleGroupId.length() > 0) {
			selQuery = selQuery + " AND LOWER(role_group_id) = LOWER(:rloleGroupId) ";
			parameters.addValue("rloleGroupId", rloleGroupId);
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

			selQuery = "select * from " + TB_ROLE_USER + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new RoleUserResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public RoleUser createRoleUser(RoleUser roleUser) {

		String objectId = Util.getTableOrCollectionObjectId();
		roleUser.setId(objectId);

		String insertQuery = "insert into " + TB_ROLE_USER + " (id, company_id, role_group_id, user_id, "
											+ " priority, status, type, "
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :roleGroupId, :userId, "
											+ " :priority, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", roleUser.getId())
			.addValue("companyId", roleUser.getCompanyId())
			.addValue("roleGroupId", roleUser.getRoleGroupId())
			.addValue("userId", roleUser.getUserId())
			.addValue("priority", roleUser.getPriority())
			.addValue("status", roleUser.getStatus())
			.addValue("type", roleUser.getType())
			.addValue("createdAt", roleUser.getCreatedAt())
			.addValue("modifiedAt", roleUser.getModifiedAt())
			.addValue("createdBy", roleUser.getCreatedBy())
			.addValue("modifiedBy", roleUser.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return roleUser;
		}
		return new RoleUser();
	}

}