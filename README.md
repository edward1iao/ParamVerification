# param-verification

#### 项目介绍
java实现的参数校验工具（通过注解与反射实现）
- [x] 参数可空校验
- [x] 参数正则校验
- [x] 同级参数同时为空校验
- [x] 对象参数渗透校验
- [x] 校验优先级设置
- [x] 支持返回多种校验结果

#### 使用说明
1. jdk需求1.8及以上版本
2. 在需要校验的实体类字段上面标记Verification注解
3. 使用ParamVerificationOperator参数验证操作类checkParam方法进行参数校验;
```java
/**
 * 参数验证操作器
 */
public class ParamVerificationOperator {
	/**
	 * 验证参数
	 * @param object 待验证参数
	 * @return
	 */
	public static ParamVerificationResult checkParam(Object object){
		return checkParam(object, true, false);
	}
	/**
	 * 验证参数
	 * @param object 待验证参数
	 * @param isPrioritySort 是否进行优先值排序
	 * @param isAllCheck 是否全部验证
	 * @return
	 */
	public static ParamVerificationResult checkParam(Object object,boolean isPrioritySort,boolean isAllCheck){
		return ParamUtil.checkParam(object, isPrioritySort, isAllCheck);
	}
}
```
4. 通过返回结果ParamVerificationResult判断参数校验结果

### 主要类及注解
1. [Verification注解详情](src/main/java/com/xyue/paramverification/annotation/Verification.java)
2. [ParamVerificationOperator参数验证操作类](src/main/java/com/xyue/paramverification/ParamVerificationOperator.java)

### 调用参考例子
[TestVerification测试例子](src/test/java/com/xyue/paramverification/test/TestVerification.java)
```java
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
```
输出结果如下：
```
2019-12-25 18:08:16,001 com.xyue.paramverification.test.TestVerification.test3(TestVerification.java:53)-> 验证成功 
2019-12-25 18:08:16,007 com.xyue.paramverification.test.TestVerification.test2(TestVerification.java:41)-> 验证失败 
2019-12-25 18:08:16,047 com.xyue.paramverification.test.TestVerification$$Lambda$1/48612937.accept(?:?)-> orderDetails2不能为空 
2019-12-25 18:08:16,047 com.xyue.paramverification.test.TestVerification$$Lambda$1/48612937.accept(?:?)-> orderDetailList不能为空 
2019-12-25 18:08:16,050 com.xyue.paramverification.test.TestVerification.test1(TestVerification.java:35)-> 验证成功 
```

#### 项目说明
本项目部分代码及思路借鉴了[Coody](https://gitee.com/coodyer)的[coody-verification](https://gitee.com/coodyer/coody-verification)项目。
