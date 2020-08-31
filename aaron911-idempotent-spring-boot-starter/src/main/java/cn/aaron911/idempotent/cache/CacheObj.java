package cn.aaron911.idempotent.cache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 */
public class CacheObj implements Serializable {
	private static final long serialVersionUID = 1L;
	private int value;
    private long expire;

    public CacheObj() {

    }

    public CacheObj(int value, long expire, TimeUnit unit) {
        this.value = value;
        // 实际过期时间等于当前时间加上有效期
        this.expire = System.currentTimeMillis() + unit.toMillis(expire);
    }

    public CacheObj(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public CacheObj setValue(int value) {
        this.value = value;
        return this;
    }

    public long getExpire() {
        return expire - System.currentTimeMillis();
    }

    public CacheObj setExpire(long expire) {
        this.expire = expire;
        return this;
    }
}
