package com.mini.cloud.common.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mini.cloud.common.constant.BaseCodeEnum;
import org.springframework.util.StringUtils;

import cn.hutool.core.util.ReflectUtil;

public class EnumUtils {

	public static <E extends Enum<E>> LinkedHashMap<Object, E> getEnumMap(final Class<E> enumClass,String fieldName) {
		final LinkedHashMap<Object, E> map = new LinkedHashMap<Object, E>();
		for (final E e : enumClass.getEnumConstants()) {
			map.put(ReflectUtil.getFieldValue(e, fieldName), e);
		}
		return map;
	}
	
	public static <E extends Enum<E>> E getEnum(final Class<E> enumClass,String fieldName,String val) {
		for(final E e : enumClass.getEnumConstants()) {
			String key=ReflectUtil.getFieldValue(e, fieldName).toString();
			if(key.equals(val)) {
				return e;
			}
		}
		return null;
	}

	public static <E extends BaseCodeEnum> LinkedHashMap<Object, E> getBaseCodeEnumMap(final Class<E> enumClass, String fieldName) {
		final LinkedHashMap<Object, E> map = new LinkedHashMap<Object, E>();
		for (final E e : enumClass.getEnumConstants()) {
			map.put(ReflectUtil.getFieldValue(e, fieldName), e);
		}
		return map;
	}
	
	public static <E extends BaseCodeEnum> Map<String, String> getBaseCodeEnumMap(final Class<E> enumClass){
		Map<String, String> map=new HashMap<String, String>();
		for (final E e : enumClass.getEnumConstants()) {
			Object val=ReflectUtil.getFieldValue(e, "msg");
			map.put(ReflectUtil.getFieldValue(e, "code").toString(), val==null?"":val.toString());
		}
		return map;
	}

	public static <E extends BaseCodeEnum> E getBaseCodeEnum(final Class<E> enumClass,String code){
		if(StringUtils.isEmpty(code)) {
			return null;
		}
		for(final E e : enumClass.getEnumConstants()) {
			String _code= ReflectUtil.getFieldValue(e, "code").toString();
			if(_code.equals(code)) {
				return e;
			}
		}
		return null;
	}
	
}
