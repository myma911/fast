package cn.aaron911.log.server.monitor;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * description： 日志报警事件
 */

public class LogMonitorEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	/**
     * 日志信息列表
     */
    List<String> logs;

    public LogMonitorEvent(Object source, List<String> logs) {
        super(source);
        this.logs = logs;
    }

    public List<String> getLogs() {
        return logs;
    }

}
