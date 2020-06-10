package com.hrt.biz.bill.entity.pagebean;

import java.io.Serializable;
import java.util.Date;

public class MerchantTaskDataBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// 当前页数
	private Integer page;

	// 总记录数
	private Integer rows;

	// 排序字段
	private String sort;

	// 排序规则 ASC DESC
	private String order;
	
	private String unName;
	private String infoName;
	private Double  singleLimit;					//单笔限额
    private Double  dailyLimit;						//单日限额
    private Double  singleLimit1;					//单笔限额-借
    private Double  dailyLimit1;					//单日限额-借
    private Integer mtype;							//限额类型 1/null 刷卡；2扫码
    
	public Double getSingleLimit1() {
		return singleLimit1;
	}

	public void setSingleLimit1(Double singleLimit1) {
		this.singleLimit1 = singleLimit1;
	}

	public Double getDailyLimit1() {
		return dailyLimit1;
	}

	public void setDailyLimit1(Double dailyLimit1) {
		this.dailyLimit1 = dailyLimit1;
	}

	public Integer getMtype() {
		return mtype;
	}

	public void setMtype(Integer mtype) {
		this.mtype = mtype;
	}

	public Double getSingleLimit() {
		return singleLimit;
	}

	public void setSingleLimit(Double singleLimit) {
		this.singleLimit = singleLimit;
	}

	public Double getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(Double dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public String getUnName() {
		return unName;
	}

	public void setUnName(String unName) {
		this.unName = unName;
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

	private Integer bmtkid; // 审核唯一ID
	private String taskId; // 工单编号
	private String unno; // 机构编号
	private String mid; // 商户编号
	private String type; // 工单类别
	private String isM35; // isM35
	private String settmethod; // settmethod
	private String taskContext; // 工单描述
	private String processContext; // 受理描述
	private String processContext1; // 受理描述
	private String processContext2; // 受理描述
	private Integer mainTainUId; // 操作人员
	private Date mainTainDate; // 操作日期
	private String mainTainType; // 变更类型
	private Integer approveUId; // 授权人员
	private Date approveDate; // 授权日期
	private String approveStatus; // 授权状态
	private String startDay;
	private String endDay;
	private Integer settleCycle;		//结算周期

	public String getIsM35() {
		return isM35;
	}

	public void setIsM35(String isM35) {
		this.isM35 = isM35;
	}

	public String getSettmethod() {
		return settmethod;
	}

	public void setSettmethod(String settmethod) {
		this.settmethod = settmethod;
	}

	public String getProcessContext2() {
		return processContext2;
	}

	public void setProcessContext2(String processContext2) {
		this.processContext2 = processContext2;
	}

	public String getProcessContext1() {
		return processContext1;
	}

	public void setProcessContext1(String processContext1) {
		this.processContext1 = processContext1;
	}

	public Integer getBmtkid() {
		return bmtkid;
	}

	public void setBmtkid(Integer bmtkid) {
		this.bmtkid = bmtkid;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTaskContext() {
		return taskContext;
	}

	public void setTaskContext(String taskContext) {
		this.taskContext = taskContext;
	}

	public String getProcessContext() {
		return processContext;
	}

	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}

	public Integer getMainTainUId() {
		return mainTainUId;
	}

	public void setMainTainUId(Integer mainTainUId) {
		this.mainTainUId = mainTainUId;
	}

	public Date getMainTainDate() {
		return mainTainDate;
	}

	public void setMainTainDate(Date mainTainDate) {
		this.mainTainDate = mainTainDate;
	}

	public String getMainTainType() {
		return mainTainType;
	}

	public void setMainTainType(String mainTainType) {
		this.mainTainType = mainTainType;
	}

	public Integer getApproveUId() {
		return approveUId;
	}

	public void setApproveUId(Integer approveUId) {
		this.approveUId = approveUId;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public MerchantTaskDataBean() {
		super();
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public Integer getSettleCycle() {
		return settleCycle;
	}

	public void setSettleCycle(Integer settleCycle) {
		this.settleCycle = settleCycle;
	}

}
