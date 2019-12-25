package com.xyue.paramverification.test;

import java.util.ArrayList;
import java.util.Date;
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
import com.xyue.paramverification.model.OrderDetailData;
import com.xyue.paramverification.model.ParamVerificationResult;

public class TestVerification{
	private static Logger logger = LoggerFactory.getLogger(TestVerification.class);
	@Test
	public void test1(){
		Order order = new Order();
		order.setOrderId(111l);
		order.setAllPrice(222d);
		order.setOrderName("asdf");
		List<OrderDetailData> orderDetailDatas = new ArrayList<OrderDetailData>();
		orderDetailDatas.add(new OrderDetailData("sss", new Date(), null));
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		orderDetails.add(new OrderDetail("assd", 222.3d,orderDetailDatas));
		orderDetails.add(new OrderDetail("dddd", 21.3d,orderDetailDatas));
		order.setOrderDetails(orderDetails);
		ParamVerificationResult paramVerificationResult = ParamVerificationOperator.checkParam(order);
		logger.info(paramVerificationResult.getVerificationMsg());
		if(paramVerificationResult.isFail()) paramVerificationResult.getHitMsgs().forEach(logger::info);
	}
	@Test
	public void test2(){
		ParamVerificationResult paramVerificationResult = ParamVerificationOperator.checkParam(new Order2(),false,true);
		logger.info(paramVerificationResult.getVerificationMsg());
		if(paramVerificationResult.isFail()) paramVerificationResult.getHitMsgs().forEach(logger::info);
	}
	@Test
	public void test3() throws IllegalArgumentException, IllegalAccessException, ParamVerificationException {
		Order3 order3 = new Order3();
		order3.setName("订单测试1");
		order3.setCreatetime("2018-01-01 20:30:01");
		order3.setIndex(23);
		order3.setLsPrice(300D);
		order3.setJhPrice(300D);
		ParamVerificationResult paramVerificationResult = ParamVerificationOperator.checkParam(order3,true,false);
		logger.info(paramVerificationResult.getVerificationMsg());
		if(paramVerificationResult.isFail()) paramVerificationResult.getHitMsgs().forEach(logger::info);
	}
}
