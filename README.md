# param-verification

#### 项目介绍
参数校验工具（通过注解与反射实现）
- [x] 参数可空校验
- [x] 参数正则校验
- [x] 同级参数同时为空校验
- [x] 对象参数渗透校验
- [x] 校验优先级设置
- [x] 校验结果支持单结果及多结果返回

#### 使用说明
1. 在需要校验的实体类字段上面标记Verification注解
2. 使用ParamVerificationOperator参数验证操作类checkParam方法进行参数校验;
3. 通过反回结果ParamVerificationResult判断参数校验结果
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

[Verification注解详情](src/main/java/com/xyue/paramverification/annotation/Verification.java)、
[ParamVerificationOperator参数验证操作类](src/main/java/com/xyue/paramverification/ParamVerificationOperator.java)、
[TestVerification测试例子](src/test/java/com/xyue/paramverification/test/TestVerification.java)

#### 项目说明
本项目部分代码及思路借鉴了[Coody](https://gitee.com/coodyer)的[coody-verification](https://gitee.com/coodyer/coody-verification)项目。
