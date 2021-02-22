package com.xmjz.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xmjz.oss.BaseCloudStorageService;
import com.xmjz.oss.support.OssUtil;
import lombok.AllArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 *
 * @author chengz
 */
@AllArgsConstructor
public class AliyunCloudStorageService extends BaseCloudStorageService {
    private final AliyunOssProperties config;
    private final OSS client;

    public AliyunCloudStorageService(AliyunOssProperties config) {
        this.config = config;
        this.client = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKey(), config.getSecretKey());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            //路径占位符替换
            path = OssUtil.parsePath(config.getPrePath() + path);
            //上传文件
            client.putObject(config.getBucketName(), path, inputStream);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败，请检查配置信息", e);
        }
        return config.getHost() + path;
    }
}
