package cn.aaron911.log.core.kafka;


import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class KafkaProducerFactory implements PooledObjectFactory<KafkaProducer> {
    private Properties props=new Properties();
    KafkaProducerFactory(String hosts){
        this.props.put(ProducerConfig.ACKS_CONFIG, "0");
        this.props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts);
        this.props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.props.put(ProducerConfig.LINGER_MS_CONFIG,10);
    }
    @Override
    public void activateObject(PooledObject<KafkaProducer> kafkaProducer) throws Exception {

    }
    @Override
    public void destroyObject(PooledObject<KafkaProducer> kafkaProducer) throws Exception{
        kafkaProducer.getObject().close();
    }
    @Override
    public PooledObject<KafkaProducer> makeObject() throws Exception {
        KafkaProducer kafkaProducer=new KafkaProducer<>(props);
        return new DefaultPooledObject<>(kafkaProducer);
    }
    @Override
    public void passivateObject(PooledObject<KafkaProducer> kafkaProducer) throws Exception {
        // TODO maybe should select db 0? Not sure right now.
    }
    @Override
    public boolean validateObject(PooledObject<KafkaProducer> kafkaProducer) {
        return true;
    }

}
