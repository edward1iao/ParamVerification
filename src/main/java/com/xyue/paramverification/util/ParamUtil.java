package com.xyue.paramverification.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import com.xyue.paramverification.annotation.Verification;
import com.xyue.paramverification.enums.EnumVerificationStatus;
import com.xyue.paramverification.exception.ParamVerificationException;
import com.xyue.paramverification.model.ParamModel;
import com.xyue.paramverification.model.ParamVerificationResult;

public class ParamUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ParamUtil.class);
	
	private static List<Class<?>> baseClazzs;
	
	/**
	 * 验证参数
	 * @param object 待验证参数
	 * @param isPrioritySort 是否进行优先级排序
	 * @param isAllCheck 是否全部参数进行校验
	 * @return
	 */
	public static ParamVerificationResult checkParam(Object object,boolean isPrioritySort,boolean isAllCheck){
		List<ParamModel> paramModels = null;
		try {
			paramModels = getAllFields(object);
		} catch (ParamVerificationException e) {
			logger.error(e.getMessage(),e);
			return ParamVerificationResult.exception(e.getMessage());
		}
		if(isPrioritySort)Collections.sort(paramModels);
		List<String> hitMsgs = new ArrayList<String>();
		Map<String,ParamModel> keyValue = new HashMap<String, ParamModel>();
		Map<String,String[]> andNullkey = new LinkedHashMap<String, String[]>();
		for(ParamModel paramModel:paramModels){
			keyValue.put(paramModel.getKey(), paramModel);
			boolean isHit = false;
			if(!paramModel.getVerification().isNull()){
				if(isNull(paramModel.getValue())){
					isHit = true;
					String hitMsg = StringUtils.isNotBlank(paramModel.getVerification().nullHitMsg())?paramModel.getVerification().nullHitMsg():paramModel.getVerification().hitMsg();
					if(hitMsg.indexOf("$key")!=-1)hitMsg = hitMsg.replace("$key", paramModel.getKey());
					if(StringUtils.isBlank(hitMsg))return ParamVerificationResult.exception(paramModel.getKey()+"字段对应空值命中提示语句为空");
					if(!isAllCheck)return ParamVerificationResult.fail(hitMsg);
					hitMsgs.add(hitMsg);
				}
			}
			if(!isHit&&paramModel.getVerification().format().length>0){
				for(int i=0;i<paramModel.getVerification().format().length;i++){
					String format=paramModel.getVerification().format()[i];
					if(StringUtils.isBlank(format))continue;
					if(isNull(paramModel.getValue())||!Pattern.matches(format, paramModel.getValue().toString())){
						String hitMsg = paramModel.getVerification().formatHitMsg().length>0&&i<paramModel.getVerification().formatHitMsg().length?paramModel.getVerification().formatHitMsg()[i]:paramModel.getVerification().hitMsg();
						if(StringUtils.isBlank(hitMsg))return ParamVerificationResult.exception(paramModel.getKey()+"字段对应正则命中提示语句为空");
						if(!isAllCheck)return ParamVerificationResult.fail(hitMsg);
						hitMsgs.add(hitMsg);
					}
				}
			}
			if(paramModel.getVerification().andNulls().length>0)andNullkey.put(paramModel.getKey(), paramModel.getVerification().andNulls());
		}
		for(Entry<String, String[]> entry:andNullkey.entrySet()){
			ParamModel paramModel = keyValue.get(entry.getKey());
			if(isNull(paramModel.getValue())){
				for(int i=0;i<entry.getValue().length;i++){
					boolean isAllNull = true;
					for(String key:entry.getValue()[i].split(",")){
						if(!isNull(keyValue.get(key).getValue())){
							isAllNull = false;
							break;
						}
					}
					if(isAllNull){
						String hitMsg = paramModel.getVerification().andNullsHitMsg().length>0&&i<paramModel.getVerification().andNullsHitMsg().length?paramModel.getVerification().andNullsHitMsg()[i]:paramModel.getVerification().hitMsg();
						if(StringUtils.isBlank(hitMsg))return ParamVerificationResult.exception(paramModel.getKey()+"字段("+(i+1)+")对应同时为空命中提示语句为空");
						if(!isAllCheck)return ParamVerificationResult.fail(hitMsg);
						hitMsgs.add(hitMsg);
					}
					
				}
			}
		}
		EnumVerificationStatus enumVerificationStatus = hitMsgs.size()>0?EnumVerificationStatus.FAIL:EnumVerificationStatus.SUCCESS;
		return new ParamVerificationResult(enumVerificationStatus.getStatus(), enumVerificationStatus.getMsg(), hitMsgs);
	}
	
	public static List<ParamModel> getAllFields(Object object)throws ParamVerificationException{
		List<ParamModel> paramModels = new ArrayList<ParamModel>();
		if(object!=null&&!isBaseClass(object.getClass())){
			if(object instanceof List||object.getClass().isArray()){
				paramModels.addAll(getAllFields(object,object.getClass(), null,null,null));
			}else if(object instanceof Map){
				throw new ParamVerificationException("暂不支持该类结构解析");
			}else{
				List<String> recursions = listAdd(null,object.getClass().getName());
				for(Field field :object.getClass().getDeclaredFields()){
					ParamModel paramModel = getParamModel(field, object, null, null);
					paramModels.add(paramModel);
					if(paramModel==null)continue;
					List<Integer> prioritys_ = new ArrayList<Integer>();
					prioritys_.add(paramModel.getVerification().priority());
					if(!isNull(paramModel.getValue())){
						paramModels.addAll(getAllFields(paramModel.getValue(),field.getType(), paramModel.getKey(),prioritys_,recursions));
					}else if(List.class.isAssignableFrom(field.getType())){
						List<String> recursions_ = listAdd(recursions, field.getGenericType().getTypeName());
						Type type =((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
						while(type instanceof ParameterizedTypeImpl&&!(type instanceof Class<?>)){
							recursions_ = listAdd(recursions_, type.getTypeName());
							type = ((ParameterizedTypeImpl)type).getActualTypeArguments()[0];
						}
						getAllFields(null,(Class<?>)type, paramModel.getKey(),prioritys_,recursions_);
					}else if(field.getType().isArray()){
						getAllFields(null,field.getType().getComponentType(), paramModel.getKey(),prioritys_,listAdd(recursions, field.getType().getName()));
					}
				}
			}
		}
		return paramModels;
	}
	
	private static List<ParamModel> getAllFields(Object object,Class<?> clazz,String preFieldNames,List<Integer> prioritys,List<String> recursions)throws ParamVerificationException{
		logger.debug("getAllFields_object:{},clazz:{},preFieldNames:{},prioritys:{},recursions:{}",object,clazz,preFieldNames,prioritys,recursions);
		List<ParamModel> paramModels = new ArrayList<ParamModel>();
		if(!isBaseClass(clazz)){
			if(Map.class.isAssignableFrom(clazz))throw new ParamVerificationException("暂不支持该类结构解析");
			if(List.class.isAssignableFrom(clazz)){
				if(!isNull(object)){
					for(Object obj:(List<?>)object) {
						if(!isBaseClass(obj.getClass())){
							paramModels.addAll(getAllFields(obj,obj.getClass(), preFieldNames,prioritys,listAdd(recursions, object.getClass().getName()+"<"+obj.getClass().getName()+">")));
						}
					}
				}else{
					logger.warn("存在空的集合（object:{}，class:{}），无法解析泛型结构，不进行结构合理性验证！", object,clazz);
				}
			}else if(clazz.isArray()){
				Class<?> clazz_ = clazz.getComponentType();
				if(!isBaseClass(clazz_)){
					List<String> recursions_ = listAdd(recursions,clazz.getName());
					if(object!=null)for(Object obj:(Object[])object) paramModels.addAll(getAllFields(obj,clazz_, preFieldNames,prioritys,recursions_));
					else getAllFields(null,clazz_, preFieldNames,prioritys,recursions_);
				}
			}else{
				List<String> recursions_ = listAdd(recursions,clazz.getName());
				for(Field field :clazz.getDeclaredFields()){
					ParamModel paramModel = getParamModel(field, object, preFieldNames, prioritys);
					if(paramModel==null)continue;
					paramModels.add(paramModel);
					List<Integer> prioritys_ = prioritys==null?new ArrayList<Integer>():new ArrayList<Integer>(prioritys);
					prioritys_.add(paramModel.getVerification().priority());
					paramModels.addAll(getAllFields(paramModel.getValue(),field.getType(), paramModel.getKey(),prioritys_,recursions_));
				}
			}
		}
		return paramModels;
	}
	
	private static ParamModel getParamModel(Field field,Object object,String preFieldNames,List<Integer> prioritys){
		if(field==null)return null;
		field.setAccessible(true);
		Verification verification = field.getAnnotation(Verification.class);
		if(verification==null)return null;
		Object value = null;
		try {
			if(object!=null)value = field.get(object);
		} catch (IllegalArgumentException |IllegalAccessException e) {
			logger.error(e.getMessage());
		}
		String key = preFieldNames==null?field.getName():preFieldNames+"."+field.getName();
		List<String> andNulls_ = new ArrayList<String>();
		for(String andNull:verification.andNulls())andNulls_.add(preFieldNames==null?andNull:preFieldNames+"."+andNull);
		if(andNulls_.size()>0){
			try {
				setAnnotationValue(verification, "andNulls", andNulls_.toArray(new String[]{}));
			} catch (IllegalArgumentException
					| IllegalAccessException | NoSuchFieldException
					| SecurityException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return new ParamModel(key,verification, value,prioritys); 
	}

	/**
	 * 设置注解字段值
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@SuppressWarnings("unchecked")
	private static void setAnnotationValue(Annotation annotation, String propertyName, Object value)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
		Field declaredField = invocationHandler.getClass().getDeclaredField("memberValues");
		declaredField.setAccessible(true);
		Map<String, Object> memberValues = (Map<String, Object>) declaredField.get(invocationHandler);
		memberValues.put(propertyName, value);
	}

	private static boolean isNull(Object object){
		boolean isNull = object ==null;
		if(!isNull){
			if(object instanceof String)isNull = StringUtils.isBlank(object.toString());
			else if(object instanceof List)isNull = ((List<?>)object).size()<=0;
		}
		return isNull;
	}
	
	private static boolean isBaseClass(Class<?> clazz){
		if(baseClazzs==null){
			baseClazzs = new ArrayList<Class<?>>();
			baseClazzs.add(String.class);
			baseClazzs.add(Character.class);
			baseClazzs.add(Integer.class);
			baseClazzs.add(Long.class);
			baseClazzs.add(Float.class);
			baseClazzs.add(Double.class);
			baseClazzs.add(Byte.class);
			baseClazzs.add(Boolean.class);
			baseClazzs.add(Short.class);
			baseClazzs.add(Date.class);
		}
		return baseClazzs.indexOf(clazz)!=-1;
	}
	
	private static List<String> listAdd(List<String> list,String data)throws ParamVerificationException{
		List<String> list_ = list==null?new ArrayList<String>():new ArrayList<String>(list);
		if(list_.indexOf(data)==-1)list_.add(data);
		else throw new ParamVerificationException("存在异常结构（关联层级间重复出现类结构）："+data);
		return list_;
	}
}

