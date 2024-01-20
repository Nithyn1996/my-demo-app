package com.common.api.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.common.api.model.RoleModule;
import com.common.api.util.ResultSetConversion;  

public class RoleModuleResultset extends ResultSetConversion implements RowMapper<RoleModule> {
	 
    public RoleModule mapRow(ResultSet rs, int rowNum) throws SQLException { 
    	
    	RoleModule roleModule = new RoleModule();   
    	
    	try { 
    		
    		roleModule.setId(resultSetToString(rs, "id"));
    		roleModule.setCompanyId(resultSetToString(rs, "company_id")); 
    		roleModule.setDivisionId(resultSetToString(rs, "division_id")); 
    		roleModule.setModuleId(resultSetToString(rs, "module_id")); 
    		roleModule.setRoleModuleName(resultSetToString(rs, "role_module_name"));  
    		roleModule.setStatus(resultSetToString(rs, "status")); 
    		roleModule.setType(resultSetToString(rs, "type"));  
    		roleModule.setActive(resultSetToString(rs, "active"));       
    		roleModule.setCreatedBy(resultSetToString(rs, "created_by"));  
    		roleModule.setModifiedBy(resultSetToString(rs, "modified_by"));  
    		roleModule.setCreatedAt(resultSetToTimestamp(rs, "created_at"));    
    		roleModule.setModifiedAt(resultSetToTimestamp(rs, "modified_at"));  
    		
    		try { 
    			System.out.println("resultSetToString 1: " + resultSetToString(rs, "role_module_privileges"));
    			System.out.println("resultSetToString 2: " + objectStringToStringList(resultSetToString(rs, "role_module_privileges")));
 
        		roleModule.setRoleModulePrivileges(objectStringToStringList(resultSetToString(rs, "role_module_privileges"))); 
	    	} catch (Exception errMess) { } 
    		 
    	} catch (Exception errMess) {
    	}
        return roleModule;
    }
     

}