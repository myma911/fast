package cn.aaron911.log.server.init;


import java.util.Date;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import cn.aaron911.log.core.redis.RedisClient;
import cn.aaron911.log.server.collect.KafkaLogCollect;
import cn.aaron911.log.server.collect.RedisLogCollect;
import cn.aaron911.log.server.properties.DefaultInitProperty;
import cn.aaron911.log.server.util.ElasticLowerClientSingleton;
import cn.aaron911.log.server.util.IndexUtil;



@Component
public class CollectAutoStart implements CommandLineRunner {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CollectAutoStart.class);
    
    @Autowired
    private ElasticLowerClientSingleton elasticLowerClient;
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired(required = false)
    private KafkaConsumer kafkaConsumer;
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

   
    private void autoCreatIndice(){
        Date date = new Date();
        if(DefaultInitProperty.ES_INDEX_MODEL.equals("day")) {
            creatIndiceLog(IndexUtil.getRunLogIndex(date));
            creatIndiceTrace(IndexUtil.getTraceLogIndex(date));
        }else {
            for (int a = 0; a < 24; a++) {
                String hour=String.format("%02d",a);
                creatIndiceLog(IndexUtil.getRunLogIndex(date,hour));
                creatIndiceTrace(IndexUtil.getTraceLogIndex(date,hour));

            }
        }
    }
    
    private void serverStart() {
        if (DefaultInitProperty.Mode.KAFKA_UI.equals(DefaultInitProperty.Mode.START)) {
            KafkaLogCollect kafkaLogCollect = new KafkaLogCollect(elasticLowerClient, kafkaConsumer, applicationEventPublisher);
            kafkaLogCollect.kafkaStart();
        }
        if (DefaultInitProperty.Mode.REDIS_UI.equals(DefaultInitProperty.Mode.START)) {
            RedisLogCollect redisLogCollect = new RedisLogCollect(elasticLowerClient, redisClient, applicationEventPublisher);
            redisLogCollect.redisStart();
        }
    }
    
    private void creatIndiceLog(String index){
        if(!elasticLowerClient.existIndice(index)){
            elasticLowerClient.creatIndice(index);
        };
    }
    private void creatIndiceTrace(String index){
        if(!elasticLowerClient.existIndice(index)){
            elasticLowerClient.creatIndiceTrace(index);
        };
    }


	@Override
	public void run(String... args) throws Exception {
		try {
			autoCreatIndice();
			serverStart();
		} catch (Exception e) {
			logger.error("aaron911log server starting failed!", e);
		}
	}
}
