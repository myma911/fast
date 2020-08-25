package cn.aaron911.test.spider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

import cn.aaron911.spider.MySpider;
import cn.aaron911.spider.config.SpiderConfig;
import cn.aaron911.spider.config.SpiderConfigContext;
import cn.aaron911.spider.config.platform.Platform;
import cn.aaron911.spider.entity.VirtualArticle;
import cn.aaron911.spider.processor.BlogSpiderProcessor;
import cn.aaron911.spider.processor.SpiderProcessor;
import cn.aaron911.spider.util.SpiderPrintWriter;
import cn.hutool.core.collection.CollUtil;

/**
 * 
 * 
 */
public class SpiderTest {

	
	

    /**
     * 抓取单个文章
     * @Title: crawlSingle
     * @author Aaron
     * @Date 2020年7月3日 下午4:39:15
     * @param typeId
     * @param urls
     * @param convertImg
     * @param writer void
     * @throws FileNotFoundException 
     */
    @Test
    public void crawlSingle() throws FileNotFoundException {
    	String[] urls = new String[]{"https://www.cnblogs.com/hello-shf/p/10864977.html"};
    	boolean convertImg = true;
    	PrintWriter writer = new PrintWriter(new File("D:\\111.txt"));
    	
        SpiderPrintWriter writerUtil = new SpiderPrintWriter(writer);
        for (String url : urls) {
            SpiderProcessor hunter = new BlogSpiderProcessor(url, convertImg, writerUtil);
            CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
            saveArticles(hunter.getConfig(), writerUtil, list);
        }
        writerUtil.shutdown();
    }
    

    private void saveArticles(SpiderConfig config, SpiderPrintWriter writerUtil, CopyOnWriteArrayList<VirtualArticle> list) {
        for (VirtualArticle spiderVirtualArticle : list) {
        	System.out.println(spiderVirtualArticle.toString());
     
            writerUtil.print(String.format("[ save ] Succeed! <a href=\"%s\" target=\"_blank\">%s</a>", spiderVirtualArticle.getSource(), spiderVirtualArticle.getTitle()));
        }
    }


	/**
	 * 运行文章搬运工
	 * @Title: run
	 * @author Aaron
	 * @Date 2020年7月3日 下午4:40:49
	 * @param typeId
	 * @param config
	 * @param writer void
	 * @throws FileNotFoundException 
	 */
    @Test
    public void run() throws FileNotFoundException {
    	

    	PrintWriter writer = new PrintWriter(new File("D:\\111.txt"));
    	SpiderConfig config = SpiderConfigContext.getSpiderConfig(Platform.CNBLOGS);
    	
        SpiderPrintWriter writerUtil = new SpiderPrintWriter(writer);
        long start = System.currentTimeMillis();

        SpiderProcessor hunter = new BlogSpiderProcessor(config, writerUtil, "userid");
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (CollUtil.isEmpty(list)) {
            writerUtil.print(String.format("未抓取到任何内容，请确保连接[<a href=\"%s\" target=\"_blank\">%s</a>]是否正确并能正常访问！共耗时 %s ms.", config.getEntryUrls().get(0), config.getEntryUrls().get(0), (System.currentTimeMillis() - start))).shutdown();
            return;
        }
        writerUtil.print("Congratulation ! 此次共整理到" + list.size() + "篇文章");
        saveArticles(config, writerUtil, list);

        writerUtil.print(String.format("搬家完成！耗时 %s ms.", (System.currentTimeMillis() - start)));
        writerUtil.shutdown();
    }
    
    
    /**
     * 停止文章搬运工
     * @Title: stop
     * @author Aaron
     * @Date 2020年7月3日 下午4:40:19 void
     */
    @Test
    public void stop() {
        MySpider spider = MySpider.getHunter("userid");
        spider.stop();
    }
}
