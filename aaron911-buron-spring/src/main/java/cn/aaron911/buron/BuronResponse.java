package cn.aaron911.buron;

import javax.servlet.http.HttpServletRequest;

import cn.aaron911.buron.util.RequestUtil;


public class BuronResponse {

    private int code;
    private String msg;

    private long expire;
    private int limitCount;

    private AccessInfo accessInfo;

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public BuronResponse setAccessInfo(HttpServletRequest request) {
        if (this.accessInfo == null) {
            this.accessInfo = new AccessInfo();
        }
        RequestUtil requestUtil = new RequestUtil(request);
        this.accessInfo.setIp(requestUtil.getIp())
                .setParams(requestUtil.getParameters())
                .setReferer(requestUtil.getReferer())
                .setRequestUrl(requestUtil.getRequestUrl())
                .setUa(requestUtil.getUa());
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public BuronResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public long getExpire() {
        return expire;
    }

    BuronResponse setExpire(long expire) {
        this.expire = expire;
        return this;
    }

    public int getLimitCount() {
        return limitCount;
    }

    BuronResponse setLimitCount(int limitCount) {
        this.limitCount = limitCount;
        return this;
    }

    BuronResponse isSuccess() {
        this.code = 1;
        return this;
    }

    BuronResponse isError() {
        this.code = 0;
        return this;
    }


    private class AccessInfo {
        private String ip;
        private String ua;
        private String referer;
        private String requestUrl;
        private String params;

        public String getIp() {
            return ip;
        }

        AccessInfo setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public String getUa() {
            return ua;
        }

        AccessInfo setUa(String ua) {
            this.ua = ua;
            return this;
        }

        public String getReferer() {
            return referer;
        }

        AccessInfo setReferer(String referer) {
            this.referer = referer;
            return this;
        }

        public String getRequestUrl() {
            return requestUrl;
        }

        AccessInfo setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        public String getParams() {
            return params;
        }

        AccessInfo setParams(String params) {
            this.params = params;
            return this;
        }
    }


}
