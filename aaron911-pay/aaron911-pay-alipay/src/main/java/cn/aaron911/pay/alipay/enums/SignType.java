package cn.aaron911.pay.alipay.enums;

/**
 *
 * <p>签名方式</p>
 *
 * @author Javen
 */
public enum SignType {
    /**
     * MD5 加密
     */
    MD5("MD5");

    SignType(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }
}
