package com.hrt.biz.credit.entity.pagebean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.check.service.impl.CheckRefundServiceImpl;
import com.hrt.frame.entity.pagebean.DataGridBean;

public class LoanInfoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(LoanInfoBean.class);
	
	private String unno; 			//机构编号
	private String agentName;	//代理商名称
	private String creType;		//贷款方式  0：现金 1：购买pos机
	private Double creAmt;		//贷款金额
	private String creTimeLimit;	//贷款期限
	private String proposer;		//申请人
	private String propemail;		//申请人邮箱
	private String prophone;		//申请人电话
	
	private Integer page;			//当前页数
	private Integer rows;			//总记录数
	private String sort;			//排序字段
	private String order;			//排序规则 ASC DESC
	
	//XmlToOb
	public static DataGridBean XmlToOb(String xml){
		log.info("贷款授信系统查询贷款信息"+xml);
		DataGridBean dataGridBean = new DataGridBean();
		//解析返回结果
		try{
			com.alibaba.fastjson.JSONObject reqJson = JSONObject.parseObject(xml.toString());
			Integer total =(Integer)reqJson.get("total");
			String status =(String)reqJson.get("status");
			String msg =(String)reqJson.get("msg");
			if(!"1".equals(status)&&total>0){
				List<LoanInfoBean> list =(List) reqJson.get("data");
				dataGridBean.setRows(list);
				dataGridBean.setTotal(total);
			}else{
				log.info("贷款授信系统查询贷款信息失败");
				return null;
			}
		}catch (Exception e) {
			log.info("贷款授信系统查询贷款信息-解析异常"+e);
		}
		return dataGridBean;
	}
	
	//ObToXml
	public static String ObToXml(LoanInfoBean creditInfoBean){
		String xml ="";
		if(creditInfoBean!=null){
			xml=creditInfoBean.toString();
		}
		log.info("贷款授信系统提交贷款信息"+xml);
		return xml;
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

	public String getCreType() {
		return creType;
	}

	public void setCreType(String creType) {
		this.creType = creType;
	}

	public Double getCreAmt() {
		return creAmt;
	}

	public void setCreAmt(Double creAmt) {
		this.creAmt = creAmt;
	}

	public String getCreTimeLimit() {
		return creTimeLimit;
	}

	public void setCreTimeLimit(String creTimeLimit) {
		this.creTimeLimit = creTimeLimit;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getPropemail() {
		return propemail;
	}

	public void setPropemail(String propemail) {
		this.propemail = propemail;
	}

	public String getProphone() {
		return prophone;
	}

	public void setProphone(String prophone) {
		this.prophone = prophone;
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
