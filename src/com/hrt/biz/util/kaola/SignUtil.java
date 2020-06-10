package com.hrt.biz.util.kaola;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Lenovo
 * @explain 在此类修改密钥信息
 *
 */
public class SignUtil {
	private static final Log log = LogFactory.getLog(SignUtil.class);

	//生产密钥
	//DES
	private static String desKey="zX41a7R7";
	//己方私钥
	private static String rsaPrivateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCWWXhQtWxAzHAWUiQPGnm2Rz8f+1moBp+W1/KdPKeLJDwd3lqeS8hYbXDNr/omeIjNfjVrtHvW3FKrvRTzUB+BcOlH7QbIYtIRcXEoXML92C8tnbM5PSsj37cooUN6O/mBWoLm36R22eI9U2Mz5IFhP78e+3+iCtXga+he7Xhw4oUR6LU66GizebcxEM7en0r0Cic/I/vF6ygWTMWg/bUX61LlFItQlH+As/X9EHYLdbis+YvPA0fVQ6Fb431Z/HpZN6HhEwZtrJh+iCQ2sikSooVFOHQQzaOB4aBCL/cC5U0++MwxtQkAncej9V1ELG5NBc43JvTgTyJA/SYe13kjAgMBAAECggEACdZQyPcV3ieCLfOx6EsTK5n9x1nqw9OrHXODrEYLsE7uoAWsaC8NLLv8ZXjeJSY9zTQ0ypdEgwpnXlVAyaq0ZQO+P7aCexn8JKgHf1ySZYl/jLyEvXoDGJXKph3nDFhgOtZxOinn2N7orpqGIx8QWAyTtLM/5ppO5CArVAq1lq0hr1FF95BKQzpPLicp82kdsMjWgRmNpEpDYHPi1wgQcI8eA5WXq6ER+FOw+j+opfH6pX1LC+V6B7SLLVd0FQnPyf2bl6IF604UgqdAHAfoZnDQmMD7alz9Dxvx8Ah0jyiOo/ZRhKMBSOsdIB+rd6kppzzvRhZf16Hj/DSZxnU2AQKBgQDcTBD8JYPBilw511T+Tclht3ygmbirmT6WeddramKrEBZRg7RFR29xsVQixhYR7CfZD0igHk8v1n1xmCxqLmrij+TAexXorwNmDDaKOhl21/1/Cq0k0LjNCtZzM+skdXJojc+6mZDz44YwsycGr8PTGWrz53+FZO83l9yKqFDn/wKBgQCut1Zzx6V/yVHiGVracSFO2rrxCbbZ2wIUytStnwd9GQ1y5AbEDWYnCkKc49XrbfQMEMyXFPiEGo+RFbUUVblskwgKKP8sO452OUInKff0PPXMWA/WNvuPDmL+fWjYyNhHSbM/vwj7kOOljadv7uDi0cdcCopsvDn4Jb/FybDO3QKBgGZ1lny/sf0FLgnU5fn1EdG59mP3uxVMi/iOqKuT6lonfEnjWKfUJrgZQsl9mewyCwKAgkW+cxQLzYJUxWuJNTs0s672UGVNaOZAjelSRl+o64T34Tm7PghXnxAruJTXbyPDuTbPj8RvMQ1bda49d3WqX70bEYtoO35+yhtPV3nfAoGBAIxtWXQ3nGLIKiNDY829LhtWHPlNaIVTzSlvGV/vOTKoJzIX6wQ7DMcGLSIy0aanuobUHcy1E0YCny/qtZ+Bg0asKsMXznj76XZGcVBPIDdTo/A5O4leqfSCiLipjdLoI0WxC/yJjCu+eep/n3uNEX3dRJJK+pWI+ELl/Gx5vr2VAoGBAKt8urE/Yg/MhSnsKPaE4Xppa0KaUTx9ORF56lNoaCCMb5G52AwRIHL9MFWqZ8t74FCmXyh/8niwta7HR+mqcMOL+Wp6TVkTY4Fa+JA25IDuFk7ksrl3VD5afwITAZUQg47PRUSLBK3JCFEUbboB0L4ES9b2YneI+MwAtUj5ncOv";
	//对方公钥
	private static String rsaPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkea7hM1AuTdHYi7h0+i/YCmV46lAUP8J7whI8AX7q4FQiQ7RRy9Pq0DyxeM3n1jxSKhCkd71QuOWOviYi22GMvmv5HIeJXK7NoM8rkLBeOjocBrVIIWVx+vs4DK3QFoEOhWNglUN+XKFnqQo3KDFwe+OOZX5MK94PmT9Ai5v4sQIDAQAB";
	/**
	 * 加密并签名
	 * @param reqMap
	 * @return 加密和签名后的结果Map
	 */
    public static Map<String,String> getSign(Map<String ,String> reqMap){
    	Map<String, String> data = new HashMap<String, String>();
    	String reqData = JSONObject.toJSONString(reqMap);
		System.out.println("转json后数据:"+reqData);
		try {
			 DES des=new DES(desKey);
			 reqData=des.encrypt(reqData);
			data.put("reqData", reqData);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.error("getSign 异常:"+e);
		}
 		data.put("sign", RSA.sign(reqData, rsaPrivateKey));
 		return data;	
    }
    
    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getSignVeryfy(Map<String, Object> resMap) {
		String retData=String.valueOf(resMap.get("retData"));
        //获得签名验证结果
        boolean isSign = RSA.verify(retData,String.valueOf(resMap.get("sign")), rsaPublicKey);
        System.out.println("验证考拉签名结果:"+isSign);
        if(isSign){
        	DES des = new DES(desKey);
    		try {
    			retData=des.decrypt(retData);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		Map<String, Object> data = (Map<String, Object>) JSONObject.parse(retData);
    		return data;
        }else{
        	System.out.println("验证考拉签名失败");
        	return null;
        }
    }
}
