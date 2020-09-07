package com.scott.wiker.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * <p>Title: </p>
 * <p>Description: 加密类</p>
 * <p>Copyright: Copyright (c) 2011 版权</p>
 * <p>Company: </p>
 * @author kevin
 * @version V1.0 
 * @date 2011-6-10下午02:26:39
 */
public class EncryptUtil {
	/**
	 * 
	 * @author: kevin
	 * @Title getEncrypt
	 * @Time: 2011-6-10下午02:26:59
	 * @Description: SHA256位加密
	 * @return: String 
	 * @throws: 
	 * @param strSrc
	 * @return
	 */
    public String getSHA256Encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try { 
            md = MessageDigest.getInstance("SHA-256");
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * 
     * @author: kevin
     * @Title getMD5Encrypt
     * @Time: 2011-6-10下午02:29:14
     * @Description: MD5加密
     * @return: String 
     * @throws: 
     * @param strSrc
     * @return
     */
    public String getMD5Encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try { 
            md = MessageDigest.getInstance("MD5");
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        }
        catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes.toUpperCase();
    }
    
    /**
     * 
     * @author: kevin
     * @Title bytes2Hex
     * @Time: 2011-6-10下午02:27:13
     * @Description: 
     * @return: String 
     * @throws: 
     * @param bts
     * @return
     */
    public String bytes2Hex(byte[]bts) {
        StringBuffer des = new StringBuffer();
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();
    }
    
    public static void main(String[] args) {




		System.out.println(new EncryptUtil().getMD5Encrypt("4023601569854746"));
		System.out.println(new EncryptUtil().getMD5Encrypt("5388097500005704"));
		System.out.println(new EncryptUtil().getMD5Encrypt("4509360640251548"));
		System.out.println(new EncryptUtil().getMD5Encrypt("4691350073216133"));
		
//		System.out.println(new EncryptUtil().getMD5Encrypt("ABCD"));
//		
//		System.out.println(new EncryptUtil().getSHA256Encrypt("abcd").length());
//		
//		System.out.println(new EncryptUtil().getSHA256Encrypt("201806230941265043510.0110859").toUpperCase());
	}
}
