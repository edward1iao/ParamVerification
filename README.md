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
2. 使用com.xyue.paramverification.ParamVerificationOperator.checkParam(Object)，
com.xyue.paramverification.ParamVerificationOperator.checkParam(Object, boolean, boolean)进行参数校验;
3. 通过反回结果ParamVerificationResult判断参数校验结果

ps:/param-verification/src/test/java/com/xyue/paramverification/test/TestVerification.java 类中提供了测试例子
Verification注解详情 [传送门](src/main/java/com/xyue/paramverification/annotation/Verification.java)
ParamVerificationOperator参数验证操作类[传送门](src/main/java/com/xyue/paramverification/ParamVerificationOperator.java)

#### 项目说明
本项目借鉴了Coody(https://gitee.com/coodyer)的coody-verification(https://gitee.com/coodyer/coody-verification)项目，再加入部分个人理解及需求进行改造。
