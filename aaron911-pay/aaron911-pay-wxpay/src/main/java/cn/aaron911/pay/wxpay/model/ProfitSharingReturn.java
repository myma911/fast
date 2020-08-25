/**
 *
 * <p>分账回退 Model</p>
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
public class ProfitSharingReturn extends BaseModel {
    private String appid;
    private String sub_appid;
    private String mch_id;
    private String sub_mch_id;
    private String nonce_str;
    private String sign;
    private String sign_type;
    private String order_id;
    private String out_order_no;
    private String out_return_no;
    private String return_account_type;
    private String return_account;
    private String return_amount;
    private String description;
}
