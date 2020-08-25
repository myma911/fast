/**
 *
 * <p>消费押金 Model</p>
 *
 * @author Javen
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
public class DepositConsume extends BaseModel {
    private String appid;
    private String sub_appid;
    private String mch_id;
    private String sub_mch_id;
    private String transaction_id;
    private String out_trade_no;
    private String total_fee;
    private String consume_fee;
    private String fee_type;
    private String nonce_str;
    private String sign;
    private String sign_type;
}
