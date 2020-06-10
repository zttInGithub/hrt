package com.hrt.phone.service;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPhoneReceiptsUploadService {

	/**
	 * 风控照片补传
	 */
	void updateReceiptsUploadData(UserBean userSession, Integer pkid, String parth, String parth2);

	boolean queryReceiptsUploadData(Integer rsid);
	
	DataGridBean queryMerchantBankCardData(String mid);
	DataGridBean queryMerchantTaskDetail2(String mid);

	String queryUploadPath();
	
	String queryRiskUploadPath();

	/**
	 * 风控照片上传（即交易完成之后立即上传小票）
	 */
	void saveReceiptsUploadData(String rrn, String parth);

}
