package cn.aaron911.encrypt.api.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
/**
 * encrpty 默认配置属性
 **/
@Configuration
public class EncryptConfig{
	
	@Value("${aaron911.encrypt.aesKey:}")
    private String aesKey;

	@Value("${aaron911.encrypt.aesKey:}")
    private String desKey;

    private String charset = "UTF-8";

    /**
     * 是否启用加密、解密
     * 默认启用
     */
    @Value("${aaron911.encrypt.open: true}")
    private boolean open;
    
	/**
	 * 私钥
	 * type = "rsa"; 或者 type = "dsa"; 此属性必须 配置
	 */
    @Value("${aaron911.encrypt.privateKey:}")
    private String privateKey;

    /**
     * 公钥
     * type = "rsa"; 或者 type = "dsa"; 此属性必须 配置
     */
    @Value("${aaron911.encrypt.publicKey:}")
    private String publicKey;
    
    /**
     * 对称算法秘钥
     * type = "aes"; 或者 type = "des"; 此属性必须 配置
     */
    @Value("${aaron911.encrypt.key:}")
    private String key;
    
    /**
     * 对称算法盐
     * type = "aes"; 或者 type = "des"; 此属性非必须 配置
     */
    @Value("${aaron911.encrypt.salt:}")
    private String salt;

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
    
    
    
}