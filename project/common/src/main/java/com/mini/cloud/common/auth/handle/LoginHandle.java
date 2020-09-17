package com.mini.cloud.common.auth.handle;

import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.cache.CacheKeys;
import com.mini.cloud.common.cache.CacheService;
import com.mini.cloud.common.constant.BaseReturnEnum;
import com.mini.cloud.common.exception.BusinessException;
import org.springframework.util.StringUtils;



public class LoginHandle implements AuthHandle{

	private static LoginHandle _handle=new LoginHandle();
	private LoginHandle() {
		
	}
	public static LoginHandle getInstall() {
		return _handle;
	}
	
	@Override
	public LoginUserInfo handle(String token, String url, CacheService cacheService) throws BusinessException {
		 if(StringUtils.isEmpty(token)){
	         throw new BusinessException(BaseReturnEnum.NOT_LOGIN);
	     }
		 LoginUserInfo loginUserInfo = cacheService.get(CacheKeys.getUserKey(token), LoginUserInfo.class);

		 if(loginUserInfo == null){
	        	throw new BusinessException(BaseReturnEnum.NOT_LOGIN);
	     }
		 return loginUserInfo;
	}
	
}
