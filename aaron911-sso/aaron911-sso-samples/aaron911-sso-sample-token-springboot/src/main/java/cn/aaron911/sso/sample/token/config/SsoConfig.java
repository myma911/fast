package cn.aaron911.sso.sample.token.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.aaron911.sso.core.conf.Conf;
import cn.aaron911.sso.core.filter.SsoTokenFilter;
import cn.aaron911.sso.core.util.JedisUtil;

@Configuration
public class SsoConfig implements DisposableBean {


    @Value("${sso.server}")
    private String ssoServer;

    @Value("${sso.logout.path}")
    private String ssoLogoutPath;

    @Value("${sso.redis.address}")
    private String ssoRedisAddress;

    @Value("${sso.excluded.paths}")
    private String ssoExcludedPaths;


    @Bean
    public FilterRegistrationBean<SsoTokenFilter> ssoFilterRegistration() {

        // sso, redis init
        JedisUtil.init(ssoRedisAddress);

        // sso, filter init
        FilterRegistrationBean<SsoTokenFilter> registration = new FilterRegistrationBean<SsoTokenFilter>();

        registration.setName("SsoWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new SsoTokenFilter());
        registration.addInitParameter(Conf.SSO_SERVER, ssoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, ssoLogoutPath);
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, ssoExcludedPaths);

        return registration;
    }

    @Override
    public void destroy() throws Exception {

        // sso, redis close
        JedisUtil.close();
    }

}
