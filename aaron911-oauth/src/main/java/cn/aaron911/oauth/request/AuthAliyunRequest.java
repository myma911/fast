package cn.aaron911.oauth.request;

import com.alibaba.fastjson.JSONObject;

import cn.aaron911.oauth.cache.AuthStateCache;
import cn.aaron911.oauth.config.AuthConfig;
import cn.aaron911.oauth.config.AuthDefaultSource;
import cn.aaron911.oauth.enums.AuthUserGender;
import cn.aaron911.oauth.model.AuthCallback;
import cn.aaron911.oauth.model.AuthToken;
import cn.aaron911.oauth.model.AuthUser;

/**
 * 阿里云登录
 *
 */
public class AuthAliyunRequest extends AuthDefaultRequest {

    public AuthAliyunRequest(AuthConfig config) {
        super(config, AuthDefaultSource.ALIYUN);
    }

    public AuthAliyunRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.ALIYUN, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        return AuthToken.builder()
                .accessToken(accessTokenObject.getString("access_token"))
                .expireIn(accessTokenObject.getIntValue("expires_in"))
                .tokenType(accessTokenObject.getString("token_type"))
                .idToken(accessTokenObject.getString("id_token"))
                .refreshToken(accessTokenObject.getString("refresh_token"))
                .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String userInfo = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(userInfo);
        return AuthUser.builder()
            .rawUserInfo(object)
            .uuid(object.getString("sub"))
            .username(object.getString("login_name"))
            .nickname(object.getString("name"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

}
