package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.QueryMap;
import com.common.api.util.ResultSetConversion;

public class QueryMapResultset extends ResultSetConversion implements RowMapper<QueryMap> {

    @Override
	public QueryMap mapRow(ResultSet rs, int rowNum) throws SQLException {

    	QueryMap queryMap = new QueryMap();
    	try {
    		queryMap.setId(resultSetToString(rs, "id"));
    		queryMap.setCompanyId(resultSetToString(rs, "company_id"));
    		queryMap.setGroupId(resultSetToString(rs, "group_id"));
    		queryMap.setDivisionId(resultSetToString(rs, "division_id"));
    		queryMap.setModuleId(resultSetToString(rs, "module_id"));
     		queryMap.setName(resultSetToString(rs, "name"));
     		queryMap.setProcedureName(resultSetToString(rs, "procedure_name"));
    		queryMap.setQueryType(resultSetToString(rs, "query_type"));
    		queryMap.setCreatedBy(resultSetToString(rs, "created_by"));
    		queryMap.setModifiedBy(resultSetToString(rs, "modified_by"));
    		queryMap.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		queryMap.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return queryMap;
    }

}