package cn.aaron911.idempotent.context;

import org.springframework.boot.Banner;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;

/**
 * Print LOGO.
 *
 * @version 1.0
 */
@Order(LoggingApplicationListener.DEFAULT_ORDER)
@Slf4j
public class LogoApplactionListener implements ApplicationListener<ContextRefreshedEvent> {

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

        String bannerText = ProgramBanner.buildBannerText();
        if (mode == Banner.Mode.CONSOLE) {
            System.out.println(bannerText);
        } else {
            log.info(bannerText);
        }
    }
}
