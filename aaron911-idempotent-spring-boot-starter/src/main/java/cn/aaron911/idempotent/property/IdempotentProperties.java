package cn.aaron911.idempotent.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import cn.aaron911.idempotent.cache.IdempotentCacheType;

/**
 * @version 1.0
 * 
 */
@ConfigurationProperties("aaron911.idempotent")
public class IdempotentProperties {
	
    /**
     * 缓存类型，默认为map存储
     */
    private IdempotentCacheType type = IdempotentCacheType.MAP;
    
    
	/**
	 * 幂等性验证码保存时间
	 * 单位：毫秒(ms)，默认为 5分钟
	 * 
	 */
	private long validTime = 5 * 60 * 1000; 
	
	
	/**
	 * 幂等性验证码默认前缀
	 */
	private String idempotentPrefix = "Unique_Identification_";
	
	/**
	 * 放在请求头或者url参数中的幂等性校验名称
	 */
	private String idempotentName = "unique";
	
	
	
	
	
    public IdempotentCacheType getType() {
        return type;
    }

    public void setType(String type) {
        if (StringUtils.isEmpty(type)) {
        	return;
            //return this;
        }
        this.type = IdempotentCacheType.valueOf(type.toUpperCase());
        //return this;
    }
	
	


	
	public long getValidTime() {
		return validTime;
	}

	public void setValidTime(long validTime) {
		this.validTime = validTime;
	}

	public String getIdempotentPrefix() {
		return idempotentPrefix;
	}

	public void setIdempotentPrefix(String idempotentPrefix) {
		this.idempotentPrefix = idempotentPrefix;
	}

	public String getIdempotentName() {
		return idempotentName;
	}

	public void setIdempotentName(String idempotentName) {
		this.idempotentName = idempotentName;
	}
	
}
