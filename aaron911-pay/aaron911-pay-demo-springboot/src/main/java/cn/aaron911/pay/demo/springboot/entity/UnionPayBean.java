package cn.aaron911.pay.demo.springboot.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * <p>云闪付配置 Bean</p>
 *
 */
@Component
@PropertySource("classpath:/unionpay.properties")
@ConfigurationProperties(prefix = "union")
public class UnionPayBean {
    private String machId;
    private String key;
    private String serverUrl;
    private String domain;

    public String getMachId() {
        return machId;
    }

    public void setMachId(String machId) {
        this.machId = machId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "UnionPayBean{" +
                "machId='" + machId + '\'' +
                ", key='" + key + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}