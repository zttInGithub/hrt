package com.hrt.biz.util.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * HrtApp
 * HrtCostConstant
 *
 * @author xuegangliu  2019/4/28
 * @since JDK1.8+
 **/
public final class HrtCostConstant {

    public final static String RGSZ_PREX="RGSZ"; // 人工成本设置机构号前缀

    public final static String MACHINE_TYPE="MACHINE_TYPE"; // 机器类型
    public final static String TXN_TYPE="TXN_TYPE"; // 产品类型
    public final static String TXN_DETAIL="TXN_DETAIL"; // 产品细分

    private static Map machineType=new HashMap<String,String>();
    private static Map txnTypeMap=new HashMap<String,String>();
    private static Map txnDetilMap=new HashMap<String,String>();

    static {
        // 机器类型 1手刷 2传统
        machineType.put("1","手刷");
        machineType.put("2","传统");

        // 产品类型 1-秒到、2-理财、3-信用卡代还、4-云闪付、5-快捷、6-T0、7-T1
        txnTypeMap.put("1","秒到");
        txnTypeMap.put("2","理财");
        txnTypeMap.put("3","信用卡代还");
        txnTypeMap.put("4","云闪付");
        txnTypeMap.put("5","快捷");
        txnTypeMap.put("6","T0");
        txnTypeMap.put("7","T1");

        // 产品细分 1-0.6秒到、2-0.72秒到、3-理财、4-信用卡代还、5-云闪付、6-快捷VIP、7-快捷完美、8-微信、9-支付宝、10-银联二维码、11-传统-标准、12-传统-优惠、13-传统-减免
        txnDetilMap.put("1","0.6秒到");
        txnDetilMap.put("2","0.72秒到");
        txnDetilMap.put("3","理财");
        txnDetilMap.put("4","信用卡代还");
        txnDetilMap.put("5","云闪付");
        txnDetilMap.put("6","快捷VIP");
        txnDetilMap.put("7","快捷完美");
        txnDetilMap.put("8","扫码1000以下");
        txnDetilMap.put("9","扫码1000以上");
        txnDetilMap.put("10","银联二维码");
        txnDetilMap.put("11","传统-标准");
        txnDetilMap.put("12","传统-优惠");
        txnDetilMap.put("13","传统-减免");
        txnDetilMap.put("20","活动20");
        txnDetilMap.put("21","活动21");
    }

    /**
     * 成本设置类型名称获取
     * @param type
     * @param key
     * @return
     */
    public static String getValueByTypeAndKey(String type,String key){
        if(type.equals(MACHINE_TYPE)){
            return null!=machineType.get(key)?machineType.get(key)+"":"";
        }else if(type.equals(TXN_TYPE)){
            return null!=txnTypeMap.get(key)?txnTypeMap.get(key)+"":"";
        }else if(type.equals(TXN_DETAIL)){
            if(Integer.parseInt(key)<20) {
                return null != txnDetilMap.get(key) ? txnDetilMap.get(key) + "" : "";
            }else{
                return null != txnDetilMap.get(key) ? ("活动"+key) : key;
            }
        }
        return "";
    }

    public static String getKeyByTypeAndValue(String type,String value){
        String t = getString(type, value, MACHINE_TYPE, machineType);
        if (t != null) return t;
        t = getString(type, value, TXN_TYPE, txnTypeMap);
        if (t != null) return t;
        t = getString(type, value, TXN_DETAIL, txnDetilMap);
        if (t != null) return t;
        return "";
    }

    private static String getString(String type, String value, String txnDetail, Map txnDetilMap) {
        if (type.equals(txnDetail)) {
            if(txnDetail.equals(TXN_DETAIL) && value.contains("活动")){
                String act = value.replace("活动","").trim();
                return act;
            }else {
                if (txnDetilMap.containsValue(value)) {
                    for (Object t : txnDetilMap.keySet()) {
                        if (txnDetilMap.get(t + "").equals(value)) {
                            return t + "";
                        }
                    }
                }
            }
        }
        return null;
    }
}
