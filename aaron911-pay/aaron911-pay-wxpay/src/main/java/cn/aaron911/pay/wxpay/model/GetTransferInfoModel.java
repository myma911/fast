/**
 *
 * <p>企业付款到零钱 Model</p>
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
public class GetTransferInfoModel extends BaseModel {
    private String nonce_str;
    private String sign;
    private String partner_trade_no;
    private String mch_id;
    private String appid;

}
