package com.mini.cloud.common.auth.intercepter;

import com.mini.cloud.common.auth.AuthSecurityManager;
import com.mini.cloud.common.auth.annotation.Authc;
import com.mini.cloud.common.auth.annotation.Login;
import com.mini.cloud.common.auth.constant.AuthConstant;
import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.auth.handle.AuthHandle;
import com.mini.cloud.common.cache.CacheService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthorizationAnnotationInterceptor extends HandlerInterceptorAdapter{
	
	private CacheService cacheService;

	public AuthorizationAnnotationInterceptor(CacheService cacheService) {
		this.cacheService=cacheService;
	}
	
	

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		System.out.println("+++++ AuthorizationAnnotationInterceptor:"+System.currentTimeMillis());
		System.out.println("+++++ contextPath="+request.getContextPath());
		System.out.println("++++ full url="+request.getRequestURI());
		System.out.println("+++++ url="+request.getServletPath());

		boolean checkLoginFlag=false;
		String handleName=null;
		
		String lang= request.getHeader("lang");
		lang="zh";
		request.setAttribute(AuthConstant.REQ_LANG_KEY, lang);
		
        if(handler instanceof HandlerMethod) {
        	HandlerMethod method=(HandlerMethod) handler;
        	Class<?> classType=method.getBeanType();
        	if(method.getMethodAnnotation(Login.class) !=null || classType.getAnnotation(Login.class) !=null) {
        		checkLoginFlag=true;
        		handleName= AuthHandle.Login;
        	}else if(method.getMethodAnnotation(Authc.class) !=null || classType.getAnnotation(Authc.class) !=null) {
        		checkLoginFlag=true;
        		handleName=AuthHandle.Authc;
        	}
        }
        if(checkLoginFlag==false) {
        	return true;
        }
		
		String token = request.getHeader("token");
		String url= request.getServletPath();
		
		LoginUserInfo user= AuthSecurityManager.getInstall().check(handleName, url, token,cacheService);
		if(user!=null) {
			request.setAttribute(AuthConstant.REQ_USER_KEY, user);
		}
		return true;
	}
	
}
