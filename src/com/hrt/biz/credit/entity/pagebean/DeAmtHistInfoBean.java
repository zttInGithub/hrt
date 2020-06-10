package com.hrt.biz.credit.entity.pagebean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.hrt.frame.entity.pagebean.DataGridBean;

public class DeAmtHistInfoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(DeAmtHistInfoBean.class);
	
	private String UNNO;//机构编号
	private String AGENTNAME;//代理商名称
	private String CERINFOID;//贷款协议编号
	private String DEAMTTYPE;//扣款类型      0本金 1利息
	private String DEAMT;//扣款金额
	private String DEAMTDATE;//扣款日
	private String OPERATOR;//操作人
	private String REMARKS;//备注
	private String SOURCE;//扣款来源 0现金 1分润
		
	private Integer page;			//当前页数
	private Integer rows;			//总记录数
	private String sort;			//排序字段
	private String order;			//排序规则 ASC DESC
	
	//XmlToOb
	public static DataGridBean XmlToOb(String xml){
		log.info("贷款授信系统提交扣款信息"+xml);
		DataGridBean dataGridBean = new DataGridBean();
		//解析返回结果
		try{
			com.alibaba.fastjson.JSONObject reqJson = JSONObject.parseObject(xml.toString());
			Integer total =(Integer)reqJson.get("total");
			Integer status =(Integer)reqJson.get("status");
			String msg =(String)reqJson.get("msg");
			if(status!=1&&total>0){
				List<DeAmtHistInfoBean> list =(List) reqJson.get("data");
				dataGridBean.setRows(list);
				dataGridBean.setTotal(total);
			}else{
				dataGridBean.setRows(new ArrayList<DeAmtHistInfoBean>());
				dataGridBean.setTotal(0);
				return dataGridBean;
			}
		}catch (Exception e) {
			log.info("贷款授信系统扣款信息-解析异常"+e);
		}
		return dataGridBean;
	}
	
	//ObToXml
	public static String ObToXml(DeAmtHistInfoBean deAmtHistInfoBean){
		String xml ="";
		if(deAmtHistInfoBean!=null){
			xml=deAmtHistInfoBean.toString();
		}
		log.info("贷款授信系统提交扣款信息"+xml);
		return xml;
	}

	public String getSOURCE() {
		return SOURCE;
	}

	public void setSOURCE(String sOURCE) {
		SOURCE = sOURCE;
	}

	public String getUNNO() {
		return UNNO;
	}

	public void setUNNO(String uNNO) {
		UNNO = uNNO;
	}

	public String getAGENTNAME() {
		return AGENTNAME;
	}

	public void setAGENTNAME(String aGENTNAME) {
		AGENTNAME = aGENTNAME;
	}

	public String getCERINFOID() {
		return CERINFOID;
	}

	public void setCERINFOID(String cERINFOID) {
		CERINFOID = cERINFOID;
	}

	public String getDEAMTTYPE() {
		return DEAMTTYPE;
	}

	public void setDEAMTTYPE(String dEAMTTYPE) {
		DEAMTTYPE = dEAMTTYPE;
	}

	public String getDEAMT() {
		return DEAMT;
	}

	public void setDEAMT(String dEAMT) {
		DEAMT = dEAMT;
	}

	public String getDEAMTDATE() {
		return DEAMTDATE;
	}

	public void setDEAMTDATE(String dEAMTDATE) {
		DEAMTDATE = dEAMTDATE;
	}

	public String getOPERATOR() {
		return OPERATOR;
	}

	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
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
