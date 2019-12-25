package com.xyue.paramverification.model;

import java.util.List;

import com.xyue.paramverification.annotation.Verification;

public class ParamModel implements Comparable<ParamModel>{
	private String key;
	private Object value;
	private Verification verification;
	private List<Integer> prioritys;
	public Verification getVerification() {
		return verification;
	}
	public void setVerification(Verification verification) {
		this.verification = verification;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public ParamModel(String key,Verification verification, Object value,List<Integer> prioritys) {
		super();
		this.key = key;
		this.verification = verification;
		this.value = value;
		this.prioritys = prioritys;
	}
	public List<Integer> getPrioritys() {
		return prioritys;
	}
	public void setPrioritys(List<Integer> prioritys) {
		this.prioritys = prioritys;
	}
	@Override
	public String toString() {
		return "ParamModel [key=" + key + ", value=" + value + ", verification="
				+ verification + ", prioritys=" + prioritys + "]";
	}
	@Override
	public int compareTo(ParamModel paramModel) {
		int compareResult = 0;
		if(this.prioritys==null){
			compareResult = compareResult(this.verification.priority(), paramModel.prioritys==null?paramModel.verification.priority():paramModel.prioritys.get(0));
		}else{
			if(paramModel.prioritys==null){
				compareResult = compareResult(this.prioritys.get(0), paramModel.verification.priority());
			}else{
				int operPrioritysSize = paramModel.prioritys.size();
				int thisPrioritysSize = this.prioritys.size();
				for(int i=0;i<thisPrioritysSize;i++){
					if(operPrioritysSize<=i+1) return 1;
					compareResult = compareResult(this.prioritys.get(i), paramModel.prioritys.get(i));
					if(compareResult!=0)return compareResult;
				}
				if(compareResult==0)compareResult(this.verification.priority(), paramModel.verification.priority());
			}
		}
		return compareResult==0?this.key.compareTo(paramModel.key):compareResult;
	}
	private int compareResult(int priority1,int priority2){
		return priority1>priority2?-1:priority1<priority2?1:0;
	}
}
