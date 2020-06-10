package com.hrt.frame.dao.sysadmin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.UserBean;

public class UnitDaoImpl extends BaseHibernateDaoImpl<UnitModel> implements IUnitDao {

	@Override
	public String queryUnitMaxUnNo(String hql, Map<String, Object> param) {
		String result=null;
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		if(query.uniqueResult() != null){
			result=query.uniqueResult().toString();
		}
		return result;
	}

	@Override
	public List queryProvince(String sql) {
		Query query=this.getCurrentSession().createSQLQuery(sql);
		return query.list();
	}

	@Override
	public List queryUnno(UserBean user) {
		List result = new ArrayList();
		UnitModel unitModel = super.getObjectByID(UnitModel.class, user.getUnNo());
		String sql = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if("110000".equals(user.getUnNo())){
			result.add("110000");
			return result;
		}else if(unitModel.getUnAttr() == 2 && unitModel.getUnLvl() == 0){		//如果为部门机构并且级别为总公司
			UnitModel parent = unitModel.getParent();
			result.add(parent.getUnNo());
			return result;
		}else if(unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 1){		//如果为公司机构并且级别为分公司
			sql = "SELECT UNNO  FROM SYS_UNIT  WHERE UPPER_UNIT IN (SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '"+user.getUnNo()+"' OR UNNO = '"+user.getUnNo()+"') OR UNNO = '"+user.getUnNo()+"'";
			List<Map<String, String>> listmap = super.executeSql(sql);
			if(listmap != null && listmap.size() > 0){
				for (Map<String, String> m : listmap) {
					result.add(m.get("UNNO"));
				}
				return result;
			}else{
				result.add(user.getUnNo());
				return result;
			}
		}else if (unitModel.getUnAttr() == 1 && unitModel.getUnLvl() == 2){
			sql = "SELECT UNNO FROM SYS_UNIT WHERE UPPER_UNIT = '" + user.getUnNo() +"' OR UNNO = '" + user.getUnNo()+"'";
			List<Map<String, String>> listmap = super.executeSql(sql);
			if(listmap != null && listmap.size() > 0){
				for (Map<String, String> m : listmap) {
					result.add(m.get("UNNO"));
				}
				return result;
			}else{
				result.add(user.getUnNo());
				return result;
			}
		}else{
			result.add(user.getUnNo());
			return result;
		}
	}

}
