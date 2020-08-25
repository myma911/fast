package cn.aaron911.pay.demo.springboot.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * <p>JD配置 Bean</p>
 *
 */
@Component
@PropertySource("classpath:/jdpay.properties")
@ConfigurationProperties(prefix = "jdpay")
public class JdPayBean {
    private String mchId;
    private String rsaPrivateKey;
    private String desKey;
    private String rsaPublicKey;
    private String certPath;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    @Override
    public String toString() {
        return "JdPayBean{" +
                "mchId='" + mchId + '\'' +
                ", rsaPrivateKey='" + rsaPrivateKey + '\'' +
                ", desKey='" + desKey + '\'' +
                ", rsaPublicKey='" + rsaPublicKey + '\'' +
                ", certPath='" + certPath + '\'' +
                '}';
    }
}