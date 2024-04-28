package com.base.core.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * @author: yiming
 * @DATE: 2020/5/31
 * @Description: 二维码工具
 */
public class QRCodeUtils {

    /**
     * 二维码内容
     * @param content 内容
     * @param logoUrl logo地址
     * @param qrCodePath 阿里云上传路径
     * @return
     * @throws Exception
     */
    public static String create(String content, String logoUrl, String qrCodePath) throws Exception {
        final QrConfig qrConfig = QrConfig.create();
        if(StringUtils.isNotEmpty(logoUrl)) {
            final URL logoURL = new URL(logoUrl);
            final BufferedImage read = ImageIO.read(logoURL);
            //LOGO越大值越小
            qrConfig.setRatio(5);
            qrConfig.setImg(read);
        }
        final BufferedImage generate = QrCodeUtil.generate(content, qrConfig);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(generate, "png", os);
        InputStream input = new ByteArrayInputStream(os.toByteArray());
        OssUtil.INSTANCE.upLoadFile(qrCodePath, input);
        input.close();
        os.close();
        return new StringBuilder(OssUtil.CDN_HOST).append("/").append(qrCodePath).toString();
    }
}
