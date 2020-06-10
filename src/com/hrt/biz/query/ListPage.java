package com.hrt.biz.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 查询界面元数据
 * @author kevin
 *
 */
public class ListPage {
	//查询页面标题
	private String title="";
	
	private boolean isDynamic=false;
	
	private int dynamicPos;
	
	public boolean isDynamic() {
		return isDynamic;
	}
	public String getGroupByFeild(){
		return this.allColumns.get(this.dynamicPos).getGroupByField();
	}public String getGroupByFeildTitle(){
		return this.allColumns.get(this.dynamicPos).getGroupByFieldTitle();
	}
	//查询对应的表
	private String tablename="";
	//页面内的数据列
	private List<ListPageColumn>  columns=new ArrayList<ListPageColumn>();//当前显示的列
	private List<ListPageColumn>  allColumns=new  ArrayList<ListPageColumn>();//全部的列当columns没有时候使用
	public List<ListPageColumn> getAllColumns() {
		return allColumns;
	}
	public void setAllColumns(List<ListPageColumn> allColumns) {
		this.allColumns = allColumns;
	}
	//页面内的操作列
	private List<ListPageOperation> operations=new ArrayList<ListPageOperation>();
	
	//主键
	private List<String>  keyColumnNames=new ArrayList<String>();
	private String filter="";
	private String orderBy="";
	private int pageSize=15;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		if(pageSize==0) return ;
		this.pageSize = pageSize;
	}
	private String filterPageUrl="blank.htm";
	
	public String getFilterPageUrl() {
		return filterPageUrl;
	}
	public void setFilterPageUrl(String filterPageUrl) {
		if(filterPageUrl==null||"".equals(filterPageUrl)) return ;
		this.filterPageUrl = filterPageUrl;
	}
	public String getOrderBy() {
		if("".equals(orderBy)||orderBy.indexOf("ORDER BY")>-1) return orderBy;
		//return " ORDER BY " +orderBy;
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		if(orderBy==null||"".equals(orderBy.trim()))return;
		this.orderBy = orderBy;
	}
	public int getColspan(){
		return this.getOperations().size()+this.getColumns().size();
	}
	public void addOperation(ListPageOperation operationObj){
		this.getOperations().add(operationObj);
	}
	/**
	 *  allColumns defaultShow 的加到columns 
	 * @param columnObj
	 */
	public void addColumn(ListPageColumn columnObj){
		allColumns.add(columnObj);
		if(columnObj.isDefaultShow()){
			this.columns.add(columnObj);
		}
	}
	public List<String> getKeyColumnNames(){
		if(keyColumnNames.size()==0){
			parseKeyColumns();
		}
		return keyColumnNames;
	}
	private void parseKeyColumns(){
		keyColumnNames.clear();
		for(ListPageColumn column:this.allColumns){
			if(column.isKey()){
				keyColumnNames.add(column.getName());
			}
		}
	}
	public Map<String,String> getFormatterColums(){
		Map<String,String> map=new HashMap<String,String>();
		for(ListPageColumn column:this.getColumns()){
			if(column.getFormatter()!=null&&!"".equals(column.getFormatter())){
				map.put(column.getName(),column.getFormatter());
			}
		}
		return map;
		
	}
	public Map getExcelHead(){
		Map map=new HashMap();
		for(ListPageColumn column:columns){
				map.put(column.getName(), column.getTitle());
		}
		return map;
	}
	
	public List getExcelListHead(){
		List list=new ArrayList();
		for(int i=0;i<columns.size();i++){
			ListPageColumn column=columns.get(i);
			list.add(column.getName()+":"+ column.getTitle());
		}
		return list;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public List<ListPageColumn> getColumns() {
		if(columns.size()==0) return this.allColumns;
		return columns;
	}
	/**
	 * 
	 * @param keys 逗号分隔的 KEY  形如：a,b,c,
	 * @return
	 */
	public void procSelectedColumns(String keys){
		if(this.isDynamic()||keys==null||"".equals(keys.trim())){
			return ;
		}
		columns.clear();
		String[] strs=keys.split(",");
		for (int i = 0; i < strs.length; i++) {
			//String temp = strs[i].toUpperCase();
			String temp = strs[i];
			for (ListPageColumn o : this.allColumns) {
				if(temp.equals(o.getName())){
					this.columns.add(o);
					break;
				}
			}
		} 
	}
	//形如：a,b,c,
	public String getSelectedKeys(){
		StringBuilder str=new StringBuilder();
		for(ListPageColumn o:this.getColumns()){
			str.append(o.getName()).append(",");
		}
		return str.toString();
	}
	public void setColumns(List<ListPageColumn> columns) {
		this.columns = columns;
	}

	public List<ListPageOperation> getOperations() {
		return operations;
	}
	public void setOperations(List<ListPageOperation> operations) {
		this.operations = operations;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	//add by zqx
	private String newFilter="";

	public String getNewFilter() {
		return newFilter;
	}
	public void setNewFilter(String newFilter) {
		this.newFilter = newFilter;
	}
	
	
}
