package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.UserDistinct;

public interface UserDistinctService {

	public List<UserDistinct> viewListByCriteria(String companyId, String userId, String id, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public UserDistinct createUserDistinct(UserDistinct userDistinct);

	public UserDistinct updateUserDistinct(UserDistinct userDistinct);

	public int updateNextDeviceId(String companyId, String userId, String type);

	public int removeUserDistinctByCriteria(String companyId, String userId, String type);

}
