package com.weixin.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.weixin.model.BaseMessage;
import com.weixin.util.Constant;

/**
 * 处理事件的service
 * @author wan
 */
public class EventService {
	
	private static Calendar calendar = Calendar.getInstance();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 处理事件的函数
	 * 直接由这个函数产生应该怎样回复的信息
	 * @param key 判断事件的key值
	 * @param base 会从这个类里面将一些通用的内容拷贝出来，例如发向哪个用户，来自哪个用户
	 */
	public static String processEvent(String key, BaseMessage base) {
		String result = "";
		
		//预约空调
		if( key.equals(Constant.CLICK11_KEY)) {
			
		} else if( key.equals(Constant.CLICK12_KEY)) {
			
		} else if( key.equals(Constant.CLICK13_KEY)) {
			
		} else if( key.equals(Constant.CLICK14_KEY)) {
			
		} else if( key.equals(Constant.CLICK15_KEY)) {
			
		} else if( key.equals(Constant.CLICK21_KEY)) {
			
		} else if( key.equals(Constant.CLICK22_KEY)) {
			
		} else if( key.equals(Constant.CLICK23_KEY)) {
			
		} else if( key.equals(Constant.CLICK24_KEY)) {
			
		} else if( key.equals(Constant.CLICK25_KEY) ) {
			
		} else if( key.equals(Constant.CLICK31_KEY)) {
			
		} else if( key.equals(Constant.CLICK32_KEY)) {
			
		} else if( key.equals(Constant.CLICK33_KEY)) {
			
		} else if( key.equals(Constant.CLICK34_KEY)) {
			
		} else if( key.equals(Constant.CLICK35_KEY)) {
		} 
		return result;
	}
	
	
}
