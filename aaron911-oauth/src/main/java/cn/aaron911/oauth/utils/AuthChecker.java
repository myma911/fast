package cn.aaron911.oauth.utils;

import cn.aaron911.oauth.cache.AuthStateCache;
import cn.aaron911.oauth.config.AuthConfig;
import cn.aaron911.oauth.config.AuthDefaultSource;
import cn.aaron911.oauth.config.AuthSource;
import cn.aaron911.oauth.enums.AuthResponseStatus;
import cn.aaron911.oauth.exception.AuthException;
import cn.aaron911.oauth.model.AuthCallback;
import cn.hutool.core.util.StrUtil;

/**
 * 授权配置类的校验器
 *
 */
public class AuthChecker {

    /**
     * 是否支持第三方登录
     *
     * @param config config
     * @param source source
     * @return true or false
     * @since 1.6.1-beta
     */
    public static boolean isSupportedAuth(AuthConfig config, AuthSource source) {
        boolean isSupported = StrUtil.isNotEmpty(config.getClientId()) && StrUtil.isNotEmpty(config.getClientSecret()) && StrUtil.isNotEmpty(config.getRedirectUri());
        if (isSupported && AuthDefaultSource.ALIPAY == source) {
            isSupported = StrUtil.isNotEmpty(config.getAlipayPublicKey());
        }
        if (isSupported && AuthDefaultSource.STACK_OVERFLOW == source) {
            isSupported = StrUtil.isNotEmpty(config.getStackOverflowKey());
        }
        if (isSupported && AuthDefaultSource.WECHAT_ENTERPRISE == source) {
            isSupported = StrUtil.isNotEmpty(config.getAgentId());
        }
        if (isSupported && AuthDefaultSource.CODING == source) {
            isSupported = StrUtil.isNotEmpty(config.getCodingGroupName());
        }
        return isSupported;
    }

    /**
     * 检查配置合法性。针对部分平台， 对redirect uri有特定要求。一般来说redirect uri都是http://，而对于facebook平台， redirect uri 必须是https的链接
     *
     * @param config config
     * @param source source
     * @since 1.6.1-beta
     */
    public static void checkConfig(AuthConfig config, AuthSource source) {
        String redirectUri = config.getRedirectUri();
        if (!GlobalAuthUtils.isHttpProtocol(redirectUri) && !GlobalAuthUtils.isHttpsProtocol(redirectUri)) {
            throw new AuthException(AuthResponseStatus.ILLEGAL_REDIRECT_URI, source);
        }
        // facebook的回调地址必须为https的链接
        if (AuthDefaultSource.FACEBOOK == source && !GlobalAuthUtils.isHttpsProtocol(redirectUri)) {
            // Facebook's redirect uri must use the HTTPS protocol
            throw new AuthException(AuthResponseStatus.ILLEGAL_REDIRECT_URI, source);
        }
        // 支付宝在创建回调地址时，不允许使用localhost或者127.0.0.1
        if (AuthDefaultSource.ALIPAY == source && GlobalAuthUtils.isLocalHost(redirectUri)) {
            // The redirect uri of alipay is forbidden to use localhost or 127.0.0.1
            throw new AuthException(AuthResponseStatus.ILLEGAL_REDIRECT_URI, source);
        }
    }

    /**
     * 校验回调传回的code
     * <p>
     * {@code v1.10.0}版本中改为传入{@code source}和{@code callback}，对于不同平台使用不同参数接受code的情况统一做处理
     *
     * @param source   当前授权平台
     * @param callback 从第三方授权回调回来时传入的参数集合
     * @since 1.8.0
     */
    public static void checkCode(AuthSource source, AuthCallback callback) {
        // 推特平台不支持回调 code 和 state
        if (source == AuthDefaultSource.TWITTER) {
            return;
        }
        String code = callback.getCode();
        if (source == AuthDefaultSource.ALIPAY) {
            code = callback.getAuth_code();
        } else if (source == AuthDefaultSource.HUAWEI) {
            code = callback.getAuthorization_code();
        }
        if (StrUtil.isEmpty(code)) {
            throw new AuthException(AuthResponseStatus.ILLEGAL_CODE, source);
        }
    }

    /**
     * 校验回调传回的{@code state}，为空或者不存在
     * <p>
     * {@code state}不存在的情况只有两种：
     * 1. {@code state}已使用，被正常清除
     * 2. {@code state}为前端伪造，本身就不存在
     *
     * @param state          {@code state}一定不为空
     * @param source         {@code source}当前授权平台
     * @param authStateCache {@code authStateCache} state缓存实现
     */
    public static void checkState(String state, AuthSource source, AuthStateCache authStateCache) {
        // 推特平台不支持回调 code 和 state
        if (source == AuthDefaultSource.TWITTER) {
            return;
        }
        if (StrUtil.isEmpty(state) || !authStateCache.containsKey(state)) {
            throw new AuthException(AuthResponseStatus.ILLEGAL_STATUS, source);
        }
    }
}
