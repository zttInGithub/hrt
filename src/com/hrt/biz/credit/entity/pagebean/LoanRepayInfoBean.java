package com.hrt.biz.credit.entity.pagebean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.check.service.impl.CheckRefundServiceImpl;
import com.hrt.frame.entity.pagebean.DataGridBean;

public class LoanRepayInfoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(LoanRepayInfoBean.class);
	
	private String unno;//机构编号
	private String agentName;//代理商名称
	private String repayTime;//还款日期
	private Date repayTimeDate;//还款日期
	private Double repayAmt;//还款金额
	private String protocolNo;//协议编号
	private String posInvoiceNo;//还款单据号
	private String payNo;//开户银行账号
	private String proposer;//申请人
	private String propEmail;//申请人邮箱
	private String proPhone;//申请人电话
	private String remittanceImg;//汇款单图片-二进制字符串
	private File remittanceFile;//汇款单图片-二进制字符串
	
	private Integer page;			//当前页数
	private Integer rows;			//总记录数
	private String sort;			//排序字段
	private String order;			//排序规则 ASC DESC
	
	//XmlToOb
	public static DataGridBean XmlToOb(String xml){
		log.info("贷款授信系统提交还款信息"+xml);
		DataGridBean dataGridBean = new DataGridBean();
		//解析返回结果
		try{
			com.alibaba.fastjson.JSONObject reqJson = JSONObject.parseObject(xml.toString());
			Integer total =(Integer)reqJson.get("total");
			Integer status =(Integer)reqJson.get("status");
			String msg =(String)reqJson.get("msg");
			if(status!=1&&total>0){
				List<LoanRepayInfoBean> list =(List) reqJson.get("data");
				dataGridBean.setRows(list);
				dataGridBean.setTotal(total);
			}else{
				dataGridBean.setRows(new ArrayList<LoanRepayInfoBean>());
				dataGridBean.setTotal(0);
				return dataGridBean;
			}
		}catch (Exception e) {
			log.info("贷款授信系统提交还款信息-解析异常"+e);
		}
		return dataGridBean;
	}
	
	//ObToXml
	public static String ObToXml(LoanRepayInfoBean loanRepayInfoBean){
		String xml ="";
		if(loanRepayInfoBean!=null){
			xml=loanRepayInfoBean.toString();
		}
		log.info("贷款授信系统提交还款信息"+xml);
		return xml;
	}

	public File getRemittanceFile() {
		return remittanceFile;
	}

	public void setRemittanceFile(File remittanceFile) {
		this.remittanceFile = remittanceFile;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public Date getRepayTimeDate() {
		return repayTimeDate;
	}

	public void setRepayTimeDate(Date repayTimeDate) {
		this.repayTimeDate = repayTimeDate;
	}

	public Double getRepayAmt() {
		return repayAmt;
	}

	public void setRepayAmt(Double repayAmt) {
		this.repayAmt = repayAmt;
	}

	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public String getPosInvoiceNo() {
		return posInvoiceNo;
	}

	public void setPosInvoiceNo(String posInvoiceNo) {
		this.posInvoiceNo = posInvoiceNo;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getPropEmail() {
		return propEmail;
	}

	public void setPropEmail(String propEmail) {
		this.propEmail = propEmail;
	}

	public String getProPhone() {
		return proPhone;
	}

	public void setProPhone(String proPhone) {
		this.proPhone = proPhone;
	}

	public String getRemittanceImg() {
		return remittanceImg;
	}

	public void setRemittanceImg(String remittanceImg) {
		this.remittanceImg = remittanceImg;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Log getLog() {
		return log;
	}
	
}
