package com.common.api.constant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GroupByConstant {

	ID("id", "id");

	private String dbField = "";
	private String jsonField = "";

	private GroupByConstant(String dbField, String jsonField) {
		this.dbField = dbField;
		this.jsonField = jsonField;
	}
	public String getDbField() {
		return dbField;
	}
	public String getJsonField() {
		return jsonField;
	}
	public static List<String> getEnumValueListJsonField() {
		try {
			return Stream.of(GroupByConstant.values()).map(ev -> ev.getJsonField()).collect(Collectors.toList());
		} catch(Exception errMess) {
			return new ArrayList<>();
		}
    }
	public static String getEnumValueListDBField(String inputValue) {
		try {
			EnumSet<GroupByConstant> allSortBy = EnumSet.allOf(GroupByConstant.class);
			for (GroupByConstant sortByTemp : allSortBy) {
				String sortByJson = sortByTemp.getJsonField();
				String sortByDB   = sortByTemp.getDbField();
				if (inputValue.equals(sortByJson)) {
					return sortByDB;
				}
			}
			return "";
		} catch(Exception errMess) {
			return "";
		}
    }

}
