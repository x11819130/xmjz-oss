//package com.xmjz.oss.minio;
//
//import com.platform.utils.BusiException;
//import com.platform.utils.FileUtil;
//import com.xmjz.oss.BaseCloudStorageService;
//import com.xmjz.oss.CloudStorageConfig;
//import io.minio.MinioClient;
//import io.minio.errors.InvalidEndpointException;
//import io.minio.errors.InvalidPortException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
///**
// * MinioStorageService
// *
// * @author chengz
// */
//public class MinioStorageService extends BaseCloudStorageService {
//    private static final Logger logger = LoggerFactory.getLogger(MinioStorageService.class);
//    private MinioClient client;
//
//    public MinioStorageService(CloudStorageConfig config) {
//        this.config = config;
//        //初始化
//        init();
//    }
//
//    private void init() {
//        try {
//            client = new MinioClient(config.getMinioEndpoint(), config.getMinioAccessKey(), config.getMinioSecretKey());
//        } catch (InvalidEndpointException | InvalidPortException e) {
//            logger.error("初始化 MinIO Client 出错", e);
//        }
//    }
//
//    @Override
//    public String upload(MultipartFile file) throws Exception {
//        String fileName = file.getOriginalFilename();
//        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
//        String path = getPath(config.getMinioPrefix()) + "." + prefix;
//        try {
//            client.putObject(config.getMinioBucketName(), path, file.getInputStream(), file.getContentType());
//        } catch (Exception e) {
//            logger.error("上传文件失败，请检查配置信息", e);
//            throw new BusiException("上传文件失败，请检查配置信息", e);
//        }
//        return config.getMinioDomain() + "/" + config.getMinioBucketName() + "/" + path;
//    }
//
//    @Override
//    public String upload(byte[] data, String path) {
//        return upload(new ByteArrayInputStream(data), path);
//    }
//
//    @Override
//    public String upload(InputStream inputStream, String path) {
//        try {
//            String contentType = FileUtil.getContentType(path);
//            client.putObject(config.getMinioBucketName(), path, inputStream, contentType);
//        } catch (Exception e) {
//            throw new BusiException("上传文件失败，请检查配置信息", e);
//        }
//        return config.getMinioDomain() + "/" + config.getMinioBucketName() + "/" + path;
//    }
//}
