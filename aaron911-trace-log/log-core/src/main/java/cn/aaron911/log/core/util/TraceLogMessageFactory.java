package cn.aaron911.log.core.util;

import cn.aaron911.log.core.TraceId;
import cn.aaron911.log.core.TraceMessage;
import cn.aaron911.log.core.dto.RunLogMessage;
import cn.aaron911.log.core.dto.TraceLogMessage;

public class TraceLogMessageFactory<T> {

    public static TraceLogMessage getTraceLogMessage(TraceMessage traceMessage, String appName, long time) {
        TraceLogMessage traceLogMessage = new TraceLogMessage();
        traceLogMessage.setAppName(appName);
        traceLogMessage.setTraceId(traceMessage.getTraceId());
        traceLogMessage.setMethod(traceMessage.getMessageType());
        traceLogMessage.setTime(time);
        traceLogMessage.setPosition(traceMessage.getPosition());
        traceLogMessage.setPositionNum(traceMessage.getPositionNum().get());
        traceLogMessage.setServerName(IpGetter.CURRENT_IP);
        return traceLogMessage;
    }

    public static RunLogMessage getLogMessage(String appName, String message, long time) {
        RunLogMessage logMessage = new RunLogMessage();
        logMessage.setServerName(IpGetter.CURRENT_IP);
        logMessage.setAppName(appName);
        logMessage.setContent(message);
        logMessage.setDataTimeStamp(time);
        logMessage.setTraceId(TraceId.logTraceID.get());
        return logMessage;
    }

    public static String packageMessage(String message, Object[] args) {
        StringBuilder builder = new StringBuilder(128);
        builder.append(message);
        for (int i = 0; i < args.length; i++) {
            builder.append("\n").append(args[i]);
        }
        return builder.toString();
    }

}
