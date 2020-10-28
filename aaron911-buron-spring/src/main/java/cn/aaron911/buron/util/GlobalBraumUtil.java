package cn.aaron911.buron.util;


public enum GlobalBraumUtil {

    INSTANCE;

    private static final String cacheKeyPrefix = "buron_";
    private static final String blacklistKeyPrefix = "buron_blacklist_";

    public String getLockKey(String ip) {
        return formatKey(cacheKeyPrefix + ip);
    }

    public String getBlacklistKey(String ip) {
        return formatKey(blacklistKeyPrefix + ip);
    }

    public String formatKey(String key) {
        if (null == key || key.isEmpty()) {
            return null;
        }
        return key.replaceAll("[.:]", "_");
    }

}
