package com.mini.cloud.common.auth.handle;

import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.auth.util.AuthCacheUtils;
import com.mini.cloud.common.cache.CacheKeys;
import com.mini.cloud.common.cache.CacheService;
import com.mini.cloud.common.constant.BaseReturnEnum;
import com.mini.cloud.common.exception.BusinessException;
import org.springframework.util.StringUtils;


public class AuthcHandle implements AuthHandle{

	
	private static AuthcHandle _handle=new AuthcHandle();
	private AuthcHandle() {
		
	}
	public static AuthcHandle getInstall() {
		return _handle;
	}
	
	@Override
	public LoginUserInfo handle(String token, String url, CacheService cacheService) throws BusinessException {
		 if(StringUtils.isEmpty(token)){
	        throw new BusinessException(BaseReturnEnum.NOT_LOGIN);
	     }
		 LoginUserInfo user = cacheService.get(CacheKeys.getUserKey(token), LoginUserInfo.class);
		 if(user == null){
	        throw new BusinessException(BaseReturnEnum.NOT_LOGIN);
	     }
		 if(user.getRoles()==null || user.getRoles().size()<=0) {
			 throw new BusinessException(BaseReturnEnum.NOT_AUTHC);
		 }
		boolean authcFlag=AuthCacheUtils.checkAuthc(user.getCompanyNo(),url,user.getRoles(),cacheService);
		if(authcFlag==false) {
			 throw new BusinessException(BaseReturnEnum.NOT_AUTHC);
		}
		return user;
	}

	
}
