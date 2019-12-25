package com.xyue.paramverification.model;

import com.xyue.paramverification.annotation.Verification;
import com.xyue.paramverification.constant.FormatConstant;

public class Order3 {
	@Verification(nullHitMsg="名称不能为空")
	private String name;
	@Verification(nullHitMsg="创建时间不能为空",format={FormatConstant.DATETIME},formatHitMsg={"创建时间格式错误"})
	private String createtime;
	@Verification(format={"^[0-9]|1[0-9]|2[0-3]$"},formatHitMsg={"取值范围只能在0-23"})
	private Integer index;
	@Verification(isNull=true,andNulls={"lsPrice","jhTime,jhSource"},andNullsHitMsg={"进货价格与零售价格不能同时为空","进货相关信息不能同时为空"})
	private Double jhPrice;
	@Verification(isNull=true)
	private String jhTime;
	@Verification(isNull=true)
	private String jhSource;
	@Verification(isNull=true)
	private Double lsPrice;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Double getJhPrice() {
		return jhPrice;
	}
	public void setJhPrice(Double jhPrice) {
		this.jhPrice = jhPrice;
	}
	public String getJhTime() {
		return jhTime;
	}
	public void setJhTime(String jhTime) {
		this.jhTime = jhTime;
	}
	public String getJhSource() {
		return jhSource;
	}
	public void setJhSource(String jhSource) {
		this.jhSource = jhSource;
	}
	public Double getLsPrice() {
		return lsPrice;
	}
	public void setLsPrice(Double lsPrice) {
		this.lsPrice = lsPrice;
	}
	@Override
	public String toString() {
		return "Order3 [name=" + name + ", createtime=" + createtime
				+ ", index=" + index + ", jhPrice=" + jhPrice + ", jhTime="
				+ jhTime + ", jhSource=" + jhSource + ", lsPrice=" + lsPrice
				+ "]";
	}
}
