package cn.aaron911.encrypt.api.annotation.decrypt;

import java.lang.annotation.*;

/**
 * RSA 解密
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RSADecryptBody {
}
