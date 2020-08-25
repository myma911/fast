/**
 *
 * <p>自定义 Http 客户端</p>
 *
 */
package cn.aaron911.pay.demo.springboot.custom;

import java.io.InputStream;
import java.util.Map;

import cn.aaron911.pay.core.http.AbstractHttpDelegate;

public class OkHttpKit extends AbstractHttpDelegate {
    @Override
    public String get(String url) {
        // 替换具体实现
        return super.get(url);
    }

    @Override
    public String post(String url, String data) {
        // 替换具体实现
        return super.post(url, data);
    }

    @Override
    public String post(String url, String data, String certPath, String certPass) {
        // 替换具体实现
        return super.post(url, data, certPath, certPass);
    }

    @Override
    public String post(String url, Map<String, Object> paramMap) {
        // 替换具体实现
        return super.post(url, paramMap);
    }

    @Override
    public String post(String url, String data, InputStream certFile, String certPass) {
        // 替换具体实现
        return super.post(url, data, certFile, certPass);
    }

}
