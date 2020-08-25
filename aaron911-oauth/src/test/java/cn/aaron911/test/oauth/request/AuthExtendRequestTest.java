package cn.aaron911.test.oauth.request;

import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.aaron911.oauth.config.AuthConfig;
import cn.aaron911.oauth.model.AuthCallback;
import cn.aaron911.oauth.model.AuthResponse;
import cn.aaron911.oauth.model.AuthToken;
import cn.aaron911.oauth.model.AuthUser;
import cn.aaron911.oauth.request.AuthRequest;
import cn.hutool.core.util.IdUtil;

/**
 * 自定义扩展的第三方request的测试类，用于演示具体的用法
 *
 */
public class AuthExtendRequestTest {

    @Test
    public void authorize() {
        AuthRequest request = new AuthExtendRequest(AuthConfig.builder()
            .clientId("wx7205d04152318d29")
            .clientSecret("HXznRuihuokeji518918741256348952")
            .redirectUri("http://redirectUri")
            .build());
        String authorize = request.authorize(IdUtil.fastUUID());
        System.out.println(authorize);
        Assert.assertNotNull(authorize);
    }

    @Test
    public void login() {
        AuthRequest request = new AuthExtendRequest(AuthConfig.builder()
            .clientId("wx7205d04152318d29")
            .clientSecret("HXznRuihuokeji518918741256348952")
            .redirectUri("http://redirectUri")
            .build());

        String state = IdUtil.fastUUID();
        request.authorize(state);
        AuthCallback callback = AuthCallback.builder()
            .code("code")
            .state(state)
            .build();
        AuthResponse response = request.login(callback);
        Assert.assertNotNull(response);

        AuthUser user = (AuthUser) response.getData();
        Assert.assertNotNull(user);
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void revoke() {
        AuthRequest request = new AuthExtendRequest(AuthConfig.builder()
            .clientId("clientId")
            .clientSecret("clientSecret")
            .redirectUri("http://redirectUri")
            .build());

        AuthResponse response = request.revoke(AuthToken.builder().build());
        Assert.assertNotNull(response);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void refresh() {
        AuthRequest request = new AuthExtendRequest(AuthConfig.builder()
            .clientId("clientId")
            .clientSecret("clientSecret")
            .redirectUri("http://redirectUri")
            .build());

        AuthResponse response = request.refresh(AuthToken.builder().build());
        Assert.assertNotNull(response);
        System.out.println(JSON.toJSONString(response.getData()));

    }
}
