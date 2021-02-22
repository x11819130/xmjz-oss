package com.xmjz.oss;

import com.xmjz.oss.aliyun.AliyunOssProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 云存储(支持七牛、阿里云、腾讯云、又拍云、MinIO)
 *
 * @author chengz
 */
public abstract class BaseCloudStorageService {
    /**
     * 云存储配置信息
     */
    AliyunOssProperties config;

    /**
     * 文件上传
     *
     * @param file 文件
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    public String upload(MultipartFile file, String path) throws Exception{
        return upload(file.getBytes(), path);
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);
}
