package com.base.core.handler;

import com.base.core.utils.CodeUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author: yiming
 * @DATE: 2019/6/22
 * @Description: 图片统一处理器
 */
@Controller
@RequestMapping("image")
public class ImageHandle {

    Logger logger = LoggerFactory.getLogger(ImageHandle.class);

    /**
     * 通用上传 第一个"/"代表OSS根目录
     * @param file 上传的文件
     * @param path 上传到OSS后的全路径 /{业务类别}/{层级}../
     * @return "路径"
     */
    @RequestMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file, String path) {
        String ossFullName;
        try {
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());

            String fileName = CodeUtil.create() + "." + ext;
            ossFullName = FilenameUtils.concat(path, fileName);
            ossFullName = FilenameUtils.separatorsToUnix(ossFullName);
            //todo oss
        } catch (Exception e) {
            ossFullName = "404";
            e.printStackTrace();
            logger.error("local_upload_error={}", e.getMessage());
        }
        return ossFullName;
    }
}
