package com.hrt.biz.bill.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IPurchaseStorageDao;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.model.PurchaseStorageModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseStorageBean;
import com.hrt.biz.bill.service.IPurchaseStorageService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class PurchaseStorageServiceImpl implements IPurchaseStorageService {

		private IPurchaseStorageDao purchaseStorageDao;

		public IPurchaseStorageDao getPurchaseStorageDao() {
			return purchaseStorageDao;
		}

		public void setPurchaseStorageDao(IPurchaseStorageDao purchaseStorageDao) {
			this.purchaseStorageDao = purchaseStorageDao;
		}

		@Override
		public DataGridBean listPurchaseStorage(PurchaseStorageBean purchaseStorageBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchasestorage where storageApproveStatus!='D' ";
			sqlCount = "select count(*) from bill_purchasestorage where storageApproveStatus!='D' ";
			//判断库存管理类型 1-借样单管理 2-盘盈 3-盘亏 4-库存调拨
			if(purchaseStorageBean.getStorageTypes()==1){
				sql += " and storageType = 1 ";
				sqlCount += " and storageType = 1 ";
			}else if(purchaseStorageBean.getStorageTypes()==2){
				sql += " and storageType in (2,3) ";
				sqlCount += " and storageType in (2,3) ";
			}else if(purchaseStorageBean.getStorageTypes()==4){
				sql += " and storageType = 4 ";
				sqlCount += " and storageType = 4 ";
			}
			//借样单查询
			if(purchaseStorageBean.getLender()!=null&&!"".equals(purchaseStorageBean.getLender())){
				sql += " and lender = :lender ";
				sqlCount += " and lender = :lender ";
				map.put("lender", purchaseStorageBean.getLender());
			}
			if(purchaseStorageBean.getLendDate()!=null&&!"".equals(purchaseStorageBean.getLendDate())){
				sql += " and lendDate >= "+purchaseStorageBean.getLendDate().replace("-", "")+" ";
				sqlCount += " and lendDate >= "+purchaseStorageBean.getLendDate().replace("-", "")+" ";
			}
			if(purchaseStorageBean.getLendDateEnd()!=null&&!"".equals(purchaseStorageBean.getLendDateEnd())){
				sql += " and lendDate <= "+purchaseStorageBean.getLendDateEnd().replace("-", "")+" ";
				sqlCount += " and lendDate <= "+purchaseStorageBean.getLendDateEnd().replace("-", "")+" ";
			}
			//盘盈盘亏
			if(purchaseStorageBean.getStorageType()!=null&&!"".equals(purchaseStorageBean.getStorageType())){
				sql += " and storageType = :storageType ";
				sqlCount += " and storageType = :storageType ";
				map.put("storageType", purchaseStorageBean.getStorageType());
			}
			if(purchaseStorageBean.getStorageStatus()!=null&&!"".equals(purchaseStorageBean.getStorageStatus())){
				sql += " and storageStatus = :storageStatus ";
				sqlCount += " and storageStatus = :storageStatus ";
				map.put("storageStatus", purchaseStorageBean.getStorageStatus());
			}
			//库存调拨
			if(purchaseStorageBean.getOutStorage()!=null&&!"".equals(purchaseStorageBean.getOutStorage())){
				sql += " and outStorage = :outStorage ";
				sqlCount += " and outStorage = :outStorage ";
				map.put("outStorage", purchaseStorageBean.getOutStorage());
			}
			if(purchaseStorageBean.getInStorage()!=null&&!"".equals(purchaseStorageBean.getInStorage())){
				sql += " and inStorage = :inStorage ";
				sqlCount += " and inStorage = :inStorage ";
				map.put("inStorage", purchaseStorageBean.getInStorage());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseStorageBean.getStorageCdate()!=null&&!"".equals(purchaseStorageBean.getStorageCdate())){
				sql += " and storageCdate >= to_date('"+sdf.format(purchaseStorageBean.getStorageCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and storageCdate = to_date('"+sdf.format(purchaseStorageBean.getStorageCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseStorageBean.getStorageCdateEnd()!=null&&!"".equals(purchaseStorageBean.getStorageCdateEnd())){
				sql += " and storageCdate <= to_date('"+sdf.format(purchaseStorageBean.getStorageCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and storageCdate <= to_date('"+sdf.format(purchaseStorageBean.getStorageCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			//共有的
			if(purchaseStorageBean.getStorageStatus()!=null&&!"".equals(purchaseStorageBean.getStorageStatus())){
				sql += " and storageStatus = :storageStatus ";
				sqlCount += " and storageStatus = :storageStatus ";
				map.put("storageStatus", purchaseStorageBean.getStorageStatus());
			}
			if(purchaseStorageBean.getStorageBrandName()!=null&&!"".equals(purchaseStorageBean.getStorageBrandName())){
				sql += " and storageBrandName = :storageBrandName ";
				sqlCount += " and storageBrandName = :storageBrandName ";
				map.put("storageBrandName", purchaseStorageBean.getStorageBrandName());
			}
			if(purchaseStorageBean.getStorageMachineModel()!=null&&!"".equals(purchaseStorageBean.getStorageMachineModel())){
				sql += " and storageMachineModel = :storageMachineModel ";
				sqlCount += " and storageMachineModel = :storageMachineModel ";
				map.put("storageMachineModel", purchaseStorageBean.getStorageMachineModel());
			}
			if(purchaseStorageBean.getStorageStatuss()!=null&&!"".equals(purchaseStorageBean.getStorageStatuss())){
				sql += " and storageStatus in ("+purchaseStorageBean.getStorageStatuss()+") ";
				sqlCount += " and storageStatus in ("+purchaseStorageBean.getStorageStatuss()+") ";
			}
			if(purchaseStorageBean.getStorageID()!=null&&!"".equals(purchaseStorageBean.getStorageID())){
				sql += " and storageID =:storageID ";
				sqlCount += " and storageID =:storageID ";
				map.put("storageID", purchaseStorageBean.getStorageID());
			}
			
			sql += " order by psid desc ";
			List<PurchaseStorageModel> list = purchaseStorageDao.queryObjectsBySqlLists(sql, map, purchaseStorageBean.getPage(), purchaseStorageBean.getRows(),PurchaseStorageModel.class);
			Integer counts = purchaseStorageDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}

		@Override
		public Integer savePurchaseStorage(PurchaseStorageBean purchaseStorageBean, UserBean user) {
			PurchaseStorageModel purchaseStorageModel = new PurchaseStorageModel();
			BeanUtils.copyProperties(purchaseStorageBean, purchaseStorageModel);
			SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
			StringBuffer storageID = new StringBuffer(sdf.format(new Date()));
			while (storageID.length() < 16) {
				String s = Double.toString(Math.random()*10);
				if (s.length() > 3) {
					storageID.append(s.substring(0, 1));
				}
			}
			purchaseStorageModel.setStorageID(storageID.toString());
			if(purchaseStorageModel.getStorageType()==1){
				purchaseStorageModel.setLendDate(purchaseStorageBean.getLendDate().replace("-", ""));
			}
			purchaseStorageModel.setStorageStatus(1);//待提交（未借出）
			purchaseStorageModel.setLoadStorageNum(0);//已修改的设备数量
			purchaseStorageModel.setStorageCby(user.getLoginName());
			purchaseStorageModel.setStorageCdate(new Date());//创建时间（盘盈盘亏时间，调拨时间）
			purchaseStorageModel.setStorageApproveStatus("A");
			
			purchaseStorageDao.saveObject(purchaseStorageModel);
			return 1;
		}

		@Override
		public Integer updatePurchaseStorageStatus(PurchaseStorageBean purchaseStorageBean, UserBean user) {
			PurchaseStorageModel purchaseStorageModel = purchaseStorageDao.queryObjectByHql("from PurchaseStorageModel where storageApproveStatus!='D' and psid=?", new Object[]{purchaseStorageBean.getPsid()});
			if(purchaseStorageModel!=null){
				//借样单修改归还时间，其余修改状态为待审核
				if(purchaseStorageModel.getStorageType()==1){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					purchaseStorageModel.setReturnDate(sdf.format(new Date()));
					purchaseStorageModel.setStorageStatus(3);//已归还
					purchaseStorageDao.executeSqlUpdate("update bill_terminalinfo set unno='110000' WHERE terorderID='"+purchaseStorageModel.getStorageID()+"'", null);
				}else{
					purchaseStorageModel.setStorageStatus(2);
				}
				purchaseStorageModel.setStorageApproveStatus("M");
				purchaseStorageDao.updateObject(purchaseStorageModel);
				return 1;
			}
			
			return 0;
		}

		@Override
		public Integer updatePurchaseStorageY(PurchaseStorageBean purchaseStorageBean, UserBean user) {
			PurchaseStorageModel purchaseStorageModel = purchaseStorageDao.queryObjectByHql("from PurchaseStorageModel where storageApproveStatus!='D' and storageStatus=2 and psid=?", new Object[]{purchaseStorageBean.getPsid()});
			if(purchaseStorageModel!=null){
				purchaseStorageModel.setStorageApprover(user.getLoginName());
				purchaseStorageModel.setStorageApproveDate(new Date());
				purchaseStorageModel.setStorageStatus(3);
				purchaseStorageModel.setStorageApproveStatus("M");
				
				purchaseStorageDao.updateObject(purchaseStorageModel);
				return 1;
			}
			return 0;
		}

		@Override
		public Integer updatePurchaseStorageK(PurchaseStorageBean purchaseStorageBean, UserBean user) {
			PurchaseStorageModel purchaseStorageModel = purchaseStorageDao.queryObjectByHql("from PurchaseStorageModel where storageApproveStatus!='D' and storageStatus=2 and psid=?", new Object[]{purchaseStorageBean.getPsid()});
			if(purchaseStorageModel!=null){
				purchaseStorageModel.setStorageApprover(user.getLoginName());
				purchaseStorageModel.setStorageApproveDate(new Date());
				purchaseStorageModel.setStorageStatus(1);
				purchaseStorageModel.setStorageApproveStatus("K");
				purchaseStorageModel.setStorageApproveNote(purchaseStorageBean.getStorageApproveNote());
				
				purchaseStorageDao.updateObject(purchaseStorageModel);
				return 1;
			}
			return 0;
		}

		@Override
		public Integer updatePurchaseStorage(PurchaseStorageBean purchaseStorageBean, UserBean user) {
			PurchaseStorageModel purchaseStorageModel = purchaseStorageDao.queryObjectByHql("from PurchaseStorageModel where storageApproveStatus!='D' and psid=?", new Object[]{purchaseStorageBean.getPsid()});
			if(purchaseStorageModel!=null){
				if(purchaseStorageModel.getStorageType()==1){
					purchaseStorageModel.setLendDate(purchaseStorageBean.getLendDate().replace("-", ""));
				}
				
				purchaseStorageModel.setStorageBrandName(purchaseStorageBean.getStorageBrandName());
				purchaseStorageModel.setStorageMachineModel(purchaseStorageBean.getStorageMachineModel());
				purchaseStorageModel.setStorageMachineNum(purchaseStorageBean.getStorageMachineNum());
				purchaseStorageModel.setLender(purchaseStorageBean.getLender());
				purchaseStorageModel.setDepartment(purchaseStorageBean.getDepartment());
				purchaseStorageModel.setStorageRemark(purchaseStorageBean.getStorageRemark());
				purchaseStorageModel.setOutStorage(purchaseStorageBean.getOutStorage());
				purchaseStorageModel.setInStorage(purchaseStorageBean.getInStorage());
				
				purchaseStorageModel.setStorageApproveStatus("M");
				purchaseStorageDao.updateObject(purchaseStorageModel);
				return 1;
			}
			
			return 0;
		}

		@Override
		public Integer deletePurchaseStorage(PurchaseStorageBean purchaseStorageBean, UserBean user) {
			String sql = "from PurchaseStorageModel where psid = ? and storageApproveStatus!='D'";
			PurchaseStorageModel purchaseStorageModel = purchaseStorageDao.queryObjectByHql(sql, new Object[]{purchaseStorageBean.getPsid()});
			if(purchaseStorageModel!=null){
				purchaseStorageModel.setStorageApproveStatus("D");
				
				purchaseStorageDao.updateObject(purchaseStorageModel);
				return 1;
			}
			return 0;
		}

		@Override
		public List<Map<String, Object>> exportPurchaseStorage(PurchaseStorageBean purchaseStorageBean, UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchasestorage where storageApproveStatus!='D' ";
			//判断库存管理类型 1-借样单管理 2-盘盈 3-盘亏 4-库存调拨
			if(purchaseStorageBean.getStorageTypes()==1){
				sql += " and storageType = 1 ";
			}else if(purchaseStorageBean.getStorageTypes()==2){
				sql += " and storageType in (2,3) ";
			}else if(purchaseStorageBean.getStorageTypes()==4){
				sql += " and storageType = 4 ";
			}
			//盘盈盘亏
			if(purchaseStorageBean.getStorageType()!=null&&!"".equals(purchaseStorageBean.getStorageType())){
				sql += " and storageType = :storageType ";
				map.put("storageType", purchaseStorageBean.getStorageType());
			}
			if(purchaseStorageBean.getStorageStatus()!=null&&!"".equals(purchaseStorageBean.getStorageStatus())){
				sql += " and storageStatus = :storageStatus ";
				map.put("storageStatus", purchaseStorageBean.getStorageStatus());
			}
			//共有的
			if(purchaseStorageBean.getStorageStatus()!=null&&!"".equals(purchaseStorageBean.getStorageStatus())){
				sql += " and storageStatus = :storageStatus ";
				map.put("storageStatus", purchaseStorageBean.getStorageStatus());
			}
			if(purchaseStorageBean.getStorageBrandName()!=null&&!"".equals(purchaseStorageBean.getStorageBrandName())){
				sql += " and storageBrandName = :storageBrandName ";
				map.put("storageBrandName", purchaseStorageBean.getStorageBrandName());
			}
			if(purchaseStorageBean.getStorageMachineModel()!=null&&!"".equals(purchaseStorageBean.getStorageMachineModel())){
				sql += " and storageMachineModel = :storageMachineModel ";
				map.put("storageMachineModel", purchaseStorageBean.getStorageMachineModel());
			}
			if(purchaseStorageBean.getStorageStatuss()!=null&&!"".equals(purchaseStorageBean.getStorageStatuss())){
				sql += " and storageStatus in ("+purchaseStorageBean.getStorageStatuss()+") ";
			}
			
			sql += " order by psid desc ";
			
			List<Map<String, Object>> list = purchaseStorageDao.queryObjectsBySqlListMap2(sql, map);
			
			return list;
		}

		@Override
		public List<Map<String, Object>> exportPurchaseStorageD(PurchaseStorageBean purchaseStorageBean,
				UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchasestorage where storageApproveStatus!='D' ";
			//判断库存管理类型 1-借样单管理 2-盘盈 3-盘亏 4-库存调拨
			if(purchaseStorageBean.getStorageTypes()==1){
				sql += " and storageType = 1 ";
			}else if(purchaseStorageBean.getStorageTypes()==2){
				sql += " and storageType in (2,3) ";
			}else if(purchaseStorageBean.getStorageTypes()==4){
				sql += " and storageType = 4 ";
			}
			//库存调拨
			if(purchaseStorageBean.getOutStorage()!=null&&!"".equals(purchaseStorageBean.getOutStorage())){
				sql += " and outStorage = :outStorage ";
				map.put("outStorage", purchaseStorageBean.getOutStorage());
			}
			if(purchaseStorageBean.getInStorage()!=null&&!"".equals(purchaseStorageBean.getInStorage())){
				sql += " and inStorage = :inStorage ";
				map.put("inStorage", purchaseStorageBean.getInStorage());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseStorageBean.getStorageCdate()!=null&&!"".equals(purchaseStorageBean.getStorageCdate())){
				sql += " and storageCdate >= to_date('"+sdf.format(purchaseStorageBean.getStorageCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseStorageBean.getStorageCdateEnd()!=null&&!"".equals(purchaseStorageBean.getStorageCdateEnd())){
				sql += " and storageCdate <= to_date('"+sdf.format(purchaseStorageBean.getStorageCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			//共有的
			if(purchaseStorageBean.getStorageStatus()!=null&&!"".equals(purchaseStorageBean.getStorageStatus())){
				sql += " and storageStatus = :storageStatus ";
				map.put("storageStatus", purchaseStorageBean.getStorageStatus());
			}
			if(purchaseStorageBean.getStorageBrandName()!=null&&!"".equals(purchaseStorageBean.getStorageBrandName())){
				sql += " and storageBrandName = :storageBrandName ";
				map.put("storageBrandName", purchaseStorageBean.getStorageBrandName());
			}
			if(purchaseStorageBean.getStorageMachineModel()!=null&&!"".equals(purchaseStorageBean.getStorageMachineModel())){
				sql += " and storageMachineModel = :storageMachineModel ";
				map.put("storageMachineModel", purchaseStorageBean.getStorageMachineModel());
			}
			if(purchaseStorageBean.getStorageStatuss()!=null&&!"".equals(purchaseStorageBean.getStorageStatuss())){
				sql += " and storageStatus in ("+purchaseStorageBean.getStorageStatuss()+") ";
			}
			
			sql += " order by psid desc ";
			
			List<Map<String, Object>> list = purchaseStorageDao.queryObjectsBySqlListMap2(sql, map);
			
			return list;
		}

}
