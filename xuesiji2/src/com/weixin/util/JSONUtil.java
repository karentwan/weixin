package com.weixin.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.weixin.pojo.Leave;
import com.weixin.pojo.Sign;

/**
 * 对象到json的工具类
 * @author wan
 */
public class JSONUtil {

	/**
	 * 将map集合转换成json字符串
	 * @param map 要转换的map集合
	 * @param type 类型,例如：sign、disconnect、leave
	 * @param obj 需要将结果加进的jsonObject
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
			//element将会代替里面的值，但是accumulate方法将会积累同一个key下面的值
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
