package com.mini.cloud.common.auth.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.mini.cloud.common.auth.annotation.April;
import com.mini.cloud.common.util.SpringContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class AprilInterceptor extends HandlerInterceptorAdapter{

	public AprilInterceptor() {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			April annotation = method.getAnnotation(April.class);
			if (annotation != null) {
				if(repeatDataValidator(request)){
					//请求数据相同
					System.out.println("please don't repeat submit,url:"+ request.getServletPath());
					JSONObject result = new JSONObject();
					result.put("statusCode","500");
					result.put("message","请勿重复请求");
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().write(result.toString());
					response.getWriter().close();
					return false;
				}else {//如果不是重复相同数据
					return true;
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}
	/**
	 * 验证同一个url的nonceStr是否相同提交,相同返回true
	 * @param request
	 * @return
	 */
	public boolean repeatDataValidator(HttpServletRequest request){
		String nonceStr = "";
		// 获取请求body
		try {
			WebHttpServletRequestWrapper requestWrapper = new WebHttpServletRequestWrapper(request);
			String body = requestWrapper.getBodyContent();
			Map<String, String> it = (Map<String, String>)JSONObject.parse(body);
			nonceStr = it.get("nonceStr");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		//如果没有随机字符串，直接放行
		if (null==nonceStr || "".equals(nonceStr)){
			return false;
		}
		String url = request.getServletPath();

//		RedisUtils redis = SpringContextUtils.getBean(RedisUtils.class);
//		String redisKey = nonceStr + url;
//		String nonceStrRedis = redis.get(redisKey);
//		System.out.println("nonceStrRedis===="+nonceStrRedis);
//		if(nonceStrRedis == null){
//			//如果上一个数据为null,表示还没有访问页面
//			//存放并且设置有效期，2秒
//			redis.set(redisKey, nonceStr, 2);
//			return false;
//		}else{//否则，已经访问过页面
//			if(nonceStrRedis.equals(nonceStr)){
//				//如果上次nonceStr和本次nonceStr相同，则表示重复添加数据
//				return true;
//			}else{//如果上次nonceStr和本次nonceStr不同，则不是重复提交
//				redis.set(redisKey, nonceStr, 2);
//				return false;
//			}
//		}
		return false;
	}

}
