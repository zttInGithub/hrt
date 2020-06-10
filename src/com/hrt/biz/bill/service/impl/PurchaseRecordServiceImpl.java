package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.hrt.biz.bill.action.PurchaseRecordAction;
import com.hrt.biz.bill.dao.IPurchaseRecordDao;
import com.hrt.biz.bill.entity.model.PurchaseOrderModel;
import com.hrt.biz.bill.entity.model.PurchaseRecordModel;
import com.hrt.biz.bill.entity.pagebean.PurchaseRecordBean;
import com.hrt.biz.bill.service.IPurchaseRecordService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class PurchaseRecordServiceImpl implements IPurchaseRecordService {
	
	private IPurchaseRecordDao purchaseRecordDao;
	
	public IPurchaseRecordDao getPurchaseRecordDao() {
		return purchaseRecordDao;
	}
	private static final Log log = LogFactory.getLog(PurchaseRecordServiceImpl.class);
	public void setPurchaseRecordDao(IPurchaseRecordDao purchaseRecordDao) {
		this.purchaseRecordDao = purchaseRecordDao;
	}

	/**
	 *	查询主订单ID（通用，动态）
	 */
	
	public DataGridBean searchPurchaseOrderByOID(String orderID) {
        String sql = "select t.* from BILL_PURCHASEORDER t WHERE  1=1";
        if(orderID !=null&&!orderID.equals("")){
    		sql+=" and t.orderid = '"+orderID+"'";
    	}
		
        List<Map<String,String>> roleList = purchaseRecordDao.executeSql(sql);
		
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < roleList.size(); i++) {
			Map map =roleList.get(i);
			
			list.add(map);
		}
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(roleList.size());
		dgd.setRows(list);
		
		return dgd;
	}
	/**
	 *查询来款记录
	 */
	@Override
	public DataGridBean queryPurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean) {
		DataGridBean dgb = new DataGridBean();
		String sql = "";
		String sqlCount = "";
		Map<String,Object> map = new HashMap<String,Object>();
//		sql = "select t.prid,t.poid,t.orderid,t.arraiveamt,t.arraivecard,t.arraivedate,t.arraivestatus,t.arraiveway,t.recordcdate,t.recordcby,t.recordlmdate,t.recordlmby from BILL_PURCHASERECORD t  WHERE 1=1 AND t.arraivestatus!=5";
		sql = "select  t.PRID, t.POID, t.ORDERID, t.ARRAIVEAMT, t.ARRAIVECARD, t.ARRAIVEDATE, "
			+ "t.RECORDAMT, t.RECORDCDATE, t.RECORDCBY, t.RECORDLMDATE, t.RECORDLMBY, t.ARRAIVESTATUS, "
			+ "t.ARRAIVEWAY, o.PURCHASERNAME, o.UNNO, t.ARRAIVERNAME, t.ARRAIVEREMARKS, t.ARRAIVEMINFO1, "
			+ "t.ARRAIVEMINFO2,m.pmid,m.orderID MatchOrderID,m.matchAmt,m.matchLmby,m.matchDate,"
			+ "m.matchApproveStatus from BILL_PURCHASERECORD t left join bill_purchaseMatch m "
			+ "on t.prid=m.prid LEFT JOIN  bill_purchaseorder o ON m.poid=o.poid WHERE 1=1 "
			+ "AND t.arraivestatus!=5 and (m.matchApproveStatus != 'D' or m.matchApproveStatus is null)";

		sqlCount = "select count(*) from bill_purchaserecord t left join bill_purchaseMatch m on t.prid=m.prid  where 1 = 1  and t.arraivestatus != 5 and (m.matchApproveStatus != 'D' or m.matchApproveStatus is null)";
		
		if(purchaseRecordBean.getOrderID()!=null&&!"".equals(purchaseRecordBean.getOrderID())){
			sql += " and m.orderID = :orderID ";
			sqlCount += " and m.orderID = :orderID ";
			map.put("orderID", purchaseRecordBean.getOrderID());
		}
		if(purchaseRecordBean.getArraiveCard()!=null&&!"".equals(purchaseRecordBean.getArraiveCard())){
			sql += " and t.arraiveCard = :arraiveCard ";
			sqlCount += " and t.arraiveCard = :arraiveCard ";
			map.put("arraiveCard", purchaseRecordBean.getArraiveCard());
		}
		if(purchaseRecordBean.getARRAIVEWAY()!=null&&!"".equals(purchaseRecordBean.getARRAIVEWAY())){
			sql += " and t.arraiveWay = :arraiveWay ";
			sqlCount += " and t.arraiveWay = :arraiveWay ";
			map.put("arraiveWay", purchaseRecordBean.getARRAIVEWAY());
		}
		if(purchaseRecordBean.getArraiverName()!=null&&!"".equals(purchaseRecordBean.getArraiverName())){
			sql += " and t.arraiverName like '%"+purchaseRecordBean.getArraiverName().trim()+"%' ";
			sqlCount += " and t.arraiverName like '%"+purchaseRecordBean.getArraiverName().trim()+"%' ";
		}
		if(purchaseRecordBean.getArraiveStatus()!=null&&!"".equals(purchaseRecordBean.getArraiveStatus())){
			sql += " and t.arraiveStatus = :arraiveStatus ";
			sqlCount += " and t.arraiveStatus = :arraiveStatus ";
			map.put("arraiveStatus", purchaseRecordBean.getArraiveStatus());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseRecordBean.getArraiveDateEnd()!=null&&!"".equals(purchaseRecordBean.getArraiveDateEnd())){
			sql += " and t.arraiveDate <= '"+purchaseRecordBean.getArraiveDateEnd().replace("-", "")+"' ";
			sqlCount += " and t.arraiveDate <= '"+purchaseRecordBean.getArraiveDateEnd().replace("-", "")+"' ";
		}
		if(purchaseRecordBean.getArraiveDate()!=null&&!"".equals(purchaseRecordBean.getArraiveDate())){
			sql += " and t.arraiveDate >= '"+purchaseRecordBean.getArraiveDate().replace("-", "")+"' ";
			sqlCount += " and t.arraiveDate >= '"+purchaseRecordBean.getArraiveDate().replace("-", "")+"' ";
		}
		if(purchaseRecordBean.getArraiveRemarks()!=null&&!"".equals(purchaseRecordBean.getArraiveRemarks())){
			sql += " and t.arraiveRemarks like '%"+purchaseRecordBean.getArraiveRemarks()+"%' ";
			sqlCount += " and t.arraiveRemarks like '%"+purchaseRecordBean.getArraiveRemarks()+"%' ";
		}
		
		sql += " order by t.prid desc ";
		List<Map<String,String>> list = purchaseRecordDao.queryObjectsBySqlList(sql, map, purchaseRecordBean.getPage(), purchaseRecordBean.getRows());
		Integer counts = purchaseRecordDao.querysqlCounts2(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts);
		return dgb;
	}
	
	/**
	 * 新增来款
	 */
	@Override
	public void savePurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean) {
		PurchaseRecordModel purchaseRecordModel=new PurchaseRecordModel();
		purchaseRecordModel.setArraiveAmt(purchaseRecordBean.getArraiveAmt());
		purchaseRecordModel.setArraiveRemarks(purchaseRecordBean.getArraiveRemarks());
		purchaseRecordModel.setRecordAmt(0.0);//已匹配金额
		purchaseRecordModel.setArraiveCard(purchaseRecordBean.getArraiveCard());
		purchaseRecordModel.setArraiveDate(purchaseRecordBean.getArraiveDate().trim().replaceAll("-", ""));
		purchaseRecordModel.setArraiveWay(purchaseRecordBean.getARRAIVEWAY());
		purchaseRecordModel.setArraiverName(purchaseRecordBean.getArraiverName());
		purchaseRecordModel.setArraiveStatus(1);
		purchaseRecordModel.setRecordCdate(new Date()); 
		purchaseRecordModel.setRecordCby(userBean.getLoginName());
		
		purchaseRecordDao.saveObject(purchaseRecordModel);		
	}
	
	/**
	 * 查询订单ID
	 */
	@Override
	public List<Map<String, Object>> queryPurchaseOrder(PurchaseRecordBean purchaseRecordBean) {
		String sql="select t.poid,t.orderid,t.unno,t.purchaserName,t.orderamt,t.orderpayamt from BILL_PURCHASEORDER t WHERE t.poid ="+purchaseRecordBean.getPoid();
		
		List<Map<String,Object>> list = purchaseRecordDao.executeSql2(sql, null);
		if(list.size()>0){
			Double orderAmt = ((BigDecimal)list.get(0).get("ORDERAMT")).doubleValue();
			Double orderPayAmt = ((BigDecimal)list.get(0).get("ORDERPAYAMT")).doubleValue();
			//退款
			if(purchaseRecordBean.getMatchAmt()<0){
				//不能使金额低于0
				if(0>orderPayAmt+purchaseRecordBean.getMatchAmt()){
					list.remove(0);
					return list;
				}
			}else{
				//判断当前订单金额>已付款金额+匹配金额
				if(orderAmt<orderPayAmt+purchaseRecordBean.getMatchAmt()){
					list.remove(0);
					return list;
				}
			}
		}
		return list;
	}

	/**
	 * 匹配来款
	 */
	@Override
	public void updatePurchaseRecord(String poid, String orderID,String unno, String purchaserName,PurchaseRecordBean purchaseRecordBean, UserBean userBean) {
		String sql ="update BILL_PURCHASERECORD set orderid='"+orderID+"', poid="+poid+",unno='"+unno+"',purchaserName='"+purchaserName+"',recordLmdate=sysdate,recordLmby='"+userBean.getLoginName()+"',arraiveStatus=2,recordAmt=recordAmt+"+purchaseRecordBean.getMatchAmt()+" where PRID="+purchaseRecordBean.getPrid()+" and arraiveAmt>=recordAmt+"+purchaseRecordBean.getMatchAmt()+"";
		purchaseRecordDao.executeUpdate(sql);
		//订单表和来款表关联的中间表
		String sql2="insert into BILL_PURCHASEMATCH (pmid,prid,poid,orderId,matchAmt,matchDate,matchLmby,matchApproveStatus) values (s_Bill_Purchasematch.Nextval,"+purchaseRecordBean.getPrid()+","+poid+",'"+orderID+"',"+purchaseRecordBean.getMatchAmt()+",sysdate,'"+userBean.getLoginName()+"','A')";
		purchaseRecordDao.executeUpdate(sql2);
	}

	/**
	 * 退回来款
	 */
	@Override
	public void returnPurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean) {
//		String sql ="update BILL_PURCHASERECORD set orderid='', poid=null,unno='',PURCHASERNAME='',recordLmdate=sysdate,recordLmby='"+userBean.getLoginName()+"',arraiveStatus=4,recordAmt=recordAmt-"+purchaseRecordBean.getPrid()+" where PRID="+purchaseRecordBean.getPrid()+" and 0<=recordAmt-"+purchaseRecordBean.getPrid();		
//		purchaseRecordDao.executeUpdate(sql);
//		String sql1="update bill_purchaseMatch set matchApproveStatus='K' where pmid="+purchaseRecordBean.getPmid();
//		purchaseRecordDao.executeUpdate(sql1);
		//财务退回先查中间表
		Map<String,Object> map= new HashMap<String, Object>();
		String sql="select * from BILL_PURCHASEMATCH where pmid=:pmid and matchApproveStatus!='D'";
		map.put("pmid", purchaseRecordBean.getPmid());
		List<Map<String,Object>> list = purchaseRecordDao.queryObjectsBySqlListMap2(sql, map);
		//根据中间表查来款表
		if(list.size()>0){
			Map<String, Object> map2 = list.get(0);
			String prid = ((BigDecimal) map2.get("PRID")).toString();//中间表对应的订单总表
			double matchAmt = ((BigDecimal)map2.get("MATCHAMT")).doubleValue();
			String sql1 ="select * from BILL_PURCHASERECORD where prid="+prid;
			List<Map<String,Object>> list2 = purchaseRecordDao.queryObjectsBySqlListMap2(sql1, null);
			if(list2.size()>0){
				//判断退回金额<=已匹配金额
				Map<String, Object> map3 = list2.get(0);
				double orderpayAmt = ((BigDecimal)map3.get("RECORDAMT")).doubleValue();
				if(orderpayAmt>=matchAmt){
					//可以通过,状态为4结款中
					purchaseRecordDao.executeSqlUpdate("update BILL_PURCHASERECORD set recordAmt=recordAmt-"+matchAmt+" WHERE prid="+prid,null);
				}else{
					//超过订单金额,失败
					int a = 1/0;
				}
				//修改中间表信息Y通过
				purchaseRecordDao.executeUpdate("update bill_purchaseMatch set matchApproveStatus='K' where pmid="+purchaseRecordBean.getPmid()+"");
			}
		}
	}

	/**
	 * 删除来款
	 */
	@Override
	public void deletePurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean) {
		String sql ="update BILL_PURCHASERECORD set recordLmdate=sysdate,recordLmby='"+userBean.getLoginName()+"',arraiveStatus=5 where PRID="+purchaseRecordBean.getPrid();		
		purchaseRecordDao.executeUpdate(sql);

	}

	/**
	 * 财务确认
	 */
	@Override
	public Integer confirmPurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean) {		
//		Integer j = purchaseRecordDao.executeSqlUpdate("update BILL_PURCHASEORDER set orderpayamt=orderpayamt+"+purchaseRecordBean.getArraiveAmt()+",status=5 WHERE poid="+purchaseRecordBean.getPoid()+" and orderamt=orderpayamt+"+purchaseRecordBean.getArraiveAmt(), null);
//		if (j==0) {
//			j = purchaseRecordDao.executeSqlUpdate("update BILL_PURCHASEORDER set orderpayamt=orderpayamt+"+purchaseRecordBean.getArraiveAmt()+",status=4 WHERE poid="+purchaseRecordBean.getPoid()+" and orderamt>orderpayamt+"+purchaseRecordBean.getArraiveAmt(), null);
//		}
//		if(j>0){
//			String sql ="update BILL_PURCHASERECORD set recordLmdate=sysdate,recordLmby='"+userBean.getLoginName()+"',arraiveStatus=3 where PRID="+purchaseRecordBean.getPrid();	
//			purchaseRecordDao.executeUpdate(sql);
//		}
		//财务确认先查中间表
		Map<String,Object> map= new HashMap<String, Object>();
		String sql="select * from BILL_PURCHASEMATCH where pmid=:pmid and matchApproveStatus!='D'";
		map.put("pmid", purchaseRecordBean.getPmid());
		List<Map<String,Object>> list = purchaseRecordDao.queryObjectsBySqlListMap2(sql, map);
		//根据中间表查订单总表
		if(list.size()>0){
			Map<String, Object> map2 = list.get(0);
			String poid = ((BigDecimal) map2.get("POID")).toString();//中间表对应的订单总表
			double matchAmt = ((BigDecimal)map2.get("MATCHAMT")).doubleValue();
			String sql1 ="select * from BILL_PURCHASEORDER where poid="+poid;
			List<Map<String,Object>> list2 = purchaseRecordDao.queryObjectsBySqlListMap2(sql1, null);
			if(list2.size()>0){
				//判断订单已付款金额+匹配金额<=总金额
				Map<String, Object> map3 = list2.get(0);
				double orderpayAmt = ((BigDecimal)map3.get("ORDERPAYAMT")).doubleValue();
				double orderAmt = ((BigDecimal)map3.get("ORDERAMT")).doubleValue();
				if(orderAmt>orderpayAmt+matchAmt){
					//可以通过,状态为4结款中
					purchaseRecordDao.executeSqlUpdate("update BILL_PURCHASEORDER set orderpayamt=orderpayamt+"+matchAmt+",status=4 WHERE poid="+poid,null);
				}else if(orderAmt-orderpayAmt-matchAmt<0.0000001){
					//状态为5已结款
					purchaseRecordDao.executeSqlUpdate("update BILL_PURCHASEORDER set orderpayamt=orderpayamt+"+matchAmt+",status=5 WHERE poid="+poid,null);
				}else{
					//超过订单金额，返回
					return -1;
				}
				//修改中间表信息Y通过
				purchaseRecordDao.executeUpdate("update bill_purchaseMatch set matchApproveStatus='Y' where pmid="+purchaseRecordBean.getPmid()+"");
			}
		}
		return 1;			
	}

	/**‘
	 * 查询来款方式
	 */
	@Override
	public DataGridBean listArraiveWay() {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select DISTINCT T.NAME AS ArraiveWay from bill_purconfigure T WHERE T.TYPE=4 AND t.status = 1";
		List<Map<String,String>> list = purchaseRecordDao.queryObjectsBySqlListMap(sql, null);
		dgb.setRows(list);
		return dgb;
	}
	
	/**
	 * 来款导入
	 */
	@Override
	public List<Map<String, String>> addPurchaseRecordbyFile(String xlsfile, String fileName, String loginName) {		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 设置日期转化成功标识
		boolean dateflag=true;
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			if (0 == sheet.getLastRowNum()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("arraiveAmt", "");
				map.put("arraiverName", "");
				map.put("arraiveCard", "");
				map.put("arraiveDate", "");
				map.put("arraiveWay", "");
				map.put("msg", "导入文件格式不正确或文件为空");
				list.add(map);
			}
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//来款金额
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					String arraiveAmt = row.getCell((short)0).getStringCellValue().trim();
					//来款人姓名
					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					String arraiverName=row.getCell((short)1).getStringCellValue();
					//来款卡号
					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					String arraiveCard=row.getCell((short)2).getStringCellValue();
					//来款日期
					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					String arraiveDate=row.getCell((short)3).getStringCellValue();
					//来款方式
					row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
					String arraiveWay=row.getCell((short)4).getStringCellValue();
					
					if (arraiveAmt!=null&&!"".equals(arraiveAmt)&&arraiverName!=null&&!"".equals(arraiverName)&&arraiveCard!=null&&!"".equals(arraiveCard)&&arraiveDate!=null&&!"".equals(arraiveDate)&&arraiveWay!=null&&!"".equals(arraiveWay)) {						
						
						try
						{
						Date date = sdf.parse(arraiveDate);
						} catch (ParseException e){
							dateflag=false;
						}finally{
							//成功：true ;失败:false
						log.info("日期是否满足要求"+dateflag);
						}
						if(dateflag){
							PurchaseRecordModel purchaseRecordModel=new PurchaseRecordModel();
							purchaseRecordModel.setArraiveAmt(Double.valueOf(arraiveAmt));
							purchaseRecordModel.setArraiveCard(arraiveCard);
							purchaseRecordModel.setArraiveDate(arraiveDate.trim().replaceAll("-", ""));
							purchaseRecordModel.setArraiveWay(arraiveWay);
							purchaseRecordModel.setArraiverName(arraiverName);
							purchaseRecordModel.setArraiveStatus(1);
							purchaseRecordModel.setRecordCdate(new Date()); 
							purchaseRecordModel.setRecordCby(loginName);
							purchaseRecordModel.setRecordAmt(0.0);//已匹配金额
							
							purchaseRecordDao.saveObject(purchaseRecordModel);
						}else{
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("arraiveAmt", arraiveAmt);
							map.put("arraiverName", arraiveAmt);
							map.put("arraiveCard", arraiveCard);
							map.put("arraiveDate", arraiveDate);
							map.put("arraiveWay", arraiveWay);
							map.put("msg", "日期格式不正确请填写正确格式如：2018-01-01");
							list.add(map);
						}
					}else{
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("arraiveAmt", arraiveAmt);
						map.put("arraiverName", arraiveAmt);
						map.put("arraiveCard", arraiveCard);
						map.put("arraiveDate", arraiveDate);
						map.put("arraiveWay", arraiveWay);
						map.put("msg", "导入文件字段不正确或字段为空");
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
	    	log.info("进销存系统新增来款异常:fileName="+fileName,e);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 来款总金额查询
	 */
	@Override
	public List<Map<String, Object>> queryPurchaseOrderByID(PurchaseRecordBean purchaseRecordBean) {
		String sql="select t.poid,t.orderpayamt,t.status,t.orderamt from BILL_PURCHASEORDER t WHERE 1=1 and t.poid ="+purchaseRecordBean.getPoid();
		return purchaseRecordDao.executeSql2(sql, null);
	}

	/**
	 * 来款导出
	 */
	@Override
	public List<Map<String, Object>> exportPurchaseRecord(PurchaseRecordBean purchaseRecordBean, UserBean userBean) {
		String sql = "";
		Map<String,Object> map = new HashMap<String,Object>();
//		sql = "select t.prid,t.poid,t.orderid,t.arraiveamt,t.arraivecard,t.arraivedate,t.arraivestatus,t.arraiveway,t.recordcdate,t.recordcby,t.recordlmdate,t.recordlmby from BILL_PURCHASERECORD t  WHERE 1=1 AND t.arraivestatus!=5";
		sql = "select  t.PRID, t.POID, t.ORDERID, t.ARRAIVEAMT, t.ARRAIVECARD, t.ARRAIVEDATE, "
				+ "t.RECORDAMT, t.RECORDCDATE, t.RECORDCBY, t.RECORDLMDATE, t.RECORDLMBY, t.ARRAIVESTATUS, "
				+ "t.ARRAIVEWAY, o.PURCHASERNAME, o.UNNO, t.ARRAIVERNAME, t.ARRAIVEREMARKS, t.ARRAIVEMINFO1, "
				+ "t.ARRAIVEMINFO2,m.pmid,m.orderID MatchOrderID,m.matchAmt,m.matchLmby,m.matchDate,"
				+ "m.matchApproveStatus from BILL_PURCHASERECORD t left join bill_purchaseMatch m "
				+ "on t.prid=m.prid LEFT JOIN  bill_purchaseorder o ON m.poid=o.poid WHERE 1=1 "
				+ "AND t.arraivestatus!=5 and (m.matchApproveStatus != 'D' or m.matchApproveStatus is null) ";
		
		if(purchaseRecordBean.getOrderID()!=null&&!"".equals(purchaseRecordBean.getOrderID())){
			sql += " and t.orderID = :orderID ";
			map.put("orderID", purchaseRecordBean.getOrderID());
		}
		if(purchaseRecordBean.getArraiveCard()!=null&&!"".equals(purchaseRecordBean.getArraiveCard())){
			sql += " and t.arraiveCard = :arraiveCard ";
			map.put("arraiveCard", purchaseRecordBean.getArraiveCard());
		}
		if(purchaseRecordBean.getARRAIVEWAY()!=null&&!"".equals(purchaseRecordBean.getARRAIVEWAY())){
			sql += " and t.arraiveWay = :arraiveWay ";
			map.put("arraiveWay", purchaseRecordBean.getARRAIVEWAY());
		}
		if(purchaseRecordBean.getArraiverName()!=null&&!"".equals(purchaseRecordBean.getArraiverName())){
			sql += " and t.arraiverName like '%"+purchaseRecordBean.getArraiverName().trim()+"%' ";
		}
		if(purchaseRecordBean.getArraiveStatus()!=null&&!"".equals(purchaseRecordBean.getArraiveStatus())){
			sql += " and t.arraiveStatus = :arraiveStatus ";
			map.put("arraiveStatus", purchaseRecordBean.getArraiveStatus());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(purchaseRecordBean.getArraiveDateEnd()!=null&&!"".equals(purchaseRecordBean.getArraiveDateEnd())){
			sql += " and t.arraiveDate <= '"+purchaseRecordBean.getArraiveDateEnd().trim()+"' ";
		}
		if(purchaseRecordBean.getArraiveDate()!=null&&!"".equals(purchaseRecordBean.getArraiveDate())){
			sql += " and t.arraiveDate >= '"+purchaseRecordBean.getArraiveDate().trim()+"' ";
		}
		
		sql += " order by t.poid desc ";
		
		List<Map<String,Object>> list = purchaseRecordDao.queryObjectsBySqlListMap2(sql, map);
		return list;		
	}

}
