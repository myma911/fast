package cn.aaron911.log.server.collect;


import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.server.monitor.LogMonitorEvent;
import cn.aaron911.log.server.properties.DefaultInitProperty;
import cn.aaron911.log.server.util.ElasticLowerClientSingleton;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;



public class BaseLogCollect {
    public ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4);
    public ElasticLowerClientSingleton elasticLowerClient;
    protected ApplicationEventPublisher applicationEventPublisher;

    public String getRunLogIndex(){
        if(DefaultInitProperty.ES_INDEX_MODEL.equals("day")) {
            return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN + "_" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        }else {
            return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN + "_" + DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN + "HH");
        }
    }
    public String getTraceLogIndex(){
        if(DefaultInitProperty.ES_INDEX_MODEL.equals("day")) {
            return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE + "_" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        }else {
            return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE + "_" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT + "HH");
        }
    }

    public void sendLog(String index, List<String> sendList) {
    	if (CollUtil.isNotEmpty(sendList)) {
    		elasticLowerClient.insertListLog(sendList, index);
    	}
    }

    public void sendTraceLogList(String index, List<String> sendTraceLogList) {
    	if (CollUtil.isNotEmpty(sendTraceLogList)) {
            elasticLowerClient.insertListTrace(sendTraceLogList, index);
        }
    }

    protected void publisherMonitorEvent(List<String> logs) {
    	if (CollUtil.isNotEmpty(logs)) {
    		applicationEventPublisher.publishEvent(new LogMonitorEvent(this, logs));
    	}
    }
}
