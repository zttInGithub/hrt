package com.hrt.biz.bill.entity.pagebean;

import java.util.Date;

public class MerchantCardBean {
	//当前页数
		private Integer page;
			
		//总记录数
		private Integer rows;
			
		//排序字段
		private String sort;
			
		//排序规则 ASC DESC
		private String order;
		private String mtcid;
		private String mid;		//	MID   char(15) NOT NULL,--MID
		private String name;		//	  name  varchar2(50),     --姓名
		private String phone;	//	  phone varchar2(20),--手机号
		private String idnum;	//	  IDNUM  varchar2(30),--身份证号
		private String cardnum;	//	  cardnum varchar(30),--卡号
		private String cardtoken;//	  cardtoken varchar(30),--卡号token值
		private String status;	//	  STATUS	CHAR(1),			        --状态
		private Date cdate;	//	  CDATE   DATE not null,  --创建时间	
		private Date lmdate;	//	  LMDATE  date,	
		private String lmby6;	//	  LMBY	VARCHAR2(70)
		private String cardType;	//卡类别（借记卡 贷记卡 准借记卡  预付费卡）
		private String paymentBank;	//银行名称
		private String paymentBankImg;//银行图片
		private String paymentLine;	//银行编号
		
		public String getCardType() {
			return cardType;
		}
		public void setCardType(String cardType) {
			this.cardType = cardType;
		}
		public String getPaymentBank() {
			return paymentBank;
		}
		public void setPaymentBank(String paymentBank) {
			this.paymentBank = paymentBank;
		}
		public String getPaymentBankImg() {
			return paymentBankImg;
		}
		public void setPaymentBankImg(String paymentBankImg) {
			this.paymentBankImg = paymentBankImg;
		}
		public String getPaymentLine() {
			return paymentLine;
		}
		public void setPaymentLine(String paymentLine) {
			this.paymentLine = paymentLine;
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
		public String getMtcid() {
			return mtcid;
		}
		public void setMtcid(String mtcid) {
			this.mtcid = mtcid;
		}
		public String getMid() {
			return mid;
		}
		public void setMid(String mid) {
			this.mid = mid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getIdnum() {
			return idnum;
		}
		public void setIdnum(String idnum) {
			this.idnum = idnum;
		}
		public String getCardnum() {
			return cardnum;
		}
		public void setCardnum(String cardnum) {
			this.cardnum = cardnum;
		}
		public String getCardtoken() {
			return cardtoken;
		}
		public void setCardtoken(String cardtoken) {
			this.cardtoken = cardtoken;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Date getCdate() {
			return cdate;
		}
		public void setCdate(Date cdate) {
			this.cdate = cdate;
		}
		public Date getLmdate() {
			return lmdate;
		}
		public void setLmdate(Date lmdate) {
			this.lmdate = lmdate;
		}
		public String getLmby6() {
			return lmby6;
		}
		public void setLmby6(String lmby6) {
			this.lmby6 = lmby6;
		}
		
}
