package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Subscription;

public interface SubscriptionService {

	public List<Subscription> viewListByCriteria(String companyId, String groupId, String divisionId, String id, String name, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public List<Subscription> viewListByCriteria(String companyId, String groupId, String divisionId, String id, List<String> typeList);

	public Subscription createSubscription(Subscription subscription);

	public Subscription updateSubscription(Subscription subscription);

	public int subscriptionCountIncrementAndroid(String companyId, String divisionId,String id,  int countValue, int orderCount);

	public int subscriptionCountIncrementIos(String companyId, String divisionId,String id,  int countValue, int orderCount);

	public int subscriptionCountDecrementAndroid(String companyId, String divisionId,String id,  int countValue);

	public int subscriptionCountDecrementIos(String companyId, String divisionId,String id,  int countValue);

	public int removeSubscriptionById(String companyId, String divisionId, String id);

}

