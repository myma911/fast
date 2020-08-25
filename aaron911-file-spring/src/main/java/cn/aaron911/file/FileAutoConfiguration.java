package cn.aaron911.file;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.aaron911.file.property.FileProperties;

/**
 * @version 1.0.0
 * 
 */
@Configuration
@EnableConfigurationProperties(FileProperties.class)
public class FileAutoConfiguration {
	
    @Bean
    BaseFileUploader baseFileUploader() {
        return new BaseFileUploader();
    }
    
    @Bean
    GlobalFileUploader globalFileUploader() {
        return new GlobalFileUploader();
    }

}
