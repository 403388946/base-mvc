package com.base.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by YiMing on 2017-07-01.
 */
public class CodeUtil {
    public static String create() {
        String uuId = UUID.randomUUID().toString();
        return StringUtils.remove(uuId, "-");
    }

    /**
     * 生成随机数
     *
     * @param len
     *            随机数长度
     * @return 返回随机数
     */
    public static String randomNumber(int len) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(Math.abs(random.nextInt()) % 10);
        }
        return sb.toString();
    }

    /**
     * 将字符串转成32位加密字符串
     *
     * @param str
     *            明码
     * @return 密码
     */
    public static String md5(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }

        return md5StrBuff.toString();
    }

    public static void main(String[] args) {
        Integer a = 128;
        Integer d = 128;
        Integer b = new Integer(127);
        boolean c = (a == d);
        System.out.println(c);
    }
}
