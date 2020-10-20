package cn.aaron911.esclientrhl.auto.index;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.RequestOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import cn.aaron911.esclientrhl.annotation.ESMetaData;
import cn.aaron911.esclientrhl.auto.util.EnableESTools;
import cn.aaron911.esclientrhl.index.ElasticsearchIndex;
import cn.aaron911.esclientrhl.util.IndexTools;
import cn.aaron911.esclientrhl.util.MetaData;
import java.util.Map;


/**
 * 
 * description: 用于扫描ESMetaData注解的类，并自动创建索引mapping
 * 启动时调用，但如果需要让spring知道哪些bean配置了ESMetaData注解，需要ElasticProcessor
 * 
 **/
@Configuration
public class CreateIndex implements ApplicationListener, ApplicationContextAware {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
    private ElasticsearchIndex elasticsearchIndex;

    private ApplicationContext applicationContext;

    /**
     * 扫描ESMetaData注解的类，并自动创建索引mapping
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Map<String, Object> beansWithAnnotationMap = this.applicationContext.getBeansWithAnnotation(ESMetaData.class);
        beansWithAnnotationMap.forEach((beanName,bean) -> {
	        try {
	            MetaData metaData = IndexTools.getMetaData(bean.getClass());
	            if(metaData.isAlias()) {//当配置了别名后自动创建索引功能将失效
	                elasticsearchIndex.createAlias(bean.getClass());
	            }else if(!elasticsearchIndex.exists(bean.getClass())){
	                elasticsearchIndex.createIndex(bean.getClass());
	                if(EnableESTools.isPrintregmsg()) {
	                    logger.info("创建索引成功，索引名称："+metaData.getIndexname()+"索引类型："+metaData.getIndextype());
	                }
	            }
	        } catch (Exception e) {
	            logger.error("创建索引不成功",e);
	        }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
