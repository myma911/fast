/**
 *
 * <p>撤销订单 Model</p>
 * <p>支持: 普通支付撤销订单、刷脸支付撤销订单</p>
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
public class ReverseModel extends BaseModel {
    private String appid;
    private String sub_appid;
    private String mch_id;
    private String sub_mch_id;
    private String transaction_id;
    private String out_trade_no;
    private String nonce_str;
    private String sign;
    private String sign_type;
}
