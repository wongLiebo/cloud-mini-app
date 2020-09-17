package com.mini.cloud.common.util;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

public class HttpContextUtils {

	
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	
	public static UA getUA() {
		ServletRequestAttributes servletRequest = tryGetRequestAttributes();
		if(servletRequest==null) {
			return null;
		}
		HttpServletRequest request=servletRequest.getRequest();
		String userAgent= ServletUtil.getHeaderIgnoreCase(request, "User-Agent");
		UserAgent ua= UserAgentUtil.parse(userAgent);
		
		UA result=new UA();
		result.setBrowser(ua.getBrowser().toString());
		result.setVersion(ua.getVersion());
		result.setEngine(ua.getEngine().toString());
		result.setEngineVersion(ua.getEngineVersion());
		result.setOs(ua.getOs().toString());
		result.setPlatform(ua.getPlatform().toString());
		result.setMobile(ua.isMobile());
//		Dict dict=Dict.create();
//		dict.set("browser", ua.getBrowser().toString());
//		dict.set("version", ua.getVersion());
//		dict.set("engine", ua.getEngine().toString());
//		dict.set("engineVersion", ua.getEngineVersion());
//		dict.set("os", ua.getOs().toString());
//		dict.set("platform", ua.getPlatform().toString());
//		dict.set("isMobile", ua.isMobile());
		return result;
	}
	
	public static String getClientIP() {
		ServletRequestAttributes servletRequest = tryGetRequestAttributes();
		if(servletRequest==null) {
			return null;
		}
		HttpServletRequest request=servletRequest.getRequest();
		if(request==null) {
			return null;
		}
		return ServletUtil.getClientIP(request);
	}

	public static void addCookie(String name, String value, int maxAgeInSeconds) {
		HttpServletResponse response=getResponse();
		if(response==null) {
			return;
		}
		ServletUtil.addCookie(response, name, value, maxAgeInSeconds);
	}
	
	public static void addCookie(String name,String value) {
		HttpServletResponse response=getResponse();
		if(response==null) {
			return;
		}
		ServletUtil.addCookie(response, name, value);
	}
	
	public static void addCookie(String name, String value, int maxAgeInSeconds, String path, String domain) {
		HttpServletResponse response=getResponse();
		if(response==null) {
			return;
		}
		ServletUtil.addCookie(response, name, value, maxAgeInSeconds, path, domain);
	}
	
	public static Cookie getCookie(String name) {
		HttpServletRequest request=getRequest();
		if(request==null) {
			return null;
		}
		Cookie cookie=ServletUtil.getCookie(request, name);
		return cookie;
	}
	
	/**
	 * 获取Cookie
	 * */
	public static Map<String, Cookie> getCookie() {
		HttpServletRequest request=getRequest();
		if(request==null) {
			return null;
		}
		return ServletUtil.readCookieMap(request);
	}
	
	/**
	 * 获取请求参数
	 * */
	public static Map<String, Object> getDataMap(){
		HttpServletRequest request = getRequestAttributes().getRequest();
		return MapDataUtil.convertDataMap(request);
	}
	
	 public static HttpServletRequest getRequest() {
	        HttpServletRequest request = getRequestAttributes().getRequest();
	        return request;
	 }

    public static HttpServletResponse getResponse() {
        HttpServletResponse response = getRequestAttributes().getResponse();
        return response;
    }
	
	public static ServletRequestAttributes getRequestAttributes() {
		ServletRequestAttributes servletRequestAttributes = tryGetRequestAttributes();
        return servletRequestAttributes;
	}
	
	public static ServletRequestAttributes tryGetRequestAttributes() {
		RequestAttributes attributes = null;
        try {
            attributes = RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException e) {
        }
        if (attributes == null) {
            return null;
        }
        if (attributes instanceof ServletRequestAttributes) {
            return (ServletRequestAttributes) attributes;
        }
        return null;
	}
	
	
}
