package cn.aaron911.oauth.request;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.aaron911.oauth.cache.AuthStateCache;
import cn.aaron911.oauth.config.AuthConfig;
import cn.aaron911.oauth.config.AuthDefaultSource;
import cn.aaron911.oauth.enums.AuthResponseStatus;
import cn.aaron911.oauth.enums.AuthUserGender;
import cn.aaron911.oauth.exception.AuthException;
import cn.aaron911.oauth.model.AuthCallback;
import cn.aaron911.oauth.model.AuthResponse;
import cn.aaron911.oauth.model.AuthToken;
import cn.aaron911.oauth.model.AuthUser;
import cn.aaron911.oauth.utils.HttpUtils;
import cn.aaron911.oauth.utils.UrlBuilder;

/**
 * 华为授权登录
 *
 */
public class AuthHuaweiRequest extends AuthDefaultRequest {

    public AuthHuaweiRequest(AuthConfig config) {
        super(config, AuthDefaultSource.HUAWEI);
    }

    public AuthHuaweiRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.HUAWEI, authStateCache);
    }

    /**
     * 获取access token
     *
     * @param authCallback 授权成功后的回调参数
     * @return token
     * @see AuthDefaultRequest#authorize()
     * @see AuthDefaultRequest#authorize(String)
     */
    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        Map<String, String> form = new HashMap<>(5);
        form.put("grant_type", "authorization_code");
        form.put("code", authCallback.getAuthorization_code());
        form.put("client_id", config.getClientId());
        form.put("client_secret", config.getClientSecret());
        form.put("redirect_uri", config.getRedirectUri());

        String response = new HttpUtils(config.getHttpConfig()).post(source.accessToken(), form, false);
        return getAuthToken(response);
    }

    /**
     * 使用token换取用户信息
     *
     * @param authToken token信息
     * @return 用户信息
     * @see AuthDefaultRequest#getAccessToken(AuthCallback)
     */
    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        Map<String, String> form = new HashMap<>(4);
        form.put("nsp_ts", System.currentTimeMillis() + "");
        form.put("access_token", authToken.getAccessToken());
        form.put("nsp_fmt", "JS");
        form.put("nsp_svc", "OpenUP.User.getInfo");

        String response = new HttpUtils(config.getHttpConfig()).post(source.userInfo(), form, false);
        JSONObject object = JSONObject.parseObject(response);

        this.checkResponse(object);

        AuthUserGender gender = getRealGender(object);

        return AuthUser.builder()
            .rawUserInfo(object)
            .uuid(object.getString("userID"))
            .username(object.getString("userName"))
            .nickname(object.getString("userName"))
            .gender(gender)
            .avatar(object.getString("headPictureURL"))
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    @Override
    public AuthResponse refresh(AuthToken authToken) {
        Map<String, String> form = new HashMap<>(4);
        form.put("client_id", config.getClientId());
        form.put("client_secret", config.getClientSecret());
        form.put("refresh_token", authToken.getRefreshToken());
        form.put("grant_type", "refresh_token");

        String response = new HttpUtils(config.getHttpConfig()).post(source.refresh(), form, false);
        return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).data(getAuthToken(response)).build();
    }

    private AuthToken getAuthToken(String response) {
        JSONObject object = JSONObject.parseObject(response);

        this.checkResponse(object);

        return AuthToken.builder()
            .accessToken(object.getString("access_token"))
            .expireIn(object.getIntValue("expires_in"))
            .refreshToken(object.getString("refresh_token"))
            .build();
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     * @since 1.9.3
     */
    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("response_type", "code")
            .queryParam("client_id", config.getClientId())
            .queryParam("redirect_uri", config.getRedirectUri())
            .queryParam("access_type", "offline")
            .queryParam("scope", "https%3A%2F%2Fwww.huawei.com%2Fauth%2Faccount%2Fbase.profile")
            .queryParam("state", getRealState(state))
            .build();
    }

    /**
     * 返回获取accessToken的url
     *
     * @param code 授权码
     * @return 返回获取accessToken的url
     */
    @Override
    protected String accessTokenUrl(String code) {
        return UrlBuilder.fromBaseUrl(source.accessToken())
            .queryParam("grant_type", "authorization_code")
            .queryParam("code", code)
            .queryParam("client_id", config.getClientId())
            .queryParam("client_secret", config.getClientSecret())
            .queryParam("redirect_uri", config.getRedirectUri())
            .build();
    }

    /**
     * 返回获取userInfo的url
     *
     * @param authToken token
     * @return 返回获取userInfo的url
     */
    @Override
    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.userInfo())
            .queryParam("nsp_ts", System.currentTimeMillis())
            .queryParam("access_token", authToken.getAccessToken())
            .queryParam("nsp_fmt", "JS")
            .queryParam("nsp_svc", "OpenUP.User.getInfo")
            .build();
    }

    /**
     * 获取用户的实际性别。华为系统中，用户的性别：1表示女，0表示男
     *
     * @param object obj
     * @return AuthUserGender
     */
    private AuthUserGender getRealGender(JSONObject object) {
        int genderCodeInt = object.getIntValue("gender");
        String genderCode = genderCodeInt == 1 ? "0" : (genderCodeInt == 0) ? "1" : genderCodeInt + "";
        return AuthUserGender.getRealGender(genderCode);
    }

    /**
     * 校验响应结果
     *
     * @param object 接口返回的结果
     */
    private void checkResponse(JSONObject object) {
        if (object.containsKey("NSP_STATUS")) {
            throw new AuthException(object.getString("error"));
        }
        if (object.containsKey("error")) {
            throw new AuthException(object.getString("sub_error") + ":" + object.getString("error_description"));
        }
    }
}
