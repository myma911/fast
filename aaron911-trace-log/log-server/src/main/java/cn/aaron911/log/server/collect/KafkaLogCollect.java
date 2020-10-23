package cn.aaron911.log.server.collect;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.server.properties.DefaultInitProperty;
import cn.aaron911.log.server.util.ElasticLowerClientSingleton;
import cn.hutool.core.collection.CollUtil;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 
 * description：KafkaLogCollect 获取kafka中日志，存储到es
 * 
 */
public class KafkaLogCollect extends BaseLogCollect {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(KafkaLogCollect.class);

	private KafkaConsumer<String, String> kafkaConsumer;

	public KafkaLogCollect(ElasticLowerClientSingleton elasticLowerClient, KafkaConsumer<String, String> kafkaConsumer,
			ApplicationEventPublisher applicationEventPublisher) {
		super.elasticLowerClient = elasticLowerClient;
		this.kafkaConsumer = kafkaConsumer;
		this.kafkaConsumer.subscribe(Arrays.asList(LogMessageConstant.LOG_KEY, LogMessageConstant.LOG_KEY_TRACE));
		super.applicationEventPublisher = applicationEventPublisher;
	}

	public void kafkaStart() {
		executorService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				collectRuningLog();
			}
		}, 1000, DefaultInitProperty.MAX_INTERVAL, TimeUnit.MILLISECONDS);
	}

	public void collectRuningLog() {
		List<String> logList = new ArrayList();
		List<String> sendlogList = new ArrayList();
		try {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
			records.forEach(record -> {
				if (logger.isDebugEnabled()) {
					logger.debug("get log:" + record.value() + "  logType:" + record.topic());
				}
				if (record.topic().equals(LogMessageConstant.LOG_KEY)) {
					logList.add(record.value());
				}
				if (record.topic().equals(LogMessageConstant.LOG_KEY_TRACE)) {
					sendlogList.add(record.value());
				}
			});
		} catch (Exception e) {
			logger.error("get logs from kafka failed! ", e);
		} finally {
			if (CollUtil.isNotEmpty(logList)) {
				super.sendLog(super.getRunLogIndex(), logList);
				publisherMonitorEvent(logList);
			}
			if (CollUtil.isNotEmpty(sendlogList)) {
				super.sendTraceLogList(super.getTraceLogIndex(), sendlogList);
			}
		}
	}
}
