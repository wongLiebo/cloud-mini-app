package com.mini.cloud.common.auth.handle;


import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.cache.CacheService;
import com.mini.cloud.common.exception.BusinessException;

/**
 * 匿名处理
 * */
public class AnonHandle implements AuthHandle{

	private static AnonHandle _handle=new AnonHandle();
	private AnonHandle() {
	}
	public static AnonHandle getInstall() {
		return _handle;
	}
	
	@Override
	public LoginUserInfo handle(String token, String url, CacheService cacheService) throws BusinessException {
		return null;
	}
	
}
