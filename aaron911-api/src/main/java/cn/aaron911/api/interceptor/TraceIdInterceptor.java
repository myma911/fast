package cn.aaron911.api.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import cn.aaron911.common.context.HttpRequestContext;
import cn.aaron911.common.result.HttpRequestInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;

/**
 * 日志链路追踪traceID
 *
 */
@Component
public class TraceIdInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(TraceIdInterceptor.class);
    
	private Snowflake snowflake = IdUtil.createSnowflake(2,1);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String traceId = snowflake.nextIdStr();
        // 自定义的请求信息封装
		HttpRequestInfo httpRequestInfo = new HttpRequestInfo();
		httpRequestInfo.setTraceId(traceId);
		HttpRequestContext.setHttpRequestInfo(httpRequestInfo);
        return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		final HttpRequestInfo httpRequestInfo = HttpRequestContext.getHttpRequestInfo();

		Date date = new Date();
		final long currentTimeMillis = date.getTime();
		httpRequestInfo.setRequestEndTime(currentTimeMillis);
		httpRequestInfo.setRequestEndTimeStr(DateUtil.formatDateTime(date));

		log.info(JSONUtil.toJsonStr(httpRequestInfo));
		HttpRequestContext.remove();
	}
}
