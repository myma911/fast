package cn.aaron911.buron.property;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import cn.aaron911.buron.cache.BuronCacheType;


@Configuration
public class BuronProperties {

    /**
     * 连续访问最高阀值，超过该值则认定为恶意操作的IP
     * 单位：次 默认为20
     */
	@Value("${aaron911.buron.threshold: 20}")
    private int threshold;

    /**
     * 间隔时间，在该时间内如果访问次数大于阀值，则记录为恶意IP，否则视为正常访问
     * 单位：毫秒(ms)，默认为 5秒
     */
	@Value("${aaron911.buron.interval: 5000}")
    private long interval;

    /**
     * 当检测到恶意访问时，对恶意访问的ip进行限制的时间
     * 单位：毫秒(ms)，默认为 1分钟
     */
	@Value("${aaron911.buron.limitedTime: 60000}")
    private long limitedTime;

    /**
     * 黑名单存在的时间，在单位时间内用户访问受限的次数累加
     * 单位：毫秒(ms)，默认为 1个月
     */
	@Value("${aaron911.buron.blacklistTime: 2592000000}")
    private long blacklistTime;

    /**
     * 缓存类型，默认为map存储
     */
	@Value("${aaron911.buron.type: map}")
    private BuronCacheType type;
	
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

	public void setType(BuronCacheType type) {
		this.type = type;
	}

	public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(long limitedTime) {
        this.limitedTime = limitedTime;
    }

    public long getBlacklistTime() {
        return blacklistTime;
    }

    public void setBlacklistTime(long blacklistTime) {
        this.blacklistTime = blacklistTime;
    }

    public BuronCacheType getType() {
        return type;
    }

    public BuronProperties setType(String type) {
        if (StringUtils.isEmpty(type)) {
            return this;
        }
        this.type = BuronCacheType.valueOf(type.toUpperCase());
        return this;
    }
}
