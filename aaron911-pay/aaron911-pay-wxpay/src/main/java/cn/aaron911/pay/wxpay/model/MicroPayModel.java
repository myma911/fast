/**
 *
 * <p>付款码支付 Model</p>
 * <p>支持: 付款支付、支付押金（人脸支付）、支付押金（付款码支付）</p>
 *
 */
package cn.aaron911.pay.wxpay.model;

import cn.aaron911.pay.core.model.BaseModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MicroPayModel extends BaseModel {
    /**
     * 是否押金支付
     */
    private String deposit;
    private String appid;
    private String sub_appid;
    private String mch_id;
    private String sub_mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String sign_type;
    private String body;
    private String detail;
    private String attach;
    private String out_trade_no;
    private String total_fee;
    private String fee_type;
    private String spbill_create_ip;
    private String goods_tag;
    private String limit_pay;
    private String time_start;
    private String time_expire;
    private String auth_code;
    private String receipt;
    private String scene_info;
    private String openid;
    /**
     * 人脸凭证，用于人脸支付
     */
    private String face_code;
}
