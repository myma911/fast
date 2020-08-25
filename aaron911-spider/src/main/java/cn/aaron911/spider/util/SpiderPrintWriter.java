package cn.aaron911.spider.util;



import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.aaron911.spider.consts.SpiderConsts;

/**
 * 系统输出工具类，当传入PrintWriter时可以将字符流输出到页面， 默认为log日志输出
 * 
 */
public class SpiderPrintWriter {
	private static Logger logger = LoggerFactory.getLogger(SpiderPrintWriter.class);
	
    private String jsoupCallback = "<script>parent.printMessage('%s');</script>";
    private PrintWriter writer;

    public SpiderPrintWriter() {
    }

    /**
     * @param writer        输出流
     * @param jsoupCallback 用于页面打印日志的jsoup回调函数，默认为使用iframe方式打开，回调函数为‘parent.printMessage’。具体使用方法，可参考帮助文档
     */
    public SpiderPrintWriter(PrintWriter writer, String jsoupCallback) {
        this.writer = writer;
        if (null != jsoupCallback) {
            this.jsoupCallback = jsoupCallback;
        }
    }

    /**
     * @param writer 输出流
     */
    public SpiderPrintWriter(PrintWriter writer) {
        this(writer, null);
    }

    public SpiderPrintWriter print(String... msgs) {
        for (String msg : msgs) {
            if (!msg.equals("shutdown")) {
                msg = SpiderConsts.LOG_PREFIX + msg;
            }

            logger.info(msg);
            if (null != writer) {
                writer.print(String.format(this.jsoupCallback, msg));
                writer.flush();
            }
        }

        return this;
    }

    public void shutdown() {
        print("bye~~", "shutdown");
        if (null != writer) {
            writer.close();
            writer = null;
        }
    }
}
