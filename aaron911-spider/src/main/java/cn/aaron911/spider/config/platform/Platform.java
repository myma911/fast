package cn.aaron911.spider.config.platform;

import cn.aaron911.spider.util.PlatformUtil;

/**
 * @version 1.0
 */
public enum Platform {
    CSDN("csdn", "csdn.net", CsdnPlatform.class),
    ITEYE("iteye", "iteye.com", IteyePlatform.class),
    IMOOC("imooc", "imooc.com", ImoocPlatform.class),
    CNBLOGS("cnblogs", "cnblogs.com", CnblogsPlatform.class),
    JUEJIN("juejin", "juejin.im", JuejinPlatform.class),
    V2EX("v2ex", "v2ex.com", V2exPlatform.class),
    ;

    private String platform;
    private String host;
    private Class clazz;

    Platform(String platform, String host, Class clazz) {
        this.platform = platform;
        this.host = host;
        this.clazz = clazz;
    }

    public static Platform getPlatformByUrl(String url) {
        if (null == url) {
            return null;
        }
        String host = PlatformUtil.getHost(url);
        if (host == null) {
            return null;
        }
        for (Platform value : Platform.values()) {
            if (host.contains(value.getHost())) {
                return value;
            }
        }
        return null;
    }

    public String getPlatform() {
        return platform;
    }

    public String getHost() {
        return host;
    }

    public Class getClazz() {
        return clazz;
    }

}
