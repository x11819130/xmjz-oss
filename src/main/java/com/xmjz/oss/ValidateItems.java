package com.xmjz.oss;

import com.xmjz.oss.support.Objects;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 上传文件校验项
 *
 * @author chengz
 * swagger接口参考:
 *     //@ApiImplicitParams({
 *     //        @ApiImplicitParam(name = "accept", value = "允许的文件类型", paramType = "form", dataType = "string"),
 *     //        @ApiImplicitParam(name = "maxSize", value = "允许的最大文件大小", paramType = "form", dataType = "int"),
 *     //        @ApiImplicitParam(name = "minSize", value = "允许的最小文件大小", paramType = "form", dataType = "int"),
 *     //        @ApiImplicitParam(name = "scale", value = "允许的图片比例(宽:高)", paramType = "form", dataType = "string"),
 *     //        @ApiImplicitParam(name = "maxHeight", value = "允许的最大高度", paramType = "form", dataType = "int"),
 *     //        @ApiImplicitParam(name = "maxWidth", value = "允许的最大宽度", paramType = "form", dataType = "int"),
 *     //        @ApiImplicitParam(name = "minHeight", value = "允许的最小高度", paramType = "form", dataType = "int"),
 *     //        @ApiImplicitParam(name = "minWidth", value = "允许的最小宽度", paramType = "form", dataType = "int"),
 *     //        @ApiImplicitParam(name = "height", value = "允许的高度", paramType = "form", dataType = "int"),
 *     //        @ApiImplicitParam(name = "width", value = "允许的宽度", paramType = "form", dataType = "int"),
 *     //})
 */
@Data
public class ValidateItems {
    /**
     * 允许的文件类型 content-type格式 支持多个与通配符
     * 例如:
     * image/jpeg
     * image/png;text/xml
     * image/*;text/xml
     * 注意: jpg文件的content-type是[image/jpeg] 而不是image/jpg
     * content-type详见:https://www.runoob.com/http/http-content-type.html
     */
    private String accept;
    /**
     * 允许的最大文件大小 单位KB
     */
    private Integer maxSize;
    /**
     * 允许的最小文件大小 单位KB
     */
    private Integer minSize;
    /**
     * 允许的图片比例(宽:高)
     * 例如:
     * 1:1
     * 3:4;16:9
     */
    private String scale;
    /**
     * 允许的最大高度
     */
    private Integer maxHeight;
    /**
     * 允许的最大宽度
     */
    private Integer maxWidth;
    /**
     * 允许的最小高度
     */
    private Integer minHeight;
    /**
     * 允许的最小宽度
     */
    private Integer minWidth;
    /**
     * 允许的高度
     */
    private Integer height;
    /**
     * 允许的宽度
     */
    private Integer width;

    public void validate(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }
        //校验文件类型
        if (accept != null) {
            boolean flag = false;
            String type = file.getContentType();
            if (type != null) {
                for (String contentType : accept.split(";")) {
                    if (contentType.endsWith("*")) {
                        if (type.startsWith(contentType.substring(0, contentType.length() - 1))) {
                            flag = true;
                            break;
                        }
                    } else if (type.equals(contentType)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                throw new RuntimeException("请上传[" + accept + "]文件");
            }
        }
        //校验文件大小
        if (maxSize != null) {
            if (file.getSize() / 1024 > maxSize) {
                throw new RuntimeException("请上传" + maxSize + "KB以内的文件");
            }
        }
        if (minSize != null) {
            if (file.getSize() / 1024 < minSize) {
                throw new RuntimeException("请上传" + minSize + "KB以上的文件");
            }
        }

        //校验图片像素比例
        if (Objects.isExistNotNull(scale, minHeight, maxHeight, minWidth, maxWidth, height, width)) {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new RuntimeException("不是有效图片");
            }
            if (minHeight != null) {
                if (image.getHeight() < minHeight) {
                    throw new RuntimeException("请上传高度在" + minHeight + "像素以上的图片");
                }
            }
            if (maxHeight != null) {
                if (image.getHeight() > maxHeight) {
                    throw new RuntimeException("请上传高度在" + maxHeight + "像素以下的图片");
                }
            }
            if (minWidth != null) {
                if (image.getWidth() < minWidth) {
                    throw new RuntimeException("请上传宽度在" + minWidth + "像素以上的图片");
                }
            }
            if (maxWidth != null) {
                if (image.getWidth() > maxWidth) {
                    throw new RuntimeException("请上传宽度在" + maxWidth + "像素以下的图片");
                }
            }
            if (width != null) {
                if (image.getWidth() != width) {
                    throw new RuntimeException("请上传宽度" + width + "像素的图片");
                }
            }
            if (height != null) {
                if (image.getHeight() != height) {
                    throw new RuntimeException("请上传高度" + height + "像素的图片");
                }
            }
            if (scale != null) {
                boolean flag = false;
                for (String split : scale.split(";")) {
                    String[] wh = split.split(":");
                    int width0 = Integer.parseInt(wh[0]);
                    int height0 = Integer.parseInt(wh[1]);
                    if (1d * image.getWidth() / width0 == 1d * image.getHeight() / height0) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    throw new RuntimeException("请上传宽高比符合比例[" + scale + "]的图片");
                }
            }
        }
    }
}
