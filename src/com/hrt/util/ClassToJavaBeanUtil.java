package com.hrt.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.gmms.webservice.AgentUnitInfo;
import com.hrt.gmms.webservice.MerchantInfo;

public class ClassToJavaBeanUtil {
	public static MerchantInfo toMerchantInfo(MerchantInfoModel model,String cby,String tenance,String sales){
		MerchantInfo info=new MerchantInfo();
		info.setSales(sales);//业务人员
		info.setCby(cby);//操作人员
		info.setMainTenance(tenance);//维护人员
		//微信扫码费率
		if(model.getScanRate()!=null){
			info.setScanRate(model.getScanRate());
		}
		//银联费率
		if(model.getScanRate1()!=null){
			info.setScanRate1(model.getScanRate1());
		}
		//支付宝费率
		if(model.getScanRate2()!=null){
			info.setScanRate2(model.getScanRate2());
		}
		//是否返现
		if(model.getIsRebate()!=null){
			info.setIsRebate(model.getIsRebate());
		}
		//贷记卡费率
		if(model.getCreditBankRate()!=null){
			info.setCreditBankRate(Double.valueOf(model.getCreditBankRate()));
		}
		//贷记卡大额封顶手续费
		if(model.getCreditFeeamt()!=null){
			info.setCreditFeeamt(Double.valueOf(model.getCreditFeeamt()));
		}
		//境外银联卡费率
		if(model.getOutsideBankRate()!=null){
			info.setOutsideBankRate(Double.valueOf(model.getOutsideBankRate()));
		}
		//T+0转T+1结算周期transterm;
		if(model.getTransterm()!=null){
			info.setTranSterm(model.getTransterm());
		}
		//提现费率 节假日dcashRate;
		if(model.getDcashRate()!=null){
			info.setDcashRate(Double.valueOf(model.getDcashRate()));
		}
		//提现费率 工作日dcashRate;
		if(model.getCashRate()!=null){
			info.setTcashRate(Double.valueOf(model.getCashRate()));
		}
		//秒到封顶值secondLimit;
		if(model.getSecondLimit()!=null){
			info.setSecondLimit(Double.valueOf(model.getSecondLimit()));
		}
		//秒到手续费（大）secondRate2;
		if(model.getSecondRate2()!=null){
			info.setSecondRate2(Double.valueOf(model.getSecondRate2()));
		}
		//是否实码ifCode;
		if(model.getIfCode()!=null){
			info.setIfCode(model.getIfCode());
		}
		//秒到手续费 secondRate;
		if(model.getSecondRate()!=null){
			info.setSecondRate(Double.valueOf(model.getSecondRate()));
		}
	    //省名称 province_name;
		if(model.getProvince_name()!=null){
			info.setProvince_name(model.getProvince_name());
		}
	    //区县名称 QX_name;
		if(model.getQX_name()!=null){
			info.setQX_name(model.getQX_name());
		}
		//D- 自然日，T- 工作日 settleMethod;
		if(model.getSettleMethod()!=null){
			info.setSettleMethod(model.getSettleMethod());
		}
		// D1 费率naturalRate;
		if(model.getNaturalRate()!=null){
			info.setNaturalRate(Double.valueOf(model.getNaturalRate()));
		}
		
		if(model.getAccountStatus()!=null){
			info.setAccountStatus(model.getAccountStatus());
		}
		if(model.getAccType()!=null){
			info.setAccType(model.getAccType());		
		}
		if(model.getAreaType()!=null){
			info.setAreaType(model.getAreaType());
		}
		if(model.getBaddr()!=null){
			info.setBaddr(model.getBaddr());
		}
		if(model.getBankAccName()!=null){
			info.setBankAccName(model.getBankAccName());
		}
		if(model.getBankAccNo()!=null){
			info.setBankAccNo(model.getBankAccNo());
		}
		if(model.getBankBranch()!=null){
			info.setBankBranch(model.getBankBranch());
		}
		if(model.getBankFeeRate()!=null){
			info.setBankFeeRate(Double.valueOf(model.getBankFeeRate()));
		}
		if(model.getBankType()!=null){
			info.setBankType(model.getBankType());
		}
		if(model.getBno()!=null){
			info.setBno(model.getBno());
		}
		if(model.getContactPerson()!=null){
			info.setContactPerson(model.getContactPerson());
		}
		if(model.getContactPhone()!=null){
			info.setContactPhone(model.getContactPhone());
		}
		if(model.getDealAmt()!=null){
			info.setDealAmt(Double.valueOf(model.getDealAmt()));
		}
		if(model.getEmail()!=null){
			info.setEmail(model.getEmail());
		}
		if(model.getFeeAmt()!=null){
			info.setFeeAmt(Double.valueOf(model.getFeeAmt()));
		}
		if(model.getIndustryType()!=null){
			info.setIndustryType(model.getIndustryType());
		}
		if(model.getLegalNum()!=null){
			info.setLegalNum(model.getLegalNum());
		}
		if(model.getLegalPerson()!=null){
			info.setLegalPerson(model.getLegalPerson());
		}
		if(model.getLegalType()!=null){
			info.setLegalType(model.getLegalType());
		}
		if(model.getMaintainDate()!=null){
			info.setMaintainDate(convertToXMLGregorianCalendar(model.getMaintainDate()));
		}
		if(model.getMccid()!=null){
			info.setMccid(Integer.valueOf(model.getMccid()));
		}
		if(model.getConMccid()!=null){
			info.setConMccid(Integer.valueOf(model.getConMccid()));
		}
		if(model.getMerchantType()!=null){
			info.setMerchantType(model.getMerchantType());
		}
		if(model.getMid()!=null){
			info.setMid(model.getMid());
		}
		if(model.getPayBankId()!=null){
			info.setPayBankId(model.getPayBankId());
		}
		if(model.getRaddr()!=null){
			info.setRaddr(model.getRaddr());
		}
		if(model.getRname()!=null){
			info.setRname(model.getRname());
		}
		if(model.getSettleCycle()!=null){
			info.setSettleCycle(model.getSettleCycle());
		}
		if(model.getSettleMerger()!=null){
			info.setSettleMerger(model.getSettleMerger());
		}
		if(model.getSettleRange()!=null){
			info.setSettleRange(model.getSettleRange());
		}
		if(model.getUnno()!=null){
			info.setUnno(model.getUnno());
		}
		if(model.getFeeRateV()!=null){
			info.setForeignFeeRate(Double.valueOf(model.getFeeRateV()));
		}
		if(model.getSendCode()!=null){
			info.setSendCode(model.getSendCode());
		}
		if(model.getIsM35()!=null){
			info.setIsM35(model.getIsM35());
		}
		//计价类别
		if(model.getValuationType()!=null) {
			info.setValuationType(model.getValuationType());
		}
		//三级营销商户落地审批
		if("7".equals(model.getAreaType())){
			info.setIsM35(7);
		}
//		else if ("8".equals(model.getAreaType())){
			//快捷
//			info.setIsM35(8);
//		}
		if(model.getAccNum()!=null){
			info.setAccNum(model.getAccNum());
		}
		if(model.getAccExpdate()!=null){
			info.setAccExpdate(model.getAccExpdate());
		}
		if(model.getBnoExpdate()!=null){
			info.setLicenseValid(model.getBnoExpdate());
		}
		if(model.getLegalExpdate()!=null){
			info.setIdentificationValid(model.getLegalExpdate());
		}
		if(model.getSettMethod()!=null && !"".equals(model.getSettMethod())){
			info.setSettMethod(model.getSettMethod().substring(0, 1));
		}else{
			info.setSettMethod("0");
		}
		return info;
	}
	
	 public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        XMLGregorianCalendar gc = null;
	        try {
	            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        } catch (Exception e) {

	             e.printStackTrace();
	        }
	        return gc;
	    }
	 
	     public static  Date convertToDate(XMLGregorianCalendar cal) throws Exception{
	         GregorianCalendar ca = cal.toGregorianCalendar();
	         return ca.getTime();
	     }

		public static AgentUnitInfo toAgentInfo(AgentUnitModel m) {
			AgentUnitInfo info=new AgentUnitInfo();

			if(m.getAccType()!=null){
				info.setAccType(m.getAccType());
			}
			if(m.getAgentName()!=null){
				info.setAgentName(m.getAgentName());
			}
			if(m.getAmountConfirmDate()!=null){
				info.setAmountConfirmDate(convertToXMLGregorianCalendar(m.getAmountConfirmDate()));
			}
			if(m.getAreaType()!=null){
				info.setAreaType(m.getAreaType());
			}
			if(m.getBaddr()!=null){
				info.setBaddr(m.getBaddr());
			}
			if(m.getBankAccName()!=null){
				info.setBankAccName(m.getBankAccName());
			}
			if(m.getBankAccNo()!=null){
				info.setBankAccNo(m.getBankAccNo());
			}
			if(m.getBankArea()!=null){
				info.setBankArea(m.getBankArea());
			}
			if(m.getBankBranch()!=null){
				info.setBankBranch(m.getBankBranch());
			}
			if(m.getBankOpenAcc()!=null){
				info.setBankOpenAcc(m.getBankOpenAcc());
			}
			if(m.getBankType()!=null){
				info.setBankType(m.getBankType());
			}
			if(m.getBno()!=null){
				info.setBno(m.getBno());
			}
			if(m.getBusinessContacts()!=null){
				info.setBusinessContacts(m.getBusinessContacts());
			}
			if(m.getBusinessContactsMail()!=null){
				info.setBusinessContactsMail(m.getBusinessContactsMail());
			}
			if(m.getBusinessContactsPhone()!=null){
				info.setBusinessContactsPhone(m.getBusinessContactsPhone());
			}
			if(m.getContact()!=null){
				info.setContact(m.getContact());
			}
			if(m.getContactPhone()!=null){
				info.setContactPhone(m.getContactPhone());
			}
			if(m.getContactTel()!=null){
				info.setContactTel(m.getContactTel());
			}
			if(m.getContractNo()!=null){
				info.setContractNo(m.getContractNo());
			}
			if(m.getLegalNum()!=null){
				info.setLegalNum(m.getLegalNum());
			}
			if(m.getLegalPerson()!=null){
				info.setLegalPerson(m.getLegalPerson());
			}
			if(m.getLegalType()!=null){
				info.setLegalType(m.getLegalType());
			}
			if(m.getMaintainDate()!=null){
				info.setMaintainDate(convertToXMLGregorianCalendar(m.getMaintainDate()));
			}
			if(m.getMaintainType()!=null){
				info.setMaintainType(m.getMaintainType());
			}
			if(m.getMaintainUid()!=null){
				info.setMaintainUid(m.getMaintainUid());
			}
			if(m.getMaintainUserId()!=null){
				info.setMaintainUserId(m.getMaintainUserId());
			}
			if(m.getOpenDate()!=null){
				info.setOpenDate(convertToXMLGregorianCalendar(m.getOpenDate()));
			}
			if(m.getRegistryNo()!=null){
				info.setRegistryNo(m.getRegistryNo());
			}
			if(m.getRiskAmount()!=null){
				info.setRiskAmount(m.getRiskAmount());
			}
			if(m.getRiskContact()!=null){
				info.setRiskContact(m.getRiskContact());
			}
			if(m.getRiskContactMail()!=null){
				info.setRiskContactMail(m.getRiskContactMail());
			}
			if(m.getRiskContactPhone()!=null){
				info.setRiskContactPhone(m.getRiskContactPhone());
			}
			if(m.getRno()!=null){
				info.setRno(m.getRno());
			}
			if(m.getSecondContact()!=null){
				info.setSecondContact(m.getSecondContact());
			}
			if(m.getSecondContactPhone()!=null){
				info.setSecondContactPhone(m.getSecondContactPhone());
			}
			if(m.getSecondContactTel()!=null){
				info.setSignUserId(m.getSignUserId());
			}
			if(m.getSignUserId()!=null){
				info.setSignUserId(m.getSignUserId());
			}
			if(m.getUnno()!=null){
				info.setUnno(m.getUnno());
			}
			if(m.getAgentShortNm()!=null){
				info.setCronym(m.getAgentShortNm());
			}
			if (m.getPayBankID() != null) {
				info.setPayBankId(m.getPayBankID());
			}
			if (m.getCycle() != null) {
				info.setCycle(m.getCycle());
			}
			if (m.getCashRate() != null) {
				info.setCashRate(m.getCashRate().doubleValue());
			}
			if (m.getPurseType() != null) {
				info.setPurseType(m.getPurseType());
			}
			return info;
		}
}
