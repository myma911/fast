package cn.aaron911.encrypt.api.annotation.decrypt;

import java.lang.annotation.*;

/**
 * AES 解密
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AESDecryptBody {

    String otherKey() default "";

}
