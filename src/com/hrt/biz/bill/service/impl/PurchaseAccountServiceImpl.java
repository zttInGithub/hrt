package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IPurchaseAccountDao;
import com.hrt.biz.bill.dao.IPurchaseOrderDao;
import com.hrt.biz.bill.entity.model.PurchaseAccountModel;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseAccountBean;
import com.hrt.biz.bill.entity.pagebean.PurchaseOrderBean;
import com.hrt.biz.bill.service.IPurchaseAccountService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class PurchaseAccountServiceImpl implements IPurchaseAccountService {

		private IPurchaseAccountDao purchaseAccountDao;
		private IPurchaseOrderDao purchaseOrderDao;

		public IPurchaseOrderDao getPurchaseOrderDao() {
			return purchaseOrderDao;
		}

		public void setPurchaseOrderDao(IPurchaseOrderDao purchaseOrderDao) {
			this.purchaseOrderDao = purchaseOrderDao;
		}

		public IPurchaseAccountDao getPurchaseAccountDao() {
			return purchaseAccountDao;
		}

		public void setPurchaseAccountDao(IPurchaseAccountDao purchaseAccountDao) {
			this.purchaseAccountDao = purchaseAccountDao;
		}

		@Override
		public DataGridBean listPurchaseAccount(PurchaseAccountBean purchaseAccountBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchaseaccount where accountApproveStatus!='D' ";
			sqlCount = "select count(*) from bill_purchaseaccount where accountApproveStatus!='D' ";
			if(purchaseAccountBean.getAccountOrderID()!=null&&!"".equals(purchaseAccountBean.getAccountOrderID())){
				sql += " and accountOrderID = :accountOrderID ";
				sqlCount += " and accountOrderID = :accountOrderID ";
				map.put("accountOrderID", purchaseAccountBean.getAccountOrderID());
			}
			if(purchaseAccountBean.getAccountStatus()!=null&&!"".equals(purchaseAccountBean.getAccountStatus())){
				sql += " and accountStatus = :accountStatus ";
				sqlCount += " and accountStatus = :accountStatus ";
				map.put("accountStatus", purchaseAccountBean.getAccountStatus());
			}
			if(purchaseAccountBean.getAccountStatuss()!=null&&!"".equals(purchaseAccountBean.getAccountStatuss())){
				sql += " and accountStatus in ("+purchaseAccountBean.getAccountStatuss()+") ";
				sqlCount += " and accountStatus in ("+purchaseAccountBean.getAccountStatuss()+") ";
			}
			//创建日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseAccountBean.getAccountCdate()!=null&&!"".equals(purchaseAccountBean.getAccountCdate())){
				sql += " and accountCdate >= to_date('"+sdf.format(purchaseAccountBean.getAccountCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and accountCdate >= to_date('"+sdf.format(purchaseAccountBean.getAccountCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseAccountBean.getAccountCdateEnd()!=null&&!"".equals(purchaseAccountBean.getAccountCdateEnd())){
				sql += " and accountCdate <= to_date('"+sdf.format(purchaseAccountBean.getAccountCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and accountCdate <= to_date('"+sdf.format(purchaseAccountBean.getAccountCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += " order by paid desc ";
			List<PurchaseAccountModel> list = purchaseAccountDao.queryObjectsBySqlLists(sql, map, purchaseAccountBean.getPage(), purchaseAccountBean.getRows(),PurchaseAccountModel.class);
			Integer counts = purchaseAccountDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}

		@Override
		public Integer savePurchaseAccount(PurchaseAccountBean purchaseAccountBean, UserBean user) {
			Double accountAmt = purchaseAccountBean.getAccountAmt();//付款/退款金额
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? ", new Object[]{purchaseAccountBean.getPoid()});
			//1-付款 2-退款
			if(purchaseAccountBean.getAccountType()==1){
				//判断金额是否超出总金额
				Double orderAmt = purchaseOrderModel.getOrderAmt();
				Double orderpayAmt = purchaseOrderModel.getOrderpayAmt();
				if(orderAmt<accountAmt+orderpayAmt){
					return 0;
				}
				
				PurchaseAccountModel purchaseAccountModel = new PurchaseAccountModel();
				BeanUtils.copyProperties(purchaseAccountBean, purchaseAccountModel);
				purchaseAccountModel.setAccountOrderID(purchaseOrderModel.getOrderID());
				purchaseAccountModel.setAccountStatus(1);
				purchaseAccountModel.setAccountCdate(new Date());
				purchaseAccountModel.setAccountCby(user.getLoginName());
				purchaseAccountModel.setAccountApproveStatus("A");
				
				purchaseAccountDao.saveObject(purchaseAccountModel);
				
				return 1;
			}else{
				Integer amt = 0;//退款总金额
				Integer amt2 = 0;//已退款的金额
				Map<String,Object> map = new HashMap<String, Object>();
				//退款总数
				String sql = "select nvl(sum(detailAmt),0) amt from bill_PurchaseDetail where detailOrderID=:detailOrderID and orderType=4 and detailStatus in(7,8) ";
				map.put("detailOrderID", purchaseOrderModel.getOrderID());
				List<Map<String,Object>> list = purchaseOrderDao.executeSql2(sql,map);
				//已退
				String sql2 = "select nvl(sum(accountAmt),0) amt from bill_PurchaseAccount where accountOrderID=:detailOrderID and accountType=2 and accountStatus =9 ";
				List<Map<String,Object>> list2 = purchaseOrderDao.executeSql2(sql2,map);
				//已付款金额
				String sql3 = "select orderpayAmt from bill_PurchaseOrder where OrderID=:detailOrderID ";
				List<Map<String,Object>> list3 = purchaseOrderDao.executeSql2(sql3,map);
				if(list.size()>0){
					if(list2.size()>0){
						amt2 = -((BigDecimal) list2.get(0).get("AMT")).intValue();
					}
					amt = -((BigDecimal) list.get(0).get("AMT")).intValue();
				}
				if(accountAmt>amt-amt2){
					return 2;
				}
				Integer amt3 = ((BigDecimal) list3.get(0).get("ORDERPAYAMT")).intValue();
				if(accountAmt>amt3-amt2){
					return 2;
				}
				PurchaseAccountModel purchaseAccountModel = new PurchaseAccountModel();
				BeanUtils.copyProperties(purchaseAccountBean, purchaseAccountModel);
				purchaseAccountModel.setAccountOrderID(purchaseOrderModel.getOrderID());
				purchaseAccountModel.setAccountStatus(1);
				purchaseAccountModel.setAccountCdate(new Date());
				purchaseAccountModel.setAccountCby(user.getLoginName());
				purchaseAccountModel.setAccountApproveStatus("A");
				purchaseAccountModel.setAccountAmt(-accountAmt);
				
				purchaseAccountDao.saveObject(purchaseAccountModel);
				
				return 1;
			}
			
		}

		@Override
		public Integer updatePurchaseAccountStatus(PurchaseAccountBean purchaseAccountBean, UserBean user) {
			PurchaseAccountModel purchaseAccountModel = purchaseAccountDao.queryObjectByHql("from PurchaseAccountModel where paid=? and accountApproveStatus!='D' ", new Object[]{purchaseAccountBean.getPaid()});
			Double accountAmt = purchaseAccountModel.getAccountAmt();
			//修改采购总表的已付款金额及状态 4发票待审;
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and approveStatus!='D'", new Object[]{purchaseAccountModel.getPoid()});
			Double orderAmt = purchaseOrderModel.getOrderAmt();//订单总额
			Double orderpayAmt = purchaseOrderModel.getOrderpayAmt();//已付金额
			if(orderAmt>=accountAmt+orderpayAmt){
			}else{
				return 0;
			}
			purchaseAccountModel.setAccountStatus(2);
			purchaseAccountDao.updateObject(purchaseAccountModel);
			return 1;
		}
		
		/**
		 * 审核通过
		 */
		@Override
		public Integer updatePurchaseAccountY(PurchaseAccountBean purchaseAccountBean, UserBean user) {
			PurchaseAccountModel purchaseAccountModel = purchaseAccountDao.queryObjectByHql("from PurchaseAccountModel where paid=? and accountApproveStatus!='D' ", new Object[]{purchaseAccountBean.getPaid()});
			if(purchaseAccountModel!=null){
				//如果采购付款单已全部通过审核，修改采购单状态 5已结款
				PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=?", new Object[]{purchaseAccountModel.getPoid()});
				if(purchaseAccountModel.getAccountType()==1){
					Double accountAmt = purchaseAccountModel.getAccountAmt();
					Double orderAmt = purchaseOrderModel.getOrderAmt();
					Double orderpayAmt = purchaseOrderModel.getOrderpayAmt();
					if(orderAmt>=accountAmt+orderpayAmt){
						purchaseOrderModel.setOrderpayAmt(accountAmt+orderpayAmt);
						purchaseOrderModel.setStatus("4");
					}else{
						return 2;
					}
					//如果全部付款通过，则修改状态
					if((purchaseOrderModel.getOrderAmt()-purchaseOrderModel.getOrderpayAmt())<0.00000001){
						purchaseOrderModel.setStatus("5");
					}
					purchaseOrderDao.updateObject(purchaseOrderModel);
					
					purchaseAccountModel.setAccountStatus(9);
					purchaseAccountModel.setAccountApprover(user.getLoginName());
					purchaseAccountModel.setAccountApproveDate(new Date());
					purchaseAccountModel.setAccountApproveStatus("M");
					
					purchaseAccountDao.updateObject(purchaseAccountModel);
					return 1;
				}else{
					Integer amt = 0;
					Integer amt2 = 0;
					Double accountAmt = purchaseAccountModel.getAccountAmt();
					Map<String,Object> map = new HashMap<String, Object>();
					//退款总数
					String sql = "select sum(detailAmt) amt from bill_PurchaseDetail where detailOrderID=:detailOrderID and orderType=4 and detailStatus in(7,8) ";
					map.put("detailOrderID", purchaseOrderModel.getOrderID());
					List<Map<String,Object>> list = purchaseOrderDao.executeSql2(sql,map);
					//已退
					String sql2 = "select nvl(sum(accountAmt),0) amt from bill_PurchaseAccount where accountOrderID=:detailOrderID and accountType=2 and accountStatus =9 ";
					List<Map<String,Object>> list2 = purchaseOrderDao.executeSql2(sql2,map);
					//已付款
					String sql3 = "select orderpayAmt from bill_PurchaseOrder where OrderID=:detailOrderID ";
					List<Map<String,Object>> list3 = purchaseOrderDao.executeSql2(sql3,map);
					if(list.size()>0){
						if(list2.size()>0){
							amt2 = -((BigDecimal) list2.get(0).get("AMT")).intValue();
						}
						amt = -((BigDecimal) list.get(0).get("AMT")).intValue();
					}
					if(accountAmt>amt-amt2){
						return 3;
					}
					Integer amt3 = ((BigDecimal) list3.get(0).get("ORDERPAYAMT")).intValue();
					if(accountAmt>amt3-amt2){
						return 3;
					}
					purchaseOrderModel.setStatus("4");
					purchaseOrderModel.setOrderpayAmt(purchaseOrderModel.getOrderpayAmt()+accountAmt);
					
					purchaseOrderDao.updateObject(purchaseOrderModel);
					
					purchaseAccountModel.setAccountStatus(9);
					purchaseAccountModel.setAccountApprover(user.getLoginName());
					purchaseAccountModel.setAccountApproveDate(new Date());
					purchaseAccountModel.setAccountApproveStatus("M");
					
					purchaseAccountDao.updateObject(purchaseAccountModel);
					return 1;
				}
				
			}
			return 0;
		}
		
		/**
		 * 审核退回
		 */
		@Override
		public Integer updatePurchaseAccountK(PurchaseAccountBean purchaseAccountBean, UserBean user) {
			PurchaseAccountModel purchaseAccountModel = purchaseAccountDao.queryObjectByHql("from PurchaseAccountModel where paid=? and accountApproveStatus!='D' ", new Object[]{purchaseAccountBean.getPaid()});
			if(purchaseAccountModel!=null){
				purchaseAccountModel.setAccountStatus(1);
				purchaseAccountModel.setAccountApprover(user.getLoginName());
				purchaseAccountModel.setAccountApproveDate(new Date());
				purchaseAccountModel.setAccountApproveStatus("K");
				purchaseAccountModel.setAccountApproveNote(purchaseAccountBean.getAccountApproveNote());
				
				purchaseAccountDao.updateObject(purchaseAccountModel);
				
				return 1;
			}
			return 0;
		}

		@Override
		public Integer updatePurchaseAccount(PurchaseAccountBean purchaseAccountBean, UserBean user) {
			PurchaseAccountModel purchaseAccountModel = purchaseAccountDao.queryObjectByHql("from PurchaseAccountModel where paid=? and accountApproveStatus!='D' ", new Object[]{purchaseAccountBean.getPaid()});
			
			if(purchaseAccountModel!=null){
				Double accountAmt = purchaseAccountBean.getAccountAmt();
				//判断金额是否超出总金额
				PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=?", new Object[]{purchaseAccountModel.getPoid()});
				Double orderAmt = purchaseOrderModel.getOrderAmt();
				Double orderpayAmt = purchaseOrderModel.getOrderpayAmt();
				if(orderAmt<=accountAmt+orderpayAmt){
					return 2;
				}
				
				purchaseAccountModel.setAccountAmt(accountAmt);
				purchaseAccountModel.setPayType(purchaseAccountBean.getPayType());
				purchaseAccountModel.setAccountRemark(purchaseAccountBean.getAccountRemark());
				purchaseAccountModel.setAccountApproveStatus("M");
				
				purchaseAccountDao.updateObject(purchaseAccountModel);
				
				return 1;
			}
			return 0;
		}

		@Override
		public List<Map<String, Object>> exportPurchaseAccount(PurchaseAccountBean purchaseAccountBean, UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select a.*,b.orderAmt,b.orderpayAmt from bill_purchaseaccount a,bill_PurchaseOrder b where a.poid=b.poid and a.accountApproveStatus!='D' ";
			if(purchaseAccountBean.getAccountOrderID()!=null&&!"".equals(purchaseAccountBean.getAccountOrderID())){
				sql += " and a.accountOrderID = :accountOrderID ";
				map.put("accountOrderID", purchaseAccountBean.getAccountOrderID());
			}
			if(purchaseAccountBean.getAccountStatus()!=null&&!"".equals(purchaseAccountBean.getAccountStatus())){
				sql += " and a.accountStatus = :accountStatus ";
				map.put("accountStatus", purchaseAccountBean.getAccountStatus());
			}
			if(purchaseAccountBean.getAccountStatuss()!=null&&!"".equals(purchaseAccountBean.getAccountStatuss())){
				sql += " and a.accountStatus in ("+purchaseAccountBean.getAccountStatuss()+") ";
			}
			//创建日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseAccountBean.getAccountCdate()!=null&&!"".equals(purchaseAccountBean.getAccountCdate())){
				sql += " and a.accountCdate >= to_date('"+sdf.format(purchaseAccountBean.getAccountCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseAccountBean.getAccountCdateEnd()!=null&&!"".equals(purchaseAccountBean.getAccountCdateEnd())){
				sql += " and a.accountCdate <= to_date('"+sdf.format(purchaseAccountBean.getAccountCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += " order by a.paid desc ";
			
			List<Map<String, Object>> list = purchaseAccountDao.queryObjectsBySqlListMap2(sql, map);
			
			return list;
		}

		@Override
		public Integer deletePurchaseAccount(Integer paid, UserBean user) {
			String sql = "from PurchaseAccountModel where paid = ? and accountApproveStatus!='D'";
			PurchaseAccountModel purchaseAccountModel = purchaseAccountDao.queryObjectByHql(sql, new Object[]{paid});
			if(purchaseAccountModel!=null){
				purchaseAccountModel.setAccountApproveStatus("D");
				
				purchaseAccountDao.updateObject(purchaseAccountModel);
				return 1;
			}else{
				return 0;
			}
		}
		
}
