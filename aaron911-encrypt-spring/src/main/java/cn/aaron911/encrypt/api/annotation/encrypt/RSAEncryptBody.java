package cn.aaron911.encrypt.api.annotation.encrypt;

import java.lang.annotation.*;

/**
 * RSA 加密 
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RSAEncryptBody {
}
