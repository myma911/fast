package cn.aaron911.spider.config;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import cn.aaron911.spider.util.DateUtil;

/**
 * fastjson 的日期反序列化组件，适配大部分日期格式
 *
 */
public class SpiderDateDeserializer implements ObjectDeserializer {
    public static final SpiderDateDeserializer instance = new SpiderDateDeserializer();

    public SpiderDateDeserializer() {
    }

    protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        return (T) DateUtil.parse(val);
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        JSONObject object = JSONObject.parseObject(defaultJSONParser.getInput());
        if (null != o && o.equals("releaseDate")) {
            return (T) DateUtil.parse(object.get(o));
        }
        return (T) object.get(o);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
