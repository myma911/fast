package cn.aaron911.spider.config;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

import cn.aaron911.spider.consts.SpiderConsts;
import cn.aaron911.spider.exception.SpiderException;
import cn.hutool.core.io.IoUtil;

/**
 * @version 1.0
 */
public class SpiderConfigTemplate {

    public static JSONObject configTemplate;

    static {
        SpiderConfigTemplate configTemplate = new SpiderConfigTemplate();
        configTemplate.init();
    }

    public static String getConfig(String platform) {
        if (configTemplate.containsKey(platform)) {
            return configTemplate.getString(platform);
        }
        throw new SpiderException("暂不支持该平台[" + platform + "]");
    }

    private void init() {
        String configFileName = SpiderConsts.CONFIG_FILE_NAME;
        String config = null;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(configFileName);
            if (null == inputStream) {
                throw new SpiderException("请检查`src/main/resources`下是否存在" + configFileName);
            }
            config = IoUtil.read(inputStream, Charset.forName("UTF-8"));
            if (StringUtils.isEmpty(config)) {
                throw new SpiderException("SpiderConfig内容为空：" + configFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            configTemplate = JSONObject.parseObject(config);
        } catch (Exception e) {
            throw new SpiderException("SpiderConfig配置文件格式错误");
        }

    }

}
