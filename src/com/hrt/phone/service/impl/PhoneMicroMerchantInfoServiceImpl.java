package com.hrt.phone.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.hrt.frame.constant.Constant;
import com.hrt.frame.constant.PhoneProdConstant;
import com.hrt.util.DateTools;
import com.hrt.util.ParamPropUtils;
import com.hrt.util.PictureWaterMarkUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.argorse.security.core.hash.MD5Wrapper;
import com.hrt.biz.bill.dao.IAggPayTerminfoDao;
import com.hrt.biz.bill.dao.IMIDSeqInfoDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.ITerminalInfoDao;
import com.hrt.biz.bill.entity.model.AggPayTerminfoModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.biz.bill.entity.model.TerminalInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.frame.dao.sysadmin.IRoleDao;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.RoleModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.gmms.webservice.MerchantInfo;
import com.hrt.gmms.webservice.TermAcc;
import com.hrt.phone.dao.IPhoneMicroMerchantInfoDao;
import com.hrt.phone.entity.model.MerchantBankCardModel;
import com.hrt.phone.service.IPhoneMicroMerchantInfoService;
import com.hrt.redis.JedisSource;
import com.hrt.redis.RedisUtil;
import com.hrt.util.ClassToJavaBeanUtil;
import com.hrt.util.HttpXmlClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import sun.misc.BASE64Decoder;

public class PhoneMicroMerchantInfoServiceImpl implements
		IPhoneMicroMerchantInfoService {

	private IPhoneMicroMerchantInfoDao phoneMicroMerchantInfoDao;
	private IMIDSeqInfoDao midSeqInfoDao;
	private IRoleDao roleDao;
	private IUnitDao unitDao;
	private IUserDao userDao;
	private IMerchantInfoDao merchantInfoDao;
	private ITodoDao todoDao;
	private IMerchantInfoService merchantInfoService;
	private String hybUrl;
	private String loginHybUrl;
	private String AggPayMerHybUrl;
	private String AggPayTerHybUrl;
	private String admAppIp;
	private IGmmsService gsClient;
	private IAggPayTerminfoDao aggPayTerminfoDao;
	private String jhTaskHybUrl;
	private ITerminalInfoDao terminalInfoDao;
	private static final Log log = LogFactory.getLog(PhoneMicroMerchantInfoServiceImpl.class);
	
	public ITerminalInfoDao getTerminalInfoDao() {
		return terminalInfoDao;
	}
	public void setTerminalInfoDao(ITerminalInfoDao terminalInfoDao) {
		this.terminalInfoDao = terminalInfoDao;
	}
	public String getJhTaskHybUrl() {
		return jhTaskHybUrl;
	}
	public void setJhTaskHybUrl(String jhTaskHybUrl) {
		this.jhTaskHybUrl = jhTaskHybUrl;
	}
	public String getAdmAppIp() {
		return admAppIp;
	}
	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}
	public String getAggPayMerHybUrl() {
		return AggPayMerHybUrl;
	}
	public void setAggPayMerHybUrl(String aggPayMerHybUrl) {
		AggPayMerHybUrl = aggPayMerHybUrl;
	}
	public String getAggPayTerHybUrl() {
		return AggPayTerHybUrl;
	}
	public void setAggPayTerHybUrl(String aggPayTerHybUrl) {
		AggPayTerHybUrl = aggPayTerHybUrl;
	}
	public IAggPayTerminfoDao getAggPayTerminfoDao() {
		return aggPayTerminfoDao;
	}
	public void setAggPayTerminfoDao(IAggPayTerminfoDao aggPayTerminfoDao) {
		this.aggPayTerminfoDao = aggPayTerminfoDao;
	}
	public String getLoginHybUrl() {
		return loginHybUrl;
	}
	public void setLoginHybUrl(String loginHybUrl) {
		this.loginHybUrl = loginHybUrl;
	}
	public IGmmsService getGsClient() {
		return gsClient;
	}
	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
	}
	public IPhoneMicroMerchantInfoDao getPhoneMicroMerchantInfoDao() {
		return phoneMicroMerchantInfoDao;
	}
	public void setPhoneMicroMerchantInfoDao(
			IPhoneMicroMerchantInfoDao phoneMicroMerchantInfoDao) {
		this.phoneMicroMerchantInfoDao = phoneMicroMerchantInfoDao;
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
	
	
	@Override
	public boolean findHrtSnInfo(String sn) {
		String sql2="select a.sn from bill_terminalinfo a where a.maintaintype!='D' and a.sn='"+sn+"' and nvl(a.DEPOSITAMT,0)=0";
		Object isSn =phoneMicroMerchantInfoDao.queryObjectBySql(sql2, null);
		if(isSn !=null && !"".equals(isSn)){
			return true;
		}
		return false;
	}
	/**
	 * 查询SN是否存在(增机)
	 * @param sn
	 * @return  boolean
	 */
	@Override
	public Integer findHrtSnInfoT(String sn ,String unno) {
		String sql2="select a.unno from bill_terminalinfo a,sys_unit u where a.maintaintype!='D' and a.sn='"+sn+"' and u.unno=a.unno and u.status=1 and a.status=1 "; //and a.sntype is null ";
//		if(!"000000".equals(unno)){
//			sql2+=" and a.unno='"+unno+"' ";
//		}
		Object unno1 =phoneMicroMerchantInfoDao.queryObjectBySql(sql2, null);
		if(unno1==null||"".equals(unno1)){
			return 6;
		}else if("000000".equals(unno)||unno1.equals(unno)){
			return 4;
		}else{
			return 5;
		}
	}
	
	/**
	 * 判断设备上的机构号是不是字母开头并且商户所属是数字开头并且不是000000
	 * flag true-判断设备上的机构号是不是字母开头并且商户所属是数字开头并且不是000000
	 * flag false-判断sn是否被使用，未被使用转移到字母机构下
	 */
	@Override
	public boolean updateUnnoForTer(String sn, String unno, boolean flag) {
		TerminalInfoModel model = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where sn=? and maintainType!='D'", new Object[] {sn});
		if(model!=null) {
			if(flag) {//判断设备上的机构号是不是字母开头并且商户所属是数字开头并且不是000000
				String terUnno = model.getUnNO();
				if(("a".equals(terUnno.substring(0, 1))||"b".equals(terUnno.substring(0, 1))||
					"c".equals(terUnno.substring(0, 1))||"d".equals(terUnno.substring(0, 1))||
					"e".equals(terUnno.substring(0, 1))||"f".equals(terUnno.substring(0, 1))||
					"g".equals(terUnno.substring(0, 1))||"h".equals(terUnno.substring(0, 1))||
					"i".equals(terUnno.substring(0, 1))||"j".equals(terUnno.substring(0, 1))||
					"k".equals(terUnno.substring(0, 1)))&&!"a".equals(unno.substring(0, 1))&&
					!"b".equals(unno.substring(0, 1))&&!"c".equals(unno.substring(0, 1))&&
					!"d".equals(unno.substring(0, 1))&&!"e".equals(unno.substring(0, 1))&&
					!"f".equals(unno.substring(0, 1))&&!"g".equals(unno.substring(0, 1))&&
					!"h".equals(unno.substring(0, 1))&&!"i".equals(unno.substring(0, 1))&&
					!"j".equals(unno.substring(0, 1))&&!"k".equals(unno.substring(0, 1))&&!"000000".equals(unno)) {
					if(terUnno.equals(formatUnno(unno))) {
						model.setUnNO(unno);
						terminalInfoDao.updateObject(model);
						return false;//标识修改过机构号，如果之后没有绑定成功，则修改为原来的
					}
				}
			}else {//如果传上来的设备还是未绑定，修改为字母开头的
				if(model.getStatus()==1) {
					model.setUnNO(formatUnno(model.getUnNO()));
					terminalInfoDao.updateObject(model);
				}
				return true;
			}
		}
		return true;
	}
	
	private String formatUnno(String unno) {
//		'0' -> 'a'
//		'1' -> 'b'
//		'2' -> 'c'
//		'3' -> 'd'
//		'4' -> 'e'
//		'5' -> 'f'
//		'6' -> 'g'
//		'7' -> 'h'
//		'8' -> 'i'
//		'9' -> 'j'
//		'A' -> 'k'
		if("0".equals(unno.substring(0, 1))) {
			return "a"+unno.substring(1, unno.length());
		}else if("1".equals(unno.substring(0, 1))) {
			return "b"+unno.substring(1, unno.length());
		}else if("2".equals(unno.substring(0, 1))) {
			return "c"+unno.substring(1, unno.length());
		}else if("3".equals(unno.substring(0, 1))) {
			return "d"+unno.substring(1, unno.length());
		}else if("4".equals(unno.substring(0, 1))) {
			return "e"+unno.substring(1, unno.length());
		}else if("5".equals(unno.substring(0, 1))) {
			return "f"+unno.substring(1, unno.length());
		}else if("6".equals(unno.substring(0, 1))) {
			return "g"+unno.substring(1, unno.length());
		}else if("7".equals(unno.substring(0, 1))) {
			return "h"+unno.substring(1, unno.length());
		}else if("8".equals(unno.substring(0, 1))) {
			return "i"+unno.substring(1, unno.length());
		}else if("9".equals(unno.substring(0, 1))) {
			return "j"+unno.substring(1, unno.length());
		}else if("A".equals(unno.substring(0, 1))) {
			return "k"+unno.substring(1, unno.length());
		}
		return "";
	}
	
	/*
	 * 判断SN所属Pose是否已被占用
	 */
	@Override
	public Integer findHrtSnIsUsing(String sn,String mid1) {
		String sql="select t.mid  from bill_terminalinfo a,BILL_MERCHANTTERMINALINFO t,bill_merchantinfo m  where a.termid=t.tid and t.maintaintype!='D' " +
				" and m.mid=t.mid and m.isM35='1' and t.approvestatus!='K' and a.sn='"+sn+"'";
		Object mid =phoneMicroMerchantInfoDao.queryObjectBySql(sql, null);
		if(mid==null || "".equals(mid)){
			return 1;
		}else if(mid1!=null&&mid1.equals(mid)){
			return 2;
		}else{
			return 3;
		}
	}
	/* 
	 * 判断设备是不是优享，如果是则APP必须支持 isNewApp
	 */
	@Override
	public boolean findHrtAppIsNew(MerchantInfoBean merchantInfoBean) {
		String sql = "from TerminalInfoModel where sn='"+merchantInfoBean.getSn()+"' and maintainType !='D'";
		TerminalInfoModel model = terminalInfoDao.queryObjectByHql(sql, new Object[]{});
		if(null!=model && model.getRebateType()!=null&&(model.getRebateType()==14||model.getRebateType()==15)){//优享设备
			if(merchantInfoBean.getIsNewApp()!=null&&merchantInfoBean.getIsNewApp()==1){
				return true;//支持
			}else{
				return false;//不支持
			}
		}
		return true;
	}
	/*
	 * 判断Qrid是否已被占用
	 */
	@Override
	public Integer updateHrtQridIsUsing(String Qrid,String mid1,String sign) {
		String sql="select t.* from BILL_aggpayterminfo t,sys_unit u where t.QRTID='"+Qrid+"' and u.unno=t.unno and u.status=1 ";
		List<Map<String,Object>> list=phoneMicroMerchantInfoDao.queryObjectsBySqlList(sql);
		MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{mid1});
		//5不存在；0未使用；4停用；1待审核；2审核通过；3退回；6占用 ;7机构归属不一致 ;8 非法链接
		if(list==null||list.size()==0||m==null){
			return 5;
		}else{
			String UNNO = list.get(0).get("UNNO").toString();
			String STATUS = list.get(0).get("STATUS").toString();
			String MID = list.get(0).get("MID")+"";
			String QRPWD = list.get(0).get("QRPWD").toString();
			String QRURL = list.get(0).get("QRURL").toString();
			if(!"000000".equals(m.getUnno())&&!UNNO.equals(m.getUnno())){
				return 7;
			}else if("0".equals(STATUS)){
				if(sign!=null&&!"".equals(sign)&&sign.contains("sn-")){
					//非接设备不验签
					return saveNonConnection(mid1,sign)==true?0:3;
				}else{
					if(QRURL.contains("sign")){
							try {
								if(sign!=null&&!"".equals(sign)&&sign.equals(MD5Wrapper.encryptMD5ToString(QRPWD).replace("+", ""))){
									return 0;
								}else{
									log.info("聚合商户增机申请-判断Qrid规则-校验不一致:前端传入="+sign+";本地="+QRURL);
									return 8;
								}
							} catch (Exception e) {
								e.printStackTrace();
								log.error("聚合商户增机申请-判断Qrid规则-校验sign异常", e);
								return 8;
							}
					}else{
						return 0;
					}
				}
			}else if("4".equals(STATUS)){
				return 4;
			}else{
				if("".equals(mid1)||mid1==null){
					return 6;
				}else if(mid1.equals(MID)){
					return Integer.parseInt(STATUS);
				}else{
					return 6;
				}
			}
		}
		
	}
	@Override
	public List<Map<String, Object>> saveHrtMerchantInfo(
			MerchantInfoBean merchantInfoBean) throws Exception {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(new HashMap<String, Object>());
		String rate="";
		String secondRate="";
		String scanRate="";
		if(merchantInfoBean.getSn()!=null&&!"".equals(merchantInfoBean.getSn())){
			String sql="select t.unno as UNNO ,t.termid as TID , t.sn as SN,t.rate||'' as RATE,t.secondRate||'' as SECONDRATE,t.scanRate ||'' as SCANRATE from bill_terminalinfo t where t.sn='"+merchantInfoBean.getSn()+"'";
			List<Map<String,Object>> list1=phoneMicroMerchantInfoDao.queryObjectsBySqlList(sql);
			String unno=(String) list1.get(0).get("UNNO");
			String tid=(String) list1.get(0).get("TID");
			String sn=(String) list1.get(0).get("SN");
			rate=(String) list1.get(0).get("RATE");
			secondRate=(String) list1.get(0).get("SECONDRATE");
			scanRate=(String) list1.get(0).get("SCANRATE");
			merchantInfoBean.setUnno(unno);
			merchantInfoBean.setTid(tid);
			merchantInfoBean.setSn(sn);
			list=list1;
		}else{
			merchantInfoBean.setUnno("000000");
		}
		MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
		BeanUtils.copyProperties(merchantInfoBean, merchantInfoModel);
		if(merchantInfoBean.isWeChatProg()){
		    // 微信端请求上来的四个图片,copy后处理为空
			merchantInfoModel.setMaterialUpLoad4(null);
			merchantInfoModel.setMaterialUpLoad5(null);
			merchantInfoModel.setMaterialUpLoad6(null);
			merchantInfoModel.setMaterialUpLoad7(null);
		}
		//根据此字段来区分微商户与传统商户：0 传统商户        1   微商户
		merchantInfoModel.setIsM35(1);
		//对公，对私
		merchantInfoModel.setAccType("2");
		merchantInfoModel.setSettleStatus("1");
		merchantInfoModel.setAreaType("1");
		//账户状态
		merchantInfoModel.setAccountStatus("1");
		if(merchantInfoBean.getSn()!=null&&!"".equals(merchantInfoBean.getSn())){
			//审核状态---W待审
			merchantInfoModel.setApproveStatus("W");
		}else{
			merchantInfoModel.setApproveStatus("Z");
		}
		//操作时间
		merchantInfoModel.setMaintainDate(new Date());
		//入网时间
		merchantInfoModel.setJoinConfirmDate(new Date());
		//节假日是否合并结账 0：否 1：是
		merchantInfoModel.setSettleMerger("1");
		
		merchantInfoModel.setIsForeign(2);
		//经营范围
		merchantInfoModel.setBusinessScope("全行业");
		//区域码
		merchantInfoModel.setAreaCode("010");
		//业务人员
		merchantInfoModel.setBusid(1);
		merchantInfoModel.setMaintainUserId(1);
		//证件类型
		merchantInfoModel.setLegalType("1");
		//行业编码
		merchantInfoModel.setMccid("5311");
		//结算周期
		merchantInfoModel.setSettleCycle(0);
		//结算时间点
		merchantInfoModel.setSettleRange("00");
		merchantInfoModel.setMaintainType("A");
		StringBuffer mid = new StringBuffer();
		//判断是大额还是秒到
		if(merchantInfoBean.getAgentId()!=null&&PhoneProdConstant.PHONE_SYT.equals(merchantInfoBean.getAgentId())) {
			//大额扫码
			String mid1 = merchantInfoService.saveSYTMerchantMid();
			if(mid1==null){
				throw new Exception("收银台商户号生成失败");
			}
			mid.append(mid1);
		}else if(merchantInfoBean.getAgentId()!=null&&PhoneProdConstant.PHONE_PLUS.equals(merchantInfoBean.getAgentId())){
			String mid1 = merchantInfoService.savePlusMerchantMid();
			if(mid1==null){
				throw new Exception("PLUS商户号生成失败");
			}
			mid.append(mid1);
		}else if(merchantInfoBean.getAgentId()!=null&& PhoneProdConstant.PHONE_PRO.equals(merchantInfoBean.getAgentId())){
			String mid1 = merchantInfoService.savePROMerchantMid();
			if(mid1==null){
				throw new Exception("PRO商户号生成失败");
			}
			mid.append(mid1);
		}else {
			//商户编号生成秒到
			mid.append("864");
			String mrcioRule=phoneMicroMerchantInfoDao.querySavePath("MicroRule");
			mid.append(mrcioRule.trim());
			mid.append("5311");
			//synchronized (MerchantInfoServiceImpl.class){
			Integer seqNo = merchantInfoService.searchMIDSeqInfo("000","5311","xxx");
			DecimalFormat deFormat = new DecimalFormat("0000");
			mid.append(deFormat.format(seqNo));
		}
		merchantInfoModel.setMid(mid.toString());
		
		if("000000".equals(merchantInfoBean.getSettMethod())||merchantInfoBean.getSettMethod()==null ||"".equals(merchantInfoBean.getSettMethod())){
			//是否大小额
			merchantInfoModel.setMerchantType(2);
			//封顶手续费
			merchantInfoModel.setFeeAmt("35");
			//封顶值
			merchantInfoModel.setDealAmt("5385");
			//费率
			merchantInfoModel.setBankFeeRate("0.0065");
			merchantInfoModel.setCreditBankRate("0.0065");
			merchantInfoModel.setSettMethod("000000");
		}else{
			//是否大小额
			merchantInfoModel.setMerchantType(1);
			//封顶手续费
			merchantInfoModel.setFeeAmt("0");
			//封顶值
			merchantInfoModel.setDealAmt("0");
			//费率
			merchantInfoModel.setBankFeeRate("0.0072");
			merchantInfoModel.setCreditBankRate("0.0072");
		}
		//rate
		if(rate!=null&&!"".equals(rate)){
			merchantInfoModel.setBankFeeRate(rate);
			merchantInfoModel.setCreditBankRate(rate);
		}
		//scanRate
		if(scanRate!=null&&!"".equals(scanRate)){
			merchantInfoBean.setScanRate(Double.valueOf(scanRate));
		}
		//秒到手续费
		if(secondRate!=null&&!"".equals(secondRate)){
			merchantInfoModel.setSecondRate(secondRate);
		}
//		QRToImageWriter writer= new QRToImageWriter();
//		merchantInfoModel =writer.doQR(merchantInfoModel);
		//10位随机数
//		String sign="";
//		for(int i=0;i<10;i++){
//			sign=sign+String.valueOf((char)(Math.random()*26+65));
//		}
//		String content = "http://xpay.hrtpayment.com/xpay/qrpayment?mid="+merchantInfoModel.getMid()+"&sign="+sign;
//		merchantInfoModel.setQrUrl(content);
//		merchantInfoModel.setQrUpload("0");
		//merchantInfoModel.setCreditBankRate("0");
		merchantInfoModel.setBankAccName(merchantInfoBean.getBankAccName().trim());
		merchantInfoModel.setBankAccNo(merchantInfoBean.getBankAccNo());
		merchantInfoModel.setBankBranch(merchantInfoBean.getBankBranch());
		merchantInfoModel.setLegalPerson(merchantInfoBean.getBankAccName().trim());
		merchantInfoModel.setAccNum(merchantInfoBean.getLegalNum());
		merchantInfoModel.setContactPerson(merchantInfoBean.getBankAccName());
		merchantInfoModel.setRname(merchantInfoBean.getBankAccName().trim());
		merchantInfoModel.setBaddr(merchantInfoBean.getRaddr());
		merchantInfoModel.setContactAddress(merchantInfoBean.getRaddr());
//		if(merchantInfoBean.getBno()==null || "".equals(merchantInfoBean.getBno())){
//			merchantInfoModel.setBno(" ");
//		}
		//将agentId 10000-秒到 10002-连刷放入bno
		merchantInfoModel.setBno(merchantInfoBean.getAgentId());

		if(merchantInfoBean.isWeChatProg()) {
		    // 微信端图片默认名称设置
			setMerchantInfoImgByWeChatImg(merchantInfoModel, merchantInfoBean);
		}
		//}
		//上传文件
		if(merchantInfoBean.getLegalUploadFile() != null ){
			StringBuffer fName1 = new StringBuffer();
//			fName1.append(merchantInfoModel.getUnno());
//			fName1.append(".");
			fName1.append(merchantInfoModel.getMid());
			fName1.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(".jpg");
//			uploadFile(merchantInfoBean.getLegalUploadFile(),fName1.toString(),imageDay);
			merchantInfoModel.setLegalUploadFileName(fName1.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoadFile() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad1File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".3");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad1File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad1(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad2File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".4");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad2File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad2(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad3File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".5");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad3File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad3(fName2.toString());
		}
		
		if(merchantInfoBean.getBupLoadFile() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".6");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getBupLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setBupLoad(fName2.toString());
		}
		if(merchantInfoBean.getMaterialUpLoad5File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".7");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad5(fName2.toString());
		}
		if(merchantInfoBean.getRegistryUpLoadFile() != null ){
			StringBuffer fName2 = new StringBuffer();
			fName2.append(merchantInfoModel.getUnno());
			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".8");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getBupLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setRegistryUpLoad(fName2.toString());
		}
		if(merchantInfoBean.getMaterialUpLoad4File() != null ){
			StringBuffer fName2 = new StringBuffer();
			fName2.append(merchantInfoModel.getUnno());
			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".9");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad3File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad4(fName2.toString());
		}
		if(merchantInfoBean.getSn()!=null&&!"".equals(merchantInfoBean.getSn())){
			//终端商户关联表
			MerchantTerminalInfoModel mtim = new MerchantTerminalInfoModel();
			mtim.setMid(merchantInfoModel.getMid());
			mtim.setSn(merchantInfoBean.getSn());
			mtim.setApproveDate(new Date());
			mtim.setApproveStatus("Z");
	//		mtim.setApproveStatus("C");
			mtim.setMaintainType("A");		//A-add   M-Modify  D-Delete
			mtim.setMaintainDate(new Date());
			mtim.setStatus(1);
			mtim.setTid(merchantInfoBean.getTid());
			mtim.setUnno(merchantInfoModel.getUnno());
			mtim.setBmaid(queryBmaid(merchantInfoBean.getMachineModel()));
			mtim.setInstalledAddress(merchantInfoBean.getRaddr());
			
			phoneMicroMerchantInfoDao.saveObject(mtim);
			
			//更新时间
			String updateSql="update BILL_TERMINALINFO t set t.usedconfirmdate=sysdate ,t.maintaindate=sysdate,t.status=2 where t.sn='"+merchantInfoBean.getSn()+"'";
			phoneMicroMerchantInfoDao.executeUpdate(updateSql);
		}
		phoneMicroMerchantInfoDao.saveObject(merchantInfoModel);
		
		MerchantBankCardModel mbc = new MerchantBankCardModel();
		mbc.setBankAccName(merchantInfoModel.getBankAccName());
		mbc.setBankAccNo(merchantInfoModel.getBankAccNo());
		mbc.setCreateDate(new Date());
		mbc.setMerCardType(0);
		mbc.setStatus(0);
		mbc.setMid(merchantInfoModel.getMid());
		mbc.setBankBranch(merchantInfoModel.getBankBranch());
		mbc.setPayBankId(merchantInfoModel.getPayBankId());
		phoneMicroMerchantInfoDao.saveObject(mbc);
		
		list.get(0).put("MID",merchantInfoModel.getMid());
		list.get(0).put("UNNO","000000");
		list.get(0).put("rname",merchantInfoModel.getRname());
		list.get(0).put("merchantInfoModel",merchantInfoModel);
		return list;
	}

	@Override
	public List<Map<String, Object>> saveHrtMerchantInfoDGL(MerchantInfoBean merchantInfoBean) throws Exception {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(new HashMap<String, Object>());
		log.info("德古拉报单请求：unno="+merchantInfoBean.getUnno()+";hybPhone="+merchantInfoBean.getHybPhone()+";rname="+merchantInfoBean.getRname()+
				";rname="+merchantInfoBean.getLegalNum()+";legalPerson="+merchantInfoBean.getLegalPerson());
		MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
		BeanUtils.copyProperties(merchantInfoBean, merchantInfoModel);
		//根据此字段来区分微商户与传统商户：0 传统商户        1   微商户
		merchantInfoModel.setIsM35(1);
		//对公，对私
		merchantInfoModel.setAccType("2");
		merchantInfoModel.setSettleStatus("1");
		merchantInfoModel.setAreaType("1");
		//账户状态
		merchantInfoModel.setAccountStatus("1");
		merchantInfoModel.setApproveStatus("Z");
		//操作时间
		merchantInfoModel.setMaintainDate(new Date());
		//入网时间
		merchantInfoModel.setJoinConfirmDate(new Date());
		//节假日是否合并结账 0：否 1：是
		merchantInfoModel.setSettleMerger("1");
		
		merchantInfoModel.setIsForeign(2);
		//经营范围
		merchantInfoModel.setBusinessScope("全行业");
		//区域码
		merchantInfoModel.setAreaCode("010");
		//业务人员
		merchantInfoModel.setBusid(1);
		merchantInfoModel.setMaintainUserId(1);
		//证件类型
		merchantInfoModel.setLegalType("1");
		//行业编码
		merchantInfoModel.setMccid("5311");
		//结算周期
		merchantInfoModel.setSettleCycle(0);
		//结算时间点
		merchantInfoModel.setSettleRange("00");
		merchantInfoModel.setMaintainType("A");
		//商户编号生成
		StringBuffer mid = new StringBuffer("6");
		mid.append(merchantInfoBean.getHybPhone().substring(1)).append("1108");
		merchantInfoModel.setMid(mid.toString());
		
		//是否大小额
		merchantInfoModel.setMerchantType(1);
		//封顶手续费
		merchantInfoModel.setFeeAmt("0");
		//封顶值
		merchantInfoModel.setDealAmt("0");
		//费率
		merchantInfoModel.setBankFeeRate("0.0072");
		merchantInfoModel.setCreditBankRate("0.0072");
		merchantInfoModel.setSettMethod("100000");
		merchantInfoModel.setScanRate(0.0038d);
		merchantInfoModel.setSecondRate("2");
		merchantInfoModel.setBankAccName(merchantInfoBean.getRname().trim());
		merchantInfoModel.setBankAccNo(" ");
		merchantInfoModel.setBankBranch(" ");
		merchantInfoModel.setAccNum(merchantInfoBean.getLegalNum());
		merchantInfoModel.setContactPerson(merchantInfoBean.getRname().trim());
		merchantInfoModel.setBaddr("无");
		merchantInfoModel.setRaddr("无");
		merchantInfoModel.setContactAddress("无");
		if(merchantInfoBean.getBno()==null || "".equals(merchantInfoBean.getBno())){
			merchantInfoModel.setBno(" ");
		}
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//上传文件
		if(merchantInfoBean.getLegalUploadFile() != null || merchantInfoBean.getLegalUploadFileName()!=null){
			StringBuffer fName1 = new StringBuffer();
//			fName1.append(merchantInfoModel.getUnno());
//			fName1.append(".");
			fName1.append(merchantInfoModel.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(".jpg");
//			uploadFile(merchantInfoBean.getLegalUploadFile(),fName1.toString(),imageDay);
			merchantInfoModel.setLegalUploadFileName(fName1.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoadFile() != null || merchantInfoBean.getMaterialUpLoad() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad1File() != null || merchantInfoBean.getMaterialUpLoad1() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".3");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad1File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad1(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad2File() != null || merchantInfoBean.getMaterialUpLoad2() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".4");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad2File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad2(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad3File() != null || merchantInfoBean.getMaterialUpLoad3() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".5");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad3File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad3(fName2.toString());
		}
		
		if(merchantInfoBean.getBupLoadFile() != null || merchantInfoBean.getBupLoad() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".6");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getBupLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setBupLoad(fName2.toString());
		}
		//收银台照片
		if(merchantInfoBean.getMaterialUpLoad5File() != null || merchantInfoBean.getMaterialUpLoad5() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".7");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad5(fName2.toString());
		}
		//拓展人与法人合影照片
		if(merchantInfoBean.getMaterialUpLoad6File() != null || merchantInfoBean.getMaterialUpLoad6() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".12");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad6(fName2.toString());
		}
		//店面门头照
		if(merchantInfoBean.getPhotoUpLoadFile() != null || merchantInfoBean.getPhotoUpLoad() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".8");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setPhotoUpLoad(fName2.toString());
		}	
		if(merchantInfoBean.getMaterialUpLoad4File() != null || merchantInfoBean.getMaterialUpLoad4() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".9");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad4(fName2.toString());
		}
		//店内经营：registryUpLoadFile
		if(merchantInfoBean.getRegistryUpLoadFile() != null || merchantInfoBean.getRegistryUpLoad() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".10");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setRegistryUpLoad(fName2.toString());
		}
		//对公开户证明（对公必填）：rupLoadFile
		if(merchantInfoBean.getRupLoadFile()!= null || merchantInfoBean.getRupLoad()!= null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".11");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setRupLoad(fName2.toString());
		}
		phoneMicroMerchantInfoDao.saveObject(merchantInfoModel);
		
		MerchantBankCardModel mbc = new MerchantBankCardModel();
		mbc.setBankAccName(merchantInfoModel.getBankAccName());
		mbc.setBankAccNo(merchantInfoModel.getBankAccNo());
		mbc.setCreateDate(new Date());
		mbc.setMerCardType(0);
		mbc.setStatus(0);
		mbc.setMid(merchantInfoModel.getMid());
		mbc.setBankBranch(merchantInfoModel.getBankBranch());
		mbc.setPayBankId(merchantInfoModel.getPayBankId());
		phoneMicroMerchantInfoDao.saveObject(mbc);
		
		list.get(0).put("MID",merchantInfoModel.getMid());
		list.get(0).put("UNNO","000000");
		list.get(0).put("rname",merchantInfoModel.getRname());
		list.get(0).put("merchantInfoModel",merchantInfoModel);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> saveAggPayMerchantInfo(MerchantInfoBean merchantInfoBean) throws Exception {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(new HashMap<String, Object>());
		merchantInfoBean.setUnno("000000");
		MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
		BeanUtils.copyProperties(merchantInfoBean, merchantInfoModel);
		merchantInfoModel.setIsM35(6);
		merchantInfoModel.setSettleStatus("1");
		//商户类型：4企业；5商户；6个人AreaType
		//账户状态
		merchantInfoModel.setAccountStatus("1");
		merchantInfoModel.setApproveStatus("Z");
		//操作时间
		merchantInfoModel.setMaintainDate(new Date());
		//入网时间
		merchantInfoModel.setJoinConfirmDate(new Date());
		//节假日是否合并结账 0：否 1：是
		merchantInfoModel.setSettleMerger("1");
		//经营范围
//		merchantInfoModel.setBusinessScope("全行业");
		merchantInfoModel.setMccid("5311");
		//区域码
		merchantInfoModel.setAreaCode("010");
		//业务人员
		merchantInfoModel.setBusid(1);
		merchantInfoModel.setMaintainUserId(1);
		//证件类型
		merchantInfoModel.setLegalType("1");
		//结算周期
		merchantInfoModel.setSettleCycle(1);
		//结算时间点
		merchantInfoModel.setSettleRange("00");
		merchantInfoModel.setMaintainType("A");
		//是否开通储值卡  0，是，1 否 (默认)
		if(merchantInfoBean.getIsForeign()!=null&&merchantInfoBean.getIsForeign()==0){
			merchantInfoModel.setIsForeign(0);
		}else{
			merchantInfoModel.setIsForeign(1);
		}
		if("000000".equals(merchantInfoBean.getSettMethod())||merchantInfoBean.getSettMethod()==null ||"".equals(merchantInfoBean.getSettMethod())){
			//是否大小额
			merchantInfoModel.setMerchantType(2);
			//封顶手续费
			merchantInfoModel.setFeeAmt("35");
			//封顶值
			merchantInfoModel.setDealAmt("5385");
			//费率
			merchantInfoModel.setBankFeeRate("0.0065");
			merchantInfoModel.setCreditBankRate("0.0065");
			merchantInfoModel.setSettMethod("000000");
		}else{
			//是否大小额
			merchantInfoModel.setMerchantType(1);
			//封顶手续费
			merchantInfoModel.setFeeAmt("0");
			//封顶值
			merchantInfoModel.setDealAmt("0");
			//费率
			merchantInfoModel.setBankFeeRate("0.0072");
			merchantInfoModel.setCreditBankRate("0.0072");
		}
		
		if("".equals(merchantInfoModel.getContactPerson())){
			merchantInfoModel.setContactPerson(merchantInfoBean.getBankAccName());
		}
		if("".equals(merchantInfoModel.getLegalPerson())){
			merchantInfoModel.setLegalPerson(merchantInfoBean.getBankAccName().trim());
		}
		if("".equals(merchantInfoModel.getAccNum())){
			merchantInfoModel.setAccNum(merchantInfoBean.getLegalNum());
		}
		if("".equals(merchantInfoBean.getContactPhone())){
			merchantInfoBean.setContactPhone(merchantInfoBean.getHybPhone());
		}
		if("".equals(merchantInfoBean.getRaddr())){
			merchantInfoModel.setRaddr(merchantInfoBean.getBaddr());
		}
		if(merchantInfoBean.getShortName()==null || "".equals(merchantInfoBean.getShortName())){
			merchantInfoModel.setShortName(merchantInfoBean.getRname());
		}
		merchantInfoModel.setContactAddress(merchantInfoBean.getBaddr());
		if(merchantInfoBean.getBno()==null || "".equals(merchantInfoBean.getBno())){
			merchantInfoModel.setBno(" ");
		}
		//商户编号生成
		String mid = merchantInfoService.saveAggPayMerchantMid();
		if(mid==null){
			return null;
		}
		merchantInfoModel.setMid(mid);
		log.info("查询待入库聚合商户mid:"+mid+";rame:"+merchantInfoModel.getRname());
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//上传文件
		if(merchantInfoBean.getLegalUploadFile() != null || merchantInfoBean.getLegalUploadFileName()!=null){
			StringBuffer fName1 = new StringBuffer();
//			fName1.append(merchantInfoModel.getUnno());
//			fName1.append(".");
			fName1.append(merchantInfoModel.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(".jpg");
//			uploadFile(merchantInfoBean.getLegalUploadFile(),fName1.toString(),imageDay);
			merchantInfoModel.setLegalUploadFileName(fName1.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoadFile() != null || merchantInfoBean.getMaterialUpLoad() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad1File() != null || merchantInfoBean.getMaterialUpLoad1() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".3");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad1File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad1(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad2File() != null || merchantInfoBean.getMaterialUpLoad2() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".4");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad2File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad2(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad3File() != null || merchantInfoBean.getMaterialUpLoad3() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".5");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad3File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad3(fName2.toString());
		}
		
		if(merchantInfoBean.getBupLoadFile() != null || merchantInfoBean.getBupLoad() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".6");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getBupLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setBupLoad(fName2.toString());
		}
		//收银台照片
		if(merchantInfoBean.getMaterialUpLoad5File() != null || merchantInfoBean.getMaterialUpLoad5() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".7");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad5(fName2.toString());
		}
		//拓展人与法人合影照片
		if(merchantInfoBean.getMaterialUpLoad6File() != null || merchantInfoBean.getMaterialUpLoad6() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".12");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad6(fName2.toString());
		}
		//店面门头照
		if(merchantInfoBean.getPhotoUpLoadFile() != null || merchantInfoBean.getPhotoUpLoad() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".8");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setPhotoUpLoad(fName2.toString());
		}	
		if(merchantInfoBean.getMaterialUpLoad4File() != null || merchantInfoBean.getMaterialUpLoad4() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".9");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad4(fName2.toString());
		}
		//店内经营：registryUpLoadFile
		if(merchantInfoBean.getRegistryUpLoadFile() != null || merchantInfoBean.getRegistryUpLoad() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".10");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setRegistryUpLoad(fName2.toString());
		}
		//对公开户证明（对公必填）：rupLoadFile
		if(merchantInfoBean.getRupLoadFile()!= null || merchantInfoBean.getRupLoad()!= null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".11");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			merchantInfoModel.setRupLoad(fName2.toString());
		}
		//减免
		if(merchantInfoBean.getMaterialUpLoad7File() != null || merchantInfoBean.getMaterialUpLoad7() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".E");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad7File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad7(fName2.toString());
		}
		if(merchantInfoBean.getQrtid()!=null&&!"".equals(merchantInfoBean.getQrtid())){
			//终端商户关联表
			AggPayTerminfoModel terminfoModel = aggPayTerminfoDao.queryObjectByHql("from AggPayTerminfoModel t where t.qrtid=?", new Object[]{merchantInfoBean.getQrtid()});
			merchantInfoModel.setUnno(terminfoModel.getUnno());
			merchantInfoModel.setApproveStatus("W");
			merchantInfoModel.setScanRate(terminfoModel.getScanRate());
			terminfoModel.setMid(mid);
			terminfoModel.setShopname(merchantInfoBean.getShopname());
			terminfoModel.setStatus(2);
			terminfoModel.setUsedConfirmDate(new Date());
		}
		phoneMicroMerchantInfoDao.saveObject(merchantInfoModel);
		
//		MerchantBankCardModel mbc = new MerchantBankCardModel();
//		mbc.setBankAccName(merchantInfoModel.getBankAccName());
//		mbc.setBankAccNo(merchantInfoModel.getBankAccNo());
//		mbc.setCreateDate(new Date());
//		mbc.setMerCardType(0);
//		mbc.setStatus(0);
//		mbc.setMid(merchantInfoModel.getMid());
//		mbc.setBankBranch(merchantInfoModel.getBankBranch());
//		mbc.setPayBankId(merchantInfoModel.getPayBankId());
//		phoneMicroMerchantInfoDao.saveObject(mbc);
		
		list.get(0).put("MID",merchantInfoModel.getMid());
		list.get(0).put("UNNO","000000");
		list.get(0).put("rname",merchantInfoModel.getRname());
		list.get(0).put("merchantInfoModel",merchantInfoModel);
		return list;
	}
	
	public void hrtMerchantToHYB(MerchantInfoModel merchantInfoModel,MerchantInfoBean merchantInfoBean){
		try{
			//推送会员宝 手机号&商户号关联关系
			Map<String,String> params = new HashMap<String, String>();
			params.put("loginName",merchantInfoModel.getHybPhone());
			params.put("mid",merchantInfoModel.getMid());
			params.put("agentId", merchantInfoBean.getAgentId());		//0: 和融通系统M35报单 久玖为1，托付为2
			params.put("status", "insert");
			params.put("rname", merchantInfoModel.getRname());
			params.put("terminateSn", "");
			params.put("tid", "");
			String json = JSON.toJSONString(params);
			log.info("推送会员宝 手机号&商户号关联 "+json);
			String ss=HttpXmlClient.postForJson(loginHybUrl, json);
		}catch (Exception e) {
			log.info("推送会员宝 手机号&商户号关联异常 e:"+e);
		}
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		if(merchantInfoBean.isWeChatProg()){
		    // 微信端保存图片信息
			uploadWeChatImg(merchantInfoModel,merchantInfoBean,imageDay);
		}else {
			//上传文件
			if (merchantInfoBean.getLegalUploadFile() != null) {
				uploadFile(merchantInfoBean.getLegalUploadFile(), merchantInfoModel.getLegalUploadFileName(), imageDay);
			}
			if (merchantInfoBean.getMaterialUpLoadFile() != null) {
				uploadFile(merchantInfoBean.getMaterialUpLoadFile(), merchantInfoModel.getMaterialUpLoad(), imageDay);
			}
			if (merchantInfoBean.getMaterialUpLoad1File() != null) {
				uploadFile(merchantInfoBean.getMaterialUpLoad1File(), merchantInfoModel.getMaterialUpLoad1(), imageDay);
			}
			if (merchantInfoBean.getMaterialUpLoad2File() != null) {
				uploadFile(merchantInfoBean.getMaterialUpLoad2File(), merchantInfoModel.getMaterialUpLoad2(), imageDay);
			}
			if (merchantInfoBean.getMaterialUpLoad3File() != null) {
				uploadFile(merchantInfoBean.getMaterialUpLoad3File(), merchantInfoModel.getMaterialUpLoad3(), imageDay);
			}
			if (merchantInfoBean.getMaterialUpLoad4File() != null) {
				uploadFile(merchantInfoBean.getMaterialUpLoad4File(), merchantInfoModel.getMaterialUpLoad4(), imageDay);
			}
			if (merchantInfoBean.getBupLoadFile() != null) {
				uploadFile(merchantInfoBean.getBupLoadFile(), merchantInfoModel.getBupLoad(), imageDay);
			}
			if (merchantInfoBean.getMaterialUpLoad5File() != null) {
				uploadFile(merchantInfoBean.getMaterialUpLoad5File(), merchantInfoModel.getMaterialUpLoad5(), imageDay);
			}
			if (merchantInfoBean.getPhotoUpLoadFile() != null) {
				uploadFile(merchantInfoBean.getPhotoUpLoadFile(), merchantInfoModel.getPhotoUpLoad(), imageDay);
			}
			if (merchantInfoBean.getRegistryUpLoadFile() != null) {
				uploadFile(merchantInfoBean.getRegistryUpLoadFile(), merchantInfoModel.getRegistryUpLoad(), imageDay);
			}
			if (merchantInfoBean.getRupLoadFile() != null) {
				uploadFile(merchantInfoBean.getRupLoadFile(), merchantInfoModel.getRupLoad(), imageDay);
			}
		}
	}

	/**
	 * 微信小程序上传时保存
	 * @param merchantInfoModel
	 * @param merchantInfoBean
	 */
	private void setMerchantInfoImgByWeChatImg(MerchantInfoModel merchantInfoModel,MerchantInfoBean merchantInfoBean){
		if(merchantInfoBean.getMaterialUpLoad4() != null ){
			StringBuffer fName1 = new StringBuffer();
//			fName1.append(merchantInfoModel.getUnno());
//			fName1.append(".");
			fName1.append(merchantInfoModel.getMid());
			fName1.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(".jpg");
//			uploadFile(merchantInfoBean.getLegalUploadFile(),fName1.toString(),imageDay);
			merchantInfoModel.setLegalUploadFileName(fName1.toString());
		}

		if(merchantInfoBean.getMaterialUpLoad5() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad(fName2.toString());
		}

		if(merchantInfoBean.getMaterialUpLoad6() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".3");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad1File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad1(fName2.toString());
		}

		if(merchantInfoBean.getMaterialUpLoad7() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(merchantInfoModel.getUnno());
//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".4");
			fName2.append(".jpg");
//			uploadFile(merchantInfoBean.getMaterialUpLoad2File(),fName2.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad2(fName2.toString());
		}
	}

	/**
	 * 微信上传的转码串保存为图片
	 * @param merchantInfoModel
	 * @param merchantInfoBean
	 * @param imageDay
	 */
	private void uploadWeChatImg(MerchantInfoModel merchantInfoModel,MerchantInfoBean merchantInfoBean,String imageDay){
		if(merchantInfoBean.getMaterialUpLoad4() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad4(),merchantInfoModel.getLegalUploadFileName(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad5() != null){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad5(),merchantInfoModel.getMaterialUpLoad(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad6() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad6(),merchantInfoModel.getMaterialUpLoad1(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad7() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad7(),merchantInfoModel.getMaterialUpLoad2(),imageDay);
		}
	}

	public void hrtMerchantToDGL(MerchantInfoModel merchantInfoModel,MerchantInfoBean merchantInfoBean){
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//上传文件
		if(merchantInfoBean.getLegalUploadFileName() != null ){
			uploadFile2byte(merchantInfoBean.getLegalUploadFileName(),merchantInfoModel.getLegalUploadFileName(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad() != null){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad(),merchantInfoModel.getMaterialUpLoad(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad1() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad1(),merchantInfoModel.getMaterialUpLoad1(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad2() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad2(),merchantInfoModel.getMaterialUpLoad2(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad3() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad3(),merchantInfoModel.getMaterialUpLoad3(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad4() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad4(),merchantInfoModel.getMaterialUpLoad4(),imageDay);
		}
		if(merchantInfoBean.getBupLoad() != null ){
			uploadFile2byte(merchantInfoBean.getBupLoad(),merchantInfoModel.getBupLoad(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad5() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad5(),merchantInfoModel.getMaterialUpLoad5(),imageDay);
		}
		if(merchantInfoBean.getMaterialUpLoad6() != null ){
			uploadFile2byte(merchantInfoBean.getMaterialUpLoad6(),merchantInfoModel.getMaterialUpLoad6(),imageDay);
		}
		if(merchantInfoBean.getPhotoUpLoad() != null ){
			uploadFile2byte(merchantInfoBean.getPhotoUpLoad(),merchantInfoModel.getPhotoUpLoad(),imageDay);
		}
		if(merchantInfoBean.getRegistryUpLoad() != null ){
			uploadFile2byte(merchantInfoBean.getRegistryUpLoad(),merchantInfoModel.getRegistryUpLoad(),imageDay);
		}
		if(merchantInfoBean.getRupLoad() != null ){
			uploadFile2byte(merchantInfoBean.getRupLoad(),merchantInfoModel.getRupLoad(),imageDay);
		}
	}
	
	public void hrtAggPayMerchantToHYB(MerchantInfoModel merchantInfoModel,MerchantInfoBean merchantInfoBean){
		try{
			//推送会员宝 手机号&商户号关联关系
			Map<String,String> params = new HashMap<String, String>();
			params.put("phone",merchantInfoModel.getHybPhone());//登录手机号
			params.put("contacter_name",merchantInfoModel.getContactPerson());//联系人
			params.put("contacter_phone", merchantInfoBean.getContactPhone());//联系人手机	
			params.put("jh_mid", merchantInfoModel.getMid());//商户mid
			params.put("org_no", merchantInfoModel.getUnno());//商户机构号
			params.put("business_license_name", merchantInfoModel.getShortName());//营业执照经营名称
			params.put("business_name", merchantInfoModel.getRname());//经营名称
			params.put("business_license_no", merchantInfoModel.getBno());//营业执照号
			params.put("business_address", merchantInfoModel.getBaddr());//经营地址
			params.put("legal_person", merchantInfoModel.getLegalPerson());//法人名称 经营者名称
			params.put("legal_id_num", merchantInfoModel.getLegalNum());//法人身份证
			params.put("acc_type", merchantInfoModel.getAccType());//账户类型，  1-对公    2-对私
			params.put("bank_acc_name", merchantInfoModel.getBankAccName());//账户名称
			params.put("bank_branch", merchantInfoModel.getBankBranch());//开户行+开户分支行
			params.put("bank_acc_no", merchantInfoModel.getBankAccNo());//结算账号
			params.put("pay_bank_id", merchantInfoModel.getPayBankId());//支付系统行号
			params.put("isForeign", merchantInfoModel.getIsForeign()+"");//是否开通储值卡  0，是，1 否 (默认)
			params.put("agentId", merchantInfoBean.getAgentId());//agentid
			params.put("city", merchantInfoBean.getQX_name());//市
			params.put("areaType", merchantInfoBean.getAreaType());//商户类型：4企业；5商户；6个人AreaType
			String json = JSON.toJSONString(params);
			log.info("推送会员宝 手机号&聚合商户号关联 "+json);
			String ss=HttpXmlClient.postForJson(AggPayMerHybUrl, json);
		}catch (Exception e) {
			log.info("推送会员宝 手机号&聚合商户号关联异常 e:"+e);
		}
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//上传文件
		if("h5".equals(merchantInfoBean.getRemarks())){
			if(merchantInfoBean.getLegalUploadFileName() != null ){
				uploadFile2byte(merchantInfoBean.getLegalUploadFileName(),merchantInfoModel.getLegalUploadFileName(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad() != null){
				uploadFile2byte(merchantInfoBean.getMaterialUpLoad(),merchantInfoModel.getMaterialUpLoad(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad1() != null ){
				uploadFile2byte(merchantInfoBean.getMaterialUpLoad1(),merchantInfoModel.getMaterialUpLoad1(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad2() != null ){
				uploadFile2byte(merchantInfoBean.getMaterialUpLoad2(),merchantInfoModel.getMaterialUpLoad2(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad3() != null ){
				uploadFile2byte(merchantInfoBean.getMaterialUpLoad3(),merchantInfoModel.getMaterialUpLoad3(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad4() != null ){
				uploadFile2byte(merchantInfoBean.getMaterialUpLoad4(),merchantInfoModel.getMaterialUpLoad4(),imageDay);
			}
			if(merchantInfoBean.getBupLoad() != null ){
				uploadFile2byte(merchantInfoBean.getBupLoad(),merchantInfoModel.getBupLoad(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad5() != null ){
				uploadFile2byte(merchantInfoBean.getMaterialUpLoad5(),merchantInfoModel.getMaterialUpLoad5(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad6() != null ){
				uploadFile2byte(merchantInfoBean.getMaterialUpLoad6(),merchantInfoModel.getMaterialUpLoad6(),imageDay);
			}
			if(merchantInfoBean.getPhotoUpLoad() != null ){
				uploadFile2byte(merchantInfoBean.getPhotoUpLoad(),merchantInfoModel.getPhotoUpLoad(),imageDay);
			}
			if(merchantInfoBean.getRegistryUpLoad() != null ){
				uploadFile2byte(merchantInfoBean.getRegistryUpLoad(),merchantInfoModel.getRegistryUpLoad(),imageDay);
			}
			if(merchantInfoBean.getRupLoad() != null ){
				uploadFile2byte(merchantInfoBean.getRupLoad(),merchantInfoModel.getRupLoad(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad7File() != null){
				uploadFile2byte(merchantInfoBean.getMaterialUpLoad7(),merchantInfoBean.getMaterialUpLoad7(),imageDay);
			}
		}else{
			if(merchantInfoBean.getLegalUploadFile() != null ){
				uploadFile(merchantInfoBean.getLegalUploadFile(),merchantInfoModel.getLegalUploadFileName(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoadFile() != null){
				uploadFile(merchantInfoBean.getMaterialUpLoadFile(),merchantInfoModel.getMaterialUpLoad(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad1File() != null ){
				uploadFile(merchantInfoBean.getMaterialUpLoad1File(),merchantInfoModel.getMaterialUpLoad1(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad2File() != null ){
				uploadFile(merchantInfoBean.getMaterialUpLoad2File(),merchantInfoModel.getMaterialUpLoad2(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad3File() != null ){
				uploadFile(merchantInfoBean.getMaterialUpLoad3File(),merchantInfoModel.getMaterialUpLoad3(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad4File() != null ){
				uploadFile(merchantInfoBean.getMaterialUpLoad4File(),merchantInfoModel.getMaterialUpLoad4(),imageDay);
			}
			if(merchantInfoBean.getBupLoadFile() != null ){
				uploadFile(merchantInfoBean.getBupLoadFile(),merchantInfoModel.getBupLoad(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad5File() != null ){
				uploadFile(merchantInfoBean.getMaterialUpLoad5File(),merchantInfoModel.getMaterialUpLoad5(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad6File() != null ){
				uploadFile(merchantInfoBean.getMaterialUpLoad6File(),merchantInfoModel.getMaterialUpLoad6(),imageDay);
			}
			if(merchantInfoBean.getPhotoUpLoadFile() != null ){
				uploadFile(merchantInfoBean.getPhotoUpLoadFile(),merchantInfoModel.getPhotoUpLoad(),imageDay);
			}
			if(merchantInfoBean.getRegistryUpLoadFile() != null ){
				uploadFile(merchantInfoBean.getRegistryUpLoadFile(),merchantInfoModel.getRegistryUpLoad(),imageDay);
			}
			if(merchantInfoBean.getRupLoadFile() != null ){
				uploadFile(merchantInfoBean.getRupLoadFile(),merchantInfoModel.getRupLoad(),imageDay);
			}
			if(merchantInfoBean.getMaterialUpLoad7File() != null){
				uploadFile(merchantInfoBean.getMaterialUpLoad7File(),merchantInfoModel.getMaterialUpLoad7(),imageDay);
			}
		}
	}
	
	@Override
	public void saveHrtMerToADMDB(MerchantInfoBean merchantInfoBean,List<Map<String, Object>> list) throws Exception {
		
		//绑定邀请码之前终端号
		String unno=(String) list.get(0).get("UNNO");
		String tid=(String) list.get(0).get("TID");
		String mid=(String) list.get(0).get("MID");
		String sn=(String) list.get(0).get("SN");
		MerchantInfoModel merchantInfoModel =(MerchantInfoModel) list.get(0).get("merchantInfoModel");
		if("000000".equals(unno)){
			try{
				if(merchantInfoBean.getAgentId()!=null && !"".equals(merchantInfoBean.getAgentId())){
					Map<String,String> params = new HashMap<String, String>();
					params.put("userName",merchantInfoModel.getHybPhone());
					params.put("mid",merchantInfoModel.getMid());
					params.put("tid", tid);
					params.put("sn", sn);
					params.put("agentId", merchantInfoBean.getAgentId());		//0: 和融通系统M35报单 久玖为1，托付为2
					params.put("mark", "0");	//0：待挂终端增机   1：增机申请增机
					params.put("settMethod", merchantInfoModel.getSettMethod());
					params.put("rname", merchantInfoModel.getRname());
					String ss=HttpXmlClient.post(hybUrl, params);
				}
			}catch (Exception e) {
				log.info("手机报单推送会员宝异常"+e);
			}finally{
				//调用webservice，对GMMS添加开通商户信息
				String tenance=merchantInfoModel.getUnno();
				String sales="xxx";
				String sign="";
				for(int i=0;i<10;i++){
					sign=sign+String.valueOf((char)(Math.random()*26+65));
				}
				if(merchantInfoModel.getScanRate()==null||"".equals(merchantInfoModel.getScanRate())){
					merchantInfoModel.setScanRate(0.0038);
				}
				merchantInfoModel.setQrUrl(ParamPropUtils.props.get("xpayPath")+"/qrpayment?mid="+merchantInfoModel.getMid()+"&sign="+sign);
				merchantInfoModel.setQrUpload("0");
				MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(merchantInfoModel, "SYS", tenance, sales);
				
				Boolean falg = false ;
				try{
					String firstSql="select k.rebatetype from    bill_terminalinfo k where k.termid in ( " +
							" select t.tid from    bill_merchantterminalinfo t where  t.maintaintype!='D' and t.mid='"+merchantInfoModel.getMid()+"' " +
							") and k.rebatetype is not null and rownum=1 order by k.usedconfirmdate, k.btid ";
					List<Map<String,String>> firstRebateType =unitDao.queryObjectsBySqlListMap(firstSql,null);
					if(firstRebateType.size()==1){
						info.setRebateType(firstRebateType.get(0).get("REBATETYPE")+"");
					}
					info.setUnno(merchantInfoService.getProvince(merchantInfoModel.getUnno()));
					falg=gsClient.saveMerchantInfo(info,"hrtREX1234.Java");
				}catch(Exception e ){
					log.error("调用webservice失败：" + e);
				}
				if(!falg){
					throw new IllegalAccessError("调用webservice失败");
				}
				Integer typeFlag;
				if("000000".equals(merchantInfoBean.getSettMethod())){
					typeFlag=2;
				}else{
					typeFlag=3;
				}
				String terminalHql="from TerminalInfoModel t where t.termID='"+tid+"'";
				TerminalInfoModel t=terminalInfoDao.queryObjectByHql(terminalHql, new Object[]{});
				TermAcc acc=new TermAcc();
				acc.setRebateType(t.getRebateType());//返利类型
				acc.setKeyConfirmDate(new SimpleDateFormat("yyyyMMdd").format(t.getKeyConfirmDate()));//入网时间
				acc.setOutDate(t.getOutDate());
				acc.setSnsigndate(t.getAcceptDate());
				acc.setSnType(t.getSnType()==null?"":t.getSnType().toString());//蓝牙类型
				if(t!=null && t.getRebateType()!=null){
					Map configInfo = merchantInfoService.getSelfTermConfig(t.getRebateType(),t.getDepositAmt());
					if(configInfo!=null) {
						if (configInfo.containsKey("depositamt")) {
							acc.setDepositAmt(Integer.parseInt(configInfo.get("depositamt").toString()));
						}
						if (configInfo.containsKey("tradeamt")) {
                            acc.setFirmoney(Double.parseDouble(configInfo.get("tradeamt").toString()));
						}
						if (configInfo.containsKey("adm_deposit_flag")) {
							acc.setDepositFlag(Integer.parseInt(configInfo.get("adm_deposit_flag").toString()));
						}
					}
				}

				acc.setIsReturnCash(t.getIsReturnCash());
				acc.setCby("SYS");
				acc.setModleId(merchantInfoBean.getMachineModel());
				acc.setMid(mid);
				acc.setSn(sn);
				acc.setUnno(info.getUnno());
				acc.setTid(tid);
				acc.setTypeflag(typeFlag);
				if(merchantInfoModel.getBankFeeRate()!=null&&!"".equals(merchantInfoModel.getBankFeeRate())&&merchantInfoModel.getSecondRate()!=null&&!"".equals(merchantInfoModel.getSecondRate())){
					acc.setRate(Double.valueOf(merchantInfoModel.getBankFeeRate()));
					acc.setSecondRate(Double.valueOf(merchantInfoModel.getSecondRate()));
				}
				Boolean falg2 = true ;
				if(falg==true&&sn!=null&&!"".equals(sn)){
					try{
						falg2=gsClient.saveTermAccSub(acc,"hrtREX1234.Java");
					}catch(Exception e ){
						log.error("调用webservice失败：" + e);
					}
					if(!falg2){
						throw new IllegalAccessError("调用webservice失败");
						//log.error("调用webservice失败：");
					}
				}
				if(falg2==true&&falg==true){
					//审核状态---W待审核 C待审核通过 Y审核通过 
					//Y-放行   Z-待挂终端 K-踢回 C-审批中  W待审核
					merchantInfoModel.setApproveStatus("Y");
					merchantInfoModel.setApproveDate(new Date());
					merchantInfoModel.setJoinConfirmDate(new Date());	//入网时间
					merchantInfoModel.setCheckConfirmDate(new Date());
					
					System.out.println(merchantInfoModel.getBmid());
					merchantInfoDao.updateObject(merchantInfoModel);
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(sn!=null){
						String hql2 = "";
						hql2 = "update MerchantTerminalInfoModel m set m.approveStatus='Y' " +
						" ,m.approveDate=to_date('"+df.format(new Date())+"','yyyy-MM-dd hh24:mi:ss') " +
						" where m.mid='"+merchantInfoModel.getMid()+"' and m.tid='"+tid+"'";
						phoneMicroMerchantInfoDao.executeHql(hql2);
					}
					
					UserModel userModel = new UserModel();
					userModel.setCreateDate(new Date());
					userModel.setUseLvl(3);
					userModel.setIsLogin(0);
					userModel.setCreateUser("sys");
					userModel.setUserName(merchantInfoModel.getRname());
					userModel.setLoginName(merchantInfoModel.getMid());	//账号默认mid
					String pass = merchantInfoModel.getMid().substring(merchantInfoModel.getMid().length()-6,merchantInfoModel.getMid().length());
					userModel.setPassword(MD5Wrapper.encryptMD5ToString(pass));		//默认密码mid后六位
					UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, merchantInfoModel.getUnno());
					Set<UnitModel> units = new HashSet<UnitModel>();
					units.add(unitModel);
					userModel.setUnits(units);
					String hql="from RoleModel r where r.roleLevel = 4 and r.roleAttr = 1";
					List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql);
					Set<RoleModel> roles = new HashSet<RoleModel>();
					for (RoleModel role : roleList) {
						roles.add(role);
					}
					userModel.setRoles(roles);
					userModel.setResetFlag(1);
					userModel.setStatus(0);
					userDao.saveObject(userModel);
				}
			}
		}
	}
	@Override
	public void saveAggMerToADMDB(MerchantInfoBean merchantInfoBean,List<Map<String, Object>> list) throws Exception {
		
		//绑定终端之前终端号
		MerchantInfoModel merchantInfoModel =(MerchantInfoModel) list.get(0).get("merchantInfoModel");
		String yidai = merchantInfoService.getProvince(merchantInfoModel.getUnno());
		try{
			if(merchantInfoModel.getHybPhone()!=null && !"".equals(merchantInfoModel.getHybPhone())){
				Map<String,String> params = new HashMap<String, String>();
				params.put("phone",merchantInfoModel.getHybPhone());//商户注册手机
				params.put("mid",merchantInfoModel.getMid());
				params.put("tid", merchantInfoBean.getQrtid());
				params.put("org_no", yidai);//一代机构号
				String json = JSON.toJSONString(params);
				log.info("聚合商户增机推送hyb接口 "+json);
				String ss=HttpXmlClient.postForJson(AggPayTerHybUrl, json);
			}
		}catch (Exception e) {
			log.info("聚合商户增机推送hyb接口异常"+e);
		}finally{
			//调用webservice，对GMMS添加开通商户信息
			String tenance=merchantInfoModel.getUnno();
			String sales="xxx";
			MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(merchantInfoModel, "SYS", tenance, sales);
			Boolean falg = false ;
			try{
				info.setUnno(yidai);
				// @author:lxg-20190508 手刷商户推送时,传活动类型
				String firstSql="select k.rebatetype from    bill_terminalinfo k where k.termid in ( " +
						" select t.tid from    bill_merchantterminalinfo t where  t.maintaintype!='D' and t.mid='"+merchantInfoModel.getMid()+"' " +
						") and k.rebatetype is not null and rownum=1 order by k.usedconfirmdate,k.btid ";
				List<Map<String,String>> firstRebateType =unitDao.queryObjectsBySqlListMap(firstSql,null);
				if(firstRebateType.size()==1){
					info.setRebateType(firstRebateType.get(0).get("REBATETYPE")+"");
				}
				falg=gsClient.saveMerchantInfo(info,"hrtREX1234.Java");
			}catch(Exception e ){
				log.error("调用webservice失败：" + e);
			}
			if(!falg){
				return ;
//				throw new IllegalAccessError("调用webservice失败");
			}
			if(falg==true){
				merchantInfoModel.setApproveStatus("Y");
				merchantInfoModel.setApproveDate(new Date());
				merchantInfoModel.setCheckConfirmDate(new Date());
				merchantInfoDao.updateObject(merchantInfoModel);
				
				UserModel userModel = new UserModel();
				userModel.setCreateDate(new Date());
				userModel.setUseLvl(3);
				userModel.setIsLogin(0);
				userModel.setCreateUser("sys");
				userModel.setUserName(merchantInfoModel.getRname());
				userModel.setLoginName(merchantInfoModel.getMid());	//账号默认mid
				String pass = merchantInfoModel.getMid().substring(merchantInfoModel.getMid().length()-6,merchantInfoModel.getMid().length());
				userModel.setPassword(MD5Wrapper.encryptMD5ToString(pass));		//默认密码mid后六位
				UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, merchantInfoModel.getUnno());
				Set<UnitModel> units = new HashSet<UnitModel>();
				units.add(unitModel);
				userModel.setUnits(units);
				String hql="from RoleModel r where r.roleLevel = 4 and r.roleAttr = 1";
				List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql);
				Set<RoleModel> roles = new HashSet<RoleModel>();
				for (RoleModel role : roleList) {
					roles.add(role);
				}
				userModel.setRoles(roles);
				userModel.setResetFlag(1);
				userModel.setStatus(0);
				userDao.saveObject(userModel);
			}
		}
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
//			File destFile = new File(fPath);
			FileInputStream fis = new FileInputStream(upload);
			// @date:20190123 商户签约上传的图片添加水印
			PictureWaterMarkUtil.addWatermark(fis,fPath, Constant.PICTURE_WATER_MARK,
					fName.substring(fName.lastIndexOf(".")+1));
//			FileOutputStream fos = new FileOutputStream(destFile);
//			byte [] buffer = new byte[1024];
//			int len = 0;
//			while((len = fis.read(buffer))>0){
//				fos.write(buffer,0,len);
//			}
//			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 二进制上传
	 */
	private void uploadFile2byte(String upload, String fName, String imageDay) {
	    if(upload!=null) {
            upload = upload.replaceAll("data:image/png;base64,", "");
        }
		try {
			String realPath = queryUpLoadPath() + File.separator + imageDay;
			File dir = new File(realPath);
			dir.mkdirs();
			String fPath = realPath + File.separator + fName;
			byte[] imgByte = decode(upload);  
			FileOutputStream outputStream = new FileOutputStream(new File(fPath));
			outputStream.write(imgByte);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static byte[] decode(String imageData) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] data = decoder.decodeBuffer(imageData);
		for (int i = 0; i < data.length; ++i) {
			if (data[i] < 0) {
				// 调整异常数据
				data[i] += 256;
			}
		}
		//
		return data;
	}

	/**
	 * 查询上传路径
	 */
	private String queryUpLoadPath() {
		String title="HrtFrameWork";
		return phoneMicroMerchantInfoDao.querySavePath(title);
	}

	@Override
	public boolean hasUnbundBeforeExistsAct(String mid,String sn){
		Map tt = new HashMap();
		// 收银台商户解绑已激活设备不允许该商户再次绑定
		String merTerSql = "select count(1) from bill_merchantterminalinfo t where t.mid=:mid and t.maintaintype='D' and t.tid in (" +
				" select p.termid from bill_terminalinfo p where p.sn=:sn" +
				" ) and t.depositflag=6";
		tt.put("mid",mid);
		tt.put("sn",sn);
		long hasMerTer = phoneMicroMerchantInfoDao.querysqlCounts2(merTerSql,tt);
		return hasMerTer>0;
	}
	
	@Override
	public List<Map<String, String>> saveMerchantTermialInfoByPhone(
			MerchantInfoBean merchantInfoBean) {
			List<Map<String,String>>  list2;
			String sql="select t.unno as UNNO ,t.termid as TID ,t.RATE||'' as RATE,t.SECONDRATE||'' as SECONDRATE,t.scanRate||'' as SCANRATE,nvl(DEPOSITAMT,0)||'' as DEPOSITFLAG,t.rebateType,t.scanrateup||'' as scanrateup,t.huabeirate||'' as huabeirate from bill_terminalinfo t where t.sn='"+merchantInfoBean.getSn()+"'";//t.snType is null and
			List<Map<String,String>> list=phoneMicroMerchantInfoDao.queryObjectsBySqlList(sql);
			String unno=list.get(0).get("UNNO");
			String tid=list.get(0).get("TID");
			String rate=list.get(0).get("RATE");
			String secondRate=list.get(0).get("SECONDRATE");
			String scanRate=list.get(0).get("SCANRATE");
			Double depositFlag = Double.parseDouble(list.get(0).get("DEPOSITFLAG"));
			String rebateType=list.get(0).get("REBATETYPE");
			String scanRateUp=list.get(0).get("SCANRATEUP");
			String huaBeiRate=list.get(0).get("HUABEIRATE");
			boolean flag = false;
			MerchantTerminalInfoModel mtim = new MerchantTerminalInfoModel();
			MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantInfoBean.getMid()});
			if(depositFlag>0&&"000000".equals(m.getSettMethod())){
				list2 = new ArrayList<Map<String,String>>();
				Map<String,String> msg = new HashMap<String,String>();
				msg.put("msg", "该POS:SN号禁止在理财版使用,请下载秒到版！");
				list2.add(msg);
				return list2;
			}
			if(depositFlag>0&&merchantInfoBean.getDeposit()!=null&&merchantInfoBean.getDeposit()>0){
				mtim.setDepositFlag(1);
			}else if(depositFlag>0){
				list2 = new ArrayList<Map<String,String>>();
				Map<String,String> msg = new HashMap<String,String>();
				msg.put("msg", "该POS:SN号禁止在此版本使用,请下载最新版本！");
				list2.add(msg);
				return list2;
			}
			String tSql = " select t.RATE||'' as RATE,t.SECONDRATE||'' as SECONDRATE from bill_terminalinfo t,bill_merchantterminalinfo i " +
							" where i.tid=t.termid and i.mid='"+merchantInfoBean.getMid()+"' and i.approvestatus !='K' and i.maintaintype!='D' order by t.RATE asc";
			List<Map<String,String>> tList=phoneMicroMerchantInfoDao.queryObjectsBySqlList(tSql);	
			if(rate==null||"".equals(rate)||secondRate==null||"".equals(secondRate)){
			}else{
				if(tList!=null&&tList.size()>0){
					if(rate.equals(m.getCreditBankRate())&&rate.equals(m.getBankFeeRate())&&(secondRate.equals(m.getSecondRate())||(m.getSecondRate()==null&&secondRate.equals("2")))){
						m.setSecondRate(secondRate);
						flag=true;
					}else{
						return null;
					}
				}else{
					flag=true;
					m.setSecondRate(secondRate);
					m.setBankFeeRate(rate);
					m.setCreditBankRate(rate);
				}
			}
			//判断hyb传送过来的agentId(0：传统版本，10000：秒到版本)
			if(merchantInfoBean.getAgentId()==null || "".equals(merchantInfoBean.getAgentId())){
				merchantInfoBean.setAgentId("0");
			}
				String yidai = merchantInfoService.getProvince(unno);
				if("000000".equals(m.getUnno())){
					m.setUnno(unno);
					m.setApproveStatus("Y");
					m.setApproveDate(new Date());
					String sign="";
					for(int i=0;i<10;i++){
						sign=sign+String.valueOf((char)(Math.random()*26+65));
					}
					m.setQrUrl(ParamPropUtils.props.get("xpayPath")+"/qrpayment?mid="+m.getMid()+"&sign="+sign);
					if(scanRate==null||"".equals(scanRate)){
						m.setScanRate(0.0038);
					}else{
						m.setScanRate(Double.valueOf(scanRate));
					}
					if(scanRateUp!=null && !"".equals(scanRateUp)){
						m.setScanRate1(Double.valueOf(scanRateUp));
					}
					if(huaBeiRate!=null && !"".equals(huaBeiRate)){
						m.setScanRate2(Double.valueOf(huaBeiRate));
					}
					m.setQrUpload("0");
					String tenance=unno;
					String sales="xxx";
					//是否返现0-F 1-T
					if(merchantInfoBean.getIsRebate()!=null&&merchantInfoBean.getIsRebate()==0){
						m.setIsRebate(merchantInfoBean.getIsRebate());//不返
					}else {
						m.setIsRebate(1);//返
					}
					log.info("推送商户到综合isRebate="+m.getIsRebate()+"mid="+m.getMid());
					MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(m, "SYS", tenance, sales);
					Boolean falg = false ;
					try{
						info.setUnno(yidai);
						info.setRebateType(rebateType);//活动类型
						falg=gsClient.saveMerchantInfo(info,"hrtREX1234.Java");
					}catch(Exception e ){
						log.error("调用webservice失败：" + e);
					}
					if(!falg){
						m.setApproveStatus("W");
						log.error("调用webservice失败：");
					}
					phoneMicroMerchantInfoDao.updateObject(m);
					UserModel userModel = new UserModel();
					userModel.setCreateDate(new Date());
					userModel.setUseLvl(3);
					userModel.setIsLogin(0);
					userModel.setCreateUser("sys");
					userModel.setUserName(m.getRname());
					userModel.setLoginName(m.getMid());	//账号默认mid
					//默认密码mid后六位
					String pass = m.getMid().substring(m.getMid().length()-6,m.getMid().length());
					try {
						userModel.setPassword(MD5Wrapper.encryptMD5ToString(pass));
					} catch (Exception e) {
						e.printStackTrace();
					}		
					UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, m.getUnno());
					Set<UnitModel> units = new HashSet<UnitModel>();
					units.add(unitModel);
					userModel.setUnits(units);
					String hql="from RoleModel r where r.roleLevel = 4 and r.roleAttr = 1";
					List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql);
					Set<RoleModel> roles = new HashSet<RoleModel>();
					for (RoleModel role : roleList) {
						roles.add(role);
					}
					userModel.setRoles(roles);
					userModel.setResetFlag(1);
					userModel.setStatus(0);
					userDao.saveObject(userModel);
				}
				//推送终端
				Integer typeFlag;
				if("000000".equals(m.getSettMethod())){
					typeFlag=2;
				}else{
					typeFlag=3;
				}
				String hql="from TerminalInfoModel t where t.termID='"+tid+"' ";
				TerminalInfoModel t = terminalInfoDao.queryObjectByHql(hql, new Object[]{});
				TermAcc acc=new TermAcc();
				acc.setCby("SYS");
				acc.setModleId(merchantInfoBean.getMachineModel());
				acc.setMid(m.getMid());
				acc.setTid(tid);
				acc.setTypeflag(typeFlag);
				acc.setUnno(unno);
				acc.setSn(merchantInfoBean.getSn());
				acc.setUnno(yidai);
				acc.setRebateType(t.getRebateType());//返利类型
				acc.setKeyConfirmDate(new SimpleDateFormat("yyyyMMdd").format(t.getKeyConfirmDate()));//入网时间
				acc.setOutDate(t.getOutDate());
				acc.setSnsigndate(t.getAcceptDate());
				acc.setSnType(t.getSnType()==null?"":t.getSnType().toString());//大小蓝牙 1-小蓝牙 2-大蓝牙
				// @author:xuegangliu-20190226 终端返现状态
				acc.setIsReturnCash(t.getIsReturnCash());
				if(flag&&m.getBankFeeRate()!=null&&!"".equals(m.getBankFeeRate())&&m.getSecondRate()!=null&&!"".equals(m.getSecondRate())){
					acc.setRate(Double.valueOf(m.getBankFeeRate()));
					acc.setSecondRate(Double.valueOf(m.getSecondRate()));
				}
				//设备是否已经返现0未返，1，2-已返
				if(t.getIsReturnCash()!=null&&t.getIsReturnCash()!=0) {
					acc.setIsReturnCash(1);
				}else {
					acc.setIsReturnCash(t.getIsReturnCash());
				}
				if(t!=null && t.getRebateType()!=null){
					Map configInfo = merchantInfoService.getSelfTermConfig(t.getRebateType(),t.getDepositAmt());
					if(configInfo!=null) {
						if (configInfo.containsKey("depositamt")) {
                            acc.setDepositAmt(Integer.parseInt(configInfo.get("depositamt").toString()));
						}
						if (configInfo.containsKey("tradeamt")) {
                            acc.setFirmoney(Double.parseDouble(configInfo.get("tradeamt").toString()));
						}
						if (configInfo.containsKey("adm_deposit_flag")) {
							acc.setDepositFlag(Integer.parseInt(configInfo.get("adm_deposit_flag").toString()));
							mtim.setDepositFlag(Integer.parseInt(configInfo.get("app_deposit_flag").toString()));
						}
					}
				}else{
					acc.setDepositFlag(mtim.getDepositFlag());
				}
//				if(depositFlag!=null&&depositFlag>0){
//					acc.setDepositAmt(depositFlag.intValue());
//				}
				mtim.setSn(merchantInfoBean.getSn());
				mtim.setInstalledAddress(m.getRaddr());
				mtim.setContactPerson(m.getContactPerson());
				mtim.setContactPhone(m.getContactPhone());
				mtim.setMid(merchantInfoBean.getMid());
				mtim.setApproveDate(new Date());
				mtim.setApproveStatus("Y");			//微商户增机---专属状态，用来区分传统增机与微商户自助报单增机。
				mtim.setMaintainType("A");		//A-add   M-Modify  D-Delete
				mtim.setStatus(2);
				mtim.setTid(tid);
				mtim.setUnno(unno);
				mtim.setBmaid(queryBmaid(merchantInfoBean.getMachineModel()));
				Boolean falg2 = false ;
				if(merchantInfoBean.getSn()!=null&&!"".equals(merchantInfoBean.getSn())){
					try{
						log.error("手刷商户审批(app):mid="+acc.getMid()+",请求综合参数:"+JSONObject.toJSONString(acc));
						falg2=gsClient.saveTermAccSub(acc,"hrtREX1234.Java");
					}catch(Exception e ){
						log.error("调用webservice失败：" + e);
					}
					if(!falg2){
						mtim.setApproveStatus("Z");
						log.error("调用webservice失败：");
					}
				}
				Map tt = new HashMap(16);
				String merTerSql = "select count(1) from bill_merchantterminalinfo b where b.tid = :tid and mid = :mid";
				tt.put("tid",tid);
				tt.put("mid",merchantInfoBean.getMid());
				long hasMerTer = phoneMicroMerchantInfoDao.querysqlCounts2(merTerSql,tt);
				if(hasMerTer==1){
					String sql2 = "update bill_merchantterminalinfo b set b.maintaintype =:maintaintype,b.approvestatus=:approvestatus where b.tid =:tid and mid =:mid";
					tt.put("maintaintype",mtim.getMaintainType());
					tt.put("approvestatus",mtim.getApproveStatus());
					phoneMicroMerchantInfoDao.executeSqlUpdate(sql2,tt);
				}else{
					phoneMicroMerchantInfoDao.saveObject(mtim);
				}
				String sqlInfo="select MID,rname MER_NAME, nvl(contactperson,' ') CONTACTER,nvl(contactphone,' ') CONTACTOR_CELLPHONE," +
								" nvl(contactaddress,' ') BUSINESS_ADDRESS,nvl(b.LOGIN_Name,' ')  USER_NAME,nvl(PWD,' ') PASSWORD" +
								"  from  bill_merchantinfo a left join  Sys_User b " +
								"  on a.MID = b.LOGIN_Name where a.mid='"+merchantInfoBean.getMid()+"'";
				list2=phoneMicroMerchantInfoDao.queryObjectsBySqlList(sqlInfo);
				if(list2.size()>0 && list2 !=null){
					list2.get(0).put("UNNO", unno);
					list2.get(0).put("TID", tid);
					list2.get(0).put("SN",merchantInfoBean.getSn());
				}
				if(depositFlag>0){
					list2.get(0).put("deposit", depositFlag+6+"");
				}
				if(acc.getDepositFlag()!=null&&acc.getDepositFlag()==3){
					list2.get(0).put("yxsb", "1");//优享设备返回语标识
				}
				//更新时间
				String updateSql="update BILL_TERMINALINFO t set t.usedconfirmdate=sysdate ,t.maintaindate=sysdate,t.status=2 where t.sn='"+merchantInfoBean.getSn()+"'";
				phoneMicroMerchantInfoDao.executeUpdate(updateSql);
//			}
			
		return list2;
	}
	
	@Override
	public List<Map<String, String>> saveMerchantTermialInfoDGL(MerchantInfoBean merchantInfoBean) {
		List<Map<String,String>>  list2;
		String sql = "select * from bill_merchantterminalinfo m1 where m1.mid = :mid and m1.maintainType != :maintainType and m1.approveStatus = :approveStatus and tid=:tid and m1.sn=:sn ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", merchantInfoBean.getMid());
		map.put("maintainType", "D");
		map.put("approveStatus", "Y");
		map.put("tid", merchantInfoBean.getTid());
		map.put("sn", merchantInfoBean.getSn());
		
		MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantInfoBean.getMid()});
		List<Map<String,String>> result = phoneMicroMerchantInfoDao.queryObjectsBySqlListMap(sql, map);
		if(result!=null&&result.size()>0){
		}else{
			MerchantTerminalInfoModel mtim = new MerchantTerminalInfoModel();
			mtim.setSn(merchantInfoBean.getSn());
			mtim.setInstalledAddress(m.getRaddr());
			mtim.setContactPerson(m.getLegalPerson());
			mtim.setContactPhone(m.getHybPhone());
			mtim.setMid(merchantInfoBean.getMid());
			mtim.setApproveDate(new Date());
			mtim.setApproveStatus("Y");			//微商户增机---专属状态，用来区分传统增机与微商户自助报单增机。
			mtim.setMaintainType("A");		//A-add   M-Modify  D-Delete
			mtim.setStatus(2);
			mtim.setTid(merchantInfoBean.getTid());
			mtim.setUnno(m.getUnno());
			mtim.setBmaid(181);
			mtim.setApproveStatus("Z");
			phoneMicroMerchantInfoDao.saveObject(mtim);
			if("Z".equals(m.getApproveStatus())){
				m.setApproveStatus("W");
				phoneMicroMerchantInfoDao.updateObject(m);
			}
		}
		
		String sqlInfo="select MID,rname MER_NAME, nvl(contactperson,' ') CONTACTER,nvl(contactphone,' ') CONTACTOR_CELLPHONE," +
						" nvl(contactaddress,' ') BUSINESS_ADDRESS,nvl(b.LOGIN_Name,' ')  USER_NAME,nvl(PWD,' ') PASSWORD" +
						"  from  bill_merchantinfo a left join  Sys_User b " +
						"  on a.MID = b.LOGIN_Name where a.mid='"+merchantInfoBean.getMid()+"'";
		list2=phoneMicroMerchantInfoDao.queryObjectsBySqlList(sqlInfo);
		if(list2.size()>0 && list2 !=null){
			list2.get(0).put("UNNO", m.getUnno());
			list2.get(0).put("TID", merchantInfoBean.getTid());
			list2.get(0).put("SN",merchantInfoBean.getSn());
		}
			
		return list2;
	}
	
	@Override
	public List<Map<String, String>> saveAggPayMerchantTermialInfo(MerchantInfoBean merchantInfoBean) {
//		List<Map<String,String>>  list2;
		String sql="select t.unno as UNNO ,t.QRTID as QRTID,t.scanRate as SCANRATE,nvl(t.SETTLEMENT,1) as SETTLEMENT,nvl(t.secondRate,2) as secondRate from BILL_aggpayterminfo t where t.QRTID='"+merchantInfoBean.getQrtid()+"'";
		List<Map<String,Object>> list=phoneMicroMerchantInfoDao.executeSql(sql);
		String unno=list.get(0).get("UNNO").toString();
		String rate= list.get(0).get("SCANRATE").toString();
		String secondRate= list.get(0).get("SECONDRATE").toString();
		String settlement= list.get(0).get("SETTLEMENT").toString();
		MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantInfoBean.getMid()});
		String tSql = " select * from BILL_aggpayterminfo i where i.mid='"+merchantInfoBean.getMid()+"' and i.status in (1,2,4) ";
		List<Map<String,String>> tList=phoneMicroMerchantInfoDao.queryObjectsBySqlList(tSql);	
		if(rate==null||"".equals(rate)){
		}else{
			if(tList!=null&&tList.size()>0){
//				if(rate.equals(m.getScanRate().toString())){
//				}else{
//					list2 = new ArrayList<Map<String,String>>();
//					Map<String,String> msg = new HashMap<String,String>();
//					msg.put("msg", "设备与商户费率信息不一致,请联系销售!");
//					list2.add(msg);
//					return list2;
//				}
			}else{
				m.setScanRate(Double.valueOf(rate));
			}
		}
		String yidai = merchantInfoService.getProvince(unno);
		try {
			UnitModel unitModel = unitDao.queryObjectByHql("from UnitModel t where t.unNo=?", new Object[]{yidai});
			if(m.getHybPhone()!=null && !"".equals(m.getHybPhone())){
				Map<String,String> params = new HashMap<String, String>();
				params.put("phone",m.getHybPhone());//商户注册手机
				params.put("mid",m.getMid());
				params.put("tid", merchantInfoBean.getQrtid());
				params.put("tname", merchantInfoBean.getShopname());
				params.put("org_no", yidai);//一代机构号
				params.put("org_no_name", unitModel.getUnitName());//一代机构号名称
				params.put("isForeign", m.getIsForeign()+"");//是否开通储值卡  0，是，1 否 (默认)
				if("000000".equals(m.getUnno()))params.put("settlement", settlement);//结算周期
				String json = JSON.toJSONString(params);
				log.info("聚合商户增机推送hyb接口 "+json);
				String ss=HttpXmlClient.postForJson(AggPayTerHybUrl, json);
			}
		} catch (Exception e) {
			log.info("聚合商户增机推送hyb接口异常 "+e);
		}finally{
			if("000000".equals(m.getUnno())){
				m.setUnno(unno);
				if("6".equals(m.getAreaType())){
					m.setApproveStatus("Y");
				}else{
					m.setApproveStatus("C");
				}
				m.setApproveDate(new Date());
				m.setQrUpload("0");
				m.setSettleCycle(Integer.valueOf(settlement));
				//t0默认秒到
				if(m.getSettleCycle()==0){
					m.setSettMethod("100000");
				}
				m.setSecondRate(secondRate);
				Boolean falg = false ;
				if(m.getBankArea()!=null&&m.getBankArea().contains("同一个身份证号下申请聚合商户")){
					//非个人聚合身份证超过五次落地
				}/**20171226曾:立码富合伙人（机构号111006）报单为企业类型的商户不落地审核，直接进行交易
				else if ("111006".equals(unno)&&"4".equals(m.getAreaType())){
					//20171226曾:立码富合伙人（机构号111006）报单为企业类型的商户进行交易落地审核
				}**/else{
					String tenance=unno;
					String sales="xxx";
					MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(m, "SYS", tenance, sales);
					try{
						// @author:lxg-20190508 手刷商户推送时,传活动类型
						String firstSql="select k.rebatetype from    bill_terminalinfo k where k.termid in ( " +
								" select t.tid from    bill_merchantterminalinfo t where  t.maintaintype!='D' and t.mid='"+m.getMid()+"' " +
								") and k.rebatetype is not null and rownum=1 order by k.usedconfirmdate,k.btid ";
						List<Map<String,String>> firstRebateType =unitDao.queryObjectsBySqlListMap(firstSql,null);
						if(firstRebateType.size()==1){
							info.setRebateType(firstRebateType.get(0).get("REBATETYPE")+"");
						}
						info.setUnno(yidai);
						falg=gsClient.saveMerchantInfo(info,"hrtREX1234.Java");
					}catch(Exception e ){
						log.error("推送聚合商户调用webservice失败：" + e);
					}
				}
				if(!falg){
					m.setApproveStatus("W");
					log.error("调用webservice失败：");
	//				throw new IllegalAccessError("调用webservice失败");
				}
				UserModel userModel = new UserModel();
				userModel.setCreateDate(new Date());
				userModel.setUseLvl(3);
				userModel.setIsLogin(0);
				userModel.setCreateUser("sys");
				userModel.setUserName(m.getRname());
				userModel.setLoginName(m.getMid());	//账号默认mid
				//默认密码mid后六位
				String pass = m.getMid().substring(m.getMid().length()-6,m.getMid().length());
				try {
					userModel.setPassword(MD5Wrapper.encryptMD5ToString(pass));
				} catch (Exception e) {
					e.printStackTrace();
				}		
				UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, m.getUnno());
				Set<UnitModel> units = new HashSet<UnitModel>();
				units.add(unitModel);
				userModel.setUnits(units);
				String hql="from RoleModel r where r.roleLevel = 4 and r.roleAttr = 1";
				List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql);
				Set<RoleModel> roles = new HashSet<RoleModel>();
				for (RoleModel role : roleList) {
					roles.add(role);
				}
				userModel.setRoles(roles);
				userModel.setResetFlag(1);
				userModel.setStatus(0);
				userDao.saveObject(userModel);
			}
			//推送终端
//			Integer typeFlag;
//			if("000000".equals(m.getSettMethod())){
//				typeFlag=2;
//			}else{
//				typeFlag=3;
//			}
			AggPayTerminfoModel term =aggPayTerminfoDao.queryObjectByHql("from AggPayTerminfoModel t where t.qrtid=?", new Object[]{merchantInfoBean.getQrtid()});
	
//			AggPayTerm payTerm = new AggPayTerm();
//			payTerm.setUnno(yidai);
//			payTerm.setCby("SYS");
//			payTerm.setMid(m.getMid());
//			payTerm.setQrtid(merchantInfoBean.getQrtid());
//			payTerm.setTypeflag(typeFlag);
//			payTerm.setBapid(term.getBapid());
//			payTerm.setShopname(merchantInfoBean.getShopname());
			
			term.setApproveDate(new Date());
			term.setStatus(2);
			term.setApproveId(544924);
			term.setMid(m.getMid());
			term.setShopname(merchantInfoBean.getShopname());
			term.setUsedConfirmDate(new Date());
//			Boolean falg2 = false ;
//			if(merchantInfoBean.getQrtid()!=null&&!"".equals(merchantInfoBean.getQrtid())){
//				try{
//					log.info("聚合商户审批(app):mid="+payTerm.getMid()+";Qrtid="+payTerm.getQrtid()+";typeflag="+payTerm.getTypeflag());
//					falg2=gsClient.saveAggPayTermSub(payTerm,"hrtREX1234.Java");
//				}catch(Exception e ){
//					log.error("聚合商户推送终端调用webservice失败：" + e);
//				}
//				if(!falg2){
//					term.setStatus(1);
//					log.error("调用webservice失败：");
//					//throw new IllegalAccessError("调用webservice失败");
//				}
//			}
		}
		return null;
	}
	
	/**
	 * @msg 判断立码富是否可以开通非接（北京市，sign=sn-xxx），可以修改非接待审核-W，审核通过-Y，退回-K
	 * @return true-可以开通 false-不可以开通
	 */
	private boolean saveNonConnection(String mid,String sign) {
		boolean flag=false;
		try{
		//查商户
		MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{mid});
		String[] sn = sign.split("-");
		if(!m.getQX_name().contains("北京市")){
			//非北京市，可以绑码
			return true;
		}
		String sql="select * from bill_terminalinfo t where t.sn='"+sn[1]+"'";
		List<Map<String,String>> list=phoneMicroMerchantInfoDao.queryObjectsBySqlList(sql);
		//无此SN
		if(list.size()==0){
			log.info("立码富非接绑定失败，sn = "+sn[1]+" 号未查到");
			return flag;
		}
		//已经被使用
		if("2".equals(list.get(0).get("STATUS"))){
			log.info("立码富非接绑定失败，sn = "+sn[1]+" 号已被使用");
			return flag;
		}
		//将关系维护在中间表
		MerchantTerminalInfoModel mtim = new MerchantTerminalInfoModel();
		mtim.setSn(sn[1]);
		mtim.setInstalledAddress(m.getRaddr());
		mtim.setContactPerson(m.getBankAccName());
		mtim.setContactPhone(m.getHybPhone());
		mtim.setMid(m.getMid());
		mtim.setApproveDate(new Date());
		mtim.setApproveStatus("Z");		
		mtim.setMaintainType("A");
		mtim.setStatus(1);
		mtim.setTid(list.get(0).get("TERMID"));
		mtim.setUnno(m.getUnno());
		//机型
		mtim.setBmaid(queryBmaid(list.get(0).get("MACHINEMODEL")));
		phoneMicroMerchantInfoDao.saveObject(mtim);
		flag = true;
		//修改终端为已使用
		phoneMicroMerchantInfoDao.executeUpdate("update bill_terminalinfo set status=2,usedConfirmDate=sysdate where sn='"+sn[1]+"'");
		//判断可以开通非接，修改非接状态为待审核
		m.setNonConnection("W");
		merchantInfoDao.saveObject(m);
		}catch(Exception e){
		}
		return flag;
	}
	@Override
	public List<Map<String, String>> saveAggPayMerchantKJInfo(MerchantInfoBean merchantInfoBean) {
		String unno="111006";
		Double rate= 0.0038d;
		String secondRate= "0";
		String settlement= "1";
		MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantInfoBean.getMid()});
		if("000000".equals(m.getUnno())){
			m.setUnno(unno);
			m.setScanRate(rate);
			m.setApproveStatus("Y");
			m.setApproveDate(new Date());
			m.setQrUpload("0");
			m.setSettleCycle(Integer.valueOf(settlement));
			//t0默认秒到
			if(m.getSettleCycle()==0){
				m.setSettMethod("100000");
			}
			m.setSecondRate(secondRate);
			Boolean falg = false ;
			String tenance=unno;
			String sales="xxx";
			MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(m, "SYS", tenance, sales);
			try{
				// @author:lxg-20190508 手刷商户推送时,传活动类型
				String firstSql="select k.rebatetype from    bill_terminalinfo k where k.termid in ( " +
						" select t.tid from    bill_merchantterminalinfo t where  t.maintaintype!='D' and t.mid='"+merchantInfoBean.getMid()+"' " +
						") and k.rebatetype is not null and rownum=1 order by k.usedconfirmdate,k.btid ";
				List<Map<String,String>> firstRebateType =unitDao.queryObjectsBySqlListMap(firstSql,null);
				if(firstRebateType.size()==1){
					info.setRebateType(firstRebateType.get(0).get("REBATETYPE")+"");
				}
				info.setUnno(unno);
				falg=gsClient.saveMerchantInfo(info,"hrtREX1234.Java");
			}catch(Exception e ){
				log.error("saveAggPayMerchantKJInfo推送聚合商户调用webservice失败：" + e);
			}
			if(!falg){
				m.setApproveStatus("W");
				log.error("调用webservice失败：");
//				throw new IllegalAccessError("调用webservice失败");
			}
			UserModel userModel = new UserModel();
			userModel.setCreateDate(new Date());
			userModel.setUseLvl(3);
			userModel.setIsLogin(0);
			userModel.setCreateUser("sys");
			userModel.setUserName(m.getRname());
			userModel.setLoginName(m.getMid());	//账号默认mid
			//默认密码mid后六位
			String pass = m.getMid().substring(m.getMid().length()-6,m.getMid().length());
			try {
				userModel.setPassword(MD5Wrapper.encryptMD5ToString(pass));
			} catch (Exception e) {
				e.printStackTrace();
			}		
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, m.getUnno());
			Set<UnitModel> units = new HashSet<UnitModel>();
			units.add(unitModel);
			userModel.setUnits(units);
			String hql="from RoleModel r where r.roleLevel = 4 and r.roleAttr = 1";
			List<RoleModel> roleList = roleDao.queryObjectsByHqlList(hql);
			Set<RoleModel> roles = new HashSet<RoleModel>();
			for (RoleModel role : roleList) {
				roles.add(role);
			}
			userModel.setRoles(roles);
			userModel.setResetFlag(1);
			userModel.setStatus(0);
			userDao.saveObject(userModel);
			return null;
		}else{
			ArrayList<Map<String,String>> list2 = new ArrayList<Map<String,String>>();
			Map<String,String> msg = new HashMap<String,String>();
			msg.put("msg", "请等待人工审核!");
			list2.add(msg);
			return list2;
		}
		
	}
	
	/**
	 * 增机推送hyb
	 */
	public void merchantTermialInfotoHyb(MerchantInfoBean merchantInfoBean,List<Map<String,String>> list){
		if(merchantInfoBean.getAgentId()!=null && !"".equals(merchantInfoBean.getAgentId())){
			Map<String,String> params = new HashMap<String, String>();
			params.put("userName",merchantInfoBean.getHybPhone());
			params.put("mid",merchantInfoBean.getMid());
			params.put("tid", list.get(0).get("TID"));
			params.put("sn", merchantInfoBean.getSn());
			params.put("agentId", merchantInfoBean.getAgentId());		//0: 和融通系统M35报单 久玖为1，托付为2
			params.put("mark", "1");	//0：待挂终端增机   1：增机申请增机
			log.info("手机增机接口 userName："+merchantInfoBean.getHybPhone()+"  mid:"+merchantInfoBean.getMid()+"  tid:"+list.get(0).get("TID")+"  sn:"+merchantInfoBean.getSn());
			String ss=HttpXmlClient.post(hybUrl, params);
		}
	}
	
	@Override
	public boolean findMidInfo(String mid) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql ="select mid from bill_merchantInfo t where t.mid=:mid";
		map.put("mid", mid);
		Object aa =phoneMicroMerchantInfoDao.queryObjectBySql(sql, map);
		if(aa!=null && !"".equals(aa)){
			return true;
		}
		return false;
	}
	
	@Override
	public Map serachMerchantinfoDepositBySn(String sn) {
		Map result=new HashMap();
		if (sn != null && !sn.isEmpty()) {
            TerminalInfoModel terminalInfoModel = terminalInfoDao.queryObjectByHql("from TerminalInfoModel t where t.sn=? and t.depositAmt>0 and rownum =1 ",new Object[]{sn});
			if(terminalInfoModel!=null){
                if (terminalInfoModel.getDepositAmt()!=null) {
					String amtSql="select nvl(t.depositamt,0)||'' depositamt,t.configinfo,t.SPECIALINFO from bill_purconfigure t where t.type=3 and t.valueinteger=:rebateType ";
					Map amtMap = new HashMap();
					if(terminalInfoModel.getRebateType()!=null) {
						String rebateType = terminalInfoModel.getRebateType() + "";
						String depositAmt1 = terminalInfoModel.getDepositAmt()+"";
						amtMap.put("rebateType", rebateType);
						List<Map<String, String>> amtList = terminalInfoDao.queryObjectsBySqlListMap(amtSql, amtMap);
						if (amtList.size() == 1) {
                            // 设置配置表押金金额
                            String aaMt = amtList.get(0).get("DEPOSITAMT");

							// 给app展示的押金金额 depositAmt1为设备表里押金
							String specialInfo = amtList.get(0).get("SPECIALINFO");
							if(StringUtils.isNotEmpty(specialInfo)){
								JSONObject depostDef = JSON.parseObject(specialInfo);
								if (depostDef!=null && depostDef.containsKey(depositAmt1)) {
									result.put("depositAmt",depostDef.getLong(depositAmt1));
								}else{
									result.put("depositAmt",Long.parseLong(aaMt));
								}
							}else{
                                result.put("depositAmt",Long.parseLong(terminalInfoModel.getDepositAmt()+""));
                            }
						}else{
							result.put("depositAmt",Long.parseLong(terminalInfoModel.getDepositAmt()+""));
						}
					}else{
						result.put("depositAmt",Long.parseLong(terminalInfoModel.getDepositAmt()+""));
					}
                }else{
                    result.put("depositAmt",0);
                }

				if(terminalInfoModel.getRebateType()!=null &&(terminalInfoModel.getRebateType()==14 || terminalInfoModel.getRebateType()==15
						|| terminalInfoModel.getRebateType()==70)) {
					SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
					if("20200101".compareTo(df.format(new Date()))>0){
						result.put("numberUnits","0");
					}else{
						result.put("numberUnits","1");
					}
					return result;
				}else{
					result.put("numberUnits","0");
					return result;
				}
			}else{
				result.put("depositAmt",0L);
				result.put("numberUnits","0");
				return result;
			}
		}
		result.put("depositAmt",0L);
		result.put("numberUnits","0");
		return result;
	}	
	
	@Override
	public Integer updateDeposit(String mid,String tid) {
		if (mid != null && !mid.isEmpty()&&tid != null && !tid.isEmpty()) {
			String hql="update MerchantTerminalInfoModel t set t.depositFlag=6,t.maintainDate=sysdate,t.maintainType='M' where t.mid='"+mid+"' and t.tid='"+tid+"' and t.depositFlag=1";
			//优享
			String hql2="update MerchantTerminalInfoModel t set t.depositFlag=4,t.maintainDate=sysdate,t.maintainType='M' where t.mid='"+mid+"' and t.tid='"+tid+"' and t.depositFlag=3";
			//大额扫码
			String hql3="update MerchantTerminalInfoModel t set t.depositFlag=6,t.maintainDate=sysdate,t.maintainType='M' where t.mid='"+mid+"' and t.tid='"+tid+"' and t.depositFlag=5";
			Integer i = phoneMicroMerchantInfoDao.executeHql(hql);
			Integer i1 = phoneMicroMerchantInfoDao.executeHql(hql2);
			Integer i2 = phoneMicroMerchantInfoDao.executeHql(hql3);
			return i+i1+i2;
		}
		return 0;
	}	
	
	public Integer queryBmaid(String bmaidName){
		String sql="select bmaid from bill_machineinfo t where t.machinemodel=:Name";
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("Name", bmaidName);
		List<Map<String,Object>> list=userDao.queryObjectsBySqlList2(sql,map, 1, 1);
		if(list.size()==0){
			return 181;
		}else{
			Integer aa=Integer.parseInt(list.get(0).get("BMAID").toString());
			return aa;
		}
	}
	
	
	@Override
	public List<Map<String, Object>> findMidStatusInfo(String mid) {
		String sql ="select APPROVESTATUS,nvl(PROCESSCONTEXT,' ') as PROCESSCONTEXT,nvl(acctype,2) as acctype from bill_merchantInfo t where t.mid='"+mid+"'";
		List<Map<String ,Object>> list =phoneMicroMerchantInfoDao.queryObjectsBySqlObject(sql);
//		String status=list.get(0).get("APPROVESTATUS")+"";
//		if("Z".equals(status)){
//			list.get(0).put("APPROVESTATUS", "待挂终端");
//		}else if("W".equals(status)){
//			list.get(0).put("APPROVESTATUS", "待审核");
//		}else if("C".equals(status)){
//			list.get(0).put("APPROVESTATUS", "审核中");
//		}else if("K".equals(status)){
//			list.get(0).put("APPROVESTATUS", "被退回");
//		}else if("Y".equals(status)){
//			list.get(0).put("APPROVESTATUS", "审核通过");
//		}
		return list;
	}
	
	
	
	@Override
	public MerchantInfoModel queryMicroMerchantInfo(String mid) {
		String hql="from MerchantInfoModel t where t.mid=?";
		MerchantInfoModel m=(MerchantInfoModel) phoneMicroMerchantInfoDao.queryObjectByHql(hql, new Object[]{mid});
		return m;
	}
	
	@Override
	public MerchantInfoModel queryMerchantInfoDGL(String phone) {
		String hql="from MerchantInfoModel t where t.unno='992084' and t.hybPhone=?";
		MerchantInfoModel m=(MerchantInfoModel) phoneMicroMerchantInfoDao.queryObjectByHql(hql, new Object[]{phone});
		return m;
	}
	
	
	@Override
	public void updateMicroMerchantInfoZWK(MerchantInfoModel m,MerchantInfoBean merchantInfoBean) {
		// Integer authType;提交类型-3提额;4开通扫码(不传默认)
//		if (merchantInfoBean.getAuthType() == null) {
//			m.setAuthType(4);
//		}else{
//			m.setAuthType(merchantInfoBean.getAuthType());
//		}
		if (merchantInfoBean.getAreaType() != null) {
			m.setAreaType(merchantInfoBean.getAreaType().trim());
		}
		if(merchantInfoBean.getBankAccName()!=null){
			m.setBankAccName(merchantInfoBean.getBankAccName().trim());
		}
		if(merchantInfoBean.getBankAccNo()!=null){
			m.setBankAccNo(merchantInfoBean.getBankAccNo().trim());
		}
		if(merchantInfoBean.getLegalNum()!=null){
			m.setLegalNum(merchantInfoBean.getLegalNum().trim());
		}
		if(merchantInfoBean.getLegalNum()!=null){
			m.setAccNum(merchantInfoBean.getLegalNum().trim());
		}
		if(merchantInfoBean.getContactPhone()!=null){
			m.setContactPhone(merchantInfoBean.getContactPhone().trim());
		}
		if(merchantInfoBean.getRaddr()!=null){
			m.setRaddr(merchantInfoBean.getRaddr().trim());
		}
//		if(merchantInfoBean.getBno()!=null){
//			m.setBno(merchantInfoBean.getBno());
//		}
		if(merchantInfoBean.getAgentId()!=null){
			m.setBno(merchantInfoBean.getAgentId());
		}
		if("Z".equals(m.getApproveStatus())){
			m.setApproveStatus("Z");
		}else if ("W".equals(m.getApproveStatus())||"K".equals(m.getApproveStatus())){
			m.setApproveStatus("W");
		}else{
			m.setApproveStatus("C");
		}
		if(merchantInfoBean.getBankBranch()!=null){
			m.setBankBranch(merchantInfoBean.getBankBranch().trim());
		}
		if(merchantInfoBean.getRaddr()!=null){
			m.setContactAddress(merchantInfoBean.getRaddr().trim());
		}
		
		if(m.getIsM35()!=6){
			if(merchantInfoBean.getBankAccName()!=null){
				m.setRname(merchantInfoBean.getBankAccName().trim());
			}
			if(merchantInfoBean.getBankAccName()!=null){
				m.setLegalPerson(merchantInfoBean.getBankAccName().trim());
			}
			if(merchantInfoBean.getBankAccName()!=null){
				m.setContactPerson(merchantInfoBean.getBankAccName().trim());
			}
			if(merchantInfoBean.getRaddr()!=null){
				m.setBaddr(merchantInfoBean.getRaddr().trim());
			}
		}else{
			if(merchantInfoBean.getRname()!=null){
				m.setRname(merchantInfoBean.getRname().trim());
				if(merchantInfoBean.getMid().startsWith("HRTPAY")){
					//推送会员宝 手机号&商户号关联关系
					Map<String,String> params = new HashMap<String, String>();
					params.put("jhMid",merchantInfoBean.getMid());
					params.put("merName",merchantInfoBean.getRname());
					String json = "{body:"+JSON.toJSONString(params)+"}";
					log.info("App推送会员宝 手机号&商户号关联 "+json);
					String ss=HttpXmlClient.postForJson(jhTaskHybUrl, json);
					log.info("App基本信息工单审核-聚合商户-推送hrt:"+json+";"+ss);
				}
			}
			if(merchantInfoBean.getLegalPerson()!=null){
				m.setLegalPerson(merchantInfoBean.getLegalPerson().trim());
			}
			if(merchantInfoBean.getBankAccName()!=null){
				m.setContactPerson(merchantInfoBean.getContactPerson().trim());
			}
			if(merchantInfoBean.getRaddr()!=null){
				m.setBaddr(merchantInfoBean.getRaddr().trim());
			}
		}
		String unno=m.getUnno();
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();

		if(merchantInfoBean.isWeChatProg()){
            if(merchantInfoBean.getMaterialUpLoad4() != null ){
                StringBuffer fName1 = new StringBuffer();
                fName1.append(m.getMid());
                fName1.append(".");
                fName1.append(imageDay);
                fName1.append(".1");
                fName1.append(".jpg");
                m.setLegalUploadFileName(fName1.toString());
            }
            if(merchantInfoBean.getMaterialUpLoad5() != null){
                StringBuffer fName2 = new StringBuffer();
                fName2.append(m.getMid());
                fName2.append(".");
                fName2.append(imageDay);
                fName2.append(".2");
                fName2.append(".jpg");
                m.setMaterialUpLoad(fName2.toString());
            }
            if(merchantInfoBean.getMaterialUpLoad6() != null ){
                StringBuffer fName2 = new StringBuffer();
                fName2.append(m.getMid());
                fName2.append(".");
                fName2.append(imageDay);
                fName2.append(".3");
                fName2.append(".jpg");
                m.setMaterialUpLoad1(fName2.toString());
            }
            if(merchantInfoBean.getMaterialUpLoad7() != null ){
                StringBuffer fName2 = new StringBuffer();
                fName2.append(m.getMid());
                fName2.append(".");
                fName2.append(imageDay);
                fName2.append(".4");
                fName2.append(".jpg");
                m.setMaterialUpLoad2(fName2.toString());
            }
            uploadWeChatImg(m,merchantInfoBean,imageDay);
        }

		//上传文件
		if(merchantInfoBean.getLegalUploadFile() != null ){
			StringBuffer fName1 = new StringBuffer();
//			fName1.append(unno);
//			fName1.append(".");
			fName1.append(m.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(".jpg");
			uploadFile(merchantInfoBean.getLegalUploadFile(),fName1.toString(),imageDay);
			m.setLegalUploadFileName(fName1.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoadFile() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoadFile(),fName2.toString(),imageDay);
			m.setMaterialUpLoad(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad1File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".3");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad1File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad1(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad2File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".4");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad2File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad2(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad3File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".5");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad3File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad3(fName2.toString());
		}
		
		if(merchantInfoBean.getBupLoadFile() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".6");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getBupLoadFile(),fName2.toString(),imageDay);
			m.setBupLoad(fName2.toString());
		}
		if(merchantInfoBean.getMaterialUpLoad5File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".7");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad5(fName2.toString());
		}
		//拓展人与法人合影照片
		if(merchantInfoBean.getMaterialUpLoad6File() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(m.getUnno());
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".12");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad6File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad6(fName2.toString());
		}
		//店面门头照
		if(merchantInfoBean.getPhotoUpLoadFile() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(m.getUnno());
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".8");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getPhotoUpLoadFile(),fName2.toString(),imageDay);
			m.setPhotoUpLoad(fName2.toString());
		}	
		//店内经营：registryUpLoadFile
		if(merchantInfoBean.getRegistryUpLoadFile() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(m.getUnno());
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".10");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getRegistryUpLoadFile(),fName2.toString(),imageDay);
			m.setRegistryUpLoad(fName2.toString());
		}
		//桌牌的收银台照片
		if(merchantInfoBean.getMaterialUpLoad7File() != null){
			StringBuffer fNameE = new StringBuffer();
//			fNameE.append(m.getUnno());
//			fNameE.append(".");
			fNameE.append(m.getMid());
			fNameE.append(".");
			fNameE.append(imageDay);
			fNameE.append(".E");
			fNameE.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad7File(),fNameE.toString(),imageDay);
			m.setMaterialUpLoad7(fNameE.toString());
		}
		phoneMicroMerchantInfoDao.updateObject(m);
		String updateSql2="update bill_merchantbankcardinfo t set t.bankaccname='"+m.getBankAccName()+"',t.bankAccNo='"+m.getBankAccNo()+"',t.bankbranch='"+m.getBankBranch()+"',t.paybankid='"+m.getPayBankId()+"' where t.mid='"+m.getMid()+"' and t.mercardtype=0 ";
		merchantInfoDao.executeUpdate(updateSql2);
	}
	@Override
	public void updateMicroMerchantInfoDGL(MerchantInfoModel m,MerchantInfoBean merchantInfoBean) {
		if(merchantInfoBean.getBankAccName()!=null){
			m.setBankAccName(merchantInfoBean.getBankAccName().trim());
		}
		if(merchantInfoBean.getBankAccNo()!=null){
			m.setBankAccNo(merchantInfoBean.getBankAccNo().trim());
		}
		if(merchantInfoBean.getLegalNum()!=null){
			m.setLegalNum(merchantInfoBean.getLegalNum().trim());
		}
		if(merchantInfoBean.getContactPhone()!=null){
			m.setContactPhone(merchantInfoBean.getContactPhone().trim());
		}
		if(merchantInfoBean.getRaddr()!=null){
			m.setRaddr(merchantInfoBean.getRaddr().trim());
		}
		if(merchantInfoBean.getBno()!=null){
			m.setBno(merchantInfoBean.getBno());
		}
		if("Z".equals(m.getApproveStatus())){
			m.setApproveStatus("Z");
		}else{
			m.setApproveStatus("W");
		}
		if(merchantInfoBean.getBankBranch()!=null){
			m.setBankBranch(merchantInfoBean.getBankBranch().trim());
		}
		if(merchantInfoBean.getRaddr()!=null){
			m.setContactAddress(merchantInfoBean.getRaddr().trim());
		}
		
		if(merchantInfoBean.getLegalPerson()!=null){
			m.setLegalPerson(merchantInfoBean.getLegalPerson().trim());
		}
		if(merchantInfoBean.getBankAccName()!=null){
			m.setContactPerson(merchantInfoBean.getBankAccName().trim());
		}
		if(merchantInfoBean.getRaddr()!=null){
			m.setBaddr(merchantInfoBean.getRaddr().trim());
		}
		if(merchantInfoBean.getPayBankId()!=null){
			m.setPayBankId(merchantInfoBean.getPayBankId().trim());
		}
		String unno=m.getUnno();
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		
		//上传文件
		if(merchantInfoBean.getLegalUploadFile() != null ){
			StringBuffer fName1 = new StringBuffer();
//			fName1.append(unno);
//			fName1.append(".");
			fName1.append(m.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(".jpg");
			uploadFile(merchantInfoBean.getLegalUploadFile(),fName1.toString(),imageDay);
			m.setLegalUploadFileName(fName1.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoadFile() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoadFile(),fName2.toString(),imageDay);
			m.setMaterialUpLoad(fName2.toString());
		}
		if(merchantInfoBean.getMaterialUpLoad4File() != null ){
			StringBuffer fName2 = new StringBuffer();
			fName2.append(unno);
			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".9");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad4File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad4(fName2.toString());
		}
		if(merchantInfoBean.getMaterialUpLoad1File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".3");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad1File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad1(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad2File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".4");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad2File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad2(fName2.toString());
		}
		
		if(merchantInfoBean.getMaterialUpLoad3File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".5");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad3File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad3(fName2.toString());
		}
		
		if(merchantInfoBean.getBupLoadFile() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".6");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getBupLoadFile(),fName2.toString(),imageDay);
			m.setBupLoad(fName2.toString());
		}
		if(merchantInfoBean.getMaterialUpLoad5File() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(unno);
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".7");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad5(fName2.toString());
		}
		//拓展人与法人合影照片
		if(merchantInfoBean.getMaterialUpLoad6File() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(m.getUnno());
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".12");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getMaterialUpLoad6File(),fName2.toString(),imageDay);
			m.setMaterialUpLoad6(fName2.toString());
		}
		//店面门头照
		if(merchantInfoBean.getPhotoUpLoadFile() != null ){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(m.getUnno());
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".8");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getPhotoUpLoadFile(),fName2.toString(),imageDay);
			m.setPhotoUpLoad(fName2.toString());
		}	
		//店内经营：registryUpLoadFile
		if(merchantInfoBean.getRegistryUpLoadFile() != null){
			StringBuffer fName2 = new StringBuffer();
//			fName2.append(m.getUnno());
//			fName2.append(".");
			fName2.append(m.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".10");
			fName2.append(".jpg");
			uploadFile(merchantInfoBean.getRegistryUpLoadFile(),fName2.toString(),imageDay);
			m.setRegistryUpLoad(fName2.toString());
		}
		phoneMicroMerchantInfoDao.updateObject(m);
		String updateSql2="update bill_merchantbankcardinfo t set t.bankaccname='"+m.getBankAccName()+"',t.bankAccNo='"+m.getBankAccNo()+"',t.bankbranch='"+m.getBankBranch()+"',t.paybankid='"+m.getPayBankId()+"' where t.mid='"+m.getMid()+"' and t.mercardtype=0 ";
		merchantInfoDao.executeUpdate(updateSql2);
	}
	@Override
	public boolean queryIsHotMerch(MerchantInfoBean merchantInfoBean) {
		String sql ="select count(*) from pg_hotmerch t where t.tname='"+merchantInfoBean.getRname()+"' or t.sn='"+merchantInfoBean.getSn()+"' or t.bankAccNo='"+merchantInfoBean.getBankAccNo()+"' or t.identificationnumber='"+merchantInfoBean.getLegalNum()+"' or t.license='"+merchantInfoBean.getBno()+"' ";
		Integer count=merchantInfoDao.querysqlCounts2(sql, null);
		if(count>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 得到业务员名称
	 */
	private String getSaleName(Integer id) {
		String sql="SELECT SALENAME FROM BILL_AGENTSALESINFO WHERE BUSID="+id;
		List list=merchantInfoDao.queryObjectsBySqlList(sql);
		return ((HashMap)list.get(0)).get("SALENAME").toString();
	}
	
	public Map<String, Object> addQKPayCard(String mid,String accno, String phone){
		String url=admAppIp+"/AdmApp/qkPayCard/qkPayCard_addQKPayCard.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		params.put("accno",accno);
		params.put("phone",phone);
		String ss=HttpXmlClient.post(url, params);
		log.info("添加信用卡:mid="+mid+";accno="+accno+";phone="+phone+";"+ss);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	
	public Map<String, Object> queryQKPayCard(String mid,String page, String size){
		String url=admAppIp+"/AdmApp/qkPayCard/qkPayCard_queryQKPayCard.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		params.put("page",page);
		params.put("size",size);
		String ss=HttpXmlClient.post(url, params);
		log.info("查询快捷卡管理:mid="+mid+";page="+page+";size="+size+";"+ss);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	
	public Map<String, Object> unBQKPayCard(String mid,String qpcid){
		String url=admAppIp+"/AdmApp/qkPayCard/qkPayCard_unBQKPayCard.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("mid",mid);
		params.put("qpcid",qpcid);
		String ss=HttpXmlClient.post(url, params);
		log.info("解除绑定:mid="+mid+";qpcid="+qpcid+";"+ss);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	
	public Map<String, Object> isIntegral(String accno,String type){
		String url=admAppIp+"/AdmApp/qkPayCard/qkPayCard_isIntegral.action";
		Map<String,String> params = new HashMap<String, String>();
		params.put("accno",accno);
		params.put("type",type);
		String ss=HttpXmlClient.post(url, params);
		log.info("查询是否支持积分：accno="+accno+";"+ss);
		JSONObject json = new JSONObject();
		Map<String ,Object> map=(Map<String, Object>) json.parse(ss);
		return map;
	}
	@Override
	public boolean queryManyTimes(MerchantInfoBean merchantInfoBean) {
		String sql ="select count(1) from bill_merchantinfo where maintaindate >= sysdate-interval '2' minute and legalnum ='"+merchantInfoBean.getLegalNum()+"'";
		Integer count=merchantInfoDao.querysqlCounts2(sql, null);
		if(count>0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 判断时间段内是否放开
	 */
	boolean isTime(String time){
//		String str = "0920-0950,1110-1130,1540-1610";
	    String[] list = time.split(",");
	    int times = Integer.parseInt(new SimpleDateFormat("HHmm").format(new Date()));
        log.info("当前时间段："+times);
	    for(String s:list){
	    	String[] lis = s.split("-");
	    	int startTime = Integer.parseInt(lis[0]);
	        int endTime = Integer.parseInt(lis[1]);
	    	if(times-startTime>=0 && endTime-times>=0){
	    		return true;
	    	}
	    }
	    return false;
	}
	/**
	 * 判断机构号是否放开
	 */
	Integer isUnno(String sn,String unnos,Jedis jedis) throws Exception{
		String[] list = unnos.split(",");
		String unno="";
		for(String s:list){
			Map<String,Object> map = new HashMap<String, Object>();
			String[] lis = s.split("-");
	    	unno = lis[0];
	        int times = Integer.parseInt(lis[1]);
			map.put("SN", sn);
			map.put("UNNO", unno);
	        String sql="select sn from bill_terminalinfo bt where bt.rebatetype=18 and unno in( "+
	        " select UNNO from sys_unit start with unno =:UNNO and status = 1 connect by NOCYCLE prior unno = upper_unit "+
	        " )and bt.sn=:SN ";
	        List<Map<String, Object>> li = merchantInfoDao.executeSql2(sql,map);
	        if(li.size()>0){
	        	boolean exis=jedis.hexists("RebateUnno", unno);
	        	log.info("机构"+unno+",放开次数"+times);
	        	if(exis){
	        		int count=Integer.parseInt(jedis.hget("RebateUnno", unno));
		        	log.info("机构："+unno+",已绑定次数："+count);
		        	if(count<times){
		        		jedis.hset("RebateUnno",unno,String.valueOf(count+1));
		        		return 1;
		        	}else{
		        		return 0;
		        	}
	        	}else if(times>0){
	        		jedis.hset("RebateUnno", unno,"1");
	        		return 1;
	        	}
	        }
	    }
		return -1;
	}
	/**
	 * 判断机构号是否放开-new
	 * 
	 */
	Integer isUnnoNew(String sn,String unnos) throws Exception{
		String[] list = unnos.split(",");
		String unno="";
		for(String s:list){
			Map<String,Object> map = new HashMap<String, Object>();
			String[] lis = s.split("-");
			unno = lis[0];
			int times = Integer.parseInt(lis[1]);
			map.put("SN", sn);
			map.put("UNNO", unno);
			String sql="select sn from bill_terminalinfo bt where bt.rebatetype=18 and unno in( "+
					" select UNNO from sys_unit start with unno =:UNNO and status = 1 connect by NOCYCLE prior unno = upper_unit "+
					" )and bt.sn=:SN ";
			List<Map<String, Object>> li = merchantInfoDao.executeSql2(sql,map);
			boolean broken = false;
			ShardedJedis shardedJedis = null;
			if(li.size()>0){
				try {
					shardedJedis = RedisUtil.getShardedJedis();
					boolean exis=shardedJedis.hexists("RebateUnno", unno);
					log.info("机构"+unno+",放开次数"+times);
					if(exis){
						int count=Integer.parseInt(shardedJedis.hget("RebateUnno", unno));
						log.info("机构："+unno+",已绑定次数："+count);
						if(count<times){
							shardedJedis.hset("RebateUnno",unno,String.valueOf(count+1));
							return 1;
						}else{
							return 0;
						}
					}else if(times>0){
						shardedJedis.hset("RebateUnno", unno,"1");
						return 1;
					}
				} catch (Exception e) {
					log.info("判断机构是否放开异常"+e.getMessage());
					broken = true;
				}finally {
					if(shardedJedis != null) {
						RedisUtil.delShardedJedis(broken, shardedJedis);
					}
				}
			}
		}
		return -1;
	}
	
	/**
	 * @return 1通过
	 */
	@Override
	public String queryAgainSaveTer(MerchantInfoBean merchantInfoBean) {
		try {
			if(PhoneProdConstant.PHONE_MD.equals(merchantInfoBean.getAgentId())) {
				TerminalInfoModel ter = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where sn=? and maintainType!='D'", new Object[] {merchantInfoBean.getSn()});
				//秒到添加拦截，其余不拦
				// @author:lxg-20190716 秒到设备不能帮plus设备 添加81,82
				if(null!=ter && null!=ter.getRebateType() && ".22.23.81.82.".contains("."+ter.getRebateType()+".")){
					return "设备类型不符，请绑定会员宝秒到设备";
				}else if(null!=ter && null!=ter.getRebateType()){
					// @author:lxg-20191015 去除极速秒到的设备
					if(isRebateTypeBelongToSpeedMd(ter.getRebateType())){
						return "设备类型不符，请绑定会员宝秒到设备";
					}
				}
				MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantInfoBean.getMid()});
				
				if(m!=null&&"000000".equals(m.getUnno())) {//微商户增机，不管；初次绑定设备，需判定
					//活动66设备，单独判定是否3个月内有交易进行绑定20191021
					if(ter!=null &&ter.getRebateType()!=null && ter.getRebateType()==66){
						if(m!=null) {
							//判断身份证是否三个月内交易过
							String transactionSql = "select count(1) from BILL_IDCARD_TXN t where 1=1"+
										" and t.legalNum = '"+m.getLegalNum()+"'"+
										" and t.txnday between to_char(add_months(sysdate,-3),'yyyyMMdd') and to_char(sysdate,'yyyyMMdd')"+
										" and t.type = 2";
							Integer i1 = phoneMicroMerchantInfoDao.querysqlCounts2(transactionSql,null);
							if(i1>0) {
								merchantInfoBean.setIsRebate(0);
							}else {
								merchantInfoBean.setIsRebate(1);
							}
							return null;
						}
						return "该商户不存在，请核查！";
					}
					//活动93,94设备，单独判定是否3个月内有交易进行绑定20191219
					else if(ter!=null &&ter.getRebateType()!=null && (ter.getRebateType()==93 || ter.getRebateType()==94)){
						if(m!=null) {
							//判断身份证是否三个月内交易过
							String transactionSql = "select count(1) from BILL_IDCARD_TXN t where 1=1"+
										" and t.legalNum = '"+m.getLegalNum()+"'"+
										" and t.txnday between to_char(add_months(sysdate,-3),'yyyyMMdd') and to_char(sysdate,'yyyyMMdd')"+
										" and t.type = 3";
							Integer i1 = phoneMicroMerchantInfoDao.querysqlCounts2(transactionSql,null);
							if(i1>0) {
								merchantInfoBean.setIsRebate(0);
							}else {
								merchantInfoBean.setIsRebate(1);
							}
							return null;
						}
						return "该商户不存在，请核查！";
					}
					else if(ter!=null && ter.getRebateType()!=null && (ter.getRebateType()==98 || ter.getRebateType()==99)){
						if(validateHasTxnByMonth(m.getLegalNum(),PhoneProdConstant.PHONE_MD)){
							return "您已经拥有其他商户，不可绑定设备到新商户！";
						}else{
							merchantInfoBean.setIsRebate(1);
							return null;
						}
					}
					
					if(ter!=null){
						if(ter!=null&&ter.getRebateType()!=null
	                       &&(ter.getRebateType()==18)) {//限制活动18 20190627
							
							//ztt-20200220-redis连接统一改造
							log.info("校验商户"+merchantInfoBean.getMid());
							String rebateInfoByRedis = RedisUtil.getRebateInfoByRedis();
							String[] split = rebateInfoByRedis.split("&");
							String split0 = split[0];//限制开关
							String split1 = split[1];//放开区段
							String split2 = split[2];//放开机构
							String split3 = split[3];//放开时间
							
							if("true".equals(split0)) {
								if(!isTime(split3)) {
									Integer isTure = isUnnoNew(ter.getSn(),split2);
									log.info("机构限制返回状态："+isTure);
									if(isTure==0){
										return limitRebateType();
								    }else if(isTure==-1){
								    	if(null!=ter.getSn() && !split1.contains(merchantInfoBean.getMid().substring(merchantInfoBean.getMid().length()-2))){
											return limitRebateType();
								    	}
								    }
								}
							}
						}
						//同一个身份证是否绑定过shoushua设备
						Map map1 = new HashMap(16);
						map1.put("legalNum",m.getLegalNum());
						List<MerchantInfoModel> list = merchantInfoDao.queryObjectsBySqlList("select a.* from bill_merchantinfo a,bill_merchantterminalinfo b where a.mid=b.mid and a.legalnum=:legalNum and a.ism35=1 and a.settmethod!='000000' and NOT exists (SELECT * FROM WX b WHERE a.unno = b.unno )",  map1, MerchantInfoModel.class);
						if(list.size()==0) {
							//到这，可绑定，调用是否返现（因为能过走到这里的目前都是非押金设备，所以直接判断是否半年交易就行）
							if(judgeIsReturnCash(map1,ter)) {
								merchantInfoBean.setIsRebate(1);
							}else {
								merchantInfoBean.setIsRebate(0);
							}
							return null;
						}else {
							//判断身份证是否三个月内交易过
							String transactionSql = "select count(1) from BILL_IDCARD_TXN t where 1=1"+
										" and t.legalNum = '"+m.getLegalNum()+"'"+
										" and t.txnday between to_char(add_months(sysdate,-3),'yyyyMMdd') and to_char(sysdate,'yyyyMMdd')"+
										" and (t.type = 1 or t.type is null)";
							Integer i1 = phoneMicroMerchantInfoDao.querysqlCounts2(transactionSql,null);
							if(i1>0) {
								for(MerchantInfoModel merchantInfoModel : list) {
									//you交易
									String judgeIsUnlevelSql = " SELECT COUNT(1) FROM ( SELECT unno FROM sys_unit a WHERE a.un_lvl = 2 "+
											" START WITH unno = '"+merchantInfoModel.getUnno()+"' CONNECT BY unno = PRIOR upper_unit ) t1,"+
											" ( SELECT unno FROM sys_unit a WHERE a.un_lvl = 2 START WITH unno = '"+ter.getUnNO()+"' "+
											" CONNECT BY unno = PRIOR upper_unit ) t2 WHERE t1.unno = t2.unno";
									Integer i2 = merchantInfoDao.querysqlCounts2(judgeIsUnlevelSql,null);
									if(i2>0) {//判断是否为同一个一代
										if(judgeIsReturnCash(map1,ter)) {
											merchantInfoBean.setIsRebate(1);
										}else {
											merchantInfoBean.setIsRebate(0);
										}
										return null;
									}else if(ter.getUnNO().equals(formatUnno(merchantInfoModel.getUnno()))){//判断是否是同一新老平台
										if(judgeIsReturnCash(map1,ter)) {
											merchantInfoBean.setIsRebate(1);
										}else {
											merchantInfoBean.setIsRebate(0);
										}
										return null;
									}
								}
								//到这说明既不是同一代，也不是同一平台，就不能绑定设备
								return "您已经拥有其他商户，不可绑定设备到新商户！";
							}else {
								//fou交易
								if(judgeIsReturnCash(map1,ter)) {
									merchantInfoBean.setIsRebate(1);
								}else {
									merchantInfoBean.setIsRebate(0);
								}
								return null;
							}
						}
					}else {
						return "该POS:SN号不存在,请联系销售！";
					}
				}else {
					merchantInfoBean.setIsRebate(1);
					return null;
				}
			}else if(PhoneProdConstant.PHONE_JS.equals(merchantInfoBean.getAgentId())){
				return validateTerminalBySpeedMd(merchantInfoBean);
			}else if(PhoneProdConstant.PHONE_PRO.equals(merchantInfoBean.getAgentId())){
				return validateTerminalByPro(merchantInfoBean);
			}else {
				merchantInfoBean.setIsRebate(1);
				return null;
			}
		}catch(Exception e) {
			log.error("绑定设备，判断是否可以再次绑定出现异常:mid="+merchantInfoBean.getMid());
		}
		return "您已经拥有其他商户，不可绑定设备到新商户！";
	}

	/**
	 * 可绑定设备是否返现判断20191021(20191022将设备判断是否押金判断后移)
	 * @return
	 */
	public boolean judgeIsReturnCash(Map map,TerminalInfoModel ter) {
		if(ter!=null&&ter.getRebateType()!=null
                &&(ter.getRebateType()==14||ter.getRebateType()==15||ter.getRebateType()==98||ter.getRebateType()==99)) {//绑定设备是否是优享设备
			return true;
		}else {
			Integer i1 = phoneMicroMerchantInfoDao.querysqlCounts2("select count(1) from BILL_IDCARD_TXN t where t.legalNum = :legalNum and (t.type = 1 or t.type is null)", map);
			if(i1>0) {
				return false;
			}
			return true;
		}
	}
	/**
	 * 活动限制
	 * @return
	 */
	public String limitRebateType(){
		//ztt-20200220-redis连接统一改造
		//JedisSource rource = new JedisSource();
		//Jedis jedis = rource.getJedis();
		boolean broken = false;
		ShardedJedis shardedJedis = null;
		String msg=null;
		try {
			shardedJedis = RedisUtil.getShardedJedis();
			log.info("活动限制信息读取redis参数，读取结果为" + shardedJedis.hget("RebateInfo", "info2"));
			String[] list = shardedJedis.hget("RebateInfo", "info2").split("\\|");
			Random r = new Random();
			msg=list[r.nextInt(list.length)];
			log.info("本次拦截原因："+msg);
		} catch (Exception e) {
			msg="绑定失败，已注册我司其它产品";
			log.info("限制活动18信息请求redis出现异常"+e.getMessage());
			log.error(e);
			broken = true;
		} finally {
		//	JedisSource.returnResource(jedis);
			if(shardedJedis != null) {
				RedisUtil.delShardedJedis(broken, shardedJedis);
			}
		}
		return msg;
	}

	/**
	 * 某身份证号下边bno产品商户一个月内是否存在交易
	 * @param legalNum 身份证号
	 * @param bno 产品类型
	 * @return
	 */
	public boolean validateHasTxnByMonth(String legalNum,String bno){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar t = Calendar.getInstance();
		String currentDate = df.format(t.getTime());
		String currentStart=df.format(DateTools.getStartMonth(t.getTime()));

		t.add(Calendar.DAY_OF_MONTH,-30);
		String upDate = df.format(t.getTime());
		String upEnd=df.format(DateTools.getEndtMonth(t.getTime()));
		String txnSql="select t.txnamount from check_unitdealdata_"+currentDate.substring(4,6)+" t" +
				"  where t.mid in (select k.mid from  bill_merchantinfo k where k.legalnum=:legalnum and k.bno='"+bno+"') " +
				" and t.txnday>=:curStart and t.txnday<=:curEnd";
		Map param = new HashMap();
		param.put("curStart",currentStart);
		param.put("curEnd",currentDate);
		param.put("legalnum",legalNum);
		if(!currentDate.substring(4,6).equals(upDate.substring(4,6))){
			txnSql+=" union all select t1.txnamount from check_unitdealdata_"+upDate.substring(4,6)+" t1 " +
					" where t1.mid in (select k.mid from  bill_merchantinfo k where k.legalnum=:legalnum and k.bno='"+bno+"') " +
					" and t1.txnday>=:upStart and t1.txnday<=:upEnd ";
			param.put("upStart",upDate);
			param.put("upEnd",upEnd);
		}
		List list = terminalInfoDao.executeSql2(txnSql,param);
		return list.size()>0;
	}

	/**
	 * 极速秒到终端增机校验
	 * @param merchantInfoBean
	 */
	public String validateTerminalBySpeedMd(MerchantInfoBean merchantInfoBean){
		MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantInfoBean.getMid()});
        // @author:lxg-20191017 看该身份证下,是否有秒到商户，有秒到商户，看一个月内是否有交易，有交易不允许注册新机器
        if(m!=null&&"000000".equals(m.getUnno())) {
			if(validateHasTxnByMonth(m.getLegalNum(),PhoneProdConstant.PHONE_MD)){
				return "您已经拥有其他商户，不可绑定设备到新商户！";
			}

        }

		if(m!=null) {
			TerminalInfoModel ter = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where sn=? and maintainType!='D'", new Object[] {merchantInfoBean.getSn()});
			if(ter!=null && ter.getRebateType()!=null){
				if(!isRebateTypeBelongToSpeedMd(ter.getRebateType())){
					// @author:lxg-20191015 设备不属于极速版产品
					return "设备类型不符，请绑定会员宝极速版设备。";
				}
			}else if(ter!=null) {
				return "设备类型不符，请绑定会员宝极速版设备。";
			}else{
				return "该POS:SN号不存在,请联系销售！";
			}
		}else if(m==null){
			return "商户信息不存在！";
		}
		merchantInfoBean.setIsRebate(0);
		return null;
	}

    /**
     * PRO商户增机规则校验
     * @param merchantInfoBean
     * @return
     */
	private String validateTerminalByPro(MerchantInfoBean merchantInfoBean){
		MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantInfoBean.getMid()});
//		if(m!=null&&"000000".equals(m.getUnno())) {
//			if(validateHasTxnByMonth(m.getLegalNum(),PhoneProdConstant.PHONE_MD)){
//				return "您已经拥有其他商户，不可绑定设备到新商户！";
//			}
//		}

		if(m!=null) {
			TerminalInfoModel ter = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where sn=? and maintainType!='D'", new Object[] {merchantInfoBean.getSn()});
			if(ter!=null && ter.getRebateType()!=null){
				if(!isRebateTypeBelongToPro(ter.getRebateType())){
					return "设备类型不符，请绑定会员宝Pro设备。";
				}
			}else if(ter!=null) {
				return "设备类型不符，请绑定会员宝Pro设备。";
			}else{
				return "该POS:SN号不存在,请联系销售！";
			}
		}else if(m==null){
			return "商户信息不存在！";
		}
		merchantInfoBean.setIsRebate(0);
		return null;
	}

	public boolean isRebateTypeBelongToSpeedMd(Integer rebateType){
		String speedHasType = "select t.valueinteger from bill_purconfigure t where t.type=8 and t.status=1 and t.valueinteger=:valueinteger ";
		Map param = new HashMap();
		param.put("valueinteger",rebateType);
		List<Map<String,Object>> result = merchantInfoDao.queryObjectsBySqlListMap2(speedHasType, param);
		if (result.size()>0) {
			return true;
		}else{
			return false;
		}
	}

	private boolean isRebateTypeBelongToPro(Integer rebateType){
		String proHasType = "select t.valueinteger from bill_purconfigure t where t.type=11 and t.status=1 and t.valueinteger=:valueinteger ";
		Map param = new HashMap();
		param.put("valueinteger",rebateType);
		List<Map<String,Object>> result = merchantInfoDao.queryObjectsBySqlListMap2(proHasType, param);
		if (result.size()>0) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @return返回连刷行业类别
	 */
	@Override
	public List<Object> listAgentMccid() {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.* from agent_mccid a where a.mcclvl=1";
		List<Map<String,Object>> list1 = merchantInfoDao.queryObjectsBySqlListMap2(sql, null);
		String sql1 = "select a.* from agent_mccid a where a.parentBamid=";
		for (Map<String, Object> map : list1) {
			List<Map<String,Object>> list2 = merchantInfoDao.queryObjectsBySqlListMap2(sql1+map.get("BAMID"), null);
			map.put("mccType", list2);
			list.add(map);
		}
		return list;
	}

	@Override
	public JsonBean updateSelectSn(MerchantInfoBean merchantInfoBean){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", merchantInfoBean.getMid());
		map.put("sn", merchantInfoBean.getSn());
		JSONObject json = new JSONObject();
		String ss=HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/hrttermacc_selectUniqueSn.action", map);
		map = (Map<String, String>) json.parse(ss);
		JsonBean jsonBean = new JsonBean();
		if("1".equals(map.get("status"))) {
			terminalInfoDao.executeUpdate("update bill_terminalinfo set isselect=0 where sn in (select sn from bill_merchantterminalinfo where mid='"+merchantInfoBean.getMid()+"')");
			terminalInfoDao.executeUpdate("update bill_terminalinfo set isselect=1 where sn = '"+merchantInfoBean.getSn()+"'");
			jsonBean.setMsg("设定默认设备成功");
			jsonBean.setSuccess(true);
		}else {
			jsonBean.setMsg("设定默认设备失败");
			jsonBean.setSuccess(false);
		}
		return jsonBean;
	}

	@Override
	public JsonBean findReturnRecord(MerchantInfoBean merchantInfoBean){
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", merchantInfoBean.getMid());
		map.put("sn", merchantInfoBean.getSn());
		map.put("page", merchantInfoBean.getPage()+"");
		map.put("size", merchantInfoBean.getRows()+"");
		JSONObject json = new JSONObject();
		String ss=HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/hrttermacc_snReturnRecord.action", map);
		map = (Map<String, String>) json.parse(ss);
		JsonBean jsonBean = new JsonBean();
		if("1".equals(map.get("status"))) {
			jsonBean.setMsg("查询激活返现记录成功");
			jsonBean.setSuccess(true);
			jsonBean.setObj(map);
		}else {
			jsonBean.setMsg("查询激活返现记录失败");
			jsonBean.setSuccess(false);
		}
		return jsonBean;
	}

	/**
	 * sn交易量查询
	 * @param merchantInfoBean
	 * @return
	 */
	@Override
	public JsonBean querySnSumsamt(MerchantInfoBean merchantInfoBean){
		JsonBean jsonBean = new JsonBean();
		//查询综合sn交易量
		Map<String,String> map = new HashMap<String, String>();
		map.put("sn", merchantInfoBean.getSn());
		map.put("mid", merchantInfoBean.getMid());
		log.info("sn交易量查询请求综合参数mid="+merchantInfoBean.getMid()+",sn="+merchantInfoBean.getSn());
		String post = HttpXmlClient.post(admAppIp+"/AdmApp/bank/bankTermAcc_querySnSumsamt.action", map);
		log.info("sn交易量查询请求综合返回参数:"+post);
		jsonBean=JSONObject.parseObject(post,JsonBean.class);
//		Boolean flag = (Boolean) ((Map<String, Object>) JSONObject.parse(post)).get("success");
//		if(flag==false){
//			jsonBean.setMsg(String.valueOf(((Map<String, Object>) JSONObject.parse(post)).get("msg")));
//			jsonBean.setSuccess(false);
//		}else{
//			jsonBean.setMsg(String.valueOf(((Map<String, Object>) JSONObject.parse(post)).get("msg")));
//			jsonBean.setSuccess(true);
//			jsonBean.setObj((Map)((Map<String, Object>) JSONObject.parse(post)).get("obj"));
//		}
		return jsonBean;
	}

	@Override
	public synchronized String limitForTerms(Integer txnType,String prod){
		JedisSource rource = new JedisSource();
		Jedis jedis = rource.getJedis();
		try {
			if(txnType.intValue()==1){
				BigDecimal ZFB=new BigDecimal(jedis.hget("limitForTerms","ZFB"));
				BigDecimal WX=new BigDecimal(jedis.hget("limitForTerms","WX"));
				BigDecimal ALL=ZFB.add(WX);
				BigDecimal LIMIT=new BigDecimal(jedis.hget("RebateInfo","TXNLIMIT"));
				log.info("获取redis中实时累计交易额ZFB["+ZFB+"],WX["+WX+"],限制金额["+LIMIT+"]");
				if(ALL.compareTo(LIMIT)>0){
					if(".10000.10002.10006.".contains("."+prod+".")){
						// 秒到、联刷、PLUS：
						// 因系统升级，今日扫码交易T+1到账，明日恢复秒到。为不影响您的资金使用，请您今日优先选择其他支付方式。
						return jedis.hget("RebateInfo","LS_MD_PLUS_TIP");
					}else if(PhoneProdConstant.PHONE_SYT.equals(prod)){
						//			收银台：
						// 因系统升级，今日扫码交易T+1到账，明日恢复秒到。
						return jedis.hget("RebateInfo","SYT_TIP");
					}
				}
			}
		} catch (Exception e) {
			log.info("查询交易限额异常："+e.getMessage());
		} finally {
			JedisSource.returnResource(jedis);
		}
		return null;
	}
	/**
	 * 花呗分期
	 * @return
	 */
	public Map<String,Object> huaBeiParam() throws Exception{
		return RedisUtil.queryHuaBeiInfo(PhoneProdConstant.PHONE_PLUS);
	}

	@Override
	public Map<String, Object> huaBeiParamInfo(String productType) throws Exception{
		return RedisUtil.queryHuaBeiInfo(productType);
	}

	public String updateTermAcceptDate(String sn,Date acceptDate){
		String hql="from TerminalInfoModel where sn=?";
		TerminalInfoModel terminalInfoModel=terminalInfoDao.queryObjectByHql(hql,new Object[]{sn});
		if(terminalInfoModel!=null){
//			if(terminalInfoModel.getAcceptDate()==null){
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				String accept=df.format(acceptDate);
				terminalInfoModel.setAcceptDate(accept);
				terminalInfoDao.updateObject(terminalInfoModel);
				log.info("sn签收日期推送请求AdmApp参数:acceptDate="+acceptDate+",sn="+sn);
				Map paramMap=new HashMap();
				paramMap.put("sn",sn);
				paramMap.put("snsigndate",accept);
				String post = HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/hrttermacc_snUpdateSignDate.action", paramMap);
				log.info("sn签收日期推送返回参数:"+post);
//				JSONObject jsonObject=JSONObject.parseObject(post);
//				if(jsonObject!=null){
//					if("1".equals(jsonObject.getString("status"))){
//						return null;
//					}else{
//						return "sn推送失败";
//					}
//				}
//			}else{
//				return "该sn签收日期已存在!";
//			}
		}else{
			return "sn信息不存在!";
		}
		return null;
	}
	
	@Override
	public String queryIsSpecialUnno(MerchantInfoBean merchantInfoBean) {
		boolean broken = false;
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = RedisUtil.getShardedJedis();
			String limitSwitch = shardedJedis.hget("limitForTerms", "limitSpicalUnno_switch");
			String limitUnno = shardedJedis.hget("limitForTerms", "limitSpicalUnno");
			String limitInfo = shardedJedis.hget("limitForTerms", "limitSpicalUnno_info");
			log.info("特殊机构限制设备使用信息读取redis参数，读取结果为" +limitSwitch+","+limitUnno+","+limitInfo);
			if(!"1".equals(limitSwitch)) {
				return null;
			}
			if(limitUnno==null) {
				return null;
			}
			String isSpecialUnnoSntypeSql = " SELECT count(1) FROM (SELECT (case when(ht.sn like 'HYB1%' or ht.sn like 'HYB4%' or ht.sn like 'HYB5%' or ht.sn like 'HYB0%'" + 
					" or ht.sn like 'HYB7%' or ht.sn like 'HYB8%' or ht.sn like 'AACA%' or ht.sn like 'BBCA%' or ht.sn like 'HYB2%' or ht.sn like 'HYB3%')"+
					" then 1 else 2 end)sn_type,(SELECT count(1) FROM ppusr.sys_unit u where u.unno = ht.unno"+
					" start with u.unno in("+limitUnno+") connect by prior u.unno = u.upper_unit) isSpecialUnno"+
					" FROM ppusr.bill_terminalinfo ht where 1=1 and ht.sn = :sn)"+
					" where sn_type =1"+
					" and isSpecialUnno =1";
			Map param = new HashMap();
			param.put("sn",merchantInfoBean.getSn());
			Integer querysqlCounts2 = terminalInfoDao.querysqlCounts2(isSpecialUnnoSntypeSql, param);
			if(querysqlCounts2>0) {
				return limitInfo;
			}
		} catch (Exception e) {
			log.info("特殊机构限制设备使用信息读取redis出现异常"+e.getMessage());
			broken = true;
		} finally {
			if(shardedJedis != null) {
				RedisUtil.delShardedJedis(broken, shardedJedis);
			}
		}
		return null;
	}
}
