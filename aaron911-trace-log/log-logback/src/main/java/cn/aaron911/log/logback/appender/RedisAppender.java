package cn.aaron911.log.logback.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import cn.aaron911.log.core.MessageAppenderFactory;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.dto.BaseLogMessage;
import cn.aaron911.log.core.dto.RunLogMessage;
import cn.aaron911.log.core.redis.RedisClient;
import cn.aaron911.log.logback.util.LogMessageUtil;
import cn.hutool.json.JSONUtil;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class RedisAppender extends AppenderBase<ILoggingEvent> {
    private RedisClient redisClient;
    private String appName;
    private String redisHost;
    private String redisPort;
    private String redisAuth;
    private int redisDb=13;
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

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public void setRedisAuth(String redisAuth) {
        this.redisAuth = redisAuth;
    }

    public void setRedisDb(int redisDb) {
        this.redisDb = redisDb;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
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
        if (this.runModel != null) {
            LogMessageConstant.RUN_MODEL = Integer.parseInt(this.runModel);
        }
        if (this.expand != null && LogMessageConstant.EXPANDS.contains(this.expand)) {
            LogMessageConstant.EXPAND = this.expand;
        }
        if (this.redisClient == null) {
            this.redisClient = RedisClient.getInstance(this.redisHost,
                    this.redisPort == null ? LogMessageConstant.REDIS_DEFAULT_PORT : Integer.parseInt(this.redisPort),
                    this.redisAuth, this.redisDb);
        }
        if(MessageAppenderFactory.rundataQueue==null) {
            MessageAppenderFactory.rundataQueue = new LinkedBlockingQueue<>(this.logQueueSize);
            for (int a = 0; a < this.threadPoolSize; a++) {
            	executorService.scheduleAtFixedRate(new RunLogRunnable(this.redisClient, this.maxCount), 10, 10, TimeUnit.MILLISECONDS);
            }
        }
        if(MessageAppenderFactory.tracedataQueue==null) {
            MessageAppenderFactory.tracedataQueue = new LinkedBlockingQueue<>(this.logQueueSize);
            for (int a = 0; a < this.threadPoolSize; a++) {
            	executorService.scheduleAtFixedRate(new TraceLogRunnable(this.redisClient, this.maxCount), 10, 10, TimeUnit.MILLISECONDS);
            }
        }
    }
}


