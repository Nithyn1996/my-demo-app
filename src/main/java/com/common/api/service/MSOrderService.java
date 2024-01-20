package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.OrderService;
import com.common.api.model.Order;
import com.common.api.resultset.OrderResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSOrderService extends APIFixedConstant implements OrderService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Order> viewListByCriteria(String companyId, String groupId, String divisionId, String subscriptionId, String id, String name, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

	    offset = (offset <= 0) ? DV_OFFSET : offset;
	    limit  = (limit <= 0) ? DV_LIMIT : limit;
	    sortOrder = (sortOrder.equals(GC_SORT_ASC)) ? GC_SORT_ASC : GC_SORT_DESC;
	    sortBy = SortByConstant.getEnumValueListDBField(sortBy);
	    sortBy = (sortBy.length() > 0) ? sortBy : SortByConstant.CREATED_AT.getDbField();

	    MapSqlParameterSource parameters = new MapSqlParameterSource();

	    String selQuery = " LOWER(active) = LOWER(:active) ";
	    parameters.addValue("active", DV_ACTIVE);

	    if (companyId != null && companyId.length() > 0) {
	        selQuery += " AND LOWER(company_id) = LOWER(:companyId)";
	        parameters.addValue("companyId", companyId);
	    }
	    if (groupId != null && groupId.length() > 0) {
	        selQuery += " AND LOWER(group_id) = LOWER(:groupId)";
	        parameters.addValue("groupId", groupId);
	    }
	    if (divisionId != null && divisionId.length() > 0) {
	        selQuery += " AND LOWER(division_id) = LOWER(:divisionId)";
	        parameters.addValue("divisionId", divisionId);
	    }
	    if (subscriptionId != null && subscriptionId.length() > 0) {
	        selQuery += " AND LOWER(subscription_id) = LOWER(:subscriptionId)";
	        parameters.addValue("subscriptionId", subscriptionId);
	    }
	    if (id != null && id.length() > 0) {
	        selQuery += " AND LOWER(id) = LOWER(:id)";
	        parameters.addValue("id", id);
	    }
	    if (name != null && name.length() > 0) {
	        selQuery += " AND LOWER(name) = LOWER(:name)";
	        parameters.addValue("name", name);
	    }
	    if (statusList != null && statusList.size() > 0) {
	        String statusListTemp = resultSetConversion.stringListToCommaAndQuoteString(statusList);
	        selQuery += " AND status in (" + statusListTemp + ")";
	    }
	    if (typeList != null && typeList.size() > 0) {
	        String typeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList);
	        selQuery += " AND type in (" + typeListTemp + ")";
	    }

	    if (selQuery.length() > 0) {

	    	selQuery = selQuery + " ORDER BY " + sortBy + " " + sortOrder;
			selQuery = selQuery + " OFFSET :offset ROWS";
			selQuery = selQuery + " FETCH NEXT :limit ROWS ONLY";

			parameters.addValue("offset", offset);
			parameters.addValue("limit", limit);

			selQuery = "Select * from " + TB_ORDER + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new OrderResultset());
	    }

	    return new ArrayList<>();

	}

	@Override
	public Order createOrder(Order order) {

		String objectId = Util.getTableOrCollectionObjectId();
		order.setId(objectId);

		String insertQuery = "INSERT INTO " + TB_ORDER  + " (id, company_id, group_id, division_id, subscription_id, "
														+ "name, ios_license_count, android_license_count, total_license_count, "
														+ " status, type, created_at, modified_at, created_by, modified_by) "
												+ "VALUES (:id, :companyId, :groupId, :divisionId, :subscriptionId, "
														+ ":name, :iosLicenseCount, :androidLicenceCount, :totalLicenceCount, "
														+ ":status, :type, :createdAt, :modifiedAt, :createdBy, :modifiedBy)";


		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", order.getId())
				.addValue("companyId", order.getCompanyId())
				.addValue("groupId", order.getGroupId())
				.addValue("divisionId", order.getDivisionId())
				.addValue("subscriptionId", order.getSubscriptionId())
				.addValue("name", order.getName())
				.addValue("iosLicenseCount", order.getIosLicenseCount())
				.addValue("androidLicenceCount", order.getAndroidLicenseCount())
				.addValue("totalLicenceCount", order.getTotalLicenseCount())
				.addValue("status", order.getStatus())
				.addValue("type", order.getType())
				.addValue("active", order.getActive())
				.addValue("createdAt", order.getCreatedAt())
				.addValue("modifiedAt", order.getModifiedAt())
				.addValue("createdBy", order.getCreatedBy())
				.addValue("modifiedBy", order.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if(status > 0) {
			return order;
		}
		return new Order();
	}

	@Override
	public Order updateOrder(Order order) {

		String updateQuery = "update "+ TB_ORDER
									+ " set "
										+ " company_id = :companyId, "
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " subscription_id = :subscriptionId, "
										+ " name = :name, "
										+ " ios_license_Count = :iosLicenseCount, "
										+ " android_license_count = :androidLicenseCount, "
										+ " total_license_count = :totalLicenseCount, "
										+ " status = :status, "
										+ " type = :type, "
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy "
								  + " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", order.getId())
				.addValue("companyId", order.getCompanyId())
				.addValue("groupId", order.getGroupId())
				.addValue("divisionId", order.getDivisionId())
				.addValue("subscriptionId" , order.getSubscriptionId())
				.addValue("name", order.getName())
				.addValue("iosLicenseCount", order.getIosLicenseCount())
				.addValue("androidLicenseCount", order.getAndroidLicenseCount())
				.addValue("totalLicenseCount", order.getTotalLicenseCount())
				.addValue("status", order.getStatus())
				.addValue("type", order.getType())
				.addValue("modifiedAt", order.getModifiedAt())
				.addValue("modifiedBy", order.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(updateQuery, parameters);

		if (status > 0) {
			return order;
		}
		return new Order ();
	}

	@Override
	public int removeOrderById(String companyId, String divisionId, String subscriptionId, String id) {

		String deleteQuery = "update " + TB_ORDER
				+ " SET active = :active "
				+ " where id = :id AND "
					+ " company_id = :companyId AND "
					+ " division_id = :divisionId AND"
					+ " subscription_id = :subscriptionId";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id)
				.addValue("companyId", companyId)
				.addValue("divisionId", divisionId)
				.addValue("subscriptionId", subscriptionId)
				.addValue("active", DV_INACTIVE);

		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}
