package cn.aaron911.lock.redisson.listener;


/**
 * @version 1.0
 */
public class RedissonLockBanner {

    public static final String INIT_VERSION = "1.0.0";

    public static final String LOGO = "RedissonLock~!";
            

    public static String buildBannerText() {
    	return System.getProperty("line.separator") + LOGO + " :: RedissonLock :: (v" + RedissonLockVersion.getVersion(INIT_VERSION) + ")" + System.getProperty("line.separator");
    }
}
