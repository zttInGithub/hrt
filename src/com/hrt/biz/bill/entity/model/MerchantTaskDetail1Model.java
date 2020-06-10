package com.hrt.biz.bill.entity.model;

import java.io.Serializable;

public class MerchantTaskDetail1Model  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer bt1id;							// 修改商户信息唯一主键
	private Integer bmtkid;							//所关联“待审核”主键
	private Integer busid;							//所属销售
	private String rname;			 				//商户全称
	private String legalPerson;						//法人
	private String legalType;						//法人证件类型
	private String legalNum;						//法人证件号码
	private String legalExpDate;					//法人证件有效期
	private String contactAddress;					//联系地址
	private String contactPerson;					//联系人
	private String contactPhone;					//联系手机
	private String contactTel; 	 					//联系固话
	private String legalUploadFileName;				//法人身份上传文件名
	private String bupload;							//营业执照上传文件名
	private String rupload;							//组织结构证上传文件名
	private String registryUpLoad;					//税务登记证上传文件
	private String materialUpLoad;					//补充材料上传文件名
	private String legalUpload2FileName;			//法人身份反面上传文件名
	
	private String busidName;		//业务人员姓名
	
	
	public String getLegalUpload2FileName() {
		return legalUpload2FileName;
	}
	public void setLegalUpload2FileName(String legalUpload2FileName) {
		this.legalUpload2FileName = legalUpload2FileName;
	}
	public Integer getBt1id() {
		return bt1id; 
	}
	public void setBt1id(Integer bt1id) {
		this.bt1id = bt1id;
	}
	public Integer getBusid() {
		return busid;
	}
	public void setBusid(Integer busid) {
		this.busid = busid;
	}
	
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
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
	public String getLegalExpDate() {
		return legalExpDate;
	}
	public void setLegalExpDate(String legalExpDate) {
		this.legalExpDate = legalExpDate;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public Integer getBmtkid() {
		return bmtkid;
	}
	public void setBmtkid(Integer bmtkid) {
		this.bmtkid = bmtkid;
	}
	public String getLegalUploadFileName() {
		return legalUploadFileName;
	}
	public void setLegalUploadFileName(String legalUploadFileName) {
		this.legalUploadFileName = legalUploadFileName;
	}

	public String getBupload() {
		return bupload;
	}
	public void setBupload(String bupload) {
		this.bupload = bupload;
	}
	public String getRupload() {
		return rupload;
	}
	public void setRupload(String rupload) {
		this.rupload = rupload;
	}
	public String getRegistryUpLoad() {
		return registryUpLoad;
	}
	public void setRegistryUpLoad(String registryUpLoad) {
		this.registryUpLoad = registryUpLoad;
	}
	public String getMaterialUpLoad() {
		return materialUpLoad;
	}
	public void setMaterialUpLoad(String materialUpLoad) {
		this.materialUpLoad = materialUpLoad;
	}
	public MerchantTaskDetail1Model() {
		super();
	}
	public String getBusidName() {
		return busidName;
	}
	public void setBusidName(String busidName) {
		this.busidName = busidName;
	}
	
}
