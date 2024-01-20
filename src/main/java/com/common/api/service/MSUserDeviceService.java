package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.UserDeviceService;
import com.common.api.model.UserDevice;
import com.common.api.resultset.UserDeviceResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSUserDeviceService extends APIFixedConstant implements UserDeviceService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<UserDevice> viewListByCriteria(String companyId, String userId, String id, String deviceOrderId, String deviceUniqueId, String deviceVersionNumber, String deviceModelName, String deviceType, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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
		if (userId != null && userId.length() > 0) {
			selQuery = selQuery + " AND LOWER(user_id) = LOWER(:userId) ";
			parameters.addValue("userId", userId);
		}
		if (id != null && id.length() > 0) {
			selQuery = selQuery + " AND LOWER(id) = LOWER(:id) ";
			parameters.addValue("id", id);
		}
		if (deviceOrderId != null && deviceOrderId.length() > 0) {
			selQuery = selQuery + " AND LOWER(device_order_id) = LOWER(:deviceOrderId) ";
			parameters.addValue("deviceOrderId", deviceOrderId);
		}
		if (deviceUniqueId != null && deviceUniqueId.length() > 0) {
			selQuery = selQuery + " AND LOWER(device_unique_id) = LOWER(:deviceUniqueId) ";
			parameters.addValue("deviceUniqueId", deviceUniqueId);
		}
		if (deviceVersionNumber != null && deviceVersionNumber.length() > 0) {
			selQuery = selQuery + " AND LOWER(device_version_number) = LOWER(:deviceVersionNumber) ";
			parameters.addValue("deviceVersionNumber", deviceVersionNumber);
		}
		if (deviceModelName != null && deviceModelName.length() > 0) {
			selQuery = selQuery + " AND LOWER(device_model_name) = LOWER(:deviceModelName) ";
			parameters.addValue("deviceModelName", deviceModelName);
		}
		if (deviceType != null && deviceType.length() > 0) {
			selQuery = selQuery + " AND LOWER(device_type) = LOWER(:deviceType) ";
			parameters.addValue("deviceType", deviceType);
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

			selQuery = "select * from " + TB_USER_DEVICE + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new UserDeviceResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public UserDevice createUserDevice(UserDevice userDevice) {

		String objectId = Util.getTableOrCollectionObjectId();
		userDevice.setId(objectId);

		String insertQuery = "insert into " + TB_USER_DEVICE + " (id, company_id, division_id, module_id, user_id, device_order_id, device_unique_id, "
											+ " device_version_number, device_model_name, device_type, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :divisionId, :moduleId, :userId, :deviceOrderId, :deviceUniqueId, :deviceVersionNumber, "
											+ " :deviceModelName, :deviceType, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userDevice.getId())
			.addValue("companyId", userDevice.getCompanyId())
			.addValue("divisionId", userDevice.getDivisionId())
			.addValue("moduleId", userDevice.getModuleId())
			.addValue("userId", userDevice.getUserId())
			.addValue("deviceOrderId", userDevice.getDeviceOrderId())
			.addValue("deviceUniqueId", userDevice.getDeviceUniqueId())
			.addValue("deviceVersionNumber", userDevice.getDeviceVersionNumber())
			.addValue("deviceModelName", userDevice.getDeviceModelName())
			.addValue("deviceType", userDevice.getDeviceType())
			.addValue("status", userDevice.getStatus())
			.addValue("type", userDevice.getType())
			.addValue("createdAt", userDevice.getCreatedAt())
			.addValue("modifiedAt", userDevice.getModifiedAt())
			.addValue("createdBy", userDevice.getCreatedBy())
			.addValue("modifiedBy", userDevice.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userDevice;
		}
		return new UserDevice();
	}

	@Override
	public UserDevice updateUserDevice(UserDevice userDevice) {

		String insertQuery = "update " + TB_USER_DEVICE
									+ " set "
										+ " company_id = :companyId, "
										+ " user_id = :userId, "
										+ " device_order_id = :deviceOrderId, "
										+ " device_unique_id = :deviceUniqueId, "
										+ " device_version_number = :deviceVersionNumber, "
										+ " device_model_name = :deviceModelName, "
										+ " device_type = :deviceType, "
 										+ " status = :status, "
										+ " type = :type,"
										+ " modified_at = :modifiedAt, "
										+ " modified_by = :modifiedBy"
								+ " WHERE id = :id" ;

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", userDevice.getId())
			.addValue("companyId", userDevice.getCompanyId())
			.addValue("userId", userDevice.getUserId())
			.addValue("deviceOrderId", userDevice.getDeviceOrderId())
			.addValue("deviceUniqueId", userDevice.getDeviceUniqueId())
			.addValue("deviceVersionNumber", userDevice.getDeviceVersionNumber())
			.addValue("deviceModelName", userDevice.getDeviceModelName())
			.addValue("deviceType", userDevice.getDeviceType())
 			.addValue("status", userDevice.getStatus())
			.addValue("type", userDevice.getType())
			.addValue("modifiedAt", userDevice.getModifiedAt())
			.addValue("modifiedBy", userDevice.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return userDevice;
		}
		return new UserDevice();
	}

	@Override
	public int removeUserDeviceByCriteria(String companyId, String userId, String deviceType) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String deleteQuery = " LOWER(active) = LOWER(:active) ";
		parameters.addValue("active", DV_ACTIVE);

		deleteQuery = deleteQuery + " AND LOWER(company_id) = LOWER(:companyId) ";
		parameters.addValue("companyId", companyId);

		deleteQuery = deleteQuery + " AND LOWER(device_type) = LOWER(:deviceType) ";
		parameters.addValue("deviceType", deviceType);

		deleteQuery = deleteQuery + " AND LOWER(user_id) = LOWER(:userId) ";
		parameters.addValue("userId", userId);

		deleteQuery = "update " + TB_USER_DEVICE + " SET active = :active where " + deleteQuery;
		parameters.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);
	}

}