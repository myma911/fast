package cn.aaron911.idempotent;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cn.aaron911.idempotent.context.LogoApplactionListener;
import cn.aaron911.idempotent.core.IdempotentCoreImpl;
import cn.aaron911.idempotent.interceptor.IdempotentInterceptor;
import cn.aaron911.idempotent.property.IdempotentProperties;

/**
 * 在 spring 项目中使用 @Import({cn.aaron911.idempotent.EnableIdempotentConfiguration.class})
 * 
 * 
 */
@Configuration
@Import({
	LogoApplactionListener.class,
	IdempotentProperties.class,
	IdempotentCoreImpl.class,
	IdempotentInterceptor.class
})
public class EnableIdempotentConfiguration {

}
