package cn.aaron911.test.oauth.utils;

import org.junit.Assert;
import org.junit.Test;

import cn.aaron911.oauth.config.AuthConfig;
import cn.aaron911.oauth.config.AuthDefaultSource;
import cn.aaron911.oauth.request.AuthWeChatOpenRequest;
import cn.aaron911.oauth.utils.UrlBuilder;

/**
 * <p>
 * UrlBuilder测试类
 * </p>
 *
 */
public class UrlBuilderTest {
    @Test
    public void testUrlBuilder() {
        AuthConfig config = AuthConfig.builder()
            .clientId("appid-110110110")
            .clientSecret("secret-110110110")
            .redirectUri("https://xkcoding.com")
            .build();
        String build = UrlBuilder.fromBaseUrl(AuthDefaultSource.WECHAT_OPEN.authorize())
            .queryParam("appid", config.getClientId())
            .queryParam("redirect_uri", config.getRedirectUri())
            .queryParam("response_type", "code")
            .queryParam("scope", "snsapi_login")
            .queryParam("state", "")
            .build(false);
        System.out.println(build);
        AuthWeChatOpenRequest request = new AuthWeChatOpenRequest(config);
        String authorize = request.authorize("state");
        System.out.println(authorize);
    }

    @Test
    public void build() {
        String url = UrlBuilder.fromBaseUrl("")
            .queryParam("name", "")
            .build();
        Assert.assertEquals(url, "");

        url = UrlBuilder.fromBaseUrl(url)
            .queryParam("github", "")
            .build();
        Assert.assertEquals(url, "");
    }

    @Test
    public void build1() {
        String url = UrlBuilder.fromBaseUrl("")
            .queryParam("name", "")
            .build(true);
        Assert.assertEquals(url, "");

        url = UrlBuilder.fromBaseUrl(url)
            .queryParam("github", "")
            .build(true);
        Assert.assertEquals(url, "");
    }
}
