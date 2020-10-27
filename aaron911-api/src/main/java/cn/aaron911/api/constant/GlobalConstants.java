package cn.aaron911.api.constant;

import cn.aaron911.common.context.HttpRequestContext;
import cn.aaron911.common.result.HttpRequestInfo;
import cn.hutool.core.util.StrUtil;

public class GlobalConstants {

    /**
     * 登录设备类型
     */
    public enum  LoginDevice{
        PC_, MOBILE_;
    }

    
    /**
     * 日志统一前缀格式
     * @return
     */
    public static String logPrefix(){
    	HttpRequestInfo httpRequestInfo = HttpRequestContext.getHttpRequestInfo();
    	if (null == httpRequestInfo) {
    		return StrUtil.format("【traceId：{}】", "");
    	}
        return StrUtil.format("【traceId：{}】", httpRequestInfo.getTraceId());
    }


}
