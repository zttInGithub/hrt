package com.hrt.biz.threads;

import com.alibaba.fastjson.JSON;
import com.hrt.util.HttpXmlClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * HrtApp
 * ReceiveRepayBDThread
 *  商户认证成功请求综合接口推送数据
 * @author xuegangliu  2019/5/14
 * @since JDK1.8+
 **/
public class ReceiveRepayBDThread extends Thread{

    private static final Log log = LogFactory.getLog(ReceiveRepayBDThread.class);

    private String admAppIp;
    private Map map;
    public ReceiveRepayBDThread(String admAppIp,Map map){
        this.admAppIp=admAppIp;
        this.map=map;
    }

    public String getAdmAppIp() {
        return admAppIp;
    }

    public void setAdmAppIp(String admAppIp) {
        this.admAppIp = admAppIp;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public void run() {
        //http://10.51.130.169:10087/AdmApp/repayment/repayment_receiveRepayBD.action
        log.info(map.get("mid")+"请求/AdmApp/repayment/repayment_receiveRepayBD.action,请求参数:"+ JSON.toJSONString(map));
        String ss= HttpXmlClient.post(admAppIp+"/AdmApp/repayment/repayment_receiveRepayBD.action", map);
        log.info(map.get("mid")+"请求/AdmApp/repayment/repayment_receiveRepayBD.action,返回参数:"+ss);
    }
}
