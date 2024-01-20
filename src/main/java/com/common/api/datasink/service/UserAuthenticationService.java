package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.UserAuthentication;

public interface UserAuthenticationService {

	public List<UserAuthentication> viewListByCriteria(String companyId, String divisionId, String moduleId, String userId, String id, String deviceUniqueId, String category, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public UserAuthentication createUserAuthentication(UserAuthentication userAuthentication);

	public UserAuthentication updateUserAuthentication(UserAuthentication userAuthentication);

	public int removeDeviceById(String companyId, String userId, String id);

}
