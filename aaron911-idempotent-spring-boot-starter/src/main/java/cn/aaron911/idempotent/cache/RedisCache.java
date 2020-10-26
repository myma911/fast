package cn.aaron911.idempotent.cache;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import cn.hutool.core.util.StrUtil;

public class RedisCache implements Cache {
	private static final Logger log = LoggerFactory.getLogger(RedisCache.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return 
     * @return true成功 false 失败
     */
    @Override
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
    	try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, new CacheObj(value), time, null == timeUnit ? TimeUnit.SECONDS : timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
        	log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value) {
    	try {
            redisTemplate.opsForValue().set(key, new CacheObj(value));
            return true;
        } catch (Exception e) {
        	log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public CacheObj get(String key) {
    	return key == null ? null : (CacheObj)redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean hasKey(String key) {
    	try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
        	log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean del(String key) {
    	if (StrUtil.isEmpty(key)) {
    		return false;
    	}
    	try {
    		return redisTemplate.delete(key);
		} catch (Exception e) {
			log.error(e.getMessage());
            return false;
		}
    	
    }

    @Override
    public Long getExpire(String key) {
    	try {
    		return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);	
		} catch (Exception e) {
			log.error(e.getMessage());
            return 0L;
		}
        
    }
}
