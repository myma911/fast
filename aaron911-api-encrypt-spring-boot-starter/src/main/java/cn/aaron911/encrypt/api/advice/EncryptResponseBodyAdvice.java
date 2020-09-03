package cn.aaron911.encrypt.api.advice;

import java.io.ByteArrayInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import cn.aaron911.encrypt.api.annotation.Encrypt;
import cn.aaron911.encrypt.api.config.EncryptConfig;
import cn.aaron911.encrypt.api.util.Base64Util;
import cn.aaron911.encrypt.api.util.RSAUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现ResponseBodyAdvice接口 数据返回加密
 **/
@ControllerAdvice
@Slf4j
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	private boolean encrypt;

	@Autowired
	private EncryptConfig encryptConfig;

	private static ThreadLocal<Boolean> encryptLocal = new ThreadLocal<>();

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		encrypt = false;
		if (returnType.getMethod().isAnnotationPresent(Encrypt.class) && encryptConfig.isOpen()) {
			encrypt = true;
		}
		return encrypt;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		// EncryptResponseBodyAdvice.setEncryptStatus(false);
		// Dynamic Settings Not Encrypted
		Boolean status = encryptLocal.get();
		if (null != status && !status) {
			encryptLocal.remove();
			return body;
		}
		if (encrypt) {

			try {
				String content = JSONUtil.toJsonStr(body);

				if ("aes".equalsIgnoreCase(encryptConfig.getType().trim())) { // 对称加密

					if (StrUtil.isEmpty(encryptConfig.getKey())) {
						throw new IllegalArgumentException("encrypt.key is illegalArgument, 秘钥未配置");
					}
					// aes对称加密
					String result = SecureUtil.aes(encryptConfig.getKey().getBytes()).encryptBase64(content);
					if (encryptConfig.isShowLog()) {
						log.info("加密前数据：{}，aes对称加密后数据：{}", content, result);
					}
					return result;

				} else if ("des".equalsIgnoreCase(encryptConfig.getType().trim())) { // 对称加密

					if (StrUtil.isEmpty(encryptConfig.getKey())) {
						throw new IllegalArgumentException("encrypt.key is illegalArgument, 秘钥未配置");
					}
					// des对称加密

					String result = SecureUtil.des(encryptConfig.getKey().getBytes()).encryptBase64(content);
					if (encryptConfig.isShowLog()) {
						log.info("加密前数据：{}，des加密后数据：{}", content, result);
					}
					return result;

				} else if ("rsa".equalsIgnoreCase(encryptConfig.getType().trim())) { // 非对称加密

					if (StrUtil.isEmpty(encryptConfig.getPrivateKey())) {
						throw new IllegalArgumentException("encrypt.privateKey is illegalArgument, 私钥未配置");
					}

					RSA rsa = SecureUtil.rsa(encryptConfig.getPrivateKey(), encryptConfig.getPublicKey());
					String encryptBase64 = rsa.encryptBase64(content, KeyType.PrivateKey);
					if (encryptConfig.isShowLog()) {
						log.info("加密前数据：{}，rsa加密后数据：{}", content, encryptBase64);
					}
					return encryptBase64;

				} else {
					throw new IllegalArgumentException("encrypt.type is illegalArgument, 暂未实现的加密方式");
				}
			} catch (Exception e) {
				log.error("加密数据失败", e);
			}
		}
		return body;
	}
}
