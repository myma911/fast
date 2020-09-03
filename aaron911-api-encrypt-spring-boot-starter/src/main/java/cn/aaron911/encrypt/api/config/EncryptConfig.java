package cn.aaron911.encrypt.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.hutool.core.util.IdUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * encrpty 默认配置属性
 **/
@ConfigurationProperties(prefix = "aaron911.encrypt")
@Configuration
@Data
@EqualsAndHashCode(callSuper = false)
public class EncryptConfig{
	
	/**
	 * 对称加密aes/des
	 * 非对称加密rsa
	 * type 取值为“aes”或者“des”或者“rsa”
	 * 默认aes
	 */
	private String type = "aes";

    private String charset = "UTF-8";

    /**
     * 是否启用加密、解密
     * 默认启用
     */
    private boolean open = true;

    /**
     * 是否显示日志
     * 默认不显示
     */
    private boolean showLog = false;
    
    
	/**
	 * 私钥
	 * type = "rsa"; 或者 type = "dsa"; 此属性必须 配置
	 */
    private String privateKey;

    /**
     * 公钥
     * type = "rsa"; 或者 type = "dsa"; 此属性必须 配置
     */
    private String publicKey;
    
    /**
     * 对称算法秘钥
     * type = "aes"; 或者 type = "des"; 此属性必须 配置
     */
    private String key = IdUtil.simpleUUID();
    
    /**
     * 对称算法盐
     * type = "aes"; 或者 type = "des"; 此属性非必须 配置
     */
    private String salt;
    
}