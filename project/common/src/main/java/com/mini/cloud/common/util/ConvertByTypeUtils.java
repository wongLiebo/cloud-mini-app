package com.mini.cloud.common.util;

public class ConvertByTypeUtils {

	
	public static String getStr(Object obj) {
		if(obj==null) {
			return null;
		}
		if (obj instanceof Integer) {
			return ((Integer) obj).toString();
		}else if(obj instanceof Long) {
			return ((Long) obj).toString();
		}else if(obj instanceof String) {
			return (String)obj;
		}
		
		return null;
	}
	
	public static Integer getInt(Object obj) {
		if(obj==null) {
			return null;
		}
		if (obj instanceof Integer) {
			return (Integer) obj;
		}else if(obj instanceof Long) {
			return Integer.parseInt(((Long)obj).toString());
			
		}else if(obj instanceof String) {
			return Integer.parseInt((String)obj);
		}
		
		return null;
	}
	
	public static Integer getInt(Object obj,Integer def) {
		Integer num=getInt(obj);
		return num==null?def:num;
	}
	
}
