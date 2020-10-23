package cn.aaron911.log.server.init;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import cn.aaron911.log.core.kafka.KafkaConsumerClient;
import cn.aaron911.log.core.redis.RedisClient;
import cn.aaron911.log.server.properties.DefaultInitProperty;
import cn.aaron911.log.server.properties.InitProperty;
import cn.aaron911.log.server.util.ElasticLowerClientSingleton;

@Configuration
public class Init {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Init.class);
    
	@Autowired
	private InitProperty initProperty;

    @Bean
    public RedisClient initRedisClient() {
    	String redisHost = initProperty.getRedisHost();
        if (StringUtils.isEmpty(redisHost)) {
            logger.error("没有配置redis地址，检查application.properties(aaron911.log.redis.redisHost) ");
            throw new RuntimeException("没有配置redis地址，检查application.properties(aaron911.log.redis.redisHost) ");
        }
        String[] hs = redisHost.split(":");
        if (hs.length == 2) {
        	String ip = hs[0];
            int port = Integer.valueOf(hs[1]);
            String redisPassWord = initProperty.getRedisPassWord();
            int redisDb = initProperty.getRedisDb();
            return RedisClient.getInstance(ip, port, redisPassWord, redisDb);
        }
        logger.error("redis地址配置错误，检查application.properties(aaron911.log.redis.redisHost) ");
        throw new RuntimeException("redis地址配置错误，检查application.properties(aaron911.log.redis.redisHost) ");
    }

    @Bean
    public ElasticLowerClientSingleton initElasticLowerClient() {
    	String esHosts = initProperty.getEsHosts();
        if (StringUtils.isEmpty(esHosts)) {
            logger.error("没有配置elasticsearch地址，检查application.properties(aaron911.log.es.esHosts) ");
            throw new RuntimeException("没有配置elasticsearch地址，检查application.properties(aaron911.log.es.esHosts) ");
        }
        String esUserName = initProperty.getEsUserName();
        String esPassWord = initProperty.getEsPassWord();
        return ElasticLowerClientSingleton.getInstance(esHosts, esUserName, esPassWord);
    }

    @Bean
    @ConditionalOnProperty(name = "aaron911.log.model",havingValue = "kafka&ui")
    public KafkaConsumer<?, ?> initKafkaConsumer() {
    	String kafkaHosts = initProperty.getKafkaHosts();
        if (StringUtils.isEmpty(kafkaHosts)) {
            logger.error("没有配置kafka地址，检查application.properties(aaron911.log.kafka.kafkaHosts) ");
            throw new RuntimeException("没有配置kafka地址，检查application.properties(aaron911.log.kafka.kafkaHosts) ");
        }
        return KafkaConsumerClient.getInstance(kafkaHosts, DefaultInitProperty.KAFKA_GROUP_NAME, DefaultInitProperty.MAX_SEND_SIZE).getKafkaConsumer();
    }

}
