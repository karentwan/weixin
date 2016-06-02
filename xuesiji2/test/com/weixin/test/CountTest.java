package com.weixin.test;

import java.util.Map;
import org.junit.Test;
import com.weixin.dao.SignInDao;
import com.weixin.util.JSONUtil;

public class CountTest {
	
	@Test
	public void testCount() {
		String time = "2016-05-20";
		Map<String, Integer> map = SignInDao.getInstance().count("Эѕве", time);
		String str = JSONUtil.count2JSON(map, time);
		System.out.println("str:" + str);
	}

}
