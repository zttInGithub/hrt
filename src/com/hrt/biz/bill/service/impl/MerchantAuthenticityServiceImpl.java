package com.hrt.biz.bill.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.threads.PushTaskThreadPools;
import com.hrt.biz.threads.ReceiveRepayBDThread;
import com.hrt.util.FileZipUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.argorse.security.core.hash.MD5Wrapper;
import com.hrt.biz.bill.dao.IMerchantAuthenticityDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.MerchantAuthenticityModel;
import com.hrt.biz.bill.entity.pagebean.MerchantAuthenticityBean;
import com.hrt.biz.bill.service.IMerchantAuthenticityService;
import com.hrt.biz.util.AES;
import com.hrt.biz.util.Base64;
import com.hrt.biz.util.BaseDataUtil;
import com.hrt.biz.util.HttpPostMap;
import com.hrt.biz.util.JSONArray;
import com.hrt.biz.util.JSONObject;
import com.hrt.biz.util.ParamUtil;
import com.hrt.biz.util.SignatureUtil;
import com.hrt.biz.util.auth.SfidAuthMain;
import com.hrt.biz.util.gateway.MD5Util;
import com.hrt.biz.util.kaola.HttpUtil;
import com.hrt.biz.util.kaola.SignUtil;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.redis.JedisSource;
import com.hrt.redis.RedisUtil;
import com.hrt.util.HttpXmlClient;

import redis.clients.jedis.Jedis;

public class MerchantAuthenticityServiceImpl implements IMerchantAuthenticityService {

	private IMerchantAuthenticityDao merchantAuthenticityDao;
	private IMerchantInfoDao merchantInfoDao;
	private IUnitDao unitDao;
	private SfidAuthMain sfidAuthMain;
	private static final Log log = LogFactory.getLog(MerchantAuthenticityServiceImpl.class);
	private String msUrl;
	private String jxUrl;
	private String JXmiyao;
	private String JXmerchno;
	private String hsUrl;
	private String hybMerAuthUrl;
	private ParamUtil paramUtil;
	private String serverAddress; // "https://prmcap.eveus.com"
	private String keyStorePath ; // "D:/certs/"
	private String kaolaAddress;
	private String customerId;
	private String yidaoAddress;
	private String yidaoAppId;
	private String yidaoAppKey;
	private String juheAddress;
	private String juheAppKey;
	private String juheOpenId;
	private String admAppIp;

	public String getAdmAppIp() {
		return admAppIp;
	}
	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}
	public String getYidaoAddress() {
		return yidaoAddress;
	}
	public void setYidaoAddress(String yidaoAddress) {
		this.yidaoAddress = yidaoAddress;
	}
	public String getYidaoAppId() {
		return yidaoAppId;
	}
	public void setYidaoAppId(String yidaoAppId) {
		this.yidaoAppId = yidaoAppId;
	}
	public String getYidaoAppKey() {
		return yidaoAppKey;
	}
	public void setYidaoAppKey(String yidaoAppKey) {
		this.yidaoAppKey = yidaoAppKey;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getKaolaAddress() {
		return kaolaAddress;
	}
	public void setKaolaAddress(String kaolaAddress) {
		this.kaolaAddress = kaolaAddress;
	}
	public String getJXmerchno() {
		return JXmerchno;
	}
	public void setJXmerchno(String jXmerchno) {
		JXmerchno = jXmerchno;
	}
	public String getJXmiyao() {
		return JXmiyao;
	}
	public void setJXmiyao(String jXmiyao) {
		JXmiyao = jXmiyao;
	}
	public String getJxUrl() {
		return jxUrl;
	}
	public void setJxUrl(String jxUrl) {
		this.jxUrl = jxUrl;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public String getKeyStorePath() {
		return keyStorePath;
	}
	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}
	public String getHsUrl() {
		return hsUrl;
	}
	public void setHsUrl(String hsUrl) {
		this.hsUrl = hsUrl;
	}
	public ParamUtil getParamUtil() {
		return paramUtil;
	}
	public void setParamUtil(ParamUtil paramUtil) {
		this.paramUtil = paramUtil;
	}
	public String getHybMerAuthUrl() {
		return hybMerAuthUrl;
	}
	public void setHybMerAuthUrl(String hybMerAuthUrl) {
		this.hybMerAuthUrl = hybMerAuthUrl;
	}
	public String getMsUrl() {
		return msUrl;
	}
	public void setMsUrl(String msUrl) {
		this.msUrl = msUrl;
	}
	public IMerchantAuthenticityDao getMerchantAuthenticityDao() {
		return merchantAuthenticityDao;
	}
	public SfidAuthMain getSfidAuthMain() {
		return sfidAuthMain;
	}
	public String getJuheAddress() {
		return juheAddress;
	}
	public void setJuheAddress(String juheAddress) {
		this.juheAddress = juheAddress;
	}
	public String getJuheAppKey() {
		return juheAppKey;
	}
	public void setJuheAppKey(String juheAppKey) {
		this.juheAppKey = juheAppKey;
	}
	public String getJuheOpenId() {
		return juheOpenId;
	}
	public void setJuheOpenId(String juheOpenId) {
		this.juheOpenId = juheOpenId;
	}

	public void setSfidAuthMain(SfidAuthMain sfidAuthMain) {
		this.sfidAuthMain = sfidAuthMain;
	}

	public void setMerchantAuthenticityDao(
			IMerchantAuthenticityDao merchantAuthenticityDao) {
		this.merchantAuthenticityDao = merchantAuthenticityDao;
	}
	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}
	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}
	public IUnitDao getUnitDao() {
		return unitDao;
	}
	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}
	
	/**
	 * 查看明细(查看 认证失败后 上传的照片)
	 * @param id
	 */
	public MerchantAuthenticityBean queryTxnAuthenticityById(Integer id){
		MerchantAuthenticityModel mm=(MerchantAuthenticityModel) merchantAuthenticityDao.queryObjectByHql("from MerchantAuthenticityModel m where m.authType='TXN' and m.bmatid=?", new Object[]{id});
		MerchantAuthenticityBean mb = new MerchantAuthenticityBean(); 
		BeanUtils.copyProperties(mm, mb);
		mb.setAuthUpload(queryUpLoadPath()+File.separator+mm.getAuthUpload());
		return mb; 
	}
	
	/**
	 * 查询商户认证信息（未/已认证）
	 */
	@Override
	public DataGridBean queryMerchantAuthenticity(MerchantAuthenticityBean matb, UserBean user) throws Exception {
		
		String sql = "";
		String sqlCount = "";
		boolean date_flag = false ;
		Map<String, Object> map = new HashMap<String, Object>();
		sql = "SELECT m FROM  MerchantAuthenticityModel m WHERE  1=1 ";
		sqlCount = "SELECT COUNT(*) FROM  BILL_MERAUTHINFO m WHERE  1=1  ";
		
		if (matb.getUsername() != null&&!"".equals(matb.getUsername())) {
			sql +=" and m.username like :userName ";
			sqlCount +=" and m.username like :userName ";
			map.put("userName", '%'+matb.getUsername()+'%');
			date_flag = true ;
		}
		if (matb.getAuthType() != null&&!"".equals(matb.getAuthType())) {
			sql +=" and m.authType =:authType ";
			sqlCount +=" and m.authType =:authType ";
			map.put("authType", matb.getAuthType());
		}
		if (matb.getCardName() != null&&!"".equals(matb.getCardName())) {
			sql +=" and m.cardName like :cardName ";
			sqlCount +=" and m.cardName like :cardName ";
			map.put("cardName", '%'+matb.getCardName()+'%');
			date_flag = true ;
		}	
		if (matb.getBankAccName() != null&&!"".equals(matb.getBankAccName())) {
			sql +=" and m.bankAccName like :bankAccName ";
			sqlCount +=" and m.bankAccName like :bankAccName ";
			map.put("bankAccName", '%'+matb.getBankAccName()+'%');
			date_flag = true ;
		}	
		if (matb.getMid() != null && !"".equals(matb.getMid())) {
			sql +=" and m.mid =:mid ";
			sqlCount +=" and m.mid =:mid ";
			map.put("mid", matb.getMid());
			date_flag = true ;
		}	
		if (matb.getSysseqnb()!= null && !"".equals(matb.getSysseqnb())) {
			sql +=" and m.sysseqnb =:sysseqnb ";
			sqlCount +=" and m.sysseqnb =:sysseqnb  ";
			map.put("sysseqnb", matb.getSysseqnb());
			date_flag = true ;
		}
		if (matb.getBankAccNo()!= null && !"".equals(matb.getBankAccNo())) {
			sql +=" and m.bankAccNo =:bankAccNo ";
			sqlCount +=" and m.bankAccNo =:bankAccNo  ";
			map.put("bankAccNo", matb.getBankAccNo());
			date_flag = true ;
		}
		if (matb.getSendType()!= null && "1".equals(matb.getSendType())) {
			sql +=" and m.mid not like 'HRTSYT%' ";
			sqlCount +=" and m.mid not like 'HRTSYT%' ";
			date_flag = true ;
		}else if(matb.getSendType()!= null && "2".equals(matb.getSendType())) {
			sql +=" and m.mid like 'HRTSYT%' ";
			sqlCount +=" and m.mid like 'HRTSYT%' ";
			date_flag = true ;
		}
		if(matb.getStatus()== null || "".equals(matb.getStatus())){
			//所有
		}else if(matb.getStatus()== "00" || "00".equals(matb.getStatus())){
			//认证成功
			sql +=" and m.status ='00' and m.respcd='2000' ";
			sqlCount +=" and m.status ='00' and m.respcd='2000' ";
		}else if(matb.getStatus()== "01" || "01".equals(matb.getStatus())){
			//认证失败已上传
			sql +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is not null and m.approveNote is not null ";
			sqlCount +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is not null and m.approveNote is not null ";
		}else if(matb.getStatus()== "02" || "02".equals(matb.getStatus())){
			//认证失败已上传待审
			sql +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is not null and m.approveNote is null ";
			sqlCount +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is not null and m.approveNote is null ";
		}else if(matb.getStatus()== "03" || "03".equals(matb.getStatus())){
			//认证失败未上传
			sql +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is null ";
			sqlCount +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is null ";
		}else if(matb.getStatus()== "10" || "10".equals(matb.getStatus())){
			//认证中
			sql +=" and m.status ='01' ";
			sqlCount +=" and m.status ='01'  ";
		}else if(matb.getStatus()== "11" || "11".equals(matb.getStatus())){
			//未认证(null/03)
			sql +="  and (m.status is null  or m.status='03' )  ";
			sqlCount +="  and (m.status is null  or m.status='03' ) ";
			//map.put("status", matb.getStatus());
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		if(matb.getCdate()== null && "".equals(matb.getCdate())&&date_flag == true){
			//当有其他条件且时间为空时 ，查寻 不考虑时间
		}else if (matb.getCdate()!= null && !"".equals(matb.getCdate())) {
			sql +=" and m.cdate between to_date('"+df.format(matb.getCdate())+" 000000','yyyy-mm-dd hh24miss') and to_date('"+df.format(matb.getCdate())+" 235959','yyyy-mm-dd hh24miss')" ;
			sqlCount +=" and m.cdate between to_date('"+df.format(matb.getCdate())+" 000000','yyyy-mm-dd hh24miss') and to_date('"+df.format(matb.getCdate())+" 235959','yyyy-mm-dd hh24miss')" ;
		}else if (date_flag == false ){
			//查询当天 to_date('1987-09-18','yyyy-mm-dd')
//			sql +=" and m.cdate >=  to_date('"+df.format(new Date())+"','yyyy-mm-dd hh24miss')" ;
//			sqlCount +=" and m.cdate >=  to_date('"+df.format(new Date())+"','yyyy-mm-dd hh24miss')" ;
			sql +=" and m.cdate >=  trunc(sysdate) " ;
			sqlCount +=" and m.cdate >=  trunc(sysdate) " ;
		}
		
		if (matb.getSort() != null) {
			sql += " ORDER BY " + matb.getSort() + " " + matb.getOrder();
		}
		List<MerchantAuthenticityModel> MerchantAList = merchantAuthenticityDao.querySQLMerchantAuthenticity(sql, map, matb.getPage(), matb.getRows(), MerchantAuthenticityModel.class);
		BigDecimal count = merchantAuthenticityDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(MerchantAList, count.intValue());
		
		return dataGridBean;  
	}
	
	/**
	 * 查询交易认证信息
	 */
	@Override
	public DataGridBean queryTxnAuthenticity(MerchantAuthenticityBean matb, UserBean user) throws Exception {
		
		String sql = "";
		String sqlCount = "";
		boolean date_flag = false ;
		Map<String, Object> map = new HashMap<String, Object>();
		sql = "SELECT m FROM  MerchantAuthenticityModel m WHERE  ( m.unno ='110000' or m.unno is null ) ";
		sqlCount = "SELECT COUNT(*) FROM  BILL_MERAUTHINFO m WHERE  ( m.unno ='110000' or m.unno is null )  ";
		
		if (matb.getUsername() != null&&!"".equals(matb.getUsername())) {
			sql +=" and m.username like :userName ";
			sqlCount +=" and m.username like :userName ";
			map.put("userName", '%'+matb.getUsername()+'%');
			date_flag = true ;
		}
		if (matb.getAuthType() != null&&!"".equals(matb.getAuthType())) {
			sql +=" and m.authType =:authType ";
			sqlCount +=" and m.authType =:authType ";
			map.put("authType", matb.getAuthType());
		}
		if (matb.getCardName() != null&&!"".equals(matb.getCardName())) {
			sql +=" and m.cardName like :cardName ";
			sqlCount +=" and m.cardName like :cardName ";
			map.put("cardName", '%'+matb.getCardName()+'%');
			date_flag = true ;
		}	
		if (matb.getBankAccName() != null&&!"".equals(matb.getBankAccName())) {
			sql +=" and m.bankAccName like :bankAccName ";
			sqlCount +=" and m.bankAccName like :bankAccName ";
			map.put("bankAccName", '%'+matb.getBankAccName()+'%');
			date_flag = true ;
		}	
		if (matb.getMid() != null && !"".equals(matb.getMid())) {
			sql +=" and m.mid =:mid ";
			sqlCount +=" and m.mid =:mid ";
			map.put("mid", matb.getMid());
			date_flag = true ;
		}	
		if (matb.getSysseqnb()!= null && !"".equals(matb.getSysseqnb())) {
			sql +=" and m.sysseqnb =:sysseqnb ";
			sqlCount +=" and m.sysseqnb =:sysseqnb  ";
			map.put("sysseqnb", matb.getSysseqnb());
			date_flag = true ;
		}
		if (matb.getBankAccNo()!= null && !"".equals(matb.getBankAccNo())) {
			sql +=" and m.bankAccNo =:bankAccNo ";
			sqlCount +=" and m.bankAccNo =:bankAccNo  ";
			map.put("bankAccNo", matb.getBankAccNo());
			date_flag = true ;
		}
		if(matb.getStatus()== null || "".equals(matb.getStatus())){
			//所有
		}else if(matb.getStatus()== "00" || "00".equals(matb.getStatus())){
			//认证成功
			sql +=" and m.status ='00' and m.respcd='2000' ";
			sqlCount +=" and m.status ='00' and m.respcd='2000' ";
		}else if(matb.getStatus()== "01" || "01".equals(matb.getStatus())){
			//认证失败已上传
			sql +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is not null and m.approveNote is not null ";
			sqlCount +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is not null and m.approveNote is not null ";
		}else if(matb.getStatus()== "02" || "02".equals(matb.getStatus())){
			//认证失败已上传待审
			sql +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is not null and m.approveNote is null ";
			sqlCount +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is not null and m.approveNote is null ";
		}else if(matb.getStatus()== "03" || "03".equals(matb.getStatus())){
			//认证失败未上传
			sql +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is null ";
			sqlCount +=" and m.status ='00' and m.respcd!='2000' and m.authUpload is null ";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		if(matb.getCdate()== null && "".equals(matb.getCdate())&&date_flag == true){
			//当有其他条件且时间为空时 ，查寻 不考虑时间
		}else if (matb.getCdate()!= null && !"".equals(matb.getCdate())) {
//			sql +=" and trunc(m.cdate) =  to_date('"+df.format(matb.getCdate())+"','yyyy-mm-dd')" ;
//			sqlCount +=" and trunc(m.cdate) =  to_date('"+df.format(matb.getCdate())+"','yyyy-mm-dd')" ;
			sql +=" and m.cdate >=  to_date('"+df.format(matb.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and m.cdate <=  to_date('"+df.format(matb.getCdate())+" 23:59:59','yyyy-mm-dd hh24:mi:ss') " ;
			sqlCount +=" and m.cdate >=  to_date('"+df.format(matb.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and m.cdate <=  to_date('"+df.format(matb.getCdate())+" 23:59:59','yyyy-mm-dd hh24:mi:ss') " ;
		}else if (date_flag == false ){
			//查询当天 to_date('1987-09-18','yyyy-mm-dd')
//			sql +=" and trunc(m.cdate) >=  to_date('"+df.format(new Date())+"','yyyy-mm-dd')" ;
//			sqlCount +=" and trunc(m.cdate) >=  to_date('"+df.format(new Date())+"','yyyy-mm-dd')" ;
			sql +=" and m.cdate >=  to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') " ;
			sqlCount +=" and m.cdate >=  to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') " ;
		}
		
		if (matb.getSort() != null) {
			sql += " ORDER BY " + matb.getSort() + " " + matb.getOrder();
		}
		List<MerchantAuthenticityModel> MerchantAList = merchantAuthenticityDao.querySQLMerchantAuthenticity(sql, map, matb.getPage(), matb.getRows(), MerchantAuthenticityModel.class);
		BigDecimal count = merchantAuthenticityDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(MerchantAList, count.intValue());
		
		return dataGridBean;  
	}
	
	/**
	 * 修改商户认证信息（未/已认证）
	 */
	@Override
	public boolean updatemMerchantAuthenticity(
			MerchantAuthenticityBean merchantAuthenticity, UserBean user)
			throws Exception {
		boolean falg =false;
		MerchantAuthenticityModel merchantAuthenticityModel = merchantAuthenticityDao.getObjectByID(MerchantAuthenticityModel.class, merchantAuthenticity.getBmatid());
		if(merchantAuthenticityModel.getCdate()!=null && !"".equals(merchantAuthenticityModel.getCdate())){
			merchantAuthenticity.setCdate(merchantAuthenticityModel.getCdate());
		}
		BeanUtils.copyProperties(merchantAuthenticity, merchantAuthenticityModel);
		
		if(merchantAuthenticity.getStatus()=="2"||"2".equals(merchantAuthenticity.getStatus())){	
			//成功
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=2 where m.mid=?",new Object[]{merchantAuthenticityModel.getMid()});
			falg=true;
		}else if(merchantAuthenticity.getStatus()=="0"||"0".equals(merchantAuthenticity.getStatus())) {
			//失败
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=1 where m.mid=?",new Object[]{merchantAuthenticityModel.getMid()});
			falg=true;
		}else if(merchantAuthenticity.getStatus()=="1"||"1".equals(merchantAuthenticity.getStatus())) {
			//认证中
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=0 where m.mid=?",new Object[]{merchantAuthenticityModel.getMid()});
			falg=true;
		}
		return falg;
	}

	@Override
	public void updatemAutoMerchantApproveNote(Integer bmatid)
			throws Exception {
		MerchantAuthenticityModel merchantAuthenticityModel = merchantAuthenticityDao.getObjectByID(MerchantAuthenticityModel.class, bmatid);
        merchantAuthenticityModel.setApproveNote("异常重发返回认证成功");
        merchantAuthenticityDao.updateObject(merchantAuthenticityModel);
	}
	/**
	 * 修改交易认证信息Go
	 * 
	 */
	@Override
	public boolean updateTxnAuthenticityGo(MerchantAuthenticityBean mab, UserBean user)throws Exception {
		boolean falg =false;
		String hql = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//通过
		hql = "update MerchantAuthenticityModel m set m.status='00',m.respcd='2000',m.respinfo='人工认证成功;'||respinfo " +
					",m.cdate=to_date('"+df.format(mab.getCdate())+"','yyyy-MM-dd hh24:mi:ss') " +
					" where m.bmatid="+mab.getBmatid();
		if("MER".equals(mab.getAuthType())){
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=2 where m.mid=?",new Object[]{mab.getMid()});
		}
		merchantInfoDao.executeHql(hql);
		falg=true;
		return falg;
	}
	/**
	 * 修改交易认证信息Back
	 * 
	 */
	@Override
	public boolean updateTxnAuthenticityBack(MerchantAuthenticityBean mab, UserBean user)throws Exception {
		boolean falg =false;
		String hql = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//退回
		hql = "update MerchantAuthenticityModel m set m.status='00',m.respcd='2001',m.respinfo='人工退回;'||respinfo,m.approveNote='" +mab.getApproveNote()+
					"',m.cdate=to_date('"+df.format(mab.getCdate())+"','yyyy-MM-dd hh24:mi:ss') " +
					" where m.bmatid="+mab.getBmatid();
		merchantInfoDao.executeHql(hql);
		falg=true;
		return falg;
	}
	/**
	 * 方法功能：格式化MerchantAuthenticityModel为datagrid数据格式
	 * 参数：MerchantAuthenticityList
	 * 		total 总记录数
	 */
	private DataGridBean formatToDataGrid(List<MerchantAuthenticityModel> MerchantAList, long total) {
		List<MerchantAuthenticityBean> merchantABeanList = new ArrayList<MerchantAuthenticityBean>();
		for(MerchantAuthenticityModel merchantAModel : MerchantAList) {
			MerchantAuthenticityBean merchantABean = new MerchantAuthenticityBean();
			BeanUtils.copyProperties(merchantAModel, merchantABean);
			if(merchantAModel.getBankAccNo()!=null && merchantAModel.getBankAccNo().length()>=12) {
				merchantABean.setBankAccNo(merchantAModel.getBankAccNo().substring(0, 6) + "******" + merchantAModel.getBankAccNo().substring(12));
			}
			//暂无额外字段需要封装
			merchantABeanList.add(merchantABean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(merchantABeanList);
		
		return dgb;
	}
	public MerchantAuthenticityModel queryMerchantAuthenticityByMid(String mid)throws Exception{
		String hql = "";
		hql = "SELECT m FROM  MerchantAuthenticityModel m WHERE  1=1 AND m.authType='MER'  and m.mid ='"+mid+"'";
		List<MerchantAuthenticityModel> MerchantAList = merchantAuthenticityDao.queryObjectsByHqlList(hql);
		MerchantAuthenticityModel merchant=null;
		if(MerchantAList.size()!=0){
			merchant = MerchantAList.get(0);	
		}
		return merchant;
	}
	// 获取认证成功的数据
	public MerchantAuthenticityModel queryMerchantAuthenticitySuccess(MerchantAuthenticityBean maut)throws Exception{
		String hql = "SELECT m FROM  MerchantAuthenticityModel m WHERE  1=1 " +
				" AND m.status='00' and m.respcd='2000' and m.bankAccName='"+maut.getBankAccName()+"' " +
				" and m.legalNum='"+maut.getLegalNum()+"' and m.bankAccNo='"+maut.getBankAccNo()+"' ";
		List<MerchantAuthenticityModel> MerchantAList = merchantAuthenticityDao.queryObjectsByHqlList(hql);
		MerchantAuthenticityModel merchant=null;
		if(MerchantAList.size()!=0){
			merchant = MerchantAList.get(0);
		}
		return merchant;
	}

	/**
	 * 添加认证信息
	 */
	public Map<String, String> saveMerchantAuthInfo(MerchantAuthenticityBean matb) throws Exception{
		Map<String ,String>  map= new HashMap<String, String>();
		MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
		BeanUtils.copyProperties(matb,matm);
		Serializable id = merchantAuthenticityDao.saveObject(matm);
		pushADMreceiveRepayBD(matm,null);
		map.put("id", id.toString());
		return map;

	}
	public boolean isTrue(MerchantAuthenticityBean matb){
		if("864".equals(matb.getMid().substring(0,3))){
			JedisSource rource = new JedisSource();
			Jedis jedis = rource.getJedis();
			try {
				log.info("校验商户：" +matb.getMid());
				log.info("校验开关：" + jedis.hget("RebateInfo", "info")+",限制尾号："+jedis.hget("RebateInfo", "mer_id"));
				if(jedis.hget("RebateInfo", "info").equals("true")){
					if(jedis.hget("RebateInfo", "mer_id").equals(matb.getMid().substring(matb.getMid().length()-1))){
						return false;
					}
				}
			} catch (Exception e) {
				log.info("实名认证限制商户异常："+e.getMessage());
			} finally {
				JedisSource.returnResource(jedis);
			}
		}
		return true;
	}
	/**
	 * 商户认证
	 */
	@Override
	public Map<String, String> addMerchantAuthInfo(MerchantAuthenticityBean matb) 
		throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		//证通认证
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql=" select count(1) from bill_merauthinfo t where t.status='00' and t.respcd='2000' and  " +
					" ((t.legalnum=:LEGALNUM and t.bankaccname=:BANKACCNAME and t.bankaccno=:BANKACCNO) or t.mid=:MID)";
		matb.setLegalNum(matb.getLegalNum().toUpperCase());
		paramMap.put("LEGALNUM",matb.getLegalNum());
		paramMap.put("BANKACCNAME",matb.getBankAccName());
		paramMap.put("BANKACCNO",matb.getBankAccNo());
		paramMap.put("MID",matb.getMid());
		Integer yesCount=merchantAuthenticityDao.querysqlCounts2(sql, paramMap);
		if(yesCount<1){
//			MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
//			BeanUtils.copyProperties(matb,matm);
//			matm.setCdate(new Date());
//			merchantAuthenticityDao.saveObject(matm);
//			Integer bmatid=matm.getBmatid();
//			Map<String, String> map=bulidSendReqData(matb);
//			//请求接口
//			String result = HttpPostMap.post(BaseDataUtil.url, map,2);
//			log.info("第三方认证返回信息："+result);
//			
//			//解析返回结果
//			JSONObject jsonObject = new JSONObject(result);
//			String error_no =(String) jsonObject.get("error_no");
//			if(!error_no.equals("0")){
//				rtnMap.put("msg", jsonObject.get("error_info").toString());
//				rtnMap.put("status", "0");
//				matm.setRespinfo(jsonObject.get("error_info").toString());
//				merchantAuthenticityDao.updateObject(matm);
//				log.info("操作失败，失败原因："+jsonObject.get("error_info"));
//			}else {
//				if(jsonObject.get("results")!=null){
//					JSONArray jsonArray = jsonObject.getJSONArray("results");
//					JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
//					String sysseqnb = jsonObject1.getString("sysseqnb");
//					log.info("返回的业务流水号是："+sysseqnb);
//					for(int i=0;i<3;i++){
//						log.info("开始睡眠喽。。。。。。。。。。。。。。。。。。");
//						Thread.sleep(3000);
//						rtnMap=bulidSendReqResultData(sysseqnb,bmatid);
//						if(!"1".equals(rtnMap.get("status"))){
//							break;
//						}
//					}
//				}
//				else{
//					log.info("操作失败！");
//					rtnMap.put("msg","查询结果失败！");
//					rtnMap.put("status", "0");
//					matm.setRespinfo("查询结果失败！");
//					merchantAuthenticityDao.updateObject(matm);
//				}	
//			}
			rtnMap = addAuthInfoAll(matb);
		}else{
			log.info("信息已经实名验证通过，无需再次验证！");
			rtnMap.put("msg","认证成功！");
			rtnMap.put("status", "2");
			// @author:lxg-20190814 按新mid再次保存认证信息
			matb.setRespcd("2000");
			matb.setStatus("00");
			matb.setRespinfo("认证成功");
			matb.setCdate(new Date());
			saveMerchantAuthInfo(matb);
		}
		//修改商户状态
		if("2".equals(rtnMap.get("status"))){
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=2 where m.mid=?",new Object[]{matb.getMid()});
		}else {
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=1 where m.mid=?",new Object[]{matb.getMid()});
		}
		return rtnMap;
	}
	/**
	 *交易认证 
	 */
	@Override
	public Map<String, String> addTxnAuthInfo(MerchantAuthenticityBean matb) 
		throws Exception{
		Map<String, String> sMap = addAuthInfoAll(matb);
		return sMap;
	}
	
	public Map<String, String> addAuthInfoAll(MerchantAuthenticityBean matb)throws Exception{
		MerchantAuthenticityModel pushAdm=new MerchantAuthenticityModel();
		Map<String, String> rtnMap = new HashMap<String, String>();
		String mag_flag = "";
		//读取redis @author yq
		//ztt-20200220-redis连接获取统一改造
		if(StringUtils.isEmpty(matb.getWayName())) {
//			JedisSource rource = new JedisSource();
//			Jedis jedis = rource.getJedis();
			try {
//				if (matb.getAuthType().equals("MER")) {//商户认证
//					mag_flag = jedis.hget("AuthInfoType", "Type1").trim();
//				} else if (matb.getAuthType().equals("TXN")) {//交易认证
//					mag_flag = jedis.hget("AuthInfoType", "Type2").trim();
//				} else if (matb.getAuthType().equals("HRT")) {//交易认证（对外）
//					mag_flag = jedis.hget("AuthInfoType", "Type3").trim();
//				}
				String authtype_flag = matb.getAuthType();
				mag_flag = RedisUtil.getMagFlag(authtype_flag);
				log.info(matb.getAuthType() + "认证，通道读取redis参数，读取结果为" + mag_flag);
				if (mag_flag == null || "".equals(mag_flag.trim())) {
					log.info("从redis读取数据异常，正在从其他途径读取参数...");
					if (matb.getWayName() != null && !"".equals(matb.getWayName())) {
						mag_flag = matb.getWayName();
						log.info(matb.getAuthType() + "认证，从WayName读取结果:" + mag_flag);
					} else {
						mag_flag = paramUtil.getTypeMap(matb.getAuthType()) + "";
						log.info(matb.getAuthType() + "认证，从缓存读取结果为：" + mag_flag);
					}
				}
			} catch (Exception e) {
				log.info("请求redis出现异常");
				log.error(e);
				if (matb.getWayName() != null && !"".equals(matb.getWayName())) {
					mag_flag = matb.getWayName();
					log.info(matb.getAuthType() + "认证，从WayName读取结果:" + mag_flag);
				} else {
					mag_flag = paramUtil.getTypeMap(matb.getAuthType()) + "";
					log.info(matb.getAuthType() + "认证，从缓存读取参数，读取结果为：" + mag_flag);
				}
			} /*finally {
				JedisSource.returnResource(jedis);
			}*/
			
		}else{
			mag_flag = matb.getWayName();
			log.info("认证,接口中传入的wayName:"+mag_flag);
		}
		// @author:xuegangliu-20190307 易道认证添加 聚合认证
//		if(!".5.6.7.8.9.10.".contains("."+mag_flag+".")){
		if(!"5".equals(mag_flag)&&!"6".equals(mag_flag)
				&&!"7".equals(mag_flag)&&!"8".equals(mag_flag)
                &&!"9".equals(mag_flag)&&!"10".equals(mag_flag)
				&&!"11".equals(mag_flag)){
			if("MER".equals(matb.getAuthType())){
				mag_flag = "8";
			}else{
				mag_flag = "8";
			}
		}
		//01民生；02来宜
		if("5".equals(mag_flag)){
			//民生认证
			String miyao = "hrt8888@20160713";
			if(matb.getUnno()==null||"".equals(matb.getUnno())){
				matb.setUnno("110000");
			}else if("110000".equals(matb.getUnno())){
			}else if("992107".equals(matb.getUnno())){
				miyao = "Ybf8888@20160711";
			}else{
				miyao = "wn8888@20170505";
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("username",matb.getUsername());
			map.put("unno", matb.getUnno());
			map.put("mid", matb.getMid());
			map.put("legalNum", matb.getLegalNum());
			map.put("bankAccName", matb.getBankAccName());
			map.put("bankAccNo", matb.getBankAccNo());
			map.put("authType", matb.getAuthType());
			map.put("accnoExpdate", matb.getAccnoExpdate());
			map.put("sessionId", UUID.randomUUID().toString());
			map.put("phoneNo", matb.getPhoneNo());
			String hrtStr = matb.getUnno()+matb.getMid()+matb.getBankAccNo()+matb.getBankAccName()+matb.getLegalNum()+miyao;
			map.put("sign", MD5Wrapper.encryptMD5ToString(hrtStr));
			String info = JSON.toJSONString(map);
			log.info("请求民生对接程序请求信息json:"+info);
			String result=HttpXmlClient.post(msUrl, info);
			//解析返回结果
			JSONObject jsonObject = new JSONObject(result);
			try{
				String status =jsonObject.get("status")+"";
				String msg =(String) jsonObject.get("msg");
				String sysseqnb =(String) jsonObject.get("obj");
				log.info("请求民生对接程序返回信息：流水("+sysseqnb+");结果 ("+status+");信息("+msg+")");
				if("success".equals(status)){
					rtnMap.put("msg", msg);
					rtnMap.put("status", "2");
					rtnMap.put("sessionId", sysseqnb);
				}else{
					rtnMap.put("msg", msg);
					rtnMap.put("status", "0");
					rtnMap.put("sessionId", sysseqnb);
				}
			}catch(Exception e){
				log.info("请求民生对接程序请求信息json解析失败");
				log.error(e);
				rtnMap.put("msg", "认证失败");
				rtnMap.put("status", "0");
				rtnMap.put("sessionId", "");
			}
		}else if("6".equals(mag_flag)){
			//请求来宜接口
			MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
			BeanUtils.copyProperties(matb,matm);
			matm.setCdate(new Date());
			matm.setCardName("6");
			Map<String, String> sMap = sfidAuthMain.authCardWithoutOTP(matb.getBankAccName(), matb.getLegalNum().toUpperCase(), matb.getBankAccNo(), matb.getPhoneNo());
			//流水号
			matm.setSysseqnb(sMap.get("sessionId"));
			rtnMap.put("sessionId",sMap.get("sessionId"));
			merchantAuthenticityDao.saveObject(matm);
			if("2".equals(sMap.get("status"))){
					matm.setRespcd("2000");
					matm.setStatus("00");
					matm.setRespinfo(sMap.get("msg"));
					rtnMap.put("msg","认证成功！");
					rtnMap.put("status", "2");
			}else if("1".equals(sMap.get("status"))){
					matm.setRespcd("2001");
					matm.setStatus("00");
					matm.setRespinfo(sMap.get("msg"));
					rtnMap.put("msg","认证失败！"+sMap.get("msg"));
					rtnMap.put("status", "1");
			}else {
					matm.setRespcd("2001");
					matm.setStatus("00");
					matm.setRespinfo(sMap.get("msg"));
					rtnMap.put("msg","交易失败！"+sMap.get("msg"));
					rtnMap.put("status", "3");
			}
			log.info("请求来宜对接程序返回信息："+sMap.toString());
			merchantAuthenticityDao.updateObject(matm);
			BeanUtils.copyProperties(matm,pushAdm);
		}else if("7".equals(mag_flag)){
			MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
			BeanUtils.copyProperties(matb,matm);
			matm.setCdate(new Date());
			matm.setCardName("7");
			// 卓望服务器地址
			String apiAddress = serverAddress + "/api/rest/UidApiService/authCardWithoutOTP";
			// 授信证书库
			String trustStore = keyStorePath + "cacerts";
			String trustStorePass = "changeit";
			// 私钥证书
			String keyStore = keyStorePath + "www.hrtpayment.com" + ".p12";
			String keyStorePass = "846088715980643";//691712387030822
			PrintWriter out = null;
			BufferedReader in = null;
			String sysseqnb = UUID.randomUUID().toString();
			String result = "";
			try {
				TrustManager[] tms = getTrustManagers(trustStore, trustStorePass);
				KeyManager[] kms = getKeyManagers(keyStore, keyStorePass);
				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(kms, tms, new java.security.SecureRandom());
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				// 服务链接 ：银行卡鉴权
				URL url = new URL(apiAddress);
				Map<String, String>requestmMap = new HashMap<String, String>();
				requestmMap.put("merchantCode","www.hrtpayment.com");
				requestmMap.put("sessionId",sysseqnb);
				requestmMap.put("userName",matb.getBankAccName());
				requestmMap.put("idNumber",matb.getLegalNum());
				requestmMap.put("cardNo",matb.getBankAccNo());
				requestmMap.put("phoneNo",matb.getPhoneNo());
				String params = JSON.toJSONString(requestmMap);
				log.info("请求卓望对接程序请求信息json:"+params);
				// 请求参数
//				String params = "{\"merchantCode\": \""+merchantCode+"\","
//						+ "\"sessionId\": \"10000011\","
//						+ "\"userName\": \"冯悦清\","
//						+ "\"idNumber\": \"150404198204083714\","
//						+ "\"cardNo\": \"6226890120140966\"," + "\"phoneNo\": \"18648173453\"}";
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setSSLSocketFactory(ssf);
				// 设置通用的请求属性
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent", "Mozilla/4.0");
				// content-type 按具体需要进行设置
				conn.setRequestProperty("content-type", "application/json");
				// 发送POST请求必须设置如下两行
				conn.setDoOutput(true);
				conn.setDoInput(true);
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
				// 发送请求参数
				out.print(params);
				// flush输出流的缓冲
				out.flush();
				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				log.info("请求卓望对接程序返回信息json:"+result);
				//解析返回结果
				JSONObject jsonObject = new JSONObject(result);
				boolean ret = jsonObject.getBoolean("success");
				rtnMap.put("msg", jsonObject.getString("message"));
				rtnMap.put("sessionId", sysseqnb);
				matm.setSysseqnb(sysseqnb);
				if (ret == true) {
					rtnMap.put("status", "2");
					rtnMap.put("msg","认证成功");
					matm.setRespcd("2000");
					matm.setStatus("00");
					matm.setRespinfo("认证成功");
				}else {
					rtnMap.put("status", "0");
					matm.setRespcd("2001");
					matm.setStatus("00");
					matm.setRespinfo(jsonObject.getString("message"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				rtnMap.put("status", "0");
				rtnMap.put("msg", "认证失败!");
				rtnMap.put("sessionId", sysseqnb);
				matm.setRespcd("2001");
				matm.setStatus("00");
				matm.setRespinfo("认证失败!");
			} finally {
				merchantAuthenticityDao.saveObject(matm);
				BeanUtils.copyProperties(matm,pushAdm);
				try {
					in.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else if("9".equals(mag_flag)){
			//考拉
			MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
			BeanUtils.copyProperties(matb,matm);
			matm.setCdate(new Date());
			matm.setCardName("9");
			String outOrderNo = UUID.randomUUID().toString();
			try {
				//明文数据
				Map<String,String> reqMap = new HashMap<String,String>();	
				//业务参数
				reqMap.put("accountNo", matb.getBankAccNo());
				reqMap.put("name", matb.getBankAccName());
				reqMap.put("idCardCore", matb.getLegalNum());
//				reqMap.put("accountNo", "6228480500861233451");
//				reqMap.put("name", "张三");
//				reqMap.put("idCardCore", "130321198804010188");
				reqMap.put("outOrderNo", "zx"+System.currentTimeMillis());//上送流水号，保证唯一性	
				System.out.println("请求考拉对接程序请求信息:"+reqMap);
				//签名后数据
				Map<String, String> signMap = SignUtil.getSign(reqMap);
				signMap.put("customerId", customerId);
				//调用考拉其他产品替换prdGrpId和prdId
				signMap.put("prdGrpId","bankCardQuery");
				signMap.put("prdId","qryBankCardBy3Element");

				//请求考拉api接口
				String res=HttpUtil.buildRequest(kaolaAddress, signMap);

				Map<String,Object> resMap = (Map<String, Object>) com.alibaba.fastjson.JSONObject.parse(res);
		    	String retCode=(String)resMap.get("retCode");
		    	String retMsg=(String)resMap.get("retMsg");
		    	System.out.println("考拉响应码:"+retCode+",考拉响应信息:"+retMsg);
		    	if("000000".equals(retCode)){
		    		Map <String,Object> retData=SignUtil.getSignVeryfy(resMap);
		    		log.info("请求考拉对接程序返回信息:"+retData);
					//解析返回结果
					String ret = (String) retData.get("result");
					rtnMap.put("msg", (String)retData.get("message"));
					rtnMap.put("sessionId", outOrderNo);
					matm.setSysseqnb(outOrderNo);
					if ("T".equals(ret)) {
						rtnMap.put("status", "2");
						rtnMap.put("msg","认证成功");
						matm.setRespcd("2000");
						matm.setStatus("00");
						matm.setRespinfo("认证成功");
					}else {
						rtnMap.put("status", "0");
						rtnMap.put("msg", "认证失败!");
						matm.setRespcd("2001");
						matm.setStatus("00");
						matm.setRespinfo((String)retData.get("message"));
					}
		    	}else{
		    		System.out.println("考拉响应失败");
		    		throw new Exception();
		    	}
			} catch (Exception e) {
				e.printStackTrace();
				rtnMap.put("status", "0");
				rtnMap.put("msg", "认证失败!");
				rtnMap.put("sessionId", outOrderNo);
				matm.setRespcd("2001");
				matm.setStatus("00");
				matm.setRespinfo("认证失败!");
			} finally {
				merchantAuthenticityDao.saveObject(matm);
				BeanUtils.copyProperties(matm,pushAdm);
			}
		}else if("10".equals(mag_flag)){
            // @author:xuegangliu-20190307 易道认证添加
			yidaoMerAuth(matb, rtnMap, mag_flag);
		}else if("11".equals(mag_flag)){
			// @author:xuegangliu-20190318 聚合认证
			juheMerAuth(matb,rtnMap,mag_flag);
		}else{
			//吉信
			MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
			BeanUtils.copyProperties(matb,matm);
			matm.setCdate(new Date());
			matm.setCardName("8");
			String sysseqnb = UUID.randomUUID().toString().replaceAll("-", "");
			String transcode = null;
			Map<String, String> map = new HashMap<String, String>();

			map.put("bankcard", matb.getBankAccNo());//银行卡号
			map.put("dsorderid", sysseqnb);//商户订单号
			map.put("idcard", matb.getLegalNum());//证件号码
			map.put("idtype", "01");//证件类型
			map.put("merchno", JXmerchno);//商户号
			map.put("ordersn", sysseqnb);//流水号
//			map.put("reqIp", matb.getAuthType());//请求IP
			map.put("username", matb.getBankAccName());//姓名
			map.put("version", "0100");//版本号
			// @date:20190417 接口新添加的字段
			map.put("sceneCode", "02");//交易业务场景
//            嘉联支付-POS收单实名认证
//			map.put("sCustomerName", "和融通-商户实名认证");//二级商户名称
			//XXX-收单实名-实名认证
			map.put("sCustomerName", "和融通支付-收单实名-实名认证");//二级商户名称

			StringBuffer hrtStr =new StringBuffer();
			hrtStr.append("bankcard=").append(matb.getBankAccNo()).append("dsorderid=").append(sysseqnb).append("idcard=").append(matb.getLegalNum()).append("idtype=01").append("merchno=").append(JXmerchno);
			if(matb.getPhoneNo()==null||"".equals(matb.getPhoneNo())){
				map.put("transcode","106");//交易码
				transcode="106";
			}else{
				map.put("mobile", matb.getPhoneNo());//手机号
				map.put("transcode","107");//交易码
				hrtStr.append("mobile=").append(matb.getPhoneNo());
				transcode="107";
			}
			hrtStr.append("ordersn=").append(sysseqnb)
					.append("sCustomerName=和融通支付-收单实名-实名认证")
					.append("sceneCode=02")
					.append("transcode=").append(transcode).append("username=").append(matb.getBankAccName()).append("version=0100").append(JXmiyao);
			map.put("sign", DigestUtils.md5Hex(hrtStr.toString()));
			String info = JSON.toJSONString(map);
			log.info("请求吉信对接程序请求信息json:"+info);
			String result=HttpXmlClient.post(jxUrl, info);
			//解析返回结果
			JSONObject jsonObject = new JSONObject(result);
			try{
				String status =jsonObject.getString("returncode");
				String msg = null;
				if("0000".equals(status)){
					msg = "认证成功";
					rtnMap.put("msg", msg);
					rtnMap.put("status", "2");
					rtnMap.put("sessionId", sysseqnb);
					matm.setRespcd("2000");
					matm.setStatus("00");
					matm.setRespinfo(msg);
				}else{
					msg =jsonObject.getString("errtext");
					rtnMap.put("msg", msg);
					rtnMap.put("status", "0");
					rtnMap.put("sessionId", sysseqnb);
					matm.setRespcd("2001");
					matm.setStatus("00");
					matm.setRespinfo(msg);
				}
				matm.setSysseqnb(sysseqnb);
				log.info("请求吉信对接程序返回信息：流水("+sysseqnb+");结果 ("+status+");信息("+msg+")");
			}catch(Exception e){
				log.info("请求吉信对接程序请求信息json解析失败");
				log.error(e);
				rtnMap.put("msg", "认证失败");
				rtnMap.put("status", "0");
				rtnMap.put("sessionId", "");
				matm.setRespcd("2001");
				matm.setStatus("00");
				matm.setRespinfo("认证失败");
			}
			merchantAuthenticityDao.saveObject(matm);
			BeanUtils.copyProperties(matm,pushAdm);
		}
		log.info("认证返回信息：type : "+matb.getAuthType()+" ; target : "+mag_flag+"; bankNo : "+matb.getBankAccNo()+" ; msg : "+rtnMap.get("msg"));
//		rtnMap.put("a",mag_flag );
		// @authr:lxg-20190514 认证成功的商户,推送商户信息到综合
		if(!".10.11.".contains("."+mag_flag+".")) {//10,11单独进行推送
			pushADMreceiveRepayBD(pushAdm,matb.getPhoneNo());
		}
		return rtnMap;
	}

	public void pushADMreceiveRepayBD(MerchantAuthenticityModel matm,String phoneNumber){
//		matm.setRespcd("2000");
//		matm.setStatus("00");
		// 认证成功
		if("2000".equals(matm.getRespcd()) && "00".equals(matm.getStatus())
			&& ".MER.TXN.".contains("."+matm.getAuthType()+".")){
            Map<String,String> map = new HashMap<String, String>();
			map.put("mid",matm.getMid()); //商户号
			map.put("accno",matm.getBankAccNo()); //卡号
//			String mobileno = request.getParameter("mobileno");//手机号
			if(StringUtils.isNotEmpty(phoneNumber)) {
				map.put("mobileno", phoneNumber);
			}else{
				MerchantInfoModel merchantInfoModel = merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{matm.getMid()});
				map.put("mobileno", null!=merchantInfoModel&&StringUtils.isNotEmpty(merchantInfoModel.getContactPhone())?merchantInfoModel.getContactPhone():"0");
			}
			map.put("legalnum",matm.getLegalNum()); //身份证
			map.put("accname",matm.getBankAccName()); //入账人
//			String orderid = request.getParameter("orderid");//订单号
			map.put("orderid",matm.getSysseqnb());
			map.put("authtype","MER".equals(matm.getAuthType())?"-1":"-2"); //类型 -1报单实名认证-MER  -2使用自己的磁条卡做的交易-TXN
            // 异步推送
            PushTaskThreadPools.addWorkerThread(new ReceiveRepayBDThread(admAppIp,map));
//            //http://10.51.130.169:10087/AdmApp/repayment/repayment_receiveRepayBD.action
//            log.info("请求/AdmApp/repayment/repayment_receiveRepayBD.action,请求参数:"+JSON.toJSONString(map));
//            String ss=HttpXmlClient.post(admAppIp+"/AdmApp/repayment/repayment_receiveRepayBD.action", map);
//            log.info("请求/AdmApp/repayment/repayment_receiveRepayBD.action,返回参数:"+ss);
		}
	}

	/**
	 * 易道三要素认证
	 * @param matb
	 * @param rtnMap
	 * @param mag_flag
	 */
	private void yidaoMerAuth(MerchantAuthenticityBean matb, Map<String, String> rtnMap, String mag_flag) {
		MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
		BeanUtils.copyProperties(matb,matm);
		matm.setCdate(new Date());
		matm.setCardName(mag_flag);
		String outOrderNo = UUID.randomUUID().toString();
		try {
//                appId 应用ID
//                appKey 应用Key
//                type 认证类型2=二要素认证，3=三要素认证，4=四要素认证
//                cardno 银行卡号
//                owner 持卡人姓名
//                选填idno 身份证号码，二要素认证时不需要填此字段
//                phone 电话号码，二要素或三要素认证时不需要填此字段
			//明文数据
			Map<String,String> reqMap = new HashMap<String,String>();
			reqMap.put("appId", yidaoAppId);
			reqMap.put("appKey", yidaoAppKey);
			reqMap.put("type", "3");
			reqMap.put("cardno", matb.getBankAccNo());
			reqMap.put("owner", matb.getBankAccName());
			reqMap.put("idno", matb.getLegalNum());   // 3要素需要填
//                reqMap.put("phone", matb.getPhoneNo()); //4要素需要填
			String params = com.alibaba.fastjson.JSONObject.toJSONString(reqMap);
			log.error("易道认证请求参数:"+params);
			String result= HttpXmlClient.postISOtoUTF8(yidaoAddress, reqMap);
//                result=new String(result.getBytes("ISO-8859-1"), "UTF-8");
			com.alibaba.fastjson.JSONObject resMap = com.alibaba.fastjson.JSONObject.parseObject(result);
			log.error("易道认证返回参数:"+resMap);

//                {"time":1,"requestId":"5fd4846503f24b658416f1d8315da78f","code":20500,"available":9,"msg":"è®¤è¯ä¸ä¸è´ï¼è®¤è¯æªéè¿"}
//                available int 剩余可调用次数
//                requestId string 请求ID
//                time int 调用耗时（毫秒）
//                code string 结果码
//                msg string 结果描述
			String retCode=resMap.getString("code");
			String time=resMap.getString("time");
			String available=resMap.getString("available");
			String retMsg=resMap.getString("msg");//+time+"|"+available;
			String requestId=resMap.getString("requestId");

			matm.setRespinfo(retMsg);
			matm.setSysseqnb(requestId);

			log.info("易道响应码:"+retCode+",易道响应信息:"+retMsg);
			if("20000".equals(retCode)){
//                    rtnMap.put("msg", retMsg);
				rtnMap.put("sessionId", outOrderNo);
//                    matm.setSysseqnb(outOrderNo);
				rtnMap.put("status", "2");
				rtnMap.put("msg","认证成功");
				matm.setRespcd("2000");
				matm.setStatus("00");
//                    matm.setRespinfo("认证成功");
			}else{
				rtnMap.put("status", "0");
				rtnMap.put("msg", "认证失败!");
				matm.setRespcd("2001");
				matm.setStatus("00");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("status", "0");
			rtnMap.put("msg", "认证失败!");
			rtnMap.put("sessionId", outOrderNo);
			matm.setRespcd("2001");
			matm.setStatus("00");
			matm.setRespinfo("认证失败!");
		} finally {
			merchantAuthenticityDao.saveObject(matm);
			pushADMreceiveRepayBD(matm,matb.getPhoneNo());
		}
	}

	/**
	 * 聚合三要素认证
	 * @param matb
	 * @param rtnMap
	 * @param mag_flag
	 */
	public void juheMerAuth(MerchantAuthenticityBean matb,Map<String, String> rtnMap,String mag_flag){
		MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
		BeanUtils.copyProperties(matb,matm);
		matm.setCdate(new Date());
		matm.setCardName(mag_flag);
		String sysseqnb = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String, String> map = new HashMap<String, String>();
//        key string 是 在个人中心->我的数据,接口名称上方查看
//        realname string 是 姓名，需要utf8 Urlencode
//        idcard string 是 身份证号码
//        bankcard string 是 银行卡卡号
//        uorderid string 否 用户订单号,不超过32位，要保证唯一
//        isshow int 否 是否显示匹配信息,0默认不显示，1显示
//        sign string 是 md5(openid+appkey+bankcard+realname+idcard),openid在个人中
//        心查询
		map.put("key",juheAppKey);
		map.put("realname",matm.getBankAccName());
		map.put("idcard",matm.getLegalNum());
		map.put("bankcard",matm.getBankAccNo());
		map.put("uorderid",sysseqnb);
		map.put("isshow","1");
		String md5Vlaue = MD5Util.MD5(juheOpenId+juheAppKey+matm.getBankAccNo()+matm.getBankAccName()+matm.getLegalNum());
		// 聚合md5加密返回的16进制32位小写编码
		map.put("sign",md5Vlaue.toLowerCase());
		log.info("聚合认证请求参数:"+ com.alibaba.fastjson.JSONObject.toJSONString(map));
		String result=HttpXmlClient.post(juheAddress, map);
		com.alibaba.fastjson.JSONObject jsonResult=com.alibaba.fastjson.JSONObject.parseObject(result);
		log.info("聚合认证返回参数:"+jsonResult.toString());
		try{

//			error_code int 返回码
//			reason string 返回码描述
//			jobid string 本次查询流水号
//			bankcard string 银行卡卡号
//			realname string 姓名
//			idcard string 身份证号码
//			res int 匹配结果，1:匹配 2:不匹配i只有参数isshow不为空且不为0时
//			{ "reason": "成功",
//					"result": { "jobid": "2017052515514933954", "bankcard": "*****************",
//					"realname": "**", "idcard": "*************", "res": 2, "message": "认证信息不匹配，银行卡无 }, " +
//					""error_code": 0}
			String msg = null;
			Integer error_code = jsonResult.getInteger("error_code");
			String reason = jsonResult.getString("reason");
			com.alibaba.fastjson.JSONObject content= jsonResult.getJSONObject("result");
			Integer res = content.getInteger("res");
			String jobid = content.getString("jobid");
			matm.setSysseqnb(jobid);
			if(error_code==0){
				if(res==1){
					msg = "认证成功";
					rtnMap.put("msg", msg);
					rtnMap.put("status", "2");
					rtnMap.put("sessionId", sysseqnb);
					matm.setRespcd("2000");
					matm.setStatus("00");
					matm.setRespinfo(msg);
				}else{
					msg =content.getString("message");
					rtnMap.put("msg", reason+"|"+msg);
					rtnMap.put("status", "0");
					rtnMap.put("sessionId", sysseqnb);
					matm.setRespcd("2001");
					matm.setStatus("00");
					matm.setRespinfo(reason+"|"+msg);
				}
			}else{
				msg =content.getString("message");
				rtnMap.put("msg", reason+"|"+msg);
				rtnMap.put("status", "0");
				rtnMap.put("sessionId", sysseqnb);
				matm.setRespcd("2001");
				matm.setStatus("00");
				matm.setRespinfo(reason+"|"+msg);
			}
		}catch(Exception e){
			log.info("请求聚合认证失败!!!!");
			log.error(e);
			rtnMap.put("msg", "认证失败");
			rtnMap.put("status", "0");
			rtnMap.put("sessionId", "");
			matm.setRespcd("2001");
			matm.setStatus("00");
			matm.setRespinfo("认证失败");
		}
		merchantAuthenticityDao.saveObject(matm);
		pushADMreceiveRepayBD(matm,matb.getPhoneNo());
	}
	/**
	 * 加载信任证书库
	 * 
	 * @param trustStore
	 * @param trustStorePass
	 * @return
	 * @throws IOException
	 */
	private static TrustManager[] getTrustManagers(String trustStore,
			String trustStorePass) throws IOException {
		try {
			String alg = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory factory = TrustManagerFactory.getInstance(alg);
			InputStream fp = new FileInputStream(trustStore);
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(fp, trustStorePass.toCharArray());
			fp.close();
			factory.init(ks);
			TrustManager[] tms = factory.getTrustManagers();
			System.out.println(tms);
			return tms;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载私钥证书
	 * 
	 * @param keyStore
	 * @param keyStorePass
	 * @return
	 * @throws IOException
	 */
	private static KeyManager[] getKeyManagers(String keyStore,
			String keyStorePass) throws IOException {
		try {
			String alg = KeyManagerFactory.getDefaultAlgorithm();
			KeyManagerFactory factory = KeyManagerFactory.getInstance(alg);
			InputStream fp = new FileInputStream(keyStore);
			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(fp, keyStorePass.toCharArray());
			fp.close();
			factory.init(ks, keyStorePass.toCharArray());
			KeyManager[] keyms = factory.getKeyManagers();
			System.out.println(keyms);
			return keyms;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *交易认证 （对外）
	 */
	@Override
	public Map<String, String> addTxnAuthInfoForYBF(MerchantAuthenticityBean matb) 
	throws Exception{
		Map<String, String> rtnMap = new HashMap<String, String>();
		MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
		BeanUtils.copyProperties(matb,matm);
		matm.setCdate(new Date());
		
		//请求三方接口
		Map<String, String> sMap = addAuthInfoAll(matb);
		//流水号
		matm.setSysseqnb(sMap.get("sessionId"));
//		merchantAuthenticityDao.saveObject(matm);
		if("2".equals(sMap.get("status"))){
			matm.setRespcd("2000");
			matm.setStatus("00");
			matm.setRespinfo(sMap.get("msg"));
			rtnMap.put("msg","认证成功！");
			rtnMap.put("status", "2");
			rtnMap.put("sessionId", matb.getSessionId());
		}else if("1".equals(sMap.get("status"))){
			matm.setRespcd("2001");
			matm.setStatus("00");
			matm.setRespinfo(sMap.get("msg"));
			rtnMap.put("msg","认证失败！"+sMap.get("msg"));
			rtnMap.put("status", "1");
			rtnMap.put("sessionId", matb.getSessionId());
		}else {
			matm.setRespcd("2002");
			matm.setStatus("03");
			matm.setRespinfo(sMap.get("msg"));
			rtnMap.put("msg","交易失败！"+sMap.get("msg"));
			rtnMap.put("status", "1");
			rtnMap.put("sessionId", matb.getSessionId());
		}
//		merchantAuthenticityDao.updateObject(matm);
		return rtnMap;
	}

	public Map<String, String> bulidSendReqData(MerchantAuthenticityBean matb) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String ptyKye = BaseDataUtil.ptyKey;	
		//6259071159176755
		//130684198207185467
		//王亚敏
		//String acctno ="6225760009693446";
		//String certseq = "460033198908063896";
		AES aes = new AES();
		String AESAcctno = aes.encrypt(matb.getBankAccNo(), "utf-8");  //加密
		log.info("aes卡号:"+AESAcctno);
		String AESCertseq = aes.encrypt(matb.getLegalNum(), "utf-8");   //加密
		log.info("aes身份证号:"+AESCertseq);
		
		String base64Acctno=  new String(Base64.encode(URLEncoder.encode(AESAcctno, "utf-8").getBytes("utf-8")), "utf-8");
		log.info("aesbase64卡号:"+base64Acctno);
		String base64Certseq=  new String(Base64.encode(URLEncoder.encode(AESCertseq, "utf-8").getBytes("utf-8")), "utf-8");
		log.info("aesbase64身份证号:"+base64Acctno);
		
		map.put("acctno", AESAcctno);
		map.put("biztyp", "A001");//对照接口文档查看
		map.put("biztypdesc", "Bank");//服务描述
		map.put("certseq", AESCertseq);//身份证号
		map.put("code", "");//短信验证码 .如不调用短信，这里可以传空字符
		map.put("phoneno", "");//手机号
		map.put("placeid", "00");//业务发生地
		map.put("ptyacct", BaseDataUtil.ptyacct);//机构帐号
		map.put("ptycd", BaseDataUtil.ptycd);//机构号
		map.put("sourcechnl", "0");//来源渠道，pc端传0
		map.put("sysseqnb", "");//调用生成短信接口返回的业务流水号 .如不调用短信，这里可以传空字符
		
		String sign = SignatureUtil.signature(map,ptyKye);
		map.put("sign", sign);//防篡改密钥
		map.put("funcNo", "2000203");//单笔请求业务BUS功能号
		map.put("acctno", base64Acctno);
		map.put("certseq", base64Certseq);//身份证号
		map.put("usernm", matb.getBankAccName());//姓名
		return map;
	}
	
	
	public Map<String, String> bulidSendReqResultData(String sysseqnb,Integer bmatid) throws Exception, NoSuchAlgorithmException{
		Map<String, String> rtnMap = new HashMap<String, String>();
		String ptyKey= BaseDataUtil.ptyKey;//会话密钥
		Map<String, String> map = new HashMap<String, String>();
		map.put("sysseqnb", sysseqnb);//发送请求后返回的流水号流水号
		map.put("ptyacct", BaseDataUtil.ptyacct);//券商帐号
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		map.put("timestamp", timestamp);//时间
		String sign = SignatureUtil.signature( map,ptyKey);
		map.put("sign", sign);//防篡改密钥
		map.put("funcNo", "2000102");//单笔请求业务BUS功能号	
		
		//发送查询结果请求
		String result = HttpPostMap.post(BaseDataUtil.url, map,2);
		log.info("第三方认证结果返回信息："+result);
		MerchantAuthenticityModel macm=merchantAuthenticityDao.getObjectByID(MerchantAuthenticityModel.class,bmatid);
		macm.setSysseqnb(sysseqnb);

		//解析返回数据
		JSONObject jsonObject = new JSONObject(result);
		String error_no =(String) jsonObject.get("error_no");
		if(!error_no.equals("0")){
			rtnMap.put("msg", jsonObject.get("error_info").toString());
			rtnMap.put("status", "0");
			macm.setRespinfo(jsonObject.get("error_info").toString());
			log.info("操作失败，失败原因："+jsonObject.get("error_info"));
		}else {
			if(jsonObject.get("results")!=null){
				JSONArray jsonArray = jsonObject.getJSONArray("results");
				JSONObject jsonObject1 = new JSONObject(jsonArray.get(0).toString());
				//获取返回的参数
				String status = jsonObject1.getString("status");//处理状态，00为成功，01为正在处理，03位处理失败
				String mpssim = jsonObject1.getString("mpssim");//公安比对分值
				String localsim = jsonObject1.getString("localsim");//本地比对分值  
				String respcd = jsonObject1.getString("respcd");//本地比对分值  
				String respinfo =jsonObject1.getString("respinfo");
				macm.setStatus(status);
				macm.setRespcd(respcd);
				macm.setRespinfo(respinfo);
				log.info("处理状态是：[status="+status+"],公安比对分值是：[mpssim="+mpssim+"],本地比对分值是：[localsim="+localsim+"],返回码 "+respcd);
				if("01".equals(status)){
					rtnMap.put("msg","处理中...请稍后查询！");
					rtnMap.put("status", "1");
				}else if("00".equals(status)){
					if("2000".equals(respcd)){
						rtnMap.put("msg","认证成功！");
						rtnMap.put("status", "2");
					}else if("2001".equals(respcd)){
						rtnMap.put("msg","认证失败：（银行卡、姓名、身份证号（或者银行预留手机）不一致）！");
						rtnMap.put("status", "0");
					}else if("2002".equals(respcd)){
						rtnMap.put("msg","交易异常！");
						rtnMap.put("status", "0");
					}else{
						rtnMap.put("msg",respinfo);
						rtnMap.put("status", "0");
					}
				}else{
					rtnMap.put("msg","认证处理失败！");
					rtnMap.put("status", "0");
				}
			}
			else{
				rtnMap.put("msg","查询结果失败！");
				rtnMap.put("status", "0");
				log.info("查询结果失败！");
			}
		}
		merchantAuthenticityDao.updateObject(macm);
		return rtnMap;
		
	}
	
	
	@Override
	public void sendResultToHyb(Map<String, String> map,MerchantAuthenticityBean matb) {
		map.put("userName",matb.getUsername());
		map.put("mid", matb.getMid());
		log.info("商户认证-推送会员宝："+map);
		String ss=HttpXmlClient.post(hybMerAuthUrl, map);
		log.info("HYB请求返回信息："+ss);
	}
	
	
	@Override
	public Integer queryMerchantAuthCount(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.mid=:MID and t.authType=:authType " ;
		map.put("MID", matb.getMid());
		map.put("authType",matb.getAuthType());
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	
	
	//查询如果认证通过，直接返回成功，不走三方
	@Override
	public Integer queryTxnAuthCountYes(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where 1=1 " +
				//" t.bankAccName=:bankAccName and t.bankAccNo=:bankAccNo and t.legalNum=:legalNum " +
				" and t.status='00' and t.respcd='2000' " ;
		if(matb.getBankAccName()!=null&&!"".equals(matb.getBankAccName())){
			sql+=" and t.bankAccName=:bankAccName ";
			map.put("bankAccName",matb.getBankAccName());
		}
		if(matb.getBankAccNo()!=null&&!"".equals(matb.getBankAccNo())){
			sql+=" and t.bankAccNo=:bankAccNo ";
			map.put("bankAccNo",matb.getBankAccNo());
		}
		if(matb.getLegalNum()!=null&&!"".equals(matb.getLegalNum())){
			sql+=" and t.legalNum=:legalNum  ";
			map.put("legalNum",matb.getLegalNum());
		}
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}

	//查询如果认证通过（mid），直接返回成功，不走三方
	@Override
	public Integer queryTxnAuthCountYesWithMid(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where 1=1 " +
				//" t.bankAccName=:bankAccName and t.bankAccNo=:bankAccNo and t.legalNum=:legalNum " +
				" and t.status='00' and t.respcd='2000' " ;
		if(matb.getMid()!=null&&!"".equals(matb.getMid())){
			sql+=" and t.mid=:mid ";
			map.put("mid",matb.getMid());
		}
		if(matb.getBankAccName()!=null&&!"".equals(matb.getBankAccName())){
			sql+=" and t.bankAccName=:bankAccName ";
			map.put("bankAccName",matb.getBankAccName());
		}
		if(matb.getBankAccNo()!=null&&!"".equals(matb.getBankAccNo())){
			sql+=" and t.bankAccNo=:bankAccNo ";
			map.put("bankAccNo",matb.getBankAccNo());
		}
		if(matb.getLegalNum()!=null&&!"".equals(matb.getLegalNum())){
			sql+=" and t.legalNum=:legalNum  ";
			map.put("legalNum",matb.getLegalNum());
		}
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	
	//商户认证,查询在商户是否存在待审的记录
	@Override
	public Integer queryAuthInfoByMid(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.status='00' and t.respcd!='2000' and t.mid=:mid and t.authType='MER' and t.approvenote is null and t.authupload is not null" ;
		map.put("mid",matb.getMid());
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		if(count==0) {
			String sql1 = "select count(1) from bill_merchantinfo where mid = :mid and authtype=2";
			Integer count1 =merchantAuthenticityDao.querysqlCounts2(sql1, map);
			if(count1>0) {
				return -1;
			}
		}
		return count;
	}
	
	//查询如果认证失败，
	@Override
	public Integer queryTxnAuthCountNo(MerchantAuthenticityBean matb) {
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.authType=:authType " +
		" and t.bankAccName=:bankAccName and t.bankAccNo=:bankAccNo and t.legalNum=:legalNum " +
		" and t.status='00' and t.respcd!='2000' " ;
		//" and trunc(t.cdate) =  to_date('"+df.format(new Date())+"','yyyy-mm-dd')";
		map.put("authType",matb.getAuthType());
		map.put("bankAccName",matb.getBankAccName());
		map.put("bankAccNo",matb.getBankAccNo());
		map.put("legalNum",matb.getLegalNum());
		if(matb.getMid()!=null&&!"".equals(matb.getMid())){
			sql+=" and t.mid =:mid ";
			map.put("mid",matb.getMid());
		}
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	//查询当天如果认证失败，
	@Override
	public Integer queryTxnAuthCountNoToDay(MerchantAuthenticityBean matb) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.authType=:authType " +
		" and t.bankAccName=:bankAccName and t.bankAccNo=:bankAccNo and t.legalNum=:legalNum " +
		" and t.status='00' and t.respcd!='2000' " +
//		" and trunc(t.cdate) =  to_date('"+df.format(new Date())+"','yyyy-mm-dd')";
		" and t.cdate >=  to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		map.put("authType",matb.getAuthType());
		map.put("bankAccName",matb.getBankAccName());
		map.put("bankAccNo",matb.getBankAccNo());
		map.put("legalNum",matb.getLegalNum());
		if(matb.getMid()!=null&&!"".equals(matb.getMid())){
			sql+=" and t.mid =:mid ";
			map.put("mid",matb.getMid());
		}
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	
	//第一次失败上传照片/当天失败多次，照片付给旧记录，（只记录一次）
	@Override
	public Integer updateTxnAuthWithPic(MerchantAuthenticityBean matb) {
		Integer count=null;
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer fName1 = new StringBuffer();
		//查询认证id
		Map<String,Object> map_id = new HashMap<String, Object>();
		String hql_id = "select t from MerchantAuthenticityModel t where t.authType=:authType " +
				" and t.bankAccName=:bankAccName and t.bankAccNo=:bankAccNo and t.legalNum=:legalNum " +
				" and t.status='00' and t.respcd!='2000' and t.mid =:mid" +
				" and t.cdate >=  trunc(sysdate) ";
		map_id.put("authType",matb.getAuthType());
		map_id.put("bankAccName",matb.getBankAccName());
		map_id.put("bankAccNo",matb.getBankAccNo());
		map_id.put("legalNum",matb.getLegalNum());
		map_id.put("mid",matb.getMid());
		List<MerchantAuthenticityModel> m_list=merchantAuthenticityDao.queryObjectsByHqlList(hql_id, map_id);
		Integer id = null;
		if(m_list.size()==0){
			try{//添加交易失败记录
				matb.setCdate(new Date());
				matb.setRespcd("2001");
				matb.setStatus("00");
				matb.setRespinfo("认证失败！此信息认证已失败");
				MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
				matb.setAuthUpload("");//正常为空，送检为加密字符串(长度不够)
				BeanUtils.copyProperties(matb,matm);
				Serializable id1 = merchantAuthenticityDao.saveObject(matm);
				//处理图片名
				fName1.append(matb.getMid());
				fName1.append("-");
				fName1.append(Integer.valueOf(id1+"")+"");
				fName1.append(".jpg");
				matm.setAuthUpload(imageDay+File.separator+fName1.toString());
				//修改
				matm.setCdate(new Date());
				matm.setApproveNote("");
				matm.setRespcd("2001");
//				Map<String, String> map = saveMerchantAuthInfo(matb);
				count = Integer.valueOf(id1+"");
			}catch(Exception e){
				log.error(e);
			}
		}else{
			id =m_list.get(0).getBmatid();
			//处理图片名
			fName1.append(matb.getMid());
			fName1.append("-");
			fName1.append(id+"");
			fName1.append(".jpg");
			matb.setAuthUpload(imageDay+File.separator+fName1.toString());
			//添加/修改图片路径
			Map<String,Object> map = new HashMap<String, Object>();
			String hql="update  MerchantAuthenticityModel t set t.authUpload=:authUpload ," +
					" t.cdate = to_date('"+df.format(new Date())+"','yyyy-MM-dd hh24:mi:ss') ,t.approveNote='' " +
					" where t.bmatid=:bmatid ";
			map.put("authUpload",matb.getAuthUpload());
			map.put("bmatid",id);
			count = merchantAuthenticityDao.executeHql(hql, map);
		}
		if(matb.isWeChatProg()){
			FileZipUtil.uploadFile2byte(queryUpLoadPath(),matb.getUploadFileBaseInfo(),fName1.toString(),imageDay);
		}else {
			uploadFile(matb.getAuthUploadFile(), fName1.toString(), imageDay);
		}
		return count;
	}
	/**
	 * 查询商户下所有交易认证记录
	 */
	@Override
	public List<MerchantAuthenticityModel> queryTxnAuthInfoByMid(String mid) {
		
		List<MerchantAuthenticityModel> authInfoList1 =new ArrayList<MerchantAuthenticityModel>();
		String hql = " select m from MerchantAuthenticityModel m where   m.authType='TXN' and m.bmatid in "+
						" (select max(a.bmatid) from MerchantAuthenticityModel a where a.mid='"+mid+"' group by a.bankAccNo,a.bankAccName,a.legalNum) "+
						"  order by m.cdate desc ";
		List<MerchantAuthenticityModel> authInfoList =merchantAuthenticityDao.queryObjectsByHqlList(hql);
		for(MerchantAuthenticityModel m  : authInfoList){
			MerchantAuthenticityModel t=new MerchantAuthenticityModel();
			BeanUtils.copyProperties(m,t);
			//2001 失败 ；= 2000成功 ；= 2002 认证中 ；= 2003 退回
			if("2000".equals(m.getRespcd())){
				t.setAppcd("2000");
			}else if("2001".equals(m.getRespcd())&&!"".equals(m.getApproveNote())&&m.getApproveNote()!=null){
				t.setAppcd("2003");
			}else if("2001".equals(m.getRespcd())&&!"".equals(m.getAuthUpload())&&m.getAuthUpload()!=null){
				t.setAppcd("2002");
			}else {
				t.setAppcd("2001");
			}
			t.setUsername("*");
			authInfoList1.add(t);
		}
		return authInfoList1;
	}
	// 查询当天此商户交易验证失败次数
	@Override
	public Integer queryTxnAuthCount(MerchantAuthenticityBean matb) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.authType=:authType " +
				" and t.mid=:mid " +
				" and t.status='00' and t.respcd!='2000' " +
//				" and trunc(t.cdate) =  to_date('"+df.format(new Date())+"','yyyy-mm-dd')";
				" and t.cdate >= to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
		map.put("authType",matb.getAuthType());
		map.put("mid",matb.getMid());
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public Integer queryCardRule(MerchantAuthenticityBean matb) {
		int cardLeng=matb.getBankAccNo().trim().length();
		String cardAnd=matb.getBankAccNo().trim().substring(0, 6);
		Map<String,Object> map = new HashMap<String, Object>();

		// @author:lxg-20190523 银行卡添加商户白名单,白名单的商户去查询 所有银行卡列表,存在:继续校验/不存在:返回 无效卡提示
		// matb.setAuthType("TXN"); 白名单规则只限制 交易认证
		if(null!=matb && "TXN".equals(matb.getAuthType())) {
			// 查询商户白名单
			String sqlMerWhite = "select count(1) from bill_merchantinfo k where k.ifwhite=1 and k.mid='" + matb.getMid() + "'";
			Integer whiteCount = merchantAuthenticityDao.querysqlCounts2(sqlMerWhite, null);
			if (whiteCount > 0) {
				Map<String, Object> allMap = new HashMap<String, Object>();
				// 查询总卡表是否有此卡
				String allSql = "select count(1) from bill_cardtable_all t where t.cardand=substr(:CARDAND,0,t.cardandleng) and t.cardleng=:CARDLENG ";
				allMap.put("CARDAND", matb.getBankAccNo().trim());
				allMap.put("CARDLENG", cardLeng);
				Integer allCount = merchantAuthenticityDao.querysqlCounts2(allSql, allMap);
				return allCount > 0 ? allCount : -1;
			}
		}

		String sql="select count(1) from bill_cardtable t where t.cardand=:CARDAND and t.cardleng=:CARDLENG ";
		map.put("CARDAND", cardAnd);
		map.put("CARDLENG", cardLeng);
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	/**
	 * sign验证
	 * @param matb
	 * @return
	 */
	@Override
	public Integer querySignAuth(MerchantAuthenticityBean matb){
		Integer count = 0 ;
//		String miyao = "";
		String signAuthByRedis=null;
		// @author:lxg-20190529 将sign改为动态获取,配置添加到redis中 db0->HrtApp_HRT_SignAuth
		//ztt-20200220-redis连接获取统一改造
		try {
			String unno = matb.getUnno();
			signAuthByRedis = RedisUtil.getsignAuthByRedis(unno);
		} catch (Exception e) {
			log.info("请求redis出现异常:"+e.getMessage());
		}
		
//		JedisSource rource = new JedisSource();
//		Jedis jedis = rource.getJedis();
//		try {
//			log.info("获取sign验证,传入的机构号unno:"+unno);
//			signAuthByRedis = jedis.hget("HrtApp_HRT_SignAuth",unno);
//			if(StringUtils.isEmpty(signAuthByRedis)){
//                // 获取默认
//				signAuthByRedis = jedis.hget("HrtApp_HRT_SignAuth","OTHER");
//			}
//			log.info("获取redis中sign验证,传入的机构号unno:"+unno+",查询的sigin:"+signAuthByRedis);
//		} catch (Exception e) {
//			log.info("请求redis出现异常:"+e.getMessage());
//		}finally{
//			if(StringUtils.isEmpty(signAuthByRedis)){
//				if("110000".equals(matb.getUnno())){
//					signAuthByRedis = "hrt8888@20160713";
//				}else if("992107".equals(matb.getUnno())){
//					signAuthByRedis = "ybf8888820160711";
//				}else{
//					signAuthByRedis = "wn8888@20170505";
//				}
////				signAuthByRedis="wn8888@20170505";
//				log.info("设置默认sign:"+signAuthByRedis);
//			}
//			JedisSource.returnResource(jedis);
//		}

//		if("110000".equals(matb.getUnno())){
//			miyao = "hrt8888@20160713";
//		}else if("992107".equals(matb.getUnno())){
//			miyao = "ybf8888820160711";
//		}else{
//			miyao = "wn8888@20170505";
//		}
//		String hrtStr = matb.getUnno()+matb.getMid()+matb.getBankAccNo()+matb.getBankAccName()+matb.getLegalNum()+miyao;
		String hrtStr = matb.getUnno()+matb.getMid()+matb.getBankAccNo()+matb.getBankAccName()+matb.getLegalNum()+signAuthByRedis;
		String hrtSign;
		try {
			hrtSign = MD5Wrapper.encryptMD5ToString(hrtStr);
			if(matb.getSign().equals(hrtSign)){
				count=1;
			}else{
				log.info("HRT交易认证接口参数传输异常(传入sign："+matb.getSign()+"; 本地sign："+hrtSign+")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public Integer queryMerAuthYes(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.status='00' and t.respcd='2000' and t.mid=:MID and t.authType=:authType";
		map.put("MID",matb.getMid());
		map.put("authType",matb.getAuthType());
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	//查询商户&交易信息所有失败记录
	@Override
	public Integer queryHasFailMerCount(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.bankaccname=:BANKACCNAME and t.legalnum=:LEGALNUM and t.bankaccno=:BANKACCNO and t.status='00' and t.respcd!='2000' ";
		map.put("BANKACCNAME", matb.getBankAccName());
		map.put("LEGALNUM", matb.getLegalNum()); 
		map.put("BANKACCNO", matb.getBankAccNo());
		//map.put("authType",matb.getAuthType());
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public String queryHasFailMerMid(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select t.mid from bill_merauthinfo t where t.bankaccname=:BANKACCNAME and t.legalnum=:LEGALNUM and t.bankaccno=:BANKACCNO and t.respcd!='2000' and t.authType=:authType";
		map.put("BANKACCNAME", matb.getBankAccName());
		map.put("LEGALNUM", matb.getLegalNum()); 
		map.put("BANKACCNO", matb.getBankAccNo());
		map.put("authType",matb.getAuthType());
		String m =merchantAuthenticityDao.queryObjectBySql(sql, map)+"";
		return m;
	}
	/**
	 * 下载交易认证失败照片
	 */
	private void uploadFile(File upload, String fName, String imageDay) {
		try {
			//String realPath = "D:" + File.separator + imageDay;
			//String realPath = File.separator+"u01"+File.separator+"hrtwork"+File.separator+"SignFailUpload"+ File.separator + imageDay;
			String realPath = queryUpLoadPath()+ File.separator + imageDay;
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
		return merchantAuthenticityDao.queryObjectsBySqlListMap("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='SignFailUpload'",null).get(0).get("UPLOAD_PATH");
	}
	/**
	 * 查看明细(查看 商户认证失败后 上传的照片)
	 */
	public MerchantAuthenticityBean queryMerAuthenticityById(Integer id){
		//String title="HrtFrameWork";/u01/hrtwork/SignFailUpload/
		//String path = merchantAuthenticityDao.querySavePath(title);
		MerchantAuthenticityModel mm=(MerchantAuthenticityModel) merchantAuthenticityDao.queryObjectByHql("from MerchantAuthenticityModel m where m.authType='MER' and m.bmatid=?", new Object[]{id});
		MerchantAuthenticityBean mb = new MerchantAuthenticityBean();
		if(mm!=null) {
            BeanUtils.copyProperties(mm, mb);
            mb.setAuthUpload(queryUpLoadPath() + File.separator + mm.getAuthUpload());
        }
		return mb;
	}
	/**
	 * 查询通道商认证信息
	 */
	@Override
	public DataGridBean queryHrtAuthenticity(MerchantAuthenticityBean matb, UserBean user) throws Exception {
		String sql = "";
		String sqlCount = "";
		boolean date_flag = false ;
		Map<String, Object> map = new HashMap<String, Object>();
		sql = "SELECT m FROM  MerchantAuthenticityModel m WHERE  1=1 ";
		sqlCount = "SELECT COUNT(*) FROM  BILL_MERAUTHINFO m WHERE  1=1  ";
		
		if (matb.getUnno() != null&&!"".equals(matb.getUnno())) {
			sql +=" and m.unno like :unno ";
			sqlCount +=" and m.unno like :unno ";
			map.put("unno", '%'+matb.getUnno()+'%');
			date_flag = true ;
		}
		if (matb.getAuthType() != null&&!"".equals(matb.getAuthType())) {
			sql +=" and m.authType =:authType ";
			sqlCount +=" and m.authType =:authType ";
			map.put("authType", matb.getAuthType());
		}
		if (matb.getBankAccName() != null&&!"".equals(matb.getBankAccName())) {
			sql +=" and m.bankAccName like :bankAccName ";
			sqlCount +=" and m.bankAccName like :bankAccName ";
			map.put("bankAccName", '%'+matb.getBankAccName()+'%');
			date_flag = true ;
		}
		if (matb.getBankAccNo()!= null && !"".equals(matb.getBankAccNo())) {
			sql +=" and m.bankAccNo =:bankAccNo ";
			sqlCount +=" and m.bankAccNo =:bankAccNo  ";
			map.put("bankAccNo", matb.getBankAccNo());
			date_flag = true ;
		}
		if(matb.getStatus()== null || "".equals(matb.getStatus())){
			//所有
		}else if(matb.getStatus()== "00" || "00".equals(matb.getStatus())){
			//认证成功
			sql +=" and m.status ='00' and m.respcd='2000' ";
			sqlCount +=" and m.status ='00' and m.respcd='2000' ";
		}else if(matb.getStatus()== "01" || "01".equals(matb.getStatus())){
			//认证失败
			sql +=" and m.status ='00' and m.respcd!='2000' ";
			sqlCount +=" and m.status ='00' and m.respcd!='2000' ";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		if(matb.getCdate()== null && "".equals(matb.getCdate())&&date_flag == true){
			//当有其他条件且时间为空时 ，查寻 不考虑时间
		}else if (matb.getCdate()!= null && !"".equals(matb.getCdate())&&matb.getCdate1()!= null && !"".equals(matb.getCdate1())) {
			sql +=" and m.cdate >=  to_date('"+df.format(matb.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and m.cdate <=  to_date('"+df.format(matb.getCdate1())+" 23:59:59','yyyy-mm-dd hh24:mi:ss') " ;
			sqlCount +=" and m.cdate >=  to_date('"+df.format(matb.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and m.cdate <=  to_date('"+df.format(matb.getCdate1())+" 23:59:59','yyyy-mm-dd hh24:mi:ss') " ;
		}else if (date_flag == false ){
			//查询当天 to_date('1987-09-18','yyyy-mm-dd')
			sql +=" and m.cdate >=  to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss')" ;
			sqlCount +=" and m.cdate >=  to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss')" ;
		}
		
		if (matb.getSort() != null) {
			sql += " ORDER BY " + matb.getSort() + " " + matb.getOrder();
		}
		List<MerchantAuthenticityModel> MerchantAList = merchantAuthenticityDao.querySQLMerchantAuthenticity(sql, map, matb.getPage(), matb.getRows(), MerchantAuthenticityModel.class);
		BigDecimal count = merchantAuthenticityDao.querysqlCounts(sqlCount, map);
		
		DataGridBean dataGridBean = formatToDataGrid(MerchantAList, count.intValue());
		
		return dataGridBean;
	}
	
	@Override
	public List<Map<String, Object>> exportHrtAuthenticity(MerchantAuthenticityBean matb, UserBean user) {
		String sql = "";
		boolean date_flag = false ;
		Map<String, Object> map = new HashMap<String, Object>();
		sql = "SELECT * FROM  BILL_MERAUTHINFO m WHERE  1=1 ";
		
		if (matb.getUnno() != null&&!"".equals(matb.getUnno())) {
			sql +=" and m.unno like :unno ";
			map.put("unno", '%'+matb.getUnno()+'%');
			date_flag = true ;
		}
		if (matb.getAuthType() != null&&!"".equals(matb.getAuthType())) {
			sql +=" and m.authType =:authType ";
			map.put("authType", matb.getAuthType());
		}
		if (matb.getBankAccName() != null&&!"".equals(matb.getBankAccName())) {
			sql +=" and m.bankAccName like :bankAccName ";
			map.put("bankAccName", '%'+matb.getBankAccName()+'%');
			date_flag = true ;
		}
		if (matb.getBankAccNo()!= null && !"".equals(matb.getBankAccNo())) {
			sql +=" and m.bankAccNo =:bankAccNo ";
			map.put("bankAccNo", matb.getBankAccNo());
			date_flag = true ;
		}
		if(matb.getStatus()== null || "".equals(matb.getStatus())){
			//所有
		}else if(matb.getStatus()== "00" || "00".equals(matb.getStatus())){
			//认证成功
			sql +=" and m.status ='00' and m.respcd='2000' ";
		}else if(matb.getStatus()== "01" || "01".equals(matb.getStatus())){
			//认证失败
			sql +=" and m.status ='00' and m.respcd!='2000' ";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		if(matb.getCdate()== null && "".equals(matb.getCdate())&&date_flag == true){
			//当有其他条件且时间为空时 ，查寻 不考虑时间
		}else if (matb.getCdate()!= null && !"".equals(matb.getCdate())&&matb.getCdate1()!= null && !"".equals(matb.getCdate1())) {
			sql +=" and m.cdate >=  to_date('"+df.format(matb.getCdate())+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and m.cdate <=  to_date('"+df.format(matb.getCdate1())+" 23:59:59','yyyy-mm-dd hh24:mi:ss') " ;
		}else if (date_flag == false ){
			sql +=" and m.cdate >=  to_date('"+df.format(new Date())+" 00:00:00','yyyy-mm-dd hh24:mi:ss')" ;
		}
		
		if (matb.getSort() != null) {
			sql += " ORDER BY " + matb.getSort() + " " + matb.getOrder();
		}
		List<Map<String, Object>> list = merchantAuthenticityDao.queryObjectsBySqlListMap2(sql, map);
		
		return list;
	}

	/**
	 * 商户信用卡认证信息获取
	 * @param mid
	 * @param legalPerson
	 * @param legalNum
	 * @return
	 */
	@Override
	public MerchantAuthenticityModel getSuccessInfoMerTypeByMid(String mid,String legalPerson,String legalNum){
        // @author:lxg-20190809 获取手机号已存在的商户信息及信用卡认证信息
		String hqlMerAuth = "from MerchantAuthenticityModel k where k.mid=? and k.bankAccName=? and k.legalNum=? and k.status='00' and k.respcd='2000' and k.authType='MER'";
		List<MerchantAuthenticityModel> list = merchantAuthenticityDao.queryObjectsByHqlList(hqlMerAuth,new Object[]{mid,legalPerson,legalNum});
		return list.size()>0?list.get(0):null;
	}

	/**
	 * 银收宝商户信用卡认证信息获取
	 * @param mid
	 * @param legalNum
	 * @return
	 */
	@Override
	public MerchantAuthenticityModel getSuccessInfoMerTypeByYsbMid(String mid,String legalNum){
		// @author:lxg-20190809 获取手机号已存在的商户信息及信用卡认证信息
		String hqlMerAuth = "from MerchantAuthenticityModel k where k.mid=? and k.legalNum=? and k.status='00' and k.respcd='2000' and k.authType='MER'";
		List<MerchantAuthenticityModel> list = merchantAuthenticityDao.queryObjectsByHqlList(hqlMerAuth,new Object[]{mid,legalNum});
		return list.size()>0?list.get(0):null;
	}
	
	
	@Override
	public Integer queryMerchantAuthCountForZfb(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.mid=:MID "
				+ " and t.bankaccname = :bankaccname and t.legalnum = :legalnum "
				+ " and t.authType=:authType and t.respcd = '2001' and t.cdate >= trunc(sysdate) " ;
		map.put("MID", matb.getMid());
		map.put("authType",matb.getAuthType());
		map.put("legalnum", matb.getLegalNum());
		map.put("bankaccname", matb.getBankAccName());
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public Integer queryMerchantAuthIsReadyForZfb(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.mid=:MID "
				+ " and t.bankaccname = :bankaccname and t.legalnum = :legalnum "
				+ " and t.authType=:authType and t.respcd = '1999' " ;
		map.put("MID", matb.getMid());
		map.put("authType",matb.getAuthType());
		map.put("legalnum", matb.getLegalNum());
		map.put("bankaccname", matb.getBankAccName());
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public List<Map<String, String>> queryCertifyIdForOrderNo(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select t.sysseqnb,t.respcd from bill_merauthinfo t where t.cardholdername=:outer_order_no and t.authType=:authType";
		map.put("outer_order_no", matb.getAuthUpload());
		map.put("authType", matb.getAuthType());
		List<Map<String,String>> list = merchantAuthenticityDao.queryObjectsBySqlListMap(sql, map);
		return list;
	}
	@Override
	public String updateAuthenticityForZFB(MerchantAuthenticityBean matb, String flag, String string) {
		String status=null;
		Map<String,Object> map = new HashMap<String, Object>();
		String sql = " update bill_merauthinfo m set m.status='00',"
				+ " m.respcd=:respcd,m.respinfo=:respinfo,m.cdate=sysdate"
				+ " where m.cardholdername = :authupload and m.cardname = '13'";
		
		String sql1 = " update bill_merauthinfo m set m.status='00',"
				+ " m.respcd=:respcd,m.respinfo=:respinfo,m.cdate=sysdate"
				+ " where m.mid = :mid and m.cardname = '13' and M.respcd = '1999'"
				+ " and M.bankAccName=:bankAccName and M.legalNum =:legalNum";
		
		if("1".equals(string)) {
			if("T".equals(flag)) {
				map.put("respcd", "2000");
				map.put("respinfo", "支付宝人脸认证通过");
				status = "2000";
				
				Map<String, String> result = new HashMap<String, String>();
				result.put("userName", matb.getUsername());
				result.put("mid", matb.getMid());
				result.put("msg", "认证成功");
				result.put("status", "2");
				sendResultToHyb(result, matb);
			}else {
				map.put("respcd", "2001");
				map.put("respinfo", "支付宝人脸认证不通过");
				status = "2001";
				
				Map<String, String> result = new HashMap<String, String>();
				result.put("msg", "认证失败！");
				result.put("status", "1");
				result.put("userName", matb.getUsername());
				result.put("mid", matb.getMid());
				sendResultToHyb(result, matb);
			}
		}else {
			map.put("respcd", "2001");
			map.put("respinfo", flag);
			status = "2001";
			
			Map<String, String> result = new HashMap<String, String>();
			result.put("msg", "认证失败！");
			result.put("status", "1");
			result.put("userName", matb.getUsername());
			result.put("mid", matb.getMid());
			sendResultToHyb(result, matb);
		}
		if(matb.getAuthUpload()!=null && !"".equals(matb.getAuthUpload())) {
			map.put("authupload", matb.getAuthUpload());
			merchantAuthenticityDao.executeSqlUpdate(sql, map);
		}else {
			map.put("mid", matb.getMid());
			map.put("bankAccName", matb.getBankAccName());
			map.put("legalNum", matb.getLegalNum());
			merchantAuthenticityDao.executeSqlUpdate(sql1, map);
		}
		
		//修改商户状态
		if("2000".equals(status)){
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=2 where m.mid=?",new Object[]{matb.getMid()});
		}else {
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=1 where m.mid=?",new Object[]{matb.getMid()});
		}
		
		return status;
	}
	@Override
	public void saveMerchantAuthInfoZFB(MerchantAuthenticityBean matb) {
		MerchantAuthenticityModel matm=new MerchantAuthenticityModel();
		BeanUtils.copyProperties(matb,matm);
		matm.setRespcd("1999");
		matm.setSendType("5");
		matm.setCardName("13");
		matm.setCdate(new Date());
		merchantAuthenticityDao.saveObject(matm);
	}
	@Override
	public List<Map<String, String>> queryCertifyIdForMatb(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql= " select m1.*,rownum FROM ("
				+ "select t.sysseqnb,t.respcd from bill_merauthinfo t "
				+ " where t.mid=:mid and t.bankAccName = :bankAccName"
				+ " and t.legalNum=:legalNum and t.authType=:authType"
				+ " ORDER BY t.cdate DESC) m1"
				+ " where rownum = 1";
		map.put("mid", matb.getMid());
		map.put("bankAccName", matb.getBankAccName());
		map.put("legalNum", matb.getLegalNum());
		map.put("authType", matb.getAuthType());
		List<Map<String,String>> list = merchantAuthenticityDao.queryObjectsBySqlListMap(sql, map);
		return list;
	}
	@Override
	public Integer queryMerchantAuthIsSuccessForZfb(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select count(1) from bill_merauthinfo t where t.mid=:MID "
				+ " and t.bankaccname = :bankaccname and t.legalnum = :legalnum "
				+ " and t.authType=:authType and t.respcd = '2000' " ;
		map.put("MID", matb.getMid());
		map.put("authType",matb.getAuthType());
		map.put("legalnum", matb.getLegalNum());
		map.put("bankaccname", matb.getBankAccName());
		Integer count =merchantAuthenticityDao.querysqlCounts2(sql, map);
		return count;
	}
	@Override
	public List<Map<String, String>> queryCertifyIdForMatbForOrderNo(MerchantAuthenticityBean matb) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql="select t.cardholdername from bill_merauthinfo t "
				+ " where t.mid=:mid and t.bankAccName = :bankAccName"
				+ " and t.legalNum=:legalNum and t.authType=:authType"
				+ " and t.respcd = '1999'";
		map.put("mid", matb.getMid());
		map.put("bankAccName", matb.getBankAccName());
		map.put("legalNum", matb.getLegalNum());
		map.put("authType", matb.getAuthType());
		List<Map<String,String>> list = merchantAuthenticityDao.queryObjectsBySqlListMap(sql, map);
		return list;
	}
}