/**
 *
 * <p> 微信发送红包 Mode </p>
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
public class SendReadPackModel extends BaseModel {
    private String nonce_str;
    private String sign;
    private String mch_billno;
    private String mch_id;
    private String wxappid;
    private String send_name;
    private String re_openid;
    private String total_amount;
    private String total_num;
    private String amt_type;
    private String wishing;
    private String client_ip;
    private String act_name;
    private String remark;
    private String scene_id;
    private String risk_info;
    private String notify_way;
}
