package cn.aaron911.buron;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cn.aaron911.buron.context.LogoApplactionListener;
import cn.aaron911.buron.property.BuronProperties;

/**
 * spring 中使用 @Import({cn.aaron911.buron.EnableBuronConfiguration.class}) 
 * 
 * 
 */
@Configuration
@Import({BuronAutoConfiguration.class, BuronProperties.class, LogoApplactionListener.class})
public class EnableBuronConfiguration {
	
	

}
