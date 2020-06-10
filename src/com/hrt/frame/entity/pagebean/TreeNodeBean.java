package com.hrt.frame.entity.pagebean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类功能：tree combotree数据集合类
 * 创建人：wwy
 * 创建日期：2012-12-18
 */
public class TreeNodeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//节点ID
	private String id;
	
	//树节点名称
	private String text;
	
	//节点前图标样式
	private String iconCls;
	
	//是否选中
	private boolean checked;
	
	//是否展开(open,closed)
	private String state;
	
	//子节点
	private List<TreeNodeBean> children;
	
	//节点自定义属性集合
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<TreeNodeBean> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeBean> children) {
		this.children = children;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
}
