package com.hrt.biz.bill.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.hrt.frame.constant.PhoneProdConstant;
import com.hrt.phone.dao.IPhoneMicroMerchantInfoDao;
import com.hrt.util.ParamPropUtils;
import org.apache.commons.collections.CollectionUtils;
import com.hrt.frame.constant.Constant;
import com.hrt.util.PictureWaterMarkUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.argorse.security.core.hash.MD5Wrapper;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IAggPayTerminfoDao;
import com.hrt.biz.bill.dao.IMIDSeqInfoDao;
import com.hrt.biz.bill.dao.IMachineInfoDao;
import com.hrt.biz.bill.dao.IMachineTaskDataDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.IMerchantInfoTempDao;
import com.hrt.biz.bill.dao.IMerchantTaskDetailDao;
import com.hrt.biz.bill.dao.IMerchantTerminalInfoDao;
import com.hrt.biz.bill.dao.ITerminalInfoDao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.AggPayTerminfoModel;
import com.hrt.biz.bill.entity.model.MIDSeqInfoModel;
import com.hrt.biz.bill.entity.model.MachineInfoModel;
import com.hrt.biz.bill.entity.model.MachineTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantAuthenticityModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantInfoTempModel;
import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.biz.bill.entity.model.TerminalInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantAuthenticityBean;
import com.hrt.biz.bill.entity.pagebean.MerchantInfoBean;
import com.hrt.biz.bill.entity.pagebean.MerchantTerminalInfoBean;
import com.hrt.biz.bill.service.IMerchantAuthenticityService;
import com.hrt.biz.bill.service.IMerchantInfoService;
import com.hrt.biz.util.JSONObject;
import com.hrt.biz.util.QRToImageWriter;
import com.hrt.frame.dao.sysadmin.IRoleDao;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.dao.sysadmin.IUserDao;
import com.hrt.frame.entity.model.RoleModel;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.model.UserModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.gmms.webservice.AggPayTerm;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.gmms.webservice.MerchantInfo;
import com.hrt.gmms.webservice.TermAcc;
import com.hrt.phone.entity.model.MerchantBankCardModel;
import com.hrt.util.ClassToJavaBeanUtil;
import com.hrt.util.HttpXmlClient;

public class MerchantInfoServiceImpl implements IMerchantInfoService {

	private IMerchantInfoDao merchantInfoDao;

	private ITodoDao todoDao;

	private IGmmsService gsClient;

	private IMIDSeqInfoDao midSeqInfoDao;

	private IUserDao userDao;

	private IRoleDao roleDao;

	private IUnitDao unitDao;

	private IAgentSalesDao agentSalesDao;

	private IMerchantTerminalInfoDao merchantTerminalInfoDao;

	private IMachineInfoDao machineInfoDao;

	private IMachineTaskDataDao machineTaskDataDao;

	private ITerminalInfoDao terminalInfoDao;

	private IMerchantInfoTempDao merchantInfoTempDao;

	private IMerchantTaskDetailDao merchantTaskDetailDao;

	private IAggPayTerminfoDao aggPayTerminfoDao;

	private IMerchantAuthenticityService merchantAuthenticityService;
	private IPhoneMicroMerchantInfoDao phoneMicroMerchantInfoDao;

	private String hybUnbindUrl;

	private String connectionHybUrl;

	private String hybCube;
	private String admAppIp;

	public IPhoneMicroMerchantInfoDao getPhoneMicroMerchantInfoDao() {
		return phoneMicroMerchantInfoDao;
	}

	public void setPhoneMicroMerchantInfoDao(IPhoneMicroMerchantInfoDao phoneMicroMerchantInfoDao) {
		this.phoneMicroMerchantInfoDao = phoneMicroMerchantInfoDao;
	}

	public IMerchantAuthenticityService getMerchantAuthenticityService() {
		return merchantAuthenticityService;
	}

	public void setMerchantAuthenticityService(IMerchantAuthenticityService merchantAuthenticityService) {
		this.merchantAuthenticityService = merchantAuthenticityService;
	}

	public String getAdmAppIp() {
		return admAppIp;
	}

	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}

	public String getHybCube() {
		return hybCube;
	}

	public void setHybCube(String hybCube) {
		this.hybCube = hybCube;
	}

	public String getConnectionHybUrl() {
		return connectionHybUrl;
	}

	public void setConnectionHybUrl(String connectionHybUrl) {
		this.connectionHybUrl = connectionHybUrl;
	}

	private static final Log log = LogFactory.getLog(MerchantInfoServiceImpl.class);

	public IAggPayTerminfoDao getAggPayTerminfoDao() {
		return aggPayTerminfoDao;
	}

	public void setAggPayTerminfoDao(IAggPayTerminfoDao aggPayTerminfoDao) {
		this.aggPayTerminfoDao = aggPayTerminfoDao;
	}

	public IGmmsService getGsClient() {
		return gsClient;
	}

	public void setGsClient(IGmmsService gsClient) {
		this.gsClient = gsClient;
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

	public IMIDSeqInfoDao getMidSeqInfoDao() {
		return midSeqInfoDao;
	}

	public void setMidSeqInfoDao(IMIDSeqInfoDao midSeqInfoDao) {
		this.midSeqInfoDao = midSeqInfoDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
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

	public IMachineInfoDao getMachineInfoDao() {
		return machineInfoDao;
	}

	public void setMachineInfoDao(IMachineInfoDao machineInfoDao) {
		this.machineInfoDao = machineInfoDao;
	}

	public IMachineTaskDataDao getMachineTaskDataDao() {
		return machineTaskDataDao;
	}

	public void setMachineTaskDataDao(IMachineTaskDataDao machineTaskDataDao) {
		this.machineTaskDataDao = machineTaskDataDao;
	}

	public ITerminalInfoDao getTerminalInfoDao() {
		return terminalInfoDao;
	}

	public void setTerminalInfoDao(ITerminalInfoDao terminalInfoDao) {
		this.terminalInfoDao = terminalInfoDao;
	}

	public IMerchantInfoTempDao getMerchantInfoTempDao() {
		return merchantInfoTempDao;
	}

	public void setMerchantInfoTempDao(IMerchantInfoTempDao merchantInfoTempDao) {
		this.merchantInfoTempDao = merchantInfoTempDao;
	}

	public IMerchantTaskDetailDao getMerchantTaskDetailDao() {
		return merchantTaskDetailDao;
	}

	public void setMerchantTaskDetailDao(
			IMerchantTaskDetailDao merchantTaskDetailDao) {
		this.merchantTaskDetailDao = merchantTaskDetailDao;
	}

	public String getHybUnbindUrl() {
		return hybUnbindUrl;
	}

	public void setHybUnbindUrl(String hybUnbindUrl) {
		this.hybUnbindUrl = hybUnbindUrl;
	}
	/**
	 * 查询未认证商户
	 */
	@Override
	public DataGridBean queryNoMerchant(MerchantInfoBean matb, UserBean user) throws Exception {
		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		sql="select m.* from bill_merchantinfo m where (m.authType = 0 ) or (m.authType = null ) ";
		sqlCount="select count(*) from bill_merchantinfo m where (m.authType = 0 ) or (m.authType = null ) ";
		if (matb.getMid() != null && !"".equals(matb.getMid())) {
			sql +=" and m.mid =:mid ";
			sqlCount +=" and m.mid =:mid ";
			map.put("mid", matb.getMid());
		}
		if (matb.getRname() != null && !"".equals(matb.getRname())){
			sql +=" and m.rname like :rname ";
			sqlCount +=" and m.rname like :rname ";
			map.put("rname", matb.getRname()+"%");
		}
		if (matb.getSort() != null) {
			sql += " ORDER BY " + matb.getSort() + " " + matb.getOrder();
		}
		List<MerchantInfoModel> MerchantAList = merchantInfoDao.queryMerchantInfoSql(sql, map, matb.getPage(), matb.getRows(), MerchantInfoModel.class);
		BigDecimal count = merchantInfoDao.querysqlCounts(sqlCount, map);
		DataGridBean dataGridBean = formatToDataGrid(MerchantAList, count.intValue());

		return dataGridBean;  
	}
	/**
	 * 修改未认证商户为认证成功（手录认证）
	 */
	public boolean updateMerchantGoByMid(String mid, UserBean user) throws Exception {
		boolean flag = false ;
		String hql = " update MerchantInfoModel b set b.authType = 2 where mid = '"+ mid +"'" ;
		Integer i = merchantInfoDao.executeHql(hql);
		if(i>0){
			flag=true;
		}
		return flag;

	}
	/**
	 * 查询认证失败统计
	 */
	@Override
	public DataGridBean queryMerchantFalse(MerchantInfoBean matb, UserBean user) throws Exception {

		String sql = "";
		String sqlCount = "";
		Map<String, Object> map = new HashMap<String, Object>();
		sql = "select  (select u.unitName from UnitModel u where u.unNo =mc.unno) as unitName,mc,m.cardName ,m.bankAccNo ,m.bankAccName ,m.legalNum "
				+"from MerchantAuthenticityModel m,MerchantInfoModel  mc "
				+"where  m.mid=mc.mid  and m.bmatid in(select max(a.bmatid)  from MerchantAuthenticityModel a,MerchantInfoModel b "
				+"where a.mid = b.mid  AND a.authType='MER' and a.cardName != '13'";
		//		sql = "select  (select u.unitName from UnitModel u where u.unNo =su.parent) as unitName,mc,m.cardName ,m.bankAccNo ,m.bankAccName ,m.legalNum "
		//			+"from MerchantAuthenticityModel m,UnitModel su,MerchantInfoModel  mc "
		//			+"where  m.mid=mc.mid and mc.unno=su.unNo  "
		//			+"and m.bmatid in(select max(a.bmatid)  from MerchantAuthenticityModel a,MerchantInfoModel b "
		//			+"where a.mid = b.mid  AND a.authType='MER' ";
		sqlCount = "select  COUNT(*) from bill_merauthinfo m,bill_merchantinfo  mc "
				+"where  m.mid=mc.mid and m.bmatid in(select max(a.bmatid) from bill_merauthinfo a,bill_merchantinfo b "
				+"where a.mid = b.mid AND a.authType='MER' and a.cardName != '13'";

		if (matb.getAuthType() ==null||"".equals(matb.getAuthType()+"")) {

			sql +=" and  (b.authType =1 or b.authType =3) group by a.mid) ";
			sqlCount +="and (b.authType =1 or b.authType =3) group by a.mid) ";
		} else if(matb.getAuthType() !=null&&!"".equals(matb.getAuthType()+"")){
			sql +=" and   b.authType =:Type  group by a.mid) ";
			sqlCount +=" and  b.authType =:Type  group by a.mid) ";
			map.put("Type", matb.getAuthType());
		}	
		if (matb.getAuthType() ==null||"".equals(matb.getAuthType()+"")) {
			//0未认证  1失败  2成功 3入账中 4入账失败
			sql +="  and (mc.authType=1 or mc.authType=3) ";
			sqlCount +="  and (mc.authType=1 or mc.authType=3) ";
		} else if(matb.getAuthType() !=null&&!"".equals(matb.getAuthType()+"")){
			sql +=" and mc.authType=:authType ";
			sqlCount +=" and mc.authType=:authType ";
			map.put("authType", matb.getAuthType());
		}	
		if (matb.getMid() != null && !"".equals(matb.getMid())) {
			sql +=" and m.mid =:mid ";
			sqlCount +=" and m.mid =:mid ";
			map.put("mid", matb.getMid());
		}	

		if (matb.getSort() != null) {
			sql += " ORDER BY " + matb.getSort() + " " + matb.getOrder();
		}
		List<MerchantInfoModel> MerchantAList = merchantInfoDao.queryMerchantInfo(sql, map, matb.getPage(), matb.getRows());
		BigDecimal count = merchantInfoDao.querysqlCounts(sqlCount, map);
		DataGridBean dataGridBean = formatToDataGridFalse(MerchantAList, count.intValue());

		return dataGridBean;  
	}
	/**
	 * 认证失败统计
	 * 方法功能：格式化MerchantAuthenticityModel为datagrid数据格式
	 * 参数：MerchantAuthenticityList
	 * 		total 总记录数
	 */
	private DataGridBean formatToDataGridFalse(List<MerchantInfoModel> MerchantAList, long total) {
		List<MerchantInfoBean> merchantABeanList = new ArrayList<MerchantInfoBean>();
		for(Object ob : MerchantAList) {
			Object[] arry=(Object[]) ob;
			//归属
			String belonging =(String) arry[0]; 
			MerchantInfoModel merchantAModel=(MerchantInfoModel) arry[1];
			merchantAModel.setUnitName(belonging);
			MerchantInfoBean merchantABean = new MerchantInfoBean();
			BeanUtils.copyProperties(merchantAModel, merchantABean);
			//持卡人姓名
			String cardName = (String) arry[2];
			merchantABean.setCardName(cardName);
			//卡号
			String bankAccNo = (String) arry[3];
			merchantABean.setBankAccNo(bankAccNo);
			//账号人身份证号
			String legalNum =(String) arry[5];
			merchantABean.setLegalNum(legalNum);
			//账号名称
			String bankAccName =(String) arry[4];
			merchantABean.setBankAccName(bankAccName);
			merchantABeanList.add(merchantABean);
		}

		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(merchantABeanList);

		return dgb;
	}

	/*
	 * 人工实名认证批量导入
	 */
	@Override
	public List<Map<String, String>> updateAllMerchantAuth(String xlsfile, String fileName, UserBean user) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			File filename = new File(xlsfile);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Object> arrayList = new ArrayList<Object>();
			for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				HSSFRow row = sheet.getRow(i);
				String mid="";
				String bankAccNo="";
				String bankAccName="";
				String legalNum="";
				String goOrBack="";
				boolean flag = false;
				HSSFCell cell = row.getCell((short)0);
				if(cell == null || cell.toString().trim().equals("")){
					break;
				}else{
					//商户号
					row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					mid = row.getCell((short)0).getStringCellValue().trim();
					//卡号
					row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					bankAccNo = row.getCell((short)1).getStringCellValue().trim();
					//名称
					row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					bankAccName = row.getCell((short)2).getStringCellValue().trim();
					//身份证
					row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					legalNum = row.getCell((short)3).getStringCellValue().trim();
					//操作
					row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
					goOrBack = row.getCell((short)4).getStringCellValue().trim();
					if("".equals(mid)||"".equals(bankAccNo)||"".equals(bankAccName)||"".equals(legalNum)||"".equals(goOrBack)
							||mid==null||bankAccNo==null||bankAccName==null||legalNum==null||goOrBack==null) {
						Map<String,String> map = new HashMap<String,String>();
						map.put("mid", mid);
						map.put("bankAccNo", bankAccNo);
						map.put("bankAccName", bankAccName);
						map.put("legalNum", legalNum);
						map.put("goOrBack", goOrBack);
						map.put("remark", "请确认是否所有信息都已填写");
						list.add(map);
						continue;
					}
					if("通过".equals(goOrBack)){
						flag = MerchantAuthGo(mid, bankAccNo, bankAccName, legalNum);
					}else if("退回".equals(goOrBack)){
						flag = MerchantAuthBack(mid, bankAccNo, bankAccName, legalNum);
					}else{
						Map<String,String> map = new HashMap<String,String>();
						map.put("mid", mid);
						map.put("bankAccNo", bankAccNo);
						map.put("bankAccName", bankAccName);
						map.put("legalNum", legalNum);
						map.put("goOrBack", goOrBack);
						map.put("remark", "操作信息填写有误");
						list.add(map);
						continue;
					}
					if(flag && "通过".equals(goOrBack)){
						MerchantAuthenticityBean matb= new MerchantAuthenticityBean();
						Map<String, String> result= new HashMap<String, String>();
						MerchantAuthenticityModel authenticityModel = merchantAuthenticityService.queryMerchantAuthenticityByMid(mid);
						matb.setUsername(authenticityModel.getUsername());
						matb.setMid(authenticityModel.getMid());
						result.put("userName",matb.getUsername());
						result.put("mid",matb.getMid());
						result.put("msg","认证成功");
						result.put("status","2");
						merchantAuthenticityService.sendResultToHyb(result,matb);
					}else if(flag && "退回".equals(goOrBack)){
					}else{
						Map<String,String> map = new HashMap<String,String>();
						map.put("mid", mid);
						map.put("bankAccNo", bankAccNo);
						map.put("bankAccName", bankAccName);
						map.put("legalNum", legalNum);
						map.put("goOrBack", goOrBack);
						map.put("remark", "通过或退回失败");
						list.add(map);
						continue;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 认证通过认证失败统一查询
	 *  0未认证  1失败  2成功 3入账中 4入账失败
	 */
	public boolean MerchantAuthGo(String mid,String bankAccNo,String bankAccName,String legalNum)throws Exception{
		try{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int a = merchantInfoDao.executeHql("update MerchantAuthenticityModel m set m.status='00',m.respcd='2000',m.respinfo='人工认证成功;'||respinfo " +
					" ,m.cdate=to_date('"+df.format(new Date())+"','yyyy-MM-dd hh24:mi:ss') " +
					" where m.mid=? and m.bankAccNo=? and m.bankAccName=? and m.legalNum=? ",new Object[]{mid,bankAccNo,bankAccName,legalNum});
			if(a==0){
				return false;
			}
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=2 where m.mid=?",new Object[]{mid});
			return true;
		}catch (Exception e) {
			log.info("人工实名认证通过异常"+e);
		}
		return false;
	}
	/**
	 * 认证回退认证失败统一查询
	 *  0未认证  1失败  2成功 3入账中 4入账失败
	 */
	public boolean MerchantAuthBack(String mid,String bankAccNo,String bankAccName,String legalNum)throws Exception{
		try{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int a = merchantInfoDao.executeHql("update MerchantAuthenticityModel m set m.status='00',m.respcd='2001',m.respinfo='人工入账失败;'||respinfo " +
					",m.cdate=to_date('"+df.format(new Date())+"','yyyy-MM-dd hh24:mi:ss'),m.approveNote='人工入账失败' " +
					"  where m.mid=? and m.bankAccNo=? and m.bankAccName=? and m.legalNum=? ",new Object[]{mid,bankAccNo,bankAccName,legalNum});
			if(a==0){
				return false;
			}
			merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=4 where m.mid=?",new Object[]{mid});
			return true;
		}catch (Exception e) {
			log.info("人工实名认证退回异常"+e);
		}
		return false;
	}
	/**
	 * 修改商户anthType为3 入账中
	 */
	@Override
	public void updateMerAuthType(String bmids) {
		//修改状态
		merchantInfoDao.executeUpdate("update bill_merchantinfo m set m.authType=3 where m.bmid IN ("+bmids+")");
	}
	/**
	 * 提交结算
	 * 0未认证  1失败  2成功 3入账中 4入账失败
	 */
	public HSSFWorkbook export(String bmids)throws Exception{
		//修改状态
		//		merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=3 where m.bmid IN ("+bmids+")");

		//导出excel 
		String sql1="select z.*,row_number()over (order by 1) SERIAL from( select distinct 48641000 PAYACCOUNT,t.mid,0.01 PAYMENT,m.BANkACCNO B,m.BANKACCNAME,m.CARDNAME,'补充认证' REMARK,''  PAYEENO," +
				"0 IFPUBLIC from bill_merchantinfo t,bill_merauthinfo m WHERE " +
				" m.bmatid IN (select max(a.bmatid)  from MerchantAuthenticityModel a,MerchantInfoModel b "
				+ " where a.mid = b.mid) and t.mid=m.mid and t.mid in("+bmids+")) z";

		//		String sql="select z.*,row_number()over (order by 1) SERIAL from( select distinct 48641000 PAYACCOUNT,t.mid,0.01 PAYMENT,m.BANkACCNO B,m.BANKACCNAME,m.CARDNAME,'补充认证' REMARK,''  PAYEENO," +
		//		"0 IFPUBLIC from bill_merchantinfo t,bill_merauthinfo m WHERE t.bmid IN ("+bmids+") and t.mid=m.mid ) z";
		//优化实名人工实名认证
		String sql="SELECT DISTINCT 48641000 AS PAYACCOUNT, b.mid, 0.01 AS PAYMENT, b.BANkACCNO AS B, b.BANKACCNAME ,b.legalNum, b.CARDNAME, "
				+ "'补充认证' AS REMARK, NULL AS PAYEENO, 0 AS IFPUBLIC, ROW_NUMBER() OVER (ORDER BY 1) AS SERIAL FROM ( SELECT cc.*,"
				+ " ROW_NUMBER() OVER (PARTITION BY mid ORDER BY bmatid DESC) AS rn FROM bill_merauthinfo cc WHERE cc.mid IN "
				+ "( SELECT mid FROM bill_merchantinfo WHERE bmid IN ("+bmids+") ) ) b WHERE b.rn = 1";


		List<Map<String, Object>> data=merchantInfoDao.queryObjectsBySqlList(sql);
		List<String> excelHeader=new ArrayList<String>();
		Map<String, Object> headMap=new HashMap<String, Object>();

		excelHeader.add("PAYACCOUNT");		
		excelHeader.add("MID");
		excelHeader.add("PAYMENT");
		excelHeader.add("B");
		excelHeader.add("BANKACCNAME");
		excelHeader.add("LEGALNUM");
		excelHeader.add("CARDNAME");
		excelHeader.add("REMARK");
		excelHeader.add("PAYEENO");
		excelHeader.add("SERIAL");
		excelHeader.add("IFPUBLIC");

		headMap.put("PAYACCOUNT", "付款账号");
		headMap.put("MID", "商户编号");
		headMap.put("PAYMENT", "付款金额");
		headMap.put("B", "收款人帐号");
		headMap.put("BANKACCNAME", "收款人名称");
		headMap.put("LEGALNUM", "收款人身份证号");
		headMap.put("CARDNAME", "通道");
		headMap.put("REMARK", "附言");
		headMap.put("PAYEENO", "收款行人行号");
		headMap.put("IFPUBLIC", "是否对公账户");
		headMap.put("SERIAL", "序列号");
		return ExcelUtil.export("提交结算资料", data, excelHeader, headMap);
	}
	/**
	 * 查询流水号
	 */
	public  synchronized Integer searchMIDSeqInfo(String areaCode, String mccid,String loginName ){
		String sql = "SELECT * FROM BILL_MIDSEQINFO WHERE AREA_CODE = '" + areaCode + "' AND MCCID = '" + mccid + "'";
		List<MIDSeqInfoModel> midSeqInfoList;
		Integer seqNo = 600;
		//synchronized (MIDSeqInfoDaoImpl.class){
		midSeqInfoList = midSeqInfoDao.queryObjectsBySqlList(sql, null, MIDSeqInfoModel.class);
		if(midSeqInfoList != null && midSeqInfoList.size() > 0){
			MIDSeqInfoModel midSeqInfoModel = midSeqInfoList.get(0);
			seqNo = midSeqInfoModel.getSeqNo() + 1;
			midSeqInfoModel.setSeqNo(seqNo);
			midSeqInfoDao.updateObject(midSeqInfoModel);
		}else{
			MIDSeqInfoModel midSeqInfo = new MIDSeqInfoModel();
			midSeqInfo.setAreaCode(areaCode);
			midSeqInfo.setMccid(mccid);
			midSeqInfo.setSeqNo(seqNo);
			midSeqInfo.setCreateDate(new Date());
			midSeqInfo.setCreateUser(loginName);
			midSeqInfoDao.saveObject(midSeqInfo);
		}
		//}
		return seqNo;
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
	 * 传统商户上传
	 */
	private void uploadFile2(File upload, String fName, String imageDay) {
		try {
			String realPath = queryUpLoadPath2() + File.separator + imageDay;
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
	 * 方法功能：格式化MerchantInfoModel为datagrid数据格式
	 * 参数：merchantInfoList
	 *     total 总记录数
	 *     i 是否隐藏手机号
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<MerchantInfoModel> merchantInfoList, long total,Integer i) {
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
			if(merchantInfoModel.getContactPhone()!=null&&i>0){
				merchantInfoBean.setContactPhone(merchantInfoModel.getContactPhone().substring(0, 3)+"****"+merchantInfoModel.getContactPhone().substring(7));
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

			//机构名称
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, merchantInfoModel.getUnno());
			if(unitModel != null){
				merchantInfoBean.setUnitName(unitModel.getUnitName());
				if(unitModel.getUnLvl()==7){
					UnitModel unitModel1= unitModel.getParent().getParent().getParent().getParent();
					merchantInfoBean.setParentUnitName(unitModel1.getUnitName());
					merchantInfoBean.setAgentAttr(unitModel1.getAgentAttr());
				}else if(unitModel.getUnLvl()==6){
					UnitModel unitModel2=unitModel.getParent().getParent().getParent();
					merchantInfoBean.setParentUnitName(unitModel2.getUnitName());
					merchantInfoBean.setAgentAttr(unitModel2.getAgentAttr());
				}else if(unitModel.getUnLvl()==5){
					UnitModel unitModel3=unitModel.getParent().getParent();
					merchantInfoBean.setParentUnitName(unitModel3.getUnitName());
					merchantInfoBean.setAgentAttr(unitModel3.getAgentAttr());
				}else if(unitModel.getUnLvl()==3){
					UnitModel unitModel4=unitModel.getParent();
					merchantInfoBean.setParentUnitName(unitModel4.getUnitName());
					merchantInfoBean.setAgentAttr(unitModel4.getAgentAttr());
				}else if(unitModel.getUnLvl()==2){
					Integer j = merchantInfoDao.querysqlCounts2("select count(1) from sys_configure a where a.groupname='phoneFilter' and a.minfo1='"+unitModel.getParent().getUnNo()+"'", null);
					if(j>0) {
						merchantInfoBean.setParentUnitName("");
					}else {
						merchantInfoBean.setParentUnitName(unitModel.getParent().getUnitName());
						merchantInfoBean.setAgentAttr(unitModel.getAgentAttr());
					}
				}else if(unitModel.getUnLvl()==1){
					merchantInfoBean.setParentUnitName(unitModel.getParent().getUnitName());
				}else{
					merchantInfoBean.setParentUnitName("");
				}

			}

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

			//上级商户名称
			if(merchantInfoBean.getParentMID() != null && !"".equals(merchantInfoBean.getParentMID().trim())){
				if(parentMIDName(merchantInfoModel.getParentMID()) != null && !"".equals(parentMIDName(merchantInfoModel.getParentMID()).trim())){
					merchantInfoBean.setParentMIDName(parentMIDName(merchantInfoModel.getParentMID()));
				}
			}

			//商户所在地区码名称
			if(merchantInfoBean.getAreaCode() != null && !"".equals(merchantInfoBean.getAreaCode().trim())){
				String code = areaCodeName(merchantInfoModel.getAreaCode());
				if( code!= null  && !"".equals(code.trim())){
					merchantInfoBean.setAreaCodeName(code);
				}
			}

			//业务人员名称
			if(merchantInfoBean.getBusid() != null&&merchantInfoBean.getBusid() != 1){
				String name = busidName(merchantInfoModel.getBusid());
				if( name!= null && !"".equals(name.trim())){
					merchantInfoBean.setBusidName(name);
				}
			}

			//维护人员名称
			if(merchantInfoBean.getMaintainUserId() != null&&merchantInfoBean.getMaintainUserId() != 1){
				String name1 = busidName(merchantInfoModel.getMaintainUserId());
				if(name1!= null && !"".equals(name1.trim())){
					merchantInfoBean.setMaintainUserIdName(name1);
				}
			}

			//行业选择名称
			if(merchantInfoBean.getIndustryType() != null && !"".equals(merchantInfoBean.getIndustryType().trim())){
				String indus = industryTypeName(merchantInfoBean.getIndustryType());
				if(indus!= null && !"".equals(indus.trim())){
					merchantInfoBean.setIndustryTypeName(indus);
				}
			}

			//行业编码名称
			if(merchantInfoBean.getMccid() != null && !"".equals(merchantInfoBean.getMccid().trim())){
				String mcc = mccidName(merchantInfoBean.getMccid());
				if(mcc!= null && !"".equals(mcc.trim())){
					merchantInfoBean.setMccidName(mcc);
				}
			}

			//确认行业编码名称
			if(merchantInfoBean.getConMccid() != null && !"".equals(merchantInfoBean.getConMccid().trim())){
				String mcc = mccidName(merchantInfoBean.getConMccid());
				if(mcc!= null && !"".equals(mcc.trim())){
					merchantInfoBean.setConMccidName(mcc);
				}
			}

			//受理人员名称
			if(merchantInfoBean.getApproveUid() != null && !"".equals(merchantInfoBean.getApproveUid())){
				UserModel user = userDao.getObjectByID(UserModel.class, merchantInfoBean.getApproveUid());
				if(user != null){
					merchantInfoBean.setApproveUidName(user.getUserName());
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
				Double cfr= creditBankRate.multiply(number).doubleValue();
				merchantInfoBean.setCreditBankRate(cfr.toString());
			}

			//微信费率
			if(merchantInfoBean.getScanRate()!=null&&!"".equals(merchantInfoBean.getScanRate())){
				Double scanRate2 = merchantInfoBean.getScanRate()*100;
				Double dou = (double)Math.round(scanRate2*100)/100 ;
				merchantInfoBean.setScanRate(dou);
				if(merchantInfoModel.getMid().contains("HRTSYT")) {
					merchantInfoBean.setScanRate2(dou);
				}
			}
			//银联二维码费率
			if(merchantInfoBean.getScanRate1()!=null&&!"".equals(merchantInfoBean.getScanRate1())){
				Double scanRate1 = merchantInfoBean.getScanRate1()*100;
				Double dou = (double)Math.round(scanRate1*100)/100 ;
				merchantInfoBean.setScanRate1(dou);
			}
			//支付宝费率
			if(merchantInfoBean.getScanRate2()!=null&&!"".equals(merchantInfoBean.getScanRate2())){
				Double scanRate2 = merchantInfoBean.getScanRate2()*100;
				Double dou = (double)Math.round(scanRate2*100)/100 ;
				if(!merchantInfoModel.getMid().contains("HRTSYT")) {
					merchantInfoBean.setScanRate2(dou);
				}
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
			
			//20191224-ztt修改收银台页面不显示支付宝。。显示扫码1000以上与扫码1000以下
			//显示规则1、特殊机构2个中心5个代理----按商户显示
			//2、华南---1000以上按商户，1000以下需判定---商户维护>0.4，显示0.4。商户维护<0.4,显示商户
			//3、非特殊---显示1000以上0.45，1000以下0.38
			
			if(merchantInfoModel.getUnno()!=null&&!"".equals(merchantInfoModel.getUnno()) 
					&&merchantInfoModel.getMid().contains("HRTSYT")) {
				
				//查询特殊机构配置
				String title1 = "showRateForUnno1";
				String	unnos1 = "";
				String limitUnnoSql1 = " SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title1+"'";
				List<Map<String,Object>> sqlObject1 = unitDao.queryObjectsBySqlObject(limitUnnoSql1);
				if(sqlObject1 ==null || sqlObject1.get(0) == null || sqlObject1.get(0).get("UPLOAD_PATH") == null) {
					unnos1="'-1'";//说明没有限制机构
				}else {
					String[] unnoArrays1 = ((String) sqlObject1.get(0).get("UPLOAD_PATH")).split(",");
					
					for (int j = 0; j < unnoArrays1.length; j++) {
						if(j==0) {
							unnos1 += "'"+unnoArrays1[j]+"'";
						}else {
							unnos1 += ",'"+unnoArrays1[j]+"'";
						}
					}
				}
				
				String title2 = "showRateForUnno2";
				String	unnos2 = "";
				String limitUnnoSql2 = " SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title2+"'";
				List<Map<String,Object>> sqlObject2 = unitDao.queryObjectsBySqlObject(limitUnnoSql2);
				if(sqlObject2 ==null || sqlObject2.get(0) == null || sqlObject2.get(0).get("UPLOAD_PATH") == null) {
					unnos2="'-1'";//说明没有限制机构
				}else {
					String[] unnoArrays2 = ((String) sqlObject2.get(0).get("UPLOAD_PATH")).split(",");
					
					for (int j = 0; j < unnoArrays2.length; j++) {
						if(j==0) {
							unnos2 += "'"+unnoArrays2[j]+"'";
						}else {
							unnos2 += ",'"+unnoArrays2[j]+"'";
						}
					}
				}
				
				
				String judgeUnnoSql1 = " SELECT count(1) FROM ("+
						" SELECT u.unno FROM  sys_unit u"+
						" start with u.unno in ("+unnos1+")"+
						" connect by prior u.unno = u.upper_unit) where unno = '"+merchantInfoModel.getUnno()+"'";
				String judgeUnnoSql2 = " SELECT count(1) FROM ("+
						" SELECT u.unno FROM  sys_unit u"+
						" start with u.unno in ("+unnos2+")"+
						" connect by prior u.unno = u.upper_unit) where unno = '"+merchantInfoModel.getUnno()+"'";
				BigDecimal querysqlCounts1= merchantInfoDao.querysqlCounts(judgeUnnoSql1, null);
				BigDecimal querysqlCounts2 = merchantInfoDao.querysqlCounts(judgeUnnoSql2, null);
			
				if(new BigDecimal("0").compareTo(querysqlCounts1)!=0) {
					//说明是特殊2中心，5机构---按照商户维护费率（所以这里之前有处理的，不需要动）
				}else if(new BigDecimal("0").compareTo(querysqlCounts2)!=0){
					//说明是华南---1000以上按商户scanRate2不动
					//			---1000以下scanRate>0.4----按0.4
					//			---1000以下scanRate<0.4----按商户
					if(merchantInfoBean.getScanRate()!=null&&!"".equals(merchantInfoBean.getScanRate())){
						BigDecimal a1 = new BigDecimal(merchantInfoBean.getScanRate());
						BigDecimal a2 = new BigDecimal(0.4);
						if(a1.compareTo(a2)==-1) {
							//前面有处理，按商户不需要动
						}else{
							merchantInfoBean.setScanRate(0.4);
						}
					}
				}else {
					//说明是非特殊
					merchantInfoBean.setScanRate(0.38);
					merchantInfoBean.setScanRate2(0.45);
				}
			}

			merchantInfoBeanList.add(merchantInfoBean);
		}

		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(merchantInfoBeanList);

		return dgb;
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

			//机构名称
			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, merchantInfoModel.getUnno());
			if(unitModel != null){
				merchantInfoBean.setUnitName(unitModel.getUnitName());
				if(unitModel.getUnLvl()==7){
					UnitModel unitModel1= unitModel.getParent().getParent().getParent().getParent();
					merchantInfoBean.setParentUnitName(unitModel1.getUnitName());
					merchantInfoBean.setAgentAttr(unitModel1.getAgentAttr());
				}else if(unitModel.getUnLvl()==6){
					UnitModel unitModel2=unitModel.getParent().getParent().getParent();
					merchantInfoBean.setParentUnitName(unitModel2.getUnitName());
					merchantInfoBean.setAgentAttr(unitModel2.getAgentAttr());
				}else if(unitModel.getUnLvl()==5){
					UnitModel unitModel3=unitModel.getParent().getParent();
					merchantInfoBean.setParentUnitName(unitModel3.getUnitName());
					merchantInfoBean.setAgentAttr(unitModel3.getAgentAttr());
				}else if(unitModel.getUnLvl()==3){
					UnitModel unitModel4=unitModel.getParent();
					merchantInfoBean.setParentUnitName(unitModel4.getUnitName());
					merchantInfoBean.setAgentAttr(unitModel4.getAgentAttr());
				}else if(unitModel.getUnLvl()==2){
					merchantInfoBean.setParentUnitName(unitModel.getParent().getUnitName());
					merchantInfoBean.setAgentAttr(unitModel.getAgentAttr());
				}else if(unitModel.getUnLvl()==1){
					merchantInfoBean.setParentUnitName(unitModel.getParent().getUnitName());
				}else{
					merchantInfoBean.setParentUnitName("");
				}

			}

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

			//上级商户名称
			if(merchantInfoBean.getParentMID() != null && !"".equals(merchantInfoBean.getParentMID().trim())){
				if(parentMIDName(merchantInfoModel.getParentMID()) != null && !"".equals(parentMIDName(merchantInfoModel.getParentMID()).trim())){
					merchantInfoBean.setParentMIDName(parentMIDName(merchantInfoModel.getParentMID()));
				}
			}

			//商户所在地区码名称
			if(merchantInfoBean.getAreaCode() != null && !"".equals(merchantInfoBean.getAreaCode().trim())){
				String code = areaCodeName(merchantInfoModel.getAreaCode());
				if( code!= null  && !"".equals(code.trim())){
					merchantInfoBean.setAreaCodeName(code);
				}
			}

			//业务人员名称
			if(merchantInfoBean.getBusid() != null&&merchantInfoBean.getBusid() != 1){
				String name = busidName(merchantInfoModel.getBusid());
				if( name!= null && !"".equals(name.trim())){
					merchantInfoBean.setBusidName(name);
				}
			}

			//维护人员名称
			if(merchantInfoBean.getMaintainUserId() != null&&merchantInfoBean.getMaintainUserId() != 1){
				String name1 = busidName(merchantInfoModel.getMaintainUserId());
				if(name1!= null && !"".equals(name1.trim())){
					merchantInfoBean.setMaintainUserIdName(name1);
				}
			}

			//行业选择名称
			if(merchantInfoBean.getIndustryType() != null && !"".equals(merchantInfoBean.getIndustryType().trim())){
				String indus = industryTypeName(merchantInfoBean.getIndustryType());
				if(indus!= null && !"".equals(indus.trim())){
					merchantInfoBean.setIndustryTypeName(indus);
				}
			}

			//行业编码名称
			if(merchantInfoBean.getMccid() != null && !"".equals(merchantInfoBean.getMccid().trim())){
				String mcc = mccidName(merchantInfoBean.getMccid());
				if(mcc!= null && !"".equals(mcc.trim())){
					merchantInfoBean.setMccidName(mcc);
				}
			}

			//确认行业编码名称
			if(merchantInfoBean.getConMccid() != null && !"".equals(merchantInfoBean.getConMccid().trim())){
				String mcc = mccidName(merchantInfoBean.getConMccid());
				if(mcc!= null && !"".equals(mcc.trim())){
					merchantInfoBean.setConMccidName(mcc);
				}
			}

			//受理人员名称
			if(merchantInfoBean.getApproveUid() != null && !"".equals(merchantInfoBean.getApproveUid())){
				UserModel user = userDao.getObjectByID(UserModel.class, merchantInfoBean.getApproveUid());
				if(user != null){
					merchantInfoBean.setApproveUidName(user.getUserName());
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
				Double cfr= creditBankRate.multiply(number).doubleValue();
				merchantInfoBean.setCreditBankRate(cfr.toString());
			}

			//微信费率
			if(merchantInfoBean.getScanRate()!=null&&!"".equals(merchantInfoBean.getScanRate())){
				Double scanRate2 = merchantInfoBean.getScanRate()*100;
				Double dou = (double)Math.round(scanRate2*100)/100 ;
				merchantInfoBean.setScanRate(dou);
			}
			//银联二维码费率
			if(merchantInfoBean.getScanRate1()!=null&&!"".equals(merchantInfoBean.getScanRate1())){
				Double scanRate2 = merchantInfoBean.getScanRate1()*100;
				Double dou = (double)Math.round(scanRate2*100)/100 ;
				merchantInfoBean.setScanRate1(dou);
			}
			//支付宝费率
			if(merchantInfoBean.getScanRate2()!=null&&!"".equals(merchantInfoBean.getScanRate2())){
				Double scanRate2 = merchantInfoBean.getScanRate2()*100;
				Double dou = (double)Math.round(scanRate2*100)/100 ;
				merchantInfoBean.setScanRate2(dou);
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
	private DataGridBean formatToDataGridbaodan(List<MerchantInfoModel> merchantInfoList, long total) {
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

			//机构名称
			List<Map<String, String>> List = merchantInfoDao.queryObjectsBySqlList(
					"select i.*  from bill_agentunitdetail u, bill_agentunitdetail i where u.upperunit = i.unno and u.unno = (nvl((select s.unno from sys_unit s  "+
							" where s.un_lvl = 2 start with s.unno = '"+merchantInfoModel.getUnno()+"' connect by NOCYCLE s.unno = prior upper_unit), '"+merchantInfoModel.getUnno()+"'))", null, 1, 1);
			//			UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, merchantInfoModel.getUnno());
			if(List != null&&List.size()>0){
				//parentUnitName归属；unitName
				merchantInfoBean.setUnitName(List.get(0).get("AGENTNAME"));
				//				merchantInfoBean.setParentUnitName(List.get(0).get("UPPERUNIT"));
				List = null;
			}else{
				merchantInfoBean.setUnitName("自营");
			}

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

			//上级商户名称
			if(merchantInfoBean.getParentMID() != null && !"".equals(merchantInfoBean.getParentMID().trim())){
				if(parentMIDName(merchantInfoModel.getParentMID()) != null && !"".equals(parentMIDName(merchantInfoModel.getParentMID()).trim())){
					merchantInfoBean.setParentMIDName(parentMIDName(merchantInfoModel.getParentMID()));
				}
			}

			//商户所在地区码名称
			if(merchantInfoBean.getAreaCode() != null && !"".equals(merchantInfoBean.getAreaCode().trim())){
				String code = areaCodeName(merchantInfoModel.getAreaCode());
				if( code!= null  && !"".equals(code.trim())){
					merchantInfoBean.setAreaCodeName(code);
				}
			}

			//业务人员名称
			if(merchantInfoBean.getBusid() != null&&merchantInfoBean.getBusid() != 1){
				String name = busidName(merchantInfoModel.getBusid());
				if( name!= null && !"".equals(name.trim())){
					merchantInfoBean.setBusidName(name);
				}
			}

			//维护人员名称
			if(merchantInfoBean.getMaintainUserId() != null&&merchantInfoBean.getMaintainUserId() != 1){
				String name1 = busidName(merchantInfoModel.getMaintainUserId());
				if(name1!= null && !"".equals(name1.trim())){
					merchantInfoBean.setMaintainUserIdName(name1);
				}
			}

			//行业选择名称
			if(merchantInfoBean.getIndustryType() != null && !"".equals(merchantInfoBean.getIndustryType().trim())){
				String indus = industryTypeName(merchantInfoBean.getIndustryType());
				if(indus!= null && !"".equals(indus.trim())){
					merchantInfoBean.setIndustryTypeName(indus);
				}
			}

			//行业编码名称
			if(merchantInfoBean.getMccid() != null && !"".equals(merchantInfoBean.getMccid().trim())){
				String mcc = mccidName(merchantInfoBean.getMccid());
				if(mcc!= null && !"".equals(mcc.trim())){
					merchantInfoBean.setMccidName(mcc);
				}
			}

			//受理人员名称
			if(merchantInfoBean.getApproveUid() != null && !"".equals(merchantInfoBean.getApproveUid())){
				UserModel user = userDao.getObjectByID(UserModel.class, merchantInfoBean.getApproveUid());
				if(user != null){
					merchantInfoBean.setApproveUidName(user.getUserName());
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
				Double cfr= creditBankRate.multiply(number).doubleValue();
				merchantInfoBean.setCreditBankRate(cfr.toString());
			}

			//扫码支付费率
			if(merchantInfoBean.getScanRate()!=null&&!"".equals(merchantInfoBean.getScanRate())){
				Double scanRate2 = merchantInfoBean.getScanRate()*100;
				Double dou = (double)Math.round(scanRate2*100)/100 ;
				merchantInfoBean.setScanRate(dou);
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
	 * 终端数量
	 */
	private Long tidCount(String mid){
		String hql = "select count(distinct tid) from MerchantTerminalInfoModel m where m.approveStatus = 'Y' and m.maintainType != 'D' and m.mid = :mid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mid", mid);
		Long tidCount = merchantTerminalInfoDao.queryCounts(hql,map);
		return tidCount;
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

	/**
	 * 查询上传路径
	 */
	private String queryUpLoadPath() {
		String title="HrtFrameWork";
		return merchantInfoDao.querySavePath(title);
	}

	/**
	 * 查询传统上传路径
	 */
	private String queryUpLoadPath2() {
		String title="traditionImage";//表sys_param中的title
		return merchantInfoDao.querySavePath(title);
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
			if("110000".equals(user.getUnNo())){
				sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus and ISM35!=1 ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus  and ISM35!=1 ";
				map.put("maintainType", "D");
				map.put("approveStatus", "Y");
			}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				UnitModel parent = unitModel.getParent();
				if("110000".equals(parent.getUnNo())){
					sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus  and ISM35!=1 ";
					sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus  and ISM35!=1 ";
					map.put("maintainType", "D");
					map.put("approveStatus", "Y");
				}
			}else if(isAgentformMan(user)){//判断是否是代理商报单员
				sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID  and ISM35!=1 ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID and ISM35!=1 ";
				map.put("unno", user.getUnNo());
				map.put("maintainType", "D");
				map.put("approveStatus", "Y");
				map.put("MaintainUID", user.getUserID());
			}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus and ISM35!=1  ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus  and ISM35!=1 ";
				map.put("unno", user.getUnNo());
				map.put("maintainType", "D");
				map.put("approveStatus", "Y");
			}else{
				sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus and ISM35!=1  ";
				sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus and ISM35!=1  ";
				map.put("unno", user.getUnNo());
				map.put("maintainType", "D");
				map.put("approveStatus", "Y");
			}
		}else{
			sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus and ISM35!=1  ";
			sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus and ISM35!=1  ";
			map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			map.put("maintainType", "D");
			map.put("approveStatus", "Y");
		}

		if(merchantInfo.getMid()!=null && !"".equals(merchantInfo.getMid())){
			sql += " and MID=:MID";
			sqlCount += " and MID=:MID";
			map.put("MID", merchantInfo.getMid());
		}
		if(merchantInfo.getIsM35()!=null && merchantInfo.getIsM35()==6){
			sql += " and ISM35=:ISM35";
			sqlCount += " and ISM35=:ISM35";
			map.put("ISM35", merchantInfo.getIsM35());
		}else if(merchantInfo.getIsM35()!=null && merchantInfo.getIsM35()==0){
			sql += " and ISM35!=:ISM35";
			sqlCount += " and ISM35!=:ISM35";
			map.put("ISM35", 6);
		}
		if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			sql += " and RNAME LIKE '%"+merchantInfo.getRname()+"%'";
			sqlCount += " and RNAME LIKE '%"+merchantInfo.getRname()+"%'";
		}
		if(merchantInfo.getMaintainDate()!=null && !"".equals(merchantInfo.getMaintainDate())){
			//			sql+=" and trunc(maintainDate)=trunc(sysdate) ";
			//			sqlCount+=" and trunc(maintainDate)=trunc(sysdate) ";
			sql+=" and maintainDate>=trunc(sysdate) ";
			sqlCount+=" and maintainDate>=trunc(sysdate) ";
		}
		if(merchantInfo.getApproveStatus()!=null &&!"".equals(merchantInfo.getApproveStatus())){
			sql+=" and approveStatus=:approveStatus2";
			sqlCount+=" and approveStatus=:approveStatus2";
			map.put("approveStatus2", merchantInfo.getApproveStatus());
		}
		sql += " ORDER BY BMID DESC";
		BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());
		return dataGridBean;
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

	@Override
	public Integer saveUnnoInfobaodan(String unno,String unnoname, UserBean user) throws Exception{
		String sql = "select * from bill_agentmappinfo s where (s.creater = '"+user.getUnNo()+"' and s.Code = '"+unno+"') or (s.Code = '"+unno+"' and  s.creater is null )";
		List list = merchantInfoDao.queryObjectsBySqlList(sql);
		if(list.size() > 0){
			if(unnoname!=null&&!"".equals(unnoname)){
				merchantInfoDao.executeUpdate("update bill_agentmappinfo s set s.CODENAME='"+unnoname+"' where (s.creater = '"+user.getUnNo()+"' and s.Code = '"+unno+"') or (s.Code = '"+unno+"' and  s.creater is null )");
			}
			return 0;
		}else{
			String uuid = "";
			String sql1 = "select * from bill_agentmappinfo s where s.Code = '"+user.getUnNo()+"'";
			List<Map<String,String>> list1 = merchantInfoDao.queryObjectsBySqlList(sql1);
			if(list1.size() > 0){
				uuid = list1.get(0).get("UUID");
			}
			if(unnoname==null||"".equals(unnoname)) return 2;
			try{
				merchantInfoDao.executeUpdate("insert into sys_unit (UNNO, UNITCODE, UN_NAME, UPPER_UNIT, UN_LVL, UN_ATTR, SEQNO, BIZ_CODE, STATUS, PROVINCE_CODE, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER, SEQNO2, AGENT_ATTR, UPPER_UNIT2, UPPER_UNIT3, AGENT_MID)"+
						"values ('"+unno+"', '122', '"+unnoname+"', '"+user.getUnNo()+"', 2, 1, null, null, 1, '41', sysdate, 'superadmin', sysdate, 'superadmin', null, null, null, null, null)");
			}catch (Exception e) {
				return 3;
			}
			merchantInfoDao.executeUpdate("insert into bill_agentmappinfo (UUID,CODENAME,creater, CODE, DETAILMINFO1, DETAILMINFO2) values ('"+
					uuid+"','"+unnoname+"','"+user.getUnNo()+"', '"+unno+"', 0, null)");
			return 1;
		}
	}

	/**
	 * 方法功能：商户
	 * 参数：merchantInfo
	 * 	   loginName 当前登录用户名
	 * 返回值：void
	 * 异常：
	 */
	@Override
	public MerchantInfoModel saveMerchantInfo(MerchantInfoBean merchantInfo, UserBean user)
			throws Exception {
		MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
		BeanUtils.copyProperties(merchantInfo, merchantInfoModel);

		String ip=null;
		HttpServletRequest request=ServletActionContext.getRequest();
		if (request.getHeader("x-forwarded-for") == null) {
			ip=request.getRemoteAddr();
		}
		else{
			ip=request.getHeader("x-forwarded-for");
		}
		merchantInfoModel.setIp(ip);
		//传统商户 isM35:0
		//merchantInfoModel.setIsM35(0);
		if(merchantInfoModel.getIsM35()==null){
			merchantInfoModel.setIsM35(0);
		}else if(merchantInfoModel.getIsM35()==2||merchantInfoModel.getIsM35()==3){
			merchantInfoModel.setAreaType("1");
		}else if(merchantInfoModel.getIsM35()==4){
			merchantInfoModel.setAreaType("2");
		}else if(merchantInfoModel.getIsM35()==5){
			merchantInfoModel.setAreaType("3");
		}else{
			merchantInfoModel.setIsM35(0);
		}

		//结算方式（是否秒到，传统商户默认：000000）
		merchantInfoModel.setSettMethod("000000");
		merchantInfoModel.setSettleMethod("T");
		//注册地址拼接
		merchantInfoModel.setBaddr((merchantInfo.getProvince().trim()+merchantInfo.getCity().trim()+merchantInfo.getBaddr()).trim().toString());
		//营业地址拼接
		merchantInfoModel.setRaddr((merchantInfo.getProvince().trim()+merchantInfo.getCity().trim()+merchantInfo.getRaddr()).trim().toString());

		//实时交易查询次数
		merchantInfoModel.setRealtimeQueryTimes(20);

		//合同起始时间
		merchantInfoModel.setContractPeriod(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());

		//银联卡费率
		Double bankFeeRate = Double.parseDouble(merchantInfo.getBankFeeRate())/100;
		merchantInfoModel.setBankFeeRate(bankFeeRate.toString());
		//贷记卡费率
		if(merchantInfo.getCreditBankRate()!=null&&!"".equals(merchantInfo.getCreditBankRate())){
			Double creditBankRate = Double.parseDouble(merchantInfo.getCreditBankRate())/100;
			merchantInfoModel.setCreditBankRate(creditBankRate.toString());
		}else{
			merchantInfoModel.setCreditBankRate("0");
		}
		//境外银联卡费率
		if(merchantInfo.getOutsideBankRate()!=null&&!"".equals(merchantInfo.getOutsideBankRate())){
			Double outsideBankRate = Double.parseDouble(merchantInfo.getOutsideBankRate())/100;
			merchantInfoModel.setOutsideBankRate(outsideBankRate.toString());
		}
		//微信费率
		if(merchantInfo.getScanRate()!=null&&!"".equals(merchantInfo.getScanRate())){
			Double ScanRate = merchantInfo.getScanRate()/100;
			merchantInfoModel.setScanRate(ScanRate);
		}else{
			merchantInfoModel.setScanRate(0.0);
		}
		//银联二维码费率
		if(merchantInfo.getScanRate1()!=null&&!"".equals(merchantInfo.getScanRate1())){
			Double ScanRate1 = merchantInfo.getScanRate1()/100;
			merchantInfoModel.setScanRate1(ScanRate1);
		}
		//支付宝费率
		if(merchantInfo.getScanRate2()!=null&&!"".equals(merchantInfo.getScanRate2())){
			Double ScanRate2 = merchantInfo.getScanRate2()/100;
			merchantInfoModel.setScanRate2(ScanRate2);
		}
		//结算周期
		if(merchantInfo.getSettleCycle()==null||"".equals(merchantInfo.getSettleCycle())){
			merchantInfoModel.setSettleCycle(1);
		}
		//节假日是否合并结账 0：否 1：是
		merchantInfoModel.setSettleMerger("1");

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

		//商户账单名称为空则和商户注册名称一样
		if(merchantInfoModel.getShortName() == null || "".equals(merchantInfoModel.getShortName().trim())){
			merchantInfoModel.setShortName(merchantInfo.getRname());
		}

		//凭条打印名称为空泽合商户注册名称一样
		if(merchantInfoModel.getPrintName() == null || "".equals(merchantInfoModel.getPrintName().trim())){
			merchantInfoModel.setPrintName(merchantInfo.getRname());
		}

		StringBuffer bank = new StringBuffer();
		if(merchantInfo.getBankSendCode()!=null&&!"".equals(merchantInfo.getBankSendCode())&&!"其他".equals(merchantInfo.getBankSendCode().trim())){
			bank.append(merchantInfo.getBankSendCode());
		}
		if(merchantInfo.getBankProvinceCode()!=null&&!"".equals(merchantInfo.getBankProvinceCode())&&!"00".equals(merchantInfo.getBankProvinceCode())){
			bank.append(merchantInfo.getBankProvinceCode());
		}
		bank.append(merchantInfo.getBankName());
		merchantInfoModel.setBankBranch(bank.toString());

		//判断开户银行中是否有交通银行几个字
		if(bank.toString().indexOf("交通银行") == -1){
			merchantInfoModel.setBankType("2");		//非交通银行
		}else{
			merchantInfoModel.setBankType("1");		//交通银行
		}

		AgentSalesModel agentSalesModel = agentSalesDao.getObjectByID(AgentSalesModel.class, merchantInfo.getBusid());
		merchantInfoModel.setUnno(agentSalesModel.getUnno());
		merchantInfoModel.setApproveStatus("Z");		//默认为待放行 Y-放行   Z-待放行  K-踢回
		if(user.getUnNo().equals("901000")){
			merchantInfoModel.setMaintainUserId(merchantInfo.getMaintainUserId());		//添加的时候业务人员和维护人员一样
		}else{
			merchantInfoModel.setMaintainUserId(merchantInfo.getBusid());		//添加的时候业务人员和维护人员一样
		}
		merchantInfoModel.setMaintainUid(user.getUserID());		//操作人员
		merchantInfoModel.setMaintainDate(new Date());			//操作日期
		merchantInfoModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
		merchantInfoModel.setSettleStatus("1");		//结算状态 1-正常 2-冻结
		merchantInfoModel.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销

		//商户编号生成
		StringBuffer mid = new StringBuffer("864");

		if(merchantInfo.getAreaCode().trim().length()<=3){
			mid.append("0"+merchantInfo.getAreaCode().trim());
		}else{
			mid.append(merchantInfo.getAreaCode().trim());
		}
		mid.append(merchantInfo.getMccid());
		Integer seqNo = searchMIDSeqInfo(merchantInfo.getAreaCode(), merchantInfo.getMccid().toString(),user.getLoginName());
		DecimalFormat deFormat = new DecimalFormat("0000");
		mid.append(deFormat.format(seqNo));
		merchantInfoModel.setMid(mid.toString());

		//上传文件
		if(merchantInfo.getLegalUploadFile() != null && merchantInfo.getLegalUploadFileName() != null){
			StringBuffer fName1 = new StringBuffer();
			//			fName1.append(user.getUnNo());
			//			fName1.append(".");
			fName1.append(merchantInfoModel.getMid());
			fName1.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName1.append(imageDay);
			fName1.append(".1");
			fName1.append(merchantInfo.getLegalUploadFileName().substring(merchantInfo.getLegalUploadFileName().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getLegalUploadFile(),fName1.toString(),imageDay);
			merchantInfoModel.setLegalUploadFileName(fName1.toString());
		}

		if(merchantInfo.getBupLoadFile() != null && merchantInfo.getBupLoad() != null){
			StringBuffer fName2 = new StringBuffer();
			//			fName2.append(user.getUnNo());
			//			fName2.append(".");
			fName2.append(merchantInfoModel.getMid());
			fName2.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName2.append(imageDay);
			fName2.append(".2");
			fName2.append(merchantInfo.getBupLoad().substring(merchantInfo.getBupLoad().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getBupLoadFile(),fName2.toString(),imageDay);
			merchantInfoModel.setBupLoad(fName2.toString());
		}

		if(merchantInfo.getRupLoadFile() != null && merchantInfo.getRupLoad() != null){
			StringBuffer fName3 = new StringBuffer();
			//			fName3.append(user.getUnNo());
			//			fName3.append(".");
			fName3.append(merchantInfoModel.getMid());
			fName3.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName3.append(imageDay);
			fName3.append(".3");
			fName3.append(merchantInfo.getRupLoad().substring(merchantInfo.getRupLoad().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getRupLoadFile(),fName3.toString(),imageDay);
			merchantInfoModel.setRupLoad(fName3.toString());
		}

		if(merchantInfo.getRegistryUpLoadFile() != null && merchantInfo.getRegistryUpLoad() != null){
			StringBuffer fName4 = new StringBuffer();
			//			fName4.append(user.getUnNo());
			//			fName4.append(".");
			fName4.append(merchantInfoModel.getMid());
			fName4.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName4.append(imageDay);
			fName4.append(".4");
			fName4.append(merchantInfo.getRegistryUpLoad().substring(merchantInfo.getRegistryUpLoad().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getRegistryUpLoadFile(),fName4.toString(),imageDay);
			merchantInfoModel.setRegistryUpLoad(fName4.toString());
		}

		if(merchantInfo.getMaterialUpLoadFile() != null && merchantInfo.getMaterialUpLoad() != null){
			StringBuffer fName5 = new StringBuffer();
			//			fName5.append(user.getUnNo());
			//			fName5.append(".");
			fName5.append(merchantInfoModel.getMid());
			fName5.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName5.append(imageDay);
			fName5.append(".5");
			fName5.append(merchantInfo.getMaterialUpLoad().substring(merchantInfo.getMaterialUpLoad().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getMaterialUpLoadFile(),fName5.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad(fName5.toString());
		}

		if(merchantInfo.getPhotoUpLoadFile() != null && merchantInfo.getPhotoUpLoad() != null){
			StringBuffer fName6 = new StringBuffer();
			//			fName6.append(user.getUnNo());
			//			fName6.append(".");
			fName6.append(merchantInfoModel.getMid());
			fName6.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName6.append(imageDay);
			fName6.append(".6");
			fName6.append(merchantInfo.getPhotoUpLoad().substring(merchantInfo.getPhotoUpLoad().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getPhotoUpLoadFile(),fName6.toString(),imageDay);
			merchantInfoModel.setPhotoUpLoad(fName6.toString());
		}

		if(merchantInfo.getBigdealUpLoadFile() != null && merchantInfo.getBigdealUpLoad() != null){
			StringBuffer fName7 = new StringBuffer();
			//			fName7.append(user.getUnNo());
			//			fName7.append(".");
			fName7.append(merchantInfoModel.getMid());
			fName7.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName7.append(imageDay);
			fName7.append(".7");
			fName7.append(merchantInfo.getBigdealUpLoad().substring(merchantInfo.getBigdealUpLoad().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getBigdealUpLoadFile(),fName7.toString(),imageDay);
			merchantInfoModel.setBigdealUpLoad(fName7.toString());
		}

		if(merchantInfo.getMaterialUpLoad1File() != null && merchantInfo.getMaterialUpLoad1() != null){
			StringBuffer fName8 = new StringBuffer();
			//			fName8.append(user.getUnNo());
			//			fName8.append(".");
			fName8.append(merchantInfoModel.getMid());
			fName8.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName8.append(imageDay);
			fName8.append(".8");
			fName8.append(merchantInfo.getMaterialUpLoad1().substring(merchantInfo.getMaterialUpLoad1().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getMaterialUpLoad1File(),fName8.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad1(fName8.toString());
		}

		if(merchantInfo.getMaterialUpLoad2File() != null && merchantInfo.getMaterialUpLoad2() != null){
			StringBuffer fName9 = new StringBuffer();
			//			fName9.append(user.getUnNo());
			//			fName9.append(".");
			fName9.append(merchantInfoModel.getMid());
			fName9.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fName9.append(imageDay);
			fName9.append(".9");
			fName9.append(merchantInfo.getMaterialUpLoad2().substring(merchantInfo.getMaterialUpLoad2().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getMaterialUpLoad2File(),fName9.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad2(fName9.toString());
		}

		if(merchantInfo.getMaterialUpLoad3File() != null && merchantInfo.getMaterialUpLoad3() != null){
			StringBuffer fNameA = new StringBuffer();
			//			fNameA.append(user.getUnNo());
			//			fNameA.append(".");
			fNameA.append(merchantInfoModel.getMid());
			fNameA.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fNameA.append(imageDay);
			fNameA.append(".A");
			fNameA.append(merchantInfo.getMaterialUpLoad3().substring(merchantInfo.getMaterialUpLoad3().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getMaterialUpLoad3File(),fNameA.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad3(fNameA.toString());
		}

		if(merchantInfo.getMaterialUpLoad4File() != null && merchantInfo.getMaterialUpLoad4() != null){
			StringBuffer fNameB = new StringBuffer();
			//			fNameB.append(user.getUnNo());
			//			fNameB.append(".");
			fNameB.append(merchantInfoModel.getMid());
			fNameB.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fNameB.append(imageDay);
			fNameB.append(".B");
			fNameB.append(merchantInfo.getMaterialUpLoad4().substring(merchantInfo.getMaterialUpLoad4().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getMaterialUpLoad4File(),fNameB.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad4(fNameB.toString());
		}

		if(merchantInfo.getMaterialUpLoad5File() != null && merchantInfo.getMaterialUpLoad5() != null){
			StringBuffer fNameC = new StringBuffer();
			//			fNameC.append(user.getUnNo());
			//			fNameC.append(".");
			fNameC.append(merchantInfoModel.getMid());
			fNameC.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fNameC.append(imageDay);
			fNameC.append(".C");
			fNameC.append(merchantInfo.getMaterialUpLoad5().substring(merchantInfo.getMaterialUpLoad5().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getMaterialUpLoad5File(),fNameC.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad5(fNameC.toString());
		}
		if(merchantInfo.getMaterialUpLoad6File() != null && merchantInfo.getMaterialUpLoad6() != null){
			StringBuffer fNameD = new StringBuffer();
			//			fNameD.append(user.getUnNo());
			//			fNameD.append(".");
			fNameD.append(merchantInfoModel.getMid());
			fNameD.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fNameD.append(imageDay);
			fNameD.append(".D");
			fNameD.append(merchantInfo.getMaterialUpLoad6().substring(merchantInfo.getMaterialUpLoad6().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getMaterialUpLoad6File(),fNameD.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad6(fNameD.toString());
		}
		if(merchantInfo.getMaterialUpLoad7File() != null && merchantInfo.getMaterialUpLoad7() != null){
			StringBuffer fNameE = new StringBuffer();
			//			fNameE.append(user.getUnNo());
			//			fNameE.append(".");
			fNameE.append(merchantInfoModel.getMid());
			fNameE.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fNameE.append(imageDay);
			fNameE.append(".E");
			fNameE.append(merchantInfo.getMaterialUpLoad7().substring(merchantInfo.getMaterialUpLoad7().lastIndexOf(".")).toLowerCase());
			//			uploadFile(merchantInfo.getMaterialUpLoad7File(),fNameE.toString(),imageDay);
			merchantInfoModel.setMaterialUpLoad7(fNameE.toString());
		}
		if(merchantInfo.getPosBackImgFile() != null && merchantInfo.getPosBackImgFile() != null){
			StringBuffer fNameF = new StringBuffer();
			//			fNameF.append(user.getUnNo());
			//			fNameF.append(".");
			fNameF.append(merchantInfoModel.getMid());
			fNameF.append(".");
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			fNameF.append(imageDay);
			fNameF.append(".F");
			fNameF.append(merchantInfo.getPosBackImg().substring(merchantInfo.getPosBackImg().lastIndexOf(".")).toLowerCase());
			merchantInfoModel.setPosBackImg(fNameF.toString());
		}
		//因为数据库中为decimal类型，所以如果为""则插入不进去，这里如果为""则改为null
		if(merchantInfo.getIsForeign()==1){
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
		//插入HYB手机号
		merchantInfoModel.setHybPhone(merchantInfo.getHybPhone());
		merchantInfoDao.saveObject(merchantInfoModel);

		MerchantBankCardModel mbc = new MerchantBankCardModel();
		mbc.setBankAccName(merchantInfoModel.getBankAccName());
		mbc.setBankAccNo(merchantInfoModel.getBankAccNo());
		mbc.setCreateDate(new Date());
		mbc.setMerCardType(0);
		mbc.setStatus(0);
		mbc.setMid(merchantInfoModel.getMid());
		mbc.setBankBranch(merchantInfoModel.getBankBranch());
		mbc.setPayBankId(merchantInfoModel.getPayBankId());
		merchantTaskDetailDao.saveObject(mbc);
		return merchantInfoModel;
	}

	public MerchantInfoModel saveMerchantInfobaodan(MerchantInfoBean merchantInfo, UserBean user)
			throws Exception {
		MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
		BeanUtils.copyProperties(merchantInfo, merchantInfoModel);

		String ip=null;
		HttpServletRequest request=ServletActionContext.getRequest();
		if (request.getHeader("x-forwarded-for") == null) {
			ip=request.getRemoteAddr();
		}
		else{
			ip=request.getHeader("x-forwarded-for");
		}
		merchantInfoModel.setIp(ip);
		//传统商户 isM35:0
		//merchantInfoModel.setIsM35(0);
		if(merchantInfoModel.getIsM35()==null){
			merchantInfoModel.setIsM35(0);
		}else if(merchantInfoModel.getIsM35()==2||merchantInfoModel.getIsM35()==3){
			merchantInfoModel.setAreaType("1");
		}else if(merchantInfoModel.getIsM35()==4){
			merchantInfoModel.setAreaType("2");
		}else if(merchantInfoModel.getIsM35()==5){
			merchantInfoModel.setAreaType("3");
		}else{
			merchantInfoModel.setIsM35(0);
		}

		//结算方式（是否秒到，传统商户默认：000000）
		merchantInfoModel.setSettMethod("000000");
		merchantInfoModel.setSettleMethod("T");
		//注册地址拼接
		merchantInfoModel.setBaddr((merchantInfo.getProvince().trim()+merchantInfo.getCity().trim()+merchantInfo.getBaddr()).trim().toString());
		//营业地址拼接
		merchantInfoModel.setRaddr((merchantInfo.getProvince().trim()+merchantInfo.getCity().trim()+merchantInfo.getRaddr()).trim().toString());

		//实时交易查询次数
		merchantInfoModel.setRealtimeQueryTimes(20);

		//合同起始时间
		merchantInfoModel.setContractPeriod(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());

		//银联卡费率
		Double bankFeeRate = Double.parseDouble(merchantInfo.getBankFeeRate())/100;
		merchantInfoModel.setBankFeeRate(bankFeeRate.toString());
		//贷记卡费率
		if(merchantInfo.getCreditBankRate()!=null&&!"".equals(merchantInfo.getCreditBankRate())){
			Double creditBankRate = Double.parseDouble(merchantInfo.getCreditBankRate())/100;
			merchantInfoModel.setCreditBankRate(creditBankRate.toString());
		}else{
			merchantInfoModel.setCreditBankRate("0");
		}
		//境外银联卡费率
		if(merchantInfo.getOutsideBankRate()!=null&&!"".equals(merchantInfo.getOutsideBankRate())){
			Double outsideBankRate = Double.parseDouble(merchantInfo.getOutsideBankRate())/100;
			merchantInfoModel.setOutsideBankRate(outsideBankRate.toString());
		}
		//扫码支付费率
		if(merchantInfo.getScanRate()!=null&&!"".equals(merchantInfo.getScanRate())){
			Double ScanRate = merchantInfo.getScanRate()/100;
			merchantInfoModel.setScanRate(ScanRate);
		}else{
			merchantInfoModel.setScanRate(0.0);
		}
		//结算周期
		if(merchantInfo.getSettleCycle()==null||"".equals(merchantInfo.getSettleCycle())){
			merchantInfoModel.setSettleCycle(1);
		}
		//节假日是否合并结账 0：否 1：是
		merchantInfoModel.setSettleMerger("1");

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

		//商户账单名称为空则和商户注册名称一样
		if(merchantInfoModel.getShortName() == null || "".equals(merchantInfoModel.getShortName().trim())){
			merchantInfoModel.setShortName(merchantInfo.getRname());
		}

		//凭条打印名称为空泽合商户注册名称一样
		if(merchantInfoModel.getPrintName() == null || "".equals(merchantInfoModel.getPrintName().trim())){
			merchantInfoModel.setPrintName(merchantInfo.getRname());
		}

		StringBuffer bank = new StringBuffer();
		if(merchantInfo.getBankSendCode()!=null&&!"".equals(merchantInfo.getBankSendCode())&&!"其他".equals(merchantInfo.getBankSendCode().trim())){
			bank.append(merchantInfo.getBankSendCode());
		}
		if(merchantInfo.getBankProvinceCode()!=null&&!"".equals(merchantInfo.getBankProvinceCode())&&!"00".equals(merchantInfo.getBankProvinceCode())){
			bank.append(merchantInfo.getBankProvinceCode());
		}
		bank.append(merchantInfo.getBankName());
		merchantInfoModel.setBankBranch(bank.toString());

		//判断开户银行中是否有交通银行几个字
		if(bank.toString().indexOf("交通银行") == -1){
			merchantInfoModel.setBankType("2");		//非交通银行
		}else{
			merchantInfoModel.setBankType("1");		//交通银行
		}

		AgentSalesModel agentSalesModel = agentSalesDao.getObjectByID(AgentSalesModel.class, merchantInfo.getBusid());
		merchantInfoModel.setUnno(user.getUnNo());
		merchantInfoModel.setApproveStatus("Z");		//默认为待放行 Y-放行   Z-待放行  K-踢回
		if(user.getUnNo().equals("901000")){
			merchantInfoModel.setMaintainUserId(merchantInfo.getMaintainUserId());		//添加的时候业务人员和维护人员一样
		}else{
			merchantInfoModel.setMaintainUserId(merchantInfo.getBusid());		//添加的时候业务人员和维护人员一样
		}
		merchantInfoModel.setMaintainUid(user.getUserID());		//操作人员
		merchantInfoModel.setMaintainDate(new Date());			//操作日期
		merchantInfoModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
		merchantInfoModel.setSettleStatus("1");		//结算状态 1-正常 2-冻结
		merchantInfoModel.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销

		//商户编号生成
		StringBuffer mid = new StringBuffer("864");

		if(merchantInfo.getAreaCode().trim().length()<=3){
			mid.append("0"+merchantInfo.getAreaCode().trim());
		}else{
			mid.append(merchantInfo.getAreaCode().trim());
		}
		mid.append(merchantInfo.getMccid());
		Integer seqNo = searchMIDSeqInfo(merchantInfo.getAreaCode(), merchantInfo.getMccid().toString(),user.getLoginName());
		DecimalFormat deFormat = new DecimalFormat("0000");
		mid.append(deFormat.format(seqNo));
		merchantInfoModel.setMid(mid.toString());

		//上传文件
		if(merchantInfo.getLegalUploadFile() != null && merchantInfo.getLegalUploadFileName() != null){
			StringBuffer fName1 = new StringBuffer();
			//			fName1.append(user.getUnNo());
			//			fName1.append(".");
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
			//			fName2.append(user.getUnNo());
			//			fName2.append(".");
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
			//			fName3.append(user.getUnNo());
			//			fName3.append(".");
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
			//			fName4.append(user.getUnNo());
			//			fName4.append(".");
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
			//			fName5.append(user.getUnNo());
			//			fName5.append(".");
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
			//			fName6.append(user.getUnNo());
			//			fName6.append(".");
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
			//			fName7.append(user.getUnNo());
			//			fName7.append(".");
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
			//			fName8.append(user.getUnNo());
			//			fName8.append(".");
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
			//			fName9.append(user.getUnNo());
			//			fName9.append(".");
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
			//			fNameA.append(user.getUnNo());
			//			fNameA.append(".");
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
			//			fNameB.append(user.getUnNo());
			//			fNameB.append(".");
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
			//			fNameC.append(user.getUnNo());
			//			fNameC.append(".");
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
			//			fNameD.append(user.getUnNo());
			//			fNameD.append(".");
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
			//			fNameE.append(user.getUnNo());
			//			fNameE.append(".");
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
		if(merchantInfo.getIsForeign()==1){
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
		//插入HYB手机号
		merchantInfoModel.setHybPhone(merchantInfo.getHybPhone());
		merchantInfoDao.saveObject(merchantInfoModel);

		MerchantBankCardModel mbc = new MerchantBankCardModel();
		mbc.setBankAccName(merchantInfoModel.getBankAccName());
		mbc.setBankAccNo(merchantInfoModel.getBankAccNo());
		mbc.setCreateDate(new Date());
		mbc.setMerCardType(0);
		mbc.setStatus(0);
		mbc.setMid(merchantInfoModel.getMid());
		mbc.setBankBranch(merchantInfoModel.getBankBranch());
		mbc.setPayBankId(merchantInfoModel.getPayBankId());
		merchantTaskDetailDao.saveObject(mbc);
		return merchantInfoModel;
	}

	/**
	 * 得到业务员名称
	 */
	private String getSaleName(Integer id) {
		String sql="SELECT SALENAME FROM BILL_AGENTSALESINFO WHERE BUSID="+id;
		List list=merchantInfoDao.queryObjectsBySqlList(sql);
		return ((HashMap)list.get(0)).get("SALENAME").toString();
	}

	/**
	 * 查询待放行的商户(入网时间为空)
	 */
	@Override
	public DataGridBean queryMerchantInfoWJoin(MerchantInfoBean merchantInfo,UserBean user)
			throws Exception {
		String hql = "from MerchantInfoModel m where m.maintainType != :maintainType and m.approveStatus in ('W','C')  and m.isM35!=1 ";
		String hqlCount = "select count(*) from MerchantInfoModel m where m.maintainType != :maintainType and m.approveStatus in ('W','C') and m.isM35!=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maintainType", "D");

		if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			hql+=" and m.rname like :RNAME ";
			hqlCount +=" and m.rname like :RNAME ";
			map.put("RNAME", merchantInfo.getRname()+'%');
		}
		if(merchantInfo.getMid() != null && !"".equals(merchantInfo.getMid())){
			hql+=" and m.mid like :MID ";
			hqlCount +=" and m.mid like :MID ";
			map.put("MID", merchantInfo.getMid()+"%");
		}
		if(merchantInfo.getUnitName() !=null && !"".equals(merchantInfo.getUnitName())){
			String unnoSql = "select unno from sys_unit where un_name like '%"+merchantInfo.getUnitName()+"%'";
			List<Map<String, String>> unnolist = merchantInfoDao.executeSql(unnoSql);
			String unnovalue = "";
			for (Map<String, String> unlist : unnolist) {
				unnovalue += "'"+unlist.get("UNNO")+"',";
			}
			if(unnovalue != null &&!"".equals(unnovalue)){
				unnovalue=unnovalue.substring(0, unnovalue.length()-1);
				hql +=" and m.unno in ("+unnovalue+") ";
				hqlCount +=" and m.unno in ("+unnovalue+") ";
			}else{
				hql +=" and m.unno='' ";
				hqlCount +=" and m.unno='' ";
			}
		}
		if(merchantInfo.getUnno()!=null&&!"".equals(merchantInfo.getUnno())){
			hql +=" and m.unno in ("+merchantInfo.getUnno()+") ";
			hqlCount +=" and m.unno in ("+merchantInfo.getUnno()+") ";
		}
		if(merchantInfo.getIsM35()!=null && merchantInfo.getIsM35()==6){
			hql += " and m.isM35=6";
			hqlCount += " and m.isM35=6";
		}else if(merchantInfo.getIsM35()!=null && merchantInfo.getIsM35()==0){
			hql += " and m.isM35 !=6";
			hqlCount += " and m.isM35 !=6";
		}
		if(merchantInfo.getApproveStatus()!=null && !"".equals(merchantInfo.getApproveStatus())){
			hql += " and m.approveStatus ='"+merchantInfo.getApproveStatus()+"' ";
			hqlCount += " and m.approveStatus ='"+merchantInfo.getApproveStatus()+"' ";
		}
		hql += " order by m.maintainDate DESC";
		Long count = merchantInfoDao.queryCounts(hqlCount, map);
		List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfo(hql, map, merchantInfo.getPage(), merchantInfo.getRows());

		DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, count);

		return dataGridBean;
	}

	/**
	 * 查询待放行的商户(入网时间为空)
	 */
	@Override
	public DataGridBean queryMerchantInfoWJoin1(MerchantInfoBean merchantInfo,UserBean user)
			throws Exception {
		String hql = "from MerchantInfoModel m where m.maintainType != :maintainType and m.approveStatus in ('W','C')  and m.isM35!=1 ";
		String hqlCount = "select count(*) from MerchantInfoModel m where m.maintainType != :maintainType and m.approveStatus in ('W','C') and m.isM35!=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maintainType", "D");

		if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			hql+=" and m.rname like :RNAME ";
			hqlCount +=" and m.rname like :RNAME ";
			map.put("RNAME", merchantInfo.getRname()+'%');
		}
		if(merchantInfo.getMid() != null && !"".equals(merchantInfo.getMid())){
			hql+=" and m.mid like :MID ";
			hqlCount +=" and m.mid like :MID ";
			map.put("MID", merchantInfo.getMid()+"%");
		}
		if(merchantInfo.getUnitName() !=null && !"".equals(merchantInfo.getUnitName())){
			String unnoSql = "select unno from sys_unit where un_name like '%"+merchantInfo.getUnitName()+"%'";
			List<Map<String, String>> unnolist = merchantInfoDao.executeSql(unnoSql);
			String unnovalue = "";
			for (Map<String, String> unlist : unnolist) {
				unnovalue += "'"+unlist.get("UNNO")+"',";
			}
			if(unnovalue != null &&!"".equals(unnovalue)){
				unnovalue=unnovalue.substring(0, unnovalue.length()-1);
				hql +=" and m.unno in ("+unnovalue+") ";
				hqlCount +=" and m.unno in ("+unnovalue+") ";
			}else{
				hql +=" and m.unno='' ";
				hqlCount +=" and m.unno='' ";
			}
		}
		if(merchantInfo.getUnno()!=null&&!"".equals(merchantInfo.getUnno())){
			hql +=" and m.unno in ("+merchantInfo.getUnno()+") ";
			hqlCount +=" and m.unno in ("+merchantInfo.getUnno()+") ";
		}
		if(merchantInfo.getIsM35()!=null && merchantInfo.getIsM35()==6){
			hql += " and m.isM35=6";
			hqlCount += " and m.isM35=6";
		}else if(merchantInfo.getIsM35()!=null && merchantInfo.getIsM35()==0){
			hql += " and m.isM35 !=6";
			hqlCount += " and m.isM35 !=6";
		}
		if(merchantInfo.getApproveStatus()!=null && !"".equals(merchantInfo.getApproveStatus())){
			hql += " and m.approveStatus ='"+merchantInfo.getApproveStatus()+"' ";
			hqlCount += " and m.approveStatus ='"+merchantInfo.getApproveStatus()+"' ";
		}
		hql += " order by m.maintainDate DESC";
		Long count = merchantInfoDao.queryCounts(hqlCount, map);
		List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfo(hql, map, merchantInfo.getPage(), merchantInfo.getRows());

		DataGridBean dataGridBean = formatToDataGridbaodan(merchantInfoList, count);

		return dataGridBean;
	}

	/**
	 * 商户开通 修改状态
	 *
	 */@Override
	 public MerchantInfoModel updateMerchantInfoC(MerchantInfoBean merchantInfo, UserBean user, String ids) throws Exception{

		 MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class,Integer.parseInt(ids));
		 if(merchantInfo.getConMccid()!=null&&!"".equals(merchantInfo.getConMccid())){
			 merchantInfoModel.setConMccid(merchantInfo.getConMccid());//业务确认行业编码
		 }else{
			 merchantInfoModel.setConMccid(merchantInfoModel.getMccid());//业务确认行业编码
		 }
		 if(merchantInfo.getIsM35()!=null&&!"".equals(merchantInfo.getIsM35())&&merchantInfoModel.getIsM35()==3){
			 merchantInfoModel.setIsM35(merchantInfo.getIsM35());//业务确认商户性质
		 }
		 if(merchantInfo.getAccType()!=null&&!"".equals(merchantInfo.getAccType())){
			 merchantInfoModel.setAccType(merchantInfo.getAccType());//业务确认账户性质
		 }
		 merchantInfoModel.setProcessContext(merchantInfo.getProcessContext());
		 merchantInfoModel.setSendCode(merchantInfo.getSendCode());
		 merchantInfoModel.setApproveUid(user.getUserID());
		 merchantInfoModel.setApproveDate(new Date());
		 merchantInfoModel.setApproveStatus("C");		//Y-放行   Z-待放行  K-踢回 C-审批中
		 merchantInfoModel.setJoinConfirmDate(new Date());	//入网时间
		 //授权人员
		 merchantInfoModel.setApproveUid(user.getUserID());
		 merchantInfoModel.setApproveDate(new Date());
		 merchantInfoModel.setCheckConfirmDate(new Date());
		 merchantInfoModel.setQrUrl(new QRToImageWriter().doQR(merchantInfoModel).getQrUrl());
		 merchantInfoModel.setQrUpload("0");
		 merchantInfoDao.updateObject(merchantInfoModel);
		 return merchantInfoModel;

	 }

	 /**
	  * 获取一代机构号
	  */
	 public String getProvince(String unno){
		 String sql = "select s.unno from sys_unit s where s.un_lvl=2 start with s.unno = '"+unno+"' connect by NOCYCLE s.unno = prior s.upper_unit  and rownum = 1";
		 List<Map<String,String>> list = unitDao.queryObjectsBySqlListMap(sql, null);
		 if(list!=null&&list.size()>0){
			 return list.get(0).get("UNNO");
		 }else{
			 return unno;
		 }
	 }

	 /**
	  * 商户开通 推送综合
	  */
	 @Override
	 public boolean updateMerchantInfoCToADM(MerchantInfoModel merchantInfoModel, UserBean user, String ids)
			 throws Exception {

		 //调用webservice，对GMMS添加开通商户信息
		 String tenance=merchantInfoModel.getUnno();
		 String sales=getSaleName(merchantInfoModel.getBusid());
		 MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(merchantInfoModel, user.getLoginName(), tenance, sales);
		 Boolean falg =false;
		 try{
			 // @author:lxg-20190508 手刷商户推送时,传活动类型
			 //                String firstSql="select k.rebatetype from bill_terminalinfo k where k.termid in ( " +
			 //                        " select t.tid from bill_merchantterminalinfo t where  t.maintaintype!='D' and t.mid='"+merchantInfoModel.getMid()+"' " +
			 //                        ") and k.rebatetype is not null and rownum=1 order by k.usedconfirmdate,k.btid ";
			 //                List<Map<String,String>> firstRebateType =unitDao.queryObjectsBySqlListMap(firstSql,null);
			 //                if(firstRebateType.size()==1){
			 //                    info.setRebateType(firstRebateType.get(0).get("REBATETYPE")+"");
			 //                }
			 log.info("传统商户审批:mid="+info.getMid()+";rname="+info.getRname()+";bankaccno="+info.getBankAccNo()+";bankaccno="+info.getBankAccNo()+";bankbranch="+info.getBankBranch());
			 info.setUnno(getProvince(merchantInfoModel.getUnno()));
			 falg=gsClient.saveMerchantInfo(info,"hrtREX1234.Java");
		 }catch (Exception e) {
			 log.info("调用webservice失败"+e);
		 }
		 if(!falg){
			 merchantInfoModel.setApproveStatus("W");
			 merchantInfoDao.updateObject(merchantInfoModel);
			 return false;
			 //				throw new IllegalAccessError("调用webservice失败");
		 }

		 String mtimhql = "from MerchantTerminalInfoModel m where mid = :mid and maintainType != :maintainType";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("mid", merchantInfoModel.getMid());
		 map.put("maintainType", "D");
		 List<MerchantTerminalInfoModel> listmtim = merchantTerminalInfoDao.queryObjectsByHqlList(mtimhql, map);
		 for (MerchantTerminalInfoModel mtim : listmtim) {
			 mtim.setApproveUid(user.getUserID());
			 mtim.setApproveDate(new Date());
			 mtim.setApproveStatus("Y");		//Y-已受理   Z-待受理  K-不受理
			 merchantTerminalInfoDao.updateObject(mtim);
			 String terminalHql="from TerminalInfoModel t where t.termID=?";
			 TerminalInfoModel t=terminalInfoDao.queryObjectByHql(terminalHql, new Object[]{mtim.getTid()});
			 //调用webservice，对GMMS添加布放信息
			 TermAcc acc=new TermAcc();
			 acc.setCby(user.getLoginName());
			 MachineInfoModel infoModel=machineInfoDao.getObjectByID(MachineInfoModel.class, mtim.getBmaid());
			 acc.setModleId(infoModel.getMachineModel());
			 acc.setMid(mtim.getMid());
			 acc.setTid(mtim.getTid());
			 //				if(t!=null){
			 //					acc.setSn(t.getSn());
			 //				}else{
			 //					acc.setSn(mtim.getSn());
			 //				}
			 if(mtim.getSn()!=null&&!"".equals(mtim.getSn())){
				 acc.setSn(mtim.getSn());
			 }else if(t!=null){
				 acc.setSn(t.getSn());
			 }
			 //机构号
			 acc.setUnno(info.getUnno());
			 Boolean falg2 = false;
			 try{
				 falg2=gsClient.saveTermAcc(acc,"hrtREX1234.Java");
			 }catch (Exception e) {
				 log.info("调用webservice失败"+e);
			 }
			 if(!falg2){
				 merchantInfoModel.setApproveStatus("W");
				 merchantInfoDao.updateObject(merchantInfoModel);
				 mtim.setApproveStatus("Z");		//Y-已受理   Z-待受理  K-不受理
				 merchantTerminalInfoDao.updateObject(mtim);
				 return false;
				 //					throw new IllegalAccessError("调用webservice失败");
			 }
		 }
		 if(merchantInfoModel.getIsM35()==6){
			 String hql = "from AggPayTerminfoModel where mid = :mid and status != :status";
			 Map<String, Object> map1 = new HashMap<String, Object>();
			 map1.put("mid", merchantInfoModel.getMid());
			 map1.put("status", 4);
			 List<AggPayTerminfoModel> list1 = aggPayTerminfoDao.queryObjectsByHqlList(hql, map1);
			 for(AggPayTerminfoModel payTerm:list1){
				 AggPayTerminfoModel term =aggPayTerminfoDao.queryObjectByHql("from AggPayTerminfoModel t where t.qrtid=?", new Object[]{payTerm.getQrtid()});
				 AggPayTerm payTermAcc = new AggPayTerm();
				 payTermAcc.setUnno(info.getUnno());
				 payTermAcc.setCby("SYS");
				 payTermAcc.setMid(term.getMid());
				 payTermAcc.setQrtid(term.getQrtid());
				 payTermAcc.setBapid(term.getBapid());
				 payTermAcc.setShopname(term.getShopname());

				 term.setApproveDate(new Date());
				 term.setStatus(2);
				 term.setApproveId(544924);
				 term.setShopname(term.getShopname());

				 Boolean falg2 = false ;
				 try{
					 log.info("聚合商户审批(业务):mid="+payTermAcc.getMid()+";Qrtid="+payTermAcc.getQrtid()+";typeflag="+payTermAcc.getTypeflag());
					 falg2=gsClient.saveAggPayTermSub(payTermAcc,"hrtREX1234.Java");
				 }catch(Exception e ){
					 log.error("聚合商户推送终端调用webservice失败：" + e);
				 }
				 if(!falg2){
					 term.setStatus(1);
					 log.error("调用webservice失败：");
					 //throw new IllegalAccessError("调用webservice失败");
				 }
			 }
		 }

		 UserModel userModel = new UserModel();
		 userModel.setCreateDate(new Date());
		 userModel.setUseLvl(3);
		 userModel.setIsLogin(0);
		 userModel.setCreateUser(user.getLoginName());
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
		 userModel.setStatus(1);
		 userDao.saveObject(userModel);

		 todoDao.editStatusTodo("bill_merchant",ids);
		 return true;
	 }

	 /**
	  * 聚合商户调整限额 推送综合
	  */
	 @Override
	 public boolean updateAggMerchantLimitCToADM(MerchantInfoBean merchantInfo, UserBean user)throws Exception {
		 if(merchantInfo.getMid()==null||"".equals(merchantInfo.getMid())){
			 return false;
		 }
		 MerchantInfo info = new MerchantInfo();
		 String use = user.getUserName();
		 Date date = new Date();
		 info.setAccountStatus("3");
		 if(!"0".equals(merchantInfo.getTxnLimit())){
			 info.setMinfo1(merchantInfo.getTxnLimit());
		 }
		 info.setMinfo2(merchantInfo.getDailyLimit());
		 info.setMid(merchantInfo.getMid());
		 info.setCby(use);
		 info.setSales("1");//业务人员
		 info.setMainTenance("1");//维护人员
		 info.setMaintainDate(ClassToJavaBeanUtil.convertToXMLGregorianCalendar(date));
		 Boolean falg=gsClient.updateMerchantInfo(info,"hrtREX1234.Java");
		 if(falg){
			 MerchantInfoModel m =merchantInfoDao.queryObjectByHql("from MerchantInfoModel t where t.mid=?", new Object[]{merchantInfo.getMid()});
			 m.setAreaType(merchantInfo.getAreaType());
			 m.setApproveStatus("Y");
		 }
		 log.info("聚合商户调整限额 推送综合：mid="+merchantInfo.getMid()+"; Minfo1="+merchantInfo.getTxnLimit()+"; Minfo2="+merchantInfo.getDailyLimit()+"; "+falg);
		 return falg;
	 }

	 /**
	  * 商户退回
	  */
	 @Override
	 public void updateMerchantInfoK(MerchantInfoBean merchantInfo, UserBean user, String ids)
			 throws Exception {
		 String merchantInfoIds[]=ids.split(",");
		 for (int i = 0; i < merchantInfoIds.length; i++) {
			 MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, Integer.parseInt(merchantInfoIds[i]));
			 merchantInfoModel.setProcessContext(merchantInfo.getProcessContext());
			 merchantInfoModel.setApproveUid(user.getUserID());
			 merchantInfoModel.setApproveDate(new Date());
			 merchantInfoModel.setApproveStatus("K");		//Y-放行   Z-待放行  K-踢回
			 merchantInfoDao.updateObject(merchantInfoModel);
			 //退回后删除该商户下的所有已绑定的终端
			 //查找中间表并删除
			 String sql = "from MerchantTerminalInfoModel where mid=:mid and maintaintype!='D'";
			 Map<String, Object> map = new HashMap<String, Object>();
			 map.put("mid", merchantInfoModel.getMid());
			 List<MerchantTerminalInfoModel> mtifs = merchantTerminalInfoDao.queryObjectsByHqlList(sql, map);
			 for (MerchantTerminalInfoModel merchantTerminalInfoModel : mtifs) {
				 //				merchantTerminalInfoModel.setMaintainType("D");
				 //				merchantTerminalInfoModel.setMaintainDate(new Date());
				 //				merchantTerminalInfoModel.setMaintainUid(user.getUserID());
				 //				merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
				 //根据中间表修改设备表
				 String hql = "from TerminalInfoModel where termID = :termID";
				 Map<String, Object> map2 = new HashMap<String, Object>();
				 map2.put("termID", merchantTerminalInfoModel.getTid());
				 List<TerminalInfoModel> timList = terminalInfoDao.queryObjectsByHqlList(hql, map2);
				 for (TerminalInfoModel terminalInfoModel : timList) {
					 terminalInfoModel.setStatus(0);
					 terminalInfoModel.setUsedConfirmDate(null);
					 terminalInfoDao.updateObject(terminalInfoModel);
				 }
				 //退回之后删除该数据，中间表唯一性约束
				 merchantTerminalInfoDao.deleteObject(merchantTerminalInfoModel);
			 }
			 todoDao.editStatusTodo("bill_merchant",merchantInfoIds[i]);
		 }
	 }

	 public String queryMerchantWithQRUrl(MerchantInfoBean merchantInfo,UserBean user)throws Exception{
		 String sql = " select * from bill_merchantinfo  where mid =:mid ";
		 Map param=new HashMap();
		 param.put("mid", merchantInfo.getMid());
		 List<Map<String,Object>> list = merchantInfoDao.executeSql2(sql, param);
		 String qrUrl = (String) list.get(0).get("QR_URL");
		 return qrUrl;

	 }

	 /**
	  * 生成商户二维码
	  * 
	  */
	 @Override
	 public void updateMerchantWithQR(String bmids)throws Exception {

		 String hql = "select b from MerchantInfoModel b " +
				 "where b.bmid in ("+bmids+") and b.qrUrl is null and b.qrUpload is null and b.unno not in ('992084','992075','982035','992146','992110','982058','992107','992070','992122','992114','982049','982071','982192','982125') ";
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryObjectsByHqlList(hql);
		 //生成 & 存贮
		 QRToImageWriter writer= new QRToImageWriter();
		 for (MerchantInfoModel model: merchantInfoList){
			 MerchantInfoModel merchantInfoModel =  writer.doQR(model);
			 this.saveMerWithQRById(merchantInfoModel);
		 }
	 }

	 /**
	  * 生成商户二维码后，存贮链接和地址
	  */
	 @Override
	 public void saveMerWithQRById(MerchantInfoModel merchantInfoModel)throws Exception {

		 String hql = "update MerchantInfoModel b set b.qrUrl='"+merchantInfoModel.getQrUrl()+"',b.qrUpload='" +merchantInfoModel.getQrUpload()+
				 "'  where b.bmid="+merchantInfoModel.getBmid();
		 Map<String, Object> map = new HashMap<String, Object>();
		 merchantInfoDao.executeHql(hql);
	 }

	 /**
	  * 查询已放行的商户
	  */
	 @Override
	 public DataGridBean queryMerchantInfoY(MerchantInfoBean merchantInfo,UserBean user)
			 throws Exception {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 boolean flag = false;
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=1 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=1  ";
				 map.put("maintainType", "D");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=1 ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=1 ";
					 map.put("maintainType", "D");
				 }
			 }else if(isAgentformMan(user)){//判断是否是报单员
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35!=1 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35!=1 ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("maintainuid", user.getUserID());
			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35!=1 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35!=1 ";
				 map.put("maintainType", "D");
			 }
		 }else{
			 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=1 ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=1 ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND M.APPROVESTATUS = :approveStatus";
			 sqlCount += " AND M.APPROVESTATUS= :approveStatus";
			 map.put("approveStatus", merchantInfo.getApproveStatus());
		 } 

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND M.MID = :mid";
			 sqlCount += " AND M.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
			 flag=true;
		 }
		 //归属
		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND M.UNNO IN ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 sqlCount += " AND M.UNNO IN ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND M.RNAME LIKE :rname";
			 sqlCount += " AND M.RNAME LIKE :rname";
			 map.put("rname",merchantInfo.getRname()+'%');
			 flag=true;
		 }
		 if(merchantInfo.getBaddr() !=null && !merchantInfo.getBaddr().equals("") ){
			 sql +=" AND M.BADDR LIKE :baddr";
			 sqlCount += " AND M.BADDR LIKE :baddr";
			 map.put("baddr",'%'+merchantInfo.getBaddr()+'%');
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType and APPROVESTATUS=:approvestatus)";
			 map.put("tid",merchantInfo.getTid()+'%');
			 map.put("maintainType", "D");
			 map.put("approvestatus", "Y");
			 flag=true;
		 }
		 if(merchantInfo.getIsM35() !=null && merchantInfo.getIsM35()==6 ){
			 sql +=" AND M.ISM35 = 6 ";
			 sqlCount += " AND M.ISM35 = 6";
		 }else if(merchantInfo.getIsM35() !=null && merchantInfo.getIsM35()==0 ){
			 sql +=" AND M.ISM35 != 6 ";
			 sqlCount += " AND M.ISM35!= 6";
		 }
		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND M.BANKACCNO LIKE :bankAccNo";
			 sqlCount += " AND M.BANKACCNO LIKE :bankAccNo";
			 map.put("bankAccNo",merchantInfo.getBankAccNo()+'%');
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND M.UNNO = :merunno";
			 sqlCount += " AND M.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
			 flag=true;
		 } 
		 //		if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
		 //			sql +=" AND M.approveUid = :userID";
		 //			sqlCount += " AND M.approveUid = :userID";
		 //			map.put("userID",getUserID(merchantInfo.getApproveUidName().trim()) );
		 //			flag=true;
		 //			
		 //		} 
		 if (merchantInfo.getLegalNum() != null && !"".equals(merchantInfo.getLegalNum().trim())) {
			 sql +=" AND M.legalNum = :legalNum";
			 sqlCount += " AND M.legalNum = :legalNum";
			 map.put("legalNum",merchantInfo.getLegalNum().trim() );
			 flag=true;

		 } 
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //			sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 //			sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 //			map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
			 sql +=" AND M.approveDate >= to_date('"+merchantInfo.getCreateDateStart()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.approveDate >= to_date('"+merchantInfo.getCreateDateStart()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
		 } 

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //			sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 //			sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 //			map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
			 sql +=" AND M.approveDate <= to_date('"+merchantInfo.getCreateDateEnd()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.approveDate <= to_date('"+merchantInfo.getCreateDateEnd()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		 }
		 if (flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd()==null||merchantInfo.getCreateDateEnd().equals(""))) {
			 //			sql +=" AND trunc(M.approveDate) =trunc(sysdate)";
			 //			sqlCount += " AND trunc(M.approveDate) =trunc(sysdate)";
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 sql +=" AND M.approveDate between to_date('"+sdf.format(new Date())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+sdf.format(new Date())+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.approveDate between to_date('"+sdf.format(new Date())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+sdf.format(new Date())+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		 }

		 sql += " order by M.approveDate DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 //是否隐藏手机号信息
		 Integer i = merchantInfoDao.querysqlCounts2("select count(1) from sys_configure a where a.groupname='phoneFilter' and a.minfo1='"+user.getUnNo()+"'", null);
		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),i);
		 return dataGridBean;
	 }

	 /**
	  * 查询已放行的商户
	  */
	 @Override
	 public DataGridBean queryMerchantInfoY3(MerchantInfoBean merchantInfo,UserBean user)
			 throws Exception {
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 boolean flag = false;
		 Map<String, Object> map = new HashMap<String, Object>();
		 if("110000".equals(user.getUnNo())||user.getLoginName().startsWith("baodan0")||user.getLoginName().startsWith("11100099")){
			 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=6 ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=6  ";
			 map.put("maintainType", "D");
		 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			 UnitModel parent = unitModel.getParent();
			 if("110000".equals(parent.getUnNo())){
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=6 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35!=6 ";
				 map.put("maintainType", "D");
			 }
		 }else if(isAgentformMan(user)){//判断是否是报单员
			 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35!=6 ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35!=6 ";
			 map.put("unno", user.getUnNo());
			 map.put("maintainType", "D");
			 map.put("maintainuid", user.getUserID());
		 }else{
			 String childUnno=queryUnitUnnoUtil(user.getUnNo());
			 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35!=6 ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35!=6 ";
			 map.put("maintainType", "D");
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND M.APPROVESTATUS = :approveStatus";
			 sqlCount += " AND M.APPROVESTATUS= :approveStatus";
			 map.put("approveStatus", merchantInfo.getApproveStatus());
		 } 

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND M.MID = :mid";
			 sqlCount += " AND M.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
			 flag=true;
		 }
		 //归属
		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND M.UNNO IN ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 sqlCount += " AND M.UNNO IN ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND M.RNAME LIKE :rname";
			 sqlCount += " AND M.RNAME LIKE :rname";
			 map.put("rname",merchantInfo.getRname()+'%');
			 flag=true;
		 }
		 if(merchantInfo.getBaddr() !=null && !merchantInfo.getBaddr().equals("") ){
			 sql +=" AND M.BADDR LIKE :baddr";
			 sqlCount += " AND M.BADDR LIKE :baddr";
			 map.put("baddr",'%'+merchantInfo.getBaddr()+'%');
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType and APPROVESTATUS=:approvestatus)";
			 map.put("tid",merchantInfo.getTid()+'%');
			 map.put("maintainType", "D");
			 map.put("approvestatus", "Y");
			 flag=true;
		 }
		 if(merchantInfo.getIsM35() !=null && merchantInfo.getIsM35()==6 ){
			 sql +=" AND M.ISM35 = 6 ";
			 sqlCount += " AND M.ISM35 = 6";
		 }else if(merchantInfo.getIsM35() !=null && merchantInfo.getIsM35()==0 ){
			 sql +=" AND M.ISM35 != 6 ";
			 sqlCount += " AND M.ISM35!= 6";
		 }
		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND M.BANKACCNO LIKE :bankAccNo";
			 sqlCount += " AND M.BANKACCNO LIKE :bankAccNo";
			 map.put("bankAccNo",'%'+merchantInfo.getBankAccNo()+'%');
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND M.UNNO = :merunno";
			 sqlCount += " AND M.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
			 flag=true;
		 } 
		 //		if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
		 //			sql +=" AND M.approveUid = :userID";
		 //			sqlCount += " AND M.approveUid = :userID";
		 //			map.put("userID",getUserID(merchantInfo.getApproveUidName().trim()) );
		 //			flag=true;
		 //			
		 //		} 
		 if (merchantInfo.getLegalNum() != null && !"".equals(merchantInfo.getLegalNum().trim())) {
			 sql +=" AND M.legalNum = :legalNum";
			 sqlCount += " AND M.legalNum = :legalNum";
			 map.put("legalNum",merchantInfo.getLegalNum().trim() );
			 flag=true;

		 } 
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //			sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 //			sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 //			map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
			 sql +=" AND M.approveDate >= to_date('"+merchantInfo.getCreateDateStart()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.approveDate >= to_date('"+merchantInfo.getCreateDateStart()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
		 } 

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //			sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 //			sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 //			map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
			 sql +=" AND M.approveDate <= to_date('"+merchantInfo.getCreateDateEnd()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.approveDate <= to_date('"+merchantInfo.getCreateDateEnd()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		 }
		 if (flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd()==null||merchantInfo.getCreateDateEnd().equals(""))) {
			 //			sql +=" AND trunc(M.approveDate) =trunc(sysdate)";
			 //			sqlCount += " AND trunc(M.approveDate) =trunc(sysdate)";
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 sql +=" AND M.approveDate between to_date('2018-01-01 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+sdf.format(new Date())+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.approveDate between to_date('2018-01-01 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+sdf.format(new Date())+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		 }

		 sql += " order by M.approveDate DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 DataGridBean dataGridBean = formatToDataGridbaodan(merchantInfoList, counts.intValue());
		 return dataGridBean;
	 }
	 @Override
	 public DataGridBean queryUnnoCodeInfobaodan(MerchantInfoBean merchantInfo,UserBean user)
			 throws Exception {
		 String sql = "";
		 String sqlCount = "";
		 Map<String, Object> map = new HashMap<String, Object>();
		 if("110000".equals(user.getUnNo())){
			 sql = "SELECT M.* FROM bill_agentmappinfo M WHERE M.creater=:unno ";
			 sqlCount = "SELECT COUNT(*) FROM bill_agentmappinfo M WHERE M.creater=:unno ";
			 map.put("unno", user.getUnNo());
		 }else{		//如果为部门机构并且级别为总公司
			 sql = "SELECT M.* FROM bill_agentmappinfo M WHERE M.creater=:unno ";
			 sqlCount = "SELECT COUNT(*) FROM bill_agentmappinfo M WHERE M.creater=:unno ";
			 map.put("unno", user.getUnNo());
		 }
		 if(merchantInfo.getUnitName()!=null&&!"".equals(merchantInfo.getUnitName())){
			 sql += " and M.Code like :UnitName ";
			 sqlCount += " and M.Code like :UnitName ";
			 map.put("UnitName", "%"+merchantInfo.getUnitName()+"%");
		 }
		 sql += " order by M.Code DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 //		List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 List<Map<String, String>> merchantInfoList = merchantInfoDao.queryObjectsBySqlList(sql, map, merchantInfo.getPage(), merchantInfo.getRows());
		 DataGridBean dataGridBean = new DataGridBean();
		 dataGridBean.setRows(merchantInfoList);
		 dataGridBean.setTotal(counts.intValue());
		 return dataGridBean;
	 }

	 /**
	  * 查询已放行的商户日志
	  */
	 @Override
	 public DataGridBean listMerchantInfoYLog(MerchantInfoBean merchantInfo,UserBean user)
			 throws Exception {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 boolean flag = false;
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT M.* FROM BILL_MERCHANTINFOLOG M WHERE 1=1 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFOLOG M  WHERE 1=1 ";
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT M.* FROM BILL_MERCHANTINFOLOG M  WHERE 1=1 ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFOLOG M WHERE 1=1 ";
				 }
			 }else if(isAgentformMan(user)){//判断是否是报单员
				 sql = "SELECT M.* FROM BILL_MERCHANTINFOLOG M  WHERE M.UNNO = :unno and M.maintainuid = :maintainuid ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFOLOG M  WHERE M.UNNO = :unno and M.maintainuid = :maintainuid ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainuid", user.getUserID());
			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = "SELECT M.* FROM BILL_MERCHANTINFOLOG M  WHERE M.UNNO IN ("+childUnno+") ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFOLOG M  WHERE M.UNNO IN ("+childUnno+") ";
			 }
		 }else{
			 sql = "SELECT M.* FROM BILL_MERCHANTINFOLOG M  WHERE M.MAINTAINUSERID = :maintainUserId ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFOLOG M  WHERE M.MAINTAINUSERID = :maintainUserId  ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND M.APPROVESTATUS = :approveStatus";
			 sqlCount += " AND M.APPROVESTATUS= :approveStatus";
			 map.put("approveStatus", merchantInfo.getApproveStatus());
		 } 

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND M.MID = :mid";
			 sqlCount += " AND M.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
			 flag=true;
		 }
		 //归属
		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND M.UNNO IN ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 sqlCount += " AND M.UNNO IN ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND M.RNAME LIKE :rname";
			 sqlCount += " AND M.RNAME LIKE :rname";
			 map.put("rname",merchantInfo.getRname()+'%');
			 flag=true;
		 }
		 if(merchantInfo.getBaddr() !=null && !merchantInfo.getBaddr().equals("") ){
			 sql +=" AND M.BADDR LIKE :baddr";
			 sqlCount += " AND M.BADDR LIKE :baddr";
			 map.put("baddr",'%'+merchantInfo.getBaddr()+'%');
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType and APPROVESTATUS=:approvestatus)";
			 map.put("tid",merchantInfo.getTid()+'%');
			 map.put("maintainType", "D");
			 map.put("approvestatus", "Y");
			 flag=true;
		 }
		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND M.BANKACCNO LIKE :bankAccNo";
			 sqlCount += " AND M.BANKACCNO LIKE :bankAccNo";
			 map.put("bankAccNo",merchantInfo.getBankAccNo()+'%');
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND M.UNNO = :merunno";
			 sqlCount += " AND M.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
			 flag=true;
		 } 
		 if (merchantInfo.getLegalNum() != null && !"".equals(merchantInfo.getLegalNum().trim())) {
			 sql +=" AND M.legalNum = :legalNum";
			 sqlCount += " AND M.legalNum = :legalNum";
			 map.put("legalNum",merchantInfo.getLegalNum().trim() );
			 flag=true;

		 } 
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 sql +=" AND M.approveDate >= to_date('"+merchantInfo.getCreateDateStart()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.approveDate >= to_date('"+merchantInfo.getCreateDateStart()+" 00:00:00','yyyy-MM-dd hh24:mi:ss')";
		 } 

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 sql +=" AND M.maintainDate <= to_date('"+merchantInfo.getCreateDateEnd()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.maintainDate <= to_date('"+merchantInfo.getCreateDateEnd()+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		 }
		 if (flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd()==null||merchantInfo.getCreateDateEnd().equals(""))) {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 sql +=" AND M.maintainDate between to_date('"+sdf.format(new Date())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+sdf.format(new Date())+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
			 sqlCount +=" AND M.maintainDate between to_date('"+sdf.format(new Date())+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and to_date('"+sdf.format(new Date())+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		 }

		 sql += " order by M.maintainDate DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 //		List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 List<Map<String, String>> merchantInfoList = merchantInfoDao.queryObjectsBySqlList(sql, map, merchantInfo.getPage(), merchantInfo.getRows());
		 DataGridBean dataGridBean = new DataGridBean();
		 dataGridBean.setRows(merchantInfoList);
		 dataGridBean.setTotal(counts.intValue());
		 //		DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());
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
		 //		MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, merchantInfo.getBmid());
		 //		if("2".equals(merchantInfo.getIsM35()+"")||"3".equals(merchantInfo.getIsM35()+"")||"4".equals(merchantInfo.getIsM35()+"")||"5".equals(merchantInfo.getIsM35()+"")){
		 //			merchantInfo.setAreaCode(merchantInfoModel.getAreaCode().trim());
		 //		}
		 //		if(merchantInfoModel.getApproveStatus().equals(merchantInfo.getApproveStatus())){
		 MerchantInfoModel merchantInfoModel=new MerchantInfoModel();
		 BeanUtils.copyProperties(merchantInfo, merchantInfoModel);

		 //merchantInfoModel.setIsM35(0);
		 if(merchantInfoModel.getIsM35()==null){
			 merchantInfoModel.setIsM35(0);
		 }else if(merchantInfoModel.getIsM35()==2||merchantInfoModel.getIsM35()==3){
			 merchantInfoModel.setAreaType("1");
		 }else if(merchantInfoModel.getIsM35()==4){
			 merchantInfoModel.setAreaType("2");
		 }else if(merchantInfoModel.getIsM35()==5){
			 merchantInfoModel.setAreaType("3");
		 }else{
			 merchantInfoModel.setIsM35(0);
		 }
		 //封顶值
		 if(merchantInfo.getMerchantType()!=null&&merchantInfo.getMerchantType() == 2){

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
		 //扫码支付费率
		 if(merchantInfo.getScanRate()!=null&&!"".equals(merchantInfo.getScanRate())){
			 Double ScanRate = merchantInfo.getScanRate()/100;
			 merchantInfoModel.setScanRate(ScanRate);
		 }else{
			 merchantInfoModel.setScanRate(0.0);
		 }
		 //结算周期
		 if("".equals(merchantInfo.getSettleCycle())||merchantInfo.getSettleCycle()==null){
			 merchantInfoModel.setSettleCycle(1);
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
			 //				fName1.append(user.getUnNo());
			 //				fName1.append(".");
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
			 //				fName2.append(user.getUnNo());
			 //				fName2.append(".");
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
			 //				fName3.append(user.getUnNo());
			 //				fName3.append(".");
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
			 //				fName4.append(user.getUnNo());
			 //				fName4.append(".");
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
			 //				fName5.append(user.getUnNo());
			 //				fName5.append(".");
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
			 //				fName6.append(user.getUnNo());
			 //				fName6.append(".");
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
			 //				fName7.append(user.getUnNo());
			 //				fName7.append(".");
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
			 //				fName8.append(user.getUnNo());
			 //				fName8.append(".");
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
			 //				fName9.append(user.getUnNo());
			 //				fName9.append(".");
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
			 //				fNameA.append(user.getUnNo());
			 //				fNameA.append(".");
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
			 //				fNameB.append(user.getUnNo());
			 //				fNameB.append(".");
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
			 //				fNameC.append(user.getUnNo());
			 //				fNameC.append(".");
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
			 //				fNameD.append(user.getUnNo());
			 //				fNameD.append(".");
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
			 //				fNameE.append(user.getUnNo());
			 //				fNameE.append(".");
			 fNameE.append(merchantInfoModel.getMid());
			 fNameE.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fNameE.append(imageDay);
			 fNameE.append(".E");
			 fNameE.append(merchantInfo.getMaterialUpLoad7().substring(merchantInfo.getMaterialUpLoad7().lastIndexOf(".")).toLowerCase());
			 uploadFile(merchantInfo.getMaterialUpLoad7File(),fNameE.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad7(fNameE.toString());
		 }
		 if(merchantInfo.getPosBackImgFile() != null && merchantInfo.getPosBackImgFile() != null){
			 StringBuffer fNameF = new StringBuffer();
			 //				fNameF.append(user.getUnNo());
			 //				fNameF.append(".");
			 fNameF.append(merchantInfoModel.getMid());
			 fNameF.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fNameF.append(imageDay);
			 fNameF.append(".F");
			 fNameF.append(merchantInfo.getPosBackImg().substring(merchantInfo.getPosBackImg().lastIndexOf(".")).toLowerCase());
			 uploadFile(merchantInfo.getPosBackImgFile(),fNameF.toString(),imageDay);
			 merchantInfoModel.setPosBackImg(fNameF.toString());
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
		 if("W".equals(merchantInfo.getApproveStatus())){
			 merchantInfoModel.setApproveStatus("W");
		 }else{
			 merchantInfoModel.setApproveStatus("Z");
		 }
		 merchantInfoModel.setMaintainUid(user.getUserID());
		 merchantInfoModel.setMaintainDate(new Date());
		 merchantInfoModel.setMaintainUserId(merchantInfo.getBusid());		//添加的时候业务人员和维护人员一样
		 merchantInfoModel.setMaintainType("M");
		 merchantInfoModel.setSettleStatus("1");		//结算状态 1-正常 2-冻结
		 merchantInfoModel.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销
		 //			merchantInfoDao.updateObject(merchantInfoModel);	

		 String sql ="update BILL_MERCHANTINFO set RNAME=:RNAME,ISM35=:ISM35,DEALAMT=:DEALAMT,FEEAMT=:FEEAMT,BANKFEERATE=:BANKFEERATE,contractNo=:contractNo," +
				 "payBankId=:payBankId,businessScope=:businessScope,accNum=:accNum,accExpdate=:accExpdate,bankAccNo=:bankAccNo,bankAccName=:bankAccName," +
				 "contactAddress=:contactAddress,contactPerson=:contactPerson,contactPhone=:contactPhone,contactTel=:contactTel,remarks=:remarks," +
				 "legalPerson=:legalPerson,legalNum=:legalNum,legalExpdate=:legalExpdate,"+
				 "CREDITBANKRATE=:CREDITBANKRATE,SETTLECYCLE=:SETTLECYCLE,FEERATE_V=:FEERATE_V,FEERATE_M=:FEERATE_M,FEERATE_J=:FEERATE_J,bno=:bno," +
				 "FEERATE_A=:FEERATE_A,FEERATE_D=:FEERATE_D,SETTLEMERGER=:SETTLEMERGER,SETTMETHOD=:SETTMETHOD,SHORTNAME=:SHORTNAME,PRINTNAME=:PRINTNAME,BANKTYPE=:BANKTYPE," +
				 "LEGALUPLOADFILENAME=:LEGALUPLOADFILENAME,BUPLOAD=:BUPLOAD,RUPLOAD=:RUPLOAD,REGISTRYUPLOAD=:REGISTRYUPLOAD,MATERIALUPLOAD=:MATERIALUPLOAD,PHOTOUPLOAD=:PHOTOUPLOAD," +
				 "BIGDEALUPLOAD=:BIGDEALUPLOAD,MATERIALUPLOAD1=:MATERIALUPLOAD1,MATERIALUPLOAD2=:MATERIALUPLOAD2,MATERIALUPLOAD3=:MATERIALUPLOAD3,MATERIALUPLOAD4=:MATERIALUPLOAD4," +
				 "MATERIALUPLOAD5=:MATERIALUPLOAD5,MATERIALUPLOAD6=:MATERIALUPLOAD6,MATERIALUPLOAD7=:MATERIALUPLOAD7,APPROVESTATUS=:APPROVESTATUS,MAINTAINUID=:MAINTAINUID,MAINTAINDATE=:MAINTAINDATE," +
				 "MAINTAINUSERID=:MAINTAINUSERID,MAINTAINTYPE=:MAINTAINTYPE,SETTLESTATUS=:SETTLESTATUS,ACCOUNTSTATUS=:ACCOUNTSTATUS,ScanRate=:ScanRate,posBackImg=:posBackImg" +
				 " where mid=:MID  and APPROVESTATUS in('K') ";

		 Map<String, Object> param = new HashMap<String, Object>();
		 param.put("ScanRate", merchantInfo.getScanRate()/100);
		 param.put("ISM35", merchantInfoModel.getIsM35());
		 param.put("DEALAMT", merchantInfoModel.getDealAmt());
		 param.put("FEEAMT", merchantInfo.getFeeAmt());
		 param.put("remarks", merchantInfoModel.getRemarks());
		 param.put("legalPerson", merchantInfoModel.getLegalPerson());
		 param.put("legalNum", merchantInfoModel.getLegalNum());
		 param.put("legalExpdate", merchantInfoModel.getLegalExpdate());
		 param.put("accNum", merchantInfoModel.getAccNum());
		 param.put("contractNo", merchantInfoModel.getContractNo());
		 param.put("accExpdate", merchantInfoModel.getAccExpdate());
		 param.put("contactAddress", merchantInfoModel.getContactAddress());
		 param.put("contactPerson", merchantInfoModel.getContactPerson());
		 param.put("contactPhone", merchantInfoModel.getContactPhone());
		 param.put("contactTel", merchantInfoModel.getContactTel());
		 param.put("payBankId", merchantInfoModel.getPayBankId());
		 param.put("businessScope", merchantInfoModel.getBusinessScope());
		 param.put("bankAccNo", merchantInfoModel.getBankAccNo());
		 param.put("bankAccName", merchantInfoModel.getBankAccName());
		 param.put("BANKFEERATE", merchantInfoModel.getBankFeeRate());
		 param.put("CREDITBANKRATE", merchantInfoModel.getCreditBankRate());
		 param.put("SETTLECYCLE", merchantInfoModel.getSettleCycle());
		 param.put("FEERATE_V", merchantInfoModel.getFeeRateV());
		 param.put("FEERATE_M", merchantInfoModel.getFeeRateM());
		 param.put("FEERATE_J", merchantInfoModel.getFeeRateJ());
		 param.put("bno", merchantInfoModel.getBno());
		 param.put("FEERATE_A", merchantInfoModel.getFeeRateA());
		 param.put("FEERATE_D", merchantInfoModel.getFeeRateD());
		 param.put("SETTLEMERGER", merchantInfoModel.getSettleMerger());
		 param.put("SETTMETHOD", merchantInfoModel.getSettMethod());
		 param.put("SHORTNAME", merchantInfoModel.getShortName());
		 param.put("RNAME", merchantInfoModel.getRname());
		 param.put("PRINTNAME", merchantInfoModel.getPrintName());
		 param.put("BANKTYPE", merchantInfoModel.getBankType());
		 param.put("LEGALUPLOADFILENAME", merchantInfoModel.getLegalUploadFileName());
		 param.put("BUPLOAD", merchantInfoModel.getBupLoad());
		 param.put("RUPLOAD", merchantInfoModel.getRupLoad());
		 param.put("REGISTRYUPLOAD", merchantInfoModel.getRegistryUpLoad());
		 param.put("MATERIALUPLOAD", merchantInfoModel.getMaterialUpLoad());
		 param.put("PHOTOUPLOAD", merchantInfoModel.getPhotoUpLoad());
		 param.put("BIGDEALUPLOAD", merchantInfoModel.getBigdealUpLoad());
		 param.put("MATERIALUPLOAD1", merchantInfoModel.getMaterialUpLoad1());
		 param.put("MATERIALUPLOAD2", merchantInfoModel.getMaterialUpLoad2());
		 param.put("MATERIALUPLOAD3", merchantInfoModel.getMaterialUpLoad3());
		 param.put("MATERIALUPLOAD4", merchantInfoModel.getMaterialUpLoad4());
		 param.put("MATERIALUPLOAD5", merchantInfoModel.getMaterialUpLoad5());
		 param.put("MATERIALUPLOAD6", merchantInfoModel.getMaterialUpLoad6());
		 param.put("MATERIALUPLOAD7", merchantInfoModel.getMaterialUpLoad7());
		 param.put("APPROVESTATUS", merchantInfoModel.getApproveStatus());
		 param.put("MAINTAINUID", merchantInfoModel.getMaintainUid());
		 param.put("MAINTAINDATE", merchantInfoModel.getMaintainDate());
		 param.put("MAINTAINUSERID", merchantInfoModel.getMaintainUserId());
		 param.put("MAINTAINTYPE", merchantInfoModel.getMaintainType());
		 param.put("SETTLESTATUS", merchantInfoModel.getSettleStatus());
		 param.put("ACCOUNTSTATUS", merchantInfoModel.getAccountStatus());
		 param.put("MID", merchantInfoModel.getMid());
		 param.put("posBackImg", merchantInfoModel.getPosBackImg());
		 if(merchantInfo.getExchangTime()!=null&&!"".equals(merchantInfo.getExchangTime())){
			 sql+=" and EXCHANGE_TIME=:EXCHANGE_TIME ";
			 param.put("EXCHANGE_TIME", merchantInfo.getExchangTime());
		 }
		 Integer i = merchantInfoDao.executeSqlUpdate(sql, param);
		 if(i>0){
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

	 /**
	  * 删除商户
	  */
	 @Override
	 public void deleteMerchantInfo(MerchantInfoBean merchantInfoBean, UserBean user)
			 throws Exception {
		 MerchantInfoModel merchantInfo = merchantInfoDao.getObjectByID(MerchantInfoModel.class, merchantInfoBean.getBmid());
		 merchantInfo.setMaintainUid(user.getUserID());
		 merchantInfo.setMaintainDate(new Date());
		 merchantInfo.setMaintainType("D");
		 merchantInfoDao.updateObject(merchantInfo);

		 String mtimhql = "from MerchantTerminalInfoModel m where mid = :mid and maintainType != :maintainType";
		 Map<String, Object> mtimmap = new HashMap<String, Object>();
		 mtimmap.put("mid", merchantInfo.getMid());
		 mtimmap.put("maintainType", "D");
		 List<MerchantTerminalInfoModel> listmtim = merchantTerminalInfoDao.queryObjectsByHqlList(mtimhql, mtimmap);
		 for (MerchantTerminalInfoModel mtim : listmtim) {

			 Map<String, Object> tidmap = new HashMap<String, Object>();
			 String tidhql = "from TerminalInfoModel where maintainType != :maintainType and termID = :termID";
			 tidmap.put("maintainType", "D");
			 tidmap.put("termID", mtim.getTid());
			 List<TerminalInfoModel> terminalInfoModelList = terminalInfoDao.queryObjectsByHqlList(tidhql, tidmap);
			 if(terminalInfoModelList != null && terminalInfoModelList.size()>0){
				 TerminalInfoModel terminalInfoModel = terminalInfoModelList.get(0);
				 terminalInfoModel.setUsedConfirmDate(null);
				 terminalInfoModel.setStatus(1);
				 terminalInfoDao.updateObject(terminalInfoModel);
			 }

			 mtim.setMaintainType("D");
			 mtim.setMaintainDate(new Date());
			 mtim.setMaintainUid(user.getUserID());
			 merchantTerminalInfoDao.updateObject(mtim);
		 }
	 }

	 /**
	  * 查询商户代码
	  */
	 @Override
	 public DataGridBean searchMerchantInfo(String type ,String nameCode, UserBean user, MerchantInfoBean m) throws Exception {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
					 map.put("maintainType", "D");
					 map.put("approveStatus", "Y");
				 }
			 }else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }else{
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }
		 }else{
			 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS = :approveStatus ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
			 map.put("approveStatus", "Y");
		 }

		 if(nameCode !=null){
			 sql += " AND (MID LIKE '%" + nameCode + "%' OR RNAME LIKE '%" + nameCode + "%')";
			 sqlCount += " AND (MID LIKE '%" + nameCode + "%' OR RNAME LIKE '%" + nameCode + "%')";
		 }
		 if(type!=null && !"".equals(type)){
			 sql+=" and ism35 in ("+type+") ";
			 sqlCount+=" and ism35 in ("+type+") ";
			 //map.put("isM35", type);
		 }
		 sql += " ORDER BY BMID DESC";
		 List<MerchantInfoModel> merchantInfoModelList = merchantInfoDao.queryObjectsBySqlLists(sql, map, m.getPage(), m.getRows(), MerchantInfoModel.class);
		 BigDecimal count = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoBean> merchantInfoList = new ArrayList<MerchantInfoBean>();
		 for (MerchantInfoModel merchantInfo : merchantInfoModelList) {
			 MerchantInfoBean merchantInfoBean = new MerchantInfoBean();
			 BeanUtils.copyProperties(merchantInfo, merchantInfoBean);
			 merchantInfoList.add(merchantInfoBean);
		 }
		 DataGridBean dgb = new DataGridBean();
		 dgb.setRows(merchantInfoModelList);
		 dgb.setTotal(count.longValue());
		 return dgb;
	 }

	 /**
	  * 查询区域码
	  */
	 @Override
	 public DataGridBean searchCUPSendCodeInfo(String nameCode) throws Exception {
		 String sql = "SELECT SEND_CODE,SEND_NAME FROM BILL_CUPSENDCODEINFO C ";
		 if(nameCode !=null){
			 sql += " where (C.SEND_NAME LIKE '%" + nameCode.replaceAll("'", "") + "%' OR C.SEND_CODE LIKE '%" + nameCode.replaceAll("'", "") + "%')";
		 }
		 sql+=" ORDER BY C.SEND_CODE ASC";
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
	  * 查询MCCID
	  */
	 @Override
	 public DataGridBean searchMCC(String nameCode) throws Exception {
		 StringBuffer sb = new StringBuffer("SELECT MCCCODE,MCCNAME,MCCDESC FROM BILL_MM_L_MCC M WHERE 1 = 1");
		 if(nameCode !=null){
			 sb.append(" AND M.MCCNAME LIKE '%" + nameCode.replaceAll("'", "") + "%' OR M.MCCCODE LIKE '%" + nameCode.replaceAll("'", "") + "%'");
		 }
		 sb.append(" ORDER BY M.MCCCODE DESC");
		 List<Map<String,String>> proList = merchantInfoDao.executeSql(sb.toString());

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
	  * 查询SYS_CODE
	  */
	 @Override
	 public DataGridBean searchAreaCode(String nameCode) throws Exception {
		 StringBuffer sb = new StringBuffer("SELECT AREA_CODE,AREA_NAME,PROVINCE_CODE FROM SYS_AREA A WHERE 1 = 1");
		 if(nameCode !=null){
			 sb.append(" AND A.AREA_CODE LIKE '%" + nameCode.replaceAll("'", "") + "%' OR A.AREA_NAME LIKE '%" + nameCode.replaceAll("'", "")+ "%'");
		 }
		 sb.append(" ORDER BY A.AREA_CODE ASC"); 
		 List<Map<String,String>> proList = merchantInfoDao.executeSql(sb.toString());

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
	  * 查询SYS_CODE
	  */
	 @Override
	 public DataGridBean searchAreaByProvince(String nameCode) throws Exception {
		 StringBuffer sb = new StringBuffer("SELECT AREA_CODE,AREA_NAME,PROVINCE_CODE FROM SYS_AREA A WHERE 1 = 1");
		 if(nameCode !=null){
			 sb.append(" AND A.PROVINCE_CODE='" + nameCode.replaceAll("'", "") + "' ");
		 }
		 sb.append(" ORDER BY A.AREA_CODE ASC"); 
		 List<Map<String,String>> proList = merchantInfoDao.executeSql(sb.toString());

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
	  * 查询bill_IndustryInfo
	  */
	 @Override
	 public DataGridBean searchIndustryInfo(String nameCode) throws Exception {
		 String sql = "SELECT INDUSTRYTYPE,INAME FROM BILL_INDUSTRYINFO I WHERE 1 = 1";
		 if(nameCode !=null){
			 sql += " AND INAME LIKE '%" + nameCode + "%'";
		 }
		 sql += " ORDER BY I.INDUSTRYTYPE DESC";
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
	  * 查询出商户显示到下拉框select
	  */
	 @Override
	 public DataGridBean searchMerchantInfoTree(String unNo) throws Exception {
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, unNo);
		 String sql = "";
		 //Map<String, Object> map = new HashMap<String, Object>();
		 if("110000".equals(unNo)){
			 sql = "SELECT MID,RNAME FROM BILL_MERCHANTINFO m WHERE m.APPROVESTATUS = 'Y' and m.MAINTAINTYPE != 'D'";
		 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			 UnitModel parent = unitModel.getParent();
			 if("110000".equals(parent.getUnNo())){
				 sql = "SELECT MID,RNAME FROM BILL_MERCHANTINFO m WHERE m.APPROVESTATUS = 'Y' and m.MAINTAINTYPE != 'D'";
			 }else{
				 sql = "SELECT MID,RNAME FROM BILL_MERCHANTINFO m WHERE m.APPROVESTATUS = 'Y' and m.MAINTAINTYPE != 'D' AND UNNO = " + unNo;
			 }
		 }else{
			 sql = "SELECT MID,RNAME FROM BILL_MERCHANTINFO m WHERE m.APPROVESTATUS = 'Y' and m.MAINTAINTYPE != 'D' AND UNNO = " + unNo;
		 }
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
	  * 商户图片查看
	  * */
	 private String searchImage(MerchantInfoModel m) {
		 String title="";
		 //传统图片路径
		 //		if(m.getIsM35()!=null&&m.getIsM35()!=6&&m.getIsM35()!=1){
		 //			title="traditionImage";//表sys_param中的title
		 //		//手刷及立马富
		 //		}else{
		 title="HrtFrameWork";
		 //		}
		 return merchantInfoDao.querySavePath(title);
	 }

	 /**
	  * 查询商户明细
	  */
	 @SuppressWarnings("unchecked")
	 @Override
	 public List<Object> queryMerchantInfoDetailed(Integer bmid) {
		 MerchantInfoModel mm=(MerchantInfoModel) merchantInfoDao.queryObjectByHql("from MerchantInfoModel m where m.bmid=?", new Object[]{bmid});
		 String upload = searchImage(mm);
		 List<Object> list2=new ArrayList<Object>();
		 if(mm.getLegalUploadFileName()!="" && mm.getLegalUploadFileName()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getLegalUploadFileName())+File.separator+mm.getLegalUploadFileName());
		 }else{
			 list2.add("");
		 }
		 if(mm.getBupLoad()!="" && mm.getBupLoad()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getBupLoad())+File.separator+mm.getBupLoad());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getRupLoad()!="" && mm.getRupLoad()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getRupLoad())+File.separator+mm.getRupLoad());
		 }else{
			 list2.add("");
		 }
		 if(mm.getRegistryUpLoad()!="" && mm.getRegistryUpLoad()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getRegistryUpLoad())+File.separator+mm.getRegistryUpLoad());
		 }else{
			 list2.add("");
		 }
		 if(mm.getMaterialUpLoad()!="" && mm.getMaterialUpLoad()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad())+File.separator+mm.getMaterialUpLoad());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getPhotoUpLoad()!="" && mm.getPhotoUpLoad()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getPhotoUpLoad())+File.separator+mm.getPhotoUpLoad());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getBigdealUpLoad()!="" && mm.getBigdealUpLoad()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getBigdealUpLoad())+File.separator+mm.getBigdealUpLoad());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getMaterialUpLoad1()!="" && mm.getMaterialUpLoad1()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad1())+File.separator+mm.getMaterialUpLoad1());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getMaterialUpLoad2()!="" && mm.getMaterialUpLoad2()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad2())+File.separator+mm.getMaterialUpLoad2());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getMaterialUpLoad3()!="" && mm.getMaterialUpLoad3()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad3())+File.separator+mm.getMaterialUpLoad3());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getMaterialUpLoad4()!="" && mm.getMaterialUpLoad4()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad4())+File.separator+mm.getMaterialUpLoad4());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getMaterialUpLoad5()!="" && mm.getMaterialUpLoad5()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad5())+File.separator+mm.getMaterialUpLoad5());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getMaterialUpLoad6()!="" && mm.getMaterialUpLoad6()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad6())+File.separator+mm.getMaterialUpLoad6());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getMaterialUpLoad7()!="" && mm.getMaterialUpLoad7()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad7())+File.separator+mm.getMaterialUpLoad7());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getPosBackImg()!="" && mm.getPosBackImg()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getPosBackImg())+File.separator+mm.getPosBackImg());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getLaborContractImg()!="" && mm.getLaborContractImg()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getLaborContractImg())+File.separator+mm.getLaborContractImg());
		 }else{
			 list2.add(""); 
		 }
		 return list2; 
	 }

	 /**
	  * 导出所有
	  */
	 @Override
	 public List<Map<String, Object>> export(MerchantInfoBean merchantInfo,UserBean user) {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		 boolean flag=false ;

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " t.MCCID, decode(t.isM35,2,'个体',3,'企业',4,'优惠',5,'减免','标准') ISM35,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 " (select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE,t.CREDITBANKRATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.RADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,i.sn mersn, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.AREATYPE ,4,'企业',5,'商户',6,'个人','') AREATYPE "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b ,sys_area s"
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE != 'D' or i.MAINTAINTYPE is null)"
						 + " AND (i.APPROVESTATUS = 'Y' or i.approvestatus is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35!=1 ";
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
							 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
							 " t.MCCID, decode(t.isM35,2,'个体',3,'企业',4,'优惠',5,'减免','标准') ISM35,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
							 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
							 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
							 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
							 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
							 + " t.MID, i.TID, t.BANKFEERATE,t.CREDITBANKRATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
							 + " t.BADDR,t.RADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,i.sn mersn, "
							 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.AREATYPE ,4,'企业',5,'商户',6,'个人','') AREATYPE "
							 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s "
							 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE != 'D' or i.MAINTAINTYPE is null)"
							 + " AND (i.APPROVESTATUS = 'Y' or i.approvestatus is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35!=1 ";
				 }
			 }else if(isAgentformMan(user)){
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " t.MCCID, decode(t.isM35,2,'个体',3,'企业',4,'优惠',5,'减免','标准') ISM35,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE,t.CREDITBANKRATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.RADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,i.sn mersn, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.AREATYPE ,4,'企业',5,'商户',6,'个人','') AREATYPE "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b ,sys_area s "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO = '"+user.getUnNo()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE != 'D' or i.MAINTAINTYPE is null)"
						 + " AND (i.APPROVESTATUS = 'Y' or i.approvestatus is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35!=1 and t.maintainuid = '"+ user.getUserID()+"'";

			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " t.MCCID, decode(t.isM35,2,'个体',3,'企业',4,'优惠',5,'减免','标准') ISM35,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE,t.CREDITBANKRATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.RADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,i.sn mersn, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.AREATYPE ,4,'企业',5,'商户',6,'个人','') AREATYPE "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO IN ("+childUnno+") AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE != 'D' or i.MAINTAINTYPE is null)"
						 + " AND (i.APPROVESTATUS = 'Y' or i.approvestatus is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35!=1 ";
			 }
		 }else{
			 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
					 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
					 " t.MCCID, decode(t.isM35,2,'个体',3,'企业',4,'优惠',5,'减免','标准') ISM35,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
					 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
					 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
					 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
					 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
					 + " t.MID, i.TID, t.BANKFEERATE,t.CREDITBANKRATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
					 + " t.BADDR,t.RADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS ,b.sn,i.sn mersn, "
					 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.AREATYPE ,4,'企业',5,'商户',6,'个人','') AREATYPE "
					 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s "
					 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.BUSID AND (i.MAINTAINTYPE != 'D' or i.MAINTAINTYPE is null)"
					 + " AND (i.APPROVESTATUS = 'Y' or i.approvestatus is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35!=1 ";
		 }
		 sql+="AND t.APPROVESTATUS in ('Y','C')";
		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND t.APPROVESTATUS = '"+merchantInfo.getApproveStatus()+"'";
		 }

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND t.MID = '"+merchantInfo.getMid()+"' ";
			 flag=true;
		 }
		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND t.UNNO IN ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 flag=true;
		 }
		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND t.RNAME LIKE '%"+merchantInfo.getRname()+"%' ";
			 flag=true;
		 }
		 if(merchantInfo.getBaddr() !=null && !merchantInfo.getBaddr().equals("") ){
			 sql +=" AND t.BADDR LIKE '%"+merchantInfo.getBaddr()+"%' ";
			 flag=true;
		 }
		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND t.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE '"+merchantInfo.getTid()+"%' AND MAINTAINTYPE != 'D' and APPROVESTATUS='Y')";
			 flag=true;
		 }
		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND t.BANKACCNO LIKE '%"+merchantInfo.getBankAccNo()+"%'";
			 flag=true;
		 }
		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND t.UNNO = '"+merchantInfo.getUnno()+"' ";
			 flag=true;
		 } 
		 //		if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
		 //			String str = merchantInfo.getApproveUidName();
		 //			String userId = getUserID(merchantInfo.getApproveUidName().trim());
		 //			sql +=" AND t.approveUid = '"+userId+"' ";
		 //			flag=true;					
		 //		} 
		 if (merchantInfo.getLegalNum() != null && !"".equals(merchantInfo.getLegalNum().trim())) {
			 sql +=" AND t.legalNum = '"+merchantInfo.getLegalNum().trim()+"' ";
			 flag=true;					
		 }
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd')  >= '"+merchantInfo.getCreateDateStart()+"' ";
			 sql +=" AND t.approveDate  >= to_date('"+merchantInfo.getCreateDateStart()+"','yyyy-MM-dd') ";
		 }

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd') <= '"+merchantInfo.getCreateDateEnd()+"' ";
			 sql +=" AND t.approveDate < to_date('"+merchantInfo.getCreateDateEnd()+"','yyyy-MM-dd') + 1 ";
		 }
		 if (flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd()==null||merchantInfo.getCreateDateEnd().equals(""))) {
			 //			sql +=" AND trunc(t.approveDate) =trunc(sysdate)";
			 sql +=" AND t.approveDate >=trunc(sysdate)";
		 }
		 if(merchantInfo.getIsM35() !=null && merchantInfo.getIsM35()==6 ){
			 sql +=" AND t.ISM35 = 6 ";
		 }else if(merchantInfo.getIsM35() !=null && merchantInfo.getIsM35()==0 ){
			 sql +=" AND t.ISM35 != 6 ";
		 }
		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
		 return data;

	 }

	 /**
	  * 导出增加归属格式化
	  */
	 public  List<Map<String, Object>> export_addParentUnitName(List<Map<String, Object>> data){
		 List<Map<String, Object>> new_data =null;
		 for(int i=0;i<data.size();i++){

		 }

		 return new_data;
	 }
	 /**
	  * 根据id得到商户
	  */
	 @Override
	 public MerchantInfoModel getInfoModel(Integer id) {
		 return merchantInfoDao.getObjectByID(MerchantInfoModel.class, id);
	 }

	 /**
	  * 根据Mid查询商户明细
	  */
	 @Override
	 public DataGridBean queryMerchantInfoMid(String mid) {
		 String hql = "from MerchantInfoModel where mid = :mid and maintainType != :maintainType";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("mid", mid);
		 map.put("maintainType", "D");
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryObjectsByHqlList(hql,map);
		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, 1);
		 return dataGridBean;
	 }

	 /**
	  * 勾选导出
	  */
	 @Override
	 public List<Map<String, Object>> exportSelected(String ids,UserBean user) {
		 String sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
				 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
				 " t.MCCID, decode(t.isM35,2,'个体',3,'企业',4,'优惠',5,'减免','标准') ISM35,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
				 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
				 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
				 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
				 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
				 + " t.MID, i.TID, t.BANKFEERATE,t.CREDITBANKRATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
				 + " t.BADDR,t.RADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,i.sn mersn, "
				 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod,decode(t.AREATYPE ,4,'企业',5,'商户',6,'个人','') AREATYPE "
				 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a, bill_terminalinfo b ,sys_area s"
				 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE != 'D' or i.MAINTAINTYPE is null) AND t.BMID IN  ("
				 + ids + ") AND (i.APPROVESTATUS = 'Y' or i.approvestatus is null) ";

		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
		 return data;
	 }
	 /**
	  * 手刷勾选导出
	  */
	 @Override
	 public List<Map<String, Object>> exportMicroMerSelected(String ids,UserBean user) {
		 String sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
				 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
				 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
				 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
				 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
				 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
				 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
				 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
				 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
				 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
				 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
				 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' OR i.MAINTAINTYPE IS NULL) AND (i.APPROVESTATUS = 'Y' OR i.APPROVESTATUS IS NULL) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 AND t.mid not like 'H864%' AND t.mid not like 'HRTSYT%' AND t.BMID IN  ("
				 + ids + ")";

		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
		 return data;
	 }

	 @Override
	 public List<Map<String, Object>> exportMicroMerSelectedSyt(String ids,UserBean user) {
		 String sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
				 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
				 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
				 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
				 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
				 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
				 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
				 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
				 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
				 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
				 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
				 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' OR i.MAINTAINTYPE IS NULL) AND (i.APPROVESTATUS = 'Y' OR i.APPROVESTATUS IS NULL) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 AND t.mid like 'HRTSYT%' AND t.BMID IN  ("
				 + ids + ")";

		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
		 return data;
	 }

	 @Override
	 public List<Map<String, Object>> exportMicroMerSelectedPlus(String ids,UserBean user) {
		 String sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
				 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
				 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
				 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
				 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
				 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
				 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
				 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
				 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
				 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
				 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
				 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' OR i.MAINTAINTYPE IS NULL) AND (i.APPROVESTATUS = 'Y' OR i.APPROVESTATUS IS NULL) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 AND t.mid like 'H864%' AND t.BMID IN  ("
				 + ids + ")";

		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
		 return data;
	 }
	 @Override
	 public void updateMerchantInfoY(MerchantInfoBean merchantInfoBean,UserBean user)
			 throws Exception {
		 MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, merchantInfoBean.getBmid());
		 merchantInfoModel.setApproveStatus("Y");

		 String mtimhql = "from MerchantTerminalInfoModel m where mid = :mid and maintainType != :maintainType";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("mid", merchantInfoModel.getMid());
		 map.put("maintainType", "D");
		 List<MerchantTerminalInfoModel> listmtim = merchantTerminalInfoDao.queryObjectsByHqlList(mtimhql, map);
		 for (MerchantTerminalInfoModel mtim : listmtim) {
			 //添加机具工单作业
			 MachineTaskDataModel machineTaskDataModel = new MachineTaskDataModel();
			 machineTaskDataModel.setTaskStartDate(new Date());
			 machineTaskDataModel.setUnno(merchantInfoModel.getUnno());
			 machineTaskDataModel.setMid(merchantInfoModel.getMid());
			 machineTaskDataModel.setType("1");
			 machineTaskDataModel.setBmtID(mtim.getBmtid());
			 machineTaskDataModel.setBmaID(mtim.getBmaid());
			 machineTaskDataModel.setMaintainUid(user.getUserID());
			 machineTaskDataModel.setMaintainDate(new Date());
			 machineTaskDataModel.setMaintainType("A");
			 machineTaskDataModel.setApproveUid(user.getUserID());
			 machineTaskDataModel.setApproveDate(new Date());
			 machineTaskDataModel.setApproveStatus("N");
			 machineTaskDataDao.saveObject(machineTaskDataModel);

			 StringBuffer taskID = new StringBuffer();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			 taskID.append(sdf.format(new Date()));
			 taskID.append("-");
			 taskID.append(merchantInfoModel.getUnno());
			 taskID.append("-");
			 DecimalFormat deFormat = new DecimalFormat("000000");
			 taskID.append(deFormat.format(machineTaskDataDao.getBmtdID()));
			 machineTaskDataModel.setTaskID(taskID.toString());
			 machineTaskDataDao.updateObject(machineTaskDataModel);
		 }

		 merchantInfoDao.updateObject(merchantInfoModel);
	 }

	 @Override
	 public DataGridBean queryMerchantInfoBnoRname(
			 MerchantInfoBean merchantInfoBean) throws Exception {
		 String hql = "from MerchantInfoModel m where m.bno= :bno and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
		 String hqlCount = "select count(*) from MerchantInfoModel m where m.bno = :bno and m.maintainType != :maintainType and m.approveStatus = :approveStatus ";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("bno", merchantInfoBean.getBno());
		 map.put("maintainType", "D");
		 map.put("approveStatus", "Y");

		 long counts = merchantInfoDao.queryCounts(hqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryObjectsByHqlList(hql,map);

		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList , counts);
		 return dataGridBean;
	 }

	 @Override
	 public String getMid(String id) {
		 return merchantInfoDao.getObjectByID(MerchantInfoModel.class, Integer.parseInt(id)).getMid();
	 }

	 @Override
	 public boolean searchMCCById(String mccid) {
		 String sql="select t.MCCCODE from BILL_MM_L_MCC t where t.MCCCODE='"+mccid+"'";
		 List list=merchantInfoDao.queryObjectsBySqlList(sql);
		 if(list!=null && list.size()>0 ){
			 return true;
		 }
		 return false;
	 }

	 @Override
	 public String queryAreaCode(String areaCode) {
		 String aa="";
		 String sql ="select area_code from  sys_area where area_name like '%"+areaCode.trim()+"%'";
		 List<Map<String, String>> s = merchantInfoDao.queryObjectsBySqlList(sql);
		 if(s != null && s.size() > 0){
			 aa=s.get(0).get("AREA_CODE");
		 }
		 return aa;
	 }



	 /**
	  * 查询待审批的微商户list集合
	  */
	 @Override
	 public DataGridBean queryMicroMerchantInfoWJoin(
			 MerchantInfoBean merchantInfo, UserBean userBean) {
		 String hql = "from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 and m.mid not like 'HRTSYT%' and m.mid not like 'H864%' and m.mid not like 'D864%'";
		 String hqlCount = "select count(*) from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1  and m.mid not like 'HRTSYT%' and m.mid not like 'H864%' and m.mid not like 'D864%' ";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("maintainType", "D");


		 if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			 hql+=" and m.rname like :RNAME ";
			 hqlCount +=" and m.rname like :RNAME ";
			 map.put("RNAME", merchantInfo.getRname()+'%');
		 }
		 if(merchantInfo.getMid() != null && !"".equals(merchantInfo.getMid())){
			 hql+=" and m.mid like :MID ";
			 hqlCount +=" and m.mid like :MID ";
			 map.put("MID", merchantInfo.getMid()+"%");
		 }
		 if(merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno())){
			 hql+=" and m.unno =:unno ";
			 hqlCount +=" and m.unno =:unno ";
			 map.put("unno", merchantInfo.getUnno());
		 }
		 hql += " order by BMID DESC";
		 Long count = merchantInfoDao.queryCounts(hqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfo(hql, map, merchantInfo.getPage(), merchantInfo.getRows());

		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, count);

		 return dataGridBean;
	 }
	 @Override
	 public DataGridBean queryMicroMerchantInfoWJoinSyt(
			 MerchantInfoBean merchantInfo, UserBean userBean) {
		 String hql = "from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 and m.mid like 'HRTSYT%' ";
		 String hqlCount = "select count(*) from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 and m.mid like 'HRTSYT%' ";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("maintainType", "D");


		 if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			 hql+=" and m.rname like :RNAME ";
			 hqlCount +=" and m.rname like :RNAME ";
			 map.put("RNAME", merchantInfo.getRname()+'%');
		 }
		 if(merchantInfo.getMid() != null && !"".equals(merchantInfo.getMid())){
			 hql+=" and m.mid like :MID ";
			 hqlCount +=" and m.mid like :MID ";
			 map.put("MID", merchantInfo.getMid()+"%");
		 }
		 if(merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno())){
			 hql+=" and m.unno =:unno ";
			 hqlCount +=" and m.unno =:unno ";
			 map.put("unno", merchantInfo.getUnno());
		 }
		 hql += " order by BMID DESC";
		 Long count = merchantInfoDao.queryCounts(hqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfo(hql, map, merchantInfo.getPage(), merchantInfo.getRows());

		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, count);

		 return dataGridBean;
	 }

	 @Override
	 public DataGridBean queryMicroMerchantInfoWJoinPlus(
			 MerchantInfoBean merchantInfo, UserBean userBean) {
		 String hql = "from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 and m.mid like 'H864%' ";
		 String hqlCount = "select count(*) from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 and m.mid like 'H864%' ";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("maintainType", "D");


		 if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			 hql+=" and m.rname like :RNAME ";
			 hqlCount +=" and m.rname like :RNAME ";
			 map.put("RNAME", merchantInfo.getRname()+'%');
		 }
		 if(merchantInfo.getMid() != null && !"".equals(merchantInfo.getMid())){
			 hql+=" and m.mid like :MID ";
			 hqlCount +=" and m.mid like :MID ";
			 map.put("MID", merchantInfo.getMid()+"%");
		 }
		 if(merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno())){
			 hql+=" and m.unno =:unno ";
			 hqlCount +=" and m.unno =:unno ";
			 map.put("unno", merchantInfo.getUnno());
		 }
		 hql += " order by BMID DESC";
		 Long count = merchantInfoDao.queryCounts(hqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfo(hql, map, merchantInfo.getPage(), merchantInfo.getRows());

		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, count);

		 return dataGridBean;
	 }

	 @Override
	 public DataGridBean queryMicroMerchantInfoWJoin1(
			 MerchantInfoBean merchantInfo, UserBean userBean) {
		 String hql = "from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 ";
		 String hqlCount = "select count(*) from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 ";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("maintainType", "D");


		 if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			 hql+=" and m.rname like :RNAME ";
			 hqlCount +=" and m.rname like :RNAME ";
			 map.put("RNAME", merchantInfo.getRname()+'%');
		 }
		 if(merchantInfo.getMid() != null && !"".equals(merchantInfo.getMid())){
			 hql+=" and m.mid like :MID ";
			 hqlCount +=" and m.mid like :MID ";
			 map.put("MID", merchantInfo.getMid()+"%");
		 }
		 if(merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno())){
			 hql+=" and m.unno =:unno ";
			 hqlCount +=" and m.unno =:unno ";
			 map.put("unno", merchantInfo.getUnno());
		 }
		 hql += " order by BMID DESC";
		 Long count = merchantInfoDao.queryCounts(hqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfo(hql, map, merchantInfo.getPage(), merchantInfo.getRows());

		 DataGridBean dataGridBean = formatToDataGridbaodan(merchantInfoList, count);

		 return dataGridBean;
	 }

	 /**
	  * 查询待审批的微商户明细
	  */
	 @Override
	 public List<Object> queryMerchantMicroInfoDetailed(Integer bmid) {
		 MerchantInfoModel mm=(MerchantInfoModel) merchantInfoDao.queryObjectByHql("from MerchantInfoModel m where m.bmid=?", new Object[]{bmid});
		 String sql="select * from SYS_PARAM t WHERE t.title='HrtFrameWork'";
		 List<Map<String, Object>> list=merchantInfoDao.queryObjectsBySqlList(sql);
		 String upload =(String) list.get(0).get("UPLOAD_PATH");
		 List<Object> list2=new ArrayList<Object>();
		 if(mm.getLegalUploadFileName()!="" && mm.getLegalUploadFileName()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getLegalUploadFileName())+File.separator+mm.getLegalUploadFileName());
		 }else{
			 list2.add("");
		 }
		 if(mm.getMaterialUpLoad()!="" && mm.getMaterialUpLoad()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad())+File.separator+mm.getMaterialUpLoad());
		 }else{
			 list2.add(""); 
		 }
		 if(mm.getMaterialUpLoad1()!="" && mm.getMaterialUpLoad1()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad1())+File.separator+mm.getMaterialUpLoad1());
		 }else{
			 list2.add("");
		 }
		 if(mm.getMaterialUpLoad2()!="" && mm.getMaterialUpLoad2()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad2())+File.separator+mm.getMaterialUpLoad2());
		 }else{
			 list2.add("");
		 }
		 if(mm.getMaterialUpLoad3()!="" && mm.getMaterialUpLoad3()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad3())+File.separator+mm.getMaterialUpLoad3());
		 }else{
			 list2.add("");
		 }
		 if(mm.getBupLoad()!="" && mm.getBupLoad()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getBupLoad())+File.separator+mm.getBupLoad());
		 }else{
			 list2.add("");
		 }
		 if(mm.getMaterialUpLoad5()!="" && mm.getMaterialUpLoad5()!=null){
			 list2.add(upload+File.separator+imagePathUtil(mm.getMaterialUpLoad5())+File.separator+mm.getMaterialUpLoad5());
		 }else{
			 list2.add("");
		 }
		 return list2; 
	 }

	 /**
	  * 批量审批微商户
	  */
	 @Override
	 public boolean updateMerchantMicroInfoCs(MerchantInfoBean merchantInfo,UserBean userBean,String ids) throws Exception{
		 String merchantInfoIds[]=ids.split(",");
		 for (int i = 0; i < merchantInfoIds.length; i++) {
			 merchantInfo.setBmid(Integer.parseInt(merchantInfoIds[i]));
			 updateMerchantMicroInfoC(merchantInfo,userBean);
		 }
		 return true;
	 }
	 /**
	  * 审批通过微商户 修改状态
	  */
	 @Override
	 public MerchantInfoModel updateMerchantMicroInfoC(MerchantInfoBean merchantInfo,
			 UserBean user) throws Exception{

		 MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class,merchantInfo.getBmid());

		 String mtimhql = "from MerchantTerminalInfoModel m where mid = :mid and maintainType != :maintainType";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("mid", merchantInfoModel.getMid());
		 map.put("maintainType", "D");
		 List<MerchantTerminalInfoModel> listmtim = merchantTerminalInfoDao.queryObjectsByHqlList(mtimhql, map);
		 //判断该户下是否有终端，无终端直接拒绝
		 //		if(listmtim.size()==0){
		 //			return null;
		 //		}
		 //更改审批微商户的依据指端： 0 未审批   1已审批
		 //	merchantInfoModel.setSettleStatus("1");
		 merchantInfoModel.setProcessContext(merchantInfo.getProcessContext());
		 merchantInfoModel.setApproveUid(user.getUserID());
		 merchantInfoModel.setApproveDate(new Date());
		 merchantInfoModel.setApproveStatus("C");		//Y-放行   Z-待挂终端 K-踢回 C-审批中  W待审核
		 merchantInfoModel.setJoinConfirmDate(new Date());	//入网时间
		 //授权人员
		 merchantInfoModel.setApproveUid(user.getUserID());
		 merchantInfoModel.setApproveDate(new Date());
		 merchantInfoModel.setCheckConfirmDate(new Date());
		 merchantInfoDao.updateObject(merchantInfoModel);
		 return merchantInfoModel;
	 }

	 /**
	  * 审批微商户
	  */
	 @Override
	 public boolean updateMerchantMicroInfoCToADM(MerchantInfoModel merchantInfoModel,UserBean user) throws Exception {
		 Integer typeFlag;
		 if("000000".equals(merchantInfoModel.getSettMethod())){
			 typeFlag=2;
		 }else{
			 typeFlag=3;
		 }

		 String mtimhql = "from MerchantTerminalInfoModel m where mid = :mid and maintainType != :maintainType";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("mid", merchantInfoModel.getMid());
		 map.put("maintainType", "D");
		 List<MerchantTerminalInfoModel> listmtim = merchantTerminalInfoDao.queryObjectsByHqlList(mtimhql, map);
		 //判断该户下是否有终端，无终端直接拒绝
		 MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(merchantInfoModel, user.getLoginName(), merchantInfoModel.getUnno(), "xxx");
		 UnitModel unit =unitDao.getObjectByID(UnitModel.class, merchantInfoModel.getUnno());
		 Boolean falg =false;
		 try{
			 String firstSql="select k.rebatetype from bill_terminalinfo k where k.termid in ( " +
					 " select t.tid from bill_merchantterminalinfo t where  t.maintaintype!='D' and t.mid='"+merchantInfoModel.getMid()+"' " +
					 ") and k.rebatetype is not null and rownum=1 order by k.usedconfirmdate,k.btid ";
			 List<Map<String,String>> firstRebateType =unitDao.queryObjectsBySqlListMap(firstSql,null);
			 if(firstRebateType.size()==1){
				 info.setRebateType(firstRebateType.get(0).get("REBATETYPE")+"");
			 }
			 info.setUnno(getProvince(unit.getUnNo()));
			 log.info("手刷商户审批:mid="+info.getMid()+";rname="+info.getRname()+";bankaccno="+info.getBankAccNo()+";bankaccno="+info.getBankAccNo()+"bankbranch="+info.getBankBranch());
			 falg=gsClient.saveMerchantInfo(info,"hrtREX1234.Java");
		 }catch (Exception e) {
			 log.info("调用webservice失败"+e);
		 }
		 if(!falg){
			 merchantInfoModel.setApproveStatus("W");
			 merchantInfoDao.updateObject(merchantInfoModel);
			 return false;
		 }

		 for (MerchantTerminalInfoModel mtim : listmtim) {
			 mtim.setApproveUid(user.getUserID());
			 mtim.setApproveDate(new Date());
			 mtim.setApproveStatus("Y");		//Y-已受理   Z-待受理  K-不受理

			 Boolean falg3 =false;
			 try{
				 String terminalHql="from TerminalInfoModel t where t.termID=?";
				 TerminalInfoModel t=terminalInfoDao.queryObjectByHql(terminalHql, new Object[]{mtim.getTid()});
				 //调用webservice，对GMMS添加布放信息
				 TermAcc acc2=new TermAcc();
				 if(t==null){
					 acc2.setSn("000000");
				 }else{
					 MachineInfoModel infoModel2=machineInfoDao.getObjectByID(MachineInfoModel.class, mtim.getBmaid());
					 acc2.setModleId(infoModel2.getMachineModel());
					 acc2.setSn(t.getSn());
					 acc2.setRebateType(t.getRebateType());//返利类型
					 acc2.setKeyConfirmDate(new SimpleDateFormat("yyyyMMdd").format(t.getKeyConfirmDate()));//入网时间
					 acc2.setOutDate(t.getOutDate());
					 acc2.setIsReturnCash(t.getIsReturnCash());
					 acc2.setSnsigndate(t.getAcceptDate());
					 if(!"".equals(t.getSnType())&&t.getSnType()!=null){
						 acc2.setSnType(t.getSnType().toString());
					 }
				 }
//				 if(!"".equals(mtim.getDepositFlag())&&mtim.getDepositFlag()!=null){
					 if(t!=null && t.getRebateType()!=null){
					 	Map configInfo = getSelfTermConfig(t.getRebateType(),t.getDepositAmt());
					 	if(configInfo!=null) {
							if (configInfo.containsKey("depositamt")) {
								acc2.setDepositAmt(Integer.parseInt(configInfo.get("depositamt").toString()));
							}
							if (configInfo.containsKey("tradeamt")) {
							    acc2.setFirmoney(Double.parseDouble(configInfo.get("tradeamt").toString()));
							}
							if (configInfo.containsKey("adm_deposit_flag")) {
								acc2.setDepositFlag(Integer.parseInt(configInfo.get("adm_deposit_flag").toString()));
								 mtim.setDepositFlag(Integer.parseInt(configInfo.get("app_deposit_flag").toString()));
							}
						}
					 }
//				 }
				 merchantTerminalInfoDao.updateObject(mtim);
				 if(t!=null && t.getRebateType()==null && t.getDepositAmt()!=null&&t.getDepositAmt()>0){
					 acc2.setDepositAmt(t.getDepositAmt());
				 }
				 acc2.setCby(user.getLoginName());
				 acc2.setMid(mtim.getMid());
				 acc2.setTid(mtim.getTid());
				 acc2.setTypeflag(typeFlag);
				 acc2.setUnno(info.getUnno());
				 log.info("手刷商户审批:mid="+acc2.getMid()+";tid="+acc2.getTid()+";amt="+acc2.getDepositAmt()+";flag="+
						 acc2.getDepositFlag()+";rebatetype="+acc2.getRebateType()+",isReturnCash="+acc2.getIsReturnCash());
				 falg3=gsClient.saveTermAccSub(acc2,"hrtREX1234.Java");
			 }catch (Exception e) {
				 log.info("调用webservice失败"+e);
			 }
			 if(!falg3){
				 merchantInfoModel.setApproveStatus("W");
				 merchantInfoDao.updateObject(merchantInfoModel);
				 mtim.setApproveStatus("Z");
				 merchantTerminalInfoDao.updateObject(mtim);
				 return false;
			 }
		 }
		 todoDao.editStatusTodo("bill_merchant",merchantInfoModel.getBmid().toString());
		 return true;
	 }

	/**
	 * 获取活动配置
	 * @param rebateType 活动类型
	 * @param amt 终端押金金额
	 * @return Map
	 * 	@key depositamt 押金金额
	 * 	@key tradeamt   最低消费金额
	 * 	@key adm_deposit_flag 综合推送值
	 * 	@key app_deposit_flag app推送值
	 */
	 @Override
	 public Map getSelfTermConfig(Integer rebateType,Integer amt){
	 	Map result = new HashMap();
		String amtSql="select k.depositamt,k.tradeamt,k.remark,k.configinfo from bill_purconfigure k where k.type=3 and k.valueinteger=:valueinteger ";
		Map amtMap = new HashMap();
		amtMap.put("valueinteger",rebateType);
		List<Map<String,Object>> listAmt = terminalInfoDao.queryObjectsBySqlListMap2(amtSql,amtMap);
		if(listAmt.size()==1){
			// 默认最低消费
			if(listAmt.get(0).get("TRADEAMT")!=null) {
				result.put("tradeamt", listAmt.get(0).get("TRADEAMT"));
			}
			if(null!=listAmt.get(0).get("REMARK")) {
				// {"app_deposit_flag":5,"adm_deposit_flag",5}
				com.alibaba.fastjson.JSONObject configJson = com.alibaba.fastjson.JSONObject.parseObject(listAmt.get(0).get("REMARK").toString());
				if (configJson.containsKey("adm_deposit_flag") && null != configJson.get("adm_deposit_flag")) {
					result.put("adm_deposit_flag", configJson.getInteger("adm_deposit_flag"));
					result.put("app_deposit_flag", configJson.getInteger("app_deposit_flag"));
				}
			}
			if(null!=listAmt.get(0).get("CONFIGINFO")) {
				// {"tradeAmt":{"294":"300.00"}}
				com.alibaba.fastjson.JSONObject configJson = com.alibaba.fastjson.JSONObject.parseObject(listAmt.get(0).get("CONFIGINFO").toString());
				com.alibaba.fastjson.JSONObject amtJson = configJson.getJSONObject("tradeAmt");
				if (amt!=null && amtJson.containsKey(amt+"") && null != amtJson.get(amt+"")) {
					// 自定义最低消费
					result.put("depositamt",amt);
					result.put("tradeamt",amtJson.getString(amt+""));
				}
			}

			// 设备表的押金在配置里能匹配到就设置设备的押金,若不匹配取设置配置里的默认押金,若配置无设置则设置设备表里的押金
			if(result.containsKey("depositamt")){
				// 与多挡想匹配
			}else{
				if(listAmt.get(0).get("DEPOSITAMT")!=null) {
					// 多挡不匹配,直接取默认押金
					result.put("depositamt", listAmt.get(0).get("DEPOSITAMT"));
				}else{
					// 无默认押金,设置为设备表里的押金
					if(amt!=null) {
						result.put("depositamt", amt);
					}
				}
			}
		}
		return result;
	 }


	 /**
	  * 退回微商户
	  */
	 @Override
	 public void updateMerchantMicroInfoK(MerchantInfoBean merchantInfo,UserBean user) {
		 MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, merchantInfo.getBmid());
		 merchantInfoModel.setProcessContext(merchantInfo.getProcessContext());
		 merchantInfoModel.setApproveUid(user.getUserID());
		 merchantInfoModel.setApproveDate(new Date());
		 merchantInfoModel.setApproveStatus("K");		//Y-放行   Z-待放行  K-踢回
		 merchantInfoDao.updateObject(merchantInfoModel);
		 todoDao.editStatusTodo("bill_merchant",merchantInfo.getBmid().toString());
	 }

	 @Override
	 public String querySendCode(String sendCode) {
		 String aa="";
		 String sql ="select send_code from  BILL_CUPSENDCODEINFO where send_name like '%"+sendCode.trim()+"%'";
		 List<Map<String, String>> s = merchantInfoDao.queryObjectsBySqlList(sql);
		 if(s != null && s.size() > 0){
			 aa=s.get(0).get("SEND_CODE");
		 }
		 return aa;
	 }

	 @Override
	 public void updateMerchantInfoY(MerchantInfoModel model,UserBean user)throws Exception  {
		 model.setApproveStatus("Y");

		 String mtimhql = "from MerchantTerminalInfoModel m where mid = :mid and maintainType != :maintainType";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("mid", model.getMid());
		 map.put("maintainType", "D");
		 List<MerchantTerminalInfoModel> listmtim = merchantTerminalInfoDao.queryObjectsByHqlList(mtimhql, map);
		 for (MerchantTerminalInfoModel mtim : listmtim) {
			 //添加机具工单作业
			 MachineTaskDataModel machineTaskDataModel = new MachineTaskDataModel();
			 machineTaskDataModel.setTaskStartDate(new Date());
			 machineTaskDataModel.setUnno(model.getUnno());
			 machineTaskDataModel.setMid(model.getMid());
			 machineTaskDataModel.setType("1");
			 machineTaskDataModel.setBmtID(mtim.getBmtid());
			 machineTaskDataModel.setBmaID(mtim.getBmaid());
			 machineTaskDataModel.setMaintainUid(user.getUserID());
			 machineTaskDataModel.setMaintainDate(new Date());
			 machineTaskDataModel.setMaintainType("A");
			 machineTaskDataModel.setApproveUid(user.getUserID());
			 machineTaskDataModel.setApproveDate(new Date());
			 machineTaskDataModel.setApproveStatus("N");
			 machineTaskDataDao.saveObject(machineTaskDataModel);

			 StringBuffer taskID = new StringBuffer();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			 taskID.append(sdf.format(new Date()));
			 taskID.append("-");
			 taskID.append(model.getUnno());
			 taskID.append("-");
			 DecimalFormat deFormat = new DecimalFormat("000000");
			 taskID.append(deFormat.format(machineTaskDataDao.getBmtdID()));
			 machineTaskDataModel.setTaskID(taskID.toString());
			 machineTaskDataDao.updateObject(machineTaskDataModel);
		 }

		 merchantInfoDao.updateObject(model);

	 }

	 @Override
	 public DataGridBean queryMerchantMicroInfoZK(MerchantInfoBean merchantInfo,UserBean user) {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%'  and mid not like 'H864%' and mid not like 'D864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%'  and mid not like 'H864%' and mid not like 'D864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
					 map.put("maintainType", "D");
					 map.put("approveStatus", "Y");
				 }
			 }else if(isAgentformMan(user)){//判断是否是代理商报单员
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
				 map.put("MaintainUID", user.getUserID());
			 }else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");

			 }else{				
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }
		 }else{
			 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid not like 'HRTSYT%' and mid not like 'H864%' and mid not like 'D864%' ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
			 map.put("approveStatus", "Y");
		 }

		 if(merchantInfo.getMid()!=null && !"".equals(merchantInfo.getMid())){
			 sql += " and MID=:MID";
			 sqlCount += " and MID=:MID";
			 map.put("MID", merchantInfo.getMid());
		 }
		 if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			 sql += " and RNAME LIKE '"+merchantInfo.getRname()+"%'";
			 sqlCount += " and RNAME LIKE '"+merchantInfo.getRname()+"%'";
		 }
		 if(merchantInfo.getApproveStatus()!=null &&!"".equals(merchantInfo.getApproveStatus())){
			 sql+=" and approveStatus=:approveStatus2";
			 sqlCount+=" and approveStatus=:approveStatus2";
			 map.put("approveStatus2", merchantInfo.getApproveStatus());
		 }
		 sql += " ORDER BY BMID DESC";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());

		 return dataGridBean;
	 }

	 @Override
	 public DataGridBean queryMerchantMicroInfoZKSyt(MerchantInfoBean merchantInfo,UserBean user) {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
					 map.put("maintainType", "D");
					 map.put("approveStatus", "Y");
				 }
			 }else if(isAgentformMan(user)){//判断是否是代理商报单员
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID AND ISM35=1 and mid like 'HRTSYT%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID AND ISM35=1 and mid like 'HRTSYT%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
				 map.put("MaintainUID", user.getUserID());
			 }else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");

			 }else{
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }
		 }else{
			 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'HRTSYT%' ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
			 map.put("approveStatus", "Y");
		 }

		 if(merchantInfo.getMid()!=null && !"".equals(merchantInfo.getMid())){
			 sql += " and MID=:MID";
			 sqlCount += " and MID=:MID";
			 map.put("MID", merchantInfo.getMid());
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
		 sql += " ORDER BY BMID DESC";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());

		 return dataGridBean;
	 }

	 @Override
	 public DataGridBean queryMerchantMicroInfoZKPlus(MerchantInfoBean merchantInfo,UserBean user) {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
					 map.put("maintainType", "D");
					 map.put("approveStatus", "Y");
				 }
			 }else if(isAgentformMan(user)){//判断是否是代理商报单员
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID AND ISM35=1 and mid like 'H864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID AND ISM35=1 and mid like 'H864%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
				 map.put("MaintainUID", user.getUserID());
			 }else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");

			 }else{
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }
		 }else{
			 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'H864%' ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
			 map.put("approveStatus", "Y");
		 }

		 if(merchantInfo.getMid()!=null && !"".equals(merchantInfo.getMid())){
			 sql += " and MID=:MID";
			 sqlCount += " and MID=:MID";
			 map.put("MID", merchantInfo.getMid());
		 }
		 if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
			 sql += " and RNAME LIKE '"+merchantInfo.getRname()+"%'";
			 sqlCount += " and RNAME LIKE '"+merchantInfo.getRname()+"%'";
		 }
		 if(merchantInfo.getApproveStatus()!=null &&!"".equals(merchantInfo.getApproveStatus())){
			 sql+=" and approveStatus=:approveStatus2";
			 sqlCount+=" and approveStatus=:approveStatus2";
			 map.put("approveStatus2", merchantInfo.getApproveStatus());
		 }
		 sql += " ORDER BY BMID DESC";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());

		 return dataGridBean;
	 }

	 @Override
	 public void saveMerchantMicroInfo(MerchantInfoBean merchantInfo,
			 UserBean user ) {
		 MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
		 BeanUtils.copyProperties(merchantInfo, merchantInfoModel);

		 String ip=null;
		 HttpServletRequest request=ServletActionContext.getRequest();
		 if (request.getHeader("x-forwarded-for") == null) {
			 ip=request.getRemoteAddr();
		 }
		 else{
			 ip=request.getHeader("x-forwarded-for");
		 }
		 merchantInfoModel.setIp(ip);
		 //传统商户 isM35:0
		 merchantInfoModel.setIsM35(1);
		 //注册地址拼接
		 merchantInfoModel.setBaddr((merchantInfo.getProvince().trim()+merchantInfo.getCity().trim()+merchantInfo.getBaddr()).trim().toString());
		 //营业地址拼接
		 merchantInfoModel.setRaddr((merchantInfo.getProvince().trim()+merchantInfo.getCity().trim()+merchantInfo.getRaddr()).trim().toString());

		 //实时交易查询次数
		 merchantInfoModel.setRealtimeQueryTimes(20);

		 //合同起始时间
		 merchantInfoModel.setContractPeriod(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());

		 //银联卡费率
		 Double bankFeeRate = Double.parseDouble(merchantInfo.getBankFeeRate())/100;
		 merchantInfoModel.setBankFeeRate(bankFeeRate.toString());
		 //贷记卡费率
		 if(merchantInfo.getCreditBankRate()!=null&&!"".equals(merchantInfo.getCreditBankRate())){
			 Double creditBankRate = Double.parseDouble(merchantInfo.getCreditBankRate())/100;
			 merchantInfoModel.setCreditBankRate(creditBankRate.toString());
		 }else{
			 merchantInfoModel.setCreditBankRate("0");
		 }
		 //merchantInfoModel.setCreditBankRate("0");
		 //节假日是否合并结账 0：否 1：是
		 merchantInfoModel.setSettleMerger("1");

		 //商户编号生成
		 StringBuffer mid = new StringBuffer("864");
		 String mrcioRule=merchantInfoDao.querySavePath("MicroRule");
		 mid.append(mrcioRule.trim());
		 mid.append("5311");
		 Integer seqNo = searchMIDSeqInfo("000","5311","xxx");
		 //		Integer seqNo = 600;
		 //		if(midSeqInfoModelList != null && midSeqInfoModelList.size() > 0){
		 //			MIDSeqInfoModel midSeqInfoModel = midSeqInfoModelList.get(0);
		 //			seqNo = midSeqInfoModel.getSeqNo() + 1;
		 //			midSeqInfoModel.setSeqNo(seqNo);
		 //			midSeqInfoDao.updateObject(midSeqInfoModel);
		 //		}
		 DecimalFormat deFormat = new DecimalFormat("0000");
		 mid.append(deFormat.format(seqNo));
		 merchantInfoModel.setMid(mid.toString());

		 //封顶值
		 if("000000".equals(merchantInfo.getSettMethod())){
			 if(!"0".equals(merchantInfo.getBankFeeRate()) && !"0".equals(merchantInfo.getFeeAmt())){
				 merchantInfoModel.setDealAmt(merchantInfo.getDealAmt());
			 }else{
				 merchantInfoModel.setDealAmt("0");
				 merchantInfoModel.setFeeAmt("0");
			 }
			 //结算方式：手刷大额商户：日结
			 merchantInfoModel.setSettMethod("000000");
		 }else{
			 merchantInfoModel.setDealAmt("0");
			 merchantInfoModel.setFeeAmt("0");
		 }
		 //		QRToImageWriter writer= new QRToImageWriter();
		 //		merchantInfoModel =writer.doQR(merchantInfoModel);
		 //10位随机数
		 String sign="";
		 for(int i=0;i<10;i++){
			 sign=sign+String.valueOf((char)(Math.random()*26+65));
		 }
		 String content = ParamPropUtils.props.get("xpayPath")+"/qrpayment?mid="+merchantInfoModel.getMid()+"&sign="+sign;
		 merchantInfoModel.setQrUrl(content);
		 merchantInfoModel.setQrUpload("0");

		 //商户账单名称为空则和商户注册名称一样
		 if(merchantInfoModel.getShortName() == null || "".equals(merchantInfoModel.getShortName().trim())){
			 merchantInfoModel.setShortName(merchantInfo.getRname());
		 }

		 //凭条打印名称为空泽合商户注册名称一样
		 if(merchantInfoModel.getPrintName() == null || "".equals(merchantInfoModel.getPrintName().trim())){
			 merchantInfoModel.setPrintName(merchantInfo.getRname());
		 }

		 StringBuffer bank = new StringBuffer();
		 if(!"其他".equals(merchantInfo.getBankSendCode().trim())){
			 bank.append(merchantInfo.getBankSendCode());
		 }
		 if(!"00".equals(merchantInfo.getBankProvinceCode())){
			 bank.append(merchantInfo.getBankProvinceCode());
		 }
		 bank.append(merchantInfo.getBankName());
		 merchantInfoModel.setBankBranch(bank.toString());

		 //判断开户银行中是否有交通银行几个字
		 if(bank.toString().indexOf("交通银行") == -1){
			 merchantInfoModel.setBankType("2");		//非交通银行
		 }else{
			 merchantInfoModel.setBankType("1");		//交通银行
		 }

		 AgentSalesModel agentSalesModel = agentSalesDao.getObjectByID(AgentSalesModel.class, merchantInfo.getBusid());
		 String unno=agentSalesModel.getUnno();
		 merchantInfoModel.setUnno(agentSalesModel.getUnno());
		 merchantInfoModel.setApproveStatus("Z");		//默认为待放行 Y-放行   Z-待挂银行终端  W-待审核  K-踢回
		 if(user.getUnNo().equals("901000")){
			 merchantInfoModel.setMaintainUserId(merchantInfo.getMaintainUserId());		
		 }else{
			 merchantInfoModel.setMaintainUserId(merchantInfo.getBusid());		//添加的时候业务人员和维护人员一样
		 }
		 merchantInfoModel.setMaintainUid(user.getUserID());		//操作人员
		 merchantInfoModel.setMaintainDate(new Date());			//操作日期
		 merchantInfoModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
		 merchantInfoModel.setSettleStatus("1");		//结算状态 1-正常 2-冻结
		 merchantInfoModel.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销

		 //节假日是否合并结账 0：否 1：是
		 merchantInfoModel.setSettleMerger("1");
		 //经营范围
		 merchantInfoModel.setBusinessScope("全行业");
		 //区域码
		 merchantInfoModel.setAreaCode("010");
		 //行业编码
		 merchantInfoModel.setMccid("5311");
		 //结算周期
		 merchantInfoModel.setSettleCycle(0);

		 //上传文件
		 if(merchantInfo.getLegalUploadFile() != null ){
			 StringBuffer fName1 = new StringBuffer();
			 fName1.append(unno);
			 fName1.append(".");
			 fName1.append(merchantInfoModel.getMid());
			 fName1.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName1.append(imageDay);
			 fName1.append(".1");
			 fName1.append(".jpg");
			 uploadFile(merchantInfo.getLegalUploadFile(),fName1.toString(),imageDay);
			 merchantInfoModel.setLegalUploadFileName(fName1.toString());
		 }

		 if(merchantInfo.getMaterialUpLoadFile() != null){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".2");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoadFile(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad(fName2.toString());
		 }

		 if(merchantInfo.getMaterialUpLoad1File() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".3");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoad1File(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad1(fName2.toString());
		 }

		 if(merchantInfo.getMaterialUpLoad2File() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".4");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoad2File(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad2(fName2.toString());
		 }

		 if(merchantInfo.getMaterialUpLoad3File() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".5");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoad3File(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad3(fName2.toString());
		 }

		 if(merchantInfo.getBupLoadFile() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".6");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getBupLoadFile(),fName2.toString(),imageDay);
			 merchantInfoModel.setBupLoad(fName2.toString());
		 }

		 if(merchantInfo.getMaterialUpLoad5File() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".7");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad5(fName2.toString());
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

		 merchantInfoDao.saveObject(merchantInfoModel);
		 MerchantBankCardModel mbc = new MerchantBankCardModel();
		 mbc.setBankAccName(merchantInfoModel.getBankAccName());
		 mbc.setBankAccNo(merchantInfoModel.getBankAccNo());
		 mbc.setCreateDate(new Date());
		 mbc.setMerCardType(0);
		 mbc.setStatus(0);
		 mbc.setBankBranch(merchantInfoModel.getBankBranch());
		 mbc.setPayBankId(merchantInfoModel.getPayBankId());
		 mbc.setMid(merchantInfoModel.getMid());

		 merchantTaskDetailDao.saveObject(mbc);
	 }

	 @Override
	 public boolean updateMerchantMicroInfo(MerchantInfoBean merchantInfo,UserBean user) {
		 //		MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, merchantInfo.getBmid());
		 //		if(merchantInfoModel.getApproveStatus().equals(merchantInfo.getApproveStatus())){
		 //			merchantInfo.setBankArea(merchantInfoModel.getBankArea());
		 //			merchantInfo.setSettMethod(merchantInfoModel.getSettMethod());
		 MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
		 BeanUtils.copyProperties(merchantInfo, merchantInfoModel);

		 String ip=null;
		 HttpServletRequest request=ServletActionContext.getRequest();
		 if (request.getHeader("x-forwarded-for") == null) {
			 ip=request.getRemoteAddr();
		 }
		 else{
			 ip=request.getHeader("x-forwarded-for");
		 }
		 merchantInfoModel.setIp(ip);
		 //传统商户 isM35:0
		 merchantInfoModel.setIsM35(1);

		 //实时交易查询次数
		 merchantInfoModel.setRealtimeQueryTimes(20);

		 //合同起始时间
		 merchantInfoModel.setContractPeriod(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());

		 //银联卡费率
		 Double bankFeeRate = Double.parseDouble(merchantInfo.getBankFeeRate())/100;
		 merchantInfoModel.setBankFeeRate(bankFeeRate.toString());

		 //贷记卡费率
		 if(merchantInfo.getCreditBankRate()!=null&&!"".equals(merchantInfo.getCreditBankRate())){
			 Double creditBankRate = Double.parseDouble(merchantInfo.getCreditBankRate())/100;
			 merchantInfoModel.setCreditBankRate(creditBankRate.toString());
		 }else{
			 merchantInfoModel.setCreditBankRate("0");
		 }
		 //封顶值
		 if("000000".equals(merchantInfo.getSettMethod())){
			 if(!"0".equals(merchantInfo.getBankFeeRate()) && !"0".equals(merchantInfo.getFeeAmt())){
				 merchantInfoModel.setDealAmt(merchantInfo.getDealAmt());
			 }else{
				 merchantInfoModel.setDealAmt("0");
				 merchantInfoModel.setFeeAmt("0");
			 }
			 //结算方式：手刷大额商户：日结
			 merchantInfoModel.setSettMethod("000000");
		 }else{
			 merchantInfoModel.setDealAmt("0");
			 merchantInfoModel.setFeeAmt("0");
		 }

		 //商户账单名称为空则和商户注册名称一样
		 if(merchantInfoModel.getShortName() == null || "".equals(merchantInfoModel.getShortName().trim())){
			 merchantInfoModel.setShortName(merchantInfo.getRname());
		 }

		 //凭条打印名称为空泽合商户注册名称一样
		 if(merchantInfoModel.getPrintName() == null || "".equals(merchantInfoModel.getPrintName().trim())){
			 merchantInfoModel.setPrintName(merchantInfo.getRname());
		 }

		 merchantInfoModel.setBankBranch(merchantInfo.getBankBranch());

		 //判断开户银行中是否有交通银行几个字
		 if(merchantInfo.getBankBranch().toString().indexOf("交通银行") == -1){
			 merchantInfoModel.setBankType("2");		//非交通银行
		 }else{
			 merchantInfoModel.setBankType("1");		//交通银行
		 }

		 AgentSalesModel agentSalesModel = agentSalesDao.getObjectByID(AgentSalesModel.class, merchantInfo.getBusid());
		 String unno=agentSalesModel.getUnno();
		 merchantInfoModel.setUnno(agentSalesModel.getUnno());
		 if("W".equals(merchantInfoModel.getApproveStatus()) || "K".equals(merchantInfoModel.getApproveStatus())){
			 merchantInfoModel.setApproveStatus("W");
		 }else{
			 merchantInfoModel.setApproveStatus("Z"); //  Y-放行   Z-待挂银行终端  W-待审核  K-踢回
		 }	
		 if(user.getUnNo().equals("901000")){
			 merchantInfoModel.setMaintainUserId(merchantInfo.getMaintainUserId());		
		 }else{
			 merchantInfoModel.setMaintainUserId(merchantInfo.getBusid());		//添加的时候业务人员和维护人员一样
		 }
		 merchantInfoModel.setMaintainUid(user.getUserID());		//操作人员
		 merchantInfoModel.setMaintainDate(new Date());			//操作日期
		 merchantInfoModel.setMaintainType("M");		//A-add   M-Modify  D-Delete
		 merchantInfoModel.setSettleStatus("1");		//结算状态 1-正常 2-冻结
		 merchantInfoModel.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销

		 //节假日是否合并结账 0：否 1：是
		 merchantInfoModel.setSettleMerger("1");
		 //经营范围
		 merchantInfoModel.setBusinessScope("全行业");
		 //区域码
		 merchantInfoModel.setAreaCode("010");
		 //行业编码
		 merchantInfoModel.setMccid("5311");
		 //结算周期
		 merchantInfoModel.setSettleCycle(0);

		 merchantInfoModel.setMid(merchantInfo.getMid());

		 //上传文件
		 if(merchantInfo.getLegalUploadFile() != null ){
			 StringBuffer fName1 = new StringBuffer();
			 fName1.append(unno);
			 fName1.append(".");
			 fName1.append(merchantInfoModel.getMid());
			 fName1.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName1.append(imageDay);
			 fName1.append(".1");
			 fName1.append(".jpg");
			 uploadFile(merchantInfo.getLegalUploadFile(),fName1.toString(),imageDay);
			 merchantInfoModel.setLegalUploadFileName(fName1.toString());
		 }

		 if(merchantInfo.getMaterialUpLoadFile() != null){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".2");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoadFile(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad(fName2.toString());
		 }

		 if(merchantInfo.getMaterialUpLoad1File() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".3");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoad1File(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad1(fName2.toString());
		 }

		 if(merchantInfo.getMaterialUpLoad2File() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".4");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoad2File(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad2(fName2.toString());
		 }

		 if(merchantInfo.getMaterialUpLoad3File() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".5");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoad3File(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad3(fName2.toString());
		 }

		 if(merchantInfo.getBupLoadFile() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".6");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getBupLoadFile(),fName2.toString(),imageDay);
			 merchantInfoModel.setBupLoad(fName2.toString());
		 }


		 if(merchantInfo.getMaterialUpLoad5File() != null ){
			 StringBuffer fName2 = new StringBuffer();
			 fName2.append(unno);
			 fName2.append(".");
			 fName2.append(merchantInfoModel.getMid());
			 fName2.append(".");
			 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			 fName2.append(imageDay);
			 fName2.append(".7");
			 fName2.append(".jpg");
			 uploadFile(merchantInfo.getMaterialUpLoad5File(),fName2.toString(),imageDay);
			 merchantInfoModel.setMaterialUpLoad5(fName2.toString());
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

		 //			merchantInfoDao.updateObject(merchantInfoModel);
		 String sql ="update BILL_MERCHANTINFO set rname=:rname,bno=:bno,legalPerson=:legalPerson,legalNum=:legalNum,contractNo=:contractNo," +
				 "payBankId=:payBankId,legalExpdate=:legalExpdate,accNum=:accNum,accExpdate=:accExpdate,bankAccNo=:bankAccNo,hybPhone=:hybPhone," +
				 "contactAddress=:contactAddress,contactPerson=:contactPerson,contactPhone=:contactPhone,contactTel=:contactTel,bankAccName=:bankAccName,"+
				 "IP=:IP,ISM35=1,realtimeQueryTimes=20,contractPeriod=:contractPeriod,DEALAMT=:DEALAMT,FEEAMT=:FEEAMT,BANKFEERATE=:BANKFEERATE," +
				 "CREDITBANKRATE=:CREDITBANKRATE,FEERATE_V=:FEERATE_V,FEERATE_M=:FEERATE_M,FEERATE_J=:FEERATE_J,bankBranch=:bankBranch," +
				 "FEERATE_A=:FEERATE_A,FEERATE_D=:FEERATE_D,SETTLEMERGER=:SETTLEMERGER,SETTMETHOD=:SETTMETHOD,SHORTNAME=:SHORTNAME,PRINTNAME=:PRINTNAME,BANKTYPE=:BANKTYPE," +
				 "LEGALUPLOADFILENAME=:LEGALUPLOADFILENAME,BUPLOAD=:BUPLOAD,RUPLOAD=:RUPLOAD,REGISTRYUPLOAD=:REGISTRYUPLOAD,MATERIALUPLOAD=:MATERIALUPLOAD,PHOTOUPLOAD=:PHOTOUPLOAD," +
				 "BIGDEALUPLOAD=:BIGDEALUPLOAD,MATERIALUPLOAD1=:MATERIALUPLOAD1,MATERIALUPLOAD2=:MATERIALUPLOAD2,MATERIALUPLOAD3=:MATERIALUPLOAD3,MATERIALUPLOAD4=:MATERIALUPLOAD4," +
				 "MATERIALUPLOAD5=:MATERIALUPLOAD5,MATERIALUPLOAD6=:MATERIALUPLOAD6,MATERIALUPLOAD7=:MATERIALUPLOAD7,APPROVESTATUS=:APPROVESTATUS,MAINTAINUID=:MAINTAINUID,MAINTAINDATE=:MAINTAINDATE," +
				 "MAINTAINUSERID=:MAINTAINUSERID,MAINTAINTYPE=:MAINTAINTYPE,SETTLESTATUS=:SETTLESTATUS,ACCOUNTSTATUS=:ACCOUNTSTATUS," +
				 "businessScope='全行业',AREA_CODE='010',mccid='5311',settleCycle=0,remarks=:remarks,FEEAMT=:FEEAMT " +
				 "where mid=:MID and EXCHANGE_TIME=:EXCHANGE_TIME and APPROVESTATUS in('K')";

		 Map<String, Object> param = new HashMap<String, Object>();
		 param.put("IP", merchantInfoModel.getIp());
		 param.put("hybPhone", merchantInfoModel.getHybPhone());
		 param.put("bno", merchantInfoModel.getBno());
		 //			param.put("FEEAMT", merchantInfo.getFeeAmt());
		 param.put("remarks", merchantInfoModel.getRemarks());
		 param.put("legalExpdate", merchantInfoModel.getLegalExpdate());
		 param.put("accNum", merchantInfoModel.getAccNum());
		 param.put("bankAccNo", merchantInfoModel.getBankAccNo());
		 param.put("bankAccName", merchantInfoModel.getBankAccName());
		 param.put("accExpdate", merchantInfoModel.getAccExpdate());
		 param.put("contactAddress", merchantInfoModel.getContactAddress());
		 param.put("contactPerson", merchantInfoModel.getContactPerson());
		 param.put("contactPhone", merchantInfoModel.getContactPhone());
		 param.put("contactTel", merchantInfoModel.getContactTel());
		 param.put("legalPerson", merchantInfoModel.getLegalPerson());
		 param.put("legalNum", merchantInfoModel.getLegalNum());
		 param.put("contractNo", merchantInfoModel.getContractNo());
		 param.put("payBankId", merchantInfoModel.getPayBankId());
		 param.put("contractPeriod", merchantInfoModel.getContractPeriod());
		 param.put("DEALAMT", merchantInfoModel.getDealAmt());
		 param.put("FEEAMT", merchantInfoModel.getFeeAmt());
		 param.put("BANKFEERATE", merchantInfoModel.getBankFeeRate());
		 param.put("CREDITBANKRATE", merchantInfoModel.getCreditBankRate());
		 param.put("FEERATE_V", merchantInfoModel.getFeeRateV());
		 param.put("FEERATE_M", merchantInfoModel.getFeeRateM());
		 param.put("FEERATE_J", merchantInfoModel.getFeeRateJ());
		 param.put("FEERATE_A", merchantInfoModel.getFeeRateA());
		 param.put("FEERATE_D", merchantInfoModel.getFeeRateD());
		 param.put("bankBranch", merchantInfoModel.getBankBranch());
		 param.put("SETTLEMERGER", merchantInfoModel.getSettleMerger());
		 param.put("SETTMETHOD", merchantInfoModel.getSettleMethod());
		 param.put("rname", merchantInfoModel.getRname());
		 param.put("SHORTNAME", merchantInfoModel.getShortName());
		 param.put("PRINTNAME", merchantInfoModel.getPrintName());
		 param.put("BANKTYPE", merchantInfoModel.getBankType());
		 param.put("LEGALUPLOADFILENAME", merchantInfoModel.getLegalUploadFileName());
		 param.put("BUPLOAD", merchantInfoModel.getBupLoad());
		 param.put("RUPLOAD", merchantInfoModel.getRupLoad());
		 param.put("REGISTRYUPLOAD", merchantInfoModel.getRegistryUpLoad());
		 param.put("MATERIALUPLOAD", merchantInfoModel.getMaterialUpLoad());
		 param.put("PHOTOUPLOAD", merchantInfoModel.getPhotoUpLoad());
		 param.put("BIGDEALUPLOAD", merchantInfoModel.getBigdealUpLoad());
		 param.put("MATERIALUPLOAD1", merchantInfoModel.getMaterialUpLoad1());
		 param.put("MATERIALUPLOAD2", merchantInfoModel.getMaterialUpLoad2());
		 param.put("MATERIALUPLOAD3", merchantInfoModel.getMaterialUpLoad3());
		 param.put("MATERIALUPLOAD4", merchantInfoModel.getMaterialUpLoad4());
		 param.put("MATERIALUPLOAD5", merchantInfoModel.getMaterialUpLoad5());
		 param.put("MATERIALUPLOAD6", merchantInfoModel.getMaterialUpLoad6());
		 param.put("MATERIALUPLOAD7", merchantInfoModel.getMaterialUpLoad7());
		 param.put("APPROVESTATUS", merchantInfoModel.getApproveStatus());
		 param.put("MAINTAINUID", merchantInfoModel.getMaintainUid());
		 param.put("MAINTAINDATE", merchantInfoModel.getMaintainDate());
		 param.put("MAINTAINUSERID", merchantInfoModel.getMaintainUserId());
		 param.put("MAINTAINTYPE", merchantInfoModel.getMaintainType());
		 param.put("SETTLESTATUS", merchantInfoModel.getSettleStatus());
		 param.put("ACCOUNTSTATUS", merchantInfoModel.getAccountStatus());
		 param.put("MID", merchantInfoModel.getMid());
		 param.put("EXCHANGE_TIME", merchantInfoModel.getExchangTime());

		 Integer i = merchantInfoDao.executeSqlUpdate(sql, param);
		 if(i>0){
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
	 public void updateMerchantMicroInfoY(MerchantInfoBean merchantInfo,
			 UserBean userSession) {
		 MerchantInfoModel merchantInfoModel = merchantInfoDao.getObjectByID(MerchantInfoModel.class, merchantInfo.getBmid());
		 merchantInfoModel.setApproveStatus("Y");
		 merchantInfoDao.updateObject(merchantInfoModel);
	 }

	 @Override
	 public void updateMerchantBatchMicroInfoY(String ids,
			 UserBean userSession) {
		 String sql ="update bill_merchantinfo t set t.ApproveStatus='Y' where t.bmid in("+ids+")";
		 merchantInfoDao.executeUpdate(sql);
	 }

	 @Override
	 public DataGridBean queryMerchantMicroToUnbid(
			 MerchantInfoBean merchantInfo, UserBean user) {
		 if(("".equals(merchantInfo.getMid())||merchantInfo.getMid()==null)
				 &&("".equals(merchantInfo.getRname())||merchantInfo.getRname()==null)
				 &&("".equals(merchantInfo.getApproveStatus())||merchantInfo.getApproveStatus()==null)
				 &&("".equals(merchantInfo.getSn())||merchantInfo.getSn()==null)){
			 return null;
		 }
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT T.*,M.SN FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM  " +
						 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM  " +
						 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 ";
				 map.put("maintainType", "D");
				 map.put("maintainType2", "D");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT T.*,M.SN FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM  " +
							 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM  " +
							 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2  and T.ISM35=1 ";
					 map.put("maintainType", "D");
					 map.put("maintainType2", "D");
				 }
			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = "SELECT T.*,M.SN FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM " +
						 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.UNNO IN ("+childUnno+")"+
						 " AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM " +
						 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.UNNO IN ("+childUnno+")"+
						 " AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 ";
				 map.put("maintainType", "D");
				 map.put("maintainType2", "D");
			 }
		 }else{
			 sql = "SELECT T.*,M.SN FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM" +
					 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINUSERID = :maintainUserId AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM" +
					 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINUSERID = :maintainUserId AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
			 map.put("maintainType2", "D");
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND T.APPROVESTATUS = :approveStatus";
			 sqlCount += " AND T.APPROVESTATUS= :approveStatus";
			 map.put("approveStatus", merchantInfo.getApproveStatus());
		 } 

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND T.MID = :mid";
			 sqlCount += " AND T.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND T.RNAME =:rname";
			 sqlCount += " AND T.RNAME = :rname";
			 map.put("rname",merchantInfo.getRname());
		 }

		 if(merchantInfo.getSn() !=null && !merchantInfo.getSn().equals("") ){
			 sql +=" AND M.SN =:sn ";
			 sqlCount += " AND M.SN =:sn";
			 map.put("sn",merchantInfo.getSn());
		 }

		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND T.BANKACCNO = :bankAccNo";
			 sqlCount += " AND T.BANKACCNO = :bankAccNo";
			 map.put("bankAccNo",merchantInfo.getBankAccNo());
		 }

		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 sql +=" AND T.JOINCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 sqlCount += " AND T.JOINCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
		 } 

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 sql +=" AND T.JOINCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 sqlCount += " AND T.JOINCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND T.UNNO = :merunno";
			 sqlCount += " AND T.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
		 } 
		 sql += " order by t.bmid desc";

		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<Map<String,Object>> merchantInfoList = merchantInfoDao.queryObjectsBySqlList2(sql, map, merchantInfo.getPage(), merchantInfo.getRows());
		 List<Map<String,Object>> merchantInfoListUtil=new ArrayList<Map<String,Object>>();
		 for(int i=0;i<merchantInfoList.size();i++){
			 Map<String,Object> mapUtil = merchantInfoList.get(i);
			 Map<String,Object> mapUtil2 = new HashMap<String, Object>();
			 //机构名称
			 UnitModel unitModelUtil = unitDao.getObjectByID(UnitModel.class, mapUtil.get("UNNO").toString());
			 if(unitModel != null){
				 mapUtil2.put("unno",unitModel.getUnitName());
			 }
			 //将大写字段名改成 与实体一致
			 mapUtil2.put("bmid", mapUtil.get("BMID"));
			 mapUtil2.put("mid", mapUtil.get("MID"));
			 mapUtil2.put("sn", mapUtil.get("SN"));
			 mapUtil2.put("rname", mapUtil.get("RNAME"));
			 mapUtil2.put("joinConfirmDate", mapUtil.get("JOINCONFIRMDATE"));
			 mapUtil2.put("approveStatus", mapUtil.get("APPROVESTATUS"));
			 mapUtil2.put("payBankId", mapUtil.get("PAYBANKID"));
			 mapUtil2.put("bankBranch", mapUtil.get("BANKBRANCH"));
			 mapUtil2.put("bankAccNo", mapUtil.get("BANKACCNO"));
			 mapUtil2.put("contactPerson", mapUtil.get("CONTACTPERSON"));
			 mapUtil2.put("bankAccName", mapUtil.get("BANKACCNAME"));
			 mapUtil2.put("contactPhone", mapUtil.get("CONTACTPHONE"));
			 mapUtil2.put("legalNum", mapUtil.get("LEGALNUM"));

			 merchantInfoListUtil.add(mapUtil2);

		 }
		 //DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());
		 DataGridBean dataGridBean = new DataGridBean();
		 dataGridBean.setRows(merchantInfoListUtil);
		 dataGridBean.setTotal(counts.intValue());
		 return dataGridBean;
	 }

	 /**
	  * 解绑终端号
	  * @param sn
	  * @param mid
	  */
	 @Override
	 public String updateUnbindSn(String sn,String mid){
		 String errMsg=null;
		 //JiuJiuJdbcUtil jjUtil= new JiuJiuJdbcUtil();
		 // @author:lxg-20190904 收银台已激活的设备解绑时,将设备的出库时间改为解绑时间
		 String sql=null;
		 if(StringUtils.isNotEmpty(mid) && mid.contains("HRTSYT")){
			 String sytActSql="select nvl(t.depositflag,-1) from bill_merchantterminalinfo t where t.mid=:mid and t.sn=:sn ";
			 Map map = new HashMap();
			 map.put("mid",mid);
			 map.put("sn",sn);
			 Integer t = merchantInfoDao.querysqlCounts2(sytActSql,map);
			 if(t!=null && t==6){
				 sql = "update bill_terminalinfo set usedconfirmdate = '' , maintaindate = '', status = 1, outdate=sysdate where sn = '"+sn+"'";
			 }else{
				 sql = "update bill_terminalinfo set usedconfirmdate = '' , maintaindate = '', status = 1 where sn = '"+sn+"'";
			 }
		 }else{
			 sql = "update bill_terminalinfo set usedconfirmdate = '' , maintaindate = '', status = 1 where sn = '"+sn+"'";
		 }
		 String sql1 = "select termid from bill_terminalinfo where sn = '"+sn+"'";
		 List<Map<String, String>> list = merchantInfoDao.executeSql(sql1);
		 Map<String, String> map = list.get(0);
		 String tid =(String)map.get("TERMID");
		 String sql2 = "update bill_merchantterminalinfo b set b.maintaintype = 'D',b.maintaindate=sysdate where b.tid = '"+tid+"' and mid = '"+mid+"'";
		 //因为数据库唯一性约束改为删除
		 //		String sql2 = "delete bill_merchantterminalinfo b where b.tid = '"+tid+"' and b.mid = '"+mid+"'";

		 try {
			 //给会员宝后台响应解绑信息。
			 Map<String,String> params = new HashMap<String, String>();
			 params.put("mid",mid);
			 params.put("tid", tid);
			 params.put("sn", sn);
			 String ss=HttpXmlClient.post(admAppIp+"/AdmApp/hybmove/gathering_removeMpos.action", params);
			 log.info("sn解绑通知综合后台服务器"+ss);
			// System.out.println("sn解绑通知综合后台服务器"+ss);
			 boolean flag = (new JSONObject(ss)).getBoolean("success");
			 if(flag){
				 merchantInfoDao.executeUpdate(sql2);
				 //jjUtil.updateJiuJiuInfoToAdmApp(tid,mid);
				 merchantInfoDao.executeUpdate(sql);
			 }else{
				 errMsg="解绑失败";
				 throw new IllegalAccessError("调用webservice解绑终端号失败");
			 }
		 } catch (Exception e) {
			 errMsg="解绑异常";
			 e.printStackTrace();
		 }finally{
			 return errMsg;
		 }
	 }

	 /**
	  * plus解绑-查询
	  * @author ztt
	  * */
	 @Override
	 public DataGridBean queryMerchantMicroToUnbidPlus(
			 MerchantInfoBean merchantInfo, UserBean user) {
		 if(("".equals(merchantInfo.getMid())||merchantInfo.getMid()==null)
				 &&("".equals(merchantInfo.getRname())||merchantInfo.getRname()==null)
				 &&("".equals(merchantInfo.getApproveStatus())||merchantInfo.getApproveStatus()==null)
				 &&("".equals(merchantInfo.getSn())||merchantInfo.getSn()==null)){
			 return null;
		 }
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT T.*,M.SN FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM  " +
						 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1" +
						 " AND (T.mid like 'HRTSYT%'"+
						 " or T.mid like 'H864%' or T.mid like 'D864%')";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM  " +
						 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 "+
						 " AND (T.mid like 'HRTSYT%'"+
						 " or T.mid like 'H864%' or T.mid like 'D864%')";
				 map.put("maintainType", "D");
				 map.put("maintainType2", "D");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT T.*,M.SN FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM  " +
							 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 "+
							 " AND (T.mid like 'HRTSYT%'"+
							 " or T.mid like 'H864%' or T.mid like 'D864%')";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM  " +
							 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2  and T.ISM35=1 "+
							 " AND (T.mid like 'HRTSYT%'"+
							 " or T.mid like 'H864%' or T.mid like 'D864%')";
					 map.put("maintainType", "D");
					 map.put("maintainType2", "D");
				 }
			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = "SELECT T.*,M.SN FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM " +
						 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.UNNO IN ("+childUnno+")"+
						 " AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 "+
						 " AND (T.mid like 'HRTSYT%'"+
						 " or T.mid like 'H864%' or T.mid like 'D864%')";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM " +
						 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.UNNO IN ("+childUnno+")"+
						 " AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 "+
						 " AND (T.mid like 'HRTSYT%'"+
						 " or T.mid like 'H864%' or T.mid like 'D864%')";
				 map.put("maintainType", "D");
				 map.put("maintainType2", "D");
			 }
		 }else{
			 sql = "SELECT T.*,M.SN FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM" +
					 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINUSERID = :maintainUserId AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 "+
					 " AND (T.mid like 'HRTSYT%'"+
					 " or T.mid like 'H864%' or T.mid like 'D864%')";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO T ,BILL_TERMINALINFO M,BILL_MERCHANTTERMINALINFO TM" +
					 " WHERE T.MID=TM.MID AND TM.TID=M.TERMID AND T.MAINTAINUSERID = :maintainUserId AND T.MAINTAINTYPE != :maintainType AND TM.MAINTAINTYPE !=:maintainType2 and T.ISM35=1 "+
					 " AND (T.mid like 'HRTSYT%'"+
					 " or T.mid like 'H864%' or T.mid like 'D864%')";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
			 map.put("maintainType2", "D");
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND T.APPROVESTATUS = :approveStatus";
			 sqlCount += " AND T.APPROVESTATUS= :approveStatus";
			 map.put("approveStatus", merchantInfo.getApproveStatus());
		 } 

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND T.MID = :mid";
			 sqlCount += " AND T.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND T.RNAME =:rname";
			 sqlCount += " AND T.RNAME = :rname";
			 map.put("rname",merchantInfo.getRname());
		 }

		 if(merchantInfo.getSn() !=null && !merchantInfo.getSn().equals("") ){
			 sql +=" AND M.SN =:sn ";
			 sqlCount += " AND M.SN =:sn";
			 map.put("sn",merchantInfo.getSn());
		 }

		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND T.BANKACCNO = :bankAccNo";
			 sqlCount += " AND T.BANKACCNO = :bankAccNo";
			 map.put("bankAccNo",merchantInfo.getBankAccNo());
		 }

		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 sql +=" AND T.JOINCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 sqlCount += " AND T.JOINCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
		 } 

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 sql +=" AND T.JOINCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 sqlCount += " AND T.JOINCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND T.UNNO = :merunno";
			 sqlCount += " AND T.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
		 } 
		 sql += " order by t.bmid desc";

		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<Map<String,Object>> merchantInfoList = merchantInfoDao.queryObjectsBySqlList2(sql, map, merchantInfo.getPage(), merchantInfo.getRows());
		 List<Map<String,Object>> merchantInfoListUtil=new ArrayList<Map<String,Object>>();
		 for(int i=0;i<merchantInfoList.size();i++){
			 Map<String,Object> mapUtil = merchantInfoList.get(i);
			 Map<String,Object> mapUtil2 = new HashMap<String, Object>();
			 //机构名称
			 UnitModel unitModelUtil = unitDao.getObjectByID(UnitModel.class, mapUtil.get("UNNO").toString());
			 if(unitModel != null){
				 mapUtil2.put("unno",unitModel.getUnitName());
			 }
			 //将大写字段名改成 与实体一致
			 mapUtil2.put("bmid", mapUtil.get("BMID"));
			 mapUtil2.put("mid", mapUtil.get("MID"));
			 mapUtil2.put("sn", mapUtil.get("SN"));
			 mapUtil2.put("rname", mapUtil.get("RNAME"));
			 mapUtil2.put("joinConfirmDate", mapUtil.get("JOINCONFIRMDATE"));
			 mapUtil2.put("approveStatus", mapUtil.get("APPROVESTATUS"));
			 mapUtil2.put("payBankId", mapUtil.get("PAYBANKID"));
			 mapUtil2.put("bankBranch", mapUtil.get("BANKBRANCH"));
			 mapUtil2.put("bankAccNo", mapUtil.get("BANKACCNO"));
			 mapUtil2.put("contactPerson", mapUtil.get("CONTACTPERSON"));
			 mapUtil2.put("bankAccName", mapUtil.get("BANKACCNAME"));
			 mapUtil2.put("contactPhone", mapUtil.get("CONTACTPHONE"));
			 mapUtil2.put("legalNum", mapUtil.get("LEGALNUM"));

			 merchantInfoListUtil.add(mapUtil2);

		 }
		 //DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());
		 DataGridBean dataGridBean = new DataGridBean();
		 dataGridBean.setRows(merchantInfoListUtil);
		 dataGridBean.setTotal(counts.intValue());
		 return dataGridBean;
	 }

	 /**
	  * 解绑Plus终端号
	  * @author ztt
	  */
	 public String updateUnbindPlusSn(String sn,String mid){
		 String sql3 ="select t.approvedate ad,t.depositflag df,t.actStatus act from bill_merchantterminalinfo t"
				 +" where t.sn='"+ sn+"'"
				 +" AND t.mid='"+ mid+"'";
		 String data = null;
		 List<Map<String, String>> list = merchantInfoDao.executeSql(sql3);
		 SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
		 if(CollectionUtils.isEmpty(list)) {
			 data = "无此终端请重试";
			 return data;
		 }else {
			 Map<String, String> map = list.get(0);
			 if((null == map.get("DF") && map.get("ACT") != null) || 6 == Integer.parseInt(String.valueOf(map.get("DF")))) {
				 data = "机器已激活，不予解绑";
				 return data;
			 }
			 if(null == map.get("AD") ||  
					 true == fmt.format(map.get("AD")).toString().equals(fmt.format(new Date()).toString())) {
				 data = "入网当日不可解绑";
				 return data;
			 }
		 }
		 String msg=updateUnbindSn(sn,mid);
		 data = "解绑成功";
		 return StringUtils.isNotEmpty(msg)?msg:data;
	 }

	 @Override
	 public boolean updateMoreSales(String xlsfile, UserBean user) {
		 boolean flag=false;
		 try {
			 File filename = new File(xlsfile);
			 HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			 HSSFSheet sheet = workbook.getSheetAt(0);
			 for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				 HSSFRow row = sheet.getRow(i);
				 HSSFCell cell;
				 String midValue="";
				 String salesNameValue="";
				 cell = row.getCell((short)0);
				 if(cell == null || cell.toString().trim().equals("")){
					 flag=false;
					 break;
				 }else{
					 //mid
					 row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					 midValue = row.getCell((short)0).getStringCellValue().trim();
					 //销售名称
					 row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					 salesNameValue=row.getCell((short)1).getStringCellValue().trim();
					 String sqlSales="select busid from bill_agentsalesinfo t where t.salename='"+salesNameValue+"' and t.maintaintype !='D'";	
					 List<Map<String, Object>> salesList= agentSalesDao.queryObjectsBySqlObject(sqlSales);
					 if(salesList.size()>0){
						 Map<String, Object> map=salesList.get(0);
						 Integer busid=Integer.parseInt(map.get("BUSID").toString());
						 String updateSql="update bill_merchantinfo t set t.maintainuserid='"+busid+"' where t.mid='"+midValue+"'";
						 merchantInfoDao.executeUpdate(updateSql);
						 flag=true;
					 }else{
						 flag=false;
						 break;
					 }
				 }				
			 }		
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }

		 return flag;
	 }

	 @Override
	 public boolean saveMoreMerchantInfo(String xlsfile, UserBean user,String fileName2) {
		 boolean flag=false;
		 try {
			 String sql=" insert into Bill_BP_FILE (fname,ftype,status,cdate,cby)" +
					 " VALUES ('"+fileName2+"',0,0,sysdate,'"+user.getUnitName()+"')";
			 merchantInfoTempDao.executeUpdate(sql);
			 Integer fid=merchantInfoTempDao.getFid();

			 File filename = new File(xlsfile);
			 HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			 HSSFSheet sheet = workbook.getSheetAt(0);
			 List<Object> batchList = new ArrayList<Object>();
			 for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				 HSSFRow row = sheet.getRow(i);
				 HSSFCell cell;
				 MerchantInfoTempModel merch = new MerchantInfoTempModel();
				 cell = row.getCell((short)0);
				 if(cell == null || cell.toString().trim().equals("")){
					 break;
				 }else{
					 //unno
					 row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setUnno(row.getCell((short)0).getStringCellValue().trim());
					 //mccid
					 row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setMccid(row.getCell((short)1).getStringCellValue().trim());
					 //商户名称
					 row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setRname((row.getCell((short)2).getStringCellValue().trim()));

					 //商户注册地址
					 row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setRaddr(((row.getCell((short)3).getStringCellValue().trim())));
					 //4.商户安装地址省
					 row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
					 String province=row.getCell((short)4).getStringCellValue().trim()+"";
					 //商户归属地区 市
					 row.getCell((short)5).setCellType(Cell.CELL_TYPE_STRING);
					 String city=row.getCell((short)5).getStringCellValue().trim()+"";
					 merch.setAreaAddr(city);
					 //商户安装地址 详细
					 row.getCell((short)6).setCellType(Cell.CELL_TYPE_STRING);
					 String detail=row.getCell((short)6).getStringCellValue().trim()+"";
					 merch.setBaddr(province+city+detail);
					 //商户联系电话
					 row.getCell((short)7).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setContactPhone((((row.getCell((short)7).getStringCellValue().trim()))));

					 //商户联系人
					 row.getCell((short)8).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setContactPerson((((row.getCell((short)8).getStringCellValue().trim()))));
					 //法人
					 row.getCell((short)9).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setLegalPerson((((row.getCell((short)9).getStringCellValue().trim()))));
					 //法人身份证号
					 row.getCell((short)10).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setLegalNum((((row.getCell((short)10).getStringCellValue().trim()))));
					 //营业执照编号
					 row.getCell((short)11).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setBno((((row.getCell((short)11).getStringCellValue().trim()))));
					 //入账对公对私
					 row.getCell((short)12).setCellType(Cell.CELL_TYPE_STRING);
					 String accType=(row.getCell((short)12).getStringCellValue().trim());
					 if("对公".equals(accType)){
						 merch.setAccType("1");
					 }else{
						 merch.setAccType("2");
					 }
					 //merch.setAccType(((row.getCell((short)11).getStringCellValue().trim())));
					 //开户行名称
					 row.getCell((short)13).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setBankBranch(((row.getCell((short)13).getStringCellValue().trim())));
					 //开户行账号
					 row.getCell((short)14).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setBankAccNo(((row.getCell((short)14).getStringCellValue().trim())));
					 //银行账户名称
					 row.getCell((short)15).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setBankAccName(((row.getCell((short)15).getStringCellValue().trim())));
					 //支付系统行号
					 row.getCell((short)16).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setPayBankId(((row.getCell((short)16).getStringCellValue().trim())));

					 //入账人证件号码
					 row.getCell((short)17).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setAccNum(((row.getCell((short)17).getStringCellValue().trim())));

					 //借记卡签约费率
					 row.getCell((short)18).setCellType(Cell.CELL_TYPE_NUMERIC);
					 double aa=row.getCell((short)18).getNumericCellValue()/100;
					 merch.setBankFeeRate(aa+"");

					 //借记卡大额手续费
					 row.getCell((short)19).setCellType(Cell.CELL_TYPE_NUMERIC);
					 double bb=row.getCell((short)19).getNumericCellValue();
					 merch.setFeeAmt(bb+"");
					 //是否大小额
					 //					row.getCell((short)19).setCellType(Cell.CELL_TYPE_STRING);
					 //					String merchantType= row.getCell((short)19).getStringCellValue();
					 //					if("是".equals(merchantType.trim())){
					 //						merch.setMerchantType(2);
					 //					}else{
					 //						merch.setMerchantType(1);
					 //					}
					 //贷记卡签约费率
					 row.getCell((short)20).setCellType(Cell.CELL_TYPE_NUMERIC);
					 double aa1=row.getCell((short)20).getNumericCellValue()/100;
					 merch.setCreditBankRate(aa1+"");
					 //销售名称
					 row.getCell((short)21).setCellType(Cell.CELL_TYPE_STRING);
					 String salesNameValue=row.getCell((short)21).getStringCellValue().trim();
					 String areaCodeSql="select area_code from SYS_AREA t where t.area_name like '%"+merch.getAreaAddr()+"%'";
					 List<Map<String, Object>> areaList= merchantInfoTempDao.queryObjectsBySqlObject(areaCodeSql);
					 if(areaList.size()==0){
						 int a=1/0;
					 }
					 merch.setAreaCode(areaList.get(0).get("AREA_CODE").toString());
					 String sqlSales="select busid from bill_agentsalesinfo t where t.salename='"+salesNameValue+"' and t.maintaintype !='D'";	
					 List<Map<String, Object>> salesList= agentSalesDao.queryObjectsBySqlObject(sqlSales);
					 if(salesList.size()==0){
						 flag=false;
						 int a=1/0;
					 }
					 Map<String, Object> map=salesList.get(0);
					 Integer busid=Integer.parseInt(map.get("BUSID").toString());
					 merch.setBusid(busid);
					 merch.setSaleName(salesNameValue);
					 merch.setFileId(fid);
					 merch.setMaintainDate(new Date());
					 merch.setIsM35(0);
					 merch.setMerchantType(2);
					 flag=true;
					 batchList.add(merch);
				 }				
			 }	
			 merchantInfoTempDao.batchSaveObject(batchList);
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }

		 return flag;
	 }

	 @Override
	 public boolean saveMoreMicroMerchantInfo(String xlsfile, UserBean user,
			 String fileName2) {
		 boolean flag=false;
		 try {
			 String sql=" insert into Bill_BP_FILE (fname,ftype,status,cdate,cby)" +
					 " VALUES ('"+fileName2+"',1,0,sysdate,'"+user.getUnitName()+"')";
			 merchantInfoTempDao.executeUpdate(sql);
			 Integer fid=merchantInfoTempDao.getFid();

			 File filename = new File(xlsfile);
			 HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			 HSSFSheet sheet = workbook.getSheetAt(0);
			 List<Object> batchList = new ArrayList<Object>();
			 for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				 HSSFRow row = sheet.getRow(i);
				 HSSFCell cell;
				 MerchantInfoTempModel merch = new MerchantInfoTempModel();
				 cell = row.getCell((short)0);
				 if(cell == null || cell.toString().trim().equals("")){
					 flag=false;
					 break;
				 }else{
					 //unno
					 row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setUnno(row.getCell((short)0).getStringCellValue().trim());
					 //商户名称
					 row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setRname((row.getCell((short)1).getStringCellValue().trim()));

					 //法人
					 row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setLegalPerson((((row.getCell((short)2).getStringCellValue().trim()))));
					 //法人身份证号
					 row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setLegalNum((((row.getCell((short)3).getStringCellValue().trim()))));
					 //商户联系电话
					 row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setContactPhone((((row.getCell((short)4).getStringCellValue().trim()))));
					 //入账对公对私
					 row.getCell((short)5).setCellType(Cell.CELL_TYPE_STRING);
					 String accType=(row.getCell((short)5).getStringCellValue().trim());
					 if("对公".equals(accType)){
						 merch.setAccType("1");
					 }else{
						 merch.setAccType("2");
					 }
					 //开户行名称
					 row.getCell((short)6).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setBankBranch(((row.getCell((short)6).getStringCellValue().trim())));
					 //开户行账号
					 row.getCell((short)7).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setBankAccNo(((row.getCell((short)7).getStringCellValue().trim())));
					 //银行账户名称
					 row.getCell((short)8).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setBankAccName(((row.getCell((short)8).getStringCellValue().trim())));
					 //支付系统行号
					 row.getCell((short)9).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setPayBankId(((row.getCell((short)9).getStringCellValue().trim())));
					 //商户安装地址 省
					 row.getCell((short)10).setCellType(Cell.CELL_TYPE_STRING);
					 String province = row.getCell((short)10).getStringCellValue().trim();
					 //商户归属地区 市
					 row.getCell((short)11).setCellType(Cell.CELL_TYPE_STRING);
					 String city = row.getCell((short)11).getStringCellValue().trim();
					 merch.setAreaAddr(city);
					 //商户安装地址 详细
					 row.getCell((short)12).setCellType(Cell.CELL_TYPE_STRING);
					 String detail = row.getCell((short)12).getStringCellValue().trim();
					 merch.setBaddr(province+city+detail);
					 //签约费率
					 row.getCell((short)13).setCellType(Cell.CELL_TYPE_NUMERIC);
					 double aa=row.getCell((short)13).getNumericCellValue()/100;
					 merch.setBankFeeRate(aa+"");
					 //大额手续费
					 row.getCell((short)14).setCellType(Cell.CELL_TYPE_NUMERIC);
					 double bb=row.getCell((short)14).getNumericCellValue();
					 merch.setFeeAmt(bb+"");
					 //是否大额
					 //					row.getCell((short)14).setCellType(Cell.CELL_TYPE_STRING);
					 //					String merchantType= row.getCell((short)14).getStringCellValue();
					 //					if("是".equals(merchantType.trim())){
					 //						merch.setMerchantType(2);
					 //					}else{
					 //						merch.setMerchantType(1);
					 //					}
					 //贷记卡签约费率
					 row.getCell((short)15).setCellType(Cell.CELL_TYPE_NUMERIC);
					 double aa1=row.getCell((short)15).getNumericCellValue()/100;
					 merch.setCreditBankRate(aa1+"");
					 //会员宝手机号
					 row.getCell((short)16).setCellType(Cell.CELL_TYPE_STRING);
					 merch.setHybPhone((((row.getCell((short)16).getStringCellValue().trim()))));

					 //销售名称
					 row.getCell((short)17).setCellType(Cell.CELL_TYPE_STRING);
					 String salesNameValue=row.getCell((short)17).getStringCellValue().trim();
					 //					String areaCodeSql="select area_code from SYS_AREA t where t.area_name like '%"+merch.getAreaAddr()+"%'";
					 //					List<Map<String, Object>> areaList= merchantInfoTempDao.queryObjectsBySqlObject(areaCodeSql);
					 //					if(areaList.size()!=0){
					 //						merch.setAreaCode(areaList.get(0).get("AREA_CODE").toString());
					 //					}
					 merch.setAreaCode("010");

					 String sqlSales="select busid from bill_agentsalesinfo t where t.salename='"+salesNameValue+"' and t.maintaintype !='D'";	
					 List<Map<String, Object>> salesList= agentSalesDao.queryObjectsBySqlObject(sqlSales);
					 if(salesList.size()==0){
						 flag=false;
						 int a=1/0;
					 }
					 Map<String, Object> map=salesList.get(0);
					 Integer busid=Integer.parseInt(map.get("BUSID").toString());
					 merch.setBusid(busid);
					 merch.setSaleName(salesNameValue);
					 merch.setFileId(fid);
					 merch.setMaintainDate(new Date());
					 merch.setIsM35(1);
					 merch.setAccNum(merch.getLegalNum());
					 merch.setBno(" ");
					 merch.setContactPerson(merch.getLegalPerson());
					 merch.setRaddr(merch.getBaddr());
					 merch.setMccid("5311");
					 merch.setMerchantType(2);
					 flag=true;
					 batchList.add(merch);
				 }				
			 }		
			 merchantInfoTempDao.batchSaveObject(batchList);
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 return flag;
	 }

	 public String queryUnitUnnoUtil(String unno){
		 String sql="select UNNO from sys_unit start with unno ='"+unno+"' and status=1 connect by NOCYCLE prior  unno =  upper_unit ";
		 return sql;
	 }

	 @Override
	 public DataGridBean queryMicroMerchantInfoY(MerchantInfoBean merchantInfo,
			 UserBean user)throws Exception {
		 DataGridBean dataGridBean = new DataGridBean();
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 boolean flag = false;

		 // 精确查询条件个数,满足 商编\终端编号\SN号\开户账号 展示查询出的手机号,否则隐藏中间4位数
		 int queryConditionCount = 0;
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
				 map.put("maintainType", "D");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
					 map.put("maintainType", "D");
				 }
			 }else if(isAgentformMan(user)){//判断是否是报单员
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("maintainuid", user.getUserID());
			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
				 map.put("maintainType", "D");
			 }
		 }else{
			 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1  and M.mid not like 'HRTSYT%' and M.mid not like 'H864%' and M.mid not like 'D864%'";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
		 }

		 if (merchantInfo.getUnitType() != null && "2".equals(merchantInfo.getUnitType())) {
			 sql +=" AND M.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 sqlCount +=" AND M.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }else if(merchantInfo.getUnitType() != null && "1".equals(merchantInfo.getUnitType())){
			 sql +=" AND M.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 sqlCount +=" AND M.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND M.APPROVESTATUS = :approveStatus";
			 sqlCount += " AND M.APPROVESTATUS= :approveStatus";
			 map.put("approveStatus", merchantInfo.getApproveStatus());
		 } 

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.MID = :mid";
			 sqlCount += " AND M.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND M.RNAME LIKE :rname";
			 sqlCount += " AND M.RNAME LIKE :rname";
			 map.put("rname",merchantInfo.getRname()+'%');
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType  and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType  and APPROVESTATUS=:approvestatus)";
			 map.put("tid",merchantInfo.getTid()+'%');
			 map.put("maintainType", "D");
			 map.put("approvestatus", "Y");
			 flag=true;
		 }

		 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
			 queryConditionCount++;
			 sql +=" AND M.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN  and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND M.MID IN (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN  and APPROVESTATUS=:approvestatus)";
			 map.put("maintainType", "D");
			 map.put("SN",merchantInfo.getSn());
			 map.put("approvestatus", "Y");
			 flag=true;
		 }
		 //归属
		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND M.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 sqlCount += " AND M.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
		 }

		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.BANKACCNO LIKE :bankAccNo";
			 sqlCount += " AND M.BANKACCNO LIKE :bankAccNo";
			 map.put("bankAccNo",merchantInfo.getBankAccNo());
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND M.UNNO = :merunno";
			 sqlCount += " AND M.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
			 flag=true;
		 } 
		 if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
			 sql +=" AND M.approveUid = :userID";
			 sqlCount += " AND M.approveUid = :userID";
			 map.put("userID",getUserID(merchantInfo.getApproveUidName().trim()) );
			 flag=true;
		 } 
		 //设备型号
		 if (merchantInfo.getBmaid() != null && !"".equals(merchantInfo.getBmaid())) {
			 sql += " AND M.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = :bmaid )";
			 sqlCount += " AND M.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = :bmaid )";
			 flag=true;
			 map.put("bmaid", merchantInfo.getBmaid());
		 }
		 if (merchantInfo.getSettMethod() != null && !"".equals(merchantInfo.getSettMethod().trim())) {
			 sql +=" AND M.settMethod = :SETTMETHOD";
			 sqlCount += " AND M.settMethod = :SETTMETHOD";
			 map.put("SETTMETHOD", merchantInfo.getSettMethod());
		 } //JOINCONFIRMDATE
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //            sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 //            sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 sql +=" AND M.approveDate >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 sqlCount += " AND M.approveDate >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
		 }

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //          sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 //          sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 sql +=" AND M.approveDate < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 sqlCount += " AND M.approveDate < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
		 }
		 if(flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd() == null||merchantInfo.getCreateDateEnd().equals(""))){
			 //			sql +=" AND trunc(M.approveDate) = trunc(sysdate)";
			 //			sqlCount += " AND trunc(M.approveDate) = trunc(sysdate)";
			 //			sql +=" AND M.approveDate >= trunc(sysdate)";
			 //			sqlCount += " AND M.approveDate >= trunc(sysdate)";
			 return dataGridBean;
		 }

		 sql += " order by M.approveDate DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 //		Integer i = merchantInfoDao.querysqlCounts2("select count(1) from sys_configure a where a.groupname='phoneFilter' and a.minfo1='"+user.getUnNo()+"'", null);
		 Integer i=1;
		 //是否隐藏手机号信息 大于0隐藏
		 if(queryConditionCount!=0){
			 i=0;
		 }
		 //		DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),i);
		 dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),i);
		 return dataGridBean;
	 }

	 @Override
	 public DataGridBean queryMicroMerchantInfoY10631(MerchantInfoBean merchantInfo,
			 UserBean user)throws Exception {
		 DataGridBean dataGridBean = new DataGridBean();
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 boolean flag = false;

		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 ";
				 map.put("maintainType", "D");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 ";
					 map.put("maintainType", "D");
				 }
			 }else if(isAgentformMan(user)){//判断是否是报单员
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1 ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("maintainuid", user.getUserID());
			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 ";
				 map.put("maintainType", "D");
			 }
		 }else{
			 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
		 }
		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND M.MID = :mid";
			 sqlCount += " AND M.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
			 flag=true;
		 }
		 //手机号查询
		 if(merchantInfo.getHybPhone() != null && !"".equals(merchantInfo.getHybPhone())) {
			 sql += " AND M.HYBPHONE = :hybPhone";
			 sqlCount += " AND M.HYBPHONE = :hybPhone";
			 map.put("hybPhone", merchantInfo.getHybPhone());
			 flag=true;
		 }
		 if(flag==false){
			 return dataGridBean;
		 }
		 //		sql += " order by M.approveDate DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 //是否隐藏手机号信息 大于0隐藏
		 dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),1);
		 return dataGridBean;
	 }

	 @Override
	 public DataGridBean queryMicroMerchantInfoYSyt(MerchantInfoBean merchantInfo,
			 UserBean user)throws Exception {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		 DataGridBean dataGridBean=new DataGridBean();
		 // 精确查询条件个数,满足 商编\终端编号\SN号\开户账号 展示查询出的手机号,否则隐藏中间4位数
		 int queryConditionCount = 0;
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 boolean flag = false;
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'HRTSYT%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'HRTSYT%' ";
				 map.put("maintainType", "D");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'HRTSYT%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'HRTSYT%' ";
					 map.put("maintainType", "D");
				 }
			 }else if(isAgentformMan(user)){//判断是否是报单员
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1 and mid like 'HRTSYT%'  ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1 and mid like 'HRTSYT%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("maintainuid", user.getUserID());
			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'HRTSYT%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'HRTSYT%' ";
				 map.put("maintainType", "D");
			 }
		 }else{
			 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'HRTSYT%' ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'HRTSYT%' ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
		 }

		 if (merchantInfo.getUnitType() != null && "2".equals(merchantInfo.getUnitType())) {
			 sql +=" AND M.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 sqlCount +=" AND M.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }else if(merchantInfo.getUnitType() != null && "1".equals(merchantInfo.getUnitType())){
			 sql +=" AND M.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 sqlCount +=" AND M.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND M.APPROVESTATUS = :approveStatus";
			 sqlCount += " AND M.APPROVESTATUS= :approveStatus";
			 map.put("approveStatus", merchantInfo.getApproveStatus());
		 }

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.MID = :mid";
			 sqlCount += " AND M.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND M.RNAME LIKE :rname";
			 sqlCount += " AND M.RNAME LIKE :rname";
			 map.put("rname",merchantInfo.getRname()+'%');
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType  and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType  and APPROVESTATUS=:approvestatus)";
			 map.put("tid",merchantInfo.getTid()+'%');
			 map.put("maintainType", "D");
			 map.put("approvestatus", "Y");
			 flag=true;
		 }

		 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
			 queryConditionCount++;
			 sql +=" AND M.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN  and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND M.MID IN (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN  and APPROVESTATUS=:approvestatus)";
			 map.put("maintainType", "D");
			 map.put("SN",merchantInfo.getSn());
			 map.put("approvestatus", "Y");
			 flag=true;
		 }
		 //归属
		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND M.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 sqlCount += " AND M.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
		 }

		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.BANKACCNO LIKE :bankAccNo";
			 sqlCount += " AND M.BANKACCNO LIKE :bankAccNo";
			 map.put("bankAccNo",merchantInfo.getBankAccNo());
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND M.UNNO = :merunno";
			 sqlCount += " AND M.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
			 flag=true;
		 }
		 if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
			 sql +=" AND M.approveUid = :userID";
			 sqlCount += " AND M.approveUid = :userID";
			 map.put("userID",getUserID(merchantInfo.getApproveUidName().trim()) );
			 flag=true;
		 }
		 //设备型号
		 if (merchantInfo.getBmaid() != null && !"".equals(merchantInfo.getBmaid())) {
			 sql += " AND M.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = :bmaid )";
			 sqlCount += " AND M.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = :bmaid )";
			 flag=true;
			 map.put("bmaid", merchantInfo.getBmaid());
		 }
		 if (merchantInfo.getSettMethod() != null && !"".equals(merchantInfo.getSettMethod().trim())) {
			 sql +=" AND M.settMethod = :SETTMETHOD";
			 sqlCount += " AND M.settMethod = :SETTMETHOD";
			 map.put("SETTMETHOD", merchantInfo.getSettMethod());
		 } //JOINCONFIRMDATE
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //			sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 //			sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 sql +=" AND M.approveDate >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 sqlCount += " AND M.approveDate >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
		 }

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //			sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 //			sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 sql +=" AND M.approveDate < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 sqlCount += " AND M.approveDate < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
		 }
		 if(flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd() == null||merchantInfo.getCreateDateEnd().equals(""))){
			 //			sql +=" AND trunc(M.approveDate) = trunc(sysdate)";
			 //			sqlCount += " AND trunc(M.approveDate) = trunc(sysdate)";
			 //			sql +=" AND M.approveDate >= trunc(sysdate)";
			 //			sqlCount += " AND M.approveDate >= trunc(sysdate)";
			 return dataGridBean;
		 }

		 sql += " order by M.approveDate DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 //是否隐藏手机号信息
		 //		Integer i = merchantInfoDao.querysqlCounts2("select count(1) from sys_configure a where a.groupname='phoneFilter' and a.minfo1='"+user.getUnNo()+"'", null);
		 Integer i=1;
		 if(queryConditionCount!=0){
			 i=0;
		 }
		 //		DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),i);
		 dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),i);
		 return dataGridBean;
	 }

	 @Override
	 public DataGridBean queryMicroMerchantInfoYPlus(MerchantInfoBean merchantInfo,
			 UserBean user)throws Exception {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		 DataGridBean dataGridBean=new DataGridBean();
		 // 精确查询条件个数,满足 商编\终端编号\SN号\开户账号 展示查询出的手机号,否则隐藏中间4位数
		 int queryConditionCount = 0;
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 String sqlCount = "";
		 boolean flag = false;
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'H864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'H864%' ";
				 map.put("maintainType", "D");
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'H864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'H864%' ";
					 map.put("maintainType", "D");
				 }
			 }else if(isAgentformMan(user)){//判断是否是报单员
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1 and mid like 'H864%'  ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1 and mid like 'H864%' ";
				 map.put("unno", user.getUnNo());
				 map.put("maintainType", "D");
				 map.put("maintainuid", user.getUserID());
			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'H864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'H864%' ";
				 map.put("maintainType", "D");
			 }
		 }else{
			 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'H864%' ";
			 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'H864%' ";
			 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
			 map.put("maintainType", "D");
		 }

		 if (merchantInfo.getUnitType() != null && "2".equals(merchantInfo.getUnitType())) {
			 sql +=" AND M.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 sqlCount +=" AND M.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }else if(merchantInfo.getUnitType() != null && "1".equals(merchantInfo.getUnitType())){
			 sql +=" AND M.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 sqlCount +=" AND M.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND M.APPROVESTATUS = :approveStatus";
			 sqlCount += " AND M.APPROVESTATUS= :approveStatus";
			 map.put("approveStatus", merchantInfo.getApproveStatus());
		 }

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.MID = :mid";
			 sqlCount += " AND M.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND M.RNAME LIKE :rname";
			 sqlCount += " AND M.RNAME LIKE :rname";
			 map.put("rname",merchantInfo.getRname()+'%');
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType  and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType  and APPROVESTATUS=:approvestatus)";
			 map.put("tid",merchantInfo.getTid()+'%');
			 map.put("maintainType", "D");
			 map.put("approvestatus", "Y");
			 flag=true;
		 }

		 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
			 queryConditionCount++;
			 sql +=" AND M.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN  and APPROVESTATUS=:approvestatus)";
			 sqlCount += " AND M.MID IN (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN  and APPROVESTATUS=:approvestatus)";
			 map.put("maintainType", "D");
			 map.put("SN",merchantInfo.getSn());
			 map.put("approvestatus", "Y");
			 flag=true;
		 }
		 //归属
		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND M.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 sqlCount += " AND M.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
		 }

		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 queryConditionCount++;
			 sql +=" AND M.BANKACCNO LIKE :bankAccNo";
			 sqlCount += " AND M.BANKACCNO LIKE :bankAccNo";
			 map.put("bankAccNo",merchantInfo.getBankAccNo());
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND M.UNNO = :merunno";
			 sqlCount += " AND M.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
			 flag=true;
		 }
		 if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
			 sql +=" AND M.approveUid = :userID";
			 sqlCount += " AND M.approveUid = :userID";
			 map.put("userID",getUserID(merchantInfo.getApproveUidName().trim()) );
			 flag=true;
		 }
		 //设备型号
		 if (merchantInfo.getBmaid() != null && !"".equals(merchantInfo.getBmaid())) {
			 sql += " AND M.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = :bmaid )";
			 sqlCount += " AND M.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = :bmaid )";
			 flag=true;
			 map.put("bmaid", merchantInfo.getBmaid());
		 }
		 if (merchantInfo.getSettMethod() != null && !"".equals(merchantInfo.getSettMethod().trim())) {
			 sql +=" AND M.settMethod = :SETTMETHOD";
			 sqlCount += " AND M.settMethod = :SETTMETHOD";
			 map.put("SETTMETHOD", merchantInfo.getSettMethod());
		 } //JOINCONFIRMDATE
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //			sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 //			sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') >= :createDateTimeStart";
			 sql +=" AND M.approveDate >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 sqlCount += " AND M.approveDate >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
		 }

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //			sql +=" AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 //			sqlCount += " AND to_char(M.approveDate,'yyyy-MM-dd') <= :createdatetimeEnd";
			 sql +=" AND M.approveDate < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 sqlCount += " AND M.approveDate < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
		 }
		 if(flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd() == null||merchantInfo.getCreateDateEnd().equals(""))){
			 //			sql +=" AND trunc(M.approveDate) = trunc(sysdate)";
			 //			sqlCount += " AND trunc(M.approveDate) = trunc(sysdate)";
			 //			sql +=" AND M.approveDate >= trunc(sysdate)";
			 sqlCount += " AND M.approveDate >= trunc(sysdate)";
			 return dataGridBean;
		 }

		 sql += " order by M.approveDate DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 //是否隐藏手机号信息
		 //		Integer i = merchantInfoDao.querysqlCounts2("select count(1) from sys_configure a where a.groupname='phoneFilter' and a.minfo1='"+user.getUnNo()+"'", null);
		 Integer i=1;
		 if(queryConditionCount!=0){
			 i=0;
		 }
		 //		DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),i);
		 dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),i);
		 return dataGridBean;
	 }

	 @Override
	 public DataGridBean querySytRebateTotal(MerchantInfoBean merchantInfo,
			 UserBean user)throws Exception {
		 String sql = getSytRebateTotalSql(merchantInfo);
		 String sqlCount = "select count(1) from ("+sql+")";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, null);
		 List<Map<String, Object>> list = merchantInfoDao.queryObjectsBySqlList2(sql,null,merchantInfo.getPage(), merchantInfo.getRows());
		 DataGridBean dataGridBean = new DataGridBean();
		 dataGridBean.setTotal(counts.intValue());
		 dataGridBean.setRows(list);
		 return dataGridBean;
	 }

	 private String getSytRebateTotalSql(MerchantInfoBean merchantInfo){
		 String sql = "select unno,un_name,out_total,act_total,sum(add_out_act) add_out_act,sum(out_count) out_count," +
				 " sum(act_count) act_count," +
				 " to_char(decode(m.out_total,0,0,round((m.act_total/m.out_total)*100,2)))||'%' act_rate from ( " +
				 "select t.unno, t.un_name, t.add_out_act, t.out_count, t.act_count, " +
				 "       (select sum(k.out_count) from check_rebate_summary k where k.unno=t.unno) out_total, " +
				 "       (select sum(k.act_count) from check_rebate_summary k where k.unno=t.unno) act_total " +
				 "  from check_rebate_summary t " +
				 " where 1=1 ";
		 if(StringUtils.isNotEmpty(merchantInfo.getCreateDateStart()) && StringUtils.isNotEmpty(merchantInfo.getCreateDateEnd())){
			 sql+=" and t.summary_day >= '"+merchantInfo.getCreateDateStart().replace("-","")+
					 "' and  t.summary_day <= '"+merchantInfo.getCreateDateEnd().replace("-","")+"'  ";
		 }else{
			 sql+=" and t.summary_day >= to_char(sysdate-1,'yyyyMMdd') and  t.summary_day <= to_char(sysdate,'yyyyMMdd') ";
		 }
		 sql += " ) m group by unno,un_name,out_total,act_total ";
		 return sql;
	 }

	 @Override
	 public List<Map<String, Object>>  querySytRebateTotalAll(MerchantInfoBean merchantInfo,
			 UserBean user)throws Exception {
		 String sql = getSytRebateTotalSql(merchantInfo);
		 List<Map<String, Object>> list = merchantInfoDao.queryObjectsBySqlListMap2(sql,null);
		 return list;
	 }
	 @Override
	 public List<Map<String, Object>> exportMicro(MerchantInfoBean merchantInfo,
			 UserBean user) {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		 boolean flag = false ;

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid not like 'H864%' and t.mid not like 'HRTSYT%' ";
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
							 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
							 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
							 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
							 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
							 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
							 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
							 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
							 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
							 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
							 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
							 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid not like 'H864%' and t.mid not like 'HRTSYT%' ";
				 }
			 }else if(isAgentformMan(user)){
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod"
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO = '"+user.getUnNo()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid not like 'H864%' and t.mid not like 'HRTSYT%' and t.maintainuid = '"+ user.getUserID()+"'";

			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO IN ("+childUnno+") AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid not like 'H864%' and t.mid not like 'HRTSYT%' ";
			 }
		 }else{
			 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
					 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
					 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
					 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
					 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
					 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
					 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
					 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
					 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS ,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
					 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
					 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
					 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.BUSID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid not like 'H864%' and t.mid not like 'HRTSYT%' ";
		 }

		 if (merchantInfo.getUnitType() != null && "2".equals(merchantInfo.getUnitType())) {
			 sql +=" AND t.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }else if(merchantInfo.getUnitType() != null && "1".equals(merchantInfo.getUnitType())){
			 sql +=" AND t.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND t.APPROVESTATUS = '"+merchantInfo.getApproveStatus()+"'";
		 }else{
			 sql +=" AND t.APPROVESTATUS in ('C','Y')";
		 }

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND t.MID = '"+merchantInfo.getMid()+"' ";
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND t.RNAME LIKE '%"+merchantInfo.getRname()+"%' ";
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND t.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE '"+merchantInfo.getTid()+"%' AND MAINTAINTYPE != 'D'  and APPROVESTATUS='Y')";
			 flag=true;
		 }

		 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
			 sql +=" AND t.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !='D' and b.sn="+merchantInfo.getSn()+"  and APPROVESTATUS='Y')";
			 flag=true;
		 }

		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND t.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
		 }

		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND t.BANKACCNO LIKE '"+merchantInfo.getBankAccNo()+"'";
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND t.UNNO = '"+merchantInfo.getUnno()+"' ";
			 flag=true;
		 } 
		 if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
			 String str = merchantInfo.getApproveUidName();
			 String userId = getUserID(merchantInfo.getApproveUidName().trim());
			 sql +=" AND t.approveUid = '"+userId+"' ";
			 flag=true;						
		 } 

		 if (merchantInfo.getBmaid() != null && !"".equals(merchantInfo.getBmaid())) {
			 sql += " AND t.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = "+merchantInfo.getBmaid()+" )";
			 flag=true;
		 }

		 if (merchantInfo.getSettMethod() != null && !"".equals(merchantInfo.getSettMethod().trim())) {
			 sql +=" AND nvl(t.settMethod,'000000')='"+merchantInfo.getSettMethod()+"'";
		 } 
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd')  >= '"+merchantInfo.getCreateDateStart()+"' ";
			 sql +=" AND t.approveDate  >= to_date('"+merchantInfo.getCreateDateStart()+"','yyyy-MM-dd') ";
		 }

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd') <= '"+merchantInfo.getCreateDateEnd()+"' ";
			 sql +=" AND t.approveDate < to_date('"+merchantInfo.getCreateDateEnd()+"','yyyy-MM-dd') + 1 ";
		 }
		 if(flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd() == null||merchantInfo.getCreateDateEnd().equals(""))){
			 //			sql +=" AND trunc(t.approveDate) = trunc(sysdate)";
			 sql +=" AND t.approveDate >= trunc(sysdate+1) ";
		 }
		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
		 return data;

	 }

	 @Override
	 public List<Map<String, Object>> exportMicroSyt(MerchantInfoBean merchantInfo,
			 UserBean user) {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		 boolean flag = false ;

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'HRTSYT%' ";
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
							 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
							 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
							 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
							 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
							 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
							 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
							 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
							 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
							 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
							 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
							 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'HRTSYT%' ";
				 }
			 }else if(isAgentformMan(user)){
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod"
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO = '"+user.getUnNo()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1  and t.mid like 'HRTSYT%' and t.maintainuid = '"+ user.getUserID()+"'";

			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO IN ("+childUnno+") AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'HRTSYT%' ";
			 }
		 }else{
			 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
					 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
					 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
					 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
					 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
					 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
					 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
					 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
					 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS ,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
					 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
					 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
					 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.BUSID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'HRTSYT%' ";
		 }

		 if (merchantInfo.getUnitType() != null && "2".equals(merchantInfo.getUnitType())) {
			 sql +=" AND t.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }else if(merchantInfo.getUnitType() != null && "1".equals(merchantInfo.getUnitType())){
			 sql +=" AND t.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND t.APPROVESTATUS = '"+merchantInfo.getApproveStatus()+"'";
		 }else{
			 sql +=" AND t.APPROVESTATUS in ('C','Y')";
		 }

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND t.MID = '"+merchantInfo.getMid()+"' ";
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND t.RNAME LIKE '%"+merchantInfo.getRname()+"%' ";
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND t.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE '"+merchantInfo.getTid()+"%' AND MAINTAINTYPE != 'D'  and APPROVESTATUS='Y')";
			 flag=true;
		 }

		 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
			 sql +=" AND t.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !='D' and b.sn="+merchantInfo.getSn()+"  and APPROVESTATUS='Y')";
			 flag=true;
		 }

		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND t.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
		 }

		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND t.BANKACCNO LIKE '"+merchantInfo.getBankAccNo()+"'";
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND t.UNNO = '"+merchantInfo.getUnno()+"' ";
			 flag=true;
		 }
		 if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
			 String str = merchantInfo.getApproveUidName();
			 String userId = getUserID(merchantInfo.getApproveUidName().trim());
			 sql +=" AND t.approveUid = '"+userId+"' ";
			 flag=true;
		 }

		 if (merchantInfo.getBmaid() != null && !"".equals(merchantInfo.getBmaid())) {
			 sql += " AND t.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = "+merchantInfo.getBmaid()+" )";
			 flag=true;
		 }

		 if (merchantInfo.getSettMethod() != null && !"".equals(merchantInfo.getSettMethod().trim())) {
			 sql +=" AND nvl(t.settMethod,'000000')='"+merchantInfo.getSettMethod()+"'";
		 }
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd')  >= '"+merchantInfo.getCreateDateStart()+"' ";
			 sql +=" AND t.approveDate  >= to_date('"+merchantInfo.getCreateDateStart()+"','yyyy-MM-dd') ";
		 }

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd') <= '"+merchantInfo.getCreateDateEnd()+"' ";
			 sql +=" AND t.approveDate < to_date('"+merchantInfo.getCreateDateEnd()+"','yyyy-MM-dd') + 1 ";
		 }
		 /*
		  * if(flag==false&&(merchantInfo.getCreateDateStart() ==
		  * null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.
		  * getCreateDateEnd() == null||merchantInfo.getCreateDateEnd().equals(""))){ //
		  * sql +=" AND trunc(t.approveDate) = trunc(sysdate)"; sql
		  * +=" AND t.approveDate >= trunc(sysdate+1) "; }
		  */
		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
		 return data;

	 }

	 @Override
	 public boolean queryIsHotMerch(MerchantInfoBean merchantInfo) {
		 String sql ="select count(*) from pg_hotmerch t where t.tname='"+merchantInfo.getRname()+"' or t.bankAccNo='"+merchantInfo.getBankAccNo()+"' or t.identificationnumber='"+merchantInfo.getLegalNum()+"' or t.license='"+merchantInfo.getBno()+"' ";
		 Integer count=merchantInfoDao.querysqlCounts2(sql, null);
		 if(count>0){
			 return false;
		 }else{
			 return true;
		 }

	 }

	 @Override
	 public List<Map<String, Object>> exportMicroPlus(MerchantInfoBean merchantInfo,
			 UserBean user) {
		 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
		 boolean flag = false ;

		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 String sql = "";
		 if(agentSalesModels.size()==0){
			 if("110000".equals(user.getUnNo())){
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'H864%' ";
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
							 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
							 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
							 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
							 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
							 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
							 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
							 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
							 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
							 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
							 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
							 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'H864%' ";
				 }
			 }else if(isAgentformMan(user)){
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod"
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO = '"+user.getUnNo()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1  and t.mid like 'HRTSYT%' and t.maintainuid = '"+ user.getUserID()+"'";

			 }else{
				 String childUnno=queryUnitUnnoUtil(user.getUnNo());
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO IN ("+childUnno+") AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'H864%' ";
			 }
		 }else{
			 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
					 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
					 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
					 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
					 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
					 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
					 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
					 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
					 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS ,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
					 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
					 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
					 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.BUSID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'H864%' ";
		 }

		 if (merchantInfo.getUnitType() != null && "2".equals(merchantInfo.getUnitType())) {
			 sql +=" AND t.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }else if(merchantInfo.getUnitType() != null && "1".equals(merchantInfo.getUnitType())){
			 sql +=" AND t.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
		 }

		 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
			 sql +=" AND t.APPROVESTATUS = '"+merchantInfo.getApproveStatus()+"'";
		 }else{
			 sql +=" AND t.APPROVESTATUS in ('C','Y')";
		 }

		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND t.MID = '"+merchantInfo.getMid()+"' ";
			 flag=true;
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND t.RNAME LIKE '%"+merchantInfo.getRname()+"%' ";
			 flag=true;
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND t.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE '"+merchantInfo.getTid()+"%' AND MAINTAINTYPE != 'D'  and APPROVESTATUS='Y')";
			 flag=true;
		 }

		 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
			 sql +=" AND t.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !='D' and b.sn="+merchantInfo.getSn()+"  and APPROVESTATUS='Y')";
			 flag=true;
		 }

		 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
			 sql +=" AND t.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
		 }

		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND t.BANKACCNO LIKE '"+merchantInfo.getBankAccNo()+"'";
			 flag=true;
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND t.UNNO = '"+merchantInfo.getUnno()+"' ";
			 flag=true;
		 }
		 if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
			 String str = merchantInfo.getApproveUidName();
			 String userId = getUserID(merchantInfo.getApproveUidName().trim());
			 sql +=" AND t.approveUid = '"+userId+"' ";
			 flag=true;
		 }

		 if (merchantInfo.getBmaid() != null && !"".equals(merchantInfo.getBmaid())) {
			 sql += " AND t.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = "+merchantInfo.getBmaid()+" )";
			 flag=true;
		 }

		 if (merchantInfo.getSettMethod() != null && !"".equals(merchantInfo.getSettMethod().trim())) {
			 sql +=" AND nvl(t.settMethod,'000000')='"+merchantInfo.getSettMethod()+"'";
		 }
		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd')  >= '"+merchantInfo.getCreateDateStart()+"' ";
			 sql +=" AND t.approveDate  >= to_date('"+merchantInfo.getCreateDateStart()+"','yyyy-MM-dd') ";
		 }

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd') <= '"+merchantInfo.getCreateDateEnd()+"' ";
			 sql +=" AND t.approveDate < to_date('"+merchantInfo.getCreateDateEnd()+"','yyyy-MM-dd') + 1 ";
		 }

		 if(flag==false&&(merchantInfo.getCreateDateStart() ==
				 null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.
						 getCreateDateEnd() == null||merchantInfo.getCreateDateEnd().equals(""))){ 
			 //		  sql +=" AND trunc(t.approveDate) = trunc(sysdate)"; 
			 sql +=" AND t.approveDate >= trunc(sysdate+1) "; 
		 }


		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
		 return data;

	 }

	 @Override
	 public DataGridBean queryAllMerchantInfo(MerchantInfoBean merchantInfo) {
		 String sql="select * from bill_merchantinfo  where 1=1 ";
		 String sqlCount="select count(*) from bill_merchantinfo where 1=1 ";
		 Map<String,Object> map = new HashMap<String, Object>();
		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND MID = :mid";
			 sqlCount += " AND MID = :mid";
			 map.put("mid",merchantInfo.getMid());
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND RNAME LIKE :rname";
			 sqlCount += " AND RNAME LIKE :rname";
			 map.put("rname",merchantInfo.getRname()+'%');
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType)";
			 sqlCount += " AND MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType)";
			 map.put("tid",merchantInfo.getTid()+'%');
			 map.put("maintainType", "D");
		 }

		 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
			 sql +=" AND MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN )";
			 sqlCount += " AND MID IN (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN )";
			 map.put("maintainType", "D");
			 map.put("SN",merchantInfo.getSn());
		 }


		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND BANKACCNO LIKE :bankAccNo";
			 sqlCount += " AND BANKACCNO LIKE :bankAccNo";
			 map.put("bankAccNo",merchantInfo.getBankAccNo()+'%');
		 }

		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 sql +=" AND JOINCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 sqlCount += " AND JOINCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
		 } 

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 sql +=" AND JOINCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 sqlCount += " AND JOINCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND UNNO = :merunno";
			 sqlCount += " AND UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
		 }
		 if(merchantInfo.getLegalPerson()!=null && !"".equals(merchantInfo.getLegalPerson())){
			 sql +=" AND LEGALPERSON LIKE :legalperson";
			 sqlCount += " AND LEGALPERSON LIKE :legalperson";
			 map.put("legalperson",merchantInfo.getLegalPerson()+'%');
		 }
		 if(merchantInfo.getAccNum()!=null && !"".equals(merchantInfo.getAccNum())){
			 sql +=" AND ACCNUM LIKE :accNum";
			 sqlCount += " AND ACCNUM LIKE :accNum";
			 map.put("accNum",merchantInfo.getAccNum()+'%');
		 }
		 if(merchantInfo.getContactPerson()!=null && !"".equals(merchantInfo.getContactPerson())){
			 sql +=" AND CONTACTPERSON LIKE :contactperson";
			 sqlCount += " AND CONTACTPERSON LIKE :contactperson";
			 map.put("contactperson",merchantInfo.getContactPerson()+'%');
		 }
		 if(merchantInfo.getLegalNum()!=null && !"".equals(merchantInfo.getLegalNum())){
			 sql +=" AND LEGALNUM LIKE :legalnum";
			 sqlCount += " AND LEGALNUM LIKE :legalnum";
			 map.put("legalnum",merchantInfo.getLegalNum()+'%');
		 }
		 if(merchantInfo.getContactPhone()!=null && !"".equals(merchantInfo.getContactPhone())){
			 sql +=" AND CONTACTPHONE = :contactphone";
			 sqlCount += " AND CONTACTPHONE = :contactphone";
			 map.put("contactphone",merchantInfo.getContactPhone());
		 }
		 if(merchantInfo.getBankAccName()!=null && !"".equals(merchantInfo.getBankAccName())){
			 sql +=" AND BANKACCNAME LIKE :bankaccname";
			 sqlCount += " AND BANKACCNAME LIKE :bankaccname";
			 map.put("bankaccname",merchantInfo.getBankAccName()+'%');
		 }
		 if(merchantInfo.getBankBranch()!=null && !"".equals(merchantInfo.getBankBranch())){
			 sql +=" AND BANKBRANCH LIKE :bankbranch";
			 sqlCount += " AND BANKBRANCH LIKE :bankbranch";
			 map.put("bankbranch",merchantInfo.getBankBranch()+'%');
		 }
		 //		sql += " order by bmid DESC ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
		 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());
		 return dataGridBean;
	 }

	 @Override
	 public HSSFWorkbook exportAll(MerchantInfoBean merchantInfo,
			 UserBean userBean) {
		 String sql = " SELECT t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME,t.FEEAMT,to_char(t.CHECKCONFIRMDATE,'yyyy-MM-dd HH24:mi:ss') CHECKCONFIRMDATE," +
				 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
				 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, case when t.BANKACCNO is not null then "
				 + " substr(t.BANKACCNO,0,4)||'****'||substr(t.BANKACCNO,length(t.BANKACCNO)-3) else '' end BANKACCNO, t.PAYBANKID,"
				 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
				 + " t.BADDR,t.BUSINESSSCOPE, case when t.LEGALNUM is not null then "
				 + " substr(t.LEGALNUM,0,4)||'****'||substr(t.LEGALNUM,length(t.LEGALNUM)-3) else '' end LEGALNUM, t.CONTACTPERSON, case when t.CONTACTPHONE is not null then "
				 + " substr(t.CONTACTPHONE,0,3)||'****'||substr(t.CONTACTPHONE,length(t.CONTACTPHONE)-3) else '' end CONTACTPHONE,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,i.sn mersn "
				 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b "
				 + " WHERE t.MID = i.MID AND i.tid=b.termid AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND i.MAINTAINTYPE !='D' AND i.APPROVESTATUS = 'Y' ";
		 Map<String,Object> map = new HashMap<String, Object>();
		 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
			 sql +=" AND t.MID = :mid";
			 map.put("mid",merchantInfo.getMid());
		 }

		 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
			 sql +=" AND t.RNAME LIKE :rname";
			 map.put("rname",'%'+merchantInfo.getRname()+'%');
		 }

		 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
			 sql +=" AND t.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType)";
			 map.put("tid",merchantInfo.getTid()+'%');
			 map.put("maintainType", "D");
		 }

		 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
			 sql +=" AND t.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN )";
			 map.put("maintainType", "D");
			 map.put("SN",merchantInfo.getSn());
		 }


		 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
			 sql +=" AND t.BANKACCNO LIKE :bankAccNo";
			 map.put("bankAccNo",'%'+merchantInfo.getBankAccNo()+'%');
		 }

		 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
			 sql +=" AND t.JOINCONFIRMDATE >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
			 map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
		 } 

		 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
			 sql +=" AND t.JOINCONFIRMDATE < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
			 map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
		 }

		 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
			 sql +=" AND t.UNNO = :merunno";
			 map.put("merunno", merchantInfo.getUnno());
		 }
		 if(merchantInfo.getLegalPerson()!=null && !"".equals(merchantInfo.getLegalPerson())){
			 sql +=" AND t.LEGALPERSON LIKE :legalperson";
			 map.put("legalperson",'%'+merchantInfo.getLegalPerson()+'%');
		 }
		 if(merchantInfo.getContactPerson()!=null && !"".equals(merchantInfo.getContactPerson())){
			 sql +=" AND t.CONTACTPERSON LIKE :contactperson";
			 map.put("contactperson",'%'+merchantInfo.getContactPerson()+'%');
		 }
		 if(merchantInfo.getLegalNum()!=null && !"".equals(merchantInfo.getLegalNum())){
			 sql +=" AND t.LEGALNUM LIKE :legalnum";
			 map.put("legalnum",'%'+merchantInfo.getLegalNum()+'%');
		 }
		 if(merchantInfo.getContactPhone()!=null && !"".equals(merchantInfo.getContactPhone())){
			 sql +=" AND t.CONTACTPHONE = :contactphone";
			 map.put("contactphone",merchantInfo.getContactPhone());
		 }
		 if(merchantInfo.getBankAccName()!=null && !"".equals(merchantInfo.getBankAccName())){
			 sql +=" AND t.BANKACCNAME LIKE :bankaccname";
			 map.put("bankaccname",'%'+merchantInfo.getBankAccName()+'%');
		 }
		 if(merchantInfo.getBankBranch()!=null && !"".equals(merchantInfo.getBankBranch())){
			 sql +=" AND t.BANKBRANCH LIKE :bankbranch";
			 map.put("bankbranch",'%'+merchantInfo.getBankBranch()+'%');
		 }
		 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlListMap2(sql, map);
		 List<String> excelHeader = new ArrayList<String>();
		 Map<String, Object> headMap = new HashMap<String, Object>();
		 excelHeader.add("UNNO");
		 excelHeader.add("UNNONAME");
		 excelHeader.add("BNO");
		 excelHeader.add("RNAME");
		 excelHeader.add("CHECKCONFIRMDATE");
		 excelHeader.add("MAINTAINDATE");
		 excelHeader.add("DEPOSIT");
		 excelHeader.add("CHARGE");
		 excelHeader.add("LEGALPERSON");
		 excelHeader.add("BANKBRANCH");
		 excelHeader.add("BANKACCNAME");
		 excelHeader.add("BANKACCNO");
		 excelHeader.add("PAYBANKID");
		 excelHeader.add("MID");
		 excelHeader.add("TID");
		 excelHeader.add("BANKFEERATE");
		 excelHeader.add("FEEAMT");
		 excelHeader.add("FEERATE_V");
		 excelHeader.add("SALENAME");
		 excelHeader.add("MACHINEMODEL");
		 excelHeader.add("SETTLECYCLE");
		 excelHeader.add("BADDR");
		 excelHeader.add("BUSINESSSCOPE");
		 excelHeader.add("LEGALNUM");
		 excelHeader.add("CONTACTPERSON");
		 excelHeader.add("CONTACTPHONE");
		 excelHeader.add("APPROVEUIDNAME");
		 excelHeader.add("REMARKS");
		 excelHeader.add("SN");
		 excelHeader.add("TUSN");

		 headMap.put("UNNO", "机构编号");
		 headMap.put("UNNONAME", "机构名称");
		 headMap.put("BNO", "营业执照号");
		 headMap.put("RNAME", "商户名称");
		 headMap.put("CHECKCONFIRMDATE", "批准日期");
		 headMap.put("MAINTAINDATE", "申请日期");
		 headMap.put("DEPOSIT", "押金");
		 headMap.put("CHARGE", "服务费");
		 headMap.put("LEGALPERSON", "法人姓名");
		 headMap.put("BANKBRANCH", "开户银行");
		 headMap.put("BANKACCNAME", "开户人名");
		 headMap.put("BANKACCNO", "账号");
		 headMap.put("PAYBANKID", "支付系统行号");
		 headMap.put("MID", "公司mid");
		 headMap.put("TID", "公司tid");
		 headMap.put("BANKFEERATE", "商户结算费率（内卡）");
		 headMap.put("FEEAMT", "封顶手续费");
		 headMap.put("FEERATE_V", "商户结算费率（外卡）");
		 headMap.put("SALENAME", "销售");
		 headMap.put("MACHINEMODEL", "机型");
		 headMap.put("SETTLECYCLE", "结算周期");
		 headMap.put("BADDR", "营业地址");
		 headMap.put("BUSINESSSCOPE", "类别");
		 headMap.put("LEGALNUM", "法人证件号");
		 headMap.put("CONTACTPERSON", "联系人");
		 headMap.put("CONTACTPHONE", "电话");
		 headMap.put("APPROVEUIDNAME", "受理人员");
		 headMap.put("REMARKS", "备注");
		 headMap.put("SN", "SN");
		 headMap.put("TUSN", "MERSN");

		 return ExcelUtil.export("入网资料", data, excelHeader, headMap);
	 }

	 @Override
	 public boolean queryMicroMerchantCount() {
		 Integer seqNo = searchMIDSeqInfo("000","5311");
		 if(seqNo>=0 && seqNo<9990){
			 return true;
		 }else if(seqNo>=9990){
			 try {
				 return updateMicroMerchantCount();
			 } catch (Exception e) {
				 log.info("自动修改手刷商户号生成规则错误");
				 return false;
			 }
		 }
		 return false;
	 }
	 @Override
	 public synchronized String saveAggPayMerchantMid(){
		 String sqlMax = "SELECT minfo2 FROM sys_configure WHERE groupName='AggPayMerMid'";
		 List<Map<String, Object>> list= terminalInfoDao.queryObjectsBySqlObject(sqlMax);
		 Long maxTermid = Long.valueOf(list.get(0).get("MINFO2").toString());
		 Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+(String.format("%09d", maxTermid+1))+"' WHERE groupName='AggPayMerMid'", null);
		 String mid = "HRTPAY"+String.format("%09d", maxTermid);
		 log.info("聚合商户获取mid :"+mid+"; i = "+ i);
		 if(i==0){
			 return null;
		 }
		 return mid;
	 }

	 @Override
	 public synchronized String saveSYTMerchantMid(){
		 String sqlMax = "SELECT minfo2 FROM sys_configure WHERE groupName='SYTMerMid'";
		 List<Map<String, Object>> list= terminalInfoDao.queryObjectsBySqlObject(sqlMax);
		 Long maxTermid = Long.valueOf(list.get(0).get("MINFO2").toString());
		 Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+(String.format("%09d", maxTermid+1))+"' WHERE groupName='SYTMerMid'", null);
		 String mid = "HRTSYT"+String.format("%09d", maxTermid);
		 log.info("收银台商户获取mid :"+mid+"; i = "+ i);
		 if(i==0){
			 return null;
		 }
		 return mid;
	 }

	 @Override
	 public synchronized String savePlusMerchantMid(){
		 String sqlMax = "SELECT minfo2 FROM sys_configure WHERE groupName='PLUSMerMid'";
		 List<Map<String, Object>> list= terminalInfoDao.queryObjectsBySqlObject(sqlMax);
		 Long maxTermid = Long.valueOf(list.get(0).get("MINFO2").toString());
		 Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+(String.format("%011d", maxTermid+1))+"' WHERE groupName='PLUSMerMid'", null);
		 String mid = "H864"+String.format("%011d", maxTermid);
		 log.info("PLUS商户获取mid :"+mid+"; i = "+ i);
		 if(i==0){
			 return null;
		 }
		 return mid;
	 }

	@Override
	public synchronized String savePROMerchantMid(){
		String sqlMax = "SELECT minfo2 FROM sys_configure WHERE groupName='PROMerMid'";
		List<Map<String, Object>> list= terminalInfoDao.queryObjectsBySqlObject(sqlMax);
		Long maxTermid = Long.valueOf(list.get(0).get("MINFO2").toString());
		Integer i = terminalInfoDao.executeSqlUpdate("update sys_configure set minfo2='"+(String.format("%011d", maxTermid+1))+"' WHERE groupName='PROMerMid'", null);
		String mid = "D864"+String.format("%011d", maxTermid);
		log.info("D864商户获取mid :"+mid+"; i = "+ i);
		if(i==0){
			return null;
		}
		return mid;
	}

	 /**
	  * 查询微商户当前规则总数量
	  */
	 public  Integer searchMIDSeqInfo(String areaCode, String mccid){
		 String sql = "SELECT * FROM BILL_MIDSEQINFO WHERE AREA_CODE = '" + areaCode + "' AND MCCID = '" + mccid + "'";
		 List<MIDSeqInfoModel>   midSeqInfoList = midSeqInfoDao.queryObjectsBySqlList(sql, null, MIDSeqInfoModel.class);
		 Integer seqNo=0;	
		 if(midSeqInfoList != null && midSeqInfoList.size() > 0){
			 MIDSeqInfoModel midSeqInfoModel = midSeqInfoList.get(0);
			 seqNo = midSeqInfoModel.getSeqNo();
		 }
		 return seqNo;
	 }

	 public synchronized boolean updateMicroMerchantCount() throws Exception {
		 //		Integer seqNo = searchMIDSeqInfo("000","5311");
		 //		if(seqNo>0 && seqNo<9990){
		 //			return true;
		 //		}else if(seqNo>=9990){
		 String sql1 = "update bill_midseqinfo m3 set m3.seqno=0 where m3.area_code='000' and m3.mccid='5311' and m3.seqno>=9990" ;
		 String sql2 = "update sys_param pa set pa.upload_path=to_char(pa.upload_path+1,'FM0000') where  pa.title='MicroRule'" ;
		 Integer i = merchantInfoDao.executeSqlUpdate(sql1, null);
		 if(i>0){
			 Integer j = merchantInfoDao.executeSqlUpdate(sql2, null);
			 if(j==0){
				 int z = 1/0;
			 }
		 }
		 //		}
		 return true;
	 }

	 /**
	  * 根据Excel表格中的MID、TID、UNNO信息更新数据库中的UNNO
	  */
	 @Override
	 public boolean updateMerchantToUnit(String xlsfile, UserBean user,String fileName) {
		 boolean flag=false;
		 try {
			 //读取文件
			 File filename = new File(xlsfile);
			 HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
			 HSSFSheet sheet = workbook.getSheetAt(0);
			 String MID = "";
			 String TID = "";
			 String UNNO = "";
			 //读取表格信息
			 for(int i = 1; i < sheet.getLastRowNum()+1; i++){
				 HSSFRow row = sheet.getRow(i);
				 HSSFCell cell;
				 cell = row.getCell((short)0);
				 if(cell == null || cell.toString().trim().equals("")){
					 flag=false;
					 break;
				 }else{
					 //读取到的MID
					 row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
					 MID = row.getCell((short)0).getStringCellValue().trim();
					 //读取到的TID
					 row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
					 TID = row.getCell((short)1).getStringCellValue().trim();
					 //读取到的UNNO
					 row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
					 UNNO = row.getCell((short)2).getStringCellValue().trim();
					 if(MID!=null && !"".equals(MID) && TID!=null  && !"".equals(TID) && UNNO!=null  && !"".equals(UNNO)){
						 String sqlMID="update  bill_merchantinfo t set t.UNNO='"+UNNO+"' where t.MID='"+MID+"'";
						 merchantInfoDao.executeUpdate(sqlMID);

						 String sqlTID="update  bill_terminalinfo t set t.UNNO='"+UNNO+"' where t.TERMID='"+TID+"'";
						 merchantInfoDao.executeUpdate(sqlTID);

						 String sqlMIDTID="update  bill_merchantterminalinfo t set t.UNNO='"+UNNO+"' where t.MID='"+MID+"' and t.TID='"+TID+"'";
						 merchantInfoDao.executeUpdate(sqlMIDTID);

						 //					String sqlUSERMID="select USER_ID from  sys_user t where t.LOGIN_NAME='"+MID+"'";
						 //					String sqlUSER_ID="update  sys_unit_user t set t.UNNO='"+UNNO+"' where t.USER_ID=('"+sqlUSERMID+"')";
						 String sql="update sys_unit_user  t  set t.unno='"+UNNO+"' where t.user_id in (select user_id from sys_user where login_name='"+MID+"' )";
						 merchantInfoDao.executeUpdate(sql);
						 flag=true;
					 }else{
						 return false;
					 }
				 }				
			 }		
		 } catch (Exception e) {
			 flag=false;
			 e.printStackTrace();
		 }
		 return flag;
	 }

	 //	@Override
	 //	public void saveMerchantInfoByHttpClient(MerchantInfoBean merchantInfo) {
	 //		MerchantInfoModel merchant= new MerchantInfoModel();
	 //		BeanUtils.copyProperties(merchantInfo, merchant);
	 //		if(merchant.getIsM35()==0){
	 //			merchant.setAreaCode(merchantInfo.getAreaCode().trim());
	 //			StringBuffer mid = new StringBuffer("864");
	 //			if(merchantInfo.getAreaCode().trim().length()<=3){
	 //				mid.append("0"+merchantInfo.getAreaCode().trim());
	 //			}else{
	 //				mid.append(merchantInfo.getAreaCode().trim());
	 //			}
	 //			mid.append(merchantInfo.getMccid());
	 //			Integer seqNo = searchMIDSeqInfo(merchantInfo.getAreaCode(), merchantInfo.getMccid().toString(),"superadmin");
	 //			DecimalFormat deFormat = new DecimalFormat("0000");
	 //			mid.append(deFormat.format(seqNo));
	 //			merchant.setMid(mid.toString());
	 //			merchant.setMaintainUid(1);		//操作人员
	 //			merchant.setMaintainDate(new Date());			//操作日期
	 //			merchant.setMaintainType("A");		//A-add   M-Modify  D-Delete
	 //			merchant.setSettleStatus("1");		//结算状态 1-正常 2-冻结
	 //			merchant.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销
	 //			merchant.setSettleCycle(1);
	 //			//节假日是否合并结账 0：否 1：是
	 //			merchant.setSettleMerger("1");
	 //			merchant.setApproveStatus("W");
	 //			merchant.setFeeRateA("0");
	 //			merchant.setFeeRateD("0");
	 //			merchant.setFeeRateJ("0");
	 //			merchant.setFeeRateM("0");
	 //			merchant.setFeeRateV("0");
	 //			merchant.setBusinessScope(" ");
	 //			merchant.setMaintainUserId(merchantInfo.getBusid());
	 //			merchant.setLegalType("1");
	 //			merchant.setContactAddress(merchantInfo.getBaddr());
	 //			merchant.setSettleRange("00");
	 //			String sqlSales="select busid from bill_agentsalesinfo t where t.salename='"+merchantInfo.getBusidName()+"' and t.maintaintype !='D'";	
	 //			List<Map<String, Object>> salesList= agentSalesDao.queryObjectsBySqlObject(sqlSales);
	 //			if(salesList.size()==0){
	 //				int a=1/0;
	 //			}
	 //			Map<String, Object> map=salesList.get(0);
	 //			Integer busid=Integer.parseInt(map.get("BUSID").toString());
	 //			merchant.setBusid(busid);
	 //			merchant.setMaintainUserId(busid);
	 //			if(merchantInfo.getMerchantType()==2){
	 //				Double a=Double.parseDouble(merchantInfo.getFeeAmt());
	 //				Double b=Double.parseDouble(merchantInfo.getBankFeeRate());
	 //				merchant.setDealAmt(Math.round(a/b)+"");
	 //			}
	 //			merchantInfoDao.saveObject(merchant);
	 //			//钱包默认银行卡
	 //			MerchantBankCardModel mbc = new MerchantBankCardModel();
	 //			mbc.setBankAccName(merchantInfo.getBankAccName());
	 //			mbc.setBankAccNo(merchantInfo.getBankAccNo());
	 //			mbc.setCreateDate(new Date());
	 //			mbc.setMerCardType(0);
	 //			mbc.setStatus(0);
	 //			mbc.setBankBranch(merchantInfo.getBankBranch());
	 //			mbc.setPayBankId(merchantInfo.getPayBankId());
	 //			mbc.setMid(merchant.getMid());
	 //			merchantTaskDetailDao.saveObject(mbc);
	 //		}else{
	 //			StringBuffer mid = new StringBuffer("864");
	 //			mid.append("0001".trim());
	 //			mid.append("5311");
	 //			Integer seqNo  = searchMIDSeqInfo("000","5311","superadmin");
	 //			DecimalFormat deFormat = new DecimalFormat("0000");
	 //			mid.append(deFormat.format(seqNo));
	 //			merchant.setMid(mid.toString());
	 //			merchant.setMaintainUid(1);		//操作人员
	 //			merchant.setMaintainDate(new Date());			//操作日期
	 //			merchant.setMaintainType("A");		//A-add   M-Modify  D-Delete
	 //			merchant.setSettleStatus("1");		//结算状态 1-正常 2-冻结
	 //			merchant.setAccountStatus("1");	//账户状态 1-正常 2-冻结 3-注销
	 //			merchant.setSettleCycle(0);
	 //			//节假日是否合并结账 0：否 1：是
	 //			merchant.setSettleMerger("1");
	 //			merchant.setApproveStatus("W");
	 //			merchant.setFeeRateA("0");
	 //			merchant.setFeeRateD("0");
	 //			merchant.setFeeRateJ("0");
	 //			merchant.setFeeRateM("0");
	 //			merchant.setFeeRateV("0");
	 //			merchant.setBusinessScope(" ");
	 //			merchant.setMaintainUserId(merchantInfo.getBusid());
	 //			merchant.setLegalType("1");
	 //			merchant.setContactAddress(merchantInfo.getBaddr());
	 //			merchant.setSettleRange("00");
	 //			if(merchantInfo.getMerchantType()==2){
	 //				Double a=Double.parseDouble(merchantInfo.getFeeAmt());
	 //				Double b=Double.parseDouble(merchantInfo.getBankFeeRate());
	 //				merchant.setDealAmt(Math.floor(a/b)+"");
	 //			}
	 //			merchantInfoDao.saveObject(merchant);
	 //			//钱包默认银行卡
	 //			MerchantBankCardModel mbc = new MerchantBankCardModel();
	 //			mbc.setBankAccName(merchantInfo.getBankAccName());
	 //			mbc.setBankAccNo(merchantInfo.getBankAccNo());
	 //			mbc.setCreateDate(new Date());
	 //			mbc.setMerCardType(0);
	 //			mbc.setStatus(0);
	 //			mbc.setBankBranch(merchantInfo.getBankBranch());
	 //			mbc.setPayBankId(merchantInfo.getPayBankId());
	 //			mbc.setMid(merchant.getMid());
	 //			merchantTaskDetailDao.saveObject(mbc);
	 //		}
	 //		
	 //		MerchantTerminalInfoModel merchantTerminalInfoModel = new MerchantTerminalInfoModel();
	 //		merchantTerminalInfoModel.setMid(merchant.getMid());
	 //		merchantTerminalInfoModel.setTid(merchantInfo.getTid());
	 //		merchantTerminalInfoModel.setBmaid(181);
	 //		merchantTerminalInfoModel.setInstalledAddress(merchantInfo.getBaddr());
	 //		merchantTerminalInfoModel.setInstalledName(merchantInfo.getRname());
	 //		merchantTerminalInfoModel.setContactPerson(merchantInfo.getContactPerson());
	 //		merchantTerminalInfoModel.setContactPhone(merchantInfo.getContactPhone());
	 //		merchantTerminalInfoModel.setContactTel(merchantInfo.getContactTel());
	 //		merchantTerminalInfoModel.setInstalledSIM("1");
	 //		
	 //		merchantTerminalInfoModel.setUnno(merchantInfo.getUnno());
	 //		merchantTerminalInfoModel.setApproveStatus("Z");		//默认为待受理 Y-已受理   Z-待受理  K-不受理
	 //		merchantTerminalInfoModel.setMaintainUid(1);
	 //		merchantTerminalInfoModel.setMaintainDate(new Date());
	 //		merchantTerminalInfoModel.setMaintainType("A");		//A-add   M-Modify  D-Delete
	 //		merchantTerminalInfoModel.setStatus(1);
	 //		merchantTerminalInfoDao.saveObject(merchantTerminalInfoModel);
	 //	}

	 public String imagePathUtil(String imageDay){
		 if(imageDay.length()>30){
			 String imagepath=imageDay.substring(23,31);
			 if(imagepath!=null && !"".equals(imagepath) && imagepath.length()>=8){
				 Integer imageInt= Integer.parseInt(imagepath);
				 if(imageInt>20151214){
					 return imagepath;
				 }else{
					 return imageDay.substring(0,6);
				 }
			 }
		 }else{
			 return imageDay.substring(16,24);
		 }
		 return imageDay.substring(0,6);
	 }


	 public HSSFWorkbook queryTidUseing(){
		 String unnoSql=" select t2.unno,t3.agentname,t4.salename from  sys_unit t, bill_terminalinfo t2 " +
				 ", bill_agentunitinfo t3, bill_agentsalesinfo t4 " +
				 "where t.unno=t2.unno and t3.unno=t.unno and t3.signuserid=t4.busid and  t.un_lvl=2   group by t2.unno,t3.agentname,t4.salename ";

		 List<Map<String ,Object>> list = merchantInfoDao.queryObjectsBySqlListMap2(unnoSql, null);
		 List<Map<String,Object>> listSum= new ArrayList<Map<String,Object>>();
		 for(Map<String,Object> map:list){
			 String unno=map.get("UNNO").toString();
			 String sqlCountNoUse=" select count(*)  from  bill_terminalinfo c " +
					 " where unno in( select UNNO from  sys_unit start with unno ='"+unno+"' and status = 1  connect by NOCYCLE prior unno = upper_unit ) and c.usedconfirmdate is null  ";
			 String sqlCountUse=" select count(*)  from  bill_terminalinfo c " +
					 " where unno in( select UNNO from  sys_unit start with unno ='"+unno+"' and status = 1  connect by NOCYCLE prior unno = upper_unit ) and c.usedconfirmdate is not null  ";
			 Integer CountNoUse=merchantInfoDao.querysqlCounts2(sqlCountNoUse, null);
			 Integer CountUse=merchantInfoDao.querysqlCounts2(sqlCountUse, null);
			 map.put("CountNoUse", CountNoUse);
			 map.put("CountUse", CountUse);
		 }
		 List<String> excelHeader = new ArrayList<String>();
		 Map<String, Object> headMap = new HashMap<String, Object>();

		 excelHeader.add("UNNO");
		 excelHeader.add("AGENTNAME");
		 excelHeader.add("SALENAME");
		 excelHeader.add("CountNoUse");
		 excelHeader.add("CountUse");

		 headMap.put("UNNO", "机构编号");
		 headMap.put("AGENTNAME", "机构名称");
		 headMap.put("SALENAME", "销售");
		 headMap.put("CountNoUse", "未使用");
		 headMap.put("CountUse", "已使用");

		 return ExcelUtil.export("代理商终端使用资料", list, excelHeader, headMap);
	 }


	 @Override
	 public Integer queryTidCountByLegalNum(String leaglNum,Integer type,String mid){
		 Map<String,Object> map = new HashMap<String, Object>();
		 if(type==1){
			 //增机,现根据MID查询出身份证号
			 leaglNum=merchantInfoDao.queryLegalNumByMid(mid);
		 }
		 String sql="select count(1) from bill_merchantinfo t, bill_merchantterminalinfo t2 " +
				 "  where t.mid = t2.mid and t.legalnum =:LegalNum and t.maintaintype!='D'" +
				 "  and t.approvestatus !='K' and t2.maintaintype!='D' and t.ism35=1 and t.unno not in (" +
				 "'992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034'," +
				 "'992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107'," +
				 "'992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011'," +
				 "'982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192'," +
				 "'982157','972018','982125','982165','962018')";
		 map.put("LegalNum", leaglNum);
		 return merchantInfoDao.querysqlCounts2(sql, map);
	 }

	 @Override
	 public Integer queryTidCountByLegalNum2(MerchantInfoBean merchantInfoBean,Integer type){
		 Map<String,Object> map = new HashMap<String, Object>();
		 String legalNum = null;
		 if(type==1){
			 //增机,现根据MID查询出身份证号
			 legalNum=merchantInfoDao.queryLegalNumByMid(merchantInfoBean.getMid());
		 }else {
			 legalNum = merchantInfoBean.getLegalNum();
		 }
		 String sql="select count(1) from bill_merchantinfo t, bill_merchantterminalinfo t2 " +
				 "  where t.mid = t2.mid and t.legalnum =:LegalNum and t.maintaintype!='D'" +
				 "  and t.approvestatus !='K' and t2.maintaintype!='D' and t.mid like 'HYBSYT%'";
		 map.put("LegalNum", legalNum);
		 return merchantInfoDao.querysqlCounts2(sql, map);
	 }

	 @Override
	 public Integer queryPlusTidCountByLegalNum(MerchantInfoBean merchantInfoBean,Integer type){
		 Map<String,Object> map = new HashMap<String, Object>();
		 String legalNum = null;
		 if(type==1){
			 //增机,现根据MID查询出身份证号
			 legalNum=merchantInfoDao.queryLegalNumByMid(merchantInfoBean.getMid());
		 }else {
			 legalNum = merchantInfoBean.getLegalNum();
		 }
		 String sql="select count(1) from bill_merchantinfo t, bill_merchantterminalinfo t2 " +
				 "  where t.mid = t2.mid and t.legalnum =:LegalNum and t.maintaintype!='D'" +
				 "  and t.approvestatus !='K' and t2.maintaintype!='D' and t.mid like 'H864%'";
		 map.put("LegalNum", legalNum);
		 return merchantInfoDao.querysqlCounts2(sql, map);
	 }

	@Override
	public Integer queryPROTidCountByLegalNum(MerchantInfoBean merchantInfoBean,Integer type){
		Map<String,Object> map = new HashMap<String, Object>();
		String legalNum = null;
		if(type==1){
			legalNum=merchantInfoDao.queryLegalNumByMid(merchantInfoBean.getMid());
		}else {
			legalNum = merchantInfoBean.getLegalNum();
		}
		String sql="select count(1) from bill_merchantinfo t, bill_merchantterminalinfo t2 " +
				"  where t.mid = t2.mid and t.legalnum =:LegalNum and t.maintaintype!='D'" +
				"  and t.approvestatus !='K' and t2.maintaintype!='D' and t.mid like 'D864%'";
		map.put("LegalNum", legalNum);
		return merchantInfoDao.querysqlCounts2(sql, map);
	}

	 @Override
	 public Integer queryQRidCountByLegalNum(String leaglNum,Integer type,String mid){
		 Map<String,Object> map = new HashMap<String, Object>();
		 if(type==1){
			 //增机,现根据MID查询出身份证号
			 leaglNum=merchantInfoDao.queryLegalNumByMid(mid);
		 }
		 String sql="select count(1) from bill_merchantinfo t where t.legalnum =:LegalNum and t.maintaintype!='D' and t.ism35='6' ";// and t.approvestatus !='K'
		 map.put("LegalNum", leaglNum);
		 return merchantInfoDao.querysqlCounts2(sql, map);
	 }

	 public List<String> csvUtils(String path){
		 List<String> dataList=new ArrayList<String>();
		 BufferedReader br=null;
		 try { 
			 br = new BufferedReader(new FileReader(path));
			 String line = "";
			 while ((line = br.readLine()) != null) {
				 dataList.add(line);
			 }
		 }catch (Exception e) {
		 }finally{
			 if(br!=null){
				 try {
					 br.close();
					 br=null;
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return dataList;
	 }

	 @Override
	 public boolean updateAndUpdateMerchantTerminalInfo(MerchantTerminalInfoBean merchantTerminalInfo) {
		 MerchantTerminalInfoModel merchantTerminalInfoModel = new MerchantTerminalInfoModel();
		 Map<String, Object> param = new HashMap<String, Object>();
		 param.put("MID", merchantTerminalInfo.getMid());
		 param.put("TID", merchantTerminalInfo.getTid());
		 //		String sql = "select * from bill_merchantterminalinfo where MID =:MID and TID =:TID";
		 //		List<MerchantTerminalInfoModel> list  = merchantTerminalInfoDao.queryObjectsBySqlList(sql, param);
		 //		List<MerchantTerminalInfoModel> list=merchantTerminalInfoDao.executeSqlT(sql, MerchantTerminalInfoModel.class, param);
		 String hql = "from MerchantTerminalInfoModel where MID=:MID and TID=:TID";
		 List<MerchantTerminalInfoModel>list = merchantTerminalInfoDao.queryObjectsByHqlList(hql, param);
		 MerchantTerminalInfoModel model = null;
		 model=list.get(0);
		 model.setInstalledAddress(merchantTerminalInfo.getInstalledAddress());
		 model.setInstalledName(merchantTerminalInfo.getInstalledName());
		 model.setContactPerson(merchantTerminalInfo.getContactPerson());
		 model.setContactPhone(merchantTerminalInfo.getContactPhone());
		 model.setMaintainType("M");
		 model.setApproveStatus("Z");
		 Date date = new Date();
		 model.setApproveDate(date);
		 model.setMaintainDate(date);
		 try {
			 merchantTerminalInfoDao.updateObject(model);
		 } catch (Exception e) {
			 return false;
		 }

		 return true;
	 }

	 @Override
	 public List<Map<String, Object>> getRebateType(String mid,String sn) {
		 if (mid==null||"".equals(mid)) {
			 return null;
		 }
		 String sql = "select ir.sn,ir.rebatedate,ir.rebateamt,ir.tpye1,ir.tpye2 from bill_terminalinfo a, check_ism35rebate ir where ir.mid = :mid and ir.sn = :sn and a.sn = ir.sn and a.rebatetype = '4'";
		 Map<String, Object>param = new HashMap<String, Object>();
		 param.put("mid", mid);
		 param.put("sn", sn);
		 List<Map<String, Object>>list = merchantInfoDao.queryObjectsBySqlListMap2(sql, param);
		 return list;
	 }

	 @Override
	 public MerchantInfoModel getMerInfoByMid(String mid) {
		 String sql = "from MerchantInfoModel where maintainType != 'D' and approveStatus = 'Y' and mid = :MID ";
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("MID", mid);
		 List<MerchantInfoModel> list = merchantInfoDao.queryObjectsByHqlList(sql, params);
		 if(list!=null&&!list.isEmpty()){
			 return list.get(0);
		 }
		 return null;
	 }

	 @Override
	 public MerchantInfoModel getMerInfoByMidAll(String mid) {
		 String sql = "from MerchantInfoModel where  mid = :MID ";
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("MID", mid);
		 List<MerchantInfoModel> list = merchantInfoDao.queryObjectsByHqlList(sql, params);
		 if(list!=null&&!list.isEmpty()){
			 return list.get(0);
		 }
		 return null;
	 }

	 @Override
	 public void uploadImage(MerchantInfoModel merchantInfoModel, MerchantInfoBean merchantInfo) {
		 String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		 //上传文件
		 if(merchantInfo.getLegalUploadFile() != null ){
			 //uploadFile2上传到新的文件夹,uploadFile老接口原路径
			 uploadFile(merchantInfo.getLegalUploadFile(),merchantInfoModel.getLegalUploadFileName(),imageDay);
		 }
		 if(merchantInfo.getBupLoadFile() != null ){
			 uploadFile(merchantInfo.getBupLoadFile(),merchantInfoModel.getBupLoad(),imageDay);
		 }
		 if(merchantInfo.getRupLoadFile() != null ){
			 uploadFile(merchantInfo.getRupLoadFile(),merchantInfoModel.getRupLoad(),imageDay);
		 }
		 if(merchantInfo.getRegistryUpLoadFile() != null ){
			 uploadFile(merchantInfo.getRegistryUpLoadFile(),merchantInfoModel.getRegistryUpLoad(),imageDay);
		 }
		 if(merchantInfo.getMaterialUpLoadFile() != null ){
			 uploadFile(merchantInfo.getMaterialUpLoadFile(),merchantInfoModel.getMaterialUpLoad(),imageDay);
		 }
		 if(merchantInfo.getPhotoUpLoadFile() != null ){
			 uploadFile(merchantInfo.getPhotoUpLoadFile(),merchantInfoModel.getPhotoUpLoad(),imageDay);
		 }
		 if(merchantInfo.getBigdealUpLoadFile() != null ){
			 uploadFile(merchantInfo.getBigdealUpLoadFile(),merchantInfoModel.getBigdealUpLoad(),imageDay);
		 }
		 if(merchantInfo.getMaterialUpLoad1File() != null ){
			 uploadFile(merchantInfo.getMaterialUpLoad1File(),merchantInfoModel.getMaterialUpLoad1(),imageDay);
		 }
		 if(merchantInfo.getMaterialUpLoad2File() != null ){
			 uploadFile(merchantInfo.getMaterialUpLoad2File(),merchantInfoModel.getMaterialUpLoad2(),imageDay);
		 }
		 if(merchantInfo.getMaterialUpLoad3File() != null ){
			 uploadFile(merchantInfo.getMaterialUpLoad3File(),merchantInfoModel.getMaterialUpLoad3(),imageDay);
		 }
		 if(merchantInfo.getMaterialUpLoad4File() != null ){
			 uploadFile(merchantInfo.getMaterialUpLoad4File(),merchantInfoModel.getMaterialUpLoad4(),imageDay);
		 }
		 if(merchantInfo.getMaterialUpLoad5File() != null ){
			 uploadFile(merchantInfo.getMaterialUpLoad5File(),merchantInfoModel.getMaterialUpLoad5(),imageDay);
		 }
		 if(merchantInfo.getMaterialUpLoad6File() != null ){
			 uploadFile(merchantInfo.getMaterialUpLoad6File(),merchantInfoModel.getMaterialUpLoad6(),imageDay);
		 }
		 if(merchantInfo.getMaterialUpLoad7File() != null ){
			 uploadFile(merchantInfo.getMaterialUpLoad7File(),merchantInfoModel.getMaterialUpLoad7(),imageDay);
		 }
		 if(merchantInfo.getPosBackImgFile() != null ){
			 uploadFile(merchantInfo.getPosBackImgFile(),merchantInfoModel.getPosBackImg(),imageDay);
		 }
		 if(merchantInfo.getLaborContractImgFile() != null ){
			 uploadFile(merchantInfo.getLaborContractImgFile(),merchantInfoModel.getLaborContractImg(),imageDay);
		 }

	 }

	 /**
	  * 年龄限制18-60
	  */
	 @Override
	 public boolean queryAge(MerchantInfoBean merchantInfo) {
		 try {
			 String num = merchantInfo.getLegalNum();
			 int year = Integer.parseInt(num.substring(6, 10));
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			 Date update = sdf.parse(String.valueOf(year + 18) + num.substring(10, 14));
			 Date update2 = sdf.parse(String.valueOf(year + 60) + num.substring(10, 14));
			 Date today = new Date();
			 return today.after(update)&&update2.after(today);
		 } catch (Exception e) {
			 log.error("秒到报单年龄限制错误：legalNum="+merchantInfo.getLegalNum());
		 }
		 return false;
	 }

	 /**
	  * 同一身份证注册秒到不能超过3次
	  */
	 @Override
	 public boolean queryIsRegist(MerchantInfoBean merchantInfo) {
		 try {
			 if(PhoneProdConstant.PHONE_MD.equals(merchantInfo.getAgentId())) {//秒到添加拦截，其余不拦
				 String sql = "select count(1) from bill_merchantinfo a where ism35=1 and bno!='10002' and settmethod!='000000' and a.legalnum='"+merchantInfo.getLegalNum()+"' and NOT exists (SELECT * FROM WX b WHERE a.unno = b.unno ) and a.mid like '864%'";
				 Integer i = merchantInfoDao.querysqlCounts2(sql, null);
				 if(i<5) {
					 return true;
				 }
			 }else if(PhoneProdConstant.PHONE_SYT.equals(merchantInfo.getAgentId())) {//收银台添加拦截，其余不拦
				 String sql = "select count(1) from bill_merchantinfo a where a.legalnum='"+merchantInfo.getLegalNum()+"' and a.mid like 'HYBSYT%'";
				 Integer i = merchantInfoDao.querysqlCounts2(sql, null);
				 if(i<5) {
					 return true;
				 }
			 }else {
				 return true;
			 }
		 } catch (Exception e) {
			 log.error("已经注册的身份证不能报单：legalNum="+merchantInfo.getLegalNum());
		 }
		 return false;
	 }

	 @Override
	 public boolean queryIsExistInHrtUnno(MerchantInfoBean merchantInfo) {
		 // @author:xuegangliu-20190327 限制添加
		 //		10000  秒到 x 限制
		 //		10002  联刷 x 限制
		 //		10005  收银台 通过
		 //		10004  亿米 通过
		 if(".10005.10004.".contains("."+merchantInfo.getAgentId()+".")){
			 return false;
		 }
//		 String existSql = "select count(1) from  bill_merchantinfo a where " +
//				 " a.legalnum='"+merchantInfo.getLegalNum()+"' and a.unno in ('992107','982125','982058')";
		 String existSql="SELECT count(1) FROM BILL_IDCARD_TXN T WHERE T.LEGALNUM='"+merchantInfo.getLegalNum()+"' AND T.TYPE=4 AND T.TXNDAY>=to_char(TRUNC(SYSDATE - 90, 'dd'), 'yyyyMMdd')";
		 Integer i = merchantInfoDao.querysqlCounts2(existSql, null);
		 if(i>0){
			 return true;
		 }else{
			 return false;
		 }
	 }

	 @Override
	 public Integer deleteMposMer(MerchantInfoBean merchantInfo, UserBean userBean) {
		 String sql = "from MerchantInfoModel where maintainType != 'D' and approveStatus = 'Y' and mid = ? ";
		 MerchantInfoModel model = merchantInfoDao.queryObjectByHql(sql, new Object[]{merchantInfo.getMid()});
		 Map<String,String> params = new HashMap<String, String>();
		 if(model.getBaddr().contains("联刷")){
			 params.put("agentId",PhoneProdConstant.PHONE_LS);//连刷
		 }else if("000000".equals(model.getSettMethod())){
			 params.put("agentId","0");//理财
		 }else if(merchantInfo.getMid().contains("HRTSYT")){
			 // @author:lxg-20190731 解绑放开收银台与plus
			 params.put("agentId",PhoneProdConstant.PHONE_SYT);
		 }else if(merchantInfo.getMid().contains("H864")){
			 params.put("agentId",PhoneProdConstant.PHONE_PLUS);
		 }else if(PhoneProdConstant.PHONE_PRO.equals(model.getBno())){
			 params.put("agentId",PhoneProdConstant.PHONE_PRO);
		 }else{
             // @author:lxg-20191120 亿米的按照秒到处理会失败 10004 亿米的两个机构。有一个一代机构982058，没有中心机构。另外一个是中心机构：b11023
             String sqlUnno="select unno from (select unno from sys_unit start with unno in ('982058','b11023') connect by prior unno = upper_unit) where unno=:unno";
             Map t=new HashMap();
             t.put("unno",model.getUnno());
			 List<UnitModel> listUnno = unitDao.queryObjectsBySqlList(sqlUnno,t);
			 if(listUnno.size()>0){
				 // 亿米
				 params.put("agentId", PhoneProdConstant.PHONE_YMF);
			 }else {
				 params.put("agentId", PhoneProdConstant.PHONE_MD);//秒到
			 }
		 }
		 params.put("mid",merchantInfo.getMid());
		 try {
			 // @author:lxg-20190903 商户注销推送综合
			 Map<String,String> map2 = new HashMap<String, String>();
			 map2.put("type", "1");
			 map2.put("mid", merchantInfo.getMid());
			 String receiveUpBD = HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/merchant_receiveUpBD.action", map2);
			 boolean flag = (new JSONObject(receiveUpBD)).getBoolean("success");
			 if(!flag){
				 return 0;
			 }

			 String post = HttpXmlClient.post(hybCube+"/merchant/userCancellation.do", params);
			 JSONObject jsonObject = new JSONObject(post);
			 String status =jsonObject.get("status")+"";
			 if("0".equals(status)){
				 model.setNonConnection("D");//注销
			 }else{
				 return 0;
			 }

		 } catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		 }
		 return 1;
	 }


	 /*
	  * 查询业务员开通商户数汇总
	  * @author yq
	  */
	 public DataGridBean queryYwyMerchantinfo(MerchantInfoBean merchantInfo, UserBean user) throws Exception{
		 String hql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(hql, usermap);

		 String sql = "select unno,un_name,sum(sytmer)sytmer,sum(ssmer)ssmer,sum(ctmer)ctmer,salename from check_merchantinfo ";
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 Map<String, Object> map = new HashMap<String, Object>();
		 //非业务员入口
		 if(agentSalesModels.size()==0){
			 //总公司
			 if("110000".equals(user.getUnNo())){
				 sql += "where 1=1 ";
				 //总公司下部门
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){	
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql += "where 1=1 ";
				 }
				 //运营中心报单员	
			 }else if(isAgentformMan(user)){
				 return null;
				 //运营中心管理员
			 }else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				 sql += "where unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
				 map.put("unno1",user.getUnNo());
			 }
			 //业务员入口
		 }else{
			 sql += "where busid = :busid ";
			 map.put("busid",agentSalesModels.get(0).getBusid());
		 }
		 //日期
		 if(merchantInfo.getCreateDateStart() != null && !"".equals(merchantInfo.getCreateDateStart()) 
				 && merchantInfo.getCreateDateEnd() != null && !"".equals(merchantInfo.getCreateDateEnd()) ){
			 String startDate = merchantInfo.getCreateDateStart().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
			 String endDate = merchantInfo.getCreateDateEnd().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");                                             
			 sql += "and to_date(openDay, 'yyyymmdd') >= to_date ( :startDate ,'yyyymmdd') and to_date(openDay, 'yyyymmdd') < to_date ( :endDate ,'yyyymmdd') + interval'1'day ";
			 map.put("startDate", startDate);
			 map.put("endDate", endDate);
		 }
		 //机构号
		 if(merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno())){
			 sql += "and unno = :unno ";
			 map.put("unno", merchantInfo.getUnno());
		 }
		 sql += "group by unno,un_name,salename ORDER BY ssmer DESC";
		 String sqlCount = "select count(1) from (" +sql+ ")";
		 List<Map<String,String>> list = merchantInfoDao.queryObjectsBySqlList(sql, map, merchantInfo.getPage(), merchantInfo.getRows());
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 DataGridBean dgb = new DataGridBean();
		 dgb.setRows(list);
		 dgb.setTotal(counts.intValue());
		 return dgb;
	 }

	 /*
	  * 查询业务员开通商户数导出
	  * @author yq
	  */
	 public List<Map<String, Object>> exportYwyMerchantinfo(MerchantInfoBean merchantInfo, UserBean user) throws Exception{
		 String hql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
		 Map<String, Object> usermap = new HashMap<String, Object>();
		 usermap.put("userID", user.getUserID().toString());
		 usermap.put("maintainType", "D");
		 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(hql, usermap);

		 String sql = "select unno,un_name,sum(sytmer)sytmer,sum(ssmer)ssmer,sum(ctmer)ctmer,salename from check_merchantinfo ";
		 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
		 Map<String, Object> map = new HashMap<String, Object>();
		 //非业务员入口
		 if(agentSalesModels.size()==0){
			 //总公司
			 if("110000".equals(user.getUnNo())){
				 sql += "where 1=1 ";
				 //总公司下部门
			 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){	
				 UnitModel parent = unitModel.getParent();
				 if("110000".equals(parent.getUnNo())){
					 sql += "where 1=1 ";
				 }
				 //运营中心报单员	
			 }else if(isAgentformMan(user)){
				 return null;
				 //运营中心管理员
			 }else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
				 sql += "where unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
				 map.put("unno1",user.getUnNo());
			 }else{
				 return null;
			 }
			 //业务员入口
		 }else{
			 sql += "where busid = :busid ";
			 map.put("busid",agentSalesModels.get(0).getBusid());
		 }
		 //日期
		 if(merchantInfo.getCreateDateStart() != null && !"".equals(merchantInfo.getCreateDateStart()) 
				 && merchantInfo.getCreateDateEnd() != null && !"".equals(merchantInfo.getCreateDateEnd()) ){
			 String startDate = merchantInfo.getCreateDateStart().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
			 String endDate = merchantInfo.getCreateDateEnd().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
			 sql += "and to_date(openDay, 'yyyymmdd') >= to_date ( :startDate ,'yyyymmdd') and to_date(openDay, 'yyyymmdd') < to_date ( :endDate ,'yyyymmdd') + interval'1'day ";
			 map.put("startDate", startDate);
			 map.put("endDate", endDate);
		 }
		 //机构号
		 if(merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno())){
			 sql += "and unno = :unno ";
			 map.put("unno", merchantInfo.getUnno());
		 }
		 sql += "group by unno,un_name,salename ORDER BY ssmer DESC";
		 List<Map<String, Object>> list = merchantInfoDao.executeSql2(sql,map);
		 return list;
	 }

	 @Override
	 public List<Map> updateImportMicroToUnbid(String xlsfile, String name, UserBean user) {
		 List<Map> list = new ArrayList<Map>(16);
		 Map errMap = new HashMap(16);

		 int sumCount = 0;
		 File filename = new File(xlsfile);
		 HSSFWorkbook workbook = null;
		 Map<String,String> midSn = new HashMap<String,String>();
		 try {
			 workbook = new HSSFWorkbook(new FileInputStream(filename));
		 } catch (IOException e) {
			 log.info("导入批量解绑文件异常:"+e.getMessage());
		 }
		 HSSFSheet sheet = workbook.getSheetAt(0);
		 sumCount = sheet.getPhysicalNumberOfRows()-1;
		 for(int i = 1; i <= sumCount; i++) {
			 HSSFRow row = sheet.getRow(i);
			 HSSFCell cell = row.getCell((short) 0);
			 if (cell == null || cell.toString().trim().equals("")) {
				 break;
			 } else {
				 // 商户mid
				 row.getCell((short) 0).setCellType(Cell.CELL_TYPE_STRING);
				 String mid = row.getCell((short) 0).getStringCellValue().trim();

				 // 终端sn
				 row.getCell((short) 1).setCellType(Cell.CELL_TYPE_STRING);
				 String sn = row.getCell((short) 1).getStringCellValue().trim();
				 String uniq=mid+sn;
				 // 剔除重复的mid与sn
				 if(!midSn.containsKey(uniq)) {
					 int result = updateUnbindSnNew(sn, mid);
					 if (result != 1) {
						 errMap.put("mid",mid);
						 errMap.put("sn",sn);
						 errMap.put("errorInfo",result==-1?"已激活设备不能解绑":"解绑失败");
						 list.add(errMap);
					 }
				 }
				 midSn.put(uniq,"");
			 }
		 }
		 return list;
	 }

	 /**
	  * 解绑商户设备
	  * @param sn
	  * @param mid
	  * @return 1:解绑成功 0:解绑失败 -1:已激活设备不能解绑
	  */
	 private int updateUnbindSnNew(String sn,String mid){
		 int result = 0;
		 boolean isSytOrPlus = StringUtils.isNotEmpty(mid) && (mid.contains("SYT") || mid.contains("H864"));

		 String terInfoHql = "from TerminalInfoModel t where t.sn=?";
		 TerminalInfoModel terminalInfoModel = terminalInfoDao.queryObjectByHql(terInfoHql,new Object[]{sn});
		 String tid = terminalInfoModel==null?"":terminalInfoModel.getTermID();

		 String merTerHql = "from MerchantTerminalInfoModel t where t.mid=? and t.tid=?";
		 MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.queryObjectByHql(merTerHql,new Object[]{mid,tid});

		 if(terminalInfoModel==null || merchantTerminalInfoModel==null){
			 return 0;
		 }

		 // 收银台及PLUS产品已激活设备不可解绑
		 if(isSytOrPlus && merchantTerminalInfoModel.getDepositFlag().intValue()==6){
			 return -1;
		 }

		 terminalInfoModel.setUsedConfirmDate(null);
		 terminalInfoModel.setMaintainDate(null);
		 terminalInfoModel.setStatus(1);

		 try {
			 //给会员宝后台响应解绑信息。
			 Map<String,String> params = new HashMap<String, String>();
			 params.put("mid",mid);
			 params.put("tid", tid);
			 params.put("sn", sn);
			 String ss=HttpXmlClient.post(admAppIp+"/AdmApp/hybmove/gathering_removeMpos.action", params);
			 log.info("sn解绑通知综合后台服务器"+ss);
		 } catch (Exception e) {
			 log.info("sn解绑异常"+e.getMessage());
			 e.printStackTrace();
		 }finally{
			 terminalInfoDao.updateObject(terminalInfoModel);
			 merchantTerminalInfoModel.setMaintainType("D");
			 merchantTerminalInfoModel.setMaintainDate(new Date());
			 //			merchantTerminalInfoDao.deleteObject(merchantTerminalInfoModel);
			 merchantTerminalInfoDao.updateObject(merchantTerminalInfoModel);
			 result=1;
		 }
		 return result;
	 }

	 /**
	  * plus同步的商户信息获取
	  * @param remarks
	  * @param type 1:plus同步商户信息获取(所有产品) 2:极速版秒到同步商户获取(只获取秒到商户)
	  * @return
	  */
	 public List<MerchantInfoModel> getMerchantInfoByRemarks(String remarks,int type){
		 List<MerchantInfoModel> resultList = new ArrayList<MerchantInfoModel>();
		 if(StringUtils.isEmpty(remarks)){
			 return null;
		 }
		 JSONArray jsonArray = JSON.parseArray(remarks);
		 String hql= "from MerchantInfoModel t where t.mid=? ";
		 for (int i=0,n=jsonArray.size();i<n;i++) {
			 if(!jsonArray.getJSONObject(i).containsKey("mid")){
				 continue;
			 }
			 String mdBno = jsonArray.getJSONObject(i).get("agentId").toString();
			 if(type==2 && !PhoneProdConstant.PHONE_MD.equals(mdBno)){
				 continue;
			 }
			 String mid = jsonArray.getJSONObject(i).get("mid").toString();
			 List<MerchantInfoModel> list = merchantInfoDao.queryObjectsByHqlList(hql,new Object[]{mid});
			 int sum=list.size();
			 if(sum>0){
				 for (MerchantInfoModel infoModel : list) {
					 MerchantInfoModel b1 = new MerchantInfoModel();
					 BeanUtils.copyProperties(infoModel,b1);
					 b1.setBno(jsonArray.getJSONObject(i).get("agentId").toString());
					 b1.setRemarks(jsonArray.getJSONObject(i).containsKey("productName")?jsonArray.getJSONObject(i).get("productName").toString():"");
					 resultList.add(b1);
					 if(type==2 && PhoneProdConstant.PHONE_MD.equals(mdBno)){
						 break;
					 }
				 }
			 }
		 }
		 //		1.plus同步：秒到、联刷、收银台数据
		 //		2.信用卡可以绑定多张，只过来第一张（信用卡认证的那张）
		 //		3.小风险：那张信用卡已经不用了，但是仍然可以过
		 //		4.小风险：有效期到了，也会被同步（信用卡+身份证）
		 return resultList;
	 }

	 /**
	  * 是否存在某产品的商户(除去注销的商户)
	  * @param phone
	  * @param agentId
	  * @return
	  */
	 public String isExistPhoneAndAgentId(String phone,String agentId){
		 String hql= "from MerchantInfoModel t where t.hybPhone=? and t.bno=? and (t.nonConnection!='D' or t.nonConnection is null)";
		 List<MerchantInfoModel> list = merchantInfoDao.queryObjectsByHqlList(hql,new Object[]{phone,agentId});
		 return list.size()>0?"您已注册过该产品，不允许重复注册！":null;
	 }

	 public synchronized MerchantInfoModel saveSyncMerchantInfoByPhoneToNewPlus(MerchantInfoBean merchantInfoBean,String agentId){
		 String errIsExist = isExistPhoneAndAgentId(merchantInfoBean.getHybPhone(),agentId);
		 if(StringUtils.isNotEmpty(errIsExist)){
			 MerchantInfoModel err = new MerchantInfoModel();
			 err.setRemarks("ERROR");
			 return err;
		 }

		 MerchantInfoModel model = getInfoModel(merchantInfoBean.getBmid());
		 MerchantInfoModel newModel = new MerchantInfoModel();
		 BeanUtils.copyProperties(model,newModel);
		 // 存在同步已注销的商户
		 newModel.setNonConnection("");
		 String mid1="";
		 if(PhoneProdConstant.PHONE_PLUS.equals(agentId)){
			 mid1 = savePlusMerchantMid();
			 newModel.setMid(mid1);
		 }else if(PhoneProdConstant.PHONE_JS.equals(agentId) || PhoneProdConstant.PHONE_MD.equals(agentId)){
			 mid1 = saveSpeedMdMerchantMid();
			 newModel.setMid(mid1);
		 }else if(PhoneProdConstant.PHONE_PRO.equals(agentId)){
			 mid1 = savePROMerchantMid();
			 newModel.setMid(mid1);
		 }

		 newModel.setBmid(null);
		 newModel.setUnno("000000");
		 newModel.setBno(agentId);
		 newModel.setApproveStatus("Z");
		 newModel.setBankArea(null);
		 newModel.setBaddr(merchantInfoBean.getRaddr());
		 newModel.setRaddr(merchantInfoBean.getRaddr());
		 newModel.setContactAddress(merchantInfoBean.getRaddr());
		 newModel.setParentMID(model.getMid());
		 newModel.setHybPhone(merchantInfoBean.getHybPhone());
		 newModel.setShortName(merchantInfoBean.getSendCode());
		 newModel.setEmail(merchantInfoBean.getEmail());
		 newModel.setPrintName("同步");
		 newModel.setSettMethod("100000");
		 Date date = new Date();
		 newModel.setMaintainDate(date);
		 newModel.setJoinConfirmDate(date);
		 newModel.setApproveDate(date);
		 String sign="";
		 for(int i=0;i<10;i++){
			 sign=sign+String.valueOf((char)(Math.random()*26+65));
		 }
		 newModel.setQrUrl(ParamPropUtils.props.get("xpayPath")+"/qrpayment?mid="+mid1+"&sign="+sign);

		 MerchantBankCardModel mbc = new MerchantBankCardModel();
		 mbc.setBankAccName(newModel.getBankAccName());
		 mbc.setBankAccNo(newModel.getBankAccNo());
		 mbc.setCreateDate(new Date());
		 mbc.setMerCardType(0);
		 mbc.setStatus(0);
		 mbc.setMid(newModel.getMid());
		 mbc.setBankBranch(newModel.getBankBranch());
		 mbc.setPayBankId(newModel.getPayBankId());
		 phoneMicroMerchantInfoDao.saveObject(mbc);
		 merchantInfoDao.saveObject(newModel);
		 return newModel;
	 }

	 /**
	  * 极速秒到商户mid获取
	  * @return
	  */
	 private synchronized String saveSpeedMdMerchantMid(){
		 StringBuilder mid = new StringBuilder(20);
		 mid.append("864");
		 String mrcioRule=phoneMicroMerchantInfoDao.querySavePath("MicroRule");
		 mid.append(mrcioRule.trim());
		 mid.append("5311");
		 Integer seqNo = searchMIDSeqInfo("000","5311","xxx");
		 DecimalFormat deFormat = new DecimalFormat("0000");
		 mid.append(deFormat.format(seqNo));
		 return mid.toString();
	 }

	 /**
	  * 添加导出记录
	  */
	 @Override
	 public void saveExportLog(int type, int rows, Integer userId) {
		 String sql = "insert into bill_exportLog(type,sysuser,cdate,totals) values('"+type+"','"+userId+"',sysdate,"+rows+")";
		 merchantInfoDao.executeUpdate(sql);
	 }

	 /**
	  * 查看导出记录
	  */
	 @Override
	 public DataGridBean queryExportLog(MerchantInfoBean merchantInfo){
		 Map<String, Object> map = new HashMap<String, Object>();
		 String sql = "select a.*,(select login_name from sys_user where user_id = a.sysuser)USERNAME from bill_exportLog a where 1=1 ";
		 if(merchantInfo.getStartDate()!=null && !"".equals(merchantInfo.getStartDate())){
			 sql += "and cdate between to_date(:startDate,'yyyymmdd') and to_date(:endDate,'yyyymmdd') + 1 ";
			 map.put("startDate",merchantInfo.getStartDate().replace("-", ""));
			 map.put("endDate", merchantInfo.getEndDate().replace("-", ""));
		 }else{
			 sql += "and trunc(cdate) = trunc(sysdate) ";
		 }
		 if(merchantInfo.getMerchantType()!=null && !"".equals(merchantInfo.getMerchantType())){
			 sql += "and type = :type ";
			 map.put("type", merchantInfo.getMerchantType());
		 }
		 sql += "order by cdate desc";
		 List<Map<String, String>> list = merchantInfoDao.queryObjectsBySqlList(sql,map,merchantInfo.getPage(),merchantInfo.getRows());
		 String sqlCount = "select count(*) from ("+sql+") ";
		 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
		 DataGridBean dg = new DataGridBean();
		 dg.setRows(list);
		 dg.setTotal(counts.intValue());
		 return dg;
	 }
	 
	 /**
	  * 导出-导出记录
	  */
	 @Override
	 public List<Map<String, Object>> export10972(MerchantInfoBean merchantInfo){
		 Map<String, Object> map = new HashMap<String, Object>();
		 String sql = "select TO_CHAR(a.CDATE,'YYYYMMDD')CDATE,"
		 		+ "CASE WHEN A.TYPE = '1' THEN '商户入网资料导出' WHEN TYPE = '2' THEN '代理商资料导出' "
		 		+ "WHEN TYPE = '3' THEN '运营中心资料导出' ELSE '' END TYPE,"
		 		+ "A.TOTALS,(select login_name from sys_user where user_id = a.sysuser)USERNAME from bill_exportLog a where 1=1 ";
		 if(merchantInfo.getStartDate()!=null && !"".equals(merchantInfo.getStartDate())){
			 sql += "and cdate between to_date(:startDate,'yyyymmdd') and to_date(:endDate,'yyyymmdd') + 1 ";
			 map.put("startDate",merchantInfo.getStartDate().replace("-", ""));
			 map.put("endDate", merchantInfo.getEndDate().replace("-", ""));
		 }else{
			 sql += "and trunc(cdate) = trunc(sysdate) ";
		 }
		 if(merchantInfo.getMerchantType()!=null && !"".equals(merchantInfo.getMerchantType())){
			 sql += "and type = :type ";
			 map.put("type", merchantInfo.getMerchantType());
		 }
		 sql += "order by cdate desc";
		 List<Map<String, Object>> list = merchantInfoDao.queryObjectsBySqlListMap2(sql,map);
		 return list;
	 }
	 
	 /*
		 * 人工实名认证----线下导入
		 */
		@Override
		public List<Map<String, String>> ImportAuthType(String xlsfile, String fileName, UserBean user) {
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			try {
				File filename = new File(xlsfile);
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
				HSSFSheet sheet = workbook.getSheetAt(0);
				List<Object> arrayList = new ArrayList<Object>();
				for(int i = 1; i < sheet.getLastRowNum()+1; i++){
					HSSFRow row = sheet.getRow(i);
					String mid="";
					String bankAccNo="";
					String bankAccName="";
					String legalNum="";
					String authTypeStatus="";
					boolean flag = false;
					HSSFCell cell = row.getCell((short)0);
					if(cell == null || cell.toString().trim().equals("")){
						break;
					}else{
						//商户号
						row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
						mid = row.getCell((short)0).getStringCellValue().trim();
						//卡号
						row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
						bankAccNo = row.getCell((short)1).getStringCellValue().trim();
						//名称
						row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
						bankAccName = row.getCell((short)2).getStringCellValue().trim();
						//身份证
						row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
						legalNum = row.getCell((short)3).getStringCellValue().trim();
						//操作
						row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
						authTypeStatus = row.getCell((short)4).getStringCellValue().trim();
						if("".equals(mid)||"".equals(bankAccNo)||"".equals(bankAccName)||"".equals(legalNum)||"".equals(authTypeStatus)
								||mid==null||bankAccNo==null||bankAccName==null||legalNum==null||authTypeStatus==null) {
							Map<String,String> map = new HashMap<String,String>();
							map.put("mid", mid);
							map.put("bankAccNo", bankAccNo);
							map.put("bankAccName", bankAccName);
							map.put("legalNum", legalNum);
							map.put("authTypeStatus", authTypeStatus);
							map.put("remark", "请确认是否所有信息都已填写");
							list.add(map);
							continue;
						}
						
						if(!"待入账".equals(authTypeStatus)){
							Map<String,String> map = new HashMap<String,String>();
							map.put("mid", mid);
							map.put("bankAccNo", bankAccNo);
							map.put("bankAccName", bankAccName);
							map.put("legalNum", legalNum);
							map.put("authTypeStatus", authTypeStatus);
							map.put("remark", "操作信息填写有误");
							list.add(map);
							continue;
						}
						
						String judgeUpdateStatus = judgeUpdateStatus(mid,legalNum);
						if("可导入".equals(judgeUpdateStatus)) {
							
							String authSql = " select a.* from  bill_merauthinfo a"+
									" where a.bmatid = ( SELECT max(t.bmatid) FROM  bill_merauthinfo t"+
									" where 1=1 and t.authtype = 'MER' and t.mid = '"+mid+"')";
							//可导入状态，将认证表中最大bmatid查询出来
							List<Map<String,Object>> authList = merchantInfoDao.executeSql2(authSql, null);
							Map<String, Object> authMap = authList.get(0);
							String USERNAME = authMap.get("USERNAME")==null?"":authMap.get("USERNAME").toString();
							String BANKACCNAME = authMap.get("BANKACCNAME")==null?"":authMap.get("BANKACCNAME").toString();
							String ACCNOEXPDATE = authMap.get("ACCNOEXPDATE")==null?"":authMap.get("ACCNOEXPDATE").toString();
							String SENDTYPE = authMap.get("SENDTYPE")==null?"":authMap.get("SENDTYPE").toString();
							String insertAuthSql = "insert into bill_merauthinfo t"+
									" (t.bmatid,t.username,t.mid,t.legalnum,t.bankaccno,t.bankaccname,t.accnoexpdate,"+
									" t.respinfo,t.cdate,t.authtype,t.sendtype)"+
									" values"+
									" (S_BILL_MERAUTHINFO.NEXTVAL,'"+USERNAME+"','"+mid+"','"+legalNum+"','"+bankAccNo+"',"+
									" '"+BANKACCNAME+"','"+ACCNOEXPDATE+"','线下导入',sysdate,'MER','"+SENDTYPE+"')";
							Integer update = merchantInfoDao.executeSqlUpdate(insertAuthSql, null);
							
							if(update < 1) {
								Map<String,String> map = new HashMap<String,String>();
								map.put("mid", mid);
								map.put("bankAccNo", bankAccNo);
								map.put("bankAccName", bankAccName);
								map.put("legalNum", legalNum);
								map.put("authTypeStatus", authTypeStatus);
								map.put("remark", "线下导入失败");
								list.add(map);
								continue;
							}
							Integer executeHql = merchantInfoDao.executeHql("update MerchantInfoModel m set m.authType=1 where m.mid=?",new Object[]{mid});
							
							if(executeHql < 1) {
								Map<String,String> map = new HashMap<String,String>();
								map.put("mid", mid);
								map.put("bankAccNo", bankAccNo);
								map.put("bankAccName", bankAccName);
								map.put("legalNum", legalNum);
								map.put("authTypeStatus", authTypeStatus);
								map.put("remark", "线下导入失败");
								list.add(map);
								continue;
							}else {
								Map<String,String> map = new HashMap<String,String>();
								map.put("mid", mid);
								map.put("bankAccNo", bankAccNo);
								map.put("bankAccName", bankAccName);
								map.put("legalNum", legalNum);
								map.put("authTypeStatus", authTypeStatus);
								map.put("remark", "线下导入成功");
								list.add(map);
								continue;
							}
						}else {
							Map<String,String> map = new HashMap<String,String>();
							map.put("mid", mid);
							map.put("bankAccNo", bankAccNo);
							map.put("bankAccName", bankAccName);
							map.put("legalNum", legalNum);
							map.put("authTypeStatus", authTypeStatus);
							map.put("remark", judgeUpdateStatus);
							list.add(map);
							continue;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
	 
		/**
		 * 判断线下导入是否符合规则
		 * 
		 * */
		public String judgeUpdateStatus(String mid,String legalNum) {
			String sql1 = "SELECT * FROM BILL_MERCHANTINFO WHERE 1=1 and MID = '"+mid+"' and MAINTAINTYPE != 'D'";
//			String sql2 = "SELECT * FROM BILL_MERAUTHINFO WHERE 1=1 and MID = '"+mid+"' and AUTHTYPE = 'MER'"
//					+" and BMATID = (SELECT max(BMATID) FROM  BILL_MERAUTHINFO where MID = '"+mid+"'"
//					+" and AUTHTYPE = 'MER')";
//			List<Map<String,String>> authList = merchantInfoDao.executeSql(sql2);
//			String status = authList.get(0).get("STATUS");
//			String respcd = authList.get(0).get("RESPCD"); 
			List<Map<String,Object>> proList = merchantInfoDao.executeSql2(sql1, null);
			if(proList.size()<1) {
				return "无此商户，导入失败";
			}else {
				String sql=" select count(1) from bill_merauthinfo t where t.status='00' and t.respcd='2000' and MID = '"+mid+"'";
				Integer yesCount=merchantInfoDao.querysqlCounts2(sql, null);
				if(yesCount>=1) {
					return "此商户已认证成功，导入失败";
				}
				
				String sql3=" select count(1) from bill_merauthinfo t where t.AUTHTYPE ='MER' and MID = '"+mid+"'";
				Integer merAuth=merchantInfoDao.querysqlCounts2(sql3, null);
				if(merAuth < 1) {
					return "此商户无商户认证记录，不可进行线下导入";
				}
				
				Map<String, Object> map = proList.get(0);
				String string = map.get("AUTHTYPE").toString();
				if(null == string || "0".equals(string)) {
					return "此商户未经过通道认证过，导入失败";
				}else if("2".equals(string)) {
					return "此商户已认证成功，导入失败";
				}else if("1".equals(string)) {
					return "此商户已处于待补入账状态，导入失败";
				}else if("3".equals(string)) {
					return "此商户已处于补入账中，导入失败";
				}else if("4".equals(string)) {
					return "可导入";
				}else {
					return "商户状态不正确，导入失败";
				}
			}
		}
		
		/**
		 * 会员宝Pro入网报单查询
		 */
		 @Override
		 public DataGridBean queryMerchantMicroInfoZKPro(MerchantInfoBean merchantInfo,UserBean user) {
			 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
			 Map<String, Object> usermap = new HashMap<String, Object>();
			 usermap.put("userID", user.getUserID().toString());
			 usermap.put("maintainType", "D");
			 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);

			 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
			 String sql = "";
			 String sqlCount = "";
			 Map<String, Object> map = new HashMap<String, Object>();
			 if(agentSalesModels.size()==0){
				 if("110000".equals(user.getUnNo())){
					 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
					 map.put("maintainType", "D");
					 map.put("approveStatus", "Y");
				 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
					 UnitModel parent = unitModel.getParent();
					 if("110000".equals(parent.getUnNo())){
						 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
						 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
						 map.put("maintainType", "D");
						 map.put("approveStatus", "Y");
					 }
				 }else if(isAgentformMan(user)){//判断是否是代理商报单员
					 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID AND ISM35=1 and mid like 'D864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND MaintainUID = :MaintainUID AND ISM35=1 and mid like 'D864%' ";
					 map.put("unno", user.getUnNo());
					 map.put("maintainType", "D");
					 map.put("approveStatus", "Y");
					 map.put("MaintainUID", user.getUserID());
				 }else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
					 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = :unno OR UNNO = :unno AND STATUS = 1) AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
					 map.put("unno", user.getUnNo());
					 map.put("maintainType", "D");
					 map.put("approveStatus", "Y");

				 }else{
					 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE UNNO = :unno AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
					 map.put("unno", user.getUnNo());
					 map.put("maintainType", "D");
					 map.put("approveStatus", "Y");
				 }
			 }else{
				 sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = :maintainUserId AND MAINTAINTYPE != :maintainType AND APPROVESTATUS != :approveStatus AND ISM35=1 and mid like 'D864%' ";
				 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
				 map.put("maintainType", "D");
				 map.put("approveStatus", "Y");
			 }

			 if(merchantInfo.getMid()!=null && !"".equals(merchantInfo.getMid())){
				 sql += " and MID=:MID";
				 sqlCount += " and MID=:MID";
				 map.put("MID", merchantInfo.getMid());
			 }
			 if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
				 sql += " and RNAME LIKE '"+merchantInfo.getRname()+"%'";
				 sqlCount += " and RNAME LIKE '"+merchantInfo.getRname()+"%'";
			 }
			 if(merchantInfo.getApproveStatus()!=null &&!"".equals(merchantInfo.getApproveStatus())){
				 sql+=" and approveStatus=:approveStatus2";
				 sqlCount+=" and approveStatus=:approveStatus2";
				 map.put("approveStatus2", merchantInfo.getApproveStatus());
			 }
			 sql += " ORDER BY BMID DESC";
			 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
			 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
			 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue());

			 return dataGridBean;
		 }
		 
		 //会员宝Pro审批查询
		 @Override
		 public DataGridBean queryMicroMerchantInfoWJoinPro(
				 MerchantInfoBean merchantInfo, UserBean userBean) {
			 String hql = "from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 and m.mid like 'D864%' ";
			 String hqlCount = "select count(*) from MerchantInfoModel m where m.maintainType != :maintainType and (m.approveStatus='W' or m.approveStatus='C') and m.isM35=1 and m.mid like 'D864%' ";
			 Map<String, Object> map = new HashMap<String, Object>();
			 map.put("maintainType", "D");


			 if(merchantInfo.getRname() !=null && !"".equals(merchantInfo.getRname())){
				 hql+=" and m.rname like :RNAME ";
				 hqlCount +=" and m.rname like :RNAME ";
				 map.put("RNAME", merchantInfo.getRname()+'%');
			 }
			 if(merchantInfo.getMid() != null && !"".equals(merchantInfo.getMid())){
				 hql+=" and m.mid like :MID ";
				 hqlCount +=" and m.mid like :MID ";
				 map.put("MID", merchantInfo.getMid()+"%");
			 }
			 if(merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno())){
				 hql+=" and m.unno =:unno ";
				 hqlCount +=" and m.unno =:unno ";
				 map.put("unno", merchantInfo.getUnno());
			 }
			 hql += " order by BMID DESC";
			 Long count = merchantInfoDao.queryCounts(hqlCount, map);
			 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfo(hql, map, merchantInfo.getPage(), merchantInfo.getRows());

			 DataGridBean dataGridBean = formatToDataGrid(merchantInfoList, count);

			 return dataGridBean;
		 }
		 
		 @Override
		 public DataGridBean queryMicroMerchantInfoYPro(MerchantInfoBean merchantInfo,
				 UserBean user)throws Exception {
			 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
			 Map<String, Object> usermap = new HashMap<String, Object>();
			 usermap.put("userID", user.getUserID().toString());
			 usermap.put("maintainType", "D");
			 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
			 DataGridBean dataGridBean=new DataGridBean();
			 // 精确查询条件个数,满足 商编\终端编号\SN号\开户账号 展示查询出的手机号,否则隐藏中间4位数
			 int queryConditionCount = 0;
			 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
			 String sql = "";
			 String sqlCount = "";
			 boolean flag = false;
			 Map<String, Object> map = new HashMap<String, Object>();
			 if(agentSalesModels.size()==0){
				 if("110000".equals(user.getUnNo())){
					 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'D864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'D864%' ";
					 map.put("maintainType", "D");
				 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
					 UnitModel parent = unitModel.getParent();
					 if("110000".equals(parent.getUnNo())){
						 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'D864%' ";
						 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'D864%' ";
						 map.put("maintainType", "D");
					 }
				 }else if(isAgentformMan(user)){//判断是否是报单员
					 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1 and mid like 'D864%'  ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE UNNO = :unno AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.maintainuid = :maintainuid and M.isM35=1 and mid like 'D864%' ";
					 map.put("unno", user.getUnNo());
					 map.put("maintainType", "D");
					 map.put("maintainuid", user.getUserID());
				 }else{
					 String childUnno=queryUnitUnnoUtil(user.getUnNo());
					 sql = "SELECT M.* FROM BILL_MERCHANTINFO M  WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'D864%' ";
					 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.UNNO IN ("+childUnno+") AND M.MAINTAINTYPE !=:maintainType  AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'D864%' ";
					 map.put("maintainType", "D");
				 }
			 }else{
				 sql = "SELECT M.* FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'D864%' ";
				 sqlCount = "SELECT COUNT(*) FROM BILL_MERCHANTINFO M WHERE M.MAINTAINUSERID = :maintainUserId AND M.MAINTAINTYPE != :maintainType AND M.APPROVESTATUS in ('Y','C') and M.isM35=1 and mid like 'D864%' ";
				 map.put("maintainUserId", agentSalesModels.get(0).getBusid());
				 map.put("maintainType", "D");
			 }

			 if (merchantInfo.getUnitType() != null && "2".equals(merchantInfo.getUnitType())) {
				 sql +=" AND M.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
				 sqlCount +=" AND M.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 }else if(merchantInfo.getUnitType() != null && "1".equals(merchantInfo.getUnitType())){
				 sql +=" AND M.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
				 sqlCount +=" AND M.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 }

			 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
				 sql +=" AND M.APPROVESTATUS = :approveStatus";
				 sqlCount += " AND M.APPROVESTATUS= :approveStatus";
				 map.put("approveStatus", merchantInfo.getApproveStatus());
			 }

			 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
				 queryConditionCount++;
				 sql +=" AND M.MID = :mid";
				 sqlCount += " AND M.MID = :mid";
				 map.put("mid",merchantInfo.getMid());
				 flag=true;
			 }

			 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
				 sql +=" AND M.RNAME LIKE :rname";
				 sqlCount += " AND M.RNAME LIKE :rname";
				 map.put("rname",merchantInfo.getRname()+'%');
				 flag=true;
			 }

			 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
				 queryConditionCount++;
				 sql +=" AND M.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType  and APPROVESTATUS=:approvestatus)";
				 sqlCount += " AND MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE :tid AND MAINTAINTYPE != :maintainType  and APPROVESTATUS=:approvestatus)";
				 map.put("tid",merchantInfo.getTid()+'%');
				 map.put("maintainType", "D");
				 map.put("approvestatus", "Y");
				 flag=true;
			 }

			 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
				 queryConditionCount++;
				 sql +=" AND M.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN  and APPROVESTATUS=:approvestatus)";
				 sqlCount += " AND M.MID IN (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !=:maintainType and b.sn=:SN  and APPROVESTATUS=:approvestatus)";
				 map.put("maintainType", "D");
				 map.put("SN",merchantInfo.getSn());
				 map.put("approvestatus", "Y");
				 flag=true;
			 }
			 //归属
			 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
				 sql +=" AND M.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
				 sqlCount += " AND M.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 }

			 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
				 queryConditionCount++;
				 sql +=" AND M.BANKACCNO LIKE :bankAccNo";
				 sqlCount += " AND M.BANKACCNO LIKE :bankAccNo";
				 map.put("bankAccNo",merchantInfo.getBankAccNo());
				 flag=true;
			 }

			 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
				 sql +=" AND M.UNNO = :merunno";
				 sqlCount += " AND M.UNNO = :merunno";
				 map.put("merunno", merchantInfo.getUnno());
				 flag=true;
			 }
			 if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
				 sql +=" AND M.approveUid = :userID";
				 sqlCount += " AND M.approveUid = :userID";
				 map.put("userID",getUserID(merchantInfo.getApproveUidName().trim()) );
				 flag=true;
			 }
			 //设备型号
			 if (merchantInfo.getBmaid() != null && !"".equals(merchantInfo.getBmaid())) {
				 sql += " AND M.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = :bmaid )";
				 sqlCount += " AND M.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = :bmaid )";
				 flag=true;
				 map.put("bmaid", merchantInfo.getBmaid());
			 }
			 if (merchantInfo.getSettMethod() != null && !"".equals(merchantInfo.getSettMethod().trim())) {
				 sql +=" AND M.settMethod = :SETTMETHOD";
				 sqlCount += " AND M.settMethod = :SETTMETHOD";
				 map.put("SETTMETHOD", merchantInfo.getSettMethod());
			 } //JOINCONFIRMDATE
			 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
				 sql +=" AND M.approveDate >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
				 sqlCount += " AND M.approveDate >= to_date(:createDateTimeStart,'yyyy-MM-dd') ";
				 map.put("createDateTimeStart", merchantInfo.getCreateDateStart());
			 }

			 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
				 sql +=" AND M.approveDate < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
				 sqlCount += " AND M.approveDate < to_date(:createdatetimeEnd,'yyyy-MM-dd') + 1 ";
				 map.put("createdatetimeEnd", merchantInfo.getCreateDateEnd());
			 }
			 if(flag==false&&(merchantInfo.getCreateDateStart() == null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.getCreateDateEnd() == null||merchantInfo.getCreateDateEnd().equals(""))){
				 sqlCount += " AND M.approveDate >= trunc(sysdate)";
				 return dataGridBean;
			 }

			 sql += " order by M.approveDate DESC ";
			 BigDecimal counts = merchantInfoDao.querysqlCounts(sqlCount, map);
			 List<MerchantInfoModel> merchantInfoList = merchantInfoDao.queryMerchantInfoSql(sql, map, merchantInfo.getPage(), merchantInfo.getRows(), MerchantInfoModel.class);
			 //是否隐藏手机号信息
			 Integer i=1;
			 if(queryConditionCount!=0){
				 i=0;
			 }
			 dataGridBean = formatToDataGrid(merchantInfoList, counts.intValue(),i);
			 return dataGridBean;
		 }
		 
		 @Override
		 public List<Map<String, Object>> exportMicroPro(MerchantInfoBean merchantInfo,
				 UserBean user) {
			 String userhql = "from AgentSalesModel where userID = :userID and maintainType != :maintainType";
			 Map<String, Object> usermap = new HashMap<String, Object>();
			 usermap.put("userID", user.getUserID().toString());
			 usermap.put("maintainType", "D");
			 List<AgentSalesModel> agentSalesModels = agentSalesDao.queryObjectsByHqlList(userhql, usermap);
			 boolean flag = false ;
			 UnitModel unitModel = unitDao.getObjectByID(UnitModel.class, user.getUnNo());
			 String sql = "";
			 if(agentSalesModels.size()==0){
				 if("110000".equals(user.getUnNo())){
					 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
							 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
							 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
							 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
							 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
							 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
							 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
							 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
							 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
							 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
							 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
							 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'D864%' ";
				 }else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
					 UnitModel parent = unitModel.getParent();
					 if("110000".equals(parent.getUnNo())){
						 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
								 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
								 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
								 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
								 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
								 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
								 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
								 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
								 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
								 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
								 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
								 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'D864%' ";
					 }
				 }else if(isAgentformMan(user)){
					 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
							 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
							 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
							 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
							 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
							 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
							 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
							 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
							 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
							 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod"
							 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
							 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO = '"+user.getUnNo()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+)  AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1  and t.mid like 'D864%' and t.maintainuid = '"+ user.getUserID()+"'";

				 }else{
					 String childUnno=queryUnitUnnoUtil(user.getUnNo());
					 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
							 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
							 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
							 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
							 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
							 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
							 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
							 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
							 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
							 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
							 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
							 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.UNNO IN ("+childUnno+") AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'D864%' ";
				 }
			 }else{
				 sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
						 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
						 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
						 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
						 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
						 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
						 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
						 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
						 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS ,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
						 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
						 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
						 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.BUSID AND (i.MAINTAINTYPE !='D' or i.MAINTAINTYPE is null) AND (i.APPROVESTATUS = 'Y' or i.APPROVESTATUS is null) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 and t.mid like 'D864%' ";
			 }
			 if (merchantInfo.getUnitType() != null && "2".equals(merchantInfo.getUnitType())) {
				 sql +=" AND t.unno in ('962052','992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 }else if(merchantInfo.getUnitType() != null && "1".equals(merchantInfo.getUnitType())){
				 sql +=" AND t.unno not in ('992003','992007','992015','992024','992009','992010','992013','992017','992074','992077','992084','992122','992028','992054','992080','992034','992056','992061','992066','992071','992075','992111','992062','992114','992133','992051','992059','992027','992072','992073','992107','992039','992064','992065','992076','992110','992031','992058','992069','992070','992103','992146','982035','982042','982063','982011','982060','982045','982058','982071','982072','982008','982049','982041','982053','992140','982037','982046','982065','982066','982192','982157','972018','982125','982165','962018') ";
			 }
			 if (merchantInfo.getApproveStatus() != null && !"".equals(merchantInfo.getApproveStatus().trim())) {
				 sql +=" AND t.APPROVESTATUS = '"+merchantInfo.getApproveStatus()+"'";
			 }else{
				 sql +=" AND t.APPROVESTATUS in ('C','Y')";
			 }
			 if(merchantInfo.getMid() !=null && !merchantInfo.getMid().equals("") ){
				 sql +=" AND t.MID = '"+merchantInfo.getMid()+"' ";
				 flag=true;
			 }
			 if(merchantInfo.getRname() !=null && !merchantInfo.getRname().equals("") ){
				 sql +=" AND t.RNAME LIKE '%"+merchantInfo.getRname()+"%' ";
				 flag=true;
			 }
			 if(merchantInfo.getTid() !=null && !merchantInfo.getTid().equals("") ){
				 sql +=" AND t.MID IN (SELECT MID FROM BILL_MERCHANTTERMINALINFO WHERE TID LIKE '"+merchantInfo.getTid()+"%' AND MAINTAINTYPE != 'D'  and APPROVESTATUS='Y')";
				 flag=true;
			 }
			 if(merchantInfo.getSn()!=null && !"".equals(merchantInfo.getSn())){
				 sql +=" AND t.MID in (select a.mid from bill_merchantterminalinfo a,bill_terminalinfo b where a.tid=b.termid  and a.maintaintype !='D' and b.sn="+merchantInfo.getSn()+"  and APPROVESTATUS='Y')";
				 flag=true;
			 }
			 if(merchantInfo.getParentUnitName() !=null && !merchantInfo.getParentUnitName().equals("") ){
				 sql +=" AND t.unno in ("+queryUnitUnnoUtil(merchantInfo.getParentUnitName())+") ";
			 }
			 if(merchantInfo.getBankAccNo() !=null && !merchantInfo.getBankAccNo().equals("") ){
				 sql +=" AND t.BANKACCNO LIKE '"+merchantInfo.getBankAccNo()+"'";
				 flag=true;
			 }
			 if (merchantInfo.getUnno() != null && !"".equals(merchantInfo.getUnno().trim())) {
				 sql +=" AND t.UNNO = '"+merchantInfo.getUnno()+"' ";
				 flag=true;
			 }
			 if (merchantInfo.getApproveUidName() != null && !"".equals(merchantInfo.getApproveUidName().trim())) {
				 String str = merchantInfo.getApproveUidName();
				 String userId = getUserID(merchantInfo.getApproveUidName().trim());
				 sql +=" AND t.approveUid = '"+userId+"' ";
				 flag=true;
			 }
			 if (merchantInfo.getBmaid() != null && !"".equals(merchantInfo.getBmaid())) {
				 sql += " AND t.mid in ( select Mid from bill_merchantterminalinfo mt  where mt.bmaid = "+merchantInfo.getBmaid()+" )";
				 flag=true;
			 }
			 if (merchantInfo.getSettMethod() != null && !"".equals(merchantInfo.getSettMethod().trim())) {
				 sql +=" AND nvl(t.settMethod,'000000')='"+merchantInfo.getSettMethod()+"'";
			 }
			 if (merchantInfo.getCreateDateStart() != null && !merchantInfo.getCreateDateStart().equals("")) {
				 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd')  >= '"+merchantInfo.getCreateDateStart()+"' ";
				 sql +=" AND t.approveDate  >= to_date('"+merchantInfo.getCreateDateStart()+"','yyyy-MM-dd') ";
			 }
			 if (merchantInfo.getCreateDateEnd() != null && !merchantInfo.getCreateDateEnd().equals("")) {
				 //			sql +=" AND to_char(t.approveDate,'yyyy-MM-dd') <= '"+merchantInfo.getCreateDateEnd()+"' ";
				 sql +=" AND t.approveDate < to_date('"+merchantInfo.getCreateDateEnd()+"','yyyy-MM-dd') + 1 ";
			 }
			 if(flag==false&&(merchantInfo.getCreateDateStart() ==
					 null||merchantInfo.getCreateDateStart().equals(""))&&(merchantInfo.
							 getCreateDateEnd() == null||merchantInfo.getCreateDateEnd().equals(""))){ 
				 sql +=" AND t.approveDate >= trunc(sysdate+1) "; 
			 }
			 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
			 return data;
		 }
		 
		 @Override
		 public List<Map<String, Object>> exportMicroMerSelectedPro(String ids,UserBean user) {
			 String sql = " SELECT distinct t.UNNO,(SELECT UN_NAME FROM SYS_UNIT WHERE UNNO = t.UNNO) UNNONAME,t.BNO,t.RNAME," +
					 " (select s.un_name from sys_unit s where  s.upper_unit in ('110000','991000') start with s.unno =t.UNNO  connect by NOCYCLE  s.unno =prior s.upper_unit   and rownum=1 )YIDAI, " +
					 " nvl(t.CREDITBANKRATE,0) CREDITBANKRATE,s.AREA_NAME, substr(t.BADDR, 1,3) PROVINCE," +
					 "(select u.UN_NAME from sys_unit u where u.unno =(select distinct s.upper_unit from sys_unit s where s.unno= t.unno)) PARENTUNITNAME ," +
					 "t.FEEAMT,to_char(t.APPROVEDATE,'yyyy-MM-dd HH24:mi:ss') APPROVEDATE," +
					 " to_char(t.MAINTAINDATE,'yyyy-MM-dd HH24:mi:ss') MAINTAINDATE ,t.DEPOSIT,"
					 + " t.CHARGE, t.LEGALPERSON, t.BANKBRANCH, t.BANKACCNAME, t.BANKACCNO, t.PAYBANKID,"
					 + " t.MID, i.TID, t.BANKFEERATE, t.FEERATE_V, a.SALENAME, e.MACHINEMODEL, t.SETTLECYCLE,"
					 + " t.BADDR,t.BUSINESSSCOPE, t.LEGALNUM, t.CONTACTPERSON, case when (select count(1) from sys_configure where groupname='phoneFilter' and minfo1='"+user.getUnNo()+"')=1 then substr(t.contactphone,0,3)||'****'||substr(t.contactphone,8) else t.contactphone end as contactphone,(SELECT USER_NAME FROM SYS_USER WHERE USER_ID = t.APPROVEUID) APPROVEUIDNAME,t.REMARKS,b.sn,b.usedconfirmdate,i.approvedate iapprovedate, "
					 + " t.LEGALEXPDATE,t.BNOEXPDATE,t.ACCNUM,t.ACCEXPDATE,decode(t.settmethod,100000,'秒到','普通') settmethod "
					 + " FROM BILL_MERCHANTINFO t, BILL_MERCHANTTERMINALINFO i,BILL_MACHINEINFO e,BILL_AGENTSALESINFO a,bill_terminalinfo b,sys_area s  "
					 + " WHERE s.AREA_CODE(+)=t.AREA_CODE and  t.MID = i.MID(+) AND i.tid=b.termid(+) AND e.BMAID(+)=i.BMAID AND a.BUSID(+)=t.MAINTAINUSERID AND (i.MAINTAINTYPE !='D' OR i.MAINTAINTYPE IS NULL) AND (i.APPROVESTATUS = 'Y' OR i.APPROVESTATUS IS NULL) AND t.MAINTAINTYPE != 'D' AND t.ISM35=1 AND t.mid like 'D864%' AND t.BMID IN  ("
					 + ids + ")";
			 List<Map<String, Object>> data = merchantInfoDao.queryObjectsBySqlList(sql);
			 return data;
		 }
		
}