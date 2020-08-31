package cn.aaron911.idempotent.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import cn.aaron911.idempotent.IdempotentComponent;
import cn.aaron911.idempotent.config.RedisConfig;
import cn.aaron911.idempotent.context.LogoApplactionListener;
import cn.aaron911.idempotent.interceptor.ApiIdempotentInterceptor;
import cn.aaron911.idempotent.property.IdempotentProperties;

/**
 * 在springboot application 类上启用注解 
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({
	LogoApplactionListener.class,
	RedisConfig.class,
	IdempotentProperties.class,
	IdempotentComponent.class,
	ApiIdempotentInterceptor.class
})
public @interface EnableIdempotent{

}
