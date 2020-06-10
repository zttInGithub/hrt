package com.hrt.frame.entity.pagebean;

import java.io.Serializable;
import java.util.Date;

public class TodoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	//代办编号
	private Integer todoID;

	//接收机构ID
    private String unNo;
    
    //业务关键字
    private String batchJobNo;
    
    //发送机构ID
    private String msgSendUnit;
    
    //发送用户ID
    private Integer msgSender;
    
    //发送主题
    private String msgTopic;
    
    //发送时间
    private Date msgSendTime;
    
    //菜单代码
    private String tranCode;
    
    //业务类别
    private String bizType;
    
    //代办状态
    private Integer status;
    
    //发送用户名称
    private String msgSendName;
    
    //发送机构名称
    private String msgSendUnitName;
    
    //代办状态名称
    private String StatusName;
    
    //业务类别名称
    private String bizTypeName;
    
    //接收机构ID
    private String unnoName;
    
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
	public Integer getTodoID() {
		return todoID;
	}
	public void setTodoID(Integer todoID) {
		this.todoID = todoID;
	}
	public String getUnNo() {
		return unNo;
	}
	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}
	public String getBatchJobNo() {
		return batchJobNo;
	}
	public void setBatchJobNo(String batchJobNo) {
		this.batchJobNo = batchJobNo;
	}
	public String getMsgSendUnit() {
		return msgSendUnit;
	}
	public void setMsgSendUnit(String msgSendUnit) {
		this.msgSendUnit = msgSendUnit;
	}
	public Integer getMsgSender() {
		return msgSender;
	}
	public void setMsgSender(Integer msgSender) {
		this.msgSender = msgSender;
	}
	public String getMsgTopic() {
		return msgTopic;
	}
	public void setMsgTopic(String msgTopic) {
		this.msgTopic = msgTopic;
	}
	public Date getMsgSendTime() {
		return msgSendTime;
	}
	public void setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsgSendName() {
		return msgSendName;
	}
	public void setMsgSendName(String msgSendName) {
		this.msgSendName = msgSendName;
	}
	public String getMsgSendUnitName() {
		return msgSendUnitName;
	}
	public void setMsgSendUnitName(String msgSendUnitName) {
		this.msgSendUnitName = msgSendUnitName;
	}
	public String getStatusName() {
		return StatusName;
	}
	public void setStatusName(String statusName) {
		StatusName = statusName;
	}
	public String getBizTypeName() {
		return bizTypeName;
	}
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	public String getUnnoName() {
		return unnoName;
	}
	public void setUnnoName(String unnoName) {
		this.unnoName = unnoName;
	}
}
