package cn.aaron911.pay.core.enums;

/**
 *
 * <p>支付方式</p>
 *
 */
public enum TradeType {
    /**
     * 微信公众号支付或者小程序支付
     */
    JSAPI("JSAPI"),
    /**
     * 微信扫码支付
     */
    NATIVE("NATIVE"),
    /**
     * 微信APP支付
     */
    APP("APP"),
    /**
     * 付款码支付
     */
    MICROPAY("MICROPAY"),
    /**
     * H5支付
     */
    MWEB("MWEB");

    TradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 交易类型
     */
    private final String tradeType;

    public String getTradeType() {
        return tradeType;
    }
}
