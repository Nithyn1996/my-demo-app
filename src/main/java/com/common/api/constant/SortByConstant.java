package com.common.api.constant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SortByConstant {

	ID("id", "id"),
	CREATED_AT("created_at", "createdAt"),
	FIRST_NAME("first_name", "firstName"),
	NAME("name", "name"),
	TYPE("type", "type");

	private String dbField = "";
	private String jsonField = "";
	private SortByConstant(String dbField, String jsonField) {
		this.dbField = dbField;
		this.jsonField = jsonField;
	}
	public String getDbField() {
		return dbField;
	}
	public String getJsonField() {
		return jsonField;
	}
	public static List<String> getEnumValueListDBField() {
		try {
			return Stream.of(SortByConstant.values()).map(ev -> ev.getDbField()).collect(Collectors.toList());
		} catch(Exception errMess) {
			return new ArrayList<>();
		}
    }
	public static String getEnumValueListDBField(String inputValue) {
		try {
			EnumSet<SortByConstant> allSortBy = EnumSet.allOf( SortByConstant.class);
			for (SortByConstant sortByTemp : allSortBy) {
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
	public static List<String> getEnumValueListJsonFieldd() {
		try {
			return Stream.of(SortByConstant.values()).map(ev -> ev.getJsonField()).collect(Collectors.toList());
		} catch(Exception errMess) {
			return new ArrayList<>();
		}
    }

}
