package com.xyue.paramverification.model;

import java.util.List;

import com.xyue.paramverification.annotation.Verification;

public class Order {
	@Verification(priority=1)
	private Long orderId;
	@Verification
	private String orderName;
	@Verification
	private Double allPrice;
	@Verification(priority=1000)
	private List<OrderDetail> orderDetails;
	@Verification(priority=999,nullHitMsg="价格不能为空")
	private Double price;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Double getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(Double allPrice) {
		this.allPrice = allPrice;
	}
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public Order(Long orderId, String orderName, Double allPrice,
			List<OrderDetail> orderDetails) {
		super();
		this.orderId = orderId;
		this.orderName = orderName;
		this.allPrice = allPrice;
		this.orderDetails = orderDetails;
	}
	public Order() {
		super();
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderName=" + orderName
				+ ", allPrice=" + allPrice + ", orderDetails=" + orderDetails
				+ "]";
	}
}
