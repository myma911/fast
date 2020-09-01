package cn.aaron911.idempotent.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;

import cn.aaron911.idempotent.property.IdempotentProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 */
@Slf4j
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
    public boolean set(String key, Object value, long delay, TimeUnit unit) {
        writeLock.lock();
        try {
            CacheObj cacheObj = STORE.put(key, new CacheObj(value, delay, unit));
            CacheScheduler.INSTANCE.schedule(() -> del(key), delay, unit);
        	return true;
        } catch (Exception e) {
        	log.error(e.getMessage());
        	return false;
		} finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean set(String key, Object value) {
        writeLock.lock();
        long delay = properties.getValidTime();
        TimeUnit unit = TimeUnit.MILLISECONDS;
        try {
            CacheObj cacheObj = STORE.get(key);
            if (null == cacheObj) {
               return this.set(key, value, delay, unit);
            }
            cacheObj.setValue(value);
            cacheObj = STORE.put(key, cacheObj);
            if (null != cacheObj) {
            	return true;
            }
            return false;
        } catch (Exception e) {
        	log.error(e.getMessage());
        	return false;
		} finally {
            writeLock.unlock();
        }

    }

    @Override
    public CacheObj get(String key) {
        readLock.lock();
        try {
            return STORE.get(key);
        } catch (Exception e) {
        	log.error(e.getMessage());
        	return null;
		}finally {
            readLock.unlock();
        }
    }

    @Override
    public Boolean hasKey(String key) {
        readLock.lock();
        try {
            return STORE.containsKey(key);
        } catch (Exception e) {
        	log.error(e.getMessage());
        	return false;
		} finally {
            readLock.unlock();
        }
    }

    /**
     * 删除缓存
     *
     * @param key 缓存KEY
     */
    @Override
    public Boolean del(String key) {
        writeLock.lock();
        try {
            CacheObj remove = STORE.remove(key);
            if (null != remove) {
            	return true;
            }
            return false;
        } catch (Exception e) {
        	log.error(e.getMessage());
        	return false;
		} finally {
            writeLock.unlock();
        }
    }

    
    /**
     * 获取剩余缓存失效时间
     *
     * @param key 缓存KEY
     * @return 过期时间
     */
    @Override
    public Long getExpire(String key) {
        readLock.lock();
        try {
            if (this.hasKey(key)) {
                CacheObj cacheObj = this.get(key);
                return cacheObj.getExpire();
            }
            return 0L;
        } catch (Exception e) {
        	log.error(e.getMessage());
        	return 0L;
		} finally {
            readLock.unlock();
        }
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
