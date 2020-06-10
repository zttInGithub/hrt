package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Set;

public class DictionaryModel implements Serializable{
	
		private String id;
	
		private String code;
		
		private String value;
		
		private Integer pid;
		
		//父节点
		private DictionaryModel parent;
		
		//子节点
		private Set<DictionaryModel> children;

		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Integer getPid() {
			return pid;
		}

		public void setPid(Integer pid) {
			this.pid = pid;
		}

		public DictionaryModel getParent() {
			return parent;
		}

		public void setParent(DictionaryModel parent) {
			this.parent = parent;
		}

		public Set<DictionaryModel> getChildren() {
			return children;
		}

		public void setChildren(Set<DictionaryModel> children) {
			this.children = children;
		}
		
		
		
		
		
}
