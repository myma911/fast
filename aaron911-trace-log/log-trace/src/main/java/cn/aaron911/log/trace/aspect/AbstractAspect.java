package cn.aaron911.log.trace.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.LoggerFactory;

import cn.aaron911.log.core.LogMessageThreadLocal;
import cn.aaron911.log.core.TraceId;
import cn.aaron911.log.core.TraceMessage;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.hutool.json.JSONUtil;

public abstract class AbstractAspect {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(AbstractAspect.class);
    
    public Object aroundExecute(JoinPoint joinPoint) throws Throwable {
        TraceMessage traceMessage = LogMessageThreadLocal.logMessageThreadLocal.get();
        String traceId = TraceId.logTraceID.get();
        if (traceMessage == null || traceId == null) {
            traceMessage = new TraceMessage();
            traceMessage.getPositionNum().set(0);
        }
        traceMessage.setTraceId(traceId);
        traceMessage.setMessageType(joinPoint.getSignature().toString());
        traceMessage.setPosition(LogMessageConstant.TRACE_START);
        traceMessage.getPositionNum().incrementAndGet();
        LogMessageThreadLocal.logMessageThreadLocal.set(traceMessage);
        logger.info(LogMessageConstant.TRACE_PRE + JSONUtil.toJsonStr(traceMessage));
        Object proceed = ((ProceedingJoinPoint) joinPoint).proceed();
        traceMessage.setMessageType(joinPoint.getSignature().toString());
        traceMessage.setPosition(LogMessageConstant.TRACE_END);
        traceMessage.getPositionNum().incrementAndGet();
        logger.info(LogMessageConstant.TRACE_PRE + JSONUtil.toJsonStr(traceMessage));
        return proceed;
    }
}
