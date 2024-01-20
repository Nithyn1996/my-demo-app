package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.UserVerification;

public interface UserVerificationService {

	public List<UserVerification> viewListByCriteria(String companyId, String userId, String id, String username, String verificationCode, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public UserVerification createUserVerification(UserVerification userVerification);

	public UserVerification updateUserVerification(UserVerification userVerification);

	public int removeUserVerificationByCriteria(String userId, String type);

}
