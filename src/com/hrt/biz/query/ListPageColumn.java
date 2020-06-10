package com.hrt.biz.query;

public class ListPageColumn {
	public static final int DEFAULTSIZE=12;
	//数据属性
	private String name;
	//列标题
	private String title;
	//是否主键
	private boolean isKey;
	//是否可查询
	private boolean searchable;
	private String formatter;
	private boolean hidden;
	private boolean defaultShow;
	//是否动态列
	private boolean isDynamic;
	private String idField;
	private String valuemap;    //映射
	public String getValuemap() {
		return valuemap;
	}
	public void setValuemap(String valuemap) {
		this.valuemap = valuemap;
	}
	public String getIdField() {
		return idField;
	}
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public String getTitleField() {
		return titleField;
	}
	public void setTitleField(String titleField) {
		this.titleField = titleField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	private String titleField;
	private String valueField;
	private String groupByField;
	private String groupByFieldTitle;
	
	public String getGroupByFieldTitle() {
		return groupByFieldTitle;
	}
	public void setGroupByFieldTitle(String groupByFieldTitle) {
		this.groupByFieldTitle = groupByFieldTitle;
	}
	public String getGroupByField() {
		return groupByField;
	}
	public void setGroupByField(String groupByField) {
		this.groupByField = groupByField;
	}
	public boolean isDynamic() {
		return isDynamic;
	}
	public void setDynamic(boolean isDynamic) {
		this.isDynamic = isDynamic;
	}
	public boolean isDefaultShow() {
		return defaultShow;
	}
	public void setDefaultShow(boolean defaultShow) {
		this.defaultShow = defaultShow;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public String getFormatter() {
		return formatter;
	}
	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	//是否数值型
	private boolean isNum;
	private String  align;
	private int  width=DEFAULTSIZE;
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		if(width>0) this.width = width;
	}
	public ListPageColumn(){
		
	}
	/**
	 * 
	 * @param name
	 * @param title
	 * @param searchable
	 * @param isNum
	 * @param isKey
	 */
	public ListPageColumn(String name,String title,boolean searchable,boolean isNum,boolean isKey){
		this.name=name.toUpperCase();
		this.title=title;
		this.searchable=searchable;
		this.isNum=isNum;
		this.isKey=isKey;
	}
	public ListPageColumn(String name,String title,boolean searchable,boolean isNum,boolean isKey,int width){
		this(name,title,searchable,isNum,isKey);
		this.setWidth(width);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		//this.name = name.toUpperCase();
		this.name = name;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public boolean isNum() {
		return isNum;
	}
	public void setNum(boolean isNum) {
		this.isNum = isNum;
	}
	public String getAlign() {
		String style="style='text-align:left'";
		if(align==null||"".equals(align.trim())){
		}else{
			style="style='text-align:"+align+"'";
		}
		
		return style;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isKey() {
		return isKey;
	}
	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}
	
}
