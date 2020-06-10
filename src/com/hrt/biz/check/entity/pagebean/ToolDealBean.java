package com.hrt.biz.check.entity.pagebean;

import java.util.Date;

public class ToolDealBean {
	
	//角色ID
	private String roleIds;
	
	//角色名称
	private String roleNames;
	
	//机构编号
	private String unNo;
	
	//机构名称
	private String unitName;
	
	private Integer fiid;
	
	public void setFiid(Integer fiid) {
		this.fiid = fiid;
	}
	public Integer getFiid() {
		return fiid;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getUnNo() {
		return unNo;
	}
	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	private Integer busid;			//唯一编号
	private String saleName;		//销售姓名
	
	public Integer getBusid() {
		return busid;
	}
	public void setBusid(Integer busid) {
		this.busid = busid;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
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
	private Integer cuid;
	public Integer getCuid() {
		return cuid;
	}
	public void setCuid(Integer cuid) {
		this.cuid = cuid;
	}
	
	private Integer TxnCount;            //交易笔数
	private Double RefundAmt;           //退款金额
	private Integer RefundCount;           //退款笔数
	private String  schedulesettledate;
	private Double MDA;                   //交易手续费
	private Double REFUNDMDA;             //退款手续费
	
	
	
	
	
	public Double getMDA() {
		return MDA;
	}
	public void setMDA(Double mDA) {
		MDA = mDA;
	}
	public Double getREFUNDMDA() {
		return REFUNDMDA;
	}
	public void setREFUNDMDA(Double rEFUNDMDA) {
		REFUNDMDA = rEFUNDMDA;
	}
	public String getSchedulesettledate() {
		return schedulesettledate;
	}
	public void setSchedulesettledate(String schedulesettledate) {
		this.schedulesettledate = schedulesettledate;
	}
	public Integer getTxnCount() {
		return TxnCount;
	}
	public void setTxnCount(Integer txnCount) {
		TxnCount = txnCount;
	}
	public Double getRefundAmt() {
		return RefundAmt;
	}
	public void setRefundAmt(Double refundAmt) {
		RefundAmt = refundAmt;
	}
	public Integer getRefundCount() {
		return RefundCount;
	}
	public void setRefundCount(Integer refundCount) {
		RefundCount = refundCount;
	}
	private Integer bdid;         //
	private String mid;          //商户编号
	private String tid;          //终端编号
	private String cardPan;      //卡号
	private String cbrand;       //卡别
	private Double txnAmount;    //交易金额
	private Double txnAmount1;    //交易金额
	private String txnDay;       //交易日期
	private String txnDay1;       //交易日期
	private String txnDate;      //交易时间
	private String txnDate1;      //交易时间
	private String txnType;      //交易类型
	private String authCode;     //授权码
	private String stan;         //流水号
	private String rrn;          //系统参考号
	private Double mda;          //商户手续费
	private Double mnamt;        //商户入账金额
	private Double profit;       //收益
	private Double profit1;       //收益
	private String settleDate;   //结算日期
	private Integer maintainUid; //操作人员
	private Date maintainDate;   //操作日期
	private String maintainType; //操作类型
	private String settleStatus;		//结算状态
	private String raddr;				//注册地址
	private String areaCode;			//商户所在地区
	private String rname;				//商户注册名称
	private String firsix;          //银行卡号前六位
	private String lastfour;        //银行卡号后四位
	private Integer isM35;			//商户种类
	private Integer isMpos;			//交易渠道0-Mpos1-传统Pos
	private Integer type;			//交易方式:0-银行卡、1-微信、2-支付宝、3-银联扫码、4-快捷、5-手机Pay、6-信用卡还款
	private Integer mti;			//交易类型:0-消费,1-预授权,2-预授权撤销，3-消费撤销，4-冲正，5-退款，6-退货
	private Integer txnState;		//处理状态：0-成功,1-失败
	private Integer oriPkid;		//原交易ID标识
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getMti() {
		return mti;
	}
	public void setMti(Integer mti) {
		this.mti = mti;
	}
	public Integer getTxnState() {
		return txnState;
	}
	public void setTxnState(Integer txnState) {
		this.txnState = txnState;
	}
	public Integer getOriPkid() {
		return oriPkid;
	}
	public void setOriPkid(Integer oriPkid) {
		this.oriPkid = oriPkid;
	}
	public Integer getIsMpos() {
		return isMpos;
	}
	public void setIsMpos(Integer isMpos) {
		this.isMpos = isMpos;
	}
	public Integer getIsM35() {
		return isM35;
	}
	public void setIsM35(Integer isM35) {
		this.isM35 = isM35;
	}
	public String getFirsix() {
		return firsix;
	}
	public void setFirsix(String firsix) {
		this.firsix = firsix;
	}
	public String getLastfour() {
		return lastfour;
	}
	public void setLastfour(String lastfour) {
		this.lastfour = lastfour;
	}
	private String actionsetttledate;     //当前日期
	
	public String getTxnDate1() {
		return txnDate1;
	}
	public void setTxnDate1(String txnDate1) {
		this.txnDate1 = txnDate1;
	}
	public String getActionsetttledate() {
		return actionsetttledate;
	}
	public void setActionsetttledate(String actionsetttledate) {
		this.actionsetttledate = actionsetttledate;
	}
	public Double getProfit1() {
		return profit1;
	}
	public void setProfit1(Double profit1) {
		this.profit1 = profit1;
	}
	private Integer udid;
	private String unno;                  //机构编号
	private Integer maintainUID;          //操作人员
	
	public Integer getUdid() {
		return udid;
	}
	public void setUdid(Integer udid) {
		this.udid = udid;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public Integer getMaintainUID() {
		return maintainUID;
	}
	public void setMaintainUID(Integer maintainUID) {
		this.maintainUID = maintainUID;
	}
	public Double getTxnAmount1() {
		return txnAmount1;
	}
	public void setTxnAmount1(Double txnAmount1) {
		this.txnAmount1 = txnAmount1;
	}
	public String getTxnDay1() {
		return txnDay1;
	}
	public void setTxnDay1(String txnDay1) {
		this.txnDay1 = txnDay1;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public Integer getBdid() {
		return bdid;
	}
	public void setBdid(Integer bdid) {
		this.bdid = bdid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getCardPan() {
		return cardPan;
	}
	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}
	public String getCbrand() {
		return cbrand;
	}
	public void setCbrand(String cbrand) {
		this.cbrand = cbrand;
	}
	public Double getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(String txnDay) {
		this.txnDay = txnDay;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public Double getMda() {
		return mda;
	}
	public void setMda(Double mda) {
		this.mda = mda;
	}
	public Double getMnamt() {
		return mnamt;
	}
	public void setMnamt(Double mnamt) {
		this.mnamt = mnamt;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
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
	public String getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getRaddr() {
		return raddr;
	}
	public void setRaddr(String raddr) {
		this.raddr = raddr;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	private String txnDayStart;
	private String txnDayEnd;


	public String getTxnDayStart() {
		return txnDayStart;
	}
	public void setTxnDayStart(String txnDayStart) {
		this.txnDayStart = txnDayStart;
	}
	public String getTxnDayEnd() {
		return txnDayEnd;
	}
	public void setTxnDayEnd(String txnDayEnd) {
		this.txnDayEnd = txnDayEnd;
	}
	
	
}
