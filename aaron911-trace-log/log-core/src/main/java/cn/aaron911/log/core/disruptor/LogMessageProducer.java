package cn.aaron911.log.core.disruptor;


import com.lmax.disruptor.RingBuffer;

import cn.aaron911.log.core.dto.BaseLogMessage;


public class LogMessageProducer {


    private RingBuffer<LogEvent> ringBuffer;

    public LogMessageProducer(RingBuffer<LogEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void send(BaseLogMessage data) {
        long next = ringBuffer.next();
        try {
            LogEvent event = ringBuffer.get(next);
            event.setBaseLogMessage(data);
        } finally {
            ringBuffer.publish(next);
        }
    }
}
