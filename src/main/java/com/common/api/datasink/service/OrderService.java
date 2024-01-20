package com.common.api.datasink.service;

import java.util.List;

import com.common.api.model.Order;

public interface OrderService {

	public List<Order> viewListByCriteria(String companyId, String groupId, String divisionId, String subscriptionId, String id, String name, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit);

	public Order createOrder(Order order);

	public Order updateOrder(Order order);

	public int removeOrderById(String companyId, String divisionId, String subscriptionId, String id);

}
