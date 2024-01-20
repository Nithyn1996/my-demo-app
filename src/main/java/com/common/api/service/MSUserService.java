package com.common.api.service;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserService;
import com.common.api.model.User;
import com.common.api.model.field.UserField;
import com.common.api.model.type.ModelType;
import com.common.api.resultset.UserResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util; 

@Service
public class MSUserService extends APIFixedConstant implements UserService {

	@Autowired      
	ResultSetConversion resultSetConversion;  
	@Autowired 
	NamedParameterJdbcTemplate namedParameterJdbcTemplate; 
	
	public List<User> viewListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String username, String email, String category, List<String> statusList, List<String> typeList,String sortBy, String sortOrder, int offset, int limit) { 

		offset = (offset <= 0) ? DV_OFFSET : offset;      
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;  
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;     
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);  
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();   
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();  
		
		String selQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);
		
		if (companyId != null && companyId.length() > 0) { 
			selQuery = selQuery + " AND LOWER(company_id) = LOWER(:companyId) "; 
			parameters.addValue("companyId", companyId);   
		} 
		if (groupId != null && groupId.length() > 0) {  
			selQuery = selQuery + " AND LOWER(group_id) = LOWER(:groupId) "; 
			parameters.addValue("groupId", groupId);  
		} 
		if (divisionId != null && divisionId.length() > 0) { 
			selQuery = selQuery + " AND LOWER(division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);  
		}  
		if (moduleId != null && moduleId.length() > 0) {  
			selQuery = selQuery + " AND LOWER(module_id) = LOWER(:moduleId) "; 
			parameters.addValue("moduleId", moduleId);    
		} 
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";  
			parameters.addValue("id", id);  
		}  	
		if (username != null && username.length() > 0) {      
			selQuery = selQuery + " AND LOWER(username) = LOWER(:username)"; 
			parameters.addValue("username", username);   
		}     
		if (email != null && email.length() > 0) {  
			selQuery = selQuery + " AND LOWER(email) = LOWER(:email) "; 
			parameters.addValue("email", email);  
		} 
		if (category != null && category.length() > 0) {   
			selQuery = selQuery + " AND LOWER(category) = LOWER(:category) "; 
			parameters.addValue("category", category);    
		} 
		if (statusList != null && statusList.size() > 0) { 
			String statusListTemp = resultSetConversion.stringListToCommaAndQuoteString(statusList); 
			selQuery = selQuery + " AND status in (" + statusListTemp + ")";    
		}  
		if (typeList != null && typeList.size() > 0) {  
			String typeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList); 
			selQuery = selQuery + " AND type in (" + typeListTemp + ")";    
		}  
		
		if (selQuery.length() > 0) {

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;  
			selQuery = selQuery + " OFFSET :offset ROWS";  
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";   
			     
			parameters.addValue("offset", offset);       
			parameters.addValue("limit", limit);   
			
			selQuery = "select * from " + TB_USER + " where " + selQuery; 
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserResultset()); 
		}
		return new ArrayList<User>();   
	}
	
	public List<User> viewCaseInsensitiveListByCriteria(String companyId, String groupId, String divisionId, String moduleId, String id, String username, String email, String category, List<String> statusList, List<String> typeList,String sortBy, String sortOrder, int offset, int limit) { 

		offset = (offset <= 0) ? DV_OFFSET : offset;      
	 	limit  = (limit <= 0) ? DV_LIMIT : limit;  
		sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;     
		sortBy    = SortByConstant.getEnumValueListDBField(sortBy);  
		sortBy    = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();   
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();  
		
		String selQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);
		
		if (companyId != null && companyId.length() > 0) { 
			selQuery = selQuery + " AND LOWER(company_id) = LOWER(:companyId) "; 
			parameters.addValue("companyId", companyId);   
		} 
		if (groupId != null && groupId.length() > 0) {  
			selQuery = selQuery + " AND LOWER(group_id) = LOWER(:groupId) "; 
			parameters.addValue("groupId", groupId);  
		} 
		if (divisionId != null && divisionId.length() > 0) { 
			selQuery = selQuery + " AND LOWER(division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);  
		}  
		if (moduleId != null && moduleId.length() > 0) {  
			selQuery = selQuery + " AND LOWER(module_id) = LOWER(:moduleId) "; 
			parameters.addValue("moduleId", moduleId);    
		} 
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";  
			parameters.addValue("id", id);  
		}  	
		if (username != null && username.length() > 0) {      
			selQuery = selQuery + " AND username = :username COLLATE SQL_Latin1_General_CP1_CS_AS "; 
			parameters.addValue("username", username);   
		}     
		if (email != null && email.length() > 0) {   
			selQuery = selQuery + " AND LOWER(email) = LOWER(:email) "; 
			parameters.addValue("email", email);  
		} 
		if (category != null && category.length() > 0) {   
			selQuery = selQuery + " AND LOWER(category) = LOWER(:category) "; 
			parameters.addValue("category", category);    
		} 
		if (statusList != null && statusList.size() > 0) { 
			String statusListTemp = resultSetConversion.stringListToCommaAndQuoteString(statusList); 
			selQuery = selQuery + " AND status in (" + statusListTemp + ")";    
		}  
		if (typeList != null && typeList.size() > 0) {  
			String typeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList); 
			selQuery = selQuery + " AND type in (" + typeListTemp + ")";    
		}  
		
		if (selQuery.length() > 0) {

			selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;  
			selQuery = selQuery + " OFFSET :offset ROWS";  
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";   
			     
			parameters.addValue("offset", offset);       
			parameters.addValue("limit", limit);   
			
			selQuery = "select * from " + TB_USER + " where " + selQuery; 
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserResultset()); 
		}
		return new ArrayList<User>();   
	}
	
	@Override
	public User createUser(User user) { 
		 
		String objectId = Util.getTableOrCollectionObjectId();    
		user.setId(objectId);      
		
		UserField userFieldTemp = user.getUserField();    
		String userField = resultSetConversion.userFieldToString(userFieldTemp);  
 
		String insertQuery = "insert into " + TB_USER + " (id, company_id, group_id, division_id, module_id, country_id, time_zone_id, language_id, "
											+ " first_name, last_name, email, gender, "
											+ " username, password, mobile_pin, email_verified, username_verified,"
											+ " user_field, status, username_type, type, category, " 
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :groupId, :divisionId, :moduleId, :countryId, :timeZoneId, :languageId, " 
											+ "	:firstName, :lastName, :email, :gender,  " 
											+ " :username,  :password, :mobilePin, :emailVerified, :usernameVerified, "
											+ " :userField, :status, :usernameType, :type, :category, "  
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";    

		MapSqlParameterSource parameters = new MapSqlParameterSource() 
			.addValue("id", user.getId())  
			.addValue("companyId", user.getCompanyId()) 
			.addValue("groupId", user.getGroupId())  
			.addValue("divisionId", user.getDivisionId())  
			.addValue("moduleId", user.getModuleId()) 
			.addValue("countryId", user.getCountryId())   
			.addValue("timeZoneId", user.getTimeZoneId())  
			.addValue("languageId", user.getLanguageId())         
			.addValue("firstName", user.getFirstName()) 
			.addValue("lastName", user.getLastName())  
			.addValue("email", user.getEmail())        
			.addValue("username", user.getUsername())        
			.addValue("password", user.getPassword())              
			.addValue("gender", user.getGender())      
			.addValue("mobilePin", user.getMobilePin())        
			.addValue("emailVerified", user.getEmailVerified()) 
			.addValue("usernameVerified", user.getUsernameVerified())  
			.addValue("userField", userField)        
			.addValue("usernameType", user.getUsernameType())       
			.addValue("category", user.getCategory())    
			.addValue("status", user.getStatus())    
			.addValue("type", user.getType())   
			.addValue("createdAt", user.getCreatedAt())   
			.addValue("modifiedAt", user.getModifiedAt())   
			.addValue("createdBy", user.getCreatedBy())    
			.addValue("modifiedBy", user.getModifiedBy());      
			
		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);  
			 
		if (status > 0) {    
			return user;  
		} 
		return new User();  
	}
	
	@Override
	public User updateUser(User user) { 
		  
		UserField userFieldTemp = user.getUserField();  
		String userField = resultSetConversion.userFieldToString(userFieldTemp); 
		 
		String insertQuery = "update " + TB_USER
									+ " set " 
										+ " company_id = :companyId, "  
										+ " time_zone_id = :timeZoneId, " 
										+ " language_id = :languageId, " 
										+ " first_name = :firstName, " 
										+ " last_name = :lastName, " 
										+ " email = :email, " 
										+ " username = :username, "
										+ " password = :password, " 
										+ " gender = :gender, " 
										+ " email_verified = :emailVerified, "  
										+ " username_verified = :usernameVerified, "     
										+ " user_field = :userField, " 
										+ " device_auto_start_sub_mode = :deviceAutoStartSubMode, " 
										+ " category = :category, " 
										+ " status = :status, "  
										+ " type = :type," 
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;     

		MapSqlParameterSource parameters = new MapSqlParameterSource() 
			.addValue("id", user.getId())   
			.addValue("companyId", user.getCompanyId()) 
			.addValue("timeZoneId", user.getTimeZoneId())  
			.addValue("languageId", user.getLanguageId())        
			.addValue("firstName", user.getFirstName())    
			.addValue("lastName", user.getLastName()) 
			.addValue("email", user.getEmail())     
			.addValue("username", user.getUsername())  
			.addValue("password", user.getPassword())    
			.addValue("gender", user.getGender())    
			.addValue("emailVerified", user.getEmailVerified())    
			.addValue("usernameVerified", user.getUsernameVerified())     
			.addValue("userField", userField)  
			.addValue("deviceAutoStartSubMode", user.getDeviceAutoStartSubMode())
			.addValue("category", user.getCategory())
			.addValue("status", user.getStatus())   
			.addValue("type", user.getType())     
			.addValue("modifiedAt", user.getModifiedAt())    
			.addValue("modifiedBy", user.getModifiedBy());    
		
		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);   
			
		if (status > 0) {  
			return user;   
		} 
		return new User();  
	}
    
	@Override
	public User updateUserPassword(User user) {
		
		String selQuery = "update " + TB_USER   
								+ " set password = :password "  
						+ " where id = :id "; 

		MapSqlParameterSource parameters = new MapSqlParameterSource();  
		parameters.addValue("password", user.getPassword());         
		parameters.addValue("id", user.getId());  
		
		int result = namedParameterJdbcTemplate.update(selQuery, parameters); 
		
		if (result > 0) {
			return user;
		}
		return new User();
	} 
	
	@Override
	public User updateUserProfilePicture(User user) {
		
		String selQuery = "update " + TB_USER   
								+ " set profile_picture_path = :profilePicturePath " 
						+ " where id = :id ";  

		MapSqlParameterSource parameters = new MapSqlParameterSource();    
		parameters.addValue("profilePicturePath", user.getProfilePicturePath());     
		parameters.addValue("id", user.getId());  
		
		int result = namedParameterJdbcTemplate.update(selQuery, parameters); 
		
		if (result > 0) {
			return user;
		}
		return new User();
	} 
	
	@Override
	public User updateUserMobilePin(User user) {
		
		String selQuery = "update " + TB_USER   
								+ " set mobile_pin = :mobilePin "  
						+ " where id = :id "; 

		MapSqlParameterSource parameters = new MapSqlParameterSource();   
		parameters.addValue("mobilePin", user.getMobilePin());      
		parameters.addValue("id", user.getId());   
		
		int result = namedParameterJdbcTemplate.update(selQuery, parameters); 
		
		if (result > 0) {
			return user;
		}
		return new User();
	} 

	@Override
	public int updateUserNextActivity(String userId, String nextActivity) {
		
		String selQuery = "update " + TB_USER   
								+ " set next_activity = :nextActivity "  
						+ " where id = :id "; 

		MapSqlParameterSource parameters = new MapSqlParameterSource();   
		parameters.addValue("nextActivity", nextActivity);      
		parameters.addValue("id", userId);   
		
		return namedParameterJdbcTemplate.update(selQuery, parameters); 
	
	}
	
	@Override
	public int updateUserForPasswordChangeCondition(Timestamp modifiedAt, int noOfDays, String nextActivity) {
		
		String selQuery = "update " + TB_USER    
								+ " set next_activity = :nextActivity, "  
										+ " modified_at = :modifiedAt "
						+ " where active = 'ACTIVE' and category = :category and "
								+ " id in (select user_id "
										+ "	from tb_user_last_activity "
										+ "	where DATEDIFF(DAY, password_changed_at, convert(datetime, GETDATE())) > :noOfDays) "; 

		MapSqlParameterSource parameters = new MapSqlParameterSource();   
		parameters.addValue("noOfDays", noOfDays);    
		parameters.addValue("modifiedAt", modifiedAt);  
		parameters.addValue("nextActivity", nextActivity);  
		parameters.addValue("category", ModelType.UserType.USER.toString());  
		
		return namedParameterJdbcTemplate.update(selQuery, parameters); 
	
	}
	
}