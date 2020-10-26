package cn.aaron911.buron;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class BuronTypeRedisCondition implements Condition{
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		
		return "redis".equals(context.getEnvironment().getProperty("aaron911.buron.type"));
	}

}
