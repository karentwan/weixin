package com.weixin.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
			jsonObj.element(s, array);
			obj.element(type, jsonObj);
		}
		return obj;
	}
	
	public static JSONObject consum2JSON(List<String> list) {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject o = null;
		for(int i = 0; i < list.size(); i++) {
//			array.add(i, list.get(i));
			if( i % 2 == 0) {
				o = new JSONObject();
				o.put("money", list.get(i));
			} else {
				o.put("time", list.get(i));
				continue;
			}
			array.element(o);
		}
		obj.element("result", array);
		return obj;
	}
	
	public static String balance2JSON(List<String> list) {
		JSONObject obj = new JSONObject();
		obj.put("money", list.get(0));
		obj.put("userName", list.get(1));
		obj.put("userId", list.get(2));
		return obj.toString();
	}
	/**
	 * ��userNameת����json
	 * @param name
	 * @return
	 */
	public static String name2JSON(String name) {
		JSONObject obj = new JSONObject();
		obj.put("userName", name);
		return obj.toString();
	}
	
}
