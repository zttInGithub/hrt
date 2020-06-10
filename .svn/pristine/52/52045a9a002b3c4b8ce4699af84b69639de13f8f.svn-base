package com.hrt.frame.service.sysadmin.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hrt.biz.query.ListPage;
import com.hrt.biz.query.ListPageColumn;
import com.hrt.biz.query.ListPageConfig;
import com.hrt.frame.dao.sysadmin.IQueryDao;
import com.hrt.frame.service.sysadmin.IQueryService;

public class QueryServiceImpl implements IQueryService {
	
	private IQueryDao queryDao;
	public IQueryDao getQueryDao() {
		return queryDao;
	}
	public void setQueryDao(IQueryDao queryDao) {
		this.queryDao = queryDao;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List queryAll(String sql ) {
		return queryDao.queryAll(sql);
	}
	/**
	 * 
SELECT * FROM ( SELECT A.*, ROWNUM RN 
FROM (SELECT * FROM TABLE_NAME) A WHERE ROWNUM <= 40 ) WHERE RN >= 21

SELECT TOP 30 * FROM ARTICLE WHERE ID NOT IN(SELECT TOP 1000 ID FROM ARTICLE ORDER BY YEAR DESC, ID DESC)
 ORDER BY YEAR DESC,ID DESC 
	 */
	@Override
	public String getSql(ListPage listPage, int pageNum, HttpServletRequest request) {
		//[sort, order, page, LISTPAGECODE, rows]
		String sort=request.getParameter("SORT");//排序方式desc /asc
		if("ASC".equals(sort)){
			sort="DESC";
		}else{
			sort="ASC";
		}
		String orderui=request.getParameter("ORDER");//排序字段
		String tableName=listPage.getTablename();//表名
		StringBuffer searchColumns=new StringBuffer();
		StringBuilder sqlBuilder=new StringBuilder(" SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (");
		String keyColumn=null;
		String SqlWhere="";
		String order=" ORDER BY ";
		//得到配置文件中每页的显示记录行数
		int pageSize=listPage.getPageSize();
		List<ListPageColumn> columns=listPage.getColumns();
		for (ListPageColumn column : columns) {
			if(column.isKey()){//主键
				keyColumn=column.getName();
			}
			searchColumns.append(column.getName()+",");
			if(request.getParameter(column.getName())!=null&&!"".equals(request.getParameter(column.getName()))){
				if(column.isNum()){//是否为数值型
					SqlWhere+=" AND "+column.getName()+" = '"+request.getParameter(column.getName())+"' ";
				}else{
					SqlWhere+=" AND "+column.getName()+" like '%"+request.getParameter(column.getName())+"%' ";
				}
				request.setAttribute(column.getName(), request.getParameter(column.getName()));
			}
		}
		sqlBuilder.append(" SELECT "+searchColumns.toString().substring(0, searchColumns.length()-1));
		sqlBuilder.append(" FROM "+tableName+" WHERE 1=1 ");
		sqlBuilder.append(SqlWhere);
		sqlBuilder.append(" ) A WHERE ROWNUM <="+pageNum*pageSize);
		sqlBuilder.append(" ) WHERE RN >= "+(pageNum-1)*pageSize);
		
		if(orderui==null||"".equals(orderui)){
			order=order+keyColumn;
		}else{
			order=order+orderui+"  "+sort;
		}
		sqlBuilder.append(order);
		System.out.println("sql---->"+sqlBuilder.toString());
		return sqlBuilder.toString();
	}
	@Override
	public String getExlSql(ListPage listPage, int pageNum, HttpServletRequest request) {
		//[sort, order, page, LISTPAGECODE, rows]
		String sort=request.getParameter("SORT");//排序方式desc /asc
		if("ASC".equals(sort)){
			sort="DESC";
		}else{
			sort="ASC";
		}
		String orderui=request.getParameter("ORDER");//排序字段
		String tableName=listPage.getTablename();//表名
		StringBuffer searchColumns=new StringBuffer();
		StringBuilder sqlBuilder=new StringBuilder(" SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (");
		String keyColumn=null;
		String SqlWhere="";
		String order=" ORDER BY ";
		//得到配置文件中每页的显示记录行数
		int pageSize=listPage.getPageSize();
		List<ListPageColumn> columns=listPage.getColumns();
		for (ListPageColumn column : columns) {
			if(column.isKey()){//主键
				keyColumn=column.getName();
			}
			searchColumns.append(column.getName()+",");
			if(request.getParameter(column.getName()+"S")!=null&&!"".equals(request.getParameter(column.getName()+"S"))){
				if(column.isNum()){//是否为数值型
					SqlWhere+=" AND "+column.getName()+" = '"+request.getParameter(column.getName()+"S")+"' ";
				}else{
					SqlWhere+=" AND "+column.getName()+" like '%"+request.getParameter(column.getName()+"S")+"%' ";
				}
				request.setAttribute(column.getName(), request.getParameter(column.getName()));
			}
		}
		sqlBuilder.append(" SELECT "+searchColumns.toString().substring(0, searchColumns.length()-1));
		sqlBuilder.append(" FROM "+tableName+" WHERE 1=1 ");
		sqlBuilder.append(SqlWhere);
		sqlBuilder.append(" ) A WHERE ROWNUM <="+pageNum*pageSize);
		sqlBuilder.append(" ) WHERE RN >= "+(pageNum-1)*pageSize);
		
		if(orderui==null||"".equals(orderui)){
			order=order+keyColumn;
		}else{
			order=order+orderui+"  "+sort;
		}
		sqlBuilder.append(order);
		System.out.println("sql---->"+sqlBuilder.toString());
		return sqlBuilder.toString();
	}
	@Override
	public int getTotal(ListPage listPage, HttpServletRequest request) {
		String tableName=listPage.getTablename();
		String SqlWhere="";
		String key="";
		List<ListPageColumn> columns=listPage.getColumns();
		for (ListPageColumn column : columns) {
			if(column.isKey()){
				key=column.getName();
			}
			if(request.getParameter(column.getName())!=null&&!"".equals(request.getParameter(column.getName()))){
				if(column.isNum()){//是否为数值型
					SqlWhere+=" AND "+column.getName()+" = '"+request.getParameter(column.getName())+"' ";
				}else{
					SqlWhere+=" AND "+column.getName()+" like '%"+request.getParameter(column.getName())+"%' ";
				}
			}
		}
		StringBuilder sqlBuilder=new StringBuilder(" SELECT COUNT("+key+") AS TOTAL FROM  ");
		sqlBuilder.append(tableName+" WHERE 1=1 ");
		sqlBuilder.append(SqlWhere);
		List list=queryDao.executeSql(sqlBuilder.toString());
		Map<String, Object> infoMap=(Map<String, Object>) list.get(0);
		return Integer.valueOf(infoMap.get("TOTAL").toString()) ;
	}
	@Override
	public String getSql(ListPage listPage, HttpServletRequest request) {
		String tableName=listPage.getTablename();
		StringBuilder sqlBuilder=new StringBuilder(" SELECT *  FROM  ");
		sqlBuilder.append(tableName+" WHERE 1=1 ");
		String SqlWhere="";
		List<ListPageColumn> columns=listPage.getColumns();
		for (ListPageColumn column : columns) {
			if(request.getParameter(column.getName()+"S")!=null&&!"".equals(request.getParameter(column.getName()+"S"))){
				if(column.isNum()){//是否为数值型
					SqlWhere+=" AND "+column.getName()+" = '"+request.getParameter(column.getName()+"S")+"' ";
				}else{
					SqlWhere+=" AND "+column.getName()+" like '%"+request.getParameter(column.getName()+"S")+"%' ";
				}
			}
		}
		sqlBuilder.append(SqlWhere);
		return sqlBuilder.toString();
	}
	
	
}
