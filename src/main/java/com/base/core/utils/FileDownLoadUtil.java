package com.base.core.utils;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author YiMing on 2017/05/17
 */
public class FileDownLoadUtil {

    public static void exportFile(String filePath, String fileName, HttpServletResponse response,
                                  HttpServletRequest request) {
        ServletOutputStream fos = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(filePath));
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;" + encodingFileName(fileName, request));
            fos = response.getOutputStream();
            int flag = 0;
            byte[] buffer = new byte[1024];
            while (flag != -1){
                flag = inputStream.read(buffer);
                //写到输出流(out)中
                if(flag > 0) {
                    fos.write(buffer,0,flag);
                }
            }
            inputStream.close();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String encodingFileName(String filename, HttpServletRequest request) throws UnsupportedEncodingException {

        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(userAgent)) {
            return "filename=" + URLEncoder.encode(filename, "UTF8");
        } else if (userAgent.indexOf("Trident") != -1) {
            return "filename=" + URLEncoder.encode(filename, "UTF8");
        } else if (userAgent.indexOf("MSIE") != -1) {
            return "filename=" + URLEncoder.encode(filename, "UTF8");
        } else if (userAgent.indexOf("Opera") != -1) {
            return "filename*=UTF-8''" + URLEncoder.encode(filename, "UTF8");
        } else {
            return "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
        }
    }
}
