package cn.aaron911.spider.config.platform;

import cn.aaron911.spider.config.SpiderConfig;

/**
 * @version 1.0
 */
public interface InnerPlatform {

    SpiderConfig process(String url);
}
