package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.SearchMap;
import com.common.api.util.ResultSetConversion;

public class SearchMapResultset extends ResultSetConversion implements RowMapper<SearchMap> {

    @Override
	public SearchMap mapRow(ResultSet rs, int rowNum) throws SQLException {

    	SearchMap searchMap = new SearchMap();

    	try {
    		searchMap.setId(resultSetToString(rs, "id"));
    		searchMap.setCompanyId(resultSetToString(rs, "company_id"));
    		searchMap.setGroupId(resultSetToString(rs, "group_id"));
    		searchMap.setDivisionId(resultSetToString(rs, "division_id"));
    		searchMap.setModuleId(resultSetToString(rs, "module_id"));
    		searchMap.setName(resultSetToString(rs, "name"));
    		searchMap.setProcedureName(resultSetToString(rs, "procedure_name"));
    		searchMap.setSearchType(resultSetToString(rs, "search_type"));
    		searchMap.setCreatedBy(resultSetToString(rs, "created_by"));
    		searchMap.setModifiedBy(resultSetToString(rs, "modified_by"));
    		searchMap.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		searchMap.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return searchMap;
    }

}