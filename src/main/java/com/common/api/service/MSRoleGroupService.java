package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.RoleGroupService;
import com.common.api.model.RoleGroup;
import com.common.api.resultset.RoleGroupResultset;
import com.common.api.util.ResultSetConversion;

@Service
public class MSRoleGroupService extends APIFixedConstant implements RoleGroupService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<RoleGroup> viewListByCriteria(String companyId, String roleCompanyId, String roleDivisionId, String roleModuleId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (roleCompanyId != null && roleCompanyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(role_company_id) = LOWER(:roleCompanyId) ";
			parameters.addValue("roleCompanyId", companyId);
		}
		if (roleDivisionId != null && roleDivisionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(role_division_id) = LOWER(:roleDivisionId) ";
			parameters.addValue("roleDivisionId", roleDivisionId);
		}
		if (roleModuleId != null && roleModuleId.length() > 0) {
			selQuery = selQuery + " AND LOWER(role_module_id) = LOWER(:roleModuleId) ";
			parameters.addValue("roleModuleId", roleModuleId);
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

			selQuery = "select * from " + TB_ROLE_GROUP + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new RoleGroupResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public List<RoleGroup> viewListByCriteria(String companyId, String divisionId, String moduleId, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String selQuery = " LOWER(rg.active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(rg.company_id) = LOWER(:companyId) ";
			selQuery = selQuery + " AND LOWER(rc.company_id) = LOWER(:companyId) ";
			selQuery = selQuery + " AND LOWER(rd.company_id) = LOWER(:companyId) ";
			selQuery = selQuery + " AND LOWER(rm.company_id) = LOWER(:companyId) ";
			parameters.addValue("companyId", companyId);
		}
		if (divisionId != null && divisionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(rd.division_id) = LOWER(:divisionId) ";
			selQuery = selQuery + " AND LOWER(rm.division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);
		}
		if (moduleId != null && moduleId.length() > 0) {
			selQuery = selQuery + " AND LOWER(rm.module_id) = LOWER(:moduleId) ";
			parameters.addValue("moduleId", moduleId);
		}

		selQuery = "SELECT rg.id as id "
				+ " FROM " + TB_ROLE_GROUP + " AS rg "
						+ " INNER JOIN " + TB_ROLE_COMPANY + " AS rc "
								+ "	ON rc.id = rg.role_company_id "
						+ " INNER JOIN " + TB_ROLE_DIVISION + " AS rd "
								+ "	ON rd.id = rg.role_division_id "
						+ " INNER JOIN " + TB_ROLE_MODULE + " AS rm "
								+ "	ON rm.id = rg.role_module_id "
				+ " WHERE " + selQuery
				+ " ORDER BY rg." + sortBy + " " + sortOrder
				+ " OFFSET :offset ROWS"
				+ " FETCH NEXT :limit ROWS ONLY";

		parameters.addValue("offset", offset);
		parameters.addValue("limit", limit);

		return namedParameterJdbcTemplate.query(selQuery, parameters, new RoleGroupResultset());
	}

}