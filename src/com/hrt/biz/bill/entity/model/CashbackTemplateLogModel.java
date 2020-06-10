package com.hrt.biz.bill.entity.model;

import java.math.BigDecimal;
import java.util.Date;

public class CashbackTemplateLogModel {

	private Integer wid;
	private String unno;
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
	public Integer getWid() {
		return wid;
	}
	public void setWid(Integer wid) {
		this.wid = wid;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
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
}
