package com.base.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 判断手机号是否正确
 */
public class PhoneUtils {


    /**
    *大陆号码或香港号码均可
    */
    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
    *大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
    *此方法中前三位格式有：
    *13+任意数
    *15+除4的任意数
    *18+除1和4的任意数
    *17+除9的任意数
    *147
    */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp= "^((13[0-9])|(15[^4])|(18[0-9])|(16[0-9])|(17[0-8])|(19[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String encryption(String phone, Long shopId) {
        if(!StringUtils.isNumeric(phone)){
            phone = "13900000000";
        }
        BigDecimal phoneNum = new BigDecimal(phone).multiply(new BigDecimal(shopId));
        return phoneNum.toPlainString();

    }


    public static  String decryption(String phone, Long shopId){
        if(!StringUtils.isNumeric(phone)){
            phone = "13900000000";
        }
        if(phone.length() <= 11){
            return phone;
        }
        BigDecimal phoneNum = new BigDecimal(phone).divide(new BigDecimal(shopId));
        return phoneNum.toPlainString();
    }

    /**
     * 获取手机号
     *
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static String getPhoneInWeChat(String encryptedData, String sessionKey, String iv) throws Exception {
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        byte[] keyByte = Base64.decodeBase64(sessionKey);
        byte[] ivByte = Base64.decodeBase64(iv);
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }

        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            String result = new String(resultByte, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject.getString("phoneNumber");
        }
        throw new Exception("未获取到手机号");
    }
}
