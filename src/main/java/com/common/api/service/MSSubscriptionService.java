package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.SubscriptionService;
import com.common.api.model.Subscription;
import com.common.api.resultset.SubscriptionResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSSubscriptionService extends APIFixedConstant implements SubscriptionService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	SubscriptionService subscriptionService;

	@Override
	public List<Subscription> viewListByCriteria(String companyId, String groupId, String divisionId, String id, String name, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (name != null && name.length() > 0) {
			selQuery = selQuery + " AND LOWER(name) = LOWER(:name) ";
			parameters.addValue("name", name);
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

			selQuery = "select * from " + TB_SUBSCRIPTION + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new SubscriptionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public List<Subscription> viewListByCriteria(String companyId, String groupId, String divisionId, String id,List<String> typeList){

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String selQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		if (companyId != null && companyId.length() > 0) {
			selQuery = selQuery + " AND LOWER(company_id) = LOWER(:companyId) ";
			parameters.addValue("companyId", companyId);
		}
		if (divisionId != null && divisionId.length() > 0) {
			selQuery = selQuery + " AND LOWER(division_id) = LOWER(:divisionId) ";
			parameters.addValue("divisionId", divisionId);
		}
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (typeList != null && typeList.size() > 0) {
			String typeListTemp = resultSetConversion.stringListToCommaAndQuoteString(typeList);
			selQuery = selQuery + " AND type in (" + typeListTemp + ")";
		}

		if (selQuery.length() > 0) {
			selQuery = "select * from " + TB_SUBSCRIPTION + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new SubscriptionResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public Subscription createSubscription(Subscription subscription) {

		String objectId = Util.getTableOrCollectionObjectId();
		subscription.setId(objectId);

		String insertQuery = "insert into "+ TB_SUBSCRIPTION + " (id, company_id, group_id, division_id, name, order_count, "
										   + " ios_license_count, android_license_count, available_license_count, status, "
										   + " type, created_at, modified_at, created_by, modified_by, start_time, end_time) "
									+ "VALUES (:id, :companyId, :groupId, :divisionId, :name, :orderCount, "
									       + " :iosLicenseCount, :androidLicenseCount, :availableLicenseCount, :status, "
									       + " :type, :createdAt, :modifiedAt, :createdBy, :modifiedBy, :startTime, :endTime)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", subscription.getId())
		        .addValue("companyId", subscription.getCompanyId())
		        .addValue("groupId", subscription.getGroupId())
		        .addValue("divisionId", subscription.getDivisionId())
		        .addValue("name", subscription.getName())
		        .addValue("orderCount", subscription.getOrderCount())
		        .addValue("iosLicenseCount", subscription.getIosLicenseCount())
		        .addValue("androidLicenseCount", subscription.getAndroidLicenseCount())
		        .addValue("availableLicenseCount", subscription.getAvailableLicenseCount())
		        .addValue("status", subscription.getStatus())
		        .addValue("type", subscription.getType())
		        .addValue("createdAt", subscription.getCreatedAt())
		        .addValue("modifiedAt", subscription.getModifiedAt())
		        .addValue("createdBy", subscription.getCreatedBy())
		        .addValue("modifiedBy", subscription.getModifiedBy())
		        .addValue("startTime", subscription.getStartTime())
		        .addValue("endTime", subscription.getEndTime());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if(status > 0) {
			return subscription;
		}
		return new Subscription();
	}


	@Override
	public Subscription updateSubscription(Subscription subscription) {

		String updateQuery = "update "+ TB_SUBSCRIPTION
									+ " set "
										+ " company_id = :companyId, "
										+ " group_id = :groupId, "
										+ " division_id = :divisionId, "
										+ " name = :name, "
										+ " order_count = :orderCount, "
										+ " ios_license_Count = :iosLicenseCount, "
										+ " android_license_count = :androidLicenseCount, "
										+ " available_license_count = :availableLicenseCount, "
										+ " status = :status, "
										+ " type = :type, "
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy "
								  + " WHERE id = :id" ;
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", subscription.getId())
				.addValue("companyId", subscription.getCompanyId())
				.addValue("groupId", subscription.getGroupId())
				.addValue("divisionId", subscription.getDivisionId())
				.addValue("name", subscription.getName())
				.addValue("orderCount", subscription.getOrderCount())
				.addValue("iosLicenseCount", subscription.getIosLicenseCount())
				.addValue("androidLicenseCount", subscription.getAndroidLicenseCount())
				.addValue("availableLicenseCount", subscription.getAvailableLicenseCount())
				.addValue("status", subscription.getStatus())
				.addValue("type", subscription.getType())
				.addValue("modifiedAt", subscription.getModifiedAt())
				.addValue("modifiedBy", subscription.getModifiedBy());
		int status = namedParameterJdbcTemplate.update(updateQuery, parameters);

		if (status > 0) {
			return subscription;
		}
		return new Subscription();
	}

	@Override
	public int subscriptionCountIncrementAndroid(String companyId, String divisionId, String id, int countValue, int orderCount) {

		String updateQuery = "update "+ TB_SUBSCRIPTION
								+ " set android_license_count = (android_license_count + :countValue), "
									+ " order_count           = (order_count + :orderCount), "
									+ " available_license_count = (available_license_count + :countValue)"
								+ " WHERE "
									+ " id = :id AND"
									+ " company_id  = :companyId AND "
									+ " division_id = :divisionId AND "
									+ " :countValue > 0 AND "
									+ " android_license_count >= 0";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id)
				.addValue("companyId", companyId)
				.addValue("divisionId", divisionId)
				.addValue("orderCount", orderCount)
				.addValue("countValue", countValue);

		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int subscriptionCountIncrementIos(String companyId, String divisionId, String id, int countValue, int orderCount) {

		String updateQuery = "update "+ TB_SUBSCRIPTION
								+ " set ios_license_count = (ios_license_count + :countValue), "
									+ " order_count       = (order_count + :orderCount), "
									+ " available_license_count = (available_license_count + :countValue) "
								+ " WHERE "
									+ " id = :id AND"
									+ " company_id  = :companyId AND "
									+ " division_id = :divisionId AND "
									+ " :countValue > 0 AND "
									+ " ios_license_count >= 0 ";


		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id)
				.addValue("companyId", companyId)
				.addValue("divisionId", divisionId)
				.addValue("orderCount", orderCount)
				.addValue("countValue", countValue);

		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int subscriptionCountDecrementAndroid(String companyId, String divisionId,String type, int countValue) {

		String updateQuery = "update "+ TB_SUBSCRIPTION
								+ " set android_license_count   = (android_license_count - :countValue), "
									+ " available_license_count = (available_license_count - :countValue) "
								+ " WHERE "
									+ " type = :type AND"
									+ " company_id  = :companyId AND "
									+ " division_id = :divisionId AND "
									+ " :countValue >= 0 AND "
									+ " android_license_count >= 0 AND"
				    				+ " (android_license_count - :countValue) >= 0";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("type", type)
				.addValue("companyId", companyId)
				.addValue("countValue", countValue)
				.addValue("divisionId", divisionId);

		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int subscriptionCountDecrementIos(String companyId, String divisionId, String type, int countValue) {

		String updateQuery = "update "+ TB_SUBSCRIPTION
								+ " set ios_license_count       = (ios_license_count - :countValue), "
									+ " available_license_count = (available_license_count - :countValue)"
								+ " WHERE "
									+ " type = :type AND"
									+ " company_id  = :companyId AND "
									+ " division_id = :divisionId AND "
									+ " :countValue >= 0 AND "
									+ " ios_license_count >= 0 AND "
		    						+ " (ios_license_count - :countValue) >= 0";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("type", type)
				.addValue("companyId", companyId)
				.addValue("divisionId", divisionId)
				.addValue("countValue", countValue);

		return namedParameterJdbcTemplate.update(updateQuery, parameters);

	}

	@Override
	public int removeSubscriptionById(String companyId, String divisionId, String id) {

		String deleteQuery = "update " + TB_SUBSCRIPTION
							+ " SET active = :active "
							+ " where id = :id AND "
								+ " company_id = :companyId AND "
								+ " division_id = :divisionId";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id)
				.addValue("companyId", companyId)
				.addValue("divisionId", divisionId)
				.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}
