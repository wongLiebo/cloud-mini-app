package com.mini.cloud.common.auth.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.mini.cloud.common.auth.constant.AuthConstant;
import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.cache.CacheKeys;
import com.mini.cloud.common.cache.CacheService;
import com.mini.cloud.common.constant.BaseConstant;
import com.mini.cloud.common.constant.BaseReturnEnum;
import com.mini.cloud.common.exception.BusinessException;
import com.mini.cloud.common.util.HttpContextUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AuthContextUtils {

	public static LoginUserInfo getUser()throws BusinessException {
		HttpServletRequest request= HttpContextUtils.getRequest();
	    Object object =request.getAttribute(AuthConstant.REQ_USER_KEY);
        if(object == null){
        	 throw new BusinessException(BaseReturnEnum.NOT_LOGIN);
        }
        LoginUserInfo user =(LoginUserInfo)object;
        user.setIp(ServletUtil.getClientIP(request));
        return user;
	}
	
	
	public static String getLang() throws BusinessException{
		HttpServletRequest request=HttpContextUtils.getRequest();
		Object object= request.getAttribute(AuthConstant.REQ_LANG_KEY);
		if(object==null) {
			return BaseConstant.LangEnum.ZH.getCode();
		}else {
			return object.toString();
		}
	}
	
	
	public static String getSaasCompany() {
		HttpServletRequest request=HttpContextUtils.getRequest();
		String saas = request.getHeader("saas");
		if(StringUtils.isEmpty(saas)) {
			return null;
		}
		return saas;
	}
	
	public static LoginUserInfo getUserNoValidate() {
		HttpServletRequest request=HttpContextUtils.getRequest();
	    Object object =request.getAttribute(AuthConstant.REQ_USER_KEY);
        if(object == null){
        	return null;
        }
        LoginUserInfo user =(LoginUserInfo)object;
        user.setIp(ServletUtil.getClientIP(request));
        return user;
	}
	
	public static LoginUserInfo getUser2Head(CacheService cacheService) {
		HttpServletRequest request=HttpContextUtils.getRequest();
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		LoginUserInfo loginUserInfo = cacheService.get(CacheKeys.getUserKey(token), LoginUserInfo.class);
		if(loginUserInfo != null){
	        return loginUserInfo;
	    }
		return null;
	}
	
}
