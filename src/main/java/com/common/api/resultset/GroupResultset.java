package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.Group;
import com.common.api.util.ResultSetConversion;

public class GroupResultset extends ResultSetConversion implements RowMapper<Group> {

    @Override
	public Group mapRow(ResultSet rs, int rowNum) throws SQLException {

    	Group group = new Group();
    	try {
    		group.setId(resultSetToString(rs, "id"));
    		group.setCompanyId(resultSetToString(rs, "company_id"));
    		group.setCode(resultSetToString(rs, "code"));
    		group.setType(resultSetToString(rs, "type"));
    		group.setName(resultSetToString(rs, "name"));
    		group.setCategory(resultSetToString(rs, "category"));
    		group.setStatus(resultSetToString(rs, "status"));
    		group.setActive(resultSetToString(rs, "active"));
    		group.setCreatedBy(resultSetToString(rs, "created_by"));
    		group.setModifiedBy(resultSetToString(rs, "modified_by"));
    		group.setCreatedAt(resultSetToTimestamp(rs, "created_at"));
    		group.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));

    		try {
    			group.setGroupField(stringToGroupField(resultSetToString(rs, "group_field")));
      		} catch (Exception errMess) { }

    	} catch (Exception errMess) {
    	}
        return group;
    }


}