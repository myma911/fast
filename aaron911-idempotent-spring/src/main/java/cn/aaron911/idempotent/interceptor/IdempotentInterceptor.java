package cn.aaron911.idempotent.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.aaron911.idempotent.annotation.ApiIdempotent;
import cn.aaron911.idempotent.core.IIdempotentCore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 接口幂等性拦截器
 */
@Component
public class IdempotentInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 次处因为允许用户自定义实现幂等性接口，所以注入list集合，@Order(Integer.MIN_VALUE), 值越小越先读取
	 */
	@Autowired
	private List<IIdempotentCore> idempotentCoreList;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
		if (methodAnnotation != null) {
			IIdempotentCore idempotentCore = idempotentCoreList.get(idempotentCoreList.size()-1);
			// 幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
			idempotentCore.checkToken(request);
		}
		return true;
	}
}