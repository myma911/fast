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
import cn.aaron911.oauth.request.AuthWeChatOpenRequest;
import cn.hutool.core.util.IdUtil;

/**
 * 微信request的测试类，用于演示具体的用法
 *
 */
public class AuthWeiXinRequestTest {

	private final String clientId = "wx211bec9a5839cf0b";
	private final String clientSecret = "47fb27cac379ef4a1a1a8b8b2514d280";
	private final String redirectUri = "http://2587m40v86.qicp.vip/upframe/pdfjs/web/viewer.html?file=http://2587m40v86.qicp.vip/upframe/33ad1e0e-dd7b-4b9b-8a63-98080e48f5c9.pdf";

	@Test
	public void authorize() {
		AuthRequest request = new AuthWeChatOpenRequest(
				AuthConfig.builder().clientId(clientId).clientSecret(clientSecret).redirectUri(redirectUri).build());
		String authorize = request.authorize(IdUtil.fastUUID());
		System.out.println(authorize);
		Assert.assertNotNull(authorize);

	}

	@Test
	public void login() {
		AuthRequest request = new AuthWeChatOpenRequest(
				AuthConfig.builder().clientId(clientId).clientSecret(clientSecret).redirectUri(redirectUri).build());

		String state = IdUtil.fastUUID();
		String authorize = request.authorize(state);
		System.out.println(authorize);
		
		
		
		String code = "071mgfcz1GHDYb0iHpcz1bAfcz1mgfcC";
		AuthCallback callback = AuthCallback.builder().code(code).state(state).build();

		AuthResponse response = request.login(callback);
		Assert.assertNotNull(response);

		AuthUser user = (AuthUser) response.getData();
		Assert.assertNotNull(user);
		System.out.println(JSON.toJSONString(user));
	}

	@Test
	public void revoke() {
		AuthRequest request = new AuthWeChatOpenRequest(
				AuthConfig.builder().clientId(clientId).clientSecret(clientSecret).redirectUri(redirectUri).build());

		AuthResponse response = request.revoke(AuthToken.builder().build());
		Assert.assertNotNull(response);
		System.out.println(JSON.toJSONString(response));
	}

	@Test
	public void refresh() {
		AuthRequest request = new AuthWeChatOpenRequest(
				AuthConfig.builder().clientId(clientId).clientSecret(clientSecret).redirectUri(redirectUri).build());

		AuthResponse response = request.refresh(AuthToken.builder().build());
		Assert.assertNotNull(response);
		System.out.println(JSON.toJSONString(response.getData()));

	}
}
