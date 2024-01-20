package com.common.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;
import com.common.api.datasink.service.LanguageService;
import com.common.api.model.Language;
import com.common.api.model.type.ModelPartialUpdateType;
import com.common.api.request.PartialUpdateRequest;
import com.common.api.response.APIPreConditionErrorField;

@Service
public class PayloadValidatorPartial extends APIFixedConstant {

	@Autowired
	LanguageService languageService;

	public Collection<APIPreConditionErrorField> isValidUserPartialPayload(PartialUpdateRequest partialUpdateReq) {

		List<APIPreConditionErrorField> errorList = new ArrayList<>();

		List<String> allowedFieldNames = Arrays.asList("languageId", "deviceAutoStartSubMode");

		List<String> emptyList = new ArrayList<>();
		String id         = partialUpdateReq.getId();
		String userId  	  = partialUpdateReq.getUserId();
		String companyId  = partialUpdateReq.getCompanyId();
		String fieldName  = partialUpdateReq.getFieldName();
		String fieldValue = partialUpdateReq.getFieldValue();
		String type		  = partialUpdateReq.getType();

		if (allowedFieldNames.contains(fieldName)) {

			if (!id.equals(userId)) {
				errorList.add(new APIPreConditionErrorField("id", "Invalid Id"));
			} else if (fieldName.equals("languageId")) {
				if (type.equalsIgnoreCase(ModelPartialUpdateType.UserPartialUpdateType.LANGUAGE_UPDATE.toString())) {
					String languageId = fieldValue;
					List<Language> languageList = languageService.viewListByCriteria(companyId, languageId, "", "", emptyList, emptyList, "", "", 0, 0);
					if (languageList.size() <= 0) {
						errorList.add(new APIPreConditionErrorField("fieldName", "Invalid Language Id"));
					}
				} else {
					errorList.add(new APIPreConditionErrorField("type", "Invalid Type"));
				}
			}

		} else {
			errorList.add(new APIPreConditionErrorField("fieldName", "Invalid Field Name(s)"));
		}
		return errorList;
	}

}
