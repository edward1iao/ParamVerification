package com.xyue.paramverification.model;

import java.util.List;

import com.xyue.paramverification.annotation.Verification;

public class Order2 {
	@Verification
	private OrderDetail[][] orderDetails;
	@Verification
	private List<List<OrderDetail>> orderDetailList;
	public OrderDetail[][] getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(OrderDetail[][] orderDetails) {
		this.orderDetails = orderDetails;
	}
	public List<List<OrderDetail>> getOrderDetailList() {
		return orderDetailList;
	}
	public void setOrderDetailList(List<List<OrderDetail>> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	
}
