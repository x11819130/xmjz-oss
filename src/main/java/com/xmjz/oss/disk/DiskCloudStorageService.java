package com.xmjz.oss.disk;

import com.xmjz.oss.BaseCloudStorageService;

import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务器本地存储
 *
 * @author chengz
 */
public class DiskCloudStorageService extends BaseCloudStorageService {

    private final DiskOssProperties config;

    public DiskCloudStorageService(DiskOssProperties config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        if (data.length < 3 || "".equals(path)) {
            throw new RuntimeException("上传文件为空");
        }

        //本地存储必需要以"/"开头
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        try {
            //创建上传目录
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
                if (!mkdirs) {
                    throw new RuntimeException("创建上传目录失败");
                }
            }
            //创建上传文件
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    throw new RuntimeException("创建上传文件失败");
                }
            }
            //写文件
            FileImageOutputStream imageOutput = new FileImageOutputStream(file);
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败", e);
        }
        return config.getEndpoint() + path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] data = output.toByteArray();
            output.close();
            inputStream.close();
            return this.upload(data, path);
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败", e);
        }
    }
}
