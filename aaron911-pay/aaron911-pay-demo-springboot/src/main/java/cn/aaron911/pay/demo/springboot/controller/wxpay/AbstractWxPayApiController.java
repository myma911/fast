package cn.aaron911.pay.demo.springboot.controller.wxpay;

import cn.aaron911.pay.wxpay.WxPayApiConfig;

/**
 * 
 */
public abstract class AbstractWxPayApiController {
    /**
     * 获取微信支付配置
     *
     * @return {@link WxPayApiConfig} 微信支付配置
     */
    public abstract WxPayApiConfig getApiConfig();
}
