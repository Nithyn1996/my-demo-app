package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Company;
import com.common.api.util.ResultSetConversion;

public class CompanyResultset extends ResultSetConversion implements RowMapper<Company> {

    @Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Company company = new Company();
    	try {
    		company.setId(resultSetToString(rs, "id"));
    		company.setCode(resultSetToString(rs, "code"));
    		company.setType(resultSetToString(rs, "type"));
    		company.setName(resultSetToString(rs, "name"));
    		company.setCategory(resultSetToString(rs, "category"));
    		company.setStatus(resultSetToString(rs, "status"));
    		company.setActive(resultSetToString(rs, "active"));
    		company.setCreatedBy(resultSetToString(rs, "created_by"));
    		company.setModifiedBy(resultSetToString(rs, "modified_by"));
    		company.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		company.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			company.setCompanyField(stringToCompanyField(resultSetToString(rs, "company_field")));
      		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return company;
    }


}