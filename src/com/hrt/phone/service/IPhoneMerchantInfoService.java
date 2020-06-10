package com.hrt.phone.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPhoneMerchantInfoService {

	 /**
	 * 查询出踢回和未放行的商户
	 */
	DataGridBean queryMerchantInfoZK(MerchantInfoBean merchantInfo,UserBean user) throws Exception;
	/**
	 * 方法功能：修改商户
	 * 参数：merchantInfo
	 * 	   loginName 当前登录用户名
	 * 返回值：void
	 * 异常：
	 */
	boolean updateMerchantInfo(MerchantInfoBean merchantInfo, UserBean user) throws Exception;
	DataGridBean searchTerminalInfo(String unno) throws Exception;
}
