package com.common.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.constant.SortByConstant;
import com.common.api.datasink.service.HardwareDeviceService;
import com.common.api.model.HardwareDevice;
import com.common.api.resultset.HardwareDeviceResultset;
import com.common.api.util.ResultSetConversion;
import com.common.api.util.Util;

@Service
public class MSHardwareDeviceService extends APIFixedConstant implements HardwareDeviceService {

	@Autowired
	ResultSetConversion resultSetConversion;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<HardwareDevice> viewListByCriteria(String companyId, String id, String deviceOrderId, String deviceUniqueId, String deviceVersionNumber, String deviceModelName, String deviceType, List<String> statusList, List<String> typeList, String sortBy, String sortOrder, int offset, int limit) {

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

			selQuery = "select * from " + TB_HW_DEVICE + " where " + selQuery;
			return namedParameterJdbcTemplate.query(selQuery, parameters, new HardwareDeviceResultset());
		}
		return new ArrayList<>();
	}

	@Override
	public HardwareDevice createHardwareDevice(HardwareDevice hardwareDevice) {

		String objectId = Util.getTableOrCollectionObjectId();
		hardwareDevice.setId(objectId);

		String insertQuery = "insert into " + TB_HW_DEVICE + " (id, company_id, device_order_id, device_unique_id, "
											+ " device_version_number, device_model_name, device_type, status, type,"
											+ " created_at, modified_at, created_by, modified_by) "
								+ " values (:id, :companyId, :deviceOrderId, :deviceUniqueId, :deviceVersionNumber, "
											+ " :deviceModelName, :deviceType, :status, :type,"
											+ " :createdAt, :modifiedAt, :createdBy, :modifiedBy)";

		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", hardwareDevice.getId())
			.addValue("companyId", hardwareDevice.getCompanyId())
			.addValue("deviceOrderId", hardwareDevice.getDeviceOrderId())
			.addValue("deviceUniqueId", hardwareDevice.getDeviceUniqueId())
			.addValue("deviceVersionNumber", hardwareDevice.getDeviceVersionNumber())
			.addValue("deviceModelName", hardwareDevice.getDeviceModelName())
			.addValue("deviceType", hardwareDevice.getDeviceType())
			.addValue("status", hardwareDevice.getStatus())
			.addValue("type", hardwareDevice.getType())
			.addValue("createdAt", hardwareDevice.getCreatedAt())
			.addValue("modifiedAt", hardwareDevice.getModifiedAt())
			.addValue("createdBy", hardwareDevice.getCreatedBy())
			.addValue("modifiedBy", hardwareDevice.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return hardwareDevice;
		}
		return new HardwareDevice();
	}

	@Override
	public HardwareDevice updateHardwareDevice(HardwareDevice hardwareDevice) {

		String insertQuery = "update " + TB_HW_DEVICE
									+ " set "
										+ " company_id = :companyId, "
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
			.addValue("id", hardwareDevice.getId())
			.addValue("companyId", hardwareDevice.getCompanyId())
			.addValue("deviceOrderId", hardwareDevice.getDeviceOrderId())
			.addValue("deviceUniqueId", hardwareDevice.getDeviceUniqueId())
			.addValue("deviceVersionNumber", hardwareDevice.getDeviceVersionNumber())
			.addValue("deviceModelName", hardwareDevice.getDeviceModelName())
			.addValue("deviceType", hardwareDevice.getDeviceType())
 			.addValue("status", hardwareDevice.getStatus())
			.addValue("type", hardwareDevice.getType())
			.addValue("modifiedAt", hardwareDevice.getModifiedAt())
			.addValue("modifiedBy", hardwareDevice.getModifiedBy());

		int status = namedParameterJdbcTemplate.update(insertQuery, parameters);

		if (status > 0) {
			return hardwareDevice;
		}
		return new HardwareDevice();
	}

	@Override
	public int removeHardwareDeviceByCriteria(String companyId, String id, String deviceType) {

		String deleteQuery = "update " + TB_HW_DEVICE
								+ " SET active = :active "
								+ " where id = :id AND "
								+ " company_id = :companyId ";
		MapSqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("companyId", companyId)
			.addValue("id", id)
			.addValue("active", DV_INACTIVE);
		return namedParameterJdbcTemplate.update(deleteQuery, parameters);

	}

}