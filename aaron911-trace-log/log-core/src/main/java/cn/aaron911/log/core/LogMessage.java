package cn.aaron911.log.core;

/**

 *  appName 应用名称用来区分日志属于哪个应用
 *  serverName 应用运行所属IP地址
 *  traceId 应用traceId，配置了拦截器才能自动生成
 *  logType 日志类型，区分运行日志还是链路日志
 */
public class LogMessage {
	
    private String appName;
    private String serverName;
    private Long dataTimeStamp;
    private String traceId;
    private String content;
    private String logLevel;
    private String className;
    private String method;
    private String logType;
    private String dateTime;

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

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }



    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

	public Long getDataTimeStamp() {
		return dataTimeStamp;
	}

	public void setDataTimeStamp(Long dataTimeStamp) {
		this.dataTimeStamp = dataTimeStamp;
	}
}
