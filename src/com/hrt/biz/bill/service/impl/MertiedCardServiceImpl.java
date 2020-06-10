package com.hrt.biz.bill.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMerchantCartDao;
import com.hrt.biz.bill.entity.model.MertiedCardModel;
import com.hrt.biz.bill.service.MertidCardService;
import com.hrt.biz.util.unionpay.AESUtil;

public class MertiedCardServiceImpl implements MertidCardService {

	private IMerchantCartDao iMerchantCartDao;
	public void setiMerchantCartDao(IMerchantCartDao iMerchantCartDao) {
		this.iMerchantCartDao = iMerchantCartDao;
	}
	public IMerchantCartDao getiMerchantCartDao() {
		return iMerchantCartDao;
	}
	
	@Override
	public void saveCard(MertiedCardModel mertiedCardModel) {
		iMerchantCartDao.saveObject(mertiedCardModel);
	}

	@Override
	public List< MertiedCardModel> findCardByMtcid(String hql,Map<String, Object>param) {
		return iMerchantCartDao.queryObjectsByHqlList(hql);
	}
	@Override
	public void updateMertiedCard(MertiedCardModel mertiedCardModel) {
		iMerchantCartDao.updateObject(mertiedCardModel);
	}
	@Override
	public void deleteCardByMid(String mid,String accNo) {
		String hql = "from MertiedCardModel where mid =:mid and cardnum = :accNo";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mid", mid);
		params.put("accNo", accNo);
		List<MertiedCardModel>list = iMerchantCartDao.queryObjectsByHqlList(hql, params);
		MertiedCardModel mertiedCardModel = list.get(0);
		mertiedCardModel.setMaintaintype("D");
		iMerchantCartDao.updateObject(mertiedCardModel);
		
	}
	@Override
	public List<MertiedCardModel> queryCardByMid(String mid) throws UnsupportedEncodingException {
		String hql = "from MertiedCardModel where mid = :mid and status = 's' and maintaintype='A' or maintaintype='M'";
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("mid", mid);
		List<MertiedCardModel> list  = iMerchantCartDao.queryObjectsByHqlList(hql, params);
		for (MertiedCardModel mertiedCardModel : list) {
			mertiedCardModel.setCardnum(AESUtil.base64AndAesEncode(mertiedCardModel.getCardnum()));
			mertiedCardModel.setIdnum(AESUtil.base64AndAesEncode(mertiedCardModel.getIdnum()));
		}
		return list;
	}
	@Override
	public void updateMertiedCard(Map<String, String>paramsMap) throws UnsupportedEncodingException {
		String hql = "from MertiedCardModel where orderId = :orderId ";
		Map<String, Object>param = new HashMap<String, Object>();
		param.put("orderId",paramsMap.get("orderId"));
		List<MertiedCardModel>list = iMerchantCartDao.queryObjectsByHqlList(hql, param);
		if (list!=null&&list.size()>0) {
			MertiedCardModel mertiedCardModel = list.get(0);
			mertiedCardModel.setStatus("0".equals(paramsMap.get("activateStatus"))?"n":"s");
			//将token加密存储
			String token = paramsMap.get("tokenPayData").substring(paramsMap.get("tokenPayData").indexOf("=")+1, paramsMap.get("tokenPayData").indexOf("&"));
			token = AESUtil.base64AndAesEncode(token);
			mertiedCardModel.setCardtoken(token);
			iMerchantCartDao.updateObject(mertiedCardModel);
		}
	}
	@Override
	public void saveCard(Map<String, String> paramsMap) throws ParseException {
		MertiedCardModel mertiedCardModel = new MertiedCardModel();
		String accNo =paramsMap.get("accNo");
 		String certifId = paramsMap.get("certifId");
 		mertiedCardModel.setIdnum(certifId);  //证件号
		mertiedCardModel.setCardnum(accNo); //卡号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		mertiedCardModel.setCdate(sdf.parse(paramsMap.get("txnTime"))); //时间
		
		mertiedCardModel.setMid(paramsMap.get("merId"));   //商户号
		mertiedCardModel.setName(paramsMap.get("customerNm")); //姓名
		mertiedCardModel.setPhone(paramsMap.get("phoneNo")); //手机号
		mertiedCardModel.setOrderId(paramsMap.get("orderId")); //订单号
		mertiedCardModel.setStatus("a"); //新添加时的状态
		mertiedCardModel.setMaintaintype("A");
		mertiedCardModel.setLmdate(new Date());   //修改时间
		//银行存在，找到对应的图片进行保存
		mertiedCardModel.setPaymentLine(paramsMap.get("parentPaymentline"));
		String sql1 =  "select c.card_type, c.card_name,c.parent_paymentline, p.payment_bank, p.payment_bank_img from bill_BANK_CARDBIN c "+
					   "left join bill_BANK_PAYLINE b on c.parent_paymentline = b.parent_paymentline left join bill_BANK_NAME p "+
					   "on b.PARENT_NAME = p.PAYMENT_BANK where c.card_bin = '"+paramsMap.get("accNo").substring(0,6)+ "'" ;
		List<Map<String, Object>> maps = iMerchantCartDao.queryObjectsBySqlObject(sql1);
		if (maps!=null&&maps.size()>0) {
			Map<String, Object> map2 = maps.get(0);
			mertiedCardModel.setPaymentBankImg(map2.get("PAYMENT_BANK_IMG")==null? "":map2.get("PAYMENT_BANK_IMG").toString());
			mertiedCardModel.setPaymentBank(map2.get("PAYMENT_BANK")==null?"":map2.get("PAYMENT_BANK").toString());
			mertiedCardModel.setCardType(map2.get("CARD_TYPE")==null?"":map2.get("CARD_TYPE").toString());
			mertiedCardModel.setPaymentLine(map2.get("PARENT_PAYMENTLINE")==null?"":map2.get("PARENT_PAYMENTLINE").toString());
		}
		iMerchantCartDao.saveObject(mertiedCardModel);
	}
	@Override
	public List<MertiedCardModel> querySuccessCard(String accNo, String phoneNo, String idnum, String customerNm) {
		//查询该卡号是否开通成功
		String hql = "from MertiedCardModel where cardnum = :accNo and phone = :phoneNo and idnum = :idnum and name = :customerNm";
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("accNo", accNo);
		map.put("phoneNo", phoneNo);
		map.put("idnum", idnum);
		map.put("customerNm", customerNm);
		List<MertiedCardModel>list = iMerchantCartDao.queryObjectsByHqlList(hql, map);
		return list;
	}
	@Override
	public boolean queryCardByMidAndCardNum(String mid, String accNo, String phoneNo, String idnum, String customerNm) {
		String hql = "from MertiedCardModel where mid = :mid and cardnum = :accNo and phone = :phoneNo and idnum = :idnum and name = :customerNm and status='s' and maintaintype !='D'";
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("mid", mid);
		map.put("accNo", accNo);
		map.put("phoneNo", phoneNo);
		map.put("idnum", idnum);
		map.put("customerNm", customerNm);
		List<MertiedCardModel>list = iMerchantCartDao.queryObjectsByHqlList(hql, map);
		if (list.size()>0) {
			MertiedCardModel m = list.get(0);
			if ("D".equals(m.getStatus())) {
				m.setMaintaintype("A");
				iMerchantCartDao.updateObject(m);
			}
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		String string = AESUtil.base64AndAesDecode("hQr5STtNTQNlO48XTgzo4C7xGsSdlyn0JcTzFMIFmuc=");
		System.out.println(string);
	}
}
