package com.weixin.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.weixin.model.BaseMessage;
import com.weixin.util.Constant;

/**
 * �����¼���service
 * @author wan
 */
public class EventService {
	
	private static Calendar calendar = Calendar.getInstance();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * �����¼��ĺ���
	 * ֱ���������������Ӧ�������ظ�����Ϣ
	 * @param key �ж��¼���keyֵ
	 * @param base �����������潫һЩͨ�õ����ݿ������������緢���ĸ��û��������ĸ��û�
	 */
	public static String processEvent(String key, BaseMessage base) {
		String result = "";
		
		//ԤԼ�յ�
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
