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
 * 美团登录
 *
 */
public class AuthMeituanRequest extends AuthDefaultRequest {

    public AuthMeituanRequest(AuthConfig config) {
        super(config, AuthDefaultSource.MEITUAN);
    }

    public AuthMeituanRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.MEITUAN, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        Map<String, String> form = new HashMap<>(4);
        form.put("app_id", config.getClientId());
        form.put("secret", config.getClientSecret());
        form.put("code", authCallback.getCode());
        form.put("grant_type", "authorization_code");

        String response = new HttpUtils(config.getHttpConfig()).post(source.accessToken(), form, false);
        JSONObject object = JSONObject.parseObject(response);

        this.checkResponse(object);

        return AuthToken.builder()
            .accessToken(object.getString("access_token"))
            .refreshToken(object.getString("refresh_token"))
            .expireIn(object.getIntValue("expires_in"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        Map<String, String> form = new HashMap<>(3);
        form.put("app_id", config.getClientId());
        form.put("secret", config.getClientSecret());
        form.put("access_token", authToken.getAccessToken());

        String response = new HttpUtils(config.getHttpConfig()).post(source.userInfo(), form, false);
        JSONObject object = JSONObject.parseObject(response);

        this.checkResponse(object);

        return AuthUser.builder()
            .rawUserInfo(object)
            .uuid(object.getString("openid"))
            .username(object.getString("nickname"))
            .nickname(object.getString("nickname"))
            .avatar(object.getString("avatar"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    @Override
    public AuthResponse refresh(AuthToken oldToken) {
        Map<String, String> form = new HashMap<>(4);
        form.put("app_id", config.getClientId());
        form.put("secret", config.getClientSecret());
        form.put("refresh_token", oldToken.getRefreshToken());
        form.put("grant_type", "refresh_token");

        String response = new HttpUtils(config.getHttpConfig()).post(source.refresh(), form, false);
        JSONObject object = JSONObject.parseObject(response);

        this.checkResponse(object);

        return AuthResponse.builder()
            .code(AuthResponseStatus.SUCCESS.getCode())
            .data(AuthToken.builder()
                .accessToken(object.getString("access_token"))
                .refreshToken(object.getString("refresh_token"))
                .expireIn(object.getIntValue("expires_in"))
                .build())
            .build();
    }

    private void checkResponse(JSONObject object) {
        if (object.containsKey("error_code")) {
            throw new AuthException(object.getString("erroe_msg"));
        }
    }

    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("response_type", "code")
            .queryParam("app_id", config.getClientId())
            .queryParam("redirect_uri", config.getRedirectUri())
            .queryParam("state", getRealState(state))
            .queryParam("scope", "")
            .build();
    }

}
