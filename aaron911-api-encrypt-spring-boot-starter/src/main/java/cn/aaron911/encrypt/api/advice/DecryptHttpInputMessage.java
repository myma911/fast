package cn.aaron911.encrypt.api.advice;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import cn.aaron911.encrypt.api.config.EncryptConfig;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

/**
 * 解密
 * 支持对称加密 ads/des
 * 支持非对称加密 res
 * 
 **/
@Slf4j
public class DecryptHttpInputMessage implements HttpInputMessage{

    private HttpHeaders headers;
    private InputStream body;


    public DecryptHttpInputMessage(HttpInputMessage inputMessage, EncryptConfig encryptConfig) throws Exception {
    	this.headers = inputMessage.getHeaders();
		this.body = inputMessage.getBody();
    	if(!encryptConfig.isOpen()) {
    		return;
    	}
    	
    	// 非对称解密
		String content = new BufferedReader(new InputStreamReader(inputMessage.getBody())).lines().collect(Collectors.joining(System.lineSeparator()));
        String decryptBody;
        
        if (content.startsWith("{")) {
            log.info("未加密未解密内容:{}", content);
            decryptBody = content;
            this.body = new ByteArrayInputStream(decryptBody.getBytes());
            return;
        }    
    	
		if ("aes".equalsIgnoreCase(encryptConfig.getType().trim())) { // 对称加密
			
			if (StrUtil.isEmpty(encryptConfig.getKey())) {
	            throw new IllegalArgumentException("encrypt.key is illegalArgument, 秘钥未配置");
	        }	
			// 对称方法解密
			
			StringBuilder jsonStringBuilder = new StringBuilder();
            content = content.replaceAll(" ", "+");

            if (!StrUtil.isEmpty(content)) {
                String[] contents = content.split("\\|");
                for (String value : contents) {
                	value = SecureUtil.aes(encryptConfig.getKey().getBytes()).decryptStr(value);
            		jsonStringBuilder.append(value);
                }
            }
            decryptBody = jsonStringBuilder.toString();
            if(encryptConfig.isShowLog()) {
                log.info("接收到加密数据：{},解密后数据：{}", content, decryptBody);
            }
	        this.body = new ByteArrayInputStream(decryptBody.getBytes());
			
		} else if ("des".equalsIgnoreCase(encryptConfig.getType().trim())) {  // 对称加密
			
			if (StrUtil.isEmpty(encryptConfig.getKey())) {
	            throw new IllegalArgumentException("encrypt.key is illegalArgument, 秘钥未配置");
	        }	
			// 对称方法解密
			
            StringBuilder jsonStringBuilder = new StringBuilder();
            content = content.replaceAll(" ", "+");

            if (!StrUtil.isEmpty(content)) {
                String[] contents = content.split("\\|");
                for (String value : contents) {
                	value = SecureUtil.des(encryptConfig.getKey().getBytes()).decryptStr(value);
            		jsonStringBuilder.append(value);
                }
            }
            decryptBody = jsonStringBuilder.toString();
            if(encryptConfig.isShowLog()) {
                log.info("接收到加密数据：{},解密后数据：{}", content, decryptBody);
            }
	        this.body = new ByteArrayInputStream(decryptBody.getBytes());
			
		} else if ("rsa".equalsIgnoreCase(encryptConfig.getType().trim())) { // 非对称加密
			
			if (StrUtil.isEmpty(encryptConfig.getPrivateKey())) {
	            throw new IllegalArgumentException("encrypt.privateKey is illegalArgument, 私钥未配置");
	        }
			
            StringBuilder jsonStringBuilder = new StringBuilder();
            content = content.replaceAll(" ", "+");

            if (!StrUtil.isEmpty(content)) {
                String[] contents = content.split("\\|");
                for (String value : contents) {
            		RSA rsa = SecureUtil.rsa(encryptConfig.getPrivateKey(), encryptConfig.getPublicKey());
            		value = rsa.decryptStr(value, KeyType.PrivateKey);
            		jsonStringBuilder.append(value);
                }
            }
            decryptBody = jsonStringBuilder.toString();
            if(encryptConfig.isShowLog()) {
                log.info("接收到加密数据：{},解密后数据：{}", content, decryptBody);
            }
	        this.body = new ByteArrayInputStream(decryptBody.getBytes());
	        
		} else {
			throw new IllegalArgumentException("encrypt.type is illegalArgument, 暂未实现的加密方式");
		}
    }

    @Override
    public InputStream getBody(){
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
