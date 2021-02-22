package com.xmjz.oss.aliyun;

import com.xmjz.oss.OssProperties;
import lombok.Data;

/**
 * aliyun OSS 配置
 *
 */
@Data
public class AliyunOssProperties implements OssProperties {
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
    /**
     * 目录前缀 最终文件url为:host + prePath + path
     * path来自调用上传方法入参 不可为null,可为空字符串
     * prePath与path都支持预定义变量 支持的变量有:
     * #{uuid}, #{year}, #{month}, #{day}, #{date}, #{hour}, #{minute}, #{second}, #{time}, #{millisecond}
     * #{randomLetters6}, #{randomLetters8}, #{randomNumbers6}, #{randomNumbers8}
     */
    private String prePath;
    /**
     * 访问服务器地址
     */
    private String host;
}
