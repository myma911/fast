package cn.aaron911.log.core;


import com.alibaba.ttl.TransmittableThreadLocal;


public class LogMessageThreadLocal {
    public static TransmittableThreadLocal<TraceMessage> logMessageThreadLocal = new TransmittableThreadLocal<>();
}
