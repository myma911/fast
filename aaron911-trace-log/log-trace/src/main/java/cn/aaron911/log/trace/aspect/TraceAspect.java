package cn.aaron911.log.trace.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;


@Aspect
@Component
@ConditionalOnMissingBean(value = AbstractAspect.class, ignored = TraceAspect.class)
public class TraceAspect extends AbstractAspect {
    @Around("@annotation(cn.aaron911.log.trace.annotation.Trace))")
    public Object around(JoinPoint joinPoint) throws Throwable {
        return aroundExecute(joinPoint);
    }

}
