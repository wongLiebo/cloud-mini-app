package com.mini.cloud.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import cn.hutool.core.util.StrUtil;

public class StrSplitUtils {
	
	/**数组转字符，分割符，*/
	public static String list2string(Collection<?> strs) {
		return list2string(strs, ",");
	}
	/**数组转字符*/
	public static String list2string_prefix(Collection<?> strs) {
		String str=list2string(strs, ",");
		if(StringUtils.isEmpty(str)) {
			return str;
		}else {
			return ","+str+",";
		}
		
	}
	/**数组按指定分隔符转字符*/
	public static String list2string(Collection<?> strs,String split) {
		if(strs==null || strs.size()<=0) {
			return null;
		}
		if(split==null || "".equals(split)) {
			return null;
		}
		
		final StringBuilder returnStr=new StringBuilder();
		strs.forEach(item->{
			if(item!=null) {
				if(returnStr.toString()==null || "".equals(returnStr.toString())) {
					returnStr.append(item);
				}else {
					returnStr.append(split).append(item);
				}
			}
		});
		return returnStr.toString();
	}
	/***/
	public static  <T> Set<T> parse2set(String text, Class<T> clazz) {
		return parse2set(text, ",", clazz);
	}
	/***/
	public static  <T> Set<T> parse2set(String text,String split, Class<T> clazz) {
		Set<T> set=new HashSet<T>();
		 if(StringUtils.isEmpty(text)) {
			 return set;
		 }
		 if(StringUtils.isEmpty(split)) {
			 return set;
		 }
		 if(clazz==null) {
			 return set;
		 }
		 String[] strs= text.split(split);
		 if(strs==null || strs.length<=0) {
			return set;
		 }
		 for(String str:strs) {
			 if(!StringUtils.isEmpty(str)) {
				 T obj=convert(str, clazz);
				 if(obj!=null) {
					 set.add(obj);
				 }
			 }
		 }
		 return set;
	}
	/***/
	public static  <T> List<T> parse2list(String text, Class<T> clazz) {
		return parse2list(text, ",", clazz);
	}
	/***/
	public static  <T> List<T> parse2list(String text,String split, Class<T> clazz) {
		 List<T> list=new ArrayList<T>();
		 if(StringUtils.isEmpty(text)) {
			 return list;
		 }
		 if(StringUtils.isEmpty(split)) {
			 return list;
		 }
		 if(clazz==null) {
			 return list;
		 }
		 String[] strs= text.split(split);
		 if(strs==null || strs.length<=0) {
			return list;
		 }
		 for(String str:strs) {
			 if(!StringUtils.isEmpty(str)) {
				 T obj=convert(str, clazz);
				 if(obj!=null) {
					 list.add(obj);
				 }
			 }
		 }
		 return list;
	 }
	
	
	
	public static String in(Object...objs) {
		StringBuilder str=new StringBuilder();
		for(Object obj:objs) {
			if(!StringUtils.isEmpty(str.toString())) {
				str.append(",");
			}
			if (obj instanceof String) {
				str.append("'").append(obj.toString()).append("'");
			} else if(obj instanceof Integer) {
				str.append(((Integer)obj).toString());
			} else if(obj instanceof Float) {
				str.append(((Integer)obj).toString());
			}else if(obj instanceof Double) {
				str.append(((Integer)obj).toString());
			}
		}
		return StrUtil.format("{} {} {}", "(" ,str.toString(),")");
	}
	
	/**
	 * In
	 * */
	public static String in(Collection<?> strs) {
		if(strs==null || strs.size()<=0) {
			return null;
		}
		StringBuilder str=new StringBuilder();
		strs.forEach(obj->{
			if(!StringUtils.isEmpty(str.toString())) {
				str.append(",");
			}
			if (obj instanceof String) {
				str.append("'").append(obj.toString()).append("'");
			} else if(obj instanceof Integer) {
				str.append(((Integer)obj).toString());
			} else if(obj instanceof Float) {
				str.append(((Integer)obj).toString());
			}else if(obj instanceof Double) {
				str.append(((Integer)obj).toString());
			}
		});
		return StrUtil.format("{} {} {}", "(" ,str.toString(),")");
	}
	
	@SuppressWarnings("unchecked")
	private static <T>T convert(String val, Class<T> clazz) {
		 if(clazz==String.class) {
			return (T)val;
		 }else if(clazz == Integer.class) {
			return (T)Integer.valueOf(val);
		 }else if(clazz ==Float.class) {
			return (T)Float.valueOf(val);
		 }else if(clazz==Double.class) {
			 return (T)Double.valueOf(val);
		 }
		return null;
	}
	
	
	
	public static void main(String[] args) {
//		JSON.toJSONString(object, filter, features)
		
//		List<String> list=new ArrayList<String>();
//		list.add("a");
//		list.add("b");
//		list.add("c");
//		
//		System.out.println(StrSplitUtils.list2str(list));
//		System.out.println(StrSplitUtils.list2str_prefix(list));
		
//		String str=",1,2,3,";
//		List<Integer> list= StrSplitUtils.parse2list(str, ",", Integer.class);
//		list.forEach(item->{
//			System.out.println(item);
//		});
		
//		List<String> objs=new ArrayList<String>();
//		objs.add("1");
//		objs.add("2");
		
//		List<Integer> objs=new ArrayList<Integer>();
//		objs.add(1);
//		objs.add(2);
//		System.out.println(StrSplitUtils.in(objs));
		
//		System.out.println(StrSplitUtils.in("1","2"));
	}
	
	
}
