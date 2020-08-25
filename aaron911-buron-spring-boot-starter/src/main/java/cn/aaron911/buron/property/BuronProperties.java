package cn.aaron911.buron.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import cn.aaron911.buron.cache.BuronCacheType;

/**
 * @version 1.0
 * 
 */
@ConfigurationProperties("aaron911.buron")
public class BuronProperties {

    /**
     * 连续访问最高阀值，超过该值则认定为恶意操作的IP
     * 单位：次 默认为20
     */
    private int threshold = 20;

    /**
     * 间隔时间，在该时间内如果访问次数大于阀值，则记录为恶意IP，否则视为正常访问
     * 单位：毫秒(ms)，默认为 5秒
     */
    private long interval = 5000;

    /**
     * 当检测到恶意访问时，对恶意访问的ip进行限制的时间
     * 单位：毫秒(ms)，默认为 1分钟
     */
    private long limitedTime = 60000;

    /**
     * 黑名单存在的时间，在单位时间内用户访问受限的次数累加
     * 单位：毫秒(ms)，默认为 1个月
     */
    private long blacklistTime = 2592000000L;

    /**
     * 缓存类型，默认为map存储
     */
    private BuronCacheType type = BuronCacheType.MAP;

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
