package com.hrt.biz.bill.entity.model;

import java.util.Date;

/**
 * 代理商信息
 */
public class AgentUnitTaskDetail1Model implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer bautdid;				//明细主键
	private String agentName;				//代理商名称
	private String legalPerson;				//法人
	private String legalType;				//法人证件类型
	private String legalNum;				//法人证件号码
	private String bno;						//营业执照编号
	private String baddr;					//经营地址
	private String legalAUpLoad;     //法人身份证正面
	private String legalBUpLoad;     //法人身份证反面
	private String dealUpLoad;     //协议签章页照片
	private String busLicUpLoad;     //营业执照（企业必传）
	
	public Integer getBautdid() {
		return bautdid;
	}
	public void setBautdid(Integer bautdid) {
		this.bautdid = bautdid;
	}
	public String getLegalAUpLoad() {
		return legalAUpLoad;
	}
	public void setLegalAUpLoad(String legalAUpLoad) {
		this.legalAUpLoad = legalAUpLoad;
	}
	public String getLegalBUpLoad() {
		return legalBUpLoad;
	}
	public void setLegalBUpLoad(String legalBUpLoad) {
		this.legalBUpLoad = legalBUpLoad;
	}
	public String getDealUpLoad() {
		return dealUpLoad;
	}
	public void setDealUpLoad(String dealUpLoad) {
		this.dealUpLoad = dealUpLoad;
	}
	public String getBusLicUpLoad() {
		return busLicUpLoad;
	}
	public void setBusLicUpLoad(String busLicUpLoad) {
		this.busLicUpLoad = busLicUpLoad;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getLegalType() {
		return legalType;
	}
	public void setLegalType(String legalType) {
		this.legalType = legalType;
	}
	public String getLegalNum() {
		return legalNum;
	}
	public void setLegalNum(String legalNum) {
		this.legalNum = legalNum;
	}
	public String getBno() {
		return bno;
	}
	public void setBno(String bno) {
		this.bno = bno;
	}
	public String getBaddr() {
		return baddr;
	}
	public void setBaddr(String baddr) {
		this.baddr = baddr;
	}
}