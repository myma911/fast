package cn.aaron911.log.core.dto;

/**
 * 
 * description：错误告警规则
 */
public class WarningRule {

    private String appName;
    private String className;
    private String receiver;
    private String webhookUrl;
    private int errorCount;
    private int time;
    private int status;
    private int hookServe = 1; // 1 Wechat 

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHookServe() {
        return hookServe;
    }

    public void setHookServe(int hookServe) {
        this.hookServe = hookServe;
    }
}
