package cn.aaron911.lock.redisson.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.aaron911.lock.redisson.RedissonLockBanner;

/**
 * Print LOGO.
 *
 */
@Component
@Order(LoggingApplicationListener.DEFAULT_ORDER)
public class RedissonLockLogoApplactionListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(RedissonLockLogoApplactionListener.class);

    private static Banner.Mode mode = Banner.Mode.CONSOLE;

    public static Banner.Mode getMode() {
        return mode;
    }

    public static void setMode(Banner.Mode customMode) {
        mode = customMode;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (mode == Banner.Mode.OFF) {
            return;
        }

        String bannerText = RedissonLockBanner.buildBannerText();
        if (mode == Banner.Mode.CONSOLE) {
            System.out.println(bannerText);
        } else {
            log.info(bannerText);
        }
    }
}
