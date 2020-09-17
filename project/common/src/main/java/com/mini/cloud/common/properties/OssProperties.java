package com.mini.cloud.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author twang
 */
@Data
@ConfigurationProperties(prefix = "oss")
@Component
public class OssProperties {

    /**
     * 访问域名
     */
    private String endpoint;
    /**
     * 身份验证id
     */
    private String accessKeyId;
    /**
     * 身份验证Secret
     */
    private String accessKeySecret;
    /**
     * 存储空间
     */
    private String bucketName;

}
