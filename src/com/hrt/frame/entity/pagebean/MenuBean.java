package com.hrt.frame.entity.pagebean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类功能：菜单管理
 * 创建人：wwy
 * 创建日期：2013-01-10
 */
public class MenuBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//ID
	private Integer id;
	
	//菜单ID
	private Integer menuID;
	
	//菜单名称
	private String text;
	
	//菜单对应URL
	private String src;
	
	//父菜单ID
	private Integer _parentId;
	
	//菜单序列
	private Integer seq;
	
	//创建日期
	private Date createDate;
	
	//创建者
	private String createUser;
	
	//修改日期
	private Date updateDate;
	
	//修改者
	private String updateUser;
	
	//是否展开(open,closed)
	private String state;
	
	//子菜单
	private List<MenuBean> children;
	
	//图标
	private String iconcls;
	
	//菜单代码
	private String tranCode;
	
	//状态 0-停用  1-启用
	private String statusName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMenuID() {
		return menuID;
	}

	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer get_parentId() {
		return _parentId;
	}

	public void set_parentId(Integer parentId) {
		_parentId = parentId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public List<MenuBean> getChildren() {
		return children;
	}

	public void setChildren(List<MenuBean> children) {
		this.children = children;
	}

	public String getIconcls() {
		return iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}
