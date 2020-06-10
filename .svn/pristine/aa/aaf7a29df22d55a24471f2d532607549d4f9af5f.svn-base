package com.hrt.biz.bill.dao.impl;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.hrt.biz.bill.dao.BillBpFileDao;
import com.hrt.frame.base.dao.impl.BaseHibernateDaoImpl;

public class BillBpFileDaoImpl extends BaseHibernateDaoImpl<Object> implements BillBpFileDao {

	@Override
	public List<Map<String, String>> executeSql(String sql,Map<String,Object> param) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
}
