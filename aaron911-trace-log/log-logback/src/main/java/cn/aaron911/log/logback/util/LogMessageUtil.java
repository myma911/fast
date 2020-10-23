package cn.aaron911.log.logback.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import cn.aaron911.log.core.LogMessageThreadLocal;
import cn.aaron911.log.core.TraceId;
import cn.aaron911.log.core.TraceMessage;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.dto.BaseLogMessage;
import cn.aaron911.log.core.dto.RunLogMessage;
import cn.aaron911.log.core.util.LogExceptionStackTrace;
import cn.aaron911.log.core.util.TraceLogMessageFactory;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;

import org.slf4j.helpers.MessageFormatter;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class LogMessageUtil {

    /**
     * 扩展字段
     * @param baseLogMessage
     * @param iLoggingEvent
     * @return
     */
    public static String getLogMessage(BaseLogMessage baseLogMessage,final ILoggingEvent iLoggingEvent){
        Map<String, String> mdc= iLoggingEvent.getMDCPropertyMap();
        Map<String, Object> map = BeanUtil.beanToMap(baseLogMessage);
        map.putAll(mdc);
        return JSONUtil.toJsonStr(map);
    }

    public static BaseLogMessage getLogMessage(final String appName, final ILoggingEvent iLoggingEvent) {
        TraceMessage traceMessage = LogMessageThreadLocal.logMessageThreadLocal.get();
        String formattedMessage = getMessage(iLoggingEvent);
        if (formattedMessage.startsWith(LogMessageConstant.TRACE_PRE)) {
            return TraceLogMessageFactory.getTraceLogMessage(traceMessage, appName, iLoggingEvent.getTimeStamp());
        }
        RunLogMessage logMessage = TraceLogMessageFactory.getLogMessage(appName, formattedMessage, iLoggingEvent.getTimeStamp());
        logMessage.setClassName(iLoggingEvent.getLoggerName());
        if (LogMessageConstant.RUN_MODEL == 1) {
            logMessage.setMethod(iLoggingEvent.getThreadName());
        } else {
            StackTraceElement atackTraceElement = iLoggingEvent.getCallerData()[0];
            String method = atackTraceElement.getMethodName();
            String line = String.valueOf(atackTraceElement.getLineNumber());
            logMessage.setMethod(method + "(" +atackTraceElement.getFileName()+":"+ line + ")");
            Date date = new Date(iLoggingEvent.getTimeStamp());
            logMessage.setDateTime(DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN));
        }
        logMessage.setLogLevel(iLoggingEvent.getLevel().toString());
        return logMessage;
    }

    private static String getMessage(ILoggingEvent logEvent) {
        if (logEvent.getLevel().equals(Level.ERROR)) {
            if (logEvent.getThrowableProxy() != null) {
                ThrowableProxy throwableProxy = (ThrowableProxy) logEvent.getThrowableProxy();
                String[] args = new String[]{LogExceptionStackTrace.erroStackTrace(throwableProxy.getThrowable()).toString()};
                return packageMessage(logEvent.getMessage(), args);
            } else {
                Object[] args = logEvent.getArgumentArray();
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        if (args[i] instanceof Throwable) {
                            args[i] = LogExceptionStackTrace.erroStackTrace(args[i]);
                        }
                    }
                    return packageMessage(logEvent.getMessage(), args);
                }
            }
        }
        return logEvent.getFormattedMessage();
    }

    private static String packageMessage(String message, Object[] args) {
        if (message!=null&&message.indexOf(LogMessageConstant.DELIM_STR) > -1) {
            return MessageFormatter.arrayFormat(message, args).getMessage();
        }
        return TraceLogMessageFactory.packageMessage(message, args);
    }
}
