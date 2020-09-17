package com.mini.cloud.common.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.mini.cloud.common.constant.BaseCodeEnum;
import org.springframework.util.StringUtils;


public class CheckUtils {

	/**NULL:true*/
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}
	
	/**NULL:true*/
	public static boolean isEmpty(Integer i) {
		if(i==null ) {
			return true;
		}else {
			return false;
		}
	} 
	
	public static boolean hasBaseCodeEnum(BaseCodeEnum e) {
		if(e!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**NULL:false*/
	public static boolean hasText(String str) {
		return StringUtils.hasText(str);
	}
	
	public static boolean hasObj(Object obj) {
		if(obj!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**NULL:false*/
	public static boolean hasInt(Integer i) {
		if(i!=null && i>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean hasBigDecimal(BigDecimal b) {
		if(b==null) {
			return false;
		}
		return b.doubleValue()>0?true:false;
	}
	
	/**NULL:false*/
	public static boolean hasList(List<?> v) {
		if(v!=null && v.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean hasSet(Set<?> v) {
		if(v!=null && v.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	
//	public static String getVal(String val,String defVal) {
//		if(StringUtils.isEmpty(val)) {
//			return defVal;
//		}else {
//			return val;
//		}
//	}

}
