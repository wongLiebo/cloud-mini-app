package com.mini.cloud.common.auth;

import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.auth.handle.AuthHandle;
import com.mini.cloud.common.auth.handle.AuthHandleFactory;
import com.mini.cloud.common.cache.CacheService;
import com.mini.cloud.common.exception.BusinessException;
import com.mini.cloud.common.util.RegexUtils;

import java.util.HashMap;
import java.util.Map;


public class AuthSecurityManager {

	private static Object lock = new Object();
	private  Map<String, String> filterMap=null;
	
	
	private static AuthSecurityManager _sm=null;
	
	private AuthSecurityManager(Map<String, String> filterMap) {
		this.filterMap=filterMap;
	}
	
	public static AuthSecurityManager getInstall() {
		return AuthSecurityManager.getInstall(null);
	}
	
	public static AuthSecurityManager getInstall(Map<String, String> filterMap) {
		if(_sm==null) {
			synchronized (lock) {
				if(_sm==null) {
					_sm=new AuthSecurityManager(filterMap);
				}
			 }
		}
		return _sm;
	}
	
	
	private String getHandleName(String url) {
		if(this.filterMap==null) {
			return null;
		}
		for(String keyRegex:this.filterMap.keySet()) {
			if(RegexUtils.isAuthc(url, keyRegex)) {
				return this.filterMap.get(keyRegex);
			}
		}
		return null;
	}
	
	private AuthHandle getHandle(String url) {
		String name= this.getHandleName(url);
		return AuthHandleFactory.getAuthHandle(name);
	}
	
	
	public LoginUserInfo check(String handleName, String url, String token, CacheService cacheService) throws BusinessException{
		AuthHandle handle=AuthHandleFactory.getAuthHandle(handleName);
		return handle.handle(token, url, cacheService);
	}
	
	public LoginUserInfo check(String url,String token,CacheService cacheService) throws BusinessException {
		AuthHandle handle=this.getHandle(url);
		if(handle!=null) {
			return handle.handle(token, url, cacheService);
		}else {
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		Map<String, String> filterMap=new HashMap<String, String>();
		filterMap.put("/swagger/**", "anon");
	    filterMap.put("/v2/api-docs", "anon");
	    filterMap.put("/swagger-ui.html", "anon");
	    filterMap.put("/webjars/**", "anon");
	    filterMap.put("/swagger-resources", "anon");
	    filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/statics/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/**", "authc");
        
//        Pattern pattern = Pattern.compile("/.*");  
//        Matcher matcher = pattern.matcher("/user/add/1");  
//        System.out.println(matcher.matches());
        
        String str="AbcDe";
        System.out.println(str.toLowerCase());
	}
	
	
}
