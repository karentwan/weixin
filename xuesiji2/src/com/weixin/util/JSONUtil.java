package com.weixin.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.weixin.pojo.Leave;
import com.weixin.pojo.Sign;

/**
 * ����json�Ĺ�����
 * @author wan
 */
public class JSONUtil {

	/**
	 * ��map����ת����json�ַ���
	 * @param map Ҫת����map����
	 * @param type ����,���磺sign��disconnect��leave
	 * @param obj ��Ҫ������ӽ���jsonObject
	 * @return 
	 */
	public static JSONObject toJSON(Map<String, List<String>> map, String type, JSONObject obj) {
		Set<String> set = map.keySet();
		List<String> list = null;
		for( String s : set) {
			JSONArray array = new JSONArray();
			list = map.get(s);
			for(int i = 0; i < list.size(); i++) {
				array.add(i, list.get(i));
			}
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate(s, array);
			//element������������ֵ������accumulate�����������ͬһ��key�����ֵ
			obj.accumulate(type, jsonObj);
		}
		return obj;
	}
	
	public static JSONObject toJSON2(Map<String, List<Sign>> map, String type, JSONObject obj) {
		JSONObject o = JSONObject.fromObject(map);
		obj.element(type, o);
		return o;
	}
	
	public static JSONObject leave2JSON2(Map<String, List<Leave>> map, String type, JSONObject obj) {
		JSONObject o = JSONObject.fromObject(map);
		obj.element(type, o);
		return o;
	}
	
	public static String count2JSON(Map<String, Integer> map, String time) {
		JSONObject obj = new JSONObject();
		obj.element("time", time);
		int sign = map.get("signIn");
		int leave = map.get("leave");
		int disconnect = map.get("disconnect");
		obj.element("signInNum", sign);
		obj.element("leaveNum", leave);
		obj.element("disconnectNum", disconnect);
		return obj.toString();
	}
	
	public static String class2JSON(List<String> list) {
		JSONArray array = JSONArray.fromObject(list);
		JSONObject obj = new JSONObject();
		obj.element("class", array);
		return obj.toString();
	}
	
}
