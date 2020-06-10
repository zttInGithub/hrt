package com.hrt.biz.bill.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.hrt.biz.bill.dao.IAgentSalesDao;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.IMerchantTaskDataDao;
import com.hrt.biz.bill.dao.IMerchantTaskDetail1Dao;
import com.hrt.biz.bill.dao.IMerchantTaskDetail2Dao;
import com.hrt.biz.bill.dao.IMerchantTaskDetail3Dao;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDataModel;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail1Model;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail2Model;
import com.hrt.biz.bill.entity.model.MerchantTaskDetail3Model;
import com.hrt.biz.bill.entity.pagebean.MerchantTaskDataBean;
import com.hrt.biz.bill.service.IMerchantTaskDataService;
import com.hrt.frame.dao.sysadmin.ITodoDao;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.utility.ExcelUtil;
import com.hrt.gmms.webservice.IGmmsService;
import com.hrt.gmms.webservice.MerchantInfo;
import com.hrt.util.ClassToJavaBeanUtil;
import com.hrt.util.HttpXmlClient;

public class MerchantTaskDataServiceImpl implements IMerchantTaskDataService{
	
	private IMerchantTaskDataDao merchantTaskDataDao;
	private IMerchantTaskDetail1Dao merchantTaskDetail1Dao;
	private IMerchantTaskDetail2Dao merchantTaskDetail2Dao;
	private IMerchantTaskDetail3Dao merchantTaskDetail3Dao;
	private ITodoDao todoDao;
	private IMerchantInfoDao merchantInfoDao;
	private IGmmsService gsClient;
	private String jhTaskHybUrl;
	private IAgentSalesDao agentSalesDao;
	private static final Log log = LogFactory.getLog(MerchantTaskDetail4ServiceImpl.class);
	
	public String getJhTaskHybUrl() {
		return jhTaskHybUrl;
	}

	public void setJhTaskHybUrl(String jhTaskHybUrl) {
		this.jhTaskHybUrl = jhTaskHybUrl;
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

	public IMerchantTaskDetail1Dao getMerchantTaskDetail1Dao() {
		return merchantTaskDetail1Dao;
	}

	public void setMerchantTaskDetail1Dao(
			IMerchantTaskDetail1Dao merchantTaskDetail1Dao) {
		this.merchantTaskDetail1Dao = merchantTaskDetail1Dao;
	}

	public IMerchantTaskDetail2Dao getMerchantTaskDetail2Dao() {
		return merchantTaskDetail2Dao;
	}

	public void setMerchantTaskDetail2Dao(
			IMerchantTaskDetail2Dao merchantTaskDetail2Dao) {
		this.merchantTaskDetail2Dao = merchantTaskDetail2Dao;
	}

	public IMerchantTaskDetail3Dao getMerchantTaskDetail3Dao() {
		return merchantTaskDetail3Dao;
	}

	public void setMerchantTaskDetail3Dao(
			IMerchantTaskDetail3Dao merchantTaskDetail3Dao) {
		this.merchantTaskDetail3Dao = merchantTaskDetail3Dao;
	}

	public IAgentSalesDao getAgentSalesDao() {
		return agentSalesDao;
	}

	public void setAgentSalesDao(IAgentSalesDao agentSalesDao) {
		this.agentSalesDao = agentSalesDao;
	}


	private IUnitDao unitDao;
	
	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public IMerchantTaskDataDao getMerchantTaskDataDao() {
		return merchantTaskDataDao;
	}

	public void setMerchantTaskDataDao(IMerchantTaskDataDao merchantTaskDataDao) {
		this.merchantTaskDataDao = merchantTaskDataDao;
	}
	

	public ITodoDao getTodoDao() {
		return todoDao;
	}

	public void setTodoDao(ITodoDao todoDao) {
		this.todoDao = todoDao;
	}

	@Override
	public DataGridBean queryPage(MerchantTaskDataBean bean) {
		Map<String, Object> param=new HashMap<String, Object>();
		String hql = "from MerchantTaskDataModel where 1 = 1 and type!=8 and type!=9";
		String hqlCount = "select count(*) from MerchantTaskDataModel where 1 = 1 and type!=8 and type!=9";
		if (bean.getApproveStatus() != null&&!"".equals(bean.getApproveStatus())) {
			hql += " AND approveStatus=:approveStatus" ;
			hqlCount += " AND approveStatus=:approveStatus" ;
			param.put("approveStatus", bean.getApproveStatus() );
		}
		
		if (bean.getIsM35() != null&&"1".equals(bean.getIsM35())) {
			hql += " AND mid not like 'HRTSYT%' " ;
			hqlCount += " AND mid not like 'HRTSYT%' " ;
		}else if(bean.getIsM35() != null&&"2".equals(bean.getIsM35())) {
			hql += " AND mid like 'HRTSYT%' " ;
			hqlCount += " AND mid like 'HRTSYT%' " ;
		}
		if(bean.getMid()!=null && !"".equals(bean.getMid())){
			hql  += " and mid like :mid";
			hqlCount += " and mid like :mid";
			param.put("mid", bean.getMid()+"%");
		}
		if(bean.getType()!=null && !"".equals(bean.getType())){
			hql  += " and type="+bean.getType();
			hqlCount += " and type="+bean.getType();
//			param.put("type", bean.getType());
		}else{
			hql  += " and type=1 ";
			hqlCount += " and type=1 ";
		}
		if(bean.getInfoName()!=null && !"".equals(bean.getInfoName())){
			List mids=getMid(bean.getInfoName().trim());
			for(int i=0;i<mids.size();i++){
					   String mid="mid"+i;
					   if(i==0){
						   hql  += " and mid=:"+mid;
						   hqlCount += " and mid=:"+mid;
						   param.put(mid, mids.get(i)); 
					   }else{
					      hql  += " or (mid=:"+mid+" AND approveStatus=:approveStatus)";
					      hqlCount += "  or (mid=:"+mid+" AND approveStatus=:approveStatus)";;
					      param.put(mid, mids.get(i));
					      param.put("approveStatus", bean.getApproveStatus() );
					   }
			}
			
		}
		if (bean.getSort() != null) {
			hql += " order by " + bean.getSort() + " " + bean.getOrder();
		}
		long counts = merchantTaskDataDao.queryCounts(hqlCount, param);
		List<MerchantTaskDataModel> dateList = merchantTaskDataDao.queryPage(hql, param, bean.getPage(), bean.getRows());
		
		DataGridBean dataGridBean = formatToDataGrid(dateList, counts);
		
		return dataGridBean;
	}
	
	@Override
	public DataGridBean queryRiskPage(MerchantTaskDataBean bean) {
		Map<String, Object> param=new HashMap<String, Object>();
		String sql = "select mc.ISM35,mc.SETTMETHOD, mc.RNAME as INFONAME, s.un_name as UNNAME, m.*,m4.mtype from bill_merchanttaskdata m, sys_unit s, bill_merchantinfo mc, bill_merchanttaskdetail4 m4  where m.mid=mc.mid and m.unno=s.unno and m.bmtkid = m4.bmtkid and type=8 ";
		String sqlCount = "select count(*) from bill_merchanttaskdata m,sys_unit s, bill_merchantinfo mc, bill_merchanttaskdetail4 m4 where m.mid=mc.mid and m.unno=s.unno and m.bmtkid = m4.bmtkid and type=8 ";
		if (bean.getApproveStatus() != null&&!"".equals(bean.getApproveStatus())) {
			sql += " AND m.approveStatus=:approveStatus" ;
			sqlCount += " AND m.approveStatus=:approveStatus" ;
			param.put("approveStatus", bean.getApproveStatus() );
		}
		
		if(bean.getStartDay()!=null && !"".equals(bean.getStartDay()) &&  bean.getEndDay()!=null && !"".equals(bean.getEndDay())){
			sql+= " AND to_char(m.Maintaindate,'yyyy-MM-dd') >= '"+bean.getStartDay()+"'";
			sql+=" AND to_char(m.Maintaindate,'yyyy-MM-dd') <= '"+bean.getEndDay()+"'";
			sqlCount+= " AND to_char(m.Maintaindate,'yyyy-MM-dd') >= '"+bean.getStartDay()+"'";
			sqlCount+=" AND to_char(m.Maintaindate,'yyyy-MM-dd') <= '"+bean.getEndDay()+"'";
		}else{
			sql+= " AND to_char(m.Maintaindate,'yyyy-MM-dd') >= '2018-01-01'";
			sqlCount+= " AND to_char(m.Maintaindate,'yyyy-MM-dd') >= '2018-01-01'";
		}
		
		if(bean.getMid()!=null && !"".equals(bean.getMid())){
			sql  += " and m.mid=:mid";
			sqlCount += " and m.mid=:mid";
			param.put("mid", bean.getMid());
		}
		if(bean.getInfoName()!=null && !"".equals(bean.getInfoName())){
			sql += " and mc.RNAME like '%"+bean.getInfoName()+"%' ";
			sqlCount += " and mc.RNAME like '%"+bean.getInfoName()+"%' ";
//			List mids=getMid(bean.getInfoName().trim());
//			for (int i = 0; i < mids.size(); i++) {
//				String mid = "mid" + i;
//				if (i == 0) {
//					sql += " and mid=:" + mid;
//					sqlCount += " and mid=:" + mid;
//					param.put(mid, mids.get(i));
//				} else {
//					sql += " or (mid=:" + mid + " AND m.approveStatus=:approveStatus)";
//					sqlCount += "  or (mid=:" + mid + " AND m.approveStatus=:approveStatus)";
//					;
//					param.put(mid, mids.get(i));
//					param.put("approveStatus", bean.getApproveStatus());
//				}
//			}
			
		}
		if(bean.getMtype()!=null){
			sql += " and m4.mType=:mType ";
			sqlCount += " and m4.mType=:mType ";
			param.put("mType", bean.getMtype());
		}
		if (bean.getSort() != null) {
			sql += " order by m." + bean.getSort() + " " + bean.getOrder();
		}
		long counts = merchantTaskDataDao.querysqlCounts2(sqlCount, param);
		List<Map<String, String>> dataList = merchantTaskDataDao.queryObjectsBySqlList(sql, param, bean.getPage(), bean.getRows());
		
//		List<MerchantTaskDataModel> dateList = merchantTaskDataDao.queryObjectsBySqlLists(sql, param, bean.getPage(), bean.getRows(), MerchantTaskDataModel.class);
		DataGridBean dataGridBean = new DataGridBean();
		dataGridBean.setTotal(counts);
		dataGridBean.setRows(dataList);
		return dataGridBean;
	}

	/**
	 * 方法功能：格式化MerchantTaskDataModel为datagrid数据格式
	 * 参数：dateList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<MerchantTaskDataModel> dateList, long total) {
		List<MerchantTaskDataBean> gridList = new ArrayList<MerchantTaskDataBean>();
		for(MerchantTaskDataModel model : dateList) {
			MerchantTaskDataBean bean = new MerchantTaskDataBean();
			BeanUtils.copyProperties(model, bean);
			
			UnitModel unit=getSeqNoByUnno( model.getUnno());
			bean.setUnName(unit.getUnitName());
			bean.setInfoName(getMInfoName(bean.getMid())); 
			//商户标识
			List<Map<String, Object>> list=merchantInfoDao.queryObjectsBySqlList("select ISM35,SETTMETHOD from bill_merchantinfo where mid='"+model.getMid()+"'");
			if(list.size()>0){
				Map<String, Object> param=list.get(0);
				bean.setIsM35(param.get("ISM35")+"");
				bean.setSettmethod(param.get("SETTMETHOD")+"");
			}
			
			gridList.add(bean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(gridList);
		
		return dgb;
	}
	
	/**
	 * 根据机构号得到机构信息
	 */
	public UnitModel getSeqNoByUnno(String unno){
		String[] param={unno};
		String hql=" FROM UnitModel WHERE unNO=?";
		List<UnitModel> list=unitDao.queryObjectsByHqlList(hql, param);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new UnitModel();
		}
		
	}
	/**
	 * 根据商户编号得到商户姓名
	 */
	private String getMInfoName(String id) {
		String sql="SELECT RNAME FROM BILL_MERCHANTINFO WHERE MID='"+id+"'";
		List list=merchantTaskDataDao.queryObjectsBySqlList(sql);
		String rname="";
		if(list.size()>0){
			Map<String, Object> map=(Map<String, Object>) list.get(0);
			rname=map.get("RNAME").toString();
		}
		return rname;
	}
	/**
	 * 根据商户名称得到商户编号(支持模糊查询)
	 */
	private List getMid(String infoName) {
		String sql="SELECT MID FROM BILL_MERCHANTINFO WHERE RNAME like '%"+infoName+"%'";
		List list=merchantTaskDataDao.queryObjectsBySqlList(sql);
		List<String> mids=new ArrayList();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String, Object> map=(Map<String, Object>) list.get(i);
				mids.add(map.get("MID").toString());
			}
		}else{
			mids.add("-1");
		}
		return mids;
	}

	@Override
	public boolean updateAuditThrough(MerchantTaskDataBean bean, UserBean user,String ids) { 
		Date date=new Date();
		String bmtkIds[]=ids.split(",");
		for (int i = 0; i < bmtkIds.length; i++) {
			MerchantTaskDataModel mo=merchantTaskDataDao.getObjectByID(MerchantTaskDataModel.class, Integer.valueOf(bmtkIds[i]));
			if(!mo.getApproveStatus().equals("Z")){
				return false;
			}else{
				mo.setApproveDate(date);
				mo.setApproveStatus("Y");
				mo.setApproveUId(user.getUserID());
				merchantTaskDataDao.updateObject(mo);
				MerchantTaskDetail1Model t1=getTask1(mo.getBmtkid());
				MerchantTaskDetail2Model t2=getTask2(mo.getBmtkid());
				MerchantTaskDetail3Model t3=getTask3(mo.getBmtkid());
				MerchantInfoModel m=getInfoModelTwo(t1, t2, t3, mo.getMid());
				MerchantInfoModel m1=getInfoModelTwo1(t1, t2, t3, mo.getMid());
				m1.setIsM35(m.getIsM35());
				m.setMaintainDate(new Date());
				m1.setMaintainDate(new Date());
				//m.setMaintainUid(user.getUserID());
				m.setMaintainType("M");
				
				//钱包默认银行卡修改
				String hql="from  MerchantInfoModel t where t.mid=?";
				MerchantInfoModel mBefore=merchantInfoDao.queryObjectsByHqlListOpenNewSession(hql,new Object[]{m.getMid()});
				//判断卡号是否变更
				if(!mBefore.getBankAccNo().trim().equals(m.getBankAccNo().trim())){
					String updateSql="update bill_merchantbankcardinfo t set t.bankaccno='"+m.getBankAccNo()+"' where t.mid='"+m.getMid()+"' and t.mercardtype=0 ";
					merchantInfoDao.executeUpdate(updateSql);
				}
				//判断银行名称是否变更
				if(!mBefore.getBankBranch().trim().equals(m.getBankBranch().trim())){
					String updateSql3="update bill_merchantbankcardinfo t set t.bankbranch='"+m.getBankBranch()+"' where t.mid='"+m.getMid()+"' and t.mercardtype=0 ";
					merchantInfoDao.executeUpdate(updateSql3);
				}
				
				//更新支付系统行号
				String updateSql4="update bill_merchantbankcardinfo t set t.paybankId='"+m.getPayBankId()+"' where t.mid='"+m.getMid()+"' and t.mercardtype=0 ";
				merchantInfoDao.executeUpdate(updateSql4);

				
				//判断入账人名称是否变更
				if(!mBefore.getBankAccName().trim().equals(m.getBankAccName().trim())){
					String updateSql2="update bill_merchantbankcardinfo t set t.bankaccname='"+m.getBankAccName()+"' where t.mid='"+m.getMid()+"' and t.mercardtype=0 ";
					String updateSql="update bill_merchantbankcardinfo t set t.status=1 where t.mid='"+m.getMid()+"' and t.mercardtype!=0 ";
					merchantInfoDao.executeUpdate(updateSql2);
					merchantInfoDao.executeUpdate(updateSql);
				}
				
				merchantInfoDao.updateObject(m);
				
				todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString()); 
				
				//调用webservice，对GMMS修改商户信息
				String tenance=getSaleName(m.getMaintainUserId());
				String sales=getSaleName(m.getBusid());
				MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(m1, user.getLoginName(), tenance, sales);
				Boolean falg=gsClient.updateMerchantInfo(info,"hrtREX1234.Java");
				if(!falg){
					throw new IllegalAccessError("调用webservice失败");
				}
				if(falg&&t1!=null&&mo.getMid().startsWith("HRTPAY")){
					//推送会员宝 手机号&商户号关联关系
					Map<String,String> params = new HashMap<String, String>();
					params.put("jhMid",mo.getMid());
					params.put("merName",t1.getRname());
					String json = "{body:"+JSON.toJSONString(params)+"}";
					log.info("推送会员宝 手机号&商户号关联 "+json);
					String ss=HttpXmlClient.postForJson(jhTaskHybUrl, json);
					log.info("基本信息工单审核-聚合商户-推送hrt:"+json+";"+ss);
				}
			}

		}
		return true;
	}
	
	
	@Override
	public boolean updateAuditRiskThrough(MerchantTaskDataBean bean, UserBean user,Integer ids) { 
		Date date=new Date();
		MerchantTaskDataModel mo=merchantTaskDataDao.getObjectByID(MerchantTaskDataModel.class, ids);
		if(!mo.getApproveStatus().equals("Z")){
			return false;
		}else{
			mo.setApproveDate(date);
			mo.setApproveStatus("Y");
			mo.setApproveUId(user.getUserID());
			merchantTaskDataDao.updateObject(mo);
			
			todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString()); 
		}
		return true;
	}

	private String getSaleName(Integer id) {
		String sql="SELECT SALENAME FROM BILL_AGENTSALESINFO WHERE BUSID="+id;
		List<Map<String, Object>> list=merchantInfoDao.queryObjectsBySqlList(sql);
		String sale="";
		if(list.size()>0){
			sale=list.get(0).get("SALENAME").toString();
		}
		return sale;
	}
	private MerchantTaskDetail1Model getTask1(Integer bmtkid) {
		Object[] params={bmtkid};
		String hql=" FROM MerchantTaskDetail1Model WHERE BMTKID=?";
		return merchantTaskDetail1Dao.queryObjectByHql(hql,params);
	}
	private MerchantTaskDetail2Model getTask2(Integer bmtkid) {
		Object[] params={bmtkid};
		String hql=" FROM MerchantTaskDetail2Model WHERE BMTKID=?";
		return merchantTaskDetail2Dao.queryObjectByHql(hql,params);
	}
	private MerchantTaskDetail3Model getTask3(Integer bmtkid) {
		Object[] params={bmtkid};
		String hql=" FROM MerchantTaskDetail3Model WHERE BMTKID=?";
		return merchantTaskDetail3Dao.queryObjectByHql(hql,params);
	}

	@Override
	public boolean updateAuditReject(MerchantTaskDataBean bean, UserBean user,
			String ids) {
		Date date=new Date();
		String bmtkIds[]=ids.split(",");
		for (int i = 0; i < bmtkIds.length; i++) {
			MerchantTaskDataModel mo=merchantTaskDataDao.getObjectByID(MerchantTaskDataModel.class, Integer.valueOf(bmtkIds[i]));
			if(!mo.getApproveStatus().equals("Z")){
				return false;
			}else{
				mo.setApproveDate(date);
				mo.setApproveStatus("K");
				mo.setApproveUId(user.getUserID());
				merchantTaskDataDao.updateObject(mo);
				todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString());
			}
		}
		return true;
	}

	@Override
	public boolean updateAuditSingle(MerchantTaskDataBean bean, UserBean user) {
		Date date=new Date();
		MerchantTaskDataModel mo=merchantTaskDataDao.getObjectByID(MerchantTaskDataModel.class, bean.getBmtkid());
		if("Y".equals(bean.getApproveStatus())){
			if(mo.getApproveStatus().equals("Y")){
				return false;
			}else{
				mo.setProcessContext(bean.getProcessContext());
				mo.setApproveDate(date);
				mo.setApproveStatus("Y");
				mo.setApproveUId(user.getUserID());
				merchantTaskDataDao.updateObject(mo);
				MerchantTaskDetail1Model t1=getTask1(mo.getBmtkid());
				MerchantTaskDetail2Model t2=getTask2(mo.getBmtkid());
				MerchantTaskDetail3Model t3=getTask3(mo.getBmtkid());
				MerchantInfoModel m=getInfoModelTwo(t1, t2, t3, mo.getMid());
				MerchantInfoModel m2=getInfoModelTwo1(t1, t2, t3, mo.getMid());
				m2.setIsM35(m.getIsM35());
				m.setMaintainDate(new Date());
				m2.setMaintainDate(new Date());
				//m.setMaintainUid(user.getUserID());
				m2.setMaintainType("M");
				m.setMaintainType("M");
				
				//调用webservice，对GMMS修改商户信息
				String tenance=getSaleName(m.getMaintainUserId());
				String sales=getSaleName(m.getBusid());
				MerchantInfo info=ClassToJavaBeanUtil.toMerchantInfo(m2, user.getLoginName(), tenance, sales);
				Boolean falg=gsClient.updateMerchantInfo(info,"hrtREX1234.Java");
				if(!falg){
					throw new IllegalAccessError("调用webservice失败");
				}
				
				//钱包默认银行卡修改
				String hql="from  MerchantInfoModel t where t.mid=?";
				MerchantInfoModel m3=merchantInfoDao.queryObjectsByHqlListOpenNewSession(hql,new Object[]{m.getMid()});
				//判断卡号是否变更
				if(m2.getBankAccNo()!=null&&!m3.getBankAccNo().trim().equals(m2.getBankAccNo().trim())){
					String updateSql="update bill_merchantbankcardinfo t set t.bankaccno='"+m2.getBankAccNo()+"' where t.mid='"+m2.getMid()+"' and t.mercardtype=0 ";
					merchantInfoDao.executeUpdate(updateSql);
				}
				//判断入账人名称是否变更
				if(m2.getBankAccName()!=null&&!m3.getBankAccName().trim().equals(m2.getBankAccName().trim())){
					String updateSql2="update bill_merchantbankcardinfo t set t.bankaccname='"+m2.getBankAccName()+"' where t.mid='"+m2.getMid()+"' and t.mercardtype=0 ";
					String updateSql="update bill_merchantbankcardinfo t set t.status=1 where t.mid='"+m2.getMid()+"' and t.mercardtype!=0 ";
					merchantInfoDao.executeUpdate(updateSql2);
					merchantInfoDao.executeUpdate(updateSql);
				}
				
				//判断银行名称是否变更
				if(m2.getBankBranch()!=null&&!m3.getBankBranch().trim().equals(m2.getBankBranch().trim())){
					String updateSql3="update bill_merchantbankcardinfo t set t.bankbranch='"+m2.getBankBranch()+"' where t.mid='"+m2.getMid()+"' and t.mercardtype=0 ";
					merchantInfoDao.executeUpdate(updateSql3);
				}
				//判断支付系统行号是否变更

				String updateSql4="update bill_merchantbankcardinfo t set t.paybankId='"+m2.getPayBankId()+"' where t.mid='"+m2.getMid()+"' and t.mercardtype=0 ";
				merchantInfoDao.executeUpdate(updateSql4);

				
				merchantInfoDao.updateObject(m);
				
				todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString()); 
				if(falg&&t1!=null&&mo.getMid().startsWith("HRTPAY")){
					//推送会员宝 手机号&商户号关联关系
					Map<String,String> params = new HashMap<String, String>();
					params.put("jhMid",mo.getMid());
					params.put("merName",t1.getRname());
					String json = "{body:"+JSON.toJSONString(params)+"}";
					log.info("推送会员宝 手机号&商户号关联 "+json);
					String ss=HttpXmlClient.postForJson(jhTaskHybUrl, json);
					log.info("基本信息工单审核-聚合商户-推送hrt:"+json+";"+ss);
				}
				return true;
			}
		}else if("K".equals(bean.getApproveStatus())){
			if(mo.getApproveStatus().equals("K")){
				return false;
			}else{
				mo.setProcessContext(bean.getProcessContext());
				mo.setApproveDate(date);
				mo.setApproveStatus("K");
				mo.setApproveUId(user.getUserID());
				merchantTaskDataDao.updateObject(mo);
				todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString());
				return true;
			}
		}else{
			return false;
		}
	
	}
	
	
	@Override
	public boolean updateChangeT0Single(MerchantTaskDataBean bean, UserBean user) {
		Date date=new Date();
		MerchantTaskDataModel mo=merchantTaskDataDao.getObjectByID(MerchantTaskDataModel.class, bean.getBmtkid());
		if("Y".equals(bean.getApproveStatus())){
			if(mo.getApproveStatus().equals("Y")){
				return false;
			}else{
				mo.setProcessContext(bean.getProcessContext());
				mo.setApproveDate(date);
				mo.setApproveStatus("Y");
				mo.setApproveUId(user.getUserID());
				merchantTaskDataDao.updateObject(mo);
				
				String hql="from  MerchantInfoModel t where t.mid=?";
				MerchantInfoModel merchant=merchantInfoDao.queryObjectsByHqlListOpenNewSession(hql,new Object[]{bean.getMid()});
				merchant.setSettleCycle(bean.getSettleCycle());
				merchant.setMaintainUserId(user.getUserID());
				merchantInfoDao.updateObject(merchant);
				todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString()); 
				return true;
			}
		}else if("K".equals(bean.getApproveStatus())){
			if(mo.getApproveStatus().equals("K")){
				return false;
			}else{
				mo.setProcessContext(bean.getProcessContext());
				mo.setApproveDate(date);
				mo.setApproveStatus("K");
				mo.setApproveUId(user.getUserID());
				merchantTaskDataDao.updateObject(mo);
				todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString());
				return true;
			}
		}else{
			return false;
		}
	
	}
	
	
	@Override
	public boolean updateAuditRiskSingle(MerchantTaskDataBean bean, UserBean user) {
		Date date=new Date();
		MerchantTaskDataModel mo=merchantTaskDataDao.getObjectByID(MerchantTaskDataModel.class, bean.getBmtkid());
		if("Y".equals(bean.getApproveStatus()) || "Z".equals(bean.getApproveStatus())){
			if(mo.getApproveStatus().equals("Y")){
				return false;
			}else{
				mo.setProcessContext(bean.getProcessContext());
				mo.setApproveDate(date);
				mo.setApproveStatus("Y");
				mo.setApproveUId(user.getUserID());
				merchantTaskDataDao.updateObject(mo);
				todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString()); 
				return true;
			}
		}else if("K".equals(bean.getApproveStatus())){
			if(mo.getApproveStatus().equals("K")){
				return false;
			}else{
				mo.setProcessContext(bean.getProcessContext());
				mo.setApproveDate(date);
				mo.setApproveStatus("K");
				mo.setApproveUId(user.getUserID());
				merchantTaskDataDao.updateObject(mo);
				todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString());
				return true;
			}
		}else{
			return false;
		}
	
	}

	@Override
	public Map<String,Object> findDetail(Integer id) {
		
		Object[] params={id};
		String hql=" FROM MerchantTaskDataModel WHERE bmtkid=?";
		MerchantTaskDataModel task=merchantTaskDataDao.queryObjectByHql(hql, params);
		String hql1=" FROM MerchantTaskDetail1Model WHERE bmtkid=?";
		MerchantTaskDetail1Model task1=merchantTaskDetail1Dao.queryObjectByHql(hql1, params);
		
		//得到业务人员姓名
		if(task1!=null&&task1.getBusid()!=null){
			AgentSalesModel agentSalesModel = agentSalesDao.getObjectByID(AgentSalesModel.class, task1.getBusid());
			if(agentSalesModel!=null){
				task1.setBusidName(agentSalesModel.getSaleName());
			}
		}
		
		String hql2=" FROM MerchantTaskDetail2Model WHERE bmtkid=?";
		MerchantTaskDetail2Model task2=merchantTaskDetail2Dao.queryObjectByHql(hql2, params);
		String hql3=" FROM MerchantTaskDetail3Model WHERE bmtkid=?";
		MerchantTaskDetail3Model task3=merchantTaskDetail3Dao.queryObjectByHql(hql3, params);
		if(task3 != null){
			if(task3.getIsForeign() == 1){
				task3.setIsForeignName("是");
			}else{
				task3.setIsForeignName("否");
			}
		}
		
		Map<String,Object> map=new HashMap<String, Object>();
		MerchantTaskDataBean bean = new MerchantTaskDataBean();
		BeanUtils.copyProperties(task, bean);
		
		UnitModel unit=getSeqNoByUnno( task.getUnno());
		bean.setUnName(unit.getUnitName());
		bean.setInfoName(getMInfoName(bean.getMid()));
		
		String sql="select * from SYS_PARAM t WHERE t.title='HrtFrameWork'";
		List<Map<String, Object>> list=merchantInfoDao.queryObjectsBySqlList(sql);
		Map<String, Object> param=list.get(0);
		map.put("UPLOAD", param.get("UPLOAD_PATH"));
		map.put("TASK", bean);
		map.put("TASK1", task1);
		map.put("TASK2", task2);
		map.put("TASK3", task3);
		return map;
	}

	private MerchantInfoModel getInfoModelTwo(MerchantTaskDetail1Model t1,MerchantTaskDetail2Model t2,MerchantTaskDetail3Model t3,String mid){
		Object[] params={mid};
		String hql=" FROM MerchantInfoModel WHERE MID=?";
		MerchantInfoModel m=merchantInfoDao.queryObjectByHql(hql,params);
//		MerchantInfoModel m = new MerchantInfoModel();
//		m.setMid(mid);
		if(t1!=null){
			if(t1.getBusid()!=null){
				m.setBusid(t1.getBusid());
				m.setMaintainUserId(t1.getBusid());
			}
			if(t1.getBupload()!=null){
				m.setBupLoad(t1.getBupload());
			}
			if(t1.getMaterialUpLoad()!=null){
				m.setMaterialUpLoad(t1.getMaterialUpLoad());
			}
			if(t1.getRegistryUpLoad()!=null){
				m.setRegistryUpLoad(t1.getRegistryUpLoad());
			}
			if(t1.getRupload()!=null){
				m.setRupLoad(t1.getRupload());
			}
			if(t1.getRname()!=null){
				m.setRname(t1.getRname());
			}
			if(t1.getLegalPerson()!=null){
				m.setLegalPerson(t1.getLegalPerson());
			}
			if(t1.getLegalType()!=null){
				m.setLegalType(t1.getLegalType());
			}
			if(t1.getLegalNum()!=null){
				m.setLegalNum(t1.getLegalNum());
			}
			if(t1.getContactAddress()!=null){
				m.setContactAddress(t1.getContactAddress());
			}
			if(t1.getContactPerson()!=null){
				m.setContactPerson(t1.getContactPerson());
			}
			if(t1.getContactPhone()!=null){
				m.setContactPhone(t1.getContactPhone());
			}
			if(t1.getContactTel()!=null){
				m.setContactTel(t1.getContactTel());
			}
		}
		
		if(t2!=null){
			if(t2.getAreaType()!=null){
				m.setAreaType(t2.getAreaType());
			}
			if(t2.getAccType()!=null){
				m.setAccType(t2.getAccType());
			}
			if(t2.getBankAccName()!=null){
				m.setBankAccName(t2.getBankAccName());
			}
			if(t2.getBankAccNo()!=null){
				m.setBankAccNo(t2.getBankAccNo());
			}
			if(t2.getBankBranch()!=null){
				m.setBankBranch(t2.getBankBranch());
			}
			if(t2.getBankType()!=null){
				m.setBankType(t2.getBankType());
			}
			if(t2.getSettleCycle()!=null){
				m.setSettleCycle(t2.getSettleCycle());
			}
			if(t2.getPayBankId()!=null){
				m.setPayBankId(t2.getPayBankId().trim());
			}
		}
		
		if(t3!=null){
			if(t3.getSecondRate()!=null){
				m.setSecondRate(String.valueOf(t3.getSecondRate()));
			}
			if(t3.getBankFeeRate()!=null){
				m.setBankFeeRate(String.valueOf(t3.getBankFeeRate()));
			}
			if(t3.getScanRate()!=null){
				m.setScanRate(t3.getScanRate());
			}
			if(t3.getScanRate1()!=null){
				m.setScanRate1(t3.getScanRate1());
			}
			if(t3.getScanRate2()!=null){
				m.setScanRate2(t3.getScanRate2());
			}
			if(t3.getCreditBankRate()!=null){
				m.setCreditBankRate(String.valueOf(t3.getCreditBankRate()));
			}
			if(t3.getDealAmt()!=null){
				m.setDealAmt(String.valueOf(t3.getDealAmt()));
			}
			if(t3.getFeeAmt()!=null){
				m.setFeeAmt(String.valueOf(t3.getFeeAmt()));
			}
			if(t3.getFeeRateA()!=null){
				m.setFeeRateA(String.valueOf(t3.getFeeRateA()));
			}
			if(t3.getFeeRateD()!=null){
				m.setFeeRateD(String.valueOf(t3.getFeeRateD()));
			}
			if(t3.getFeeRateJ()!=null){
				m.setFeeRateJ(String.valueOf(t3.getFeeRateJ()));
			}
			if(t3.getFeeRateM()!=null){
				m.setFeeRateM(String.valueOf(t3.getFeeRateM()));
			}
			if(t3.getFeeRateV()!=null){
				m.setFeeRateV(String.valueOf(t3.getFeeRateV()));
			}
			if(t3.getIsForeign()!=null){
				m.setIsForeign(t3.getIsForeign());
			}
			if(t3.getFeeAmt()>0){
				m.setMerchantType(2);
			}else{
				m.setMerchantType(1);
			}
		}

		return m;
	}
	private MerchantInfoModel getInfoModelTwo1(MerchantTaskDetail1Model t1,MerchantTaskDetail2Model t2,MerchantTaskDetail3Model t3,String mid){
//		Object[] params={mid};
//		String hql=" FROM MerchantInfoModel WHERE MID=?";
//		MerchantInfoModel m=merchantInfoDao.queryObjectByHql(hql,params);
		MerchantInfoModel m = new MerchantInfoModel();
		m.setMid(mid);
		if(t1!=null){
			m.setAccountStatus("1");
			if(t1.getBusid()!=null){
				m.setBusid(t1.getBusid());
				m.setMaintainUserId(t1.getBusid());
			}
			if(t1.getBupload()!=null){
				m.setBupLoad(t1.getBupload());
			}
			if(t1.getMaterialUpLoad()!=null){
				m.setMaterialUpLoad(t1.getMaterialUpLoad());
			}
			if(t1.getRegistryUpLoad()!=null){
				m.setRegistryUpLoad(t1.getRegistryUpLoad());
			}
			if(t1.getRupload()!=null){
				m.setRupLoad(t1.getRupload());
			}
			if(t1.getRname()!=null){
				m.setRname(t1.getRname());
			}
			if(t1.getLegalPerson()!=null){
				m.setLegalPerson(t1.getLegalPerson());
			}
			if(t1.getLegalType()!=null){
				m.setLegalType(t1.getLegalType());
			}
			if(t1.getLegalNum()!=null){
				m.setLegalNum(t1.getLegalNum());
			}
			if(t1.getContactAddress()!=null){
				m.setContactAddress(t1.getContactAddress());
			}
			if(t1.getContactPerson()!=null){
				m.setContactPerson(t1.getContactPerson());
			}
			if(t1.getContactPhone()!=null){
				m.setContactPhone(t1.getContactPhone());
			}
			if(t1.getContactTel()!=null){
				m.setContactTel(t1.getContactTel());
			}
		}

		if(t2!=null){
			m.setAccountStatus("2");
			if(t2.getAreaType()!=null){
				m.setAreaType(t2.getAreaType());
			}
			if(t2.getAccType()!=null){
				m.setAccType(t2.getAccType());
			}
			if(t2.getBankAccName()!=null){
				m.setBankAccName(t2.getBankAccName());
			}
			if(t2.getBankAccNo()!=null){
				m.setBankAccNo(t2.getBankAccNo());
			}
			if(t2.getBankBranch()!=null){
				m.setBankBranch(t2.getBankBranch());
			}
			if(t2.getBankType()!=null){
				m.setBankType(t2.getBankType());
			}
			if(t2.getSettleCycle()!=null){
				m.setSettleCycle(t2.getSettleCycle());
			}
			if(t2.getPayBankId()!=null){
				m.setPayBankId(t2.getPayBankId().trim());
			}
		}

		if(t3!=null){
			m.setAccountStatus("2");
			if(t3.getSecondRate()!=null){
				m.setSecondRate(String.valueOf(t3.getSecondRate()));
			}
			if(t3.getBankFeeRate()!=null){
				m.setBankFeeRate(String.valueOf(t3.getBankFeeRate()));
			}
			if(t3.getScanRate()!=null){
				m.setScanRate(t3.getScanRate());
			}
			if(t3.getScanRate1()!=null){
				m.setScanRate1(t3.getScanRate1());
			}
			if(t3.getScanRate2()!=null){
				m.setScanRate2(t3.getScanRate2());
			}
			if(t3.getCreditBankRate()!=null){
				m.setCreditBankRate(String.valueOf(t3.getCreditBankRate()));
			}
			if(t3.getDealAmt()!=null){
				m.setDealAmt(String.valueOf(t3.getDealAmt()));
			}
			if(t3.getFeeAmt()!=null){
				m.setFeeAmt(String.valueOf(t3.getFeeAmt()));
			}
			if(t3.getFeeRateA()!=null){
				m.setFeeRateA(String.valueOf(t3.getFeeRateA()));
			}
			if(t3.getFeeRateD()!=null){
				m.setFeeRateD(String.valueOf(t3.getFeeRateD()));
			}
			if(t3.getFeeRateJ()!=null){
				m.setFeeRateJ(String.valueOf(t3.getFeeRateJ()));
			}
			if(t3.getFeeRateM()!=null){
				m.setFeeRateM(String.valueOf(t3.getFeeRateM()));
			}
			if(t3.getFeeRateV()!=null){
				m.setFeeRateV(String.valueOf(t3.getFeeRateV()));
			}
			if(t3.getIsForeign()!=null){
				m.setIsForeign(t3.getIsForeign());
			}
			if(t3.getFeeAmt()>0){
				m.setMerchantType(2);
			}else{
				m.setMerchantType(1);
			}
		}
		
		return m;
	}

	@Override
	public HSSFWorkbook exportAll(MerchantTaskDataBean bean, UserBean userBean,
			String ids) {
		String sql =" select t.bmtkid , t.taskid ,t.unno,t.mid ,t.taskcontext ,t.processcontext ,t.approvedate, " +
					" nvl(m.singlelimit,0) singlelimit, nvl(m.dailylimit,0) dailylimit," +
					" m.singlelimit1, m.dailylimit1, (case m.isauthorized  when 1 then '否' when 0 then '是' end) isauthorized, " +
					" s.login_name" +
					" from bill_merchanttaskdata t ,bill_merchanttaskdetail4 m ,sys_user s where t.bmtkid=m.bmtkid and t.approveuid =s.user_id and t.bmtkid in("+ids+") ";
		if(bean.getApproveStatus()!=null && !"".equals(bean.getApproveStatus())){
			sql+=" and t.approvestatus="+bean.getApproveStatus();
		}
		if(bean.getStartDay()!=null && !"".equals(bean.getStartDay()) &&  bean.getEndDay()!=null && !"".equals(bean.getEndDay())){
			sql+= " AND to_char(t.approvedate,'yyyy-MM-dd') >= '"+bean.getStartDay()+"'";
			sql+=" AND to_char(t.approvedate,'yyyy-MM-dd') <= '"+bean.getEndDay()+"'";
		}
		if(bean.getMid()!=null && !"".equals(bean.getMid())){
			sql +=" and t.mid='"+bean.getMid()+"'";
		}
		sql+=" and t.type=8";
		List<Map<String, Object>> data = merchantTaskDataDao.queryObjectsBySqlList(sql);
		List<String> excelHeader = new ArrayList<String>();
		Map<String, Object> headMap = new HashMap<String, Object>();
		excelHeader.add("BMTKID");
		excelHeader.add("TASKID");
		excelHeader.add("UNNO");
		excelHeader.add("MID");
		excelHeader.add("TASKCONTEXT");
		excelHeader.add("PROCESSCONTEXT");
		excelHeader.add("APPROVEDATE");
		excelHeader.add("DAILYLIMIT");
		excelHeader.add("SINGLELIMIT");
		excelHeader.add("DAILYLIMIT1");
		excelHeader.add("SINGLELIMIT1");
		excelHeader.add("ISAUTHORIZED");
		excelHeader.add("LOGIN_NAME");
		

		headMap.put("BMTKID", "主键");
		headMap.put("TASKID", "工单编号");
		headMap.put("UNNO", "机构号");
		headMap.put("MID", "商户编号");
		headMap.put("TASKCONTEXT", "工单描述");
		headMap.put("PROCESSCONTEXT", "审批描述");
		headMap.put("APPROVEDATE", "受理日期");
		headMap.put("DAILYLIMIT", "贷记卡单日限额");
		headMap.put("SINGLELIMIT", "贷记卡单笔限额");
		headMap.put("DAILYLIMIT1", "借记卡单日限额");
		headMap.put("SINGLELIMIT1", "借记卡单笔限额");
		headMap.put("ISAUTHORIZED", "是否开通预授权");
		headMap.put("LOGIN_NAME", "审批人");
		

		return ExcelUtil.export("风控工单明细资料", data, excelHeader, headMap);
	}

	@Override
	public DataGridBean queryChangeT0Page(MerchantTaskDataBean bean) {
		Map<String, Object> param=new HashMap<String, Object>();
		String sql = "select * from bill_merchanttaskdata  where 1 = 1 and type=9 ";
		String sqlCount = "select count(*) from bill_merchanttaskdata where 1 = 1 and type=9";
		if (bean.getApproveStatus() != null&&!"".equals(bean.getApproveStatus())) {
			sql += " AND approveStatus=:approveStatus" ;
			sqlCount += " AND approveStatus=:approveStatus" ;
			param.put("approveStatus", bean.getApproveStatus() );
		}
		
		if(bean.getStartDay()!=null && !"".equals(bean.getStartDay()) &&  bean.getEndDay()!=null && !"".equals(bean.getEndDay())){
			sql+= " AND to_char(Maintaindate,'yyyy-MM-dd') >= '"+bean.getStartDay()+"'";
			sql+=" AND to_char(Maintaindate,'yyyy-MM-dd') <= '"+bean.getEndDay()+"'";
			sqlCount+= " AND to_char(Maintaindate,'yyyy-MM-dd') >= '"+bean.getStartDay()+"'";
			sqlCount+=" AND to_char(Maintaindate,'yyyy-MM-dd') <= '"+bean.getEndDay()+"'";
		}
		
		if(bean.getMid()!=null && !"".equals(bean.getMid())){
			sql  += " and mid=:mid";
			sqlCount += " and mid=:mid";
			param.put("mid", bean.getMid());
		}
		if(bean.getInfoName()!=null && !"".equals(bean.getInfoName())){
			List mids=getMid(bean.getInfoName().trim());
			for(int i=0;i<mids.size();i++){
					   String mid="mid"+i;
					   if(i==0){
						   sql  += " and mid=:"+mid;
						   sqlCount += " and mid=:"+mid;
						   param.put(mid, mids.get(i)); 
					   }else{
						   sql  += " or (mid=:"+mid+" AND approveStatus=:approveStatus)";
						   sqlCount += "  or (mid=:"+mid+" AND approveStatus=:approveStatus)";;
					      param.put(mid, mids.get(i));
					      param.put("approveStatus", bean.getApproveStatus() );
					   }
			}
			
		}
		if (bean.getSort() != null) {
			sql += " order by " + bean.getSort() + " " + bean.getOrder();
		}
		long counts = merchantTaskDataDao.querysqlCounts2(sqlCount, param);
		List<MerchantTaskDataModel> dateList = merchantTaskDataDao.queryObjectsBySqlLists(sql, param, bean.getPage(), bean.getRows(), MerchantTaskDataModel.class);
		DataGridBean dataGridBean = formatToDataGrid(dateList, counts);
		
		return dataGridBean;
	}

	@Override
	public boolean updateMerchantLimit(MerchantTaskDataBean bean, UserBean userSession) {
		if("K".equals(bean.getApproveStatus())) return true;
		MerchantInfo info = new MerchantInfo();
		String use = userSession.getUserName();
		Date date = new Date();
		if (null==bean.getMtype()||0==bean.getMtype()) {
			info.setSingleLimit(bean.getSingleLimit());
			info.setDailyLimit(bean.getDailyLimit());
			info.setDebittxnlimit(bean.getSingleLimit1());
			info.setDebitdailylimit(bean.getDailyLimit1());
		}else {
			info.setMinfo2(bean.getDailyLimit().toString());
			if("".equals(bean.getSingleLimit())||bean.getSingleLimit()==null||bean.getSingleLimit()==0){
			}else{
				info.setMinfo1(bean.getSingleLimit().toString());
			}
		}
		info.setAccountStatus("3");
		info.setMid(bean.getMid());
		info.setCby(use);
		info.setSales("1");//业务人员
		info.setMainTenance("1");//维护人员
		info.setMaintainDate(ClassToJavaBeanUtil.convertToXMLGregorianCalendar(date));
		Boolean falg=gsClient.updateMerchantInfo(info,"hrtREX1234.Java");
		log.info("商户工单调整限额 推送综合：mid="+bean.getMid()+"; dailyLimit="+bean.getDailyLimit()+"; singleLimit="+bean.getSingleLimit()+";debitdailylimit="+bean.getDailyLimit1()+"; debittxnlimit="+bean.getSingleLimit1()+"; "+falg);
		return falg;
	}

	@Override
	public List<Map<String, Object>> queryMerchantLimit4(UserBean userSession, String bmtkids) {
		String sql = "select m1.bmtkid, m1.mid, m2.mtype, m2.singlelimit,m2.dailylimit,m2.singlelimit1,m2.dailylimit1 from bill_merchanttaskdata m1, bill_merchanttaskdetail4 m2 where m2.bmtkid in ("+bmtkids+") and m1.bmtkid=m2.bmtkid ";
		return merchantTaskDataDao.queryObjectsBySqlObject(sql);
	}

	@Override
	public void updateTaskDataStatusById(Integer merTaskDataId, String status,UserBean user) {
		MerchantTaskDataModel mo=merchantTaskDataDao.getObjectByID(MerchantTaskDataModel.class,
				Integer.valueOf(merTaskDataId));
			
		mo.setApproveDate(new Date());
		mo.setApproveStatus(status);
		mo.setApproveUId(user.getUserID());
		merchantTaskDataDao.updateObject(mo);
		todoDao.editStatusTodo("bill_task",mo.getBmtkid().toString());
	}
	
	
}
