package com.xmjz.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;

/**
 * AliossTemplate
 */
@AllArgsConstructor
public class AliossTemplate {
    private final OSS client;
    private final AliyunOssProperties config;

    public AliossTemplate(AliyunOssProperties config) {
        this.config = config;
        this.client = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKey(), config.getSecretKey());
    }

    public OSS getClient() {
        return client;
    }

    public AliyunOssProperties getConfig() {
        return config;
    }

    @SneakyThrows
    public void makeBucket(String bucketName) {
        if (!bucketExists(bucketName)) {
            client.createBucket(bucketName);
        }
    }

    @SneakyThrows
    public void removeBucket(String bucketName) {
        client.deleteBucket(bucketName);
    }

    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return client.doesBucketExist(bucketName);
    }

    @SneakyThrows
    public void copyFile(String bucketName, String fileName, String destBucketName) {
        client.copyObject(bucketName, fileName, destBucketName, fileName);
    }

    @SneakyThrows
    public void copyFile(String bucketName, String path, String destBucketName, String destPath) {
        client.copyObject(bucketName, path, destBucketName, destPath);
    }

    @SneakyThrows
    public PutObjectResult putFile(String path, InputStream stream) {
        return putFile(config.getBucketName(), path, stream);
    }

    @SneakyThrows
    public PutObjectResult putFile(String bucketName, String path, InputStream stream) {
        return put(bucketName, stream, path, false);
    }

    @SneakyThrows
    public PutObjectResult put(String bucketName, InputStream stream, String path, boolean cover) {
        PutObjectResult response;
        // 覆盖上传
        if (cover) {
            response = client.putObject(bucketName, path, stream);
        } else {
            response = client.putObject(bucketName, path, stream);
            int retry = 0;
            int retryCount = 5;
            //重试
            while (StringUtils.isEmpty(response.getETag()) && retry < retryCount) {
                response = client.putObject(bucketName, path, stream);
                retry++;
            }
        }
        return response;
    }

    @SneakyThrows
    public void removeFile(String fileName) {
        client.deleteObject(config.getBucketName(), fileName);
    }

    @SneakyThrows
    public void removeFile(String bucketName, String fileName) {
        client.deleteObject(bucketName, fileName);
    }

    @SneakyThrows
    public void removeFiles(List<String> fileNames) {
        fileNames.forEach(this::removeFile);
    }

    @SneakyThrows
    public void removeFiles(String bucketName, List<String> fileNames) {
        fileNames.forEach(fileName -> removeFile(bucketName, fileName));
    }

}
