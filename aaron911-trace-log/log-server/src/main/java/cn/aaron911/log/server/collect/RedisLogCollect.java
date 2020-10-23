package cn.aaron911.log.server.collect;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.exception.LogQueueConnectException;
import cn.aaron911.log.core.redis.RedisClient;
import cn.aaron911.log.server.properties.DefaultInitProperty;
import cn.aaron911.log.server.util.ElasticLowerClientSingleton;

/**
 * 
 * description：RedisLogCollect 获取redis中日志，存储到es
 *                 
 */
public class RedisLogCollect extends BaseLogCollect {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(RedisLogCollect.class);
    private RedisClient redisClient;

    public RedisLogCollect(ElasticLowerClientSingleton elasticLowerClient, RedisClient redisClient, ApplicationEventPublisher applicationEventPublisher) {
        super.elasticLowerClient = elasticLowerClient;
        this.redisClient = redisClient;
        super.applicationEventPublisher = applicationEventPublisher;
    }

    public void redisStart() {
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
            	collectRuningLog();
            }
        }, 1000, DefaultInitProperty.MAX_INTERVAL, TimeUnit.MILLISECONDS);
    	
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
            	collectTraceLog();
            }
        }, 1000, DefaultInitProperty.MAX_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private void collectRuningLog() {
        try {
        	List<String> logs = redisClient.getMessage(LogMessageConstant.LOG_KEY, DefaultInitProperty.MAX_SEND_SIZE);
            if(logger.isDebugEnabled()){
	            logs.forEach(log->{
	                logger.debug(log);
	            });
            }
            super.sendLog(super.getRunLogIndex(), logs);
            //发布一个事件
            publisherMonitorEvent(logs);
        } catch (LogQueueConnectException e) {
            logger.error("从redis队列拉取日志失败！", e);
        }
    }
    private void collectTraceLog() {
        try {
        	List<String> logs = redisClient.getMessage(LogMessageConstant.LOG_KEY_TRACE, DefaultInitProperty.MAX_SEND_SIZE);
            if(logger.isDebugEnabled()){
                logs.forEach(log->{
                    logger.debug(log);
                });
            }
            super.sendTraceLogList(super.getTraceLogIndex(), logs);
        } catch (LogQueueConnectException e) {
            logger.error("从redis队列拉取日志失败！", e);
        }
    }
}
