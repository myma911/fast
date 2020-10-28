package cn.aaron911.encrypt.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cn.aaron911.encrypt.api.advice.DecryptRequestBodyAdvice;
import cn.aaron911.encrypt.api.advice.EncryptResponseBodyAdvice;
import cn.aaron911.encrypt.api.config.EncryptConfig;

/**
 * spring 中使用接口加密解密
 * 
 */
@Configuration
@Import({EncryptConfig.class,
    EncryptResponseBodyAdvice.class,
    DecryptRequestBodyAdvice.class})
public class EnableEncryptBodyConfig {

}
