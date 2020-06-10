package com.hrt.biz.check.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.beans.BeanUtils;
import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckRefundDao;
import com.hrt.biz.check.entity.model.CheckReFundModel;
import com.hrt.biz.check.entity.pagebean.CheckReFundBean;
import com.hrt.biz.check.service.CheckReFundService;
import com.hrt.biz.util.JSONObject;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.phone.dao.GateCarOrderDao;
import com.hrt.phone.entity.model.GateOrderModel;
import com.hrt.util.HttpXmlClient;

public class CheckRefundServiceImpl implements CheckReFundService {

	private CheckRefundDao checkRefundDao;
	private IUnitDao unitDao;
	private IMerchantInfoService merchantInfoService; 
	private IAgentSalesDao agentSalesDao;
	private String admAppIp;
	private String hybReFundUrl;
	private GateCarOrderDao gateCarOrderDao;
	
	public GateCarOrderDao getGateCarOrderDao() {
		return gateCarOrderDao;
	}

	public void setGateCarOrderDao(GateCarOrderDao gateCarOrderDao) {
		this.gateCarOrderDao = gateCarOrderDao;
	}

	private static final Log log = LogFactory.getLog(CheckRefundServiceImpl.class);
	
	public String getHybReFundUrl() {
		return hybReFundUrl;
	}

	public void setHybReFundUrl(String hybReFundUrl) {
		this.hybReFundUrl = hybReFundUrl;
	}

	public String getAdmAppIp() {
		return admAppIp;
	}

	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}

	public static Log getLog() {
		return log;
	}

	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}

	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public CheckRefundDao getCheckRefundDao() {
		return checkRefundDao;
	}

	public void setCheckRefundDao(CheckRefundDao checkRefundDao) {
		this.checkRefundDao = checkRefundDao;
	}

	/**
	 * 退款查询
	 */
	public DataGridBean queryRefundInfo(CheckReFundBean checkReFundBean, UserBean user){
		
		String sql="select w.* from check_refund w  ,bill_merchantinfo m  where w.mid=m.mid ";
		String sqlCount="select count(1) from check_refund w ,bill_merchantinfo m  where w.mid=m.mid ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		//权限
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(user.getUseLvl()==3){
				sql+=" and m.MID in (select MID from  bill_MerchantInfo where  " +
				"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
				sqlCount+=" and m.MID in (select MID from  bill_MerchantInfo where  " +
				"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
		}
		else{
			if("110000".equals(user.getUnNo())){
			}
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
//			}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() ==1 ){
//				sql+=" and m.unno ='"+user.getUnNo()+"' ";
//				sqlCount+=" and m.unno ='"+user.getUnNo()+"' ";
			}else{
				String agHql ="select ag from AgentSalesModel ag where ag.maintainType!='D' and ag.userID="+user.getUserID();
				List<AgentSalesModel> ifAgList = agentSalesDao.queryObjectsByHqlList(agHql);
				if(ifAgList.size()>0){
					//业务员 user.getUserID()
					sql+=" and m.busid="+ifAgList.get(0).getBusid();
					sqlCount+=" and m.busid="+ifAgList.get(0).getBusid();
				}else{
					//代理
					String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
					sql+=" and m.unno IN ("+childUnno+" )";
					sqlCount+=" and m.unno IN ("+childUnno+" )";
				}
			}
		}
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 = 1 ";
//		}
		if("2".equals(checkReFundBean.getIsM35())){
			sql +=" and w.pkid=2 ";
			sqlCount +=" and w.pkid=2 ";
		}else{
			sql +=" and w.pkid=1 ";
			sqlCount +=" and w.pkid=1 ";
		}
		if(checkReFundBean.getIsM35()!=null&&"1".equals(checkReFundBean.getIsM35())){
			sql +=" and m.isM35 !=6 ";
			sqlCount +=" and m.isM35 !=6 ";
		}else if("6".equals(checkReFundBean.getIsM35())){
			sql +=" and m.isM35 =6 ";
			sqlCount +=" and m.isM35 =6 ";
		}
		if (checkReFundBean.getRefId() != null&&!"".equals(checkReFundBean.getRefId())) {
			sql +=" and w.refId =:refId ";
			sqlCount +=" and w.refId =:refId ";
			map.put("refId", checkReFundBean.getRefId());
		}
		if (checkReFundBean.getOriOrderId() != null&&!"".equals(checkReFundBean.getOriOrderId())) {
			sql +=" and w.oriOrderId =:oriOrderId ";
			sqlCount +=" and w.oriOrderId =:oriOrderId ";
			map.put("oriOrderId", checkReFundBean.getOriOrderId());
		}
		if (checkReFundBean.getRrn() != null&&!"".equals(checkReFundBean.getRrn())) {
			sql +=" and w.rrn =:rrn ";
			sqlCount +=" and w.rrn =:rrn ";
			map.put("rrn", checkReFundBean.getRrn());
			date_flag = true ;
		}
		if (checkReFundBean.getMid() != null&&!"".equals(checkReFundBean.getMid())) {
			sql +=" and w.mid like :mid ";
			sqlCount +=" and w.mid like :mid ";
			map.put("mid", '%'+checkReFundBean.getMid()+'%');
			date_flag = true ;
		}
		if (checkReFundBean.getCardPan() != null&&!"".equals(checkReFundBean.getCardPan())) {
			sql +=" and w.cardPan like :cardPan ";
			sqlCount +=" and w.cardPan like :cardPan ";
			map.put("cardPan", '%'+checkReFundBean.getCardPan()+'%');
			date_flag = true ;
		}
		if(checkReFundBean.getStatus() =="F"&&"F".equals(checkReFundBean.getStatus())){
			sql +=" and w.status ='F' or w.status ='K' ";
			sqlCount +=" and w.status ='F' or w.status ='K' ";
		}else if(checkReFundBean.getStatus() != null&&!"".equals(checkReFundBean.getStatus())) {
			sql +=" and w.status =:status ";
			sqlCount +=" and w.status =:status ";
			map.put("status", checkReFundBean.getStatus());
		}
		if (date_flag==false&&(checkReFundBean.getTxnDay() != null&&!"".equals(checkReFundBean.getTxnDay()))) {
			String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(checkReFundBean.getTxnDay());
			checkReFundBean.setTxnDayStr(txnDayStr);
			sql +=" and w.txnDay >=:txnDay ";
			sqlCount +=" and w.txnDay >=:txnDay ";
			map.put("txnDay", checkReFundBean.getTxnDayStr());
		}
		if (date_flag==false&&(checkReFundBean.getTxnDay1() != null&&!"".equals(checkReFundBean.getTxnDay1()))) {
			String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(checkReFundBean.getTxnDay1());
			sql +=" and w.txnDay <=:txnDay1 ";
			sqlCount +=" and w.txnDay <=:txnDay1 ";
			map.put("txnDay1", txnDayStr);
		}
		if (date_flag==false&&(checkReFundBean.getApproveDate() != null&&!"".equals(checkReFundBean.getApproveDate()))) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(checkReFundBean.getApproveDate());
			sql +=" and w.maintainDate >=to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
			sqlCount +=" and w.maintainDate >=to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
			//map.put("getApproveDate", checkReFundBean.getApproveDate());
		}
		if (date_flag==false&&(checkReFundBean.getApproveDate1() != null&&!"".equals(checkReFundBean.getApproveDate1()))) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(checkReFundBean.getApproveDate1());
			sql +=" and w.maintainDate <=to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			sqlCount +=" and w.maintainDate <=to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			//map.put("getApproveDate1", checkReFundBean.getApproveDate1());
		}
		if (checkReFundBean.getSort() != null) {
			sql += " ORDER BY " + checkReFundBean.getSort() + " " + checkReFundBean.getOrder();
		}
		
		List<CheckReFundModel> checkReFundList = checkRefundDao.queryObjectsBySqlLists(sql, map, checkReFundBean.getPage(), checkReFundBean.getRows(), CheckReFundModel.class);
		BigDecimal count = checkRefundDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(checkReFundList, count.intValue());
		return dataGridBean;
	}
	/**
	 * 退款导出
	 */
	public List<Map<String, Object>> exportRefundInfo(CheckReFundBean checkReFundBean, UserBean user){
		
		String sql="select w.*,decode(w.status,'Y','通过','K','退回','F','审核失败','审核中') as status1 from check_refund w  ,bill_merchantinfo m  where w.mid=m.mid ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean date_flag = false ;
		//权限
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(user.getUseLvl()==3){
			sql+=" and m.MID in (select MID from  bill_MerchantInfo where  " +
			"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
		}
		else{
			if("110000".equals(user.getUnNo())){
			}
			else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
				}
//			}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() ==1 ){
//				sql+=" and m.unno ='"+user.getUnNo()+"' ";
			}else{
				String agHql ="select ag from AgentSalesModel ag where ag.maintainType!='D' and ag.userID="+user.getUserID();
				List<AgentSalesModel> ifAgList = agentSalesDao.queryObjectsByHqlList(agHql);
				if(ifAgList.size()>0){
					//业务员 user.getUserID()
					sql+=" and m.busid="+ifAgList.get(0).getBusid();
				}else{
					//代理
					String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
					sql+=" and m.unno IN ("+childUnno+" )";
				}
			}
		}
		if(checkReFundBean.getIsM35()!=null&&"1".equals(checkReFundBean.getIsM35())){
			sql +=" and m.isM35 !=6 ";
		}else{
			sql +=" and m.isM35 =6 ";
		}
		if (checkReFundBean.getRefId() != null&&!"".equals(checkReFundBean.getRefId())) {
			sql +=" and w.refId =:refId ";
			map.put("refId", checkReFundBean.getRefId());
		}
		if (checkReFundBean.getOriOrderId() != null&&!"".equals(checkReFundBean.getOriOrderId())) {
			sql +=" and w.oriOrderId =:oriOrderId ";
			map.put("oriOrderId", checkReFundBean.getOriOrderId());
			date_flag = true ;
		}
		if (checkReFundBean.getRrn() != null&&!"".equals(checkReFundBean.getRrn())) {
			sql +=" and w.rrn =:rrn ";
			map.put("rrn", checkReFundBean.getRrn());
			date_flag = true ;
		}
		if (checkReFundBean.getMid() != null&&!"".equals(checkReFundBean.getMid())) {
			sql +=" and w.mid like :mid ";
			map.put("mid", '%'+checkReFundBean.getMid()+'%');
			date_flag = true ;
		}
		if (checkReFundBean.getCardPan() != null&&!"".equals(checkReFundBean.getCardPan())) {
			sql +=" and w.cardPan like :cardPan ";
			map.put("cardPan", '%'+checkReFundBean.getCardPan()+'%');
			date_flag = true ;
		}
		if(checkReFundBean.getStatus() =="F"&&"F".equals(checkReFundBean.getStatus())){
			sql +=" and w.status ='F' or w.status ='K' ";
			date_flag = true ;
		}else if(checkReFundBean.getStatus() != null&&!"".equals(checkReFundBean.getStatus())) {
			sql +=" and w.status =:status ";
			map.put("status", checkReFundBean.getStatus());
			date_flag = true ;
		}
		if (date_flag==false&&(checkReFundBean.getTxnDay() != null&&!"".equals(checkReFundBean.getTxnDay()))) {
			String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(checkReFundBean.getTxnDay());
			checkReFundBean.setTxnDayStr(txnDayStr);
			sql +=" and w.txnDay >=:txnDay ";
			map.put("txnDay", checkReFundBean.getTxnDayStr());
		}
		if (date_flag==false&&(checkReFundBean.getTxnDay1() != null&&!"".equals(checkReFundBean.getTxnDay1()))) {
			String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(checkReFundBean.getTxnDay1());
			sql +=" and w.txnDay <=:txnDay1 ";
			map.put("txnDay1", txnDayStr);
		}
		if (date_flag==false&&(checkReFundBean.getApproveDate() != null&&!"".equals(checkReFundBean.getApproveDate()))) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(checkReFundBean.getApproveDate());
			sql +=" and w.maintainDate >=to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
			//map.put("getApproveDate", checkReFundBean.getApproveDate());
		}
		if (date_flag==false&&(checkReFundBean.getApproveDate1() != null&&!"".equals(checkReFundBean.getApproveDate1()))) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(checkReFundBean.getApproveDate1());
			sql +=" and w.maintainDate <=to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			//map.put("getApproveDate1", checkReFundBean.getApproveDate1());
		}
		if (checkReFundBean.getSort() != null) {
			sql += " ORDER BY " + checkReFundBean.getSort() + " " + checkReFundBean.getOrder();
		}
		
		List<Map<String, Object>> checkReFundList = checkRefundDao.queryObjectsBySqlListMap2(sql, map);
		
		return checkReFundList;
	}
	
	/**
	 * 聚合商户会员宝后台退款发起
	 */
	public String saveHybRefundRequest(CheckReFundBean checkErFundBean){
		Date date = new Date();
		CheckReFundModel model =  new CheckReFundModel();
		BeanUtils.copyProperties(checkErFundBean, model);
		model.setPkId(1);
		model.setCardPan(" ");
		model.setRrn(" ");
		model.setSamt(0d);
		model.setTxnDayStr(" ");
		model.setMaintainDate(date);
		model.setMaintainUid(1);
		model.setStatus("C");
		//上传文件
		if(checkErFundBean.getRefundImg() != null ){
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			StringBuffer fName1 = new StringBuffer();
			fName1.append(checkErFundBean.getOriOrderId());
			fName1.append(".");
			fName1.append(checkErFundBean.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".jpg");
			uploadFile(checkErFundBean.getRefundImg(),fName1.toString(),imageDay);
			model.setRefundImgName(imageDay+File.separator+fName1.toString());
		}
		checkRefundDao.saveObject(model);
		return checkErFundBean.getRrn();
	}
	
	/**
	 * 查询退款记录
	 */
	public List<CheckReFundModel> queryHybRefund(CheckReFundBean checkErFundBean){
		String sql= "select * from check_refund fun where fun.oriorderid='"+checkErFundBean.getOriOrderId()+"' and fun.mid='"+checkErFundBean.getMid()+"'";
		if(checkErFundBean.getStatus()!=null&&!"".equals(checkErFundBean.getStatus())){
			sql += " and fun.status in ('"+checkErFundBean.getStatus()+"')";
		}
		List<CheckReFundModel> list = checkRefundDao.queryObjectsBySqlList(sql, null);
		return list;
	}
	
	/**
	 * 聚合商户会员宝后台退款发起
	 */
	public String updateHybRefundRequest(CheckReFundBean checkErFundBean){
		CheckReFundModel model =  checkRefundDao.queryObjectByHql(
				"from CheckReFundModel m where m.mid=? and m.oriOrderId=?", new String[]{checkErFundBean.getMid(),checkErFundBean.getOriOrderId().substring(6)});
		if(model!=null){
//			model.setPkId(1);
//			model.setCardPan(" ");
			model.setOriOrderId(checkErFundBean.getOriOrderId());
			model.setRrn(checkErFundBean.getRrn());
			model.setSamt(checkErFundBean.getSamt());
			model.setRamt(checkErFundBean.getRamt());
			model.setTxnDayStr(checkErFundBean.getTxnDayStr());
//			model.setMaintainDate(date);
//			model.setMaintainUid(1);
//			model.setStatus("C");
			checkRefundDao.updateObject(model);
			return checkErFundBean.getRrn();
		}else{
			return null;
		}
		
	}
	
	/**
	 * 提交
	 */
	public Integer updateErfundInfo(CheckReFundBean checkErFundBean,String refids, UserBean user){
		Integer i = 0;
		String status = "";
		String msg = "";
		CheckReFundModel model = checkRefundDao.queryObjectByHql("from CheckReFundModel t where t.refId=?", new Object[]{Integer.parseInt(refids)});
		if("Y".equals(checkErFundBean.getStatus())){
			status = "S";
			if(checkErFundBean.getRemarks()==null||"".equals(checkErFundBean.getRemarks())){
				checkErFundBean.setRemarks("退款处理通过");
			}
		}else if("K".equals(checkErFundBean.getStatus())){
			status = "E";
			if(checkErFundBean.getRemarks()==null||"".equals(checkErFundBean.getRemarks())){
				checkErFundBean.setRemarks("退款处理拒绝");
			}
		}else{
			return 0;
		}
		Map<String, String> params =new HashMap<String, String>();
		params.put("rrn", model.getRrn());
		params.put("status", status);
		params.put("errdesc", checkErFundBean.getRemarks());
		String json = JSON.toJSONString(params);
		String result=HttpXmlClient.postForJson(hybReFundUrl, json);
		//解析返回结果
		JSONObject jsonObject = new JSONObject(result);
		status =jsonObject.getString("status");
		msg =jsonObject.getString("msg");
		if("S".equals(status)){
			model.setStatus(checkErFundBean.getStatus());
			model.setApproveDate(new Date());
			model.setRemarks(checkErFundBean.getRemarks());
			checkRefundDao.updateObject(model);
			i=1;
		}
		log.info("聚合商户会员宝后台退款-批量审核-退款订单："+model.getRrn()+";状态："+checkErFundBean.getStatus()+";返回状态："+status+";描述："+msg);
		return i;
	}
	
	/**
	 * 退款信息匹配综合交易记录
	 */
	public DataGridBean queryAdmTxnInfo(CheckReFundBean checkReFundBean, UserBean user){
		
		CheckReFundModel checkReFundModel = new CheckReFundModel();
		BeanUtils.copyProperties(checkReFundBean,checkReFundModel);
		List<CheckReFundModel> checkReFundList = new ArrayList<CheckReFundModel>();
		
		Map<String, String> params =new HashMap<String, String>();
		params.put("refid", checkReFundBean.getRefId()+"");
		params.put("mid", checkReFundBean.getMid());
		params.put("rrn", checkReFundBean.getRrn());
		params.put("txnDay", checkReFundBean.getTxnDayStr());
		params.put("cardPan", checkReFundBean.getCardPan());
		String result=HttpXmlClient.post(admAppIp+"/AdmApp/ProxyErr/ProxyErr_queryTxnInfo.action", params);
		//解析返回结果
		JSONObject jsonObject = new JSONObject(result);
		if(jsonObject.length()!=0){
			if(!"1".equals((String)jsonObject.get("status"))){
				String pkid =(String)jsonObject.get("pkId");
				String samt =(String) jsonObject.get("sAmt");
				checkReFundModel.setPkId(Integer.valueOf(pkid));
				checkReFundModel.setSamt(Double.valueOf(samt));
			}else{
				log.info("退款信息匹配综合交易记录(未匹配到原交易)");
				return new DataGridBean();
			}
		}
		checkReFundList.add(checkReFundModel);
		DataGridBean dataGridBean = formatToDataGrid(checkReFundList,checkReFundList.size());
		return dataGridBean;
	}
	/**
	 * 查询商户是否归属此商户
	 */
	public Integer queryMidInUsr(CheckReFundBean checkReFundBean, UserBean user){
		Integer flag =1;
		//权限
		String sql="select mc.* from bill_merchantinfo mc where mc.mid='"+checkReFundBean.getMid()+"' ";
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if("110000".equals(user.getUnNo())){
		}
		else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){
			UnitModel parent = unitModel.getParent();
			if("110000".equals(parent.getUnNo())){
			}
//		}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() ==1 ){
//			sql+=" and mc.unno ='"+user.getUnNo()+"' ";
		}else{
			String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
			sql+=" and mc.unno in ("+childUnno+") ";
			List<Object> ifAgList = checkRefundDao.queryObjectsBySqlList(sql);
			flag = ifAgList.size();
		}
		
		return flag;
	}
	
	//差错文件导入
	@Override
	public List<String> saveImportRefundInfo(String xlsfile, UserBean user,String fileName) {
		List errlist = new ArrayList();
		boolean flag=false;
		
		int count=0;
		int count1=0;
		int count2=0;
		int count3=0;
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Object> erfundList = new ArrayList<Object>();
			Map<String ,String> sameMap= new HashMap<String, String>();
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell;
				CheckReFundModel erFundModel = new CheckReFundModel();
				cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
//					//1.unno
//					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
//					String unno = row.getCell((short)0).getStringCellValue().trim();
//					erFundModel.setUnno(unno);
//					//2.mid
//					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
//					String mid = row.getCell((short)0).getStringCellValue().trim();
//					erFundModel.setMid(mid);
//					//3.tid
//					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
//					String tid = row.getCell((short)1).getStringCellValue().trim();
//					erFundModel.setTid(tid);
//					//4.cardPan
//					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
//					String cardPan = row.getCell((short)2).getStringCellValue().trim();
//					erFundModel.setCardPan(cardPan);
//					//5.txnDay
//					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
//					String txnDay = ((row.getCell((short)3).getStringCellValue().trim()));
//					erFundModel.setTxnDay(txnDay);
//					//6.sAmt原始交易金额
//					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
//					String sAmt =row.getCell((short)2).getStringCellValue().trim();
//					erFundModel.setsAmt(sAmt);
//					//7.rAmt差错金额
//					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
//					String rAmt = ((row.getCell((short)3).getStringCellValue().trim()));
//					erFundModel.setrAmt(rAmt);
//					//8.refundType退款类型
//					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
//					String refundType = row.getCell((short)2).getStringCellValue().trim();
//					erFundModel.setRefundType(refundType);
//					//9.rrn交易参考号
//					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
//					String rrn = ((row.getCell((short)3).getStringCellValue().trim()));
//					erFundModel.setRrn(rrn);
////					if(ifSendCode==0){
////						//32域传统
////						String sendCodeSql ="select send_code from bill_cupsendcodeinfo where send_name like '%"+areaAddr.trim()+"%' or send_name like '%"+province_name.trim()+"' order by Send_code desc ";
////						List<Map<String, Object>> sendCodeList= agentSalesDao.queryObjectsBySqlObject(sendCodeSql);
////						if(sendCodeList.size()!=0){
////							Map<String, Object> map=sendCodeList.get(0);
////							merch.setSendCode(map.get("SEND_CODE").toString());
////						}
////					}
//					erFundModel.setRank("");
//					erFundModel.setCdate(new Date());
//					erFundModel.setStatus("W");
//					W待提交；C审核中；Y审核成功；F审核失败；K退回；
					
//					String [] errorStr = new String[]{mid,tid};
//					if(!sameMap.containsKey(mid)){
//						String sql="select count(1) from bill_erfundListantinfo t where t.mid='"+mid+"'";
//						Integer midCount=checkRefundDao.querysqlCounts2(sql, null);
//						if(midCount<1){
//							erfundList.add(erfundList);
//							count1++;
//						}else{
//							errlist.add(errorStr);
//						}
//					}
					flag=true;
				}
			}	
			checkRefundDao.batchSaveObject(erfundList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		//count3=count1+count2-count;
		System.out.println(count3);
		return errlist;
	}

	/**
	 * 提交
	 * @return 
	 */
	public Integer saveRefundInfoInfoC(CheckReFundBean checkReFundBean, UserBean user){
		
		Integer result2 = 0;
		String status ="";
		String msg ="";
		String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(checkReFundBean.getTxnDay());
		checkReFundBean.setTxnDayStr(txnDayStr);
		CheckReFundModel checkReFundModel = new CheckReFundModel();
		BeanUtils.copyProperties(checkReFundBean,checkReFundModel);
		//判断合规
		String sql =" select nvl(sum(re.ramt)||'',0) ramtsum  from check_refund re where re.pkid="+checkReFundBean.getPkId()+" and re.status!='F' and re.status!='K' ";
		List<Map<String, String>> result1 = checkRefundDao.queryObjectsBySqlListMap(sql, null);
		double ramtsum = 0d;
		double samt=checkReFundBean.getSamt();
		if(result1!=null&&result1.size()!=0){
			Map<String, String> map = result1.get(0);
			String a= map.get("RAMTSUM");
			ramtsum = Double.valueOf(a);
			//ramtsum = Double.valueOf(map.get("ramtsum"));
			//samt = Double.valueOf(map.get("samt"));
		}
		if(samt>ramtsum+checkReFundBean.getRamt()){
			
			checkReFundModel.setApproveDate(new Date());
			checkReFundModel.setApproveUid(user.getUserID());
			checkReFundModel.setStatus("C");
			checkRefundDao.saveObject(checkReFundModel);
			SimpleDateFormat cd = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//
			Map<String, String> params =new HashMap<String, String>();
			params.put("mid", checkReFundBean.getMid());
			params.put("rrn", checkReFundBean.getRrn());
			params.put("txnDay", checkReFundBean.getTxnDayStr());
			params.put("cardPan", checkReFundBean.getCardPan());
			params.put("pkId", checkReFundBean.getPkId()+"");
			params.put("refId", checkReFundModel.getRefId()+"");
			params.put("rAmt", checkReFundBean.getRamt()+"");
			params.put("sAmt", checkReFundBean.getSamt()+"");
			params.put("settlement", checkReFundBean.getSettlement()+"");
			params.put("cdate", cd.format(checkReFundModel.getApproveDate()));
			params.put("cby", user.getUserName());
			String jsonreq = JSON.toJSONString(params);
			String result=HttpXmlClient.post(admAppIp+"/AdmApp/ProxyErr/ProxyErr_addProxyRefound.action", jsonreq);
			//解析返回结果
			JSONObject jsonObject = new JSONObject(result);
			if(jsonObject.length()!=0){
				status =(String) jsonObject.get("status");
				msg =(String) jsonObject.get("msg"); 
				if("0".equals(status)){
					//checkReFundModel.setStatus("C");
					result2= 1;
				}else{
					checkReFundModel.setStatus("F");
					result2= 0;
				}
			}
		}else{
			result2= -1;
		}
		log.info("提交退款 status:"+status +";msg:"+msg);
		return result2;
	}
	
	/**
	 *  退款审核
	 * @return 
	 */
	@Override
	public boolean updateRefund(com.alibaba.fastjson.JSONObject reqJson){
		
		List jsonlist = (List) reqJson.get("data");
		Integer type =  (Integer) reqJson.get("type");
		//JSONArray jsonArray = JSONArray.fromObject(jsonlist);
		log.info("退款审核..开始解析..........");
		for(int i=0;i<jsonlist.size();i++){
			//JSONObject json = jsonlist.getJSONObject(i);
			com.alibaba.fastjson.JSONObject json=com.alibaba.fastjson.JSONObject.parseObject(jsonlist.get(i).toString());
			String refId = json.getString("refid")+"";
			String status = json.getString("status")+"";
			String remarks = json.getString("remarks");
			
			//String sql =" select * from check_refund re where re.refid="+refId;
			String sql =" update check_refund re set re.status='"+status+"', re.remarks='"+remarks+"', re.approvedate=sysdate where re.refid="+refId;
			checkRefundDao.executeUpdate(sql);
//			CheckReFundModel checkMisTakeModel = checkRefundDao.queryObjectBySql(sql, new HashMap<String, Object>());
//			if(checkMisTakeModel!=null){
//				checkMisTakeModel.setStatus(status);
//				checkMisTakeModel.setRemarks(remarks);
//				checkMisTakeModel.setApprovedate(new Date());
//				checkRefundDao.updateObject(checkMisTakeModel);
//			}
		}
		return true;
	}
	
	/**
	 * 方法功能：格式化erfundListantAuthenticityModel为datagrid数据格式
	 * 参数：erfundListantAuthenticityList
	 * 		total 总记录数
	 */
	private DataGridBean formatToDataGrid(List<CheckReFundModel> checkReFundList, long total) {
		List<CheckReFundBean> ErFundList = new ArrayList<CheckReFundBean>();
		String url = queryUpLoadPath();
		for(CheckReFundModel checkReFundModel : checkReFundList) {
			CheckReFundBean checkReFundBean = new CheckReFundBean();
			BeanUtils.copyProperties(checkReFundModel, checkReFundBean);
			checkReFundBean.setRefundImgName(url+File.separator+checkReFundBean.getRefundImgName());
			ErFundList.add(checkReFundBean);
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(ErFundList);
		
		return dgb;
	}

	/**
	 * 上传
	 */
	private void uploadFile(File upload, String fName, String imageDay) {
		try {
			String realPath = queryUpLoadPath() + File.separator + imageDay;
			File dir = new File(realPath);
			dir.mkdirs();
			String fPath = realPath + File.separator + fName;
			File destFile = new File(fPath);
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte [] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			} 
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询上传路径
	 */
	private String queryUpLoadPath() {
		return checkRefundDao.queryObjectsBySqlListMap("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='reFundInfo'",null).get(0).get("UPLOAD_PATH");
	}

	@Override
	public Integer updateErfundInfo2(CheckReFundBean checkErFundBean, String refids, UserBean user) {
		Integer i = 0;
		CheckReFundModel model = checkRefundDao.queryObjectByHql("from CheckReFundModel t where t.refId=?", new Object[]{Integer.parseInt(refids)});
		if("Y".equals(checkErFundBean.getStatus())){
			model.setStatus("Y");
			model.setRemarks("车辆代缴退款处理通过");
			model.setApproveDate(new Date());
			i=1;
			//修改车辆代缴订单状态
			String hrtOrderId = model.getRrn();
			String payOrderId = model.getOriOrderId();
			GateOrderModel gateOrderModel =gateCarOrderDao.queryObjectByHql("from GateOrderModel t where t.hrtOrderId=? and t.payOrderId=?", new Object[]{hrtOrderId,payOrderId});
			gateOrderModel.setState("7");
			gateOrderModel.setPassMemo("订单已退款");
			gateCarOrderDao.updateObject(gateOrderModel);
		}else if("K".equals(checkErFundBean.getStatus())){
			model.setStatus("K");
			model.setRemarks("车辆代缴退款处理拒绝");
			model.setApproveDate(new Date());
		}else{
			return 0;
		}
		checkRefundDao.updateObject(model);
		
		return i;
	}
}
