package cn.aaron911.pay.core.kit;

import cn.aaron911.pay.core.XmlHelper;
import cn.aaron911.pay.core.enums.RequestMethod;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.*;
import java.util.*;

/**
 *
 * <p>IJPay 工具类</p>
 *
 */
public class PayKit {

    public static String hmacSha256(String data, String key) {
        return SecureUtil.hmac(HmacAlgorithm.HmacSHA256, key).digestHex(data, CharsetUtil.UTF_8);
    }

    public static String md5(String data) {
        return SecureUtil.md5(data);
    }

    /**
     * AES 解密
     *
     * @param base64Data 需要解密的数据
     * @param key        密钥
     * @return 解密后的数据
     */
    public static String decryptData(String base64Data, String key) {
        return SecureUtil.aes(md5(key).toLowerCase().getBytes()).decryptStr(base64Data);
    }

    /**
     * AES 加密
     *
     * @param data 需要加密的数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static String encryptData(String data, String key) {
        return SecureUtil.aes(md5(key).toLowerCase().getBytes()).encryptBase64(data.getBytes());
    }

    public static String generateStr() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 把所有元素排序
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        return createLinkString(params, false);
    }

    /**
     * @param params 需要排序并参与字符拼接的参数组
     * @param encode 是否进行URLEncoder
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {
        return createLinkString(params, "&", encode);
    }

    /**
     * @param params  需要排序并参与字符拼接的参数组
     * @param connStr 连接符号
     * @param encode  是否进行URLEncoder
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, String connStr, boolean encode) {
        return createLinkString(params, connStr, encode, false);
    }

    public static String createLinkString(Map<String, String> params, String connStr, boolean encode, boolean quotes) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                if (quotes) {
                    content.append(key).append("=").append('"').append(encode ? urlEncode(value) : value).append('"');
                } else {
                    content.append(key).append("=").append(encode ? urlEncode(value) : value);
                }
            } else {
                if (quotes) {
                    content.append(key).append("=").append('"').append(encode ? urlEncode(value) : value).append('"').append(connStr);
                } else {
                    content.append(key).append("=").append(encode ? urlEncode(value) : value).append(connStr);
                }
            }
        }
        return content.toString();
    }


    /**
     * URL 编码
     *
     * @param src 需要编码的字符串
     * @return 编码后的字符串
     */
    public static String urlEncode(String src) {
        try {
            return URLEncoder.encode(src, CharsetUtil.UTF_8).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 遍历 Map 并构建 xml 数据
     *
     * @param params 需要遍历的 Map
     * @param prefix xml 前缀
     * @param suffix xml 后缀
     * @return
     */
    public static StringBuffer forEachMap(Map<String, String> params, String prefix, String suffix) {
        StringBuffer xml = new StringBuffer();
        if (StrUtil.isNotEmpty(prefix)) {
            xml.append(prefix);
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 略过空值
            if (StrUtil.isEmpty(value)) {
                continue;
            }
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        if (StrUtil.isNotEmpty(suffix)) {
            xml.append(suffix);
        }
        return xml;
    }

    /**
     * 微信下单 map to xml
     *
     * @param params Map 参数
     * @return xml 字符串
     */
    public static String toXml(Map<String, String> params) {
        StringBuffer xml = forEachMap(params, "<xml>", "</xml>");
        return xml.toString();
    }

    /**
     * 针对支付的 xml，没有嵌套节点的简单处理
     *
     * @param xmlStr xml 字符串
     * @return 转化后的 Map
     */
    public static Map<String, String> xmlToMap(String xmlStr) {
        XmlHelper xmlHelper = XmlHelper.of(xmlStr);
        return xmlHelper.toMap();
    }

    /**
     * 构造签名串
     *
     * @param method    {@link RequestMethod} GET,POST,PUT等
     * @param url       请求接口 /v3/certificates
     * @param timestamp 获取发起请求时的系统当前时间戳
     * @param nonceStr  随机字符串
     * @param body      请求报文主体
     * @return 待签名字符串
     */
    public static String buildSignMessage(RequestMethod method, String url, long timestamp, String nonceStr, String body) {
        return new StringBuffer()
                .append(method.toString())
                .append("\n")
                .append(url)
                .append("\n")
                .append(timestamp)
                .append("\n")
                .append(nonceStr)
                .append("\n")
                .append(body)
                .append("\n")
                .toString();
    }

    /**
     * 构造签名串
     *
     * @param timestamp 应答时间戳
     * @param nonceStr  应答随机串
     * @param body      应答报文主体
     * @return 应答待签名字符串
     */
    public static String buildSignMessage(String timestamp, String nonceStr, String body) {
        return new StringBuffer()
                .append(timestamp)
                .append("\n")
                .append(nonceStr)
                .append("\n")
                .append(body)
                .append("\n")
                .toString();
    }

    /**
     * 获取授权认证信息
     *
     * @param mchId     商户号
     * @param serialNo  商户API证书序列号
     * @param nonceStr  请求随机串
     * @param timestamp 时间戳
     * @param signature 签名值
     * @param authType  认证类型，目前为WECHATPAY2-SHA256-RSA2048
     * @return 请求头 Authorization
     */
    public static String getAuthorization(String mchId, String serialNo, String nonceStr, String timestamp, String signature, String authType) {
        Map<String, String> params = new HashMap<>(5);
        params.put("mchid", mchId);
        params.put("serial_no", serialNo);
        params.put("nonce_str", nonceStr);
        params.put("timestamp", timestamp);
        params.put("signature", signature);
        return authType.concat(" ").concat(createLinkString(params, ",", false, true));
    }

    /**
     * 获取商户私钥
     *
     * @param keyPath 商户私钥证书路径
     * @return {@link String} 商户私钥
     * @throws Exception 异常信息
     */
    public static String getPrivateKeyStr(String keyPath) throws Exception {
        return RsaKit.getPrivateKeyStr(getPrivateKey(keyPath));
    }

    /**
     * 获取商户私钥
     *
     * @param keyPath 商户私钥证书路径
     * @return {@link PrivateKey} 商户私钥
     * @throws Exception 异常信息
     */
    public static PrivateKey getPrivateKey(String keyPath) throws Exception {
        String originalKey = FileUtil.readUtf8String(keyPath);
        String privateKey = originalKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        return RsaKit.loadPrivateKey(privateKey);
    }

    /**
     * 获取证书
     *
     * @param inputStream 证书文件
     * @return {@link X509Certificate} 获取证书
     */
    public static X509Certificate getCertificate(InputStream inputStream) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inputStream);
            cert.checkValidity();
            return cert;
        } catch (CertificateExpiredException e) {
            throw new RuntimeException("证书已过期", e);
        } catch (CertificateNotYetValidException e) {
            throw new RuntimeException("证书尚未生效", e);
        } catch (CertificateException e) {
            throw new RuntimeException("无效的证书", e);
        }
    }

    /**
     * 公钥加密
     *
     * @param data        待加密数据
     * @param certificate 平台公钥证书
     * @return 加密后的数据
     * @throws Exception 异常信息
     */
    public static String rsaEncryptOAEP(String data, X509Certificate certificate) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());

            byte[] dataByte = data.getBytes(StandardCharsets.UTF_8);
            byte[] cipherData = cipher.doFinal(dataByte);
            return Base64.encode(cipherData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("无效的证书", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalBlockSizeException("加密原串的长度不能超过214字节");
        }
    }

    /**
     * 私钥解密
     *
     * @param cipherText 加密字符
     * @param privateKey 私钥
     * @return 解密后的数据
     * @throws Exception 异常信息
     */
    public static String rsaDecryptOAEP(String cipherText, PrivateKey privateKey) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] data = Base64.decode(cipherText);
            return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("无效的私钥", e);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new BadPaddingException("解密失败");
        }
    }
}