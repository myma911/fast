package cn.aaron911.spider.config.platform;

import cn.aaron911.spider.config.SpiderConfig;

/**
 * @version 1.0
 */
public class IteyePlatform extends BasePlatform {

    public IteyePlatform() {
        super(Platform.ITEYE.getPlatform());
    }

    @Override
    public SpiderConfig process(String url) {
    	SpiderConfig config = this.get(url);
        String domain = config.getDomain();
        String uid = domain.split("\\.")[0];
        config.setUid(uid);
        return config;
    }
}
