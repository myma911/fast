package cn.aaron911.test.oauth.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import cn.aaron911.oauth.cache.AuthDefaultStateCache;

public class AuthStateCacheTest {

    @Test
    public void cache1() throws InterruptedException {
        AuthDefaultStateCache.INSTANCE.cache("key", "value");
        Assert.assertEquals(AuthDefaultStateCache.INSTANCE.get("key"), "value");

        TimeUnit.MILLISECONDS.sleep(4);
        Assert.assertEquals(AuthDefaultStateCache.INSTANCE.get("key"), "value");
    }

    @Test
    public void cache2() throws InterruptedException {
        AuthDefaultStateCache.INSTANCE.cache("key", "value", 10);
        Assert.assertEquals(AuthDefaultStateCache.INSTANCE.get("key"), "value");

        // 没过期
        TimeUnit.MILLISECONDS.sleep(5);
        Assert.assertEquals(AuthDefaultStateCache.INSTANCE.get("key"), "value");

        // 过期
        TimeUnit.MILLISECONDS.sleep(6);
        Assert.assertNull(AuthDefaultStateCache.INSTANCE.get("key"));
    }
}
