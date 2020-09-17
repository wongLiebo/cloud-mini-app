package com.mini.cloud.common.bean;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


import cn.hutool.core.bean.DynaBean;
import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.constant.BaseConstant;

public class BaseEntity {
	
	/**
	 * 添加新增参数
	 * @param userInfo
	 */
	public void addCreateParam(LoginUserInfo userInfo) {
		Map<String, Class<?>> fieldNameMap=new HashMap<String, Class<?>>();
		Field[] field =this.getClass().getDeclaredFields();
        for(int i=0 ; i<field.length ; i++){  
              fieldNameMap.put(field[i].getName(), field[i].getType());
        }
        
        DynaBean bean = DynaBean.create(this);
        
        if(fieldNameMap.containsKey("rowsState")) {
        	bean.set("rowsState", BaseConstant.EnabledEnum.ENABLED.getCode());
        }
        
        if(userInfo!=null) {
        	if(fieldNameMap.containsKey("cman")) {
        		Class<?> clazz= fieldNameMap.get("cman");
        		if(clazz==String.class) {
        			bean.set("cman", userInfo.getId());
        		}
            	if(clazz==Integer.class) {
            		bean.set("cman", userInfo.getId());
            	}
            }
            if(fieldNameMap.containsKey("cip")) {
            	bean.set("cip", userInfo.getIp());
            }
            
        	if(fieldNameMap.containsKey("eman")) {
        		Class<?> clazz= fieldNameMap.get("eman");
        		if(clazz==String.class) {
        			bean.set("eman", userInfo.getId());
        		}
            	if(clazz==Integer.class) {
            		bean.set("eman", userInfo.getId());
            	}
            }
            if(fieldNameMap.containsKey("eip")) {
            	bean.set("eip", userInfo.getIp());
            }
        }
        
        if(fieldNameMap.containsKey("cdt")) {
        	bean.set("cdt", LocalDateTime.now());
        }
        if(fieldNameMap.containsKey("edt")) {
        	bean.set("edt", LocalDateTime.now());
        }
	}

	/**
	 * 添加修改参数
	 * @param userInfo
	 */
	public void addEditParam(LoginUserInfo userInfo) {
		Map<String, Class<?>> fieldNameMap=new HashMap<String, Class<?>>();
		Field[] field =this.getClass().getDeclaredFields();
        for(int i=0 ; i<field.length ; i++){  
        	fieldNameMap.put(field[i].getName(), field[i].getType());
        }
        DynaBean bean = DynaBean.create(this);
        if(userInfo!=null) {
        	if(fieldNameMap.containsKey("eman")) {
        		Class<?> clazz= fieldNameMap.get("eman");
        		if(clazz==String.class) {
        			bean.set("eman", userInfo.getId());
        		}
            	if(clazz==Integer.class) {
            		bean.set("eman", userInfo.getId());
            	}
            }
            if(fieldNameMap.containsKey("eip")) {
            	bean.set("eip", userInfo.getIp());
            }
        }
        if(fieldNameMap.containsKey("edt")) {
        	bean.set("edt", LocalDateTime.now());
        }
	}

	
	
}
