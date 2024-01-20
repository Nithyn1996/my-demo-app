package com.common.api.service.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.DivisionPreferenceService;
import com.common.api.datasink.service.OrderService;
import com.common.api.datasink.service.SubscriptionService;
import com.common.api.model.DivisionPreference;
import com.common.api.model.Order;
import com.common.api.model.Subscription;
import com.common.api.response.ProfileAvailabilityAccess;

@Service
public class ProfileAvailabilityService extends APIFixedConstant {

	@Autowired
	DivisionPreferenceService divisionPreferenceService;

	@Autowired
	OrderService orderService;
	@Autowired
	SubscriptionService subscriptionService;

	public ProfileAvailabilityAccess divisionPreferenceProfileAvailabilityStatus(String requestMethod, DivisionPreference divisionPreference) {

	    List<String> emptyList = new ArrayList<>();
	    ProfileAvailabilityAccess result = new ProfileAvailabilityAccess(GC_STATUS_SUCCESS, "");

	    String companyId  = divisionPreference.getCompanyId();
	    String divisionId = divisionPreference.getDivisionId();
	    String divPrefId  = divisionPreference.getId();
	    String name       = divisionPreference.getName();

	    List<DivisionPreference> divPreferenceList = divisionPreferenceService.viewListByCriteria(companyId, "", divisionId, "", name, "", emptyList, emptyList, "", "", 0, 0);

	    if (requestMethod.equals(GC_METHOD_POST)) {
		    if (divPreferenceList.size() > 0)
		        return new ProfileAvailabilityAccess(GC_STATUS_ERROR, EM_NAME_ALRDY_EXIST);
	    } else if (requestMethod.equals(GC_METHOD_PUT)) {
    		for (DivisionPreference divPrefDetail : divPreferenceList) {
	    		String divPrefIdDB = divPrefDetail.getId();
		    	if (!divPrefIdDB.equalsIgnoreCase(divPrefId) && divPrefId.length() > 0 && divPrefIdDB.length() > 0) {
	                return new ProfileAvailabilityAccess(GC_STATUS_ERROR, EM_NAME_ALRDY_EXIST);
	            }
    		}
	    }
		return result;

	}

	public ProfileAvailabilityAccess subscriptionProfileAvailabilityStatus(String requestMethod, Subscription subscription) {

	    List<String> emptyList = new ArrayList<>();
	    ProfileAvailabilityAccess result = new ProfileAvailabilityAccess(GC_STATUS_SUCCESS, "");

	    String companyId  	= subscription.getCompanyId();
	    String divisionId 	= subscription.getDivisionId();
	    String subId 		= subscription.getId();
	    String name			= subscription.getName();
	    String type 		= subscription.getType();

	    List<String> typeList = Arrays.asList(type);
	    List<Subscription> subscriptionList = subscriptionService.viewListByCriteria(companyId, "", divisionId, "", name, emptyList, typeList, "", "", 0, 0);

	    if (requestMethod.equals(GC_METHOD_POST)) {
		    if (subscriptionList.size() > 0)
		        return new ProfileAvailabilityAccess(GC_STATUS_ERROR, EM_NAME_ALRDY_EXIST);
	    } else if (requestMethod.equals(GC_METHOD_PUT)) {
	    	for (Subscription subDetail : subscriptionList) {
	    		String subIdDB = subDetail.getId();
	    		if (!subIdDB.equalsIgnoreCase(subId) && subId.length() > 0 && subIdDB.length() > 0) {
	    			 return new ProfileAvailabilityAccess(GC_STATUS_ERROR, EM_NAME_ALRDY_EXIST);
	    		}
	    	}
	    }
		return result;

	}

	public ProfileAvailabilityAccess orderProfileAvailabilityStatus(String requestMethod, Order order) {

	    List<String> emptyList = new ArrayList<>();
	    ProfileAvailabilityAccess result = new ProfileAvailabilityAccess(GC_STATUS_SUCCESS, "");

	    String companyId 	= order.getCompanyId();
	    String divisionId 	= order.getDivisionId();
	    String subscripId   = order.getSubscriptionId();
	    String orderId 		= order.getId();
	    String name 		= order.getName();
	    String type 		= order.getType();

	    List<String> typeList = Arrays.asList(type);
	    List<Order> orderList = orderService.viewListByCriteria(companyId, "", divisionId, subscripId, "", name, emptyList, typeList, "", "", 0, 0);

	    if (requestMethod.equals(GC_METHOD_POST)) {
	    	if (orderList.size() > 0)
	    		return new ProfileAvailabilityAccess(GC_STATUS_ERROR, EM_NAME_ALRDY_EXIST);
	    } else if (requestMethod.equals(GC_METHOD_PUT)) {
	    	for (Order orderDetail : orderList) {
	    		String ordIdDB = orderDetail.getId();
	    		if (!ordIdDB.equalsIgnoreCase(orderId) && orderId.length() > 0 && ordIdDB.length() > 0) {
	    			 return new ProfileAvailabilityAccess(GC_STATUS_ERROR, EM_NAME_ALRDY_EXIST);
	    		}
	    	}
	    }

		return result;
	}

}