package com.xyue.paramverification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Verification {
	/**
	 * 是否可空
	 * @return
	 */
	boolean isNull() default false;
	/**
	 * 正则表达式
	 * @return
	 */
	String[] format() default {};
	/**
	 * 同时为空的其他字段(多个字段并列用英文逗号隔开)
	 * @return
	 */
	String[] andNulls() default {};
	/**
	 * 命中提示(最优先)
	 * @return
	 */
	String hitMsg() default "";
	/**
	 * 字段为空命中提示
	 * @return
	 */
	String nullHitMsg() default "$key不能为空";
	/**
	 * 正则表达式命中提示
	 * @return
	 */
	String[] formatHitMsg() default {};
	/**
	 * 同时为空命中提示
	 * @return
	 */
	String[] andNullsHitMsg() default {};
	/**
	 * 优先级(值越大优先级越高)
	 * @return
	 */
	int priority() default 0;
}
