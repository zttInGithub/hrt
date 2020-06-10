package com.hrt.biz.check.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.entity.model.HrtUnnoCostModel;
import com.hrt.biz.bill.service.IAgentUnitService;
import com.hrt.biz.check.dao.CheckUnitProfitTraditLogDao;
import com.hrt.biz.check.dao.CheckUnitProfitTraditWDao;
import com.hrt.biz.check.entity.model.CheckProfitTempTraditLogModel;
import com.hrt.biz.check.entity.model.CheckProfitTempTraditWModel;
import com.hrt.util.DateTools;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckUnitProfitTraditDao;
import com.hrt.biz.check.entity.model.CheckProfitTempTraditModel;
import com.hrt.biz.check.entity.pagebean.CheckProfitTempTraditBean;
import com.hrt.biz.check.service.CheckUnitProfitTraditService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import org.springframework.beans.BeanUtils;


public class CheckUnitProfitTraditServiceImpl implements CheckUnitProfitTraditService {

	private CheckUnitProfitTraditDao checkUnitProfitTraditDao;
	private CheckUnitProfitTraditWDao checkUnitProfitTraditWDao;
	private CheckUnitProfitTraditLogDao checkUnitProfitTraditLogDao;
	private IUnitDao unitDao;
	private IMerchantInfoService merchantInfoService;
	private IAgentUnitService agentUnitService;

	public IAgentUnitService getAgentUnitService() {
		return agentUnitService;
	}

	public void setAgentUnitService(IAgentUnitService agentUnitService) {
		this.agentUnitService = agentUnitService;
	}

	public CheckUnitProfitTraditDao getCheckUnitProfitTraditDao() {
		return checkUnitProfitTraditDao;
	}

	public CheckUnitProfitTraditLogDao getCheckUnitProfitTraditLogDao() {
		return checkUnitProfitTraditLogDao;
	}

	public void setCheckUnitProfitTraditLogDao(CheckUnitProfitTraditLogDao checkUnitProfitTraditLogDao) {
		this.checkUnitProfitTraditLogDao = checkUnitProfitTraditLogDao;
	}

	public void setCheckUnitProfitTraditDao(
			CheckUnitProfitTraditDao checkUnitProfitTraditDao) {
		this.checkUnitProfitTraditDao = checkUnitProfitTraditDao;
	}

	public CheckUnitProfitTraditWDao getCheckUnitProfitTraditWDao() {
		return checkUnitProfitTraditWDao;
	}

	public void setCheckUnitProfitTraditWDao(CheckUnitProfitTraditWDao checkUnitProfitTraditWDao) {
		this.checkUnitProfitTraditWDao = checkUnitProfitTraditWDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	/**
	 * 查询模版
	 */
	public DataGridBean queryPROFITTEMPLATE(CheckProfitTempTraditBean cptt,UserBean userBean){

			String sql="select p.UNNO,p.TEMPNAME from CHECK_TRADIT_PROFITTEMP p where 1=1 ";
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
			//为总公司
			if("110000".equals(userBean.getUnNo())){
			 }
			//为总公司并且是部门---查询全部
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
			}else{
				sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT='"+userBean.getUnNo()+"') " ;
			}
			if(cptt.getUnno()!=null&&!"".equals(cptt.getUnno())){
				sql+= " and p.unno ='"+cptt.getUnno()+"'";
			}
			if(cptt!=null&&cptt.getTempName()!=null&&!cptt.getTempName().equals("")){
				sql+="and  p.TEMPNAME='"+cptt.getTempName()+"' ";
			}
			sql+=" and MatainType in ('A','M') group by p.TEMPNAME ,p.UNNO ";
		String count="select count(*) from ("+sql+")";
		List<Map<String,String>> list=checkUnitProfitTraditDao.queryObjectsBySqlList(sql,null,cptt.getPage(),cptt.getRows());
		BigDecimal counts = checkUnitProfitTraditDao.querysqlCounts(count,null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
	/**
	 * 查询模版明细
	 * @throws UnsupportedEncodingException
	 */
	public DataGridBean queryprofittemplateAll(CheckProfitTempTraditBean cptt,UserBean userBean) throws UnsupportedEncodingException{

		String sql= "select p.CTPID,p.tempName,p.industrytype,p.costRate,p.feeAmt,p.dealAmt,p.profitPercent,p.matainType,p.creditBankRate," +
					" p.cashRate,p.cashAmt,p.scanRate " +
					"from CHECK_TRADIT_PROFITTEMP p where p.tempname='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' " ;
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		//为总公司
		if("110000".equals(userBean.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT='"+userBean.getUnNo()+"') " ;
		}
		String count="select count(*) from ("+sql+")";
		List<Map<String,String>> list=checkUnitProfitTraditDao.queryObjectsBySqlList(sql,null,cptt.getPage(),cptt.getRows());
		BigDecimal counts = checkUnitProfitTraditDao.querysqlCounts(count,null);
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(counts.intValue());//查询数据库总条数
		dgd.setRows(list);
		return dgd;
	}
	/**
	 * 查询模版是否重复添加
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> queryTempName(CheckProfitTempTraditBean cptt,UserBean user) throws UnsupportedEncodingException {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptt.getUnno());
		String sql="select p.TEMPNAME from CHECK_TRADIT_PROFITTEMP p where p.tempname='"+unitModel.getUnitName()+"' and unno='"+cptt.getUnno()+"'  and MatainType in ('A','M') ";
		List list=checkUnitProfitTraditDao.executeSql(sql);
		return list;
	}

	@Override
	public String validateProfitTradit(CheckProfitTempTraditBean cptt, UserBean user,Integer checkType){
//		Map<String,CheckProfitTempTraditModel> minTradit = getTraditProfitTemplateMap(3,user.getUnNo(),user.getUnitLvl());
		if(user.getUnitLvl()<=2){
			if(user.getUnitLvl()==2){
				Map<String, HrtUnnoCostModel> hrtCostMap = agentUnitService.getAllHrtCostMap(1,user.getUnNo(),2,checkType);

				// 标准类成本
				// 借记卡费率 costRate
				if(hrtCostMap.get("2|6|11")!=null && hrtCostMap.get("2|6|11").getDebitRate()!=null){
					if(new BigDecimal(cptt.getCostRate().toString()).compareTo(hrtCostMap.get("2|6|11").getDebitRate())<0){
                        return "标准类借记卡费率小于上级成本";
                    }
				}else{
					return "标准类借记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
				}
				// 借卡大额手续费
				if(hrtCostMap.get("2|6|11")!=null && hrtCostMap.get("2|6|11").getDebitFeeamt()!=null){
					if(new BigDecimal(cptt.getFeeAmt().toString()).compareTo(hrtCostMap.get("2|6|11").getDebitFeeamt())<0){
						return "标准类借卡大额手续费小于上级成本";
					}
				}else{
					return "标准类借卡大额手续费成本上级代理未维护，请联系上级代理进行成本设置";
				}
				// 借卡大额封顶值-默认4146


				// 贷记卡费率 creditBankRate
				if(hrtCostMap.get("2|6|11")!=null && hrtCostMap.get("2|6|11").getCreditRate()!=null){
					if(new BigDecimal(cptt.getCreditBankRate().toString()).compareTo(hrtCostMap.get("2|6|11").getCreditRate())<0){
						return "标准类贷记卡费率小于上级成本";
					}
				}else{
					return "标准类贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
				}

				//优惠类成本
				if(hrtCostMap.get("2|6|12")!=null && hrtCostMap.get("2|6|12").getDebitRate()!=null){
					if(new BigDecimal(cptt.getCostRate1().toString()).compareTo(hrtCostMap.get("2|6|12").getDebitRate())<0){
						return "优惠类借记卡费率小于上级成本";
					}
				}else{
					return "优惠类借记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
				}
				// 借卡大额手续费
				if(hrtCostMap.get("2|6|12")!=null && hrtCostMap.get("2|6|12").getDebitFeeamt()!=null){
					if(new BigDecimal(cptt.getFeeAmt1().toString()).compareTo(hrtCostMap.get("2|6|12").getDebitFeeamt())<0){
						return "优惠类借卡大额手续费小于上级成本";
					}
				}else{
					return "优惠类借卡大额手续费成本上级代理未维护，请联系上级代理进行成本设置";
				}
				// 借卡大额封顶值-默认4146
				// 贷记卡费率 creditBankRate
				if(hrtCostMap.get("2|6|12")!=null && hrtCostMap.get("2|6|12").getCreditRate()!=null){
					if(new BigDecimal(cptt.getCreditBankRate1().toString()).compareTo(hrtCostMap.get("2|6|12").getCreditRate())<0){
						return "优惠类贷记卡费率小于上级成本";
					}
				}else{
					return "优惠类贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
				}

				// 减免类成本
				if(hrtCostMap.get("2|6|13")!=null && hrtCostMap.get("2|6|13").getDebitRate()!=null){
					if(new BigDecimal(cptt.getCostRate2().toString()).compareTo(hrtCostMap.get("2|6|13").getDebitRate())<0){
						return "减免类借记卡费率小于上级成本";
					}
				}else{
					return "减免类借记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
				}
				// 借卡大额手续费 传统减免 借记卡封顶手续费、借记卡封顶交易 都固定为“无”，不允许编辑
//				if(hrtCostMap.get("2|6|13")!=null && hrtCostMap.get("2|6|13").getDebitFeeamt()!=null){
//					if(new BigDecimal(cptt.getFeeAmt2().toString()).compareTo(hrtCostMap.get("2|6|13").getDebitFeeamt())<0){
//						return "减免类成本借卡大额手续费小于上级成本";
//					}
//				}else{
//					return "上级成本未维护减免类成本借卡大额手续费,不可创建与修改";
//				}
				// 借卡大额封顶值
				// 贷记卡费率 creditBankRate
				if(hrtCostMap.get("2|6|13")!=null && hrtCostMap.get("2|6|13").getCreditRate()!=null){
					if(new BigDecimal(cptt.getCreditBankRate2().toString()).compareTo(hrtCostMap.get("2|6|13").getCreditRate())<0){
						return "减免类贷记卡费率小于上级成本";
					}
				}else{
					return "减免类贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
				}

				//转账费
				BigDecimal cash=null;
				if(hrtCostMap.get("2|6|11")!=null && hrtCostMap.get("2|6|11").getCashCost()!=null){
					cash=hrtCostMap.get("2|6|11").getCashCost();
				}
//				if(hrtCostMap.get("2|6|9")!=null && hrtCostMap.get("2|6|9").getCashCost()!=null){
//					if(cash!=null){
//						if(cash.compareTo(hrtCostMap.get("2|6|9").getCashCost())<0){
//							cash=hrtCostMap.get("2|6|9").getCashCost();
//						}
//					}else{
//						cash=hrtCostMap.get("2|6|9").getCashCost();
//					}
//				}
//				if(hrtCostMap.get("2|6|10")!=null && hrtCostMap.get("2|6|10").getCashCost()!=null){
//					if(cash!=null){
//						if(cash.compareTo(hrtCostMap.get("2|6|10").getCashCost())<0){
//							cash=hrtCostMap.get("2|6|10").getCashCost();
//						}
//					}else{
//						cash=hrtCostMap.get("2|6|10").getCashCost();
//					}
//				}
				if(cash!=null){
					if(new BigDecimal(cptt.getCashAmt().toString()).compareTo(cash)<0){
						return "转账费小于上级成本";
					}
				}else{
					return "转账费成本上级代理未维护，请联系上级代理进行成本设置";
				}

				// 传统扫码支付费率 1000以上
				BigDecimal rate=null;
				if(hrtCostMap.get("2|6|8")!=null && hrtCostMap.get("2|6|8").getCreditRate()!=null){
					rate=hrtCostMap.get("2|6|8").getCreditRate();
				}
//				if(hrtCostMap.get("2|6|9")!=null && hrtCostMap.get("2|6|9").getCreditRate()!=null){
//					if(rate!=null){
//						if(rate.compareTo(hrtCostMap.get("2|6|9").getCreditRate())<0){
//							rate=hrtCostMap.get("2|6|9").getCreditRate();
//						}
//					}else{
//						rate=hrtCostMap.get("2|6|9").getCreditRate();
//					}
//				}
//				if(hrtCostMap.get("2|6|10")!=null && hrtCostMap.get("2|6|10").getCreditRate()!=null){
//					if(rate!=null){
//						if(rate.compareTo(hrtCostMap.get("2|6|10").getCreditRate())<0){
//							rate=hrtCostMap.get("2|6|10").getCreditRate();
//						}
//					}else{
//						rate=hrtCostMap.get("2|6|10").getCreditRate();
//					}
//				}
				if(rate!=null){
					if(new BigDecimal(cptt.getScanRate().toString()).compareTo(rate)<0){
						return "传统扫码1000以下费率小于上级成本";
					}
				}else{
					return "传统扫码1000以下费率成本上级代理未维护，请联系上级代理进行成本设置";
				}

				// 传统扫码支付费率 1000以上
				BigDecimal rateUp=null;
				if(hrtCostMap.get("2|6|9")!=null && hrtCostMap.get("2|6|9").getCreditRate()!=null){
					rateUp=hrtCostMap.get("2|6|9").getCreditRate();
				}
				if(rateUp!=null){
					if(new BigDecimal(cptt.getScanRateUp().toString()).compareTo(rateUp)<0){
						return "传统扫码1000以上费率小于上级成本";
					}
				}else{
					return "传统扫码1000以上费率成本上级代理未维护，请联系上级代理进行成本设置";
				}

				// T0提现费率
				BigDecimal cashR=null;
				if(hrtCostMap.get("2|6|11")!=null && hrtCostMap.get("2|6|11").getCashRate()!=null){
					cashR=hrtCostMap.get("2|6|11").getCashRate();
				}
//				if(hrtCostMap.get("2|6|9")!=null && hrtCostMap.get("2|6|9").getCashRate()!=null){
//					if(cashR!=null){
//						if(cashR.compareTo(hrtCostMap.get("2|6|9").getCashRate())<0){
//							cashR=hrtCostMap.get("2|6|9").getCashRate();
//						}
//					}else{
//						cashR=hrtCostMap.get("2|6|9").getCashRate();
//					}
//				}
//				if(hrtCostMap.get("2|6|10")!=null && hrtCostMap.get("2|6|10").getCashRate()!=null){
//					if(cashR!=null){
//						if(cashR.compareTo(hrtCostMap.get("2|6|10").getCashRate())<0){
//							cashR=hrtCostMap.get("2|6|10").getCashRate();
//						}
//					}else{
//						cashR=hrtCostMap.get("2|6|10").getCashRate();
//					}
//				}
				if(cashR!=null){
					if(new BigDecimal(cptt.getCashRate().toString()).compareTo(cashR)<0){
						return "T0提现费率小于上级成本";
					}
				}else{
					return "T0提现费率成本上级代理未维护，请联系上级代理进行成本设置";
				}
			}else{
				Map<String,CheckProfitTempTraditModel> parent = getTraditProfitTemplateMap(2,user.getUnNo(),user.getUnitLvl(),checkType);
				if(parent.get("1")!=null && parent.get("1").getCostRate()!=null){
					if(new BigDecimal(cptt.getCostRate().toString()).compareTo(new BigDecimal(parent.get("1").getCostRate().toString()).multiply(new BigDecimal("100")))<0){
						return "标准类借记卡费率小于上级成本";
					}else{
						return "标准类借记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
				if(parent.get("1")!=null && parent.get("1").getCreditBankRate()!=null){
					if(new BigDecimal(cptt.getCreditBankRate().toString()).compareTo(new BigDecimal(parent.get("1").getCreditBankRate().toString()).multiply(new BigDecimal("100")))<0){
						return "标准类贷记卡费率小于上级成本";
					}else{
						return "标准类贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
				if(parent.get("1")!=null && parent.get("1").getFeeAmt()!=null){
					if(new BigDecimal(cptt.getFeeAmt().toString()).compareTo(new BigDecimal(parent.get("1").getFeeAmt().toString()))<0){
						return "标准类借卡大额手续费小于上级成本";
					}else{
						return "标准类借卡大额手续费成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
//				if(parent.get("1")!=null && parent.get("1").getDealAmt()!=null){
//					if(new BigDecimal(cptt.getDealAmt().toString()).compareTo(new BigDecimal(parent.get("1").getDealAmt().toString()))<0){
//						return "标准类成本借卡大额封顶值小于上级成本";
//					}else{
//						return "上级成本未维护标准类成本借卡大额封顶值,不可创建与修改";
//					}
//				}

				if(parent.get("2")!=null && parent.get("2").getCostRate()!=null){
					if(new BigDecimal(cptt.getCostRate1().toString()).compareTo(new BigDecimal(parent.get("2").getCostRate().toString()).multiply(new BigDecimal("100")))<0){
						return "优惠类借记卡费率小于上级成本";
					}else{
						return "优惠类借记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
				if(parent.get("2")!=null && parent.get("2").getCreditBankRate()!=null){
					if(new BigDecimal(cptt.getCreditBankRate1().toString()).compareTo(new BigDecimal(parent.get("2").getCreditBankRate().toString()).multiply(new BigDecimal("100")))<0){
						return "优惠类贷记卡费率小于上级成本";
					}else{
						return "优惠类贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
				if(parent.get("2")!=null && parent.get("2").getFeeAmt()!=null){
					if(new BigDecimal(cptt.getFeeAmt1().toString()).compareTo(new BigDecimal(parent.get("2").getFeeAmt().toString()))<0){
						return "优惠类借卡大额手续费小于上级成本";
					}else{
						return "优惠类借卡大额手续费成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
//				if(parent.get("2")!=null && parent.get("2").getDealAmt()!=null){
//					if(new BigDecimal(cptt.getDealAmt1().toString()).compareTo(new BigDecimal(parent.get("2").getDealAmt().toString()))<0){
//						return "优惠类成本借卡大额封顶值小于上级成本";
//					}else{
//						return "上级成本未维护优惠类成本借卡大额封顶值,不可创建与修改";
//					}
//				}

				if(parent.get("3")!=null && parent.get("3").getCostRate()!=null){
					if(new BigDecimal(cptt.getCostRate2().toString()).compareTo(new BigDecimal(parent.get("3").getCostRate().toString()).multiply(new BigDecimal("100")))<0){
						return "减免类借记卡费率小于上级成本";
					}else{
						return "减免类借记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
				if(parent.get("3")!=null && parent.get("3").getCreditBankRate()!=null){
					if(new BigDecimal(cptt.getCreditBankRate2().toString()).compareTo(new BigDecimal(parent.get("3").getCreditBankRate().toString()).multiply(new BigDecimal("100")))<0){
						return "减免类贷记卡费率小于上级成本";
					}else{
						return "减免类贷记卡费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
//				if(parent.get("3")!=null && parent.get("3").getFeeAmt()!=null){
//					if(new BigDecimal(cptt.getFeeAmt2().toString()).compareTo(new BigDecimal(parent.get("3").getFeeAmt().toString()))<0){
//						return "减免类成本借卡大额手续费小于上级成本";
//					}else{
//						return "上级成本未维护减免类成本借卡大额手续费,不可创建与修改";
//					}
//				}
//				if(parent.get("3")!=null && parent.get("3").getDealAmt()!=null){
//					if(new BigDecimal(cptt.getDealAmt2().toString()).compareTo(new BigDecimal(parent.get("3").getDealAmt().toString()))<0){
//						return "减免类成本借卡大额封顶值小于上级成本";
//					}else{
//						return "上级成本未维护减免类成本借卡大额封顶值,不可创建与修改";
//					}
//				}

				if(parent.get("1")!=null && parent.get("1").getScanRate()!=null){
					if(new BigDecimal(cptt.getScanRate().toString()).compareTo(new BigDecimal(parent.get("1").getScanRate().toString()).multiply(new BigDecimal("100")))<0){
						return "传统扫码1000以下费率小于上级成本";
					}else{
						return "传统扫码1000以下费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}

				if(parent.get("1")!=null && parent.get("1").getScanRateUp()!=null){
					if(new BigDecimal(cptt.getScanRateUp().toString()).compareTo(new BigDecimal(parent.get("1").getScanRateUp().toString()).multiply(new BigDecimal("100")))<0){
						return "传统扫码1000以上费率率小于上级成本";
					}else{
						return "传统扫码1000以上费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
//
				if(parent.get("1")!=null && parent.get("1").getCashAmt()!=null){
					if(new BigDecimal(cptt.getCashAmt().toString()).compareTo(new BigDecimal(parent.get("1").getCashAmt().toString()))<0){
						return "T0转账费小于上级成本";
					}else{
						return "T0转账费成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
//
				if(parent.get("1")!=null && parent.get("1").getCashRate()!=null){
					if(new BigDecimal(cptt.getCashRate().toString()).compareTo(new BigDecimal(parent.get("1").getCashRate().toString()).multiply(new BigDecimal("100")))<0){
						return "T0提现费率小于上级成本";
					}else{
						return "T0提现费率成本上级代理未维护，请联系上级代理进行成本设置";
					}
				}
			}
		}
		return null;
	}
	/**
	 * 添加模版
	 */
	public void addProfitTradit(CheckProfitTempTraditBean cptt, UserBean user) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptt.getUnno());
		String name = unitModel.getUnitName();
		Date date = new Date();
		//标准类
		CheckProfitTempTraditModel Model = new CheckProfitTempTraditModel();
		CheckProfitTempTraditLogModel ModelLog = new CheckProfitTempTraditLogModel();
		Model.setUnno(cptt.getUnno());
		Model.setIndustryType("1");
		Model.setCostRate(cptt.getCostRate()/100);
		Model.setFeeAmt(cptt.getFeeAmt());
		Model.setDealAmt(cptt.getDealAmt());
		Model.setCreditBankRate(cptt.getCreditBankRate()/100);
		Model.setMatainType("A");
		Model.setMatainDate(date);
		Model.setMatainUserId(user.getUserID());
		Model.setProfitPercent(cptt.getProfitPercent()/100);
		Model.setCashRate(cptt.getCashRate()/100);
		Model.setCashAmt(cptt.getCashAmt());
		Model.setScanRate(cptt.getScanRate()/100);
		Model.setScanRateUp(cptt.getScanRateUp()/100);
		Model.setTempName(name);
		checkUnitProfitTraditDao.saveObject(Model);
		BeanUtils.copyProperties(Model,ModelLog);
		checkUnitProfitTraditLogDao.saveObject(ModelLog);
		//优惠类
		CheckProfitTempTraditModel Model1 = new CheckProfitTempTraditModel();
		CheckProfitTempTraditLogModel ModelLog1 = new CheckProfitTempTraditLogModel();
		Model1.setUnno(cptt.getUnno());
		Model1.setIndustryType("2");
		Model1.setCostRate(cptt.getCostRate1()/100);
		Model1.setFeeAmt(cptt.getFeeAmt1());
		Model1.setDealAmt(cptt.getDealAmt1());
		Model1.setCreditBankRate(cptt.getCreditBankRate1()/100);
		Model1.setMatainType("A");
		Model1.setMatainDate(date);
		Model1.setMatainUserId(user.getUserID());
		Model1.setProfitPercent(cptt.getProfitPercent()/100);
		Model1.setCashRate(cptt.getCashRate()/100);
		Model1.setCashAmt(cptt.getCashAmt());
		Model1.setScanRate(cptt.getScanRate()/100);
		Model1.setScanRateUp(cptt.getScanRateUp()/100);
		Model1.setTempName(name);
		checkUnitProfitTraditDao.saveObject(Model1);
		BeanUtils.copyProperties(Model1,ModelLog1);
		checkUnitProfitTraditLogDao.saveObject(ModelLog1);
		//减免类
		CheckProfitTempTraditModel Model2 = new CheckProfitTempTraditModel();
		CheckProfitTempTraditLogModel ModelLog2 = new CheckProfitTempTraditLogModel();
		Model2.setUnno(cptt.getUnno());
		Model2.setIndustryType("3");
		Model2.setCostRate(cptt.getCostRate2()/100);
		Model2.setFeeAmt(cptt.getFeeAmt2());
		Model2.setDealAmt(cptt.getDealAmt2());
		Model2.setCreditBankRate(cptt.getCreditBankRate2()/100);
		Model2.setMatainType("A");
		Model2.setMatainDate(date);
		Model2.setMatainUserId(user.getUserID());
		Model2.setProfitPercent(cptt.getProfitPercent()/100);
		Model2.setCashRate(cptt.getCashRate()/100);
		Model2.setCashAmt(cptt.getCashAmt());
		Model2.setScanRate(cptt.getScanRate()/100);
		Model2.setScanRateUp(cptt.getScanRateUp()/100);
		Model2.setTempName(name);
		checkUnitProfitTraditDao.saveObject(Model2);
		BeanUtils.copyProperties(Model2,ModelLog2);
		checkUnitProfitTraditLogDao.saveObject(ModelLog2);

	}
	/**
	 * 删除模版
	 */
	public void Delprofittradit(String TEMPNAME,UserBean user) {
		String sql="update CHECK_TRADIT_PROFITTEMP set MatainType='D' where CTPID in (select CTPID from CHECK_TRADIT_PROFITTEMP where TEMPNAME='"+TEMPNAME+"'  and unno in(select unno from SYS_UNIT where UPPER_UNIT='"+user.getUnNo()+"'))";
		checkUnitProfitTraditDao.executeUpdate(sql);
	}
	/**
	 * 分润模版修改查询
	 * @throws Exception
	 */
	public CheckProfitTempTraditBean queryupdateProfitTradit(CheckProfitTempTraditBean cptt,UserBean user) throws Exception{
		String sql="select * from CHECK_TRADIT_PROFITTEMP " +
					"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		//为总公司
		if("110000".equals(user.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and unno in(select unno from SYS_UNIT where UPPER_UNIT='"+user.getUnNo()+"')";
		}
		List<CheckProfitTempTraditModel> list=checkUnitProfitTraditDao.queryObjectsBySqlList(sql, null,CheckProfitTempTraditModel.class );
		CheckProfitTempTraditBean cpt= new CheckProfitTempTraditBean();
		for(int i=0;i<list.size();i++){
			CheckProfitTempTraditModel cc =list.get(i);
			if(cc.getIndustryType().equals("1")){
				cpt.setCostRate(cc.getCostRate());
				cpt.setFeeAmt(cc.getFeeAmt());
				cpt.setDealAmt(cc.getDealAmt());
				cpt.setCreditBankRate(cc.getCreditBankRate());
				cpt.setCashRate(cc.getCashRate());
				cpt.setCashAmt(cc.getCashAmt());
				cpt.setScanRate(cc.getScanRate());
				cpt.setProfitPercent(cc.getProfitPercent());
			}
			if(cc.getIndustryType().equals("2")){
				cpt.setCostRate1(cc.getCostRate());
				cpt.setFeeAmt1(cc.getFeeAmt());
				cpt.setDealAmt1(cc.getDealAmt());
				cpt.setCreditBankRate1(cc.getCreditBankRate());
				cpt.setProfitPercent1(cc.getProfitPercent());
			}
			if(cc.getIndustryType().equals("3")){
				cpt.setCostRate2(cc.getCostRate());
				cpt.setFeeAmt2(cc.getFeeAmt());
				cpt.setDealAmt2(cc.getDealAmt());
				cpt.setCreditBankRate2(cc.getCreditBankRate());
				cpt.setProfitPercent2(cc.getProfitPercent());
			}
			cpt.setTempName(cc.getTempName());
			cpt.setUnno(cc.getUnno());
		}
		return cpt;
	}

	/**
	 * 传统分润实时生效/下月生效获取
	 * @param type 1:实时生效 2:下月生效
	 * @param cptt
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public CheckProfitTempTraditBean getTraditCheckProfitTempTraditBeanInfo(int type,CheckProfitTempTraditBean cptt) throws UnsupportedEncodingException {
		CheckProfitTempTraditBean bean = new CheckProfitTempTraditBean();
		if(type==1){
			if(DateTools.isFirstDay()){
				String sqlNext="select * from CHECK_TRADIT_PROFITTEMP_W " +
						"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') " +
						" and mataindate>=trunc(sysdate-3,'mm') and mataindate<trunc(sysdate)";
				List<CheckProfitTempTraditWModel> listNext=checkUnitProfitTraditWDao.queryObjectsBySqlList(sqlNext, null,CheckProfitTempTraditWModel.class );
				if(listNext.size()>0){
					bean=setNextMonthCheckProfitTempTraditBean(listNext,null);
				}else{
					String sql="select * from CHECK_TRADIT_PROFITTEMP " +
							"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') ";
					List<CheckProfitTempTraditModel> list=checkUnitProfitTraditDao.queryObjectsBySqlList(sql, null,CheckProfitTempTraditModel.class );
					bean=setNextMonthCheckProfitTempTraditBean(null,list);
				}
			}else{
				String sql="select * from CHECK_TRADIT_PROFITTEMP " +
						"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') ";
				List<CheckProfitTempTraditModel> list=checkUnitProfitTraditDao.queryObjectsBySqlList(sql, null,CheckProfitTempTraditModel.class );
				bean=setNextMonthCheckProfitTempTraditBean(null,list);
			}
		}else if(type==2){
			String sqlNextUse="select * from CHECK_TRADIT_PROFITTEMP_W " +
					"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') " +
					" and mataindate>=trunc(sysdate,'mm')";
			List<CheckProfitTempTraditWModel> listNextUse=checkUnitProfitTraditWDao.queryObjectsBySqlList(sqlNextUse, null,CheckProfitTempTraditWModel.class );
			if(listNextUse.size()>0) {
				bean = setNextMonthCheckProfitTempTraditBean(listNextUse, null);
				return bean;
			}

			if(DateTools.isFirstDay()){
				String sqlNext="select * from CHECK_TRADIT_PROFITTEMP_W " +
						"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') " +
						" and mataindate>=trunc(sysdate-3,'mm') and mataindate<trunc(sysdate)";
				List<CheckProfitTempTraditWModel> listNext=checkUnitProfitTraditWDao.queryObjectsBySqlList(sqlNext, null,CheckProfitTempTraditWModel.class );
				if(listNext.size()>0){
					bean=setNextMonthCheckProfitTempTraditBean(listNext,null);
				}else{
					String sql="select * from CHECK_TRADIT_PROFITTEMP " +
							"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') ";
					List<CheckProfitTempTraditModel> list=checkUnitProfitTraditDao.queryObjectsBySqlList(sql, null,CheckProfitTempTraditModel.class );
					bean=setNextMonthCheckProfitTempTraditBean(null,list);
				}
			}else{
				String sql="select * from CHECK_TRADIT_PROFITTEMP " +
						"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') ";
				List<CheckProfitTempTraditModel> list=checkUnitProfitTraditDao.queryObjectsBySqlList(sql, null,CheckProfitTempTraditModel.class );
				bean=setNextMonthCheckProfitTempTraditBean(null,list);
			}
		}
		return bean;
	}

	public CheckProfitTempTraditBean queryProfitTraditNext(CheckProfitTempTraditBean cptt,UserBean user) throws Exception{
		String sql="select * from CHECK_TRADIT_PROFITTEMP " +
				"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') ";
		String sqlNext="select * from CHECK_TRADIT_PROFITTEMP_W " +
				"where TEMPNAME='"+java.net.URLDecoder.decode(cptt.getTempName(),"UTF-8").trim()+"' and MatainType in ('A','M') " +
				" and mataindate>=trunc(sysdate)";

		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		//为总公司
		if("110000".equals(user.getUnNo())){
		}
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and unno in(select unno from SYS_UNIT where UPPER_UNIT='"+user.getUnNo()+"')";
			sqlNext+=" and unno in(select unno from SYS_UNIT where UPPER_UNIT='"+user.getUnNo()+"')";
		}
		List<CheckProfitTempTraditWModel> listNext=checkUnitProfitTraditWDao.queryObjectsBySqlList(sqlNext, null,CheckProfitTempTraditWModel.class );
		CheckProfitTempTraditBean cpt=new CheckProfitTempTraditBean();
		if(listNext.size()>0){
			cpt=setNextMonthCheckProfitTempTraditBean(listNext, null);
		}else {
			List<CheckProfitTempTraditModel> list = checkUnitProfitTraditDao.queryObjectsBySqlList(sql, null, CheckProfitTempTraditModel.class);
			cpt=setNextMonthCheckProfitTempTraditBean(null, list);
		}
		return cpt;
	}

	private CheckProfitTempTraditBean setNextMonthCheckProfitTempTraditBean(List<CheckProfitTempTraditWModel> listNext, List<CheckProfitTempTraditModel> list) {
		CheckProfitTempTraditBean cpt= new CheckProfitTempTraditBean();
		if(listNext!=null && listNext.size()>0) {
			for (int i = 0; i < listNext.size(); i++) {
				CheckProfitTempTraditWModel cc = listNext.get(i);
				if (cc.getIndustryType().equals("1")) {
					cpt.setCostRate(cc.getCostRate());
					cpt.setFeeAmt(cc.getFeeAmt());
					cpt.setDealAmt(cc.getDealAmt());
					cpt.setCreditBankRate(cc.getCreditBankRate());
					cpt.setCashRate(cc.getCashRate());
					cpt.setCashAmt(cc.getCashAmt());
					cpt.setScanRate(cc.getScanRate());
					cpt.setScanRateUp(cc.getScanRateUp());
					cpt.setProfitPercent(cc.getProfitPercent());
				}
				if (cc.getIndustryType().equals("2")) {
					cpt.setCostRate1(cc.getCostRate());
					cpt.setFeeAmt1(cc.getFeeAmt());
					cpt.setDealAmt1(cc.getDealAmt());
					cpt.setCreditBankRate1(cc.getCreditBankRate());
					cpt.setProfitPercent1(cc.getProfitPercent());
				}
				if (cc.getIndustryType().equals("3")) {
					cpt.setCostRate2(cc.getCostRate());
					cpt.setFeeAmt2(cc.getFeeAmt());
					cpt.setDealAmt2(cc.getDealAmt());
					cpt.setCreditBankRate2(cc.getCreditBankRate());
					cpt.setProfitPercent2(cc.getProfitPercent());
				}
				cpt.setTempName(cc.getTempName());
				cpt.setUnno(cc.getUnno());
			}
		}else if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				CheckProfitTempTraditModel cc = list.get(i);
				if (cc.getIndustryType().equals("1")) {
					cpt.setCostRate(cc.getCostRate());
					cpt.setFeeAmt(cc.getFeeAmt());
					cpt.setDealAmt(cc.getDealAmt());
					cpt.setCreditBankRate(cc.getCreditBankRate());
					cpt.setCashRate(cc.getCashRate());
					cpt.setCashAmt(cc.getCashAmt());
					cpt.setScanRate(cc.getScanRate());
					cpt.setScanRateUp(cc.getScanRateUp());
					cpt.setProfitPercent(cc.getProfitPercent());
				}
				if (cc.getIndustryType().equals("2")) {
					cpt.setCostRate1(cc.getCostRate());
					cpt.setFeeAmt1(cc.getFeeAmt());
					cpt.setDealAmt1(cc.getDealAmt());
					cpt.setCreditBankRate1(cc.getCreditBankRate());
					cpt.setProfitPercent1(cc.getProfitPercent());
				}
				if (cc.getIndustryType().equals("3")) {
					cpt.setCostRate2(cc.getCostRate());
					cpt.setFeeAmt2(cc.getFeeAmt());
					cpt.setDealAmt2(cc.getDealAmt());
					cpt.setCreditBankRate2(cc.getCreditBankRate());
					cpt.setProfitPercent2(cc.getProfitPercent());
				}
				cpt.setTempName(cc.getTempName());
				cpt.setUnno(cc.getUnno());
			}
		}
		return cpt;
	}

	/**
	 * 修改模版
	 * @throws Exception
	 */
	public void updateProfitTradit(CheckProfitTempTraditBean cpt, UserBean user) throws Exception {
		String sql="select * "+
				"from CHECK_TRADIT_PROFITTEMP " +
				"where TEMPNAME='"+java.net.URLDecoder.decode(cpt.getTempname().trim(),"UTF-8").trim()+"'  and MatainType in ('A','M') ";
		String sqlNext="select * "+
				"from CHECK_TRADIT_PROFITTEMP_W " +
				"where TEMPNAME='"+java.net.URLDecoder.decode(cpt.getTempname().trim(),"UTF-8").trim()+"'  and MatainType in ('A','M') " +
				" and mataindate>=trunc(sysdate) ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		//为总公司
		if("110000".equals(user.getUnNo())){
		 }
		//为总公司并且是部门---查询全部
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sqlNext+=" and unno in(select unno from SYS_UNIT where UPPER_UNIT='"+user.getUnNo()+"')";
			sql+=" and unno in(select unno from SYS_UNIT where UPPER_UNIT='"+user.getUnNo()+"')";
		}
		List<CheckProfitTempTraditWModel> list=checkUnitProfitTraditWDao.queryObjectsBySqlList(sqlNext, null,CheckProfitTempTraditWModel.class );
		if(list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				CheckProfitTempTraditWModel cc = list.get(i);
				if (cc.getIndustryType().equals("1")) {
					cc.setCostRate(cpt.getCostRate() / 100);
					cc.setProfitPercent(cpt.getProfitPercent() / 100);
					cc.setFeeAmt(cpt.getFeeAmt());
					cc.setDealAmt(cpt.getDealAmt());
					cc.setCreditBankRate(cpt.getCreditBankRate() / 100);
					cc.setCashRate(cpt.getCashRate() / 100);
					cc.setCashAmt(cpt.getCashAmt());
					cc.setScanRate(cpt.getScanRate() / 100);
					cc.setScanRateUp(cpt.getScanRateUp() / 100);
					//cc.setTempName(cpt.getTempName());
					cc.setMatainType("M");
					cc.setMatainUserId(user.getUserID());
					cc.setMatainDate(new Date());
//					checkUnitProfitTraditWDao.updateObject(cc);
				}
				if (cc.getIndustryType().equals("2")) {
					cc.setCostRate(cpt.getCostRate1() / 100);
					cc.setProfitPercent(cpt.getProfitPercent() / 100);
					cc.setFeeAmt(cpt.getFeeAmt1());
					cc.setDealAmt(cpt.getDealAmt1());
					cc.setCreditBankRate(cpt.getCreditBankRate1() / 100);
					cc.setCashRate(cpt.getCashRate() / 100);
					cc.setCashAmt(cpt.getCashAmt());
					cc.setScanRate(cpt.getScanRate() / 100);
					cc.setScanRateUp(cpt.getScanRateUp() / 100);
					//cc.setTempName(cpt.getTempName());
					cc.setMatainType("M");
					cc.setMatainUserId(user.getUserID());
					cc.setMatainDate(new Date());
//					checkUnitProfitTraditWDao.updateObject(cc);
				}
				if (cc.getIndustryType().equals("3")) {
					cc.setCostRate(cpt.getCostRate2() / 100);
					cc.setProfitPercent(cpt.getProfitPercent() / 100);
					cc.setFeeAmt(cpt.getFeeAmt2());
					cc.setDealAmt(cpt.getDealAmt2());
					cc.setCreditBankRate(cpt.getCreditBankRate2() / 100);
					cc.setCashRate(cpt.getCashRate() / 100);
					cc.setCashAmt(cpt.getCashAmt());
					cc.setScanRate(cpt.getScanRate() / 100);
					cc.setScanRateUp(cpt.getScanRateUp() / 100);
					//cc.setTempName(cpt.getTempName());
					cc.setMatainType("M");
					cc.setMatainUserId(user.getUserID());
					cc.setMatainDate(new Date());
//					checkUnitProfitTraditWDao.updateObject(cc);
				}
//				cc.setUnno(cpt.getUnno());
				checkUnitProfitTraditWDao.updateObject(cc);
			}
		}else{
			List<CheckProfitTempTraditModel> listT=checkUnitProfitTraditDao.queryObjectsBySqlList(sql, null,CheckProfitTempTraditModel.class );
			for (int i = 0; i < listT.size(); i++) {
				CheckProfitTempTraditModel oldModel = listT.get(i);
				CheckProfitTempTraditWModel cc = new CheckProfitTempTraditWModel();
				BeanUtils.copyProperties(oldModel,cc);
				if (cc.getIndustryType().equals("1")) {
					cc.setCostRate(cpt.getCostRate() / 100);
					cc.setProfitPercent(cpt.getProfitPercent() / 100);
					cc.setFeeAmt(cpt.getFeeAmt());
					cc.setDealAmt(cpt.getDealAmt());
					cc.setCreditBankRate(cpt.getCreditBankRate() / 100);
					cc.setCashRate(cpt.getCashRate() / 100);
					cc.setCashAmt(cpt.getCashAmt());
					cc.setScanRate(cpt.getScanRate() / 100);
					cc.setScanRateUp(cpt.getScanRateUp() / 100);
					//cc.setTempName(cpt.getTempName());
					cc.setMatainType("M");
					cc.setMatainUserId(user.getUserID());
					cc.setMatainDate(new Date());
//					checkUnitProfitTraditWDao.saveObject(cc);
				}
				if (cc.getIndustryType().equals("2")) {
					cc.setCostRate(cpt.getCostRate1() / 100);
					cc.setProfitPercent(cpt.getProfitPercent() / 100);
					cc.setFeeAmt(cpt.getFeeAmt1());
					cc.setDealAmt(cpt.getDealAmt1());
					cc.setCreditBankRate(cpt.getCreditBankRate1() / 100);
					cc.setCashRate(cpt.getCashRate() / 100);
					cc.setCashAmt(cpt.getCashAmt());
					cc.setScanRate(cpt.getScanRate() / 100);
					cc.setScanRateUp(cpt.getScanRateUp() / 100);
					//cc.setTempName(cpt.getTempName());
					cc.setMatainType("M");
					cc.setMatainUserId(user.getUserID());
					cc.setMatainDate(new Date());
//					checkUnitProfitTraditWDao.saveObject(cc);
				}
				if (cc.getIndustryType().equals("3")) {
					cc.setCostRate(cpt.getCostRate2() / 100);
					cc.setProfitPercent(cpt.getProfitPercent() / 100);
					cc.setFeeAmt(cpt.getFeeAmt2());
					cc.setDealAmt(cpt.getDealAmt2());
					cc.setCreditBankRate(cpt.getCreditBankRate2() / 100);
					cc.setCashRate(cpt.getCashRate() / 100);
					cc.setCashAmt(cpt.getCashAmt());
					cc.setScanRate(cpt.getScanRate() / 100);
					cc.setScanRateUp(cpt.getScanRateUp() / 100);
					//cc.setTempName(cpt.getTempName());
					cc.setMatainType("M");
					cc.setMatainUserId(user.getUserID());
					cc.setMatainDate(new Date());
//					checkUnitProfitTraditWDao.saveObject(cc);
				}
				cc.setUnno(cpt.getUnno());
				checkUnitProfitTraditWDao.saveObject(cc);
			}
		}
	}
	@Override
	public HSSFWorkbook exportUnitProfitTraditDetailData(
			CheckProfitTempTraditBean cptt, UserBean userBean) {
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptt.getUnno());
		String sql="select b.* from ( select a.*, rownum rm from ( select nvl(round(sum(profit),2),0) profit ,nvl(sum(mda),0) mda ," +
					" nvl(sum(txnamount),0) txnamount,mid,unno,rname,cbrand,count(mda) txncount from (select decode(t.isM35,4,2,5,3,1) isM35_type,"+
					" (select  nvl(case when a.cbrand='1' then case when t.feeamt=0 then  ((mda - txnamount * y.costrate) * y.profitpercent) "+
                    " else case when t.feeamt>0 and txnamount > y.dealamt then  (mda - y.feeamt) * y.profitpercent "+
                    "       else (mda - txnamount * y.costrate) * y.profitpercent  END  end "+
                    " when a.cbrand='2' then (mda - txnamount * y.creditbankrate) * y.profitpercent  end,  0) from check_tradit_profittemp y "+
					" where y.unno = '"+cptt.getUnno()+"'  and y.industrytype = decode(t.isM35,4,'2',5,'3','1')  and y.mataintype != 'D') profit, " +
					" t.mid, a.mda mda,t.unno,t.rname, a.txnamount txnamount ,a.cbrand cbrand"+
					" from bill_merchantinfo t, check_unitdealdetail a where t.mid = a.mid " +
					" and t.unno in ("+childUnno+")";

		if(cptt.getTxnDay()!=null && !"".equals(cptt.getTxnDay()) && cptt.getTxnDay1()!=null && !"".equals(cptt.getTxnDay1())){
			sql+=" and a.txnday>='"+cptt.getTxnDay().replace("-", "")+"' and a.txnday<='"+cptt.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and a.txnday= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" and t.ism35 !=1 and t.maintaintype != 'D' and t.approvestatus = 'Y') group by mid,unno,rname ,cbrand)a ) b ";
		List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlListMap2(sql, null);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("CBRAND");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("MDA");
		excelHeader.add("PROFIT");

		headMap.put("UNNO", "机构号");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("CBRAND", "卡类别(1借卡;2贷卡)");
		headMap.put("TXNCOUNT", "交易笔数");
		headMap.put("TXNAMOUNT", "交易金额");
		headMap.put("MDA", "手续费金额");
		headMap.put("PROFIT", "分润金额");

		return ExcelUtil.export("代理分润明细", list, excelHeader, headMap);
	}

	@Override
	public HSSFWorkbook exportUnitProfitTraditDetailDataScan(
			CheckProfitTempTraditBean cptt, UserBean userBean) {
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptt.getUnno());
		String sql= " select b.* from ( select a.*, rownum rm from ( select nvl(round(sum(profit),2),0) profit ,nvl(sum(mda),0) mda ," +
		" nvl(sum(txnamount),0) txnamount,mid,scanType,unno,rname,count(mda) txncount from (select decode(t.isM35, 4, 2, 5, 3, 1) isM35_type,"+
        " (select (case when txnamt>1000 then nvl(mda - txnamt * y.scanrateUp,0) else nvl(mda - txnamt * y.scanrate,0) end) prof  from check_tradit_profittemp y "+
		" where y.unno = '"+cptt.getUnno()+"'  and y.industrytype = decode(t.isM35,4,'2',5,'3','1')  and y.mataintype != 'D') profit, " +
		" t.mid, a.mda mda,t.unno,t.rname, a.txnamt txnamount,(case when a.txnamt>1000 then '1000以上' else '1000以下' end) scanType  from bill_merchantinfo t, check_wechattxndetail a where t.mid = a.mid " +
		" and a.status='1' and t.unno in ("+childUnno+")";

		if(cptt.getTxnDay()!=null && !"".equals(cptt.getTxnDay()) && cptt.getTxnDay1()!=null && !"".equals(cptt.getTxnDay1())){
			sql+=" and to_char(a.cdate,'yyyyMMdd')>='"+cptt.getTxnDay().replace("-", "")
				+"' and to_char(a.cdate,'yyyyMMdd')<='"+cptt.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and to_char(a.cdate,'yyyyMMdd')= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" and t.ism35 != 1 and t.maintaintype != 'D' and t.approvestatus = 'Y') group by mid,unno,rname,scanType )a ) b ";
		List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlListMap2(sql, null);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("SCANTYPE");
		excelHeader.add("TXNCOUNT");
		excelHeader.add("TXNAMOUNT");
		excelHeader.add("MDA");
		excelHeader.add("PROFIT");

		headMap.put("UNNO", "机构号");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("SCANTYPE", "扫码类型");
		headMap.put("TXNCOUNT", "交易笔数");
		headMap.put("TXNAMOUNT", "交易金额");
		headMap.put("MDA", "手续费金额");
		headMap.put("PROFIT", "分润金额");

		return ExcelUtil.export("代理分润明细", list, excelHeader, headMap);
	}

	@Override
	public DataGridBean queryUnitProfitTraditDetailData(
			CheckProfitTempTraditBean cptt) {
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptt.getUnno());
		String sql="select b.* from ( select a.*, rownum rm from ( select nvl(round(sum(profit),2),0) profit ,nvl(sum(mda),0) mda ," +
					" nvl(sum(txnamount),0) txnamount,mid,unno,rname,cbrand,count(mda) txncount from (select decode(t.isM35,4,2,5,3,1) isM35_type,"+
					" (select  nvl(case when a.cbrand='1' then case when t.feeamt=0 then  ((mda - txnamount * y.costrate) * y.profitpercent) "+
                    " else case when t.feeamt>0 and txnamount > y.dealamt then  (mda - y.feeamt) * y.profitpercent "+
                    "       else (mda - txnamount * y.costrate) * y.profitpercent  END  end "+
                    " when a.cbrand='2' then (mda - txnamount * y.creditbankrate) * y.profitpercent  end,  0) from check_tradit_profittemp y "+
					" where y.unno = '"+cptt.getUnno()+"'  and y.industrytype = decode(t.isM35,4,'2',5,'3','1')  and y.mataintype != 'D') profit, " +
					" t.mid, a.mda mda,t.unno,t.rname, a.txnamount txnamount ,a.cbrand cbrand"+
					" from bill_merchantinfo t, check_unitdealdetail a where t.mid = a.mid " +
					" and t.unno in ("+childUnno+")";

		if(cptt.getTxnDay()!=null && !"".equals(cptt.getTxnDay()) && cptt.getTxnDay1()!=null && !"".equals(cptt.getTxnDay1())){
			sql+=" and a.txnday>='"+cptt.getTxnDay().replace("-", "")+"' and a.txnday<='"+cptt.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and a.txnday= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" and t.ism35 != 1 and t.maintaintype != 'D' and t.approvestatus = 'Y') group by mid,unno,rname ,cbrand)a ) b ";
		String sqlCount ="select  count(*) from ("+sql+")";
		sql+=" where  b.rm > "+(cptt.getPage()-1)*cptt.getRows()+" and b.rm <= "+cptt.getPage()*cptt.getRows();
		//List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlList2(sql, null, cptt.getPage(), cptt.getRows());
		List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlListMap2(sql,null);
		Integer count=checkUnitProfitTraditDao.querysqlCounts2(sqlCount, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}

	@Override
	public DataGridBean queryUnitProfitTraditDetailDataScan(
			CheckProfitTempTraditBean cptt) {
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptt.getUnno());
		String sql= " select b.* from ( select a.*, rownum rm from ( select nvl(round(sum(profit),2),0) profit ,nvl(sum(mda),0) mda ," +
					" nvl(sum(txnamount),0) txnamount,mid,scanType,unno,rname,count(mda) txncount from (select decode(t.isM35, 4, 2, 5, 3, 1) isM35_type,"+
			        " (select (case when txnamt>1000 then  nvl(mda - txnamt * y.scanrateUp,0) else nvl(mda - txnamt * y.scanrate,0) end) prof from check_tradit_profittemp y "+
					" where y.unno = '"+cptt.getUnno()+"'  and y.industrytype = decode(t.isM35,4,'2',5,'3','1')  and y.mataintype != 'D') profit, " +
					" t.mid, a.mda mda,t.unno,t.rname, a.txnamt txnamount,(case when a.txnamt>1000 then 1 else 2 end) scanType  from bill_merchantinfo t, check_wechattxndetail a where t.mid = a.mid " +
					" and a.status='1' and t.unno in ("+childUnno+")";

		if(cptt.getTxnDay()!=null && !"".equals(cptt.getTxnDay()) && cptt.getTxnDay1()!=null && !"".equals(cptt.getTxnDay1())){
			sql+=" and to_char(a.cdate,'yyyyMMdd')>='"+cptt.getTxnDay().replace("-", "")
				+"' and to_char(a.cdate,'yyyyMMdd')<='"+cptt.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and to_char(a.cdate,'yyyyMMdd')= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" and t.ism35 != 1 and t.maintaintype != 'D' and t.approvestatus = 'Y') group by mid,unno,rname,scanType )a ) b ";
		String sqlCount ="select  count(*) from ("+sql+")";
		sql+=" where  b.rm > "+(cptt.getPage()-1)*cptt.getRows()+" and b.rm <= "+cptt.getPage()*cptt.getRows();
		//List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlList2(sql, null, cptt.getPage(), cptt.getRows());
		List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlListMap2(sql,null);
		Integer count=checkUnitProfitTraditDao.querysqlCounts2(sqlCount, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}

	@Override
	public DataGridBean queryUnitProfitTraditSumData(
			CheckProfitTempTraditBean cptt) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptt.getUnno());
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptt.getUnno());
		String sql=" select '"+unitModel.getUnitName()+"' as unitName , nvl(round(sum(profit),2),0) profit ,count(*) txncount,nvl(sum(mda),0) mda ," +
					" nvl(sum(txnamount),0) txnamount ,isM35_type as INDUSTRYTYPE from  (select decode(t.isM35,4,2,5,3,1) isM35_type,(select " +
					" nvl(case  when a.cbrand='1' then case  when t.feeamt=0 then  ((mda - txnamount * y.costrate) * y.profitpercent) " +
                    "            else case  when t.feeamt>0  and txnamount > y.dealamt then  (mda - y.feeamt) * y.profitpercent " +
                    "                else (mda - txnamount * y.costrate) * y.profitpercent END end  " +
                    "        when a.cbrand='2' then (mda - txnamount * y.creditbankrate) * y.profitpercent end, 0) " +
                    " from  check_tradit_profittemp y where y.unno = '"+cptt.getUnno()+"' and y.industrytype = decode(t.isM35,4,'2',5,'3','1')  " +
                    " and y.mataintype != 'D' ) profit, a.mda mda, a.txnamount txnamount, t.unno" +
					" from bill_merchantinfo t, check_unitdealdetail a where t.mid = a.mid " +
					" and t.unno in ("+childUnno+")";
		if(cptt.getTxnDay()!=null && !"".equals(cptt.getTxnDay()) && cptt.getTxnDay1()!=null && !"".equals(cptt.getTxnDay())){
			sql+=" and a.txnday>='"+cptt.getTxnDay().replace("-", "")+"' and a.txnday<='"+cptt.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and a.txnday= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" and t.ism35 != 1 and t.maintaintype != 'D' and t.approvestatus = 'Y') group by isM35_type";
		List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlListMap2(sql, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(list.size());
		return dgb;
	}

	@Override
	public DataGridBean queryUnitProfitTraditSumDataScan(
			CheckProfitTempTraditBean cptt) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptt.getUnno());
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptt.getUnno());
		String sql= " select '"+unitModel.getUnitName()+"' as unitName , nvl(round(sum(profit),2),0) profit ,nvl(sum(mda),0) mda ," +
		" nvl(sum(txnamount),0) txnamount,count(mda) txncount ,isM35_type as INDUSTRYTYPE,scanType from (select decode(t.isM35, 4, 2, 5, 3, 1) isM35_type,"+
        " (select (case when txnamt>1000 then nvl(mda - txnamt * y.scanrateUp,0) else nvl(mda - txnamt * y.scanrate,0) end) prof  from check_tradit_profittemp y "+
		" where y.unno = '"+cptt.getUnno()+"'  and y.industrytype = decode(t.isM35,4,'2',5,'3','1')  and y.mataintype != 'D') profit, " +
		" t.mid, a.mda mda,t.unno,t.rname, a.txnamt txnamount,(case when a.txnamt>1000 then 1 else 2 end) scanType  from bill_merchantinfo t, check_wechattxndetail a where t.mid = a.mid " +
		" and a.status='1' and t.unno in ("+childUnno+")";

		if(cptt.getTxnDay()!=null && !"".equals(cptt.getTxnDay()) && cptt.getTxnDay1()!=null && !"".equals(cptt.getTxnDay1())){
			sql+=" and to_char(a.cdate,'yyyyMMdd')>='"+cptt.getTxnDay().replace("-", "")
				+"' and to_char(a.cdate,'yyyyMMdd')<='"+cptt.getTxnDay1().replace("-", "")+"'" ;
		}else{
			sql+=" and to_char(a.cdate,'yyyyMMdd')= to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" and t.ism35 != 1 and t.maintaintype != 'D' and t.approvestatus = 'Y') group by isM35_type,scanType ";
		List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlListMap2(sql, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(list.size());
		return dgb;
	}

	@Override
	public Map<String,CheckProfitTempTraditModel> getTraditProfitTemplateMap(int type,String unno,Integer unLvl,int subType){
		Map<String, CheckProfitTempTraditModel> result = new HashMap<String, CheckProfitTempTraditModel>();
		List<CheckProfitTempTraditModel> list=new ArrayList<CheckProfitTempTraditModel>();
		if(unLvl>=3) {
			if(type==1) {
				String sql = "select * from check_tradit_profittemp t where t.unno=:unno and t.mataintype!='D'";
				Map map = new HashMap();
				map.put("unno", unno);
				list = checkUnitProfitTraditDao.queryObjectsBySqlList(sql, map,CheckProfitTempTraditModel.class);
				if (list.size() == 0) {
					return new HashMap<String, CheckProfitTempTraditModel>();
				}
			}else if(type==2){
				String sql ="select t.* from check_tradit_profittemp t,sys_unit s" +
						" where t.unno=s.unno and t.mataintype!='D' and t.unno = s.upper_unit and s.unno=:unno";
				Map map = new HashMap();
				map.put("unno",unno);
				list = checkUnitProfitTraditDao.queryObjectsBySqlList(sql,map,CheckProfitTempTraditModel.class);
			}else if(type==3){
				return getSubUnnoMinTraditTemplate(unno);
			}
			for (CheckProfitTempTraditModel traditModel : list) {
				String key = traditModel.getIndustryType();
				result.put(key, traditModel);
			}

			List<CheckProfitTempTraditWModel> listW=new ArrayList<CheckProfitTempTraditWModel>();
			if(subType==2){
				if(DateTools.isFirstDay()){
					// 1号取上个月修改的生效记录
					String sqlUp ="select t.* from check_tradit_profittemp_w t,sys_unit s" +
							" where t.unno=s.unno and t.mataintype!='D' and t.unno = s.upper_unit and s.unno=:unno" +
							" and t.mataindate>=date1 and and t.mataindate<=date2";
					Map mapU = new HashMap();
					mapU.put("unno", unno);
					mapU.put("date1", DateTools.getStartMonth(DateTools.getUpMonth(new Date())));
					mapU.put("date2", DateTools.getEndtMonth(DateTools.getUpMonth(new Date())));
					listW = checkUnitProfitTraditWDao.queryObjectsBySqlList(sqlUp, mapU,CheckProfitTempTraditWModel.class);
				}else{
					// 非1号取当月修改的生效记录
					String sqlUp ="select t.* from check_tradit_profittemp_w t,sys_unit s" +
							" where t.unno=s.unno and t.mataintype!='D' and t.unno = s.upper_unit and s.unno=:unno" +
							" and t.mataindate>=date1 and and t.mataindate<=date2";
					Map mapU = new HashMap();
					mapU.put("unno", unno);
					mapU.put("date1", DateTools.getStartMonth(new Date()));
					mapU.put("date2", DateTools.getEndtMonth(new Date()));
					listW = checkUnitProfitTraditWDao.queryObjectsBySqlList(sqlUp, mapU,CheckProfitTempTraditWModel.class);
				}
				for (CheckProfitTempTraditWModel traditWModel : listW) {
					CheckProfitTempTraditModel newMM=new CheckProfitTempTraditModel();
					BeanUtils.copyProperties(traditWModel,newMM);
					String key = newMM.getIndustryType();
					result.put(key, newMM);
				}
			}
		}
		return result;
	}

	private Map<String,CheckProfitTempTraditModel> getSubUnnoMinTraditTemplate(String unno){
		Map<String,CheckProfitTempTraditModel> result = new HashMap<String, CheckProfitTempTraditModel>();
		String sql ="select t.* from check_tradit_profittemp t,sys_unit s where t.unno=s.unno and t.mataintype!='D' and t.unno = s.unno and s.upper_unit=:unno";
		Map map = new HashMap();
		map.put("unno",unno);
		List<CheckProfitTempTraditModel> list=checkUnitProfitTraditDao.queryObjectsBySqlList(sql,map,CheckProfitTempTraditModel.class);
		if(list.size()==0){
			return new HashMap<String, CheckProfitTempTraditModel>();
		}
		for (CheckProfitTempTraditModel traditModel : list) {
			String key=traditModel.getIndustryType();
			if (result.containsKey(key)) {
				// 获取对应的最小的值
				setOldProfitAndNewProfitMinProfit(key,result,result.get(key),traditModel);
			}else {
				result.put(key, traditModel);
			}
		}
		return result;
	}

	private void setOldProfitAndNewProfitMinProfit(String key,Map map,CheckProfitTempTraditModel oldModel,
												   CheckProfitTempTraditModel newModel){
		if(oldModel.getCostRate()!=null && newModel.getCostRate()!=null){
			if(new BigDecimal(oldModel.getCostRate().toString()).compareTo(new BigDecimal(newModel.getCostRate().toString()))>0){
				oldModel.setCostRate(newModel.getCostRate());
			}
		}

		if(oldModel.getFeeAmt()!=null && newModel.getFeeAmt()!=null){
			if(new BigDecimal(oldModel.getFeeAmt().toString()).compareTo(new BigDecimal(newModel.getFeeAmt().toString()))>0){
				oldModel.setCostRate(newModel.getFeeAmt());
			}
		}

		if(oldModel.getDealAmt()!=null && newModel.getDealAmt()!=null){
			if(new BigDecimal(oldModel.getDealAmt().toString()).compareTo(new BigDecimal(newModel.getDealAmt().toString()))>0){
				oldModel.setCostRate(newModel.getDealAmt());
			}
		}

		if(oldModel.getProfitPercent()!=null && newModel.getProfitPercent()!=null){
			if(new BigDecimal(oldModel.getProfitPercent().toString()).compareTo(new BigDecimal(newModel.getProfitPercent().toString()))>0){
				oldModel.setCostRate(newModel.getProfitPercent());
			}
		}

		if(oldModel.getCreditBankRate()!=null && newModel.getCreditBankRate()!=null){
			if(new BigDecimal(oldModel.getCreditBankRate().toString()).compareTo(new BigDecimal(newModel.getCreditBankRate().toString()))>0){
				oldModel.setCostRate(newModel.getCreditBankRate());
			}
		}

		if(oldModel.getCashRate()!=null && newModel.getCashRate()!=null){
			if(new BigDecimal(oldModel.getCashRate().toString()).compareTo(new BigDecimal(newModel.getCashRate().toString()))>0){
				oldModel.setCostRate(newModel.getCashRate());
			}
		}

		if(oldModel.getCashAmt()!=null && newModel.getCashAmt()!=null){
			if(new BigDecimal(oldModel.getCashAmt().toString()).compareTo(new BigDecimal(newModel.getCashAmt().toString()))>0){
				oldModel.setCostRate(newModel.getCashAmt());
			}
		}

		if(oldModel.getScanRate()!=null && newModel.getScanRate()!=null){
			if(new BigDecimal(oldModel.getScanRate().toString()).compareTo(new BigDecimal(newModel.getScanRate().toString()))>0){
				oldModel.setCostRate(newModel.getScanRate());
			}
		}

	}

	@Override
	public DataGridBean getTraditProfitLog(CheckProfitTempTraditBean cptt,UserBean userBean){
		String sql = "select p.unno,p.TEMPNAME,to_char(p.MATAINDATE,'yyyy-MM-dd') MATAINDATE,to_char(p.ENDDATE,'yyyy-MM-dd') ENDDATE " +
				" from check_tradit_profittemp_LOG p where 1=1 ";
		Map params=new HashMap();
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
		if("110000".equals(userBean.getUnNo())){
			//为总公司
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			//为总公司并且是部门---查询全部
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
		}else{
			sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
			params.put("unno",userBean.getUnNo());
		}
		if(cptt.getUnno()!=null&&!"".equals(cptt.getUnno())){
			sql+= " and p.unno =:unno1 ";
			params.put("unno1",cptt.getUnno());
		}
		if(cptt!=null&&cptt.getTempName()!=null&&!cptt.getTempName().equals("")){
			sql+=" and p.TEMPNAME=:tempName ";
			params.put("tempName",cptt.getTempName());
		}
		sql+=" group by p.unno,p.TEMPNAME,to_char(p.MATAINDATE,'yyyy-MM-dd'),to_char(p.ENDDATE,'yyyy-MM-dd') ";
		String sqlCount = "select count(1) from ("+sql+")";
		sql+=" order by MATAINDATE desc";
		List<Map<String, String>> list = checkUnitProfitTraditDao.queryObjectsBySqlList(sql, params, cptt.getPage(), cptt.getRows());
		Integer counts = checkUnitProfitTraditDao.querysqlCounts2(sqlCount, params);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}

	@Override
	public CheckProfitTempTraditBean queryProfitTraditLogDetail(CheckProfitTempTraditBean cptt){
		CheckProfitTempTraditBean cpt = new CheckProfitTempTraditBean();
		String sql = "select * " +
				" from check_tradit_profittemp_LOG p where 1=1 " +
				" and p.unno=:unno" +
				" and p.mataindate>=to_date(:startDate,'yyyy-MM-dd') and p.mataindate<to_date(:startDate,'yyyy-MM-dd')+1 ";
		Map params = new HashMap();
		params.put("unno",cptt.getUnno());
		params.put("startDate",cptt.getTxnDay());
		List<Map<String, Object>> list = checkUnitProfitTraditDao.queryObjectsBySqlListMap2(sql, params);
		for (int i = 0; i < list.size(); i++) {
			String INDUSTRYTYPE=String.valueOf(list.get(i).get("INDUSTRYTYPE"));
			String TEMPNAME=String.valueOf(list.get(i).get("TEMPNAME"));
			if(StringUtils.isEmpty(INDUSTRYTYPE)){
				continue;
			}
			if (INDUSTRYTYPE.equals("1")) {
				cpt.setCostRate(Double.parseDouble(String.valueOf(list.get(i).get("COSTRATE"))));
				cpt.setFeeAmt(Double.parseDouble(String.valueOf(list.get(i).get("FEEAMT"))));
				cpt.setDealAmt(Double.parseDouble(String.valueOf(list.get(i).get("DEALAMT"))));
				cpt.setCreditBankRate(Double.parseDouble(String.valueOf(list.get(i).get("CREDITBANKRATE"))));
				cpt.setCashRate(Double.parseDouble(String.valueOf(list.get(i).get("CASHRATE"))));
				cpt.setCashAmt(Double.parseDouble(String.valueOf(list.get(i).get("CASHAMT"))));
				cpt.setScanRate(Double.parseDouble(String.valueOf(list.get(i).get("SCANRATE"))));
				if(list.get(i).get("SCANRATEUP")!=null) {
					cpt.setScanRateUp(Double.parseDouble(String.valueOf(list.get(i).get("SCANRATEUP"))));
				}
				cpt.setProfitPercent(Double.parseDouble(String.valueOf(list.get(i).get("PROFITPERCENT"))));
			}
			if (INDUSTRYTYPE.equals("2")) {
				cpt.setCostRate1(Double.parseDouble(String.valueOf(list.get(i).get("COSTRATE"))));
				cpt.setFeeAmt1(Double.parseDouble(String.valueOf(list.get(i).get("FEEAMT"))));
				cpt.setDealAmt1(Double.parseDouble(String.valueOf(list.get(i).get("DEALAMT"))));
				cpt.setCreditBankRate1(Double.parseDouble(String.valueOf(list.get(i).get("CREDITBANKRATE"))));
				cpt.setProfitPercent1(Double.parseDouble(String.valueOf(list.get(i).get("PROFITPERCENT"))));
			}
			if (INDUSTRYTYPE.equals("3")) {
				cpt.setCostRate2(Double.parseDouble(String.valueOf(list.get(i).get("COSTRATE"))));
//				cpt.setFeeAmt2(Double.parseDouble(String.valueOf(list.get(i).get("FEEAMT"))));
//				cpt.setDealAmt2(Double.parseDouble(String.valueOf(list.get(i).get("DEALAMT"))));
				cpt.setCreditBankRate2(Double.parseDouble(String.valueOf(list.get(i).get("CREDITBANKRATE"))));
				cpt.setProfitPercent2(Double.parseDouble(String.valueOf(list.get(i).get("PROFITPERCENT"))));
			}
			cpt.setTempName(TEMPNAME);
			cpt.setUnno(String.valueOf(list.get(i).get("UNNO")));
		}
		return cpt;
	}

    @Override
    public List<CheckProfitTempTraditBean> exportProfitTraditLog(CheckProfitTempTraditBean cptt,UserBean userBean){
	    List<CheckProfitTempTraditBean> list=new ArrayList<CheckProfitTempTraditBean>();
        String sql = "select p.unno,p.TEMPNAME,to_char(p.MATAINDATE,'yyyy-MM-dd') MATAINDATE,to_char(p.ENDDATE,'yyyy-MM-dd') ENDDATE " +
                " from check_tradit_profittemp_LOG p where 1=1 ";
        Map params=new HashMap();
        UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, userBean.getUnNo());
        if("110000".equals(userBean.getUnNo())){
            //为总公司
        }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
            //为总公司并且是部门---查询全部
            UnitModel parent = unitModel.getParent();
            if("110000".equals(parent.getUnNo())){
            }
        }else{
            sql+=" and p.unno in(select unno from SYS_UNIT where UPPER_UNIT=:unno) " ;
            params.put("unno",userBean.getUnNo());
        }
        if(cptt.getUnno()!=null&&!"".equals(cptt.getUnno())){
            sql+= " and p.unno =:unno1 ";
            params.put("unno1",cptt.getUnno());
        }
        if(cptt!=null&&cptt.getTempName()!=null&&!cptt.getTempName().equals("")){
            sql+=" and p.TEMPNAME=:tempName ";
            params.put("tempName",cptt.getTempName());
        }
        sql+=" group by p.unno,p.TEMPNAME,to_char(p.MATAINDATE,'yyyy-MM-dd'),to_char(p.ENDDATE,'yyyy-MM-dd') " +
                " order by to_char(p.MATAINDATE,'yyyy-MM-dd') desc";
        List<Map<String, Object>> listTemp = checkUnitProfitTraditDao.executeSql2(sql, params);
        CheckProfitTempTraditBean x = new CheckProfitTempTraditBean();
        for (Map<String, Object> t : listTemp) {
            String unnoA = String.valueOf(t.get("UNNO"));
            String matainDate = String.valueOf(t.get("MATAINDATE"));
            String endDate = t.get("ENDDATE")==null?null:String.valueOf(t.get("ENDDATE"));
            x.setUnno(unnoA);
            x.setTxnDay(matainDate);
            x.setTxnDay1(endDate);
			CheckProfitTempTraditBean bean = queryProfitTraditLogDetail(x);
			bean.setTxnDay(matainDate);
			bean.setTxnDay1(endDate);
			bean.setUnno(unnoA);
            list.add(bean);
        }
        return list;
    }

	@Override
	public DataGridBean queryTraditionCashTransferSum(CheckProfitTempTraditBean cptt) {
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, cptt.getUnno());
		String childUnno=merchantInfoService.queryUnitUnnoUtil(cptt.getUnno());
		String sql = getSytCashSql(cptt, unitModel, childUnno);
		List<Map<String,Object>> list= checkUnitProfitTraditDao.queryObjectsBySqlListMap2(sql, null);
		DataGridBean dgb = new DataGridBean();
		dgb.setRows(list);
		dgb.setTotal(list.size());
		return dgb;
	}

	/**
	 * 传统分润管理->提现转账分润汇总sql
	 * @param cptt
	 * @param unitModel
	 * @param childUnno
	 * @return
	 */
	private String getSytCashSql(CheckProfitTempTraditBean cptt, UnitModel unitModel, String childUnno) {
		String month=null;
		String start = StringUtils.isNotEmpty(cptt.getTxnDay())?cptt.getTxnDay().replace("-", ""):"";
		String end = StringUtils.isNotEmpty(cptt.getTxnDay1())?cptt.getTxnDay1().replace("-", ""):"";
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end) && start.substring(0,6).equals(end.substring(0,6))){
			month=start.substring(4,6);
		}else{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			month=simpleDateFormat.format(new Date()).substring(4,6);
		}
		String sql = " select '"+unitModel.getUnitName()+"' as unitName ,a.* "
				+" from (select sum(profitone) profit,nvl(sum(cashfee), 0) cashfee,"
				+" nvl(sum(cashamt), 0) cashamt,count(cashfee) TXNCOUNT,cashmode"
				+" from (select a.*,(select a.cashfee - a.cashamt * m.cashrate - m.cashamt"
				+" from check_tradit_profittemp m where m.unno in ("+childUnno+") and rownum <= 1) profitone"
				+" from check_cash_"+month+" a, bill_merchantinfo t"
				+" where a.mid = t.mid and t.maintaintype != 'D' and t.approvestatus = 'Y'"
				+" and a.cashmode in (1, 4, 5) and t.ism35 != 1 and t.unno in ("+childUnno+")";
		
		if(cptt.getTxnDay()!=null && !"".equals(cptt.getTxnDay()) && cptt.getTxnDay1()!=null && !"".equals(cptt.getTxnDay1())){
			sql+=" and a.cashday>='"+start+"' and a.cashday<='"+end+"'" ;
		}else{
			sql+=" and a.cashday =to_char(sysdate-1,'yyyyMMdd')";
		}
		sql+=" ) group by cashmode)a  ";
		return sql;
	}
}
