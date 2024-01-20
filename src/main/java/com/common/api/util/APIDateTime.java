package com.common.api.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.common.api.constant.APIFixedConstant;

@Service
public class APIDateTime extends APIFixedConstant {

	public APIDateTime() {
	}

    public static String getGlobalDateAsString() {
		Timestamp currnetTZTime = getGlobalDateTimeDBOperation();
		return new SimpleDateFormat(GC_DATE_FORMAT).format(currnetTZTime);
    }

    public static String getGlobalTimeAsString() {
		Timestamp currnetTZTime = getGlobalDateTimeDBOperation();
		return new SimpleDateFormat(GC_TIME_FORMAT).format(currnetTZTime);
    }

	public static String getGlobalDateTimeAsString() {
		Timestamp currnetTZTime = getGlobalDateTimeDBOperation();
		return new SimpleDateFormat(GC_DATE_TIME_FORMAT).format(currnetTZTime);
	}

	public static String convertDateToString(Date dateTimeValue) {
		return new SimpleDateFormat(GC_DATE_FORMAT).format(dateTimeValue);
	}

	public static String convertTimeToString(Date dateTimeValue) {
		return new SimpleDateFormat(GC_TIME_FORMAT).format(dateTimeValue);
	}

	public static String convertDateTimeToString(Date dateTimeValue) {
		if (dateTimeValue != null)
			return new SimpleDateFormat(GC_DATE_TIME_FORMAT).format(dateTimeValue);
		return "";
	}

	public static Timestamp getGlobalDateTimeDBOperation() {
    	Instant instant = Instant.now();
    	ZonedDateTime atZone = instant.atZone(ZoneId.of(GC_GLOBAL_TIME_ZONE_ID));
    	return Timestamp.valueOf(atZone.toLocalDateTime());
    }

    public static String getZoneIdBasedDateTime() {
    	Instant instant = Instant.now();
    	ZonedDateTime atZone = instant.atZone(ZoneId.of(GC_GLOBAL_TIME_ZONE_ID));
    	Timestamp timeTemp = Timestamp.valueOf(atZone.toLocalDateTime());
    	return new SimpleDateFormat(GC_DATE_TIME_FORMAT).format(timeTemp);
    }

    public static String getZoneIdBasedDateTime(String timeZoneId) {
    	Instant instant = Instant.now();
    	ZonedDateTime atZone = instant.atZone(ZoneId.of(timeZoneId));
    	Timestamp timeTemp = Timestamp.valueOf(atZone.toLocalDateTime());
    	return new SimpleDateFormat(GC_DATE_TIME_FORMAT).format(timeTemp);
    }

    public static Timestamp getGlobalDateTimeLogOperation() {
    	Instant instant = Instant.now();
    	ZonedDateTime atZone = instant.atZone(ZoneId.of(GC_GLOBAL_TIME_ZONE_ID));
    	return Timestamp.valueOf(atZone.toLocalDateTime());
    }

	public static Timestamp convertStringDateToTimestamp(String dateTimeValue) {
    	ZoneId zoneId = ZoneId.of(GC_GLOBAL_TIME_ZONE_ID);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GC_DATE_FORMAT);
    	LocalDateTime ldt = LocalDateTime.parse(dateTimeValue, formatter);
 		ZonedDateTime zdt = ldt.atZone(zoneId);
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static Timestamp convertStringTimeToTimestamp(String dateTimeValue) {
    	ZoneId zoneId = ZoneId.of(GC_GLOBAL_TIME_ZONE_ID);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GC_TIME_FORMAT);
    	LocalDateTime ldt = LocalDateTime.parse(dateTimeValue, formatter);
 		ZonedDateTime zdt = ldt.atZone(zoneId);
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static Timestamp convertEpochTimeToTimestamp(long dateTimeValue) {
    	ZoneId zoneId = ZoneId.of(GC_GLOBAL_TIME_ZONE_ID);
    	LocalDateTime ldt = LocalDateTime.ofEpochSecond(dateTimeValue, 0, ZoneOffset.UTC);
 		ZonedDateTime zdt = ldt.atZone(zoneId);
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static Timestamp convertStringDateTimeToTimestamp(String dateTimeValue) {
    	ZoneId zoneId = ZoneId.of(GC_GLOBAL_TIME_ZONE_ID);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GC_DATE_TIME_FORMAT);
    	LocalDateTime ldt = LocalDateTime.parse(dateTimeValue, formatter);
 		ZonedDateTime zdt = ldt.atZone(zoneId);
		return Timestamp.valueOf(zdt.toLocalDateTime());
	}

	public static Timestamp convertDateTimeToTimestamp(Date dateTimeValue) {
		return new Timestamp(dateTimeValue.getTime());
	}

    public static Timestamp convertToAnotherDateTime(String actionType, int years, int months, int days, int hours, int minutes, int seconds) {

    	Instant instant = Instant.now();
    	ZonedDateTime atZone = instant.atZone(ZoneId.of(GC_GLOBAL_TIME_ZONE_ID));
    	if (actionType.equals(GC_ACTION_PLUS)) {
    		ZonedDateTime zdt = atZone.plusYears(years).plusMonths(months).plusDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    		return Timestamp.valueOf(zdt.toLocalDateTime());
    	} else if (actionType.equals(GC_ACTION_MINUS)) {
    		ZonedDateTime zdt = atZone.minusYears(years).minusMonths(months).minusDays(days).minusHours(hours).minusMinutes(minutes).minusSeconds(seconds);
    		return Timestamp.valueOf(zdt.toLocalDateTime());
    	}
		return Timestamp.valueOf(atZone.toLocalDateTime());
    }

    public static Timestamp convertGlobalDateTime(String actionType, String dateTimeValue, int years, int months, int days, int hours, int minutes, int seconds) {

    	ZoneId zoneId = ZoneId.of(GC_GLOBAL_TIME_ZONE_ID);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GC_DATE_TIME_FORMAT);
    	LocalDateTime ldt = LocalDateTime.parse(dateTimeValue, formatter);
    	if (actionType.equals(GC_ACTION_PLUS)) {
    		ZonedDateTime zdt = ldt.atZone(zoneId).plusYears(years).plusMonths(months).plusDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    		return Timestamp.valueOf(zdt.toLocalDateTime());
    	} else if (actionType.equals(GC_ACTION_MINUS)) {
    		ZonedDateTime zdt = ldt.atZone(zoneId).minusYears(years).minusMonths(months).minusDays(days).minusHours(hours).minusMinutes(minutes).minusSeconds(seconds);
    		return Timestamp.valueOf(zdt.toLocalDateTime());
    	}
		ZonedDateTime zdt = ldt.atZone(zoneId);
		return Timestamp.valueOf(zdt.toLocalDateTime());
    }

}
