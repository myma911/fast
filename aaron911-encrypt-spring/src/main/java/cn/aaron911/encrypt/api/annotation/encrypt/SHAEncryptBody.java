package cn.aaron911.encrypt.api.annotation.encrypt;



import java.lang.annotation.*;

import cn.aaron911.encrypt.api.enums.SHAEncryptType;

/**
 * SHA 加密
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SHAEncryptBody {

    SHAEncryptType value() default SHAEncryptType.SHA256;

}
