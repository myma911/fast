package cn.aaron911.buron;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import cn.aaron911.buron.annotation.EnableBuronConfiguration;
import cn.aaron911.buron.property.BuronProperties;

/**
 * @version 1.0
 */
@Configuration
@ConditionalOnBean(annotation = EnableBuronConfiguration.class)
@EnableConfigurationProperties(BuronProperties.class)
@ConditionalOnProperty(name = "aaron911.buron.type", havingValue = "redis")
public class BuronRedisAutoConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
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
