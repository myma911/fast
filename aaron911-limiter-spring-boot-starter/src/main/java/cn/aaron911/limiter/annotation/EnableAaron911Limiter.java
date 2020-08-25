package cn.aaron911.limiter.annotation;


import org.springframework.context.annotation.Import;

import cn.aaron911.limiter.RedisLimiterHelper;
import cn.aaron911.limiter.aop.LimitInterceptor;

import java.lang.annotation.*;

/**
 * 在springboot application 类上启用注解 
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({LimitInterceptor.class,
	RedisLimiterHelper.class})
public @interface EnableAaron911Limiter{

}
