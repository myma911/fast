package cn.aaron911.pay.alipay;

import cn.aaron911.pay.alipay.enums.SignType;
import cn.hutool.crypto.SecureUtil;

import java.util.*;

/**
 *
 * <p>AliPayCore</p>
 *
 */
public class AliPayCore {

    /**
     * 生成签名结果
     *
     * @param params   要签名的数组
     * @param key      签名密钥
     * @param signType 签名类型
     * @return 签名结果字符串
     */
    public static String buildRequestMySign(Map<String, String> params, String key, String signType) {
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String preStr = createLinkString(params);
        if (SignType.MD5.getType().equals(signType)) {
            return SecureUtil.md5(preStr.concat(key));
        }
        return null;
    }

    /**
     * 生成要请求给支付宝的参数数组
     *
     * @param params   请求前的参数数组
     * @param key      商户的私钥
     * @param signType 签名类型
     * @return 要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> params, String key, String signType) {
        // 除去数组中的空值和签名参数
        Map<String, String> tempMap = paraFilter(params);
        // 生成签名结果
        String mySign = buildRequestMySign(params, key, signType);

        // 签名结果与签名方式加入请求提交参数组中
        tempMap.put("sign", mySign);
        tempMap.put("sign_type", signType);

        return tempMap;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>(sArray.size());
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || "".equals(value) || "sign".equalsIgnoreCase(key)
                    || "sign_type".equalsIgnoreCase(key)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把数组所有元素排序
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                content.append(key + "=" + value);
            } else {
                content.append(key + "=" + value + "&");
            }
        }
        return content.toString();
    }
}