package com.hrt.biz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.util.mpos.RSAUtil;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.biz.util.mpos.AESpkcs7paddingUtil;
import com.hrt.biz.util.mpos.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;

/**
 * CommonTools
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/11/14
 * @since 1.8
 **/
public class CommonTools {

    /**
     * 解密秘钥
     **/
    private static final String KEY = "wQ2Yi4gzYfHjBZ8O";

    private static final Log log = LogFactory.getLog(CommonTools.class);

    public synchronized static String getSck(String aesEn) {
        return RSAUtil.subMild16GetAesKey(aesEn);
    }

    public synchronized static String parseAesEnAndData(String sck, String data) {
        String result = "";
        try {
//            String sck = com.hrt.biz.util.mpos.RSAUtil.subMild16GetAesKey(aesEn);
            byte[] decrypt = AESpkcs7paddingUtil.decrypt(Base64.decode(data), sck.getBytes("UTF-8"), KEY.getBytes("UTF-8"));
            result = new String(decrypt, "UTF-8");
//            log.error("解密后sck:"+sck);
//            log.error("解密后data:"+result);
        } catch (UnsupportedEncodingException e) {
            log.error("解密处理出现异常:" + e.getMessage());
        } catch (Exception e) {
            log.error("解密处理出现异常:" + e.getMessage());
        }
        return result;
    }

    public synchronized static Object jsonBeanToString(JsonBean jsonBean, boolean falg, String sck) {
        if (falg) {
            log.error("加密返回参数:"+JSON.toJSONString(jsonBean));
            String obj = Base64.encode(AESpkcs7paddingUtil.encrypt(JSONObject.toJSONString(jsonBean).getBytes(), sck.getBytes(), null));
            // @author:lxg-20191114 app直接取加密后字符串报错,添加一层对象返回
            JSONObject result=new JSONObject();
            result.put("data",obj);
            return result;
        } else {
            return jsonBean;
        }
    }
}
