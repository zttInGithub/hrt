package com.hrt.frame.entity.pagebean;

import java.io.Serializable;
import java.util.Date;

public class NoticeBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//当前页数
	private Integer page;
	
	//总记录数
	private Integer rows;
	
	//排序字段
	private String sort;
	
	//排序规则 ASC DESC
	private String order;
	
	//消息编号
	private Integer noticeID;
	
	//发送日期
	private String msgSendDate;
	
	//接受机构ID
	private String msgReceUnit;
	
	//接受者ID
	private Integer msgReceUID;
	
	//发送机构ID
	private String msgSendUnit;
	
	//发送者ID
	private Integer msgSendUID;
	
	//发送时间
	private Date msgSendTime;
	
	//接收时间
	private Date msgGetTime;
	
	//消息标题
	private String msgTopic;
	
	//消息内容
	private String msgDesc;
	
	//发送者名称
	private String msgSendName;
	
	//接受者名称
	private String MsgReceName;
	
	//发送机构名称
	private String msgSendUnitName;
	
	//接受机构名称
	private String msgReceUnitName;

	//消息状态
	private Integer status;
	
	//消息状态名称
	private String statusName;
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getMsgReceUnitName() {
		return msgReceUnitName;
	}

	public void setMsgReceUnitName(String msgReceUnitName) {
		this.msgReceUnitName = msgReceUnitName;
	}

	public String getMsgReceName() {
		return MsgReceName;
	}

	public void setMsgReceName(String msgReceName) {
		MsgReceName = msgReceName;
	}

}
