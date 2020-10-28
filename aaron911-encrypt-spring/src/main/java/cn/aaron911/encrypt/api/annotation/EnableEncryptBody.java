package cn.aaron911.encrypt.api.annotation;


import org.springframework.context.annotation.Import;

import cn.aaron911.encrypt.api.advice.DecryptRequestBodyAdvice;
import cn.aaron911.encrypt.api.advice.EncryptResponseBodyAdvice;
import cn.aaron911.encrypt.api.config.EncryptConfig;

import java.lang.annotation.*;

/**
 * 在springboot 启动类上添加该注解，启动对接口解密，加密功能
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({EncryptConfig.class,
        EncryptResponseBodyAdvice.class,
        DecryptRequestBodyAdvice.class})
public @interface EnableEncryptBody{

}
