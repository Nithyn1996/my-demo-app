package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.DashboardMap;
import com.common.api.util.ResultSetConversion;

public class DashboardMapResultset extends ResultSetConversion implements RowMapper<DashboardMap> {

    @Override
	public DashboardMap mapRow(ResultSet rs, int rowNum) throws SQLException {

    	DashboardMap reportMap = new DashboardMap();
    	try {
    		reportMap.setId(resultSetToString(rs, "id"));
    		reportMap.setCompanyId(resultSetToString(rs, "company_id"));
    		reportMap.setGroupId(resultSetToString(rs, "group_id"));
    		reportMap.setDivisionId(resultSetToString(rs, "division_id"));
    		reportMap.setModuleId(resultSetToString(rs, "module_id"));
     		reportMap.setName(resultSetToString(rs, "name"));
     		reportMap.setProcedureName(resultSetToString(rs, "procedure_name"));
    		reportMap.setDashboardType(resultSetToString(rs, "dashboard_type"));
    		reportMap.setCreatedBy(resultSetToString(rs, "created_by"));
    		reportMap.setModifiedBy(resultSetToString(rs, "modified_by"));
    		reportMap.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		reportMap.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));
    	} catch (Exception errMess) {
    	}
        return reportMap;
    }

}