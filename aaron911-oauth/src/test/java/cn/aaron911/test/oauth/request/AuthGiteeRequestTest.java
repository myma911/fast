package cn.aaron911.test.oauth.request;

import org.junit.Test;

import cn.aaron911.oauth.config.AuthConfig;
import cn.aaron911.oauth.model.AuthCallback;
import cn.aaron911.oauth.request.AuthGiteeRequest;
import cn.aaron911.oauth.request.AuthRequest;
import cn.hutool.core.util.IdUtil;

public class AuthGiteeRequestTest {
	
	
	@Test
	public void test() {
		
		// 创建授权request
		AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
		        .clientId("clientId")
		        .clientSecret("clientSecret")
		        .redirectUri("redirectUri")
		        .build());
		String state = IdUtil.fastUUID();
		// 生成授权页面
		authRequest.authorize(state);
		// 授权登录后会返回code（auth_code（仅限支付宝））、state，1.8.0版本后，可以用AuthCallback类作为回调接口的参数
		// 注：JustAuth默认保存state的时效为3分钟，3分钟内未使用则会自动清除过期的state
		AuthCallback callback = AuthCallback.builder().code("code").state(state).build();
		authRequest.login(callback);
		
	}

}
