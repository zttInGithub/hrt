package com.hrt.frame.service.sysadmin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hrt.biz.query.ListPage;

public interface IQueryService {
	public int getTotal(ListPage listPage,HttpServletRequest request);
	public List queryAll(String sql);
	public String getSql(ListPage listPage,int pageNum,HttpServletRequest request);
	public String getSql(ListPage listPage, HttpServletRequest request);
	public String getExlSql(ListPage listPage, int page,HttpServletRequest request);
}
