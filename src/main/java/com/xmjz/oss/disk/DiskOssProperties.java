package com.xmjz.oss.disk;

import com.xmjz.oss.OssProperties;
import lombok.Data;

/**
 * Minio参数配置类
 *
 * @author Chill
 */
@Data
public class DiskOssProperties implements OssProperties {
    /**
     * 对象存储服务的URL
     */
    private String endpoint;

    /**
     * Access key就像用户ID，可以唯一标识你的账户
     */
    private String accessKey;

    /**
     * Secret key是你账户的密码
     */
    private String secretKey;

    /**
     * 默认的存储桶名称
     */
    private String bucketName;
}
