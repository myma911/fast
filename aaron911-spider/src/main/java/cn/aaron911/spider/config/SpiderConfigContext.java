package cn.aaron911.spider.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.aaron911.spider.config.platform.InnerPlatform;
import cn.aaron911.spider.config.platform.Platform;
import cn.aaron911.spider.util.PlatformUtil;

/**
 * 
 */
public class SpiderConfigContext {

    /**
     * 抓取单个文章时可用；
     *
     * @param url 待抓取的文章连接
     * @return SpiderConfig
     */
    public static SpiderConfig getSpiderConfig(String url) {
        InnerPlatform platform = PlatformUtil.getPlarform(url);
        return platform.process(url);
    }

    /**
     * 抓取单个文章时可用；
     *
     * @param platform 博客平台
     * @return SpiderConfig
     */
    public static SpiderConfig getSpiderConfig(Platform platform) {
        String platformConfig = SpiderConfigTemplate.getConfig(platform.getPlatform());
        JSONObject platformObj = JSONObject.parseObject(platformConfig);
        String br = "\r\n";
        Set<Map.Entry<String, Object>> entries = platformObj.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if ("header".equals(entry.getKey())) {
                List<String> headers = JSONArray.parseArray(String.valueOf(entry.getValue()), String.class);
                entry.setValue(String.join(br, headers));
            }
        }
        return JSONObject.toJavaObject(platformObj, SpiderConfig.class);
    }

    /**
     * 重新解析配置模板， 将用户id替换为真实的id
     *
     * @param config config
     * @return config
     */
    public static SpiderConfig parseConfig(SpiderConfig config) {
        if (null == config) {
            return null;
        }
        String uid = config.getUid();
        if (StringUtils.isEmpty(uid)) {
            return config;
        }
        String domain = config.getDomain();
        if (StringUtils.isNotEmpty(domain)) {
            config.setDomain(domain.replace("{uid}", uid));
        }
        String targetLinksRegex = config.getTargetLinksRegex();
        if (StringUtils.isNotEmpty(targetLinksRegex)) {
            config.setTargetLinksRegex(targetLinksRegex.replace("{uid}", uid));
        }
        List<String> entryUrls = config.getEntryUrls();
        if (CollectionUtils.isNotEmpty(entryUrls)) {
            List<String> newEntryUrls = new ArrayList<>();
            for (String entryUrl : entryUrls) {
                newEntryUrls.add(entryUrl.replace("{uid}", uid));
            }
            config.setEntryUrls(newEntryUrls);
        }
        Map<String, String> header = config.getHeaders();
        if (MapUtils.isNotEmpty(header)) {
            Set<Map.Entry<String, String>> entries = header.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue();
                header.put(key, value.replace("{uid}", uid));
            }
        }
        return config;
    }
}
