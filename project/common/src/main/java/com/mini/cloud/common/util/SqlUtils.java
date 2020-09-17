package com.mini.cloud.common.util;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

public class SqlUtils {
	
	public static String in (String ... items) {
		StringBuilder sql=null;
		for(String item:items) {
			if(sql==null) {
				sql=new StringBuilder();
				sql.append(_in(item));
			}else {
				sql.append(",").append(_in(item));
			}
		}
		return "("+sql.toString()+")";
	} 
	
	public static String in2(List<?> items) {
		if(CollectionUtils.isEmpty(items)) {
			return null;
		}
		StringBuilder sql=null;
		for(Object item:items) {
			if(sql==null) {
				sql=new StringBuilder();
				sql.append(_in(item));
			}else {
				sql.append(",").append(_in(item));
			}
		}
		return sql.toString();
	}
	
	public static String in (List<?> items) {
		if(CollectionUtils.isEmpty(items)) {
			return null;
		}
		StringBuilder sql=null;
		for(Object item:items) {
			if(sql==null) {
				sql=new StringBuilder();
				sql.append(_in(item));
			}else {
				sql.append(",").append(_in(item));
			}
		}
		return "("+sql.toString()+")";
	}

	public static String in (Set<?> items) {
		if(CollectionUtils.isEmpty(items)) {
			return null;
		}
		StringBuilder sql=null;
		for(Object item:items) {
			if(sql==null) {
				sql=new StringBuilder();
				sql.append(_in(item));
			}else {
				sql.append(",").append(_in(item));
			}
		}
		return "("+sql.toString()+")";
	}
	
	private static String _in(Object item) {
		if (item instanceof String) {
			String str=(String)item;
			return "'"+str.trim()+"'";
		}else if(item instanceof Integer) {
			Integer i=(Integer)item;
			return i.toString();
		}else if(item instanceof Double) {
			return ((Double)item).toString();
		}else if(item instanceof Float) {
			return ((Float)item).toString();
		}else if(item instanceof BigDecimal) {
			return ((BigDecimal)item).doubleValue()+"";
		}
		return null;
	}
	
	public static void main(String[] args) {
		Set<String> items=new HashSet<>();
		items.add("1");
		items.add("2");
		System.out.println(SqlUtils.in(items));
	}
}
