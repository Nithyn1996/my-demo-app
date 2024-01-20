package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.PortionService;
import com.common.api.model.Portion;
import com.common.api.model.field.PortionField;
import com.common.api.resultset.PortionResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSPortionService extends APIFixedConstant implements PortionService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Portion> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String sectionId, String id, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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

			selQuery = "select * from " + TB_PORTION + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new PortionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public Portion createPortion(Portion portion) {

		String objectId = Util.getTableOrCollectionObjectId();
		portion.setId(objectId);

		PortionField portionFieldTemp = portion.getPortionField();
		String portionField = resultSetConversion.portionFieldToString(portionFieldTemp);

		String insertQuery = "insert into " + TB_PORTION + " (id, company_id, group_id, division_id, module_id, user_id, property_id, section_id, name, "
											+ " portion_field, category, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :groupId, :divisionId, :moduleId, :userId, :propertyId, :sectionId, :name, "
											+ " :portionField, :category, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", portion.getId())
			.addValue("companyId", portion.getCompanyId())
			.addValue("groupId", portion.getGroupId())
			.addValue("divisionId", portion.getDivisionId())
			.addValue("moduleId", portion.getModuleId())
			.addValue("userId", portion.getUserId())
			.addValue("propertyId", portion.getPropertyId())
			.addValue("sectionId", portion.getSectionId())
			.addValue("name", portion.getName())
			.addValue("portionField", portionField)
			.addValue("category", portion.getCategory())
			.addValue("status", portion.getStatus())
			.addValue("type", portion.getType())
			.addValue("createdAt", portion.getCreatedAt())
			.addValue("modifiedAt", portion.getModifiedAt())
			.addValue("createdBy", portion.getCreatedBy())
			.addValue("modifiedBy", portion.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return portion;
		}
		return new Portion();
	}

	@Override
	public Portion updatePortion(Portion portion) {

		PortionField portionFieldTemp = portion.getPortionField();
		String portionField = resultSetConversion.portionFieldToString(portionFieldTemp);

		String insertQuery = "update " + TB_PORTION
									+ " set "
										+ " company_id = :companyId, "
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " module_id = :moduleId, "
										+ " user_id = :userId, "
										+ " property_id = :propertyId, "
										+ " section_id = :sectionId, "
										+ " name = :name, "
										+ " portion_field = :portionField, "
										+ " category = :category, "
										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", portion.getId())
				.addValue("companyId", portion.getCompanyId())
				.addValue("groupId", portion.getGroupId())
				.addValue("divisionId", portion.getDivisionId())
				.addValue("moduleId", portion.getModuleId())
				.addValue("userId", portion.getUserId())
				.addValue("propertyId", portion.getPropertyId())
				.addValue("sectionId", portion.getSectionId())
				.addValue("name", portion.getName())
				.addValue("portionField", portionField)
				.addValue("category", portion.getCategory())
				.addValue("status", portion.getStatus())
				.addValue("type", portion.getType())
				.addValue("modifiedAt", portion.getModifiedAt())
				.addValue("modifiedBy", portion.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return portion;
		}
		return new Portion();
	}

}