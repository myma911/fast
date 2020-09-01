package cn.aaron911.idempotent.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.aaron911.idempotent.cache.Cache;
import cn.aaron911.idempotent.cache.ConcurrentHashMapCache;
import cn.aaron911.idempotent.cache.IdempotentCacheType;
import cn.aaron911.idempotent.cache.RedisCache;
import cn.aaron911.idempotent.property.IdempotentProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * 自动配置
 * 
 */
@Configuration
@Slf4j
public class AutoConfiguration {
	
    @Autowired
    private IdempotentProperties properties;

    @Bean
    Cache cache() {
        IdempotentCacheType type = properties.getType();
        if (type == IdempotentCacheType.REDIS) {
            log.info("Enabling Idempotent cache: [Redis]");
            return new RedisCache();
        }
        log.info("Enabling Idempotent cache: [Map]");
        return new ConcurrentHashMapCache();
    }
}
