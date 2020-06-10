package com.hrt.biz.check.service;

import java.util.List;
import java.util.Map;

import com.hrt.biz.check.entity.pagebean.CheckProfitwithholdBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckProfitwithholdService {

	/**
	 * 代扣分润查询
	 * */
	DataGridBean queryWithholdInfo(CheckProfitwithholdBean checkProfitwithholdBean, UserBean user);
	/**
	 * 代扣分润导出
	 * */
	List<Map<String, Object>> profitWithholdExport(CheckProfitwithholdBean checkProfitwithholdBean,
			UserBean userSession);
	/**
	 * 添加代扣分润
	 * */
	void addprofitWithholdInfo(CheckProfitwithholdBean checkProfitwithholdBean, UserBean user);
	/**
	 * 判断添加代扣是否符合规则
	 * */
	String judgeProfitWithholdInfo(CheckProfitwithholdBean checkProfitwithholdBean, UserBean user);
	/**
	 * 一代确认操作
	 * */
	void yidaiProfitWithholdEdit(CheckProfitwithholdBean checkProfitwithholdBean, UserBean user);

}
