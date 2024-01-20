package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.CompanyPreference;
import com.common.api.util.ResultSetConversion;

public class CompanyPreferenceResultset extends ResultSetConversion implements RowMapper<CompanyPreference> {

    @Override
	public CompanyPreference mapRow(ResultSet rs, int rowNum) throws SQLException {

    	CompanyPreference companyPreference = new CompanyPreference();
    	try {
    		companyPreference.setId(resultSetToString(rs, "id"));
    		companyPreference.setCompanyId(resultSetToString(rs, "company_id"));
    		companyPreference.setType(resultSetToString(rs, "type"));
    		companyPreference.setName(resultSetToString(rs, "name"));
    		companyPreference.setCategory(resultSetToString(rs, "category"));
    		companyPreference.setStatus(resultSetToString(rs, "status"));
    		companyPreference.setActive(resultSetToString(rs, "active"));
    		companyPreference.setCreatedBy(resultSetToString(rs, "created_by"));
    		companyPreference.setModifiedBy(resultSetToString(rs, "modified_by"));
    		companyPreference.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		companyPreference.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			companyPreference.setCompanyPreferenceField(stringToCompanyPreferenceField(resultSetToString(rs, "company_preference_field")));
    		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return companyPreference;
    }

}