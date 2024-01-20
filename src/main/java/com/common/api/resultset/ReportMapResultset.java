package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.ReportMap;
import com.common.api.util.ResultSetConversion;

public class ReportMapResultset extends ResultSetConversion implements RowMapper<ReportMap> {

    @Override
	public ReportMap mapRow(ResultSet rs, int rowNum) throws SQLException {

    	ReportMap reportMap = new ReportMap();
    	try {
    		reportMap.setId(resultSetToString(rs, "id"));
    		reportMap.setCompanyId(resultSetToString(rs, "company_id"));
    		reportMap.setGroupId(resultSetToString(rs, "group_id"));
    		reportMap.setDivisionId(resultSetToString(rs, "division_id"));
    		reportMap.setModuleId(resultSetToString(rs, "module_id"));
     		reportMap.setName(resultSetToString(rs, "name"));
     		reportMap.setProcedureName(resultSetToString(rs, "procedure_name"));
    		reportMap.setReportType(resultSetToString(rs, "report_type"));
    		reportMap.setCreatedBy(resultSetToString(rs, "created_by"));
    		reportMap.setModifiedBy(resultSetToString(rs, "modified_by"));
    		reportMap.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		reportMap.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return reportMap;
    }

}