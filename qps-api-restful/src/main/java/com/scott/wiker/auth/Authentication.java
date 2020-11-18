package com.scott.wiker.auth;

import com.alibaba.fastjson.JSON;
import com.scott.wiker.base.ErrorMsgInfo;
import com.scott.wiker.base.ResponseHeaderEntity;
import com.scott.wiker.base.TransMsgEntity;
import com.scott.wiker.encrypt.AESUtil;
import com.scott.wiker.utils.MsgContent;
import com.scott.wiker.utils.Ognl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * @ClassName :Authentication
 * @Description : 请求类鉴权操作的具体实现
 * @Author :Mr.薛
 * @Data :2019/12/16 0016 下午 1:00
 * @Version :V1.0
 * @Status : 编写
 **/
@Component
public class Authentication {
    private static Log log = LogFactory.getLog(Authentication.class);
    private static final String secretKeyOfWxh = "e10adc3949ba59abbe56e057f20f883f";
    private static final String appidOfWxh = "xxx";
//    @Autowired
//    private MerchantInfoService merchantInfoService;
//    private static Authentication auth;
//
//    public void setServiceMer(MerchantInfoService merchantInfoService) {
//        this.merchantInfoService = merchantInfoService;
//    }
//
//    @PostConstruct
//    public void init() {
//        auth = this;
//        auth.merchantInfoService = this.merchantInfoService;
//    }

    /**
     * 接口鉴权 - Header鉴权
     * 处理request域中的头部数据
     * true:通过  false：拦截
     * */
    public Boolean authentication(HttpServletRequest request, HttpServletResponse response)throws Exception{
        if(Ognl.isEmpty(request.getHeader(MsgContent.TIMESTAMP)) ||
                Ognl.isEmpty(request.getHeader(MsgContent.MID)) ||
                Ognl.isEmpty(request.getHeader(MsgContent.AUTHENTICATION))){
            log.error("[No data in header]Authentication failure...");
            this.authReturnMsg(response,3);
            return false;
        }

//        考虑使用header
        Long timestamp = Long.parseLong(
                request.getHeader(
                        MsgContent.TIMESTAMP
                ).trim());
        String mid = request.getHeader(MsgContent.MID).trim();
        String authentication = request.getHeader(
                MsgContent.AUTHENTICATION).trim();
//        获取商户号的秘钥 - 缓存
        String merchantScrect = "";
        if(Ognl.isEmpty(mid)){
            log.error("[Mid]: " + mid + "  [Timestamp]: " + timestamp + "  Authentication failure...");
            this.authReturnMsg(response,4);
            return false;
        }else{
//            查询商户秘钥 - 缓存
//            merchantScrect = auth.merchantInfoService .getMerchantScrectKeyByMerNo(mid);
//            if(Ognl.isEmpty(merchantScrect)){
//                log.error("[Mid]: " + mid + "  [Timestamp]: " + timestamp + "  Authentication failure...");
//                authReturnMsg(response,
//                        MsgContent.AUTH_REQUEST_MERCHANT_INFOMATION_ERROR);
//                return false;
//            }
        }
        // 当前时间的时间戳 前推1分钟
        Long currTimestamp = System.currentTimeMillis()
                -
                MsgContent.AUTHORIZATION_TIME_LIMIT;
        // 在一分钟范围之外，访问已过期
        if (currTimestamp > timestamp) {
            log.error("Visit expired...[Mid]: " + mid + "  [Timestamp]: " + timestamp + "  Authentication failure...");
            authReturnMsg(response,5);
            return false;
        }
        String checkAuth = MsgContent.BASIC + AESUtil.encrypt(mid + timestamp,
                merchantScrect);
        if(Ognl.isEmpty(timestamp) || !authentication.equalsIgnoreCase(checkAuth) || Ognl.isEmpty(authentication)){
            log.error("[Mid]: " + mid + "  [Timestamp]: " + timestamp + "  Authentication failure...");
            authReturnMsg(response,7);
            return false;
        }else{
            return true;
        }

    }



        /**
         * 外部接口签名验证
         * @param request
         * @return
         */
    public static Boolean checkSign(HttpServletRequest request){
        Boolean flag= false;
        //appid
        String appid = request.getParameter(MsgContent.APPID);
        if(!appid.equals(appidOfWxh)){
            log.info("appid错误");
        }
//        签名
        String sign = request.getParameter("sign");
//        时间戳
        String timestamp = request.getParameter("timestamp");
        //check时间戳的值是否在当前时间戳前后一小时以内  当前时间的时间戳
        String currTimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        int currTimestampNum = Integer.parseInt(currTimestamp);
//        时间戳的数值
        int verifyTimestampNum = Integer.parseInt(timestamp);
        // 在一小时范围之外，访问已过期
        if (Math.abs(verifyTimestampNum - currTimestampNum) > 600) {
            log.info("sigin已经过期");
        }
        //检查sigin是否过期
        Enumeration<?> pNames =  request.getParameterNames();
        Map<String, String> params = new HashMap<String, String>(10);
        while (pNames.hasMoreElements()) {
            String pName = (String) pNames.nextElement();
            if("sign".equals(pName)) {
                continue;
            }
            String pValue = (String)request.getParameter(pName);
            params.put(pName, pValue);
        }
        if(sign.equals(getSign(params, secretKeyOfWxh))){
            flag = true;
        }
        return flag;
    }

    /**
     *
     * @param value
     * @param sourceCharsetName
     * @return
     */
    public String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), MsgContent.ENCODING_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /**
     *
     * @param data
     * @return
     * @throws IOException
     */
    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance(MsgContent.ENCODING_MD5);
            bytes = md.digest(data.getBytes(MsgContent.ENCODING_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    /**
     * 2进制转字符串
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }


    /**
     * 得到签名
     * @param params 参数集合不含secretkey
     * @param secret 验证接口的secretkey
     * @return
     */
    public static String getSign(Map<String, String> params, String secret)
    {
        String sign="";
        StringBuilder sb = new StringBuilder();
        //step1：先对请求参数排序
        Set<String> keyset=params.keySet();
        TreeSet<String> sortSet=new TreeSet<String>();
        sortSet.addAll(keyset);
        Iterator<String> it=sortSet.iterator();
        //step2：把参数的key value链接起来 secretkey放在最后面，得到要加密的字符串
        while(it.hasNext())
        {
            String key=it.next();
            String value=params.get(key);
            sb.append(key).append(value);
        }
        sb.append(secret);
        byte[] md5Digest;
        try {
            //得到Md5加密得到sign
            md5Digest = getMD5Digest(sb.toString());
            sign = byte2hex(md5Digest);
        } catch (IOException e) {
            log.error("生成签名错误",e);
        }
        return sign;
    }


    /**
     * 返回客户端数据
     * @param response
     * @param json
     * @throws Exception
     */
    private void authReturnMsg(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding(MsgContent.ENCODING_UTF8);
        response.setContentType(MsgContent.CONTENY_TYPE_JSON);
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 返回数据
     * @param response
     * @param code
     * @throws Exception
     */
    void authReturnMsg(HttpServletResponse response, int code) throws Exception{
        PrintWriter writer = null;
        ResponseHeaderEntity head = new ResponseHeaderEntity();
        TransMsgEntity transMsgEntity = new TransMsgEntity();
        ErrorMsgInfo info = new ErrorMsgInfo().getErrorInfo(2);
        head.setRespCode(info.getRespCode());
        head.setRespDesc(info.getRespDescUs());

        transMsgEntity.setHead(head);

        String json = JSON.toJSONString(transMsgEntity);
        response.setCharacterEncoding(MsgContent.ENCODING_UTF8);
        response.setContentType(MsgContent.CONTENY_TYPE_JSON);
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
