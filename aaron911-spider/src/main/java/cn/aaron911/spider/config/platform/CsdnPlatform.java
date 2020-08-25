package cn.aaron911.spider.config.platform;

import cn.aaron911.spider.config.SpiderConfig;

/**
 * @version 1.0
 */
public class CsdnPlatform extends BasePlatform {

    public CsdnPlatform() {
        super(Platform.CSDN.getPlatform());
    }

    @Override
    public SpiderConfig process(String url) {
        return this.get(url);
    }
}
