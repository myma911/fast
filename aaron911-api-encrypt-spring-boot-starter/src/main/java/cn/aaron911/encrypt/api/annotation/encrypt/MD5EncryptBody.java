package cn.aaron911.encrypt.api.annotation.encrypt;

import java.lang.annotation.*;

/**
 * MD5 加密
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MD5EncryptBody {
}
