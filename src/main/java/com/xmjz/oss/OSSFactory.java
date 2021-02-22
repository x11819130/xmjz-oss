package com.xmjz.oss;

import com.xmjz.oss.aliyun.AliyunCloudStorageService;
import com.xmjz.oss.aliyun.AliyunOssProperties;

/**
 * 文件上传Factory
 *
 * @author chengz
 */
public final class OSSFactory {
    public static BaseCloudStorageService build(OssProperties config) {
        if (config instanceof AliyunOssProperties) {
            return new AliyunCloudStorageService((AliyunOssProperties) config);
        }
        return null;
    }
}
