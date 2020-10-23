package cn.aaron911.log.logback.appender;

import cn.aaron911.log.core.AbstractClient;
import cn.aaron911.log.core.MessageAppenderFactory;

public class TraceLogRunnable implements Runnable{
	
	private AbstractClient client;
	private int maxCount;
	
	public TraceLogRunnable(AbstractClient client, int maxCount) {
		this.client = client;
		this.maxCount = maxCount;
	}

	@Override
	public void run() {
		MessageAppenderFactory.startTraceLog(this.client, this.maxCount);
	}

}