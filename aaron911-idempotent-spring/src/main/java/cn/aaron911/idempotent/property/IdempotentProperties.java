package cn.aaron911.idempotent.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdempotentProperties {
	
    /**
     * 缓存类型，默认为map存储
     */
	@Value("${aaron911.idempotent.type: map}")
    private String type;
    
	/**
	 * redis 最小空闲数
	 */
	@Value("${aaron911.buron.type.redis.minIdle: 2}")
    private int minIdle;
	
	
	/**
	 * redis 最大空闲数
	 */
	@Value("${aaron911.buron.type.redis.maxIdle: 2}")
    private int maxIdle;
	
	/**
	 * redis 最大连接数
	 */
	@Value("${aaron911.buron.type.redis.maxTotal: 5}")
    private int maxTotal;
	
	/**
	 * redis 最大建立连接等待时间
	 */
	@Value("${aaron911.buron.type.redis.maxWaitMillis: 1000}")
    private int maxWaitMillis;
	
	/**
	 * redis 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
	 */
	@Value("${aaron911.buron.type.redis.testOnBorrow: true}")
    private boolean testOnBorrow;
	
	/**
	 * redis 主机
	 */
	@Value("${aaron911.buron.type.redis.host: 127.0.0.1}")
    private String host;
	
	/**
	 * redis 端口
	 */
	@Value("${aaron911.buron.type.redis.port: 6379}")
    private int port;
	
	/**
	 * redis 密码
	 */
	@Value("${aaron911.buron.type.redis.password:}")
    private String password;
	
	/**
	 * redis 超时时间
	 */
	@Value("${aaron911.buron.type.redis.timeout: 1000}")
    private int timeout;

    
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getTimeout() {
		return timeout;
	}


	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	public int getMinIdle() {
		return minIdle;
	}


	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}


	public int getMaxIdle() {
		return maxIdle;
	}


	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}


	public int getMaxTotal() {
		return maxTotal;
	}


	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}


	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}


	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}


	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}


	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 幂等性验证码保存时间
	 * 单位：毫秒(ms)，默认为 5分钟
	 * 
	 */
    @Value("${aaron911.idempotent.validTime: 300000}")
	private long validTime; 
	
	
	/**
	 * 幂等性验证码默认前缀
	 */
    @Value("${aaron911.idempotent.idempotentPrefix: Unique_Identification_}")
	private String idempotentPrefix;
	
	/**
	 * 放在请求头或者url参数中的幂等性校验名称
	 */
    @Value("${aaron911.idempotent.idempotentName: unique}")
	private String idempotentName;
	
    

    public String getType() {
		return type;
	}


	public void setType(String type) {
        this.type = type;
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
