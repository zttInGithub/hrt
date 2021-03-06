package com.hrt.frame.entity.pagebean;

import java.io.Serializable;
import java.util.List;

/**
 * 类功能：添加、修改、删除返回结果集
 * 创建人：wwy
 * 创建日期：2012-12-04
 */
public class JsonBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	//执行结果
	private boolean success = false;
	
	//Session是否失效
	private boolean sessionExpire = false;

	//页面提示信息
	private String msg = "";
	
	//为会员宝专用（单位）
	private String numberUnits = "";
	//为会员宝专用（倍数）
	private Double beiShu;
	//历史交易总金额
	private String countTxnAmount; 
	
	private Integer leftTimes; 
	
	private List list; 

	private Object obj = null;
	
	private String returnUrl;
	
	private String status;

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Integer getLeftTimes() {
		return leftTimes;
	}

	public void setLeftTimes(Integer leftTimes) {
		this.leftTimes = leftTimes;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSessionExpire() {
		return sessionExpire;
	}

	public void setSessionExpire(boolean sessionExpire) {
		this.sessionExpire = sessionExpire;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getNumberUnits() {
		return numberUnits;
	}

	public void setNumberUnits(String numberUnits) {
		this.numberUnits = numberUnits;
	}

	public Double getBeiShu() {
		return beiShu;
	}

	public void setBeiShu(Double beiShu) {
		this.beiShu = beiShu;
	}

	public String getCountTxnAmount() {
		return countTxnAmount;
	}

	public void setCountTxnAmount(String countTxnAmount) {
		this.countTxnAmount = countTxnAmount;
	}
	
}
