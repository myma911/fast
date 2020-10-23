package cn.aaron911.log.core.disruptor;

import java.io.Serializable;

import cn.aaron911.log.core.dto.BaseLogMessage;


public class LogEvent implements Serializable {
   
	/**
     * 日志主体
     */
    private BaseLogMessage baseLogMessage;

    public BaseLogMessage getBaseLogMessage() {
        return baseLogMessage;
    }

    public void setBaseLogMessage(BaseLogMessage baseLogMessage) {
        this.baseLogMessage = baseLogMessage;
    }


    public LogEvent(BaseLogMessage baseLogMessage) {
    	this.baseLogMessage = baseLogMessage;
    }

	public LogEvent() {
		
	}
}
