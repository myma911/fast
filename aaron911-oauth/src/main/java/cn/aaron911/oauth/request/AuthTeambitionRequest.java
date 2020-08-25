package cn.aaron911.oauth.request;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xkcoding.http.support.HttpHeader;

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

/**
 * Teambition授权登录
 *
 */
public class AuthTeambitionRequest extends AuthDefaultRequest {

    public AuthTeambitionRequest(AuthConfig config) {
        super(config, AuthDefaultSource.TEAMBITION);
    }

    public AuthTeambitionRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.TEAMBITION, authStateCache);
    }

    /**
     * @param authCallback 回调返回的参数
     * @return 所有信息
     */
    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        Map<String, String> form = new HashMap<>(4);
        form.put("client_id", config.getClientId());
        form.put("client_secret", config.getClientSecret());
        form.put("code", authCallback.getCode());
        form.put("grant_type", "code");

        String response = new HttpUtils(config.getHttpConfig()).post(source.accessToken(), form, false);
        JSONObject accessTokenObject = JSONObject.parseObject(response);

        this.checkResponse(accessTokenObject);

        return AuthToken.builder()
            .accessToken(accessTokenObject.getString("access_token"))
            .refreshToken(accessTokenObject.getString("refresh_token"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String accessToken = authToken.getAccessToken();

        HttpHeader httpHeader = new HttpHeader();
        httpHeader.add("Authorization", "OAuth2 " + accessToken);

        String response = new HttpUtils(config.getHttpConfig()).get(source.userInfo(), null, httpHeader, false);
        JSONObject object = JSONObject.parseObject(response);

        this.checkResponse(object);

        authToken.setUid(object.getString("_id"));

        return AuthUser.builder()
            .rawUserInfo(object)
            .uuid(object.getString("_id"))
            .username(object.getString("name"))
            .nickname(object.getString("name"))
            .avatar(object.getString("avatarUrl"))
            .blog(object.getString("website"))
            .location(object.getString("location"))
            .email(object.getString("email"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    @Override
    public AuthResponse refresh(AuthToken oldToken) {
        String uid = oldToken.getUid();
        String refreshToken = oldToken.getRefreshToken();

        Map<String, String> form = new HashMap<>(2);
        form.put("_userId", uid);
        form.put("refresh_token", refreshToken);
        String response = new HttpUtils(config.getHttpConfig()).post(source.refresh(), form, false);
        JSONObject refreshTokenObject = JSONObject.parseObject(response);

        this.checkResponse(refreshTokenObject);

        return AuthResponse.builder()
            .code(AuthResponseStatus.SUCCESS.getCode())
            .data(AuthToken.builder()
                .accessToken(refreshTokenObject.getString("access_token"))
                .refreshToken(refreshTokenObject.getString("refresh_token"))
                .build())
            .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if ((object.containsKey("message") && object.containsKey("name"))) {
            throw new AuthException(object.getString("name") + ", " + object.getString("message"));
        }
    }
}
