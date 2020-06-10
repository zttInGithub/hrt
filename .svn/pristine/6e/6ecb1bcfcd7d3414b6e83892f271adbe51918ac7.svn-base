package com.hrt.biz.bill.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.QRInvitationInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IQRInvitationInfoService {
	
	Integer updateQRInvitationStatus (QRInvitationInfoBean infoBean)throws Exception;
	
	Integer addQRInvitationStatus (QRInvitationInfoBean infoBean,UserBean user)throws Exception;
	
	Integer updateQRIUnno (QRInvitationInfoBean infoBean,String icids ,UserBean user)throws Exception;
	
	DataGridBean queryMerQRInvitationInfo (QRInvitationInfoBean infoBean,UserBean user)throws Exception;
	
	HSSFWorkbook exportMerQRAll(QRInvitationInfoBean qrInvitationInfoBean,UserBean userSession);

}
