package com.weixin.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import com.weixin.dao.InstruDao;
import com.weixin.util.Db;
import com.weixin.util.JSONUtil;

public class JSONTest {
	
//	@Test
	public void testJSON() {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			Map<String, List<String>> map = InstruDao.getInstance().queryUnSignAndClazz(stmt, "万海燕", "2016-05-11");
			Set<String> set = map.keySet();
			JSONObject obj = new JSONObject();
			List<String> list = null;
			for( String s : set) {
				JSONArray array = new JSONArray();
				list = map.get(s);
				for(int i = 0; i < list.size(); i++) {
					array.add(i, list.get(i));
				}
				JSONObject jsonObj = new JSONObject();
				jsonObj.element(s, array);
				obj.element("disconnect", jsonObj);
			}
System.out.println(obj.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
//	@Test
	public void testJSon() {
		JSONObject obj = new JSONObject();
		InstruDao id = InstruDao.getInstance();
		Connection conn = Db.getConnection();
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			Map<String, List<String>> map = id.querySignInAndClazz(stmt, "万海燕", "2016-05-11");
			Map<String, List<String>> map1 = id.queryUnSignAndClazz(stmt, "万海燕", "2016-05-11");
			Map<String, List<String>> map2 = id.queryLeaveAndClazz(stmt, "万海燕", "2016-05-11");
//			obj.element("sign", JSONUtil.toJSON(map, "sign", obj));
			JSONUtil.toJSON(map, "sign", obj);
			JSONUtil.toJSON(map1, "disconnect", obj);
//			obj.element("disconnect", JSONUtil.toJSON(map1, "disconnect", obj));
			JSONUtil.toJSON(map2, "leave", obj);
//			obj.element("leave", JSONUtil.toJSON(map2, "leave", obj));
System.out.println(obj.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testJSONArray() {
		 JSONArray jsonStrs = new JSONArray();
		 jsonStrs.add(0, "cat");
		 jsonStrs.add(1, "dog");
		 jsonStrs.add(2, "bird");
		 jsonStrs.add(3, "duck");
		 JSONObject o = new JSONObject();
		 o.put("hello", "world");
		 jsonStrs.element(o);
		 System.out.println("array:" + jsonStrs.toString());
		 for( int i = 0; i < jsonStrs.size(); i++) {
			 System.out.println(jsonStrs.get(i));
		 }
	}
 
}
