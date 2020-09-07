package com.scott.wiker.encrypt;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.scott.wiker.utils.Ognl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Base64;

/**
 * 标题:  EncryMsgUtil
 * 公司： FTP/自贸通
 * 作者： Mr.薛
 * 时间： 2020-5-21上午10:42:53
 * 内容： 数据加密格式
 * 状态： 编写
 */
public class EncryptMsgUtil {
	private static final Logger logger = LoggerFactory.getLogger(EncryptMsgUtil.class);
//	加密方式
	private static final String SHA256 = "SHA-256";
	private static final String SHA512 = "SHA-512";
    public static final String RSA_ALGORITHM = "RSA";
    public static final String RSA_ALGORITHM_MD5 = "MD5withRSA";
	public static final String KEY_ALGORITHM = "AES";
	private static final String SIGN_ALGORITHMS  = "SHA1PRNG";
//	编码格式
	private static final String ENCODE_TYPE = "UTF-8";
//	RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;
//   RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;
//    Token偏移向量 - 不允许修改
    private static final String FINGER_PRINT_IV = "8FC11150A7508F14BACA07285703392A399CC57C";
    
    /**
	 * 密钥
	 * 测试使用，请勿直接使用
	 */
	private static String KEY = "x3/eqbraBj0pFyKLza21lq3nj7TP31Wz86DQZEKEszm9NRO/JxKmUTpwBOBslGF0xS3NtT2m3EHYieymCMbQQ/8j68SZNg/5DHH+pznp3P5f65lcIYsVh3mP5fjhPI6vaYthmG0QOd/bFjzxrSMvP8Fi=/vHezHNwiZ8siAcQF3yXgStuTE3tH9tV8IOLJPpRrjRXqNCAOCjVZtpJRun6OBb";
	
	
	/**
	 * 测试类
	 * */
	public static void main(String[] args) {
//		sha  加密
		String str = "1234567890qwesdf";
		String sha256_1 = getSHA256(str);
		String sha256_2 = encryptSHA(str,SHA256);
		String sha512_1 = encryptSHA(str,SHA512);
		logger.info("sha256_1：" + sha256_1);
		logger.info("sha256_2：" + sha256_2);
		logger.info("sha512_1：" + sha512_1);
		
//		rsa  加密解密
		try {
			    // 生成密钥对
			    KeyPair keyPair = getKeyPair();
			    String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
			    String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
			    logger.info("私钥:" + privateKey);
			    logger.info("公钥:" + publicKey);
			    // RSA加密
			    String data = "303037373138303482300100000000000000000C0000000030353231303333343033333839303337323030353231303333343033383031313130303030303336313538393131303030303033363732373003303037373138303482300100000000000000000C0000000030353231303333343033333839303337323030353231303333343033383031313130303030303336313538393131303030303033363732373003303037373138303482300100000000000000000C0000000030353231303333343033333839303337323030353231303333343033383031313130303030303336313538393131303030303033363732373003303037373138303482300100000000000000000C0000000030353231303333343033333839303337323030353231303333343033383031313130303030303336313538393131303030303033363732373003";
			    logger.info("原数据长度-加密前：" + data.length());
			    String encryptData = encrypt(data, getPublicKey(publicKey));
			    logger.info("加密后内容:" + encryptData);
			    // RSA解密
			    String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
			    logger.info("解密后内容:" + decryptData);
			    logger.info("原数据长度-解密后：" + decryptData.length());
			    
			    // RSA签名
			    String sign = sign(data, getPrivateKey(privateKey));
			    // RSA验签
			    boolean result = verify(data, getPublicKey(publicKey), sign);
			    logger.info("验签结果:" + result);
			    String privateKey1 = "a4d71a5101a02dfe44e70cad5fdc48c1d0873072ca18f249d92bc8e8bc610bdbb383b842d2d68d3e8fc199ac179bc81cc7696c5dfad9ce662cc9219ae937c433ad769df399d675e23cf5244fa8f3b20b166af5e386a370c8c30550928c928dfdfe5423ce7b72bebe8779c6474035a55f48d65c03ed9f24bb04e0fde1e9c8ce296c4b602fd032516412f5bac05c827a3cb9b9b98840d6b74d1fd4cdf4be91880280fb5fd38a3155819ddeebdfb33e7776dcd6d1fa1cca052b3615555d716602f9e61d47c33d68d5ae4363881b37a6313d85c748f583cac14169535de793cbc4608bed1cb076dea14898a71d02e909e0845d515f0c7afef89e4ba8f75526af67a7";
//			    	"MIIEpAIBAAKCAQEAuSYI8rH58Th/s4yeABaKdYx2D9eb0knliNmG4HiC8C7OQ3HG" +
//                "IqWL9dnlq8sdLbYAnj7QAYQjDwF07Ru7YVPfRYYendWS9Wnpc8Ge///cKvVIQ97+" +
//                "c50DYCIuSMXZCFN3zMTJdyCtAYOPOs1LhGWJlYBxjeVKIcsNyxSHNcY8OZsWvGEg" +
//                "JDq/8F7h47ateDWmcxQdDQw7j0wAo4FhEd9eRRq7cQAt4Q7BzvkG5keuFHOvONrd" +
//                "wD3feGNDu2h7XYL8YfkF17zVb3i4gclMvTT0QsoVJGbmpSJBZnBezk/TuNmxrmJX" +
//                "DL3mkjEOU583bgaFw1sKMuptgFEIpgqDd0YWiQIDAQABAoIBABu5vGFLkZgz07u5" +
//                "dPeiaH2N2SgqK2VkN4E0wePfaAw7lhu3b+pETB820BPXyLjNpm4MDe4MMYvPtgjD" +
//                "vF2ox6iLmw4bq3Qgthrlhb/kOr4cGQRA4m3Bt32v4Kp5JjT++tOpLFowhHfTkaFG" +
//                "mOZsphvDLj4HdgB1fc1Jd63vz5j4hOzV3JZKi8DOOo4vAfXuIv2blMiTriOMOJzi" +
//                "a2R40zeWyIPTpMFeUzgIu+ax1TUPKtR1mGGuNwfjUB7+f4nbU4zRC4lUv6679fUm" +
//                "HLXtV/5he8LCkuZ5tENedR9RzpgilOq/4ErS8KRmVfc/qQKqOY0upROZrtVtVncv" +
//                "9ELQNeECgYEA8laLgqA9wNvgpPC4d5bjlPgaXyS2nP4TNJsNvsdpjeZtdDHODwVN" +
//                "+XG+6cKzdf1aXK3l7I/GT4MBVBDNVjpCV8dtqXwOtRYmcTAzpeVDXpooPQ/wk8k+" +
//                "hxpyFWJSTbyd5bOREdEJnHQgY9P4DKfP12opZKYToBmDK6Ai2gPRkSUCgYEAw5Yf" +
//                "qP0z4OPsSDUNVgt4wTJkQ3Wm4dXDq5s7cHHlNK7CJMxqtdAknxaJkiwJmuCs6lDu" +
//                "qOZr9kMOJn1OVh3N4jaSB4Twg0cE1veJZTZRhymcaiaOfMbMBep31Lj4NEm6YwfJ" +
//                "haU2d/C0GzKOEP5ebeGdRHRnTY4i2gkg6D59bJUCgYAqBlIZK/qpiDfTwp7qtjLv" +
//                "MXs5Rp+YaAMim0Tt8Zzfa7dNcBmgxzW3bT9DQG1op6/U5J87v88PGNPRJTzGmEHL" +
//                "AR2GR6oxQw8Vj9tVCIX/UNJrjGkW5849FZv1E2DcdDoZjGM66Lo29Hhtd2PLpgpH" +
//                "+/QDrT04BVSW6yVAE2tzNQKBgQCoK0ahqMSYk6EwHN2QpGB5zXhEQL7y4zhRLzIW" +
//                "ZvfrKnAn79O47HTwjRJQqqi/kHtdJaVveGBP6CwckBVxGkNSRBWffYDHf6L7qu9q" +
//                "JSBIlVC2PNy7ELuQQqAyObDAVLx8Nc/ip8GDs2VJFVjt56kN1bXBjEzedF4nFz5C" +
//                "MqjpWQKBgQCzEVShLu50oKlTREU8Cw5BLDH76/xZRo89Wgx0rSx2U7zIpjI4CV5v" +
//                "MqRQuHxgl6yDzgkSNHc6kOD+J/WCj4b70FdlPKuvd62QkIJt+tTpmIzPSAqn1SO5" +
//                "QCJP/RnQQ5dH9lkVNNht5oue7JEvDDnhRaTmXzqZPLh8C4/6ts4OiA==";
			    data = "{" +
		        "  \"cardAccountData\": {" +
		        "    \"accountNumber\": \"5123456789012345\"," +
		        "    \"expiryMonth\": \"09\"," +
		        "    \"expiryYear\": \"21\"," +
		        "    \"securityCode\": \"123\"" +
		        "  }," +
		        "  \"accountHolderData\": {" +
		        "    \"accountHolderName\": \"John Doe\"," +
		        "    \"accountHolderAddress\": {" +
		        "      \"line1\": \"100 1st Street\"," +
		        "      \"line2\": \"Apt. 4B\"," +
		        "      \"city\": \"St. Louis\"," +
		        "      \"countrySubdivision\": \"MO\"," +
		        "      \"postalCode\": \"61000\"," +
		        "      \"country\": \"USA\"" +
		        "    }," +
		        "    \"consumerIdentifier\": \"string\"," +
		        "    \"accountHolderEmailAddress\": \"string\"," +
		        "    \"accountHolderMobilePhoneNumber\": {" +
		        "      \"countryDialInCode\": \"0044\"," +
		        "      \"phoneNumber\": \"07777 777 777\"" +
		        "    }" +
		        "  }," +
		        "  \"source\": \"ACCOUNT_ON_FILE\"" +
		        "}";
			    String encryDataEnd = "522c5c20a5386b225a703702f6871d01bf1d85d80b1b805ff32aef9519cc287f64e09844e259fcb4303a692cbe7dd15dc9a04be019209c9a95bbc5a7ac7ed30c8dbeba87b0dceaef543cf11afd9720e91c629666eb877ccd18f0c9e39d97a30672bf6714a595fe1fb2813edff5afb024b28ebf52eaf5dc71761f3d0acb50c1ba74837425e482c2acf3e7e2e1c14a8dd844e3cb5993f1a3ae81bdc2ec29bd13e05500f71f6aa621c34d8fc11ad8ab08b5d5e97d8d29d57e78e61a8245c3211aab86647a527d5c6cd0baef91eb534ce700ab14ce776ffb08b36faa61f620de0a8b0c50ac3ee38a51a88949806df0319c9df850616e715ebd60855b43151cf1fe3107c032da5dffbfdfb9c504e70766eaf34481cec68dfaa1178ad7ec25bfe1df77f787ee9951f045f504964bb8decc4cdaa74ed9f3dd6019a8efbdf9fa1b7cb9d8c776c8684e2a9c2f3046e97008397b134f185fea39c63b32bbd43798fbeb592e14eb0ddb05aba22479eb31ec9b6776d604c79cd6bf282b22a0e50341766e1b2a3bb9d0b04fa72f60a8f65fce998c3e13e57c4b6312c48dc793a1ef0e6d1a436a43216c543da1e52551037129b4f810c22b3eec06a398424ab793ba7ee8cde3c8f8c7b1f1d02a428841b686502c4e2a859d3add01c0ea6668bc9e13448cb871a0474eb3e38b041bc41075443c00e57693fe65852b620782b4c7b7d77d05ffc2da70f95f8871d9b509d62abe5bc7da3bcb30d29ea9c7d9422f2b53a9371c656174c7fd31ec805ecec4f9caed13edf024bd4c01a41af4b18e7be4672e2ad11573f926556c5701593272acd00fb2aae8d58be4083e4d49a9e4c4450a379deedc5a711c18e751ce3e0a4ab9011398129703210d1eaba6f3cc9ffa18b870a26aa55cfb474f79d20ef4678075fa342fdc66786a";
			    logger.info("加密后内容-2:" + encryDataEnd);
			    privateKey = "a4d71a5101a02dfe44e70cad5fdc48c1d0873072ca18f249d92bc8e8bc610bdbb383b842d2d68d3e8fc199ac179bc81cc7696c5dfad9ce662cc9219ae937c433ad769df399d675e23cf5244fa8f3b20b166af5e386a370c8c30550928c928dfdfe5423ce7b72bebe8779c6474035a55f48d65c03ed9f24bb04e0fde1e9c8ce296c4b602fd032516412f5bac05c827a3cb9b9b98840d6b74d1fd4cdf4be91880280fb5fd38a3155819ddeebdfb33e7776dcd6d1fa1cca052b3615555d716602f9e61d47c33d68d5ae4363881b37a6313d85c748f583cac14169535de793cbc4608bed1cb076dea14898a71d02e909e0845d515f0c7afef89e4ba8f75526af67a7";
			    logger.info("sha256加密结果：" + getSHA256("123456sdf"));
			    logger.info("sha256加密结果：" + new EncryptUtil().getSHA256Encrypt("123456sdf"));
				logger.info("AES解密结果：" + AESDncode(encryDataEnd, privateKey));
			    logger.info("AES加密数据：" + AESEncode(data, privateKey));
			    logger.info("Token：" + buildToken("1002^5123456789012345"));
			    logger.info("Token验证结果：" + verifToken("1002^5123456789012345", "df2fe2c2fbb96d14c6c7a1d4ef7b2318328df0a2dceed2fdce7afacbe503f60f"));
			} catch (Exception e) {
			    e.printStackTrace();
			    System.out.print("数据加解-解密密异常");
			}
		
		
	}
	
	/**
     * 利用java原生的类实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256(String str){
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance(SHA256);
            messageDigest.update(str.getBytes(ENCODE_TYPE));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }
    
    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    
    /**
     * 字符串 SHA 加密
     * @param encryptMsg   字符数据
     * @param encryptSHAType   加密格式支持256和512方式
     * @return  加密结果
     */
    public static String encryptSHA(String encryptMsg, String encryptSHAType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (encryptMsg != null && encryptMsg.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并传入加密类型
                MessageDigest messageDigest = MessageDigest
                        .getInstance(encryptSHAType);
                // 传入要加密的字符串
                messageDigest.update(encryptMsg.getBytes());
                // 得到 byte 类型结果
                byte byteBuffer[] = messageDigest.digest();

                // 将 byte 转换为 string
                StringBuffer strHexString = new StringBuffer();
                // 遍历 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
				logger.error("-01-字符串 SHA 加密异常-"+e.getMessage());
                e.printStackTrace();
            }
        }

        return strResult;
    }
    
   
    /**
     * 获取密钥对
     * 
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        generator.initialize(1024);
        return generator.generateKeyPair();
    }
   
    /**
     * 获取私钥
     * 
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }
   
    /**
     * 获取公钥
     * 
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }
    
    /**
     * RSA加密
     * 
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }
   
     /**
      * RSA解密
      * 
      * @param data 待解密数据
      * @param privateKey 私钥
      * @return
      */
     public static String decrypt(String data, PrivateKey privateKey) throws Exception {
         Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
         cipher.init(Cipher.DECRYPT_MODE, privateKey);
         byte[] dataBytes = Base64.decodeBase64(data);
         int inputLen = dataBytes.length;
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         int offset = 0;
         byte[] cache;
         int i = 0;
         // 对数据分段解密
         while (inputLen - offset > 0) {
             if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                 cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
             } else {
                 cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
             }
             out.write(cache, 0, cache.length);
             i++;
             offset = i * MAX_DECRYPT_BLOCK;
         }
         byte[] decryptedData = out.toByteArray();
         out.close();
         // 解密后的内容 
         return new String(decryptedData, ENCODE_TYPE);
     }
   
    /**
     * 签名
     * 
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(RSA_ALGORITHM_MD5);
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }
   
    /**
     * 验签
     * 
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(RSA_ALGORITHM_MD5);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }
    
    
	/*
	 * 加密
	 */
	public static String AESEncode(String content) {
		return AESEncode(content,KEY);
	}
	/*
	 * 加密
	 */
	public static String AESEncode(String content,String pkey) {
		if(pkey == null) {
			pkey = KEY;
		}
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
			//故调整为如下方式
			SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
			random.setSeed(pkey.getBytes(ENCODE_TYPE));
			keygen.init(128, random);
			SecretKey original_key = keygen.generateKey();
			byte[] raw = original_key.getEncoded();
			SecretKey key = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] byte_encode = content.getBytes(ENCODE_TYPE);
			byte[] byte_AES = cipher.doFinal(byte_encode);
			String AES_encode = new String(Base64.encodeBase64(byte_AES));
			return AES_encode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/*
	 * 解密 
	 */
	public static String AESDncode(String content) {
		return AESDncode(content,KEY);
	}

	public static String AESDncode(String content,String pkey) {
		if(pkey == null) {
			pkey = KEY;
		}
		try {
			KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
			//故调整为如下方式
			SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
			random.setSeed(pkey.getBytes(ENCODE_TYPE));
			keygen.init(128, random);
			SecretKey original_key = keygen.generateKey();
			byte[] raw = original_key.getEncoded();
			SecretKey key = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] byte_content = Base64.decodeBase64(content);
			byte[] byte_decode = cipher.doFinal(byte_content);
			String AES_decode = new String(byte_decode, ENCODE_TYPE);
			return AES_decode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过SHA256模拟生成伪装Token
	 * */
	public static String buildToken(String msg){
		String token = "";
		if(Ognl.isEmpty(msg)){
			msg = String.valueOf(System.currentTimeMillis()) + 
			FINGER_PRINT_IV;
		}
		
		token = encryptSHA(msg,SHA256);
		
		return token;
	}
	
	/**
	 * token验证 - 伪装型，不包含过期设置
	 * */
	public static boolean verifToken(String msg,String token){
		String verifToken = "";
//		默认验证失败
		boolean flag = false;
		if(Ognl.isEmpty(msg)){
			msg = String.valueOf(System.currentTimeMillis()) + 
			FINGER_PRINT_IV;
		}
		
		verifToken = encryptSHA(msg,SHA256);
		
		if(Ognl.isNotEmpty(verifToken) && 
				verifToken.equalsIgnoreCase(token)){
			flag = true;
		}
		
		return flag;
	}
	
    
}
