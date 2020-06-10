package com.argorse.java.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.entity.pagebean.MerchantTerminalInfoBean;
import com.hrt.biz.bill.service.IMerchantTerminalInfoService;
import com.hrt.biz.check.entity.model.CheckWalletCashSwitchLogModel;
import com.hrt.biz.check.entity.model.CheckWalletCashSwitchModel;
import com.hrt.biz.check.entity.model.CheckWalletCashSwitchWModel;
import com.hrt.biz.check.entity.pagebean.CheckWalletCashSwitchBean;
import com.hrt.biz.check.service.CheckWalletCashSwitchService;
import com.hrt.biz.util.unionpay.SHAEncUtil;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.service.IPhoneMicroMerchantInfoService;
import com.hrt.biz.util.mpos.AESpkcs7paddingUtil;
import com.hrt.biz.util.mpos.Base64;
import com.hrt.biz.util.mpos.RSAUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LxgTest
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/10/22
 * @since 1.8
 **/
public class LxgTest {
    @Test
    public void findAllWalletCashTest() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        CheckWalletCashSwitchService checkWalletCashSwitchService = ctx.getBean("checkWalletCashSwitchService", CheckWalletCashSwitchService.class);
        CheckWalletCashSwitchBean c = new CheckWalletCashSwitchBean();
        c.setPage(1);
        c.setRows(10);
        UserBean t= new UserBean();
        t.setUnNo("110000");
        checkWalletCashSwitchService.findAllWalletCash(c,t);
    }

    @Test
    public void saveWalletCashTest() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        CheckWalletCashSwitchService checkWalletCashSwitchService = ctx.getBean("checkWalletCashSwitchService", CheckWalletCashSwitchService.class);
        for(int i=26;i<=99;i++){
            CheckWalletCashSwitchBean c = new CheckWalletCashSwitchBean();
            c.setUnno("121000");
            c.setWalletStatus("1");
            c.setRebateType(i+"");
            CheckWalletCashSwitchModel m = checkWalletCashSwitchService.saveWalletCash(c);
            System.out.println(JSON.toJSON(m));
        }
    }

    @Test
    public void saveWalletCashWTest() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        CheckWalletCashSwitchService checkWalletCashSwitchService = ctx.getBean("checkWalletCashSwitchService", CheckWalletCashSwitchService.class);
        for(int i=26;i<=99;i++){
            CheckWalletCashSwitchBean c = new CheckWalletCashSwitchBean();
            c.setUnno("111000");
            c.setWalletStatus("1");
            c.setRebateType(i+"");
            CheckWalletCashSwitchWModel m = checkWalletCashSwitchService.saveWalletCashW(c);
            System.out.println(JSON.toJSON(m));
        }
    }

    @Test
    public void saveWalletCashLogTest() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        CheckWalletCashSwitchService checkWalletCashSwitchService = ctx.getBean("checkWalletCashSwitchService", CheckWalletCashSwitchService.class);
        for(int i=26;i<=99;i++){
            CheckWalletCashSwitchBean c = new CheckWalletCashSwitchBean();
            c.setUnno("111000");
            c.setWalletStatus("1");
            c.setRebateType(i+"");
            CheckWalletCashSwitchLogModel m = checkWalletCashSwitchService.saveWalletCashLog(c);
            System.out.println(JSON.toJSON(m));
        }
    }

    @Test
    public void getRebateTypeListByUserTest() throws Exception {
        //String getRebateTypeListByUser(UserBean user)
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-dev.xml");
        CheckWalletCashSwitchService checkWalletCashSwitchService = ctx.getBean("checkWalletCashSwitchService", CheckWalletCashSwitchService.class);
        UserBean userBean = new UserBean();
        userBean.setUnNo("d31000");
        userBean.setUnitLvl(1);
        System.out.println(checkWalletCashSwitchService.getRebateTypeListByUser(userBean));
    }

    @Test
    public void queryAgainSaveTerTest() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-dev.xml");
        IPhoneMicroMerchantInfoService iPhoneMicroMerchantInfoService = ctx.getBean("phoneMicroMerchantInfoService", IPhoneMicroMerchantInfoService.class);
        MerchantInfoBean bean=new MerchantInfoBean();
//        bean.setAgentId("10007");
        bean.setAgentId("10000");
        bean.setSn("YM39999998");
        bean.setMid("864000353110319");
        String msg = iPhoneMicroMerchantInfoService.queryAgainSaveTer(bean);
        System.out.println(msg);

//        IMerchantTerminalInfoService iMerchantTerminalInfoService = ctx.getBean("merchantTerminalInfoService", IMerchantTerminalInfoService.class);
//        MerchantTerminalInfoBean merchantTerminalInfoBean=new MerchantTerminalInfoBean();
//        merchantTerminalInfoBean.setMid("864000353110319");
//        merchantTerminalInfoBean.setTid("12345678");
//        iMerchantTerminalInfoService.serachTerminalIsActByTid(merchantTerminalInfoBean);

    }

    @Test
    public void aesTest() throws Exception {
        // 解密
//        String sck = RSAUtil.subMild16GetAesKey(jsonObject.getString("aesEn"));
        String sck = RSAUtil.subMild16GetAesKey("3FAgMR4uH1gwpa+wH+ipgxWtX2urSBIMdTtiqZaCghCMyQI/qPLg0ihLJh7+THCm/wItdPmG+QB9lwXGhleg0FXASXc+f6tqgshxSecPoVEmC2U4PImaEA2Tn2Pf5qIEkOewa4qU5hJOA5J9+a2lxVb1hoeERciyNB0DSTELpAxDbgvpeECBu7gbX0Z+aYyWgUbi4D5fHs7SbVf3RNs07Rv3ocR999tWxew0D8ZRXaQ9xZUYR6BHqUYLmY+bfvWJHeOBoDfHoBKm8RrfPg18PZOReSyqi37m1JLzq/nAJtxJMkI/7tBTZxxR5MwVyl2u3JR6Qha4zeN21aTNV5JcSw==");
        byte[] decrypt = AESpkcs7paddingUtil.decrypt(Base64.decode("XYsfkWJw4XG8bW4jKDpcA/M9OytZLYKSjCUFOC/UIxxH6F98peA656xLjvzw7ikT"), sck.getBytes("UTF-8"), "wQ2Yi4gzYfHjBZ8O".getBytes("UTF-8"));
        String data0 = new String(decrypt,"UTF-8");
        System.out.println(data0);

        // 加密
        JSONObject object = new JSONObject();
//        String sck = (String) request.getAttribute("SCK");
        String sign = Base64.encode(AESpkcs7paddingUtil.encrypt("vOrjFHI6OXfltqb4pXMe8AdR0yG5gYA+SDHJ9AsiOkoTQkhU+7+Fi1j1t7pJSp0Zc8T76vXubCSgPkCkLGZbPmOHmG+Ci6swnc8gvIqEHBRkJL8A6/AXow4P3qVP8j9BhH/bbdzY7iEFQsovGiqj0kk5Xs7QM3+vnuldIAi0I2Z1qy7Yq+4L0/AnqYBn6aXYs7eSJLcx30RqHWw9JIfBQNT7hubA13n3ivsAzsQjyVUdxFvnNtD/2XMHr2EOBhjivK4gfHUaelSeddZ64nU3HXHrHVMucVJ21YC/k3WDsvUnj4mO1yxdeeHJevQD5myb0HdFjgt/fu87lW2POGRIqfVjzXH8pVUAcllaNTOBSeHKj4PI1gku65hjojNLa6ADA3XkHuBCKqRYGAiFhnYawjKEXKz2ZUrm5HU9CNCUscttFKVD7yiODuex6AUs9rBQReKPFpXmjg2szFRjn7jLf6xz76a5dAznQtkJRO53Y117+ltsT5M9Ngobl7agNI0aXOfulm1/c+TElDggWE/SP/pDTUQPoVsVVzhGUs6bsp/pnoia4IBVYN0t8gRKPIB1zy/s0Ob5yQZj6cujLtP2H8niWW7sFER4PnTGklTYMR89bjuLsNv8j3gU8wABRSRq".getBytes(), sck.getBytes(), null));
        System.out.println(sign);
        object.put("data", sign);
        object.put("sign", SHAEncUtil.getSHA256Str(sign));
        System.out.println(JSON.toJSON(object));
    }

    @Test
    public void tt(){
//        System.out.println("1".getBytes().toString());
        System.out.println(File.separator);//输出 \
        System.out.println(File.pathSeparator);//输出 ;
    }

    @Test
    public void dataCompare() throws ParseException {
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        System.out.println(df.format(date));
        System.out.println("20200101".compareTo(df.format(df.parse("20200101"))));
    }
}
