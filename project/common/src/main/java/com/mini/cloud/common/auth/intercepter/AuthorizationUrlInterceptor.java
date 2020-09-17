package com.mini.cloud.common.auth.intercepter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mini.cloud.common.auth.AuthSecurityManager;
import com.mini.cloud.common.auth.constant.AuthConstant;
import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.cache.CacheService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class AuthorizationUrlInterceptor extends HandlerInterceptorAdapter{

	
	private CacheService cacheService;
	
	private  Map<String, String> filterMap; 

	public AuthorizationUrlInterceptor(CacheService cacheService,Map<String, String> filterMap) {
		this.cacheService=cacheService;
		this.filterMap=filterMap;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
//		System.out.println("======================================= AuthorizationUrlInterceptor:"+new Date().getTime());
		String token = request.getHeader("token");
		String url= request.getServletPath();
		LoginUserInfo user= AuthSecurityManager.getInstall(this.filterMap).check(url, token, this.cacheService);
		if(user!=null) {
			request.setAttribute(AuthConstant.REQ_USER_KEY, user);
		}
		return true;
	}
	
	
}
