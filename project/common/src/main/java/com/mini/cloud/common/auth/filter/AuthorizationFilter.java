package com.mini.cloud.common.auth.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.mini.cloud.common.auth.constant.AuthConstant;
import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.auth.AuthSecurityManager;
import com.mini.cloud.common.bean.BaseResult;
import com.mini.cloud.common.cache.CacheService;
import com.mini.cloud.common.exception.BusinessException;

public class AuthorizationFilter implements Filter{
	
	
	private CacheService cacheService;
	
	private  Map<String, String> filterMap; 
	
	public AuthorizationFilter(CacheService cacheService,Map<String, String> filterMap) {
		this.cacheService=cacheService;
		this.filterMap=filterMap;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
//		Filter.super.init(filterConfig);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		//System.out.println("=======================================filter:"+new Date().getTime());
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		HttpServletResponse httpResponse=(HttpServletResponse) response;
//		String token ="wjj001";
//		LoginUserInfo user=new LoginUserInfo();
//		user.setId(1);
//		user.setUserName("admin");
//		request.setAttribute(AuthConstant.REQ_USER_KEY, user);
//		chain.doFilter(request, response);
		
		
		String token = httpRequest.getHeader("token");
		String lang= httpRequest.getHeader("lang");
		request.setAttribute(AuthConstant.REQ_LANG_KEY, lang);
//		token ="token_01";
		String url= httpRequest.getServletPath();
		LoginUserInfo user=null;
		try {
			 user= AuthSecurityManager.getInstall(this.filterMap).check(url, token, this.cacheService);
			 if(user!=null) {
				 request.setAttribute(AuthConstant.REQ_USER_KEY, user);
			 }
			
			 chain.doFilter(request, response);
		} catch (BusinessException e) {
			httpResponse.setStatus(401);
			httpResponse.setContentType("application/json;charset=UTF-8");
			httpResponse.setCharacterEncoding("utf-8");
			BaseResult result= BaseResult.error(e.getCode(), e.getMessage());
			httpResponse.getWriter().write(JSON.toJSONString(result));
		}
	}
	

	@Override
	public void destroy() {
//		Filter.super.destroy();
	}
}
