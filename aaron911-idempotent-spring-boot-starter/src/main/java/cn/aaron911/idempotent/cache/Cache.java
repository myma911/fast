package cn.aaron911.idempotent.cache;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 */
public interface Cache {

    /**
     * 设置缓存
     *
     * @param key   缓存KEY
     * @param value 缓存内容
     * @param delay 缓存时间
     * @param unit  缓存时间单位
     */
    void set(String key, Integer value, long delay, TimeUnit unit);

    /**
     * 设置缓存
     *
     * @param key   缓存KEY
     * @param value 缓存内容
     */
    void set(String key, Integer value);

    /**
     * 获取缓存
     *
     * @param key 缓存KEY
     * @return 缓存内容
     */
    CacheObj get(String key);

    /**
     * 判断缓存是否存在
     *
     * @param key 缓存KEY
     * @return 是否存在key
     */
    Boolean hasKey(String key);

    /**
     * 删除缓存
     *
     * @param key 缓存KEY
     */
    void del(String key);

    /**
     * 获取剩余缓存失效时间
     *
     * @param key 缓存KEY
     * @return 过期时间
     */
    long getExpire(String key);

    /**
     * 自增并返回自增后的结果
     *
     * @param key 缓存key
     * @return 返回自增后的结果
     */
    int incrementAndGet(String key);

    /**
     * 清理过期的缓存
     */
    default void clear() {

    }

}
