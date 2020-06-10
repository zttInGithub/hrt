package com.hrt.frame.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	public static final String TRIGGER_NAME = "TRIGGER_NAME";
	public static final String DISPLAY_NAME = "DISPLAY_NAME";
	public static final String NEXT_FIRE_TIME = "NEXT_FIRE_TIME";
	public static final String PREV_FIRE_TIME = "PREV_FIRE_TIME";
	public static final String TRIGGER_STATE = "TRIGGER_STATE";
	public static final String START_TIME = "START_TIME";
	public static final String END_TIME = "END_TIME";
	public static final String DISPLAY_STATE = "DISPLAY_STATE";
	public static final String TRIGGER_GROUP = "TRIGGER_GROUP";
	public static final String PICTURE_WATER_MARK="仅限北京和融通支付科技有限公司商户入网申请使用"; // 图片水印信息

	/** sn收签日期接口唯一key **/
	public static final String SN_PUSH_ACCEPTDATE_KEY="signForOrders";

	// 10000-秒到 10002-联刷 10005-收银台 10006-plus 10007-极速版 10009-Pro
	public static final String HRT_PORD_AGENTID=".10000.10005.10002.10006.10007.10009.";
	
	//返现导入次数限制
	public static final String ism35rebate_2=".91.";
	public static final String ism35rebate_3=".24.56.93.";
	public static final String ism35rebate_4=".40.50.42.52.43.53.44.54.45.55.47.57.48.58.75.";
	public static final String ism35rebate_5=".60.";
	public static final String ism35rebate_n=".10.11.15.16.17.25.";
	
	

    /**
     * 亿米付机构号,秘钥管理->sn费率调整   单独修改费率信息
     */
	public static final String YIMIFU_UNNO="b11023";
	/**
     * 特殊机构限制下设置限制---》不润许变更
     */
	public static final String LIMIT_UNNO="e21003";
	
	public static final Map<String,String> STATUS = new HashMap<String,String>();
	static{
		STATUS.put("ACQUIRED", "运行中");
		STATUS.put("PAUSED", "暂停中");
		STATUS.put("WAITING", "等待中");	
		STATUS.put("BLOCKED", "阻塞");
		STATUS.put("ERROR", "错误");
		STATUS.put("COMPLETE", "已完成");
	}
}
