package cn.aaron911.buron;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import cn.aaron911.buron.cache.BuronCacheType;
import cn.aaron911.buron.cache.Cache;
import cn.aaron911.buron.cache.ConcurrentHashMapCache;
import cn.aaron911.buron.cache.RedisCache;
import cn.aaron911.buron.property.BuronProperties;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;


@Configuration
public class BuronAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BuronAutoConfiguration.class);

    @Autowired
    private BuronProperties properties;

    @PostConstruct
    public void init() {
    	if (log.isDebugEnabled()) {
    		log.debug("Buron starts to work");
    	}
    }

    @Bean
    public BuronProcessor buronProcessor() {
        return new BuronShieldProcessor();
    }

    @Bean
    public Cache buronCache() {
        BuronCacheType type = properties.getType();
        if (type == BuronCacheType.REDIS) {
        	if (log.isDebugEnabled()) {
        		log.debug("Enabling Buron cache: [Redis]");        		
        	}
            return new RedisCache();
        }
    	if (log.isDebugEnabled()) {
    		log.debug("Enabling Buron cache: [Map]");        		
    	}
        return new ConcurrentHashMapCache();
    }
    
    
    @Bean
    @Conditional({RedisCondition.class})
    public JedisPoolConfig jedisPoolConfig() {
    	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    	jedisPoolConfig.setMinIdle(properties.getMinIdle());
    	jedisPoolConfig.setMaxIdle(properties.getMaxIdle());
    	jedisPoolConfig.setMaxTotal(properties.getMaxTotal());
    	jedisPoolConfig.setMaxWaitMillis(properties.getMaxWaitMillis());
    	jedisPoolConfig.setTestOnBorrow(properties.isTestOnBorrow());
    	return jedisPoolConfig;
    }
   
    
    @Bean
    @Conditional({RedisCondition.class})
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
    	//单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(properties.getHost());
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(0);
        //设置密码
        redisStandaloneConfiguration.setPassword(properties.getPassword());
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(properties.getPort());
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
        jpcb.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }
    
    

    @Bean
    @Conditional({RedisCondition.class})
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory factory) {
    	 final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
         redisTemplate.setConnectionFactory(factory);

         // 对key的默认序列化器。默认值是StringSerializer
         StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
         redisTemplate.setKeySerializer(stringRedisSerializer);

         // 对value的默认序列化器，默认值是取自DefaultSerializer的JdkSerializationRedisSerializer
         JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
         redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);

         // 存储Map时key需要的序列化配置
         redisTemplate.setHashKeySerializer(stringRedisSerializer);

         // 存储Map时value需要的序列化配置
         redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);

         //redisTemplate.setEnableTransactionSupport(true);
         return redisTemplate;
    }
}
