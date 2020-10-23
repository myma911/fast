package cn.aaron911.log.logback.appender;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import cn.aaron911.log.core.MessageAppenderFactory;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.dto.BaseLogMessage;
import cn.aaron911.log.core.dto.RunLogMessage;
import cn.aaron911.log.core.kafka.KafkaProducerClient;
import cn.aaron911.log.logback.util.LogMessageUtil;
import cn.hutool.json.JSONUtil;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description：KafkaAppender 如果使用kafka作为队列用这个KafkaAppender输出
 *
 */
public class KafkaAppender extends AppenderBase<ILoggingEvent> {
    private KafkaProducerClient kafkaClient;
    private String appName;
    private String kafkaHosts;
    private String runModel;
    private String expand;
    private int maxCount=100;
    private int logQueueSize=10000;
    private int threadPoolSize=1;

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setKafkaHosts(String kafkaHosts) {
        this.kafkaHosts = kafkaHosts;
    }

    public void setRunModel(String runModel) {
        this.runModel = runModel;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setLogQueueSize(int logQueueSize) {
        this.logQueueSize = logQueueSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    @Override
    protected void append(ILoggingEvent event) {
        final BaseLogMessage logMessage = LogMessageUtil.getLogMessage(appName, event);
        if (logMessage instanceof RunLogMessage) {
            final String message = LogMessageUtil.getLogMessage(logMessage, event);
            MessageAppenderFactory.pushRundataQueue(message);
        } else {
            MessageAppenderFactory.pushTracedataQueue(JSONUtil.toJsonStr(logMessage));
        }
    }
    
    public ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4);
    
    
    @Override
    public void start() {
        super.start();
        if(null != this.runModel){
            LogMessageConstant.RUN_MODEL = Integer.parseInt(this.runModel);
        }
        if (null == this.kafkaClient) {
            this.kafkaClient = KafkaProducerClient.getInstance(this.kafkaHosts);
        }
        if (null != this.expand && LogMessageConstant.EXPANDS.contains(expand)) {
            LogMessageConstant.EXPAND = expand;
        }
        
        
        if(MessageAppenderFactory.rundataQueue==null) {
            MessageAppenderFactory.rundataQueue = new LinkedBlockingQueue<>(this.logQueueSize);
            for (int a = 0; a < this.threadPoolSize; a++) {
            	executorService.scheduleAtFixedRate(new RunLogRunnable(this.kafkaClient, this.maxCount), 10, 10, TimeUnit.MILLISECONDS);
            }
        }
        if(MessageAppenderFactory.tracedataQueue==null) {
            MessageAppenderFactory.tracedataQueue = new LinkedBlockingQueue<>(this.logQueueSize);
            for (int a = 0; a < this.threadPoolSize; a++) {
            	executorService.scheduleAtFixedRate(new TraceLogRunnable(this.kafkaClient, this.maxCount), 10, 10, TimeUnit.MILLISECONDS);
            }
        }
    }
}
