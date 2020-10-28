package cn.aaron911.encrypt.api.enums;

/**
 * <p>SHA加密类型</p>
 */
public enum  SHAEncryptType {

    SHA224("sha-224"),
    SHA256("sha-256"),
    SHA384("sha-384"),
    SHA512("sha-512");
	
    public String value;
    
    // 构造方法  
    private SHAEncryptType(String value) {  
        this.value = value;  
    }  
    

}
