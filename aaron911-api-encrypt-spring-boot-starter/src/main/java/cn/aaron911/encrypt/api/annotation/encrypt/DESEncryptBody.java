package cn.aaron911.encrypt.api.annotation.encrypt;

import java.lang.annotation.*;

/**
 * DES 加密
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DESEncryptBody {

    String otherKey() default "";

}
