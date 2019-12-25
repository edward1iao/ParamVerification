package com.xyue.paramverification.model;

import com.xyue.paramverification.annotation.Verification;

public class OrderDetail2 {
	@Verification
	private String orderDetailName;
	@Verification
	private Double price;
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
	@Override
	public String toString() {
		return "OrderDetail2 [orderDetailName=" + orderDetailName + ", price="
				+ price + "]";
	}
}	
