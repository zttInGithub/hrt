package com.hrt.biz.bill.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hrt.biz.bill.dao.IMerchantTaskDetailDao;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.biz.bill.entity.model.AgentSalesModel;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
public class MerchantTaskDetailDaoImpl  extends BaseHibernateDaoImpl<Object> implements IMerchantTaskDetailDao{

	@Override
	public String findMaxId() {
		List list=this.getCurrentSession().createSQLQuery("select S_BILL_MERCHANTTASKDATA.nextval from dual").list();
		if(list==null){
			return "1";
		}else{
			return list.get(0).toString();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public MerchantInfoModel queryMerchantInfo(String mid,UserBean user) {
		if(mid!=null && mid.length()>0){
			String userhql = "from AgentSalesModel where userID = ? and maintainType != 'D'";
			List<AgentSalesModel> agentSalesModels = this.getCurrentSession().createQuery(userhql).setParameter(0, user.getUserID().toString()).list();
			
			UnitModel unitModel = (UnitModel) this.getCurrentSession().get(UnitModel.class, user.getUnNo());
			String sql = "";
			if(agentSalesModels.size()==0){
				if("110000".equals(user.getUnNo())){
					sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != 'D' AND APPROVESTATUS = 'Y' AND MID = ? ";
				}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
					UnitModel parent = unitModel.getParent();
					if("110000".equals(parent.getUnNo())){
					sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINTYPE != 'D' AND APPROVESTATUS = 'Y' AND MID = ? ";
					}
				}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){
					sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '"+user.getUnNo()+"' OR UNNO = '"+user.getUnNo()+"' AND STATUS = 1) AND MAINTAINTYPE != 'D' AND APPROVESTATUS = 'Y' AND MID = ? ";
				}else{
					sql = "SELECT * FROM BILL_MERCHANTINFO WHERE UNNO = '"+user.getUnNo()+"' AND MAINTAINTYPE != 'D' AND APPROVESTATUS = 'Y' AND MID = ? ";
				}
			}else{
				sql = "SELECT * FROM BILL_MERCHANTINFO WHERE MAINTAINUSERID = '"+agentSalesModels.get(0).getBusid()+"' AND MAINTAINTYPE != 'D' AND APPROVESTATUS = 'Y' AND MID = ? ";
			}
			List<MerchantInfoModel> list = this.getCurrentSession().createSQLQuery(sql).addEntity(MerchantInfoModel.class).setParameter(0, mid).list();
			if(list.size()>0){ 
				
				if(list.get(0).getScanRate() != null && !"".equals(list.get(0).getScanRate())){
					BigDecimal scanRate = new BigDecimal(list.get(0).getScanRate().toString());
					list.get(0).setScanRate(scanRate.doubleValue());
				}
				
				if(list.get(0).getBankFeeRate() != null && !"".equals(list.get(0).getBankFeeRate())){
					BigDecimal bankFeeRate = new BigDecimal(list.get(0).getBankFeeRate().toString());
					list.get(0).setBankFeeRate(bankFeeRate.toString());
				}
				
				if(list.get(0).getFeeAmt() != null && !"".equals(list.get(0).getFeeAmt())){
					BigDecimal feeAmt = new BigDecimal(list.get(0).getFeeAmt().toString());
					Double fa= feeAmt.doubleValue();
					list.get(0).setFeeAmt(fa.toString());
				}
				
				if(list.get(0).getDealAmt() != null && !"".equals(list.get(0).getDealAmt())){
					BigDecimal dealAmt = new BigDecimal(list.get(0).getDealAmt().toString());
					Double da= dealAmt.doubleValue();
					list.get(0).setDealAmt(da.toString()); 
				}
				
				if(list.get(0).getFeeRateD() != null && !"".equals(list.get(0).getFeeRateD())){
					BigDecimal feeRateD = new BigDecimal(list.get(0).getFeeRateD().toString());
					list.get(0).setFeeRateD(feeRateD.toString()); 
				}
				
				if(list.get(0).getFeeRateV() != null && !"".equals(list.get(0).getFeeRateV())){
					BigDecimal feeRateV = new BigDecimal(list.get(0).getFeeRateV().toString());
					list.get(0).setFeeRateV(feeRateV.toString()); 
				}
				
				if(list.get(0).getFeeRateM() != null && !"".equals(list.get(0).getFeeRateM())){
					BigDecimal feeRateM = new BigDecimal(list.get(0).getFeeRateM().toString());
					list.get(0).setFeeRateM(feeRateM.toString()); 
				}
				
				if(list.get(0).getFeeRateJ() != null && !"".equals(list.get(0).getFeeRateJ())){
					BigDecimal feeRateJ = new BigDecimal(list.get(0).getFeeRateJ().toString());
					list.get(0).setFeeRateJ(feeRateJ.toString()); 
				}
				
				if(list.get(0).getFeeRateA() != null && !"".equals(list.get(0).getFeeRateA())){
					BigDecimal feeRateA = new BigDecimal(list.get(0).getFeeRateA().toString());
					list.get(0).setFeeRateA(feeRateA.toString());
				}
				
				if(list.get(0).getPayBankId()!=null && !"".equals(list.get(0).getPayBankId())){
					list.get(0).setPayBankId(list.get(0).getPayBankId().trim());
				}
				return list.get(0); 
			}else{
				return null; 
			} 
		}else{
			return null;
		}
	}
	
	//查询上传文件路径
	@Override
	public String querySavePath(String title) {
		Session session=this.getCurrentSession();
		Query query=session.createSQLQuery("select UPLOAD_PATH from SYS_PARAM where TITLE='"+title+"'");
		
		return query.list().get(0).toString();
	}

	@Override
	public String queryDownloadPath(String title) {
		Session session=this.getCurrentSession();
		Query query=session.createSQLQuery("select DOWNLOAD_PATH from SYS_PARAM where TITLE='"+title+"'");
		return query.list().get(0).toString(); 
	}  

}
