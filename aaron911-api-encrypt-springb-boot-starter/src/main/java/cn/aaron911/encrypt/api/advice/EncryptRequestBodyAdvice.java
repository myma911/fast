package cn.aaron911.encrypt.api.advice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import cn.aaron911.encrypt.api.annotation.Decrypt;
import cn.aaron911.encrypt.api.config.SecretKeyConfig;

import java.lang.reflect.Type;

/**
 * 
 **/
@ControllerAdvice
public class EncryptRequestBodyAdvice  implements RequestBodyAdvice {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean encrypt;

    @Autowired
    private SecretKeyConfig secretKeyConfig;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (methodParameter.getMethod().isAnnotationPresent(Decrypt.class) && secretKeyConfig.isOpen()) {
            encrypt = true;
        }
        return encrypt;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType){
        if (encrypt) {
            try {
                return new DecryptHttpInputMessage(inputMessage, secretKeyConfig.getPrivateKey(), secretKeyConfig.getCharset(),secretKeyConfig.isShowLog());
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
