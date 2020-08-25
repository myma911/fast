package cn.aaron911.pay.core.kit;


import javax.servlet.http.HttpServletRequest;

import cn.aaron911.pay.core.http.AbstractHttpDelegate;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * <p>Http 工具类</p>
 *
 * @author Javen
 */
public class HttpKit {

    private static AbstractHttpDelegate delegate = new DefaultHttpKit();

    public static AbstractHttpDelegate getDelegate() {
        return delegate;
    }

    public static void setDelegate(AbstractHttpDelegate delegate) {
        HttpKit.delegate = delegate;
    }

    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * 使用 huTool 实现的 Http 工具类
 *
 * @author Javen
 */
class DefaultHttpKit extends AbstractHttpDelegate {
}
