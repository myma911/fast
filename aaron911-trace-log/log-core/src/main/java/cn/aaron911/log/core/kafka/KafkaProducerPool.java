package cn.aaron911.log.core.kafka;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.kafka.clients.producer.KafkaProducer;

import cn.aaron911.log.core.util.Pool;


public class KafkaProducerPool extends Pool<KafkaProducer> {

    public KafkaProducerPool(final GenericObjectPoolConfig poolConfig, final String hosts) {
        super(poolConfig,new KafkaProducerFactory(hosts));
    }
    public KafkaProducerPool( final String hosts) {
        super(new GenericObjectPoolConfig(),new KafkaProducerFactory(hosts));
    }
    @Override
    public KafkaProducer getResource() {
        KafkaProducer connection = super.getResource();
        return connection;
    }
    @Override
    public void returnBrokenResource(final KafkaProducer resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }
    @Override
    public void returnResource(final KafkaProducer resource) {
        if (resource != null) {
            try {
                returnResourceObject(resource);
            } catch (Exception e) {
                returnBrokenResource(resource);
            }
        }
    }
    
}
