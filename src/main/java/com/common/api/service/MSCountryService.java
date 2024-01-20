package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.CountryService;
import com.common.api.model.Country;
import com.common.api.resultset.CountryResultset;
import com.common.api.util.ResultSetConversion;

@Service
public class MSCountryService extends APIFixedConstant implements CountryService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Country> viewListByCriteria(String companyId, String continentId, String id, String name, String isoCode2, String isoCode3, String dialingCode, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

		offset = (offset <= 0) ? DV_OFFSET : offset;
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String selQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(company_id) = LOWER(:companyId)";
			parameters.addValue("companyId", companyId);
		}
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (continentId != null && continentId.length() > 0) {
			selQuery = selQuery + " AND LOWER(continent_id) = LOWER(:continentId) ";
			parameters.addValue("continentId", continentId);
		}
		if (name != null && name.length() > 0) {
			selQuery = selQuery + " AND LOWER(name) = LOWER(:name) ";
			parameters.addValue("name", name);
		}
		if (isoCode2 != null && isoCode2.length() > 0) {
			selQuery = selQuery + " AND LOWER(iso_code_2) = LOWER(:isoCode2)";
			parameters.addValue("isoCode2", isoCode2);
		}
		if (isoCode3 != null && isoCode3.length() > 0) {
			selQuery = selQuery + " AND LOWER(iso_code_3) = LOWER(:isoCode3)";
			parameters.addValue("isoCode3", isoCode3);
		}
		if (dialingCode != null && dialingCode.length() > 0) {
			selQuery = selQuery + " AND LOWER(dialing_code) = LOWER(:dialingCode)";
			parameters.addValue("dialingCode", dialingCode);
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

			selQuery = "select * from " + TB_COUNTRY + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new CountryResultset());
		}
		return new ArrayList<>();
	}

}