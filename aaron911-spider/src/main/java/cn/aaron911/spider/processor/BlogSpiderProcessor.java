package cn.aaron911.spider.processor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;

import cn.aaron911.spider.MySpider;
import cn.aaron911.spider.config.SpiderConfig;
import cn.aaron911.spider.downloader.HttpClientDownloader;
import cn.aaron911.spider.entity.VirtualArticle;
import cn.aaron911.spider.scheduler.BlockingQueueScheduler;
import cn.aaron911.spider.util.SpiderPrintWriter;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * 爬虫入口
 *
 */
public class BlogSpiderProcessor extends SpiderProcessor {

    public BlogSpiderProcessor(String url, boolean convertImage) {
        super(url, convertImage);
    }

    public BlogSpiderProcessor(String url, boolean convertImage, SpiderPrintWriter writer) {
        super(url, convertImage, writer);
    }

    public BlogSpiderProcessor(SpiderConfig config) {
        super(config);
    }

    public BlogSpiderProcessor(SpiderConfig config, String uuid) {
        super(config, uuid);
    }

    /**
     * @param config Hunter Config
     * @param writer
     * @param uuid
     */
    public BlogSpiderProcessor(SpiderConfig config, SpiderPrintWriter writer, String uuid) {
        super(config, writer, uuid);
    }

    /**
     * 运行爬虫并返回结果
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<VirtualArticle> execute() {
        List<String> errors = this.validateModel(config);
        if (CollectionUtils.isNotEmpty(errors)) {
            writer.print("校验不通过！请依据下方提示，检查输入参数是否正确......");
            for (String error : errors) {
                writer.print(">> " + error);
            }
            return null;
        }

        CopyOnWriteArrayList<VirtualArticle> virtualArticles = new CopyOnWriteArrayList<>();
        MySpider spider = MySpider.create(this, config, uuid);

        spider.addUrl(config.getEntryUrls().toArray(new String[0]))
                .setScheduler(new BlockingQueueScheduler(config))
                .addPipeline((resultItems, task) -> this.process(resultItems, virtualArticles, spider))
                .setDownloader(new HttpClientDownloader())
                .thread(config.getThreadCount());

        //设置抓取代理IP
        if (!CollectionUtils.isEmpty(config.getProxyList())) {
            HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
            SimpleProxyProvider provider = SimpleProxyProvider.from(config.getProxyList().toArray(new Proxy[0]));
            httpClientDownloader.setProxyProvider(provider);
            spider.setDownloader(httpClientDownloader);
        }
        // 测试代理
        /*HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        SimpleProxyProvider provider = SimpleProxyProvider.from(
                new Proxy("61.135.217.7", 80)
        );
        httpClientDownloader.setProxyProvider(provider);
        spider.setDownloader(httpClientDownloader);*/

        // 启动爬虫
        spider.run();
        return virtualArticles;
    }


}
