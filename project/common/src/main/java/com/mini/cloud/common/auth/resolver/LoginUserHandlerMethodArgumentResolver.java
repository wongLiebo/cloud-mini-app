package com.mini.cloud.common.auth.resolver;


import com.mini.cloud.common.auth.annotation.LoginUser;
import com.mini.cloud.common.auth.constant.AuthConstant;
import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.constant.BaseReturnEnum;
import com.mini.cloud.common.exception.BusinessException;
import com.mini.cloud.common.util.IpUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{

	private boolean isNullException=true;
	
	public LoginUserHandlerMethodArgumentResolver(boolean isNullException) {
		this.isNullException=isNullException;
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
	    return parameter.getParameterType().isAssignableFrom(LoginUserInfo.class)
				&& parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
        Object object = webRequest.getAttribute(AuthConstant.REQ_USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
        	if(this.isNullException) {
        		throw new BusinessException(BaseReturnEnum.NOT_LOGIN);
        	}else {
        		return null;
        	}
        }
        LoginUserInfo user =(LoginUserInfo)object;
        user.setIp(IpUtils.getOpIp());
        return user;
	}

}
