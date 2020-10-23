package cn.aaron911.log.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.disruptor.LogMessageProducer;
import cn.aaron911.log.core.disruptor.LogRingBuffer;
import cn.aaron911.log.core.dto.BaseLogMessage;
import cn.aaron911.log.core.exception.LogQueueConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class MessageAppenderFactory {

    private static Boolean logOutPut = true;

    public static BlockingQueue<String> rundataQueue;
    public static BlockingQueue<String> tracedataQueue;

    /**
     * 当下游异常的时候，状态缓存时间
     */
    private final static Cache<String, Boolean> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

    public static void push(BaseLogMessage baseLogMessage) {
        LogMessageProducer producer = new LogMessageProducer(LogRingBuffer.ringBuffer);
        producer.send(baseLogMessage);
    }
    public static void pushRundataQueue(String message) {
        if(message!=null) {
            rundataQueue.add(message);
        }
    }

    public static void pushTracedataQueue(String message) {
        if(message!=null) {
            tracedataQueue.add(message);
        }
    }

    public static void push(String redisKey, List<String> baseLogMessage, AbstractClient client, String logOutPutKey) {
        logOutPut = cache.getIfPresent(logOutPutKey);
        if (logOutPut == null || logOutPut) {
            try {
                client.putMessageList(redisKey, baseLogMessage);
                cache.put(logOutPutKey, true);
            } catch (LogQueueConnectException e) {
                cache.put(logOutPutKey, false);
                e.printStackTrace();
            }
        }else {
            if(redisKey.equals(LogMessageConstant.LOG_KEY)){
                rundataQueue.addAll(baseLogMessage);
            }
            if(redisKey.equals(LogMessageConstant.LOG_KEY_TRACE)){
                tracedataQueue.addAll(baseLogMessage);
            }
        }

    }

    public static void startRunLog(AbstractClient client,int maxCount) {
        try {
            List<String> logs = new ArrayList<>();
            int count=rundataQueue.drainTo(logs, maxCount);
            if(count>0) {
                push(LogMessageConstant.LOG_KEY, logs, client, "aaron911.log.ack");
            }else{
                String log=rundataQueue.take();
                logs.add(log);
                push(LogMessageConstant.LOG_KEY, logs, client, "aaron911.log.ack");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startTraceLog(AbstractClient client,int maxCount) {
        try {
            List<String> logs = new ArrayList<>();
            int count=tracedataQueue.drainTo(logs, maxCount);
            if(count>0) {
                push(LogMessageConstant.LOG_KEY_TRACE, logs, client, "aaron911.log.ack");
            }else{
                String log=tracedataQueue.take();
                logs.add(log);
                push(LogMessageConstant.LOG_KEY_TRACE, logs, client, "aaron911.log.ack");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
