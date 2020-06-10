package com.hrt.biz.bill.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IPurchaseDetailDao;
import com.hrt.biz.bill.dao.IPurchaseOrderDao;
import com.hrt.biz.bill.entity.model.PurchaseDetailModel;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseOrderBean;
import com.hrt.biz.bill.service.IPurchaseOrderService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

		private IPurchaseOrderDao purchaseOrderDao;
		private IPurchaseDetailDao purchaseDetailDao;
		private IUnitDao unitDao;

		public IUnitDao getUnitDao() {
			return unitDao;
		}
		public void setUnitDao(IUnitDao unitDao) {
			this.unitDao = unitDao;
		}
		public IPurchaseDetailDao getPurchaseDetailDao() {
			return purchaseDetailDao;
		}
		public void setPurchaseDetailDao(IPurchaseDetailDao purchaseDetailDao) {
			this.purchaseDetailDao = purchaseDetailDao;
		}
		public IPurchaseOrderDao getPurchaseOrderDao() {
			return purchaseOrderDao;
		}
		public void setPurchaseOrderDao(IPurchaseOrderDao purchaseOrderDao) {
			this.purchaseOrderDao = purchaseOrderDao;
		}

		/**
		 * 查询采购单
		 */
		@Override
		public DataGridBean queryPurchaseOrders(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchaseorder where approveStatus != 'D' ";
			sqlCount = "select count(*) from bill_purchaseorder where approveStatus != 'D' ";
			
			if(purchaseOrderBean.getOrderID()!=null&&!"".equals(purchaseOrderBean.getOrderID())){
				sql += " and orderID = :orderID ";
				sqlCount += " and orderID = :orderID ";
				map.put("orderID", purchaseOrderBean.getOrderID());
			}
			//1-厂商单 2-代理单3-厂商从代理处采购
			if(purchaseOrderBean.getOrderMethod()!=null&&!"".equals(purchaseOrderBean.getOrderMethod())){
				if(purchaseOrderBean.getOrderMethod()==2) {
					sql += " and orderMethod = :orderMethod ";
					sqlCount += " and orderMethod = :orderMethod ";
					map.put("orderMethod", purchaseOrderBean.getOrderMethod());
				}else {
					sql += " and orderMethod in (1,3) ";
					sqlCount += " and orderMethod in (1,3)";
				}
			}
			if(purchaseOrderBean.getVendorName()!=null&&!"".equals(purchaseOrderBean.getVendorName())){
				sql += " and vendorName = :vendorName ";
				sqlCount += " and vendorName = :vendorName ";
				map.put("vendorName", purchaseOrderBean.getVendorName());
			}
			if(purchaseOrderBean.getPurchaserName()!=null&&!"".equals(purchaseOrderBean.getPurchaserName())){
				sql += " and purchaserName = :purchaserName ";
				sqlCount += " and purchaserName = :purchaserName ";
				map.put("purchaserName", purchaseOrderBean.getPurchaserName());
			}
			if(purchaseOrderBean.getEditStatus()!=null&&!"".equals(purchaseOrderBean.getEditStatus())){
				sql += " and editStatus = :editStatus ";
				sqlCount += " and editStatus = :editStatus ";
				map.put("editStatus", purchaseOrderBean.getEditStatus());
			}
			if(purchaseOrderBean.getStatus()!=null&&!"".equals(purchaseOrderBean.getStatus())){
				sql += " and status in ("+purchaseOrderBean.getStatus()+") ";
				sqlCount += " and status in ("+purchaseOrderBean.getStatus()+") ";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseOrderBean.getCdateEnd()!=null&&!"".equals(purchaseOrderBean.getCdateEnd())){
				sql += " and cdate <= to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and cdate <= to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseOrderBean.getCdate()!=null&&!"".equals(purchaseOrderBean.getCdate())){
				sql += " and cdate >= to_date('"+sdf.format(purchaseOrderBean.getCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and cdate >= to_date('"+sdf.format(purchaseOrderBean.getCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseOrderBean.getUnno()!=null&&!"".equals(purchaseOrderBean.getUnno())){
				sql += " and unno = :unno ";
				sqlCount += " and unno = :unno ";
				map.put("unno", purchaseOrderBean.getUnno());
			}
			sql += " order by poid desc ";
			List<PurchaseOrderModel> list = purchaseOrderDao.queryObjectsBySqlLists(sql, map, purchaseOrderBean.getPage(), purchaseOrderBean.getRows(),PurchaseOrderModel.class);
			Integer counts = purchaseOrderDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}
		
		@Override
		public PurchaseOrderModel savePurchaseOrder(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
			BeanUtils.copyProperties(purchaseOrderBean, purchaseOrderModel);
			Date date = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String now = sdf.format(date);
			//1--厂商单  2--代理单
			if(purchaseOrderBean.getOrderMethod()==1 || purchaseOrderBean.getOrderMethod() == 3){
				purchaseOrderModel.setWriteoffStatus(1);//核销状态
				purchaseOrderModel.setWriteoffAmt(0.0);
				purchaseOrderModel.setWriteoffNum(0);
				purchaseOrderModel.setPurchaser(user.getLoginName());
				if("1".equals(purchaseOrderModel.getPurchaserName())){
					purchaseOrderModel.setPurchaserName("和融通公司");
					purchaseOrderModel.setUnno("HRT");//库位
				}else if("2".equals(purchaseOrderModel.getPurchaserName())){
					purchaseOrderModel.setPurchaserName("会员宝公司");
					purchaseOrderModel.setUnno("HYB");//库位
				}
			}else if(purchaseOrderBean.getOrderMethod()==2){
				String unno = purchaseOrderBean.getUnno();
				UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unno);
				if(unitModel!=null){
					purchaseOrderModel.setUnno(unno);
					purchaseOrderModel.setPurchaserName(unitModel.getUnitName());
				}else{
					return null;
				}
				if("1".equals(purchaseOrderModel.getVendorName())){
					purchaseOrderModel.setVendorName("和融通公司");//出库库位
				}else if("2".equals(purchaseOrderModel.getVendorName())){
					purchaseOrderModel.setVendorName("会员宝公司");;
				}
				
				purchaseOrderModel.setPurchaser(purchaseOrderBean.getPurchaser());
			}
			purchaseOrderModel.setOrderDay(now);
			purchaseOrderModel.setOrderpayAmt(0.0);
			purchaseOrderModel.setWriteoffAmt(0.0);
			purchaseOrderModel.setStatus("1");
			purchaseOrderModel.setCdate(date);
			purchaseOrderModel.setCby(user.getLoginName());
			purchaseOrderModel.setApproveStatus("A");
			
			purchaseOrderDao.saveObject(purchaseOrderModel);
			return purchaseOrderModel;
		}
		
		/**
		 * 删除采购单
		 */
		@Override
		public Integer deletePurchaseOrder(Integer poid, UserBean user) {
			String sql = "from PurchaseOrderModel where poid = ? and approveStatus!='D'";
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql(sql, new Object[]{poid});
			if(purchaseOrderModel!=null){
				purchaseOrderModel.setApproveStatus("D");
				purchaseOrderModel.setLmby(user.getLoginName());
				purchaseOrderModel.setLmdate(new Date());
				
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return 1;
			}else{
				return 0;
			}
		}

		@Override
		public boolean saveNewPurchaseOrder(Integer poid) {
			String sql = "from PurchaseOrderModel where poid = ? and approveStatus!='D'";
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql(sql, new Object[]{poid});
			if(purchaseOrderModel!=null){
				Integer num = 0;
				Double amt = 0.0;
				String sql1 = "from PurchaseDetailModel where poid = ? and detailApproveStatus!='D'";
				List<PurchaseDetailModel> list = purchaseDetailDao.queryObjectsByHqlList(sql1, new Object[]{poid});
				if(list!=null){
					for(int i =0;i<list.size();i++){
						PurchaseDetailModel purchaseDetailModel = list.get(i);
						num += purchaseDetailModel.getMachineNum();
						amt += purchaseDetailModel.getDetailAmt();
					}
				purchaseOrderModel.setCancelDate(new Date());
				purchaseOrderModel.setCancelNum((purchaseOrderModel.getCancelNum()==null?0:purchaseOrderModel.getCancelNum())+(purchaseOrderModel.getOrderNum()-num));
				purchaseOrderModel.setOrderNum(num);
				purchaseOrderModel.setOrderAmt(amt);
				}
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return true;
			}else{
				return false;
			}
		}

		@Override
		public PurchaseOrderModel updatePurchaseOrder(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and approveStatus!='D'", new Object[]{purchaseOrderBean.getPoid()});
			purchaseOrderModel.setOrderMethod(purchaseOrderBean.getOrderMethod());
			if(purchaseOrderModel.getOrderMethod()==1){
				purchaseOrderModel.setVendorName(purchaseOrderBean.getVendorName());
				purchaseOrderModel.setRemarks(purchaseOrderBean.getRemarks());
			}
			if(purchaseOrderModel.getOrderMethod()==2){
				String unno = purchaseOrderBean.getUnno();
				UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unno);
				//判断销售所填unno是否存在
				if(unitModel!=null){
					purchaseOrderModel.setUnno(unno);
					purchaseOrderModel.setPurchaserGroup(purchaseOrderBean.getPurchaserGroup());
					purchaseOrderModel.setPurchaser(purchaseOrderBean.getPurchaser());
					purchaseOrderModel.setPurchaserName(unitModel.getUnitName());
					purchaseOrderModel.setRemarks(purchaseOrderBean.getRemarks());
					if("1".equals(purchaseOrderModel.getVendorName())){
						purchaseOrderModel.setVendorName("和融通公司");//出库库位
					}else if("2".equals(purchaseOrderModel.getVendorName())){
						purchaseOrderModel.setVendorName("会员宝公司");;
					}
				}else{
					return null;
				}
			}
			purchaseOrderModel.setBusDate(purchaseOrderBean.getBusDate());
			purchaseOrderModel.setContacts(purchaseOrderBean.getContacts());
			purchaseOrderModel.setContactPhone(purchaseOrderBean.getContactPhone());
			purchaseOrderModel.setContactMail(purchaseOrderBean.getContactMail());
			purchaseOrderModel.setLmdate(new Date());
			purchaseOrderModel.setLmby(user.getLoginName());
			purchaseOrderModel.setApproveStatus("M");
			
			purchaseOrderDao.updateObject(purchaseOrderModel);
			
			return purchaseOrderModel;
		}
		
		/**
		 * 变更订单为可修改状态
		 */
		@Override
		public Integer updatePurchaseOrderStatusToEdit(Integer poid, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and approveStatus!='D'", new Object[]{poid});
			if(purchaseOrderModel!=null){
				purchaseOrderModel.setEditStatus(1);//变为可修改的状态
				purchaseOrderModel.setLmdate(new Date());
				purchaseOrderModel.setLmby(user.getLoginName());
				purchaseOrderModel.setApproveStatus("M");
				
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return 1;
			}
			return 0;
		}
		
		/**
		 * 提交审核(已审核的订单再次变更为可修改之后提交)
		 */
		@Override
		public Integer updateEditPurchaseOrder(Integer poid, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and editStatus=1 and approveStatus!='D'", new Object[]{poid});
			if(purchaseOrderModel!=null){
				purchaseOrderModel.setEditStatus(2);
				
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return 1;
			}
			return 0;
		}
		
		/**
		 * 提交审核
		 */
		@Override
		public Integer updatePurchaseOrderStatus(Integer poid, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and status=1 and approveStatus!='D'", new Object[]{poid});
			if(purchaseOrderModel!=null){
				purchaseOrderModel.setStatus("2");
				purchaseOrderModel.setLmdate(new Date());
				purchaseOrderModel.setLmby(user.getLoginName());
				purchaseOrderModel.setApproveStatus("M");
				
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return 1;
			}
			return 0;
		}
		
		/**
		 * 查询审核采购单
		 */
		@Override
		public DataGridBean queryPurchaseOrderWJoin(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchaseorder where status != 1 ";
			sqlCount = "select count(*) from bill_purchaseorder where status != 1 ";
			//orderID
			if(purchaseOrderBean.getOrderID()!=null&&!"".equals(purchaseOrderBean.getOrderID())){
				sql += " and orderID = :orderID ";
				sqlCount += " and orderID = :orderID ";
				map.put("orderID", purchaseOrderBean.getOrderID());
			}
			//status
			if(purchaseOrderBean.getStatus()!=null&&!"".equals(purchaseOrderBean.getStatus())){
				sql += " and status in(:status) ";
				sqlCount += " and status in(:status) ";
				map.put("status", purchaseOrderBean.getStatus());
			}
			//orderMethod
			if(purchaseOrderBean.getOrderMethod()!=null&&!"".equals(purchaseOrderBean.getOrderMethod())){
				sql += " and orderMethod =:orderMethod ";
				sqlCount += " and orderMethod =:orderMethod ";
				map.put("orderMethod", purchaseOrderBean.getOrderMethod());
			}
			//vendorName
			if(purchaseOrderBean.getVendorName()!=null&&!"".equals(purchaseOrderBean.getVendorName())){
				sql += " and vendorName =:vendorName ";
				sqlCount += " and vendorName =:vendorName ";
				map.put("vendorName", purchaseOrderBean.getVendorName());
			}
			//purchaserName
			if(purchaseOrderBean.getPurchaserName()!=null&&!"".equals(purchaseOrderBean.getPurchaserName())){
				sql += " and purchaserName =:purchaserName ";
				sqlCount += " and purchaserName =:purchaserName ";
				map.put("purchaserName", purchaseOrderBean.getPurchaserName());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseOrderBean.getCdateEnd()!=null&&!"".equals(purchaseOrderBean.getCdateEnd())){
				sql += " and cdate <= to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and cdate <= to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseOrderBean.getCdate()!=null&&!"".equals(purchaseOrderBean.getCdate())){
				sql += " and cdate >= to_date('"+sdf.format(purchaseOrderBean.getCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				sqlCount += " and cdate >= to_date('"+sdf.format(purchaseOrderBean.getCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "order by poid desc";
			sqlCount += "order by poid desc";
			List<PurchaseOrderModel> list = purchaseOrderDao.queryObjectsBySqlLists(sql, map, purchaseOrderBean.getPage(), purchaseOrderBean.getRows(),PurchaseOrderModel.class);
			Integer counts = purchaseOrderDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}
		
		/**
		 * 审核通过
		 */
		@Override
		public Integer updatePurchaseOrderY(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and status=2 ", new Object[]{purchaseOrderBean.getPoid()});
			if(purchaseOrderModel!=null){
				purchaseOrderModel.setStatus("3");
				purchaseOrderModel.setLmdate(new Date());
				purchaseOrderModel.setLmby(user.getLoginName());
				purchaseOrderModel.setApproveDate(new Date());
				purchaseOrderModel.setApprover(user.getLoginName());
				
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return 1;
			}
			return 0;
		}
		
		/**
		 * 审核退回
		 */
		@Override
		public Integer updatePurchaseOrderK(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and status=2 ", new Object[]{purchaseOrderBean.getPoid()});
			if(purchaseOrderModel!=null){
				purchaseOrderModel.setStatus("1");
				purchaseOrderModel.setLmdate(new Date());
				purchaseOrderModel.setLmby(user.getLoginName());
				purchaseOrderModel.setApproveStatus("K");
				purchaseOrderModel.setApproveDate(new Date());
				purchaseOrderModel.setApprover(user.getLoginName());
				purchaseOrderModel.setApproveNote(purchaseOrderBean.getApproveNote());
				
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return 1;
			}
			return 0;
		}
		@Override
		public List<Map<String, Object>> exportPurchaseOrderWJoin(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select a.*,b.brandName,b.orderType,b.machineModel,b.snType,b.rebateType,b.rate,b.scanRate,b.secondRate,b.machinePrice,b.machineNum,b.detailAmt from bill_purchaseorder a,bill_purchasedetail b where a.status != 1 and b.detailApproveStatus!='D' ";
			//orderID
			if(purchaseOrderBean.getOrderID()!=null&&!"".equals(purchaseOrderBean.getOrderID())){
				sql += " and a.orderID = :orderID ";
				map.put("orderID", purchaseOrderBean.getOrderID());
			}
			//status
			if(purchaseOrderBean.getStatus()!=null&&!"".equals(purchaseOrderBean.getStatus())){
				sql += " and a.status in(:status) ";
				map.put("status", purchaseOrderBean.getStatus());
			}
			//orderMethod
			if(purchaseOrderBean.getOrderMethod()!=null&&!"".equals(purchaseOrderBean.getOrderMethod())){
				sql += " and a.orderMethod =:orderMethod ";
				map.put("orderMethod", purchaseOrderBean.getOrderMethod());
			}
			//vendorName
			if(purchaseOrderBean.getVendorName()!=null&&!"".equals(purchaseOrderBean.getVendorName())){
				sql += " and a.vendorName =:vendorName ";
				map.put("vendorName", purchaseOrderBean.getVendorName());
			}
			//purchaserName
			if(purchaseOrderBean.getPurchaserName()!=null&&!"".equals(purchaseOrderBean.getPurchaserName())){
				sql += " and a.purchaserName =:purchaserName ";
				map.put("purchaserName", purchaseOrderBean.getPurchaserName());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(purchaseOrderBean.getCdateEnd()!=null&&!"".equals(purchaseOrderBean.getCdateEnd())){
				sql += " and a.cdate <= to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+" 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(purchaseOrderBean.getCdate()!=null&&!"".equals(purchaseOrderBean.getCdate())){
				sql += " and a.cdate >= to_date('"+sdf.format(purchaseOrderBean.getCdate())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql += "order by a.poid";
			
			List<Map<String,Object>> list = purchaseOrderDao.queryObjectsBySqlListMap2(sql, map);
			
			return list;
		}
		
		@Override
		public boolean findOrderID(String orderID){
			String hql = "from PurchaseOrderModel where orderid=? ";
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql(hql, new Object[]{orderID});
			if(purchaseOrderModel==null){
				return true;
			}else{
				return false;
			}
		}
		@Override
		public Integer queryPurchaseOrderCancel(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			String sql = "select count(*) from bill_purchasedeliver where deliverorderid='"+purchaseOrderBean.getOrderID()+"' and deliverStatus!=4";
			Integer i = purchaseOrderDao.querysqlCounts2(sql, null);
			return i;
		}
		@Override
		public DataGridBean listCancelPurchaseOrder(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			sql = "select * from bill_purchaseorder where approveStatus != 'D' ";
			sqlCount = "select count(*) from bill_purchaseorder where approveStatus != 'D' ";
			
			if(purchaseOrderBean.getOrderID()!=null&&!"".equals(purchaseOrderBean.getOrderID())){
				sql += " and orderID = :orderID ";
				sqlCount += " and orderID = :orderID ";
				map.put("orderID", purchaseOrderBean.getOrderID());
			}
			//1-厂商单 2-代理单
			if(purchaseOrderBean.getOrderMethod()!=null&&!"".equals(purchaseOrderBean.getOrderMethod())){
				sql += " and orderMethod = :orderMethod ";
				sqlCount += " and orderMethod = :orderMethod ";
				map.put("orderMethod", purchaseOrderBean.getOrderMethod());
			}
			if(purchaseOrderBean.getUnno()!=null&&!"".equals(purchaseOrderBean.getUnno())){
				sql += " and unno = :unno ";
				sqlCount += " and unno = :unno ";
				map.put("unno", purchaseOrderBean.getUnno());
			}
			sql += " order by poid desc ";
			List<PurchaseOrderModel> list = purchaseOrderDao.queryObjectsBySqlLists(sql, map, purchaseOrderBean.getPage(), purchaseOrderBean.getRows(),PurchaseOrderModel.class);
			Integer counts = purchaseOrderDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}
		/**
		 * 手刷POS应收账款统计
		 */
		@Override
		public DataGridBean queryPOSAccounts(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//查询机构号，机构名称，期初欠款，销售数量，销售金额
			sql = "select unno,purchasername,orderamt,sum(macnum) nums,sum(macnum*machineprice) amt "
					+ "from (select case when x.unno is null then z.unno else x.unno end unno,nvl(z.orderamt,0) orderamt,case when x.purchasername is null then z.purchasername else x.purchasername end purchasername,nvl(x.machineprice,0) machineprice,nvl(x.macnum,0) macnum "
					+ "from (select a.unno,a.purchasername,b.machineprice,count(d.btid) macnum from "
					+ " bill_purchaseorder a, bill_purchasedetail b, bill_terminalinfo d "
					+ "where a.poid = b.poid and d.rebatetype = b.rebatetype and d.terorderid = a.orderid and  a.ordermethod =2 "
					+ "and a.approvestatus !='D' and b.detailapprovestatus!='D' and d.machinemodel = b.machinemodel "
					+ "and b.ordertype in (1,5) and d.outDate between to_date('"+sdf.format(purchaseOrderBean.getCdate())+"000000','yyyymmddhh24miss') "
					+ "and to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+"235959','yyyymmddhh24miss') "
					+ "group by a.unno,a.purchasername,b.machineprice) x full outer join "
					+ "(select unno,purchasername,orderamt from  bill_purchaseorder "
					+ "where ordermethod = 2 and ordernum = 1) z on x.unno = z.unno)"
					+ "group by unno,orderamt,purchasername";
			
			sqlCount = "select count(1) from ("+sql+")";
			List<Map<String,Object>> list = purchaseOrderDao.queryObjectsBySqlList2(sql, map, purchaseOrderBean.getPage(), purchaseOrderBean.getRows());
			for (Map<String, Object> map2 : list) {
				String unno = (String)map2.get("UNNO");
				//查询已扣分润
				String sql1 = "select nvl(sum(b.matchamt),0) amt from  bill_purchaserecord a, bill_purchasematch b "
						+ "where a.prid = b.prid and a.arraiveway = '扣除分润' and b.matchapprovestatus='Y' and a.arraivestatus !=5 "
						+ "and unno = '"+unno+"' and a.arraivedate between '"+sdf.format(purchaseOrderBean.getCdate())+"' "
						+ "and '"+sdf.format(purchaseOrderBean.getCdateEnd())+"'";
				BigDecimal amt1 = purchaseOrderDao.querysqlCounts(sql1, null);
				map2.put("SHAREAMT", amt1);
				//查询回款金额
				String sql2 = "SELECT nvl(sum(b.matchamt), 0) amt FROM  bill_purchaserecord a,  bill_purchasematch b,"
						+ " bill_purchaseorder c WHERE a.prid = b.prid AND b.poid = c.poid AND a.arraiveway IN ('转账', '网银',"
						+ " '刷卡', '支付宝', '汇款', '现金', '微信', '支票', '退款金额') AND b.matchapprovestatus != 'D' "
						+ "AND a.arraivestatus != 5 AND b.matchapprovestatus = 'Y' and c.unno = '"+unno+"' and a.arraivedate "
						+ "between '"+sdf.format(purchaseOrderBean.getCdate())+"' and '"+sdf.format(purchaseOrderBean.getCdateEnd())+"'";
				BigDecimal amt2 = purchaseOrderDao.querysqlCounts(sql2, null);
				map2.put("REMITAMT", amt2);
				//查询激活奖励
				String sql3 = "select nvl(sum(b.matchamt),0) amt from  bill_purchaserecord a, bill_purchasematch b "
						+ "where a.prid = b.prid and a.arraiveway in('扣除激活奖励') and b.matchapprovestatus!='D' "
						+ "and a.arraivestatus !=5 and b.matchapprovestatus='Y' and unno = '"+unno+"' "
						+ "and a.arraivedate between '"+sdf.format(purchaseOrderBean.getCdate())+"' "
						+ "and '"+sdf.format(purchaseOrderBean.getCdateEnd())+"'";
				BigDecimal amt3 = purchaseOrderDao.querysqlCounts(sql3, null);
				map2.put("ACTIVEAMT", amt3);
				//查询回购款及其他
				String sql4 = "select nvl(sum(b.matchamt),0) amt from  bill_purchaserecord a, bill_purchasematch b "
						+ "where a.prid = b.prid and a.arraiveway in('回购款抵扣','保证金内扣','扣除激活返现','其他') "
						+ "and b.matchapprovestatus!='D' and a.arraivestatus !=5 and unno = '"+unno+"' "
						+ "and a.arraivedate between '"+sdf.format(purchaseOrderBean.getCdate())+"' "
						+ "and '"+sdf.format(purchaseOrderBean.getCdateEnd())+"'";
				BigDecimal amt4 = purchaseOrderDao.querysqlCounts(sql4, null);
				map2.put("BACKAMT", amt4);
				//计算期末应收账款
				BigDecimal orderAmt = (BigDecimal)map2.get("ORDERAMT");//期初应收
				BigDecimal amt = (BigDecimal)map2.get("AMT");//销售金额
				BigDecimal amt5 = orderAmt.add(amt).subtract(amt1).subtract(amt2).subtract(amt3).subtract(amt4);
				map2.put("SHOULDAMT", amt5);
			}
			Integer counts = purchaseOrderDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}
		/**
		 * 导出手刷POS应收账款统计
		 */
		@Override
		public List<Map<String, Object>> exportPOSAccounts(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//查询机构号，机构名称，期初欠款，销售数量，销售金额
			sql = "select unno,purchasername,sum(orderamt) orderamt,sum(macnum) nums,sum(macnum*machineprice) amt "
					+ "from (select case when x.unno is null then z.unno else x.unno end unno,nvl(z.orderamt,0) orderamt,case when x.purchasername is null then z.purchasername else x.purchasername end purchasername,nvl(x.machineprice,0) machineprice,nvl(x.macnum,0) macnum "
					+ "from (select a.unno,a.purchasername,b.machineprice,count(d.btid) macnum from "
					+ " bill_purchaseorder a, bill_purchasedetail b, bill_terminalinfo d "
					+ "where a.poid = b.poid and d.terorderid = a.orderid and  a.ordermethod =2 "
					+ "and a.approvestatus !='D' and b.detailapprovestatus!='D' and d.machinemodel = b.machinemodel and d.rebatetype = b.rebatetype "
					+ "and b.ordertype in (1,5) and d.outDate between to_date('"+sdf.format(purchaseOrderBean.getCdate())+"000000','yyyymmddhh24miss') "
					+ "and to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+"235959','yyyymmddhh24miss') "
					+ "group by a.unno,a.purchasername,b.machineprice) x full outer join "
					+ "(select unno,purchasername,orderamt from  bill_purchaseorder "
					+ "where ordermethod = 2 and ordernum = 1) z on x.unno = z.unno)"
					+ "group by unno,purchasername";
			
			List<Map<String,Object>> list = purchaseOrderDao.queryObjectsBySqlListMap2(sql, map);
			for (Map<String, Object> map2 : list) {
				String unno = (String)map2.get("UNNO");
				//查询已扣分润
				String sql1 = "select nvl(sum(b.matchamt),0) amt from  bill_purchaserecord a, bill_purchasematch b "
						+ "where a.prid = b.prid and a.arraiveway = '扣除分润' and b.matchapprovestatus='Y' and a.arraivestatus !=5 "
						+ "and unno = '"+unno+"' and a.arraivedate between '"+sdf.format(purchaseOrderBean.getCdate())+"' "
						+ "and '"+sdf.format(purchaseOrderBean.getCdateEnd())+"'";
				BigDecimal amt1 = purchaseOrderDao.querysqlCounts(sql1, null);
				map2.put("SHAREAMT", amt1);
				//查询回款金额
				String sql2 = "SELECT nvl(sum(b.matchamt), 0) amt FROM  bill_purchaserecord a,  bill_purchasematch b,"
						+ " bill_purchaseorder c WHERE a.prid = b.prid AND b.poid = c.poid AND a.arraiveway IN ('转账', '网银',"
						+ " '刷卡', '支付宝', '汇款', '现金', '微信', '支票', '退款金额') AND b.matchapprovestatus != 'D' "
						+ "AND a.arraivestatus != 5 AND b.matchapprovestatus = 'Y' and c.unno = '"+unno+"' and a.arraivedate "
						+ "between '"+sdf.format(purchaseOrderBean.getCdate())+"' and '"+sdf.format(purchaseOrderBean.getCdateEnd())+"'";
				BigDecimal amt2 = purchaseOrderDao.querysqlCounts(sql2, null);
				map2.put("REMITAMT", amt2);
				//查询激活奖励
				String sql3 = "select nvl(sum(b.matchamt),0) amt from  bill_purchaserecord a, bill_purchasematch b "
						+ "where a.prid = b.prid and a.arraiveway in('扣除激活奖励') and b.matchapprovestatus!='D' "
						+ "and a.arraivestatus !=5 and b.matchapprovestatus='Y' and unno = '"+unno+"' "
						+ "and a.arraivedate between '"+sdf.format(purchaseOrderBean.getCdate())+"' "
						+ "and '"+sdf.format(purchaseOrderBean.getCdateEnd())+"'";
				BigDecimal amt3 = purchaseOrderDao.querysqlCounts(sql3, null);
				map2.put("ACTIVEAMT", amt3);
				//查询回购款及其他
				String sql4 = "select nvl(sum(b.matchamt),0) amt from  bill_purchaserecord a, bill_purchasematch b "
						+ "where a.prid = b.prid and a.arraiveway in('回购款抵扣','保证金内扣','扣除激活返现','其他') "
						+ "and b.matchapprovestatus!='D' and a.arraivestatus !=5 and unno = '"+unno+"' "
						+ "and a.arraivedate between '"+sdf.format(purchaseOrderBean.getCdate())+"' "
						+ "and '"+sdf.format(purchaseOrderBean.getCdateEnd())+"'";
				BigDecimal amt4 = purchaseOrderDao.querysqlCounts(sql4, null);
				map2.put("BACKAMT", amt4);
				//计算期末应收账款
				BigDecimal orderAmt = (BigDecimal)map2.get("ORDERAMT");//期初应收
				BigDecimal amt = (BigDecimal)map2.get("AMT");//销售金额
				BigDecimal amt5 = orderAmt.add(amt).subtract(amt1).subtract(amt2).subtract(amt3).subtract(amt4);
				map2.put("SHOULDAMT", amt5);
			}
			return list;
		}
		/**
		 * 手刷POS损益情况统计
		 */
		@Override
		public DataGridBean queryPOSProfitAndLoss(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "";
			String sqlCount = "";
			Map<String,Object> map = new HashMap<String,Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//查询机构号，机构名称，期初欠款，销售金额，成本金额，返利金额
			sql = "SELECT t1.unno, (SELECT un_name FROM  sys_unit WHERE unno = t1.unno) purchasername, nvl(t2.outamt,0) outamt, nvl(t2.inamt,0) inamt, nvl(t3.rebateamt,0) rebateamt "
					+ "FROM (SELECT unno FROM (SELECT (SELECT s.unno FROM  sys_unit s WHERE s.upper_unit IN ('110000', '991000', '111000', "
					+ "'341000', '121000') start WITH s.unno = i.unno connect by NOCYCLE s.unno = prior s.upper_unit AND rownum = 1) unno "
					+ "FROM  check_ism35rebate g,  bill_merchantinfo i WHERE g.mid = i.mid AND g.rebatedate BETWEEN '"+sdf.format(purchaseOrderBean.getCdate())+"' "
					+ "AND '"+sdf.format(purchaseOrderBean.getCdateEnd())+"') k GROUP BY unno UNION SELECT a.unno FROM  bill_purchaseorder a,  bill_purchasedetail b, "
					+ " bill_terminalinfo d,  bill_purchaseorder e,  bill_purchasedetail f WHERE a.poid = b.poid "
					+ "AND d.terorderid = a.orderid AND a.ordermethod = 2 AND a.approvestatus != 'D' AND b.detailapprovestatus != 'D' "
					+ "AND d.machinemodel = b.machinemodel AND d.rebatetype = b.rebatetype AND b.ordertype IN (1, 5) AND d.batchid = e.orderid "
					+ "AND e.poid = f.poid AND d.machinemodel = f.machinemodel AND d.outDate BETWEEN to_date('"+sdf.format(purchaseOrderBean.getCdate())+"000000', 'yyyymmddhh24miss') "
					+ "AND to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+"235959', 'yyyymmddhh24miss')) t1 LEFT JOIN (SELECT unno, sum(macnum * price1) outamt, "
					+ "sum(macnum * price2) inamt FROM (SELECT a.unno, a.purchasername, count(d.btid) macnum, b.machineprice price1, "
					+ "f.machineprice price2 FROM  bill_purchaseorder a,  bill_purchasedetail b,  bill_terminalinfo d, "
					+ " bill_purchaseorder e,  bill_purchasedetail f WHERE a.poid = b.poid AND d.terorderid = a.orderid "
					+ "AND a.ordermethod = 2 AND a.approvestatus != 'D' AND b.detailapprovestatus != 'D' AND d.machinemodel = b.machinemodel "
					+ "AND d.rebatetype = b.rebatetype AND b.ordertype IN (1, 5) AND d.batchid = e.orderid AND e.poid = f.poid "
					+ "AND d.machinemodel = f.machinemodel AND d.outDate BETWEEN to_date('"+sdf.format(purchaseOrderBean.getCdate())+"000000', 'yyyymmddhh24miss') "
					+ "AND to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+"235959', 'yyyymmddhh24miss') GROUP BY a.unno, a.purchasername, f.machineprice, b.machineprice) "
					+ "GROUP BY unno) t2 ON t1.unno = t2.unno LEFT JOIN (SELECT unno, sum(rebateamt) rebateamt FROM (SELECT g.rebateamt, "
					+ "(SELECT s.unno FROM  sys_unit s WHERE s.upper_unit IN ('110000', '991000', '111000', '341000', '121000') "
					+ "start WITH s.unno = i.unno connect by NOCYCLE s.unno = prior s.upper_unit AND rownum = 1) unno FROM  check_ism35rebate g, "
					+ " bill_merchantinfo i WHERE g.mid = i.mid AND g.rebatedate BETWEEN '"+sdf.format(purchaseOrderBean.getCdate())+"' AND '"+sdf.format(purchaseOrderBean.getCdateEnd())+"') k GROUP BY unno) t3 "
					+ "ON t1.unno = t3.unno";
			
			sqlCount = "select count(1) from ("+sql+")";
			List<Map<String,Object>> list = purchaseOrderDao.queryObjectsBySqlList2(sql, map, purchaseOrderBean.getPage(), purchaseOrderBean.getRows());
			for (Map<String, Object> map2 : list) {
				String unno = (String)map2.get("UNNO");
				BigDecimal amt1 = (BigDecimal) map2.get("REBATEAMT");
				//查询赠品数量，赠品金额
				String sql2 = "select nvl(sum(macnum),0) macnum,nvl(sum(machineprice*macnum),0) giveamt "
						+ "from (select count(d.btid) macnum,f.machineprice from  bill_purchaseorder a, bill_purchasedetail b,"
						+ " bill_terminalinfo d, bill_purchaseorder e, bill_purchasedetail f "
						+ "where a.poid = b.poid and d.terorderid = a.orderid and  a.ordermethod =2 and a.approvestatus !='D' "
						+ "and b.detailapprovestatus!='D' and d.machinemodel = b.machinemodel and d.rebatetype = b.rebatetype and b.ordertype=2 "
						+ "and d.batchid = e.orderid and e.poid = f.poid and d.machinemodel = f.machinemodel "
						+ "and a.unno = '"+unno+"' and d.outDate between to_date('"+sdf.format(purchaseOrderBean.getCdate())+"000000','yyyymmddhh24miss') "
						+ "and to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+"235959','yyyymmddhh24miss') group by f.machineprice)";
				List<Map<String,Object>> list2 = purchaseOrderDao.queryObjectsBySqlListMap2(sql2, null);
				Map<String, Object> map3 = list2.get(0);
				map2.put("MACNUM", map3.get("MACNUM"));
				BigDecimal amt2 = (BigDecimal) map3.get("GIVEAMT");
				map2.put("GIVEAMT", amt2);
				//计算亏损
				BigDecimal outAmt = (BigDecimal)map2.get("OUTAMT");//销售金额
				BigDecimal inAmt = (BigDecimal)map2.get("INAMT");//成本金额
				BigDecimal amt3 = outAmt.subtract(inAmt).subtract(amt1).subtract(amt2);
				map2.put("LOSSAMT", amt3);
			}
			Integer counts = purchaseOrderDao.querysqlCounts2(sqlCount, map);
			dgb.setRows(list);
			dgb.setTotal(counts);
			return dgb;
		}
		/**
		 * 导出手刷POS损益情况统计
		 */
		@Override
		public List<Map<String, Object>> exportPOSProfitAndLoss(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			String sql = "";
			Map<String,Object> map = new HashMap<String,Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//查询机构号，机构名称，期初欠款，销售金额，成本金额，返利金额
			sql = "SELECT t1.unno, (SELECT un_name FROM  sys_unit WHERE unno = t1.unno) purchasername, nvl(t2.outamt,0) outamt, nvl(t2.inamt,0) inamt, nvl(t3.rebateamt,0) rebateamt "
					+ "FROM (SELECT unno FROM (SELECT (SELECT s.unno FROM  sys_unit s WHERE s.upper_unit IN ('110000', '991000', '111000', "
					+ "'341000', '121000') start WITH s.unno = i.unno connect by NOCYCLE s.unno = prior s.upper_unit AND rownum = 1) unno "
					+ "FROM  check_ism35rebate g,  bill_merchantinfo i WHERE g.mid = i.mid AND g.rebatedate BETWEEN '"+sdf.format(purchaseOrderBean.getCdate())+"' "
					+ "AND '"+sdf.format(purchaseOrderBean.getCdateEnd())+"') k GROUP BY unno UNION SELECT a.unno FROM  bill_purchaseorder a,  bill_purchasedetail b, "
					+ " bill_terminalinfo d,  bill_purchaseorder e,  bill_purchasedetail f WHERE a.poid = b.poid "
					+ "AND d.terorderid = a.orderid AND a.ordermethod = 2 AND a.approvestatus != 'D' AND b.detailapprovestatus != 'D' "
					+ "AND d.machinemodel = b.machinemodel AND d.rebatetype = b.rebatetype AND b.ordertype IN (1, 5) AND d.batchid = e.orderid "
					+ "AND e.poid = f.poid AND d.machinemodel = f.machinemodel AND d.outDate BETWEEN to_date('"+sdf.format(purchaseOrderBean.getCdate())+"000000', 'yyyymmddhh24miss') "
					+ "AND to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+"235959', 'yyyymmddhh24miss')) t1 LEFT JOIN (SELECT unno, sum(macnum * price1) outamt, "
					+ "sum(macnum * price2) inamt FROM (SELECT a.unno, a.purchasername, count(d.btid) macnum, b.machineprice price1, "
					+ "f.machineprice price2 FROM  bill_purchaseorder a,  bill_purchasedetail b,  bill_terminalinfo d, "
					+ " bill_purchaseorder e,  bill_purchasedetail f WHERE a.poid = b.poid AND d.terorderid = a.orderid "
					+ "AND a.ordermethod = 2 AND a.approvestatus != 'D' AND b.detailapprovestatus != 'D' AND d.machinemodel = b.machinemodel "
					+ "AND d.rebatetype = b.rebatetype AND b.ordertype IN (1, 5) AND d.batchid = e.orderid AND e.poid = f.poid "
					+ "AND d.machinemodel = f.machinemodel AND d.outDate BETWEEN to_date('"+sdf.format(purchaseOrderBean.getCdate())+"000000', 'yyyymmddhh24miss') "
					+ "AND to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+"235959', 'yyyymmddhh24miss') GROUP BY a.unno, a.purchasername, f.machineprice, b.machineprice) "
					+ "GROUP BY unno) t2 ON t1.unno = t2.unno LEFT JOIN (SELECT unno, sum(rebateamt) rebateamt FROM (SELECT g.rebateamt, "
					+ "(SELECT s.unno FROM  sys_unit s WHERE s.upper_unit IN ('110000', '991000', '111000', '341000', '121000') "
					+ "start WITH s.unno = i.unno connect by NOCYCLE s.unno = prior s.upper_unit AND rownum = 1) unno FROM  check_ism35rebate g, "
					+ " bill_merchantinfo i WHERE g.mid = i.mid AND g.rebatedate BETWEEN '"+sdf.format(purchaseOrderBean.getCdate())+"' AND '"+sdf.format(purchaseOrderBean.getCdateEnd())+"') k GROUP BY unno) t3 "
					+ "ON t1.unno = t3.unno";
			
			List<Map<String,Object>> list = purchaseOrderDao.queryObjectsBySqlListMap2(sql, map);
			for (Map<String, Object> map2 : list) {
				String unno = (String)map2.get("UNNO");
				BigDecimal amt1 = (BigDecimal) map2.get("REBATEAMT");
				//查询赠品数量，赠品金额
				String sql2 = "select nvl(sum(macnum),0) macnum,nvl(sum(machineprice*macnum),0) giveamt "
						+ "from (select count(d.btid) macnum,f.machineprice from  bill_purchaseorder a, bill_purchasedetail b,"
						+ " bill_terminalinfo d, bill_purchaseorder e, bill_purchasedetail f "
						+ "where a.poid = b.poid and d.terorderid = a.orderid and  a.ordermethod =2 and a.approvestatus !='D' "
						+ "and b.detailapprovestatus!='D' and d.machinemodel = b.machinemodel and d.rebatetype = b.rebatetype and b.ordertype=2 "
						+ "and d.batchid = e.orderid and e.poid = f.poid and d.machinemodel = f.machinemodel "
						+ "and a.unno = '"+unno+"' and d.outDate between to_date('"+sdf.format(purchaseOrderBean.getCdate())+"000000','yyyymmddhh24miss') "
						+ "and to_date('"+sdf.format(purchaseOrderBean.getCdateEnd())+"235959','yyyymmddhh24miss') group by f.machineprice)";
				List<Map<String,Object>> list2 = purchaseOrderDao.queryObjectsBySqlListMap2(sql2, null);
				Map<String, Object> map3 = list2.get(0);
				map2.put("MACNUM", map3.get("MACNUM"));
				BigDecimal amt2 = (BigDecimal) map3.get("GIVEAMT");
				map2.put("GIVEAMT", amt2);
				//计算亏损
				BigDecimal outAmt = (BigDecimal)map2.get("OUTAMT");//销售金额
				BigDecimal inAmt = (BigDecimal)map2.get("INAMT");//成本金额
				BigDecimal amt3 = outAmt.subtract(inAmt).subtract(amt1).subtract(amt2);
				map2.put("LOSSAMT", amt3);
			}
			return list;
		}
		/**
		 * 手刷POS综合收益
		 */
		@Override
		public DataGridBean queryPOSIncome(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String,Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//查询期初欠款
			String sql = "select sum(orderamt) amt from  bill_purchaseorder where ordermethod = 2 and ordernum=1";
			BigDecimal amt = purchaseOrderDao.querysqlCounts(sql, null);
			map.put("TOTALAMT", amt);
			list.add(map);
			for(int i = 1;i<13;i++){
				Map<String,Object> mapTemp = new HashMap<String, Object>();
				mapTemp.put("MONTH", i+"月");
				//日期初始化
				String date = sdf.format(getFirstDayOfMonth(purchaseOrderBean.getYear(), i-1));
				String dateEnd = sdf.format(getLastDayOfMonth(purchaseOrderBean.getYear(), i-1));
				//查询销售台数、销售金额、成本金额
				String sql1 = "select nvl(sum(macnum),0) macnum,nvl(sum(macnum*price1),0) outamt,nvl(sum(macnum*price2),0) inamt "
						+ "from(select count(d.btid) macnum,b.machineprice price1,f.machineprice price2 "
						+ "from  bill_purchaseorder a, bill_purchasedetail b, bill_terminalinfo d,"
						+ " bill_purchaseorder e, bill_purchasedetail f where a.poid = b.poid "
						+ "and d.terorderid = a.orderid and  a.ordermethod =2 and a.approvestatus !='D' "
						+ "and b.detailapprovestatus!='D' and d.machinemodel = b.machinemodel and d.rebatetype = b.rebatetype and b.ordertype in (1,5) "
						+ "and d.batchid = e.orderid and e.poid = f.poid and d.machinemodel = f.machinemodel "
						+ "and d.outDate between to_date('"+date+"000000','yyyymmddhh24miss') "
						+ "and to_date('"+dateEnd+"235959','yyyymmddhh24miss') group by f.machineprice,b.machineprice)";
				List<Map<String,Object>> list1 = purchaseOrderDao.queryObjectsBySqlListMap2(sql1, null);
				mapTemp.put("MACNUM",list1.get(0).get("MACNUM"));//销售台数
				BigDecimal outAmt = (BigDecimal)list1.get(0).get("OUTAMT");//销售金额
				mapTemp.put("OUTAMT",outAmt);
				BigDecimal inAmt = (BigDecimal)list1.get(0).get("INAMT");//成本金额
				mapTemp.put("INAMT",inAmt);
				mapTemp.put("LOSSAMT", outAmt.subtract(inAmt));//亏损金额
				//赠品数量，赠品金额
				String sql2 = "select nvl(sum(macnum),0) givenum,nvl(sum(amt),0) giveamt from ("
						+ "select count(d.btid) macnum,count(d.btid)*f.machineprice amt "
						+ "from  bill_purchaseorder a, bill_purchasedetail b,"
						+ " bill_terminalinfo d, bill_purchaseorder e, bill_purchasedetail f "
						+ "where a.poid = b.poid and d.terorderid = a.orderid and a.ordermethod =2 "
						+ "and a.approvestatus !='D' and b.detailapprovestatus!='D' and d.machinemodel = b.machinemodel and d.rebatetype = b.rebatetype "
						+ "and b.ordertype=2 and d.batchid = e.orderid and e.poid = f.poid "
						+ "and d.machinemodel = f.machinemodel and d.outDate between to_date('"+date+"000000','yyyymmddhh24miss') "
						+ "and to_date('"+dateEnd+"235959','yyyymmddhh24miss') group by f.machineprice)";
				List<Map<String,Object>> list2 = purchaseOrderDao.queryObjectsBySqlListMap2(sql2, null);
				mapTemp.put("GIVENUM", list2.get(0).get("GIVENUM"));//赠品数量
				mapTemp.put("GIVEAMT", list2.get(0).get("GIVEAMT"));//赠品金额
				//查询返利金额
				String sql3 = "select nvl(sum(rebateamt),0) rebateamt from  check_isM35Rebate a where rebatedate>='"+date+"' and rebatedate<='"+dateEnd+"'";
				BigDecimal rebateamt = purchaseOrderDao.querysqlCounts(sql3, null);
				mapTemp.put("REBATEAMT",rebateamt);//返利金额
				mapTemp.put("ALLLOSSAMT",outAmt.subtract(inAmt).subtract((BigDecimal)list2.get(0).get("GIVEAMT")).subtract(rebateamt));//亏损合计
				//查询回款金额
				String sql4 = "select nvl(sum(arraiveamt),0) income from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway in('转账','网银','刷卡','支付宝','汇款','现金','微信','支票','退款金额')";
				BigDecimal income = purchaseOrderDao.querysqlCounts(sql4, null);
				mapTemp.put("INCOME",income);//回款金额
				//查询回购款抵扣
				String sql5 = "select nvl(sum(arraiveamt),0) backAmt from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway ='回购款抵扣'";
				BigDecimal backAmt = purchaseOrderDao.querysqlCounts(sql5, null);
				mapTemp.put("BACKAMT",backAmt);//回购款抵扣
				//查询扣除分润
				String sql6 = "select nvl(sum(arraiveamt),0) profit from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway ='扣除分润'";
				BigDecimal profit = purchaseOrderDao.querysqlCounts(sql6, null);
				mapTemp.put("PROFIT",profit);//扣除分润
				//查询其他扣款
				String sql7 = "select nvl(sum(arraiveamt),0) otheramt from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway in('保证金内扣','扣除激活返现','其他')";
				BigDecimal otheramt = purchaseOrderDao.querysqlCounts(sql7, null);
				mapTemp.put("OTHERAMT",otheramt);//其他扣款
				//查询激活奖励抵欠款
				String sql8 = "select nvl(sum(arraiveamt),0) achamt from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway = '扣除激活奖励'";
				BigDecimal achamt = purchaseOrderDao.querysqlCounts(sql8, null);
				mapTemp.put("ACHAMT",achamt);//激活奖励抵欠款
				mapTemp.put("TOTALAMT", ((BigDecimal)list.get(i-1).get("TOTALAMT")).add(outAmt).subtract(income).subtract(backAmt).subtract(profit).subtract(otheramt).subtract(achamt));
				list.add(mapTemp);
			}
			dgb.setRows(list);
			dgb.setTotal(12);
			return dgb;
		}
		/**
	     * 返回指定年月的月的第一天
	     *
	     * @param year
	     * @param month
	     * @return
	     */
	    public Date getFirstDayOfMonth(Integer year, Integer month) {
	        Calendar calendar = Calendar.getInstance();
	        if (year == null) {
	            year = calendar.get(Calendar.YEAR);
	        }
	        if (month == null) {
	            month = calendar.get(Calendar.MONTH);
	        }
	        calendar.set(year, month, 1);
	        return calendar.getTime();
	    }
	    /**
	     * 返回指定年月的月的最后一天
	     *
	     * @param year
	     * @param month
	     * @return
	     */
	    public Date getLastDayOfMonth(Integer year, Integer month) {
	        Calendar calendar = Calendar.getInstance();
	        if (year == null) {
	            year = calendar.get(Calendar.YEAR);
	        }
	        if (month == null) {
	            month = calendar.get(Calendar.MONTH);
	        }
	        calendar.set(year, month, 1);
	        calendar.roll(Calendar.DATE, -1);
	        return calendar.getTime();
	    }
		@Override
		public List<Map<String, Object>> exportPOSIncome(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String,Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//查询期初欠款
			String sql = "select sum(orderamt) amt from  bill_purchaseorder where ordermethod = 2 and ordernum=1";
			BigDecimal amt = purchaseOrderDao.querysqlCounts(sql, null);
			map.put("TOTALAMT", amt);
			list.add(map);
			for(int i = 1;i<13;i++){
				Map<String,Object> mapTemp = new HashMap<String, Object>();
				mapTemp.put("MONTH", i+"月");
				//日期初始化
				String date = sdf.format(getFirstDayOfMonth(purchaseOrderBean.getYear(), i-1));
				String dateEnd = sdf.format(getLastDayOfMonth(purchaseOrderBean.getYear(), i-1));
				//查询销售台数、销售金额、成本金额
				String sql1 = "select nvl(sum(macnum),0) macnum,nvl(sum(macnum*price1),0) outamt,nvl(sum(macnum*price2),0) inamt "
						+ "from(select count(d.btid) macnum,b.machineprice price1,f.machineprice price2 "
						+ "from  bill_purchaseorder a, bill_purchasedetail b, bill_terminalinfo d,"
						+ " bill_purchaseorder e, bill_purchasedetail f where a.poid = b.poid "
						+ "and d.terorderid = a.orderid and  a.ordermethod =2 and a.approvestatus !='D' "
						+ "and b.detailapprovestatus!='D' and d.machinemodel = b.machinemodel and d.rebatetype = b.rebatetype and b.ordertype in (1,5) "
						+ "and d.batchid = e.orderid and e.poid = f.poid and d.machinemodel = f.machinemodel "
						+ "and d.outDate between to_date('"+date+"000000','yyyymmddhh24miss') "
						+ "and to_date('"+dateEnd+"235959','yyyymmddhh24miss') group by f.machineprice,b.machineprice)";
				List<Map<String,Object>> list1 = purchaseOrderDao.queryObjectsBySqlListMap2(sql1, null);
				mapTemp.put("MACNUM",list1.get(0).get("MACNUM"));//销售台数
				BigDecimal outAmt = (BigDecimal)list1.get(0).get("OUTAMT");//销售金额
				mapTemp.put("OUTAMT",outAmt);
				BigDecimal inAmt = (BigDecimal)list1.get(0).get("INAMT");//成本金额
				mapTemp.put("INAMT",inAmt);
				mapTemp.put("LOSSAMT", outAmt.subtract(inAmt));//亏损金额
				//赠品数量，赠品金额
				String sql2 = "select nvl(sum(macnum),0) givenum,nvl(sum(amt),0) giveamt from ("
						+ "select count(d.btid) macnum,count(d.btid)*f.machineprice amt "
						+ "from  bill_purchaseorder a, bill_purchasedetail b,"
						+ " bill_terminalinfo d, bill_purchaseorder e, bill_purchasedetail f "
						+ "where a.poid = b.poid and d.terorderid = a.orderid and a.ordermethod =2 "
						+ "and a.approvestatus !='D' and b.detailapprovestatus!='D' and d.machinemodel = b.machinemodel and d.rebatetype = b.rebatetype "
						+ "and b.ordertype=2 and d.batchid = e.orderid and e.poid = f.poid "
						+ "and d.machinemodel = f.machinemodel and d.outDate between to_date('"+date+"000000','yyyymmddhh24miss') "
						+ "and to_date('"+dateEnd+"235959','yyyymmddhh24miss') group by f.machineprice)";
				List<Map<String,Object>> list2 = purchaseOrderDao.queryObjectsBySqlListMap2(sql2, null);
				mapTemp.put("GIVENUM", list2.get(0).get("GIVENUM"));//赠品数量
				mapTemp.put("GIVEAMT", list2.get(0).get("GIVEAMT"));//赠品金额
				//查询返利金额
				String sql3 = "select nvl(sum(rebateamt),0) rebateamt from  check_isM35Rebate a where rebatedate>='"+date+"' and rebatedate<='"+dateEnd+"'";
				BigDecimal rebateamt = purchaseOrderDao.querysqlCounts(sql3, null);
				mapTemp.put("REBATEAMT",rebateamt);//返利金额
				mapTemp.put("ALLLOSSAMT",outAmt.subtract(inAmt).subtract((BigDecimal)list2.get(0).get("GIVEAMT")).subtract(rebateamt));//亏损合计
				//查询回款金额
				String sql4 = "select nvl(sum(arraiveamt),0) income from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway in('转账','网银','刷卡','支付宝','汇款','现金','微信','支票','退款金额')";
				BigDecimal income = purchaseOrderDao.querysqlCounts(sql4, null);
				mapTemp.put("INCOME",income);//回款金额
				//查询回购款抵扣
				String sql5 = "select nvl(sum(arraiveamt),0) backAmt from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway ='回购款抵扣'";
				BigDecimal backAmt = purchaseOrderDao.querysqlCounts(sql5, null);
				mapTemp.put("BACKAMT",backAmt);//回购款抵扣
				//查询扣除分润
				String sql6 = "select nvl(sum(arraiveamt),0) profit from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway ='扣除分润'";
				BigDecimal profit = purchaseOrderDao.querysqlCounts(sql6, null);
				mapTemp.put("PROFIT",profit);//扣除分润
				//查询其他扣款
				String sql7 = "select nvl(sum(arraiveamt),0) otheramt from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway in('保证金内扣','扣除激活返现','其他')";
				BigDecimal otheramt = purchaseOrderDao.querysqlCounts(sql7, null);
				mapTemp.put("OTHERAMT",otheramt);//其他扣款
				//查询激活奖励抵欠款
				String sql8 = "select nvl(sum(arraiveamt),0) achamt from bill_purchaserecord "
						+ "where  arraivedate >='"+date+"' and arraivedate <='"+dateEnd+"' "
						+ "and arraivestatus !=5 and arraiveway = '扣除激活奖励'";
				BigDecimal achamt = purchaseOrderDao.querysqlCounts(sql8, null);
				mapTemp.put("ACHAMT",achamt);//激活奖励抵欠款
				mapTemp.put("TOTALAMT", ((BigDecimal)list.get(i-1).get("TOTALAMT")).add(outAmt).subtract(income).subtract(backAmt).subtract(profit).subtract(otheramt).subtract(achamt));
				list.add(mapTemp);
			}
			return list;
		}
		@Override
		public Integer updatePurchaseOrderAgainY(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and editStatus=2 ", new Object[]{purchaseOrderBean.getPoid()});
			if(purchaseOrderModel!=null){
				purchaseOrderModel.setEditStatus(4);
				
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return 1;
			}
			return 0;
		}
		@Override
		public Integer updatePurchaseOrderAgainK(PurchaseOrderBean purchaseOrderBean, UserBean user) {
			PurchaseOrderModel purchaseOrderModel = purchaseOrderDao.queryObjectByHql("from PurchaseOrderModel where poid=? and editStatus=2 ", new Object[]{purchaseOrderBean.getPoid()});
			if(purchaseOrderModel!=null){
				purchaseOrderModel.setEditStatus(3);
				
				purchaseOrderDao.updateObject(purchaseOrderModel);
				return 1;
			}
			return 0;
		}


	@Override
	public DataGridBean listDepositAmtByPurConfigure(PurchaseOrderBean purchaseOrderBean,UserBean user){
		DataGridBean dgb = new DataGridBean();
		List<Map<String,String>> result = new ArrayList<Map<String, String>>();
		String confSql="SELECT T.CONFIGINFO FROM BILL_PURCONFIGURE T WHERE T.TYPE=3 AND T.CONFIGINFO IS NOT NULL AND T.VALUEINTEGER=:VALUEINTEGER";
		Map confMap = new HashMap();
		if(StringUtils.isNotEmpty(purchaseOrderBean.getRemarks())) {
			confMap.put("VALUEINTEGER", purchaseOrderBean.getRemarks());
			List<Map<String, String>> confList = purchaseOrderDao.queryObjectsBySqlListMap(confSql, confMap);
			if (confList.size() > 0) {
				for (Map<String, String> stringMap : confList) {
					// {"tradeAmt":{"288":"300.00","328":"350.00","368":"400.00"}}
					String configInfo = stringMap.get("CONFIGINFO");
					JSONObject jsonObject = JSON.parseObject(configInfo);
					if (jsonObject.containsKey("tradeAmt")) {
						JSONObject tradeAmtJson = jsonObject.getJSONObject("tradeAmt");
						Set<String> set = tradeAmtJson.keySet();
						List<String> list = new ArrayList(set);

						for (String s : list) {
							Map<String,String> map = new HashMap<String, String>();
//							map.put("VALUEINTEGER",purchaseOrderBean.getRemarks());
							map.put("VALUEINTEGER",s);
							result.add(map);
						}
					}
				}
			}
		}
		dgb.setRows(result);
		return dgb;
	}
}
