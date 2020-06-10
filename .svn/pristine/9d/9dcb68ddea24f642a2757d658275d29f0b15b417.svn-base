package com.hrt.biz.bill.service;

import java.util.List;

import com.hrt.biz.bill.entity.pagebean.MerchantTwoUploadBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMerchantTwoUploadService {
	
	/**
	 * 分页查询二次上传资料
	 */
	DataGridBean queryMerchantTwoUpload(MerchantTwoUploadBean merchantTwoUpload, UserBean user);
	
	/**
	 * 二次上传
	 */
	Integer queryMerchant(MerchantTwoUploadBean merchantTwoUpload, UserBean user);
	void saveMerchantTwoUpload(MerchantTwoUploadBean merchantTwoUpload, UserBean user);
	void updateMerchantInfo(MerchantTwoUploadBean merchantTwoUpload, UserBean user);
	Integer updateMerchantInfoK(MerchantTwoUploadBean merchantTwoUpload, UserBean user);
	Integer updateMerchantInfoY(MerchantTwoUploadBean merchantTwoUpload, UserBean user);
	List<Object> queryMerchantInfoDetailed(Integer bmtuid);
	Integer deleteUploadImg(MerchantTwoUploadBean merchantTwoUpload, UserBean user);
}
