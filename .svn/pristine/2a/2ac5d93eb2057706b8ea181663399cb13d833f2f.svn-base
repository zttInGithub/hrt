package com.hrt.biz.bill.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class MerchantInfoDaoImpl extends BaseHibernateDaoImpl<MerchantInfoModel> implements IMerchantInfoDao {

	@Override
	public List<MerchantInfoModel> queryMerchantInfo(String hql,
			Map<String, Object> param, Integer page, Integer rows) {
		return super.queryObjectsByHqlList(hql, param, page, rows);
	}
	
	//查询上传文件路径
	@Override
	public String querySavePath(String title) {
		Session session=this.getCurrentSession();
		Query query=session.createSQLQuery("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title+"'");
		return query.list().get(0).toString();
	}

	@Override
	public List<MerchantInfoModel> queryMerchantInfoSql(String sql,
			Map<String, Object> param, Integer page, Integer rows, Class cls) {
		return super.queryObjectsBySqlLists(sql, param, page, rows, cls);
	}
	
	public Integer getBmid(){
		List list=this.getCurrentSession().createSQLQuery("select S_BILL_MERCHANTINFO.currval from dual").list();
		if(list==null){
			return 1;
		}else{
			return Integer.parseInt(list.get(0).toString());
		}
	}

	@Override
	public String queryLegalNumByMid(String mid) {
		Session session=this.getCurrentSession();
		Query query=session.createSQLQuery("SELECT legalNum FROM bill_merchantinfo WHERE mid='"+mid+"'");
		return query.list().get(0).toString();
	}

	  //查询禁用活动
		@Override
		public String queryNotUseActivity(String title) {
			Session session=this.getCurrentSession();
			List list = session.createSQLQuery("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title+"'").list();
			if(list == null || list.isEmpty() ||list.get(0) == null){
				return null;
			}else{
				return list.get(0).toString();
			}
		}
}
