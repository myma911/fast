package cn.aaron911.buron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.aaron911.buron.cache.Cache;
import cn.aaron911.buron.cache.CacheObj;
import cn.aaron911.buron.property.BuronProperties;
import cn.aaron911.buron.util.GlobalBraumUtil;
import cn.aaron911.buron.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 */
public class BuronShieldProcessor implements BuronProcessor{
    private static final Logger log = LoggerFactory.getLogger(BuronShieldProcessor.class);

    @Autowired
    private BuronProperties properties;
    
    @Autowired
    @Qualifier("buronCache")
    private Cache cache;

    
    @Override
    public BuronResponse process(HttpServletRequest request) {
        RequestUtil requestUtil = new RequestUtil(request);
        String ip = requestUtil.getIp();
        String key = GlobalBraumUtil.INSTANCE.formatKey(ip);
        String lockKey = GlobalBraumUtil.INSTANCE.getLockKey(key);
        String lockMsgTpl = "[%s]涉嫌恶意访问已被临时限制！共被限制过[%s]次，本次剩余限制时间:%s ms",
                accessMsgTpl = "[%s]在%s毫秒内已连续发起 %s 次请求";

        // 本次被限制访问
        if (cache.hasKey(lockKey)) {
            int blocklistCount = this.getBlacklistCount(key);
            long expire = cache.getExpire(lockKey);
            String msg = String.format(lockMsgTpl, ip, blocklistCount, expire);
            log.debug(msg);
            return new BuronResponse().isError()
                    .setMsg(msg)
                    .setExpire(expire)
                    .setLimitCount(blocklistCount)
                    .setAccessInfo(request);
        }

        // 再次被限制访问
        if (isLimitedAccess(key)) {
            this.save2Blacklist(key);
            cache.set(lockKey, 1, properties.getLimitedTime(), TimeUnit.MILLISECONDS);
            long expire = cache.getExpire(lockKey);
            int blocklistCount = this.getBlacklistCount(key);
            String msg = String.format(lockMsgTpl, ip, blocklistCount, expire);
            log.debug(msg);
            return new BuronResponse().isError()
                    .setMsg(msg)
                    .setExpire(expire)
                    .setLimitCount(blocklistCount)
                    .setAccessInfo(request);
        }

        String msg = String.format(accessMsgTpl, ip, properties.getInterval(), cache.get(key).getValue());
        log.debug(msg);
        return new BuronResponse()
                .isSuccess()
                .setMsg(msg)
                .setAccessInfo(request);
    }

    /**
     * 拉黑用户
     *
     * @param ip 当前发起请求的用户IP
     */
    private void save2Blacklist(String ip) {
        String blacklistKey = GlobalBraumUtil.INSTANCE.getBlacklistKey(ip);
        cache.set(blacklistKey, getBlacklistCount(ip) + 1, properties.getBlacklistTime(), TimeUnit.MILLISECONDS);
    }

    /**
     * 获取被拉黑过的次数
     *
     * @param ip 当前发起请求的用户IP
     */
    private int getBlacklistCount(String ip) {
        String blacklistKey = GlobalBraumUtil.INSTANCE.getBlacklistKey(ip);
        CacheObj blacklistCache = cache.get(blacklistKey);
        if (null == blacklistCache) {
            return 0;
        }
        return blacklistCache.getValue();
    }

    /**
     * 是否已经被限制访问
     *
     * @param ip 当前发起请求的用户IP
     */
    private boolean isLimitedAccess(String ip) {
        return cache.incrementAndGet(ip) > properties.getThreshold();
    }

}
