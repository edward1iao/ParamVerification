package com.xyue.paramverification.model;

import java.util.ArrayList;
import java.util.List;

import com.xyue.paramverification.enums.EnumVerificationStatus;


public class ParamVerificationResult {
	private int verificationStatus;// 校验状态 0校验失败，命中规则，1校验成功，-1校验异常
	private String verificationMsg;//校验状态描述
	private List<String> hitMsgs;//命中提示
	public int getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(int verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public String getVerificationMsg() {
		return verificationMsg;
	}
	public void setVerificationMsg(String verificationMsg) {
		this.verificationMsg = verificationMsg;
	}
	public List<String> getHitMsgs() {
		return hitMsgs;
	}
	public void setHitMsgs(List<String> hitMsgs) {
		this.hitMsgs = hitMsgs;
	}
	public ParamVerificationResult addHitMsg(String hitMsg){
		if(this.hitMsgs==null)this.hitMsgs = new ArrayList<String>();
		this.hitMsgs.add(hitMsg);
		return this;
	}
	public boolean isSuccess(){
		return EnumVerificationStatus.SUCCESS.getStatus()==this.verificationStatus;
	}
	public boolean isFail(){
		return EnumVerificationStatus.FAIL.getStatus()==this.verificationStatus;
	}
	public ParamVerificationResult() {
		super();
	}
	public ParamVerificationResult(int verificationStatus, String verificationMsg) {
		super();
		this.verificationStatus = verificationStatus;
		this.verificationMsg = verificationMsg;
	}
	public ParamVerificationResult(int verificationStatus, String verificationMsg,
			List<String> hitMsgs) {
		super();
		this.verificationStatus = verificationStatus;
		this.verificationMsg = verificationMsg;
		this.hitMsgs = hitMsgs;
	}
	public static ParamVerificationResult fail(String hitMsg){
		return new ParamVerificationResult(EnumVerificationStatus.FAIL.getStatus(), EnumVerificationStatus.FAIL.getMsg()).addHitMsg(hitMsg);
	}
	public static ParamVerificationResult exception(String exceptionMsg){
		return new ParamVerificationResult(EnumVerificationStatus.EXCEPTION.getStatus(), EnumVerificationStatus.EXCEPTION.getMsg()+":"+exceptionMsg);
	}
	@Override
	public String toString() {
		return "ParamCheckResult [verificationStatus=" + verificationStatus + ", verificationMsg="
				+ verificationMsg + ", hitMsgs=" + hitMsgs + "]";
	}
}
