package cn.aaron911.log.core.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import cn.aaron911.log.core.AbstractClient;
import cn.aaron911.log.core.exception.LogQueueConnectException;

import java.util.List;

public class KafkaProducerClient extends AbstractClient {
    private static KafkaProducerClient instance;
    private KafkaProducerPool kafkaProducerPool;
    public static KafkaProducerClient getInstance(String hosts) {
        if (instance == null) {
            synchronized (KafkaProducerClient.class) {
                if (instance == null) {
                    instance = new KafkaProducerClient(hosts);
                }
            }
        }
        return instance;
    }
    private KafkaProducerClient(String hosts){
        this.kafkaProducerPool=new KafkaProducerPool(hosts);
    }
    @Override
    public void pushMessage(String topic, String message) throws LogQueueConnectException {
        KafkaProducer kafkaProducer=null;
        try {
            kafkaProducer=kafkaProducerPool.getResource();
            kafkaProducer.send(new ProducerRecord<String, String>(topic, message));
        }catch (Exception e){
            throw new LogQueueConnectException("kafka 写入失败！",e);
        }finally {
            if(kafkaProducer!=null){
                kafkaProducerPool.returnResource(kafkaProducer);
            }
        }

    }
    @Override
    public void putMessageList(String topic, List<String> list) throws LogQueueConnectException {
        KafkaProducer kafkaProducer=null;
        try {
            kafkaProducer=kafkaProducerPool.getResource();
            for(int a=0;a<list.size();a++){
                String message=list.get(a);
                kafkaProducer.send(new ProducerRecord<String, String>(topic, message));
            };
        }catch (Exception e){
            throw new LogQueueConnectException("kafka 写入失败！",e);
        }finally {
            if(kafkaProducer!=null){
                kafkaProducerPool.returnResource(kafkaProducer);
            }
        }

    }

}
