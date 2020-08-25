package cn.aaron911.spider.resolver;

import cn.aaron911.spider.config.SpiderConfig;
import us.codecraft.webmagic.Page;

/**
 * 页面解析器
 *
 * @version 1.0
 */
public interface Resolver {
    void process(Page page, SpiderConfig model);
}
