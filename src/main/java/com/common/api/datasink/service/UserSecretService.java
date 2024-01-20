package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.UserSecret;

public interface UserSecretService {

	public List<UserSecret> viewListByCriteria(String companyId, String userId, String id, String secretKey, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public UserSecret createUserSecret(UserSecret userSecret);

}
