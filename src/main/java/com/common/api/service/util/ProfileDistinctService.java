package com.common.api.service.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.UserDistinctService;
import com.common.api.model.Device;
import com.common.api.model.UserDistinct;
import com.common.api.util.APIDateTime;

@Service
@PropertySource({ "classpath:application.properties", "classpath:response_message.properties"})
public class ProfileDistinctService extends APIFixedConstant {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

    @Autowired
    UserDistinctService userDistinctService;

	public Long getNextDeviceId(Device deviceDetail) {

		Long result = 1L;
		String companyId = deviceDetail.getCompanyId();
		String userId    = deviceDetail.getUserId();
		String type      = deviceDetail.getType();
		List<String> statusList = new ArrayList<>();
		List<String> typeList   = Arrays.asList(type);

		List<UserDistinct> userDistinctList = userDistinctService.viewListByCriteria(companyId, userId, "", statusList, typeList, "", "", 0, 0);

		if (userDistinctList.size() > 0) {
			UserDistinct deviceDistinctTemp = userDistinctList.get(0);
			result = deviceDistinctTemp.getNextDeviceId();
		} else {

			Timestamp dbOperationStartTime = APIDateTime.getGlobalDateTimeDBOperation();
			UserDistinct userDistinct = new UserDistinct();
			userDistinct.setCompanyId(companyId);
			userDistinct.setUserId(userId);
			userDistinct.setType(type);
			userDistinct.setStatus(GC_STATUS_REGISTERED);
			userDistinct.setCreatedAt(dbOperationStartTime);
			userDistinct.setModifiedAt(dbOperationStartTime);
			userDistinct.setCreatedBy(userId);
			userDistinct.setModifiedBy(userId);

			UserDistinct userDistinctDetail = userDistinctService.createUserDistinct(userDistinct);

			if (userDistinctDetail != null && userDistinctDetail.getId().length() > 0) {

				List<UserDistinct> userDistinctAddedList = userDistinctService.viewListByCriteria(companyId, userId, "", statusList, typeList, "", "", 0, 0);

				if (userDistinctAddedList.size() > 0) {
					UserDistinct deviceDistinctTemp = userDistinctAddedList.get(0);
					result = deviceDistinctTemp.getNextDeviceId();
				}
			}
		}
		return result;
	}

}