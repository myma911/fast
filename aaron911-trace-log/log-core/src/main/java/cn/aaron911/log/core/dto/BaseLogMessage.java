package cn.aaron911.log.core.dto;



public class BaseLogMessage {
    /**
     * 记录服务IP
     */
    private String serverName;

    /**
     * 追踪码
     */
    private String traceId;

    /**
     * 应用名
     */
    private String appName;
    
	  /**
     * 方法名
     */
    private String method;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}


    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
