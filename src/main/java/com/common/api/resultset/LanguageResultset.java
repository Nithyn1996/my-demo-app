package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Language;
import com.common.api.util.ResultSetConversion;

public class LanguageResultset extends ResultSetConversion implements RowMapper<Language> {

    @Override
	public Language mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Language language = new Language();
    	try {
    		language.setId(resultSetToString(rs, "id"));
    		language.setCompanyId(resultSetToString(rs, "company_id"));
    		language.setName(resultSetToString(rs, "name"));
    		language.setCode(resultSetToString(rs, "code"));

    		language.setStatus(resultSetToString(rs, "status"));
    		language.setType(resultSetToString(rs, "type"));
    		language.setActive(resultSetToString(rs, "active"));
    		language.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		language.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}
        return language;
    }


}