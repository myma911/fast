package cn.aaron911.file;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.aaron911.file.property.FileProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0.0
 * 
 */
@Configuration
@EnableConfigurationProperties(FileProperties.class)
@Slf4j
public class FileAutoConfiguration {
	
	@PostConstruct
	public void init() {
		log.debug("cn.aaron911.file 文件上传开始初始化");
	}
	
    @Bean
    BaseFileUploader baseFileUploader() {
    	log.debug("cn.aaron911.file 文件上传BaseFileUploader初始化");
        return new BaseFileUploader();
    }
    
    @Bean
    GlobalFileUploader globalFileUploader() {
    	log.debug("cn.aaron911.file 文件上传GlobalFileUploader初始化");
        return new GlobalFileUploader();
    }

}
