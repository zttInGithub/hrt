package com.hrt.biz.check.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.check.entity.pagebean.CheckSettleReturnBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface CheckSettleReturnService {

	boolean saveMoreFillPayInfo(String xlsfile, UserBean user, String fileName);

	DataGridBean queryFillPayListData(
			CheckSettleReturnBean checkUnitSettleFillPayBean, UserBean user);

	DataGridBean queryFillPayListDataByStatus(
			CheckSettleReturnBean checkUnitSettleFillPayBean, UserBean user);

	void updateFillPayInfoStatus(String ids);

	HSSFWorkbook exportFillPayInfo(CheckSettleReturnBean checkSettleReturnBean,
			UserBean user);

}
