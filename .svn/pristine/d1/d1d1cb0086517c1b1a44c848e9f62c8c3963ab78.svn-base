package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.MerchantThreeUploadBean;
import com.hrt.biz.bill.entity.pagebean.MerchantUpdateTerBean;
import com.hrt.biz.bill.entity.pagebean.TerminalInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantThreeUploadService {
	DataGridBean queryMerchantThreeUpload(MerchantThreeUploadBean bean, UserBean user);
	DataGridBean queryMerchantThreeUploadFor(MerchantThreeUploadBean bean, UserBean user);
	List<Object> queryDetail(MerchantThreeUploadBean bean);
	JsonBean updateMerchantThreeUploadGo(MerchantThreeUploadBean bean, UserBean user);
	JsonBean updateMerchantThreeUploadBack(MerchantThreeUploadBean bean, UserBean user);
}
