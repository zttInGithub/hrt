package com.hrt.frame.action.sysadmin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.query.ListPage;
import com.hrt.biz.query.ListPageColumn;
import com.hrt.biz.query.ListPageConfig;
import com.hrt.biz.query.ListPageOperation;
import com.hrt.biz.query.PageColumn;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.service.sysadmin.IQueryService;
import com.hrt.frame.utility.ExcelUtil;

/**
 * 公用的查询的action
 */
public class QueryAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(QueryAction.class);
	
	private IQueryService queryService;
	
	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	/**
	 * 通用查询分页返回数据方法
	 */
	public void search(){
		//1.取得从前台返回的要显示的列List<ListPageColumn>
		//2.取得当前页数
		//3.取得排序列**
		//4.取得input条件
		Map<String, Object> json=new HashMap<String, Object>();
		HttpServletRequest request=getRequest();
		String LISTPAGECODE=request.getParameter("LISTPAGECODE");//xml配置文件名
		String ISSEARCH=request.getParameter("ISSEARCH");
		String dis=request.getParameter("DISCOLUMNS");
		ListPage listPage=ListPageConfig.getListPageByCode(LISTPAGECODE);
		List<PageColumn> showColumns=new ArrayList<PageColumn>();//选择的要显示的列
		List<ListPageColumn> listColumns = listPage.getAllColumns();//全部列
		Map<String, Object> disMap=new HashMap<String, Object>();
		if(dis!=null && !"".equals(dis)){
			String[] diss=dis.split(",");
			for (int i = 0; i < diss.length; i++) {
				disMap.put(diss[i], true);
			}
			for (int i = 0; i < listColumns.size(); i++) {
				ListPageColumn col=listColumns.get(i);
				if(disMap.get(col.getName())!=null){
					PageColumn pc=new PageColumn(col);
					showColumns.add(pc);
				}
			}
		}else{
			for (int i = 0; i < listColumns.size(); i++) {
				ListPageColumn col=listColumns.get(i);
				PageColumn pc=new PageColumn(col);
				showColumns.add(pc);
			}
		}
		String sort=request.getParameter("SORT");//排序方式desc /asc
		if("ASC".equals(sort)){
			sort="DESC";
		}else{
			sort="ASC";
		}
		
		String title=listPage.getTitle();//页面标题
		int pageSize=listPage.getPageSize();//页面显示记录条数
		List<ListPageOperation> pageOperations=listPage.getOperations();//扩展内容
		int total=0;//记录总数
		int page=Integer.valueOf(request.getParameter("PAGE"));//请求数据的页码
		String sql="";///分页的sql语句
		int pageNum=0;//总页数
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();//查询的数据
		try{
			sql= queryService.getSql(listPage, page, request);
			total=queryService.getTotal(listPage, request);
			list=queryService.queryAll(sql);
			pageOperations=listPage.getOperations();
			if(total<=pageSize){
				pageNum=1;
			}else{
				if(total%pageSize==0){
					pageNum=total/pageSize;
				}else{
					pageNum=total/pageSize+1;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		if("true".equals(ISSEARCH)){
			json.put("ISSEARCH", "true");
		}else{
			json.put("ISSEARCH", "none");
		}
		/*request.setAttribute("LISTPAGECODE", LISTPAGECODE);//
		request.setAttribute("DISCOLUMNS", dis);//
		request.setAttribute("PAGE", page);//
		request.setAttribute("TITLE", title);//
		request.setAttribute("PAGESIZE", pageSize);//
		request.setAttribute("PAGENUM", pageNum);//
		request.setAttribute("COLUMNS", showColumns);//
		request.setAttribute("ALLCOLUMNS", listColumns);//
		request.setAttribute("OPERATIONS", pageOperations);
		request.setAttribute("TOTAL", total);//
		request.setAttribute("LIST", list);//
*/	
		
		for (ListPageColumn column : listColumns) {
			if(request.getParameter(column.getName())!=null&&!"".equals(request.getParameter(column.getName()))){
				json.put(column.getName(), request.getParameter(column.getName()));
			}
		}
		json.put("SORT",sort );
		json.put("LISTPAGECODE",LISTPAGECODE );
		json.put("DISCOLUMNS",dis );
		json.put("PAGE", page);
		json.put("TITLE", title);
		json.put("PAGESIZE",pageSize );
		json.put("PAGENUM",pageNum );
		json.put("COLUMNS",showColumns );
		json.put("ALLCOLUMNS", listColumns);
		json.put("OPERATIONS",pageOperations );
		json.put("TOTAL",total );
		json.put("LIST", list);
		super.writeJson(json);
	}
	
	/**
	 * 初始化多功能查询页面
	 * */
	public void init(){
		HttpServletRequest request=getRequest();
		Map<String, Object> json=new HashMap<String, Object>();
		String LISTPAGECODE=request.getParameter("LISTPAGECODE");//xml配置文件名
		ListPage listPage=ListPageConfig.getListPageByCode(LISTPAGECODE);
		List<ListPageColumn> listColumns = listPage.getAllColumns();//动态显示列
		List<PageColumn> showColumns = new ArrayList<PageColumn>();
		for (ListPageColumn col : listColumns) {
			PageColumn pc=new PageColumn(col); 
			showColumns.add(pc);
		}
		String title=listPage.getTitle();//页面标题
		int pageSize=listPage.getPageSize();//页面显示记录条数
		List<ListPageOperation> pageOperations=listPage.getOperations();//扩展内容
		int total=0;//记录总数
		int pageNum=0;//总页数
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			String sql= queryService.getSql(listPage, 1, request);
			total=queryService.getTotal(listPage, request);//记录总数
			list=queryService.queryAll(sql);
			if(total<=pageSize){
				pageNum=1;
			}else{
				if(total%pageSize==0){
					pageNum=total/pageSize;
				}else{
					pageNum=total/pageSize+1;
				}
			}
			
		} catch (Exception e) {
			log.error("init query error " + e);
		}
		/*request.setAttribute("LISTPAGECODE", LISTPAGECODE);//
		request.setAttribute("PAGE", 1);//
		request.setAttribute("SORT", "DESC");//
		request.setAttribute("PAGENUM", pageNum);//
		request.setAttribute("ISSEARCH", "none");
		request.setAttribute("TITLE", title);//
		request.setAttribute("PAGESIZE", pageSize);//
		request.setAttribute("COLUMNS", listColumns);//
		request.setAttribute("ALLCOLUMNS", listColumns);//
		request.setAttribute("OPERATIONS", pageOperations);
		request.setAttribute("TOTAL", total);//
		request.setAttribute("LIST", list);//
*/		
		json.put("LISTPAGECODE",LISTPAGECODE );
		json.put("SORT","DESC" );
		json.put("PAGE", 1);
		json.put("ISSEARCH","none" );
		json.put("TITLE", title);
		json.put("PAGESIZE",pageSize );
		json.put("PAGENUM",pageNum );
		json.put("COLUMNS",showColumns );
		json.put("ALLCOLUMNS", listColumns);
		json.put("OPERATIONS",pageOperations );
		json.put("TOTAL",total );
		json.put("LIST", list);
		super.writeJson(json);
	}
	/**
	 * 导出Excel
	 */
	public String exportExl(){
		HttpServletRequest request=getRequest();
		HttpServletResponse response=getResponse();
		try {
			String flag=request.getParameter("FLAG");
			String code = request.getParameter("LISTPAGECODEE");//
			String pagenum=request.getParameter("PAGE1");
			Integer page = Integer.valueOf(pagenum);
			String dis=request.getParameter("DISCOLUMNSS");
			ListPage listPage=ListPageConfig.getListPageByCode(code);
			List<ListPageColumn> showColumns=new ArrayList<ListPageColumn>();//选择的要显示的列
			List<ListPageColumn> listColumns = listPage.getAllColumns();//全部列
			Map<String, Object> disMap=new HashMap<String, Object>();
			if(dis!=null && !"".equals(dis)){
				String[] diss=dis.split(",");
				for (int i = 0; i < diss.length; i++) {
					disMap.put(diss[i], true);
				}
				for (int i = 0; i < listColumns.size(); i++) {
					ListPageColumn col=listColumns.get(i);
					if(disMap.get(col.getName())!=null){
						showColumns.add(col);
					}
				}
			}else{
				showColumns=listColumns;
			}
			
			List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
			if("exccuu".equals(flag)){
				String sql=queryService.getExlSql(listPage, page, request);
				data=queryService.queryAll(sql);
			}else if("excall".equals(flag)){
				String sql=queryService.getSql(listPage, request);
				data=queryService.queryAll(sql);
			}
			List<String> heads=new ArrayList<String>();
			Map<String, Object> headMap=new HashMap<String, Object>();
			for(int i=0;i<showColumns.size();i++){
				ListPageColumn cl=showColumns.get(i);
				headMap.put(cl.getName(), cl.getTitle());
				heads.add(cl.getName());
			}
			
			HSSFWorkbook wb = ExcelUtil.export("sheet01",data,heads,headMap);  
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String((listPage.getTitle()+"导出").getBytes("GBK"), "ISO-8859-1") + ".xls");  
	        OutputStream ouputStream= response.getOutputStream();
	        wb.write(ouputStream);  
	        ouputStream.flush();  
	        ouputStream.close();  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
