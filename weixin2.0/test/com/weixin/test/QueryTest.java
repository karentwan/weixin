package com.weixin.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import com.weixin.dao.InstruDao;

public class QueryTest {

	@Test
	public void testQuery() {
		
//		String result = InstruDao.getInstance().queryAllAndClazz("万海燕", "2016-05-11");
		String result1 = InstruDao.getInstance().queryAllAndClazz("王艺", "2016-05-11");
//		System.out.println(result);
		System.out.println(result1);
	}
	
//	@Test
	public void testList() {
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < 10; i++) {
			list.add("test---" + i);
		}
		System.out.println(list);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("test start", list);
		map.put("test end", list);
		System.out.println(map);
	}
	@Test
	public void testQueryStu() {
		InstruDao id = InstruDao.getInstance();
//		String str = id.queryAll("万海燕", "2016-05-11");
		String str = id.queryAll("王艺", "2016-05-11");
		System.out.println(str);
	}
}
