package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.util.ResultSetConversion;
import com.fasterxml.jackson.databind.JsonNode;

public class StoredProcedureResultset extends ResultSetConversion implements RowMapper<Map<String, Object>> {

    @Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

		Map<String, Object> result = new HashMap<>();

    	try {

    		ResultSetMetaData rsmd = rs.getMetaData();
    		int columnCount = rsmd.getColumnCount();

    		for (int cc = 1; cc <= columnCount; cc++) {

	    		Object resultValue  = "";
	    		String colName  = rsmd.getColumnName(cc);
	    		try {
	    			String colValue     = resultSetToString(rs, colName);
	    			int colValueType = getResultsetColumnValueType(colValue);
 	    			if (colValueType == 3 || colValueType == 2) {
 	    				JsonNode colValueNode = convertStringToJsonNode(colValue);
 	    				resultValue = colValueNode;
 		    		} else {
 		    			resultValue = colValue;
 		    		}
	    		} catch (Exception errMess) {
	    		}
	    		result.put(colName, resultValue);
     		}
    	} catch (Exception errMess) {
    	}
        return result;
    }

}