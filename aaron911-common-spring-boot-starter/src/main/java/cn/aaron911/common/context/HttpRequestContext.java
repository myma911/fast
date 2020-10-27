package cn.aaron911.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import cn.aaron911.common.result.HttpRequestInfo;



public class HttpRequestContext {

    private static TransmittableThreadLocal<HttpRequestInfo> httpRequestInfoThreadLocalThreadLocal = new TransmittableThreadLocal<>();


    public static HttpRequestInfo getHttpRequestInfo(){
        return httpRequestInfoThreadLocalThreadLocal.get();
    }


    public static void setHttpRequestInfo(HttpRequestInfo httpRequestInfo){
        if (null == httpRequestInfo) {
            throw new IllegalArgumentException();
        }
        httpRequestInfoThreadLocalThreadLocal.set(httpRequestInfo);
    }

    public static void remove(){
        httpRequestInfoThreadLocalThreadLocal.remove();
    }
}
