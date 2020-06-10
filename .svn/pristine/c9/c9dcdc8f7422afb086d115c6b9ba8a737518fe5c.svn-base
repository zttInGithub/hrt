package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IPurchaseDeliverDao;
import com.hrt.biz.bill.dao.IPurchaseDetailDao;
import com.hrt.biz.bill.entity.model.PurchaseDeliverModel;
import com.hrt.biz.bill.entity.model.PurchaseDetailModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseDetailBean;
import com.hrt.biz.bill.entity.pagebean.PurchaseOrderBean;
import com.hrt.biz.bill.service.IPurchaseDetailService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class PurchaseDetailServiceImpl implements IPurchaseDetailService {

		private IPurchaseDetailDao purchaseDetailDao;
		private IPurchaseDeliverDao purchaseDeliverDao;

		public IPurchaseDeliverDao getPurchaseDeliverDao() {
			return purchaseDeliverDao;
		}

		public void setPurchaseDeliverDao(IPurchaseDeliverDao purchaseDeliverDao) {
			this.purchaseDeliverDao = purchaseDeliverDao;
		}

		public IPurchaseDetailDao getPurchaseDetailDao() {
			return purchaseDetailDao;
		}

		public void setPurchaseDetailDao(IPurchaseDetailDao purchaseDetailDao) {
			this.purchaseDetailDao = purchaseDetailDao;
		}

		@Override
		public void savePurchaseDetail(PurchaseDetailModel purchaseDetailModel, UserBean user) {
			purchaseDetailModel.setDetailStatus(6);
			purchaseDetailModel.setImportNum(0);
			purchaseDetailModel.setDetailCdate(new Date());
			purchaseDetailModel.setDetailCby(user.getLoginName());
			purchaseDetailModel.setDetailApproveStatus("A");
			
			purchaseDetailDao.saveObject(purchaseDetailModel);
		}

		@Override
		public DataGridBean queryPurchaseDetail(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchasedetail where poid = :poid and detailApproveStatus!=:detailApproveStatus ";
			sqlCount = "select count(*) from bill_purchasedetail where poid = :poid and detailApproveStatus!=:detailApproveStatus ";
			map.put("poid", purchaseDetailBean.getPoid());
			map.put("detailApproveStatus", "D");
			sql += " order by pdid desc ";
			List<PurchaseDetailModel> list = purchaseDetailDao.queryObjectsBySqlLists(sql, map, purchaseDetailBean.getPage(), purchaseDetailBean.getRows(),PurchaseDetailModel.class);
			Integer counts = purchaseDetailDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}
		
		@Override
		public DataGridBean listPurchaseOrderAndDetail(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			Map<String,Object> map1 =listPurchaseOrderAndDetailSql (purchaseDetailBean);
			String sql =(String) map1.get("sql");
			Map<String,Object> map = (Map<String, Object>) map1.get("map");
			String sqlCount = (String) map1.get("sqlCount");
			List<Map<String, String>> list = purchaseDetailDao.queryObjectsBySqlList(sql,map, purchaseDetailBean.getPage(), purchaseDetailBean.getRows());
			List<Map<String,String>> map2 = purchaseDetailDao.queryObjectsBySqlListMap(sqlCount, map);
			if(purchaseDetailBean.getDetailMinfo1()!=null&&purchaseDetailBean.getDetailMinfo1()==1){
				list.add(0, map2.get(0));
			}
			dgb.setRows(list);
			dgb.setTotal(Long.parseLong(map2.get(0).get("ALLCOUNT")));
			return dgb;
		}

		/**
		 * 导出采购单&明细
		 */
		@Override
		public List<Map<String, Object>> emportPurchaseInfo(PurchaseDetailBean purchaseDetailBean) {
			List<Map<String, Object>> roleList = new ArrayList<Map<String, Object>>();
			Map<String,Object> map1 =listPurchaseOrderAndDetailSql (purchaseDetailBean);
			String sql =(String) map1.get("sql");
			Map<String,Object> map = (Map<String, Object>) map1.get("map");
			try {
				roleList = purchaseDetailDao.executeSql2(sql,map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return roleList;
		}
		
		public Map<String,Object>  listPurchaseOrderAndDetailSql (PurchaseDetailBean purchaseDetailBean){
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map1 = new HashMap<String,Object>();
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select l.*,o.orderid,o.vendorname,o.purchaser,o.orderday,o.ordermethod,o.purchasername,o.contacts,o.contactphone,o.contactmail,o.ordernum,o.orderamt," +
					"o.ORDERPAYAMT,o.WRITEOFFAMT,o.WRITEOFFSTATUS,o.WRITEOFFNUM,o.unno,o.status from bill_purchaseorder o,bill_purchasedetail l where o.poid=l.poid and o.approveStatus not in ('D','K') and l.detailApproveStatus not in ('D','K') ";
			sqlCount = "select to_char(count(1)) allcount,to_char(sum(machineNum)) machineNum,to_char(sum(detailAmt)) detailAmt,to_char(sum(importNum)) importNum from bill_purchaseorder o,bill_purchasedetail l where o.poid=l.poid and o.approveStatus not in ('D','K') and l.detailApproveStatus not in ('D','K') ";
			
			if(purchaseDetailBean.getPutType()!=null&&!"".equals(purchaseDetailBean.getPutType())){
				// @author:lxg-20200313采购订单中的代理商单需要做入库操作
				sql += " AND ((o.ordermethod in (1,3) and l.Ordertype!=4) OR (o.ordermethod=2 AND l.Ordertype =4 and l.detailStatus!=6 )) ";
				sqlCount += " AND ((o.ordermethod in (1,3) and l.Ordertype!=4) OR (o.ordermethod=2 AND l.Ordertype =4 and l.detailStatus!=6)) ";
			}
			if(purchaseDetailBean.getOrderID()!=null&&!"".equals(purchaseDetailBean.getOrderID())){
				sql += " and o.orderID = :orderID ";
				sqlCount += " and o.orderID = :orderID ";
				map.put("orderID", purchaseDetailBean.getOrderID());
			}
			if(purchaseDetailBean.getOrderMethod()!=null&&!"".equals(purchaseDetailBean.getOrderMethod())){
				sql += " and o.orderMethod = :orderMethod ";
				sqlCount += " and o.orderMethod = :orderMethod ";
				map.put("orderMethod", purchaseDetailBean.getOrderMethod());
			}
			if(purchaseDetailBean.getVendorName()!=null&&!"".equals(purchaseDetailBean.getVendorName())){
				sql += " and o.vendorName = :vendorName ";
				sqlCount += " and o.vendorName = :vendorName ";
				map.put("vendorName", purchaseDetailBean.getVendorName());
			}
			if(purchaseDetailBean.getPurchaserName()!=null&&!"".equals(purchaseDetailBean.getPurchaserName())){
				sql += " and o.purchaserName = :purchaserName ";
				sqlCount += " and o.purchaserName = :purchaserName ";
				map.put("purchaserName", purchaseDetailBean.getPurchaserName());
			}
			if(purchaseDetailBean.getStatuss()!=null&&!"".equals(purchaseDetailBean.getStatuss())){
				sql += " and o.status in ("+purchaseDetailBean.getStatuss()+")";
				sqlCount += " and o.status in ("+purchaseDetailBean.getStatuss()+") ";
//				map.put("status", purchaseDetailBean.getStatus());
			}
			if(purchaseDetailBean.getStatus()!=null&&!"".equals(purchaseDetailBean.getStatus())){
				sql += " and o.status in ("+purchaseDetailBean.getStatus()+")";
				sqlCount += " and o.status in ("+purchaseDetailBean.getStatus()+") ";
//				map.put("status", purchaseDetailBean.getStatus());
			}
			if(purchaseDetailBean.getWriteoffStatus()!=null&&!"".equals(purchaseDetailBean.getWriteoffStatus())){
				sql += " and o.writeoffStatus = :writeoffStatus ";
				sqlCount += " and o.writeoffStatus = :writeoffStatus ";
				map.put("writeoffStatus", purchaseDetailBean.getWriteoffStatus());
			}
			if(purchaseDetailBean.getDetailStatus()!=null&&!"".equals(purchaseDetailBean.getDetailStatus())){
				sql += " and l.detailStatus in (:detailStatus) ";
				sqlCount += " and l.detailStatus in (:detailStatus) ";
				map.put("detailStatus", purchaseDetailBean.getDetailStatus());
			}
			if(purchaseDetailBean.getOrderType2()!=null&&!"".equals(purchaseDetailBean.getOrderType2())){
				sql += " and l.orderType != (:orderType2) ";
				sqlCount += " and l.orderType != (:orderType2) ";
				map.put("orderType2", purchaseDetailBean.getOrderType2());
			}
			if(purchaseDetailBean.getOrderType()!=null&&!"".equals(purchaseDetailBean.getOrderType())){
				sql += " and l.orderType = (:orderType) ";
				sqlCount += " and l.orderType = (:orderType) ";
				map.put("orderType", purchaseDetailBean.getOrderType());
			}
			if(purchaseDetailBean.getSnType()!=null&&!"".equals(purchaseDetailBean.getSnType())){
				sql += " and l.snType = :snType ";
				sqlCount += " and l.snType = :snType ";
				map.put("snType", purchaseDetailBean.getSnType());
			}
			if(purchaseDetailBean.getBrandName()!=null&&!"".equals(purchaseDetailBean.getBrandName())){
				sql += " and l.brandName = :brandName ";
				sqlCount += " and l.brandName = :brandName ";
				map.put("brandName", purchaseDetailBean.getBrandName());
			}
			if(purchaseDetailBean.getRebateType()!=null&&!"".equals(purchaseDetailBean.getRebateType())){
				sql += " and l.rebateType = :rebateType ";
				sqlCount += " and l.rebateType = :rebateType ";
				map.put("rebateType", purchaseDetailBean.getRebateType());
			}
			if(purchaseDetailBean.getDetailStatuss()!=null&&!"".equals(purchaseDetailBean.getDetailStatuss())){
				sql += " and l.detailStatus in ("+purchaseDetailBean.getDetailStatuss()+")";
				sqlCount += " and l.detailStatus in ("+purchaseDetailBean.getDetailStatuss()+") ";
			}
			if(purchaseDetailBean.getMachineModel()!=null&&!"".equals(purchaseDetailBean.getMachineModel())){
				sql += " and l.machineModel = :machineModel ";
				sqlCount += " and l.machineModel = :machineModel ";
				map.put("machineModel", purchaseDetailBean.getMachineModel());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDetailBean.getDetailCdateEnd()!=null&&!"".equals(purchaseDetailBean.getDetailCdateEnd())){
				sql += " and l.detailCdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and l.detailCdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDetailBean.getDetailCdate()!=null&&!"".equals(purchaseDetailBean.getDetailCdate())){
				sql += " and l.detailCdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and l.detailCdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "order by l.pdid desc";
			map1.put("sql", sql);
			map1.put("map", map);
			map1.put("sqlCount", sqlCount);
			return map1;
		
		}

		/**
		 * 查询采购单&明细
		 */
		@Override
		public DataGridBean listPurchaseOrderAndDetailForTongJi(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			String sql2 = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select l.*,o.orderid,o.vendorname,o.purchaser,o.orderday,o.ordermethod,o.purchasername,o.contacts,o.contactphone,o.contactmail,o.ordernum,o.orderamt," +
					"o.ORDERPAYAMT,o.WRITEOFFAMT,o.WRITEOFFSTATUS,o.WRITEOFFNUM,o.unno,o.status,(select count(1) from bill_terminalinfo d where d.terorderid=o.orderid and d.machinemodel = l.machinemodel and d.rebatetype=l.rebatetype) outnum "
					+ "from bill_purchaseorder o,bill_purchasedetail l where o.poid=l.poid and o.approveStatus not in ('D','K') and l.detailApproveStatus not in ('D','K') ";
			sqlCount = "select to_char(count(1)) allcount,to_char(sum(machineNum)) machineNum,to_char(sum(detailAmt)) detailAmt from bill_purchaseorder o,bill_purchasedetail l "
					+ "where o.poid=l.poid and o.approveStatus not in ('D','K') and l.detailApproveStatus not in ('D','K') ";
			//出库数量
			sql2 = "select nvl(count(d.btid),0) from bill_purchaseorder o,bill_purchasedetail l,bill_terminalinfo d where o.poid=l.poid and d.terorderid = o.orderid and l.rebatetype=d.rebatetype and o.approveStatus not in ('D','K')and l.detailApproveStatus not in ('D','K')";
			if(purchaseDetailBean.getPutType()!=null&&!"".equals(purchaseDetailBean.getPutType())){
				sql += " AND ((o.ordermethod=1 and l.Ordertype!=4) OR (o.ordermethod=2 AND l.Ordertype =4 and l.detailStatus!=6 )) ";
				sqlCount += " AND ((o.ordermethod=1 and l.Ordertype!=4) OR (o.ordermethod=2 AND l.Ordertype =4 and l.detailStatus!=6)) ";
				sql2 += " AND ((o.ordermethod=1 and l.Ordertype!=4) OR (o.ordermethod=2 AND l.Ordertype =4 and l.detailStatus!=6)) ";
			}
			if(purchaseDetailBean.getOrderID()!=null&&!"".equals(purchaseDetailBean.getOrderID())){
				sql += " and o.orderID = :orderID ";
				sqlCount += " and o.orderID = :orderID ";
				sql2 += " and o.orderID = :orderID ";
				map.put("orderID", purchaseDetailBean.getOrderID());
			}
			if(purchaseDetailBean.getOrderMethod()!=null&&!"".equals(purchaseDetailBean.getOrderMethod())){
				sql += " and o.orderMethod = :orderMethod ";
				sqlCount += " and o.orderMethod = :orderMethod ";
				sql2 += " and o.orderMethod = :orderMethod ";
				map.put("orderMethod", purchaseDetailBean.getOrderMethod());
			}
			if(purchaseDetailBean.getVendorName()!=null&&!"".equals(purchaseDetailBean.getVendorName())){
				sql += " and o.vendorName = :vendorName ";
				sqlCount += " and o.vendorName = :vendorName ";
				sql2 += " and o.vendorName = :vendorName ";
				map.put("vendorName", purchaseDetailBean.getVendorName());
			}
			if(purchaseDetailBean.getPurchaserName()!=null&&!"".equals(purchaseDetailBean.getPurchaserName())){
				sql += " and o.purchaserName = :purchaserName ";
				sqlCount += " and o.purchaserName = :purchaserName ";
				sql2 += " and o.purchaserName = :purchaserName ";
				map.put("purchaserName", purchaseDetailBean.getPurchaserName());
			}
			if(purchaseDetailBean.getStatuss()!=null&&!"".equals(purchaseDetailBean.getStatuss())){
				sql += " and o.status in ("+purchaseDetailBean.getStatuss()+")";
				sqlCount += " and o.status in ("+purchaseDetailBean.getStatuss()+") ";
				sql2 += " and o.status in ("+purchaseDetailBean.getStatuss()+") ";
//				map.put("status", purchaseDetailBean.getStatus());
			}
			if(purchaseDetailBean.getStatus()!=null&&!"".equals(purchaseDetailBean.getStatus())){
				sql += " and o.status in ("+purchaseDetailBean.getStatus()+")";
				sqlCount += " and o.status in ("+purchaseDetailBean.getStatus()+") ";
				sql2 += " and o.status in ("+purchaseDetailBean.getStatus()+") ";
//				map.put("status", purchaseDetailBean.getStatus());
			}
			if(purchaseDetailBean.getWriteoffStatus()!=null&&!"".equals(purchaseDetailBean.getWriteoffStatus())){
				sql += " and o.writeoffStatus = :writeoffStatus ";
				sqlCount += " and o.writeoffStatus = :writeoffStatus ";
				sql2 += " and o.writeoffStatus = :writeoffStatus ";
				map.put("writeoffStatus", purchaseDetailBean.getWriteoffStatus());
			}
			if(purchaseDetailBean.getDetailStatus()!=null&&!"".equals(purchaseDetailBean.getDetailStatus())){
				sql += " and l.detailStatus in (:detailStatus) ";
				sqlCount += " and l.detailStatus in (:detailStatus) ";
				sql2 += " and l.detailStatus in (:detailStatus) ";
				map.put("detailStatus", purchaseDetailBean.getDetailStatus());
			}
			if(purchaseDetailBean.getOrderType2()!=null&&!"".equals(purchaseDetailBean.getOrderType2())){
				sql += " and l.orderType != (:orderType2) ";
				sqlCount += " and l.orderType != (:orderType2) ";
				sql2 += " and l.orderType != (:orderType2) ";
				map.put("orderType2", purchaseDetailBean.getOrderType2());
			}
			if(purchaseDetailBean.getOrderType()!=null&&!"".equals(purchaseDetailBean.getOrderType())){
				sql += " and l.orderType = (:orderType) ";
				sqlCount += " and l.orderType = (:orderType) ";
				sql2 += " and l.orderType = (:orderType) ";
				map.put("orderType", purchaseDetailBean.getOrderType());
			}
			if(purchaseDetailBean.getSnType()!=null&&!"".equals(purchaseDetailBean.getSnType())){
				sql += " and l.snType = :snType ";
				sqlCount += " and l.snType = :snType ";
				sql2 += " and l.snType = :snType ";
				map.put("snType", purchaseDetailBean.getSnType());
			}
			if(purchaseDetailBean.getBrandName()!=null&&!"".equals(purchaseDetailBean.getBrandName())){
				sql += " and l.brandName = :brandName ";
				sqlCount += " and l.brandName = :brandName ";
				sql2 += " and l.brandName = :brandName ";
				map.put("brandName", purchaseDetailBean.getBrandName());
			}
			if(purchaseDetailBean.getRebateType()!=null&&!"".equals(purchaseDetailBean.getRebateType())){
				sql += " and l.rebateType = :rebateType ";
				sqlCount += " and l.rebateType = :rebateType ";
				sql2 += " and l.rebateType = :rebateType ";
				map.put("rebateType", purchaseDetailBean.getRebateType());
			}
			if(purchaseDetailBean.getDetailStatuss()!=null&&!"".equals(purchaseDetailBean.getDetailStatuss())){
				sql += " and l.detailStatus in ("+purchaseDetailBean.getDetailStatuss()+")";
				sqlCount += " and l.detailStatus in ("+purchaseDetailBean.getDetailStatuss()+") ";
				sql2 += " and l.detailStatus in ("+purchaseDetailBean.getDetailStatuss()+") ";
			}
			if(purchaseDetailBean.getMachineModel()!=null&&!"".equals(purchaseDetailBean.getMachineModel())){
				sql += " and l.machineModel = :machineModel ";
				sqlCount += " and l.machineModel = :machineModel ";
				sql2 += " and l.machineModel = :machineModel ";
				map.put("machineModel", purchaseDetailBean.getMachineModel());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDetailBean.getDetailCdateEnd()!=null&&!"".equals(purchaseDetailBean.getDetailCdateEnd())){
				sql += " and l.detailCdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and l.detailCdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sql2 += " and l.detailCdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDetailBean.getDetailCdate()!=null&&!"".equals(purchaseDetailBean.getDetailCdate())){
				sql += " and l.detailCdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and l.detailCdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sql2 += " and l.detailCdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "order by l.pdid desc";
			List<Map<String, String>> list = purchaseDetailDao.queryObjectsBySqlList(sql, map, purchaseDetailBean.getPage(), purchaseDetailBean.getRows());
			List<Map<String,String>> map2 = purchaseDetailDao.queryObjectsBySqlListMap(sqlCount, map);
			BigDecimal counts = purchaseDetailDao.querysqlCounts(sql2, map);
			if(purchaseDetailBean.getDetailMinfo1()!=null&&purchaseDetailBean.getDetailMinfo1()==1){
				map2.get(0).put("OUTNUM", counts.longValue()+"");
				list.add(0, map2.get(0));
			}
			dgb.setRows(list);
			dgb.setTotal(Long.parseLong(map2.get(0).get("ALLCOUNT")));
			return dgb;
		}
		@Override
		public void deletePurchaseDetail(Integer poid, UserBean user) {
			//Map<String,Object> map = new HashMap<String,Object>();
			String sql = "update bill_PurchaseDetail set detailApproveStatus='D',detailLmby='"+user.getLoginName()+"',detailLmdate=sysdate where poid = "+poid;
			purchaseDetailDao.executeUpdate(sql);
		}

		@Override
		public Integer deletePurchaseDetailById(Integer pdid, UserBean user) {
			String sql = "from PurchaseDetailModel where pdid = ? and detailApproveStatus!='D'";
			PurchaseDetailModel purchaseDetailModel = purchaseDetailDao.queryObjectByHql(sql, new Object[]{pdid});
			if(purchaseDetailModel!=null){
				purchaseDetailModel.setDetailApproveStatus("D");
				purchaseDetailModel.setDetailLmby(user.getLoginName());
				purchaseDetailModel.setDetailLmdate(new Date());
				
				purchaseDetailDao.updateObject(purchaseDetailModel);
				
				return 1;
			}else{
				return 0;
			}
			
		}

		@Override
		public DataGridBean queryMachineModel() {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			sql = "select * from bill_machineinfo where maintaintype != 'D' ";
			List<Map<String,String>> list = purchaseDetailDao.queryObjectsBySqlListMap(sql, null);
			dgb.setRows(list);
			return dgb;
		}
		@Override
		public DataGridBean listPurConfigure(String type) {
			if(type==null)return null;
			DataGridBean dgb = new DataGridBean();
			String sql  = "select * from bill_PurConfigure where status = 1 and type="+type;
			List<Map<String,String>> list = purchaseDetailDao.queryObjectsBySqlListMap(sql, null);
			dgb.setRows(list);
			return dgb;
		}

		/**
		 * 导出采购单&明细
		 */
		@Override
		public List<Map<String, Object>> exportPurchase(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select l.*,o.orderid,o.vendorname,o.purchaser,o.orderday,o.ordermethod,o.purchasername,o.contacts,o.contactphone,o.contactmail,o.ordernum,o.orderamt,o.unno from bill_purchaseorder o,bill_purchasedetail l where o.poid=l.poid and o.approveStatus != 'D' and l.detailApproveStatus!='D' ";
			
			if(purchaseDetailBean.getPutType()!=null&&!"".equals(purchaseDetailBean.getPutType())){
				sql += " AND (o.ordermethod=1 OR (o.ordermethod=2 AND l.Ordertype =4 and l.detailStatus!=6)) ";
			}
			if(purchaseDetailBean.getOrderID()!=null&&!"".equals(purchaseDetailBean.getOrderID())){
				sql += " and o.orderID = :orderID ";
				map.put("orderID", purchaseDetailBean.getOrderID());
			}
			if(purchaseDetailBean.getOrderType()!=null&&!"".equals(purchaseDetailBean.getOrderType())){
				sql += " and l.orderType = (:orderType)";
				map.put("orderType", purchaseDetailBean.getOrderType());
			}
			if(purchaseDetailBean.getOrderMethod()!=null&&!"".equals(purchaseDetailBean.getOrderMethod())){
				sql += " and o.orderMethod = :orderMethod ";
				map.put("orderMethod", purchaseDetailBean.getOrderMethod());
			}
			if(purchaseDetailBean.getVendorName()!=null&&!"".equals(purchaseDetailBean.getVendorName())){
				sql += " and o.vendorName = :vendorName ";
				map.put("vendorName", purchaseDetailBean.getVendorName());
			}
			if(purchaseDetailBean.getPurchaserName()!=null&&!"".equals(purchaseDetailBean.getPurchaserName())){
				sql += " and o.purchaserName = :purchaserName ";
				map.put("purchaserName", purchaseDetailBean.getPurchaserName());
			}
			if(purchaseDetailBean.getStatus()!=null&&!"".equals(purchaseDetailBean.getStatus())){
				sql += " and o.status in ("+purchaseDetailBean.getStatus()+")";
			}
			if(purchaseDetailBean.getDetailStatus()!=null&&!"".equals(purchaseDetailBean.getDetailStatus())){
				sql += " and l.detailStatus in (:detailStatus)";
				map.put("detailStatus", purchaseDetailBean.getDetailStatus());
			}
			if(purchaseDetailBean.getDetailStatuss()!=null&&!"".equals(purchaseDetailBean.getDetailStatuss())){
				sql += " and l.detailStatus in ("+purchaseDetailBean.getDetailStatuss()+")";
			}
			if(purchaseDetailBean.getMachineModel()!=null&&!"".equals(purchaseDetailBean.getMachineModel())){
				sql += " and l.machineModel = :machineModel ";
				map.put("machineModel", purchaseDetailBean.getMachineModel());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDetailBean.getDetailCdateEnd()!=null&&!"".equals(purchaseDetailBean.getDetailCdateEnd())){
				sql += " and l.detailCdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDetailBean.getDetailCdate()!=null&&!"".equals(purchaseDetailBean.getDetailCdate())){
				sql += " and l.detailCdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "order by l.pdid ";
			
			List<Map<String, Object>> list = purchaseDetailDao.queryObjectsBySqlListMap2(sql, map);
			
			return list;
		}

		@Override
		public void savePurchaseDetailOrder(Integer poid, Integer pdid,String returnNum,String loginName) {		
			String sql = "insert into bill_purchasedetail(pdid,poid,detailorderid,brandname,ordertype,machinemodel,sntype,"
					+ "rebatetype,rate,scanrate,secondrate,machineprice,machinenum,"
					+ "detailamt,detailstatus,importnum,detailcdate,detailcby,detailapprovestatus)"
					+ "select s_bill_purchasedetail.nextval,poid,detailorderid,brandname,4,"
					+ "machinemodel,sntype,rebatetype,rate,scanrate,secondrate,machineprice,-"+returnNum+","
					+ "-(machineprice*"+returnNum+"),6,0,sysdate,'"+loginName+"','A' "
					+ "from bill_purchasedetail where pdid="+pdid;
			purchaseDetailDao.executeUpdate(sql);
		}

		@Override
		public List<Map<String, Object>> queryRemainMachinenum(PurchaseDetailBean purchaseDetailBean) {
			String sql="select sum(c.a)+sum(c.b) as rMACHINENUM from ( select (case when t.ordertype!=4 then t.importNum else 0 end) as a,"+
				"(case when t.ordertype=4 then t.machinenum else 0 end) as b FROM BILL_PURCHASEDETAIL T WHERE 1=1 " +
				" AND t.detailapprovestatus NOT IN ('K', 'D') AND t.poid = "+purchaseDetailBean.getPoid()+""
						+ " AND T.detailOrderID='"+purchaseDetailBean.getDetailOrderID()+"' AND T.brandName='"+purchaseDetailBean.getBrandName()+"'"
						+ " AND T.machineModel='"+purchaseDetailBean.getMachineModel()+"' AND T.snType="+purchaseDetailBean.getSnType();
			if(purchaseDetailBean.getRate()!=null&&!"".equals(purchaseDetailBean.getRate())){
				sql += " AND T.rate="+purchaseDetailBean.getRate();
			}
			if(purchaseDetailBean.getScanRate()!=null&&!"".equals(purchaseDetailBean.getScanRate())){
				sql += " AND T.scanRate="+purchaseDetailBean.getScanRate();
			}
			if(purchaseDetailBean.getSecondRate()!=null&&!"".equals(purchaseDetailBean.getSecondRate())){
				sql += " AND T.secondRate="+purchaseDetailBean.getSecondRate();
			}
			if(purchaseDetailBean.getRebateType()!=null&&!"".equals(purchaseDetailBean.getRebateType())){
				sql += " AND T.rebateType="+purchaseDetailBean.getRebateType();
			}
			sql += " AND T.machinePrice="+purchaseDetailBean.getMachinePrice()+")c";
			List<Map<String, Object>> list = purchaseDetailDao.executeSql2(sql, null);
			return list;
		}

		@Override
		public DataGridBean listReturnOrderAndDetail(PurchaseDetailBean purchaseDetailBean, UserBean userBean) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select a.poid,a.orderid,a.ordermethod,a.vendorname,a.purchasername,a.unno,"
					+ "a.contacts,a.contactphone,a.contactmail,a.orderNum,a.orderAmt,a.purchaser,a.cdate,"
					+ "b.pdid,b.machinenum,b.detailamt,b.detailcby,b.detailstatus,b.detailcdate,b.detailapprovestatus,"
					+ "b.detailApproveNote,b.detailApproveDate,b.detailApprover"
					+ " from bill_purchaseorder a, bill_purchasedetail b"
					+ " where a.poid = b.poid"
					+ " and b.ordertype =4"
					+ " and b.detailapprovestatus!='D'";
			sqlCount = "select count(1) from bill_purchaseorder a, bill_purchasedetail b"
					+ " where a.poid = b.poid and a.approvestatus!= 'D' and b.ordertype =4 and b.detailapprovestatus!='D'";
			//orderID
			if(purchaseDetailBean.getOrderID()!=null&&!"".equals(purchaseDetailBean.getOrderID())){
				sql += " and a.orderID = :orderID ";
				sqlCount += " and a.orderID = :orderID ";
				map.put("orderID", purchaseDetailBean.getOrderID());
			}
			//orderMethod
			if(purchaseDetailBean.getOrderMethod()!=null&&!"".equals(purchaseDetailBean.getOrderMethod())){
				sql += " and a.orderMethod =:orderMethod ";
				sqlCount += " and a.orderMethod =:orderMethod ";
				map.put("orderMethod", purchaseDetailBean.getOrderMethod());
			}
			if(purchaseDetailBean.getDetailStatus()!=null&&!"".equals(purchaseDetailBean.getDetailStatus())){
				if (purchaseDetailBean.getDetailStatus()!=10) {
					sql += " and b.detailapprovestatus != 'K' and b.detailStatus in(:detailStatus) ";
					sqlCount += " and b.detailapprovestatus != 'K' and b.detailStatus in(:detailStatus) ";
					map.put("detailStatus", purchaseDetailBean.getDetailStatus());
				}else{
					sql += " and b.detailapprovestatus = 'K' ";
					sqlCount += " and b.detailapprovestatus = 'K' ";
				}
			}
			//vendorName
			if(purchaseDetailBean.getVendorName()!=null&&!"".equals(purchaseDetailBean.getVendorName())){
				sql += " and a.vendorName =:vendorName ";
				sqlCount += " and a.vendorName =:vendorName ";
				map.put("vendorName", purchaseDetailBean.getVendorName());
			}
			//purchaserName
			if(purchaseDetailBean.getPurchaserName()!=null&&!"".equals(purchaseDetailBean.getPurchaserName())){
				sql += " and a.purchaserName =:purchaserName ";
				sqlCount += " and a.purchaserName =:purchaserName ";
				map.put("purchaserName", purchaseDetailBean.getPurchaserName());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDetailBean.getDetailCdateEnd()!=null&&!"".equals(purchaseDetailBean.getDetailCdateEnd())){
				sql += " and b.detailcdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and b.detailcdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDetailBean.getDetailCdate()!=null&&!"".equals(purchaseDetailBean.getDetailCdate())){
				sql += " and b.detailcdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and b.detailcdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "order by b.poid desc";
			sqlCount += "order by b.poid desc";

			List<Map<String, String>> list = purchaseDetailDao.queryObjectsBySqlList(sql, map, purchaseDetailBean.getPage(), purchaseDetailBean.getRows());
			Integer counts = purchaseDetailDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}

		@Override
		public List<Map<String, Object>> exportReturnOrderAndDetail(PurchaseDetailBean purchaseDetailBean) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select a.poid,a.orderid,a.orderday,a.ordermethod,a.vendorname,a.purchasername,a.unno,"
					+ "a.contacts,a.contactphone,a.contactmail,a.orderNum,a.orderAmt,a.purchaser,a.cdate,"
					+ "b.pdid,b.machinenum,b.detailamt,b.detailcby,b.detailstatus,b.detailcdate,b.detailapprovestatus,"
					+ "b.detailApproveNote,b.detailApproveDate,b.detailApprover"
					+ " from bill_purchaseorder a, bill_purchasedetail b"
					+ " where a.poid = b.poid"
					+ " and b.ordertype =4"
					+ " and b.detailapprovestatus!='D'";
			//orderID
			if(purchaseDetailBean.getOrderID()!=null&&!"".equals(purchaseDetailBean.getOrderID())){
				sql += " and a.orderID = :orderID ";
				map.put("orderID", purchaseDetailBean.getOrderID());
			}
			//orderMethod
			if(purchaseDetailBean.getOrderMethod()!=null&&!"".equals(purchaseDetailBean.getOrderMethod())){
				sql += " and a.orderMethod =:orderMethod ";
				map.put("orderMethod", purchaseDetailBean.getOrderMethod());
			}
			if(purchaseDetailBean.getDetailStatus()!=null&&!"".equals(purchaseDetailBean.getDetailStatus())){
				if (purchaseDetailBean.getDetailStatus()!=10) {
					sql += " and b.detailapprovestatus != 'K' and b.detailStatus in(:detailStatus) ";
					map.put("detailStatus", purchaseDetailBean.getDetailStatus());
				}else{
					sql += " and b.detailapprovestatus = 'K' ";
				}
			}
			//vendorName
			if(purchaseDetailBean.getVendorName()!=null&&!"".equals(purchaseDetailBean.getVendorName())){
				sql += " and a.vendorName =:vendorName ";
				map.put("vendorName", purchaseDetailBean.getVendorName());
			}
			//purchaserName
			if(purchaseDetailBean.getPurchaserName()!=null&&!"".equals(purchaseDetailBean.getPurchaserName())){
				sql += " and a.purchaserName =:purchaserName ";
				map.put("purchaserName", purchaseDetailBean.getPurchaserName());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDetailBean.getDetailCdateEnd()!=null&&!"".equals(purchaseDetailBean.getDetailCdateEnd())){
				sql += " and b.detailcdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDetailBean.getDetailCdate()!=null&&!"".equals(purchaseDetailBean.getDetailCdate())){
				sql += " and b.detailcdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "order by b.poid desc";

			List<Map<String,Object>> list = purchaseDetailDao.queryObjectsBySqlListMap2(sql, map);
			
			return list;
		}
		@Override
		public DataGridBean queryPurchaseDetailById(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchasedetail where pdid = :pdid and detailApproveStatus!=:detailApproveStatus ";
			sqlCount = "select count(*) from bill_purchasedetail where pdid = :pdid and detailApproveStatus!=:detailApproveStatus ";
			map.put("pdid", purchaseDetailBean.getPdid());
			map.put("detailApproveStatus", "D");
			sql += " order by pdid desc ";
			List<PurchaseDetailModel> list = purchaseDetailDao.queryObjectsBySqlLists(sql, map, purchaseDetailBean.getPage(), purchaseDetailBean.getRows(),PurchaseDetailModel.class);
			Integer counts = purchaseDetailDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}
		
		/**
		 * 退货审核成功
		 */
		@Override
		public Integer updateReturnDetail(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			 PurchaseDetailModel purchaseDetailModel = purchaseDetailDao.queryObjectByHql("from PurchaseDetailModel where pdid=? and detailStatus=6 ", new Object[]{purchaseDetailBean.getPdid()});
			if(purchaseDetailModel!=null){
				purchaseDetailModel.setDetailStatus(7);
				purchaseDetailModel.setDetailLmdate(new Date());
				purchaseDetailModel.setDetailLmby(user.getLoginName());
				purchaseDetailModel.setDetailApproveStatus("M");	
				purchaseDetailModel.setDetailApproveDate(new Date());
				purchaseDetailModel.setDetailApprover(user.getLoginName());
				
				purchaseDetailDao.updateObject(purchaseDetailModel);
				return 1;
			}
			return 0;
		}
		
		/**
		 * 退货审核成功(厂商单)
		 */
		@Override
		public Integer updateReturnDetail2(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			 PurchaseDetailModel purchaseDetailModel = purchaseDetailDao.queryObjectByHql("from PurchaseDetailModel where pdid=? and detailStatus=6 ", new Object[]{purchaseDetailBean.getPdid()});
			if(purchaseDetailModel!=null){
				purchaseDetailModel.setDetailStatus(7);
				purchaseDetailModel.setDetailLmdate(new Date());
				purchaseDetailModel.setDetailLmby(user.getLoginName());
				purchaseDetailModel.setDetailApproveStatus("M");	
				purchaseDetailModel.setDetailApproveDate(new Date());
				purchaseDetailModel.setDetailApprover(user.getLoginName());
				//新建出库，退货
				PurchaseDeliverModel purchaseDeliverModel = new PurchaseDeliverModel();
				purchaseDeliverModel.setPoid(purchaseDetailModel.getPoid());
				purchaseDeliverModel.setPdid(purchaseDetailModel.getPdid());
				purchaseDeliverModel.setDeliveNum(purchaseDetailModel.getMachineNum());
				purchaseDeliverModel.setDeliverStatus(2);
				purchaseDeliverModel.setDeliverUnno("110000");
				purchaseDeliverModel.setDeliverPurName("退货");
				purchaseDeliverModel.setDeliverOrderID(purchaseDetailModel.getDetailOrderID());
				purchaseDeliverModel.setAllocatedNum(0);
				purchaseDeliverModel.setDeliverCdate(new Date());
				purchaseDeliverModel.setDeliverCby(user.getLoginName());
				
				purchaseDeliverDao.saveObject(purchaseDeliverModel);
				purchaseDetailDao.updateObject(purchaseDetailModel);
				return 1;
			}
			return 0;
		}

		@Override
		public DataGridBean queryPurchaseDetailByPoid(PurchaseDetailBean purchaseDetailBean, UserBean userBean) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchasedetail where 1=1 "
					+ "AND detailApproveStatus NOT IN('D','K') "
					+ "AND ((orderType!=4 AND detailStatus=8)OR (orderType=4 AND detailStatus in(6,7))) "
					+ "AND poid = :poid";
			sqlCount = "select count(*) from bill_purchasedetail where 1=1 "
					+ "AND detailApproveStatus NOT IN('D','K') "
					+ "AND ((orderType!=4 AND detailStatus=8)OR (orderType=4 AND detailStatus in(6,7))) "
					+ "AND poid = :poid";
			map.put("poid", purchaseDetailBean.getPoid());
			sql += " order by pdid desc ";
			List<PurchaseDetailModel> list = purchaseDetailDao.queryObjectsBySqlLists(sql, map, purchaseDetailBean.getPage(), purchaseDetailBean.getRows(),PurchaseDetailModel.class);
			Integer counts = purchaseDetailDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}

		/**
		 * 退回退货
		 */
		@Override
		public Integer updateBackReturnDetail(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			 PurchaseDetailModel purchaseDetailModel = purchaseDetailDao.queryObjectByHql("from PurchaseDetailModel where pdid=? and detailStatus=6 ", new Object[]{purchaseDetailBean.getPdid()});
			if(purchaseDetailModel!=null){
				purchaseDetailModel.setDetailLmdate(new Date());
				purchaseDetailModel.setDetailLmby(user.getLoginName());
				purchaseDetailModel.setDetailApproveStatus("K");
				purchaseDetailModel.setDetailApproveDate(new Date());
				purchaseDetailModel.setDetailApprover(user.getLoginName());
				purchaseDetailModel.setDetailApproveNote(purchaseDetailBean.getDetailApproveNote());
				
				purchaseDetailDao.updateObject(purchaseDetailModel);
				return 1;
			}
			return 0;
		}
		
		/**
		 * 订单统计
		 */
		@Override
		public List<Map<String,Object>> exportPurchaseOrderAndDetailForTongJi(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select l.*,o.orderid,o.vendorname,o.purchaser,o.orderday,o.ordermethod,o.purchasername,o.contacts,o.contactphone,o.contactmail,o.ordernum,o.orderamt," +
					"o.ORDERPAYAMT,o.WRITEOFFAMT,o.WRITEOFFSTATUS,o.WRITEOFFNUM,o.unno,o.status,(select count(1) from bill_terminalinfo d where d.terorderid=o.orderid and d.machinemodel = l.machinemodel and d.rebatetype = l.rebatetype) outnum "
					+ "from bill_purchaseorder o,bill_purchasedetail l where o.poid=l.poid and o.approveStatus not in ('D','K') and l.detailApproveStatus not in ('D','K') ";
			if(purchaseDetailBean.getPutType()!=null&&!"".equals(purchaseDetailBean.getPutType())){
				sql += " AND ((o.ordermethod=1 and l.Ordertype!=4) OR (o.ordermethod=2 AND l.Ordertype =4 and l.detailStatus!=6 )) ";
			}
			if(purchaseDetailBean.getOrderID()!=null&&!"".equals(purchaseDetailBean.getOrderID())){
				sql += " and o.orderID = :orderID ";
				map.put("orderID", purchaseDetailBean.getOrderID());
			}
			if(purchaseDetailBean.getOrderMethod()!=null&&!"".equals(purchaseDetailBean.getOrderMethod())){
				sql += " and o.orderMethod = :orderMethod ";
				map.put("orderMethod", purchaseDetailBean.getOrderMethod());
			}
			if(purchaseDetailBean.getVendorName()!=null&&!"".equals(purchaseDetailBean.getVendorName())){
				sql += " and o.vendorName = :vendorName ";
				map.put("vendorName", purchaseDetailBean.getVendorName());
			}
			if(purchaseDetailBean.getPurchaserName()!=null&&!"".equals(purchaseDetailBean.getPurchaserName())){
				sql += " and o.purchaserName = :purchaserName ";
				map.put("purchaserName", purchaseDetailBean.getPurchaserName());
			}
			if(purchaseDetailBean.getStatuss()!=null&&!"".equals(purchaseDetailBean.getStatuss())){
				sql += " and o.status in ("+purchaseDetailBean.getStatuss()+")";
			}
			if(purchaseDetailBean.getStatus()!=null&&!"".equals(purchaseDetailBean.getStatus())){
				sql += " and o.status in ("+purchaseDetailBean.getStatus()+")";
			}
			if(purchaseDetailBean.getWriteoffStatus()!=null&&!"".equals(purchaseDetailBean.getWriteoffStatus())){
				sql += " and o.writeoffStatus = :writeoffStatus ";
				map.put("writeoffStatus", purchaseDetailBean.getWriteoffStatus());
			}
			if(purchaseDetailBean.getDetailStatus()!=null&&!"".equals(purchaseDetailBean.getDetailStatus())){
				sql += " and l.detailStatus in (:detailStatus) ";
				map.put("detailStatus", purchaseDetailBean.getDetailStatus());
			}
			if(purchaseDetailBean.getOrderType2()!=null&&!"".equals(purchaseDetailBean.getOrderType2())){
				sql += " and l.orderType != (:orderType2) ";
				map.put("orderType2", purchaseDetailBean.getOrderType2());
			}
			if(purchaseDetailBean.getOrderType()!=null&&!"".equals(purchaseDetailBean.getOrderType())){
				sql += " and l.orderType = (:orderType) ";
				map.put("orderType", purchaseDetailBean.getOrderType());
			}
			if(purchaseDetailBean.getSnType()!=null&&!"".equals(purchaseDetailBean.getSnType())){
				sql += " and l.snType = :snType ";
				map.put("snType", purchaseDetailBean.getSnType());
			}
			if(purchaseDetailBean.getBrandName()!=null&&!"".equals(purchaseDetailBean.getBrandName())){
				sql += " and l.brandName = :brandName ";
				map.put("brandName", purchaseDetailBean.getBrandName());
			}
			if(purchaseDetailBean.getRebateType()!=null&&!"".equals(purchaseDetailBean.getRebateType())){
				sql += " and l.rebateType = :rebateType ";
				map.put("rebateType", purchaseDetailBean.getRebateType());
			}
			if(purchaseDetailBean.getDetailStatuss()!=null&&!"".equals(purchaseDetailBean.getDetailStatuss())){
				sql += " and l.detailStatus in ("+purchaseDetailBean.getDetailStatuss()+")";
			}
			if(purchaseDetailBean.getMachineModel()!=null&&!"".equals(purchaseDetailBean.getMachineModel())){
				sql += " and l.machineModel = :machineModel ";
				map.put("machineModel", purchaseDetailBean.getMachineModel());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDetailBean.getDetailCdateEnd()!=null&&!"".equals(purchaseDetailBean.getDetailCdateEnd())){
				sql += " and l.detailCdate <= to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDetailBean.getDetailCdate()!=null&&!"".equals(purchaseDetailBean.getDetailCdate())){
				sql += " and l.detailCdate >= to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "order by l.pdid desc";
			list = purchaseDetailDao.queryObjectsBySqlListMap2(sql, map);
			return list;
		}

		@Override
		public Integer updatePurchaseDetailCancel(PurchaseOrderBean purchaseOrderBean, UserBean userBean) {
			String sql = "from PurchaseDetailModel where detailOrderID = ? and detailApproveStatus!='D'";
			List<PurchaseDetailModel> list = purchaseDetailDao.queryObjectsByHqlList(sql, new Object[]{purchaseOrderBean.getOrderID()});
			if(list.size()>0){
				for (PurchaseDetailModel purchaseDetailModel : list) {
					purchaseDetailModel.setMachineNum(purchaseDetailModel.getImportNum());
					purchaseDetailModel.setDetailAmt(purchaseDetailModel.getMachinePrice()*purchaseDetailModel.getImportNum());
					purchaseDetailModel.setDetailStatus(8);
					purchaseDetailModel.setDetailApproveStatus("M");
					purchaseDetailModel.setDetailApproveDate(new Date());
				}
			}else{
				return 0;
			}
			return 1;
		}

		@Override
		public Integer updatePurchaseDetail(PurchaseDetailBean purchaseDetailBean, UserBean user) {
			PurchaseDetailModel model = purchaseDetailDao.queryObjectByHql("from PurchaseDetailModel where pdid=?", new Object[] {purchaseDetailBean.getPdid()});
			if(model!=null) {
				if(purchaseDetailBean.getMachineNum().compareTo(model.getImportNum())==0) {//修改订单后，数量等于已出库数量，修改状态为已出库
					model.setDetailStatus(8);
				}else if(purchaseDetailBean.getMachineNum().compareTo(model.getImportNum())>0) {//修改订单后，数量大于已出库数量，修改状态为出库中
					model.setDetailStatus(7);
				}else {
					return 2;
				}
				model.setMachinePrice(purchaseDetailBean.getMachinePrice());
				model.setMachineNum(purchaseDetailBean.getMachineNum());
				model.setRebateType(purchaseDetailBean.getRebateType());
				model.setDetailAmt(purchaseDetailBean.getMachinePrice()*purchaseDetailBean.getMachineNum());
				model.setDetailLmdate(new Date());
				model.setDetailLmby(user.getLoginName());
				model.setDetailApproveStatus("M");
				purchaseDetailDao.updateObject(model);
				return 1;
			}
			return 0;
		}

		@Override
		public DataGridBean queryCenterCheckout(PurchaseDetailBean purchaseDetailBean) {
			String sql =centerCheckoutSql(purchaseDetailBean);
			String count = "select count(*) from (" + sql + ") t ";
			List<Map<String, String>> list = purchaseDetailDao.queryObjectsBySqlList(sql,null,purchaseDetailBean.getPage(),
					purchaseDetailBean.getRows());
			BigDecimal counts = purchaseDetailDao.querysqlCounts(count, null);
			DataGridBean dgd = new DataGridBean();
			dgd.setTotal(counts.intValue());// 查询数据库总条数
			dgd.setRows(list);
			list = null;
			return dgd;
			
		}

		@Override
		public List<Map<String, Object>> exportCenterCheckout(PurchaseDetailBean purchaseDetailBean) {
			List<Map<String, Object>> roleList = new ArrayList<Map<String, Object>>();
			String sql =centerCheckoutSql(purchaseDetailBean);
			try {
				roleList = purchaseDetailDao.queryObjectsBySqlObject(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return roleList;
		}
		
		public String  centerCheckoutSql (PurchaseDetailBean purchaseDetailBean){
			String sql  = " select a.yungying,(select m.un_name from sys_unit m where m.unno = yungying) unname, "
				+ " b.purchaser ,a.rebatetype ,count(1) zcount  from (select k.rebatetype,k.termid, "
				+ " trunc(k.outdate, 'dd') outdate,k.terorderid, "
				+ " (select s.unno from sys_unit s where s.un_lvl = 1 start with s.unno = k.unno connect by s.unno = prior s.upper_unit) yungying "
				+ " from bill_terminalinfo k where 1=1 ";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDetailBean.getDetailCdate()!=null&&!"".equals(purchaseDetailBean.getDetailCdate())){
				sql += " and k.outdate between  to_date('"+sdf.format(purchaseDetailBean.getDetailCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDetailBean.getDetailCdateEnd()!=null&&!"".equals(purchaseDetailBean.getDetailCdateEnd())){
				sql += " and to_date('"+sdf.format(purchaseDetailBean.getDetailCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				
			}
			sql +=  ") a,bill_purchaseorder b where a.terorderid = b.orderid group by yungying, rebatetype, purchaser ";
		
			return sql;
		
		}
}
