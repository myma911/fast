package cn.aaron911.log.server.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<CorsFilter> CorsFilter() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<CorsFilter>();
        registration.setFilter(new CorsFilter());
        List<String> urlList = new ArrayList<String>();
        urlList.add("/**");
        registration.setUrlPatterns(urlList);
        registration.setName("CorsFilter");
        registration.setOrder(0);
        return registration;
    }

    @Bean
    public LoginCheckInterceptor loginCheckInterceptor() {
        return new LoginCheckInterceptor();
    }
    
   

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/login");
        excludePath.add("/logout");
        registry.addInterceptor(loginCheckInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(excludePath);

    }
   
    
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
}
