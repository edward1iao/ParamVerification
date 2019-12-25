package com.xyue.paramverification;

import com.xyue.paramverification.model.ParamVerificationResult;
import com.xyue.paramverification.util.ParamUtil;

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
