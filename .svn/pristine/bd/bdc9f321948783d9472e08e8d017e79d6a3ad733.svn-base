package com.hrt.biz.bill.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IPurchaseDeliverDao;
import com.hrt.biz.bill.dao.IPurchaseDetailDao;
import com.hrt.biz.bill.dao.IPurchaseOrderDao;
import com.hrt.biz.bill.entity.model.PurchaseDeliverModel;
import com.hrt.biz.bill.entity.model.PurchaseDetailModel;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseDeliverBean;
import com.hrt.biz.bill.service.IPurchaseDeliverService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class PurchaseDeliverServiceImpl implements IPurchaseDeliverService {

		private IPurchaseDeliverDao purchaseDeliverDao;
		private IPurchaseDetailDao purchaseDetailDao;
		private IPurchaseOrderDao purchaseOrderDao;
		
		public IPurchaseOrderDao getPurchaseOrderDao() {
			return purchaseOrderDao;
		}


		public void setPurchaseOrderDao(IPurchaseOrderDao purchaseOrderDao) {
			this.purchaseOrderDao = purchaseOrderDao;
		}


		public IPurchaseDetailDao getPurchaseDetailDao() {
			return purchaseDetailDao;
		}


		public void setPurchaseDetailDao(IPurchaseDetailDao purchaseDetailDao) {
			this.purchaseDetailDao = purchaseDetailDao;
		}


		public IPurchaseDeliverDao getPurchaseDeliverDao() {
			return purchaseDeliverDao;
		}


		public void setPurchaseDeliverDao(IPurchaseDeliverDao purchaseDeliverDao) {
			this.purchaseDeliverDao = purchaseDeliverDao;
		}


		@Override
		public Integer saveSendOutMachine(PurchaseDeliverBean purchaseDeliverBean, UserBean user) {
			PurchaseDetailModel purchaseDetailModel = purchaseDetailDao.queryObjectByHql("from PurchaseDetailModel where pdid=?", new Object[]{purchaseDeliverBean.getPdid()});
			if(purchaseDetailModel==null){
				return 0;
			}
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=?", new Object[]{purchaseDetailModel.getPoid()});
			if(!("3".equals(purchaseOrderModel.getStatus())||"4".equals(purchaseOrderModel.getStatus())||"5".equals(purchaseOrderModel.getStatus()))){
				return 3;
			}
			if(purchaseOrderModel.getEditStatus()!=null&&purchaseOrderModel.getEditStatus()!=4){
				return 4;//提交变更申请，未通过审核
			}
			if(purchaseDetailModel.getMachineNum()-purchaseDetailModel.getImportNum()<purchaseDeliverBean.getDeliveNum()){
				return 2;
			}
			PurchaseDeliverModel purchaseDeliverModel = new PurchaseDeliverModel();
			purchaseDeliverModel.setPoid(purchaseDetailModel.getPoid());
			purchaseDeliverModel.setPdid(purchaseDetailModel.getPdid());
			purchaseDeliverModel.setDeliverOrderID(purchaseDeliverBean.getDeliverOrderID());
			purchaseDeliverModel.setDeliverUnno(purchaseDeliverBean.getUnno());
			purchaseDeliverModel.setDeliveNum(purchaseDeliverBean.getDeliveNum());
			purchaseDeliverModel.setAllocatedNum(0);
			purchaseDeliverModel.setDeliverStatus(1);
			purchaseDeliverModel.setDeliverPurName(purchaseDeliverBean.getDeliverPurName());
			purchaseDeliverModel.setDeliverContacts(purchaseDeliverBean.getDeliverContacts());
			purchaseDeliverModel.setDeliverContactPhone(purchaseDeliverBean.getDeliverContactPhone());
			purchaseDeliverModel.setDeliverContactMail(purchaseDeliverBean.getDeliverContactMail());
			purchaseDeliverModel.setDeliverReceiveaddr(purchaseDeliverBean.getDeliverReceiveaddr());
			purchaseDeliverModel.setPostCode(purchaseDeliverBean.getPostCode());
			purchaseDeliverModel.setDeliverCdate(new Date());
			purchaseDeliverModel.setDeliverCby(user.getLoginName());
						
			purchaseDeliverDao.saveObject(purchaseDeliverModel);
						
			purchaseDetailModel.setImportNum(purchaseDetailModel.getImportNum()+purchaseDeliverBean.getDeliveNum());
			purchaseDetailModel.setImportDate(new Date());
			purchaseDetailModel.setDetailStatus(7);
						
			purchaseDetailDao.updateObject(purchaseDetailModel);
			
			return 1;
		}
		@Override
		public Integer addDeliverInfo(PurchaseDeliverBean purchaseDeliverBean, UserBean user) {
			PurchaseDeliverModel purchaseDeliverModel = purchaseDeliverDao.queryObjectByHql("from PurchaseDeliverModel where pdlid=?", new Object[]{purchaseDeliverBean.getPdlid()});
			if(purchaseDeliverModel==null)return 0;
			//deliverId
			if(purchaseDeliverBean.getDeliverId()!=null&&!"".equals(purchaseDeliverBean.getDeliverId())){
				purchaseDeliverModel.setDeliverId(purchaseDeliverBean.getDeliverId());
			}
			//deliverName
			if(purchaseDeliverBean.getDeliverName()!=null&&!"".equals(purchaseDeliverBean.getDeliverName())){
				purchaseDeliverModel.setDeliverName(purchaseDeliverBean.getDeliverName());
			}
			//发货时间
			purchaseDeliverModel.setDeliveDate(new Date());
			if(purchaseDeliverModel.getDeliverStatus()==4){
			}else{
				purchaseDeliverModel.setDeliverStatus(3);
			}
			purchaseDeliverDao.saveOrUpdateObject(purchaseDeliverModel);
			return 1;
		}


		@Override
		public DataGridBean queryPurchaseDeliver(PurchaseDeliverBean purchaseDeliverBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchasedeliver where 1=1";
			sqlCount = "select count(*) from bill_purchasedeliver where 1=1";
			if(purchaseDeliverBean.getDeliverOrderID()!=null&&!"".equals(purchaseDeliverBean.getDeliverOrderID())){
				sql += " and deliverOrderID = :deliverOrderID ";
				sqlCount += " and deliverOrderID = :deliverOrderID ";
				map.put("deliverOrderID", purchaseDeliverBean.getDeliverOrderID());
			}
			if(purchaseDeliverBean.getDeliverPurName()!=null&&!"".equals(purchaseDeliverBean.getDeliverPurName())){
				sql += " and deliverPurName = :deliverPurName ";
				sqlCount += " and deliverPurName = :deliverPurName ";
				map.put("deliverPurName", purchaseDeliverBean.getDeliverPurName());
			}
			if(purchaseDeliverBean.getDeliverStatus()!=null&&!"".equals(purchaseDeliverBean.getDeliverStatus())){
				sql += " and deliverStatus = :deliverStatus ";
				sqlCount += " and deliverStatus = :deliverStatus ";
				map.put("deliverStatus", purchaseDeliverBean.getDeliverStatus());
			}
			sql += " order by pdlid desc ";
			List<PurchaseDeliverModel> list = purchaseDeliverDao.queryObjectsBySqlLists(sql, map, purchaseDeliverBean.getPage(), purchaseDeliverBean.getRows(),PurchaseDeliverModel.class);
			Integer counts = purchaseDeliverDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}
		
		@Override
		public DataGridBean queryPurchaseDeliverAndDetail(PurchaseDeliverBean purchaseDeliverBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select l.*,d.pdlid,d.deliverId,d.deliverName,d.deliveNum,d.deliveDate,d.deliver,d.AllocatedNum,d.deliverStatus,d.deliverPurName,d.deliverUnno,"+
				" d.deliverContacts ,d.deliverContactPhone ,d.deliverContactMail ,d.deliverReceiveaddr ,d.postCode,"+
				" d.deliverRemark,d.deliverCdate,d.deliverCby,p.orderid,p.orderday,p.orderMethod from bill_PurchaseDeliver d,bill_purchasedetail l,bill_purchaseorder p where p.poid=l.poid and d.pdid=l.pdid ";
			sqlCount = "select count(1) from bill_PurchaseDeliver d,bill_purchasedetail l,bill_purchaseorder p where p.poid=l.poid and d.pdid=l.pdid ";
			if(purchaseDeliverBean.getDeliverOrderID()!=null&&!"".equals(purchaseDeliverBean.getDeliverOrderID())){
				sql += " and l.deliverOrderID = :deliverOrderID ";
				sqlCount += " and l.deliverOrderID = :deliverOrderID ";
				map.put("deliverOrderID", purchaseDeliverBean.getDeliverOrderID());
			}
			//orderID
			if(purchaseDeliverBean.getOrderID()!=null&&!"".equals(purchaseDeliverBean.getOrderID())){
				sql += " and p.orderid = :orderID ";
				sqlCount += " and p.orderid = :orderID ";
				map.put("orderID", purchaseDeliverBean.getOrderID());
			}
			//deliverStatus
			if(purchaseDeliverBean.getDeliverStatus()!=null&&!"".equals(purchaseDeliverBean.getDeliverStatus())){
				sql += " and d.deliverStatus in(:deliverStatus) ";
				sqlCount += " and d.deliverStatus in(:deliverStatus) ";
				map.put("deliverStatus", purchaseDeliverBean.getDeliverStatus());
			}
			//deliverId
			if(purchaseDeliverBean.getDeliverId()!=null&&!"".equals(purchaseDeliverBean.getDeliverId())){
				sql += " and d.deliverId =:deliverId ";
				sqlCount += " and d.deliverId =:deliverId ";
				map.put("deliverId", purchaseDeliverBean.getDeliverId());
			}
			//deliverUnno
			if(purchaseDeliverBean.getDeliverUnno()!=null&&!"".equals(purchaseDeliverBean.getDeliverUnno())){
				sql += " and d.deliverUnno =:deliverUnno ";
				sqlCount += " and d.deliverUnno =:deliverUnno ";
				map.put("deliverUnno", purchaseDeliverBean.getDeliverUnno());
			}
			if(purchaseDeliverBean.getDeliverPurName()!=null&&!"".equals(purchaseDeliverBean.getDeliverPurName())){
				sql += " and l.deliverPurName = :deliverPurName ";
				sqlCount += " and l.deliverPurName = :deliverPurName ";
				map.put("deliverPurName", purchaseDeliverBean.getDeliverPurName());
			}
			if(purchaseDeliverBean.getMachineModel()!=null&&!"".equals(purchaseDeliverBean.getMachineModel())){
				sql += " and l.machineModel = :machineModel ";
				sqlCount += " and l.machineModel = :machineModel ";
				map.put("machineModel", purchaseDeliverBean.getMachineModel());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDeliverBean.getDeliverCdateEnd()!=null&&!"".equals(purchaseDeliverBean.getDeliverCdateEnd())){
				sql += " and d.deliverCdate <= to_date('"+sdf.format(purchaseDeliverBean.getDeliverCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and d.deliverCdate <= to_date('"+sdf.format(purchaseDeliverBean.getDeliverCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDeliverBean.getDeliverCdate()!=null&&!"".equals(purchaseDeliverBean.getDeliverCdate())){
				sql += " and d.deliverCdate >= to_date('"+sdf.format(purchaseDeliverBean.getDeliverCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and d.deliverCdate >= to_date('"+sdf.format(purchaseDeliverBean.getDeliverCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += " order by d.pdlid desc ";
//			List<PurchaseDeliverModel> list = purchaseDeliverDao.queryObjectsBySqlLists(sql, map, purchaseDeliverBean.getPage(), purchaseDeliverBean.getRows(),PurchaseDeliverModel.class);
			List<Map<String, String>> list = purchaseDeliverDao.queryObjectsBySqlList(sql, map, purchaseDeliverBean.getPage(), purchaseDeliverBean.getRows());
			Integer counts = purchaseDeliverDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}


		@Override
		public Integer updatePurchaseDeliver(PurchaseDeliverBean purchaseDeliverBean, UserBean user) {
			PurchaseDeliverModel purchaseDeliverModel = purchaseDeliverDao.queryObjectByHql("from PurchaseDeliverModel where pdlid=? and deliverStatus=1", new Object[]{purchaseDeliverBean.getPdlid()});
			if(purchaseDeliverModel==null){
				return 0;
			}
			purchaseDeliverModel.setDeliverStatus(2);
			purchaseDeliverDao.updateObject(purchaseDeliverModel);
			return 1;
		}


		@Override
		public List<Map<String, Object>> exportPurchaseDeliver(PurchaseDeliverBean purchaseDeliverBean, UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select l.*,d.pdlid,d.deliverId,d.deliverName,d.deliveNum,d.deliveDate,d.deliver,d.AllocatedNum,d.deliverStatus,d.deliverPurName,d.deliverUnno,"+
				" d.deliverContacts ,d.deliverContactPhone ,d.deliverContactMail ,d.deliverReceiveaddr ,d.postCode,"+
				" d.deliverRemark,d.deliverCdate,d.deliverCby,p.orderid,p.orderday from bill_PurchaseDeliver d,bill_purchasedetail l,bill_purchaseorder p where p.poid=l.poid and d.pdid=l.pdid ";
			if(purchaseDeliverBean.getDeliverOrderID()!=null&&!"".equals(purchaseDeliverBean.getDeliverOrderID())){
				sql += " and l.deliverOrderID = :deliverOrderID ";
				map.put("deliverOrderID", purchaseDeliverBean.getDeliverOrderID());
			}
			//orderID
			if(purchaseDeliverBean.getOrderID()!=null&&!"".equals(purchaseDeliverBean.getOrderID())){
				sql += " and p.orderid = :orderID ";
				map.put("orderID", purchaseDeliverBean.getOrderID());
			}
			//deliverStatus
			if(purchaseDeliverBean.getDeliverStatus()!=null&&!"".equals(purchaseDeliverBean.getDeliverStatus())){
				sql += " and d.deliverStatus in(:deliverStatus) ";
				map.put("deliverStatus", purchaseDeliverBean.getDeliverStatus());
			}
			//deliverId
			if(purchaseDeliverBean.getDeliverId()!=null&&!"".equals(purchaseDeliverBean.getDeliverId())){
				sql += " and d.deliverId =:deliverId ";
				map.put("deliverId", purchaseDeliverBean.getDeliverId());
			}
			//deliverUnno
			if(purchaseDeliverBean.getDeliverUnno()!=null&&!"".equals(purchaseDeliverBean.getDeliverUnno())){
				sql += " and d.deliverUnno =:deliverUnno ";
				map.put("deliverUnno", purchaseDeliverBean.getDeliverUnno());
			}
			if(purchaseDeliverBean.getDeliverPurName()!=null&&!"".equals(purchaseDeliverBean.getDeliverPurName())){
				sql += " and l.deliverPurName = :deliverPurName ";
				map.put("deliverPurName", purchaseDeliverBean.getDeliverPurName());
			}
			if(purchaseDeliverBean.getMachineModel()!=null&&!"".equals(purchaseDeliverBean.getMachineModel())){
				sql += " and l.machineModel = :machineModel ";
				map.put("machineModel", purchaseDeliverBean.getMachineModel());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseDeliverBean.getDeliverCdateEnd()!=null&&!"".equals(purchaseDeliverBean.getDeliverCdateEnd())){
				sql += " and d.deliverCdate <= to_date('"+sdf.format(purchaseDeliverBean.getDeliverCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseDeliverBean.getDeliverCdate()!=null&&!"".equals(purchaseDeliverBean.getDeliverCdate())){
				sql += " and d.deliverCdate >= to_date('"+sdf.format(purchaseDeliverBean.getDeliverCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += " order by d.pdlid ";
			
			List<Map<String, Object>> list = purchaseDeliverDao.queryObjectsBySqlListMap2(sql, map);
			
			return list;
		}


		@Override
		public Integer updateDeliverInfo(PurchaseDeliverBean purchaseDeliverBean, UserBean user) {
			PurchaseDeliverModel purchaseDeliverModel = purchaseDeliverDao.queryObjectByHql("from PurchaseDeliverModel where pdlid=?", new Object[]{purchaseDeliverBean.getPdlid()});
			if(purchaseDeliverModel==null){
				return 0;
			}
			purchaseDeliverModel.setDeliverContacts(purchaseDeliverBean.getDeliverContacts());
			purchaseDeliverModel.setDeliverContactPhone(purchaseDeliverBean.getDeliverContactPhone());
			purchaseDeliverModel.setDeliverContactMail(purchaseDeliverBean.getDeliverContactMail());
			purchaseDeliverModel.setDeliverReceiveaddr(purchaseDeliverBean.getDeliverReceiveaddr());
			purchaseDeliverDao.updateObject(purchaseDeliverModel);
			return 1;
		}
}
