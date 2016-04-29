package com.weixin.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import com.weixin.dao.SignInDao;

public class TestQuery {
	
	@Test
	public void testMap() {
		Map<String, String> map = new HashMap<String, String>();
		System.out.println("map:" + map.get("he"));
	}
	
	@Test
	public void testDormitory() {
		SignInDao si = SignInDao.getInstance();
		Map<String, List<String>> map = si.queryDormitory();
		System.out.println("map:" + map);
	}
	
}
