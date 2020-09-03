package cn.aaron911.encrypt.api.advice;


import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import cn.aaron911.encrypt.api.annotation.Decrypt;
import cn.aaron911.encrypt.api.config.EncryptConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现RequestBodyAdvice接口
 * 实现接收数据时解密
 **/
@ControllerAdvice
@Slf4j
public class DecryptRequestBodyAdvice  implements RequestBodyAdvice {

    private boolean decrypt;

    @Autowired
    private EncryptConfig encryptConfig;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (methodParameter.getMethod().isAnnotationPresent(Decrypt.class) && encryptConfig.isOpen()) {
        	decrypt = true;
        }
        return decrypt;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType){
        if (decrypt) {
            try {
                return new DecryptHttpInputMessage(inputMessage, encryptConfig);
            } catch (Exception e) {
                log.error("Decryption failed", e);
            }
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
