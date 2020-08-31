package cn.aaron911.idempotent.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cn.aaron911.idempotent.property.IdempotentProperties;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @version 1.0
 */
public class ConcurrentHashMapCache implements Cache {

    private static final Map<String, CacheObj> STORE = new ConcurrentHashMap<>();
    private static final int DEFAULT_CLEAR_CACHE_DELAY = 5;
    private static final TimeUnit DEFAULT_CLEAR_CACHE_UNIT = TimeUnit.MINUTES;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();
    
    @Autowired
    IdempotentProperties properties;

    public ConcurrentHashMapCache() {
        /*
         * 5分钟清理一次过期缓存
         */
        CacheScheduler.INSTANCE.schedule(this::clear, DEFAULT_CLEAR_CACHE_DELAY, DEFAULT_CLEAR_CACHE_UNIT);
    }

    @Override
    public void set(String key, Integer value, long delay, TimeUnit unit) {
        Assert.notNull(key, "The object argument [key] must be null");
        Assert.notNull(value, "The object argument [value] must be null");
        Assert.notNull(unit, "The object argument [unit] must be null");
        writeLock.lock();
        try {
            STORE.put(key, new CacheObj(value, delay, unit));
            CacheScheduler.INSTANCE.schedule(() -> del(key), delay, unit);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void set(String key, Integer value) {
        Assert.notNull(key, "The object argument [key] must be null");
        Assert.notNull(value, "The object argument [value] must be null");
        writeLock.lock();
        long delay = properties.getValidTime();
        TimeUnit unit = TimeUnit.MILLISECONDS;
        try {
            CacheObj cacheObj = STORE.get(key);
            if (null == cacheObj) {
                this.set(key, value, delay, unit);
            } else {
                cacheObj.setValue(value);
                STORE.put(key, cacheObj);
            }
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public CacheObj get(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        readLock.lock();
        try {
            return STORE.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Boolean hasKey(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        readLock.lock();
        try {
            return STORE.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void del(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        writeLock.lock();
        try {
            STORE.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public long getExpire(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        readLock.lock();
        try {
            if (this.hasKey(key)) {
                CacheObj cacheObj = this.get(key);
                return cacheObj.getExpire();
            }
            return 0;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int incrementAndGet(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        int value = 0;
        CacheObj cacheObj = get(key);
        if (null == cacheObj) {
            value = 1;
        } else {
            value = cacheObj.getValue() + 1;
        }
        this.set(key, value);
        return value;
    }

    @Override
    public void clear() {
        Iterator<CacheObj> it = STORE.values().iterator();
        CacheObj cacheObj = null;
        while (it.hasNext()) {
            cacheObj = it.next();
            if (cacheObj.getExpire() <= 0) {
                it.remove();
            }
        }
    }
}
