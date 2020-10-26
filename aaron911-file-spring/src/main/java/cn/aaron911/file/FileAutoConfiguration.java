package cn.aaron911.file;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileAutoConfiguration {
	private static final Logger log = LoggerFactory.getLogger(FileAutoConfiguration.class);
	
	@PostConstruct
	public void init() {
		if (log.isDebugEnabled()) {
			log.debug("aaron911文件上传开始初始化");
		}
	}
	
    @Bean
    public BaseFileUploader baseFileUploader() {
		if (log.isDebugEnabled()) {
			log.debug("cn.aaron911.file 文件上传BaseFileUploader初始化");
		}
        return new BaseFileUploader();
    }
    
    @Bean
    public GlobalFileUploader globalFileUploader() {
		if (log.isDebugEnabled()) {
			log.debug("cn.aaron911.file 文件上传GlobalFileUploader初始化");
		}
        return new GlobalFileUploader();
    }

}
