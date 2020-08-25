package cn.aaron911.spider.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import cn.aaron911.spider.MySpider;
import cn.aaron911.spider.config.SpiderConfigContext;
import cn.aaron911.spider.config.SpiderDateDeserializer;
import cn.aaron911.spider.config.SpiderConfig;
import cn.aaron911.spider.entity.Cookie;
import cn.aaron911.spider.entity.VirtualArticle;
import cn.aaron911.spider.resolver.HtmlResolver;
import cn.aaron911.spider.resolver.JsonResolver;
import cn.aaron911.spider.resolver.Resolver;
import cn.aaron911.spider.util.CommonUtil;
import cn.aaron911.spider.util.SpiderPrintWriter;
import cn.hutool.core.collection.CollectionUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 统一对页面进行解析处理
 *
 * @version 1.0
 */
public abstract class SpiderProcessor implements PageProcessor {
	private static Logger logger = LoggerFactory.getLogger(SpiderProcessor.class);
	
    protected SpiderConfig config;
    protected SpiderPrintWriter writer = new SpiderPrintWriter();
    protected String uuid;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    SpiderProcessor() {
    }

    SpiderProcessor(SpiderConfig m) {
        this(m, UUID.randomUUID().toString());
    }

    SpiderProcessor(SpiderConfig m, String uuid) {
        this(m, null, uuid);
    }

    SpiderProcessor(SpiderConfig config, SpiderPrintWriter writer, String uuid) {
        this.config = SpiderConfigContext.parseConfig(config);
        this.uuid = uuid;
        if (null != writer) {
            this.writer = writer;
        }
    }

    SpiderProcessor(String url, boolean convertImage) {
        this(SpiderConfigContext.getSpiderConfig(url).setConvertImg(convertImage));
    }

    SpiderProcessor(String url, boolean convertImage, SpiderPrintWriter writer) {
        this(SpiderConfigContext.getSpiderConfig(url).setConvertImg(convertImage));
        if (writer != null) {
            this.writer = writer;
        }
    }

    /**
     * 程序入口方法
     *
     * @return 返回VirtualArticle列表
     */
    public abstract CopyOnWriteArrayList<VirtualArticle> execute();

    @Override
    public void process(Page page) {
        Resolver resolver = new HtmlResolver();
        if (config.getAjaxRequest()) {
            resolver = new JsonResolver();
        }
        resolver.process(page, config);

    }

    @Override
    public Site getSite() {
        Site site = Site.me()
                .setCharset(config.getCharset())
                .setDomain(config.getDomain())
                .setUserAgent(config.getUa())
                .setSleepTime(config.getSleepTime())
                .setRetryTimes(config.getRetryTimes())
                .setCycleRetryTimes(config.getCycleRetryTimes());

        //添加抓包获取的cookie信息
        List<Cookie> cookies = config.getCookies();
        if (CollectionUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (StringUtils.isEmpty(cookie.getDomain())) {
                    site.addCookie(cookie.getName(), cookie.getValue());
                    continue;
                }
                site.addCookie(cookie.getDomain(), cookie.getName(), cookie.getValue());
            }
        }
        //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
        Map<String, String> headers = config.getHeaders();
        if (MapUtils.isNotEmpty(headers)) {
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                site.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return site;
    }

    /**
     * 校验参数
     *
     * @param t 待校验的参数
     */
    final <T> List<String> validateModel(T t) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }

    /**
     * 自定义管道的处理方法
     *
     * @param resultItems     自定义Processor处理完后的所有参数
     * @param virtualArticles 爬虫文章集合
     */
    final void process(ResultItems resultItems, List<VirtualArticle> virtualArticles, MySpider spider) {
        if (null == spider) {
            return;
        }
        Map<String, Object> map = resultItems.getAll();
        if (CollectionUtil.isEmpty(map)) {
            return;
        }
        String title = String.valueOf(map.get("title"));
        ParserConfig jcParserConfig = new ParserConfig();
        jcParserConfig.putDeserializer(Date.class, SpiderDateDeserializer.instance);
        VirtualArticle virtualArticle = JSON.parseObject(JSON.toJSONString(map), VirtualArticle.class, jcParserConfig, JSON.DEFAULT_PARSER_FEATURE);
        virtualArticle.setDescription(CommonUtil.getRealDescription(virtualArticle.getDescription(), virtualArticle.getContent()))
                .setKeywords(CommonUtil.getRealKeywords(virtualArticle.getKeywords()));
        if (this.config.isConvertImg()) {
            virtualArticle.setContent(CommonUtil.formatHtml(virtualArticle.getContent()));
            virtualArticle.setImageLinks(CommonUtil.getAllImageLink(virtualArticle.getContent()));
        }
        if (CollectionUtils.isEmpty(virtualArticle.getTags())) {
            virtualArticle.setTags(Collections.singletonList("其他"));
        }
        virtualArticles.add(virtualArticle);
        writer.print(String.format("<a href=\"%s\" target=\"_blank\">%s</a> -- %s -- %s", virtualArticle.getSource(), title, virtualArticle.getAuthor(), virtualArticle.getReleaseDate()));
    }

    public SpiderConfig getConfig() {
        return config;
    }
}
