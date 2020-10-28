package cn.aaron911.encrypt.api.advice;

import cn.aaron911.encrypt.api.annotation.decrypt.AESDecryptBody;
import cn.aaron911.encrypt.api.annotation.decrypt.DESDecryptBody;
import cn.aaron911.encrypt.api.annotation.decrypt.DecryptBody;
import cn.aaron911.encrypt.api.annotation.decrypt.RSADecryptBody;
import cn.aaron911.encrypt.api.config.EncryptConfig;
import cn.aaron911.encrypt.api.enums.DecryptBodyMethod;
import cn.aaron911.encrypt.api.exception.DecryptBodyFailException;
import cn.aaron911.encrypt.api.exception.DecryptMethodNotFoundException;
import cn.aaron911.encrypt.api.pojo.DecryptAnnotationInfoBean;
import cn.aaron911.encrypt.api.pojo.DecryptHttpInputMessage;
import cn.aaron911.encrypt.api.util.AESEncryptUtil;
import cn.aaron911.encrypt.api.util.CheckUtils;
import cn.aaron911.encrypt.api.util.DESEncryptUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 请求数据的加密信息解密处理<br>
 *     本类只对控制器参数中含有<strong>{@link org.springframework.web.bind.annotation.RequestBody}</strong>
 *     
 */
@Order(1)
@ControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {
	private static final Logger log = LoggerFactory.getLogger(DecryptRequestBodyAdvice.class);

    @Autowired
    private EncryptConfig config;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Annotation[] annotations = methodParameter.getDeclaringClass().getAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof DecryptBody ||
                        annotation instanceof AESDecryptBody ||
                        annotation instanceof DESDecryptBody ||
                        annotation instanceof RSADecryptBody){
                    return true;
                }
            }
        }
        return methodParameter.getMethod().isAnnotationPresent(DecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(AESDecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(DESDecryptBody.class) ||
                methodParameter.getMethod().isAnnotationPresent(RSADecryptBody.class);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if(inputMessage.getBody()==null){
            return inputMessage;
        }
        String body;
        try {
        	body = IoUtil.read(inputMessage.getBody(), config.getCharset());
        }catch (Exception e){
        	log.error("数据读取失败", e);
            throw new DecryptBodyFailException();
        }
        if(StrUtil.isBlank(body)){
        	log.error("数据读取失败");
            throw new DecryptBodyFailException();
        }
        String decryptBody = null;
        DecryptAnnotationInfoBean methodAnnotation = this.getMethodAnnotation(parameter);
        if(methodAnnotation!=null){
            decryptBody = switchDecrypt(body,methodAnnotation);
        }else{
            DecryptAnnotationInfoBean classAnnotation = this.getClassAnnotation(parameter.getDeclaringClass());
            if(classAnnotation!=null){
                decryptBody = switchDecrypt(body,classAnnotation);
            }
        }
        if(decryptBody==null){
            throw new DecryptBodyFailException();
        }
        try {
        	InputStream inputStream = IoUtil.toStream(decryptBody, config.getCharset());
        	DecryptHttpInputMessage decryptHttpInputMessage = new DecryptHttpInputMessage(inputStream, inputMessage.getHeaders());
        	if (log.isDebugEnabled()) {
        		log.info("解密前数据{}，解密后数据{}", body, decryptBody);
        	}
            return decryptHttpInputMessage;
        }catch (Exception e){
            throw new DecryptBodyFailException();
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 获取方法控制器上的加密注解信息
     * @param methodParameter 控制器方法
     * @return 加密注解信息
     */
    private DecryptAnnotationInfoBean getMethodAnnotation(MethodParameter methodParameter){
        if(methodParameter.getMethod().isAnnotationPresent(DecryptBody.class)){
            DecryptBody decryptBody = methodParameter.getMethodAnnotation(DecryptBody.class);
            return new DecryptAnnotationInfoBean(decryptBody.value(), decryptBody.otherKey());
        }
        if(methodParameter.getMethod().isAnnotationPresent(DESDecryptBody.class)){
        	return new DecryptAnnotationInfoBean(DecryptBodyMethod.DES, methodParameter.getMethodAnnotation(DESDecryptBody.class).otherKey());
        }
        if(methodParameter.getMethod().isAnnotationPresent(AESDecryptBody.class)){
        	return new DecryptAnnotationInfoBean(DecryptBodyMethod.AES, methodParameter.getMethodAnnotation(AESDecryptBody.class).otherKey());
        }
        return null;
    }

    /**
     * 获取类控制器上的加密注解信息
     * @param clazz 控制器类
     * @return 加密注解信息
     */
    private DecryptAnnotationInfoBean getClassAnnotation(Class<?> clazz){
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        if(annotations!=null && annotations.length>0){
            for (Annotation annotation : annotations) {
                if(annotation instanceof DecryptBody){
                    DecryptBody decryptBody = (DecryptBody) annotation;
                    return new DecryptAnnotationInfoBean(decryptBody.value(), decryptBody.otherKey());
                }
                if(annotation instanceof DESDecryptBody){
                	return new DecryptAnnotationInfoBean(DecryptBodyMethod.DES, ((DESDecryptBody) annotation).otherKey());
                }
                if(annotation instanceof AESDecryptBody){
                	return new DecryptAnnotationInfoBean(DecryptBodyMethod.AES, ((AESDecryptBody) annotation).otherKey());
                }
            }
        }
        return null;
    }


    /**
     * 选择加密方式并进行解密
     * @param formatStringBody 目标解密字符串
     * @param infoBean 加密信息
     * @return 解密结果
     */
    private String switchDecrypt(String formatStringBody, DecryptAnnotationInfoBean infoBean){
        DecryptBodyMethod method = infoBean.getDecryptBodyMethod();
        if(method==null) throw new DecryptMethodNotFoundException();
        String key = infoBean.getKey();
        if(method == DecryptBodyMethod.DES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"DES-KEY");
            return DESEncryptUtil.decrypt(formatStringBody,key);
        }
        if(method == DecryptBodyMethod.AES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"AES-KEY");
            return AESEncryptUtil.decrypt(formatStringBody,key);
        }
        throw new DecryptBodyFailException();
    }
}
