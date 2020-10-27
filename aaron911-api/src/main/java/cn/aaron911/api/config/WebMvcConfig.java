package cn.aaron911.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.aaron911.api.interceptor.AuthorizationInterceptor;
import cn.aaron911.api.interceptor.TraceIdInterceptor;
import cn.aaron911.api.resolver.LoginUserHandlerMethodArgumentResolver;

/**
 * MVC配置WebMvcConfigurer
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private TraceIdInterceptor traceIDInterceptor;
	
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceIDInterceptor).addPathPatterns("/**");
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }
}