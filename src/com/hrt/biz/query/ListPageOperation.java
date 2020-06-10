package com.hrt.biz.query;

public class ListPageOperation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		if(width==null||"".equals(width)) return ;
		this.width = width;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	private String content;
	private String width="80";
	private String action;

}
