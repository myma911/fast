package cn.aaron911.lock.redisson.annotation;


import org.springframework.context.annotation.Import;

import cn.aaron911.lock.redisson.config.RedissonAutoConfiguration;

import java.lang.annotation.*;

/**
 * 启用RedissonLock, 注释在spring boot 启动类上
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({RedissonAutoConfiguration.class})
public @interface EnableRedissonLock{

}
