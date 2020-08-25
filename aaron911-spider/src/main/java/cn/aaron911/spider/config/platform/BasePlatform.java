package cn.aaron911.spider.config.platform;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import cn.aaron911.spider.config.SpiderConfigTemplate;
import cn.aaron911.spider.config.SpiderConfig;
import cn.aaron911.spider.util.PlatformUtil;

/**
 * @version 1.0
 */
public abstract class BasePlatform implements InnerPlatform {
    String platform;

    public BasePlatform(String platform) {
        this.platform = platform;
    }

    protected final SpiderConfig get(String url) {

        String host = PlatformUtil.getHost(url);
        String domain = PlatformUtil.getDomain(url);

        String platformConfig = SpiderConfigTemplate.getConfig(platform);
        JSONObject platformObj = JSONObject.parseObject(platformConfig);
        String br = "\r\n", header = null;
        Set<Map.Entry<String, Object>> entries = platformObj.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if ("header".equals(entry.getKey())) {
                header = "Host=" + host + br + "Referer=" + domain;
                entry.setValue(header);
            } else if ("entryUrls".equals(entry.getKey())) {
                entry.setValue(Collections.singletonList(url));
            } else {
                if (platform.equals(Platform.ITEYE.getPlatform()) && "domain".equals(entry.getKey())) {
                    entry.setValue(host);
                }
            }
        }
        SpiderConfig config = JSONObject.toJavaObject(platformObj, SpiderConfig.class);
        config.setSingle(true);
        return config;
    }
}
