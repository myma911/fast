package cn.aaron911.buron.cache;

import cn.aaron911.buron.property.BuronProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


public class RedisCache implements Cache {

    @Resource
    private RedisTemplate<String, Object> buronRedisTemplate;
    
    @Resource
    private BuronProperties properties;

    @Override
    public void set(String key, Integer value, long delay, TimeUnit unit) {
        Assert.notNull(key, "The object argument [key] must be null");
        Assert.notNull(value, "The object argument [value] must be null");
        Assert.notNull(unit, "The object argument [unit] must be null");
        buronRedisTemplate.opsForValue().set(key, new CacheObj(value), delay, unit);
    }

    @Override
    public void set(String key, Integer value) {
        Assert.notNull(key, "The object argument [key] must be null");
        Assert.notNull(value, "The object argument [value] must be null");

        ValueOperations<String, Object> operations = buronRedisTemplate.opsForValue();
        CacheObj cacheObj = (CacheObj) operations.get(key);
        if (null == cacheObj) {
            this.set(key, value, properties.getInterval(), TimeUnit.MILLISECONDS);
        } else {
            operations.set(key, cacheObj.setValue(value), getExpire(key), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public CacheObj get(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        return (CacheObj) buronRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean hasKey(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        return buronRedisTemplate.hasKey(key);
    }

    @Override
    public void del(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        if (hasKey(key)) {
        	buronRedisTemplate.delete(key);
        }
    }

    @Override
    public long getExpire(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        return buronRedisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    @Override
    public int incrementAndGet(String key) {
        Assert.notNull(key, "The object argument [key] must be null");
        int value = 0;
        CacheObj obj = this.get(key);
        if (null == obj) {
            value = 1;
        } else {
            value = obj.getValue() + 1;
        }
        this.set(key, value);
        return value;
    }
}
