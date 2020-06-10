package com.hrt.biz.check.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckCashDao;
import com.hrt.biz.check.entity.pagebean.CheckCashBean;
import com.hrt.biz.check.service.CheckCashService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;

public class CheckCashServiceImpl implements CheckCashService {

	private CheckCashDao checkCashDao;
	private IMerchantInfoService merchantInfoService;

	public CheckCashDao getCheckCashDao() {
		return checkCashDao;
	}

	public void setCheckCashDao(CheckCashDao checkCashDao) {
		this.checkCashDao = checkCashDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	@Override
	public DataGridBean queryCashListInfo(CheckCashBean checkCashBean,
			UserBean user) {
		String sql="select c.* from bill_merchantinfo m,check_cash c where m.mid=c.mid ";
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 = 1 ";
//		}
		if(user.getUnitLvl()==0){
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql+="and m.unno in("+childUnno+") ";
		}
		if(checkCashBean.getMid()!=null && !"".equals(checkCashBean.getMid())){
			sql+=" and c.mid='"+checkCashBean.getMid()+"' ";
		}
		if(checkCashBean.getCashDateStart()!=null && checkCashBean.getCashDateEnd()!=null 
				&& !"".equals(checkCashBean.getCashDateStart()) && !"".equals(checkCashBean.getCashDateEnd())){
			sql+=" and c.cashday>='"+checkCashBean.getCashDateStart().replaceAll("-", "")+"' " +
					" and c.cashday<='"+checkCashBean.getCashDateEnd().replaceAll("-", "")+"'";
		}else{
			sql+=" and c.cashday=to_char(sysdate-1,'yyyyMMdd') ";
		}
		String sqlCount="select count(*) from ("+sql+")";
		String sqlSum="select nvl(sum(cashamt),0) cashamt ,nvl(sum(cashfee),0) cashfee from ("+sql+")";
		Integer count=checkCashDao.querysqlCounts2(sqlCount, null);
		List<Map<String,Object>> listSum=checkCashDao.queryObjectsBySqlObject(sqlSum);
		List<Map<String,Object>> list =checkCashDao.queryObjectsBySqlList2(sql, null, checkCashBean.getPage(), checkCashBean.getRows());
		list.add(0, listSum.get(0));
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count);
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public HSSFWorkbook exportCashData(CheckCashBean checkCashBean,
			UserBean user) {
		String sql="select c.mid ,m.rname ,c.cashdate,c.cashamt,c.cashfee from bill_merchantinfo m,check_cash c where m.mid=c.mid ";
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 = 1 ";
//		}
		if(user.getUnitLvl()==0){
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql+="and m.unno in("+childUnno+") ";
		}
		if(checkCashBean.getMid()!=null && !"".equals(checkCashBean.getMid())){
			sql+=" and c.mid='"+checkCashBean.getMid()+"' ";
		}
		if(checkCashBean.getCashDateStart()!=null && checkCashBean.getCashDateEnd()!=null 
				&& !"".equals(checkCashBean.getCashDateStart()) && !"".equals(checkCashBean.getCashDateEnd())){
			sql+=" and c.cashday>='"+checkCashBean.getCashDateStart().replaceAll("-", "")+"' " +
					" and c.cashday<='"+checkCashBean.getCashDateEnd().replaceAll("-", "")+"'";
		}else{
			sql+=" and c.cashday=to_char(sysdate-1,'yyyyMMdd') ";
		}
		List<Map<String,Object>> list =checkCashDao.queryObjectsBySqlList(sql);
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();
		
		excelHeader.add("MID");
		excelHeader.add("RNAME");
		excelHeader.add("CASHDATE");
		excelHeader.add("CASHAMT");
		excelHeader.add("CASHFEE");
		
		headMap.put("MID", "商户编号");
		headMap.put("RNAME", "商户名称");
		headMap.put("CASHDATE", "提现日期");
		headMap.put("CASHAMT", "提现金额");
		headMap.put("CASHFEE", "提现手续费");

		return ExcelUtil.export("提现记录", list, excelHeader, headMap);
	}
	
	
}
