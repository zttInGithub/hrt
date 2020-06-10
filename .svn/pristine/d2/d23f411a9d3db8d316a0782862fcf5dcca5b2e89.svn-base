package com.hrt.phone.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.argorse.security.core.hash.MD5Wrapper;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMIDSeqInfoDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.IMerchantTerminalInfoDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.MIDSeqInfoModel;
import com.hrt.biz.bill.entity.model.MachineInfoModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.biz.bill.entity.model.TerminalInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.bill.service.impl.MerchantInfoServiceImpl;
import com.hrt.frame.dao.sysadmin.IRoleDao;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.RoleModel;
import com.hrt.frame.entity.model.TodoModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.gmms.webservice.MerchantInfo;
import com.hrt.gmms.webservice.TermAcc;
import com.hrt.phone.dao.IPhoneMerchantInfoDao;
import com.hrt.phone.dao.IPhoneMicroMerchantInfoDao;
import com.hrt.phone.entity.model.MerchantBankCardModel;
import com.hrt.phone.service.IPhoneMerchantInfoService;
import com.hrt.phone.service.IPhoneMicroMerchantInfoService;
import com.hrt.util.ClassToJavaBeanUtil;
import com.hrt.util.HttpXmlClient;

public class PhoneMerchantInfoServiceImpl implements IPhoneMerchantInfoService {

	private IPhoneMerchantInfoDao phoneMerchantInfoDao;
	private IMIDSeqInfoDao midSeqInfoDao;
	private IRoleDao roleDao;
	private IUnitDao unitDao;
	private IUserDao userDao;
	private IMerchantInfoDao merchantInfoDao;
	private ITodoDao todoDao;
	private IMerchantInfoService merchantInfoService;
	private String hybUrl;
	private IAgentSalesDao agentSalesDao;
	private IMerchantTerminalInfoDao merchantTerminalInfoDao;
	private IGmmsService gsClient;
	private static final Log log = LogFactory.getLog(PhoneMerchantInfoServiceImpl.class);
	
	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}
	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}
	public IMerchantTerminalInfoDao getMerchantTerminalInfoDao() {
		return merchantTerminalInfoDao;
	}
	public void setMerchantTerminalInfoDao(
			IMerchantTerminalInfoDao merchantTerminalInfoDao) {
		this.merchantTerminalInfoDao = merchantTerminalInfoDao;
	}
	public IGmmsService getGsClient() {
		return gsClient;
	}
	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}
	public IPhoneMerchantInfoDao getPhoneMerchantInfoDao() {
		return phoneMerchantInfoDao;
	}
	public void setPhoneMerchantInfoDao(IPhoneMerchantInfoDao phoneMerchantInfoDao) {
		this.phoneMerchantInfoDao = phoneMerchantInfoDao;
	}
	public IMIDSeqInfoDao getMidSeqInfoDao() {
		return midSeqInfoDao;
	}
	public void setMidSeqInfoDao(IMIDSeqInfoDao midSeqInfoDao) {
		this.midSeqInfoDao = midSeqInfoDao;
	}
	public IRoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public IUnitDao getUnitDao() {
		return unitDao;
	}
	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	public IUserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}
	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}
	public ITodoDao getTodoDao() {
		return todoDao;
	}
	public void setTodoDao(ITodoDao todoDao) {
		this.todoDao = todoDao;
	}
	public IMerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}
	public void setMerchantInfoService(IMerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}
	public String getHybUrl() {
		return hybUrl;
	}
	public void setHybUrl(String hybUrl) {
		this.hybUrl = hybUrl;
	}
	
	/**
	 * 查询出踢回和未放行的商户
	 */
	@Override
	public DataGridBean queryMerchantInfoZK(MerchantInfoBean merchantInfo,UserBean user)
			throws Exception {
		String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType ";
		Map<String, Object> usermap = new HashMap<String, Object>();
		usermap.put("userID", user.getUserID().toString());
		usermap.put("maintainType", "D");
		List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		
		UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if(agentSalesModels.size()==0){
			return null;
		}else{
			sql = "SELECT * FROM BILL_MERCHANTINFO WHERE (MAINTAINUSERID = :maintainUserId or busid =:maintainUserId) AND MAINTAINTYPE != :maintainType  and ISM35!=1  ";
			sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE (MAINTAINUSERID = :maintainUserId or busid =:maintainUserId) AND MAINTAINTYPE != :maintainType  and ISM35!=1  ";
			map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			map.put("maintainType", "D");
		}
		
		if(merchantInfo.getMid()!=null && !"".equals(merchantInfo.getMid())){
			sql += " and MID like '%"+merchantInfo.getMid()+"%'";
			sqlCount += " and MID like '%"+merchantInfo.getMid()+"%'";
		}
		if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			sql += " and RNAME LIKE '%"+merchantInfo.getRname()+"%'";
			sqlCount += " and RNAME LIKE '%"+merchantInfo.getRname()+"%'";
		}
		if(merchantInfo.getApproveStatus()!=null &&!"".equals(merchantInfo.getApproveStatus())){
			sql+=" and approveStatus=:approveStatus2";
			sqlCount+=" and approveStatus=:approveStatus2";
			map.put("approveStatus2", merchantInfo.getApproveStatus());
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			sql +=" and joinConfirmDate >=to_date(:joinConfirmDate,'yyyy-mm-dd')" ;
			sqlCount +=" and joinConfirmDate >= to_date(:joinConfirmDate,'yyyy-mm-dd')" ;
			map.put("joinConfirmDate", merchantInfo.getCreateDateStart());
		} 
		
		if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			sql +=" and joinConfirmDate < to_date(:joinConfirmDate1,'yyyy-mm-dd')+1 " ;
			sqlCount +=" and joinConfirmDate < to_date(:joinConfirmDate1,'yyyy-mm-dd')+1 " ;
			map.put("joinConfirmDate1", merchantInfo.getCreateDateEnd());
		}
		sql += " ORDER BY BMID DESC";
		BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());
		return dataGridBean;
	}
	/**
	 * 方法功能：修改商户
	 * 参数：merchantInfo
	 * 	   loginName 当前登录用户名
	 * 返回值：void
	 * 异常：
	 */
	@Override
	public boolean updateMerchantInfo(MerchantInfoBean merchantInfo, UserBean user)
			throws Exception {
		MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, merchantInfo.getBmid());
		
		if(merchantInfoModel.getApproveStatus().equals(merchantInfo.getApproveStatus())){
//			if(!merchantInfoModel.getMccid().equals(merchantInfo.getMccid()) || !merchantInfoModel.getAreaCode().trim().equals(merchantInfo.getAreaCode())){
//				//商户编号生成
//				StringBuffer mid = new StringBuffer("864");
//				if(merchantInfo.getAreaCode().trim().length()<=3){
//					mid.append("0"+merchantInfo.getAreaCode().trim());
//				}else{
//					mid.append(merchantInfo.getAreaCode().trim());
//				}
//				mid.append(merchantInfo.getMccid());
//				Integer seqNo = merchantInfoService.searchMIDSeqInfo(merchantInfo.getAreaCode(), merchantInfo.getMccid().toString(),user.getLoginName());
//				DecimalFormat deFormat = new DecimalFormat("0000");
//				mid.append(deFormat.format(seqNo));
//				merchantInfo.setMid(mid.toString());
//				
//				String merchantTerminalInfoSql = "from MerchantTerminalInfoModel where mid = :merchantTerminalInfoMid";
//				Map<String, Object> merchantTerminalInfoMap = new HashMap<String, Object>();
//				merchantTerminalInfoMap.put("merchantTerminalInfoMid", merchantInfoModel.getMid());
//				List<MerchantTerminalInfoModel> merchantTerminalInfoList = merchantTerminalInfoDao.queryObjectsByHqlList(merchantTerminalInfoSql, merchantTerminalInfoMap);
//				for (MerchantTerminalInfoModel merchantTerminalInfoModel : merchantTerminalInfoList) {
//					merchantTerminalInfoModel.setMid(mid.toString());
//					merchantTerminalInfoModel.setMaintainUid(user.getUserID());
//					merchantTerminalInfoModel.setMaintainDate(new Date());
//					merchantTerminalInfoModel.setMaintainType("M");		//A-add   M-Modify  D-Delete
//					merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
//				}
//			}
			merchantInfo.setMid(merchantInfoModel.getMid());
			BeanUtils.copyProperties(merchantInfo, merchantInfoModel);
			
			//传统商户 isM35:0
			//merchantInfoModel.setIsM35(0);
			if(merchantInfoModel.getIsM35()==2||merchantInfoModel.getIsM35()==3){
				merchantInfoModel.setAreaType("1");
			}else if(merchantInfoModel.getIsM35()==4){
				merchantInfoModel.setAreaType("2");
			}else if(merchantInfoModel.getIsM35()==5){
				merchantInfoModel.setAreaType("3");
			}

			//封顶值
			if(merchantInfo.getMerchantType() == 2){
				
				if(!"0".equals(merchantInfo.getBankFeeRate()) && !"0".equals(merchantInfo.getFeeAmt())){
					Double dealAmt = Double.parseDouble(merchantInfo.getFeeAmt())/(Double.parseDouble(merchantInfo.getBankFeeRate())/100);
					Double deal = Math.floor(dealAmt);
					merchantInfoModel.setDealAmt(deal.toString());
				}else{
					merchantInfoModel.setDealAmt("0");
					merchantInfoModel.setFeeAmt("0");
				}
			}else{
				merchantInfoModel.setDealAmt("0");
				merchantInfoModel.setFeeAmt("0");
			}
			
			//银联卡费率
			double bankFeeRate = Double.parseDouble((merchantInfo.getBankFeeRate()))/100;
			merchantInfoModel.setBankFeeRate(String.valueOf(bankFeeRate));
			//贷记卡费率
			if(merchantInfo.getCreditBankRate()!=null&&!"".equals(merchantInfo.getCreditBankRate())){
				Double creditBankRate = Double.parseDouble(merchantInfo.getCreditBankRate())/100;
				merchantInfoModel.setCreditBankRate(creditBankRate.toString());
			}else{
				merchantInfoModel.setCreditBankRate("0");
			}
			if(merchantInfo.getBno()==null||"".equals(merchantInfo.getBno())){
				merchantInfo.setBno("000000000000");
			}
			if(merchantInfo.getBusinessScope()==null||"".equals(merchantInfo.getBusinessScope())){
				merchantInfo.setBusinessScope("未知");
			}
			if(merchantInfo.getSettleCycle()==null||"".equals(merchantInfo.getSettleCycle())){
				merchantInfo.setSettleCycle(1);
			}
			if(merchantInfo.getFeeRateV()!=null && !"".equals(merchantInfo.getFeeRateV().trim()) && !"0".equals(merchantInfo.getFeeRateV().trim())){
				double feeRateV = Double.parseDouble(merchantInfo.getFeeRateV())/100;
				merchantInfoModel.setFeeRateV(String.valueOf(feeRateV));
			}
			if(merchantInfo.getFeeRateM()!=null && !"".equals(merchantInfo.getFeeRateM().trim()) && !"0".equals(merchantInfo.getFeeRateM().trim())){
				double feeRateM = Double.parseDouble(merchantInfo.getFeeRateM())*100;
				merchantInfoModel.setFeeRateM(String.valueOf(feeRateM));
			}
			if(merchantInfo.getFeeRateJ()!=null && !"".equals(merchantInfo.getFeeRateJ().trim()) && !"0".equals(merchantInfo.getFeeRateJ().trim())){
				double feeRateJ = Double.parseDouble(merchantInfo.getFeeRateJ())*100;
				merchantInfoModel.setFeeRateJ(String.valueOf(feeRateJ));
			}
			if(merchantInfo.getFeeRateA()!=null && !"".equals(merchantInfo.getFeeRateA().trim()) && !"0".equals(merchantInfo.getFeeRateA().trim())){
				double feeRateA = Double.parseDouble(merchantInfo.getFeeRateA())*100;
				merchantInfoModel.setFeeRateA(String.valueOf(feeRateA));
			}
			if(merchantInfo.getFeeRateD()!=null && !"".equals(merchantInfo.getFeeRateD().trim()) && !"0".equals(merchantInfo.getFeeRateD().trim())){
				double feeRateD = Double.parseDouble(merchantInfo.getFeeRateD())*100;
				merchantInfoModel.setFeeRateD(String.valueOf(feeRateD));
			}
			//是否合并结账
			merchantInfoModel.setSettleMerger("1");
			//结算方式（是否秒到,传统商户默认：000000）
			merchantInfoModel.setSettMethod("000000");
			
			//商户账单名称为空则和商户注册名称一样
			if(merchantInfoModel.getShortName() == null || "".equals(merchantInfoModel.getShortName().trim())){
				merchantInfoModel.setShortName(merchantInfo.getRname());
			}
			
			//凭条打印名称为空泽合商户注册名称一样
			if(merchantInfoModel.getPrintName() == null || "".equals(merchantInfoModel.getPrintName().trim())){
				merchantInfoModel.setPrintName(merchantInfo.getRname());
			}
			
			//判断开户银行中是否有交通银行几个字
			if(merchantInfo.getBankBranch().indexOf("交通银行") == -1){
				merchantInfoModel.setBankType("0");		//非交通银行
			}else{
				merchantInfoModel.setBankType("1");		//交通银行
			}
			
			//判断开户银行中是否有北京几个字
//			if(merchantInfo.getBankBranch().indexOf("北京") == -1){
//				merchantInfoModel.setAreaType("0");		//非北京
//			}else{
//				merchantInfoModel.setAreaType("1");		//北京
//			}
			
			//上传文件
			if(merchantInfo.getLegalUploadFile() != null && merchantInfo.getLegalUploadFileName() != null){
				StringBuffer fName1 = new StringBuffer();
				fName1.append(user.getUnNo());
				fName1.append(".");
				fName1.append(merchantInfoModel.getMid());
				fName1.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName1.append(imageDay);
				fName1.append(".1");
				fName1.append(merchantInfo.getLegalUploadFileName().substring(merchantInfo.getLegalUploadFileName().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getLegalUploadFile(),fName1.toString(),imageDay);
				merchantInfoModel.setLegalUploadFileName(fName1.toString());
			}
			
			if(merchantInfo.getBupLoadFile() != null && merchantInfo.getBupLoad() != null){
				StringBuffer fName2 = new StringBuffer();
				fName2.append(user.getUnNo());
				fName2.append(".");
				fName2.append(merchantInfoModel.getMid());
				fName2.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName2.append(imageDay);
				fName2.append(".2");
				fName2.append(merchantInfo.getBupLoad().substring(merchantInfo.getBupLoad().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getBupLoadFile(),fName2.toString(),imageDay);
				merchantInfoModel.setBupLoad(fName2.toString());
			}
			
			if(merchantInfo.getRupLoadFile() != null && merchantInfo.getRupLoad() != null){
				StringBuffer fName3 = new StringBuffer();
				fName3.append(user.getUnNo());
				fName3.append(".");
				fName3.append(merchantInfoModel.getMid());
				fName3.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName3.append(imageDay);
				fName3.append(".3");
				fName3.append(merchantInfo.getRupLoad().substring(merchantInfo.getRupLoad().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getRupLoadFile(),fName3.toString(),imageDay);
				merchantInfoModel.setRupLoad(fName3.toString());
			}
			
			if(merchantInfo.getRegistryUpLoadFile() != null && merchantInfo.getRegistryUpLoad() != null){
				StringBuffer fName4 = new StringBuffer();
				fName4.append(user.getUnNo());
				fName4.append(".");
				fName4.append(merchantInfoModel.getMid());
				fName4.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName4.append(imageDay);
				fName4.append(".4");
				fName4.append(merchantInfo.getRegistryUpLoad().substring(merchantInfo.getRegistryUpLoad().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getRegistryUpLoadFile(),fName4.toString(),imageDay);
				merchantInfoModel.setRegistryUpLoad(fName4.toString());
			}
			
			if(merchantInfo.getMaterialUpLoadFile() != null && merchantInfo.getMaterialUpLoad() != null){
				StringBuffer fName5 = new StringBuffer();
				fName5.append(user.getUnNo());
				fName5.append(".");
				fName5.append(merchantInfoModel.getMid());
				fName5.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName5.append(imageDay);
				fName5.append(".5");
				fName5.append(merchantInfo.getMaterialUpLoad().substring(merchantInfo.getMaterialUpLoad().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getMaterialUpLoadFile(),fName5.toString(),imageDay);
				merchantInfoModel.setMaterialUpLoad(fName5.toString());
			}
			
			if(merchantInfo.getPhotoUpLoadFile() != null && merchantInfo.getPhotoUpLoad() != null){
				StringBuffer fName6 = new StringBuffer();
				fName6.append(user.getUnNo());
				fName6.append(".");
				fName6.append(merchantInfoModel.getMid());
				fName6.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName6.append(imageDay);
				fName6.append(".6");
				fName6.append(merchantInfo.getPhotoUpLoad().substring(merchantInfo.getPhotoUpLoad().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getPhotoUpLoadFile(),fName6.toString(),imageDay);
				merchantInfoModel.setPhotoUpLoad(fName6.toString());
			}
			
			if(merchantInfo.getBigdealUpLoadFile() != null && merchantInfo.getBigdealUpLoad() != null){
				StringBuffer fName7 = new StringBuffer();
				fName7.append(user.getUnNo());
				fName7.append(".");
				fName7.append(merchantInfoModel.getMid());
				fName7.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName7.append(imageDay);
				fName7.append(".7");
				fName7.append(merchantInfo.getBigdealUpLoad().substring(merchantInfo.getBigdealUpLoad().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getBigdealUpLoadFile(),fName7.toString(),imageDay);
				merchantInfoModel.setBigdealUpLoad(fName7.toString());
			}
			
			if(merchantInfo.getMaterialUpLoad1File() != null && merchantInfo.getMaterialUpLoad1() != null){
				StringBuffer fName8 = new StringBuffer();
				fName8.append(user.getUnNo());
				fName8.append(".");
				fName8.append(merchantInfoModel.getMid());
				fName8.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName8.append(imageDay);
				fName8.append(".8");
				fName8.append(merchantInfo.getMaterialUpLoad1().substring(merchantInfo.getMaterialUpLoad1().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getMaterialUpLoad1File(),fName8.toString(),imageDay);
				merchantInfoModel.setMaterialUpLoad1(fName8.toString());
			}
			
			if(merchantInfo.getMaterialUpLoad2File() != null && merchantInfo.getMaterialUpLoad2() != null){
				StringBuffer fName9 = new StringBuffer();
				fName9.append(user.getUnNo());
				fName9.append(".");
				fName9.append(merchantInfoModel.getMid());
				fName9.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName9.append(imageDay);
				fName9.append(".9");
				fName9.append(merchantInfo.getMaterialUpLoad2().substring(merchantInfo.getMaterialUpLoad2().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getMaterialUpLoad2File(),fName9.toString(),imageDay);
				merchantInfoModel.setMaterialUpLoad2(fName9.toString());
			}
			
			if(merchantInfo.getMaterialUpLoad3File() != null && merchantInfo.getMaterialUpLoad3() != null){
				StringBuffer fNameA = new StringBuffer();
				fNameA.append(user.getUnNo());
				fNameA.append(".");
				fNameA.append(merchantInfoModel.getMid());
				fNameA.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fNameA.append(imageDay);
				fNameA.append(".A");
				fNameA.append(merchantInfo.getMaterialUpLoad3().substring(merchantInfo.getMaterialUpLoad3().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getMaterialUpLoad3File(),fNameA.toString(),imageDay);
				merchantInfoModel.setMaterialUpLoad3(fNameA.toString());
			}
			
			if(merchantInfo.getMaterialUpLoad4File() != null && merchantInfo.getMaterialUpLoad4() != null){
				StringBuffer fNameB = new StringBuffer();
				fNameB.append(user.getUnNo());
				fNameB.append(".");
				fNameB.append(merchantInfoModel.getMid());
				fNameB.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fNameB.append(imageDay);
				fNameB.append(".B");
				fNameB.append(merchantInfo.getMaterialUpLoad4().substring(merchantInfo.getMaterialUpLoad4().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getMaterialUpLoad4File(),fNameB.toString(),imageDay);
				merchantInfoModel.setMaterialUpLoad4(fNameB.toString());
			}
			
			if(merchantInfo.getMaterialUpLoad5File() != null && merchantInfo.getMaterialUpLoad5() != null){
				StringBuffer fNameC = new StringBuffer();
				fNameC.append(user.getUnNo());
				fNameC.append(".");
				fNameC.append(merchantInfoModel.getMid());
				fNameC.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fNameC.append(imageDay);
				fNameC.append(".C");
				fNameC.append(merchantInfo.getMaterialUpLoad5().substring(merchantInfo.getMaterialUpLoad5().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getMaterialUpLoad5File(),fNameC.toString(),imageDay);
				merchantInfoModel.setMaterialUpLoad5(fNameC.toString());
			}
			if(merchantInfo.getMaterialUpLoad6File() != null && merchantInfo.getMaterialUpLoad6() != null){
				StringBuffer fNameD = new StringBuffer();
				fNameD.append(user.getUnNo());
				fNameD.append(".");
				fNameD.append(merchantInfoModel.getMid());
				fNameD.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fNameD.append(imageDay);
				fNameD.append(".D");
				fNameD.append(merchantInfo.getMaterialUpLoad6().substring(merchantInfo.getMaterialUpLoad6().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getMaterialUpLoad6File(),fNameD.toString(),imageDay);
				merchantInfoModel.setMaterialUpLoad6(fNameD.toString());
			}
			if(merchantInfo.getMaterialUpLoad7File() != null && merchantInfo.getMaterialUpLoad7() != null){
				StringBuffer fNameE = new StringBuffer();
				fNameE.append(user.getUnNo());
				fNameE.append(".");
				fNameE.append(merchantInfoModel.getMid());
				fNameE.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fNameE.append(imageDay);
				fNameE.append(".E");
				fNameE.append(merchantInfo.getMaterialUpLoad7().substring(merchantInfo.getMaterialUpLoad7().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantInfo.getMaterialUpLoad7File(),fNameE.toString(),imageDay);
				merchantInfoModel.setMaterialUpLoad7(fNameE.toString());
			}
			
			//因为数据库中为decimal类型，所以如果为""则插入不进去，这里如果为""则改为null
			if(merchantInfo.getIsForeign() == 1){
				Double feeRateM = Double.parseDouble(merchantInfo.getFeeRateM())/100;
				merchantInfoModel.setFeeRateM(feeRateM.toString());
				
				Double feeRateV = Double.parseDouble(merchantInfo.getFeeRateV())/100;
				merchantInfoModel.setFeeRateV(feeRateV.toString());
				
				if(merchantInfo.getFeeRateA() == null || "".equals(merchantInfo.getFeeRateA().trim())){
					merchantInfoModel.setFeeRateA("0");
				}else{
					Double feeRateA = Double.parseDouble(merchantInfo.getFeeRateA())/100;
					merchantInfoModel.setFeeRateA(feeRateA.toString());
				}
				
				if(merchantInfo.getFeeRateD() == null || "".equals(merchantInfo.getFeeRateD().trim())){
					merchantInfoModel.setFeeRateD("0");
				}else{
					Double feeRateD = Double.parseDouble(merchantInfo.getFeeRateD())/100;
					merchantInfoModel.setFeeRateD(feeRateD.toString());
				}
				
				if(merchantInfo.getFeeRateJ() == null || "".equals(merchantInfo.getFeeRateJ().trim())){
					merchantInfoModel.setFeeRateJ("0");
				}else{
					Double feeRateJ = Double.parseDouble(merchantInfo.getFeeRateJ())/100;
					merchantInfoModel.setFeeRateJ(feeRateJ.toString());
				}
			}else{
				merchantInfoModel.setFeeRateA("0");
				merchantInfoModel.setFeeRateD("0");
				merchantInfoModel.setFeeRateJ("0");
				merchantInfoModel.setFeeRateM("0");
				merchantInfoModel.setFeeRateV("0");
			}
			
			AgentSalesModel agentSalesModel = agentSalesDao.getObjectByID(AgentSalesModel.class, merchantInfo.getBusid());
			merchantInfoModel.setUnno(agentSalesModel.getUnno());
//			if("W".equals(merchantInfo.getApproveStatus())){
//				merchantInfoModel.setApproveStatus("W");
//			}else 
			if ("Z".equals(merchantInfo.getApproveStatus())){
				merchantInfoModel.setApproveStatus("Z");
			}else{
				merchantInfoModel.setApproveStatus("W");
			}
			merchantInfoModel.setMaintainUid(user.getUserID());
			merchantInfoModel.setMaintainDate(new Date());
			merchantInfoModel.setMaintainUserId(merchantInfo.getBusid());		//添加的时候业务人员和维护人员一样
			merchantInfoModel.setMaintainType("M");
			merchantInfoModel.setSettleStatus("1");		//结算状态 1-正常 2-冻结
			merchantInfoModel.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销
			merchantInfoDao.updateObject(merchantInfoModel);	
			
			String updateSql="update bill_merchantbankcardinfo t set t.bankaccname='"+merchantInfoModel.getBankAccName()+"'," +
								"t.bankbranch='"+merchantInfoModel.getBankBranch()+"',t.paybankid='"+merchantInfoModel.getPayBankId()+"'," +
								"t.bankaccno='"+merchantInfoModel.getBankAccNo()+"' where t.mid='"+merchantInfoModel.getMid()+"' and t.mercardtype=0 ";
			merchantInfoDao.executeUpdate(updateSql);
			String updateSql2="update bill_merchantbankcardinfo t set t.bankaccname='"+merchantInfoModel.getBankAccName()+"' where t.mid='"+merchantInfoModel.getMid()+"' and t.mercardtype!=0 ";
			merchantInfoDao.executeUpdate(updateSql2);
			
			return true;
		}else{
			return false;
		}
	}
	@Override
	public DataGridBean searchTerminalInfo(String unno) throws Exception {
		String sql = "SELECT BTID,TERMID,decode(KEYTYPE,1,'短密钥','长密钥') KEYTYPENAME FROM BILL_TERMINALINFO t WHERE t.UNNO = '"+unno+"' AND t.ALLOTCONFIRMDATE IS NOT NULL AND t.USEDCONFIRMDATE IS NULL AND MAINTAINTYPE != 'D' and ism35 !=1 ORDER BY t.BTID DESC";

		List<Map<String,String>> proList = merchantInfoDao.executeSql(sql);
		
		List<Object>  list = new ArrayList<Object>();
		for (int i = 0; i < proList.size(); i++) {
			Map map = proList.get(i);
			list.add(map);
		}
		DataGridBean dgd = new DataGridBean();
		dgd.setTotal(proList.size());
		dgd.setRows(list);
		
		return dgd;
	}
	/**
	 * 方法功能：格式化MerchantInfoModel为datagrid数据格式
	 * 参数：merchantInfoList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<MerchantInfoModel> merchantInfoList, long total) {
		List<MerchantInfoBean> merchantInfoBeanList = new ArrayList<MerchantInfoBean>();
		for(MerchantInfoModel merchantInfoModel : merchantInfoList) {
			MerchantInfoBean merchantInfoBean = new MerchantInfoBean();
			BeanUtils.copyProperties(merchantInfoModel, merchantInfoBean);
			//二维码链接
			if(merchantInfoModel.getQrUrl()!=null&&"".equals(merchantInfoModel.getQrUrl())){
				merchantInfoBean.setQrUrl(merchantInfoModel.getQrUrl());
			}
			//二维码地址
			if(merchantInfoModel.getQrUpload()!=null&&"".equals(merchantInfoModel.getQrUpload())){
				merchantInfoBean.setQrUpload(merchantInfoModel.getQrUpload());
			}
			//联系电话
			if(merchantInfoModel.getContactPhone()!=null&&"".equals(merchantInfoModel.getContactPhone())){
				merchantInfoBean.setContactPhone(merchantInfoModel.getContactPhone());
			}
			//入网时间
			if(merchantInfoModel.getLegalPerson()!=null&&!"".equals(merchantInfoModel.getLegalPerson())){
				merchantInfoBean.setLegalPerson(merchantInfoModel.getLegalPerson());
			}
			//法人
			if(merchantInfoModel.getLegalPerson()!=null&&!"".equals(merchantInfoModel.getLegalPerson())){
				merchantInfoBean.setLegalPerson(merchantInfoModel.getLegalPerson());
			}
			//终端数量
			merchantInfoBean.setTidCount(tidCount(merchantInfoModel.getMid()));
			//终端集合
			merchantInfoBean.setTidList(tidList(merchantInfoModel.getMid()));
			//审批状态
			if("Z".equals(merchantInfoBean.getApproveStatus())){
				merchantInfoBean.setApproveStatusName("待添加终端");
			}else if("Y".equals(merchantInfoBean.getApproveStatus())){
				merchantInfoBean.setApproveStatusName("审批通过");
			}else if("W".equals(merchantInfoBean.getApproveStatus())){
				merchantInfoBean.setApproveStatusName("待审批");
			}else if("C".equals(merchantInfoBean.getApproveStatus())){
				merchantInfoBean.setApproveStatusName("审核中");
			}
			else{
				merchantInfoBean.setApproveStatusName("退回");
			}
			//行业编码名称
			if(merchantInfoBean.getMccid() != null && !"".equals(merchantInfoBean.getMccid().trim())){
				if(mccidName(merchantInfoBean.getMccid()) != null && !"".equals(mccidName(merchantInfoBean.getMccid()).trim())){
					merchantInfoBean.setMccidName(mccidName(merchantInfoBean.getMccid()));
				}
			}
			
			//上级商户名称
			if(merchantInfoBean.getParentMID() != null && !"".equals(merchantInfoBean.getParentMID().trim())){
				if(parentMIDName(merchantInfoModel.getParentMID()) != null && !"".equals(parentMIDName(merchantInfoModel.getParentMID()).trim())){
					merchantInfoBean.setParentMIDName(parentMIDName(merchantInfoModel.getParentMID()));
				}
			}
			
			//商户所在地区码名称
			if(merchantInfoBean.getAreaCode() != null && !"".equals(merchantInfoBean.getAreaCode().trim())){
				if(areaCodeName(merchantInfoModel.getAreaCode()) != null  && !"".equals(areaCodeName(merchantInfoModel.getAreaCode()).trim())){
					merchantInfoBean.setAreaCodeName(areaCodeName(merchantInfoModel.getAreaCode()));
				}
			}
			
			
			//法人证件类型
			if("1".equals(merchantInfoBean.getLegalType())){
				merchantInfoBean.setLegalTypeName("身份证");
			}else if("2".equals(merchantInfoBean.getLegalType())){
				merchantInfoBean.setLegalTypeName("军官证");
			}else if("3".equals(merchantInfoBean.getLegalType())){
				merchantInfoBean.setLegalTypeName("护照");
			}else if("4".equals(merchantInfoBean.getLegalType())){
				merchantInfoBean.setLegalTypeName("港澳通行证");
			}else if("5".equals(merchantInfoBean.getLegalType())){
				merchantInfoBean.setLegalTypeName("其他");
			}
			
			//开户类型
			if("1".equals(merchantInfoBean.getAccType())){
				merchantInfoBean.setAccTypeName("对公");
			}else{
				merchantInfoBean.setAccTypeName("对私 ");
			}
			
			//是否大小额商户
			if(merchantInfoBean.getMerchantType()==1){
				merchantInfoBean.setMerchantTypeName("小额商户");
			}else{
				merchantInfoBean.setMerchantTypeName("大额商户");
			}
			
			//节假日是否合并结账
			if("0".equals(merchantInfoBean.getSettleMerger())){
				merchantInfoBean.setSettleMergerName("单天结账");
			}else{
				merchantInfoBean.setSettleMergerName("合并结账");
			}
			
			//银联卡费率
			BigDecimal number = new BigDecimal(100);
			BigDecimal bankFeeRate = new BigDecimal(merchantInfoBean.getBankFeeRate());
			Double bfr= bankFeeRate.multiply(number).doubleValue();
			merchantInfoBean.setBankFeeRate(bfr.toString());
			//贷记卡费率
			if(merchantInfoBean.getCreditBankRate()!=null&&!"".equals(merchantInfoBean.getCreditBankRate())){
				BigDecimal creditBankRate = new BigDecimal(merchantInfoBean.getCreditBankRate());
				Double bfr1= creditBankRate.multiply(number).doubleValue();
				merchantInfoBean.setCreditBankRate(bfr1.toString());
			}
			
			if(merchantInfoBean.getFeeRateV()!=null && !"".equals(merchantInfoBean.getFeeRateV().trim()) && !"0".equals(merchantInfoBean.getFeeRateV().trim())){
				BigDecimal feeRateV = new BigDecimal(merchantInfoBean.getFeeRateV());
				Double frv = feeRateV.multiply(number).doubleValue();
				merchantInfoBean.setFeeRateV(frv.toString());
			}
			if(merchantInfoBean.getFeeRateM()!=null && !"".equals(merchantInfoBean.getFeeRateM().trim()) && !"0".equals(merchantInfoBean.getFeeRateM().trim())){
				BigDecimal feeRateM = new BigDecimal(merchantInfoBean.getFeeRateM());
				Double frm = feeRateM.multiply(number).doubleValue();
				merchantInfoBean.setFeeRateM(frm.toString());
			}
			if(merchantInfoBean.getFeeRateJ()!=null && !"".equals(merchantInfoBean.getFeeRateJ().trim()) && !"0".equals(merchantInfoBean.getFeeRateJ().trim())){
				BigDecimal feeRateJ = new BigDecimal(merchantInfoBean.getFeeRateJ());
				Double frj = feeRateJ.multiply(number).doubleValue();
				merchantInfoBean.setFeeRateJ(frj.toString());
			}
			if(merchantInfoBean.getFeeRateA()!=null && !"".equals(merchantInfoBean.getFeeRateA().trim()) && !"0".equals(merchantInfoBean.getFeeRateA().trim())){
				BigDecimal feeRateA = new BigDecimal(merchantInfoBean.getFeeRateA());
				Double fra = feeRateA.multiply(number).doubleValue();
				merchantInfoBean.setFeeRateA(fra.toString());
			}
			if(merchantInfoBean.getFeeRateD()!=null && !"".equals(merchantInfoBean.getFeeRateD().trim()) && !"0".equals(merchantInfoBean.getFeeRateD().trim())){
				BigDecimal feeRateD = new BigDecimal(merchantInfoBean.getFeeRateD());
				Double frd = feeRateD.multiply(number).doubleValue();
				merchantInfoBean.setFeeRateD(frd.toString());
			}
			
			merchantInfoBeanList.add(merchantInfoBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(merchantInfoBeanList);
		
		return dgb;
	}
	
	/**
	 * 查询是否是代理商报单员
	 * @param user
	 * @return
	 */
	private boolean isAgentformMan(UserBean user){
		String sql = "select user_id from sys_user_role s where s.role_id = 43 and s.user_id = '"+user.getUserID()+"'";
		List list = merchantInfoDao.queryObjectsBySqlList(sql);
		if(list.size() > 0){
			return true;
		}
		return false;
	}
	/**
	 * 根据受理人员查询USER_ID
	 */
	private String getUserID(String approveUidName){
		String sql="SELECT USER_ID FROM SYS_USER WHERE user_name ='"+approveUidName+"'";
		List list=merchantInfoDao.queryObjectsBySqlList(sql);
		String userID="";
		if(list.size()>0){
				Map<String, Object> map=(Map<String, Object>) list.get(0);
				userID=map.get("USER_ID").toString();	
		}else{
			userID="-1";
		}
		return userID;
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
		String title="HrtFrameWork";
		return merchantInfoDao.querySavePath(title);
	}
	
	/**
	 * 终端数量
	 */
	private Long tidCount(String mid){
		String hql = "select count(*) from MerchantTerminalInfoModel m where m.approveStatus = 'Y' and m.maintainType != 'D' and m.mid = :mid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", mid);
		Long tidCount = merchantTerminalInfoDao.queryCounts(hql,map);
		return tidCount;
	}
	
	/**
	 * 终端list
	 */
	private List<String> tidList(String mid){
		String hql = "select m from MerchantTerminalInfoModel m where m.approveStatus = 'Y' and m.maintainType != 'D' and m.mid = :mid";
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> tidList = new ArrayList<String>();
		map.put("mid", mid);
		List<MerchantTerminalInfoModel> list =  merchantTerminalInfoDao.queryObjectsByHqlList(hql, map);
		for(MerchantTerminalInfoModel m:list){
			tidList.add(m.getTid());
		}
		return tidList;
	}
	/**
	 * 上级商户名称
	 */
	private String parentMIDName(String mid){
		String hql = "from MerchantInfoModel m where m.mid = '" + mid + "'";
		List<MerchantInfoModel> list = merchantInfoDao.queryObjectsByHqlList(hql);
		if(list != null && list.size() > 0){
			return list.get(0).getRname();
		}
		return null;	
	}
	
	/**
	 * 商户所在地区码名称
	 */
	private String areaCodeName(String areaCode){
		String sql = "SELECT AREA_NAME FROM SYS_AREA A WHERE A.AREA_CODE = '" + areaCode + "'";
		List<Map<String, String>> s = merchantInfoDao.queryObjectsBySqlList(sql);
		if(s != null && s.size() > 0){
			return s.get(0).get("AREA_NAME");
		}
		return null;
	}
	
	/**
	 * 业务人员名称
	 */
	private String busidName(Integer busid){
		String sql = "SELECT SALENAME FROM BILL_AGENTSALESINFO A WHERE A.BUSID = " + busid;
		List<Map<String, String>> s = agentSalesDao.queryObjectsBySqlList(sql);
		if(s != null && s.size() > 0){
			return s.get(0).get("SALENAME");
		}
		return null;
	}
	
	/**
	 * 行业选择名称
	 */
	private String industryTypeName(String industryType){
		String sql = "SELECT INAME FROM BILL_INDUSTRYINFO WHERE INDUSTRYTYPE = '" + industryType + "'";
		List<Map<String, String>> s = merchantInfoDao.queryObjectsBySqlList(sql);
		if(s != null && s.size() > 0){
			return s.get(0).get("INAME");
		}
		return null;
	}
	
	/**
	 * 行业编码名称
	 */
	private String mccidName(String mccid){
		String sql = "SELECT MCCNAME FROM BILL_MM_L_MCC WHERE MCCCODE = '" + mccid + "'";
		List<Map<String, String>> s = merchantInfoDao.queryObjectsBySqlList(sql);
		if(s != null && s.size() > 0){
			return s.get(0).get("MCCNAME");
		}
		return null;
	}
}
