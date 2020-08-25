package cn.aaron911.spider.config.platform;

import cn.aaron911.spider.config.SpiderConfig;

/**
 * @version 1.0
 */
public class V2exPlatform extends BasePlatform {

    public V2exPlatform() {
        super(Platform.V2EX.getPlatform());
    }

    @Override
    public SpiderConfig process(String url) {
        return this.get(url);
    }
}
