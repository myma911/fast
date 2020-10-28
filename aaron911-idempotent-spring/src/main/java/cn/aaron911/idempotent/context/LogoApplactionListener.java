package cn.aaron911.idempotent.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class LogoApplactionListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(LogoApplactionListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        String bannerText = ProgramBanner.buildBannerText();
        if (log.isDebugEnabled()) {
        	log.debug(bannerText);
        }
    }
}
