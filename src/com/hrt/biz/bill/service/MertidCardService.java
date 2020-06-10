package com.hrt.biz.bill.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.MertiedCardModel;

public interface MertidCardService {
	public void saveCard(MertiedCardModel mertiedCardModel);
	public List<MertiedCardModel> findCardByMtcid(String hql,Map<String, Object>param);
	public void updateMertiedCard(MertiedCardModel mertiedCardModel);
	public void deleteCardByMid(String mid,String accNo);
	public List<MertiedCardModel> queryCardByMid(String mid) throws UnsupportedEncodingException;
	public void updateMertiedCard(Map<String, String>paramsMap)throws UnsupportedEncodingException;
	public void saveCard(Map<String, String> paramsMap)throws ParseException;
	public List<MertiedCardModel> querySuccessCard(String accNo, String phoneNo, String idnum, String customerNm);
	public boolean queryCardByMidAndCardNum(String mid, String accNo, String phoneNo, String idnum, String customerNm);
}
