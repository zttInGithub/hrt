package com.hrt.biz.bill.service;

import com.hrt.biz.bill.entity.pagebean.ChangeTerRateBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

import java.util.Map;

public interface IChangeTerRateService {
	DataGridBean queryChangeTerRate(ChangeTerRateBean bean,UserBean user);
	Integer addChangeTerRate(ChangeTerRateBean bean,UserBean user);
	Integer updateChangeTerRateGo(ChangeTerRateBean bean,UserBean user);
	Integer updateChangeTerRateBack(ChangeTerRateBean bean,UserBean user);
	Integer updateChangeAllTerRateGo(ChangeTerRateBean bean,UserBean user);
	Integer updateChangeAllTerRateBack(ChangeTerRateBean bean,UserBean user);

	/**
	 * 批量费率修改Excel导入
	 * @param xlsfile 文件路径
	 * @param name 文件名称
	 * @param user 用户
	 * @return
	 */
	Map addBatchChangeTerRate(String xlsfile, String name, UserBean user);
}
