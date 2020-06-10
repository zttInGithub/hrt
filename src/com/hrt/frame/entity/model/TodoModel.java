package com.hrt.frame.entity.model;

import java.io.Serializable;
import java.util.Date;

public class TodoModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer todoID;			//代办编号
    private String unNo;			//接收机构ID
    private String batchJobNo;		//业务关键字
    private String msgSendUnit;		//发送机构ID
    private Integer msgSender;		//发送用户ID
    private String msgTopic;		//发送主题
    private Date msgSendTime;		//发送时间
    private String tranCode;		//菜单代码
    private String bizType;			//业务类别
    private Integer status;			//代办状态
    
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
    
}
