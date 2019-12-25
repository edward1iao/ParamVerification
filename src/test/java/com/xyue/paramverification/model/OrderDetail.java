package com.xyue.paramverification.model;

import java.util.List;

import com.xyue.paramverification.annotation.Verification;

public class OrderDetail {
	@Verification
	private String orderDetailName;
	@Verification
	private Double price;
	@Verification
	private List<OrderDetailData> orderDetailDatas;
	public String getOrderDetailName() {
		return orderDetailName;
	}
	public void setOrderDetailName(String orderDetailName) {
		this.orderDetailName = orderDetailName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public OrderDetail(String orderDetailName, Double price,List<OrderDetailData> orderDetailDatas) {
		super();
		this.orderDetailName = orderDetailName;
		this.price = price;
		this.orderDetailDatas = orderDetailDatas;
	}
	public OrderDetail() {
		super();
	}
	public List<OrderDetailData> getOrderDetailDatas() {
		return orderDetailDatas;
	}
	public void setOrderDetailDatas(List<OrderDetailData> orderDetailDatas) {
		this.orderDetailDatas = orderDetailDatas;
	}
	@Override
	public String toString() {
		return "OrderDetail [orderDetailName=" + orderDetailName + ", price="
				+ price + ",orderDetailDatas="+orderDetailDatas+"]";
	}
}
