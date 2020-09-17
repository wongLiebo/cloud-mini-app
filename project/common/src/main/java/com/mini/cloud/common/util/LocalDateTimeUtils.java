package com.mini.cloud.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.WeekFields;
import java.util.Calendar;

import org.springframework.util.StringUtils;

import cn.hutool.core.date.DateUtil;

public class LocalDateTimeUtils {

	public static DateTimeFormatter _yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static DateTimeFormatter _yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static DateTimeFormatter _yyyyMM = DateTimeFormatter.ofPattern("yyyy-MM");
	public static DateTimeFormatter _yyyyw = DateTimeFormatter.ofPattern("yyyy-w");
	
	
	public static String format_yyyyMMddHHmmss(LocalDateTime dateTime) {
		return dateTime.format(_yyyyMMddHHmmss);
	}
	public static String format_yyyyMMdd(LocalDateTime dateTime) {
		return dateTime.format(_yyyyMMdd);
	}
	public static String format_yyyyMM(LocalDateTime dateTime) {
		return dateTime.format(_yyyyMM);
	}
	public static String format_yyyyw(LocalDateTime dateTime) {
		return dateTime.format(_yyyyw);
	}
	
	public static String format_yyyyMMddHHmmss() {
		return LocalDateTime.now().format(_yyyyMMddHHmmss);
	}
	public static String format_yyyyMMdd() {
		return LocalDateTime.now().format(_yyyyMMdd);
	}
	
	public static String format_yyyyMM() {
		return LocalDateTime.now().format(_yyyyMM);
	}
	
	public static String format_yyyyw() {
		return LocalDateTime.now().format(_yyyyw);
	}
	
	public static String format(LocalDateTime dateTime,DateTimeFormatter dateTimeFormatter) {
		return dateTime.format(dateTimeFormatter);
	}
	
	public static String format(LocalDateTime dateTime,String pattern) {
		return dateTime.format(DateTimeFormatter.ofPattern(pattern));
	}
	
	public static LocalDateTime parse_yyyyMMddHHmmss(String str) {
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		return LocalDateTime.parse(str, _yyyyMMddHHmmss);
	}
	
	public static LocalDateTime parse_yyyyMMdd(String str) {
		if(StringUtils.isEmpty(str)) {
			return null;
		}
		return LocalDateTime.parse(str+" 00:00:00", _yyyyMMddHHmmss);
	}
	
	public static LocalDate parseLocalDate(String yyyyMMdd) {
		if(StringUtils.isEmpty(yyyyMMdd)) {
			return null;
		}
		return LocalDate.parse(yyyyMMdd,_yyyyMMdd);
	}
	
	public static LocalDateTime parse(String str,DateTimeFormatter dateTimeFormatter) {
		return LocalDateTime.parse(str, dateTimeFormatter);
	}
	
	public static LocalDateTime parse(String str,String pattern) {
		return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
	}
	
	
	/**
	 * 获取某一年的某一周的周一日期
	 * */
	public static String getStartDayOfWeekNo(int year,int weekNo){
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return DateUtil.formatDate(cal.getTime());
	}
	
	/**
	 * 获取某一年的某一周的周日日期
	 * */
	public static String getEndDayOfWeekNo(int year,int weekNo) {
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return DateUtil.formatDate(cal.getTime());
	}
	
	//---------------------------------------------------
	
	public static LocalDateTime getMax(LocalDateTime d1 ,LocalDateTime d2) {
		return d1.compareTo(d2)>=0?d1:d2;
	}
	public static String getMax_yyyyMMddHHmmss(LocalDateTime d1 ,LocalDateTime d2) {
		LocalDateTime d=getMax(d1, d2);
		return format_yyyyMMddHHmmss(d);
	}
	
	public static LocalDateTime getMin(LocalDateTime d1 ,LocalDateTime d2) {
		return d1.compareTo(d2)>=0?d2:d1;
	}
	
	public static String getMin_yyyyMMddHHmmss(LocalDateTime d1 ,LocalDateTime d2) {
		LocalDateTime d=getMin(d1, d2);
		return format_yyyyMMddHHmmss(d);
	}
	
	//---------------------------------------------------
	
	/**增加分钟*/
	public static String plusMinutes_yyyyMMddHHmmss(String str,long minutes) {
		return plus_yyyyMMddHHmmss(str, minutes, ChronoUnit.MINUTES);
	}
	
	/**增加分钟*/
	public static String plus_yyyyMMddHHmmss(String str,long amountToAdd,TemporalUnit unit) {
		LocalDateTime dateTime1=parse_yyyyMMddHHmmss(str);
		LocalDateTime dateTime2=dateTime1.plus(amountToAdd, unit);
		return format_yyyyMMddHHmmss(dateTime2);
	}
	
	/**增加*/
	public static LocalDateTime plus(LocalDateTime dateTime,long amountToAdd,TemporalUnit unit) {
		return dateTime.plus(amountToAdd, unit);
	}
	/**增加*/
	public static LocalDateTime plusDay(LocalDateTime dateTime,int day) {
		if(dateTime==null) {
			return null;
		}
		return dateTime.plus(day, ChronoUnit.DAYS);
	}
	
	/**减少*/
	public static LocalDateTime minusDay(LocalDateTime dateTime,int day) {
		if(dateTime==null) {
			return null;
		}
		return dateTime.minusDays(day);
	}
	
	/**减少天数*/
	public static String minusDays_yyyyMMdd(LocalDateTime dateTime,long days) {
		LocalDateTime dateTime2= dateTime.minusDays(days);
		return format_yyyyMMdd(dateTime2);
	}
	
	/**减少天数*/
	public static String minusDays_yyyyMMdd(long days) {
		return minusDays_yyyyMMdd(LocalDateTime.now(), days);
	}
	
	/**周*/
	public static Integer getWeek(LocalDateTime dateTime) {
		return dateTime.getDayOfWeek().getValue();
	}
//	/**月*/
//	public static Integer getMonth(LocalDateTime dateTime) {
//		return dateTime.getMonth().getValue();
//	}
//	/**年*/
//	public static Integer getYear(LocalDateTime dateTime) {
//		return dateTime.getYear();
//	}
	
	/**当前日期在一年中的多少周(星期一是一周的第一天，与 %x 使用)*/
	public static int getWeek2YearByMonday(LocalDateTime d1) {
		 WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
		 return d1.get(weekFields.weekOfYear());
	}
	
	
	/**
	 * 比较两个日期大小
	 * (d1==d2)->0 (d1>d2)->1 (d1<d2)-> -1
	 * */
	public static int compare(LocalDateTime d1 ,LocalDateTime d2) {
		if(d1.isEqual(d2)) {
			return 0;
		}
		if(d1.isAfter(d2)) {
			return 1;
		}else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(LocalDateTimeUtils.minusDays_(LocalDateTime.now(), 1L));
		
		
		DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM");
		DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-w");
		
		LocalDateTime ldt=LocalDateTime.now();
		System.out.println(ldt.format(df1));
		System.out.println(ldt.format(df2));
		
		
//		System.out.println(ldt.getDayOfWeek().getValue());
//		System.out.println(ldt.get(WeekFields.of(DayOfWeek.of(1), 1).dayOfWeek()));
		
		
		
		
//		Calendar calendar = Calendar.getInstance();  
//		calendar.setFirstDayOfWeek(Calendar.MONDAY);  
//		calendar.setTime(LocalDateTime.now());
//		System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));  
		
//		System.out.println(LocalDateTimeUtils.getWeek(LocalDateTime.now()));
//		System.out.println(LocalDateTimeUtils.getMonth(LocalDateTime.now()));
//		System.out.println(LocalDateTimeUtils.getYear(LocalDateTime.now()));
		
		
	}

	
}
