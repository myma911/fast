/**
 *
 * <p>关闭订单 Model</p>
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
public class CloseOrderModel extends BaseModel {
    private String appid;
    private String sub_appid;
    private String mch_id;
    private String sub_mch_id;
    private String out_trade_no;
    private String nonce_str;
    private String sign;
    private String sign_type;
}
