package com.xyue.paramverification.enums;

/**
 * 校验状态 0-验证失败,1-验证成功,-1-验证异常
 */
public enum EnumVerificationStatus {
	/**
	 * 1-验证成功
	 */
	SUCCESS(1,"验证成功"),
	/**
	 * 0-验证失败
	 */
	FAIL(0,"验证失败"),
	/**
	 * -1-验证异常
	 */
	EXCEPTION(-1,"验证异常");
	private int status;
	private String msg;
	public int getStatus() {
		return status;
	}
	public String getMsg() {
		return msg;
	}
	private EnumVerificationStatus(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
}
