package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.SectionService;
import com.common.api.model.Section;
import com.common.api.model.field.SectionField;
import com.common.api.resultset.SectionResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSSectionService extends APIFixedConstant implements SectionService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Section> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String userId, String propertyId, String id, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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

			selQuery = "select * from " + TB_SECTION + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new SectionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public Section createSection(Section section) {

		String objectId = Util.getTableOrCollectionObjectId();
		section.setId(objectId);

		SectionField sectionFieldTemp = section.getSectionField();
		String sectionField = resultSetConversion.sectionFieldToString(sectionFieldTemp);

		String insertQuery = "insert into " + TB_SECTION + " (id, company_id, group_id, division_id, module_id, user_id, property_id, name, "
											+ " section_field, category, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :groupId, :divisionId, :moduleId, :userId, :propertyId, :name, "
											+ " :sectionField, :category, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", section.getId())
			.addValue("companyId", section.getCompanyId())
			.addValue("groupId", section.getGroupId())
			.addValue("divisionId", section.getDivisionId())
			.addValue("moduleId", section.getModuleId())
			.addValue("userId", section.getUserId())
			.addValue("propertyId", section.getPropertyId())
			.addValue("name", section.getName())
			.addValue("sectionField", sectionField)
			.addValue("category", section.getCategory())
			.addValue("status", section.getStatus())
			.addValue("type", section.getType())
			.addValue("createdAt", section.getCreatedAt())
			.addValue("modifiedAt", section.getModifiedAt())
			.addValue("createdBy", section.getCreatedBy())
			.addValue("modifiedBy", section.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return section;
		}
		return new Section();
	}

	@Override
	public Section updateSection(Section section) {

		SectionField sectionFieldTemp = section.getSectionField();
		String sectionField = resultSetConversion.sectionFieldToString(sectionFieldTemp);

		String insertQuery = "update " + TB_SECTION
									+ " set "
										+ " company_id = :companyId, "
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " module_id = :moduleId, "
										+ " user_id = :userId, "
										+ " property_id = :propertyId, "
										+ " name = :name, "
										+ " section_field = :sectionField, "
										+ " category = :category, "
										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", section.getId())
				.addValue("companyId", section.getCompanyId())
				.addValue("groupId", section.getGroupId())
				.addValue("divisionId", section.getDivisionId())
				.addValue("moduleId", section.getModuleId())
				.addValue("userId", section.getUserId())
				.addValue("propertyId", section.getPropertyId())
				.addValue("name", section.getName())
				.addValue("sectionField", sectionField)
				.addValue("category", section.getCategory())
				.addValue("status", section.getStatus())
				.addValue("type", section.getType())
				.addValue("modifiedAt", section.getModifiedAt())
				.addValue("modifiedBy", section.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return section;
		}
		return new Section();
	}

}