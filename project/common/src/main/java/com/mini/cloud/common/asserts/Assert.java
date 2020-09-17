package com.mini.cloud.common.asserts;


import com.mini.cloud.common.exception.BusinessException;

public class Assert {

	public static void isNull(Object obj) {
		isNull(obj, "对象不为空");
	}
	
	public static void isNull(Object obj, String msg) {
		if( null != obj ) {
			throw new BusinessException(msg);
		}
	}
	
	public static void isNotNull(Object obj) {
		isNotNull(obj, "对象为空");
	}
	
	public static void isNotNull(Object obj, String msg) {
		if( null == obj ) {
			throw new BusinessException(msg);
		}
	}
	
	
	
	public static boolean isNotNull2Zero(Integer id) {
		if(id==null || id <=0) {
			return false;
		}
		return true;
	}
	
	
}
