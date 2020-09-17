package com.mini.cloud.common.auth.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mini.cloud.common.auth.intercepter.WebHttpServletRequestWrapper;

public class WebServletRequestReplacedFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
        	WebHttpServletRequestWrapper webrequestWrapper = new WebHttpServletRequestWrapper((HttpServletRequest) request);
            String content= webrequestWrapper.getBodyContent();

            JSONObject obj= JSON.parseObject(content);
            if(null != obj){
				for(Map.Entry<String, Object>  entity:obj.entrySet()) {
					System.out.println("key = "+entity.getKey());
					System.out.println("val = "+entity.getValue().toString());
					System.out.println("---------");
				}
			}

        	requestWrapper = webrequestWrapper;
        }
        //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
        // 在chain.doFiler方法中传递新的request对象
        if(requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
	}

}
