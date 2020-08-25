package cn.aaron911.test.oauth.model;

import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.aaron911.oauth.config.AuthDefaultSource;
import cn.aaron911.oauth.config.AuthSource;
import cn.aaron911.oauth.model.AuthUser;
import cn.aaron911.test.oauth.config.AuthExtendSource;

public class AuthUserTest {

    @Test
    public void serialize() {

        AuthUser user = AuthUser.builder()
            .nickname("test")
            .build();
        String json = JSON.toJSONString(user);
        Assert.assertEquals(json, "{\"nickname\":\"test\"}");

    }

    @Test
    public void deserialize() {
        AuthUser user = AuthUser.builder()
            .nickname("test")
            .build();
        String json = JSON.toJSONString(user);

        AuthUser deserializeUser = JSON.parseObject(json, AuthUser.class);
        Assert.assertEquals(deserializeUser.getNickname(), "test");
    }

    @Test
    public void source() {
        AuthSource source = AuthDefaultSource.HUAWEI;
        AuthUser user = AuthUser.builder()
            .source(source.toString())
            .build();
        Assert.assertEquals(user.getSource(), "HUAWEI");

        source = AuthExtendSource.OTHER;
        user = AuthUser.builder()
            .source(source.toString())
            .build();
        Assert.assertEquals(user.getSource(), "OTHER");

        source = AuthDefaultSource.HUAWEI;
        Assert.assertEquals(source, AuthDefaultSource.HUAWEI);

        source = AuthExtendSource.OTHER;
        Assert.assertEquals(source, AuthExtendSource.OTHER);
    }

}
