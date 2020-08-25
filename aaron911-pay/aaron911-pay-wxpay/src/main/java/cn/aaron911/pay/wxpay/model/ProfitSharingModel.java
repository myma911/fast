/**
 *
 * <p>分账 Model</p>
 *
 * <p>支持: 请求单次分账、请求多次分账、添加分账接收方、删除分账接收方、完结分账</p>
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
public class ProfitSharingModel extends BaseModel {
    private String appid;
    private String sub_appid;
    private String mch_id;
    private String sub_mch_id;
    private String nonce_str;
    private String sign;
    private String sign_type;
    private String transaction_id;
    private String out_order_no;
    private String receivers;
    private String description;
}
