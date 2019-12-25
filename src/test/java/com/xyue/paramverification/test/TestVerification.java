package com.xyue.paramverification.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xyue.paramverification.ParamVerificationOperator;
import com.xyue.paramverification.exception.ParamVerificationException;
import com.xyue.paramverification.model.Order;
import com.xyue.paramverification.model.Order2;
import com.xyue.paramverification.model.Order3;
import com.xyue.paramverification.model.OrderDetail;

public class TestVerification{
	private static Logger logger = LoggerFactory.getLogger(TestVerification.class);
	@Test
	public void test1(){
		Order order = new Order();
		order.setOrderId(111l);
		order.setAllPrice(222d);
		order.setOrderName("asdf");
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		orderDetails.add(new OrderDetail("assd", 222.3d));
		orderDetails.add(new OrderDetail("dddd", 21.3d));
		order.setOrderDetails(orderDetails);
		logger.info("order checkParam:{}",ParamVerificationOperator.checkParam(order,true,false));
	}
	@Test
	public void test2(){
		logger.info("order2 checkParam:{}",ParamVerificationOperator.checkParam(new Order2(),true,false));
	}
	@Test
	public void test3() throws IllegalArgumentException, IllegalAccessException, ParamVerificationException {
		Order3 order3 = new Order3();
		order3.setName("订单测试1");
		order3.setCreatetime("2018-01-01 20:30:01");
		order3.setIndex(23);
		order3.setLsPrice(300D);
		order3.setJhPrice(300D);
		logger.info("order3 checkParam:{}",ParamVerificationOperator.checkParam(order3,true,false));
	}
}
