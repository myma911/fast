package cn.aaron911.esclientrhl.annotation;

import java.lang.annotation.*;

/**
 * description: ES entity 标识ID的注解,在es entity field上添加
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ESID {
}
