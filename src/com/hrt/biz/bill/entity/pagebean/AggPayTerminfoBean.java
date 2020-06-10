package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

/**
 * 聚合商户终端信息
 * @author tenglong
 *
 */
public class AggPayTerminfoBean implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Integer bapid;
	private String unno;
	private String unno1;
	private String mid;
	private String Shopname;
	private String qrtid;
	private String qrtid1;
	private String qrPwd;
	private String qrUrl;
	private Double scanRate;
	private Double secondRate;
	private String second;//转账手续费类型
	private Double secondRate1;//转账手续费
	private Integer status;          //7已生产；8已合成。0未使用；1已使用待审核；2审核通过；3退回；4停用。
	private String status1;
	private String type;			//0总公司分配 ；1代理
	private Date usedConfirmDate;
	private Date maintainDate;
	private Date maintainDate2;
	private Integer maintainUserId;
	private Integer aggnum;
	private Date approveDate;
	private Integer approveId;
	private String minfo1;
	private String minfo2;
	private String orderid;
	private String settlement;
	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	public String getUnno1() {
		return unno1;
	}
	public void setUnno1(String unno1) {
		this.unno1 = unno1;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public Double getSecondRate1() {
		return secondRate1;
	}
	public void setSecondRate1(Double secondRate1) {
		this.secondRate1 = secondRate1;
	}
	public Double getSecondRate() {
		return secondRate;
	}
	public void setSecondRate(Double secondRate) {
		this.secondRate = secondRate;
	}
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getQrtid1() {
		return qrtid1;
	}
	public void setQrtid1(String qrtid1) {
		this.qrtid1 = qrtid1;
	}
	public Date getMaintainDate2() {
		return maintainDate2;
	}
	public void setMaintainDate2(Date maintainDate2) {
		this.maintainDate2 = maintainDate2;
	}
	public Integer getAggnum() {
		return aggnum;
	}
	public void setAggnum(Integer aggnum) {
		this.aggnum = aggnum;
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
	public Integer getBapid() {
		return bapid;
	}
	public void setBapid(Integer bapid) {
		this.bapid = bapid;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getShopname() {
		return Shopname;
	}
	public void setShopname(String shopname) {
		Shopname = shopname;
	}
	public String getQrtid() {
		return qrtid;
	}
	public void setQrtid(String qrtid) {
		this.qrtid = qrtid;
	}
	public String getQrPwd() {
		return qrPwd;
	}
	public void setQrPwd(String qrPwd) {
		this.qrPwd = qrPwd;
	}
	public String getQrUrl() {
		return qrUrl;
	}
	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}
	public Double getScanRate() {
		return scanRate;
	}
	public void setScanRate(Double scanRate) {
		this.scanRate = scanRate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getUsedConfirmDate() {
		return usedConfirmDate;
	}
	public void setUsedConfirmDate(Date usedConfirmDate) {
		this.usedConfirmDate = usedConfirmDate;
	}
	public Date getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}
	public Integer getMaintainUserId() {
		return maintainUserId;
	}
	public void setMaintainUserId(Integer maintainUserId) {
		this.maintainUserId = maintainUserId;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public Integer getApproveId() {
		return approveId;
	}
	public void setApproveId(Integer approveId) {
		this.approveId = approveId;
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
