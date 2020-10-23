package cn.aaron911.log.trace.annotation;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

import cn.aaron911.log.trace.aspect.TraceAspect;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({TraceAspect.class})
public @interface EnableTrace {


}
