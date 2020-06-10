package com.hrt.biz.check.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.entity.pagebean.MerchantAuthenticityBean;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.check.entity.pagebean.CheckMisTakeBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckMisTakeService {

	/**
	 *  查询回复报单接收
	 * @return 
	 */
	boolean saveDispatchOrder(JSONObject reqJson);
	/**
	 *  查询回复
	 * @return 
	 */
	boolean updateDispatchOrder(CheckMisTakeBean checkMisTakeBean,UserBean user);
	/**
	 *  查询回复记录
	 * @return 
	 */
	DataGridBean querMisTakeInfo(CheckMisTakeBean checkMisTakeBean,UserBean user);
	/**
	 * 差错查询导出所有
	 */
	HSSFWorkbook querMisTakeInfoExport(CheckMisTakeBean checkMisTakeBean,UserBean user);

	/**
	 * 查看明细
	 * @param map
	 * @param matb
	 */
	CheckMisTakeBean queryMisTakeById(Integer id);
}
