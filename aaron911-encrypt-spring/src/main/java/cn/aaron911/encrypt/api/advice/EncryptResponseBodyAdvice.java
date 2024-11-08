package cn.aaron911.encrypt.api.advice;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.aaron911.encrypt.api.annotation.encrypt.AESEncryptBody;
import cn.aaron911.encrypt.api.annotation.encrypt.DESEncryptBody;
import cn.aaron911.encrypt.api.annotation.encrypt.EncryptBody;
import cn.aaron911.encrypt.api.annotation.encrypt.MD5EncryptBody;
import cn.aaron911.encrypt.api.annotation.encrypt.RSAEncryptBody;
import cn.aaron911.encrypt.api.annotation.encrypt.SHAEncryptBody;
import cn.aaron911.encrypt.api.config.EncryptConfig;
import cn.aaron911.encrypt.api.enums.EncryptBodyMethod;
import cn.aaron911.encrypt.api.enums.SHAEncryptType;
import cn.aaron911.encrypt.api.exception.EncryptBodyFailException;
import cn.aaron911.encrypt.api.exception.EncryptMethodNotFoundException;
import cn.aaron911.encrypt.api.pojo.EncryptAnnotationInfoBean;
import cn.aaron911.encrypt.api.util.AESEncryptUtil;
import cn.aaron911.encrypt.api.util.CheckUtils;
import cn.aaron911.encrypt.api.util.DESEncryptUtil;
import cn.aaron911.encrypt.api.util.MD5EncryptUtil;
import cn.aaron911.encrypt.api.util.SHAEncryptUtil;


/**
 * 响应数据的加密处理<br>
 *     本类只对控制器参数中含有<strong>{@link org.springframework.web.bind.annotation.ResponseBody}</strong>
 *     或者控制类上含有<strong>{@link org.springframework.web.bind.annotation.RestController}</strong>
 */
@Order(1)
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {
	private static final Logger log = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);
	
	
    private final ObjectMapper objectMapper;

    private final EncryptConfig config;

    @Autowired
    public EncryptResponseBodyAdvice(ObjectMapper objectMapper, EncryptConfig config) {
        this.objectMapper = objectMapper;
        this.config = config;
    }


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Annotation[] annotations = returnType.getDeclaringClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof EncryptBody ||
                annotation instanceof AESEncryptBody ||
                annotation instanceof DESEncryptBody ||
                annotation instanceof RSAEncryptBody ||
                annotation instanceof MD5EncryptBody ||
                annotation instanceof SHAEncryptBody) {
                return true;
            }
        }
        return returnType.getMethod().isAnnotationPresent(EncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(AESEncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(DESEncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(RSAEncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(MD5EncryptBody.class) ||
                returnType.getMethod().isAnnotationPresent(SHAEncryptBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body==null) return null;
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String str = null;
        try {
            str = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            log.error("数据转换错误", e);
            throw new EncryptBodyFailException();
        }
        EncryptAnnotationInfoBean classAnnotation = getClassAnnotation(returnType.getDeclaringClass());
        if(classAnnotation != null){
        	String encryptContent = switchEncrypt(str, classAnnotation);
        	
        	if(log.isDebugEnabled()) {
                log.debug("加密前：{},加密后：{}", str, encryptContent);
            }
            return encryptContent;
        }
        EncryptAnnotationInfoBean methodAnnotation = getMethodAnnotation(returnType);
        if(methodAnnotation != null){
        	String encryptContent = switchEncrypt(str, methodAnnotation);
        	if(log.isDebugEnabled()) {
                log.debug("加密前：{},加密后：{}", str, encryptContent);
            }
            return encryptContent;
        }
        throw new EncryptBodyFailException();
    }

    /**
     * 获取方法控制器上的加密注解信息
     * @param methodParameter 控制器方法
     * @return 加密注解信息
     */
    private EncryptAnnotationInfoBean getMethodAnnotation(MethodParameter methodParameter){
        if(methodParameter.getMethod().isAnnotationPresent(EncryptBody.class)){
            EncryptBody encryptBody = methodParameter.getMethodAnnotation(EncryptBody.class);
            return new EncryptAnnotationInfoBean(encryptBody.value(), encryptBody.otherKey(), encryptBody.shaType());
        }
        if(methodParameter.getMethod().isAnnotationPresent(MD5EncryptBody.class)){
        	return new EncryptAnnotationInfoBean(EncryptBodyMethod.MD5);
        }
        if(methodParameter.getMethod().isAnnotationPresent(SHAEncryptBody.class)){
        	SHAEncryptType SHAEncryptType = methodParameter.getMethodAnnotation(SHAEncryptBody.class).value();
        	return new EncryptAnnotationInfoBean(EncryptBodyMethod.SHA, SHAEncryptType);
        }
        if(methodParameter.getMethod().isAnnotationPresent(DESEncryptBody.class)){
        	String key = methodParameter.getMethodAnnotation(DESEncryptBody.class).otherKey();
        	return new EncryptAnnotationInfoBean(EncryptBodyMethod.DES, key);
        }
        if(methodParameter.getMethod().isAnnotationPresent(AESEncryptBody.class)){
        	return new EncryptAnnotationInfoBean(EncryptBodyMethod.AES, methodParameter.getMethodAnnotation(AESEncryptBody.class).otherKey());

        }
        return null;
    }

    /**
     * 获取类控制器上的加密注解信息
     * @param clazz 控制器类
     * @return 加密注解信息
     */
    private EncryptAnnotationInfoBean getClassAnnotation(Class clazz){
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof EncryptBody) {
                EncryptBody encryptBody = (EncryptBody) annotation;
                return new EncryptAnnotationInfoBean(encryptBody.value(), encryptBody.otherKey(), encryptBody.shaType());
            }
            if (annotation instanceof MD5EncryptBody) {
                return new EncryptAnnotationInfoBean(EncryptBodyMethod.MD5);
            }
            if (annotation instanceof SHAEncryptBody) {
                return new EncryptAnnotationInfoBean(EncryptBodyMethod.SHA, ((SHAEncryptBody) annotation).value());
            }
            if (annotation instanceof DESEncryptBody) {
                return new EncryptAnnotationInfoBean(EncryptBodyMethod.DES, ((DESEncryptBody) annotation).otherKey());
            }
            if (annotation instanceof AESEncryptBody) {
                return new EncryptAnnotationInfoBean(EncryptBodyMethod.AES, ((AESEncryptBody) annotation).otherKey());
            }
        }
        return null;
    }


    /**
     * 选择加密方式并进行加密
     * @param formatStringBody 目标加密字符串
     * @param infoBean 加密信息
     * @return 加密结果
     */
    private String switchEncrypt(String formatStringBody,EncryptAnnotationInfoBean infoBean){
        EncryptBodyMethod method = infoBean.getEncryptBodyMethod();
        if(method==null){
            throw new EncryptMethodNotFoundException();
        }
        if(method == EncryptBodyMethod.MD5){
            return MD5EncryptUtil.encrypt(formatStringBody);
        }
        if(method == EncryptBodyMethod.SHA){
            SHAEncryptType shaEncryptType = infoBean.getShaEncryptType();
            if(shaEncryptType==null) shaEncryptType = SHAEncryptType.SHA256;
            return SHAEncryptUtil.encrypt(formatStringBody,shaEncryptType);
        }
        String key = infoBean.getKey();
        if(method == EncryptBodyMethod.DES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"DES-KEY");
            return DESEncryptUtil.encrypt(formatStringBody,key);
        }
        if(method == EncryptBodyMethod.AES){
            key = CheckUtils.checkAndGetKey(config.getAesKey(),key,"AES-KEY");
            return AESEncryptUtil.encrypt(formatStringBody,key);
        }
        throw new EncryptBodyFailException();
    }


}
