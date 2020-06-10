package com.hrt.biz.check.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.check.entity.pagebean.CheckMisTakeBean;
import com.hrt.biz.check.entity.pagebean.CheckReOrderBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckReOrderService {

	/**
	 * 查询退单记录
	 */
	DataGridBean queryReOrderInfo(CheckReOrderBean checkReOrderBean, UserBean user);
	
	/**
	 * 查询已回复退单
	 */
	DataGridBean queryReplyReOrderInfo(CheckReOrderBean checkReOrderBean, UserBean user);
	
	/**
	 *  接收退单
	 * @return 
	 */
	boolean saveReOrder(JSONObject reqJson);
	/**
	 *  回复
	 * @return 
	 * @throws ParseException 
	 */
	Map<String, String> updateReOrder(CheckReOrderBean checkReOrderBean,UserBean user) throws ParseException;
	
	/**
	 * 查看明细
	 * @param map
	 * @param matb
	 */
	CheckReOrderBean queryReOrderById(Integer id);
	
	/**
	 * 导出所有
	 */
	HSSFWorkbook queryReOrderExport(CheckReOrderBean checkReOrderBean,UserBean user);
	boolean updateRebackReOrder(CheckReOrderBean checkReOrderBean, UserBean userBean);
	boolean updatePassReOrder(CheckReOrderBean checkReOrderBean, UserBean userBean);

}
