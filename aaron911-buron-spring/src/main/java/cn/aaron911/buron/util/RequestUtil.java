package cn.aaron911.buron.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @version 1.0
 * 
 */
public class RequestUtil {

    private HttpServletRequest request;

    public RequestUtil(HttpServletRequest request) {
        this.request = request;
    }

    public String getParameters() {
        Enumeration<String> paraNames = request.getParameterNames();
        if (paraNames == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (paraNames.hasMoreElements()) {
            String paraName = paraNames.nextElement();
            sb.append("&").append(paraName).append("=").append(request.getParameter(paraName));
        }
        return sb.toString();
    }

    public String getHeader(String headerName) {
        return request.getHeader(headerName);
    }

    public String getReferer() {
        return getHeader("Referer");
    }

    public String getUa() {
        return getHeader("User-Agent");
    }

    public String getIp() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public String getRequestUrl() {
        return request.getRequestURL().toString();
    }

    public String getServletPath() {
        return request.getServletPath();
    }

    public String getMethod() {
        return request.getMethod();
    }

}