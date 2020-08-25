package cn.aaron911.spider.config.platform;

import cn.aaron911.spider.config.SpiderConfig;

/**
 * @version 1.0
 */
public class JuejinPlatform extends BasePlatform {

    public JuejinPlatform() {
        super(Platform.JUEJIN.getPlatform());
    }

    @Override
    public SpiderConfig process(String url) {
        return this.get(url);
    }
}
