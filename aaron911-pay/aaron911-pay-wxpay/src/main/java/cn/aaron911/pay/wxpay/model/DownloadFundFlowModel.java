/**
 *
 * <p>下载资金账单 Model</p>
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
public class DownloadFundFlowModel extends BaseModel {
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String sign_type;
    private String bill_date;
    private String account_type;
    private String tar_type;
}
