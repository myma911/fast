/**
 *
 * <p>查询红包记录</p>
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
public class GetHbInfoModel extends BaseModel {
    private String nonce_str;
    private String sign;
    private String mch_billno;
    private String mch_id;
    private String appid;
    private String bill_type;
}
