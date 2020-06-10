package com.hrt.frame.base.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

import com.hrt.frame.base.dao.IBaseHibernateDao;

/**
 * 类功能：Dao父类
 * 创建人：wwy
 * 创建日期：2012-11-29
 */
public class BaseHibernateDaoImpl<T> implements IBaseHibernateDao<T> {
	
	private Log log = LogFactory.getLog(BaseHibernateDaoImpl.class);
	
	private SessionFactory sessionFactory;

	public Session openSession1() {
		return sessionFactory.openSession();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public Serializable saveObject(T o) {
		return this.getCurrentSession().save(o);
	}
	
	@Override
	public void deleteObject(T o) {
		this.getCurrentSession().delete(o);
	}
	@Override
	public void executeUpdate(String sql) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	@Override
	public void updateObject(T o) {
		this.getCurrentSession().update(o);
	}
	
	@Override
	public void saveOrUpdateObject(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryObjectsByHqlList(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	@Override
	public List<T> queryObjectsByHqlList(String hql, Map<String, Object> param) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		return query.list();
	}
	@Override
	public List<T> queryObjectsBySqlList(String sql, Map<String, Object> param) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		return query.list();
	}
	
	@Override
	public List<T> queryObjectsByHqlList(String hql, Object[] param) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		return query.list();
	}
	
	@Override
	public T queryObjectsByHqlListOpenNewSession(String hql, Object[] param) {
		Query query = this.openSession1().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		List<T> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List<T> queryObjectsByHqlList(String hql, List<Object> param) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				query.setParameter(i, param.get(i));
			}
		}
		return query.list();
	}
	
	@Override
	public List<T> queryObjectsByHqlList(String hql, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query query = this.getCurrentSession().createQuery(hql);
		query.setFirstResult((page - 1) * rows)
			.setMaxResults(rows);
		
		log.debug(query.getQueryString());
		return query.list();
	}
	
	@Override
	public List<T> queryObjectsByHqlList(String hql, Map<String, Object> param, Integer page,Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		query.setFirstResult((page - 1) * rows)
			.setMaxResults(rows);
		
		log.debug(query.getQueryString());
		return query.list();
	}
	
	@Override
	public List<T> queryObjectsBySqlLists(String sql, Map<String, Object> param, Integer page,Integer rows,Class cls) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		
		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(cls);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		query.setFirstResult((page - 1) * rows)
			.setMaxResults(rows);
		
		log.debug(query.getQueryString());
		return query.list();
	}
	

	@Override
	public T getObjectByID(Class<T> c, Serializable id) {
		return (T)this.getCurrentSession().get(c, id);
	}
	
	@Override
	public T queryObjectByHql(String hql, Object[] param) {

		List<T> list = this.queryObjectsByHqlList(hql, param);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public T queryObjectByHql(String hql, List<Object> param) {

		List<T> list = this.queryObjectsByHqlList(hql, param);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public T queryObjectBySql(String sql,  Map<String, Object> param) {

		List<T> list = this.queryObjectsBySqlList(sql, param);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Long queryCounts(String hql) {
		Query query = this.getCurrentSession().createQuery(hql);
		return (Long)query.uniqueResult();
	}

	@Override
	public Long queryCounts(String hql, Map<String, Object> param) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		return (Long)query.uniqueResult();
	}
	public BigDecimal querysqlCounts(String sql, Map<String, Object> param) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		return (BigDecimal) query.uniqueResult();
	}
	
	@Override
	public Integer executeHql(String hql) {
		Query query = this.getCurrentSession().createQuery(hql);
		return query.executeUpdate();
	}
	
	@Override
	public Integer executeHql(String hql, Object[] param) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i, param[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public Integer executeHql(String hql, Map<String, Object> param) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		return query.executeUpdate();
	}
	
	@Override
	public Integer executeHqlIn(String hql, Map<String, Object[]> param) {
		Query query = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameterList(key, param.get(key));
			}
		}
		return query.executeUpdate();
	}
	
	@Override
	public Integer executeSqlUpdate(String sql, Map<String, Object> param) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		return query.executeUpdate();
	}
	
	@Override
	public List<Map<String, String>> executeSql(String sql) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	@Override
	public List<Map<String, Object>> executeSql2(String sql,Map<String,Object> param) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	@Override
	public List<T> executeSqlT(String sql,Class cls,Map<String, Object> param) {
		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(cls);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		return query.list();
	}

public List<Map<String, String>> queryObjectsBySqlList(String sql, Map<String, Object> param, Integer page, Integer rows){
		
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		
		Query query = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		query.setFirstResult((page - 1) * rows)
			.setMaxResults(rows);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		log.debug(query.getQueryString());
		return query.list();
	}

public List<Map<String, Object>> queryObjectsBySqlList2(String sql, Map<String, Object> param, Integer page, Integer rows){
	
	if (page == null || page < 1) {
		page = 1;
	}
	if (rows == null || rows < 1) {
		rows = 10;
	}
	
	Query query = this.getCurrentSession().createSQLQuery(sql);
	if (param != null && param.size() > 0) {
		for (String key : param.keySet()) {
			query.setParameter(key, param.get(key));
		}
	}
	query.setFirstResult((page - 1) * rows)
		.setMaxResults(rows);
	query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	log.debug(query.getQueryString());
	return query.list();
}
	
	@Override
	public List<Map<String, String>> queryObjectsBySqlList(String sql) {
		Query query=this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	@Override
	public List<Map<String, String>> queryObjectsBySqlListMap(String sql,Map<String, Object> param) {
		Query query=this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	@Override
	public List<Map<String, Object>> queryObjectsBySqlObject(String sql) {
		Query query=this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	@Override
	public List<T> queryObjectsBySqlList(String sql, Map<String, Object> param, Class cls) {
		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(cls);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		return query.list();
	}

	@Override
	public Integer querysqlCounts2(String sql, Map<String, Object> param) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		int count=Integer.parseInt(query.list().get(0).toString());
		return count;
	}

	@Override
	public List<Map<String, Object>> queryObjectsBySqlListMap2(String sql,
			Map<String, Object> param) {
		Query query=this.getCurrentSession().createSQLQuery(sql);
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				query.setParameter(key, param.get(key));
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Transactional
	@Override
	public void batchSaveObject(List<Object> list) {
		Session session=this.getCurrentSession();
		for(int i=0;i<list.size();i++){
			Object o=list.get(i);
			session.save(o);
			if((i+1)%50==0){
				session.flush();
				session.clear();
			}
		}
		
	}

	@Override
	public String querySequence(String sql) {
		Query query=this.getCurrentSession().createSQLQuery(sql);
		Object seq =  query.uniqueResult();
		return String.valueOf(seq);
	}

}
