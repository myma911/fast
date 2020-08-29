package cn.aaron911.lock.redisson.annotation;


import org.springframework.context.annotation.Import;

import cn.aaron911.lock.redisson.aspect.LockAspect;
import cn.aaron911.lock.redisson.config.RedissonAutoConfiguration;
import cn.aaron911.lock.redisson.listener.RedissonLockLogoApplactionListener;

import java.lang.annotation.*;

/**
 * 启用RedissonLock, 注释在spring boot 启动类上
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({
	RedissonAutoConfiguration.class,
	LockAspect.class,
	RedissonLockLogoApplactionListener.class
})
public @interface EnableRedissonLock{

}
