package com.hrt.biz.check.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.check.dao.CheckMisTakeDao;
import com.hrt.biz.check.entity.model.CheckMisTakeModel;
import com.hrt.biz.check.entity.pagebean.CheckMisTakeBean;
import com.hrt.biz.check.service.CheckMisTakeService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;

public class CheckMisTakeServiceImpl implements CheckMisTakeService {

	private CheckMisTakeDao checkMisTakeDao;
	private IUnitDao unitDao;
	private IMerchantInfoService merchantInfoService; 
	private IAgentSalesDao agentSalesDao;
	
	private static final Log log = LogFactory.getLog(CheckMisTakeServiceImpl.class);
	
	
	public CheckMisTakeDao getCheckMisTakeDao() {
		return checkMisTakeDao;
	}
	public void setCheckMisTakeDao(CheckMisTakeDao checkMisTakeDao) {
		this.checkMisTakeDao = checkMisTakeDao;
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
	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}
	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}
	/**
	 * 查询回复记录
	 */
	public DataGridBean querMisTakeInfo(CheckMisTakeBean checkMisTakeBean, UserBean user){
		
		String sql="select w.* from check_dispatchOrder w where  w.orderType=:orderType  ";
		String sqlCount="select count(1) from check_dispatchOrder w where  w.orderType=:orderType ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderType", checkMisTakeBean.getOrderType());
		boolean date_flag = false ;
		//权限
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(user.getUseLvl()==3){
				sql+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
				"PARENTMID= '"+user.getLoginName()+"' or MID ='"+user.getLoginName()+"')  ";
				sqlCount+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
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
					sql+=" and w.minfo="+ifAgList.get(0).getBusid();
					sqlCount+=" and w.minfo="+ifAgList.get(0).getBusid();
				}else{
					//代理
					String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
					sql+=" and w.unno IN ("+childUnno+" )";
					sqlCount+=" and w.unno IN ("+childUnno+" )";
				}
			}
		}
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 = 1 ";
//		}
		if (checkMisTakeBean.getRrn() != null&&!"".equals(checkMisTakeBean.getRrn())) {
			sql +=" and w.rrn =:rrn ";
			sqlCount +=" and w.rrn =:rrn ";
			map.put("rrn", checkMisTakeBean.getRrn());
			date_flag = true ;
		}
		if (checkMisTakeBean.getMid() != null&&!"".equals(checkMisTakeBean.getMid())) {
			sql +=" and w.mid like :mid ";
			sqlCount +=" and w.mid like :mid ";
			map.put("mid", '%'+checkMisTakeBean.getMid()+'%');
			date_flag = true ;
		}
		if (checkMisTakeBean.getCardNo() != null&&!"".equals(checkMisTakeBean.getCardNo())) {
			sql +=" and w.cardNo like :cardNo ";
			sqlCount +=" and w.cardNo like :cardNo ";
			map.put("cardNo", '%'+checkMisTakeBean.getCardNo()+'%');
			date_flag = true ;
		}
		if(checkMisTakeBean.getStatus() != null&&!"".equals(checkMisTakeBean.getStatus())) {
			sql +=" and w.status =:status ";
			sqlCount +=" and w.status =:status ";
			map.put("status", checkMisTakeBean.getStatus());
			//date_flag = true ;
		}
		if (date_flag==false&&(checkMisTakeBean.getTxnDay() != null&&!"".equals(checkMisTakeBean.getTxnDay()))) {
			String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(checkMisTakeBean.getTxnDay());
			//checkMisTakeBean.setTxnDayStr(txnDayStr);
			sql +=" and w.inportDate >=:inportDate ";
			sqlCount +=" and w.inportDate >=:inportDate ";
			map.put("inportDate", txnDayStr);
		}
		if (date_flag==false&&(checkMisTakeBean.getTxnDayEnd() != null&&!"".equals(checkMisTakeBean.getTxnDayEnd()))) {
			String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(checkMisTakeBean.getTxnDayEnd());
			//checkMisTakeBean.setTxnDayStr(txnDayStr);
			sql +=" and w.inportDate <=:inportDateEnd ";
			sqlCount +=" and w.inportDate <=:inportDateEnd ";
			map.put("inportDateEnd", txnDayStr);
		}
		if (checkMisTakeBean.getIsM35()!=null&&!"".equals(checkMisTakeBean.getIsM35())) {
			if(checkMisTakeBean.getIsM35()==1){
				sql +=" and w.minfo2 ='1' ";
				sqlCount +=" and w.minfo2 ='1' ";
			}else{
				sql +=" and w.minfo2 !='1' ";
				sqlCount +=" and w.minfo2 !='1' ";
			}
		}
		if (checkMisTakeBean.getSort() != null) {
			sql += " ORDER BY " + checkMisTakeBean.getSort() + " " + checkMisTakeBean.getOrder();
		}
		//List<CheckWechatTxnDetailModel> CheckWechatTxnList = checkWechantTxnDetailDao.queryWechantTxnDetailInfo(hql, map, checkWechatTxnDetailBean.getPage(), checkWechatTxnDetailBean.getRows(), CheckWechatTxnDetailModel.class);
		List<CheckMisTakeModel> checkMisTakeList = checkMisTakeDao.queryObjectsBySqlLists(sql, map, checkMisTakeBean.getPage(), checkMisTakeBean.getRows(), CheckMisTakeModel.class);
		BigDecimal count = checkMisTakeDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(checkMisTakeList, count.intValue());
		return dataGridBean;
	}
	
	/**
	 * 差错查询导出所有
	 */
	public HSSFWorkbook querMisTakeInfoExport(CheckMisTakeBean checkMisTakeBean,UserBean user){
		
		String sql="select w.*,case when w.minfo2='1' then '手刷' else '非手刷' end ISM35," +
		" (select m.rname from bill_merchantinfo m where m.mid=w.mid)rname1 " +
		" from check_dispatchOrder w where w.orderType=:orderType  ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderType", checkMisTakeBean.getOrderType());
		boolean date_flag = false ;
		//权限
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		if(user.getUseLvl()==3){
				sql+=" and w.MID in (select MID from  bill_MerchantInfo where  " +
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
					sql+=" and w.minfo="+ifAgList.get(0).getBusid();
				}else{
					//代理
					String childUnno=merchantInfoService.queryUnitUnnoUtil(user.getUnNo());
					sql+=" and w.unno IN ("+childUnno+" )";
				}
			}
		}
//		if("0".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 !=1 ";
//		}else if("1".equals(user.getIsM35Type())){
//			sql +=" and m.isM35 = 1 ";
//		}
		if (checkMisTakeBean.getRrn() != null&&!"".equals(checkMisTakeBean.getRrn())) {
			sql +=" and w.rrn =:rrn ";
			map.put("rrn", checkMisTakeBean.getRrn());
			date_flag = true ;
		}
		if (checkMisTakeBean.getMid() != null&&!"".equals(checkMisTakeBean.getMid())) {
			sql +=" and w.mid like :mid ";
			map.put("mid", '%'+checkMisTakeBean.getMid()+'%');
			date_flag = true ;
		}
		if (checkMisTakeBean.getCardNo() != null&&!"".equals(checkMisTakeBean.getCardNo())) {
			sql +=" and w.cardNo like :cardNo ";
			map.put("cardNo", '%'+checkMisTakeBean.getCardNo()+'%');
			date_flag = true ;
		}
		if(checkMisTakeBean.getStatus() != null&&!"".equals(checkMisTakeBean.getStatus())) {
			sql +=" and w.status =:status ";
			map.put("status", checkMisTakeBean.getStatus());
			//date_flag = true ;
		}
		if (date_flag==false&&(checkMisTakeBean.getTxnDay() != null&&!"".equals(checkMisTakeBean.getTxnDay()))) {
			String txnDayStr = new SimpleDateFormat("yyyyMMdd").format(checkMisTakeBean.getTxnDay());
			sql +=" and w.inportDate =:inportDate ";
			map.put("inportDate", txnDayStr);
		}
		if (checkMisTakeBean.getSort() != null) {
			sql += " ORDER BY " + checkMisTakeBean.getSort() + " " + checkMisTakeBean.getOrder();
		}
		if (checkMisTakeBean.getIsM35()!=null&&!"".equals(checkMisTakeBean.getIsM35())) {
			if(checkMisTakeBean.getIsM35()==1){
				sql +=" and w.minfo2 ='1' ";
			}else{
				sql +=" and w.minfo2 !='1' ";
			}
		}
		//List<Map<String, Object>> data = checkMisTakeDao.queryObjectsBySqlList(sql);
		List<Map<String, Object>> data = checkMisTakeDao.queryObjectsBySqlListMap2(sql, map);
//		for (Map<String, Object> map2 : data) {
//			if(map2.get("MINFO2")!=null&&!"".equals(map2.get("MINFO2"))){
//				String i = ((BigDecimal) map2.get("ISM35")).toString();
//				if("1".equals(i)){
//					map2.put("ISM35", "手刷");
//				}else{
//					map2.put("ISM35", "非手刷");
//				}
//			}
//		}
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();

		excelHeader.add("INPORTDATE");
		excelHeader.add("MID");
		excelHeader.add("RNAME1");
		excelHeader.add("CARDNO");
		excelHeader.add("SAMT");
		excelHeader.add("TRANSDATE");
		excelHeader.add("FINALDATE");
		excelHeader.add("BANKREMARKS");
		excelHeader.add("RRN");
		excelHeader.add("RNAME");
		excelHeader.add("RADDR");
		excelHeader.add("CONTACTPHONE");
		excelHeader.add("CONTACTPERSON");
		excelHeader.add("AGREMARKS");
		excelHeader.add("REASON");
		excelHeader.add("TID");
		excelHeader.add("ISM35");
		excelHeader.add("STATUS");

		headMap.put("INPORTDATE", "通知日期");
		headMap.put("MID", "商户编号");
		headMap.put("RNAME1", "商户名称");
		headMap.put("CARDNO", "交易卡号");
		headMap.put("SAMT", "交易金额");
		headMap.put("TRANSDATE", "交易日期");
		headMap.put("FINALDATE", "暂定回复日期");
		headMap.put("BANKREMARKS", "银联备注");
		headMap.put("RRN", "参考号");
		headMap.put("RNAME", "商户注册店名");
		headMap.put("RADDR", "营业地址");
		headMap.put("CONTACTPHONE", "联系电话");
		headMap.put("CONTACTPERSON", "联系人");
		headMap.put("AGREMARKS", "代理回复备注");
		headMap.put("REASON", "原因码");
		headMap.put("TID", "终端号");
		headMap.put("ISM35", "商户类型");
		headMap.put("STATUS", "回复状态(1:已回复)");
		
		return ExcelUtil.export("差错查询", data, excelHeader, headMap);
	}
	/**
	 *  查询回复报单接收
	 * @return 
	 */
	public boolean saveDispatchOrder(JSONObject reqJson){
		
		List jsonlist = (List) reqJson.get("data");
		Integer type =  (Integer) reqJson.get("type");
		//JSONArray jsonArray = JSONArray.fromObject(jsonlist);
		log.info("查询回复报单接收..开始解析..........");
		for(int i=0;i<jsonlist.size();i++){
			//JSONObject json = jsonlist.getJSONObject(i);
			JSONObject json=JSONObject.parseObject(jsonlist.get(i).toString());
			String dpid = json.getString("ID")+"";
			String inportDate = json.getString("INPORTDATE");
			String mid = json.getString("HRTMID");
			String cardNo = json.getString("CARDNO");
			String samt = json.getString("SAMT");
			String transDate = json.getString("TRANSDATE");
			String finalDate = json.getString("FINALDATE");
			String bankRemarks = json.getString("REMARKS");
			String rrn = json.getString("RRN");
			String reason = json.getString("REASON");
			String tid = json.getString("HRTTID");
			//查询报单商户表信息
			//20190809 sl
			String sq = "select m.unno,m.ism35,m.busid from bill_merchantinfo m where m.mid='"+mid+"' ";
			List<Map<String,String>> list = checkMisTakeDao.executeSql(sq);
			String unno ="";
			String isM35="";
			String busId="";
			if(list.size()>0){
				unno = String.valueOf(list.get(0).get("UNNO"));
				isM35 = String.valueOf(list.get(0).get("ISM35"));
				busId = String.valueOf(list.get(0).get("BUSID"));
			}
			try {
				Date date=new Date();
				DateFormat df = new SimpleDateFormat("yyyyMMdd");
				long dif = df.parse(finalDate).getTime()-86400*1000*2;
				date.setTime(dif);
				finalDate=df.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
				log.info("查询回复报单接收 日期转化错误..."+e);
			}
			
			CheckMisTakeModel checkMisTakeModel =new CheckMisTakeModel();
			checkMisTakeModel.setDpid(Integer.valueOf(dpid));
			checkMisTakeModel.setMid(mid);
			checkMisTakeModel.setRrn(rrn);
			checkMisTakeModel.setInportDate(inportDate);
			checkMisTakeModel.setCardNo(cardNo);
			if(samt!=null&&!"".equals(samt)){
				checkMisTakeModel.setSamt(Double.parseDouble(samt));
			}
			checkMisTakeModel.setTransDate(transDate);
			checkMisTakeModel.setFinalDate(finalDate);
			checkMisTakeModel.setBankRemarks(bankRemarks);
			checkMisTakeModel.setUnno(unno);
			checkMisTakeModel.setMinfo(busId);
			checkMisTakeModel.setMinfo2(isM35);
			checkMisTakeModel.setTid(tid);
			checkMisTakeModel.setReason(reason);
			checkMisTakeModel.setOrderType(type);
			checkMisTakeModel.setStatus("0");
			checkMisTakeDao.saveObject(checkMisTakeModel);
		}
		return true;
	}
	
	/**
	 *  查询回复
	 * @return 
	 */
	public boolean updateDispatchOrder(CheckMisTakeBean checkMisTakeBean,UserBean user){
		
		String hql ="select di from CheckMisTakeModel di where di.dpid="+checkMisTakeBean.getDpid();
		List<CheckMisTakeModel> list = checkMisTakeDao.queryObjectsByHqlList(hql);
		//CheckMisTakeModel checkMisTakeModel = new CheckMisTakeModel();
		//BeanUtils.copyProperties(checkMisTakeBean, checkMisTakeModel);
		if(list!=null&&list.size()!=0){
			CheckMisTakeModel checkMisTakeModel = list.get(0);
			checkMisTakeModel.setStatus("1");
			checkMisTakeModel.setMaintainDate(new Date());
			checkMisTakeModel.setMaintainUid(user.getUserID());
			
			if(checkMisTakeBean.getRname()!=null&&!"".equals(checkMisTakeBean.getRname())){
				checkMisTakeModel.setRname(checkMisTakeBean.getRname().trim());
			}
			if(checkMisTakeBean.getAgRemarks()!=null&&!"".equals(checkMisTakeBean.getAgRemarks())){
				checkMisTakeModel.setAgRemarks(checkMisTakeBean.getAgRemarks().trim());
			}
			if(checkMisTakeBean.getRaddr()!=null&&!"".equals(checkMisTakeBean.getRaddr())){
				checkMisTakeModel.setRaddr(checkMisTakeBean.getRaddr().trim());
			}
			if(checkMisTakeBean.getContactPhone()!=null&&!"".equals(checkMisTakeBean.getContactPhone())){
				checkMisTakeModel.setContactPhone(checkMisTakeBean.getContactPhone().trim());
			}
			if(checkMisTakeBean.getContactPerson()!=null&&!"".equals(checkMisTakeBean.getContactPerson())){
				checkMisTakeModel.setContactPerson(checkMisTakeBean.getContactPerson().trim());
			}
			//上传文件
			if(checkMisTakeBean.getOrderUploadFile()!= null && !"".equals(checkMisTakeBean.getOrderUpload())){
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				StringBuffer fName1 = new StringBuffer();
				//fName1.append(imageDay+"\\");
				fName1.append(checkMisTakeModel.getDpid());
				fName1.append(".");
				fName1.append(checkMisTakeBean.getRrn());
				fName1.append(".");
				fName1.append(imageDay);
				fName1.append(".order");
				fName1.append(checkMisTakeBean.getOrderUpload().substring(checkMisTakeBean.getOrderUpload().lastIndexOf(".")).toLowerCase());
				uploadFile(checkMisTakeBean.getOrderUploadFile(),fName1.toString(),imageDay);
				checkMisTakeModel.setOrderUpload(imageDay+File.separator+fName1.toString());
			}
			checkMisTakeDao.updateObject(checkMisTakeModel);
			return true;
		}
		return false;
	}
	
	/**
	 * 查看明细
	 */
	public CheckMisTakeBean queryMisTakeById(Integer id){
		//String title="HrtFrameWork";/u01/hrtwork/SignFailUpload/
		//String path = merchantAuthenticityDao.querySavePath(title);
		CheckMisTakeModel mm=(CheckMisTakeModel) checkMisTakeDao.queryObjectByHql("from CheckMisTakeModel m where  m.dpid=?", new Object[]{id});
		CheckMisTakeBean mb = new CheckMisTakeBean(); 
		BeanUtils.copyProperties(mm, mb);
		mb.setOrderUpload(queryUpLoadPath()+File.separator+mm.getOrderUpload());
		//mb.setOrderUpload(File.separator+"u01"+File.separator+"hrtwork"+File.separator+"dispatchOrder"+File.separator+mm.getOrderUpload());
		//mb.setAuthUpload("D:"+File.separator+mm.getAuthUpload());
		return mb; 
	}
	
	/**
	 * 方法功能：格式化erfundListantAuthenticityModel为datagrid数据格式
	 * 参数：erfundListantAuthenticityList
	 * 		total 总记录数
	 */
	private DataGridBean formatToDataGrid(List<CheckMisTakeModel> checkMisTakeList, long total) {
		List<CheckMisTakeBean> MisTakList = new ArrayList<CheckMisTakeBean>();
		for(CheckMisTakeModel CheckMisTakeModel : checkMisTakeList) {
			CheckMisTakeBean checkMisTakeBean = new CheckMisTakeBean();
			BeanUtils.copyProperties(CheckMisTakeModel, checkMisTakeBean);
			//商户名
			if(checkMisTakeBean.getMid()!=null&&!"".equals(checkMisTakeBean.getMid())){
				DataGridBean dg = merchantInfoService.queryMerchantInfoMid(checkMisTakeBean.getMid());
				if(dg!=null&&dg.getRows().size()>0){
					String rname =((MerchantInfoBean)(dg.getRows().get(0))).getRname();
					checkMisTakeBean.setRname1(rname);
					checkMisTakeBean.setIsM35(((MerchantInfoBean)(dg.getRows().get(0))).getIsM35());
				}
			}
			//交易金额
			if(checkMisTakeBean.getSamt()!=null){
				BigDecimal number = new BigDecimal(1);
				BigDecimal samt = new BigDecimal(checkMisTakeBean.getSamt());
				Double bfr= samt.multiply(number).doubleValue();
				checkMisTakeBean.setSamt(Double.valueOf(bfr.toString()));
			}
			MisTakList.add(checkMisTakeBean);
		}
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(MisTakList);
		
		return dgb;
	}
	/**
	 * 上传
	 */
	private void uploadFile(File upload, String fName, String imageDay) {
		try {
			String realPath = queryUpLoadPath() + File.separator + imageDay;
			//String realPath = queryUpLoadPath();
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
		String title="dispatchOrder";
		return checkMisTakeDao.querySavePath(title);
		//return "/u01/hrtwork/dispatchOrder";
	}
}
