package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Continent;
import com.common.api.util.ResultSetConversion;

public class ContinentResultset extends ResultSetConversion implements RowMapper<Continent> {

    @Override
	public Continent mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Continent continent = new Continent();
    	try {
    		continent.setId(resultSetToString(rs, "id"));
    		continent.setCompanyId(resultSetToString(rs, "company_id"));
    		continent.setName(resultSetToString(rs, "name"));
    		continent.setIsoCode2(resultSetToString(rs, "iso_code_2"));

    		continent.setStatus(resultSetToString(rs, "status"));
    		continent.setType(resultSetToString(rs, "type"));
    		continent.setActive(resultSetToString(rs, "active"));
    		continent.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		continent.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return continent;
    }


}