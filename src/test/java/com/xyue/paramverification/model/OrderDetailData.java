package com.xyue.paramverification.model;

import java.util.Date;

import com.xyue.paramverification.annotation.Verification;

public class OrderDetailData {
	@Verification
	private String content;
	@Verification
	private Date date;
	private String desc;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public OrderDetailData() {
		super();
	}
	public OrderDetailData(String content, Date date, String desc) {
		super();
		this.content = content;
		this.date = date;
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "OrderDetailData [content=" + content + ", date=" + date
				+ ", desc=" + desc + "]";
	}
	
}
