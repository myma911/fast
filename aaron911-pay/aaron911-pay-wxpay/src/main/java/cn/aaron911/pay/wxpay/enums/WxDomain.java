package cn.aaron911.pay.wxpay.enums;
/**
 *
 * <p>微信支付可用域名枚举</p>
 *
 */
public enum WxDomain {
    /**
     * 中国国内
     */
    CHINA("https://api.mch.weixin.qq.com"),
    /**
     * 中国国内(备用域名)
     */
    CHINA2("https://api2.mch.weixin.qq.com"),
    /**
     * 东南亚
     */
    HK("https://apihk.mch.weixin.qq.com"),
    /**
     * 其它
     */
    US("https://apius.mch.weixin.qq.com");

    
    /**
     * 域名
     */
    private final String domain;

    WxDomain(String domain) {
        this.domain = domain;
    }

    public String getType() {
        return domain;
    }

    @Override
    public String toString() {
        return domain;
    }
}
