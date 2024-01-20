package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Metadata;
import com.common.api.util.ResultSetConversion;

public class MetadataResultset extends ResultSetConversion implements RowMapper<Metadata> {

    @Override
	public Metadata mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Metadata metadata = new Metadata();

    	try {

    		metadata.setId(resultSetToString(rs, "id"));
    		metadata.setModuleName(resultSetToString(rs, "module_name"));
    		metadata.setFieldName(resultSetToString(rs, "field_name"));
    		metadata.setFieldValue(resultSetToString(rs, "field_value"));
    		metadata.setDisplayValue(resultSetToString(rs, "display_value"));

    		metadata.setCardinality(resultSetToInteger(rs, "cardinality"));
    		metadata.setSessionTypeCreate(resultSetToString(rs, "session_type_create"));
    		metadata.setSessionTypeRead(resultSetToString(rs, "session_type_read"));
    		metadata.setSessionTypeUpdate(resultSetToString(rs, "session_type_update"));
    		metadata.setSessionTypeDelete(resultSetToString(rs, "session_type_delete"));

    		metadata.setSessionTypeCreateAll(resultSetToString(rs, "session_type_create_all"));
    		metadata.setSessionTypeReadAll(resultSetToString(rs, "session_type_read_all"));
    		metadata.setSessionTypeUpdateAll(resultSetToString(rs, "session_type_update_all"));
    		metadata.setSessionTypeDeleteAll(resultSetToString(rs, "session_type_delete_all"));

    		metadata.setStatus(resultSetToString(rs, "status"));
    		metadata.setActive(resultSetToString(rs, "active"));
    		metadata.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		metadata.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    	} catch (Exception errMess) {
    	}

        return metadata;
    }

}