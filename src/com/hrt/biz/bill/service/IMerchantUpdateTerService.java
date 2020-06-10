package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.MerchantUpdateTerBean;
import com.hrt.biz.bill.entity.pagebean.TerminalInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantUpdateTerService {
	DataGridBean queryMerchantUpdateTer(MerchantUpdateTerBean bean,UserBean user);
	DataGridBean queryMerchantUpdateTerFor(MerchantUpdateTerBean bean,UserBean user);
	JsonBean updateMerTerBack(MerchantUpdateTerBean bean,UserBean user);
	JsonBean updateMerTerGo(MerchantUpdateTerBean bean,UserBean user);
	JsonBean addMerUpdateTer1(MerchantUpdateTerBean bean,UserBean user) throws Exception;
	JsonBean addMerUpdateTer2(MerchantUpdateTerBean bean,UserBean user);
	JsonBean addMerUpdateTer3(MerchantUpdateTerBean bean,UserBean user);
	List<Object> queryDetail(MerchantUpdateTerBean bean);
}
