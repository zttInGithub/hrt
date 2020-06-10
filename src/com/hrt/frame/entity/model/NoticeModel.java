package com.hrt.frame.entity.model;

import java.io.Serializable;
import java.util.Date;

public class NoticeModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer noticeID;		//消息编号
	private String msgSendDate;		//发送日期
	private String msgReceUnit;		//接收机构
	private Integer msgReceUID;		//接受者
	private String msgSendUnit;		//发送机构
	private Integer msgSendUID;		//发送者
	private Date msgSendTime;		//发送时间
	private Date msgGetTime;		//接收时间
	private String msgTopic;		//消息标题
	private String msgDesc;			//消息内容
	private Integer	status;			//消息状态
	
	public Integer getNoticeID() {
		return noticeID;
	}
	public void setNoticeID(Integer noticeID) {
		this.noticeID = noticeID;
	}
	public String getMsgSendDate() {
		return msgSendDate;
	}
	public void setMsgSendDate(String msgSendDate) {
		this.msgSendDate = msgSendDate;
	}
	public String getMsgReceUnit() {
		return msgReceUnit;
	}
	public void setMsgReceUnit(String msgReceUnit) {
		this.msgReceUnit = msgReceUnit;
	}
	public Integer getMsgReceUID() {
		return msgReceUID;
	}
	public void setMsgReceUID(Integer msgReceUID) {
		this.msgReceUID = msgReceUID;
	}
	public String getMsgSendUnit() {
		return msgSendUnit;
	}
	public void setMsgSendUnit(String msgSendUnit) {
		this.msgSendUnit = msgSendUnit;
	}
	public Integer getMsgSendUID() {
		return msgSendUID;
	}
	public void setMsgSendUID(Integer msgSendUID) {
		this.msgSendUID = msgSendUID;
	}
	public Date getMsgSendTime() {
		return msgSendTime;
	}
	public void setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
	}
	public Date getMsgGetTime() {
		return msgGetTime;
	}
	public void setMsgGetTime(Date msgGetTime) {
		this.msgGetTime = msgGetTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsgTopic() {
		return msgTopic;
	}
	public void setMsgTopic(String msgTopic) {
		this.msgTopic = msgTopic;
	}
	public String getMsgDesc() {
		return msgDesc;
	}
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
	
}
