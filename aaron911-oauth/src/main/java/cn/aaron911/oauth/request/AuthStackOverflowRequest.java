package cn.aaron911.oauth.request;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xkcoding.http.constants.Constants;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.util.MapUtil;

import cn.aaron911.oauth.cache.AuthStateCache;
import cn.aaron911.oauth.config.AuthConfig;
import cn.aaron911.oauth.config.AuthDefaultSource;
import cn.aaron911.oauth.enums.AuthUserGender;
import cn.aaron911.oauth.exception.AuthException;
import cn.aaron911.oauth.model.AuthCallback;
import cn.aaron911.oauth.model.AuthToken;
import cn.aaron911.oauth.model.AuthUser;
import cn.aaron911.oauth.utils.HttpUtils;
import cn.aaron911.oauth.utils.UrlBuilder;

/**
 * Stack Overflow登录
 *
 */
public class AuthStackOverflowRequest extends AuthDefaultRequest {

    public AuthStackOverflowRequest(AuthConfig config) {
        super(config, AuthDefaultSource.STACK_OVERFLOW);
    }

    public AuthStackOverflowRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.STACK_OVERFLOW, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String accessTokenUrl = accessTokenUrl(authCallback.getCode());
        Map<String, String> form = MapUtil.parseStringToMap(accessTokenUrl, false);
        HttpHeader httpHeader = new HttpHeader();
        httpHeader.add(Constants.CONTENT_TYPE, "application/x-www-form-urlencoded");
        String response = new HttpUtils(config.getHttpConfig()).post(accessTokenUrl, form, httpHeader, false);

        JSONObject accessTokenObject = JSONObject.parseObject(response);
        this.checkResponse(accessTokenObject);

        return AuthToken.builder()
            .accessToken(accessTokenObject.getString("access_token"))
            .expireIn(accessTokenObject.getIntValue("expires"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String userInfoUrl = UrlBuilder.fromBaseUrl(this.source.userInfo())
            .queryParam("access_token", authToken.getAccessToken())
            .queryParam("site", "stackoverflow")
            .queryParam("key", this.config.getStackOverflowKey())
            .build();
        String response = new HttpUtils(config.getHttpConfig()).get(userInfoUrl);
        JSONObject object = JSONObject.parseObject(response);
        this.checkResponse(object);
        JSONObject userObj = object.getJSONArray("items").getJSONObject(0);

        return AuthUser.builder()
            .rawUserInfo(userObj)
            .uuid(userObj.getString("user_id"))
            .avatar(userObj.getString("profile_image"))
            .location(userObj.getString("location"))
            .nickname(userObj.getString("display_name"))
            .blog(userObj.getString("website_url"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
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
            .queryParam("scope", "read_inbox")
            .queryParam("state", getRealState(state))
            .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if (object.containsKey("error")) {
            throw new AuthException(object.getString("error_description"));
        }
    }
}
