package cn.aaron911.lock.redisson.aspect;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.aaron911.lock.redisson.annotation.RedissonLock;
import cn.aaron911.lock.redisson.util.RedissonLockUtil;

/**
 * AOP 同步锁
 * 使用redis 实现 
 * @transaction 中  order 大小的说明
 * https://docs.spring.io/spring/docs/4.3.14.RELEASE/spring-framework-reference/htmlsingle/#transaction-declarative-annotations
 * https://docs.spring.io/spring/docs/4.3.14.RELEASE/javadoc-api/
 */
@Component
@Scope
@Aspect
@Order(1)
//order越小越是最先执行，但更重要的是最先执行的最后结束。order默认值是2147483647
public class LockAspect {

	@Autowired
	private RedissonLockUtil redissLockUtil;
	
	
	//切点
	@Pointcut("@annotation(cn.aaron911.lock.redisson.annotation.RedissonLock)")  
	public void lockAspect() {}
	
    @Around("lockAspect()")
    public  Object around(ProceedingJoinPoint pjp) {

    	Signature signature = pjp.getSignature();//此处joinPoint的实现类是MethodInvocationProceedingJoinPoint
		MethodSignature methodSignature = (MethodSignature) signature;//获取参数名
		RedissonLock redissonLock = methodSignature.getMethod().getAnnotation(RedissonLock.class);
    	
    	boolean getLock = false;
    	Object obj = null;
		try {
			getLock = redissLockUtil.tryLock(redissonLock.description(), TimeUnit.SECONDS, 3, 20);
			if (getLock) {
				obj = pjp.proceed();				
			}else {
				throw new RuntimeException("获取锁失败");
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);       
		} finally{
			redissLockUtil.unlock(redissonLock.description());
		}
    	return obj;
    } 
}
