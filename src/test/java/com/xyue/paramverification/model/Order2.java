package com.xyue.paramverification.model;

import java.util.List;

import com.xyue.paramverification.annotation.Verification;

public class Order2 {
	@Verification
	private OrderDetail2[][] orderDetails2;
	@Verification
	private List<List<OrderDetail2>> orderDetailList;
	public OrderDetail2[][] getOrderDetails2() {
		return orderDetails2;
	}
	public void setOrderDetails2(OrderDetail2[][] orderDetails2) {
		this.orderDetails2 = orderDetails2;
	}
	public List<List<OrderDetail2>> getOrderDetailList() {
		return orderDetailList;
	}
	public void setOrderDetailList(List<List<OrderDetail2>> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	
}
