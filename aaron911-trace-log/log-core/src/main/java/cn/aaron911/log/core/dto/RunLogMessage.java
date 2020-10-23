package cn.aaron911.log.core.dto;


public class RunLogMessage extends BaseLogMessage{

	/**
	 * 毫秒时间戳
	 */
    private Long dataTimeStamp;
    
    /**
     * 日志内容
     */
    private String content;
    
    /**
     * 日志级别
     */
    private String logLevel;
    
    /**
     * 类名
     */
    private String className;
    
    
    private String dateTime;

    
    public Long getDataTimeStamp() {
		return dataTimeStamp;
	}

	public void setDataTimeStamp(Long dataTimeStamp) {
		this.dataTimeStamp = dataTimeStamp;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
