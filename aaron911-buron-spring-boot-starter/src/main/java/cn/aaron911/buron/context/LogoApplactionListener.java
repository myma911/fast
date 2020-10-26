package cn.aaron911.buron.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class LogoApplactionListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(LogoApplactionListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	String bannerText = Banner.buildBannerText();
        if (log.isDebugEnabled()) {
            log.debug(bannerText);
        }
    }
}
