package cn.aaron911.encrypt.api.annotation.decrypt;

import java.lang.annotation.*;

/**
 * DES 解密
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DESDecryptBody {

    String otherKey() default "";

}
