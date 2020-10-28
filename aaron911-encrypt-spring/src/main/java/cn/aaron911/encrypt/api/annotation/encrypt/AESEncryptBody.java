package cn.aaron911.encrypt.api.annotation.encrypt;

import java.lang.annotation.*;

/**
 * AES 加密
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AESEncryptBody {

    String otherKey() default "";

}
