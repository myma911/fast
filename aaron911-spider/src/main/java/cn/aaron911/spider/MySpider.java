package cn.aaron911.spider;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import cn.aaron911.spider.config.SpiderConfig;
import cn.aaron911.spider.enums.ExitWayEnum;
import cn.aaron911.spider.exception.SpiderException;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 */
public class MySpider extends Spider {

    /**
     * 用来保存正在运行的所有Spider，key要求唯一，一般为用户ID，需要调用方生成
     */
    public static final ConcurrentHashMap<String, MySpider> SPIDER_BUCKET = new ConcurrentHashMap<>();

    private SpiderConfig config;

    /**
     * 唯一的key，一般为用户ID，需要调用方生成
     */
    private String hunterId;
    private volatile long startTime = 0L;

    private MySpider(PageProcessor pageProcessor, SpiderConfig config, String hunterId) {
        super(pageProcessor);
        this.config = config;
        this.hunterId = hunterId;
        SPIDER_BUCKET.put(hunterId, this);
    }

    public static MySpider create(PageProcessor pageProcessor, SpiderConfig config, String hunterId) {
        return new MySpider(pageProcessor, config, hunterId);
    }

    public static MySpider getHunter(String hunterId) {
        if (StringUtils.isEmpty(hunterId)) {
            throw new SpiderException("HunterId：[" + hunterId + "]为空，请指定HunterId");
        }
        MySpider hunter = SPIDER_BUCKET.get(hunterId);
        if (null == hunter) {
            throw new SpiderException("当前没有正在运行的爬虫！HunterId：[" + hunterId + "]");
        }
        return hunter;
    }

    @Override
    protected void onSuccess(Request request) {
        super.onSuccess(request);
        if (this.getStatus() == Status.Running && ExitWayEnum.DURATION.toString().equals(config.getExitWay())) {
            if (startTime < System.currentTimeMillis()) {
                this.stop();
            }
        }
    }

    @Override
    public void run() {
        if (ExitWayEnum.DURATION.toString().equals(config.getExitWay())) {
            startTime = System.currentTimeMillis() + config.getCount() * 1000;
        }
        super.run();
    }

    @Override
    protected void onError(Request request) {
        super.onError(request);
    }

    @Override
    public void close() {
        super.close();
        SPIDER_BUCKET.remove(this.hunterId);
    }

    @Override
    public void stop() {
        Spider.Status status = this.getStatus();
        if (status.equals(Spider.Status.Running)) {
            super.stop();
            SPIDER_BUCKET.remove(this.hunterId);
        } else if (status.equals(Spider.Status.Init)) {
            throw new SpiderException("爬虫正在初始化！HunterId：[" + this.hunterId + "]");
        } else {
            throw new SpiderException("当前没有正在运行的爬虫！HunterId：[" + this.hunterId + "]");
        }
    }
}
