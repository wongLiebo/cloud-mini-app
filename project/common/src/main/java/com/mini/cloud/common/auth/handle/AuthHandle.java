package com.mini.cloud.common.auth.handle;


import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.cache.CacheService;
import com.mini.cloud.common.exception.BusinessException;

public interface AuthHandle {
	public String Anon="anon";
	public String Authc="authc";
	public String Login="login";
	
	public LoginUserInfo handle(String token, String url, CacheService cacheService)throws BusinessException;
	
}
