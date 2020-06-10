package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IPurchaseInvoiceDao;
import com.hrt.biz.bill.dao.IPurchaseOrderDao;
import com.hrt.biz.bill.entity.model.PurchaseAccountModel;
import com.hrt.biz.bill.entity.model.PurchaseInvoiceModel;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseInvoiceBean;
import com.hrt.biz.bill.service.IPurchaseInvoiceService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class PurchaseInvoiceServiceImpl implements IPurchaseInvoiceService {

		private IPurchaseInvoiceDao purchaseInvoiceDao;
		private IPurchaseOrderDao purchaseOrderDao;

		public IPurchaseOrderDao getPurchaseOrderDao() {
			return purchaseOrderDao;
		}

		public void setPurchaseOrderDao(IPurchaseOrderDao purchaseOrderDao) {
			this.purchaseOrderDao = purchaseOrderDao;
		}

		public IPurchaseInvoiceDao getPurchaseInvoiceDao() {
			return purchaseInvoiceDao;
		}

		public void setPurchaseInvoiceDao(IPurchaseInvoiceDao purchaseInvoiceDao) {
			this.purchaseInvoiceDao = purchaseInvoiceDao;
		}

		@Override
		public DataGridBean listPurchaseInvoice(PurchaseInvoiceBean purchaseInvoiceBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchaseinvoice where invoiceApproveStatus!='D' ";
			sqlCount = "select count(*) from bill_purchaseinvoice where invoiceApproveStatus!='D' ";
			if(purchaseInvoiceBean.getInvoiceOrderID()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceOrderID())){
				sql += " and invoiceOrderID = :invoiceOrderID ";
				sqlCount += " and invoiceOrderID = :invoiceOrderID ";
				map.put("invoiceOrderID", purchaseInvoiceBean.getInvoiceOrderID());
			}
			if(purchaseInvoiceBean.getInvoiceId()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceId())){
				sql += " and invoiceId = :invoiceId ";
				sqlCount += " and invoiceId = :invoiceId ";
				map.put("invoiceId", purchaseInvoiceBean.getInvoiceId());
			}
			if(purchaseInvoiceBean.getInvoiceStatus()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceStatus())){
				sql += " and invoiceStatus = :invoiceStatus ";
				sqlCount += " and invoiceStatus = :invoiceStatus ";
				map.put("invoiceStatus", purchaseInvoiceBean.getInvoiceStatus());
			}
			if(purchaseInvoiceBean.getInvoiceStatuss()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceStatuss())){
				sql += " and invoiceStatus in ("+purchaseInvoiceBean.getInvoiceStatuss()+") ";
				sqlCount += " and invoiceStatus in ("+purchaseInvoiceBean.getInvoiceStatuss()+") ";
			}
			//创建日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseInvoiceBean.getInvoiceCdate()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceCdate())){
				sql += " and invoiceCdate >= to_date('"+sdf.format(purchaseInvoiceBean.getInvoiceCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and invoiceCdate >= to_date('"+sdf.format(purchaseInvoiceBean.getInvoiceCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseInvoiceBean.getInvoiceCdateEnd()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceCdateEnd())){
				sql += " and invoiceCdate <= to_date('"+sdf.format(purchaseInvoiceBean.getInvoiceCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and invoiceCdate <= to_date('"+sdf.format(purchaseInvoiceBean.getInvoiceCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += " order by piid desc ";
			List<PurchaseInvoiceModel> list = purchaseInvoiceDao.queryObjectsBySqlLists(sql, map, purchaseInvoiceBean.getPage(), purchaseInvoiceBean.getRows(),PurchaseInvoiceModel.class);
			Integer counts = purchaseInvoiceDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}

		@Override
		public Integer savePurchaseInvoice(PurchaseInvoiceBean purchaseInvoiceBean, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=?", new Object[]{purchaseInvoiceBean.getPoid()});
			if(purchaseInvoiceBean.getInvoiceType()==1){
				//修改采购单状态
				Double orderAmt = purchaseOrderModel.getOrderAmt();//总金额
				Double writeoffAmt = purchaseOrderModel.getWriteoffAmt();//已核销金额
				Integer orderNum = purchaseOrderModel.getOrderNum();//总数量
				Integer writeoffNum = purchaseOrderModel.getWriteoffNum();//已核销数量
				if(orderAmt-writeoffAmt<purchaseInvoiceBean.getHaveTax()){
					return 2;//含税金额不能大于待核销金额
				}
				if(orderNum-writeoffNum<purchaseInvoiceBean.getInvoiceNum()){
					return 3;//核销数量不能大于待核销数量
				}
				
				PurchaseInvoiceModel purchaseInvoiceModel = new PurchaseInvoiceModel();
				BeanUtils.copyProperties(purchaseInvoiceBean, purchaseInvoiceModel);
				
				purchaseInvoiceModel.setInvoiceStatus(1);
				purchaseInvoiceModel.setInvoiceCdate(new Date());
				purchaseInvoiceModel.setInvoiceCby(user.getLoginName());
				purchaseInvoiceModel.setInvoiceApproveStatus("A");
				
				purchaseOrderModel.setWriteoffStatus(2);
				
				purchaseInvoiceDao.saveObject(purchaseInvoiceModel);
				purchaseOrderDao.saveObject(purchaseOrderModel);
				
				return 1;
			}else{
				Integer amt = 0;//总退款核销金额
				Integer num = 0;//总退款核销数量
				Integer amt2 = 0;//已退款核销金额
				Integer num2 = 0;//已退款核销数量
				//总退款核销
				Map<String,Object> map = new HashMap<String, Object>();
				String sql = "select nvl(sum(detailAmt),0) amt,nvl(sum(machineNum),0) num from bill_PurchaseDetail where detailOrderID=:detailOrderID and orderType=4 and detailStatus in(7,8) ";
				map.put("detailOrderID", purchaseOrderModel.getOrderID());
				List<Map<String,Object>> list = purchaseOrderDao.executeSql2(sql,map);
				//已退款核销
				String sql2 = "select nvl(sum(haveTax),0) amt,nvl(sum(invoiceNum),0) num from bill_PurchaseInvoice where invoiceOrderID=:detailOrderID and invoiceType=2 and invoiceStatus=10 ";
				List<Map<String,Object>> list2 = purchaseOrderDao.executeSql2(sql2,map);
				//已审核退款付款单
				String sql3 = "select nvl(sum(accountAmt),0) amt from bill_PurchaseAccount where accountOrderID=:detailOrderID and accountType=2 and accountStatus =9 ";
				List<Map<String,Object>> list3 = purchaseOrderDao.executeSql2(sql3,map);
				if(list.size()>0){
					if(list2.size()>0){
						num2 = -((BigDecimal) list2.get(0).get("NUM")).intValue();
						amt2 = -((BigDecimal) list2.get(0).get("AMT")).intValue();
					}
					num = -((BigDecimal) list.get(0).get("NUM")).intValue();
					amt = -((BigDecimal) list.get(0).get("AMT")).intValue();
				}
				Integer amt3 = -((BigDecimal) list3.get(0).get("AMT")).intValue();
				if(purchaseInvoiceBean.getInvoiceNum()>num-num2){
					return 4;//核销退货数量不能大于总核销退货数量
				}
				if(purchaseInvoiceBean.getHaveTax()>amt-amt2){
					return 5;//核销退款金额不能大于待核销退款金额 
				}
				if(purchaseInvoiceBean.getHaveTax()>amt3-amt2){
					return 5;//核销退款金额不能大于待核销退款金额 
				}
				PurchaseInvoiceModel purchaseInvoiceModel = new PurchaseInvoiceModel();
				BeanUtils.copyProperties(purchaseInvoiceBean, purchaseInvoiceModel);
				
				purchaseInvoiceModel.setInvoiceStatus(1);
				purchaseInvoiceModel.setInvoiceCdate(new Date());
				purchaseInvoiceModel.setInvoiceCby(user.getLoginName());
				purchaseInvoiceModel.setInvoiceApproveStatus("A");
				purchaseInvoiceModel.setInvoiceAmt(-purchaseInvoiceBean.getInvoiceAmt());
				purchaseInvoiceModel.setInvoiceNum(-purchaseInvoiceBean.getInvoiceNum());
				purchaseInvoiceModel.setHaveTax(-purchaseInvoiceBean.getHaveTax());
				purchaseInvoiceModel.setNoTax(-purchaseInvoiceBean.getNoTax());
				purchaseInvoiceModel.setTax(-purchaseInvoiceBean.getTax());
				
				purchaseOrderModel.setWriteoffStatus(2);
				
				purchaseInvoiceDao.saveObject(purchaseInvoiceModel);
				purchaseOrderDao.saveObject(purchaseOrderModel);
				return 1;
			}
		}

		@Override
		public Integer updatePurchaseInvoiceStatus(PurchaseInvoiceBean purchaseInvoiceBean, UserBean user) {
			PurchaseInvoiceModel purchaseInvoiceModel = purchaseInvoiceDao.queryObjectByHql("from PurchaseInvoiceModel where invoiceStatus=1 and piid=? and invoiceApproveStatus!='D' ", new Object[]{purchaseInvoiceBean.getPiid()});
			
			if(purchaseInvoiceModel!=null){
				if(purchaseInvoiceModel.getInvoiceType()==1){
					//修改采购单已核销金额，数量
					PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where approveStatus!='D' and poid=?", new Object[]{purchaseInvoiceModel.getPoid()});
					Double orderAmt = purchaseOrderModel.getOrderAmt();//总金额
					Double writeoffAmt = purchaseOrderModel.getWriteoffAmt();//已核销金额
					Integer orderNum = purchaseOrderModel.getOrderNum();//总数量
					Integer writeoffNum = purchaseOrderModel.getWriteoffNum();//已核销数量
					if(orderAmt-writeoffAmt<purchaseInvoiceModel.getHaveTax()){
						return 2;//含税金额不能大于待核销金额
					}
					if(orderNum-writeoffNum<purchaseInvoiceModel.getInvoiceNum()){
						return 3;//核销数量不能大于待核销数量
					}

					purchaseInvoiceModel.setInvoiceStatus(2);
					purchaseInvoiceModel.setInvoiceApproveStatus("M");
					purchaseInvoiceDao.updateObject(purchaseInvoiceModel);
					
					return 1;
				}else{
					Integer amt = 0;//总退款核销金额
					Integer num = 0;//总退款核销数量
					Integer amt2 = 0;//已退款核销金额
					Integer num2 = 0;//已退款核销数量
					//总退款核销
					Map<String,Object> map = new HashMap<String, Object>();
					String sql = "select nvl(sum(detailAmt),0) amt,nvl(sum(machineNum),0) num from bill_PurchaseDetail where detailOrderID=:detailOrderID and orderType=4 and detailStatus in(7,8) ";
					map.put("detailOrderID", purchaseInvoiceModel.getInvoiceOrderID());
					List<Map<String,Object>> list = purchaseOrderDao.executeSql2(sql,map);
					//已退款核销
					String sql2 = "select nvl(sum(haveTax),0) amt,nvl(sum(invoiceNum),0) num from bill_PurchaseInvoice where invoiceOrderID=:detailOrderID and invoiceType=2 and invoiceStatus=10 ";
					List<Map<String,Object>> list2 = purchaseOrderDao.executeSql2(sql2,map);
					//已审核退款付款单
					String sql3 = "select nvl(sum(accountAmt),0) amt from bill_PurchaseAccount where accountOrderID=:detailOrderID and accountType=2 and accountStatus =9 ";
					List<Map<String,Object>> list3 = purchaseOrderDao.executeSql2(sql3,map);
					if(list.size()>0){
						if(list2.size()>0){
							num2 = -((BigDecimal) list2.get(0).get("NUM")).intValue();
							amt2 = -((BigDecimal) list2.get(0).get("AMT")).intValue();
						}
						num = -((BigDecimal) list.get(0).get("NUM")).intValue();
						amt = -((BigDecimal) list.get(0).get("AMT")).intValue();
					}
					Integer amt3 = -((BigDecimal) list3.get(0).get("AMT")).intValue();
					if(-purchaseInvoiceModel.getInvoiceNum()>num-num2){
						return 4;//核销退货数量不能大于总核销退货数量
					}
					if(-purchaseInvoiceModel.getHaveTax()>amt-amt2){
						return 5;//核销退款金额不能大于待核销退款金额 
					}
					if(-purchaseInvoiceModel.getHaveTax()>amt3-amt2){
						return 5;//核销退款金额不能大于待核销退款金额 
					}
					
					purchaseInvoiceModel.setInvoiceStatus(2);
					purchaseInvoiceModel.setInvoiceApproveStatus("M");
					purchaseInvoiceDao.updateObject(purchaseInvoiceModel);
					return 1;
				}
			}
			return 0;
		}

		@Override
		public Integer updatePurchaseInvoiceY(PurchaseInvoiceBean purchaseInvoiceBean, UserBean user) {
			PurchaseInvoiceModel purchaseInvoiceModel = purchaseInvoiceDao.queryObjectByHql("from PurchaseInvoiceModel where invoiceStatus=2 and piid=? and invoiceApproveStatus!='D' ", new Object[]{purchaseInvoiceBean.getPiid()});
			if(purchaseInvoiceModel!=null){
				PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where approveStatus!='D' and poid=?", new Object[]{purchaseInvoiceModel.getPoid()});
				if(purchaseInvoiceModel.getInvoiceType()==1){
					Double orderAmt = purchaseOrderModel.getOrderAmt();//总金额
					Double writeoffAmt = purchaseOrderModel.getWriteoffAmt();//已核销金额
					Integer orderNum = purchaseOrderModel.getOrderNum();//总数量
					Integer writeoffNum = purchaseOrderModel.getWriteoffNum();//已核销数量
					
					if(orderAmt-writeoffAmt<purchaseInvoiceModel.getHaveTax()){
						return 2;//含税金额不能大于待核销金额
					}
					if(orderNum-writeoffNum<purchaseInvoiceModel.getInvoiceNum()){
						return 3;//核销数量不能大于待核销数量
					}
					//审核通过修改采购单的已核销金额
					purchaseOrderModel.setWriteoffAmt(purchaseOrderModel.getWriteoffAmt()+purchaseInvoiceModel.getInvoiceAmt());
					purchaseOrderModel.setWriteoffNum(purchaseOrderModel.getWriteoffNum()+purchaseInvoiceModel.getInvoiceNum());
					
					if(orderAmt-purchaseOrderModel.getWriteoffAmt()<0.00000001&&orderNum-purchaseOrderModel.getWriteoffNum()==0){
						//金额数量全部核销，修改状态
						purchaseOrderModel.setWriteoffStatus(3);
					}
					purchaseOrderDao.saveObject(purchaseOrderModel);
					purchaseInvoiceModel.setInvoiceStatus(10);
					purchaseInvoiceModel.setInvoiceApproveDate(new Date());
					purchaseInvoiceModel.setInvoiceApprover(user.getLoginName());
					purchaseInvoiceModel.setInvoiceApproveStatus("M");
					purchaseInvoiceDao.updateObject(purchaseInvoiceModel);
				}else{
					Integer amt = 0;//总退款核销金额
					Integer num = 0;//总退款核销数量
					Integer amt2 = 0;//已退款核销金额
					Integer num2 = 0;//已退款核销数量
					//总退款核销
					Map<String,Object> map = new HashMap<String, Object>();
					String sql = "select sum(detailAmt) amt,sum(machineNum) num from bill_PurchaseDetail where detailOrderID=:detailOrderID and orderType=4 and detailStatus in(7,8) ";
					map.put("detailOrderID", purchaseInvoiceModel.getInvoiceOrderID());
					List<Map<String,Object>> list = purchaseOrderDao.executeSql2(sql,map);
					//已退款核销
					String sql2 = "select nvl(sum(haveTax),0) amt,nvl(sum(invoiceNum),0) num from bill_PurchaseInvoice where invoiceOrderID=:detailOrderID and invoiceType=2 and invoiceStatus=10 ";
					List<Map<String,Object>> list2 = purchaseOrderDao.executeSql2(sql2,map);
					//已审核退款付款单
					String sql3 = "select nvl(sum(accountAmt),0) amt from bill_PurchaseAccount where accountOrderID=:detailOrderID and accountType=2 and accountStatus =9 ";
					List<Map<String,Object>> list3 = purchaseOrderDao.executeSql2(sql3,map);
					if(list.size()>0){
						if(list2.size()>0){
							num2 = -((BigDecimal) list2.get(0).get("NUM")).intValue();
							amt2 = -((BigDecimal) list2.get(0).get("AMT")).intValue();
						}
						num = -((BigDecimal) list.get(0).get("NUM")).intValue();
						amt = -((BigDecimal) list.get(0).get("AMT")).intValue();
					}
					Integer amt3 = -((BigDecimal) list3.get(0).get("AMT")).intValue();
					if(-purchaseInvoiceModel.getInvoiceNum()>num-num2){
						return 4;//核销退货数量不能大于总核销退货数量
					}
					if(-purchaseInvoiceModel.getHaveTax()>amt-amt2){
						return 5;//核销退款金额不能大于待核销退款金额 
					}
					if(-purchaseInvoiceModel.getHaveTax()>amt3-amt2){
						return 5;//核销退款金额不能大于待核销退款金额 
					}
					
					//审核通过修改采购单的已核销金额
					purchaseOrderModel.setWriteoffAmt(purchaseOrderModel.getWriteoffAmt()+purchaseInvoiceModel.getInvoiceAmt());
					purchaseOrderModel.setWriteoffNum(purchaseOrderModel.getWriteoffNum()+purchaseInvoiceModel.getInvoiceNum());
					purchaseOrderModel.setWriteoffStatus(2);
					
					purchaseOrderDao.saveObject(purchaseOrderModel);
					purchaseInvoiceModel.setInvoiceStatus(10);
					purchaseInvoiceModel.setInvoiceApproveDate(new Date());
					purchaseInvoiceModel.setInvoiceApprover(user.getLoginName());
					purchaseInvoiceModel.setInvoiceApproveStatus("M");
					purchaseInvoiceDao.updateObject(purchaseInvoiceModel);
					
					return 1;
				}
				
				
				return 1;
			}
			return 0;
		}

		@Override
		public Integer updatePurchaseInvoiceK(PurchaseInvoiceBean purchaseInvoiceBean, UserBean user) {
			PurchaseInvoiceModel purchaseInvoiceModel = purchaseInvoiceDao.queryObjectByHql("from PurchaseInvoiceModel where invoiceStatus=2 and piid=? and invoiceApproveStatus!='D' ", new Object[]{purchaseInvoiceBean.getPiid()});
			if(purchaseInvoiceModel!=null){
				purchaseInvoiceModel.setInvoiceApproveDate(new Date());
				purchaseInvoiceModel.setInvoiceApprover(user.getLoginName());
				purchaseInvoiceModel.setInvoiceApproveNote(purchaseInvoiceBean.getInvoiceApproveNote());
				purchaseInvoiceModel.setInvoiceApproveStatus("K");
				purchaseInvoiceModel.setInvoiceStatus(1);
				
				purchaseInvoiceDao.updateObject(purchaseInvoiceModel);
				
				return 1;
			}
			return 0;
		}

		@Override
		public List<Map<String, Object>> exportPurchaseInvoice(PurchaseInvoiceBean purchaseInvoiceBean, UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchaseinvoice where invoiceApproveStatus!='D' ";
			if(purchaseInvoiceBean.getInvoiceOrderID()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceOrderID())){
				sql += " and invoiceOrderID = :invoiceOrderID ";
				map.put("invoiceOrderID", purchaseInvoiceBean.getInvoiceOrderID());
			}
			if(purchaseInvoiceBean.getInvoiceId()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceId())){
				sql += " and invoiceId = :invoiceId ";
				map.put("invoiceId", purchaseInvoiceBean.getInvoiceId());
			}
			if(purchaseInvoiceBean.getInvoiceStatus()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceStatus())){
				sql += " and invoiceStatus = :invoiceStatus ";
				map.put("invoiceStatus", purchaseInvoiceBean.getInvoiceStatus());
			}
			if(purchaseInvoiceBean.getInvoiceStatuss()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceStatuss())){
				sql += " and invoiceStatus in ("+purchaseInvoiceBean.getInvoiceStatuss()+") ";
			}
			//创建日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseInvoiceBean.getInvoiceCdate()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceCdate())){
				sql += " and invoiceCdate >= to_date('"+sdf.format(purchaseInvoiceBean.getInvoiceCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseInvoiceBean.getInvoiceCdateEnd()!=null&&!"".equals(purchaseInvoiceBean.getInvoiceCdateEnd())){
				sql += " and invoiceCdate <= to_date('"+sdf.format(purchaseInvoiceBean.getInvoiceCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += " order by piid desc ";
			
			List<Map<String, Object>> list = purchaseInvoiceDao.queryObjectsBySqlListMap2(sql, map);
			
			return list;
		}

		@Override
		public Integer deletePurchaseInvoice(Integer piid, UserBean user) {
			String sql = "from PurchaseInvoiceModel where piid = ? and invoiceApproveStatus!='D'";
			PurchaseInvoiceModel purchaseInvoiceModel = purchaseInvoiceDao.queryObjectByHql(sql, new Object[]{piid});
			if(purchaseInvoiceModel!=null){
				purchaseInvoiceModel.setInvoiceApproveStatus("D");
				
				purchaseInvoiceDao.updateObject(purchaseInvoiceModel);
				return 1;
			}else{
				return 0;
			}
		}
}
