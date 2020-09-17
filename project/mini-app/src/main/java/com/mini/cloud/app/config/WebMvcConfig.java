package com.mini.cloud.app.config;

import com.mini.cloud.common.auth.intercepter.AuthorizationAnnotationInterceptor;
import com.mini.cloud.common.auth.resolver.LoginUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Spring的拦截器配置
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 权限注解拦截器
        System.out.println("----------------权限注解拦截器-------------------------");
        registry.addInterceptor(new AuthorizationAnnotationInterceptor(null));
    }

    /**
     * controller请求扩展点配置
     * */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //@LoginUser配置
        System.out.println("----------------LoginUser配置-------------------------");
        argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver(false));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
