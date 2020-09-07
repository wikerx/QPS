package com.scott.wiker.encrypt;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Arrays;
import javax.crypto.SecretKey;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :AESUtil
 * @description :
 * @data :2020/7/8 0008 下午 6:37
 * @status : 编写
 **/

public class AESUtil {

    private static final String ALGORITHM = "AES";

    private static Key toKey(byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }

    public static String encrypt(String data, String key)
            throws GeneralSecurityException, UnsupportedEncodingException {
        Key k = toKey(Base64.decodeBase64(key));
        byte[] raw = k.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, secretKeySpec);
        byte[] bytes = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.encodeBase64String(bytes);
    }

    public static String decrypt(String data, String key)
            throws GeneralSecurityException, UnsupportedEncodingException {
        Key k = toKey(Base64.decodeBase64(key));
        byte[] raw = k.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secretKeySpec);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(data));
        return new String(bytes, "UTF-8");
    }

    public static String getSecrtKey(String encrypted) throws Exception {
        byte[] bytes = encrypted.getBytes("ISO8859-1");
        bytes = Arrays.copyOf(bytes, 16);
        return Base64.encodeBase64String(bytes);
    }

    /**
     * AES解密
     * */
    public static String decrypt(String data) throws Exception {
        String decryptData = "";
        String secrtKey = getSecrtKey(System.class.getName());
        decryptData = decrypt(data, secrtKey);
        return decryptData;
    }

    /**
     * AES加密
     * */
    public static String encrypt(String data) throws Exception {
        String secrtKey = getSecrtKey(System.class.getName());
        return encrypt(data, secrtKey);
    }

    public static void main(String[] args) throws Exception {
//        String data = "jdbc:oracle:thin:@103.118.49.107:31521:orcl";
//        String data = "think02";
        String data = "test";
        String encryptData = encrypt(data);
        System.out.println("加密后数据：" + encryptData);
        System.out.println("解密后数据：" + decrypt(encryptData));
    }
}
