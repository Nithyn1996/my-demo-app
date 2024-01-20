package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.MetadataService;
import com.common.api.model.Metadata;
import com.common.api.resultset.MetadataResultset;

@Service
public class MSMetadataService extends APIFixedConstant implements MetadataService {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
	public List<Metadata> getAvailableMetadata(String companyId) {
		return viewListByCriteria(companyId, "", "", "", "", "", "", 0, 0);
    }

    @Override
	public List<Metadata> viewCardinalityListByCriteria(String companyId, String fieldValue) {
		return viewListByCriteria(companyId, "", "", "", fieldValue, "", "", 0, 0);
    }

	@Override
	public List<Metadata> viewListByCriteria(String companyId, String id, String moduleName, String fieldName, String fieldValue, String sortBy, String sortOrder, int offset, int limit) {

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
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (moduleName != null && moduleName.length() > 0) {
			selQuery = selQuery + " AND LOWER(module_name) = LOWER(:moduleName)";
			parameters.addValue("moduleName", moduleName);
		}
		if (fieldName != null && fieldName.length() > 0) {
			selQuery = selQuery + " AND LOWER(field_name) = LOWER(:fieldName) ";
			parameters.addValue("fieldName", fieldName);
		}
		if (fieldValue != null && fieldValue.length() > 0) {
			selQuery = selQuery + " AND LOWER(field_value) = LOWER(:fieldValue) ";
			parameters.addValue("fieldValue", fieldValue);
		}

		if (selQuery.length() > 0) {

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;
			selQuery = selQuery + " OFFSET :offset ROWS";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			selQuery = "select * from " + TB_METADATA + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new MetadataResultset());
		}
		return new ArrayList<>();
	}

}