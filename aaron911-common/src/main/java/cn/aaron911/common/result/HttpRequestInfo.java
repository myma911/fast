package cn.aaron911.common.result;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.useragent.UserAgent;
import java.util.Map;

public class HttpRequestInfo {

    /**
     * 链路traceId
     */
    private String traceId;

    /**
     * 请求开始时间
     */
    private Long requestStartTime = System.currentTimeMillis();

    private String requestStartTimeStr;

    private Long requestEndTime;

    private String requestEndTimeStr;

    private Long requestTime;

    /**
     * 项目自定义的app请求
     */
    private String appVersion;

    /**
     * 请求代理信息
     */
    private UserAgent userAgent;

    /**
     * ip
     */
    private String ip;

    /**
     * url
     */
    private String url;

    /**
     * httpMethod
     */
    private String httpMethod;

    /**
     * token
     */
    private String token;

    private String className;

    private String methodName;

    /**
     * 进入controller 时候的请求参数
     */
    private Map requestParam;

    /**
     * 从httpRequest中获取的parameterMap
     */
    private Map parameterMap;

    private Object requestReturn;

    private String  exceptionMessage;

    public String getRequestStartTimeStr() {
        if (null != requestEndTime) {
            return DateUtil.formatDateTime(DateUtil.date(requestStartTime));
        }
        return "";
    }

    public Long getRequestTime() {
        if (null != requestEndTime && null != requestStartTime) {
            return requestEndTime - requestStartTime;
        }
        return -1L;
    }

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public Long getRequestStartTime() {
		return requestStartTime;
	}

	public void setRequestStartTime(Long requestStartTime) {
		this.requestStartTime = requestStartTime;
	}

	public Long getRequestEndTime() {
		return requestEndTime;
	}

	public void setRequestEndTime(Long requestEndTime) {
		this.requestEndTime = requestEndTime;
	}

	public String getRequestEndTimeStr() {
		return requestEndTimeStr;
	}

	public void setRequestEndTimeStr(String requestEndTimeStr) {
		this.requestEndTimeStr = requestEndTimeStr;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public UserAgent getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Map getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Map requestParam) {
		this.requestParam = requestParam;
	}

	public Map getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map parameterMap) {
		this.parameterMap = parameterMap;
	}

	public Object getRequestReturn() {
		return requestReturn;
	}

	public void setRequestReturn(Object requestReturn) {
		this.requestReturn = requestReturn;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public void setRequestStartTimeStr(String requestStartTimeStr) {
		this.requestStartTimeStr = requestStartTimeStr;
	}

	public void setRequestTime(Long requestTime) {
		this.requestTime = requestTime;
	}
}
