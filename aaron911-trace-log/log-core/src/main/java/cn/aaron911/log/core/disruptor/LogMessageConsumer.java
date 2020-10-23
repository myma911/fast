package cn.aaron911.log.core.disruptor;

import com.lmax.disruptor.WorkHandler;

import cn.aaron911.log.core.AbstractClient;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.dto.BaseLogMessage;
import cn.aaron911.log.core.dto.RunLogMessage;
import cn.hutool.json.JSONUtil;


public class LogMessageConsumer implements WorkHandler<LogEvent> {

    private String name;

    public LogMessageConsumer(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(LogEvent event) throws Exception {
        BaseLogMessage baseLogMessage = event.getBaseLogMessage();
        final String redisKey = baseLogMessage instanceof RunLogMessage ? LogMessageConstant.LOG_KEY : LogMessageConstant.LOG_KEY_TRACE;
        AbstractClient.getClient().pushMessage(redisKey, JSONUtil.toJsonStr(baseLogMessage));
    }
}
