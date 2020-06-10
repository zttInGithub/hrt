package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

public class AgentSalesBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer page;			//当前页数
	private Integer rows;			//总记录数
	private String sort;			//排序字段
	private String order;			//排序规则 ASC DESC
	private Integer busid;			//唯一编号		
	private String unno;			//机构编号		
	private String saleName;		//销售姓名		
	private String phone;			//手机号码		
	private String telephone;		//电话	
	private String email;			//邮箱		
	private Integer maintainUid;	//操作人员
	private Date maintainDate;		//操作日期
	private String maintainType;	//操作类型
	private String userID;			//对应用户
	
	private String unnoName;		//姓名（查询条件）
	
	private String isleader;   //是否为组长级别：1- 是
	private String ismanager;  //'是否为经理级别：1- 是
	private String salesgroup;   //小组
	
	public String getIsleader() {
		return isleader;
	}
	public void setIsleader(String isleader) {
		this.isleader = isleader;
	}
	public String getIsmanager() {
		return ismanager;
	}
	public void setIsmanager(String ismanager) {
		this.ismanager = ismanager;
	}
	public String getSalesgroup() {
		return salesgroup;
	}
	public void setSalesgroup(String salesgroup) {
		this.salesgroup = salesgroup;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Integer getBusid() {
		return busid;
	}
	public void setBusid(Integer busid) {
		this.busid = busid;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getMaintainUid() {
		return maintainUid;
	}
	public void setMaintainUid(Integer maintainUid) {
		this.maintainUid = maintainUid;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getUnnoName() {
		return unnoName;
	}
	public void setUnnoName(String unnoName) {
		this.unnoName = unnoName;
	}
}