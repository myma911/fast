package cn.aaron911.buron.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import cn.aaron911.buron.BuronAutoConfiguration;
import cn.aaron911.buron.context.LogoApplactionListener;
import cn.aaron911.buron.property.BuronProperties;

/**
 * Enable Buron for spring boot application
 * 
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BuronAutoConfiguration.class, BuronProperties.class, LogoApplactionListener.class})
public @interface EnableBuron {
}
