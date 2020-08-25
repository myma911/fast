package cn.aaron911.minio.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 配置
 */
@Data
@ConfigurationProperties(prefix = "min.io")
public class MinIoProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
}