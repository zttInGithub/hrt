package com.hrt.biz.bill.entity.pagebean;

import java.math.BigDecimal;
import java.util.Date;

public class CashbackTemplateBean {

	//当前页数
	private Integer page;
		
	//总记录数
	private Integer rows;
		
	//排序字段
	private String sort;
	//返现模板日志表
	private Integer wid;
	private Integer nid;
	private String unno;//机构号
	private String upperUnno;//上级机构号
	private String rebatetype;//活动类型
	private String cashbacktype;//返现类型
	private BigDecimal cashbackratio;//返现比例
	private String maintainuser;//创建人
	private String maintaintype;    	//操作类型    A ：添加  M ：修改 D :删除
	private Date maintaindate;    	//操作日期
	private Date startdate;//开始时间
	private Date enddate;//结束时间
	private String remark;//备注
	private String minfo1;//备用1
	private String minfo2;//备用2
	//返现模板表
	private String cby;//创建人
	private String lby;//修改人
	private Date cdate;//创建时间
	
	//查询
	private String unnoname;
	private Date cashDay;
	private Date cashDay1;
	private String mid;
	private String sn;

	//中心一代返现税点表
	private BigDecimal taxpoint;//税点

	//用于接收修改的返现数据
	private String unnoRebatetype;
	private BigDecimal cashbackrationext;
	
	public Date getCashDay() {
		return cashDay;
	}

	public void setCashDay(Date cashDay) {
		this.cashDay = cashDay;
	}

	public Date getCashDay1() {
		return cashDay1;
	}

	public void setCashDay1(Date cashDay1) {
		this.cashDay1 = cashDay1;
	}

	public String getUnnoname() {
		return unnoname;
	}

	public void setUnnoname(String unnoname) {
		this.unnoname = unnoname;
	}
	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUnnoRebatetype() {
		return unnoRebatetype;
	}

	public void setUnnoRebatetype(String unnoRebatetype) {
		this.unnoRebatetype = unnoRebatetype;
	}

	public BigDecimal getCashbackrationext() {
		return cashbackrationext;
	}

	public void setCashbackrationext(BigDecimal cashbackrationext) {
		this.cashbackrationext = cashbackrationext;
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

	public Integer getWid() {
		return wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public String getUpperUnno() {
		return upperUnno;
	}

	public void setUpperUnno(String upperUnno) {
		this.upperUnno = upperUnno;
	}

	public String getRebatetype() {
		return rebatetype;
	}

	public void setRebatetype(String rebatetype) {
		this.rebatetype = rebatetype;
	}

	public String getCashbacktype() {
		return cashbacktype;
	}

	public void setCashbacktype(String cashbacktype) {
		this.cashbacktype = cashbacktype;
	}

	public BigDecimal getCashbackratio() {
		return cashbackratio;
	}

	public void setCashbackratio(BigDecimal cashbackratio) {
		this.cashbackratio = cashbackratio;
	}

	public String getMaintainuser() {
		return maintainuser;
	}

	public void setMaintainuser(String maintainuser) {
		this.maintainuser = maintainuser;
	}

	public String getMaintaintype() {
		return maintaintype;
	}

	public void setMaintaintype(String maintaintype) {
		this.maintaintype = maintaintype;
	}

	public Date getMaintaindate() {
		return maintaindate;
	}

	public void setMaintaindate(Date maintaindate) {
		this.maintaindate = maintaindate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMinfo1() {
		return minfo1;
	}

	public void setMinfo1(String minfo1) {
		this.minfo1 = minfo1;
	}

	public String getMinfo2() {
		return minfo2;
	}

	public void setMinfo2(String minfo2) {
		this.minfo2 = minfo2;
	}

	public String getCby() {
		return cby;
	}

	public void setCby(String cby) {
		this.cby = cby;
	}

	public String getLby() {
		return lby;
	}

	public void setLby(String lby) {
		this.lby = lby;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public BigDecimal getTaxpoint() {
		return taxpoint;
	}

	public void setTaxpoint(BigDecimal taxpoint) {
		this.taxpoint = taxpoint;
	}
}
