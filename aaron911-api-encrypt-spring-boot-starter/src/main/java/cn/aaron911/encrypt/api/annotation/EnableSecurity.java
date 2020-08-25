package cn.aaron911.encrypt.api.annotation;


import org.springframework.context.annotation.Import;

import cn.aaron911.encrypt.api.advice.EncryptRequestBodyAdvice;
import cn.aaron911.encrypt.api.advice.EncryptResponseBodyAdvice;
import cn.aaron911.encrypt.api.config.SecretKeyConfig;

import java.lang.annotation.*;

/**
 * 
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({SecretKeyConfig.class,
        EncryptResponseBodyAdvice.class,
        EncryptRequestBodyAdvice.class})
public @interface EnableSecurity{

}
