package com.hrt.biz.check.entity.pagebean;

import java.util.Date;

public class CheckProfitTempTraditLogBean {

	private Integer	wId;
	private Integer	ctpId;
	private String unno;
	private String tempName;			//模板名称
	private String industrytype   ;		//分润类型1、标准  2、优惠类  3、减免
	private Double costRate     ;     	 // 借记卡成本费率
	private Double  feeAmt      ;    	 // 借记卡大额手续费
	private Double dealAmt     ;   		 // 借记卡大额封顶值
	private Double creditBankRate;   	 // 贷记卡分润成本
	private Double cashRate;   	         // T0提现费率
	private Double cashAmt;   	         // 转账费
	private Double scanRate;   	         // 扫码费率
	private Double profitPercent ;  	//利润百分比
	private String matainType  ;    	//操作类型    A ：添加  M ：修改 D :删除
	private Date matainDate  ;    		//生效日期
	private Date endDate  ;    		//生效日期
	private Integer matainUserId;		//操作人
	private String Tempname;

	private String txnDay;
	private String txnDay1;
	private Double scanRateUp;

	public Double getScanRateUp() {
		return scanRateUp;
	}

	public void setScanRateUp(Double scanRateUp) {
		this.scanRateUp = scanRateUp;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getwId() {
		return wId;
	}

	public void setwId(Integer wId) {
		this.wId = wId;
	}

	public String getTempname() {
		return Tempname;
	}
	public void setTempname(String tempname) {
		Tempname = tempname;
	}
	private Double costRate1     ;     	 
	private Double  feeAmt1      ;    	
	private Double dealAmt1     ;  
	private Double creditBankRate1;   	 // 贷记卡分润成本
	private Double profitPercent1 ;
	
	private Double costRate2     ;     	 
	private Double  feeAmt2      ;    	
	private Double dealAmt2     ;
	private Double creditBankRate2;   	 // 贷记卡分润成本
	private Double profitPercent2 ;
	
	private Double costRate3     ;     	 
	private Double  feeAmt3      ;    	
	private Double dealAmt3     ;   		 
	private Double profitPercent3 ;
	
	private Double costRate4     ;     	 
	private Double  feeAmt4      ;    	
	private Double dealAmt4     ;   		 
	private Double profitPercent4 ;
	
	private Double costRate5     ;     	 
	private Double  feeAmt5      ;    	
	private Double dealAmt5     ;   		 
	private Double profitPercent5 ;
	
	public Double getCreditBankRate() {
		return creditBankRate;
	}
	public void setCreditBankRate(Double creditBankRate) {
		this.creditBankRate = creditBankRate;
	}
	public Double getCashRate() {
		return cashRate;
	}
	public void setCashRate(Double cashRate) {
		this.cashRate = cashRate;
	}
	public Double getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(Double cashAmt) {
		this.cashAmt = cashAmt;
	}
	public Double getScanRate() {
		return scanRate;
	}
	public void setScanRate(Double scanRate) {
		this.scanRate = scanRate;
	}
	public Double getCreditBankRate1() {
		return creditBankRate1;
	}
	public void setCreditBankRate1(Double creditBankRate1) {
		this.creditBankRate1 = creditBankRate1;
	}
	public Double getCreditBankRate2() {
		return creditBankRate2;
	}
	public void setCreditBankRate2(Double creditBankRate2) {
		this.creditBankRate2 = creditBankRate2;
	}
	public Double getCostRate1() {
		return costRate1;
	}
	public void setCostRate1(Double costRate1) {
		this.costRate1 = costRate1;
	}
	public Double getFeeAmt1() {
		return feeAmt1;
	}
	public void setFeeAmt1(Double feeAmt1) {
		this.feeAmt1 = feeAmt1;
	}
	public Double getDealAmt1() {
		return dealAmt1;
	}
	public void setDealAmt1(Double dealAmt1) {
		this.dealAmt1 = dealAmt1;
	}
	public Double getProfitPercent1() {
		return profitPercent1;
	}
	public void setProfitPercent1(Double profitPercent1) {
		this.profitPercent1 = profitPercent1;
	}
	public Double getCostRate2() {
		return costRate2;
	}
	public void setCostRate2(Double costRate2) {
		this.costRate2 = costRate2;
	}
	public Double getFeeAmt2() {
		return feeAmt2;
	}
	public void setFeeAmt2(Double feeAmt2) {
		this.feeAmt2 = feeAmt2;
	}
	public Double getDealAmt2() {
		return dealAmt2;
	}
	public void setDealAmt2(Double dealAmt2) {
		this.dealAmt2 = dealAmt2;
	}
	public Double getProfitPercent2() {
		return profitPercent2;
	}
	public void setProfitPercent2(Double profitPercent2) {
		this.profitPercent2 = profitPercent2;
	}
	public Double getCostRate3() {
		return costRate3;
	}
	public void setCostRate3(Double costRate3) {
		this.costRate3 = costRate3;
	}
	public Double getFeeAmt3() {
		return feeAmt3;
	}
	public void setFeeAmt3(Double feeAmt3) {
		this.feeAmt3 = feeAmt3;
	}
	public Double getDealAmt3() {
		return dealAmt3;
	}
	public void setDealAmt3(Double dealAmt3) {
		this.dealAmt3 = dealAmt3;
	}
	public Double getProfitPercent3() {
		return profitPercent3;
	}
	public void setProfitPercent3(Double profitPercent3) {
		this.profitPercent3 = profitPercent3;
	}
	public Double getCostRate4() {
		return costRate4;
	}
	public void setCostRate4(Double costRate4) {
		this.costRate4 = costRate4;
	}
	public Double getFeeAmt4() {
		return feeAmt4;
	}
	public void setFeeAmt4(Double feeAmt4) {
		this.feeAmt4 = feeAmt4;
	}
	public Double getDealAmt4() {
		return dealAmt4;
	}
	public void setDealAmt4(Double dealAmt4) {
		this.dealAmt4 = dealAmt4;
	}
	public Double getProfitPercent4() {
		return profitPercent4;
	}
	public void setProfitPercent4(Double profitPercent4) {
		this.profitPercent4 = profitPercent4;
	}
	public Double getCostRate5() {
		return costRate5;
	}
	public void setCostRate5(Double costRate5) {
		this.costRate5 = costRate5;
	}
	public Double getFeeAmt5() {
		return feeAmt5;
	}
	public void setFeeAmt5(Double feeAmt5) {
		this.feeAmt5 = feeAmt5;
	}
	public Double getDealAmt5() {
		return dealAmt5;
	}
	public void setDealAmt5(Double dealAmt5) {
		this.dealAmt5 = dealAmt5;
	}
	public Double getProfitPercent5() {
		return profitPercent5;
	}
	public void setProfitPercent5(Double profitPercent5) {
		this.profitPercent5 = profitPercent5;
	}
	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;

	
	public Integer getCtpId() {
		return ctpId;
	}
	public void setCtpId(Integer ctpId) {
		this.ctpId = ctpId;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getIndustrytype() {
		return industrytype;
	}
	public void setIndustrytype(String industrytype) {
		this.industrytype = industrytype;
	}
	public Double getCostRate() {
		return costRate;
	}
	public void setCostRate(Double costRate) {
		this.costRate = costRate;
	}
	public Double getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(Double feeAmt) {
		this.feeAmt = feeAmt;
	}
	public Double getDealAmt() {
		return dealAmt;
	}
	public void setDealAmt(Double dealAmt) {
		this.dealAmt = dealAmt;
	}
	public Double getProfitPercent() {
		return profitPercent;
	}
	public void setProfitPercent(Double profitPercent) {
		this.profitPercent = profitPercent;
	}
	public String getMatainType() {
		return matainType;
	}
	public void setMatainType(String matainType) {
		this.matainType = matainType;
	}
	public Date getMatainDate() {
		return matainDate;
	}
	public void setMatainDate(Date matainDate) {
		this.matainDate = matainDate;
	}

	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public Integer getMatainUserId() {
		return matainUserId;
	}
	public void setMatainUserId(Integer matainUserId) {
		this.matainUserId = matainUserId;
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
	public String getTxnDay() {
		return txnDay;
	}
	public void setTxnDay(String txnDay) {
		this.txnDay = txnDay;
	}
	public String getTxnDay1() {
		return txnDay1;
	}
	public void setTxnDay1(String txnDay1) {
		this.txnDay1 = txnDay1;
	}


	
}
