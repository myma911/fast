package cn.aaron911.buron;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.aaron911.buron.annotation.EnableBuronConfiguration;
import cn.aaron911.buron.cache.BuronCacheType;
import cn.aaron911.buron.cache.Cache;
import cn.aaron911.buron.cache.ConcurrentHashMapCache;
import cn.aaron911.buron.cache.RedisCache;
import cn.aaron911.buron.property.BuronProperties;

import javax.annotation.PostConstruct;

/**
 * @version 1.0.0
 * 
 */
@Configuration
@ConditionalOnBean(annotation = EnableBuronConfiguration.class)
@EnableConfigurationProperties(BuronProperties.class)
public class BuronAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BuronAutoConfiguration.class);

    @Autowired
    BuronProperties properties;

    @PostConstruct
    public void init() {
        log.info("Buron has been turned on! Best wishes for you! ");
    }

    @Bean
    @ConditionalOnMissingBean(name = {"buronProcessor"})
    BuronProcessor buronProcessor() {
        return new BuronShieldProcessor();
    }

    @Bean
    Cache buronCache() {
        BuronCacheType type = properties.getType();
        if (type == BuronCacheType.REDIS) {
            log.info("Enabling Buron cache: [Redis]");
            return new RedisCache();
        }
        log.info("Enabling Buron cache: [Map]");
        return new ConcurrentHashMapCache();
    }
}
