package cn.aaron911.pay.wxpay;

import lombok.*;

import java.io.Serializable;

/**
 * <p>微信支付常用配置</p>
 *
 */

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WxPayApiConfig implements Serializable {
    private static final long serialVersionUID = -9044503427692786302L;
    /**
     * 应用编号
     */
    private String appId;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 服务商应用编号
     */
    private String slAppId;
    /**
     * 服务商商户号
     */
    private String slMchId;
    /**
     * 同 apiKey 后续版本会舍弃
     */
    private String partnerKey;
    /**
     * 商户平台「API安全」中的 API 密钥
     */
    private String apiKey;
    /**
     * 商户平台「API安全」中的 APIv3 密钥
     */
    private String apiKey3;
    /**
     * 应用域名，回调中会使用此参数
     */
    private String domain;
    /**
     * API 证书中的 p12
     */
    private String certPath;
    /**
     * API 证书中的 key.pem
     */
    private String keyPemPath;
    /**
     * API 证书中的 cert.pem
     */
    private String certPemPath;
    /**
     * 其他附加参数
     */
    private Object exParams;
}