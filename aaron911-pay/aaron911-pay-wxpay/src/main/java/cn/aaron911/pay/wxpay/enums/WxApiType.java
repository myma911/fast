package cn.aaron911.pay.wxpay.enums;

/**
 *
 * <p>微信支付接口枚举</p>
 *
 */
public enum WxApiType {
    /**
     * 沙箱环境
     */
    SAND_BOX_NEW("/sandboxnew"),
    /**
     * 获取沙箱环境验签秘钥
     */
    GET_SIGN_KEY("/sandboxnew/pay/getsignkey"),
    /**
     * 统一下单
     */
    UNIFIED_ORDER("/pay/unifiedorder"),
    /**
     * 提交付款码支付
     */
    MICRO_PAY("/pay/micropay"),
    /**
     * 查询订单
     */
    ORDER_QUERY("/pay/orderquery"),
    /**
     * 关闭订单
     */
    CLOSE_ORDER("/pay/closeorder"),
    /**
     * 撤销订单
     */
    REVERSE("/secapi/pay/reverse"),
    /**
     * 申请退款
     */
    REFUND("/secapi/pay/refund"),
    /**
     * 查询退款
     */
    REFUND_QUERY("/pay/refundquery"),
    /**
     * 下载对账单
     */
    DOWNLOAD_BILL("/pay/downloadbill"),
    /**
     * 下载资金对账单
     */
    DOWNLOAD_FUND_FLOW("/pay/downloadfundflow"),
    /**
     * 交易保障
     */
    REPORT("/payitil/report"),
    /**
     * 转换短链接
     */
    SHORT_URL("/tools/shorturl"),
    /**
     * 授权码查询 openId
     */
    AUTH_CODE_TO_OPENID("/tools/authcodetoopenid"),
    /**
     * 拉取订单评价数据
     */
    BATCH_QUERY_COMMENT("/billcommentsp/batchquerycomment"),
    /**
     * 企业付款
     */
    TRANSFER("/mmpaymkttransfers/promotion/transfers"),
    /**
     * 查询企业付款
     */
    GET_TRANSFER_INFO("/mmpaymkttransfers/gettransferinfo"),
    /**
     * 企业付款到银行卡
     */
    TRANSFER_BANK("/mmpaysptrans/pay_bank"),
    /**
     * 查询企业付款到银行卡
     */
    GET_TRANSFER_BANK_INFO("/mmpaysptrans/query_bank"),
    /**
     * 获取 RSA 加密公钥
     */
    GET_PUBLIC_KEY("/risk/getpublickey"),
    /**
     * 发放红包
     */
    SEND_RED_PACK("/mmpaymkttransfers/sendredpack"),
    /**
     * 发放裂变红包
     */
    SEND_GROUP_RED_PACK("/mmpaymkttransfers/sendgroupredpack"),
    /**
     * 查询红包记录
     */
    GET_HB_INFO("/mmpaymkttransfers/gethbinfo"),
    /**
     * 小程序发红包
     */
    SEND_MINI_PROGRAM_HB("/mmpaymkttransfers/sendminiprogramhb"),
    /**
     * 发放代金券
     */
    SEND_COUPON("/mmpaymkttransfers/send_coupon"),
    /**
     * 查询代金券批次
     */
    QUERY_COUPON_STOCK("/mmpaymkttransfers/query_coupon_stock"),
    /**
     * 查询代金券信息
     */
    QUERY_COUPONS_INFO("/mmpaymkttransfers/querycouponsinfo"),
    /**
     * 请求单次分账
     */
    PROFIT_SHARING("/secapi/pay/profitsharing"),
    /**
     * 请求多次分账
     */
    MULTI_PROFIT_SHARING("/secapi/pay/multiprofitsharing"),
    /**
     * 查询分账结果
     */
    PROFIT_SHARING_QUERY("/pay/profitsharingquery"),
    /**
     * 添加分账接收方
     */
    PROFITS_HARING_ADD_RECEIVER("/pay/profitsharingaddreceiver"),
    /**
     * 删除分账接收方
     */
    PROFIT_SHARING_REMOVE_RECEIVER("/pay/profitsharingremovereceiver"),
    /**
     * 完结分账
     */
    PROFIT_SHARING_FINISH("/secapi/pay/profitsharingfinish"),
    /**
     * 分账回退
     */
    PROFIT_SHARING_RETURN("/secapi/pay/profitsharingreturn"),
    /**
     * 分账回退结果查询
     */
    PROFIT_SHARING_RETURN_QUERY("/pay/profitsharingreturnquery"),
    /**
     * 支付押金（人脸支付）
     */
    DEPOSIT_FACE_PAY("/deposit/facepay"),
    /**
     * 支付押金（付款码支付）
     */
    DEPOSIT_MICRO_PAY("/deposit/micropay"),
    /**
     * 查询订单（押金）
     */
    DEPOSIT_ORDER_QUERY("/deposit/orderquery"),
    /**
     * 撤销订单（押金）
     */
    DEPOSIT_REVERSE("/deposit/reverse"),
    /**
     * 消费押金
     */
    DEPOSIT_CONSUME("/deposit/consume"),
    /**
     * 申请退款（押金）
     */
    DEPOSIT_REFUND("/deposit/refund"),
    /**
     * 查询退款（押金）
     */
    DEPOSIT_REFUND_QUERY("deposit/refundquery"),
    /**
     * 公众号纯签约
     */
    ENTRUST_WEB("/papay/entrustweb"),
    /**
     * 公众号纯签约（服务商模式）
     */
    PARTNER_ENTRUST_WEB("/papay/partner/entrustweb"),
    /**
     * APP纯签约
     */
    PRE_ENTRUST_WEB("/papay/preentrustweb"),
    /**
     * APP纯签约（服务商模式）
     */
    PARTNER_PRE_ENTRUST_WEB("/papay/partner/preentrustweb"),
    /**
     * H5纯签约
     */
    H5_ENTRUST_WEB("/papay/h5entrustweb"),
    /**
     * H5纯签约（服务商模式）
     */
    PARTNER_H5_ENTRUST_WEB("/papay/partner/h5entrustweb"),
    /**
     * 支付中签约
     */
    PAY_CONTRACT_ORDER("/pay/contractorder"),
    /**
     * 查询签约关系
     */
    QUERY_ENTRUST_CONTRACT("/papay/querycontract"),
    /**
     * 查询签约关系（服务商模式）
     */
    PARTNER_QUERY_ENTRUST_CONTRACT("/papay/partner/querycontract"),
    /**
     * 代扣申请扣款
     */
    PAP_PAY_APPLY("/pay/pappayapply"),
    /**
     * 代扣申请扣款（服务商模式）
     */
    PARTNER_PAP_PAY_APPLY("/pay/partner/pappayapply"),
    /**
     * 查询代扣订单
     */
    PAP_ORDER_QUERY("/pay/paporderquery"),
    /**
     * 查询代扣订单
     */
    PARTNER_PAP_ORDER_QUERY("/pay/partner/paporderquery"),
    /**
     * 代扣申请解约
     */
    DELETE_ENTRUST_CONTRACT("/papay/deletecontract"),
    /**
     * 代扣申请解约（服务商模式）
     */
    PARTNER_DELETE_ENTRUST_CONTRACT("/papay/partner/deletecontract"),
    /**
     * 刷脸支付
     */
    FACE_PAY("/pay/facepay"),
    /**
     * 查询刷脸支付订单
     */
    FACE_PAY_QUERY("/pay/facepayqueryy"),
    /**
     * 撤销刷脸支付订单
     */
    FACE_PAY_REVERSE("/secapi/pay/facepayreverse"),
    /**
     * 小微商户申请入驻
     */
    MICRO_SUBMIT("/applyment/micro/submit"),
    /**
     * 查询申请状态
     */
    GET_MICRO_SUBMIT_STATE("/applyment/micro/getstate"),
    /**
     * 提交升级申请
     */
    MICRO_SUBMIT_UPGRADE("/applyment/micro/submitupgrade"),
    /**
     * 查询升级申请单状态
     */
    GET_MICRO_UPGRADE_STATE("/applyment/micro/getupgradestate"),
    /**
     * 查询提现状态
     */
    QUERY_AUTO_WITH_DRAW_BY_DATE("/fund/queryautowithdrawbydate"),
    /**
     * 修改结算银行卡
     */
    MICRO_MODIFY_ARCHIVES("/applyment/micro/modifyarchives"),
    /**
     * 重新发起提现
     */
    RE_AUTO_WITH_DRAW_BY_DATE("/fund/reautowithdrawbydate"),
    /**
     * 修改联系信息
     */
    MICRO_MODIFY_CONTACT_INFO("/applyment/micro/modifycontactinfo"),
    /**
     * 小微商户关注功能配置
     */
    ADD_RECOMMEND_CONF("/secapi/mkt/addrecommendconf"),
    /**
     * 小微商户开发配置新增支付目录
     */
    ADD_SUB_DEV_CONFIG("/secapi/mch/addsubdevconfig"),
    /**
     * 小微商户开发配置查询
     */
    QUERY_SUB_DEV_CONFIG("/secapi/mch/querysubdevconfig"),
    /**
     * 小微商户图片上传
     */
    MCH_UPLOAD_MEDIA("/secapi/mch/uploadmedia"),
    /**
     * 获取平台证书列表
     */
    GET_CERTIFICATES("/v3/certificates"),

    /**
     * 营销专用-图片上传
     */
    MARKETING_UPLOAD_MEDIA("/v3/marketing/favor/media/image-upload"),
    /**
     * 通用接口-图片上传
     */
    MERCHANT_UPLOAD_MEDIA("/v3/merchant/media/upload"),
    /**
     * 通用接口-视频上传
     */
    MERCHANT_VIDEO_UPLOAD("/v3/merchant/media/video_upload"),

    /**
     * 创建/查询支付分订单
     */
    PAY_SCORE_SERVICE_ORDER("/v3/payscore/serviceorder"),
    /**
     * 取消支付分订单
     */
    PAY_SCORE_SERVICE_ORDER_CANCEL("/v3/payscore/serviceorder/%s/cancel"),
    /**
     * 修改支付分订单金额
     */
    PAY_SCORE_SERVICE_ORDER_MODIFY("/v3/payscore/serviceorder/%s/modify"),
    /**
     * 完结支付分订单
     */
    PAY_SCORE_SERVICE_ORDER_COMPLETE("/v3/payscore/serviceorder/%s/complete"),
    /**
     * 支付分订单收款
     */
    PAY_SCORE_SERVICE_ORDER_PAY("/v3/payscore/serviceorder/%s/pay"),
    /**
     * 同步服务订单信息
     */
    PAY_SCORE_SERVICE_ORDER_SYNC("/v3/payscore/payafter-orders/%s/sync"),
    /**
     * 查询用户支付分开启状态
     */
    PAY_SCORE_USER_SERVICE_STATE("/v3/payscore/user-service-state"),

    /**
     * 特约商户进件-提交申请单
     */
    APPLY_4_SUB("/v3/applyment4sub/applyment/"),
    /**
     * 特约商户进件-查询申请单状态
     */
    GET_APPLY_STATE("/v3/applyment4sub/applyment/business_code/%s"),
    /**
     * 特约商户进件-修改结算帐号
     */
    MODIFY_SETTLEMENT("/v3/apply4sub/sub_merchants/%s/modify-settlement"),
    /**
     * 特约商户进件-查询结算账户
     */
    GET_SETTLEMENT("/v3/apply4sub/sub_merchants/%s/settlement"),

    /**
     * 商户开户意愿确认-提交申请单 OR 查询申请单审核结果
     */
    MER_OPEN_APPLY_SUBMIT_OR_RESULT("/v3/apply4subject/applyment"),
    /**
     * 商户开户意愿确认-撤销申请单
     */
    MER_OPEN_APPLY_CANCEL("/v3/apply4subject/applyment/%s/cancel"),
    /**
     * 商户开户意愿确认-获取商户开户意愿确认状态
     */
    GET_MER_OPEN_APPLY_STATE("/v3/apply4subject/applyment/merchants/%s/state"),

    /**
     * 商业支付投诉-查询投诉信息
     */
    MERCHANT_SERVICE_COMPLAINTS("/v3/merchant-service/complaints"),
    /**
     * 商业支付投诉-创建/查询/更新/删除投诉通知回调
     */
    MERCHANT_SERVICE_COMPLAINTS_NOTIFICATIONS("/v3/merchant-service/complaint-notifications"),


    /**
     * 代金券-创建代金券批次
     */
    CREATE_COUPON_STOCKS("/v3/marketing/favor/coupon-stocks"),
    /**
     * 代金券-激活代金券批次
     */
    START_COUPON_STOCKS("/v3/marketing/favor/stocks/%s/start"),
    /**
     * 代金券-发放代金券
     */
    COUPON_SEND("/v3/marketing/favor/users/%s/coupons"),
    /**
     * 代金券-暂停代金券批次
     */
    PAUSE_COUPON_STOCKS("/v3/marketing/favor/stocks/%s/pause"),
    /**
     * 代金券-重启代金券批次
     */
    RESTART_COUPON_STOCKS("/v3/marketing/favor/stocks/%s/restart"),
    /**
     * 代金券-条件查询批次列表
     */
    QUERY_COUPON_STOCKS("/v3/marketing/favor/stocks"),
    /**
     * 代金券-查询批次详情
     */
    QUERY_COUPON_STOCKS_INFO("/v3/marketing/favor/stocks/%s"),
    /**
     * 代金券-查询代金券详情
     */
    QUERY_COUPON_INFO("/v3/marketing/favor/users/%s/coupons/%s"),
    /**
     * 代金券-查询代金券可用商户
     */
    QUERY_COUPON_MERCHANTS("/v3/marketing/favor/stocks/%s/merchants"),
    /**
     * 代金券-查询代金券可用单品
     */
    QUERY_COUPON_ITEMS("/v3/marketing/favor/stocks/%s/items"),
    /**
     * 代金券-根据商户号查用户的券
     */
    QUERY_USER_COUPON("/v3/marketing/favor/users/%s/coupons"),
    /**
     * 代金券-下载批次核销明细
     */
    COUPON_STOCKS_USER_FLOW_DOWNLOAD("/v3/marketing/favor/stocks/%s/use-flow"),
    /**
     * 代金券-下载批次退款明细
     */
    COUPON_STOCKS_REFUND_FLOW_DOWNLOAD("/v3/marketing/favor/stocks/%s/refund-flow"),
    /**
     * 代金券-设置消息通知地址
     */
    SETTING_COUPON_CALLBACKS("/v3/marketing/favor/callbacks"),
    /**
     * 商家券-创建商家券
     */
    CREATE_BUSINESS_COUPON("/v3/marketing/busifavor/stocks"),
    /**
     * 商家券-查询商家券批次详情
     */
    QUERY_BUSINESS_COUPON_STOCKS_INFO("/v3/marketing/busifavor/stocks/%s"),
    /**
     * 商家券-查询商家券批次详情
     */
    USE_BUSINESS_COUPON("/v3/marketing/busifavor/coupons/use"),
    /**
     * 商家券-根据过滤条件查询用户券
     */
    QUERY_BUSINESS_USER_COUPON("/v3/marketing/busifavor/users/%s/coupons"),
    /**
     * 商家券-查询用户单张券详情
     */
    QUERY_BUSINESS_USER_COUPON_INFO("/v3/marketing/busifavor/users/%s/coupons/%s/appids/%s"),
    /**
     * 商家券-上传预存code
     */
    BUSINESS_COUPON_UPLOAD_CODE("/v3/marketing/busifavor/stocks/%/couponcodes"),
    /**
     * 商家券-设置/查询商家券事件通知地址
     */
    BUSINESS_COUPON_CALLBACKS("/v3/marketing/busifavor/callbacks"),

    /**
     * 支付有礼-创建全场满额送活动
     */
    PAY_GIFT_ACTIVITY("/v3/marketing/paygiftactivity/unique-threshold-activity"),
    /**
     * 支付有礼-查询活动详情接口
     */
    PAY_GIFT_ACTIVITY_INFO("/v3/marketing/paygiftactivity/activities/%s"),
    /**
     * 支付有礼-查询活动发券商户号
     */
    PAY_GIFT_ACTIVITY_QUERY_MER("/v3/marketing/paygiftactivity/activities/%s/merchants"),
    /**
     * 支付有礼-查询活动指定商品列表
     */
    PAY_GIFT_ACTIVITY_QUERY_GOODS("/v3/marketing/paygiftactivity/activities/%s/goods"),
    /**
     * 支付有礼-终止活动
     */
    PAY_GIFT_ACTIVITY_TERMINATE("/v3/marketing/paygiftactivity/activities/%s/terminate"),

    /**
     * 点金计划-特约商户管理
     */
    CHANGE_GOLD_PLAN_STATUS("/v3/goldplan/merchants/changegoldplanstatus"),


    /**
     * 电商收付通-二级商户进件
     */
    E_COMMERCE_APPLY("/v3/ecommerce/applyments"),
    /**
     * 电商收付通-查询进件申请状态
     */
    E_COMMERCE_APPLY_STATE("/v3/ecommerce/applyments/s%"),
    /**
     * 电商收付通-通过业务申请编号查询申请状态
     */
    E_COMMERCE_APPLY_STATE_BY_NO("/v3/ecommerce/applyments/out-request-no/s%"),

    /**
     * 合单下单-APP支付
     */
    COMBINE_TRANSACTIONS_APP("/v3/combine-transactions/app"),
    /**
     * 合单下单-JS支付
     */
    COMBINE_TRANSACTIONS_JS("/v3/combine-transactions/jsapi"),
    /**
     * 合单下单-合单查询订单
     */
    COMBINE_TRANSACTIONS_QUERY("/v3/combine-transactions/out-trade-no/s%"),
    /**
     * 合单下单-合单关闭订单
     */
    COMBINE_TRANSACTIONS_CLOSE("/v3/combine-transactions/out-trade-no/s%/close"),

    /**
     * 电商收付通-补差接口-请求补差
     */
    CREATE_SUBSIDIES("v3/ecommerce/subsidies/create"),
    /**
     * 电商收付通-补差接口-请求补差回退
     */
    RETURN_SUBSIDIES("/v3/ecommerce/subsidies/return"),
    /**
     * 电商收付通-补差接口-取消补差
     */
    CANCEL_SUBSIDIES("/v3/ecommerce/subsidies/cancel"),
    /**
     * 电商收付通-分账接口-请求分账/查询分账结果
     */
    PROFIT_SHARING_ORDERS("/v3/ecommerce/profitsharing/orders"),
    /**
     * 电商收付通-分账接口-查询分账回退结果
     */
    PROFIT_SHARING_RETURN_ORDERS("/v3/ecommerce/profitsharing/returnorders"),
    /**
     * 电商收付通-分账接口-完结分账
     */
    PROFIT_SHARING_FINISH_ORDER("/v3/ecommerce/profitsharing/finish-order"),
    /**
     * 电商收付通-退款接口-退款申请
     */
    E_COMMERCE_REFUNDS("/v3/ecommerce/refunds/apply"),
    /**
     * 电商收付通-退款接口-通过微信支付退款单号查询退款
     */
    QUERY_REFUND("/v3/ecommerce/refunds/id/s%"),
    /**
     * 电商收付通-退款接口-通过商户退款单号查询退款
     */
    QUERY_REFUNDS_BY_REFUND_NO("/v3/ecommerce/refunds/out-refund-no/s%"),
    /**
     * 电商收付通-余额查询接口
     */
    QUERY_BALANCE("/v3/ecommerce/fund/balance/s%"),
    /**
     * 电商收付通-提现接口-账户余额提现
     */
    WITHDRAW("/v3/ecommerce/fund/withdraw"),
    /**
     * 电商收付通-提现接口-提现状态查询
     */
    QUERY_WITHDRAW("/v3/ecommerce/fund/withdraw/s%"),
    /**
     * 电商收付通-提现接口-按日下载提现异常文件
     */
    WITHDRAW_BILL("/v3/merchant/fund/withdraw/bill-type/s%"),
    /**
     * 电商收付通-账单接口-申请交易账单
     */
    TRADE_BILL("/v3/bill/tradebill"),
    /**
     * 电商收付通-账单接口-申请资金账单
     */
    FUND_FLOW_BILL("/v3/bill/fundflowbill"),

    /**
     * 银行特约商户违规信息查询
     */
    GET_VIOLATION("/risk/getviolation"),
    /**
     * 事前-风险商户核查接口
     */
    QUERY_MCH_RISK("/mchrisk/querymchrisk"),
    /**
     * 事后-风险商户处理结果同步接口
     */
    SYNC_MCH_RISK_RESULT("/mchrisk/syncmchriskresult"),
    /**
     * 间联模式查询商户审核状态开放接口
     */
    BANK_QUERY_MCH_AUDIT_INFO("/mchrisk/bankquerymchauditinfo"),
    /**
     * 渠道商查询商户审核信息
     */
    CHANNEL_QUERY_MCH_AUDIT_INFO("/mchrisk/channelquerymchauditinfo"),
    /**
     * 设置风险通知回调链接
     */
    SET_MCH_RISK_CALLBACK("/mchrisk/setmchriskcallback"),

    /**
     * 创建先享后付订单 OR 查询先享后付订单
     */
    PAY_AFTER_ORDERS("/v3/payscore/payafter-orders"),
    /**
     * 撤销先享后付订单
     */
    CANCEL_PAY_AFTER_ORDERS("/v3/payscore/payafter-orders/out_order_no/cancel"),
    /**
     * 完结先享后付订单
     */
    COMPLETE_PAY_AFTER_ORDERS("/v3/payscore/payafter-orders/out_order_no/complete"),
    CANCEL1_PAY_AFTER_ORDERS("/v3/payscore/payafter-orders/out_order_no/cancel");


    /**
     * 类型
     */
    private final String type;

    WxApiType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
