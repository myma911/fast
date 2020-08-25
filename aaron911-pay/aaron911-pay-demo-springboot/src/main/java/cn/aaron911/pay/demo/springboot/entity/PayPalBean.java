package cn.aaron911.pay.demo.springboot.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * <p>PayPal配置 Bean</p>
 *
 */
@Component
@PropertySource("classpath:/paypal.properties")
@ConfigurationProperties(prefix = "paypal")
public class PayPalBean {
    private String clientId;
    private String clientSecret;
    private String mode;
    private String domain;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "PayPalBean{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", mode='" + mode + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}