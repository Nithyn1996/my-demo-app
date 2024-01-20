package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Country;
import com.common.api.util.ResultSetConversion;

public class CountryResultset extends ResultSetConversion implements RowMapper<Country> {

    @Override
	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Country country = new Country();
    	try {
    		country.setId(resultSetToString(rs, "id"));
    		country.setCompanyId(resultSetToString(rs, "company_id"));
    		country.setContinentId(resultSetToString(rs, "continent_id"));

    		country.setName(resultSetToString(rs, "name"));
    		country.setIsoCode2(resultSetToString(rs, "iso_code_2"));
    		country.setIsoCode3(resultSetToString(rs, "iso_code_3"));
    		country.setDialingCode(resultSetToString(rs, "dialing_code"));

    		country.setStatus(resultSetToString(rs, "status"));
    		country.setType(resultSetToString(rs, "type"));
    		country.setActive(resultSetToString(rs, "active"));
    		country.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		country.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return country;
    }


}